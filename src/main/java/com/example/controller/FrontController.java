package com.example.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter("txtType");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		buffer.append("<center>");
		buffer.append("<h3>Welcome To Front Controller</h3>");
		
		String urlPatternServlet = "";
		if(requestType.equals("register")) {
			urlPatternServlet = "Register";
		}
		else if(requestType.equals("login")) {
			urlPatternServlet = "Login";
		} 
		else {
			buffer.append("<p>We cannot process your Request.</p>");
		}
		if(!urlPatternServlet.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(urlPatternServlet);
			dispatcher.include(request, response);
		}
		
		buffer.append("<body><html>");
		out.print(buffer.toString());
	}

}
