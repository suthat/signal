<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

require_once './vendor/autoload.php';
use PhpAmqpLib\Connection\AMQPConnection;
use PhpAmqpLib\Message\AMQPMessage;

class Tests extends CI_Controller {

	public function index()
	{
		
	}

	public function trigger()
	{
		$connection = new AMQPConnection('localhost', 5672, 'guest', 'hello');
		$channel = $connection->channel();
		$channel->exchange_declare('csc102-B787TQA', 'fanout', false, false, false);
		$data = array(
			'question_id' => '123456',
			'question_title' => 'What is Java Programming?',
			'question_body' => 'Characteristics of Java programming language.',
			'answer_list' => array(
				'A' => 'OOP',
				'B' => 'Structural Programming',
				'C' => 'I don\'t know'
			),
			'SN_MESSAGE_TOKEN' => md5('123456'),
			'SN_MESSAGE_UID' => md5(time() . random_string('alnum', 16))
		);
		$msg = new AMQPMessage(json_encode($data));
		$channel->basic_publish($msg, 'csc102-B787TQA');
		echo '[x] Sent '. $data['question_id'] . ' ' . $data['question_title'] . '<br/>';
		echo '[-] SN_MESSAGE_TOKEN '. $data['SN_MESSAGE_TOKEN'] . '<br/>';
		echo '[-] SN_MESSAGE_UID '. $data['SN_MESSAGE_UID'];
		$channel->close();
		$connection->close();
	}

	public function client()
	{
		$this->load->view('tests_client');
	}
}

/* End of file tests.php */
/* Location: ./application/controllers/tests.php */