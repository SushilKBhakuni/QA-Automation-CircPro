package com.ncs.page;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_RunProcessing_Publishing extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	public CircPro_RunProcessing_Publishing(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_RunProcessing_Publishing.class);
	}
	
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////


	public CircPro_RunProcessing_Publishing navigateOverPublishing(){
		String result = PerformAction.execute(ELEMENT_LOCATOR,"publishing");
		BaseClass.logResultInReport(result, "Click On Publishing on Homepage",reportTestObj);
		return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
	}

	public CircPro_RunProcessing_Publishing click(){
		String result = PerformAction.execute(ACTION_CLICK,"publishing");
		BaseClass.logResultInReport(result, "Click On Publishing on Homepage",reportTestObj);
		return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
	}
	
	public CircPro_RunProcessing_Publishing clickforSelecting()
	
	{
	String result = PerformAction.execute(ACTION_CLICK,"selectButtonNewYorkTimes");
	result += PerformAction.execute(ACTION_CLICK,"selectButtonBarrons");
	BaseClass.logResultInReport(result, "Click on Various Publications button",reportTestObj);
	return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
}

	public CircPro_RunProcessing_Publishing clickforSelectingSingle()
	{
	String result = PerformAction.execute(ACTION_CLICK,"selectButtonOnlineE-Access");
	BaseClass.logResultInReport(result, "Click on OnlineE-Access button",reportTestObj);
	return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
}

	public CircPro_RunProcessing_Publishing publishButton(){
	String result = PerformAction.execute(ACTION_CLICK,"publishButton");
	BaseClass.logResultInReport(result, "Publishing the required Updates",reportTestObj);
	return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
}
	
	public CircPro_Login clickPublishAlert(){
		PerformAction.execute(ACTION_ACCEPT_ALERT,"ClickPublish");
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}

	public CircPro_RunProcessing_Publishing verifyTextDailyProcessing(String text, String locator){
	String result = PerformAction.execute(ACTION_VERIFY_TEXT, locator, text);
	BaseClass.logResultInReport(result, "Verify Daily Processing Started Successfully",reportTestObj);
	return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
	}

	public CircPro_RunProcessing_Publishing verifyTextMessageCompletion(){
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"messageCompletion");
	BaseClass.logResultInReport(result, "Verify Daily Processing Completed Successfully",reportTestObj);
	return new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOGS);
	}

	public CircPro_Distribution closeBrowserPost(){
	String result = PerformAction.execute(ACTION_CLICK_TERMINATE,"Close Browser");	
	BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
	return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}	
}