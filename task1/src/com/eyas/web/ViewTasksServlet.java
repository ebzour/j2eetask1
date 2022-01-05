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

public class ViewTasksServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			ArrayList<TaskBean> tasks = null;
			resp.setContentType("text/html");
			String destination = "viewTasks.jsp";
			ArrayList<EmployeeBean> emp_users = null;

			EmployeeBean user=((EmployeeBean)(session.getAttribute("user")));
			if(user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}
			
			String type =user.getType();
			if(type.equals("Manager")) {
				 tasks = DBConnector.getAllTasks();
				emp_users=DBConnector.getAllEmployees();
				 }
			else if(type.equals("Leader")){
				tasks=DBConnector.getLeadAndDevelopersTasks(user.getUsername());
				emp_users=DBConnector.getLeadAndDevelopers(user.getUsername());
				}
			else {
				tasks=DBConnector.getTasksFor(user.getUsername());
				
			}
			String [] task_status={"Done","In Progress","Pending Approval","Canceled"};
			session.setAttribute("emp_users", emp_users);
			session.setAttribute("task_status", task_status);
			session.setAttribute("tasks", tasks);
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
