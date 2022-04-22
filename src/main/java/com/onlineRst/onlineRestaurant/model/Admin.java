package com.onlineRst.onlineRestaurant.model;

import org.springframework.stereotype.Component;

@Component
public class Admin {
private String adminName;
private int adminPassword;
public Admin() {
	super();
	System.out.println("Admin object cretaed");
	}
public Admin(String adminId, int adminPassword) {
	super();
	this.adminName = adminId;
	this.adminPassword = adminPassword;
}
@Override
public String toString() {
	return "Admin [adminName=" + adminName + ", adminPassword=" + adminPassword + "]";
}
public String getAdminId() {
	return adminName;
}
public void setAdminId(String adminName) {
	this.adminName = adminName;
}
public int getAdminPassword() {
	return adminPassword;
}
public void setAdminPassword(int adminPassword) {
	this.adminPassword = adminPassword;
}
}
