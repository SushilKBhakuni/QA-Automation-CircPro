package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;

public class TypeKeywords extends Keyword {

	WebElement element;
	String elementId;
	String inputValue;
	String componentName = "";
	Map<String, String> argsMap = new HashMap<String, String>();
	ClickKeywords ClickKeywords = new ClickKeywords(APP_LOG);

	public TypeKeywords(Logger log) {
		APP_LOG = log;

	}

	public String type(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return type(argsMap);
	}

	// FINAL SIGNATURE
	public String type(Map<String, String> argsList) {
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			APP_LOG.debug("Func: Type|| inputValue=" + inputValue);
			element = getElement(elementId);
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
		return type(element, inputValue, elementId);
	}

	public String typeByJs(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return typeByJs(argsMap);
	}

	public String typeByJs(Map<String, String> argsList) {
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			WebElement element = getElement(elementId);
			return typeByJs(element, inputValue, elementId);
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String typeByJs(WebElement element, String inputString, String elementId) {
		try {

			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();", element);
			js.executeScript("arguments[0].setAttribute('value', '" + inputString + "');", element);

			return Constants.PASS + ": InputText - '" + inputString + "' is typed in Element: '" + elementId;
		} catch (Exception e) {
			return Constants.FAIL + ": Unexpected Error while typing on Element - '" + elementId + "' : "
					+ e.getMessage();
		}
	}

	public String typeAfterClickViaJs(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return typeAfterClickViaJs(argsMap);
	}

	public String typeAfterClickViaJs(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("typeViaJs");

		try {
			element = getElement(elementId);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			String isClicked = ClickKeywords.clickViaJs(element);
			if (isClicked.contains(Constants.PASS)) {
				js.executeScript("arguments[0].setAttribute('value', '" + inputValue + "');", element);

				APP_LOG.debug("typeViaJs:" + inputValue);
				return Constants.PASS + ": InputText - '" + inputValue + "' is typed in Element: '" + elementId;
			} else {
				return isClicked;
			}

		} catch (Exception e) {
			APP_LOG.debug("typeViaJs:" + e);
			return Constants.FAIL + ": Unexpected Error while typing on Element - '" + elementId + "' : "
					+ e.getMessage();
		}
	}

	public String type(WebElement element, String inputString, String elementId) {
		// APP_LOG.debug("KeywordActions TYPE : "+inputString);

		if (!element.equals(null)) {
			try {
				String isCleared = clearTextBox(element, elementId);
				if (isCleared.contains(Constants.PASS)) {
					element.sendKeys(inputString);
					return Constants.PASS + ": InputText - '" + inputString + "' is typed in Element: '" + elementId;
				} else {
					return isCleared;
				}

			} catch (Exception e) {
				return Constants.FAIL + ": Unexpected Error while typing on Element - '" + elementId + "' : "
						+ e.getMessage();
			}
		} else {
			return Constants.FAIL + ": Element is - '" + elementId + "' null ";
		}
	}

	public String typeAfterClear(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return typeAfterClear(argsMap);
	}

	public String typeAfterClear(Map<String, String> argsList) {
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			if (!element.equals(null)) {

				String isCleared = clearTextBox(element, elementId);
				if (isCleared.contains(Constants.PASS)) {
					return type(element, inputValue, elementId);
				} else {
					return isCleared;
				}
			} else {
				return Constants.FAIL + ": Element is - '" + elementId + "' null ";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String typeAfterClick(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return typeAfterClick(argsMap);
	}

	public String typeAfterClick(Map<String, String> argsList) {
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("KeywordActions TYPE : " + inputValue);

		try {
			element = getElement(elementId);
			if (!element.equals(null)) {
				String isClicked = ClickKeywords.click(element, elementId);
				if (isClicked.contains(Constants.PASS)) {
					return type(element, inputValue, elementId);
				} else {
					return isClicked;
				}
			} else {
				return Constants.FAIL + ": Element is - '" + elementId + "' null ";
			}

		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

	public String clearTextBox(WebElement element, String elementId) {
		/// APP_LOG.debug("clearTextBox : "+inputString);

		if (!element.equals(null)) {
			try {
				element.clear();
				return Constants.PASS + ": Element text box is - '" + elementId + "' Cleared ";
			} catch (WebDriverException e) {
				return Constants.PASS + ": Error while clearing Element - '" + elementId + "' text box. ";
			}
		} else {

			return Constants.FAIL + ": Element is - '" + elementId + "' null ";
		}
	}

	public String typeViaJs(String locator, String inputValue) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return typeViaJs(argsMap);
	}

	public String typeViaJs(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("typeViaJs");
		try {
			element = getElement(elementId);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			String isClicked = ClickKeywords.clickByJs(element, elementId);
			if (isClicked.contains(Constants.PASS)) {
				js.executeScript("arguments[0].setAttribute('value', '" + inputValue + "');", element);

				APP_LOG.debug("typeViaJs:" + inputValue);
				return Constants.PASS + ": InputText - '" + inputValue + "' is typed in Element: '" + elementId;
			} else {
				return isClicked;
			}

		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

}
