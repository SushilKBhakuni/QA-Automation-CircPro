package com.autofusion.ui;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xls_Reader;

@SuppressWarnings({"rawtypes","unused","all"})
public class ReadMblXlsForUI {

	public static Properties CONFIG;
    String xlsSheet = "Sheet1";
    String fileExtention = ".xls";
    
	public void initialize() throws IOException{
		InputStream input = getClass().getResourceAsStream("/com/evernote/config/config.properties");
	//	FileInputStream fileConfig = new FileInputStream(System.getProperty("user.dir")+"//workspace//qa_automation//src//com//qa//config//config.properties");
		CONFIG = new Properties();
		CONFIG.load(input);
	}
	
	public ArrayList readSuitXls(String testCaseBasePath, String device) throws IOException{
		
		//this.initialize();
		 
		//Xls_Reader suiteXls = new Xls_Reader(CONFIG.getProperty("xlsPath")+"suits.xlsx");
		Xls_Reader suiteXls;
		suiteXls = new Xls_Reader(testCaseBasePath+"/mobile/suits.xls");
			
		String testSheetName = "";
		if(device.equalsIgnoreCase("window")){
			  testSheetName = 	Constants.TEST_WIN_SUITE_SHEET;
		}else if(device.equalsIgnoreCase("Web")){
			  testSheetName = 	Constants.TEST_SUITE_SHEET;
		}else if(device.equalsIgnoreCase("Mobile")){
			  testSheetName = 	Constants.TEST_MBL_SUITE_SHEET;
		}else {
			  testSheetName = 	Constants.TEST_SUITE_SHEET;
		}
		
		
		ArrayList<ArrayList<String>> suitList = new ArrayList<ArrayList<String>>();
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
			}			
			
			
			suitList.add(suitListDetail);
		}
		
		return suitList;
	}
	
	public ArrayList<ArrayList<String>> readTestCaseXls(String testCaseBasePath,String testCaseSheet){
		ArrayList<ArrayList<String>> suitList = new ArrayList<ArrayList<String>>();
		
		try{
				//File file = new File(System.getProperty("user.dir")+CONFIG.getProperty("xlsPath")+testCaseSheet+".xlsx");
				File file = new File(testCaseBasePath+"/"+testCaseSheet+fileExtention);
			
				if(!file.exists()){
					return suitList;
				}
				
				//Xls_Reader suiteXls = new Xls_Reader(CONFIG.getProperty("xlsPath")+testCaseSheet+".xlsx");
				Xls_Reader suiteXls = new Xls_Reader(testCaseBasePath+"/"+testCaseSheet+fileExtention);
				
				for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_CASES_SHEET); suiteCount++){
				
					ArrayList<String> testCaseDetailList = new ArrayList<String>();
					
					if(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, suiteCount).equals(""))
						continue;
					
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DESCRIPTION, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, suiteCount));
					
					suitList.add(testCaseDetailList);
				}
				
			}catch(Exception e){
				System.out.println("File Not found");
		    }
		
		
		return suitList;
	}
	
	
	public ArrayList<ArrayList<String>> readTestStepsXls(String testCaseBasePath, String testSuit, String testCaseId){
		
		ArrayList<ArrayList<String>> testCaseName = new ArrayList<ArrayList<String>>();
		
		try{
			File file = new File(testCaseBasePath+"/"+testSuit+fileExtention);
			if(!file.exists()){
				return testCaseName;
			}
			
			Xls_Reader suiteXls = new Xls_Reader(testCaseBasePath+"/"+testSuit+fileExtention);
			
			for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_STEPS); suiteCount++){
				
				String tCId = suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount);
				
				if(testCaseId.equalsIgnoreCase(tCId)){
					ArrayList<String> testCaseDetailList = new ArrayList<String>();
				
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DESCRIPTION, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_KEYWORD, suiteCount));
					testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount));
					if(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount).equals("Sign_Password"))
						testCaseDetailList.add("****");
					else
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DATA, suiteCount));
					
					testCaseName.add(testCaseDetailList);	
				}
				
				
			}
		}catch(Exception e){
			System.out.println("File Not found");
		}
		
		return testCaseName;
	}
	
	
	public static void main(String[] args) throws IOException {
		ReadMblXlsForUI r = new ReadMblXlsForUI();
		//r.readSuitXls();
	}
	
	public int test(){
		return 10;
	}
	
	
	public ArrayList readConfigIdeData(String testCaseBasePath) throws IOException{
		 
		Xls_Reader suiteXls = new Xls_Reader(testCaseBasePath+"/ide/"+Constants.IDE_FILE_NAME);
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
		
		Xls_Reader suiteXls = new Xls_Reader(testCaseBasePath+"/"+Constants.SUIT_FILE_NAME);
		ArrayList  list = new ArrayList();
		
		for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++){
			String suit = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_TSID, suiteCount);
			list.add(suit);
		}
		
		return list;
		
	}
	
}



