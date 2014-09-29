<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

require_once './vendor/autoload.php';
use PhpAmqpLib\Connection\AMQPConnection;
use PhpAmqpLib\Message\AMQPMessage;

class Apis extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->model('table_users');
		$this->load->model('table_notifications');
		$this->load->model('table_feedbacks');
		$this->load->model('table_fvars');
	}

	public function index()
	{
		$this->unauthorized();
	}

	public function mdl($action = FALSE)
	{
		$param = $_GET['param'];
		if($action === 'submit'){
			$mdl = json_decode(urldecode($param));
			//var_dump($mdl);
			if(! is_null($mdl)){	
				$mdl = $mdl->signalmdl;
				$provider = $mdl->provider;
				$app_id = $provider->id;
				$app_secret = $provider->secret;
				if($this->table_users->getw(array('app_id' => $app_id, 'app_secret' => $app_secret))){
					$scope = $mdl->scope;
					$type = $scope->type;
					$domain = $scope->domain;
					$platform = $scope->platform;
					// notification
					$notification = $mdl->notification;
					$notifytime = $notification->notifytime;
					$restrict = $notification->restrict;
					// notification data
					$data = $notification->data;
					$topic = $data->topic;
					$message = $data->message;
					$link = $data->link;
					$customplatform = $data->customplatform;
					$html5 = $customplatform->html5;
					$push_data = json_encode($html5); 
					$push_data = ltrim($push_data, '[');
					$push_data = rtrim($push_data, ']');
					//var_dump($push_data);
					// feedback
					$feedback = $mdl->feedback;
					$use = $feedback->use;
					$feedback_domain = $feedback->domain;
					$feedback_vars = $feedback->variable;
					//var_dump($feedback_vars);
					$id = md5(time() . random_string('alnum', 32));
					$notification_data = array(
						'id' => $id,
						'app_id' => $app_id,
						'type' => $type,
						'domain' => $domain,
						'platform' => $platform,
						'notifytime' => $notifytime,
						'restrict' => $restrict,
						'topic' => $topic,
						'message' => $message,
						'link' => $link,
						'push_data' => $push_data,
						'feedback_vars' => implode('|', $feedback_vars),
						'token' => md5($id)
					);
					$result = $this->table_notifications->insert($notification_data);
					if($result){
						if($notifytime === 'now'){
							// immediately push
							$this->push($topic, $push_data, $id);
							$pushed_at = date('Y-m-d H:i:s');
							$this->table_notifications->update('id', $id, array('pushed_at' => $pushed_at));
							$message = array(
								'status' => 'SN-OKAY',
								'message' => 'INSERT NOTIFICATION DATA SUCCESSFULLY',
								'notification' => $id,
								'pushed_at' => $pushed_at
							);	
							header('Content-Type: application/json');
							echo json_encode($message);
							exit;
						}else{
							$message = array(
								'status' => 'SN-OKAY',
								'message' => 'INSERT NOTIFICATION DATA SUCCESSFULLY',
								'notification' => $id 
							);	
							header('Content-Type: application/json');
							echo json_encode($message);
							exit;
						}
					}else{
						$message = array(
							'status' => 'SN-FAILED',
							'message' => 'INTERNAL ERROR WHEN INSERT NOTIFICATION DATA'
						);	
						header('Content-Type: application/json');
						echo json_encode($message);
						exit;		
					}
				}else{
					$message = array(
						'status' => 'SN-FAILED',
						'message' => 'UNAUTHORZED APP ID OR APP SECRET IN SIGNAL MESAGGING DESCRIPTION LANGUAGE (MDL)'
					);	
					header('Content-Type: application/json');
					echo json_encode($message);
					exit;	
				}
			}else{
				$message = array(
					'status' => 'SN-FAILED',
					'message' => 'INVALID JSON IN SIGNAL MESAGGING DESCRIPTION LANGUAGE (MDL)'
				);	
				header('Content-Type: application/json');
				echo json_encode($message);
				exit;
			}
		}else{
			$this->bad_request();		
		}
	}

	public function feedback($token = FALSE, $data = FALSE)
	{
		if($token && $data){
			$notification = $this->table_notifications->get('id', $token);
			if($notification){
				$id = md5(time() . random_string('alnum', 32));
				$f = $this->table_feedbacks->getw(array('notification' => $token, 'session' => md5($this->session->userdata('session_id'))));
				if($f){
					$result = $this->table_fvars->delete('feedback', $f->id);
					$result = $this->table_feedbacks->update('id', $f->id, array('created_at' => date('Y-m-d H:i:s')));
				}else{
					$feedback_data = array(
						'id' => $id,
						'notification' => $token,
						'token' => md5($id),
						'session' => md5($this->session->userdata('session_id'))
					);
					$result = $this->table_feedbacks->insert($feedback_data);
				}
				if($result){
					$feedback_vars = array();
					$tmp = explode('|', $notification->feedback_vars);
					foreach ($tmp as $var) {
						$feedback_vars[] = $var;
	 				}
	 				//var_dump($feedback_vars);
	 				$tmp = explode('&', urldecode($data));
	 				foreach ($tmp as $e) {
	 					$e = explode('=', $e);
	 					if(in_array($e[0], $feedback_vars, TRUE)){
	 						$fvar_data = array(
	 							'notification' => $token,
	 							'feedback' => $id,
	 							'variable' => $e[0],
	 							'value' => $e[1]
	 						);
	 						$this->table_fvars->insert($fvar_data);
	 					}
	 				}
	 				$message = array(
						'status' => 'SN-OKAY',
						'message' => 'GOT FEEDBACK DATA',
						'timestamp' => time()
					);	
					header('Content-Type: application/json');
					echo json_encode($message);
					exit;
				}else{
					$message = array(
						'status' => 'SN-FAILED',
						'message' => 'INTERNAL ERROR WHEN INSERT FEEDBACK DATA'
					);	
					header('Content-Type: application/json');
					echo json_encode($message);
					exit;	
				}
			}else{
				$this->bad_request();
			}
		}else{
			$this->bad_request();
		}
	}

	public function mql($action = FALSE, $param = FALSE)
	{
		if($action === 'query'){
			$mql = json_decode(urldecode($param));
			if(! is_null($mql)){	
				$app_id = $mql->id;
				$app_secret = $mql->secret;
				if($this->table_users->getw(array('app_id' => $app_id, 'app_secret' => $app_secret))){
					$select = $mql->select;
					if(strtolower($select) === 'sn-summary'){
						$where = $mql->where;
						$where = explode('=', $where);
						$notification = $this->table_notifications->get('id', $where[1]);
						if($notification){
							$result = array();
							$values = array();
							$feedback_vars = explode('|', $notification->feedback_vars);
							foreach ($feedback_vars as $var) {
								$result[$var . '_counter'] = $this->table_fvars->count(array('notification' => $where[1], 'variable' => $var));
								$tmp = $this->table_fvars->query('SELECT DISTINCT value FROM fvars WHERE notification = "'.$where[1].'" AND variable = "'.$var.'";');
								//var_dump($tmp); die();
								if($tmp){
									foreach ($tmp as $value) {
										$values[$var.'.'.$value->value] = $this->table_fvars->count(array('notification' => $where[1], 'value' => $value->value)); 
									}
								}
							}
							$feedback_counter = $this->table_feedbacks->count(array('notification' => $notification->id));
							$message = array(
								'notification' => $notification->id,
								'feedback_counter' => $feedback_counter,
								'variable_counter' => $result,
								'values_counter' => $values
							);
							header('Content-Type: application/json');
							echo json_encode($message);
						}else{
							$this->bad_request();
						}	
					}
				}else{
					$message = array(
						'status' => 'SN-FAILED',
						'message' => 'UNAUTHORZED APP ID OR APP SECRET IN SIGNAL MESAGGING DESCRIPTION LANGUAGE (MDL)'
					);	
					header('Content-Type: application/json');
					echo json_encode($message);
					exit;	
				}
			}
		}
	}

	public function bad_request()
	{
		$message = array(
			'status' => 400,
			'message' => 'Bad Request'
		);	
		header("HTTP/1.1 401 Bad Request");
		header('Content-Type: application/json');
		echo json_encode($message);
		exit;
	}

	public function unauthorized()
	{
		$message = array(
			'status' => 401,
			'message' => 'Unauthorized'
		);	
		header("HTTP/1.1 401 Unauthorized");
		header('Content-Type: application/json');
		echo json_encode($message);
		exit;
	}

	private function push($topic = FALSE, $msg = FALSE, $token = FALSE)
	{
		$connection = new AMQPConnection('localhost', 5672, 'guest', 'hello');
		$channel = $connection->channel();
		$channel->exchange_declare($topic, 'fanout', false, false, false);
		
		$msg = json_decode($msg, TRUE);
		$msg['SN_MESSAGE_TOKEN'] = $token;
		$msg = json_encode($msg);
		
		$msg = new AMQPMessage($msg);
		$channel->basic_publish($msg, $topic);
		$channel->close();
		$connection->close();
	}
}

/* End of file apis.php */
/* Location: ./application/controllers/apis.php */