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

import com.test.CircPro_SubscriptionTest;


	  
public class CircPro_Routes extends BaseClass implements KeywordConstant{
	

	Logger APP_LOGS;
	ExtentTest reportTestObj;
 	public HashMap<String, String> dataMap = new HashMap<>();
	public String subZipCode= null;
	public String routeNumber= null;
	
	public CircPro_Routes(ExtentTest reportTestObj, Logger APP_LOGS) {
		
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		BaseClass.loadPage(CircPro_Routes.class);
	}
	
		/////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////Test Case Methods////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
	

	public CircPro_Routes navigateOverToSubscriberSearch(){
		String result = PerformAction.execute(ELEMENT_LOCATOR,"SearchMenu");
		BaseClass.logResultInReport(result, "Click On Subscriber Search on Homepage",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes click(){
		String result = PerformAction.execute(ACTION_CLICK,"SearchMenu");
		BaseClass.logResultInReport(result, "Click On Search on Homepage",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	
	public CircPro_Routes searchSubscriberName(String strCaller){		
	    String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"SearchBox", strCaller);
	    System.out.println(result);
	    BaseClass.logResultInReport(result, "Enter LastName",reportTestObj);
	    return new CircPro_Routes(reportTestObj, APP_LOGS);
    }
	
	public CircPro_Routes clickOnSearchButton() throws InterruptedException{
		System.out.println("in search");
		String result = PerformAction.execute(ACTION_CLICK,"SearchButton");
		BaseClass.logResultInReport(result, "Click on Search button",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	

	public CircPro_Routes clickOnAddressLink(){
		String result = PerformAction.execute(ACTION_CLICK,"addressTag");
		BaseClass.logResultInReport(result, "Click On Address",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes navigateToZipCodeTag(){
		String zipCode = generateLocalZipCode();
		String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"zipCodeTag", zipCode);
		WebDriver webDriver = getDriver();
		subZipCode=webDriver.findElement(By.name("0.3.13.0.5.5.51.1.0.3.0.1.0.0.5")).getAttribute("value");
		BaseClass.logResultInReport(result, "Zip Code Value Copied",reportTestObj);	
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes clickOnUseasTyped(){
		String result = PerformAction.execute(ACTION_CLICK,"useAsTypedButton");
		BaseClass.logResultInReport(result, "Click On Use As Typed",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes clickOnUseOkButton(){
		String result = PerformAction.execute(ACTION_CLICK,"okButton");
		BaseClass.logResultInReport(result, "Click On Use As Typed",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
		}

	public CircPro_Routes clickOnUseOkButton1(){
		String result = PerformAction.execute(ACTION_CLICK,"okButton1");
		BaseClass.logResultInReport(result, "Click On Use As Typed",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
		}
	public CircPro_Routes clickOnSaveandBackButton(){
		String result = PerformAction.execute(ACTION_CLICK,"saveAndBack1");
		BaseClass.logResultInReport(result, "Click On Back Button",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes clickOnRouter(){
		String result = PerformAction.execute(ACTION_CLICK,"router");
		BaseClass.logResultInReport(result, "Click On Router Under Admin Section",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes byZipCode(){
		String result = PerformAction.execute(ACTION_CLICK,"byZipCode");
		BaseClass.logResultInReport(result, "Click On By Zip Code",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
		
    public CircPro_Routes enterZipCode() {
		 
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "enterZipCode", subZipCode);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter ZipCode", reportTestObj);
		 return new CircPro_Routes(reportTestObj, APP_LOGS);
	 }

    public CircPro_Routes clickSearchButton(){
		String result = PerformAction.execute(ACTION_CLICK,"searchZipCode");
		BaseClass.logResultInReport(result, "Click On Search Zip Code",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}

    public CircPro_Routes deleteZip(){
		String result = PerformAction.execute(ACTION_CLICK,"deleteZipCode");
		BaseClass.logResultInReport(result, "Click On Search Zip Code",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}

    public CircPro_Routes alerthandle(){
		String result = PerformAction.execute(ACTION_ACCEPT_ALERT,"acceptAlert");
		BaseClass.logResultInReport(result, "Click On Search Zip Code",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}

    
    public CircPro_Routes addZip(){
		String result = PerformAction.execute(ACTION_CLICK,"addZipCode");
		BaseClass.logResultInReport(result, "Click On Search Zip Code",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
    
	public CircPro_Routes addRouteButton(){
		String result = PerformAction.execute(ACTION_CLICK,"addRoute");
		BaseClass.logResultInReport(result, "Click On Add Route Button",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes addRouteName(){		
		 routeNumber=generateLocalRandomrange();
		 String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR, "routeName", routeNumber);
		 System.out.println(result);
		 BaseClass.logResultInReport(result, "Enter ZipCode", reportTestObj);
		 return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes auditCodeDropDownValues(){
		String result = PerformAction.execute(ACTION_CLICK,"auditCodeDropdownValues");
		BaseClass.logResultInReport(result, "Select Audit Code DropDown Value",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes selectDaysOfWeek(){
		String result = PerformAction.execute(ACTION_CLICK,"daysOfWeekRoutes");
		BaseClass.logResultInReport(result, "Checkbox Days of the Week Routes Clicked",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes saveRoutes(){
		String result = PerformAction.execute(ACTION_CLICK,"saveRoutes");
		BaseClass.logResultInReport(result, "Click On Save Route",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes dataSaved(String text, String locator){
		String result = PerformAction.execute(ACTION_VERIFY_TEXT, locator, text);
		BaseClass.logResultInReport(result, "Verified Data Saved Text",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes navigateDistributorSearch(){
		String result = PerformAction.execute(ACTION_CLICK,"navigateDistributor");
		BaseClass.logResultInReport(result, "Navigate To Distributor",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes selectDropdownFilter(){
		String result = PerformAction.execute(ACTION_CLICK,"filterNone");
		BaseClass.logResultInReport(result, "Select filter None Value",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes distributorSearch(String strCaller){		
	    String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"SearchBox", strCaller);
	    System.out.println(result);
	    BaseClass.logResultInReport(result, "Enter LastName",reportTestObj);
	    return new CircPro_Routes(reportTestObj, APP_LOGS);
    }
	
	public CircPro_Routes clickDistributorSearch(){
		String result = PerformAction.execute(ACTION_CLICK,"distributionsSearch");
		BaseClass.logResultInReport(result, "Select filter None Value",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	
	public CircPro_Routes selectuserDistributor(){
		String result = PerformAction.execute(ACTION_CLICK,"distributions");
		BaseClass.logResultInReport(result, "Select distributions",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes addDistribution(){
		String result = PerformAction.execute(ACTION_CLICK,"addDistributions");
		BaseClass.logResultInReport(result, "Add Distribution",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	

	public CircPro_Routes selectDistributionTypeDropdown(){
		String result = PerformAction.execute(ACTION_CLICK,"distributionType");
		BaseClass.logResultInReport(result, "Add Distribution",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}	
	

	public CircPro_Routes addRouteNumber(){		
	    String result = PerformAction.execute(ACTION_TYPE_AFTER_CLEAR,"routeNumber", routeNumber);
	    System.out.println(result);
	    BaseClass.logResultInReport(result, "Enter Route Number",reportTestObj);
	    return new CircPro_Routes(reportTestObj, APP_LOGS);
    }
	
	public CircPro_Routes saveButton(){
		String result = PerformAction.execute(ACTION_CLICK,"saveButton");
		BaseClass.logResultInReport(result, "Save distributions",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes clickOnSubscriptionLink(){
		String result = PerformAction.execute(ACTION_CLICK,"subscription");
		BaseClass.logResultInReport(result, "Click On subscription",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}	

	public CircPro_Routes clickOnAddSubscription(){		
	    String result = PerformAction.execute(ACTION_CLICK,"addSubscriptionNew");
	    System.out.println(result);
	    BaseClass.logResultInReport(result, "Add Subscription",reportTestObj);
	    return new CircPro_Routes(reportTestObj, APP_LOGS);
    }
	
	public CircPro_Routes selectPayType(){
		String result = PerformAction.execute(ACTION_CLICK,"selectPayType");
		BaseClass.logResultInReport(result, "Select Pay Type",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	

	public CircPro_Routes clickSaveandBack(){
		String result = PerformAction.execute(ACTION_CLICK,"saveAndBack");
		BaseClass.logResultInReport(result, "Select Pay Type",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}
	
	public CircPro_Routes verifyText(){
		String result = PerformAction.execute(ACTION_VERIFY_TEXT,routeNumber);
		BaseClass.logResultInReport(result, "Select Pay Type",reportTestObj);
		return new CircPro_Routes(reportTestObj, APP_LOGS);
	}	

	  public String getFields() 
	  {
	
		String lastName = CircPro_SubscriptionTest.lastname; 
		return lastName;
	  }
	  

	  public String generateLocalZipCode()
		{
			 Random rand = new Random();
			 int randomInt = rand.nextInt(89999) + 10000;
			 String zip = Integer.toString(randomInt);
			 return zip;}
		
	public CircPro_Distribution closeBrowserPost(){
	 String result = PerformAction.execute(ACTION_CLICK_TERMINATE,"Close Browser");	
	 BaseClass.logResultInReport(result,"Browser Closed", reportTestObj);
	 return new CircPro_Distribution(reportTestObj, APP_LOGS);
	}	
}