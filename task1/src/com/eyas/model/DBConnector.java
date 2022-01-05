package com.eyas.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
	private Connection connection;
	public static DBConnector instance = null;

	private DBConnector() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			String dbURL = "jdbc:mysql://localhost:3306/j2eetask1";
			String userName = "root";
			String password = "";
			connection = DriverManager.getConnection(dbURL, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DBConnector getInstance() {
		if (instance == null)
			instance = new DBConnector();
		return instance;
	}

	public static EmployeeBean verifyCredentials(String username, String passowrd) throws SQLException {
		EmployeeBean emp = null;
		String query = "SELECT * FROM employee WHERE USERNAME=? and PASSWORD=?";
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, passowrd);
		ResultSet res = stmt.executeQuery();
		if (res.next()) {
			emp = new EmployeeBean();
			int id = res.getInt("ID");
			String name = res.getString("NAME");
			String type = res.getString("TYPE");
			String lead = res.getString("LEAD");
			emp.setID(id);
			emp.setName(name);
			emp.setType(type);
			emp.setUsername(username);
			emp.setPassword(passowrd);
			emp.setLead(lead);
		}
		return emp;
	}
	public static ArrayList<EmployeeBean> getManagerAndAllEmployees() throws SQLException {
		return getEmployees("SELECT * FROM employee");

	}
	public static ArrayList<EmployeeBean> getAllEmployees() throws SQLException {
		return getEmployees("SELECT * FROM employee WHERE TYPE != 'Manager'");

	}

	public static ArrayList<EmployeeBean> getManagerAndLeads() throws SQLException {
		return getEmployees("SELECT * FROM employee WHERE TYPE != 'Developer'");

	}

	public static ArrayList<EmployeeBean> getLeadAndDevelopers(String lead) throws SQLException {
		ArrayList<EmployeeBean> Devs = getLeadDevelopers(lead);
		ArrayList<EmployeeBean> Lead = getDeveloper(lead);
		Lead.addAll(Devs);
		return Lead;

	}

	public static ArrayList<EmployeeBean> getLeadDevelopers(String username) throws SQLException {
		return getEmployees("SELECT * FROM employee WHERE LEAD = " + "'" + username + "'");

	}

	public static ArrayList<EmployeeBean> getDeveloper(String username) throws SQLException {
		return getEmployees("SELECT * FROM employee WHERE USERNAME = " + "'" + username + "'");

	}

	public static ArrayList<EmployeeBean> getEmployees(String query) throws SQLException {
		EmployeeBean emp = null;
		ArrayList<EmployeeBean> employees = new ArrayList<EmployeeBean>();
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			emp = new EmployeeBean();
			int id = res.getInt("ID");
			String name = res.getString("NAME");
			String type = res.getString("TYPE");
			String username = res.getString("USERNAME");
			String passowrd = res.getString("PASSWORD");
			String lead = res.getString("LEAD");
			emp.setID(id);
			emp.setName(name);
			emp.setType(type);
			emp.setUsername(username);
			emp.setPassword(passowrd);
			emp.setLead(lead);
			employees.add(emp);
		}
		return employees;
	}

	public static void createEmployee(String name, String type, String username, String passowrd, String lead)
			throws SQLException {
		String query = "INSERT INTO employee (NAME,TYPE, USERNAME,PASSWORD,LEAD) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.setString(3, username);
		stmt.setString(4, passowrd);
		stmt.setString(5, lead);
		stmt.executeUpdate();

	}


	public static void updateEmployee(int id, String name, String type, String username,String password ,String lead)
			throws SQLException {
		String query = "UPDATE employee SET NAME = ?, TYPE = ?, USERNAME = ?, PASSWORD = ? , LEAD = ? WHERE id = ?";
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.setString(3, username);
		stmt.setString(4, password);
		stmt.setString(5, lead);
		stmt.setInt(6, id);
		stmt.executeUpdate();

	}

	public static void createTask(String title, String status, String comment, String emp_user) throws SQLException {
		String query = "INSERT INTO task (TITLE,STATUS, COMMENT,EMP_USER) VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		stmt.setString(1, title);
		stmt.setString(2, status);
		stmt.setString(3, comment);
		stmt.setString(4, emp_user);
		stmt.executeUpdate();
	}

	public static void updateTask(int id, String title, String status, String comment, String emp_user)
			throws SQLException {
		String query = "UPDATE task SET TITLE = ?, STATUS = ?, COMMENT = ?, EMP_USER = ? WHERE id = ?";
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		stmt.setString(1, title);
		stmt.setString(2, status);
		stmt.setString(3, comment);
		stmt.setString(4, emp_user);
		stmt.setInt(5, id);
		stmt.executeUpdate();

	}

	public static ArrayList<TaskBean> getPendingTasks() throws SQLException {
		return getTasks("SELECT * FROM task WHERE STATUS = 'Pending Approval'");

	}

	public static ArrayList<TaskBean> getAllTasks() throws SQLException {
		return getTasks("SELECT * FROM task");

	}

	public static ArrayList<TaskBean> getTasksFor(String username) throws SQLException {
		return getTasks("SELECT * FROM task WHERE EMP_USER = " + "'" + username + "'");

	}

	public static ArrayList<TaskBean> getLeadAndDevelopersTasks(String lead) throws SQLException {
		ArrayList<TaskBean> DevTasks = new ArrayList<TaskBean>();
		for (EmployeeBean e : getLeadDevelopers(lead)) {
			DevTasks.addAll(getTasksFor(e.getUsername()));
		}
		ArrayList<TaskBean> LeadTasks = getTasksFor(lead);
		LeadTasks.addAll(DevTasks);
		return LeadTasks;
	}

	public static ArrayList<TaskBean> getTasks(String query) throws SQLException {
		TaskBean task = null;
		ArrayList<TaskBean> tasks = new ArrayList<TaskBean>();
		PreparedStatement stmt = getInstance().connection.prepareStatement(query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			task = new TaskBean();
			int id = res.getInt("ID");
			String title = res.getString("TITLE");
			String status = res.getString("STATUS");
			String comment = res.getString("COMMENT");
			String emp_user = res.getString("EMP_USER");
			task.setID(id);
			task.setTitle(title);
			task.setStatus(status);
			task.setComment(comment);
			task.setEmp_user(emp_user);
			tasks.add(task);
		}
		return tasks;
	}

}
