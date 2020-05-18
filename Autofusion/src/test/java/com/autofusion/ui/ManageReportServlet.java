package com.autofusion.ui;
/**
 * @author nitin.singh
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autofusion.constants.Constants;
import com.autofusion.report.TestCaseReportBean;
import com.autofusion.sql.ReportSqls;
import com.autofusion.util.ReadXlsForUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 * Servlet implementation class ManageReportServlet
 */
@SuppressWarnings("unused")
@WebServlet("/ManageReportServlet")
public class ManageReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public static int countt=0;  
     public static String result;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		
		StringBuffer resultStr = new StringBuffer();
		
		String method = request.getParameter("method");
		
		if(method.equalsIgnoreCase("getTestStepData")) {
			String resultStrCase = getTestStepData(request.getParameter("suiteName"), request.getParameter("env"),  request.getParameter("batchId"), request.getParameter("testCaseId"), request.getParameter("device"));
			//System.out.println(jsonPrettyPrint(resultStrCase));
			out.println(resultStrCase);
			return;
		}else if(method.equalsIgnoreCase("getTestCaseData")) {
		
			String resultStrCase = getTestCaseData(request.getParameter("suiteName"), request.getParameter("env"), 
					request.getParameter("batchId"), request.getParameter("device"));
			
			//System.out.println(jsonPrettyPrint(resultStrCase));
			out.println(resultStrCase);
			return;
		
		}else if(method.equalsIgnoreCase("getSuitCombo")) {
			//resultStr = getExecutionDateForCombo(request.getParameter("env"), request.getParameter("device"));
		}else if(method.equalsIgnoreCase("getTestResult")) {
			//resultStr = getTestResult(request.getParameter("executionDate"), request.getParameter("env"), request.getParameter("device"));
		}else if(method.equalsIgnoreCase("getEnvCombo")) {
			//resultStr = getEnvCombo(request.getParameter("path"),request.getParameter("device"));
		}
		out.println(resultStr);
		out.close();
	}
	
	/*public StringBuffer getTestStepData(String suiteName, String env, String batchId, String testCaseId){
		int batchIdInt = Integer.valueOf(batchId);
		ResultSet rs = ReportSqls.getTestStepData(suiteName, env, batchIdInt, testCaseId);
		StringBuffer strBuffer = new StringBuffer();
		
		try{
			if(!rs.equals(null)){
			  while(rs.next()){
				  strBuffer.append("<tr>");
					  strBuffer.append("<td>"+rs.getString("teststepid")+"</td>");
					  strBuffer.append("<td>"+rs.getString("teststepdescription")+"</td>");
					  String result = rs.getString("result");
					  String style="";
					  if(result.equalsIgnoreCase(Constants.PASS)){
						  style="style='background: #66CC66'";
					  }else if(result.equalsIgnoreCase(Constants.FAIL)){
						  result = Constants.FAIL;
						  style="style='background: #FF0000'";

					  }else{
 						  style="style='background: #00A6FF'";

					  }					  
					  strBuffer.append("<td "+style+" align='center'><font color='#ffffff'>"+result+"</font></td>");
					  if(rs.getString("screenshotpath").equals("")){
						  strBuffer.append("<td  align='center'> - </td>");
					  }else{
						  strBuffer.append("<td><a href='"+rs.getString("screenshotpath")+"' target='blank'>"+rs.getString("screenshotpath")+"</a></td>");
					  }
					  
					  if(rs.getString("exception").equals("")){
						  strBuffer.append("<td>"+rs.getString("exception")+"</td>");
					  }else{
						  strBuffer.append("<td  align='center'> - </td>");
					  }
					  
					  strBuffer.append("<td>"+rs.getString("starttime")+"</td>");
					  strBuffer.append("<td>"+rs.getString("endtime")+"</td>");
				 
				 strBuffer.append("</tr>");
			  }
			}
		}catch(Exception e){
			
		}
		
		return strBuffer;
	}
*/		
	public String getTestCaseData(String suiteName, String env, String batchId, String device){
			
		int batchIdInt = Integer.valueOf(batchId);
		ResultSet rs = ReportSqls.getTestCaseData(suiteName, env, batchIdInt, device);
		List<List<String>> lstTestCaseReportBean = new ArrayList<List<String>>();
		TestCaseReportBean objTestBean = new TestCaseReportBean();
		try{
			if(!rs.equals(null)){
			  while(rs.next()){
					
				  List<String> lstTestCaseReport = new ArrayList<String>();
				  String result = rs.getString("result");
				  String style="";
				  
				  if(!result.equalsIgnoreCase(Constants.SKIP)){
					  lstTestCaseReport.add("<a href='#'"
						  		+ "	onclick=\"getTestStepData('"+rs.getString("suitename")+"','"+rs.getString("environment")+"',"
				  				+ "'"+rs.getString("batchid")+"','"+rs.getString("testcaseid")+"','"+device+"')\">"+rs.getString("testcaseid")+"</a>");
				  }else{
					  lstTestCaseReport.add(rs.getString("testcaseid"));
				  }
				  
				  lstTestCaseReport.add(rs.getString("testcasedescription"));
				  lstTestCaseReport.add(rs.getString("result"));
				  lstTestCaseReport.add(rs.getString("starttime"));
				  lstTestCaseReport.add(rs.getString("endtime"));
				  
				  lstTestCaseReportBean.add(lstTestCaseReport);
			  }
			}
		}catch(Exception e){
			
		}
		
		objTestBean.setData(lstTestCaseReportBean);
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(objTestBean);
		
		return jsonCartList;
		
	}	

	
	public String getTestStepData(String suiteName, String env, String batchId, String testCaseId, String device){
		
		int batchIdInt = Integer.valueOf(batchId);
		ResultSet rs = ReportSqls.getTestStepData(suiteName, env, batchIdInt, testCaseId, device);

		List<List<String>> lstTestCaseReportBean = new ArrayList<List<String>>();
		TestCaseReportBean objTestBean = new TestCaseReportBean();
		try{
			if(!rs.equals(null)){
			  while(rs.next()){
					
				  List<String> lstTestCaseReport = new ArrayList<String>();
				  String result = rs.getString("result");
				  String style="";
				  
				  lstTestCaseReport.add(rs.getString("teststepid"));
				  lstTestCaseReport.add(rs.getString("teststepdescription"));
				  lstTestCaseReport.add(rs.getString("actionperformed"));
				  lstTestCaseReport.add(rs.getString("expecteddata"));
				  lstTestCaseReport.add(rs.getString("actualdata"));
				  if(rs.getString("failuredetail").length() > 100)
					  lstTestCaseReport.add(rs.getString("failuredetail").substring(0,100));
				  else{
					  lstTestCaseReport.add(rs.getString("failuredetail"));
				  }
				  lstTestCaseReport.add(rs.getString("result"));
				  
				  if(rs.getString("screenshotpath").equals("")){
					  lstTestCaseReport.add(" - ");
				  }else{
					  lstTestCaseReport.add("<a href='"+rs.getString("screenshotpath")+"' target='blank'><img src='"+rs.getString("screenshotpath")+"' width='100%' height='30'/></a>");
				  }
				  
				 /* if(rs.getString("exception").equals("")){
					  lstTestCaseReport.add(rs.getString("exception"));
				  }else{
					  lstTestCaseReport.add(" - ");
				  }*/
				  
				  lstTestCaseReport.add(rs.getString("starttime"));
				  lstTestCaseReport.add(rs.getString("endtime"));
				  
				  lstTestCaseReportBean.add(lstTestCaseReport);
			  }
			}
		}catch(Exception e){
			System.out.println("e");
		}
 		  
		
		objTestBean.setData(lstTestCaseReportBean);
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(objTestBean);
		
		return jsonCartList;
		
	}	

	
	
/*	public StringBuffer getTestCaseData1(String suiteName, String env, String batchId){
		int batchIdInt = Integer.valueOf(batchId);
		ResultSet rs = ReportSqls.getTestCaseData(suiteName, env, batchIdInt);
		StringBuffer strBuffer = new StringBuffer();
		
		try{
			if(!rs.equals(null)){
			  while(rs.next()){
				  strBuffer.append("<tr>");
				  				  
				  String result = rs.getString("result");
				  String style="";
				  if(result.equalsIgnoreCase(Constants.PASS)){
					  style="style='background: #66CC66'";
				  }else if(result.equalsIgnoreCase(Constants.FAIL)){
					  result = Constants.FAIL;
					  style="style='background: #FF0000'";
				  }else{
					  style="style='background: #00A6FF'";
				  }
				  
				  if(!result.equalsIgnoreCase(Constants.SKIP)){
					  strBuffer.append("<td><a href='#'"
					  		+ "	onclick=\"getTestStepData('"+rs.getString("suitename")+"','"+rs.getString("environment")+"',"
					  				+ "'"+rs.getString("batchid")+"','"+rs.getString("testcaseid")+"')\">"+rs.getString("testcaseid")+"</a></td>");
				  }else{
					  strBuffer.append("<td>"+rs.getString("testcaseid")+"</td>");
				  }
					  
					  strBuffer.append("<td>"+rs.getString("testcasedescription")+"</td>");
					  
					  
					  strBuffer.append("<td "+style+" align='center'><font color='#ffffff'>"+result+"</font></td>");
					  
					  strBuffer.append("<td>"+rs.getString("starttime")+"</td>");
					  strBuffer.append("<td>"+rs.getString("endtime")+"</td>");
				 
				 strBuffer.append("</tr>");
			  }
			}
		}catch(Exception e){
			
		}
		
		return strBuffer;
	}	
*/	
	public StringBuffer getExecutionDateForCombo(String env, String device){
	
		ResultSet rs = ReportSqls.getExecutionDateForCombo(env, device);
		StringBuffer option = new StringBuffer();	
		try{
		  	
			while(rs.next())
		   	{
			     option.append("<option value='"+rs.getString("executiondate")+"_"+rs.getString("batchid")+"'>"
			    	 		+ ""+rs.getString("executiondate")+" ("+rs.getString("batchid")+")</option>");
		   	}
		  
		}catch(Exception e){
			  option.append("<option value=''>Execution not done</option>"); 
		}
		
		return option;
	}
	
	public StringBuffer getTestResult(String executionDate, String env, String device){
		
		String batchId =  executionDate.split("_")[1].trim();
		executionDate =  executionDate.split("_")[0].trim();
		
		int batchIdInt = Integer.valueOf(batchId);
		StringBuffer strBuffer = new StringBuffer();
		ResultSet rs = ReportSqls.getSuiteDetail(executionDate, env, batchIdInt, device);
		
		try{
		//	if (rs.next()) {
				while (rs.next()) {
	
					int pass = rs.getInt("pass");
					int fail = rs.getInt("fail");
					int skip = rs.getInt("skip");
					int total = rs.getInt("totaltestcase");
	
					float passPer = ((float) pass / total) * 100;
					float failPer = ((float) fail / total) * 100;
					float skipPer = ((float) skip / total) * 100;
		
					strBuffer.append("<tr>");
						if (rs.getString("runmode").equalsIgnoreCase("Y")) {
							strBuffer.append("<td><a href='#' onclick=getTestCaseData('"+rs.getString("suitename")+"','"+env+"'"
									+ ",'"+rs.getString("batchid")+"','"+device+"')>"+rs.getString("suitename")+"</a></td>");
			 
						} else {
			 
							strBuffer.append("<td>"+rs.getString("suitename")+"</td>");
						}
						strBuffer.append("<td align='center'>"+rs.getInt("totaltestcase")+"</td>");
						String browserDB = rs.getString("browser");


						if(browserDB.equalsIgnoreCase("firefox")){	
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
									strBuffer.append("</div>");
								strBuffer.append("</div>");
							strBuffer.append("</td>");
						}else{
								strBuffer.append("<td align=\"center\">");
									strBuffer.append("<div class=\"progress\">");
										strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: 0%\"	title='Test cases not executed'>");
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: 0%\" title='Test cases not executed'>");
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: 0%\" title='Test cases not executed'>");
										strBuffer.append("</div>");
									strBuffer.append("</div>");
								strBuffer.append("</td>");
						}
						/****************CHROME*********/
						
						if(browserDB.equalsIgnoreCase("chrome")){
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
									strBuffer.append("</div>");
								strBuffer.append("</div>");
							strBuffer.append("</td>");
						}else{
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: 0%\"	title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
								strBuffer.append("</div>");
							strBuffer.append("</td>");
						}
						//------------IE-------------------//
						if(browserDB.equalsIgnoreCase("ie")){	
							strBuffer.append("<td align=\"center\">");
							strBuffer.append("<div class=\"progress\">");
								strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
								strBuffer.append("</div>");
							strBuffer.append("</div>");
						strBuffer.append("</td>");
						}else{
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: 0%\"	title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
								strBuffer.append("</div>");
						   strBuffer.append("</td>");
						}
						 
						//-------------------------------//
						if(browserDB.equalsIgnoreCase("android")){	
							strBuffer.append("<td align=\"center\">");
							strBuffer.append("<div class=\"progress\">");
								strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
								strBuffer.append("</div>");
							strBuffer.append("</div>");
						strBuffer.append("</td>");
						}else{
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: 0%\"	title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
								strBuffer.append("</div>");
						   strBuffer.append("</td>");
						}
				 
						if(browserDB.equalsIgnoreCase("api")){	
							strBuffer.append("<td align=\"center\">");
							strBuffer.append("<div class=\"progress\">");
								strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
								strBuffer.append("</div>");
							strBuffer.append("</div>");
						strBuffer.append("</td>");
						}else{
							strBuffer.append("<td align=\"center\">");
								strBuffer.append("<div class=\"progress\">");
									strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: 0%\"	title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
									strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: 0%\" title='Test cases not executed'>");
									strBuffer.append("</div>");
								strBuffer.append("</div>");
						   strBuffer.append("</td>");
						}
						
						strBuffer.append("<td align=\"center\">&nbsp; <a onclick=\"createBarChartWeb(0,0,0,0,0,0,0,0,0,'IE','Firefox','Chrome')\" "
								+ "	href=\"#\"><img width=\"15\" height=\"15\" title=\"Bar Chart\" 	src=\"images/Bar.png\"></a></td>");
					strBuffer.append("</tr>");	
				}
			//}
		}catch(Exception e){
			
		}
		
		return strBuffer;
	}
	
	public static String jsonPrettyPrint(Object response){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.toString());
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
		}
	
	public StringBuffer getEnvCombo(String path, String device){
		
		ReadXlsForUI objReadXlsForUI = new ReadXlsForUI();
		String envCombo = objReadXlsForUI.getEnvSuitXls(path, device);
		String a = "<option value='' selected>Select Environment</option>";

		StringBuffer env = new StringBuffer();
		env.append(a+envCombo);
		return env;
	}
	
}