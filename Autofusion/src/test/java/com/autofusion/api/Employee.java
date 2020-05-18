package com.autofusion.api;

import java.util.Date;

@SuppressWarnings("all")
public class Employee {

	private String courseid;
	   private String rsp;
	   public Employee(String courseid, String rsp, String title, String sid) {
		super();
		this.courseid = courseid;
		this.rsp = rsp;
		this.title = title;
		this.sid = sid;
	}

	private String title;
	   private String sid;
	   
	   
	   public Employee(){
	 
	   }
	 
	   @Override
	   public String toString()
	   {
	      return "Employee [id=" + courseid + ", firstName=" + rsp + ", " +
	            "lastName=" + sid + "]";
	   }

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
