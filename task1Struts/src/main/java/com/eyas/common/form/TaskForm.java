package com.eyas.common.form;

public class TaskForm {
	private int id;
	private String title;
	private String status;
	private String comment;
	private String emp_user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEmp_user() {
		return emp_user;
	}
	public void setEmp_user(String emp_user) {
		this.emp_user = emp_user;
	}
	
}
