package com.eyas.common.action;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eyas.common.helpers.EmployeeHelper;
import com.eyas.common.form.EmployeeForm;

public class LoginAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		resp.setContentType("text/html");
		String destination = "failure";
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			if(username ==null || password==null)
				return mapping.findForward(destination);
			
			EmployeeForm emp = EmployeeHelper.verifyCredentials(username, password);
			
			if (emp != null) {
				HttpSession session = req.getSession();
				session.setAttribute("user", emp);
				destination = "success";
				req.setAttribute("headerMsg", "Welcome "+emp.getName()+" !");
			} else {
				String message = "Invalid username or password ";
				req.setAttribute("message", message);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward(destination);
	}

}
