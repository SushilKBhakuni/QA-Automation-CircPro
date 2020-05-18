package com.autofusion.sql;
/**
 * @author nitin.singh
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.autofusion.constants.Constants;
@SuppressWarnings("unused")
public class ReportSqls extends SQLManager{
/*
	static {
		con = getConnection();
	}
*/	/**
	 * 
	 * @param suiteName
	 * @param suiteDescription
	 * @param executionEnviroment
	 * @param totalTestCase
	 * @param finalTestResult
	 * @param executiondate
	 * @param reportStartTime
	 * @param reportEndTime
	 * @param batchId
	 * @param prgRunmode
	 */
	public static void insertSuiteDetails(String suiteName, String  suiteDescription, String  executionEnviroment, int totalTestCase, ArrayList<String> finalTestResult,
			String executiondate,String reportStartTime , String reportEndTime, int batchId, String prgRunmode,  String runOnDevice){
		
//		con = getConnection();
		int totPass  = 0;
		int totFail  = 0;
		int totSkip  = 0;
		
		if(finalTestResult != null){
			totPass = Collections.frequency(finalTestResult, Constants.PASS);
			totFail  = Collections.frequency(finalTestResult, Constants.FAIL);
			totSkip  = Collections.frequency(finalTestResult, Constants.SKIP);
		}
		
		if(totalTestCase == 0){
 			totalTestCase = totSkip+totFail+totPass;
		} 
		 		
		String sql = "insert into tbl_suitedetail values ('"+suiteName+"','"+suiteDescription+"','"+executionEnviroment+"','"+totalTestCase+"','"+totPass+"','"+totFail+"',"
				+ "'"+totSkip+"','"+executiondate+"',"
				+ "'"+reportStartTime+"','"+reportEndTime+"','"+batchId+"','"+prgRunmode+"','"+runOnDevice+"','"+browser+"')";
		
		//System.out.println(sql);
		APP_LOG.debug("insertSuiteDetails SQL = "+sql);
		try{
			SQLManager.insertData(sql);
		}catch(Exception e){
			APP_LOG.debug("insertSuiteDetails = "+e);
		}
	}
	
	/**
	 * 
	 * @param suiteName
	 * @param tCaseID
	 * @param tCaseDesc
	 * @param executionEnviroment
	 * @param result
	 * @param executionDate
	 * @param reportStartTime
	 * @param reportEndTime
	 * @param batchId
	 * @param prgRunmode
	 */
	
	public static void insertTestCaseDetails(String suiteName, String tCaseID, String tCaseDesc, String executionEnviroment, String result, String executionDate,
			   String reportStartTime ,String reportEndTime,int batchId, String  prgRunmode,  String runOnDevice){
		
		String sql = "insert into tbl_testcasedetail values ('"+suiteName+"','"+tCaseID+"','"+tCaseDesc+"','"+executionEnviroment+"','"+result+"',"
				+ "'"+executionDate+"','"+reportStartTime+"','"+reportEndTime+"','"+batchId+"','"+prgRunmode+"')";//,'"+runOnDevice+"'
		
		APP_LOG.debug("insertTestCaseDetails SQL = "+sql);
		try{
			SQLManager.insertData(sql);
		}catch(Exception e){
			APP_LOG.debug("insertTestCaseDetails = "+e);
		}
			
	}
	
	public static void insertTestStepDetails(String suiteName, String tCaseID,String tsid, String action, String executionEnviroment, 
			String result,String filePath,String exception ,String faildetail ,String executionDate, String reportStartTime ,
			String reportEndTime,int batchId,  String prgRunmode,  String runOnDevice, String testDescription, String userInput){
		 
		String resultTmp = result.substring(0,4);
		exception =	result.replace("'","''");
		faildetail = exception;
		
		if(resultTmp.equalsIgnoreCase("PASS")) {
			faildetail = "";
			exception ="";
		}
		
		String sql = "insert into tbl_teststepdetail values ('"+suiteName+"','"+tCaseID+"','"+tsid+"','"+action+"','"+testDescription+"','"+executionEnviroment+"',"
					  + "'"+userInput+"','"+actualDataPresentOnUi+"','"+resultTmp+"','"+filePath+"','"+exception+"','"+faildetail+"','"+executionDate+"','"+reportStartTime+"',"
					  + "'"+reportEndTime+"','"+batchId+"','"+prgRunmode+"')";//,'"+runOnDevice+"'
		
		APP_LOG.debug("insertTestStepDetails SQL = "+sql);
		try{
			SQLManager.insertData(sql);
		}catch(Exception e){
			APP_LOG.debug("insertTestStepDetails = "+e);
		}
	}
	
	
	public static int selectBatchId(String env){
		String sql = "";
		if(!env.equals("")){
			sql = "select batchid from batchdetail where environment = '"+env.trim()+"' and device ='"+device+"'";
		}else{
			sql = "select batchid from batchdetail where environment = 'qa' and device ='"+device+"'";
		}
		APP_LOG.debug("selectBatchId sql : "+sql);

		Statement stmt;
		try {
			int batchId = 0;
			 	
			ResultSet rs = SQLManager.selectData(sql); 
			while(rs.next())
	    	 {
	    		 batchId= rs.getInt("batchid");
 	    	 }
			
			 APP_LOG.debug("selectBatchId batchId : "+batchId);

			//updateBatchId(env, batchId);
	    	return batchId;
		} catch (SQLException e) {
 			APP_LOG.debug("selectBatchId : "+env+" No batch id selected it will impact the report || " + e); 
		}
		
		return 0;
	}
	public static void updateBatchId(String env, int batchId){
		
		batchId = batchId+1;
		String sql = "update batchdetail set batchid = "+batchId+" where environment = '"+env.trim()+"' and device = '"+device+"'";
		
		APP_LOG.debug("updateBatchId sql : "+sql);

		
		Statement stmt;
		try {
 
			SQLManager.insertData(sql);

		} catch (SQLException e) {
 			APP_LOG.debug("updateBatchId : "+env+" update batch id fail || " + e); 
		}
	}
	
	/**
	 * 
	 * @param executiondate
	 * @param env
	 * @param batchId
	 * @return
	 */
	public static ResultSet getSuiteDetail(String executiondate, String env, int batchId, String browser){
		String browserStr = "";
		if(browser.equalsIgnoreCase("Web")) {
			browserStr = "'Firefox','chrome','ie'";
		}else if(browser.equalsIgnoreCase("mobile")){
			browserStr = "'android','ios'";
		}else {
			browserStr = "'api'";
		}
		
			String sql = "select * from tbl_suitedetail where environment = '"+env+"' and "
					+ "executiondate= '"+executiondate+"' and batchid="+batchId+"  and browser in ("+browserStr+")";
			
		APP_LOG.debug("getSuiteDetail sql : "+sql);

		Statement stmt;
		ResultSet rs = null;
		Map<String, HashMap<String,Integer>> suitResultSet = new HashMap<String, HashMap<String,Integer>>();
		try {
			//int batchId = 0;
		//	stmt = con.createStatement();
		//	rs = stmt.executeQuery(sql);	
			rs = SQLManager.selectData(sql);

			/* while(rs.next())
	    	 {
				//HashMap<String,Integer> suitResult = new HashMap<String, Integer>();
				System.out.println(rs.getString("suitname"));
				suitResult.put("pass",rs.getInt("pass"));
				suitResult.put("fail",rs.getInt("fail"));
				suitResult.put("skip",rs.getInt("skip"));
				
				suitResultSet.put(suiteName,suitResult);
	    	 }
	*/
		} catch (SQLException e) {
 			APP_LOG.debug("selectBatchId : "+env+" No batch id selected it will impact the report || " + e); 
		}
		return rs;
	}
   
	/**
	 * 
	 * @param suiteName
	 * @param env
	 * @param batchId
	 * @param testCaseId
	 * @param device
	 * @return
	 */
	public static ResultSet getTestStepData(String suiteName, String env, int batchId, String testCaseId, String device){
		
		String sql = "select * from tbl_teststepdetail where enviroment = '"+env+"' and "
				+ "suitename= '"+suiteName+"' and batchid="+batchId+"  and testcaseid = '"+testCaseId+"'";// and device = '"+device+"'
		
		APP_LOG.debug("getTestStepData sql : "+sql);

		Statement stmt;
		ResultSet rs = null;
		try {
 
			rs = SQLManager.selectData(sql);

		} catch (SQLException e) {
 			APP_LOG.debug("getTestStepData : "+env+"  || " + e); 
		}
		return rs;
	}
	
	/**
	 * 
	 * @param suiteName
	 * @param env
	 * @param batchId
	 * @param device
	 * @return
	 */
	public static ResultSet getTestCaseData(String suiteName, String env, int batchId, String device){
		
		String sql = "select * from tbl_testcasedetail where environment = '"+env+"' and "
				+ "suitename= '"+suiteName+"' and batchid="+batchId+" ";// and device = '"+device+"'
		
		//System.out.println(sql);
		APP_LOG.debug("getTestCaseData sqlInsert : "+sql);

		Statement stmt;
		ResultSet rs = null;
		try {
		 
			rs = SQLManager.selectData(sql);

		} catch (SQLException e) {
 			APP_LOG.debug("getTestCaseData : "+env+"  || " + e); 
		}
		return rs;
	}
	
	/**
	 * 
	 * @param env
	 * @return
	 */
	public static ResultSet getExecutionDateForCombo(String env, String browser){
		String browserStr = "";
		if(browser.equalsIgnoreCase("Web")) {
			browserStr = "'Firefox','chrome','ie'";
		}else if(browser.equalsIgnoreCase("mobile")){
			browserStr = "'android','ios'";
		}else {
			browserStr = "'api'";
		}
		
		String sql = "SELECT DISTINCT executiondate, batchid FROM `tbl_suitedetail` WHERE environment ='"+env+"' and browser in ("+browserStr+") "
				+ " ORDER BY executiondate DESC "; 
		
		//System.out.println(sql);
		APP_LOG.debug("getExecutionDateForCombo "+sql);
		Statement stmt;
		ResultSet rs = null;
		
		try {
	 
			rs = SQLManager.selectData(sql);
		 
	 
		} catch (SQLException e) {
 			APP_LOG.debug("selectBatchId : "+env+" No batch id selected it will impact the report || " + e); 
		}
		
 		return rs;
		
	}
	
	
	public static void insertAutomationSummary(String category, String executionEnviroment, 
			String executionDate,int batchId){
		
		
		String sql = "SELECT COUNT(suitename) cntSuiteName, SUM(totaltestcase) as ttc, SUM(pass) as p, "
					+ " SUM(fail) as f, SUM(skip) as s FROM `tbl_suitedetail` WHERE batchid = "+batchId+""
					+ " and environment = '"+executionEnviroment+"' and runmode='Y'";
		
		String sql1 = "SELECT COUNT(testcaseid) as cntTestCaseId FROM tbl_testcasedetail WHERE batchid = "+batchId+""
					+ " and environment = '"+executionEnviroment+"' and runmode='Y'";

		String sql2 = "SELECT COUNT(teststepid) as cntTestStepId FROM tbl_teststepdetail WHERE batchid = "+batchId+""
					+ " and enviroment = '"+executionEnviroment+"' and runmode='Y'";

		
		APP_LOG.debug("insertAutomationSummary "+sql);
		APP_LOG.debug("insertAutomationSummary "+sql1);
		APP_LOG.debug("insertAutomationSummary "+sql2);

		
		Statement stmt;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {
			rs = SQLManager.selectData(sql);
			rs1 = SQLManager.selectData(sql1);
			rs2 = SQLManager.selectData(sql2);
		} catch (SQLException e) {
 			APP_LOG.debug("insertAutomationSummary : "+executionEnviroment+"  || " + e); 
		}
			
		try {
	
			int totSuite  = 0;
			int totTestCase  = 0;
			int totPass  = 0;
			int totFail  = 0;
			int totSkip  = 0;
			while(rs.next())
			{
					totSuite = rs.getInt("cntSuiteName");
					totTestCase = rs.getInt("ttc");
					totPass = rs.getInt("p");
					totFail = rs.getInt("f");
					totSkip = rs.getInt("s");
					
				String sqlInsert = 	"insert into tbl_automationsummary (suitecategory,releaseversion, executiondate, "
						+ "environment, totalsuite,totaltestcase,totalsteps,pass,fail,skip,browser,device, batchid,reportpath) "
						+ "values ('"+category+"','"+releaseVersion+"',"
							+ "'"+executionDate+"','"+executionEnviroment+"',"+totSuite+",0,0,"+totPass+","+totFail+","+totSkip+", "
							+ "'"+browser+"','"+device+"','"+batchId+"','"+extentReportFile+"')";
				
				APP_LOG.debug("insertAutomationSummary sqlInsert : "+sqlInsert);
				SQLManager.insertData(sqlInsert);
			}
			
			while(rs1.next())
			{
				totTestCase = rs1.getInt("cntTestCaseId");;
				String sqlInsert = 	" update tbl_automationsummary set totaltestcase="+totTestCase+" where batchid = '"+batchId+"'";
				APP_LOG.debug("insertAutomationSummary sqlInsert 1 : "+sqlInsert);
				SQLManager.insertData(sqlInsert);

			}
			
			while(rs2.next())
			{
				totTestCase = rs2.getInt("cntTestStepId");;
				String sqlInsert = 	" update tbl_automationsummary set totalsteps="+totTestCase+" where batchid = '"+batchId+"'";
				APP_LOG.debug("insertAutomationSummary sqlInsert 2: "+sqlInsert);

				SQLManager.insertData(sqlInsert);

			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	 public ResultSet readFinalReport(String device){
			
	     /* String query =" SELECT SUM(totaltestcase), SUM(pass), SUM(fail), SUM(skip), executiondate, starttime, endtime, "
		    			+ "	browser, batchid FROM `tbl_suitedetail` WHERE device='"+device+"' GROUP BY batchid, browser ORDER BY batchid,executiondate "
		    			+ "LIMIT 0,5";
*/
		   String query ="SELECT * FROM  tbl_automationsummary where device = '"+device+"' order by batchid desc limit 5";
		    
			ResultSet rs = null;
			try {
				rs = SQLManager.selectData(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 	rs;
	  }
}
