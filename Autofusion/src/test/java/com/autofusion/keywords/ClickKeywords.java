package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.autofusion.BaseClass;
import com.autofusion.constants.Constants;
import com.ncs.page.CircPro_Login;

@SuppressWarnings({"unused","all"})
public class ClickKeywords extends Keyword {

	public String locatorId = "";
	public String inputValue = "";
	WebElement element;
	Map<String, String> argsMap = new HashMap<String, String>();

	public ClickKeywords(Logger log) {
		APP_LOG = log;
	}

	public String click(String locator) {
		argsMap.put("ElementId", locator);
		argsMap.put("InputValue", inputValue);

		return click(argsMap);
	}

	/**
	 * 
	 * @param args
	 *            args0 : Object
	 * @return
	 * @throws InterruptedException
	 */
	public String click(Map<String, String> argsList) {
		APP_LOG.debug("Func:Click");
		elementId = argsList.get("ElementId");
		String Inputvalue  = argsList.get("InputValue");
		WebElement element;
		try {
			element = getElement(elementId);
			return click(element, elementId);
		} catch (Exception e) {
			APP_LOG.debug("Func:Click Exception:" + e);
			return Constants.FAIL + ": Error while finding Element - " + elementId + " : " + e.getMessage();
		}
	}

	public String click(WebElement element, String elementId) {
		APP_LOG.debug("Func:Static Click");
		try {
			element.click();
			
		} catch (Throwable e) {
			APP_LOG.debug("Func:Static Click Exception=" + e);
			return Constants.FAIL + ": Unexpected Error while clicking Element - '" + elementId + "' : "
					+ e.getMessage();
		}
		return Constants.PASS + ": Element - '" + elementId + "' is clicked";
	}

	/**
	 * 
	 * @param args
	 *            Args0 : Object argsList.get("InputValue") : wait
	 * @return
	 * @throws InterruptedException
	 */
	public String clickAndWait(Map<String, String> argsList) throws InterruptedException {
		APP_LOG.info("Func:clickAndWait");

		elementId = argsList.get("ElementId");
		try {
			WebDriver webDriver = getDriver();
			// webDriver.findElement(By.xpath("(//div[contains(@class,'radioButtonsGroup')])/div[2]")).click();;

			WebElement element = getElement(elementId);
			if (element.equals(null)) {
				APP_LOG.info("Func:clickAndWait || Element is Null");
				return Constants.FAIL + ": Element is - '" + elementId + "' null";
			}

			if (webDriver instanceof InternetExplorerDriver) {
				Actions action = new Actions(webDriver);
				action.moveToElement(element).perform();
			}

			click(element, argsList.get("ElementId"));
			Long wait = Long.parseLong(argsList.get("InputValue"));
			APP_LOG.info("Func:clickAndWait || wait for =" + wait);
			Thread.sleep(wait);
		} catch (Throwable e) {
			APP_LOG.debug("Func:clickAndWait || Exception : " + e);
			return Constants.FAIL + ": Error while finding Element - '" + elementId + "' : " + e.getMessage();
		}
		return Constants.PASS;
	}

	/**
	 * This function is to perform double click
	 * 
	 * @param object
	 * @param data
	 * @return
	 * @throws InterruptedException
	 */
	public String doubleClick(Map<String, String> argsList) throws InterruptedException {
		APP_LOG.debug("Func: doubleClick ");
		WebDriver webDriver = getDriver();
		element = getElement(argsList.get("ElementId"));
		Actions action = new Actions(webDriver);
		action.doubleClick(element).perform();
		return Constants.PASS;
	}

	public String clickByJs(WebElement element, String elementId) {
		try {
			WebDriver webDriver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();", element);

			return Constants.PASS + ": Element - '" + elementId + "' is clicked";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while Clicking Elmenet - '" + elementId + "' by JS: "
					+ e.getMessage();
		}
	}

	public String clickByJs(Map<String, String> argsList) throws InterruptedException {
		APP_LOG.debug("Func:clickByJs");
		String elementId = argsList.get("ElementId");

		try {
			WebElement element = getElement(elementId);
			if (element == null) {
				APP_LOG.debug("Func:clickByJs Element is Null");
				return Constants.FAIL + ": Element - '" + elementId + "' is null";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while Finding Element - '" + elementId + "' by JS: " + e.getMessage();
		}
		return clickByJs(element, elementId);
	}

	public String clickControlandPrint(String object, String data) {
		APP_LOG.debug("Func:clickControlandPrint");
		try {
			WebDriver webDriver = getDriver();
			Actions actionObject = new Actions(webDriver);
			actionObject.keyDown(Keys.CONTROL).sendKeys("p").keyUp(Keys.CONTROL).perform();
			return Constants.PASS;
		} catch (Exception e) {
			APP_LOG.debug("Func:clickControlandPrint" + e);
			return Constants.FAIL;
		}
	}

	// clickAskaQuestion - old name /data-->SiteCatalyst
	public String clickHoldingModifierKey(Map<String, String> argsList) {
		String object = argsList.get("ElementId");
		String data = argsList.get("InputValue");

		APP_LOG.debug("Func clickHoldingModifierKey");
		WebDriver webDriver = getDriver();
		WebElement element = getElement(object);
		Actions builder = new Actions(webDriver);
		builder.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL);

		Action execute = builder.build();
		execute.perform();

		return Constants.PASS;
	}

	public String clickViaJs(WebElement element) {
		try {
			WebDriver webDriver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();", element);

			return Constants.PASS;
		} catch (Exception e) {
			return Constants.FAIL;
		}
	}

	public String clickViaJs(Map<String, String> argsList) {
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("clickViaJs");
		try {
			element = getElement(locatorId);
			return clickViaJs(element);

		} catch (Exception e) {
			APP_LOG.debug("clickViaJs:" + e);
			return Constants.FAIL;
		}
	}

	public String clickCheckBoxInList(Map<String, String> argsList) {
		APP_LOG.info("inside clickCheckBox");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		element = getElement(locatorId);
		try {
			WebDriver webDriver = getDriver();
			List<WebElement> chkbox = webDriver.findElements(By.tagName("label"));
			for (int i = 0; i < chkbox.size(); i++) {
				if (chkbox.get(i).getText().equals(inputValue)) {
					chkbox.get(i).click();
					break;
				}
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while findng Element - '" + locatorId + "' : " + e.getMessage();
		}
		return Constants.PASS + ": CheckBox in the list for Element '" + locatorId + "' is clicked";
	}

	/**
	 *  Returns the result of the link clicked
	 * @param log
	 * @param args
	 */
	public String clickLink(Map<String, String> argsList) {
		APP_LOG.info("inside clickLink");
		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		String res = click(argsList);
		APP_LOG.info("The click link is :" + res);
		return res;
	}

	public String clickOnText(Map<String, String> argsList) {

		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);
		try {
			List<WebElement> elements = getElements(locatorId);

			for (int i = 0; i < elements.size(); i++) {
				elements.get(i).getText();
				if (inputValue.equals(elements.get(i).getText())) {

					return click(elements.get(i), argsList.get("ElementId"));
				}
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Element - " + locatorId + " : " + e.getMessage();
		}
		return Constants.FAIL + ": Unexpected error Error while performing action on Element - '" + locatorId + "' ";
	}

	public String clickIndexPosition(Map<String, String> argsList) {

		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);
		try {
			List<WebElement> elements = getElements(locatorId);
			System.out.println(elements.size());

			int indexPos = Integer.valueOf(inputValue);
			return click(elements.get(indexPos), argsList.get("ElementId"));
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while finding Elements - " + locatorId + " : " + e.getMessage();
		}
	}

	public String clickAllElementsInList(Map<String, String> argsList) {
		APP_LOG.info("inside clickCheckBox");
		locatorId = argsList.get("ElementId");
		try {
			List<WebElement> list = getElements(locatorId);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).click();
				break;

			}
		} catch (Throwable e) {
			APP_LOG.debug(e);
			// log.error(e.getMessage());
			// System.out.println(e);
			return Constants.FAIL + ": Error while findng Element - '" + locatorId + "' : " + e.getMessage();
		}

		return Constants.PASS + ": All Element on the list is Clicked for Element '" + "'" + locatorId;
	}

	public String clickContentDescFromList(Map<String, String> argsList) {

		locatorId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			List<WebElement> elementIdList = getElements(locatorId);
			String data = argsList.get(1);
			for (int i = 0; i < elementIdList.size(); i++) {
				String lstItem = elementIdList.get(i).getAttribute("name").trim();
				// System.out.println(lstItem);
				if (lstItem.equalsIgnoreCase(data)) {
					elementIdList.get(i).click();
					return Constants.PASS + ": Element on the list for Element '" + locatorId
							+ "' is clicked at index: " + i;
				} else {
					return Constants.FAIL + ": Element on the list for Element '" + locatorId
							+ "' is not clicked at index: " + i;
				}
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while findng Element - '" + locatorId + "' : " + e.getMessage();
		}
		return Constants.FAIL + ": Unexpected Error while clicking content in list";
	}
	
	public String clickterminate(Map<String, String> argsList){
		elementId = argsList.get("ElementId");
		element = getElement(elementId);
		WebDriver webDriver = getDriver();
		String result = null;
		try {
		boolean result1 = false;
		try {
			result1=element.isDisplayed();
			if(result1)
			result="PASS: Element on UI- 'Terminate' is displayed";
			//BaseClass.logResultInReport(result, "Previous session Terminated",reportTestObj);
		}
		catch(Exception e)
		{
			System.out.println("Terminate not clicked");
		}
		if(result1)
		{
			click(element, elementId);
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
			
		}
		}
		catch(Exception e) {
			return Constants.FAIL + ": Error while findng Element - '" + locatorId + "' : " + e.getMessage();
		}
		return Constants.PASS + "Element found" + locatorId;
	}
	
}
