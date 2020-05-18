package com.test;

import java.io.IOException;
import java.util.HashMap;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.autofusion.BaseClass;
import com.autofusion.GetTestData;
import com.ncs.page.CircPro_MailLables;
import com.ncs.page.CircPro_ReportsBundleTopsManifests;


public class CircPro_ReportsBundleTopsManifestsTest extends BaseClass{
	  public CircPro_ReportsBundleTopsManifests bundleTopManifests_TestPageObj;
	  String firstname=null;
	  String lastname=null;
	  String sheetName = "XYZ";
	  String fileName="";
	  GetTestData dp = null;
	  HashMap<String, String> data = new HashMap<String, String>();
	  
	  
	  public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		 bundleTopManifests_TestPageObj = new CircPro_ReportsBundleTopsManifests(reportTestObj, APP_LOG);
 		  //BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		  BaseClass.loadPage(CircPro_MailLables.class);
		  /***********************************************************/
	  }
	  
	  @BeforeTest
	  public void initialize() {
		  dp = new GetTestData(fileName, sheetName);
		  try 
			{
				data = dp.getData();
			} 
			catch (IOException e) 
			{
				
			}
	  }
	  

	  	  	
	@Test
	public void reports_bundleTop() throws InterruptedException
		{
		reports_bundle_pdf();
		reports_bundle_browser();
		reports_bundle_csv();
		reports_bundle_text();
		}
		
	@Test
	public void reports_manifest_deliveryTruckLoading() throws InterruptedException
	{
		reports_manifest_deliveryTruckLoading_pdf();
		reports_manifest_deliveryTruckLoading_browser();
		reports_manifest_deliveryTruckLoading_csv();
		reports_manifest_deliveryTruckLoading_text();
		
	}
	
	@Test
	public void reports_manifest_deliveryTruck() throws InterruptedException{
		reports_manifest_deliveryTruck_pdf();
		reports_manifest_deliveryTruck_browser();
		reports_manifest_deliveryTruck_csv();
		reports_manifest_deliveryTruck_text();
	} 
	  
	  
	@Test  
	public void reports_manifest_deliveryTruckRelay() throws InterruptedException{
		reports_manifest_deliveryTruckRelay_pdf();
	}
	
	
	@Test
	public void reports_manifest_aamReport() throws InterruptedException{
		
		aam_reports_pdf();
		aam_reports_browser();
		aam_reports_csv();
		aam_reports_text();
	}

	@Test
	public void reports_CriteriaBasedListing() throws InterruptedException
		{
		
		reports_CriteriaBasedListing_pdf();
		reports_CriteriaBasedListing_browser();
		reports_CriteriaBasedListing_csv();
		reports_CriteriaBasedListing_text();
		}
	
	@Test
	public void reports_manifest_dailyCashDistributor() throws InterruptedException {
		reports_manifest_dailyCashDistributor_pdf();
		reports_manifest_dailyCashDistributor_browser();
		reports_manifest_dailyCashDistributor_csv();
		reports_manifest_dailyCashDistributor_text();
	
	}	
	
	@Test
	public void reports_NonSubListing() throws InterruptedException{
		reports_nonSublisting();
	}
	
	
/*************************************Test Methods for Bundle Tops*******************************/
	
	
	public void reports_bundle_pdf() throws InterruptedException{
		 
		 startReport("BundleTopManifests_pdf_circPro");
		 bundleTopManifests_TestPageObj.click();		 
		 bundleTopManifests_TestPageObj.clickBundleTopMessages();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUser();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserSavedSettings();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadPDF();
		 bundleTopManifests_TestPageObj.emailFileTo2(configurationsXlsMap.get("EmailTo"));
		 bundleTopManifests_TestPageObj.clickSendEmail12();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
	
	public void reports_bundle_browser() throws InterruptedException{
				 
		 startReport("BundleTopManifests_browser_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateBrowser();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.verifyText();
		 bundleTopManifests_TestPageObj.backButton();
	}		 
	
	public void reports_bundle_csv() throws InterruptedException{
				 
		startReport("BundleTopManifests_csv_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateCSV();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadCSV();
		 bundleTopManifests_TestPageObj.emailFileTo2(configurationsXlsMap.get("EmailTo"));
		 bundleTopManifests_TestPageObj.clickSendEmail12();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
		 
	}
	
	public void reports_bundle_text() throws InterruptedException{
				 
		startReport("BundleTopManifests_text_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateText();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadtext();
		 bundleTopManifests_TestPageObj.emailFileTo2(configurationsXlsMap.get("EmailTo"));
		 bundleTopManifests_TestPageObj.clickSendEmail12();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	
	}
	
/***********************Test Methods for Delivery Truck Loading Manifest**************************/
	
		public void reports_manifest_deliveryTruckLoading_pdf() throws InterruptedException{
			 
		 startReport("deliveryTruckLoadingManifest_pdf_circPro");
		 bundleTopManifests_TestPageObj.click();		 
		 bundleTopManifests_TestPageObj.clickDeliveryTruckLoadingManifest();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserdtlm();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserSavedSettingsdtlm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadPDF();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
	

	public void reports_manifest_deliveryTruckLoading_browser() throws InterruptedException{
				 
		startReport("deliveryTruckLoadingManifest_browser_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateBrowserdtlm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.verifyText1();
		 bundleTopManifests_TestPageObj.backButton();
	}		 
	

	public void reports_manifest_deliveryTruckLoading_csv() throws InterruptedException{
				 
		startReport("deliveryTruckLoadingManifest_csv_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateCSVdtlm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadCSV();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
	
	public void reports_manifest_deliveryTruckLoading_text() throws InterruptedException{
				 
		startReport("deliveryTruckLoadingManifest_text_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateTextdtlm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadtext();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}


/*********************************Test for Delivery Truck Manifest*****************************/
	
	
		public void reports_manifest_deliveryTruck_pdf() throws InterruptedException{
			 
		 startReport("deliveryTruckManifest_pdf_circPro"); 
		 bundleTopManifests_TestPageObj.click();		 
		 bundleTopManifests_TestPageObj.clickDeliveryTruckManifest();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserdtlm();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserSavedSettingsdtlm();	 
		 bundleTopManifests_TestPageObj.clickOnDropdownGeneratePDFdtm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadPDF();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
	
	public void reports_manifest_deliveryTruck_browser() throws InterruptedException{
				 
		 startReport("deliveryTruckManifest_browser_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateBrowserdtm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.verifyText2();
		 bundleTopManifests_TestPageObj.backButton();
	}		 
	
	public void reports_manifest_deliveryTruck_csv() throws InterruptedException{
				 
		 startReport("deliveryTruckManifest_csv_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateCSVdtm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadCSV();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
	
	public void reports_manifest_deliveryTruck_text() throws InterruptedException{
				 
		 startReport("deliveryTruckManifest_text_circPro");
		 bundleTopManifests_TestPageObj.clickOnDropdownGenerateTextdtm();
		 bundleTopManifests_TestPageObj.clickRunButton();
		 bundleTopManifests_TestPageObj.downloadtext();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	
	}

/************************Test Methods for Delivery Truck Relay Manifest******************************/
	
	
	
		public void reports_manifest_deliveryTruckRelay_pdf() throws InterruptedException{
			 
		 startReport("deliveryTruckRelayManifest_circPro"); 
		 bundleTopManifests_TestPageObj.click();		 
		 bundleTopManifests_TestPageObj.clickDeliveryTruckRelayManifest();
		 bundleTopManifests_TestPageObj.selectTrucks();
		 bundleTopManifests_TestPageObj.clickPrintButton();
		 bundleTopManifests_TestPageObj.downloadPDF();
		 bundleTopManifests_TestPageObj.emailFileTo3(configurationsXlsMap.get("EmailTo"));
		 bundleTopManifests_TestPageObj.clickSendEmail13();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
	}
		
		

/**************************Test Methods for Criteria Based Listing*******************************/
	
	
		public void reports_CriteriaBasedListing_pdf() throws InterruptedException{
 
				 startReport("CriteriaBasedListing_PDFReport");
				 bundleTopManifests_TestPageObj.clickonallReports();		 
				 bundleTopManifests_TestPageObj.clickCriteriaBasedListingandLabels(); 
				 bundleTopManifests_TestPageObj.SelectPrinter_PDF();
				 bundleTopManifests_TestPageObj.Select_Publications();
				 bundleTopManifests_TestPageObj.Select_Status();
				 bundleTopManifests_TestPageObj.Select_DayPattern();
				 bundleTopManifests_TestPageObj.clickRun_Button();
				 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
				 bundleTopManifests_TestPageObj.Clickemailfile();
				 bundleTopManifests_TestPageObj.verifyTextEmailSent();
				 bundleTopManifests_TestPageObj.backButton();
				 
			  }	
	
	public void reports_CriteriaBasedListing_browser() throws InterruptedException{
		 
		 startReport("CriteriaBasedListing_BrowserReport");
		 bundleTopManifests_TestPageObj.SelectPrinter_Browser();
		 bundleTopManifests_TestPageObj.clickRun_Button();
		 bundleTopManifests_TestPageObj.backButton();
	
	}
	
public void reports_CriteriaBasedListing_text() throws InterruptedException{
		
		 startReport("CriteriaBasedListing_textReport");
		 bundleTopManifests_TestPageObj.SelectPrinter_TEXT();
		 bundleTopManifests_TestPageObj.clickRun_Button();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
		 
	}
		 
	public void reports_CriteriaBasedListing_csv() throws InterruptedException{
		
		 startReport("CriteriaBasedListing_csvReport");
		 bundleTopManifests_TestPageObj.SelectPrinter_CSV();
		 bundleTopManifests_TestPageObj.clickRun_Button();
		 bundleTopManifests_TestPageObj.EnterEmail(configurationsXlsMap.get("Email"));
		 bundleTopManifests_TestPageObj.Clickemailfile();
		 bundleTopManifests_TestPageObj.verifyTextEmailSent();
		 bundleTopManifests_TestPageObj.backButton();
		 
	}
	
/*****************************Test Methods for AAM  reports*************************/
	
	public void aam_reports_pdf() throws InterruptedException{
		
		startReport("AAMreports_pdf");
		bundleTopManifests_TestPageObj.click();
		bundleTopManifests_TestPageObj.clickAAMReports();
		bundleTopManifests_TestPageObj.clickPublicationNewscycleMessenger();
		bundleTopManifests_TestPageObj.enterPublishDate(configurationsXlsMap.get("AamReportDate"));
		bundleTopManifests_TestPageObj.selectPrintDevicePdf();
		bundleTopManifests_TestPageObj.clickRunReport();	
		bundleTopManifests_TestPageObj.pdfReportMessage();
		bundleTopManifests_TestPageObj.emailFileTo(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail1();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backToAamReport();	
		
	}
	
	public void aam_reports_browser() throws InterruptedException{
		
		startReport("AAMreports_browser");
		bundleTopManifests_TestPageObj.enterPublishDate(configurationsXlsMap.get("AamReportDate"));
		bundleTopManifests_TestPageObj.selectPrintDeviceBrowser();
		bundleTopManifests_TestPageObj.clickRunReport();	
		bundleTopManifests_TestPageObj.browserReportMessage();
		bundleTopManifests_TestPageObj.backToAamReport();	
		
	}
	
	public void aam_reports_csv() throws InterruptedException{
		
		startReport("AAMreports_csv");
		bundleTopManifests_TestPageObj.enterPublishDate(configurationsXlsMap.get("AamReportDate"));
		bundleTopManifests_TestPageObj.selectPrintDeviceCSV();
		bundleTopManifests_TestPageObj.clickRunReport();	
		bundleTopManifests_TestPageObj.pdfReportMessage();
		bundleTopManifests_TestPageObj.emailFileTo(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail1();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backToAamReport();	
		
	}

	
	public void aam_reports_text() throws InterruptedException{
		
		startReport("AAMreports_text");
		bundleTopManifests_TestPageObj.enterPublishDate(configurationsXlsMap.get("AamReportDate"));
		bundleTopManifests_TestPageObj.selectPrintDeviceTxt();
		bundleTopManifests_TestPageObj.clickRunReport();
		bundleTopManifests_TestPageObj.pdfReportMessage();
		bundleTopManifests_TestPageObj.emailFileTo(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backToAamReport();	
	}

	/*********************************
	 * Test Methods for Daily Cash Distributor Manifest
	 ******************************/

	public void reports_manifest_dailyCashDistributor_pdf() throws InterruptedException {

		startReport("DailyCashDistributorManifest_Pdf");
		bundleTopManifests_TestPageObj.click();
		bundleTopManifests_TestPageObj.clickDailyCashDistributorManifest();
		bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserDistributor();
		bundleTopManifests_TestPageObj.clickOnDropdownSelectionUserSavedSettingsDistributor();
		bundleTopManifests_TestPageObj.clickRunButton();
		bundleTopManifests_TestPageObj.downloadPDF();
		bundleTopManifests_TestPageObj.emailFileTo1(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail11();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backButton();
	}

	public void reports_manifest_dailyCashDistributor_browser() throws InterruptedException {
		
		startReport("DailyCashDistributorManifest_Browser");
		bundleTopManifests_TestPageObj.clickOnDropdownGenerateBrowserCashDistributor();
		bundleTopManifests_TestPageObj.clickRunButton();
		bundleTopManifests_TestPageObj.verifyTextCashDistributor();
		bundleTopManifests_TestPageObj.backButton();
	}

	public void reports_manifest_dailyCashDistributor_csv() throws InterruptedException {

		startReport("DailyCashDistributorManifest_Csv");
		bundleTopManifests_TestPageObj.clickOnDropdownGenerateCSVCashDistributor();
		bundleTopManifests_TestPageObj.clickRunButton();
		bundleTopManifests_TestPageObj.downloadCSV();
		bundleTopManifests_TestPageObj.emailFileTo1(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail11();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backButton();
	}

	public void reports_manifest_dailyCashDistributor_text() throws InterruptedException {

		startReport("DailyCashDistributorManifest_Text");
		bundleTopManifests_TestPageObj.clickOnDropdownGenerateTextCashDistributor();
		bundleTopManifests_TestPageObj.clickRunButton();
		bundleTopManifests_TestPageObj.downloadtext();
		bundleTopManifests_TestPageObj.emailFileTo1(configurationsXlsMap.get("EmailTo"));
		bundleTopManifests_TestPageObj.clickSendEmail11();
		bundleTopManifests_TestPageObj.verifyTextEmailSent();
		bundleTopManifests_TestPageObj.backButton();
	
	}

	public void reports_manifest_dailyCashDistributor_SaveSettings() throws InterruptedException {

		bundleTopManifests_TestPageObj.clickNewButton();
		bundleTopManifests_TestPageObj.saveNewSettings("MessengerQ1");
		bundleTopManifests_TestPageObj.saveButton();
		bundleTopManifests_TestPageObj.saveButton();
		bundleTopManifests_TestPageObj.deleteButton();
		bundleTopManifests_TestPageObj.backButton();
	}
	
/***************************Test Methods for Subscriber - StartStops reports**************************/
	
	
	public void reports_nonSublisting() throws InterruptedException{
		 
		 startReport("NonSubListing_circPro");
		 bundleTopManifests_TestPageObj.click();		 
		 bundleTopManifests_TestPageObj.clickNonSubListing();
		 bundleTopManifests_TestPageObj.clickOnDropdownSelectionPublication();
		 bundleTopManifests_TestPageObj.clickPrintButtonNonSub();
		 Thread.sleep(15000);
		 bundleTopManifests_TestPageObj.clickGetPdfFile();
		 bundleTopManifests_TestPageObj.backButtonNonSub();
		 
		 bundleTopManifests_TestPageObj.selectMailLablesfromDropdown();
		 bundleTopManifests_TestPageObj.clickPrintButtonNonSub();
		 Thread.sleep(15000);
		 bundleTopManifests_TestPageObj.verifyTextNonSub();
		 bundleTopManifests_TestPageObj.backButtonNonSubMailLables();

		 bundleTopManifests_TestPageObj.selectTextfromDropdown();
		 bundleTopManifests_TestPageObj.clickPrintButtonNonSub();
		 Thread.sleep(15000);
		 bundleTopManifests_TestPageObj.clickGetTextFile();
		 bundleTopManifests_TestPageObj.backButtonNonSub();
	}
	
}