package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;

public class Keyword extends PerformAction {

	Connection conn = null;
	String elementId;
	WebElement element;
	List<WebElement> elements;
	String inputValue;

	/**
	 * @desc checks whether the element is present or not
	 * @param locatorId
	 * @throws InterruptedException
	 */
	public String isElementPresent(String locatorId) {
		APP_LOG.debug("Func: isElementPresent");
		try {
			List<WebElement> element = getElements(locatorId);
			APP_LOG.info("element is " + element);
			if (!element.get(0).equals(null)) {
				return Constants.PASS + ": Element - '" + locatorId + "' is present on UI";
			} else {
				return Constants.FAIL + ": Element - '" + locatorId + "' is not present on UI";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + locatorId + "' : " + e.getMessage();
		}

	}

	public String isAlertPresent() {
		APP_LOG.debug("Func: isAlertPresent");
		try {
			WebDriver webDriver = getWebDriver();
			webDriver.switchTo().alert();
			return Constants.PASS + ": Alert is found and Switched";
		} catch (NoAlertPresentException e) {
			APP_LOG.debug("Func: isAlertPresent Exception" + e);
			return Constants.FAIL + ": Alert is not present - : " + e.getMessage();
		} catch (Exception e) {
			APP_LOG.debug("Func: isAlertPresent Exception" + e);
			return Constants.FAIL + ": Unexpected error while finding Alert with Exception - : " + e.getMessage();
		}
	}

	public String acceptAlert(String args[]) {
		try {
			WebDriver webDriver = getWebDriver();
			String isAlertPresent = isAlertPresent();
			if (isAlertPresent.contains(Constants.PASS)) {
				Alert alert = webDriver.switchTo().alert();
				alert.accept();
				return Constants.PASS + ": Alert is found and Switched.";
			} else {
				return isAlertPresent;
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected error while finding Alert with Exception - : " + e.getMessage();
		}
	}

	public String getNumberOfElementInList(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			elements = getElements(elementId);

			return Integer.toString(elements.size());
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - " + elementId + " : " + e.getMessage();
		}
	}

	
}
