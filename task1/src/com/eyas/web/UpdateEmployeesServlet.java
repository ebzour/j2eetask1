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
import com.eyas.model.TaskBean;

public class UpdateEmployeesServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		resp.setHeader("Pragma","no-cache"); //HTTP 1.0
		resp.setDateHeader ("Expires", 0);
		
		String destination = "/view_employees";
		RequestDispatcher view = req.getRequestDispatcher(destination);
		view.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			resp.setHeader("Cache-Control","no-cache"); //HTTP 1.1
			resp.setHeader("Pragma","no-cache"); //HTTP 1.0
			resp.setDateHeader ("Expires", 0);
			
			ArrayList<EmployeeBean> employees = null;
			HttpSession session = req.getSession();
			resp.setContentType("text/html");
			String destination = "/view_employees";
			EmployeeBean user = ((EmployeeBean) (session.getAttribute("user")));

			if (user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}

			String type = user.getType();
			if (type.equals("Manager")) {
				employees = DBConnector.getAllEmployees();
			}
			for (EmployeeBean empB : employees) {
				String emp_name = req.getParameter("name_" + empB.getID());
				String emp_type = req.getParameter("type_" + empB.getID());
				String emp_username = req.getParameter("username_" + empB.getID());
				String emp_password = req.getParameter("password_" + empB.getID());
				String emp_lead = req.getParameter("lead_" + empB.getID());
				DBConnector.updateEmployee(empB.getID(), emp_name, emp_type, emp_username, emp_password, emp_lead);
			}
			RequestDispatcher view = req.getRequestDispatcher(destination);
			req.setAttribute("message", "Updated , please refresh or go back");
			view.forward(req, resp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
