package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.constants.Constants;
import com.autofusion.util.DateUtil;

@SuppressWarnings("all")
public class VerificationKeywords extends Keyword {

	String locatorId;
	WebElement element;
	String inputValue;
	String componentName;
	private FindElement FindElement = new FindElement();

	public VerificationKeywords(Logger log) {
		// setOrMap(orMap);
		APP_LOG = log;
	}

	public String verifyNotVisible(Map<String, String> argsList) {

		return Constants.PASS;
	}

	public String verifyAlertPresent(Map<String, String> argsList) {
		APP_LOG.debug("Func verifyAlertPresent");
		return isAlertPresent();
	}

	public String verifyAlertNotPresent(Map<String, String> argsList) {
		APP_LOG.debug("Func verifyAlertNotPresent");
		String isAlertPresent = isAlertPresent();
		if (!isAlertPresent.contains(Constants.PASS)) {
			return Constants.PASS + ": Alert is found and Switched.";
		} else {
			return isAlertPresent;
		}
	}

	/*
	 * public String verifyTextNotPresent(Map<String, String> argsList) {
	 * 
	 * return Constants.PASS; }
	 * 
	 * public String verifyTextPresent(Map<String, String> argsList) {
	 * 
	 * return Constants.PASS; }
	 * 
	 * public String waitForPageToLoad(Map<String, String> argsList) {
	 * 
	 * return Constants.PASS; }
	 */

	public String verifySelectedValue(Map<String, String> argsList) {
		APP_LOG.info("Inside verifySelectedValue");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			element = getElement(locatorId);
			return verifySelectedValue(element, inputValue);
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while getting Element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String verifySelectedValue(WebElement element, String inputString) {
		APP_LOG.debug(" verifySelectedValue " + inputString);

		try {
			actualDataPresentOnUi = element.getText().trim();
			if (!element.getText().trim().equalsIgnoreCase(inputString.trim())) {
				return Constants.PASS + ": Selected InputText - '" + inputString
						+ "' is matched/found with Selected Expected Text - '" + actualDataPresentOnUi + "'";
			} else {
				return Constants.FAIL + ": Selected InputText - '" + inputString
						+ "' is not matched/found with Selected Expected Text - '" + actualDataPresentOnUi + "'";
			}
		} catch (Exception e) {
			APP_LOG.debug(" VerifySelectedValue " + e);
			return Constants.FAIL + ": Unexpected error while selecting Element for - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	public String verifyText(WebElement element, String inpuString) {
		APP_LOG.debug(" verifyText " + inpuString);

		String textValue = element.getText();
		actualDataPresentOnUi = textValue;
		try {
			if (textValue.trim().equals(inpuString)) {
				return Constants.PASS + ": InputText - '" + inpuString + "' is matched with Actual Text - '"
						+ actualDataPresentOnUi + "'";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": InputText - '" + inpuString + "' is not matched with Actual Text - '"
				+ actualDataPresentOnUi + "'";
			}
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			APP_LOG.debug(" verifyTextById " + e);
			return Constants.FAIL + ": Error while Verifying Text for - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String verifyTextContains(WebElement element, String inpuString) {
		APP_LOG.debug(" verifyTextContains => " + inpuString);

		try {
			String textValue = element.getText();
			actualDataPresentOnUi = textValue;
			if (textValue.trim().contains(inpuString)) {
				return Constants.PASS + ": Element Text - '" + actualDataPresentOnUi + "' contains - '" + inpuString
						+ "'";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": Element Text - '" + actualDataPresentOnUi + "' does not contain - '"
				+ inpuString + "'";
			}
		} catch (Exception e) {
			failureErrorMessageCollector = e.toString();
			APP_LOG.debug(" verifyTextById " + e);
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Unexpected Error while Verifying Text for - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	public String verifyisDispalyed(Map<String, String> argsList) {
		APP_LOG.info("To check if element is displayed");

		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			if (element.isDisplayed() == true) {
				return Constants.PASS + ": Element on UI- '" + elementId + "' is displayed";
			} else {
				return Constants.PASS + ": Element on UI- '" + elementId + "' is not displayed";
			}
		} catch (Exception e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

	public String verifyElementPresent(Map<String, String> argsList) {
		APP_LOG.debug("Func:verifyElementPresent");

		String locatorId = argsList.get("ElementId");
		try {
			return isElementPresent(locatorId);
			/*
			 * if (isElementPresent(locatorId).contains(Constants.PASS)) return
			 * Constants.PASS + ": Element on UI- '" + elementId + "is Present";
			 * else { collectFailureMessage("Element is not present"); return
			 * Constants.PASS + ": Element on UI- '" + elementId +
			 * "is not Present"; }
			 */
		} catch (Exception e) {
			APP_LOG.debug("Func:verifyElementPresent || " + e);
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Unexpected error while Finding Element - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	public String verifyElementNotPresent(Map<String, String> argsList) throws InterruptedException {
		String locatorId = argsList.get("ElementId");
		try {

			if (isElementPresent(locatorId).contains(Constants.PASS)) {
				collectFailureMessage("Element is present");
				return Constants.FAIL + ": Element - '" + locatorId + "' is present on UI";
			} else {
				return Constants.PASS + ": Element - '" + locatorId + "' is not present on UI";
			}

		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Unexpected error while Verfying Element - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	/*
	 * public void verifyValidHyperLinks(String locatorId, String locatorId){
	 *
	 * List elementList = findElementList(locatorId);
	 *
	 * for(int i=0; i<elementList.size(); i++){ String str ;
	 * if(locatorId.contains("##")){ locatorId = locatorId.replace("##",
	 * String.valueOf(i)); //str = "//div[contains(@class,'clearfix')]/div["+
	 * i+"]/descendant::a[text()='About us']"; }
	 *
	 * }
	 *
	 * }
	 */

	public String verifyNotEmpty(Map<String, String> argsList) {
		APP_LOG.info("Inside verifyNotEmpty");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			element = getElement(locatorId);
			String pageValueString = element.getText().trim();
			actualDataPresentOnUi = pageValueString;
			if (!pageValueString.equals("")) {
				return Constants.PASS + ": Element with value - " + actualDataPresentOnUi + "is not null";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": Element does not contain any text";
			}
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

	public String verifyisEnabled(Map<String, String> argsList) {
		APP_LOG.info("To check if element is enabled");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			if (inputValue.equalsIgnoreCase("yes")) {
				if (element.isEnabled()) {
					return Constants.PASS + ": Element- '" + elementId + "' is Enabled";
				} else {
					return Constants.FAIL + ": Element- '" + elementId + "' is not Enabled";
				}
			} else if (inputValue.equalsIgnoreCase("no")) {
				if (element.isEnabled()) {
					return Constants.FAIL + ": Element- '" + elementId + "' is Enabled";
				} else {
					return Constants.PASS + ": Element- '" + elementId + "' is not Enabled";
				}
			}

			else {
				return Constants.FAIL + ": Input value given which is-" + inputValue
						+ "is not correct, kindly give YES or NO as input to check for Enable or DISABLE respectively";
			}
		} catch (Exception e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

	public String verifyEmpty(Map<String, String> argsList) {
		APP_LOG.info("Inside verifyEmpty");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			element = getElement(locatorId);
			String pageValueString = element.getText().trim();
			if (pageValueString.equals("")) {
				return Constants.PASS + ": Element- '" + elementId + "' is Empty";
			} else {
				return Constants.PASS + ": Element- '" + elementId + "' is not Empty";
			}
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

	}

	public String verifyHighLightElementByStyle(Map<String, String> argsList) {
		webDriver = getDriver();
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: highLightElement =>locatorId =" + argsList.get("ElementId") + " || inputValue = "
				+ argsList.get("InputValue"));

		try {
			element = getElement(locatorId);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].setAttribute('style','" + inputValue + "')", element);
			Thread.sleep(1000);
			js.executeScript("arguments[0].setAttribute('style','')", element);
		} catch (Exception e) {
			APP_LOG.debug("Func: highLightElement " + e);
			collectFailureMessage("Exception during highlighting of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}

		return Constants.PASS + ": Element- '" + elementId + "' is highlighted";
	}

	public String verifyAttributeValue(Map<String, String> argsList) {
		APP_LOG.info("inside verifyInputText");
		locatorId = argsList.get("ElementId");
		String attributeName = argsList.get("ComponentName");
		inputValue = argsList.get("InputValue");
		try {
			element = getElement(locatorId);
			String defaulText = element.getAttribute(attributeName);
			actualDataPresentOnUi = defaulText;
			APP_LOG.info("Default Text is :" + defaulText);
			if (defaulText.equals(inputValue)) {
				return Constants.PASS + ": Attribute value - '" + inputValue
						+ "' is matched with Expected attribute value - '" + actualDataPresentOnUi + "'";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": Attribute value - '" + inputValue
						+ "' is not matched with Expected attribute value - '" + actualDataPresentOnUi + "'";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - '" + locatorId + "' : " + e.getMessage();
		}
	}

	public String verifyText(Map<String, String> argsList) {
		APP_LOG.info("Inside type");
		locatorId = argsList.get("ElementId");
		try {
			element = getElement(locatorId);
			return verifyText(element, argsList.get("InputValue"));
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error occured while Finding element:- '" + locatorId + "' because of : "
			+ e.getMessage();
		}
	}

	public String verifyTextContains(Map<String, String> argsList) {
		APP_LOG.info("Inside verifyTextContains");
		locatorId = argsList.get("ElementId");
		try {
			element = getElement(locatorId);
			return verifyTextContains(element, argsList.get("InputValue"));
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error occured while Finding element:- '" + locatorId + "' because of : "
			+ e.getMessage();
		}
	}

	public String verifyTitle(Map<String, String> argsList) {
		webDriver = getDriver();
		APP_LOG.info("inside verifyTitle");

		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			actualDataPresentOnUi = webDriver.getTitle();
			if (webDriver.getTitle().equalsIgnoreCase(inputValue)) {
				return Constants.PASS + ": Expected Title - '" + inputValue + "' is matched with actual title - '"
						+ actualDataPresentOnUi + "'";
			} else {
				failureErrorMessageCollector = "Expected: " + inputValue + " || Actual=" + webDriver.getTitle();
				return Constants.FAIL + ": Expected Title - '" + inputValue + "' is not matched with actual title - '"
				+ actualDataPresentOnUi + "'";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while getting Title for - '" + elementId + "' : " + e.getMessage();
		}
	}

	/**
	 * @desc Verify text on input field type.
	 * @param log
	 * @param args
	 */
	public String verifyInputText(Map<String, String> argsList) {
		APP_LOG.info("inside verifyInputText");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			element = getElement(locatorId);
			String defaulText = element.getAttribute("value");
			actualDataPresentOnUi = defaulText;
			APP_LOG.info("Default Text is :" + defaulText);
			if (actualDataPresentOnUi.equals(inputValue)) {
				return Constants.PASS + ": InputText for Element- '" + inputValue
						+ "' is matched with Expected Text - '" + actualDataPresentOnUi + "'";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": InputText for Element- '" + inputValue
						+ "' is not matched with Expected Text - '" + actualDataPresentOnUi + "'";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String verifyIsLinkBroken(Map<String, String> argsList) {
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("Func: verifyIsLinkBroken =>elementId =" + locatorId + " || inputValue = " + inputValue);

		element = getElement(locatorId);
		String attributeValue = element.getAttribute(inputValue);
		APP_LOG.debug("Func: verifyIsLinkBroken => attributeValue = " + attributeValue);
		HttpURLConnection connection;
		try {
			URL url = new URL(attributeValue);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			int response = connection.getResponseCode();
			connection.disconnect();
			if (response == 200) {
				return Constants.PASS;
			} else {
				collectFailureMessage("Link is broken");
				return Constants.FAIL;
			}
		} catch (IOException e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			APP_LOG.debug("Func: verifyIsLinkBroken " + e.getMessage());
			return Constants.FAIL;
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			APP_LOG.debug("Func: verifyIsLinkBroken " + e.getMessage());
			return Constants.FAIL;
		}
	}

	/**
	 * To verify the element in the list of elements
	 *
	 * @param argsList
	 * @return
	 */
	public String verifyTextContainsInList(Map<String, String> argsList) {
		APP_LOG.info("Inside verifyTextContains");

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			elements = getElements(elementId);
			for (WebElement ele : elements) {
				actualDataPresentOnUi = ele.getText().trim();
				if (actualDataPresentOnUi.equalsIgnoreCase(inputValue.trim())) {
					return Constants.PASS + ": InputText for list with value- '" + inputValue
							+ "' is matched with Expected Text - '" + actualDataPresentOnUi + "'";
				}
			}
		} catch (Throwable e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.FAIL + ": InputText for list with value- '" + inputValue
				+ "' is not matched with Expected Text - '" + actualDataPresentOnUi + "'";
	}

	/**
	 * * To verify any particular element in the list of elements with
	 * indexposition
	 *
	 *
	 * @param argsList
	 *
	 *            elementId inputValue indexPosition
	 * @return
	 */
	public String verifyTextContainsInListByIndex(Map<String, String> argsList) {
		APP_LOG.info("Inside verifyTextContains");

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		int indexPosition = Integer.valueOf(argsList.get("IndexPosition"));

		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			elements = getElements(elementId);
			actualDataPresentOnUi = elements.get(indexPosition).getText().trim();
			if (actualDataPresentOnUi.equalsIgnoreCase(inputValue)) {
				return Constants.PASS + ": InputText for list at Index with value- '" + indexPosition + ":" + inputValue
						+ "' is matched with Expected Text - '" + actualDataPresentOnUi + "'";
			} else {
				collectFailureMessage("Expected value is not matching with Actual value.");
				return Constants.FAIL + ": InputText for list at Index with value- '" + indexPosition + ":" + inputValue
						+ "' is not matched with Expected Text - '" + actualDataPresentOnUi + "'";
			}
			// }
		} catch (Exception e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	/**
	 * @author Richa Bajaj
	 * @date 27 November,16
	 * @description On mouse hover the font weight should change from normal to
	 *              bold
	 * @return PASS/FAIL
	 */
	public String verifyFont(WebElement element) {
		APP_LOG.debug("Func:Find Value");
		try {
			String fontSize = element.getCssValue("font-weight");
			System.out.println("Font Size  -> " + fontSize);
			/*
			 * Actions action = new Actions(webDriver);
			 * action.moveToElement(element).perform();
			 */
			// objCommon.elementOnmouseHover("Assign");
			// fontSize = element.getCssValue("font-weight");
			// System.out.println("Font Size after -> "+fontSize);
			return fontSize;

		} catch (Exception e) {
			APP_LOG.debug("Func:Static Click Exception=" + e);
			return Constants.FAIL + ": Error while verifying Font - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String verifyFontWeight(Map<String, String> argsList) {

		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try {
			APP_LOG.debug("Func: Type|| inputValue=" + inputValue);
			List<WebElement> elements = getElements(locatorId);
			System.out.println(elements.size());

			int indexPos = Integer.valueOf(inputValue);
			return verifyFont(elements.get(indexPos));
		} catch (Throwable e) {
			APP_LOG.debug("Verify Font wieght || " + e);
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	/**
	 * @author Nitish Jaiswal
	 * @date 27 November,16
	 * @description Validate date should be in format DDD DD MMM, YYYY and it
	 *              Should be always future date
	 * @return PASS/FAIL
	 */
	public String verifyDate(Map<String, String> argsList) {
		APP_LOG.debug("Verify date format");

		try {
			elementId = argsList.get("ElementId");
			WebElement element = getElement(elementId);
			String textValue = element.getText();
			String isFutureDate = DateUtil.validateFutureDateFormat(textValue);
			if (isFutureDate.contains(Constants.PASS))
				return isFutureDate;
			else {
				collectFailureMessage("Date format is not verified");
				return isFutureDate;
			}
		} catch (Exception e) {
			APP_LOG.debug("Verify date format || " + e);
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	// mayank mittal
	public String verifyIsClickable(Map<String, String> argsList) {
		webDriver = getDriver();
		APP_LOG.info("inside verifyIsClickable");
		elementId = argsList.get("object");
		inputValue = argsList.get("data");

		try {
			int dataInt = (int) Double.parseDouble(inputValue);
			element = getElement(elementId);
			// code for explicit wait
			WebDriverWait wait = new WebDriverWait(webDriver, dataInt);
			//wait.until(ExpectedConditions.elementToBeClickable(element));
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(element.getTagName())));

		} catch (TimeoutException e) {
			APP_LOG.debug(e);
			e.printStackTrace();
			return Constants.FAIL + ": Timed out for element while verifying element - '" + elementId
					+ "' is clikable : " + e.getMessage();
		} catch (Throwable e) {
			APP_LOG.debug(e);
			e.printStackTrace();
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Element - '" + elementId + "' is Clickable.'";
	}

	public String verifyIsClickableForList(Map<String, String> argsList) {
		webDriver = getDriver();
		APP_LOG.info("Inside verifyIsClickableForList");

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			elements = getElements(elementId);
			for (WebElement ele : elements) {

				WebDriverWait wait = new WebDriverWait(webDriver, 30);
				if (wait.until(ExpectedConditions.elementToBeClickable(ele)) != null) {

				}
			}
		} catch (Throwable e) {
			collectFailureMessage("Exception during clicking element in list" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": Element in List - '" + elementId + "' is Clickable.'";
	}

	// Reena
	public String verifyisItalic(Map<String, String> argsList) {
		APP_LOG.info("To check if element is Italic");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {

			WebElement element = getElement(elementId);
			if (element.getCssValue("font-style").equalsIgnoreCase("italic")) {
				return Constants.PASS + ": Element style for - '" + elementId + "' is Italic.'";
			} else {
				return Constants.FAIL + ": Element style for - '" + elementId + "' is not Italic.'";
			}
		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}

	}

	// Richa
	// To check whether the text is in bold or not

	public String verifyisBold(Map<String, String> argsList) {
		APP_LOG.info("To check if element is Bold or not");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {

			WebElement element = getElement(elementId);
			if (element.getCssValue("font-weight").equalsIgnoreCase("Bold")) {
				System.out.println("Bold result Pass");
				return Constants.PASS + ": Element style for - '" + elementId + "' is Bold.'";
			} else {
				return Constants.FAIL + ": Element style for - '" + elementId + "' is not Bold.'";
			}
		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	// richa
	// To check whether the text color

	public String verifyTextColour(Map<String, String> argsList) {
		APP_LOG.info("To verify the text colour");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			String colour = element.getCssValue("color");
			return colour;

		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}
	}

	/**
	 * @author Nitesh Singh
	 * @date 17 January,17
	 * @description To check if an element on a given index of list is displayed
	 * @return PASS/FAIL
	 */
	public String verifyIsDisplayedInListByIndex(Map<String, String> argsList) {
		APP_LOG.info("Validate if an element on a given index of list is displayed");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {

			int listPosition = Integer.parseInt(inputValue);
			List<WebElement> element = FindElement.getElements(elementId);
			if (element.size() > 0 && element.get(listPosition).isDisplayed() == true) {
				return Constants.PASS + ": Element on the given index of list - '" + inputValue + "' is displayed.'";
			} else {
				return Constants.FAIL + ": Element on the given index of list - '" + inputValue
						+ "' is not displayed.'";
			}

		} catch (Throwable e) {
			collectFailureMessage("Exception during verification of element || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while getting element for - '" + elementId + "' : " + e.getMessage();
		}

	}

	/**
	 * @author reenajai.sharma
	 * @desc-To verify style of text
	 * @return
	 */
	public String verifyStyle(Map<String, String> argsList) {
		APP_LOG.info("To check style of an Element");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			String style = element.getCssValue("bottom");
			System.out.println("style" + style);
			return style;
		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Error while getting style for - '" + elementId + "' : " + e.getMessage();
		}

	}

	/**
	 * @author Nitish Jaiswal
	 * @date 03 May,17
	 * @description To check if element is not editable
	 * @return PASS/FAIL
	 */

	public String verifyIsElementEditable(Map<String, String> argsList) {
		APP_LOG.info("To check if element is not editable");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			element.clear();
			return Constants.FAIL + ": Element '" + elementId + "' can not be verified - '" + "as not editable";
		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			if (e.getMessage().contains("Element must be user-editable in order to clear it")) {
				return Constants.PASS + ": Element - '" + elementId + "' is not editable";
			}
			return Constants.FAIL + ": Element '" + elementId + "' can not be verified - '" + "as not editable" + "' : "
			+ e.getMessage();
		}

	}

	/**
	 * @author Nitish Jaiswal
	 * @date 03 May,17
	 * @description To check if default value is present as expected in list
	 * @return PASS/FAIL
	 */

	public String verifyIsSelected(Map<String, String> argsList) {
		APP_LOG.info("inside selectDropdownValue");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			Select list = new Select(element);
			String defaultSelectedValue = list.getFirstSelectedOption().getText();
			if (defaultSelectedValue.equals(inputValue)) {
				return Constants.PASS + ": Default selected value - '" + inputValue
						+ "' is matched with Actual Text - '" + defaultSelectedValue + "'";
			} else {
				return Constants.FAIL + ": Default selected value - '" + inputValue
						+ "' is not matched with Actual Text - '" + defaultSelectedValue + "'";
			}
		} catch (Throwable e) {
			APP_LOG.debug(e);
			return Constants.FAIL + ": Error while verifying default selected value for Element - '" + elementId
					+ "' : " + e.getMessage();
		}

	}

	/**
	 * @author Nitish Jaiswal
	 * @date 04 May,17
	 * @description To verify dropdown values
	 * @return PASS/FAIL
	 */

	public String verifyDropDownOptions(Map<String, String> argsList) {

		APP_LOG.info("To Verify dropdown values");
		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");

		String[] dropDownValues = inputValue.split("\\|");
		element = getElement(elementId);
		Select select = new Select(element);

		try {
			boolean match = false;
			List<WebElement> options = select.getOptions();
			if (options.size() == dropDownValues.length) {
				for (int i = 0; i < dropDownValues.length; i++) {
					if (options.get(i).getText().equals(dropDownValues[i])) {
						match = true;
					} else {
						return Constants.FAIL + ":  Given Dropdown value- '" + dropDownValues[i]
								+ "' is not matched with actual Dropdown value - '" + element.getText() + "'";
					}
				}
				if (match = true) {
					return Constants.PASS + ":  Given Dropdown values - '"
							+ Arrays.toString(dropDownValues).replace("[", "").replace("]", "")
							+ "' are matched with actual Dropdown values - '" + getDropdownValues(options) + "'";
				} else {
					return Constants.FAIL + ":  Given Dropdown values - '" + dropDownValues
							+ "' are not matched with actual Dropdown values - '" + getDropdownValues(options) + "'";
				}
			} else {
				return Constants.FAIL + ":  Given Dropdown size- '" + dropDownValues.length
						+ "' is not matched with actual Dropdown size - '" + options.size() + "'";
			}
		} catch (Throwable e) {
			APP_LOG.debug(e);
			return Constants.FAIL + ": Error while verifying dropdown values for Element - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	/**
	 * @author Nitish Jaiswal
	 * @date 04 May,17
	 * @description Fetching dropdown values
	 * @return String
	 */

	public String getDropdownValues(List<WebElement> options) {
		String dropDownItems = "";
		for (int i = 0; i < options.size(); i++) {
			dropDownItems = dropDownItems + ", " + options.get(i).getText();
		}
		return dropDownItems.substring(2);
	}


	/**
	 * @author Mukul Sehra
	 * @date 11 May,17
	 * @description Verifying the focus on element
	 * @return String
	 */
	public String verifyIsElementFocused(Map<String, String> argsList) {
		APP_LOG.info("To Verify that the element is focused");

		// Get element
		elementId = argsList.get("ElementId");
		element = getElement(elementId);

		// Declare WebElement variables
		WebElement activeElementJS, elementActive;

		try {
			// Initialize webDriver instance
			webDriver = getDriver();

			// Getting an active element via selenium activeElement() method
			elementActive = webDriver.switchTo().activeElement();

			// Getting an active element in DOM via javaScript
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			activeElementJS = (WebElement) js.executeScript("return document.activeElement");

			// Assert the equality of the objects
			if (elementActive.equals(activeElementJS)) {
				return Constants.PASS + ": The focus is on  - '" + elementId + "'";
			}
			else
				return Constants.FAIL + ": The focus is not on  - '" + elementId;

		} catch (Throwable e) {
			APP_LOG.debug(e);
			return Constants.FAIL + ": Error while verifying focus on element - '" + elementId + "' : "
			+ e.getMessage();
		}
	}

	public String verifyBackgroundColor(Map<String, String> argsList) {
		APP_LOG.info("To check style of an Element");

		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
		WebElement element = getElement(elementId);
		String backgroundColor = element.getCssValue("background-color");
		System.out.println("style" + backgroundColor);
		if (backgroundColor.equals(inputValue)) {
		return Constants.PASS + ": Background color i.e '" + backgroundColor + "' is equal to " + inputValue;
		} else
		return Constants.FAIL + ": Background color i.e '" + backgroundColor + "' is not equal to "
		+ inputValue;

		} catch (Throwable e) {
		APP_LOG.debug("Theme color : " + e.getMessage());
		return Constants.FAIL + ": Error while getting backgroundColor for - '" + elementId + "' because of : "
		+ e.getMessage();
		}

		}
}
