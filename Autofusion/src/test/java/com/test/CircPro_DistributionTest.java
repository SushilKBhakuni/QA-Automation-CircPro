package com.test;

import org.testng.annotations.Test;

import com.autofusion.BaseClass;
import com.ncs.page.CircPro_Distribution;

public class CircPro_DistributionTest extends BaseClass{
	  public CircPro_Distribution distribution_TestPageObj ;
	  String firstname=null;
	  String lastname=null;
	  public void startReport(String testDesc){
 		  reportTestObj = reportObj.startTest(testDesc);
		  /***************** Change the class here ******************/
 		  distribution_TestPageObj = new CircPro_Distribution(reportTestObj, APP_LOG);
 		  //BaseClass.setRunningComponentName(this.getClass().getSimpleName());
		  BaseClass.loadPage(CircPro_Distribution.class);
		  /***********************************************************/
	  }
	 
	  @Test
	public void testMethod() throws InterruptedException {
			searchDistribution_circpro(); //Parul-TC_5
	}
	
	public void searchDistribution_circpro() throws InterruptedException{
		  startReport("Search_Distribution_CircPro");
		  distribution_TestPageObj.clickOnDistributionSearch();
		  distribution_TestPageObj.enterLnInDistributorSearch(configurationsXlsMap.get("lastname"));
		  distribution_TestPageObj.clickOnAddCriteria();
		  distribution_TestPageObj.clickOnDropdown();
		  distribution_TestPageObj.clickOnDropdownSelection();
		  distribution_TestPageObj.enterFnInDistributorSearch(configurationsXlsMap.get("firstname"));
		  distribution_TestPageObj.clickOnSearch();
		  distribution_TestPageObj.clickOnHideInactives();
		  distribution_TestPageObj.clickOnDropdownP();
		  distribution_TestPageObj.clickOnDropdownMessenger();
		  distribution_TestPageObj.clickOnAdd_distribution();
		  distribution_TestPageObj.clickOnDropdownD();
		  distribution_TestPageObj.clickOnDropdownMR();
		  distribution_TestPageObj.enterRN(configurationsXlsMap.get("RN"));
		  distribution_TestPageObj.clickOnSave();
		  distribution_TestPageObj.clickOnHome();
	
	}
}
