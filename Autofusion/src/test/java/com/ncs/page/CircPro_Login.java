package com.ncs.page;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_Login extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	
	public CircPro_Login(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_Login.class);
	}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////


	public CircPro_Login enterUsername(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"validusername", strCaller);
		BaseClass.logResultInReport(result, "Enter username",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login enterPassword(String strCaller){
		
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"password", strCaller);
		BaseClass.logResultInReport(result, "Enter password",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login clickOnLogin(){
		String result = PerformAction.execute(ACTION_CLICK,"loginButton");
		BaseClass.logResultInReport(result, "Click on Login button",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login verifytext(){
		String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"verifyload");
		BaseClass.logResultInReport(result, "Verify HomePage Loaded successfully",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login clickterminate(){
		PerformAction.execute(ACTION_CLICK_TERMINATE,"Clickterminate");
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login VerifyPageLoad(){
		String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"verifyload");
		BaseClass.logResultInReport(result, "Verify HomePage Loaded successfully",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Login VerifyErrorMessages(String text, String locator){
		String result = PerformAction.execute(ACTION_VERIFY_TEXT, locator, text);
		BaseClass.logResultInReport(result, "Verify Error message",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
         public CircPro_Login closeBrowserPost(){
		 String result = PerformAction.execute(ACTION_CLOSE_BROWSER,"Close Browser");	
		 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
		 return new CircPro_Login(reportTestObj, APP_LOGS);
	}
}