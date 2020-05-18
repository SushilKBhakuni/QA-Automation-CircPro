package com.autofusion.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author nitin.singh
 */
import io.appium.java_client.AppiumDriver;

@SuppressWarnings({"all"})
public class AndroidCommon{
	public static Properties prop = new Properties();
	public static String adbBasePath = "";
	public static ArrayList<String> strState = new ArrayList<String>();
	public static int i = 0, j = 0;
	public static AppiumDriver appiumDriver = null;
	public static Logger APP_LOGS;
	 
	
	public AndroidCommon(String adbBasePath,Logger APP_LOGS ){
		this.adbBasePath = adbBasePath;
		this.APP_LOGS = APP_LOGS;
	}
	
	public AndroidCommon(){
	
	}
	
	public static String getDeviceModel(String deviceName){
		APP_LOGS.debug("getDeviceModel : adb shell getprop ro.product.model");
		String command = executeCommand(" cmd /C adb  -s "+deviceName+" shell getprop ro.product.model");
		APP_LOGS.debug("getDeviceModel : "+command);
		return command.trim();
	}
	
	public static String getDeviceSerial(String deviceName)
	{
		APP_LOGS.debug("getDeviceSerial : adb get-serialno");
		String command = executeCommand(" cmd /C adb  -s "+deviceName+" get-serialno");
		APP_LOGS.debug("getDeviceSerial : "+command);
		return command.trim();
	}
	
	public static String getDeviceVersion(String deviceName)
	{	
		APP_LOGS.debug("getDeviceVersion : adb shell getprop ro.build.version.release");
		String command = executeCommand(" cmd /C adb -s "+deviceName+"  shell getprop ro.build.version.release");
		APP_LOGS.debug("getDeviceVersion : "+command);
		return command.trim();
	}
	
	public static String getDeviceAPILevel(String deviceName)
	{
		APP_LOGS.debug("adbBasePath : "+adbBasePath);
		String command = executeCommand("cmd /C adb -s "+deviceName+" shell getprop ro.build.version.sdk");
		APP_LOGS.debug("getDeviceAPILevel : "+command);
		return command.trim();
	}
	
	public static String getDeviceType(String deviceName)
	{
	
		APP_LOGS.debug("getDeviceType : adb shell getprop ro.setupwizard.mode");
		String command = executeCommand(" cmd /C adb  -s "+deviceName+"  shell getprop ro.setupwizard.mode");
		APP_LOGS.debug("getDeviceType : "+command);
		return command.trim();
	}
	
	public static String deviceSerielNo(){
		String command = executeCommand("instruments -s devices");
		//APP_LOGS.debug("getDeviceType : "+command);
		return command.trim();
	}

	public static void clearADBLogs() {
		try {
			Runtime.getRuntime().exec("cmd /C adb shell logcat -c");
		} catch (Exception e) {
			APP_LOGS.debug("Error while clearing logcat");
			e.printStackTrace();
		}

	}

	public static void captureADBLogs(File file) {
		try {
			Runtime.getRuntime().exec("cmd /C adb logcat -d" + ">" + file);
		} catch (Exception e) {
			APP_LOGS.debug("Error while capturing ADB Logs");
			e.printStackTrace();
		}
	}
	
	
	public static void forceStopAnApp(String packagename) {
		try {
			Runtime.getRuntime().exec("cmd /C adb shell am force-stop "+packagename);
		} catch (Exception e) {
			APP_LOGS.debug("Error while stopping an app");
			e.printStackTrace();
		}
	}
	
	
	public static void openRecentlyUsedApps() {
		try {
			Runtime.getRuntime().exec("cmd /C adb shell input keyevent KEYCODE_APP_SWITCH");
		} catch (Exception e) {
			APP_LOGS.debug("Error while opening recent apps from task manager");
			e.printStackTrace();
		}
	}
	
	public static void switchApp(String appPackage, String appActivity) {
		try {			
			Runtime.getRuntime().exec("cmd /C adb shell am start -n "+appPackage+"/"+appActivity);	
		}catch (Exception e) {
			APP_LOGS.debug("Error switching app");
			e.printStackTrace();
		}
	}


	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
            String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			APP_LOGS.debug("Execute command to get details of device " + e);
			e.printStackTrace();
		}

		return output.toString();

	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
	}

	public static boolean verifyKeyboard() throws InterruptedException {
		boolean val;

		String command = executeCommand(" cmd /C adb shell dumpsys input_method");
		if (command.contains("mInputShown=true"))
			val = true;
		else
			val = false;
		return val;
	}

}
