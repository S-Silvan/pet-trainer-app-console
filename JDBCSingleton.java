package com.pettrainerappointment.online;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCSingleton {
	private static JDBCSingleton jdbc;
	private static Connection connection;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettrainer","root","");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static JDBCSingleton getInstance() {
		if(jdbc==null)
			jdbc=new JDBCSingleton();
		return jdbc;
	}
	public Connection getConnection() {
		return connection;
	}
}
