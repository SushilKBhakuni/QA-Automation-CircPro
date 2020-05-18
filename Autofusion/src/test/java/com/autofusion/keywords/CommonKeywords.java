package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.constants.Constants;

@SuppressWarnings("all")
public class CommonKeywords extends Keyword {
	public Map<String, String> presistanceMap = new HashMap<String, String>();
	String elementId;
	WebElement element;
	List<WebElement> elements;
	String inputValue;
	private String locatorId;
	private FindElement FindElement = new FindElement();

	public CommonKeywords(Logger log, Map<String, HashMap<String, String>> orMap) {
		APP_LOG = log;
	}

	public CommonKeywords(Logger log) {
		APP_LOG = log;
	}
	/*
	 * public void createDriverObj(Map<String,String> argsList){
	 * createDriver(argsList); }
	 */

	public String open(Map<String, String> argsList) throws Exception {

		WebDriver webDriver = getWebDriver();// argsList.get(Constants.EXECUTION_ON_DEVICE)
		if (webDriver == null) {
			return Constants.FAIL;
		} else {
			return Constants.PASS;

		}
	}

	/**
	 * @author reenajai.sharma implemented for mouseHover
	 *
	 */
	public String mouseHoverOnMoveToElement(Map<String, String> argsList) {

		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		APP_LOG.debug("Func: Type|| inputValue=" + inputValue);

		try {
			List<WebElement> elements = getElements(elementId);
			int indexPos = Integer.valueOf(inputValue);
			return mouseHoverMoveToElement(elements.get(indexPos), elementId);
		} catch (Throwable e) {
			return Constants.FAIL + ": Error while hovering on Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String mouseHoverMoveToElement(WebElement element, String elementId) {
		APP_LOG.debug("Func: Mouse hover over an element");
		try {
			WebDriver webDriver = getDriver();
			Actions action = new Actions(webDriver);
			action.moveToElement(element).perform();
			return Constants.PASS + ": Mouse Hovering of element- " + elementId + " is done.";
		} catch (Throwable e) {
			APP_LOG.debug("Func:Mouse hover over an element=" + e);
			return Constants.FAIL + ": Unexpected Error while Hovering for element - '" + elementId + "' : "
					+ e.getMessage();
		}
	}

	public String getListItemOnIndex(Map<String, String> argsList) {
		APP_LOG.info("Fetching the list item corresponding to given index");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			int listPosition = Integer.parseInt(inputValue);
			List<WebElement> element = FindElement.getElements(elementId);
			if (element.size() > 0) {
				String eleText = element.get(listPosition).getText().trim();
				return eleText;
			} else {
				return Constants.FAIL + ":Element - '" + elementId + "' does not contain any value and is null";
			}

		} catch (Throwable e) {
			collectFailureMessage("Exception during getting text of list item || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while finding an Element - '" + elementId + "' : " + e.getMessage();
		}
	}

	public String waitForTitle(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
			webDriverWait.until(ExpectedConditions.titleIs(argsList.get("InputValue")));
			APP_LOG.info("Wait For Loading the Page:" + inputValue);
			return Constants.PASS + " : Page is loaded and Page title is present.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while waiting for title of Page- '" + e.getMessage();
		}

	}

	public String explicitWait(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			elementId = argsList.get("ElementId");
			element = getElement(elementId);
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
			webDriverWait.until(ExpectedConditions.visibilityOf(element));
			APP_LOG.info("Wait For Loading the Page:" + inputValue);
			return Constants.PASS + " : Page is loaded";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while waiting for element- '" + e.getMessage();
		}

	}

	public String elementIsVisible(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			elementId = argsList.get("ElementId");
			element = getElement(elementId);
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver);
			wait.withTimeout(10000, TimeUnit.MILLISECONDS);
			wait.pollingEvery(250, TimeUnit.MILLISECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			APP_LOG.info("Wait For visiblity of element:" + inputValue);
			return Constants.PASS + "Element '" + elementId + "' : is visible.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while checking visiblity of element- '" + e.getMessage();
		}

	}

	public String waitTillEementIsNotVisible(Map<String, String> argsList) {

		try {
			WebDriver webDriver = getDriver();
			elementId = argsList.get("ElementId");
			List<WebElement> element = getElements(elementId);
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver);
			wait.withTimeout(10000, TimeUnit.MILLISECONDS);
			wait.pollingEvery(250, TimeUnit.MILLISECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.invisibilityOfAllElements(element));
			APP_LOG.info("Wait For Element to be invisible");
			return Constants.PASS + "Element '" + elementId + "' : is not visible.";
		} catch (Throwable e) {
			APP_LOG.info("Wait For visiblity of element:" + inputValue);
			return Constants.FAIL + ": Unexpected Error while waiting for visiblity of element- '" + e.getMessage();
		}
	}

	public String elementIsClickable(Map<String, String> argsList) {
		try {

			WebDriver webDriver = getDriver();
			elementId = argsList.get("ElementId");
			element = getElement(elementId);
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver);
			wait.withTimeout(10000, TimeUnit.MILLISECONDS);
			wait.pollingEvery(250, TimeUnit.MILLISECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			APP_LOG.info("Wait For Loading the Page:" + inputValue);
			return Constants.PASS + ": Element '" + elementId + "' : is clickable.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while checking element '" + elementId + "' : is clickable- '"
					+ e.getMessage();
		}
	}

	public String waitForLoadingPage(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			Long waitTime = Long.valueOf(argsList.get("InputValue"));
			webDriver.manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
			APP_LOG.debug("Apply Wait For Loading Page");
			return Constants.PASS + ": Page is loaded.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while waiting for element- '" + e.getMessage();
		}

	}

	public String implicitWait(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			Long waitTime = Long.valueOf(argsList.get("InputValue"));
			webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
			APP_LOG.debug("Apply Implicit wait");
			return Constants.PASS + "Page is loaded.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while waiting for element- '" + e.getMessage();
		}
	}

	public ExpectedCondition<Boolean> angularHasFinishedProcessing() {

		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String hasAngularFinishedScript = "var callback = arguments[arguments.length - 1];\n"
						+ "var el = document.querySelector('html');\n" + "if (!window.angular) {\n"
						+ "    callback('false')\n" + "}\n" + "if (angular.getTestability) {\n"
						+ "    angular.getTestability(el).whenStable(function(){callback('true')});\n" + "} else {\n"
						+ "    if (!angular.element(el).injector()) {\n" + "        callback('false')\n" + "    }\n"
						+ "    var browser = angular.element(el).injector().get('$browser');\n"
						+ "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n" + "}";

				JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
				String isProcessingFinished = javascriptExecutor.executeAsyncScript(hasAngularFinishedScript)
						.toString();

				return Boolean.valueOf(isProcessingFinished);
			}
		};
	}

	public String angularWaitForLoadingPage(Map<String, String> argsList) {
		try {
			WebDriver webDriver = getDriver();
			Long waitTime = Long.valueOf(argsList.get("InputValue"));
			WebDriverWait webDriverWait = new WebDriverWait(webDriver, waitTime);
			webDriverWait.until(angularHasFinishedProcessing());
			APP_LOG.info("Wait For Loading the Page:" + inputValue);
			return Constants.PASS + "Page is loaded.";
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while waiting for element- '" + e.getMessage();
		}

	}

	/**
	 * @desc Sends the keys which are entered
	 * @param log
	 * @param args
	 * @throws InterruptedException
	 */
	public String enterKey(Map<String, String> argsList) throws InterruptedException {
		elementId = argsList.get("ElementId");

		APP_LOG.info("Inside enterKey");
		element = getElement(elementId);

		if (!element.equals(null)) {
			try {
				element.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				APP_LOG.debug("Inside enterKey : " + e);
				return Constants.FAIL;
			}
		} else {
			return Constants.FAIL;
		}
		return Constants.PASS;
	}

	public String compareSavedValue(Map<String, String> argsList) {
		try {
			elementId = argsList.get("ElementId");
			inputValue = argsList.get("InputValue");
			APP_LOG.info("Compare values");
			String savedValue = "";

			if (!getValueFromMemory(inputValue).equals("")) {
				element = getElement(elementId);
				String currentValue = element.getText().toString().trim();
				if (savedValue.equalsIgnoreCase(currentValue)) {
					return Constants.PASS + " : Values '" + currentValue + "' and '" + savedValue
							+ "'  are successfully compared.";
				} else {
					return Constants.FAIL + " : Values '" + currentValue + "' and '" + savedValue
							+ "'  are not successfully compared.";
				}
			} else {
				return Constants.FAIL;
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while comparing saved values '" + e.getMessage();
		}

	}

	public String getText(Map<String, String> argsList) {
		APP_LOG.info("Inside type");
		locatorId = argsList.get("ElementId");
		try {
			element = getElement(locatorId);
			return getText(element);
		} catch (Throwable e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Unexpected Error while getting text.'" + e.getMessage();
		}
	}

	public String getText(WebElement element) {
		APP_LOG.debug("Func:Find Value");
		try {
			String value = element.getText();
			return value;
		} catch (Throwable e) {
			APP_LOG.debug("Func:Static Click Exception=" + e);
			return Constants.FAIL;
		}
	}

	public String saveUiValue(Map<String, String> argsList) {
		APP_LOG.info("Inside type");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		element = getElement(elementId);
		if (element != null) {
			String pageValueString = element.getText().trim();
			setValueInMemory(inputValue, pageValueString);
			return Constants.PASS + "UI value is saved successfully.";
		} else {
			return Constants.FAIL + "UI value is not saved successfully.";
		}

	}

	public String getValueFromMemory(String key) {
		String savedValue = "";

		APP_LOG.debug("Func getValueFromMemory : key=" + key);

		if (presistanceMap.containsKey(key)) {
			savedValue = presistanceMap.get(key);
		}
		return savedValue;
	}

	public String setValueInMemory(String key, String value) {
		APP_LOG.debug("saveValueInMemory : key=" + key + " || value=" + value);
		try {
			if (!key.equals("")) {
				presistanceMap.put(key, value);
				return Constants.PASS + "Value is set in memory.";
			} else {
				return Constants.FAIL + "Value is not set in memory.";
			}
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while setting value in memory.'" + e.getMessage();
		}

	}

	public void getViewSource() {
		APP_LOG.debug("/******************************/");
		WebDriver webDriver = getDriver();
		try {
			String pageSource = webDriver.getPageSource();
			APP_LOG.debug("/******************************/");
			APP_LOG.debug(pageSource);
			APP_LOG.debug("/******************************/");
		} catch (Exception e) {

		}
	}

	public String acceptAlert(Map<String, String> argsList) {
		WebDriver webDriver = getDriver();
		String isAlertPresent = isAlertPresent();
		if (isAlertPresent.contains(Constants.PASS)) {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
			return Constants.PASS + ": Alert is found and accepted.";
		} else {
			return isAlertPresent;
		}
	}

	public String focus() {
		APP_LOG.info("in focusOnUI");
		WebDriver webDriver = getDriver();
		webDriver = getDriver();
		try {
			webDriver.switchTo().activeElement();
			// Switch to currently active element in a page
		} catch (Throwable t) {
			return Constants.FAIL;
		}
		return Constants.PASS;
	}

	public String closeBrowser() {
		APP_LOG.info("in closeBrowser");
		WebDriver webDriver = getDriver();
		try {
			webDriver.quit();
			return Constants.PASS + "Browser is closed successfully.";
		} catch (Throwable e) {
			APP_LOG.debug("Unable to close the  browser");
			// log.error("Unable to close the browser",e);
			return Constants.FAIL + " Browser is not closed.";
		}
	}

	public String closeWindow(Map<String, String> argsList) {
		APP_LOG.info("in closeWindow");
		WebDriver webDriver = getDriver();
		try {
			webDriver.close();
			return Constants.PASS + "Browser is closed successfully.";
		} catch (Throwable e) {
			APP_LOG.debug("Unable to close the  browser");
			return Constants.FAIL + " Browser is not closed.";
		}
	}

	
	public String navigateBrowserBack(Map<String, String> argsList) {
		APP_LOG.info("Navigate Browser Back");
		WebDriver webDriver = getDriver();
		try {
			webDriver.navigate().back();
			webDriver.navigate().refresh();
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			return Constants.PASS + "Navigate back";
		} catch (Exception e) {
			APP_LOG.debug("Unable to Navigate to page");
			return Constants.FAIL + "Back Button is not Navigated";
		}
	}

	public String navigateToUrl(Map<String, String> argsList) {

		WebDriver webDriver = getDriver();
		try {
			APP_LOG.info("Navigate Browser Back");
			inputValue = argsList.get("InputValue");
			webDriver.navigate().to(inputValue);
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			return Constants.PASS + "navigated to specific URL";
		} catch (Exception e) {
			APP_LOG.debug("Unable to Navigate to page");
			return Constants.FAIL + "Not navigated to specific URL.";
		}
	}

	public String verifyisDispalyed(Map<String, String> argsList) {
		APP_LOG.info("To check if element is displayed");
		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			if (element.isDisplayed() == true) {
				return Constants.PASS + "Element '" + elementId + "' is displayed.";
			} else {
				return Constants.FAIL + "Element '" + elementId + "' is not displayed.";
			}
		} catch (Exception e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Unexpected Error while setting value in memory.'" + e.getMessage();
		}

	}

	// nitish - to check if element is selected
	public String verifyisSelected(Map<String, String> argsList) {
		APP_LOG.info("To check if element is selected");
		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			WebElement element = getElement(elementId);
			if (element.isSelected() == true) {
				return Constants.PASS + "Element '" + elementId + "' is selected.";
			} else {
				return Constants.FAIL + "Element '" + elementId + "' is not selected.";
			}
		} catch (Exception e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + ": Unexpected Error while setting value in memory.'" + e.getMessage();
		}

	}

	/*
	 * public static String closeBrowser() { APP_LOG.info("in closeBrowser");
	 * try { webDriver.quit(); return Constants.PASS; } catch (Exception e) {
	 * APP_LOG.debug("Unable to close the  browser"); // log.error(
	 * "Unable to close the browser",e); return Constants.FAIL; } }
	 */

	public String switchBrowserWindow(Map<String, String> argsList) {
		APP_LOG.info("inside switchBrowserWindow");
		WebDriver webDriver = getDriver();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			APP_LOG.debug("InterruptedException");
			e.printStackTrace();
		}
		String parentHandle = webDriver.getWindowHandle();
		APP_LOG.info("parentHandle");
		System.out.println("parent " + parentHandle);
		boolean childWindow = false;
		for (String winHandle : webDriver.getWindowHandles()) {
			APP_LOG.info("winHandle");
			System.out.println("WindowHandle" + winHandle);
			
			if(!argsList.get("InputValue").equals("") && !winHandle.equals(parentHandle)) {
				APP_LOG.debug("Window Title"+webDriver.getTitle());
				webDriver.switchTo().window(winHandle);
				System.out.println("Window Title"+webDriver.getTitle());

				if(webDriver.getTitle().equals(argsList.get("InputValue"))){
					childWindow = true;
					///webDriver.switchTo().window(winHandle);
					break;
				}
			}else{
				if (!winHandle.equals(parentHandle)) {
					childWindow = true;
					webDriver.switchTo().window(winHandle);
					break;
				}
			}
		}
		if (childWindow) {
			APP_LOG.info("Window Switch Pass");
			System.out.println("Window Switch Pass");
			return Constants.PASS;
		} else {
			APP_LOG.info("Window Switch Fail");
			System.out.println("Window Switch Fail");
			return Constants.FAIL;
		}

	}

	
	/**
	 * @desc Keyword added for uploading of the file component
	 * @param log
	 * @param args
	 */
	public String uploadFile(Map<String, String> argsList) {
		APP_LOG.info("inside uploadFile");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			element = getElement(elementId);
			element.sendKeys(inputValue);
		} catch (Throwable e) {
			APP_LOG.debug(e);
			// log.error(e.getMessage());
			// System.out.println(e);
			return Constants.FAIL;
		}
		return Constants.PASS;
	}

	/**
	 * @desc Adds the delay value
	 * @param log
	 * @param args
	 */
	public String addDelay(Map<String, String> argsList) {
		APP_LOG.info("inside addDelay");
		String delay = argsList.get("ElementId");
		Long delayinLong = Long.parseLong(delay);
		try {
			Thread.sleep(delayinLong);
		} catch (InterruptedException e) {
			APP_LOG.debug(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.FAIL;
		}
		return Constants.PASS;
	}

	/**
	 * @desc Explicitly waits for the element to be visible and clickable
	 * @param log
	 * @param args
	 */
	/*
	 * public String waitForElementPresence(Map<String, String> argsList){
	 * APP_LOG.info("inside addDelayForElementPresence");
	 * elementId=argsList.get("ElementId");
	 * inputValue=argsList.get("InputValue");
	 *
	 * int dataInt = (int) Double.parseDouble(inputValue); element =
	 * getElement(elementId); try { // code for explicit wait WebDriverWait wait
	 * = new WebDriverWait(webDriver, dataInt);
	 * wait.until(ExpectedConditions.elementToBeClickable(element));
	 * wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(element
	 * .getTagName())));
	 *
	 * String[] keyValue = elementIdType(elementId, "");
	 *
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(keyValue[1
	 * ])));
	 *
	 * } catch (TimeoutException e) { APP_LOG.debug(e); // TODO Auto-generated
	 * catch block e.printStackTrace(); return Constants.FAIL; } return
	 * Constants.PASS; }
	 */

	/**
	 * @desc Switches to a particular frame
	 * @param log
	 * @param args
	 */
	public String switchToFrameOLD(Map<String, String> argsList) {
		APP_LOG.info("inside switchToFrame");
		WebDriver webDriver = getDriver();
		elementId = argsList.get("ElementId");
		try {
			// webDriver.switchTo().frame(webDriver.findElement(By.cssSelector("#ContentPageInterior1_pageBody>span>iframe")));
			//webDriver.switchTo().defaultContent();
			webDriver.switchTo().frame(getElement(elementId));

		} catch (Exception e) {
			APP_LOG.debug("Switch frame exception");
			// log.error("Switch frame exception",e);
			return Constants.FAIL + "Frame is not switched.";
		}
		return Constants.PASS + "Frame is switched.";
	}

	/**
	 * @desc Switches out of that particular frame
	 * @param log
	 * @param elementId
	 */
	public String switchOutFrame(Map<String, String> argsList) {
		APP_LOG.info("inside switchoutFrame");
		WebDriver webDriver = getDriver();
		try {
			webDriver.switchTo().defaultContent();

		} catch (Exception e) {
			APP_LOG.debug("Switch frame exception");
			// log.error("Switch frame exception",e);
			return Constants.FAIL;
		}
		return Constants.PASS;
	}


	public String uploadFileAutoIt(Map<String, String> argsList) {
		APP_LOG.debug("Func: executeAutoItScript ");
		inputValue = argsList.get("InputValue");
		String[] batchArgument={argsList.get("projectPath")+"\\autoItScripts\\UploadFile.exe",inputValue};
		try {
			java.lang.Runtime.getRuntime().exec(batchArgument);
			Thread.sleep(2000);

			return Constants.PASS;
		} catch (IOException e) {
			APP_LOG.debug("Func: executeAutoItScript || Exception:" + e);
			return Constants.FAIL;
		} catch (InterruptedException e) {
			APP_LOG.debug("Func: executeAutoItScript || Exception:" + e);
			return Constants.FAIL;
		}
	}

	
	
/*	public String executeAutoItScript(Map<String, String> argsList) {
		APP_LOG.debug("Func: executeAutoItScript ");
		inputValue = argsList.get("InputValue");

		try {
			java.lang.Runtime.getRuntime().exec(inputValue);
			Thread.sleep(2000);

			return Constants.PASS;
		} catch (IOException e) {
			APP_LOG.debug("Func: executeAutoItScript || Exception:" + e);
			return Constants.FAIL;
		} catch (InterruptedException e) {
			APP_LOG.debug("Func: executeAutoItScript || Exception:" + e);
			return Constants.FAIL;
		}
	}
*/
	/**
	 * This function is to perform the mouse hover
	 *
	 * @param argsList
	 * @return
	 */
	public String mouseHover(Map<String, String> argsList) {
		APP_LOG.info("Inside dateParameter");
		WebDriver webDriver = getDriver();
		inputValue = argsList.get("InputValue");
		elementId = argsList.get("ElementId");
		try {
			long temp_data = Integer.parseInt(inputValue);

			Actions actions = new Actions(webDriver);
			WebElement menuHoverLink = getElement(elementId);
			actions.moveToElement(menuHoverLink);
			Action a = actions.moveToElement(menuHoverLink).build();
			Thread.sleep(temp_data);
			a.perform();
			Thread.sleep(temp_data);
			
			return Constants.PASS + "Element with '" + elementId + "' is hovered.";
		} catch (Throwable e) {
			APP_LOG.debug("Inside dateParameter : " + e.getMessage());
			return Constants.FAIL + "Element with '" + elementId + "' is not hovered." + e.getMessage();
		}

	}
	


	public String getSubstring(Map<String, String> argsList) {
		APP_LOG.info("Fetching the substring from the given string");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			String spiltValues[] = inputValue.split("\\|");
			int startIndex = Integer.parseInt(spiltValues[0]);
			int endIndex = Integer.parseInt(spiltValues[1]);
			element = getElement(elementId);
			String eleText = element.getText().trim();
			eleText = eleText.substring(startIndex, endIndex);
			return eleText;
		} catch (Throwable e) {
			collectFailureMessage("Exception during getting substring of text || Exception:" + e.getMessage());
			return Constants.FAIL + "";
		}
	}

	public String mouseHoverOnElement(Map<String, String> argsList) {
		try {
			elementId = argsList.get("ElementId");
			inputValue = argsList.get("InputValue");
			System.out.println("in element on mousehover" + elementId + inputValue);
			APP_LOG.debug("Func: Type|| inputValue=" + inputValue);
			List<WebElement> elements = getElements(elementId);
			System.out.println("size mouse hover" + elements.size());
			int indexPos = Integer.valueOf(inputValue);
			System.out.println("indexPos" + indexPos);
			return mouseHover(elements.get(indexPos));
		} catch (Throwable e) {
			return Constants.FAIL + ": Unexpected Error while hovering on an element.'" + e.getMessage();
		}

	}

	public String mouseHover(WebElement element) {
		APP_LOG.debug("Func:Mouse hover over an element");

		try {
			WebDriver webDriver = getDriver();
			Actions action = new Actions(webDriver);
			action.moveToElement(element).build().perform();
			return Constants.PASS;
			

		} catch (Throwable e) {
			APP_LOG.debug("Func:Mouse hover over an element=" + e);
			return Constants.FAIL;
		}

	}

	public String getListSubstring(Map<String, String> argsList) {
		APP_LOG.info("Fetching the substring from the given of the list string");
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");

		try {
			String spiltValues[] = inputValue.split("\\|");
			int startIndex = Integer.parseInt(spiltValues[0]);
			int endIndex = Integer.parseInt(spiltValues[1]);
			int listPosition = Integer.parseInt(spiltValues[2]);
			List<WebElement> element = FindElement.getElements(elementId);

			if (element.size() > 0) {
				String eleText = element.get(listPosition).getText().trim();
				eleText = eleText.substring(startIndex, endIndex);
				return eleText;
			} else {
				return Constants.FAIL;
			}

		} catch (Throwable e) {
			collectFailureMessage("Exception during getting substring of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Unexpected Error while getting substring from List.'" + e.getMessage();
		}
	}

	/**
	 * Drag a sourceElement and drop it over the destinationElement.
	 * 
	 * @author : Mukul Sehra
	 * @param argsList
	 * @return Pass/Fail
	 */
	public String dragAndDrop(Map<String, String> argsList) {
		APP_LOG.debug("Func:actionDragNdrop");

		WebElement sourceElement, destinationElement;
		sourceElement = getElement(argsList.get("ElementId"));
		destinationElement = getElement(argsList.get("ElementId1"));
		WebDriver webDriver = getDriver();

		// DragAndDrop using Actions class
		try {
			Actions action = new Actions(webDriver);
			action.dragAndDrop(sourceElement, destinationElement).perform();
		} catch (Exception e) {
			APP_LOG.debug(" Func:actionDragNdrop = " + e);
			return Constants.FAIL + ": Error while getting Element - " + sourceElement + "or" + destinationElement
					+ " : " + e.getMessage();
		}
		return Constants.PASS + "Source element - '" + sourceElement
				+ "' has been dragged on dropped over Destination Element" + destinationElement + ".";
	}

	/**
	 * Right click the webElement to open the context menu.
	 * 
	 * @author : Mukul Sehra
	 * @param argsList
	 * @return Pass/Fail
	 */
	public String rightClick(Map<String, String> argsList) {
		APP_LOG.debug("Func:actionRightClick");

		WebElement element;
		element = getElement(argsList.get("ElementId"));
		WebDriver webDriver = getDriver();

		// RightClick on the webElement
		try {
			Actions action = new Actions(webDriver);
			action.contextClick(element).perform();
		} catch (Exception e) {
			APP_LOG.debug(" Func:actionRightClick = " + e);
			return Constants.FAIL + ": Error while getting Element - '" + element + " : " + e.getMessage();
		}
		return Constants.PASS + "Element - " + element + ".";

	}

	/**
	 * Scroll into view of a particular element on the page.
	 * 
	 * @author : Pallavi Tyagi
	 * @return Pass/Fail
	 */
	public String scrollIntoView(Map<String, String> argsList) {
		APP_LOG.debug("Func:actionScrollIntoView");

		WebElement element;
		element = getElement(argsList.get("ElementId"));
		webDriver = getDriver();
		// scroll into view using javascript
		try {

			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView()", element);
		} catch (Exception e) {
			APP_LOG.debug(" Func:actionRightClick = " + e);
			return Constants.FAIL + ": Error while getting Element - '" + element + " : " + e.getMessage();
		}
		return Constants.PASS + "Element - " + element + ".";

	}

	/**
	 * Press Enter Key.
	 * 
	 * @author : Pallavi Tyagi
	 * @return Pass/Fail
	 */
	public String pressEnterKey(Map<String, String> argsList) {
		APP_LOG.debug("Func:pressEnterKey");
		WebDriver webDriver = getDriver();

		try {
			Actions action = new Actions(webDriver);
			action.sendKeys(Keys.ENTER).perform();

		} catch (Exception e) {
			APP_LOG.debug(" Func:pressEnterKey = " + e);
			return Constants.FAIL + " : " + e.getMessage();
		}
		return Constants.PASS;

	}

	/**
	 * Press Tab Key.
	 * 
	 * @author : Mehak Verma
	 * @return Pass/Fail
	 */
	public String pressTabKey(Map<String, String> argsList) {
		APP_LOG.debug("Func:pressTabKey");
		WebDriver webDriver = getDriver();

		try {
			Actions action = new Actions(webDriver);
			action.sendKeys(Keys.TAB).perform();

		} catch (Exception e) {
			APP_LOG.debug(" Func:pressTabKey = " + e);
			return Constants.FAIL + " : " + e.getMessage();
		}
		return Constants.PASS;

	}

	public String countElementPresent(Map<String, String> argsList) throws InterruptedException{
		elementId = argsList.get("ElementId");
		inputValue = argsList.get("InputValue");
		try
		{
			
			List<WebElement> element = FindElement.getElements(elementId);
			int user_datasize=0;
			int Size=element.size();
			
			if(inputValue.matches("\\d+"))
			{
				user_datasize=Integer.parseInt(inputValue);
			}
			APP_LOG.debug("Func:countElementPresent--Size:"+Size+"---User Defined Size:"+user_datasize);
			System.out.println("Size:"+Size+"---User Defined Size:"+user_datasize);
			if(Size==user_datasize)
			{
				for(int l=0;l < user_datasize;l++)
				{
					if(element.get(l).isDisplayed())
					{
						System.out.println("Element is displayed");
					}
					else
					{
						return Constants.FAIL;
					}
				}
				return Constants.PASS;
			}
			else
			{
				return Constants.FAIL;
			}}catch(Exception e)
			{
				return Constants.FAIL;
			}
	}

	public String refreshpage(Map<String, String> argsList) {
		webDriver.navigate().refresh();
		return Constants.PASS;
		}


	/**
	 * This function is to open openExternalUrl 
	 * @param object
	 * @param data - url append
  	 * @return
	 * @throws Exception 
	 */
	public String openExternalUrl(Map<String, String> args) throws Exception{

		elementId = args.get("ElementId");
		inputValue= args.get("InputValue");
		
		APP_LOG.info("openExternalUrl ");  
		
		if(getWebDriver()==null||getWebDriver().equals("")){
			open(args);
			System.out.println("new browser");
		}
		System.out.println(inputValue);
		getWebDriver().get(inputValue);
		try {
			getWebDriver().manage().window().maximize();
		}catch (Exception e) {
			APP_LOG.info(e);
		}
		APP_LOG.info("Wait For the Url to open:"+inputValue);
		return Constants.PASS;
	}	
	public String switchToFrame(Map<String, String> argsList) {
		WebDriver webDriver = getDriver();
		elementId = argsList.get("ElementId");
		String frameName =elementId;
		try {
		Thread.sleep(6000);
		webDriver.switchTo().frame(frameName);
		 System.out.println("********We are switching to the iframe*******");
		} catch(Exception e) {
			APP_LOG.debug("Switch frame exception");
			return Constants.FAIL + "Frame is not switched.";
		}
		return Constants.PASS + "Frame is switched.";
	}

	private static final String CHAR_LIST = 
		        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    private static final int RANDOM_STRING_LENGTH = 10;
		     
		    /**
		     * This method generates random string
		     * @return
		     */
		    public static String randomstring(Map<String, String> argsList) {
				APP_LOG.info("To check if element is displayed");
		        StringBuffer randStr = new StringBuffer();
		        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
		            int number = getRandomNumber();
		            char ch = CHAR_LIST.charAt(number);
		            randStr.append(ch);
		        }
		        return randStr.toString();
		    }
		     
		    /**
		     * This method generates random numbers
		     * @return int
		     */
		    public static int getRandomNumber() {
		        int randomInt = 0;
		        java.util.Random randomGenerator = new java.util.Random();
	        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
	        if (randomInt - 1 == -1) {
		            return randomInt;
		        } else {
		            return randomInt - 1;
	        }
	    }

}