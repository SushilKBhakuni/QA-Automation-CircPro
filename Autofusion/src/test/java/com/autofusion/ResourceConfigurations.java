package com.autofusion;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.autofusion.constants.Constants;

public final class ResourceConfigurations {
	static Logger APP_LOGS;
	public static String language = PropertyManager.getInstance().getValueForKey("language").trim();
	/**
	 * Resource bundle.
	 */

	static Locale locale = new Locale(language);
	static ResourceBundle resourceBundle = ResourceBundle.getBundle("constant", locale);

	/**
	 * Method to read the property value. @ Created by Abhishek Sharda
	 * 
	 * @param key
	 * @return key value
	 */
	public static String getProperty(final String key) {
		String str = null;
		try {

			if (resourceBundle != null) {
				str = resourceBundle.getString(key);
			} else {
				System.out.println("Properties file was not loaded correctly!!");
			}
			return str;
		} catch (Throwable e) {

			return Constants.FAIL + ": Error while getting value of: " + key + " from ResourceConfigurations";
		}
	}

}
