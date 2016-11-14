package io.moorea.model;

public class JsonResult {
	
	private Boolean success;
	private String msg;
	private Object object;
	
	public JsonResult(Boolean success, String msg){
		this.success = success;
		this.msg = msg;
	}
	
	public JsonResult(Boolean success, String msg, Object object){
		this.success = success;
		this.msg = msg;
		this.object = object;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	

}
