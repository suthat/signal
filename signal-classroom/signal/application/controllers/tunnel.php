<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Tunnel extends CI_Controller {

	public function index()
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

	public function authorize()
	{
		if($this->session->userdata('user_logged_in') === FALSE){
			redirect(site_url('tunnel/login'), 'refresh');
		}else{
			redirect(site_url('dashboard/?sess=' . $this->session->userdata('session_id')), 'refresh');
		}
	}

	public function login()
	{
		if($this->session->userdata('user_logged_in')){
			redirect(site_url('dashboard/?sess=' . $this->session->userdata('session_id')), 'refresh');
		}
		$this->load->view('tunnel/login');
	}

	public function authenticate()
	{
		$email = strtolower(trim($this->input->post('email')));
		$password = md5($this->input->post('password'));
		$this->load->model('table_users');
		$result = $this->table_users->getw(array('email' => $email, 'password' => $password));
		if($result){
			$user = $result;
			$user_data = array(
				'user_logged_in' => TRUE,
				'user_id' => $user->id,
				'user_name' => $user->name,
				'user_email' => $user->email,
				'app_id' => $user->app_id,
				'app_title' => $user->app_title,
				'app_excerpt' => $user->app_excerpt,
				'app_id' => $user->app_id,
				'app_secret' => $user->app_secret
			);
			$this->session->set_userdata($user_data);
			redirect(site_url('tunnel/authorize'), 'refresh');
		}else{
			redirect(site_url('tunnel/login/?failed=true'), 'refresh');	
		}
	}

	public function logout()
	{
		$this->session->sess_destroy();
		redirect(site_url('tunnel/authorize'), 'refresh');
	}
}

/* End of file tunnel.php */
/* Location: ./application/controllers/tunnel.php */