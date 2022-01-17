package com.eyas.common.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eyas.common.form.EmployeeForm;
import com.eyas.common.form.TaskForm;

public class Utilities {

	public static boolean checkSessionExpiration(HttpServletRequest req) {
		HttpSession session = req.getSession();
		EmployeeForm user = ((EmployeeForm) (session.getAttribute("user")));
		return (user == null);
	}

	public static boolean EmployeeEquals(EmployeeForm e1, EmployeeForm e2) {
		return e1.getName().equals(e2.getName()) && e1.getType().equals(e2.getType())
				&& e1.getUsername().equals(e2.getUsername()) && e1.getPassword().equals(e2.getPassword())
				&& e1.getLead().equals(e2.getLead());
	}

	public static boolean TaskEquals(TaskForm t1, TaskForm t2) {
		return t1.getTitle().equals(t2.getTitle()) && t1.getStatus().equals(t2.getStatus())
				&& t1.getComment().equals(t2.getComment()) && t1.getEmp_user().equals(t2.getEmp_user());
	}
}
