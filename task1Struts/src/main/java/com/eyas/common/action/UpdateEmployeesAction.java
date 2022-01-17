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
import com.eyas.common.helpers.Utilities;

public class UpdateEmployeesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		resp.setContentType("text/html");
		resp.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
		resp.setDateHeader("Expires", 0);
		HttpSession session = req.getSession();
		String destination = "success";
		String message = "No Change";
		req.setAttribute("headerMsg", "Employees Details:");

		try {
			EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));

			if (user == null) {
				destination = "failure";
				message = "Session Expired";
				req.setAttribute("message", message);
				return mapping.findForward(destination);
			}
			ArrayList<EmployeeForm> employees = null;
			String type = user.getType();

			if (type.equals("Manager")) {
				employees = EmployeeHelper.getAllEmployees();
			}

			for (EmployeeForm empB : employees) {

				String emp_name = req.getParameter("name_" + empB.getId());
				String emp_type = req.getParameter("type_" + empB.getId());
				String emp_username = req.getParameter("username_" + empB.getId());
				String emp_password = req.getParameter("password_" + empB.getId());
				String emp_lead = req.getParameter("lead_" + empB.getId());

				EmployeeForm updatedEmployee = new EmployeeForm();

				updatedEmployee.setId(empB.getId());
				updatedEmployee.setName(emp_name);
				updatedEmployee.setType(emp_type);
				updatedEmployee.setUsername(emp_username);
				updatedEmployee.setPassword(emp_password);
				updatedEmployee.setLead(emp_lead);

				if (!Utilities.EmployeeEquals(empB, updatedEmployee)) {
					EmployeeHelper.updateEmployee(empB.getId(), emp_name, emp_type, emp_username, emp_password,
							emp_lead);
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
