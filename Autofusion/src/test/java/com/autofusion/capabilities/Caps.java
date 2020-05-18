package com.autofusion.capabilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Nitin Singh
 */
public class Caps {
    protected static DesiredCapabilities androidCaps = new DesiredCapabilities();
    protected static DesiredCapabilities iOSCaps = new DesiredCapabilities();
    protected static DesiredCapabilities firefoxCaps = new DesiredCapabilities();

    public DesiredCapabilities getCapabilities(Logger APP_LOGS, String deviceId) {
        System.out.println("Error!");
        return null;
    }

	 
}