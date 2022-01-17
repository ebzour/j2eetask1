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

public class ViewEmployeesAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		resp.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
		resp.setDateHeader("Expires", 0);
		resp.setContentType("text/html");
		String destination = "success";
		HttpSession session = req.getSession();
		req.setAttribute("headerMsg", "Employees Details :");

		try {
			ArrayList<EmployeeForm> employees = null;
			ArrayList<EmployeeForm> managerAndLeads = null;
			EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));
			if (user == null) {
				destination = "failure";
				req.setAttribute("message", "Session Expired");
				return mapping.findForward(destination);
			}
			String type = user.getType();
			if (type.equals("Manager")) {
				employees = EmployeeHelper.getAllEmployees();
				managerAndLeads = EmployeeHelper.getManagerAndLeads();
			} else if (type.equals("Leader")) {
				employees = EmployeeHelper.getLeadAndDevelopers(user.getUsername());
			} else {
				employees = EmployeeHelper.getDeveloper(user.getUsername());
			}
			ArrayList<String> types= EmployeeHelper.getAllTypes();
			req.setAttribute("types", types);
			req.setAttribute("m_leads", managerAndLeads);
			req.setAttribute("employees", employees);
			return mapping.findForward(destination);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward(destination);
	}

}
