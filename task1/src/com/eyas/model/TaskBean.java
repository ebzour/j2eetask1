package com.eyas.model;

public class TaskBean {
	private int ID;
	private String Title;
	private String Status;
	private String Comment;
	private String Emp_user;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getEmp_user() {
		return Emp_user;
	}
	public void setEmp_user(String Emp_user) {
		this.Emp_user = Emp_user;
	}
	
}
