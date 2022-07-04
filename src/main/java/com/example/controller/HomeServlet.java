package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import com.example.model.User;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String uid= request.getParameter("uid");
		String name= request.getParameter("name");
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		buffer.append("<center>");
		buffer.append("<br><br><h3>Welcome To Home</h3>");
		buffer.append("<p>Its: "+new Date()+"</p>");  
		
//		Cookie[] cookieArr = request.getCookies();
//		
//		for(Cookie cookie : cookieArr) {
//			buffer.append("<p>"+cookie.getName()+" ~ "+cookie.getValue()+"</p>");
//		}
		
		//Session Tracking with URL ReWriting
		buffer.append("<p>[URL ReWriting] USER ID: "+uid+" NAME: "+name+"</p>");
		
		
		//Session Tracking with Http - Reading the data
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("keyUser");
		
		buffer.append("<p> [HTTP Session] USER ID: "+uid+" NAME: "+name+"</p>");
		
		buffer.append("<p> [Hidden Form] USER ID: "+uid + " NAME: "+name+"</p>");
		
		buffer.append("</body></html>");
		out.print(buffer.toString());
		
		
	}

}
