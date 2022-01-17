package com.eyas.common.action;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eyas.common.form.EmployeeForm;
import com.eyas.common.form.TaskForm;
import com.eyas.common.helpers.EmployeeHelper;
import com.eyas.common.helpers.TaskHelper;

public class ViewTasksAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
			resp.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
			resp.setDateHeader("Expires", 0);
			resp.setContentType("text/html");
			HttpSession session = req.getSession();
			String destination = "success";
			req.setAttribute("headerMsg", "Tasks Details :");
		try {
			ArrayList<TaskForm> tasks = null;
			ArrayList<EmployeeForm> emp_users = null;

			EmployeeForm user=((EmployeeForm)(session.getAttribute("user")));
			if(user == null) {
				destination="failure";
				req.setAttribute("message", "Session Expired");
				return mapping.findForward(destination);
			}
			
			String type =user.getType();
			if(type.equals("Manager")) {
				 tasks = TaskHelper.getAllTasks();
				emp_users=EmployeeHelper.getAllEmployees();
				 }
			else if(type.equals("Leader")){
				tasks=TaskHelper.getLeadAndDevelopersTasks(user.getUsername());
				emp_users=EmployeeHelper.getLeadAndDevelopers(user.getUsername());
				}
			else {
				tasks=TaskHelper.getTasksFor(user.getUsername());
				
			}
			ArrayList<String> task_status=TaskHelper.getAllStatus();
			req.setAttribute("emp_users", emp_users);
			req.setAttribute("task_status", task_status);
			req.setAttribute("tasks", tasks);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward(destination);
	}
}