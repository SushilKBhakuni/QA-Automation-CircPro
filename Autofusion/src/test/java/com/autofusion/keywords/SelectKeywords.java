package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.autofusion.constants.Constants;

public class SelectKeywords extends Keyword {

	WebElement element;
	String elementId;
	String inputValue;

	public SelectKeywords(Logger log, Map<String, HashMap<String, String>> orMap) {
		APP_LOG = log;
	}

	public SelectKeywords(Logger log) {
		APP_LOG = log;
	}

	/**
	 *  Selects from DropDown the shop location
	 * @param log
	 * @param args
	 */
	public String selectByVisibleText(Map<String, String> argsList) {
		APP_LOG.info("Inside selectDropDown");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			return selectByVisibleText(element, inputValue, elementId);
		} catch (Throwable e) {
			APP_LOG.debug(" DropDown value is not selected" + e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String selectByVisibleText(WebElement element, String inpuString, String elementId) {
		APP_LOG.debug(" selectByVisibleText => " + inpuString);

		try {
			new Select(element).selectByVisibleText(inpuString);
		} catch (Throwable e) {
			APP_LOG.debug(" DropDown value is not selected" + e);
			return Constants.FAIL + ": Unexpected Error while Selecting list Item for - '" + elementId + "' : "
					+ e.getMessage();
		}
		return Constants.PASS + ": List Item- '" + inpuString + "' is selected for element- '" + elementId + "'.";
	}

	public String selectType(Map<String, String> argsList) {
		APP_LOG.info("Inside selecttype");

		/*
		 * elementId = argsList.get("object"); String elementId =
		 * argsList.get("data");
		 */
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			APP_LOG.info(element);
			element.sendKeys(inputValue);
		} catch (Throwable e) {
			APP_LOG.debug(" The type is not selected" + e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Item- '" + inputValue + "' is entered for element- '" + elementId + "'.";
	}

	public  String findvalue(WebElement element, String elementId) {
		APP_LOG.debug("Func:Find Value");
		try {
			String value = element.getText();
			return Constants.PASS + ": Text- '" + value + "' is fetched for element- '" + elementId + "'.";
		} catch (Throwable e) {
			APP_LOG.debug("Func:Static Click Exception=" + e);
			return Constants.FAIL + ": Unexpected error while getting Text for Element - " + elementId + " : "
					+ e.getMessage();
		}
	}

	public String selectvalueFromlist(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			List<WebElement> elements = getElements(elementId);
			System.out.println(elements.size());
			int indexPos = Integer.valueOf(inputValue);
			return findvalue(elements.get(indexPos), elementId);
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	// Nitish - to return list elements
	public List<WebElement> getListItems(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			List<WebElement> elements = getElements(elementId);
			return elements;
		} catch (Throwable e) {
			return elements;
			// return Constants.FAIL + ": Error while finding Element - '" +
			// elementId + "' : " + e.getMessage();
		}
	}

	public String mouseHover(WebElement element, String elementId) {
		APP_LOG.debug("Func:Mouse hover over an element");
		try {
			element.click();
			/*
			 * Actions action = new Actions(webDriver);
			 * action.moveToElement(element).perform();
			 */
		} catch (Throwable e) {
			APP_LOG.debug("Func:Mouse hover over an element=" + e);
			return Constants.FAIL + ": Unexpected error while mouse hovering/clicking Element - " + elementId + " : "
					+ e.getMessage();
		}
		return Constants.PASS + ": Mouse over for Element - '" + elementId + "' is performed and is clicked";
	}

	public String elementOnmouseHover(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		System.out.println("in element on mousehover" + elementId + inputValue);
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			List<WebElement> elements = getElements(elementId);
			System.out.println("size mouse hover" + elements.size());
			int indexPos = Integer.valueOf(inputValue);
			System.out.println("indexPos" + indexPos);
			return mouseHover(elements.get(indexPos), elementId);
		} catch (Exception e) {
			APP_LOG.debug("Func:Mouse hover over an element=" + e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

		// return findvalue(elements.get(indexPos));
	}

	public String selectDropdownValue(Map<String, String> argsList) {
		APP_LOG.info("inside selectDropdownValue");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			Select dropdown = new Select(element);
			dropdown.selectByValue(inputValue);
		} catch (Throwable e) {
			APP_LOG.debug(e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

		return Constants.PASS + ": Text- '" + inputValue + "' is selected for element- '" + elementId + "'.";
	}

	/**
	 *  The option is selected from the dropdown based on the visible text
	 * @param log
	 * @param args
	 */
	public String selectDropdownVisibleText(Map<String, String> argsList) {
		APP_LOG.info("inside selectDropdownVisibleText");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(inputValue);
			// dropdown.selectByValue(elementId);
		} catch (Throwable e) {
			APP_LOG.debug(e);

			// element.sendKeys(inputValue);
			// log.error(e.getMessage());yes
			// System.out.println(e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

		return Constants.PASS + ": List Item- '" + inputValue + "' is selected for element- '" + elementId + "'.";
	}

	public String select(Map<String, String> argsList) {
		APP_LOG.info("inside select");
		elementId = argsList.get("object");
		inputValue = argsList.get("data");

		try {
			element = getElement(elementId);
			element.sendKeys(inputValue);
		} catch (Throwable e) {
			APP_LOG.debug(e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Text- '" + inputValue + "' is entered for element- '" + elementId + "'.";
	}

	/**
	 *  Selection of integer values
	 * @param log
	 * @param args
	 */
	public String selectIntegerValue(Map<String, String> argsList) {
		APP_LOG.info("inside selectIntegerValue");
		elementId = argsList.get("object");
		inputValue = argsList.get("data");

		try {
			int d = (int) Double.parseDouble(inputValue);

			argsList.put("data", String.valueOf(d));

			String result = select(argsList);

			APP_LOG.info("Integer value selected is :" + result);
			return result;
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected error while performing action on - " + elementId + " : "
					+ e.getMessage();
		}
	}

}