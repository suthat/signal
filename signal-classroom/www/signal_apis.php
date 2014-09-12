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

/* end of file */