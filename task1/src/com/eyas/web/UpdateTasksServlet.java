package com.eyas.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eyas.model.DBConnector;
import com.eyas.model.EmployeeBean;
import com.eyas.model.TaskBean;

public class UpdateTasksServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			ArrayList<TaskBean> tasks = null;
			resp.setContentType("text/html");
			String destination = "viewTasks.jsp";
			EmployeeBean user = ((EmployeeBean) (session.getAttribute("user")));
			if (user == null) {
				RequestDispatcher view = req.getRequestDispatcher("login.jsp");
				req.setAttribute("message", "Session Expired");
				view.forward(req, resp);
			}

			String type = user.getType();
			if (type.equals("Manager")) {
				tasks = DBConnector.getAllTasks();
			} else if (type.equals("Leader")) {
				tasks = DBConnector.getLeadAndDevelopersTasks(user.getUsername());
			} else {
				tasks = DBConnector.getTasksFor(user.getUsername());
			}
			for(TaskBean taskB : tasks) {
				String title = req.getParameter("title_"+taskB.getID());
				String status = req.getParameter("status_"+taskB.getID());
				String comment = req.getParameter("comment_"+taskB.getID());
				String username = req.getParameter("emp_user_"+taskB.getID());
				DBConnector.updateTask(taskB.getID(), title,status, comment, username);
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
