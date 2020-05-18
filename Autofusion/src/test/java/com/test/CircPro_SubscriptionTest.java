package com.test;

import org.testng.annotations.Test;
import com.autofusion.BaseClass;
import com.ncs.page.CircPro_Subscription;

public class CircPro_SubscriptionTest extends BaseClass{
	  public CircPro_Subscription subscriptionTestPageObj ;
	  
	  
	  String firstname=null;
	  public static String lastname = "K";
	  public static String zipCode = null;
	  public void startReport(String testDesc){
	    
		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		  subscriptionTestPageObj = new CircPro_Subscription(reportTestObj, APP_LOG);
 		  //BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		  BaseClass.loadPage(CircPro_Subscription.class);
		  /***********************************************************/
		  
		 
	  }
	 
	@Test
	public void testMethod() throws InterruptedException {
		newSubscription_circPro(); //VivekAgarwal-TC_2
		searchSubscription_circpro(); //AmeyaSinha-TC_3
		paymentsSubscripton_circpro(); //Anirudh-TC_4
	}
	 
	public void newSubscription_circPro() throws InterruptedException{
		 startReport("New_Subscription_CircPro");
		 subscriptionTestPageObj.clickOnNew();
		 firstname="AUTOMATION"+generateRandomString(6)+generateRandomNumber(4);
		 subscriptionTestPageObj.enterFirstName(firstname);
		 lastname="TESTER"+generateRandomString(6)+generateRandomNumber(4);
		 zipCode ="" + subscriptionTestPageObj.generateLocalZipCode();
		
		 subscriptionTestPageObj.enterLastName(lastname);
		 subscriptionTestPageObj.enterEmail(configurationsXlsMap.get("Email"));
		 subscriptionTestPageObj.enterStreetNumber(configurationsXlsMap.get("StreetNumber"));
		 subscriptionTestPageObj.enterStreetName(configurationsXlsMap.get("StreetName"));
		 subscriptionTestPageObj.enterSuffix(configurationsXlsMap.get("Suffix"));
	
	//    subscriptionTestPageObj.enterZipCode(zipCode);
	   	  subscriptionTestPageObj.enterZipCode(configurationsXlsMap.get("ZipCode"));
		 
		 
		 subscriptionTestPageObj.enterCity(configurationsXlsMap.get("City"));
		 subscriptionTestPageObj.enterState(configurationsXlsMap.get("State"));
		 subscriptionTestPageObj.clickOnSaveandBack();
		 subscriptionTestPageObj.clickOnOK();
		 subscriptionTestPageObj.VerifyUserCreated();
		 subscriptionTestPageObj.VerifyUserName("(//font[text()='"+firstname+" "+lastname+"'])[1]");
		 //subscriptionTestPageObj.clickOnSatori();	
		 subscriptionTestPageObj.clickOnHome();
	}
	
	public void searchSubscription_circpro() throws InterruptedException{
		 startReport("Search_Subscription_CircPro");
		  subscriptionTestPageObj.clickOnSearchMenu();
		  subscriptionTestPageObj.enterSearch(lastname);
		  subscriptionTestPageObj.clickOnSearchButton();
		  subscriptionTestPageObj.clickOnSubscriptions();
		  subscriptionTestPageObj.clickOnAdd();
		  //subscriptionTestPageObj.acceptalert();
		  subscriptionTestPageObj.select_Del_Type();
		  subscriptionTestPageObj.select_Pay_Type();
		  subscriptionTestPageObj.select_Day_Pattern();
		  subscriptionTestPageObj.clickOnSaveAndBack();
		  subscriptionTestPageObj.VerifyUserName("(//font[text()='"+firstname+" "+lastname+"'])[1]");
		  subscriptionTestPageObj.VerifyPay();
		  subscriptionTestPageObj.clickOnHome();
		 
	}
	
	public void paymentsSubscripton_circpro() throws InterruptedException
	
	{
		startReport("Payments_Subscription_CircPro");	
		subscriptionTestPageObj.paymentsoption();
		subscriptionTestPageObj.paymenttype();
		subscriptionTestPageObj.Cash();
		subscriptionTestPageObj.Account(configurationsXlsMap.get("Account"));
		subscriptionTestPageObj.EnterPayment(configurationsXlsMap.get("EnterPayment"));
		subscriptionTestPageObj.NIE(configurationsXlsMap.get("NIE"));
		subscriptionTestPageObj.Discount(configurationsXlsMap.get("Discount"));
		subscriptionTestPageObj.Payfrom(configurationsXlsMap.get("Payfrom"));
		subscriptionTestPageObj.Save();
		subscriptionTestPageObj.clickOnHome();

	}
}