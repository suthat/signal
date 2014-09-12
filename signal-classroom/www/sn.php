<?php

require_once('signal_apis.php');

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
//$notification = 'b87464b64ab2d7628eee802f2feefa82';
$query = array(
	'select' => 'sn-summary',
	'where' => 'notification='.$notification
);
$mql_json = $sn->generate_mql($query);
//var_dump($mql_json);
$mql_result = $sn->submit_mql($mql_json);
var_dump($mql_result);