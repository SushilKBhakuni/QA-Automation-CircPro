package com.autofusion.keywords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;

public class SQLDatabaseKeywords extends Keyword{

	public SQLDatabaseKeywords(Logger log) {
 		APP_LOG = log;
    }
	
	public Connection getSqlServerConnection(Map<String, String> argsList){
		
		String userName = argsList.get("DB_USER");
		String password = argsList.get("DB_PASS");
		String connectionString = argsList.get("DB_URL");
		
 		try{
 			//STEP 2: Register JDBC driver
 			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

 			//STEP 3: Open a connection
 			System.out.println("Connecting to a selected database...");
 			DriverManager.setLoginTimeout(20);
 			
 			APP_LOG.debug("Connecting  database :: "+connectionString+" || "+userName+" || "+password);
 			conn=DriverManager.getConnection(connectionString,userName,password);
             setConn(conn);
          
             
             if(conn==null){
            	 APP_LOG.debug("connection not created ");
             	System.out.println("connection not created ");
             }
 		}catch(Exception e){
 			APP_LOG.debug("connection not created "+e);
 			System.out.println("connection not created "+e);
 		}
 		return conn;
 	}

	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	} 
	
public void getOracleJdbcConnection(Map<String, String> argsList){
		
		String userName = argsList.get("DB_USER");
		String password = argsList.get("DB_PASS");
		String connectionString = argsList.get("DB_URL");
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+connectionString,userName,password);
			connection.close();
			
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
		}
		
	}

   public String sqlServerGetAndSaveDataFromDb(Map<String, String> argsList){
	   
	   String sqlQuery=argsList.get("data");
	   String param=argsList.get("param");
	   
	   conn = getSqlServerConnection(argsList);
	   APP_LOG.debug("Func sqlServerGetAndSaveDataFromDb:sqlQuery = "+sqlQuery);
	   try{
		   PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		   String paramValue = "";
		   ResultSet rs = stmt.executeQuery();
		   while(rs.next()){
				paramValue  = rs.getString(param);
		   }
		   
		   APP_LOG.debug("Func sqlServerGetAndSaveDataFromDb: param="+param+" || paramValue="+paramValue);
		   //TODO CommonKeywords.setValueInMemory(param, paramValue);
		   rs.close();
		   
		   return Constants.PASS;   
	   }catch(Exception e){
		   APP_LOG.debug("Func sqlServerGetAndSaveDataFromDb:Exception="+e);
		   return Constants.FAIL;
	   }
   }
   public String sqlVerifyDataFromDb(Map<String, String> argsList){
		  
		 String data = argsList.get("data");
		 
		    APP_LOG.debug("getAndSaveAdcDataFromDB : "+data);
		  
		    Connection conn = getSqlServerConnection(argsList);
		    //Connection conn = SQLManager.getOracleServerConnection(argsList);
			String sqlParamVal = "";
		
			try{
				String sql = argsList.get("data1");;
				APP_LOG.debug("getAndSaveAdcDataFromDB : "+sql);
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()){
					sqlParamVal = rs.getString(data);
					//APP_LOGS.debug("getAndSaveAdcDataFromDB : sqlParamVal=> "+sqlParamVal+" || selQryArr="+adcCodeVariableArr[i]+" | "+selParam);
				}
				
			}catch(Exception e){
				APP_LOG.debug("sql1 "+e);
				return "0";
			}  
			return  sqlParamVal;
	  }
   

   public static Connection getOracleServerConnection(Map<String, String> argsList) {
	   Connection con = null;
		try {
			String url = "jdbc:oracle:thin:@172.18.14.132:1521:orcl";
		 			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				//con = DriverManager.getConnection(url, CommonUtility.USER_CONFIG.getProperty("dbUserName"), CommonUtility.USER_CONFIG.getProperty("dbPassword"));
				con = DriverManager.getConnection(url, "MTSAUL", "MTSAUL");
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

		}catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		return con;
	}

}
