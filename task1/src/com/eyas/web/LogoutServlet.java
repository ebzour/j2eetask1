package com.eyas.web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eyas.model.EmployeeBean;

public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session != null) // If session is not null
		{
			resp.setContentType("text/html");
			String destination = "index.jsp";
			session.invalidate(); // removes all session attributes bound to the session
			String message = "You Have Logged Out Successfully ";
			req.setAttribute("message", message);
			RequestDispatcher view = req.getRequestDispatcher(destination);
			view.forward(req, resp);
		} else {
			RequestDispatcher view = req.getRequestDispatcher("login.jsp");
			req.setAttribute("message", "Session Expired");
			view.forward(req, resp);
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}