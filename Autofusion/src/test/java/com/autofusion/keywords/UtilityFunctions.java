//package com.autofusion.keywords;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import com.autofusion.BaseClass;
//import com.relevantcodes.extentreports.LogStatus;
//
//public class UtilityFunctions extends BaseClass {
//	public int timeout = 10;
//	
//	public void enterInputData(String locator, String value, String message) {
//		String functionName = "EnterData";
//		String testData = value;
//		APP_LOG.debug(locator + "Click on the Element.");
//		element = null;
//		element = GetElement(locator, timeout);
//		try{
//			element.clear();
//			element.sendKeys(testData.trim());
//			extentReportlogSteps_Update(LogStatus.PASS, functionName, testData, getImageFileLocation(), element);	
//		}
//		catch(Exception e)
//		{
//			extentReportlogSteps_Update(LogStatus.FAIL, functionName, testData, getImageFileLocation(), element);
//		}
//	}
//	
//	public void clickOnElement(String locator, String message) {
//		String functionName = "Click";
//		APP_LOG.debug(locator + "Click on the Element.");
//		element = null;
//		
//		element = GetElement(locator, timeout);
//		try{
//			element.click();
//			extentReportlogSteps_Update(LogStatus.PASS, functionName, message, getImageFileLocation(), element);	
//		}
//		catch(Exception e)
//		{
//			try{
//				webDriver.findElement(By.xpath(locator)).click();
//				extentReportlogSteps_Update(LogStatus.PASS, functionName, message, getImageFileLocation(), element);	
//			}
//			catch(Exception e1)
//			{
//				extentReportlogSteps_Update(LogStatus.FAIL, functionName, message, getImageFileLocation(), element);
//			}
//			
//		}
//	}
//	
//	
//	
//		public void selectDropdownValue(String locator, String value, String message) {
//			String functionName = "selectDropdownValue";
//			APP_LOG.debug(locator + "Click on the Element.");
//			element = GetElement(locator, timeout);
//			try{
//			Select oSelect = new Select(element);
//			oSelect.selectByVisibleText(value);
//			extentReportlogSteps_Update(LogStatus.PASS, functionName, value, getImageFileLocation(), element);	
//			}
//			catch(Exception e)
//			{
//			extentReportlogSteps_Update(LogStatus.FAIL, functionName, value, getImageFileLocation(), element);
//			}
//	}
//		public void Wait(int time) {
//			try {
//				Thread.sleep(time * 1000);
//			} catch (Exception e) {
//				
//			}
//		}
//		public void VerifyCondition(String locator, String verificationType, String text, String logMessage) {
//			String functionName = "VerifyCondition";
//			String stepDescription = logMessage;
//			String testData = text;
//			String messages = "";
//			WebElement element = null;
//
//			try {
//				element = GetElement(locator, 3);
//				Wait(2);
//				switch (verificationType.toLowerCase()) {
//
//				case "emptystringcheck":
//					if (element.getText() != "") {
//						messages = element.getText();
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "notnull":
//					if (element != null) {
//						messages = "";
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "null":
//					if (element == null) {
//						messages = "";
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "equals": 
//					if (element.getText().equalsIgnoreCase(text)) {
//						messages = element.getText();
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = element.getText();
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "enabled":
//					if (element.isEnabled()) {
//						messages = "Enabled";
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "Not Enabled";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "disabled":
//					if (!element.isEnabled() || element.getAttribute("disabled").equalsIgnoreCase("true")) {
//						messages = "Disabled";
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "Enabled";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "contains":
//					if (element.getText().toLowerCase().contains(text.toLowerCase())
//							|| element.getText().toLowerCase().replace(" ", "").trim().contains(text.toLowerCase())) {
//						messages = element.getText();
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, testData, getImageFileLocation(),
//								element);
//					} else {
//						messages = element.getText();
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, testData, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "equalsvalueattribute":
//
//					if (element.getAttribute("value").toLowerCase().contains(text.toLowerCase())) {
//						messages = element.getAttribute("value");
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "Match By Value Failed";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "maxlength":
//
//					if (element.getAttribute("maxlength").contentEquals(text)) {
//						messages = element.getAttribute("maxlength");
//						extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(),
//								element);
//					} else {
//						messages = "Max Length Test Failed";
//						extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(),
//								element);
//					}
//					break;
//
//				case "reportsuccess":
//					messages = "Verified";
//					extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(), element);
//					break;
//
//				case "reportfailure":
//					messages = "Failed Verification";
//					extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(), element);
//					break;
//
//				}
//			} catch (Exception e) {
//				extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(), element);
//			}
//
//		}
//		//************************************************************************************************************************
//		public void ClearTextField(String locator, String logMessage) {
//			String functionName = "ClearTextField";
//			String stepDescription = logMessage;
//			WebElement element = null;
//			element = GetElement(locator, timeout);
//			try {
//				if (element != null) {
//					element.clear();
//					extentReportlogSteps_Update(LogStatus.PASS, functionName, stepDescription, getImageFileLocation(), null);
//				} else {
//					extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(), null);
//				}
//			} catch (Exception e) {
//				extentReportlogSteps_Update(LogStatus.FAIL, functionName, stepDescription, getImageFileLocation(), null);
//			}
//
//		}
//		
//		//************************************************************************************************************************
//}
