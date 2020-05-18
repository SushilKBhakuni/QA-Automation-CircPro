package com.autofusion.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.autofusion.BaseClass;
import com.autofusion.PropertyManager;
import com.autofusion.android.KeywordAndroid;
import com.autofusion.bean.CommonUtility;
import com.autofusion.bean.DriverScriptBean;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.autofusion.report.CreateTestNgXml;
import com.autofusion.sql.ReportSqls;
import com.autofusion.util.InitClass;
import com.autofusion.util.ReadConfigXlsFiles;
import com.autofusion.util.ReportUtil;
import com.autofusion.util.SendMail;
import com.autofusion.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author nitin.singh
 * 
 * android : Native App
 * web : mobile web and website
 * 
 */
import io.appium.java_client.AppiumDriver;

@SuppressWarnings("all")
public class DriverScript extends BaseClass{

//	public static Logger APP_LOG = null;
	public DriverScriptBean objMainScriptBean ;
	public static Properties CONFIG;
	private static Properties OR;
	public static Map<String,HashMap<String, String>> orMap = new HashMap<String, HashMap<String, String>>();;
	public Map<String, String> argsMap = new HashMap<String, String>();
	public boolean testCaseResultStatus = true;
	public boolean dbNotRequire = true;
	private Map<String, HashMap> masterMap = new HashMap<String, HashMap>();;
	public KeywordAndroid objAndroidKeywords;
    private AppiumDriver appDriver;	
	public Method objMethod[];
	public Map innerMap ; 
	public ReportUtil reportUtil;
	private ArrayList<String> finalTestResult;
	public ArrayList skipTestCase;
    public String folderLocation = "";
    public String reportFolderLocation = "";
    public ExtentTest extentParentNode;
    public String method = "";
    public String resource = "";
    public String parameters = "";
    public String header = "";
    public String dynamicDataParam =""; 
    
    
	public DriverScript(){
		objMainScriptBean = new DriverScriptBean();
    }
	
	public void initializeKeywordClases(String executionMode, String ip) throws IOException{
		APP_LOG.debug("initializing Keywords For Script");
		

		if(executionMode.equalsIgnoreCase("mobileWeb")){ //android mobile web
			objAndroidKeywords = new KeywordAndroid();
			objMethod = objAndroidKeywords.getClass().getMethods();
			AppiumDriver webDriver = KeywordAndroid.driver;
			setAppDriver(webDriver);
		}else if(executionMode.equalsIgnoreCase("mobile")){//executionMode.equalsIgnoreCase("android") || executionMode.equalsIgnoreCase("ios")){//android native app
			/*objNativeKeywords = new KeywordsNativeApp(this.APP_LOG, objMainScriptBean.getBrowserToOpen(), ip);
			AppiumDriver webDriver = KeywordsNativeApp.driver;
			KeywordsNativeApp.APP_LOGS = this.APP_LOG;
			setAppDriver(webDriver);
	    	objMethod = objNativeKeywords.getClass().getMethods();*/
		}else{//website
			//objKeywords = new Keyword();
		//	objMethod = objKeywords.getClass().getMethods();
		}
		
		
		APP_LOG.debug("initializing Keywords For Script done");
	}
	
	/**
	 * 
	 * @param basePath
	 * @param executionMode - mobile, web 
	 * @param ip
	 * @param enviroment
	 * @param browserToOpen
	 * @throws IOException
	 */
	public void initializeWebScript(String basePath, String executionMode, String ip, String enviroment, String browserToOpen) throws IOException{
		
		objMainScriptBean.setProjectBasePath(basePath);
	    folderLocation = basePath+File.separator+executionMode;
		objMainScriptBean.setExecutionDeviceCategory(executionMode);
		objMainScriptBean.setBrowserToOpen(browserToOpen);
 		objMainScriptBean.setExecutionDevice(executionMode);
 		objMainScriptBean.setExutionEnvironment(enviroment);
 		
 		APP_LOG.debug("The base  path b4 calling initilzise in initializeWebScript --- "+basePath);

		InitClass.initializeLogger(basePath, executionMode);
		
		/** Initialize extent report object ***/
		initializeReportObject();
		//HTML Report
		objMainScriptBean.setExecutionStartTime(reportStartTime);
		reportIntialization(basePath,browser);
		
		/*if(executionMode.equalsIgnoreCase("mobileWeb")){ //android mobile web
				objAndroidKeywords = new KeywordAndroid();
				objMethod = objAndroidKeywords.getClass().getMethods();
				AppiumDriver webDriver = KeywordAndroid.webDriver;
				setAppDriver(webDriver);
		}else */
		if(executionMode.equalsIgnoreCase("Mobile")){//android native app
			getMobileDriver(ip);
		}		
		
	}
			
	public static String now(String dateFormat) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return (String)sdf.format(cal.getTime());
	}


	
	//Step 1
	public void executeTestSuites() throws IOException{
		
		
		APP_LOG.debug("Executing Test Suite => "+Constants.SUIT_FILE_NAME);
		
		//String reportStartTime = now("dd.MMMMM.yyyy hh.mm.ss aaa");
		objMainScriptBean.setExecutionStartTime(reportStartTime);
		ArrayList testCasesList = new ArrayList();
		Xls_Reader suiteXls = new Xls_Reader(objMainScriptBean.getProjectBasePath()+File.separator+objMainScriptBean.getExecutionDeviceCategory()+"/"+Constants.SUIT_FILE_NAME);
    	
		for(int suiteProgramsCount = 2; suiteProgramsCount <= suiteXls.getRowCount(Constants.TEST_SUITE_SHEET); suiteProgramsCount++){
  		   
    		String stTime = now("dd.MMMMM.yyyy hh.mm.ss aaa");
    		String stTimeDb = now("yyyy-MM-dd hh.mm.ss");
    		String suiteName = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_TSID, suiteProgramsCount);
    		String suiteDescription = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_DESCRIPTION, suiteProgramsCount);
    		String androidBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_ANDROID_BWR, suiteProgramsCount);
    		String iosBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_IOS_BWR, suiteProgramsCount);
    		String ieBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_IE, suiteProgramsCount);
    		String ffBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_FF, suiteProgramsCount);
    		String chBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_CHROME, suiteProgramsCount);
    		String sfBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_SAFARI, suiteProgramsCount);
    		String mobileBrowser = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "MobileBrowser", suiteProgramsCount);
    		
    		String prgRunmode = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_RUNMODE, suiteProgramsCount);
    		String ip = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, "RemoteMachine", suiteProgramsCount);
    		
    		APP_LOG.debug("Data Row:"+suiteName+" "+ieBrowser+" "+ffBrowser+" "+chBrowser+" "+sfBrowser);
    		String browserToOpen = "Firefox";
    		if(androidBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "andoid";
    		}else if(iosBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "IOS";
    		}else if(ieBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "InternetExplorer";
    		}else if(ffBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "Firefox";
    		}else if(chBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "Chrome";
    		}else if(sfBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "Sfari";
    		}else if(mobileBrowser.equalsIgnoreCase("Y")){
    			browserToOpen = "mobileBrowser";
    		}
    		
    		objMainScriptBean.setBrowserToOpen(browserToOpen);
    		//extentParentNode = reportObj.startTest(suiteName).assignCategory("Regression");
    		
    		//Insertdata in db

    		if(prgRunmode.equalsIgnoreCase("Y")){

    			//if(objMainScriptBean.getReportBasePath() == null){
    		    	reportIntialization(objMainScriptBean.getProjectBasePath(),browserToOpen);
					/*String reportPath=null;
					reportPath = folderLocation+File.separator+CONFIG.getProperty("REPORT_FOLDER_LOC")+File.separator+browserToOpen+File.separator+now("dd.MMMMM.yyyy.hh.mm.ss");
					APP_LOG.debug("reportPath :"+reportPath);
					objMainScriptBean.setReportBasePath(reportPath);
					reportHeader();*/
				//}
    		    	
    			
    			executeWebTestCase(suiteName, testCasesList);
        		String  edTime= now("yyyy-MM-dd hh.mm.ss");
        		int totalTestCase = 0;
        		//ReportSqls.insertSuiteDetails(suiteName, suiteDescription, executionEnviroment,totalTestCase,finalTestResult, now("yyyy-MM-dd"),stTimeDb ,edTime,batchId, prgRunmode, objMainScriptBean.getExecutionDevice());

    			//insertResult(suiteName, suiteDescription, browserToOpen, stTime);
    		/*try {
	    			int totPass = Collections.frequency(finalTestResult, Constants.PASS);
	     			int totFail  = Collections.frequency(finalTestResult, Constants.FAIL);
	    			int totSkip  = Collections.frequency(finalTestResult, Constants.SKIP);
	    			
	    			int totTCase = totSkip+totFail+totPass; 
	    		
	    			APP_LOG.debug("Insert Data in report summary");
        			CommonUtility.insertReportSummaryData(suiteName, suiteDescription , totTCase, totPass, totFail, totSkip, browserToOpen,
    						stTime, now("dd.MMMMM.yyyy hh.mm.ss aaa"), objMainScriptBean.getBatchId(),objMainScriptBean.getExecutionDevice(), APP_LOG);
    			} catch (SQLException e) {
    				APP_LOG.debug("Exception Insert Data in report summary "+e);
    				e.printStackTrace();
    			}*/
    		
    		}else{
    			finalTestResult = new ArrayList<String>();
        		String  edTime= now("yyyy-MM-dd hh.mm.ss");
        		Xls_Reader suiteXlsSkip = new Xls_Reader(folderLocation+File.separator+"TestCases"+File.separator+suiteName+".xlsx");
        		int totalTestCase = suiteXlsSkip.getRowCount(Constants.TEST_CASES_SHEET);
        		ReportSqls.insertSuiteDetails(suiteName, suiteDescription, executionEnviroment,totalTestCase,finalTestResult, now("yyyy-MM-dd"),stTimeDb ,edTime,batchId, prgRunmode, objMainScriptBean.getExecutionDevice());
    			extentParentNode.log(LogStatus.SKIP, suiteName);
    		}
    		
    	}
		tearDownExtentReport();

		createSuiteReportToSend();
		ReportSqls.insertAutomationSummary("Category", executionEnviroment,
				 now("dd.MMMMM.yyyy hh.mm.ss"),batchId);
    	reportUtil.updateEndTime(now("dd.MMMMM.yyyy hh.mm.ss aaa"));
    } 
	
	public void reportIntialization(String basePath, String browserToOpen){

		if(objMainScriptBean.getReportBasePath() == null){
			String reportPath=null;
			//reportPath = folderLocation+File.separator+CONFIG.getProperty("REPORT_FOLDER_LOC")+File.separator+browserToOpen+File.separator+now("dd.MMMMM.yyyy.hh.mm.ss");
			reportPath = basePath+File.separator+File.separator+device+"//report//Browser"+File.separator+File.separator+browser+File.separator+File.separator+reportStartTime;
			APP_LOG.debug("reportPath :"+reportPath);
			objMainScriptBean.setReportBasePath(reportPath);
			reportHeader();
		}
		
	}
	
	//ToDo for batch
	public void executeSingleTestSuites(String suiteName, ArrayList testCasesList, String ip, boolean batchFlag) throws IOException{
		APP_LOG.debug("executeTestSuites");
		String[] suiteArr = new String[testCasesList.size()];
		//Code  to add before this
		//String reportStartTime = now("dd.MMMMM.yyyy hh.mm.ss aaa");
		for(int i=0; i<testCasesList.size();i++)
		{
			suiteArr[i]=testCasesList.get(i).toString();
		}
		for(int suiteProgramsCount = 0; suiteProgramsCount < suiteArr.length; suiteProgramsCount++){
			String stTime = now("dd.MMMMM.yyyy hh.mm.ss aaa");
    		String stTimeDb = now("yyyy-MM-dd hh.mm.ss");
    		String suiteDescription = "";
    		String browserToOpen = objMainScriptBean.getBrowserToOpen();
    		suiteName = suiteArr[suiteProgramsCount];
    		setRunningComponentName(suiteName);
    		APP_LOG.debug("Data Row:"+suiteName+" "+browserToOpen);
    		System.out.println("Data Row:"+suiteName+" "+browserToOpen);
    		try{
    			executeWebTestCase(suiteName, testCasesList);
    		}catch(Exception e){
    			APP_LOG.debug(" executeSingleTestSuites :"+e);
    		}
    		
			String  edTime= now("yyyy-MM-dd hh.mm.ss");
    		int totalTestCase = 0;

    		//ReportSqls.insertSuiteDetails(suiteName, suiteDescription, executionEnviroment,totalTestCase,finalTestResult, now("yyyy-MM-dd"),stTimeDb ,edTime,batchId, "Y", objMainScriptBean.getExecutionDevice());

    	}
    		if(!batchFlag) {
    			createSuiteReportToSend();
    			reportUtil.updateEndTime(now("dd.MMMMM.yyyy hh.mm.ss aaa"));
    		}
    
	}
	

/** for batch execution ***/
	
	public void executeBatchTestSuites(String suiteName, ArrayList testSuitList, String ip) throws IOException{
		APP_LOG.debug("executeTestSuites in batch");

		for(int suiteProgramsCount = 0; suiteProgramsCount <= testSuitList.size(); suiteProgramsCount++){
				executeSingleTestSuites(testSuitList.get(suiteProgramsCount).toString(),testSuitList, ip, true);
    	}
    		createSuiteReportToSend();
    	reportUtil.updateEndTime(now("dd.MMMMM.yyyy hh.mm.ss aaa"));
    
	}

	
	public void insertResult(String suiteName, String suiteDescription,String browserToOpen,String stTime){
		if(!isDbNotRequire()){
			APP_LOG.debug("Insert DB Off");
			return;
		}
		try {
			int totPass = Collections.frequency(finalTestResult, Constants.PASS);
			int totFail  = Collections.frequency(finalTestResult, Constants.FAIL);
			int totSkip  = Collections.frequency(finalTestResult, Constants.SKIP);
			
			int totTCase = totSkip+totFail+totPass; 
		
			APP_LOG.debug("Insert Data in report summary");
			CommonUtility.insertReportSummaryData(suiteName, suiteDescription , totTCase, totPass, totFail, totSkip, browserToOpen,
					stTime, now("dd.MMMMM.yyyy hh.mm.ss aaa"), objMainScriptBean.getBatchId(),objMainScriptBean.getExecutionDevice(), APP_LOG);
		} catch (SQLException e) {
			APP_LOG.debug("Exception Insert Data in report summary "+e);
			e.printStackTrace();
		}

	}
	//Step 2
	public void executeWebTestCase(String suiteName, ArrayList testCasesList){//, String[] testCases		
		
		APP_LOG.info("Function executeWebTestCase : Starting Execution.... ");
		
		reportUtil.startSuite(suiteName);
		skipTestCase=new ArrayList<String>();
		finalTestResult = new ArrayList<String>();
		
		APP_LOG.debug("[Function executeWebTestCase] Suite Name :"+folderLocation+File.separator+"TestCases"+File.separator+suiteName+".xlsx");
		
		Xls_Reader suiteXls = new Xls_Reader(folderLocation+File.separator+"TestCases"+File.separator+suiteName+".xlsx");
		objMainScriptBean.setLastRowCounter(2);
		//Test Case Loop
		for(int testCaseRowCount = 2; testCaseRowCount <= suiteXls.getRowCount(Constants.TEST_CASES_SHEET); testCaseRowCount++){
			
			//get case ID
			boolean returnResult = true;
			testCaseResultStatus = true;
    		String stTime =  now("yyyy-MM-dd hh.mm.ss");
			String tCaseID = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, testCaseRowCount);
			String tCaseRunmodeFlag = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, testCaseRowCount);
			
			/*if(testCasesList.size() > 0 && !testCasesList.contains(tCaseID)){
				APP_LOG.debug("[Function executeWebTestCase] Test case skipduring selected run");
				continue;
			}else if(testCasesList.size() > 0 && testCasesList.contains(tCaseID)){//we are bypassing the xlsx
				APP_LOG.debug("[Function executeWebTestCase] Inside by passing xlsx "+testCasesList.size());
				tCaseRunmodeFlag = "Y";
			}*/
			
			String tCaseDesc = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DESCRIPTION, testCaseRowCount);
			String isDataDriven = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_DATA_DRIVEN, testCaseRowCount);
			
			objMainScriptBean.setIsDataDriven(isDataDriven);
			objMainScriptBean.setCurrentTestCaseId(tCaseID);
			objMainScriptBean.setLastTestCaseId(tCaseID);
			objMainScriptBean.setCurrentSuitName(suiteName);
			objMainScriptBean.setTestSuitDesc(tCaseDesc);
			//objMainScriptBean.setLastRowCounter(2);
		
			
			/****API*******/
			
			
			method = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "Method",testCaseRowCount );
			resource = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "Resource",testCaseRowCount );
			parameters = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "Parameters",testCaseRowCount );
			header = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "Header",testCaseRowCount );
			dynamicDataParam = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "DynamicDataParam",testCaseRowCount );

			
			
		/*	//argMap = new HashMap<String, String>();
			argMap.put("Method", method);
			argMap.put("EndPoint", configurationsXlsMap.get(Constants.CONFIG_COL_DOMAIN_NAME));
			argMap.put("Resource", resource);
			argMap.put("Parameters", parameters);
			argMap.put("DynamicDataParam", dynamicDataParam);
			argMap.put("Header", header);
			*/
			
			APP_LOG.debug("[Function executeWebTestCase] Test Case: "+tCaseID +" => Runmode = "+tCaseRunmodeFlag);
					
			reportTestObj = null;
			if(isRunableMode(tCaseRunmodeFlag)){
			reportTestObj = reportObj.startTest(tCaseID+" : "+tCaseDesc); 
			}
			//extentParentNode.appendChild(reportTestObj);
			
			if(isRunableMode(tCaseRunmodeFlag)){
				System.out.println(tCaseRunmodeFlag);
				if(suiteXls.isSheetExist(tCaseID)){//if we have different sheet for testcase
					//Fetch test data
					for(int currentTestStepDataId = 2; currentTestStepDataId <= suiteXls.getRowCount(tCaseID); currentTestStepDataId++){
						String testDataRunmodeFlag = suiteXls.getCellData(tCaseID, Constants.COL_HEAD_RUNMODE, currentTestStepDataId);
						   if(isRunableMode(testDataRunmodeFlag)){
							    //execute test steps
							    returnResult = executeWebTestSteps(suiteXls);
							}
					  }
				}else if(isDataDriven.equalsIgnoreCase("Y")){
					System.out.println(tCaseRunmodeFlag);
					APP_LOG.debug("[Function executeWebTestCase] Data Driven Selected::"+tCaseID);
					readDataFromDataSheet(tCaseID, suiteXls);
					for(int currentTestStepDataId = 1; currentTestStepDataId <= masterMap.size(); currentTestStepDataId++){
						innerMap = masterMap.get(Constants.PREFIX_DATA_SHEET+"-"+currentTestStepDataId);//increase the counter
						if(!innerMap.isEmpty()){
							if(innerMap.get(Constants.COL_HEAD_RUNMODE).equals("Y"))	
								returnResult = executeWebTestSteps(suiteXls);
						}
					}
				}else{//no data sheet available run once 
						
						returnResult = executeWebTestSteps(suiteXls);
						//reportUtil.addTestCase(objMainScriptBean.getLastTestCaseId(), objMainScriptBean.getExecutionStartTime(),now("dd.MMMMM.yyyy hh.mm.ss aaa"),Constants.PASS);
						APP_LOG.debug("[Function executeWebTestCase] Execution of Test steps done: "+tCaseID);
				}
				/*if(!returnResult){
					APP_LOG.debug("[Function executeWebTestCase] Return result :: "+returnResult);
					break;
					
				}*/
				
				String endTime = now("yyyy-MM-dd hh.mm.ss");
				String keywordResult = ""; 
				if(returnResult){
					finalTestResult.add(Constants.PASS);
					keywordResult = Constants.PASS;
 				}else{
					finalTestResult.add(Constants.FAIL);
					keywordResult = Constants.FAIL;
 				}
				 
				reportUtil.addTestCase(objMainScriptBean.getLastTestCaseId(), objMainScriptBean.getExecutionStartTime(),now("dd.MMMMM.yyyy hh.mm.ss aaa"),keywordResult, objMainScriptBean.getTestSuitDesc());
				//ReportSqls.insertTestCaseDetails(suiteName, tCaseID, tCaseDesc, executionEnviroment, keywordResult, now("yyyy-MM-dd"),stTime ,endTime,batchId, tCaseRunmodeFlag, objMainScriptBean.getExecutionDevice());
				 
			}else{
				//APP_LOG.debug("[Function executeWebTestCase] Skipping Test Case: "+tCaseID +" => Runmode = "+tCaseRunmodeFlag);
				//skipTestCase.add(tCaseID);	
				//finalTestResult.add(Constants.SKIP);
//				reportUtil.addTestCase(tCaseID, 
//						objMainScriptBean.getExecutionStartTime(), 
//						now("dd.MMMMM.yyyy hh.mm.ss aaa"),
//						Constants.SKIP, tCaseDesc );
//				reportTestObj.log(LogStatus.SKIP, tCaseDesc);
//				String endTime =  now("yyyy-MM-dd hh.mm.ss");;
				//ReportSqls.insertTestCaseDetails(suiteName, tCaseID, tCaseDesc, executionEnviroment, Constants.SKIP, now("yyyy-MM-dd"),stTime ,endTime,batchId, tCaseRunmodeFlag, objMainScriptBean.getExecutionDevice());
				
			}
			//extentParentNode.appendChild(reportTestObj);
			//reportObj.endTest(extentParentNode);
			
			 
		}
		    
		// convertXLStoXML(); //One Time use only
		//createTestResultXlsReport();
 		endExtentReport();
 		reportUtil.updateEndTime(reportUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		reportUtil.updateExecutionReport(finalTestResult);
		APP_LOG.debug("[Function executeWebTestCase] Execution Done");
	}
	
	public boolean executeWebTestSteps(Xls_Reader currentSuiteXls) throws RuntimeException{
		
		ArrayList<String> goTo = new ArrayList<String>(); 
		String waitParameter = "0";
		
	    int lastRowCounter = objMainScriptBean.getLastRowCounter();
	    String keywordResult ="";
	    boolean jumpFlag = false; 
		//Test Step Loop
	    String prevTestId=""; 
	    
	    
	    for(int testCaseStepsCount = lastRowCounter; testCaseStepsCount <= currentSuiteXls.getRowCount(Constants.TEST_STEPS); testCaseStepsCount++){
			String fileName = null;
			 
			System.out.println("testCaseStepsCount : TS-" +testCaseStepsCount +" - LastRowCounter"+lastRowCounter);
			
			File f = new File(objMainScriptBean.getProjectBasePath()+File.separator+"kill"+objMainScriptBean.getCurrentSuitName()+".txt");
			if(f.exists()){
				APP_LOG.debug(objMainScriptBean.getCurrentSuitName()+" Script Killed!!");
				reportUtil.addTestCase(objMainScriptBean.getLastTestCaseId(), objMainScriptBean.getExecutionStartTime(),now("dd.MMMMM.yyyy hh.mm.ss aaa"),"Script Killed", objMainScriptBean.getTestSuitDesc());
				return stopScript(f);
			}
			
 			String testStepsCaseId = currentSuiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID,testCaseStepsCount );
			String jumpTo = currentSuiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_GO_TO,testCaseStepsCount );
			waitParameter = currentSuiteXls.getCellData(Constants.TEST_STEPS,Constants.COL_HEAD_WAIT,testCaseStepsCount);
			String skipStepFlag  = currentSuiteXls.getCellData(Constants.TEST_STEPS,Constants.COL_SKIP_STEP,testCaseStepsCount);
			
			System.out.println("testStepsCaseId: "+testStepsCaseId+" "+jumpTo);
			
			if(skipStepFlag.equals("Y")){
				APP_LOG.debug("Skipping step tsid="+testStepsCaseId);
				continue;
			}

			
			if(skipTestCase.contains(testStepsCaseId)){
				continue;
			}
			
			/*if(!testStepsCaseId.equals(objMainScriptBean.getCurrentTestCaseId())){
				//objMainScriptBean.setLastRowCounter(testCaseStepsCount);
				//objMainScriptBean.setCurrentTestCaseId(testStepsCaseId);
				
				APP_LOG.debug("TestCaseStepsCount : "+testCaseStepsCount+" || TestStepsCaseId: "+testStepsCaseId);
				*//** if we have jump to then we have to iterate till test case get find out **//*
				if(!jumpFlag)
					break;
				else
					continue;
			}*/
		  if(testStepsCaseId.equals(objMainScriptBean.getCurrentTestCaseId())){
				if(!jumpTo.equals("")){
					if(jumpTo.startsWith("CS-")){
						keywordResult = executeCommonTestCases(jumpTo, Constants.TEST_COMMON_SHEET, currentSuiteXls, testStepsCaseId);
						prevTestId = testStepsCaseId; 
						
						if(keywordResult.contains(Constants.FAIL))
							testCaseResultStatus = false;
						reportObj.flush();
						continue;
					}
					if(jumpTo.startsWith(Constants.COMMON_STEPS_VAR_PREFIX)){
						executeCommonTestCasesFromCommonSheet(jumpTo);
						prevTestId = testStepsCaseId; 
						reportObj.flush();
						continue;
					}
					if(jumpTo.startsWith("TC-") && !jumpFlag){
						objMainScriptBean.setCurrentTestCaseId(jumpTo);
						jumpFlag = true;
						reportObj.flush();
						continue;
					}
				}else{
					keywordResult = executeKeyword(currentSuiteXls, testCaseStepsCount, Constants.TEST_STEPS, testStepsCaseId);
					reportObj.flush();
				}
				
				if(keywordResult.contains(Constants.FAIL))
					testCaseResultStatus = false;	
				reportObj.flush();
				if(!jumpTo.equals(""))
					goTo.add(jumpTo);
					
				objMainScriptBean.setCurrentTestCaseId(testStepsCaseId);
				
				try {
					applyWait(waitParameter);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				prevTestId = testStepsCaseId; 
	       }//end if
		  
		  if(!prevTestId.equals(testStepsCaseId) && !prevTestId.equals("")){
			  break;
		  }
		}// end for
		/*if(testCaseResultStatus){
			finalTestResult.add(Constants.PASS);
			keywordResult = Constants.PASS;
		}else{
			finalTestResult.add(Constants.FAIL);
			keywordResult = Constants.FAIL;
		}
		 
		reportUtil.addTestCase(objMainScriptBean.getLastTestCaseId(), objMainScriptBean.getExecutionStartTime(),now("dd.MMMMM.yyyy hh.mm.ss aaa"),keywordResult, objMainScriptBean.getTestSuitDesc());
		*/
	    reportObj.flush();
		return testCaseResultStatus;
	}
	
	
	public String assignData(String object, String data){
		String newData = data;
		
		if(objMainScriptBean.getIsDataDriven().equalsIgnoreCase("Y")){
			try{
				newData = innerMap.get(object).toString();
			}catch(Exception e){
				APP_LOG.error("Key doesnot exists" , e);
				newData = data; 
			}
			
			if(innerMap.containsKey(Constants.COL_DYNAMIC_XPATH)){
					dynamicTextToBeReplaced = innerMap.get(Constants.COL_DYNAMIC_XPATH).toString();
			}
			if(innerMap.containsKey(Constants.COL_INDEX_POSITION)){
				locatorIndexPosition = innerMap.get(Constants.COL_INDEX_POSITION).toString();
			}
	    }
		APP_LOG.debug("assignData : "+newData);
		
		newData = regenerateData(newData);
		return newData;
	}
	
	public String regenerateData(String data){
		String newData  = data;
		APP_LOG.debug("regenerateData : "+data);
		if(data.startsWith("GEN_DATA")){ // generate randome data
			newData = CommonUtility.getRandomTimeString();
			if(data.split("\\|").length > 1){
				newData = data.split("\\|")[1].replace("REPLACE_ME", newData);
			}
		}else if(data.startsWith("CONFIG")){ // get config data
			if(data.split("\\|").length > 1){
				if(configurationsXlsMap.containsKey(data.split("\\|")[1])){
					newData = configurationsXlsMap.get(data.split("\\|")[1]);
					if(data.split("\\|").length > 2)
						newData+=data.split("\\|")[2];
				}
			}
		}else if(data.startsWith("SQL")){
			String query = "";
			if(data.split("\\|").length > 1){
				query = ReadConfigXlsFiles.getSQLQuery(objMainScriptBean.getProjectBasePath(),data.split("\\|")[1], APP_LOG);
				if(data.split("\\|").length > 2){
					query = query.replace("<PARAM>", data.split("\\|")[2]);
					query = query.replace("<WHERE>", data.split("\\|")[3]);
					argsMap.put("PARAM", data.split("\\|")[2]);
					argsMap.put("WHERE", data.split("\\|")[3]);
				}
			}
			argsMap.put("DB_USER", configurationsXlsMap.get("DB_USER"));
			argsMap.put("DB_PASS", configurationsXlsMap.get("DB_PASS"));
			argsMap.put("DB_URL", configurationsXlsMap.get("DB_URL"));
			data = query;
		}
		APP_LOG.debug("regenerateData : "+newData);
		return newData;
	}
	
	public boolean isRunableMode(String runmodeFlag){
		APP_LOG.info("Method : isRunableMode => runmodeFlag = "+runmodeFlag);

		if(runmodeFlag.equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}


	//Read Data from DataSheet
	public void readDataFromDataSheet(String testStepsCaseId, Xls_Reader currentSuiteXls){//splitedData = Column name
		String tcaseId ;
		masterMap.clear();
		for(int i = 2; i <= currentSuiteXls.getRowCount(Constants.DATA_SHEET); i++){
			tcaseId = currentSuiteXls.getCellData(Constants.DATA_SHEET, 0, i);
			if(tcaseId.equalsIgnoreCase(testStepsCaseId)){
				HashMap<String, String> rowData = new HashMap<String, String>();

				for(int j=0; j < currentSuiteXls.getColumnCount(Constants.DATA_SHEET); j++){

					String colHeading = currentSuiteXls.getCellData(Constants.DATA_SHEET, j, 1);
					rowData.put(colHeading, currentSuiteXls.getCellData(Constants.DATA_SHEET, colHeading, i));
				}
				masterMap.put(currentSuiteXls.getCellData(Constants.DATA_SHEET, Constants.COL_HEAD_TCDI, i),rowData);

			}
		}

	}

	public void applyWait(String waitDuration) throws InterruptedException{

		if(waitDuration.equals("")){
			return;
		}
	
		long waitParameterLong = Long.valueOf(waitDuration);
		APP_LOG.debug("Wait for next operation => waitParameterLong = "+waitParameterLong);
		Thread.sleep(waitParameterLong);
	}
	

	public void executeCommonTestCasesFromCommonSheet(String jumpTo){

		APP_LOG.debug("Method : executeCommonSteps => Common Step found = "+jumpTo);

		String[] jumpToArr = jumpTo.split("_");
		String sheetName = jumpToArr[1];
		String cmnStep = jumpToArr[2];

		Xls_Reader objCommonSheet = new Xls_Reader(folderLocation+File.separator+Constants.COMMON_STEPS_FILE_NAME+".xlsx");
		
		for(int testCaseStepsCount = 2; testCaseStepsCount <= objCommonSheet.getRowCount(Constants.TEST_STEPS); testCaseStepsCount++){
				String fileName = null;
				
				String testStepsCaseId = objCommonSheet.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID,testCaseStepsCount );

				if(!testStepsCaseId.equals(jumpTo)){
					APP_LOG.debug("TestCaseStepsCount : "+testCaseStepsCount+" || TestStepsCaseId: "+testStepsCaseId);
					break;
				}else{
					String keywordResult = executeKeyword(objCommonSheet, testCaseStepsCount, Constants.TEST_STEPS, testStepsCaseId);
				}
		  }
	}
	
	public String executeCommonTestCases(String jumpTo, String sheeName, Xls_Reader objCommonSheet, String actualTcId){

		APP_LOG.debug("Method : executeCommonSteps => Common Step found = "+jumpTo);
		String keywordResult = "";
		for(int testCaseStepsCount = 2; testCaseStepsCount <= objCommonSheet.getRowCount(sheeName); testCaseStepsCount++){
				String fileName = null;
				
				String testStepsCaseId = objCommonSheet.getCellData(sheeName, Constants.COL_HEAD_TCID,testCaseStepsCount );

				if(testStepsCaseId.equals(jumpTo)){
					keywordResult = executeKeyword(objCommonSheet, testCaseStepsCount, sheeName, actualTcId);
					APP_LOG.debug("executeCommonTestCases : Common steps done");
					APP_LOG.debug("TestCaseStepsCount : "+testCaseStepsCount+" || TestStepsCaseId: "+testStepsCaseId);
					if(keywordResult.contains(Constants.FAIL))
						testCaseResultStatus = false;
				}
				/*else{
					
					
				}*/
		  }
		
		return keywordResult;
	}
	
	
	
	public String executeKeyword(Xls_Reader currentSuiteXls, int testCaseStepsCount, String sheetName, String actualTcId){
		String startTime =  now("yyyy-MM-dd hh.mm.ss");;

		String keywordResult = "";
		String fileName = null;
		String exceptionMsg = "";
		String currentKeyword = "";
		if(objMainScriptBean.getExecutionDevice().equalsIgnoreCase("ios")){
			currentKeyword = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_KEYWORD_IOS,testCaseStepsCount );
		}else{
			currentKeyword = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_KEYWORD,testCaseStepsCount );
		}
		String testDescription = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_STEP_DESCRIPTION,testCaseStepsCount );
		String tsid   = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_TSID,testCaseStepsCount );
		String tcid   = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_TCID,testCaseStepsCount );
		String object = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_ELEMENT_ID,testCaseStepsCount );
		String data   = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_DATA,testCaseStepsCount );
		String data1   = currentSuiteXls.getCellData(sheetName, "Data1",testCaseStepsCount );
		
		String indexPositionofEdit   = currentSuiteXls.getCellData(sheetName, Constants.COL_HEAD_INDEX_POS,testCaseStepsCount );
		String dynamicXpathVal   = currentSuiteXls.getCellData(sheetName, Constants.COL_DYNAMIC_XPATH,testCaseStepsCount );
		String parantNode   = currentSuiteXls.getCellData(sheetName, "ParantNode",testCaseStepsCount );
		String indexPos = currentSuiteXls.getCellData(sheetName, "IndexPosition",testCaseStepsCount );
		String runMode   = currentSuiteXls.getCellData(sheetName, "Runmode",testCaseStepsCount );
		String waitParamStr   = currentSuiteXls.getCellData(sheetName, "Wait",testCaseStepsCount );
		
		dynamicTextToBeReplaced = dynamicXpathVal;
		locatorIndexPosition = indexPos;
		String recordNo	 = currentSuiteXls.getCellData(sheetName,"RecordNo",testCaseStepsCount);
		String elementId	 = currentSuiteXls.getCellData(sheetName,"ElementId",testCaseStepsCount);
		String endPoint	 = currentSuiteXls.getCellData(sheetName,"EndPoint",testCaseStepsCount);
		String jiraId 		 = currentSuiteXls.getCellData(sheetName, "JiraID",testCaseStepsCount );
		
		/*if(!jiraId.trim().equals("")){
			totalJiraCount++;
		}*/
		
		/********************API Header ********************************/
		String method1 = currentSuiteXls.getCellData(sheetName, "Method",testCaseStepsCount );
		String resource1 = currentSuiteXls.getCellData(sheetName, "Resource",testCaseStepsCount );
		String parameters1 = currentSuiteXls.getCellData(sheetName, "Parameters",testCaseStepsCount );
		String header1 = currentSuiteXls.getCellData(sheetName, "Header",testCaseStepsCount );
		String dynamicDataParam1 = currentSuiteXls.getCellData(sheetName, "DynamicDataParam",testCaseStepsCount );
		String filePath2 = null;
		
		//System.out.println("TestStep"+tsid);
		APP_LOG.debug("currentKeyword :"+currentKeyword+" || tsid:"+tsid+" || tcid:"+tcid+" || object:"+object+" || data:"+data);
		System.out.println("currentKeyword :"+currentKeyword+" || tsid:"+tsid+" || tcid:"+tcid+" || object:"+object+" || data:"+data);
		
		//if(!data.equals("")){
			data = assignData(object, data);
		//}
		
		if(!dynamicXpathVal.equals("")){ //if we have to replace any dynamic value of OR
			//createObjectRepository(object, dynamicXpathVal);
		}
		String filePath = "";
		try{
				if(currentKeyword.equalsIgnoreCase("open")){//For Web only
					object = objMainScriptBean.getBrowserToOpen();
					//data = configurationsXlsMap.get(Constants.CONFIG_COL_DOMAIN_NAME)+data;
				}
				
				String[] args = {object, data, indexPositionofEdit};
				
				if(!method1.equals("")){
					method = method1;
				}
				
				if(!resource1.equals("")){
					resource = resource1;
				}
				
				if(!parameters1.equals("")){
					parameters = parameters1;
				}
				
				if(!header1.equals("")){
					header = header1;
				}
				
				if(!dynamicDataParam1.equals("")){
					dynamicDataParam = dynamicDataParam1;
				}
				
				if(endPoint.equals("")){
					endPoint = configurationsXlsMap.get(Constants.CONFIG_COL_DOMAIN_NAME);
				}
				
				
				try{
					if(currentKeyword.startsWith("api")){
							if(parameters.contains("<"+elementId+">")){
								parameters = parameters.replace("<"+elementId+">", assignData(elementId, data));
							}
							
							if(!runTimeValue.equalsIgnoreCase("") && data.contains("RUNTIME|")) {
					    		data = runTimeValue;
					    	}	
					}
					
					
						argsMap.put(KeywordConstant.ELEMENT_LOCATOR, object);
						argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, data);
						argsMap.put("data1", data1);
						argsMap.put(Constants.EXECUTION_ON_DEVICE, objMainScriptBean.getExecutionDevice());
						argsMap.put(Constants.COMPONENT_NAME, objMainScriptBean.getCurrentSuitName());
						argsMap.put("EndPoint", endPoint);
						argsMap.put("RecordNo", recordNo);
						argsMap.put("Method", method);
						argsMap.put("Resource", resource);
						argsMap.put("Parameters", parameters);
						argsMap.put("Header", header);
						argsMap.put("DynamicDataParam", dynamicDataParam);
						argsMap.put("ParentNode",parantNode);
						argsMap.put("indexPositionofEdit", indexPositionofEdit);
						argsMap.put("projectPath", projectPath);
						
						
						//try{
						PerformAction objPerformAction = new PerformAction();
						keywordResult = objPerformAction.execute(currentKeyword, argsMap);
					
						if(keywordResult.contains(Constants.PASS)){  
							 reportTestObj.log(LogStatus.PASS, tsid,testDescription);
						}else{
							 reportTestObj.log(LogStatus.FAIL, tsid,testDescription);
						}
						
					
						if(keywordResult.contains(Constants.FAIL) && !device.equals("api")){
							fileName=objMainScriptBean.getCurrentTestCaseId()+"_"+tsid+"_"+currentKeyword+".jpg";
							filePath = addScreenshot();

							filePath2 = takeScreenShot(objMainScriptBean.getReportBasePath(),fileName, browser, runOnMachine);
							reportTestObj.log(
									LogStatus.FAIL,
									testDescription
											+ reportTestObj.addScreenCapture(filePath));
					    }
						
		
				
					if(!waitParamStr.equals("")){
						int waitParam = Integer.parseInt(waitParamStr);
					}
					APP_LOG.debug(objMainScriptBean.getCurrentTestCaseId()+" "+keywordResult);
					reportObj.flush();
					reportObj.endTest(reportTestObj);
					System.out.println(keywordResult);
				}catch(Exception ex){
					keywordResult = Constants.FAIL;
					exceptionMsg = "InvocationTargetException";
					APP_LOG.debug("keyword execution Failed"+ex);
				}
			}catch(Exception e){
				APP_LOG.error("keyword: "+currentKeyword+" execution Failed || "+e.toString());
				keywordResult = Constants.FAIL;
				exceptionMsg = e.toString();
			}
							 
		reportUtil.addKeyword(testDescription, currentKeyword, keywordResult, filePath2, tsid, tcid,exceptionMsg);
		String endTime =  now("yyyy-MM-dd hh.mm.ss");;
//		ReportSqls.insertTestStepDetails(objMainScriptBean.getCurrentSuitName(), actualTcId,tsid,currentKeyword,  executionEnviroment, keywordResult, filePath+fileName,
//				exceptionMsg,failureErrorMessageCollector,now("yyyy-MM-dd"),startTime ,endTime,batchId, "Y", objMainScriptBean.getExecutionDevice(),testDescription,data);

		filePath = "";filePath2=null;
		failureErrorMessageCollector = "";
		return keywordResult;
	
	}
	
	
	public void reportHeader(){
		reportUtil = new ReportUtil();
		APP_LOG.info("Method : reportHeader => start");
		File reportFolder = new File(objMainScriptBean.getReportBasePath());
		
		try{
			if(!reportFolder.exists()){
				if(!reportFolder.mkdirs()){
					APP_LOG.debug("Report folder not able to create");
				}
			}
			
			String reportStartTime = objMainScriptBean.getExecutionStartTime();
			reportUtil.startTesting(objMainScriptBean.getReportBasePath()+File.separator+"index_"+reportStartTime+".html",reportStartTime ,
					objMainScriptBean.getExutionEnvironment(), releaseVersion, objMainScriptBean.getExecutionDevice());
			APP_LOG.info("Method : reportHeader => end");

		}catch(Exception e){
				APP_LOG.info("Method : try catch => "+e);
		}
	}

	
	
   public static void main(String[] args) {
	  
	    DriverScript objDriverScript = new DriverScript();
	
	   	ArrayList arrLst = new ArrayList();
	   	/** For creation of jar, comment the init() function in baseclass **/
	    
//	   	args =new  String[]{"D:\\Framework\\Autofusion3.7\\SprintTestRepository","web","127.0.0.1","KeywordFiles","qa","chrome","keyword"};
		
//	    projectPath = args[0].trim();
//		System.out.println("projectPath"+projectPath);
//		browser = args[5];
//		String Testtype = args[6];
//		device = args[1];
//		releaseVersion ="";
//		executionEnviroment = args[4];
//		alm = "";
//	   	System.out.println(runOnMachine+"Before");
//		runOnMachine = args[2];
//		System.out.println(runOnMachine+"After");
////		if (null == APP_LOG) {
//			APP_LOG = InitClass.initializeLogger(projectPath, device);
//		}
//		if (configurationsXlsMap.isEmpty()) {
//			readEnvironmentConfigurationFile();
//		}

		InitClass.initializeExternalConfigFile(projectPath);

		mainMethodWebSingle(projectPath,device, runOnMachine, 
				suitename,arrLst, executionEnviroment, browser, testtype);
		/*if (System.getenv("RunOnMachine")!= null)
		{
		runOnMachine = System.getProperty("RunOnMachine");
		}
		else
		{	
		runOnMachine = PropertyManager.getInstance().getValueForKey("runOnMachine").trim();
		}*/
		sendMail = PropertyManager.getInstance().getValueForKey("sendMail");
		
	   //	mainMethodWebSingle(args[0], args[1], args[2], args[3],arrLst, args[4], args[5], args[6]);
   }

   public static void mainMethodWeb(String locationPath, String appToRun, String ip, String environment, String browserToRun) {
	   
	DriverScript objDriverScript = new DriverScript();
		
	try{
			
				/**** Initializing base class variables **************/
				projectPath = locationPath;
				executionEnviroment = environment;
				browser = browserToRun;
				device = appToRun;
				runOnMachine = ip;
				
				/*********************************/
				//setRunningComponentName(suiteName);

		
			objDriverScript.setDbNotRequire(false);	
			objDriverScript.initializeWebScript(locationPath,appToRun, ip, environment,browser);
  			objDriverScript.executeTestSuites();
  			ReportSqls.insertAutomationSummary("",executionEnviroment, CommonUtility.now("yyyy-MM-dd"),batchId);
  			

  			if(appToRun.equals("android") || appToRun.equals("ios")){
  				//KeywordsNativeApp.tearDown();
  			}else{
	  	  			try {
	  	  				try {
	  	  					getDriver().quit();
	  	  				}catch(Exception e) {
	  	  					APP_LOG.debug("Browser is already closed");
		  	  			}
	  					//setDriver(null);
	  	  				reportObj.flush();
  	  				reportObj=null;
  	  			}catch(Exception e){
  	  				APP_LOG.debug("Method : Main => Exception = "+e+"Script Killed due to exception");
  	  			}
  				}
			}catch (Exception e) {
				APP_LOG.debug("Method : Main => Exception = "+e+"Script Killed due to exception");
			}
			APP_LOG.info("Method : Main => Automation Script End");
 			
	}
	
   /**
    * 
    * @param locationPath
    * @param appToRun
    * @param ip
    * @param suiteName
    * @param testCaseLst
    * @param environment
    * @param browser
    */
   
   
   public static void mainMethodWebSingle(String locationPath, String appToRun, String ip, String suiteName,
		   ArrayList testCaseLst, String environment, String browserToRun, String testType) {
		
		DriverScript objDriverScript = new DriverScript();
		
		try{
				
				/**** Initializing base class variables **************/
					projectPath = locationPath;
					executionEnviroment = environment;
					browser = browserToRun;
					device = appToRun;
					runOnMachine = ip;
					
				/*********************************/
			

			if(testType.equalsIgnoreCase("POM")){		
				CreateTestNgXml.createXmlSuite(locationPath,  appToRun,  ip,  suiteName,
	  				    testCaseLst,  environment,  browserToRun);
			}else{
				CreateTestNgXml.createarryLst(locationPath, appToRun,testCaseLst, suiteName);
				configurationsXlsMap = new HashMap<>();
				init();
				releaseVersion = configurationsXlsMap.get("RELEASE_VERSION");
				objDriverScript.setDbNotRequire(false);
				objDriverScript.initializeWebScript(locationPath,appToRun, ip, environment.trim(), browser);
				objDriverScript.executeSingleTestSuites(suiteName,testCaseLst, ip, false);
	  			//ReportSqls.insertAutomationSummary("",executionEnviroment, CommonUtility.now("yyyy-MM-dd"),batchId);
			}
			

	  			if(appToRun.equals("android") || appToRun.equals("ios")){
	  				//KeywordsNativeApp.tearDown();
	  			}else{
 	  	  			try {
 	  	  				try {
 	  	  					getDriver().quit();
 	  	  				}catch(Exception e) {
 	  	  					APP_LOG.debug("Browser is already closed");
 		  	  			}
  	  					//setDriver(null);
 	  	  				reportObj.flush();
	  	  				reportObj=null;
	  	  			objDriverScript.createSuiteReportToSend();
	  	  			}catch(Exception e){
	  	  				APP_LOG.debug("Method : Main => Exception = "+e+"Script Killed due to exception");
	  	  			}
	  			}
			}catch (Exception e) {
				
				e.printStackTrace();
				APP_LOG.debug("Method : Main => Exception = "+e+"Script Killed due to exception");
			}
			APP_LOG.info("Method : Main => Automation Script End");
		}
	    
   
   public String createObjectRepository(String object, String dynamicXpathVal){
	   HashMap objectMap = new HashMap();
	   if(orMap.containsKey(object)){
		   objectMap =orMap.get(object);
		}

	   Iterator itr = objectMap.entrySet().iterator();
		
		String[] mapObjectSplit = new String[2];
		while(itr.hasNext())
		{
		   Map.Entry pairs = (Map.Entry)itr.next();
		   String objectElement = pairs.getValue().toString();
		   
		   if(!objectElement.equals("")){
			   mapObjectSplit[0] = pairs.getKey().toString();
			   mapObjectSplit[1] = objectElement;
			   break;
		   }
		}
		
		if(mapObjectSplit[1].contains("<REPLACE_XPATH>")){
			mapObjectSplit[1] = mapObjectSplit[1].replace("<REPLACE_XPATH>", dynamicXpathVal);
		}
		if(mapObjectSplit[1].contains("REPLACE_XPATH")){
			mapObjectSplit[1] = mapObjectSplit[1].replace("REPLACE_XPATH", dynamicXpathVal);
		}
		
		/*if(mapObjectSplit[1].contains("INDEX_POSITION")){
			mapObjectSplit[1] = mapObjectSplit[1].replace("INDEX_POSITION", indexPos);
		}
		
		if(mapObjectSplit[1].contains("<DYNAMIC_TEXT>")){
			mapObjectSplit[1] = mapObjectSplit[1].replace("<DYNAMIC_TEXT>", data);
		}*/
		objectMap.clear();
		objectMap.put(mapObjectSplit[0], mapObjectSplit[01]);
		orMap.put(object, objectMap);
	   return object;
   }

	public AppiumDriver getAppDriver() {
		return appDriver;
	}
	
	public void setAppDriver(AppiumDriver appDriver) {
		this.appDriver = appDriver;
	}
	
	/*public WebDriver getWebDriver() {
		return webDriver;
	}*/
	
	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

   public void createSuiteReportToSend(){
	   		
	 //  String attachment_path=folderLocation+File.separator+"report.zip";
	   String attachment_name="ExecutionReport_"+ reportStartTime +".html";
		
	/*	try{
			Zip.zipFolder(objMainScriptBean.getReportBasePath(),attachment_path);	
		}catch(Exception e){
			System.out.println("Zip"+e);
		}*/
	
		//String fileName = objMainScriptBean.getReportBasePath()+"\\ExecutionReport_"+ reportStartTime +".html";
		String fileName = projectPath+"\\web\\report\\Browser\\Chrome\\"+reportStartTime+"\\ExecutionReport_"+ reportStartTime +".html";
		
		 
		  /*if(!configurationsXlsMap.containsKey("To")) {
			  return;
		  }*/
		
		   String to 		= configurationsXlsMap.get("To");
		   String cc 		= configurationsXlsMap.get("CC");
		   String bcc 		= configurationsXlsMap.get("BCC");
		   String subject 	= configurationsXlsMap.get("Subject");
		   String message 	= configurationsXlsMap.get("Message");
		   String smtpHost	= configurationsXlsMap.get("SmtpHost");
		   String smtpPort 	= configurationsXlsMap.get("SmtpPort");
		   String userName 	= configurationsXlsMap.get("UserName");
		   String userPass 	= configurationsXlsMap.get("Password");
			
		   
		   String[] toAdd  = CommonUtility.splitMailAddresses(to);
		   String[] ccAdd  = CommonUtility.splitMailAddresses(cc);
		   String[] bccAdd = CommonUtility.splitMailAddresses(bcc);
			
			APP_LOG.info(to+"-"+cc+"-"+bcc+"-"+subject+"-"+message);
	
			System.out.println("userName="+userName );
			
			boolean mailResult = SendMail.sendMail(userName, userPass, toAdd, ccAdd, bccAdd, subject, 
					message,fileName ,attachment_name,smtpHost,smtpPort, APP_LOG);
			if(mailResult)
				APP_LOG.info("Email Sent");
			else
				APP_LOG.info("Email Not Sent");
	   
	   return;
   }
   
   public boolean stopScript(File f){
	   String device = objMainScriptBean.getExecutionDevice();
	    getDriver().quit();
	    setWebDriver(null);
	    reportObj.flush();
		reportObj=null;
		
		if(device.equals("android")){
		 try {
				//KeywordsNativeApp.tearDown();
		 } catch (Exception e) {
			 APP_LOG.debug("Android Script Killed Failed");
		 } 

		  f.delete();
		  
		  APP_LOG.debug("Script Killed file deleted");
		  System.out.println("Android Script is Killed");
	   }
	   
	   return false;
   }
   
	public boolean isDbNotRequire() {
		return dbNotRequire;
	}

	public void setDbNotRequire(boolean dbNotRequire) {
		this.dbNotRequire = dbNotRequire;
	}

	/*public static Map<String, String> getEnviromentMap() {
		return enviromentMap;
	}

	public static void setEnviromentMap(Map<String, String> enviromentMap) {
		DriverScript.enviromentMap = enviromentMap;
	}*/
	 
	
	public void startExtentReport(String testString){
		reportTestObj = reportObj.startTest(testString);
	}
	public void endExtentReport(){
		reportObj.endTest(reportTestObj);
	    reportObj.flush();
	}
	
	public void tearDownExtentReport(){
		reportObj.endTest(reportTestObj);
	    reportObj.flush();
	}
	
	public static String takeScreenShot(String filePath, String fileName, String browser, String machine) {
		if(getDriver() == null){
			return "";
			}
		if(!(getDriver().getCurrentUrl().contains("CQSearch/queries/CQSearch")))
		{
		try {
				if(!machine.equals("localhost")){
					WebDriver webDriver = new Augmenter().augment( getDriver() );
				}
				File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64.FILE);
				filePath = filePath+"/"+Constants.SCREENSHOT_FOLDER+"/";

				File f = new File(filePath);
				if(!f.exists()){
					f.mkdirs();
				}

				FileUtils.copyFile(scrFile, new File(filePath+"/"+fileName),true); //new File(filePath)
				// Error_File_Path= filePath;

			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return filePath+"/"+fileName;
	}
	
}

