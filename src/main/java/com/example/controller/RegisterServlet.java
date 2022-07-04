package com.example.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.db.DB;
import com.example.model.User;

public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	DB db;
	
	//Serve as a constructor 
	// We do initialization here
	public void init(ServletConfig config) throws ServletException {
		System.out.println("[RegisterServlet] - init");
		db = DB.getDB();
	}
	
	//Serve as a destructor
	// May be save some data before destruction or close connection
	public void destroy() {
		System.out.println("[RegisterServlet] - destroy");
		db.closeConnection();
	}

	//Which will be executed whenever a new request comes from the client  
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[RegisterServlet] - service ");
		User user = new User();
		user.name = request.getParameter("txtName");
		user.email = request.getParameter("txtEmail");
		user.password = request.getParameter("txtPassword");
		
		System.out.println("[RegisterServlet] - " + user);
		String sql = user.toRegisterSQL();
		System.out.println("[RegisterServlet] - " + sql);
		
		int result = db.executeUpdate(sql);
		String message = result>0 ? user.name+" Thank You For Registring with us"
								   : user.name+" Registration Failed. Please Try Again";
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		buffer.append("<center>");
		buffer.append("<h3>"+message+"</h3>");
		buffer.append("<p>Registered On: "+user.registerOn+"</p>");
		buffer.append("<body><html>");
		
		out.print(buffer.toString()); 
		   
	}

}
