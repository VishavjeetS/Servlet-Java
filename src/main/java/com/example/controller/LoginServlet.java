package com.example.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import com.example.db.DB;
import com.example.model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DB db;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("[LoginServlet] - init");
		db = DB.getDB();
	}
	public void destroy() {
		System.out.println("[LoginServlet] - destroy");
		db.closeConnection();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[LoginServlet] - doPost");
		User user = new User();
		user.email = request.getParameter("txtEmail");
		user.password = request.getParameter("txtPassword");
		
		System.out.println("[LoginServlet] - " + user);
		String sql = user.toLoginSQL();
		System.out.println("[LoginServlet] - " + sql);
		
		ResultSet set = db.executeQuery(user.toLoginSQL());
		
		boolean isRowAvailable = false;
		String message = "";
		try {
			isRowAvailable = set.next();
			if(isRowAvailable) {
				user.uid = set.getInt("uid");
				user.name = set.getString("name");
				user.registerOn = set.getString("registeredOn");
				
				
				//Session Tracking with URL ReWriting
				String queryString = "Welcome?uid="+user.uid+"&name="+user.name;
				String urlToHome = "<p><a href='"+queryString+"'>Click to Navigate to Home</a></p>";
				
				
				//Session Tracking with Hidden Form Field - Writing the data 
				String formToHome = "<form action='Welcome' method ='post'>" + 
									"<input type='hidden' name='txtUID' value='"+user.uid+"'>" + 
									"<input type='hidden' name='txtName' value='"+user.name+"'>" +
//									"<input type='submit' value='Navigate To Home '>" + 
									"</form>";
				message = "<h3>Login Successfull for "+user.name+"</h3>"
						+ urlToHome + formToHome; 
				
				//Session Tracking with Cookies
				Cookie cookie1 = new Cookie("keyName", user.name);
				Cookie cookie2 = new Cookie("keyEmail", user.email);
//				Cookie cookie3 = new Cookie("keyRegesteredOn", user.registerOn);
				Cookie cookie4 = new Cookie("keyUID", String.valueOf(user.uid));
				
				response.addCookie(cookie1);
				response.addCookie(cookie2);
//				response.addCookie(cookie3);
				response.addCookie(cookie4);
				
				
				//Session Tracking with HTTP- Writing the data
				HttpSession session = request.getSession();
				session.setAttribute("keyUser", user);
			}
			else {
				message = "<h3>Login Failed for "+user.name+"</h3>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		buffer.append("<center>");
		buffer.append("<h3>"+message+"</h3>");
		buffer.append("<p>Registered On: "+user.uid +" | "+user.registerOn+"</p>");
		buffer.append("<body><html>");
		
		out.print(buffer.toString()); 
	}

}
