package com.test;

import java.io.IOException;
import java.util.HashMap;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.autofusion.BaseClass;
import com.autofusion.GetTestData;
import com.ncs.page.CircPro_MailLables;


public class CircPro_MailLablesTest extends BaseClass{
	  public CircPro_MailLables mailLables_TestPageObj;
	  String firstname=null;
	  String lastname=null;
	  String sheetName = "XYZ";
	  String fileName="";
	  GetTestData dp = null;
	  HashMap<String, String> data = new HashMap<String, String>();
	  
	  
	  public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		 mailLables_TestPageObj = new CircPro_MailLables(reportTestObj, APP_LOG);
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
	public void navigation_mailLables() throws InterruptedException{
		mailLables_circpro();
	}
	
	public void mailLables_circpro() throws InterruptedException
	
	{
		 startReport("MailLables_circPro");
		 mailLables_TestPageObj.click();	
		 mailLables_TestPageObj.clickOnDropdownSelection();
		 mailLables_TestPageObj.clickExportToFileBox();
		 mailLables_TestPageObj.moveToExportSection();
		 mailLables_TestPageObj.clickOnTypeDropdownSelection();
		 mailLables_TestPageObj.selectCategoriesAddLine2();
		 mailLables_TestPageObj.moveAddressLine2();
		 mailLables_TestPageObj.selectCategories();
		 mailLables_TestPageObj.moveAddressLine2();
		 mailLables_TestPageObj.selectedCategoriesLine2();
		 mailLables_TestPageObj.moveFromSelected();
		 mailLables_TestPageObj.printButton();
		 mailLables_TestPageObj.verifyText();
		 mailLables_TestPageObj.downloadPDF();
		 mailLables_TestPageObj.clickBackButton();
		 mailLables_TestPageObj.downloadCSVFile();
		 		   
	  }	
}
