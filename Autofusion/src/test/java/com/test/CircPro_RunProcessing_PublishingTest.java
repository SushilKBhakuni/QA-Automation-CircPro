package com.test;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.autofusion.BaseClass;
import com.autofusion.GetTestData;
import com.ncs.page.CircPro_MailLables;
import com.ncs.page.CircPro_RunProcessing_Publishing;

    public class CircPro_RunProcessing_PublishingTest extends BaseClass{
	  public CircPro_RunProcessing_Publishing processingPublishing_TestPageObj;
	  String firstname=null;
	  String lastname=null;
	  String sheetName = "XYZ";
	  String fileName="";
	  GetTestData dp = null;
	  HashMap<String, String> data = new HashMap<String, String>();
	  
	  
	public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		 processingPublishing_TestPageObj = new CircPro_RunProcessing_Publishing(reportTestObj, APP_LOG);
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
	@Test(priority=6, enabled = true)		
	public void dailyPublicationTest() throws InterruptedException{
		dailyPublicationSingle();
		dailyPublicationMultiple();	
}
		

		public void dailyPublicationSingle() throws InterruptedException
		
		{
		 startReport("Publications_Single_circPro");
		
		 processingPublishing_TestPageObj.click();		 
		 processingPublishing_TestPageObj.clickforSelectingSingle();
	     processingPublishing_TestPageObj.publishButton();
	     processingPublishing_TestPageObj.clickPublishAlert();
	     Thread.sleep(35000);
	   //  processingPublishing_TestPageObj.verifyTextMessageCompletion(); 
	}  
    
    
    public void dailyPublicationMultiple() throws InterruptedException{
	 	  
	 	 startReport("Publications_Multiple_circPro");
		 processingPublishing_TestPageObj.click();		 
		 processingPublishing_TestPageObj.clickforSelecting();
	     processingPublishing_TestPageObj.publishButton();
	     processingPublishing_TestPageObj.clickPublishAlert();
	     Thread.sleep(35000);
	   //  processingPublishing_TestPageObj.verifyTextMessageCompletion(); 
	}	
}