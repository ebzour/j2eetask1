package com.eyas.common.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.eyas.common.form.EmployeeForm;
import com.eyas.common.form.TaskForm;

public class TaskHelper {
	private static Connection dbConnection=DBConnector.getInstance().getConnection();

	public static void createTask(String title, String status, String comment, String emp_user) throws SQLException {
		String query = "INSERT INTO task (TITLE,STATUS, COMMENT,EMP_USER) VALUES (?, ?, ?, (select id from employee as e2 where e2.username = ?))";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		stmt.setString(1, title);
		stmt.setString(2, status);
		stmt.setString(3, comment);
		stmt.setString(4, emp_user);
		stmt.executeUpdate();
	}

	public static void updateTask(int id, String title, String status, String comment, String emp_user)
			throws SQLException {
		String query = "UPDATE task SET TITLE = ?, STATUS = ?, COMMENT = ?, EMP_USER = (select id from employee as e2 where e2.username = ?) WHERE id = ?";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		stmt.setString(1, title);
		stmt.setString(2, status);
		stmt.setString(3, comment);
		stmt.setString(4, emp_user);
		stmt.setInt(5, id);
		stmt.executeUpdate();

	}

	public static ArrayList<TaskForm> getPendingTasks() throws SQLException {
		return getTasks(" and t1.STATUS = 'Pending Approval'");

	}

	public static ArrayList<TaskForm> getAllTasks() throws SQLException {
		return getTasks("");

	}

	public static ArrayList<TaskForm> getTasksFor(String username) throws SQLException {
		return getTasks(" and e1.USERNAME = " + "'" + username + "'");

	}

	public static ArrayList<TaskForm> getLeadAndDevelopersTasks(String lead) throws SQLException {
		ArrayList<TaskForm> DevTasks = new ArrayList<TaskForm>();
		for (EmployeeForm e :EmployeeHelper.getLeadDevelopers(lead)) {
			DevTasks.addAll(getTasksFor(e.getUsername()));
		}
		ArrayList<TaskForm> LeadTasks = getTasksFor(lead);
		LeadTasks.addAll(DevTasks);
		return LeadTasks;
	}

	public static ArrayList<TaskForm> getTasks(String query) throws SQLException {
		String query1="SELECT t1.ID,t1.TITLE,t1.STATUS,t1.COMMENT,e1.USERNAME as 'EMP_USER' "
				+ "FROM task t1 , employee e1"
				+ " where t1.EMP_USER=e1.ID ";
		TaskForm task = null;
		ArrayList<TaskForm> tasks = new ArrayList<TaskForm>();
		PreparedStatement stmt = dbConnection.prepareStatement(query1+query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			task = new TaskForm();
			int id = res.getInt("ID");
			String title = res.getString("TITLE");
			String status = res.getString("STATUS");
			String comment = res.getString("COMMENT");
			String emp_user = res.getString("EMP_USER");
			task.setId(id);
			task.setTitle(title);
			task.setStatus(status);
			task.setComment(comment);
			task.setEmp_user(emp_user);
			tasks.add(task);
		}
		return tasks;
	}
	public static ArrayList<String> getAllStatus() throws SQLException{
		ArrayList<String> status = new ArrayList<String>();
		String query="select STATE from status";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			status.add(res.getString("STATE"));
		}
		return status;
	}
}
