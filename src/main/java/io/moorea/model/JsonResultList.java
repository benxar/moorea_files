package io.moorea.model;

import java.util.List;

public class JsonResultList {
	
	private Boolean success;
	private String msg;
	private List<Object> list;
	private Long total_items;
	
	public Long getTotal_items() {
		return total_items;
	}

	public void setTotal_items(Long total_items) {
		this.total_items = total_items;
	}

	public JsonResultList(Boolean success, String msg, List<Object> list){
		this.success = success;
		this.msg = msg;
		this.list = list;
	}
	
	public JsonResultList(Boolean success, String msg, List<Object> list, Long total_items){
		this.success = success;
		this.msg = msg;
		this.list = list;
		this.setTotal_items(total_items);
		
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
	
	

}
