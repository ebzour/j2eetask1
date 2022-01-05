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

import org.eclipse.jdt.internal.compiler.env.IModule.IService;

import com.eyas.model.DBConnector;
import com.eyas.model.EmployeeBean;

public class CreateEmployeeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/html");
			HttpSession session = req.getSession();
			EmployeeBean user=((EmployeeBean)(session.getAttribute("user")));
			if(user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}
			String destination = "createEmployee.jsp";
			ArrayList<EmployeeBean> employees = DBConnector.getManagerAndLeads();
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
			EmployeeBean user=((EmployeeBean)(session.getAttribute("user")));
			if(user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}
			String destination = "createEmployee.jsp";
			String name = req.getParameter("name");
			String type = req.getParameter("type");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String lead = req.getParameter("lead");
			DBConnector.createEmployee(name, type, username, password, lead);
			message="created successfully";
			req.setAttribute("message", message);
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
