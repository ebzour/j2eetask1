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
import com.eyas.common.helpers.TaskHelper;
import com.eyas.common.helpers.Utilities;

public class UpdateTasksAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		resp.setContentType("text/html");
		resp.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
		resp.setDateHeader("Expires", 0);
		HttpSession session = req.getSession();
		String destination = "success";
		String message = "no change";
		req.setAttribute("headerMsg", "Tasks Details:");

		try {
			
			ArrayList<TaskForm> tasks = null;
			EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));
			if (user == null) {
				destination = "failure";
				message = "Session Expired";
				req.setAttribute("message", message);
				return mapping.findForward(destination);
			}
			String type = user.getType();
			if (type.equals("Manager")) {
				tasks = TaskHelper.getAllTasks();
			} else if (type.equals("Leader")) {
				tasks = TaskHelper.getLeadAndDevelopersTasks(user.getUsername());
			} else {
				tasks = TaskHelper.getTasksFor(user.getUsername());
			}
			for (TaskForm taskB : tasks) {
				String title = req.getParameter("title_" + taskB.getId());
				String status = req.getParameter("status_" + taskB.getId());
				String comment = req.getParameter("comment_" + taskB.getId());
				String username = req.getParameter("emp_user_" + taskB.getId());

				TaskForm updatedTask = new TaskForm();
				updatedTask.setId(taskB.getId());
				updatedTask.setTitle(title);
				updatedTask.setStatus(status);
				updatedTask.setComment(comment);
				updatedTask.setEmp_user(username);

				if (!Utilities.TaskEquals(taskB, updatedTask)) {
					TaskHelper.updateTask(taskB.getId(), title, status, comment, username);
					message = "Updated";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("message", message);
		return mapping.findForward(destination);

	}
}
