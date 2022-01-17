package com.eyas.common.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.eyas.common.form.EmployeeForm;

public class EmployeeHelper {
	
	private static Connection dbConnection=DBConnector.getInstance().getConnection();
	
	public static EmployeeForm verifyCredentials(String username, String passowrd) throws SQLException {
		EmployeeForm emp = null;
		String query="SELECT e1.ID,e1.NAME,e1.TYPE,e1.USERNAME,e1.PASSWORD, e2.USERNAME as'LEAD'"
				+ " from employee e1 , employee e2 "
				+ " where e2.id=e1.lead and e1.USERNAME=? and e1.PASSWORD=?";
		PreparedStatement stmt =  dbConnection.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, passowrd);
		ResultSet res = stmt.executeQuery();
		if (res.next()) {
			emp = new EmployeeForm();
			int id = res.getInt("ID");
			String name = res.getString("NAME");
			String type = res.getString("TYPE");
			String lead = res.getString("LEAD");
			emp.setId(id);
			emp.setName(name);
			emp.setType(type);
			emp.setUsername(username);
			emp.setPassword(passowrd);
			emp.setLead(lead);
		}
		return emp;
	}
	public static ArrayList<EmployeeForm> getManagerAndAllEmployees() throws SQLException {
		return getEmployees("");

	}
	public static ArrayList<EmployeeForm> getAllEmployees() throws SQLException {
		return getEmployees(" and e1.TYPE != 'Manager'");

	}

	public static ArrayList<EmployeeForm> getManagerAndLeads() throws SQLException {
		return getEmployees(" and e1.TYPE != 'Developer'");

	}

	public static ArrayList<EmployeeForm> getLeadAndDevelopers(String lead) throws SQLException {
		ArrayList<EmployeeForm> Devs = getLeadDevelopers(lead);
		ArrayList<EmployeeForm> Lead = getDeveloper(lead);
		Lead.addAll(Devs);
		return Lead;

	}

	public static ArrayList<EmployeeForm> getLeadDevelopers(String username) throws SQLException {
		return getEmployees("and e2.USERNAME = " + "'" + username + "'");

	}

	public static ArrayList<EmployeeForm> getDeveloper(String username) throws SQLException {
		return getEmployees("and e1.USERNAME = " + "'" + username + "'");

	}

	public static ArrayList<EmployeeForm> getEmployees(String query) throws SQLException {
		String query1="SELECT e1.ID,e1.NAME,e1.TYPE,e1.USERNAME,e1.PASSWORD, e2.USERNAME as'LEAD'"
				+ " from employee e1 , employee e2 "
				+ " where e2.id=e1.lead ";
		EmployeeForm emp = null;
		ArrayList<EmployeeForm> employees = new ArrayList<EmployeeForm>();
		PreparedStatement stmt = dbConnection.prepareStatement(query1+query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			emp = new EmployeeForm();
			int id = res.getInt("ID");
			String name = res.getString("NAME");
			String type = res.getString("TYPE");
			String username = res.getString("USERNAME");
			String passowrd = res.getString("PASSWORD");
			String lead = res.getString("LEAD");
			emp.setId(id);
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
	
		String query = "INSERT INTO employee (NAME,TYPE, USERNAME,PASSWORD,LEAD) VALUES (?, ?, ?, ?, (select id from employee as e2 where e2.username = ?))";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.setString(3, username);
		stmt.setString(4, passowrd);
		stmt.setString(5, lead);
		stmt.executeUpdate();

	}


	public static void updateEmployee(int id, String name, String type, String username,String password ,String lead)
			throws SQLException {
		
		String query = "UPDATE employee SET NAME = ?, TYPE = ?, USERNAME = ?, PASSWORD = ? , LEAD = (select id from employee as e2 where e2.username = ? ) WHERE id = ?";
		PreparedStatement stmt =dbConnection.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.setString(3, username);
		stmt.setString(4, password);
		stmt.setString(5, lead);
		stmt.setInt(6, id);
		stmt.executeUpdate();

	}
	public static ArrayList<String> getAllTypes() throws SQLException{
		ArrayList<String> types = new ArrayList<String>();
		String query="select ROLE from type";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			types.add(res.getString("ROLE"));
		}
		return types;
	}
}
