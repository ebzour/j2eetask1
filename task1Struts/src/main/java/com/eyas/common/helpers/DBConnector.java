package com.eyas.common.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

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

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
