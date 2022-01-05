package com.eyas.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eyas.model.DBConnector;
import com.eyas.model.EmployeeBean;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		RequestDispatcher view = req.getRequestDispatcher("login.jsp");
		view.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			String destination  = "login.jsp";
			String username=req.getParameter("username");
			String password=req.getParameter("password");

			EmployeeBean emp = DBConnector.verifyCredentials(username, password);
			
			if (emp!=null) {
				HttpSession session = req.getSession();	
				session.setAttribute("user", emp);
				destination = "index.jsp";
			}
			else {
				String message = "Invalid username or password ";
				req.setAttribute("message", message);
			}
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
