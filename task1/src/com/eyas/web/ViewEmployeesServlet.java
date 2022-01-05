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

public class ViewEmployeesServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setHeader("Cache-Control","no-cache"); //HTTP 1.1
			resp.setHeader("Pragma","no-cache"); //HTTP 1.0
			resp.setDateHeader ("Expires", 0);
			ArrayList<EmployeeBean> employees = null;
			ArrayList<EmployeeBean> managerAndLeads = null;
			HttpSession session = req.getSession();
			resp.setContentType("text/html");
			EmployeeBean user=((EmployeeBean)(session.getAttribute("user")));
			if(user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}			
			String type =user.getType();
			String destination = "viewEmployees.jsp";
			if(type.equals("Manager")) {
				employees = DBConnector.getAllEmployees();
				managerAndLeads =DBConnector.getManagerAndLeads();
				}
			else if(type.equals("Leader")){
				employees=DBConnector.getLeadAndDevelopers(user.getUsername());
				}
			else {
				employees=DBConnector.getDeveloper(user.getUsername());
			}
			String [] types={"Manager","Leader","Developer"};
			session.setAttribute("types", types);
			session.setAttribute("m_leads", managerAndLeads);
			session.setAttribute("employees", employees);
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
