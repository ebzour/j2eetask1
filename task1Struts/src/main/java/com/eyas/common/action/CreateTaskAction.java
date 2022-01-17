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
import com.eyas.common.helpers.EmployeeHelper;
import com.eyas.common.helpers.TaskHelper;


public class CreateTaskAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		String message = "";
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		String destination = "success";
		req.setAttribute("headerMsg", "Input Task Details :");
		try {
			EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));
			if (user == null) {
				destination = "failure";
				message = "Session Expired";
				req.setAttribute("message", message);
				return mapping.findForward(destination);
			}
			String title = req.getParameter("title");
			String comment = req.getParameter("comment");
			if(title==null&&comment==null) {
				ArrayList<EmployeeForm> employees = EmployeeHelper.getLeadDevelopers(user.getUsername());
				session.setAttribute("employees", employees);
			}
			else {
				String status ="";
				String username = "";
				if(user.getType().equals("Developer")) {
					username=user.getUsername();
					status="Pending Approval";
					}
				else {
					username=req.getParameter("username");
					status= req.getParameter("status");;
					}				
				TaskHelper.createTask(title, status, comment, username);
				message = "created successfully";
				req.setAttribute("message", message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mapping.findForward(destination);
	}

}
