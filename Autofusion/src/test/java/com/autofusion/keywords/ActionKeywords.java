package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
@SuppressWarnings("unused")
public class ActionKeywords extends Keyword {

	WebElement element;
	WebElement element1;
	String locatorId;
	String inputValue;

	Keyword objKeyWord;

	/*
	 * public ActionKeywords(Logger log, Map<String,HashMap<String, String>>
	 * orMap) { //setOrMap(orMap); APP_LOG = log; }
	 */
	public ActionKeywords(Logger log) {
		// setOrMap(orMap);
		APP_LOG = log;
		objKeyWord = new Keyword();
	}

	
	/**
	 * moves to the respective element on hovering of mouse over that
	 * @param args
	 * @return
	 */
	public String actionMousehover(String[] args) {
		locatorId = args[0];
		// String locatorId2=args[2];
		inputValue = args[1];
		try {
			WebDriver webDriver = getDriver();
			Actions action = new Actions(webDriver);
			element = getElement(locatorId);
			// WebElement element2 = findElementWeb(locatorId2);
			// action.moveToElement(element).moveToElement(element2).click().build().perform();
		} catch (Throwable e) {
			APP_LOG.debug(" Nothing happens on hovering by mouse over an area" + e);
			return Constants.FAIL + ": Error while getting Element for - '" + locatorId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Mouse hover on ELement - '" + locatorId + "' is performed.";

	}

	public String actionMoveToElement(Map<String, String> argsList) {
		APP_LOG.debug("Func:actionMoveToElement");
		WebElement element;
		try {
			WebDriver webDriver = getDriver();
			element = getElement(argsList.get(KeywordConstant.ELEMENT_LOCATOR));
			Actions action = new Actions(webDriver);
			action.moveToElement(element).perform();
			action.click(element).perform();
			/*
			 * element1 =
			 * getElement(argsList.get(KeywordConstant.ELEMENT_LOCATOR1));
			 * action.moveToElement(element1).click().build().perform();
			 */
		} catch (Exception e) {
			APP_LOG.debug(" Func:actionMoveToElement=" + e);
			return Constants.FAIL + ": Error while getting Element for - '" + KeywordConstant.ELEMENT_LOCATOR + "' : "
					+ e.getMessage();
		}
		return Constants.PASS + ": Mouse is moved to element - '" + KeywordConstant.ELEMENT_LOCATOR + "' .";

	}

	public String actionFocusElement() {
		APP_LOG.info("in focusOnUI");
		try {
			WebDriver webDriver = getDriver();
			webDriver.switchTo().activeElement();
			// Switch to currently active element in a page
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while moving to the Active element - '"
					+ KeywordConstant.ELEMENT_LOCATOR + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Focus is moved to the Active Element on UI.";
	}

	/**
	 * 
	 * @desc- popup features
	 * @return
	 */

	public WebElement actionFocusElementPopUp() {
		APP_LOG.info("in focusOnUI");
		try {
			WebDriver webDriver = getDriver();
			WebElement element = webDriver.switchTo().activeElement();
			return element;
		} catch (Throwable e) {
			// to be updated
		}
		return element;
	}

	public String sendKeysPopUp(Map<String, String> argsList) {
		APP_LOG.debug("Func:actionMoveToElement");
		// WebElement element;
		try {
			WebElement element = actionFocusElementPopUp();
			element.sendKeys("this is test chat");
			return Constants.PASS + ": Text '" + "this is test chat" + "' is entered in the pop up";
		} catch (Throwable e) {
			APP_LOG.debug(" Func:actionMoveToElement=" + e);
			return Constants.FAIL + ": Unexpected Error while entering text in the POP UP - '"
					+ KeywordConstant.ELEMENT_LOCATOR + "' : " + e.getMessage();
		}

	}
	
}
