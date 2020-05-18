package com.autofusion;
/**
 * @author nitin.singh
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;

public class InitializeWebDriver extends BaseClass implements SauceOnDemandSessionIdProvider {

	public static final DesiredCapabilities capabilities = new DesiredCapabilities();
	static String username = "<username>"; // Your username
	//static String username = "p_GLP"; // Your username
	static String authkey = "4979d6d6-0e15-494f-a940-35eecb2b30b7"; // Your authkey
	//static String authkey = "1af5cef6-7cc1-4074-8dab-479113f61b90";
	private static String sessionId;																
	public static final String sauceLabUrl = "http://" + username + ":"
			+ authkey + "@ondemand.saucelabs.com:80/wd/hub";
	public static final String SAUCE_JENKINSUSERNAME = System.getenv("SAUCE_USERNAME");
	public static final String SAUCE_JENKINSACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SAUCE_JENKINSUSERNAME, SAUCE_JENKINSACCESS_KEY);
    
	public static WebDriver getWebDriver(String browser, WebDriver driver,
			String projectPath, Logger APP_LOGS) {
		// WebDriver driver; APP_LOGS.debug("getWebDriver browser=" + browser);
		if (BaseClass.runOnMachine.equalsIgnoreCase("local") || BaseClass.runOnMachine.equalsIgnoreCase("127.0.0.1")) {
			if (browser.equalsIgnoreCase("Firefox")) {
				if (driver instanceof FirefoxDriver) {
					return driver;
				}
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("acceptInsecureCerts", true);
				capabilities.setJavascriptEnabled(true);
				capabilities.setCapability("marionette", true);
				System.setProperty("webdriver.gecko.driver", projectPath+"//drivers//geckodriver.exe");
				driver = new FirefoxDriver(capabilities);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("Chrome")) {
				if (driver instanceof ChromeDriver) {
					return driver;
				}

				System.setProperty("webdriver.chrome.driver",
						projectPath+"//drivers//chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");
				options.addArguments("--enable-precise-memory-info");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("test-type=browser");
				options.addArguments("disable-infobars");
				// capabilities = WebCaps.chrome();
				capabilities.setCapability(
						CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
						true);
				capabilities.setCapability("chrome.switches",
						Arrays.asList("--incognito"));
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(capabilities);
				driver.manage().deleteAllCookies();
				
			} else if ((browser.equalsIgnoreCase("InternetExplorer"))
					|| (browser.equalsIgnoreCase("IE"))) {
				if (driver instanceof InternetExplorerDriver) {
					return driver;
				}
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

				System.setProperty("webdriver.ie.driver",
						projectPath+"//drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver(capabilities);
			}

			else if (browser.equalsIgnoreCase("Safari")) {
				if (driver instanceof SafariDriver) {
					return driver;
				}
				driver = new SafariDriver();
			}
		}

		else if (BaseClass.runOnMachine.equalsIgnoreCase("SAUCELAB")) {
			try {
				return setSauceLabDriver(browser, driver, APP_LOGS);
			} catch (Exception e) {
				e.getMessage();
			}
		}
			else if (BaseClass.runOnMachine.equalsIgnoreCase("JenkinsSAUCELAB")) {
				try {
					return setJenkinsSauceLabDriver(browser,driver, APP_LOGS);
				} catch (Exception e) {
					e.getMessage();
				}

		} else {
			try {
				return setRemoteDriver(browser, driver, APP_LOGS);
			} catch (Exception e) {
				e.getMessage();
			}

		}

		return driver;
	}
	
	private static WebDriver setJenkinsSauceLabDriver(String browser, WebDriver driver,
			Logger APP_LOGS) 
	   {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(System.getenv("SELENIUM_BROWSER"));
		cap.setVersion(System.getenv("SELENIUM_VERSION"));
		//cap.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
		//cap.setCapability("build", System.getenv("JOB_NAME") + "__" + System.getenv("BUILD_NUMBER"));
		URL selserverhost = null;
		try {
			selserverhost =  new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}


	private static WebDriver setSauceLabDriver(String browser, WebDriver driver,
			Logger APP_LOGS) {
		DesiredCapabilities cap = null;
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
			cap.setCapability("platform", "Windows XP");
			cap.setCapability("version", "latest");
		} else if (browser.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
			cap.setCapability("platform", "Windows XP");
			cap.setCapability("version", "latest");
		} else if (browser.equalsIgnoreCase("Safari")) {
			cap = DesiredCapabilities.safari();
			cap.setCapability("platform", "Windows XP");
			cap.setCapability("version", "latest");

		} else if ((browser.equalsIgnoreCase("ie"))
				|| (browser.equalsIgnoreCase("internetexplorer"))
				|| (browser.equalsIgnoreCase("internet explorer"))) {
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability("platform", "Windows XP");
			cap.setCapability("version", "latest");
		}

		URL selserverhost = null;
		try {
			selserverhost = new URL(sauceLabUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}

	@SuppressWarnings("null")
	private static WebDriver setRemoteDriver(String browser, WebDriver driver,
			Logger APP_LOGS) {
		DesiredCapabilities cap = null;
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
			cap.setCapability("version", "");
			cap.setCapability("platform", "Linux");
		} else if (browser.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
			cap.setCapability("version", "");
			cap.setCapability("platform", "Linux");
		} else if (browser.equalsIgnoreCase("Safari")) {
			cap.setCapability("version", "");
			cap.setCapability("platform", "Linux");

		} else if ((browser.equalsIgnoreCase("ie"))
				|| (browser.equalsIgnoreCase("internetexplorer"))
				|| (browser.equalsIgnoreCase("internet explorer"))) {
			cap = DesiredCapabilities.internetExplorer();
		}
		String seleniuhubaddress = "http://172.16.16.169:4444/wd/hub";
		URL selserverhost = null;
		try {
			selserverhost = new URL(seleniuhubaddress);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		 return sessionId;
	}

}
