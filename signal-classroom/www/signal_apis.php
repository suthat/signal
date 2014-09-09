<?php 

class Signal_apis {

	public $SIGNAL_HOST = 'http://localhost/signal';

	public $ROOT = 'signalmdl';
	public $app_id = '';
	public $app_secret = '';
	public $type = 'pubsub'; 
	public $domain = 'websocket';
	public $platform = 'html5';
	public $notifytime = 'now';
	public $restrict = 'high'; 
	public $topic = 'hello';
	public $message = '';
	public $link = '';
	public $custom_json_data = array();
	public $use_feedback = True;
	public $feedback_domain = 'mql';
	public $feedback_vars = array();

	public function __construct($app_id, $app_secret)
	{	
		$this->app_id = $app_id;
		$this->app_secret = $app_secret;
	}

	public function generate_mdl()
	{
		$mdl = array();
		$mdl[$this->ROOT] = array(
			'provider' => array(
				'id' => $this->app_id,
				'secret' => $this->app_secret
			),
			'scope' => array(
				'type' => $this->type,
				'domain' => $this->domain,
				'platform' => $this->platform
			),
			'notification' => array(
				'notifytime' => $this->notifytime,
				'restrict' => $this->restrict,
				'data' => array(
					'topic' => $this->topic,
					'message' => $this->message,
					'link' => $this->link,
					'customplatform' => array(
						'html5' => $this->custom_json_data
					)
				)
			),
			'feedback' => array(
				'use' => $this->use_feedback,
				'domain' => $this->feedback_domain,
				'variable' => $this->feedback_vars
			)
		);
		//var_dump($mdl);
		return json_encode($mdl);
	}

	public function submit_mdl($json)
	{
        $ch = curl_init(); 
        curl_setopt($ch, CURLOPT_URL, $this->SIGNAL_HOST . '/apis/mdl/submit/' . urlencode($json)); 
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
        $output = curl_exec($ch); 
        curl_close($ch);  
    	return json_decode($output);
	}

	public function send_feedback($token, $data)
	{
        $ch = curl_init(); 
        curl_setopt($ch, CURLOPT_URL, $this->SIGNAL_HOST . '/apis/feedback/'.$token.'/' . urlencode($data)); 
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
        $output = curl_exec($ch); 
        curl_close($ch);  
    	return json_decode($output);
	}

	public function generate_mql($mql = array())
	{
		$mql['id'] = $this->app_id;
		$mql['secret'] = $this->app_secret;
		return json_encode($mql);	
	}

	public function submit_mql($json)
	{
        $ch = curl_init(); 
        curl_setopt($ch, CURLOPT_URL, $this->SIGNAL_HOST . '/apis/mql/query/' . urlencode($json)); 
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
        $output = curl_exec($ch); 
        curl_close($ch);  
    	return json_decode($output);
	}

}

$sn = new Signal_apis('3422841103384567', 'cef9d37913b8449d9610f4f653f0ef01');
$sn->topic = 'csc102-B787TQA';

$q = array(
	'question_id' => '123456',
	'question_title' => 'Basic Java',
	'question_body' => 'What is main characteristic of Java?',
	'answer_list' => array(
		'A' => 'OOP',
		'B' => 'Structural',
		'C' => 'Boht',
		'D' => 'No right anwser'
	)
);
$sn->custom_json_data[] = $q;
$sn->feedback_vars[] = 'student_id';
$sn->feedback_vars[] = 'answer';

$json = $sn->generate_mdl();
//var_dump($json);
$result = $sn->submit_mdl($json);
$notification = $result->notification;
if($notification){
	echo 'got >>> '.$notification;
}else{
	echo 'failed';
}

// QUERY FEEDBACK
$notification = 'ff82b70b419d6c3dcf80b1f7aef53ac1';
$query = array(
	'select' => 'sn-summary',
	'where' => 'notification='.$notification
);
$mql_json = $sn->generate_mql($query);
var_dump($mql_json);
$mql_result = $sn->submit_mql($mql_json);
var_dump($mql_result);

/* end of file */