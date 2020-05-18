package com.ncs.page;

import java.util.HashMap;
import java.util.Random;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.autofusion.BaseClass;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


public class CircPro_Subscription extends BaseClass implements KeywordConstant{
	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	
	
	public CircPro_Subscription(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_Subscription.class);
	}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CircPro_Subscription clickOnNew() throws InterruptedException{
		String result = PerformAction.execute(ACTION_CLICK,"newSubscriber");
		BaseClass.logResultInReport(result, "Click on New button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public int generateLocalZipCode()
	{
		 Random rand = new Random();
		
		 int randomInt = rand.nextInt(89999) + 10000;
		 return randomInt;
	}
	
	public CircPro_Subscription enterFirstName(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"firstName", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter FirtName",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription enterLastName(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"lastName", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter LastName",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription enterEmail(String strCaller){
		
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"email", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter email",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription enterStreetName(String strCaller) {
		
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "streetName", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter StreetName", reportTestObj);
		return new CircPro_Subscription(reportTestObj,APP_LOGS);
		
    }
	
	 public CircPro_Subscription enterStreetNumber(String strCaller) {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "streetNumber", strCaller);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter StreetNumber", reportTestObj);
		 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	 
	 public CircPro_Subscription enterSuffix(String strCaller) {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "suffix", strCaller);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter Suffix", reportTestObj);
		 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	 }
	 
     public CircPro_Subscription enterZipCode(String strCaller) {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "zipCode", strCaller);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter ZipCode", reportTestObj);
		 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	 }
	 
     public CircPro_Subscription enterCity(String strCaller) {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "city", strCaller);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter City", reportTestObj);
		 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	 }
     
     public CircPro_Subscription enterState(String strCaller) {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "state", strCaller);
		 System.out.println(result);
		// BaseClass.logResultInReport(result, "Enter State", reportTestObj);
		 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	 }
     
	
	
	public CircPro_Subscription clickOnSaveandBack(){
		String result = PerformAction.execute(ACTION_CLICK,"saveandBack");
		BaseClass.logResultInReport(result, "Click on Save & Back button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnOK(){
		String result=PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"OKButton");
		if(result.contains("PASS"))
		PerformAction.execute(ACTION_CLICK,"OKButton");
		BaseClass.logResultInReport(result, "Click on OK",reportTestObj);
		result=PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"OKButton");
		if(result.contains("PASS"))
		PerformAction.execute(ACTION_CLICK,"OKButton");
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnSatori(){
		String result = PerformAction.execute(ACTION_CLICK,"satori");
		BaseClass.logResultInReport(result, "Click on Satori button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnHome(){
		String result = PerformAction.execute(ACTION_CLICK,"homeButton");
		BaseClass.logResultInReport(result, "Click on Home button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription VerifyUserCreated(){
		String result=PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"RecordFound");
		BaseClass.logResultInReport(result, "Record Found Successfully",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription VerifyUserName(String locator){
		String result = null;
		WebDriver webDriver = getDriver();
		boolean result1=webDriver.findElement(By.xpath(locator)).isDisplayed();
		if(result1) {
		result="PASS: Element on UI- 'UserName' is displayed";
		BaseClass.logResultInReport(result, "New User matches Successfully",reportTestObj);
		}
		else
		{
			
		}
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription VerifyPay(){
		String result=PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"VerifyDelTypeOption");
		BaseClass.logResultInReport(result, "Del Type matches Successfully",reportTestObj);
		result=PerformAction.execute(ACTION_VERIFY_ISDISPLAYED,"VerifyPayTypeOption");
		BaseClass.logResultInReport(result, "Pay Type matches Successfully",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public static String generateRandomStringWithPrefix( int size, String prefix) 
	{ 
		return prefix + generateRandomString(size); 
	} 
	
	public static String generateRandomString(int size) 
	{ 
		StringBuffer num = new StringBuffer(); 
		Random random = new Random(); 
		for (int i = 0; i < size; i++) 
		{ 
			num.append((char)(65 + (random.nextInt(100) %26))); 
			} 
		return num.toString(); 
		} 
	
	public static String generateRandomNumber(int digits) 
	{ 
		StringBuffer num = new StringBuffer();
		Random random = new Random(); 
		for (int i = 0; i < digits; i++) 
		{ 
			num.append(random.nextInt(9)); 
			} 
		return num.toString(); 
		} 
	
	public CircPro_Subscription clickOnSearchMenu(){
		String result = PerformAction.execute(ACTION_CLICK,"SearchMenu");
		BaseClass.logResultInReport(result, "Click on Search menu",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnSearchButton() throws InterruptedException{
		System.out.println("in search");
		String result = PerformAction.execute(ACTION_CLICK,"SearchButton");
		BaseClass.logResultInReport(result, "Click on Search button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnLogout(){
		String result = PerformAction.execute(ACTION_CLICK,"LogoutButton");
		BaseClass.logResultInReport(result, "Click on Logout button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription enterSearch(String strCaller){
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"SearchBox", strCaller);
		BaseClass.logResultInReport(result, "Enter subscriber search",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnSubscriptions(){
		String result = PerformAction.execute(ACTION_CLICK,"SubButton");
		BaseClass.logResultInReport(result, "Click on subscriptions button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription clickOnAdd() throws InterruptedException{
		String result = PerformAction.execute(ACTION_CLICK,"AddButton");
		BaseClass.logResultInReport(result, "Click on Add button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription select_Del_Type(){
		String result = PerformAction.execute(ACTION_SELECT_BY_VISIBLE_TEXT,"DelTypeSelect", "Digital");
		BaseClass.logResultInReport(result, "Click on Delivery type select",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription select_Pay_Type(){
		String result = PerformAction.execute(ACTION_SELECT_BY_VISIBLE_TEXT,"PayTypeSelect","PIO");
		BaseClass.logResultInReport(result, "Click on Pay type select",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription select_Day_Pattern(){
		String result = PerformAction.execute(ACTION_SELECT_BY_VISIBLE_TEXT,"DayPatternSelect","Non-Weekly");
		BaseClass.logResultInReport(result, "Click on Day Pattern select",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	
	public CircPro_Subscription clickOnSaveAndBack(){
		String result = PerformAction.execute(ACTION_CLICK,"SaveAndBackOption");
		BaseClass.logResultInReport(result, "Click on Save and Back button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}

	
	public CircPro_Subscription acceptalert(){
		String result = PerformAction.execute(ACTION_ACCEPT_ALERT);
		BaseClass.logResultInReport(result, "accept alert",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription paymentsoption(){
		String result = PerformAction.execute(ACTION_CLICK,"Payments");
		BaseClass.logResultInReport(result, "Click on payment button",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription paymenttype() {
		String result = PerformAction.execute(ACTION_CLICK,"Paymenttype");
		BaseClass.logResultInReport(result, "Click on payment type dropdown",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
			
	}
	
	public CircPro_Subscription Cash() {
		String result = PerformAction.execute(ACTION_CLICK,"Cash");
		BaseClass.logResultInReport(result, "Click on payment type dropdown",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription Account(String strCaller)
	{
			
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"Account", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter Account",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
		
	public CircPro_Subscription EnterPayment(String strCaller) throws InterruptedException{
			Thread.sleep(3000);
			String result = PerformAction.execute(ACTION_TYPE_BY_JS,"PaymentValue", strCaller);
			System.out.println(result);
			BaseClass.logResultInReport(result, "Enter payment",reportTestObj);
			return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription NIE (String strCaller){
		
		String result = PerformAction.execute(ACTION_TYPE_BY_JS,"NIE", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter NIE",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription Discount(String strCaller){
		
		String result = PerformAction.execute(ACTION_TYPE_BY_JS,"Discount", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter Discount",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	
	public CircPro_Subscription Payfrom(String strCaller){
		
		String result = PerformAction.execute(ACTION_TYPE_BY_JS,"Payfrom", strCaller);
		System.out.println(result);
		BaseClass.logResultInReport(result, "Enter pay from date",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Subscription Save() {
		String result = PerformAction.execute(ACTION_CLICK,"Save");
		BaseClass.logResultInReport(result, "Click on payment type dropdown",reportTestObj);
		return new CircPro_Subscription(reportTestObj, APP_LOGS);
		
	}
	    public CircPro_Subscription closeBrowserPost()
	    {
			 String result = PerformAction.execute(ACTION_CLOSE_BROWSER,"Close Browser");
			 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
			 return new CircPro_Subscription(reportTestObj, APP_LOGS);
	}
}