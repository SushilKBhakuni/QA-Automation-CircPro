package com.autofusion.util;
/**
 * @author nitin.singh
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class InitClass {

	public static Logger APP_LOGS = null;
	public static ExtentReports reportObj = null;
	public static ExtentTest reportTestObj = null;

	public Properties prop = new Properties();
	public static ArrayList<String> strState = new ArrayList<String>();
	public int i = 0, j = 0;
	public static List<WebElement> tab2;
	public static int apilevel;
	public static String deviceversion;
	public static String devicemodel;
	public static String deviceserial;
	public static String devicetype;
	public static Properties CONFIG;
	public static Properties USER_CONFIG;
	// public static Logger APP_LOG = null;
	public static String fileSeprator = System.getProperty("file.separator");

	public InitClass(String path) {
		/*
		 * System.setProperty("log.dir", path); APP_LOG =
		 * Logger.getLogger("automation"); APP_LOG.debug("Logger Initiated");
		 */
	}

	public InitClass() {
	}

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	public static Logger initializeLogger(String basePath, String device) {
		String logDate = now("dd.MMMMM.yyyyhh.mm.ss");
		try {
			System.setProperty("log.dir", basePath + File.separator + device.toLowerCase() + File.separator + "testLogs"
					+ File.separator + "ApplicationLog_" + logDate + ".log");
			APP_LOGS = Logger.getLogger("automation");
		} catch (Exception e) {
			APP_LOGS.debug("Exception during Logger Creation " + e);
		}
		APP_LOGS.debug(" initializeLogger " + basePath + " :: " + device + " - " + logDate);
		APP_LOGS.debug("Logger Initiated");

		return APP_LOGS;
	}

	public static String initializeExternalConfigFile(String folderPath) {
		APP_LOGS.debug("config Initiated :" + folderPath);
		try {
			InputStreamReader fileConfig2 = new InputStreamReader(
					new FileInputStream(folderPath + fileSeprator + "config.properties"), "UTF-8");
			USER_CONFIG = new Properties();
			USER_CONFIG.load(fileConfig2);
			APP_LOGS.debug("User config loaded");
			System.out.println("User config loaded");
			return "Success";
		} catch (Exception e) {
			APP_LOGS.debug("config file not Initiated" + e);
			return null;
		}

	}

}
