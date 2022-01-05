package com.eyas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eyas.model.DBConnector;
import com.eyas.model.EmployeeBean;

public class CreateTaskServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			HttpSession session = req.getSession();
			String destination = "createTask.jsp";
			EmployeeBean user=((EmployeeBean)(session.getAttribute("user")));
			if(user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}
			
			ArrayList<EmployeeBean> employees = DBConnector.getLeadDevelopers(user.getUsername());
			session.setAttribute("employees", employees);
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String message="";
			resp.setContentType("text/html");
			HttpSession session = req.getSession();
			String destination = "createTask.jsp";
			String title = req.getParameter("title");
			String status = req.getParameter("status");
			String comment = req.getParameter("comment");
			String username = req.getParameter("username");
			DBConnector.createTask(title, status, comment, username);
			message="created successfully";
			req.setAttribute("message", message);
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
