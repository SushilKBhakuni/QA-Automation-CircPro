package com.test;

import java.io.IOException;
import java.util.HashMap;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.autofusion.BaseClass;
import com.autofusion.GetTestData;
import com.ncs.page.CircPro_Routes;
import com.test.CircPro_SubscriptionTest;

public class CircPro_RoutesTest extends BaseClass {
	  public CircPro_Routes routes_TestPageObj;
	  public static CircPro_SubscriptionTest subTest;
	  
	  String firstname=null;
	  String sheetName = "XYZ";
	  String fileName="";
	  GetTestData dp = null;
	public static String zipCode = null;
	public static String lastName = null; 
	  
	  HashMap<String, String> data = new HashMap<String, String>();
	 
	  
	  public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		 routes_TestPageObj = new CircPro_Routes(reportTestObj, APP_LOG);
 		  //BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		  BaseClass.loadPage(CircPro_Routes.class);
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
	public void navigation_routes() throws InterruptedException{
		
		navigation_routes_getZipCode();
		navigation_routes_createRoute();
		navigation_routes_addRouteDistributor();
		navigation_routes_addRouteSubscription();
		
	}

		public void navigation_routes_getZipCode() throws InterruptedException{
			 
		 startReport("Routes_circPro");
		 
		 routes_TestPageObj.click();		 
		 String lastName = routes_TestPageObj.getFields();
		 routes_TestPageObj.searchSubscriberName(lastName);
		 routes_TestPageObj.clickOnSearchButton();
		 routes_TestPageObj.clickOnAddressLink();
		 routes_TestPageObj.navigateToZipCodeTag();
		 routes_TestPageObj.clickOnSaveandBackButton();
		 routes_TestPageObj.clickOnRouter();
		}
		 
		 public void navigation_routes_createRoute() throws InterruptedException{
				
		 routes_TestPageObj.byZipCode();
		 routes_TestPageObj.enterZipCode();
		 routes_TestPageObj.clickSearchButton();
		// routes_TestPageObj.deleteZip();
		// routes_TestPageObj.alerthandle();
		 routes_TestPageObj.addZip();
		 routes_TestPageObj.addRouteButton();
		 routes_TestPageObj.addRouteName();
		 routes_TestPageObj.auditCodeDropDownValues();
		 routes_TestPageObj.selectDaysOfWeek();
		 routes_TestPageObj.saveRoutes();
		 routes_TestPageObj.dataSaved("Data saved.","dataSaved");
		 }
		 
		 public void navigation_routes_addRouteDistributor() throws InterruptedException{
				
		 routes_TestPageObj.navigateDistributorSearch();
		 routes_TestPageObj.selectDropdownFilter();		
		 routes_TestPageObj.distributorSearch("A");	
		 routes_TestPageObj.clickDistributorSearch();
		 routes_TestPageObj.selectuserDistributor();
		 routes_TestPageObj.addDistribution();
		 routes_TestPageObj.selectDistributionTypeDropdown();
		 routes_TestPageObj.addRouteNumber();
		 routes_TestPageObj.saveButton();
		 }
		 
		 public void navigation_routes_addRouteSubscription() throws InterruptedException{
				
		 routes_TestPageObj.click();	
		 String lastName = routes_TestPageObj.getFields();
		 routes_TestPageObj.searchSubscriberName(lastName);
		 routes_TestPageObj.clickOnSearchButton();
		 routes_TestPageObj.clickOnSubscriptionLink();
		 routes_TestPageObj.clickOnAddSubscription();
		 routes_TestPageObj.alerthandle();
		 routes_TestPageObj.selectPayType();
		 routes_TestPageObj.clickSaveandBack();
//		 routes_TestPageObj.clickOnUseasTyped();
//		 routes_TestPageObj.clickOnUseOkButton();
//		 routes_TestPageObj.clickOnUseOkButton1();
		 
		  }	
		 
}
