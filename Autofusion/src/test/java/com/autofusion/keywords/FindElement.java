package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.autofusion.constants.Constants;

@SuppressWarnings("rawtypes")
public class FindElement extends ReadObjectRepoXml {

	public ConcurrentHashMap<String, String> hs = new ConcurrentHashMap<String, String>();

	public WebElement getElement(String elementName) {
		APP_LOG.info("Inside findElement");
		hs = getElementAttribute(elementName);
		WebElement element = findlocatorIdElement(hs);
		return element;
	}

	public List<WebElement> getElements(String elementName) {
		APP_LOG.info("Inside findElement");
		hs = getElementAttribute(elementName);
		List<WebElement> listOfElements = findlocatorIdElements(hs);
		return listOfElements;
	}

	private WebElement findlocatorIdElement(ConcurrentHashMap<String, String> hs) {
		String key = "";
		String value = "";
		WebElement element = null;
		Iterator entries = hs.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			key = (String) thisEntry.getKey();
			value = (String) thisEntry.getValue();
			System.out.println("Key and value in Map:" + key + value);
			break;
		}

		value = replaceDynamicText(value);

		if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_XPATH) || key.startsWith("//") || key.startsWith("xp")
				|| key.startsWith("xpath=(//")) {

			try {
				element = waitForVisibilityOfElement(By.xpath(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElement(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_ID)) {
			try {
				element = waitForVisibilityOfElement(By.id(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElement(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_NAME)) {

			try {
				element = waitForVisibilityOfElement(By.name(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CSS)) {

			try {
				element = waitForVisibilityOfElement(By.cssSelector(value));

			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CLASSNAME)) {

			try {
				element = waitForVisibilityOfElement(By.className(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_LINKTEXT)) {
			try {
				element = waitForVisibilityOfElement(By.linkText(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_PARTIALLINKTEXT)) {

			try {
				element = waitForVisibilityOfElement(By.partialLinkText(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_TAGNAME)) {

			try {
				element = waitForVisibilityOfElement(By.tagName(value));
			} catch (org.openqa.selenium.TimeoutException e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}

				}
				element = findlocatorIdElement(hs);
				return element;
			}
		}
		return element;
	}

	/**
	 * This function is to find elements
	 * 
	 * @param object
	 * @return
	 */
	public List<WebElement> findlocatorIdElements(ConcurrentHashMap<String, String> hs) {
		String key = "";
		String value = "";
		List<WebElement> element = null;
		Iterator entries = hs.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			key = (String) thisEntry.getKey();
			value = (String) thisEntry.getValue();
			System.out.println("Key and Value in Map:" + key + value);
			break;
		}
		
		value = replaceDynamicText(value);

		
		if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_XPATH) || key.startsWith("//") || key.startsWith("xp")
				|| key.startsWith("xpath=(//")) {

			try {
				element = waitForVisibilityOfElements(By.xpath(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_ID)) {

			try {
				element = waitForVisibilityOfElements(By.id(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_NAME)) {

			try {
				element = waitForVisibilityOfElements(By.name(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CSS)) {

			try {
				element = waitForVisibilityOfElements(By.cssSelector(value));

			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_CLASSNAME)) {

			try {
				element = waitForVisibilityOfElements(By.className(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_LINKTEXT)) {
			try {
				element = waitForVisibilityOfElements(By.linkText(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_PARTIALLINKTEXT)) {

			try {
				element = waitForVisibilityOfElements(By.partialLinkText(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;

			}
		} else if (key.equalsIgnoreCase(Constants.PREFIX_FIELD_TAGNAME)) {

			try {
				element = waitForVisibilityOfElements(By.tagName(value));
			} catch (Exception e) {
				Iterator it = hs.entrySet().iterator();
				while (it.hasNext()) {
					it.next();
					if (value != null) {
						it.remove();
						break;
					}
				}
				element = findlocatorIdElements(hs);
				return element;
			}
		}
		return element;

	}

	public WebElement waitForVisibilityOfElement(By locator) {
		APP_LOG.debug("inside waitForVisibilityOfElement");
		WebElement element = null;
		
		try {
			if(browser.equalsIgnoreCase("android") || browser.equalsIgnoreCase("ios")){
				WebDriverWait wait = new WebDriverWait(appiumDriver, maxTimeOutForElement);
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				APP_LOG.debug("\n--------- Element Found : Locator="+locator);
			}else{
				WebDriver webDriver = getDriver();
				try {
					WebDriverWait wait = new WebDriverWait(webDriver, maxTimeOutForElement);
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					APP_LOG.debug("\n--------- Element Found : Locator="+locator);
					highlight(element);
				}catch(Exception e) {
					APP_LOG.debug("waitForVisibilityOfElement bye bye after timeout=" + maxTimeOutForElement + " : Locator="
						+ locator);
				}
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw e;
		}catch (NoSuchElementException e) {
			APP_LOG.debug("waitForVisibilityOfElement bye bye after timeout=" + maxTimeOutForElement + " : Locator="
					+ locator);
			throw e;
		}

		return element;
	}
	public void navigateBack() {
		try {
			WebDriver webDriver = getDriver();
			webDriver.navigate().back();
			webDriver.navigate().refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void refresh() {
		try {
			WebDriver webDriver = getDriver();
			webDriver.navigate().refresh();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<WebElement> waitForVisibilityOfElements(By locator) throws Exception {
		try {
			APP_LOG.debug("inside waitForVisibilityOfElements");

			if(browser.equalsIgnoreCase("android") || browser.equalsIgnoreCase("ios")){
				WebDriverWait wait = new WebDriverWait(appiumDriver, maxTimeOutForElement);
				elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			}else{
				WebDriver webDriver = getDriver();
				waitForPageToLoad();
				WebDriverWait wait = new WebDriverWait(webDriver, maxTimeOutForElement);
				elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
				APP_LOG.debug("waitForVisibilityOfElement bye bye after timeout=" + maxTimeOutForElement + " : Locator="
						+ locator);
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}
		return elements;

	}

	/**
	 * @author sumit.bhardwaj
	 * @param element-->
	 *            WebElement on which highlight will be done
	 */

	public void highlight(WebElement element) {
		final int wait = 500;
		String originalStyle = element.getAttribute("style");
		try {

			setAttribute(element, "style", "background-color: yellow; outline: 1px solid rgb(136, 255, 136);;");
			Thread.sleep(wait);
			setAttribute(element, "style", originalStyle);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author sumit.bhardwaj
	 * @param element-->
	 *            WebElement on which highlight will be done
	 * @param value-->
	 *            Highlight type
	 */

	public void setAttribute(WebElement element, String attributeName, String value) {

		WebDriver webDriver = getDriver();
		try {

			for (int i = 0; i < 3; i++) {
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				// js.executeScript("arguments[0].setAttribute('style',
				// arguments[1]);",driver.findElement(Locator),
				// "color: red; border: 2px solid red;");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, value);

			}

		}

		catch (Throwable t) {
			System.out.println("Error came : " + t.getMessage());
		}

	}

	/**
	 * @author sumit.bhardwaj
	 * @description: Wait till 30 seconds for Angular(API calls) to load on
	 *               page.
	 */
	public void checkPageIsReady() {

		WebDriver webDriver = getDriver();
		try {

			WebDriverWait wait = new WebDriverWait(webDriver, 30);
			JavascriptExecutor jsExec = (JavascriptExecutor) webDriver;

			String angularReadyScript = "return (angular.element(document.body).injector().get('$http').pendingRequests.length === 0)";

			// Wait for ANGULAR to load
			ExpectedCondition<Boolean> angularLoad = driver -> Boolean
					.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

			// Get Angular is Ready
			boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());

			// Wait ANGULAR until it is Ready!
			if (!angularReady) {
				System.out.println("ANGULAR is NOT Ready!");
				// Wait for Angular to load
				wait.until(angularLoad);

			} else {

				System.out.println("API length on Page:- " + jsExec.executeScript(
						"return angular.element(document.body).injector().get('$http').pendingRequests.length"));

				APP_LOG.debug("API length on Page:- " + jsExec.executeScript(
						"return angular.element(document.body).injector().get('$http').pendingRequests.length"));
				System.out.println("ANGULAR is Ready!");
				APP_LOG.debug("ANGULAR is Ready!");
			}

			System.out.println("All API loaded, no API calls is Pending on Page");
			APP_LOG.debug("All API loaded, no API calls is Pending on Page");

		} catch (Throwable e) {
			System.out.println("Error occured while waiting for Angular to Load on Page because of " + e.getMessage());
			APP_LOG.debug("Error occured while waiting for Angular to Load on Page because of " + e.getMessage());
		}
	}

	/**
	 * public static void waitForPageToLoad() method specification :- 1) Waits
	 * for a new page to load completely 2) new WebDriverWait(driver, 60) ->
	 * Waits for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait
	 * until expected condition (All documents present on the page get ready)
	 * met
	 * 
	 * @author sumit.bhardwaj
	 * @param :
	 *            no parameters passed
	 * @return : void
	 */

	public static void waitForPageToLoad() {

		WebDriver webDriver = getDriver();
		try {

			// Waits for 60 seconds
			WebDriverWait wait = new WebDriverWait(webDriver, 60);
			// Wait until expected condition (All documents present on the page
			// get ready) met
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if (!(d instanceof JavascriptExecutor))
						return true;
					Object result = ((JavascriptExecutor) d)
							.executeScript("return document['readyState'] ? 'complete' == document.readyState : true");
					if (result != null && result instanceof Boolean && (Boolean) result)
						return true;
					return false;
				}
			});
		}

		catch (Throwable waitForPageToLoadException) {
			System.err.println("Error came while waiting for page to load ");

		}

	}

	public String replaceDynamicText(String value){
		
		if(value.contains("DYNAMIC_TEXT") && !dynamicTextToBeReplaced.equals("")){
			value = value.replace("DYNAMIC_TEXT", dynamicTextToBeReplaced);
		}
		if(value.contains("INDEX_POSITION") && !locatorIndexPosition.equals("")){
			value = value.replace("INDEX_POSITION", locatorIndexPosition);
		}
		
		return value;
	}
	
}