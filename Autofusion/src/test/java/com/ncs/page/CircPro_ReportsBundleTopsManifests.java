package com.ncs.page;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_ReportsBundleTopsManifests extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	public CircPro_ReportsBundleTopsManifests(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_ReportsBundleTopsManifests.class);
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Test Case Methods///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////


	public CircPro_ReportsBundleTopsManifests navigateOverAllReports(){
		String result = PerformAction.execute(ELEMENT_LOCATOR,"allReports");
		BaseClass.logResultInReport(result, "Highlight All Reports on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}

	public CircPro_ReportsBundleTopsManifests click(){
		String result = PerformAction.execute(ACTION_CLICK,"allReports");
		BaseClass.logResultInReport(result, "Click On All Reports on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	
	public CircPro_ReportsBundleTopsManifests clickBundleTopMessages(){
		String result = PerformAction.execute(ACTION_CLICK,"bundleTops");
		BaseClass.logResultInReport(result, "Click On Bundle Tops on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
		
	public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUser(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"selectionSettingsUser");
		BaseClass.logResultInReport(result, "Click on BETHS User name from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUserSavedSettings(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"selectionSettingsSavedPrevious");
		BaseClass.logResultInReport(result, "Click on previous saved setting from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickAll(){
		String result = PerformAction.execute(ACTION_CLICK,"selectAll");
		BaseClass.logResultInReport(result, "Click On Select All for Districts",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickRunButton(){
		String result = PerformAction.execute(ACTION_CLICK,"runButton");
		BaseClass.logResultInReport(result, "Click On Run button",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests downloadPDF(){
		String result = PerformAction.execute(ACTION_CLICK,"pdfLink");
		BaseClass.logResultInReport(result, "Downloading PDF Files",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests backButton(){
		String result = PerformAction.execute(ACTION_CLICK,"backButton");
		BaseClass.logResultInReport(result, "Click On Back button",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}

	public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateBrowser(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"reportFormatBrowser");
		BaseClass.logResultInReport(result, "Click on Report format Browser from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
    public CircPro_ReportsBundleTopsManifests verifyText(){
    	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"Message1");
    	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully",reportTestObj);
    	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    	}

    public CircPro_ReportsBundleTopsManifests verifyText1(){
    	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"Message2");
    	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully",reportTestObj);
    	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    	}

    public CircPro_ReportsBundleTopsManifests verifyText2(){
    	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"Message3");
    	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully",reportTestObj);
    	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    	}
    
    public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateCSV(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatCSV");
	BaseClass.logResultInReport(result, "Click on Report format CSV from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	
	}

	public CircPro_ReportsBundleTopsManifests downloadCSV(){
	String result = PerformAction.execute(ACTION_CLICK,"csvLink");
	BaseClass.logResultInReport(result, "Downloading CSV Files",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	
    public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateText(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatText");
	BaseClass.logResultInReport(result, "Click on Report format Text from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    }
	    	
	public CircPro_ReportsBundleTopsManifests downloadtext(){
	String result = PerformAction.execute(ACTION_CLICK,"textLink");
	BaseClass.logResultInReport(result, "Downloading TEXT File",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickDeliveryTruckLoadingManifest(){
		String result = PerformAction.execute(ACTION_CLICK,"deliveryTruckLoadingManifest");
		BaseClass.logResultInReport(result, "Click On Bundle Tops on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickDeliveryTruckManifest(){
		String result = PerformAction.execute(ACTION_CLICK,"deliveryTruckManifest");
		BaseClass.logResultInReport(result, "Click On Bundle Tops on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}

	public CircPro_ReportsBundleTopsManifests clickDeliveryTruckRelayManifest(){
		String result = PerformAction.execute(ACTION_CLICK,"deliveryTruckRelayManifest");
		BaseClass.logResultInReport(result, "Click On Bundle Tops on Homepage",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}

	public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUserdtlm(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"selectionSettingsUserdtlm");
		BaseClass.logResultInReport(result, "Click on BETHS User name from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUserSavedSettingsdtlm(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"selectionSettingsSavedPreviousdtlm");
		BaseClass.logResultInReport(result, "Click on previous saved setting from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateBrowserdtlm(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"reportFormatBrowserdtlm");
		BaseClass.logResultInReport(result, "Click on Report format Browser from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateCSVdtlm(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatCSVdtlm");
	BaseClass.logResultInReport(result, "Click on Report format CSV from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	
	}
	
    public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateTextdtlm(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatTextdtlm");
	BaseClass.logResultInReport(result, "Click on Report format Text from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    }
    
    public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateBrowserdtm(){ 
	    String result = PerformAction.execute(ACTION_CLICK,"reportFormatBrowserdtm");
		BaseClass.logResultInReport(result, "Click on Report format Browser from dropdown list",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateCSVdtm(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatCSVdtm");
	BaseClass.logResultInReport(result, "Click on Report format CSV from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
	
	}
	
    public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateTextdtm(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatTextdtm");
	BaseClass.logResultInReport(result, "Click on Report format Text from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    }
    

    public CircPro_ReportsBundleTopsManifests clickOnDropdownGeneratePDFdtm(){ 
	String result = PerformAction.execute(ACTION_CLICK,"reportFormatPDFdtm");
	BaseClass.logResultInReport(result, "Click on Report format PDF from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
    }
    
	public CircPro_ReportsBundleTopsManifests selectTrucks(){
	String result = PerformAction.execute(ACTION_CLICK,"TrucksToSelectZZZZ");
	result +=PerformAction.execute(ACTION_CLICK,"selected");
    result += PerformAction.execute(ACTION_CLICK,"TrucksToSelectTR02");
    result +=PerformAction.execute(ACTION_CLICK,"selected");
    result += PerformAction.execute(ACTION_CLICK,"TrucksToSelectZZYY");
    result +=PerformAction.execute(ACTION_CLICK,"selected");
	result += PerformAction.execute(ACTION_CLICK,"TrucksToSelectZZZY");
	result +=PerformAction.execute(ACTION_CLICK,"selected");
	
	BaseClass.logResultInReport(result, "Select Categories",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}    
    
	public CircPro_ReportsBundleTopsManifests clickPrintButton(){
		String result = PerformAction.execute(ACTION_CLICK,"printButton");
		BaseClass.logResultInReport(result, "Click On Print button",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
	
	public CircPro_ReportsBundleTopsManifests closeBrowserPost(){
	 String result = PerformAction.execute(ACTION_CLICK_TERMINATE,"Close Browser");	
	 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
	 return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}	

	public CircPro_Login enterEmailID(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"emailBox", strCaller);
		BaseClass.logResultInReport(result, "Enter username",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}
	
	public CircPro_ReportsBundleTopsManifests clickSendEmail(){
		String result = PerformAction.execute(ACTION_CLICK,"sendEmailButton");
		BaseClass.logResultInReport(result, "Click On Send Email button",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
	public CircPro_Login verifyEmailSendMessages(String text, String locator){
		String result = PerformAction.execute(ACTION_VERIFY_TEXT, locator, text);
		BaseClass.logResultInReport(result, "Verify Email Sent Message",reportTestObj);
		return new CircPro_Login(reportTestObj, APP_LOGS);
	}	

	public CircPro_ReportsBundleTopsManifests clickonAAM(){
		String result = PerformAction.execute(ACTION_CLICK,"printButton");
		BaseClass.logResultInReport(result, "Click On Print button",reportTestObj);
		return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Test Case Methods for ///////////////////////////////////////////////
///////////////////////////////////////Criteria Based Listing////////////////////////////////////////////////////////////////

public CircPro_ReportsBundleTopsManifests clickonallReports(){
String result = PerformAction.execute(ACTION_CLICK,"allReports");
BaseClass.logResultInReport(result, "Click On All Reports on Homepage",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests clickCriteriaBasedListingandLabels(){
String result = PerformAction.execute(ACTION_CLICK,"CriteriaBasedListingandLabels");
BaseClass.logResultInReport(result, "Click On Criteria Based Listing and Labels from Homepage",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

/* public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUser(){ 
String result = PerformAction.execute(ACTION_SELECT_DROPDOWN_VALUE,"selectionSettingsUser");
BaseClass.logResultInReport(result, "Select User name from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
} */

public CircPro_ReportsBundleTopsManifests ProccedWithUserSavedSettingsAsNONE(){ 
String result = PerformAction.execute(ACTION_CLICK,"None_WithoutSavedsettings");
BaseClass.logResultInReport(result, "Proceed with saved setting from dropdown list as None",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests createNEWuserSettings(){ 
String result = PerformAction.execute(ACTION_CLICK,"Newbutton");
BaseClass.logResultInReport(result, "Click on New button to save setting",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);

}

public CircPro_ReportsBundleTopsManifests SelectionUserSavedSettings(){ 
String result = PerformAction.execute(ACTION_CLICK,"SavedselectionSettings");
BaseClass.logResultInReport(result, "Click on previous saved setting from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests VerifySubscribersASdefault(){
String result = PerformAction.execute(ACTION_VERIFY_ISSELECTED,"Subscribers");
BaseClass.logResultInReport(result, "Verify that Subscribers is default selected",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests SelectNonSubscribers(){
String result = PerformAction.execute(ACTION_CLICK,"NonSubscribers");
BaseClass.logResultInReport(result, "Click On Subscribers and Non-Subscribers for NonSubscribers ",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);

}
public CircPro_ReportsBundleTopsManifests SelectPrinter_Browser(){ 
String result = PerformAction.execute(ACTION_CLICK,"Printer_Browser");
BaseClass.logResultInReport(result, "Click on Report format Browser from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Browser_Selected(){ 
String result = PerformAction.execute(ACTION_VERIFY_ISSELECTED,"Printer_Browser");
BaseClass.logResultInReport(result, "check that Browser is selected as an option",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);

}

public CircPro_ReportsBundleTopsManifests SelectPrinter_PDF(){ 
String result = PerformAction.execute(ACTION_CLICK,"Printer_PDF");
BaseClass.logResultInReport(result, "Click on Report format PDF from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);

}
public CircPro_ReportsBundleTopsManifests PDF_Selected(){ 
String result = PerformAction.execute(ACTION_VERIFY_ISSELECTED,"Printer_PDF");
BaseClass.logResultInReport(result, "check that PDF is selected as an option",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);


}
public CircPro_ReportsBundleTopsManifests SelectPrinter_CSV(){ 
String result = PerformAction.execute(ACTION_CLICK,"Printer_CSV");
BaseClass.logResultInReport(result, "Click on Report format CSV from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests SelectPrinter_TEXT(){ 
String result = PerformAction.execute(ACTION_CLICK ,"Printer_TEXT");
BaseClass.logResultInReport(result, "Click on Report format TEXT from dropdown list",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Select_Publications(){ 
String result = PerformAction.execute(ACTION_CLICK ,"Publications");
BaseClass.logResultInReport(result, "Select Messeneger from Publication options",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Select_Status(){
String result = PerformAction.execute(ACTION_CLICK,"Status");
BaseClass.logResultInReport(result, "Click On Status",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Select_PayType(){
String result = PerformAction.execute(ACTION_CLICK,"PayType");
BaseClass.logResultInReport(result, "Click On PayType",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Select_DayPattern(){
String result = PerformAction.execute(ACTION_CLICK,"DayPattern");
BaseClass.logResultInReport(result, "Click On DayPattern",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Select_Delivery(){
String result = PerformAction.execute(ACTION_CLICK,"Delivery");
BaseClass.logResultInReport(result, "Click On Delivery",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests SelectRenewal(){
String result = PerformAction.execute(ACTION_CLICK,"Renewal");
BaseClass.logResultInReport(result, "Click On Renewal",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests clickRun_Button(){
String result = PerformAction.execute(ACTION_CLICK,"Run_Button");
BaseClass.logResultInReport(result, "Click On Run button",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickPageRefresh(){
String result = PerformAction.execute(ACTION_CLICK,"ClickHere");
BaseClass.logResultInReport(result, "Click On CLick Here link",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests DownloadMessage(){
String result = PerformAction.execute(ACTION_VERIFY_TEXT_PRESENT,"verifyText");
BaseClass.logResultInReport(result, "The report was successfully printed to ",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests WAIT_FOR_ELEMENT_IS_VISIBLE(){
String result = PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE ,"DownloadIsVisible");
BaseClass.logResultInReport(result, "Wait for element Is Visible ",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests ClickDownload(){
String result = PerformAction.execute(ACTION_CLICK ,"DownloadIsVisible");
BaseClass.logResultInReport(result, "Click on Download Button",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests EnterEmail(String strCaller){
String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"Email",strCaller);
BaseClass.logResultInReport(result, "Enter Email",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests Email(){

String result = PerformAction.execute(ACTION_CLICK,"Email");
BaseClass.logResultInReport(result, "Click Enter email",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests Clickemailfile(){

String result = PerformAction.execute(ACTION_CLICK,"emailfile");
BaseClass.logResultInReport(result, "Click Enter email",reportTestObj);
return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickAAMReports(){
	String result = PerformAction.execute(ACTION_CLICK,"aamParagraph3");
	BaseClass.logResultInReport(result, "Click On AAm Paragraph3",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickPublicationNewscycleMessenger(){
	String result = PerformAction.execute(ACTION_CLICK,"publicationNewscycleMessenger");
	BaseClass.logResultInReport(result, "Click On AAm publicationNewscycleMessenger",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests enterPublishDate(String date){
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"publishDate", date);
	BaseClass.logResultInReport(result, "Enter publishDate",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests selectPrintDevicePdf(){
	String result = PerformAction.execute(ACTION_CLICK,"selectPrintDevicePdf");
	BaseClass.logResultInReport(result, "Enter selectPrintDevicePdf",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests selectPrintDeviceBrowser(){
	String result = PerformAction.execute(ACTION_CLICK,"selectPrintDeviceBrowser");
	BaseClass.logResultInReport(result, "Enter selectPrintDeviceBrowser",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests selectPrintDeviceCSV(){
	String result = PerformAction.execute(ACTION_CLICK,"selectPrintDeviceCSV");
	BaseClass.logResultInReport(result, "Enter selectPrintDeviceCSV",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests selectPrintDeviceTxt(){
	String result = PerformAction.execute(ACTION_CLICK,"selectPrintDeviceTxt");
	BaseClass.logResultInReport(result, "Enter selectPrintDeviceTxt",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}



public CircPro_ReportsBundleTopsManifests clickRunReport(){
	String result = PerformAction.execute(ACTION_CLICK,"runReport");
	BaseClass.logResultInReport(result, "Click On AAm runReport",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests pdfReportMessage(){
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"pdfReportMessage");
	BaseClass.logResultInReport(result, "Verify On AAm pdfReportMessage",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests browserReportMessage(){
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"browserReportMessage");
	BaseClass.logResultInReport(result, "Verify On AAm browserReportMessage",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests emailFileTo(String email){
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"enterEmailId", email);
	BaseClass.logResultInReport(result, "Click On AAm emailFileTo",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickSendEmail1(){
	String result = PerformAction.execute(ACTION_CLICK,"clickSendEmail");
	BaseClass.logResultInReport(result, "Click On clickSendEmail",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests emailFileTo1(String email){
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"enterEmailId1", email);
	BaseClass.logResultInReport(result, "Click On AAm emailFileTo",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickSendEmail11(){
	String result = PerformAction.execute(ACTION_CLICK,"clickSendEmail1");
	BaseClass.logResultInReport(result, "Click On clickSendEmail",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests emailFileTo2(String email){
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"enterEmailId2", email);
	BaseClass.logResultInReport(result, "Click On AAm emailFileTo",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickSendEmail12(){
	String result = PerformAction.execute(ACTION_CLICK,"clickSendEmail2");
	BaseClass.logResultInReport(result, "Click On clickSendEmail",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests emailFileTo3(String email){
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"enterEmailId3", email);
	BaseClass.logResultInReport(result, "Click On AAm emailFileTo",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickSendEmail13(){
	String result = PerformAction.execute(ACTION_CLICK,"clickSendEmail3");
	BaseClass.logResultInReport(result, "Click On clickSendEmail",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests backToAamReport(){
	String result = PerformAction.execute(ACTION_CLICK,"backToAamReport");
	BaseClass.logResultInReport(result, "Click On AAm backToAamReport",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickDailyCashDistributorManifest() {
	String result = PerformAction.execute(ACTION_CLICK, "dailyCashDistributorManifest");
	BaseClass.logResultInReport(result, "Click On Daily Cash Distributor on Homepage", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}


public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUserDistributor() {
	String result = PerformAction.execute(ACTION_CLICK, "selectionSettingsDistributor");
	BaseClass.logResultInReport(result, "Click on BETHS User name from dropdown list", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionUserSavedSettingsDistributor() {
	String result = PerformAction.execute(ACTION_CLICK, "selectionSettingsSavedPreviousDistributor");
	BaseClass.logResultInReport(result, "Click on previous saved setting from dropdown list", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateBrowserCashDistributor() {
	String result = PerformAction.execute(ACTION_CLICK, "reportFormatBrowserCashDistributor");
	BaseClass.logResultInReport(result, "Click on Report format Browser from dropdown list", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateCSVCashDistributor() {
	String result = PerformAction.execute(ACTION_CLICK, "reportFormatCSVCashDistributor");
	BaseClass.logResultInReport(result, "Click on Report format CSV from dropdown list", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);

}

public CircPro_ReportsBundleTopsManifests clickOnDropdownGenerateTextCashDistributor() {
	String result = PerformAction.execute(ACTION_CLICK, "reportFormatTextCashDistributor");
	BaseClass.logResultInReport(result, "Click on Report format Text from dropdown list", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests verifyTextCashDistributor() {
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, "CashDistributorMessage");
	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests verifyTextEmailSent() {
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, "EmailSentMessage");
	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests emailButton() {
	String result = PerformAction.execute(ACTION_CLICK, "emailButton");
	BaseClass.logResultInReport(result, "Click On Email button", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickNewButton() {
	String result = PerformAction.execute(ACTION_CLICK, "newButton");
	BaseClass.logResultInReport(result, "Click On New button", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_Login saveNewSettings (String inputString) {
	String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "saveSettings", inputString);
	BaseClass.logResultInReport(result, "Enter setting name", reportTestObj);
	return new CircPro_Login(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests saveButton() {
	String result = PerformAction.execute(ACTION_CLICK, "saveButton");
	BaseClass.logResultInReport(result, "Click On save button", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests deleteButton() {
	String result = PerformAction.execute(ACTION_CLICK, "deleteButton");
	BaseClass.logResultInReport(result, "Click On delete button", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

/*-------------------------------------------*/

public CircPro_ReportsBundleTopsManifests clickNonSubListing(){
	String result = PerformAction.execute(ACTION_CLICK,"nonSublisting");
	BaseClass.logResultInReport(result, "Click On Non Sublisting on Reports Homepage",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickOnDropdownSelectionPublication(){ 
    String result = PerformAction.execute(ACTION_CLICK,"dropdownPublicationName");
	BaseClass.logResultInReport(result, "Click on Publication name from dropdown list",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickPrintButtonNonSub(){ 
    String result = PerformAction.execute(ACTION_CLICK,"printButtonNonSub");
	BaseClass.logResultInReport(result, "Click on Print Button for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickGetPdfFile(){ 
    String result = PerformAction.execute(ACTION_CLICK,"clickGetPdf");
	BaseClass.logResultInReport(result, "Click on GetPdf Button for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests backButtonNonSub(){ 
    String result = PerformAction.execute(ACTION_CLICK,"clickBackButton");
	BaseClass.logResultInReport(result, "Click on Back Button for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests backButtonNonSubMailLables(){ 
    String result = PerformAction.execute(ACTION_CLICK,"clickBackButtonMailLables");
	BaseClass.logResultInReport(result, "Click on Back Button for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}
public CircPro_ReportsBundleTopsManifests selectMailLablesfromDropdown(){ 
    String result = PerformAction.execute(ACTION_CLICK,"dropdownPrintFormatMailLables");
	BaseClass.logResultInReport(result, "Select MailLables Format for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests selectTextfromDropdown(){ 
    String result = PerformAction.execute(ACTION_CLICK,"dropdownPrintFormatText");
	BaseClass.logResultInReport(result, "Select Text Format for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests clickGetTextFile(){ 
    String result = PerformAction.execute(ACTION_CLICK,"clickGetText");
	BaseClass.logResultInReport(result, "Click on GetPdf Button for Non Sub",reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

public CircPro_ReportsBundleTopsManifests verifyTextNonSub() {
	String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, "mailLablesVerify");
	BaseClass.logResultInReport(result, "Verify Browser Report Generated Successfully", reportTestObj);
	return new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOGS);
}

}