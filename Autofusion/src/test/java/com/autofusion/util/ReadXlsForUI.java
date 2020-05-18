package com.autofusion.util;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.autofusion.bean.AndroidCommon;
import com.autofusion.constants.Constants;

@SuppressWarnings({"rawtypes","unused","all"})
public class ReadXlsForUI {
	
	public static Properties CONFIG;
    String xlsSheet = "Sheet1";
    String fileExtention = ".xlsx";

   
    /*public void initialize() throws IOException{
		InputStream input = getClass().getResourceAsStream("/com/common/config/config.properties");
	//	FileInputStream fileConfig = new FileInputStream(System.getProperty("user.dir")+"//workspace//qa_automation//src//com//qa//config//config.properties");
		CONFIG = new Properties();
		CONFIG.load(input);
	}*/
	
	public ArrayList readSuitXls(String testCaseBasePath, String device){
		//APP_LOG.debug("Logger Initiated readSuitXls"+testCaseBasePath);
		ArrayList<ArrayList<String>> suitList = new ArrayList<ArrayList<String>>();
		try{
			//this.initialize();
			Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+device+File.separator+Constants.SUIT_FILE_NAME);
			//APP_LOG.debug("Logger Initiated"+testCaseBasePath);
			String testSheetName = "";
			if(device.equalsIgnoreCase("window")){
				  testSheetName = 	Constants.TEST_WIN_SUITE_SHEET;
			}else if(device.equalsIgnoreCase("Web")){
				  testSheetName = 	Constants.TEST_SUITE_SHEET;
			}else if(device.equalsIgnoreCase("Mobile")){
				  testSheetName = 	Constants.TEST_SUITE_SHEET;
			}else {
				  testSheetName = 	Constants.TEST_SUITE_SHEET;
			}
			
			for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(testSheetName); suiteCount++){
				
				ArrayList<String> suitListDetail = new ArrayList<String>();
				if(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_TSID, suiteCount).equals("") 
					|| suiteXls.getCellData(testSheetName, device, suiteCount).equals("N"))
					continue;
				
				
				suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_TSID, suiteCount));
				suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_DESCRIPTION, suiteCount));
				suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_RUNMODE, suiteCount));
				
				if(device.equalsIgnoreCase("web")){
					suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_IE, suiteCount));
					suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_FF, suiteCount));
					suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_CHROME, suiteCount));
				}else if(device.equalsIgnoreCase("mobile")){
					suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_ANDROID_BWR, suiteCount));
					suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_IOS_BWR, suiteCount));
				}
 				
				suitListDetail.add(suiteXls.getCellData(testSheetName, Constants.COL_HEAD_EXECUTION_STATUS, suiteCount));
			
				suitList.add(suitListDetail);
			}
			
			return suitList;
			
		 //}//catch (FileNotFoundException e) {
			 
			// return suitList;
		}catch (Exception e) {
			//APP_LOG.debug("Logger Initiated readSuitXls"+e); 
			 
			 return suitList;
		}	
	}
	
	public ArrayList<ArrayList<String>> readTestCaseXls(String testCaseBasePath,String testCaseSheet){
		ArrayList<ArrayList<String>> suitList = new ArrayList<ArrayList<String>>();
		//APP_LOG.debug("Logger Initiated readTestCaseXls"+testCaseBasePath+testCaseSheet);
		try{
				File file = new File(testCaseBasePath+File.separator+"TestCases"+File.separator+testCaseSheet+fileExtention);
				//APP_LOG.debug("Logger Initiated"+testCaseBasePath+testCaseSheet);
				if(!file.exists()){
					return suitList;
				}
				Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+"TestCases"+File.separator+testCaseSheet+fileExtention);
				
				for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_CASES_SHEET); suiteCount++){
				
					ArrayList<String> testCaseDetailList = new ArrayList<String>();
					
					if(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, suiteCount).equals(""))
						continue;
					
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DESCRIPTION, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, suiteCount));

					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_PACKAGE, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_CLASS_NAME, suiteCount));
		
					suitList.add(testCaseDetailList);
				}
				
			}catch(Exception e){
				//APP_LOG.debug("Logger Initiated readTestCaseXls"+e);
				System.out.println("File Not found");
		    }
		
		
		return suitList;
	}
	
	
	public ArrayList<ArrayList<String>> readTestStepsXls(String testCaseBasePath, String testSuit, String testCaseId){
		
		ArrayList<ArrayList<String>> testCaseName = new ArrayList<ArrayList<String>>();
		
		try{
			File file = new File(testCaseBasePath+File.separator+"TestCases"+File.separator+testSuit+fileExtention);

			if(!file.exists()){
				return testCaseName;
			}
			
			Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+"TestCases"+File.separator+testSuit+fileExtention);
			
			for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_STEPS); suiteCount++){
				
				String tCId = suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount);
				
				if(testCaseId.equalsIgnoreCase(tCId)){
					ArrayList<String> testCaseDetailList = new ArrayList<String>();
				
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_STEP_DESCRIPTION, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_KEYWORD, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount));
					if(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount).equals("Login_Password") || 
							suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount).equals("Sign_Password")){
						testCaseDetailList.add("*****");	
					}else{
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DATA, suiteCount));
					}
					
					testCaseName.add(testCaseDetailList);	
				}
				
				
			}
		}catch(Exception e){
			System.out.println("File Not found");
		}
		
		return testCaseName;
	}
	
	
	public static void main(String[] args) throws IOException {
		//ReadXlsForUI r = new ReadXlsForUI();
		//r.readSuitXls();
	}
	
	public int test(){
		return 10;
	}
	
	
	public ArrayList readConfigIdeData(String testCaseBasePath) throws IOException{
		 
		Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+"ide"+File.separator+Constants.IDE_FILE_NAME);
		ArrayList<ArrayList<String>> suitList = new ArrayList<ArrayList<String>>();
		ArrayList l = checkExistanceTests(testCaseBasePath);
		
		for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(xlsSheet); suiteCount++){
			
			if(suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_RUNMODE, suiteCount).equalsIgnoreCase("N"))
				 continue;

			ArrayList<String> suitListDetail = new ArrayList<String>();
			
			String newSuitName = suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_IDE_FILE_NAME, suiteCount);
			String flagNew     = suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_CREATE_NEW_SUIT, suiteCount);
			
			//TODO show duplicate suit in jsp with alert message
			if(l.contains(newSuitName) && flagNew.equalsIgnoreCase("Y")){//skip duplicate suits
				continue;
			}
			
			suitListDetail.add(newSuitName);
			suitListDetail.add(flagNew);
			suitListDetail.add(suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_CREATE_NEW_TEST_CASE, suiteCount));
			suitListDetail.add(suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_APPEND_IN_TEST_CASE, suiteCount));
			suitListDetail.add(suiteXls.getCellData(xlsSheet, Constants.COL_HEAD_DESCRIPTION, suiteCount));
							
			
			suitList.add(suitListDetail);
		}
		
		return suitList;
	}
	
	
	public ArrayList checkExistanceTests(String testCaseBasePath){
		
		Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+Constants.SUIT_FILE_NAME);
		ArrayList  list = new ArrayList();
		
		for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++){
			String suit = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_TSID, suiteCount);
			list.add(suit);
		}
		
		return list;
		
	}
	 public String getMachineNameSuitXls(String testCaseBasePath, String device){

		String machineDetail ="";
		String envDetail ="";
		Map<String,String> deviceName= new HashMap<String,String>();

		 if(device.equalsIgnoreCase("Mobile")){
			 deviceName = getDeviceModel(testCaseBasePath);
			if(deviceName.isEmpty()) {
				machineDetail+="<option value=''>Device not attached</option>";
			}else {
				Iterator it = deviceName.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println(pair.getKey() + " = " + pair.getValue());
			        machineDetail+="<option value='"+pair.getKey()+"'>"+ pair.getValue()+"</option>";
			    }    
			
			}
		 }else {
			 
		 	Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+"Config.xlsx");
			 
			for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount("UIConfigration"); suiteCount++){
			 
				String data = suiteXls.getCellData("UIConfigration", "MachineName", suiteCount).trim();
				String dataDesc = suiteXls.getCellData("UIConfigration", "MachineDescription", suiteCount).trim();
				
				
				String dataEnv = suiteXls.getCellData("EnviromentConfig", "Enviroment", suiteCount).trim();
				String dataEnvDesc = suiteXls.getCellData("EnviromentConfig", "Enviroment", suiteCount).trim();

				if(!data.equals("")){
					if(dataDesc.equals("")){
						dataDesc = data;
					}
					if(data.equalsIgnoreCase("localhost") || data.equalsIgnoreCase("127.0.0.1"))
						machineDetail+="<option value='"+data+"' selected>"+dataDesc+"</option>";
					else
						machineDetail+="<option value='"+data+"' >"+dataDesc+"</option>";
				}
				
				if(!dataEnv.equals("")){
				   if(dataEnvDesc.equals("")){
						dataEnvDesc = data;
				   }
				   envDetail+="<option value='"+dataEnv+"' >"+dataEnvDesc+"</option>";
				}
				
				
			}
		 }			
			return machineDetail;//+"##"+envDetail;
	 }
	 public String getEnvSuitXls(String testCaseBasePath, String device){
		 
		  	Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+File.separator+"Config.xlsx");
			 
			String machineDetail ="";
			String envDetail ="";

			for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount("EnviromentConfig"); suiteCount++){
			    String deviceXls = suiteXls.getCellData("EnviromentConfig", "Device", suiteCount).trim();
				
			    if(deviceXls.equalsIgnoreCase(device)){
				    String dataEnv = suiteXls.getCellData("EnviromentConfig", "Enviroment", suiteCount).trim();
				    String dataEnvDesc = suiteXls.getCellData("EnviromentConfig", "Enviroment", suiteCount).trim();
	
					if(!dataEnv.equals("")){
					   if(dataEnvDesc.equals("")){
							dataEnvDesc = dataEnv;
					   }
					   envDetail+="<option value='"+dataEnv+"' >"+dataEnvDesc+"</option>";
					}
			    }
			}
					
			
			return envDetail;
	 }
	 public static Map getDeviceModel(String testCaseBasePath){
		 String comm1 = AndroidCommon.executeCommand("cmd /c adb devices");
		 Map<String,String> deviceIds = new HashMap<String,String>();

		 String str[] = comm1.split("\n");

		 for(String s: Arrays.copyOfRange(str, 1, str.length)) {
			 deviceIds.put(s.split("\t")[0],AndroidCommon.executeCommand("cmd /c adb -s "+s.split("\t")[0]+" shell getprop ro.product.model"));
		 }
		 return deviceIds;
	 }
}



