<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Dashboard extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		if($this->session->userdata('user_logged_in') === FALSE){
			redirect(site_url('tunnel/login'), 'refresh');
		}
		$this->load->model('table_users');
	}

	public function index()
	{
		$data['user'] = $this->table_users->get('id', $this->session->userdata('user_id'));
		$this->load->view('dashboard/index', $data);	
	}
}

/* End of file dashboard.php */
/* Location: ./application/controllers/dashboard.php */