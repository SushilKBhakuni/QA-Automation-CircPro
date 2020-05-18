package com.test;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.autofusion.GetTestData;
import com.ncs.page.CircPro_Login;

public class CircPro_LoginTest extends BaseClass{
	  public CircPro_Login login_TestPageObj ;
	  String firstname=null;
	  String lastname=null;
	  String sheetName = "XYZ";
	  String fileName="";
	  GetTestData dp = null;
	  HashMap<String, String> data = new HashMap<String, String>();
	  
	  
	  public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		 login_TestPageObj = new CircPro_Login(reportTestObj, APP_LOG);
 		  //BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		  BaseClass.loadPage(CircPro_Login.class);
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
	public void invalid_loginblank() throws InterruptedException{
			 
			 startReport("Invalidlogin_Blank_circPro");
			 login_TestPageObj.enterUsername("");
			 login_TestPageObj.enterPassword("");
			 login_TestPageObj.clickOnLogin();
			 login_TestPageObj.VerifyErrorMessages("Incorrect log in.", "errorMessage1");
			  
		  }	

	  
	@Test
	public void invalid_login() throws InterruptedException{
		 
		 startReport("Invalidlogin_circPro");
		 login_TestPageObj.enterUsername(configurationsXlsMap.get("invalidusername"));
		 login_TestPageObj.enterPassword(configurationsXlsMap.get("invalidpassword"));
		 login_TestPageObj.clickOnLogin();
		 login_TestPageObj.VerifyErrorMessages("Incorrect log in.", "errorMessage1");
		  
	  }	
	@Test
	public void login_circPro() throws InterruptedException{
	     startReport("validlogin_circPro");
	     login_TestPageObj.enterUsername(configurationsXlsMap.get("username"));
	     login_TestPageObj.enterPassword(configurationsXlsMap.get("password"));
	     login_TestPageObj.clickOnLogin();
	     login_TestPageObj.clickterminate();
	     login_TestPageObj.VerifyPageLoad();
     }
}
