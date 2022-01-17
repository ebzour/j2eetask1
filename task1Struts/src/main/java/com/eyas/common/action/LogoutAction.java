package com.eyas.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		resp.setContentType("text/html");
		String destination = "success";
		String message;
		HttpSession session = req.getSession(false);
		if (session != null) // If session is not null
		{
			session.invalidate(); // removes all session attributes bound to the session
			message = "You Have Logged Out Successfully ";
		} else {
			destination = "failure";
			message = "Session Expired";
		}
		req.setAttribute("headerMsg", "Input Login Credentials");
		req.setAttribute("message", message);
		return mapping.findForward(destination);

	}

}