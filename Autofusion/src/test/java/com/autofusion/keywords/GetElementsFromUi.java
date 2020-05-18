package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;

@SuppressWarnings("unused")
public class GetElementsFromUi extends Keyword {
	public static Map<String, String> presistanceMap = new HashMap<String, String>();
	static String elementId;
	static WebElement element;
	List<WebElement> elements;
	String inputValue;
	private static String locatorId;

	public String getText(Map<String, String> argsList) {
		APP_LOG.info("Inside type");
		elementId = argsList.get("ElementId");
		try {
			element = getElement(elementId);
			return getText(element);
		} catch (Throwable e) {
			collectFailureMessage("Exception during verification of text || Exception:" + e.getMessage());
			return Constants.FAIL + ": Error while finding Element - " + elementId + " : " + e.getMessage();
		}
	}

	public static String getText(WebElement element) {
		APP_LOG.debug("Func:Find Value");
		try {
			String value = element.getText();
			return value;
		} catch (Throwable e) {
			APP_LOG.debug("Func:Static Click Exception=" + e);
			return Constants.FAIL + ": Unexpected error while getting Text for Element - '" + elementId + "' : "
					+ e.getMessage();
		}
	}

}
