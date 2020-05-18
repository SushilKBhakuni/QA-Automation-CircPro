package com.autofusion.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xlsx_Reader;

/**
 * Servlet implementation class CheckSuitFileStatus
 */
@SuppressWarnings("unused")
@WebServlet("/CheckSuitFileStatus")
public class CheckSuitFileStatus extends HttpServlet {
	public static String testCaseBasePath;
	/**
	 * 
	 */
	private static final long serialVersionUID = -230673651464920086L;

	public CheckSuitFileStatus() {
        super();
        // TODO Auto-generated constructor stub
    }
	public static String getTestCaseBasePath() {
		return testCaseBasePath;
	}

	public static void setTestCaseBasePath(String testCaseBasePath) {
		CheckSuitFileStatus.testCaseBasePath = testCaseBasePath;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		testCaseBasePath = request.getParameter("hTestCaseBasePath");
		
		FileInputStream fileConfig = new FileInputStream(testCaseBasePath+"/config.properties");
		Properties configProps = new Properties();
		configProps.load(fileConfig);
		long timeout = 0L;
		String timeoutVal = (String) configProps.get("scriptTimeOut");
		if(timeoutVal == null || timeoutVal.trim().equals("")){
			timeout = Constants.HANG_TIMEOUT;
		} else {
			timeout = Long.parseLong(timeoutVal) * 60L;	
		}
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/xml");
		
		Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+"/"+Constants.SUIT_FILE_NAME);
	  	
	  	for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++){
	  		String firefox_logPath = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_FF_LOG_PATH, suiteCount);
	  		String chrome_logPath = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_CHROME_LOG_PATH, suiteCount);
	  		String ie_logPath = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_IE_LOG_PATH, suiteCount);
	  		
	  		String runStatus = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "ExecutionStatus", suiteCount);
	  		Boolean hanged = false;
	  		if(runStatus.equals("Running")){
	  			
	  			File f1 = new File(firefox_logPath + "\\application.log");
	  			if(f1.exists()){
	  				long fileModTime = f1.lastModified();
					Date modTime = new Date(fileModTime);
					Date currTime = new Date();
					long lastModSince = (currTime.getTime() - modTime.getTime())/1000;
					if(lastModSince > timeout){
						hanged = true;
					}
	  			}
	  			
	  			File f2 = new File(chrome_logPath + "\\application.log");
	  			if(f2.exists()){
	  				long fileModTime = f2.lastModified();
					Date modTime = new Date(fileModTime);
					Date currTime = new Date();
					long lastModSince = (currTime.getTime() - modTime.getTime())/1000;
					if(lastModSince > timeout){
						hanged = true;
					}
	  			}
	  			
	  			File f3 = new File(ie_logPath + "\\application.log");
	  			if(f3.exists()){
	  				long fileModTime = f3.lastModified();
					Date modTime = new Date(fileModTime);
					Date currTime = new Date();
					long lastModSince = (currTime.getTime() - modTime.getTime())/1000;
					if(lastModSince > timeout){
						hanged = true;
					}
	  			}
	  			
	  			if(hanged){
	  				suiteXls.openXls();
					suiteXls.setCellData(Constants.TEST_SUITE_SHEET, "ExecutionStatus", suiteCount, Constants.EXE_HANG);
					out.println("<suiteName>hng_" + suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "TSID", suiteCount) + "</suiteName>");
					suiteXls.writeXls();
					sendMail();
	  			}
	  			
	  		} else if(runStatus.equals(Constants.EXE_HANG)){
	  			out.println("<suiteName>hng_" + suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "TSID", suiteCount) + "</suiteName>");
	  		} else if(runStatus.equals(Constants.EXE_INTERUPTED)){
	  			out.println("<suiteName>int_" + suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "TSID", suiteCount) + "</suiteName>");
	  		}
	  	}
	}
	
	public void sendMail() throws IOException{
		String testCaseBasePath =  getTestCaseBasePath();
//		String userName=CommonDriverScript.CONFIG.getProperty("username");
//		String password=CommonDriverScript.CONFIG.getProperty("password");
//		String sendTo=CommonDriverScript.CONFIG.getProperty("to");
		
		InputStreamReader userFileConfig = new InputStreamReader(new FileInputStream(testCaseBasePath+"/config.properties"), "UTF-8");
		Properties uconfig = new Properties();
        uconfig.load(userFileConfig);
		
        String userName = uconfig.getProperty("username");
        String password = uconfig.getProperty("password");
        String to = uconfig.getProperty("to");
		
		String[] toAdd  = {to};
		String[] ccAdd  = {}; 
		String[] bccAdd = {};
		String subject = "Test Suite Execution has failed";
		String message = "The Test Suite Execution started by you has hanged.\nPlease go to your dashboard and manually kill the script.";
		
		//Boolean sendMail = SendMail.sendMail(userName,password, toAdd, ccAdd, bccAdd, subject, message);
		//System.out.println("Mail sending has "+(sendMail ? "passed" : "failed"));
	}
}
