package com.autofusion.capabilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autofusion.bean.CommonUtility;

/**
 * @author nitin.singh
 */
public class IosCaps extends Caps {

    public DesiredCapabilities getCapabilities(Logger APP_LOGS) {
       /* iOSCaps.setCapability(MobileCapabilityType.BROWSER_NAME, PropertyManager.getInstance().getValueForKey(Constants.IOS_BROWSER_NAME));
        iOSCaps.setCapability(MobileCapabilityType.APP, PropertyManager.getInstance().getValueForKey(Constants.IOS_APP_PATH));
        iOSCaps.setCapability(MobileCapabilityType.DEVICE_NAME, PropertyManager.getInstance().getValueForKey(Constants.IOS_DEVICE_NAME));
        iOSCaps.setCapability("bundleId", PropertyManager.getInstance().getValueForKey(Constants.IOS_BUNDLE_ID));
        iOSCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, PropertyManager.getInstance().getValueForKey(Constants.IOS_PLATFORM_NAME));
        iOSCaps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PropertyManager.getInstance().getValueForKey(Constants.IOS_PLATFORM_VERSION));
      */
    	iOSCaps.setCapability("locationServicesEnabled", true);
        iOSCaps.setCapability("locationServicesAuthorized", true);
        iOSCaps.setCapability("autoAcceptAlerts", true);
      
	 try{	
		 iOSCaps.setCapability("automationName", "Appium");
		 iOSCaps.setCapability("platformName", "iOS");
		 iOSCaps.setCapability("platformVersion", CommonUtility.USER_CONFIG.getProperty("IOS_PLATFORM_VER"));
		 iOSCaps.setCapability("deviceName", CommonUtility.USER_CONFIG.getProperty("IOS_DEVICE_NAME"));
	        //capabilities.setCapability("udid", CommonUtility.USER_CONFIG.getProperty("IOS_UDID"));//iPodTouch1
	       // capabilities.setCapability("bundleId", CommonUtility.USER_CONFIG.getProperty("BUNDLE_ID"));//iPodTouch1
		 iOSCaps.setCapability("newCommandTimeout", "2000");
	     iOSCaps.setCapability("browserName", "Chrome");
         
	 }catch(Exception e){
		 //APP_LOGS.debug("Set Capability Issue with device connectivity: "+e);
		 System.out.println("Set Capability Issue with device connectivity: "+e);
		 throw e;
	 }
        return iOSCaps;
	}
}
