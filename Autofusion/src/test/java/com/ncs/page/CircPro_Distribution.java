package com.ncs.page;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_Distribution extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	
	public CircPro_Distribution(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_Distribution.class);
	}
	
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CircPro_Distribution clickOnDistributionSearch(){
		String result = PerformAction.execute(ACTION_CLICK,"SearchButton1");
		BaseClass.logResultInReport(result, "Click on Search button",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}

	public CircPro_Distribution enterLnInDistributorSearch(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"validLastname", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter Last name in Distributor Search",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnAddCriteria(){
		String result = PerformAction.execute(ACTION_CLICK,"AddCriteria");
		BaseClass.logResultInReport(result, "Click on Add Criteria",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdown(){
		String result = PerformAction.execute(ACTION_CLICK,"Dropdown1");
		BaseClass.logResultInReport(result, "Click on Drop down list",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
		
	public CircPro_Distribution clickOnDropdownSelection(){
		String result = PerformAction.execute(ACTION_CLICK,"DropdownFN");
		BaseClass.logResultInReport(result, "Click on Firstname from dropdwon list",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
		
	
	public CircPro_Distribution enterFnInDistributorSearch(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"validfirstname", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter First name in Distributor Search",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnSearch(){
		String result = PerformAction.execute(ACTION_CLICK,"Search1");
		BaseClass.logResultInReport(result, "Click on Search button",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnHideInactives(){
		String result = PerformAction.execute(ACTION_CLICK,"HideInactivies");
		BaseClass.logResultInReport(result, "Click on HideInactivies",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdownP(){
		String result = PerformAction.execute(ACTION_CLICK,"DropdownP");
		BaseClass.logResultInReport(result, "Click on Dropdown link",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdownMessenger(){
		String result = PerformAction.execute(ACTION_CLICK,"DropdownMessenger");
		BaseClass.logResultInReport(result, "Click on Messenger from drop down",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnAdd_distribution(){
		String result = PerformAction.execute(ACTION_CLICK,"Add");
		BaseClass.logResultInReport(result, "Click on Add button",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdownD(){
		String result = PerformAction.execute(ACTION_CLICK,"DropdownD");
		BaseClass.logResultInReport(result, "Click on Drop down list",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdownMR(){
		String result = PerformAction.execute(ACTION_CLICK,"DropdownMR");
		BaseClass.logResultInReport(result, "Click on Motor Route",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution enterRN(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"validRN", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter Route number",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnHome(){
		String result = PerformAction.execute(ACTION_CLICK,"homeButton");
		BaseClass.logResultInReport(result, "Click on Home button",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnSave(){
		String result = PerformAction.execute(ACTION_CLICK,"Save");
		BaseClass.logResultInReport(result, "Click on Save button",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution closeBrowserPost(){
	 String result = PerformAction.execute(ACTION_CLOSE_BROWSER,"Close Browser");	
	 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
	 return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	
}