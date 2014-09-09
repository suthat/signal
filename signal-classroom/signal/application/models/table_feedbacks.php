<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Table_feedbacks extends CI_Model {
	
	public function __construct()
	{
		parent::__construct();
	}

	public function existing($field = FALSE, $value = FALSE)
	{
		$this->db->select('id');
		$this->db->from('feedbacks');
		$this->db->where($field, $value);
		$this->db->limit(1);
		$result = $this->db->get();
		if($result->num_rows() > 0){
			return TRUE;
		}else{
			return FALSE;
		}
	}

	public function insert($data = FALSE)
	{
		$result = $this->db->insert('feedbacks', $data);
		if($result == 1){
			return TRUE;
		}else{
			return FALSE;
		}
	}

	public function update($field = FALSE, $value = FALSE, $data = FALSE)
	{
		$this->db->where($field, $value);
		$result = $this->db->update('feedbacks', $data);
		if($result == 1){
			return TRUE;
		}else{
			return FALSE;
		}
	}

	public function delete($field = FALSE, $value = FALSE)
	{
		$this->db->where($field, $value);
		$result = $this->db->delete('feedbacks');
		if($result == 1){
			return TRUE;
		}else{
			return FALSE;
		}
	}

	public function get($field = FALSE, $value = FALSE)
	{
		$this->db->select('*');
		$this->db->from('feedbacks'); 
		$this->db->where($field, $value);
		$this->db->limit(1);
		$result = $this->db->get();
		if($result->num_rows() > 0){
			return $result->row();
		}else{
			return FALSE;
		}
	}

	public function getw($where = FALSE)
	{
		$this->db->select('*');
		$this->db->from('feedbacks'); 
		$this->db->where($where);
		$this->db->limit(1);
		$result = $this->db->get();
		if($result->num_rows() > 0){
			return $result->row();
		}else{
			return FALSE;
		}
	}

	public function filter($field = FALSE, $value = FALSE, $start = 0, $end = 50, $order_by = 'created_at desc')
	{
		$this->db->select('*');
		$this->db->from('feedbacks'); 
		if($field && $value){
			$this->db->where($field, $value);
		}
		$this->db->order_by($order_by);
		$this->db->limit($end, $start);
		$this->db->distinct();
		$result = $this->db->get();
		if($result->num_rows() > 0){
			return $result->result();
		}else{
			return FALSE;
		}
	}

	public function filterw($where = FALSE, $start = 0, $end = 50, $order_by = 'created_at desc')
	{
		$this->db->select('*');
		$this->db->from('feedbacks'); 
		if($where){
			$this->db->where($where);
		}
		$this->db->order_by($order_by);
		$this->db->limit($end, $start);
		$this->db->distinct();
		$result = $this->db->get();
		if($result->num_rows() > 0){
			return $result->result();
		}else{
			return FALSE;
		}
	}

	public function count($where = FALSE)
	{
		if($where){
			$this->db->where($where);
		}
		return $this->db->count_all_results('feedbacks');
	}
}

/* End of file table_feedbacks.php */
/* Location: ./application/models/table_feedbacks.php */