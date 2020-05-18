package com.autofusion.capabilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autofusion.bean.AndroidCommon;
import com.autofusion.util.InitClass;

import io.appium.java_client.remote.MobileCapabilityType;

/**
 * @author nitin.singh
 */
@SuppressWarnings("unused")
public class AndroidCaps extends Caps {
	 
	public static String ADB_PATH = "";
    public static String APK_PATH = "";
    public static String APK_NAME = "";
    public static String APK_PACKAGE = "";
    public static String LAUNCH_ACTIVITY = "";
    public static String reInstallApp  ="";
    
    @Override
    public DesiredCapabilities getCapabilities(Logger APP_LOGS,String deviceId) {
       
    	ADB_PATH = InitClass.USER_CONFIG.getProperty("ADB_PATH");
		APK_PATH = InitClass.USER_CONFIG.getProperty("APK_PATH");
		APK_NAME = InitClass.USER_CONFIG.getProperty("APK_NAME");
 		APK_PACKAGE = InitClass.USER_CONFIG.getProperty("APK_PACKAGE");
		LAUNCH_ACTIVITY = InitClass.USER_CONFIG.getProperty("LAUNCH_ACTIVITY");
		reInstallApp= InitClass.USER_CONFIG.getProperty("ReInstallApp");
    	
    	androidCaps.setCapability(MobileCapabilityType.BROWSER_NAME, "TO BE ADDED");
    	AndroidCommon objAndroidCommon = new AndroidCommon(ADB_PATH, APP_LOGS);
    	
	 try{	
	    	int apilevel = Integer.parseInt(AndroidCommon.getDeviceAPILevel(deviceId));
		    if(apilevel<17){
		    	androidCaps.setCapability("automationName", "Selendroid");
		    }else{
		    	androidCaps.setCapability("automationName", "Appium");
		    }
		    androidCaps.setCapability("platformName", "Android");
		    androidCaps.setCapability("platformVersion", AndroidCommon.getDeviceVersion(deviceId));
		    androidCaps.setCapability("udid", AndroidCommon.getDeviceSerial(deviceId));
		    androidCaps.setCapability("deviceName", AndroidCommon.getDeviceModel(deviceId));
		    androidCaps.setCapability("newCommandTimeout", "2000");
		           
        	if(reInstallApp.equals("on")){
        		androidCaps.setCapability("app", APK_PATH+"/"+APK_NAME);
        	}
        	androidCaps.setCapability("appActivity", LAUNCH_ACTIVITY);
        	androidCaps.setCapability("appPackage", APK_PACKAGE);
       
	 }catch(Exception e){
		 APP_LOGS.debug("Set Capability Issue with device connectivity: "+e);
		 System.out.println("Set Capability Issue with device connectivity: "+e);
		 throw e;
	 }
        return androidCaps;
	}
}