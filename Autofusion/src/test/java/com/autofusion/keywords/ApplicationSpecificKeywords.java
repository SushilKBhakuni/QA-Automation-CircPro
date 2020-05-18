package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.util.Accessibility;
import com.relevantcodes.extentreports.ExtentTest;
@SuppressWarnings("all")
public class ApplicationSpecificKeywords extends Keyword implements KeywordConstant {
	Logger APP_LOGS;
	ExtentTest reportTestObj;
	public static String result = "";
	public static String stepDescription = "";

	private PerformAction PerformAction = new PerformAction();
	private FindElement FindElement = new FindElement();

	public ApplicationSpecificKeywords(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
	}

	/*
	 * @Override public void logResultInReport(String testResult, String
	 * stepDescription,reportTestObj) { if (testResult.contains(Constants.PASS))
	 * { reportTestObj.log(LogStatus.PASS, stepDescription, testResult); } else
	 * { reportTestObj.log(LogStatus.FAIL, stepDescription, testResult); } }
	 * 
	 * public ExtentTest getReportTestObj() { return reportTestObj; }
	 */

	public void setReportTestObj(ExtentTest reportTestObj) {
		this.reportTestObj = reportTestObj;
	}

	/**
	 * @author Abhishek Sharda
	 * @date 13 March,17
	 * @description to check accessibility issues using
	 * @return null
	 */

	public ApplicationSpecificKeywords testAccessibility() {
		APP_LOGS.debug("Verify Acessabilty issues on Sign-on Page");
		Accessibility sbc = new Accessibility();
		List<String> getAccessibilityErrors = sbc.runAcopChecks();
		ListIterator<String> itr = getAccessibilityErrors.listIterator();
		while (itr.hasNext()) {
			logResultInReport(itr.next(),
					"Accesabilty result as per Web Content Accessibility Guidelines 2.0 & Sect 508", reportTestObj);
		}
		return new ApplicationSpecificKeywords(reportTestObj, APP_LOGS);
	}

	/**
	 * @author Mehak Verma
	 * @date 23 January,16
	 * @param time
	 * @description to figure out whether your website using AngularJS has
	 *              finished making AJAX calls
	 * @return null
	 */
	/*
	 * public ExpectedCondition<Boolean> angularHasFinishedProcessing() { return
	 * new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { return
	 * Boolean.valueOf(((JavascriptExecutor) driver) .executeScript(
	 * "return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)"
	 * ) .toString()); } }; }
	 */

	/**
	 * @author Mehak Verma
	 * @date 08 December,16
	 * @param time
	 *            (in milliseconds)
	 * @description to provide given time of wait within a function
	 * @return null
	 */
	public void doWait(long time) {
		try {
			time = 0;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description to check if the app is already login
	 * @return null
	 */
	public void checkIfAlreadyLoginThenLogout() {
		// doWait(3000);
		String checkLogoutSign = PerformAction.execute(ACTION_CLICK, "LearnerConsoleLogOutButtonsign");
		// doWait(5000);
		if (checkLogoutSign.equalsIgnoreCase("PASS")) {
			String result = PerformAction.execute(ACTION_CLICK, "LearnerConsoleLogOutButton");
		}
	}

	/**
	 *
	 * @description It navigates to Homework page.
	 * @return null
	 */
	public void navigateHomeworkPage() {
		PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE, "CourseHomeNavigation");
		PerformAction.execute(ACTION_CLICK, "CourseHomeNavigation");
		PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE, "HomeWorkButton");
		PerformAction.execute(ACTION_CLICK, "HomeWorkButton");
	}

	/**
	 * @author Lekh Bahl
	 * @date 06 Jan,17
	 * @description It navigates to the Page where element Id2 is present.
	 * @return null
	 */
	// elementId1: Element Id is being clicked till elementId2 is found
	// elementId2: Element Id whose presence is checked

	public void navigatesToPage(String elementId1, String elementId2) {
		int maxPageCount = 0;
		while (!(PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, elementId2).equalsIgnoreCase(Constants.PASS))
				&& (maxPageCount <= 10)) {
			PerformAction.execute(ACTION_CLICK, elementId1);
			String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, elementId2);
			maxPageCount = maxPageCount + 1;
			if (result.equalsIgnoreCase(Constants.PASS) || (maxPageCount == 10)) {
				break;
			}
			// logResultInReport(result, elementId2+ " is present.");
		}
	}

	/**
	 * @author Mayank Mittal
	 * @date 11 Jan,17
	 * @description To find the existence of an element on DOM.
	 * @return null
	 */
	public void verifyIsDisplayed(String elementId) {
		String result = PerformAction.execute(ACTION_VERIFY_ISDISPLAYED, elementId);
		logResultInReport(result, elementId + " is displayed.", reportTestObj);
	}

	/**
	 * @author Mayank Mittal
	 * @date 18 Jan,17
	 * @description To click on an element on DOM.
	 * @return null
	 */
	public void clickOnElement(String elementId) {
		String result = PerformAction.execute(ACTION_CLICK, elementId);
		logResultInReport(result, "Cross is clicked", reportTestObj);
	}

	/**
	 * @author Mayank Mittal
	 * @date 18 Jan,17
	 * @description To verify page title.
	 * @return null
	 */
	public void verifyPageTitle(String title) {
		PerformAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, "LearnerConsoleSettingsXpath");
		String result = PerformAction.execute(ACTION_VERIFY_TITLE, "", title);
		logResultInReport(result, "Verify " + title + " title.", reportTestObj);
	}
	
	

	/**
	 * @author sumit.bhardwaj
	 * @date 24 Jan,2017
	 * @description Check angular injector value is true
	 * @return True if injector value is true False if injector value is false
	 */

	/*
	 * public ExpectedCondition<Boolean> angularHasFinishedProcessing() { return
	 * new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { // return
	 * Boolean.valueOf(((JavascriptExecutor) // driver).executeScript("return
	 * (window.angular !== undefined) // &&
	 * (angular.element(document).injector() !== undefined) && //
	 * (angular.element(document).injector().get('$http').pendingRequests.length
	 * <<<<<<< HEAD // === 0)").toString()); System.out.println("results "
	 * +(((JavascriptExecutor) driver) .executeScript(
	 * "return (angular.element(document).injector() !== undefined)"
	 * ).toString())); return Boolean.valueOf(((JavascriptExecutor) driver)
	 * .executeScript(
	 * "return (angular.element(document).injector() !== undefined)" ======= //
	 * === 0)").toString());
	 * System.out.println("results "+(((JavascriptExecutor) driver)
	 * .executeScript("return (angular.element(document).injector() !== undefined)"
	 * ).toString())); return Boolean.valueOf(((JavascriptExecutor) driver)
	 * .executeScript("return (angular.element(document).injector() !== undefined)"
	 * >>>>>>> 2fa285f5afef4551047807dbd07fb7c84a3137fb ).toString()); } }; }
	 * 
	 * public void waitTillAnglularFinishProcessing() { try { WebDriverWait wait
	 * = new WebDriverWait(webDriver, 10);
	 * wait.until(angularHasFinishedProcessing()); } catch (Exception e) {
	 * System.out.println(e.getMessage()); } }
	 */
}