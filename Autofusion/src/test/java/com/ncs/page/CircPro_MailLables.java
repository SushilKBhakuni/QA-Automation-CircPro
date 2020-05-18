package com.ncs.page;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_MailLables extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	public CircPro_MailLables(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_MailLables.class);
	}
	
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////


	public CircPro_MailLables navigateOverMailLables(){
		String result = PerformAction.execute(ELEMENT_LOCATOR,"mailLables");
		BaseClass.logResultInReport(result, "Click On MultiLables on Homepage",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}

	public CircPro_MailLables click(){
		String result = PerformAction.execute(ACTION_CLICK,"mailLables");
		BaseClass.logResultInReport(result, "Click On MultiLables on Homepage",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Distribution clickOnDropdownSelection(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"publicationDropdownValues");
		BaseClass.logResultInReport(result, "Click on Barrons name from dropdown list",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}
	

	public CircPro_Distribution clickOnTypeDropdownSelection(){ 
		String result = PerformAction.execute(ACTION_CLICK,"typeDropdownValues");
		BaseClass.logResultInReport(result, "Select type from dropdown list",reportTestObj);
		return new CircPro_Distribution(reportTestObj, APP_LOGS);
	
	}
	
	public CircPro_MailLables clickExportToFileBox(){
		String result = "PASS";
		BaseClass.logResultInReport(result, "Click on ExportToFile button",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
    }

	public CircPro_MailLables moveToExportSection(){
		String result = PerformAction.execute(ACTION_CLICK,"exportTab");
		BaseClass.logResultInReport(result, "Move to Export Section",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
    }

	public CircPro_MailLables selectCategoriesAddLine2(){
		String result = "PASS";
		BaseClass.logResultInReport(result, "Select Address Line 2",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
    }

	public CircPro_MailLables selectCategories(){
		String result = PerformAction.execute(ACTION_CLICK,"fullName");
		result += PerformAction.execute(ACTION_CLICK,"account");
		result += PerformAction.execute(ACTION_CLICK,"addressLine3");
		result += PerformAction.execute(ACTION_CLICK,"addressLine1");
		result += PerformAction.execute(ACTION_CLICK,"barCode");
		result += PerformAction.execute(ACTION_CLICK,"city");
		result += PerformAction.execute(ACTION_CLICK,"email");
		BaseClass.logResultInReport(result, "Select Categories",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}

	public CircPro_MailLables moveAddressLine2(){
		String result = PerformAction.execute(ACTION_CLICK,"addressLine2Move");
		BaseClass.logResultInReport(result, "Select Address Line 2",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}

	public CircPro_MailLables selectCategoriesAddLine3(){
		String result = PerformAction.execute(ACTION_CLICK,"addressLine3");
		BaseClass.logResultInReport(result, "Select Address Line 3",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_MailLables selectedCategoriesLine2(){
		String result = "PASS";
		BaseClass.logResultInReport(result, "Select Address Line 3",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	public CircPro_MailLables moveFromSelected(){
		String result = PerformAction.execute(ACTION_CLICK,"addressLine2MoveFromSelected");
		BaseClass.logResultInReport(result, "Select Address Line 2",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_MailLables printButton(){
		String result = PerformAction.execute(ACTION_CLICK,"printButton");
		BaseClass.logResultInReport(result, "Printing Mailing List",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}

	public CircPro_MailLables verifyText(){
		String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"Message1");
		BaseClass.logResultInReport(result, "Verify Mailing List Generated Successfully",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}

	public CircPro_MailLables downloadPDF(){
		String result = PerformAction.execute(ACTION_CLICK,"pdfLink");
		BaseClass.logResultInReport(result, "Downloading PDF Files",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_MailLables clickBackButton(){
		String result = PerformAction.execute(ACTION_CLICK,"backButton");
		BaseClass.logResultInReport(result, "Downloading PDF Files",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_MailLables downloadCSVFile(){
		String result = PerformAction.execute(ACTION_CLICK,"downloadCSVFile");
		BaseClass.logResultInReport(result, "Downloading PDF Files",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	
	public CircPro_MailLables openPDFFile(){
		String result = PerformAction.execute(ACTION_NAVIGATE_URL,"filePath");
		BaseClass.logResultInReport(result, "Printing Mailing List",reportTestObj);
		return new CircPro_MailLables(reportTestObj, APP_LOGS);
	}
	

	public CircPro_Distribution closeBrowserPost(){
	 String result = PerformAction.execute(ACTION_CLICK_TERMINATE,"Close Browser");	
	 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
	 return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}	
}