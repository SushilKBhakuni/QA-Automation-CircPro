package com.autofusion.ui;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xlsx_Reader;
import com.autofusion.web.DriverScript;
//import com.desktop.datascripts.Main;
//import com.web.datascripts.DriverScriptMobile;
//import com.web.datascripts.PerformCommonTask;

/**
 * Servlet implementation class ExecuteTestCases
 */

@SuppressWarnings("all")
public class ExecuteTestCases extends HttpServlet
{
	public static Properties CONFIG;
	//private Logger APP_LOGS = Logger.getLogger(ExecuteTestCases.class);
	private static final long serialVersionUID = 1L;
	public static Logger APP_LOG = null;
	public static Logger APP_LOG1 = null;

	private String testCaseBasePath ;
	
	public void setTestCaseBasePath(String hTestCaseBasePath){
		testCaseBasePath =hTestCaseBasePath;
	}
	
	public String getTestCaseBasePath(){
		return testCaseBasePath;
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteTestCases() {
        super();
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		testCaseBasePath = request.getParameter("hTestCaseBasePath");
		

		PrintWriter out=response.getWriter();
		out.print(testCaseBasePath);

		if(request.getParameter("k")!=null){
			createKillMeFile(request.getParameter("runSuite"));
			Xlsx_Reader suiteXls1 = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("hRunApplication")+"/"+Constants.SUIT_FILE_NAME);
			
			for(int p = 2; p <= suiteXls1.getRowCount(Constants.TEST_SUITE_SHEET); p++){
				String cName   = suiteXls1.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_TSID, p);
				if(cName.equalsIgnoreCase(request.getParameter("runSuite"))){
					suiteXls1.setCellDataRW(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_EXECUTION_STATUS,p,"");
					//suiteXls1.setCellDataRW(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_FF_LOG_PATH,p,"");
					//suiteXls1.setCellDataRW(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_IE_LOG_PATH,p,"");
					//suiteXls1.setCellDataRW(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_CHROME_LOG_PATH,p,"");
					break;
				}
			}
			return;
		}else{
			deleteKillMeFile(request.getParameter("runSuite"));
		}
		String browser = "";
		String environment = "";
		String webIp = "";
		String testType = ""  ;

		if(!request.getParameter("runSuite").equalsIgnoreCase("All")){
			if(!request.getParameter("hPackageName_"+request.getParameter("runSuite")).equals("")){
				testType = "POM";
			}else{
				testType = "KeywordDriven";
			}
		}else {
			testType = "KeywordDriven";
		}
		
		if(request.getParameter("hRunApplication").equalsIgnoreCase("Web")){
			updateSuitXls(request,testCaseBasePath+"\\web\\","Web");
			
			try {
				
				if(request.getParameter("runSuite").equalsIgnoreCase("All")){
					browser = request.getParameter("selBatchBrowser");
					environment = request.getParameter("selBatchEnvironment");
					webIp = request.getParameter("ipBatchExecution");
		        	//DriverScript.mainMethodWeb(testCaseBasePath,"Web", request.getParameter("TS"+request.getParameter("runSuite")+"_ip"), request.getParameter("runSuite"), testCaseArrLst,environment, browser, testType);
				
				}else if(request.getParameter("selBrowser_"+request.getParameter("runSuite")+"Suite").equalsIgnoreCase("MobileChrome")){
					DriverScript.mainMethodWeb(testCaseBasePath, "mobileWeb",webIp,environment, "Chrome"); 
				}else{
					browser = request.getParameter("selBrowser_"+request.getParameter("runSuite")+"Suite");
					environment = request.getParameter("selEnvironment_"+request.getParameter("runSuite"));
			        webIp = request.getParameter("TS"+request.getParameter("runSuite")+"_ip");
					
					
					ArrayList testCaseArrLst = getTestCaseXls(request , request.getParameter("runSuite"));
					DriverScript.mainMethodWebSingle(testCaseBasePath,"Web", request.getParameter("TS"+request.getParameter("runSuite")+"_ip"), request.getParameter("runSuite"), testCaseArrLst,environment, browser, testType);

				}
			}catch (Exception e){
				System.out.println("Error"+e);
			}
			
			
			//********************************************************	
			Cookie counterCookie = new Cookie("set", "set");
			counterCookie.setMaxAge(60*60*24); 
			response.addCookie(counterCookie);
			response.setContentType("text/html");
			//*********************************************************
			out.print("Complete JSP");
		}else if(request.getParameter("hRunApplication").equalsIgnoreCase("mobile")){
			try {
				if(request.getParameter("runSuite").equalsIgnoreCase("All")){
					updateSuitXls(request,testCaseBasePath+"//mobile//","Mobile");
					DriverScript.mainMethodWeb(testCaseBasePath, "Mobile", request.getParameter("ipBatchExecution"),environment, request.getParameter("envBatchExecution"));
				}else{
					ArrayList testCaseArrLst = getTestCaseXls(request , request.getParameter("runSuite"));
					DriverScript.mainMethodWebSingle(testCaseBasePath, "Mobile", 
							request.getParameter("TS"+request.getParameter("runSuite")+"_ip"), request.getParameter("runSuite"), 
							testCaseArrLst,"dev",request.getParameter(request.getParameter("runSuite")+"_platform"), testType);
				}
			}catch (Exception e){
				//APP_LOGS.error(e.getMessage());
				//APP_LOGS.debug(e);
				System.out.println(e);
			}
			
			
			
			  /** copy file to sd card  **/
			  
			 /* String[] batchArgument={testCaseBasePath+"/batchFile/copyTestCaseToMbl.bat",
			  	                      testCaseBasePath+"/mobile/suits.xls"};
			  Runtime.getRuntime().exec(batchArgument);
			  
			  
			  String[] batchArgument1 = {testCaseBasePath+"/batchFile/copyTestCaseToMbl.bat",
	                         			 testCaseBasePath+"/mobile/OR.xls"};
			  
			  Runtime.getRuntime().exec(batchArgument1);
			  
			  copySuitsFile();
			  
			  Runtime.getRuntime().exec(testCaseBasePath+"/batchFile/runMobileTestCase.bat");
			  out.print("Success");*/
			    
		}else if(request.getParameter("hRunApplication").equalsIgnoreCase("api")){
		//	updateSuitXls(request,testCaseBasePath+"\\web\\");	
		 try{	
			if(request.getParameter("runSuite").equalsIgnoreCase("All")){
				browser = request.getParameter("selBatchBrowser");
				environment = request.getParameter("selBatchEnvironment");
				webIp = request.getParameter("ipBatchExecution");
	        	DriverScript.mainMethodWeb(testCaseBasePath, "api",webIp, environment,browser);
			}else{
				browser = "api";
				environment = request.getParameter("selEnvironment_"+request.getParameter("runSuite"));
		        webIp = request.getParameter("TS"+request.getParameter("runSuite")+"_ip");
				
				
				ArrayList testCaseArrLst = getTestCaseXls(request , request.getParameter("runSuite"));
				DriverScript.mainMethodWebSingle(testCaseBasePath,"api", request.getParameter("TS"+request.getParameter("runSuite")+"_ip"), request.getParameter("runSuite"), testCaseArrLst,environment, browser, testType);

			}
		}catch (Exception e){
				//APP_LOGS.error(e.getMessage());
				//APP_LOGS.debug(e);
				System.out.println(e);
			}
			
			out.print("Success");
		}else if(request.getParameter("hRunApplication").equalsIgnoreCase("Window")){
			updateSuitXls(request, testCaseBasePath+"//Window//","Window");
			//Main objMain = new Main();
			//objMain.initializeScript(testCaseBasePath, request);
			out.print("Success");
			
		}else{
			RequestDispatcher reqDis = request.getRequestDispatcher("/error.jsp"); 
			reqDis.forward(request, response);	
		}
		
		//RequestDispatcher reqDis = request.getRequestDispatcher("/success.jsp"); 
		//reqDis.forward(request, response);
		
		out.print("Success");
	}
	
	private boolean isRunableMode(String runmodeFlag){
		if(runmodeFlag.equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}
	
	/*public String[] splitMailAddresses(String address){
		
		String[] addressSplited = {};
		
		if(!address.equals(""))
			addressSplited = address.split(";");
		
		return addressSplited;
	}
	*/
	
	/*public void copySuitsFile() throws IOException{
		
		Xls_Reader webTestSuitXls = new Xls_Reader(testCaseBasePath+"/mobile/suits.xls");
		String fileName = "";
		for(int suiteCount = 2; suiteCount <= webTestSuitXls.getRowCount(Constants.TEST_MBL_SUITE_SHEET);  suiteCount++){
			fileName   = webTestSuitXls.getCellData(Constants.TEST_MBL_SUITE_SHEET, Constants.COL_HEAD_TSID, suiteCount);
			  
			  String[] batchArgument2={testCaseBasePath+"/batchFile/copyTestCaseToMbl.bat",
					  				   testCaseBasePath+"/mobile/"+fileName+".xls"};
			  Runtime.getRuntime().exec(batchArgument2);
		}
	*/	
		
	//}
	public void updateSuitXls(HttpServletRequest request, String testCasePath, String device){
		
		Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCasePath+File.separator+Constants.SUIT_FILE_NAME);
		
		Map updatedTestMap =  request.getParameterMap();
		String testSheetName = Constants.TEST_SUITE_SHEET;
		
		for(int suiteCount = 2; suiteCount <= webTestSuitXls.getRowCount(testSheetName);  suiteCount++){
			 
			 String suitName = "TS"+suiteCount+"_suitName";
			 
			 if(updatedTestMap.containsKey(suitName)){
				 
				 String[] sName =   (String[]) updatedTestMap.get(suitName);
			
				 webTestSuitXls.openXls();
				 
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_RUNMODE, suiteCount, "Y");  //for web, windows
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_EXECUTION_STATUS, suiteCount, Constants.EXE_RUNNING);  //for web, windows
				 if(testSheetName.equalsIgnoreCase(Constants.TEST_SUITE_SHEET)){  //only for web
					 if(device.equalsIgnoreCase("Web")){
					 	 //make all No
						 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IE, suiteCount, "N");
						 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_FF, suiteCount, "N");
						 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_CHROME, suiteCount, "N");
						 //webTestSuitXls.writeXls();
						 
						 //webTestSuitXls.openXls();	
						 //If suit runmode is set to yes then we will chck for the browser
						 if(updatedTestMap.containsKey("TS"+sName[0]+"_browser")){
								String[] bwr =   (String[]) updatedTestMap.get("TS"+sName[0]+"_browser");
								for(int b=0; b < bwr.length; b++){
									if(bwr[b].equalsIgnoreCase("ie")){
										webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IE, suiteCount, "Y");
									}
									
									if(bwr[b].equalsIgnoreCase("Firefox")){
										webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_FF, suiteCount, "Y");
									}
									
									if(bwr[b].equalsIgnoreCase("Chrome")){
										webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_CHROME, suiteCount, "Y");
									}
								}
						 }else{// if no browser is selected then Firefox  will be the default browser for that suite
							
								webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_FF, suiteCount, "Y");
						 }
					 }else if(device.equalsIgnoreCase("Mobile")){
						 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_ANDROID_BWR, suiteCount, "N");
						 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IOS_BWR, suiteCount, "N");
						 
						 
						 //webTestSuitXls.writeXls();
						 
						 //webTestSuitXls.openXls();
						 if(updatedTestMap.containsKey("TS"+sName[0]+"_ip")){
							String[] ip =   (String[]) updatedTestMap.get("TS"+sName[0]+"_ip");
							 webTestSuitXls.setCellData(testSheetName, "RemoteMachine", suiteCount, ip[0]);	 
						 }
						 
						 
						 //If suit runmode is set to yes then we will chck for the browser
						 if(updatedTestMap.containsKey("TS"+sName[0]+"_platform")){
								String[] bwr =   (String[]) updatedTestMap.get("TS"+sName[0]+"_platform");
								for(int b=0; b < bwr.length; b++){
									if(bwr[b].equalsIgnoreCase("android")){
										 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_ANDROID_BWR, suiteCount, "Y");
									}
									
									if(bwr[b].equalsIgnoreCase("ios")){
										webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IOS_BWR, suiteCount, "Y");
									}
								}
						 }else{// if no browser is selected then Firefox  will be the default browser for that suite
								webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_ANDROID_BWR, suiteCount, "Y");
						 }
					 }
				 }
				 webTestSuitXls.writeXls();		 
				 updateTestCaseXls(request, sName[0]);
				 
			 }else{
				    //if suit runmode is No then no ned to check for the browser array
					webTestSuitXls.setCellDataRW(testSheetName, Constants.COL_HEAD_RUNMODE, suiteCount, "N");
			
			 }
			 
		}
	}
	
	
  public void updateTestCaseXls(HttpServletRequest request, String testCaseFile){
	  
	    Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("hRunApplication")+"/TestCases/"+testCaseFile+".xlsx");
		Map updatedTestMap =  request.getParameterMap();
		webTestSuitXls.openXls();
		for(int tCaseCount = 2; tCaseCount <= webTestSuitXls.getRowCount(Constants.TEST_CASES_SHEET);  tCaseCount++){
			 String testCaseName = testCaseFile+"_testCaseId"+tCaseCount;
		
			 if(updatedTestMap.containsKey(testCaseName)){

				 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, tCaseCount, "Y");
				 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, tCaseCount, "N");
				 
				 if(updatedTestMap.containsKey(testCaseFile+"_testCaseDataId"+tCaseCount)){
					 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, tCaseCount, "Y");
				 }else{
					 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, tCaseCount, "N");
				 }
			 }else{
					webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, tCaseCount, "N");
			 }
		}
	    webTestSuitXls.writeXls();
	}

  
  public ArrayList getTestCaseXls(HttpServletRequest request, String testCaseFile){
	  ArrayList arrList = new ArrayList();
	    Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("hRunApplication")+"/TestCases/"+testCaseFile+".xlsx");
		Map updatedTestMap =  request.getParameterMap();
		//webTestSuitXls.openXls();
		for(int tCaseCount = 2; tCaseCount <= webTestSuitXls.getRowCount(Constants.TEST_CASES_SHEET);  tCaseCount++){
			 String testCaseName = testCaseFile+"_testCaseId"+tCaseCount;
		
			 if(updatedTestMap.containsKey(testCaseName)){
				 String testCaseId =  webTestSuitXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, tCaseCount);
				 arrList.add(testCaseId);
			 }
		}
		return arrList;
	}

  
  
	  public void createKillMeFile(String suitName){
		  
		  File f = new File(testCaseBasePath+"/kill"+suitName+".txt");
		  try {
			f.createNewFile();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	  }
	  
	  public void deleteKillMeFile(String suitName){
		  
		    File f = new File(testCaseBasePath+"/kill"+suitName+".txt");
			if(f.exists()){
				f.deleteOnExit();
				//APP_LOGS.debug(testCaseBasePath+" Script Killed file deleted");
			}
	  }
	  
	 

}
