
package com.autofusion.util;

import java.security.SecureRandom;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.PerformAction;
import com.relevantcodes.extentreports.ExtentTest;


@SuppressWarnings("all")
public class CommonUtil extends BaseClass implements KeywordConstant {

	protected Logger APP_LOGS = null;
	protected ExtentTest reportTestObj;
	protected String result = "";
	protected String stepDescription = "";
	private PerformAction performAction = new PerformAction();
	private JavascriptExecutor js;


	public CommonUtil(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
	}

	/**
	 * @author mohit.gupta5
	 * @date 13 July ,17
	 * @description Press Tab key
	 */
	public void pressTabKey() {
		this.result = this.performAction.execute(ACTION_PRESS_TAB_KEY);
	}

	/**
	 * @author sagar.pawar
	 * @date 04 May ,17
	 * @description Clear Session storage
	 */
	public void clearSessionStorage() {
		String windowSessionStorage = "window.sessionStorage.clear();";
		this.js = (JavascriptExecutor) returnDriver();
		this.js.executeScript(String.format(windowSessionStorage));

	}

	/**
	 * @author sagar.pawar
	 * @date 04 May ,17
	 * @description Clear Local storage
	 */
	public void clearLocalStorage() {
		String windowStorageClear = "window.localStorage.clear();";
		this.js = (JavascriptExecutor) returnDriver();
		this.js.executeScript(String.format(windowStorageClear));

	}

	/**
	 * @author tarun.gupta1
	 * @date 22 May, 2017
	 * @description To generate random string of numbers
	 */

	public String generateRandomStringOfNumbers(int numofChars) {
		String inputNumbers = "1234567890";
		StringBuilder inputString = new StringBuilder();
		SecureRandom rnd = new SecureRandom();
		while (inputString.length() < numofChars) {
			// string.
			int index = (int) (rnd.nextDouble() * inputNumbers.length());
			inputString.append(inputNumbers.charAt(index));
		}
		String FinalStr = inputString.toString();
		APP_LOG.info(FinalStr);
		return FinalStr;
	}

	/**
	 * @author tarun.gupta1
	 * @date 22 May, 2017
	 * @description To generate random string of Alphabets
	 */
	public String generateRandomStringOfAlphabets(int numofChars) {
		try {
			String inputAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			StringBuilder inputString = new StringBuilder();
			SecureRandom rnd = new SecureRandom();
			while (inputString.length() < numofChars) {
				// string.
				int index = (int) (rnd.nextDouble() * inputAlphabets.length());
				inputString.append(inputAlphabets.charAt(index));
			}
			String FinalStr = inputString.toString();
			APP_LOG.info(FinalStr);
			return FinalStr;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * @author tarun.gupta1
	 * @date 22 May, 2017
	 * @description To generate random string of alphanumeric character
	 */
	public String generateRandomStringOfAlphaNumericCharacters(int numofChars) {
		try {
			String inputAlphaNumerics = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
			StringBuilder inputString = new StringBuilder();
			SecureRandom rnd = new SecureRandom();
			while (inputString.length() < numofChars) {
				// string.
				int index = (int) (rnd.nextDouble() * inputAlphaNumerics.length());
				inputString.append(inputAlphaNumerics.charAt(index));
			}
			String FinalStr = inputString.toString();
			APP_LOG.info(FinalStr);
			return FinalStr;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * @author tarun.gupta1
	 * @date 23 May, 2017
	 * @description To generate random string of Special character
	 */
	public String generateRandomStringOfSpecialCharacters(int numofChars) {
		try {
			String inputSpecialChars = "!@#$%^&*()_,.<>/?][{};:'";
			StringBuilder inputString = new StringBuilder();
			SecureRandom rnd = new SecureRandom();
			while (inputString.length() < numofChars) {
				// string.
				int index = (int) (rnd.nextDouble() * inputSpecialChars.length());
				inputString.append(inputSpecialChars.charAt(index));
			}
			String FinalStr = inputString.toString();
			return FinalStr;
		} catch (RuntimeException e) {
			APP_LOG.error("unable to generate generateRandomStringOfSpecialCharacters " + e);
			return null;
		}
	}

	/**
	 * @author sumit.bhardwaj Scrolls the webpage upto pixels passed in
	 *         parameter.
	 * 
	 * @param pixelToScrollHorizontally
	 *            : pixel to scroll horizontally
	 * @param pixelToScrollVertically
	 *            : pixel to scroll vertically
	 * @return Pass or fail with the cause.
	 */
	public String scrollWebPage(int pixelToScrollHorizontally, int pixelToScrollVertically) {

		System.out.println("Scrolling through web page ... ");
		BaseClass base = new BaseClass() {
		};
		WebDriver dr = base.returnDriver();
		try {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			js.executeScript("scroll(" + pixelToScrollHorizontally + "," + pixelToScrollVertically + ")");
			logResultInReport("PASS, Sucessfully scroll through the page",
					"Scroll through the page to make the element visible", this.reportTestObj);
		}

		catch (Exception e) {

			// Log the exception
			APP_LOG.error("Error while scrolling through the web page : " + e);

			return "Fail : Error while scrolling through the web page : " + e;
		}

		return "Pass : Scrolled through the web page";

	}

	/**
	 * @author mukul.sehra
	 * @description Press Enter Key
	 */
	public void pressEnterKey() {
		performAction.execute(ACTION_PRESS_ENTER_KEY);
	}

	/**
	 * @author Abhishek. Sharda
	 * @date 10 April ,17
	 * @description Verify videos
	 */
	public void verifyVideoPlayback(String element, String stepDesc) {
		this.result = this.performAction.execute(ACTION_VERIFY_VIDEOS, element);
		logResultInReport(this.result, stepDesc, this.reportTestObj);
	}

	/**
	 * @author Abhishek. Sharda
	 * @date 10 April ,17
	 * @description Verify images
	 */
	public void verifyimages(String element, String stepDesc) {
		this.result = this.performAction.execute(ACTION_VERIFY_IMAGES, element);
		logResultInReport(this.result, stepDesc, this.reportTestObj);
	}

	/**
	 * @author mukul.sehra
	 * @param webElementLocator
	 *            --> element to scroll into
	 */
	public void scrollIntoView(String webElementLocator) {
		this.performAction.execute(ACTION_SCROLL_INTO_VIEW, webElementLocator);
	}

	/**
	 * @description Refreshes the web page
	 */
	public void refreshCurrentPage() {
		returnDriver().navigate().refresh();
	}

	/**
	 * @author Nitish.Jaiswal
	 * @date 29 Sep,2017
	 * @description handle run time pop ups
	 */
	public void handleRunTimePopUp(String consoleCrosslocator, String consoleBacklocator) {
		try {
			String res = "";
			APP_LOG.debug("handle run time pop ups");
			res = performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, consoleCrosslocator);
			if (res.contains(Constants.PASS)) {
				JavascriptExecutor js = (JavascriptExecutor) returnDriver();
				js.executeScript("arguments[0].click();", returnDriver().findElement(By.cssSelector(".close-title")));
				if (performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, consoleBacklocator)
						.contains(Constants.PASS)) {
					performAction.execute(ACTION_CLICK, consoleBacklocator);
				}
			}
		} catch (Exception e) {

		}
	}
}
