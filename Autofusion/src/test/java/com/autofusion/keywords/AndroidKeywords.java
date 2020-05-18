package com.autofusion.keywords;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.bean.AndroidCommon;
import com.autofusion.constants.Constants;

import io.appium.java_client.MobileBy;

public class AndroidKeywords extends Keyword {

	String locatorId;
	WebElement element;
	String elementId;
	String inputValue;
	String[] keyValue;
	 List<WebElement> listOfElements;

	public AndroidKeywords(Logger log) {
		// setOrMap(orMap);
		APP_LOG = log;
	}

	public String andLaunchApplication(Map<String, String> argsList) {
		APP_LOG.debug("Closing closeApplication ");
		return andLaunchApplication();
	}

	public static String andCloseApplication(Map<String, String> argsList) {
		APP_LOG.debug("Closing closeApplication ");
		return andCloseApplication();
	}

	public static String andLaunchApplication() {
		APP_LOG.debug("Closing closeApplication ");
		try {
			andCloseApplication();
			// .appiumDriver.launchApp();
		} catch (Exception e) {
			APP_LOG.debug("Closing closeApplication " + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public String andClearAdbLogs(Map<String, String> argsList) {
		APP_LOG.debug("Clearing ADB logs");
		try {
			AndroidCommon.clearADBLogs();
		} catch (Exception e) {
			APP_LOG.debug("Clearing ADB logs" + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public String andCaptureADBLogs(Map<String, String> argsList) {
		APP_LOG.debug("Capturing ADB logs");
		try {
			String filePath = argsList.get("InputValue");
			String projectPath = argsList.get("projectPath");
			AndroidCommon.captureADBLogs(new File(projectPath+"/"+filePath));
		} catch (Exception e) {
			APP_LOG.debug("Capturing ADB logs" + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public static String andCloseApplication() {
		APP_LOG.debug("Closing closeApplication ");
		try {
			appiumDriver.quit();
		} catch (Exception e) {
			APP_LOG.debug("closeApplication " + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public String andForceStopAnApp(Map<String, String> argsList) {
		try {
			APP_LOG.debug("Stopping an application");
			String packageName = argsList.get("InputValue");
			AndroidCommon.forceStopAnApp(packageName);
		} catch (Exception e) {
			APP_LOG.debug("force stop an app " + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public String andOpenRecentlyUsedApps(Map<String, String> argsList) {
		try {
			APP_LOG.debug("Switching apps");
			AndroidCommon.openRecentlyUsedApps();
			return Constants.PASS;
		} catch (Exception e) {
			APP_LOG.debug("Error while Switching App" + e);
			return Constants.FAIL;
		}
	}

	public String andKillMostRecentApp(Map<String, String> argsList) {
		try {			
			APP_LOG.debug("Killing an application");
			elementId = argsList.get("ElementId");
			listOfElements = getElements(elementId);
			APP_LOG.debug("Size of the list if "+ listOfElements.size());
			System.out.println("Size of the list if "+ listOfElements.size());
			listOfElements.get(listOfElements.size()-1).click();			
			return Constants.PASS;
		} catch (Exception e) {
			APP_LOG.debug("Error while Killing Latest app" + e);
			return Constants.FAIL;
		}
	}

	public String andOpenAnotherApp(Map<String, String> argsList) {
		try {
			APP_LOG.debug("Opening another application");
			String packageName = argsList.get("InputValue");
			String className = argsList.get("data1");
//			System.out.println("className = "+className);
			AndroidCommon.switchApp(packageName, className);
		} catch (Exception e) {
			APP_LOG.debug("Error while Opening Another application" + e);
			return Constants.FAIL;
		}

		return Constants.PASS;
	}

	public String andScrollTo(Map<String, String> argsList) {
		try {
			APP_LOG.debug("Scrolling to element");
			locatorId = argsList.get("ElementId");
			inputValue = argsList.get("InputValue");
			appiumDriver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ inputValue + "\").instance(0))"));
			return Constants.PASS;
		} catch (Exception e) {
			APP_LOG.debug("Error in scrolling " + e);
			e.printStackTrace();
			return Constants.FAIL;
		}

		/*
		 * locatorId = argsList.get("ElementId"); inputValue =
		 * argsList.get("InputValue");
		 * 
		 * String selectorString = String.format(
		 * "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("
		 * + inputValue + ")");
		 * appiumDriver.findElement(MobileBy.AndroidUIAutomator(selectorString));
		 */
	}

	public String andScrollToElementGiven(Map<String, String> argsList) {
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("ScrollToElementGiven : " + inputValue);
		// ((Object) appiumDriver).scrollToExact(inputValue);
		return Constants.PASS;

	}
	
	public String andScrollToElementGivenMbl(Map<String,String> argsList){
		
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("ScrollToElementGiven : "+inputValue);
		
 		MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+inputValue+"\").instance(0))");
		MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"\").instance(0))");
		
		return Constants.PASS;
	}
	
    
	public String andScrollToExact(Map<String,String> argsList){
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("ScrollToElementGiven : "+inputValue);
 	//	appiumDriver.scrollToExact(inputValue);
		return Constants.PASS;
		
	}

	public String andExplicitWait(Map<String, String> argsList) {
		try {
			APP_LOG.debug("Entering Explicit wait");
			elementId = argsList.get("ElementId");
			element = getElement(elementId);
			WebDriverWait wait = new WebDriverWait(appiumDriver, 10);
			wait.until(ExpectedConditions.visibilityOf(element));
			return Constants.PASS + "Element '" + elementId + "' : is visible.";
		} catch (Exception e) {
			APP_LOG.debug("Error in explicit wait for android " + e);
			e.printStackTrace();
			return Constants.FAIL;
		}

	}

	public String andHideKeyboard(Map<String, String> argsList) {
		try {
			// appiumDriver.hideKeyboard();
		} catch (Exception e) {
			APP_LOG.debug("hideKeyboard :" + e);
		}
		return Constants.PASS;
	}

	public String andHideKeyboardViaJs(Map<String, String> argsList) {
		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("mobile: hideKeyboard");
		return Constants.PASS;
	}

	public String andEnterMobileEnterButton(Map<String, String> argsList) {

		// appiumDriver.getKeyboard().sendKeys("66") ;
		return Constants.PASS;
	}
}
