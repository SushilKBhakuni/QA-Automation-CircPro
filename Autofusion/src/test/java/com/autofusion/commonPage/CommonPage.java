package com.autofusion.commonPage;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.autofusion.BaseClass;
import com.autofusion.ResourceConfigurations;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;
import com.autofusion.keywords.FindElement;
import com.autofusion.keywords.PerformAction;
import com.autofusion.util.Accessibility;
import com.relevantcodes.extentreports.ExtentTest;

@SuppressWarnings("all")
public class CommonPage extends BaseClass implements KeywordConstant {

	Logger APP_LOGS;
	ExtentTest reportTestObj;
	public static String result = "";
	public static String stepDescription = "";
	private PerformAction performAction = new PerformAction();
	private FindElement findElement = new FindElement();
	private JavascriptExecutor js;

	public CommonPage(ExtentTest reportTestObj, Logger APP_LOGS) {
		this.APP_LOGS = APP_LOGS;
		this.reportTestObj = reportTestObj;
		// BaseClass.runningComponentName = "OR";
	}

	/**
	 * @description Verify element is clickable
	 * @return
	 */
	public void verifyElementIsClickable(String elememtLocator, String stepDesc) {
		result = performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE, elememtLocator);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description to check accessibility issues using
	 * @return null
	 */

	public CommonPage testAccessibility() {
		try {
			APP_LOGS.debug("Verify Acessabilty issues on Sign-on Page");
			Accessibility accessibilty = new Accessibility();
			List<String> getAccessibilityErrors = accessibilty.runAcopChecks();
			ListIterator<String> itr = getAccessibilityErrors.listIterator();
			if (itr.hasNext()) {
				logResultInReport(itr.next(),
						"Accesabilty result as per Web Content Accessibility Guidelines 2.0 & Sect 508", reportTestObj);
			} else {
				logResultInReport("PASS, No voilation found on the page",
						"Accesabilty result as per Web Content Accessibility Guidelines 2.0 & Sect 508", reportTestObj);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new CommonPage(reportTestObj, APP_LOGS);
	}

	/**
	 * @description Click on Tab
	 */
	public void verifyClickOnElement(String element, String stepDesc) {
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE, element);
		result = performAction.execute(ACTION_CLICK, element);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description Verify the content of listed tabs
	 */
	public void verifyGetTabContent(String element, String tabName, String contentValue) {
		String content = ResourceConfigurations.getProperty(contentValue);

		if (content.contains(Constants.FAIL)) {

			result = content;

			logResultInReport(result, "Error while comparing UserList data with WebList data", reportTestObj);

			return;
		}

		try {
			String[] searchContent = content.split("\\|");
			List<WebElement> tabRealContent = findElement.getElements(element);
			for (int i = 0; i < tabRealContent.size(); i++) {
				if (tabRealContent.get(i).getText().equalsIgnoreCase(searchContent[i])) {
					result = Constants.PASS + ": Expected UserText: '" + searchContent[i]
							+ "' in list matches with actualText '" + tabRealContent.get(i).getText() + "' in WebList";
					logResultInReport(result, "Verify " + searchContent[i] + " is displayed", reportTestObj);
				} else {
					result = Constants.FAIL + ": Expected UserText: '" + searchContent[i]
							+ "' in list doesn't matches with actualText '" + tabRealContent.get(i).getText()
							+ "' in WebList";
					;
					logResultInReport(result, "Verify " + searchContent[i] + " is displayed", reportTestObj);
				}
			}
		} catch (Throwable e) {

			result = Constants.FAIL + "Error while comparing UserList data with WebList data because of: "
					+ e.getMessage();

			logResultInReport(result, "Error while comparing UserList data with WebList data", reportTestObj);

		}
	}

	/**
	 * @description Verify "Search" is displayed as the placeholder.
	 */
	public void verifyElementAttributeValue(String element, String attributeName, String verifyText, String stepDesc) {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put(ACTION_TO_PERFORM, ACTION_VERIFY_ATTRIBUTE_VALUE);
		dataMap.put(ELEMENT_LOCATOR, element);
		dataMap.put(COMPONENT_NAME, attributeName);
		dataMap.put(ELEMENT_INPUT_VALUE, verifyText);
		result = performAction.execute(dataMap);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description Verify Element Present/display
	 * @return Object
	 */
	public void verifyElementPresent(String element, String stepDesc) {
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		result = performAction.execute(ACTION_VERIFY_ELEMENT_PRESENT, element);
		logResultInReport(result, stepDesc, reportTestObj);

	}

	/**
	 * @description Verify Element Text
	 */
	public void verifyElementText(String element, String text, String stepDesc) {
		APP_LOGS.debug(stepDesc);
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, element, text);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description Enter Valid Data in the Search Text Box.
	 */
	public void enterInputData(String element, String text, String testDesc) {
		result = performAction.execute(ACTION_TYPE_AFTER_CLEAR, element, text);
		logResultInReport(result, testDesc, reportTestObj);
	}

	/**
	 * @description Clear Session storage
	 */
	public void clearSessionStorage() {
		js = (JavascriptExecutor) getDriver();
		js.executeScript(String.format("window.sessionStorage.clear();"));

	}

	/**
	 * @description Clear Local storage
	 */
	public void clearLocalStorage() {

		js = (JavascriptExecutor) getDriver();
		js.executeScript(String.format("window.localStorage.clear();"));

	}

	/**
	 * @description To verify Status Dropdown Values and functionality
	 */

	public void selectDropdownValue(String element, String text, String stepDesc) {
		APP_LOGS.debug(stepDesc);
		result = performAction.execute(ACTION_SELECT_DROPDOWN_VISIBLE_TEXT, element, text);
		// result = performAction.execute(ACTION_VERIFY_TEXT_CONTAINS, element,
		// text);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description Enter Valid Data in the Search Text Box.
	 */
	public void verifyText(String element, String text, String stepDesc) {
		APP_LOGS.debug(stepDesc);
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		result = performAction.execute(ACTION_VERIFY_TEXT, element, text);
		logResultInReport(result, stepDesc, reportTestObj);
	}

	/**
	 * @description Get text from any element
	 */
	public String getText(String element) {
		performAction.execute(ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE, element);
		String valueText = performAction.execute(ACTION_GET_TEXT, element);
		return valueText;
	}

	/**
	 * @description To verify version field is not editable when adding a new
	 *              resource
	 * @return Object
	 */

	public void verifyNotEditable(String elementLocator, String stepDesc) {

		result = performAction.execute(ACTION_VERIFY_ELEMENT_IS_NOT_EDITABLE, elementLocator);
		logResultInReport(result, stepDesc, reportTestObj);

	}

	/**
	 * @description To verify status Dropdown default Value is Public
	 */

	public void verifyDefaultValueSelected(String dropDown, String defaultValue, String stepDesc) {

		String result = performAction.execute(ACTION_VERIFY_ISSELECTED, dropDown, defaultValue);
		logResultInReport(result, stepDesc, reportTestObj);

	}

	/**
	 * @description Verify Element Not Present/display
	 */
	public void verifyElementNotPresent(String element, String stepDesc) {

		result = performAction.execute(ACTION_VERIFY_ELEMENT_NOT_PRESENT, element);
		logResultInReport(result, stepDesc, reportTestObj);

	}

	/**
	 * @description To verify button is disabled or enabled
	 */
	public void verifyButtonDisabled(String elementLocator, String value, String stepDesc) {

		String result = performAction.execute(ACTION_VERIFY_ISENABLED, elementLocator, value);
		logResultInReport(result, stepDesc, reportTestObj);

	}

	public void verifyFocusOnElement(String element) {
		String result = performAction.execute(ACTION_VERIFY_IS_ELEMENT_FOCUSED, element);
		logResultInReport(result, "Verify focus on element : " + element, reportTestObj);
	}

	/**
	 * @description Get field width.
	 */
	public int verifyFieldWidth(String element) {
		int elementWidth = 0;
		try {
			WebElement element1 = findElement.getElement(element);
			elementWidth = element1.getSize().getWidth();
		} catch (Throwable e) {

			result = Constants.FAIL + " : Could not get the width of the field because of : " + e.getMessage();
		}
		return elementWidth;

	}

	/**
	 * @description To select each dropdown value one by one
	 */

	public void selectAllDropdownValuesOneByOne(String elementLocator, String dropdownValues,
			String expectedDropdownSize) {

		int expectedDropdownLength = Integer.parseInt(expectedDropdownSize);
		String[] values = dropdownValues.split("\\|");

		int count = values.length;
		if (count == expectedDropdownLength) {

			result = Constants.PASS + ": Expected dropdown values count and values passed are same";
			logResultInReport(result, "Dropdown length and values matches", reportTestObj);

			for (int i = 0; i < expectedDropdownLength; i++) {
				selectDropdownValue(elementLocator, dropdownValues.split("\\|")[i],
						"Verify " + dropdownValues.split("\\|")[i] + " value is selected");

			}
		} else {
			result = Constants.FAIL + ": Expected dropdown values count and values passed are not same";

			logResultInReport(result, "Dropdown length and values are not same", reportTestObj);
		}

	}

}
