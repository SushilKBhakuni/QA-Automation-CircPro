package com.autofusion.capabilities;

import java.io.File;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import com.autofusion.constants.Constants;

public class WebCaps {

	
public static FirefoxProfile createFirefoxProfile(Logger APP_LOGS){
		APP_LOGS.debug("createFirefoxProfile");
		ProfilesIni fprofile = new ProfilesIni();
		FirefoxProfile profile = fprofile.getProfile("selenium");
	
		String folderName = Constants.SELENIUM_PROFILE_LOCATION;
		 
		File file = new File(folderName);
		if (!file.exists()) {
			if (file.mkdir()) {
				APP_LOGS.debug("Directory is created!");
			} else {
				APP_LOGS.debug("Failed to create a Directory!");
			}
		}
		
		profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", folderName);
        profile.setPreference("browser.download.downloadDir", folderName);
		profile.setPreference("browser.download.defaultFolder", folderName);
        profile.setPreference("browser.download.alertOnEXEOpen", false);
        profile.setPreference("browser.helperApps.neverAsksaveToDisk", "application/msword,application/csv,text/csv,application/pdf,application/x-msexcel,application/excel,application/x-excel,application/excel,application/x-excel,application/excel,application/vnd.ms-excel,application/x-excel,application/x-msexcel");
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", true);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.closeWhenDone", true);
        profile.setPreference("browser.download.manager.showAlertOnComplete", true);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("network.http.phishy-userpass-length", 255); 
        profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
        profile.setPreference("pdfjs.disabled", true);
    	profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);	
        
        return profile;
        
	}
}
