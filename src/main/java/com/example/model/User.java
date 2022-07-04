package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 	create table User(
 		uid int primary key auto_increment,
 		name varchar(256),
 		email varchar(256),
 		password varchar(256),
 		registeredOn datetime
 	);
 */



public class User {
	public int uid;
	public String name;
	public String email;
	public String password;
	public String registerOn;
	
	public User() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
		registerOn = formatter.format(date);
	}

	public User(int uid, String name, String email, String password, String registerOn) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.password = password;
		this.registerOn = registerOn;
	}



	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", email=" + email + ", password=" + password + ", registerOn="
				+ registerOn + "]";
	}

	public String toLoginSQL() {
		return "select * from User where email = '"+email+"' and password = '"+password+"'";  
	}

	public String toRegisterSQL() {
		return "insert into User values(null, '"+name+"', '"+email+"', '"+password+"', '"+registerOn+"')";
	}
}
