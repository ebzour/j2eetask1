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

public class CreateEmployeeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		String message = "";
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		String destination = "success";
		req.setAttribute("headerMsg", "Input Employee Details :");

		try {
			
			EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));
			if (user == null) {
				destination = "failure";
				message = "Session Expired";
				req.setAttribute("message", message);
				return mapping.findForward(destination);
			}
			
			String name = req.getParameter("name");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			if(name==null&&username==null&&password==null) {
				ArrayList<EmployeeForm> employees = EmployeeHelper.getManagerAndLeads();
				session.setAttribute("employees", employees);
			}
			
			else {
				String type = req.getParameter("type");
				String lead = req.getParameter("lead");
				EmployeeHelper.createEmployee(name, type, username, password, lead);
				message = "created successfully";
				req.setAttribute("message", message);
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward(destination);
	}
}
