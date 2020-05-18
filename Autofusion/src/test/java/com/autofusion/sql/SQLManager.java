
package com.autofusion.sql;
/**
 * @author nitin.singh
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.autofusion.BaseClass;

 
public class SQLManager extends BaseClass {

	static Connection con;
	static String url;

	public static Connection getConnection() {
		
		try {
			String url = "jdbc:mysql://localhost:3306/" + "qaautomation";
		 			Class.forName("com.mysql.jdbc.Driver");
			try {
				//con = DriverManager.getConnection(url, CommonUtility.USER_CONFIG.getProperty("dbUserName"), CommonUtility.USER_CONFIG.getProperty("dbPassword"));
				con = DriverManager.getConnection(url, "root", "root");
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

		}catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		return con;
	}

	
	public static void insertData(String sql) throws SQLException{
		con = getConnection();	
		Statement stmt = con.createStatement();
	    	
			stmt.executeUpdate(sql);	
	}

	public static ResultSet selectData(String sql) throws SQLException{
			con = getConnection();	
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
	    return rs;
	}

	
}