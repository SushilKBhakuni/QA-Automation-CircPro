package com.autofusion;

/**
 * @author nitin.singh
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.autofusion.capabilities.AndroidCaps;
import com.autofusion.capabilities.Caps;
import com.autofusion.capabilities.IosCaps;
import com.autofusion.commonPage.CommonPage;
import com.autofusion.constants.Constants;
import com.autofusion.email.MailSender;
import com.autofusion.keywords.ReTryFailedTests;
import com.autofusion.util.ALM;
import com.autofusion.util.FileUtil;
import com.autofusion.util.InitClass;
import com.autofusion.util.ReadConfigXlsFiles;
import com.google.api.client.util.Base64;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

@SuppressWarnings("rawtypes")
public abstract class BaseClass {

	public static WebDriver webDriver;
	public static AppiumDriver appiumDriver = null;
	private static ThreadLocal<Boolean> executionStatus = new ThreadLocal<Boolean>();	
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<String> runningComponentName = new ThreadLocal<String>();
	public static String runningComponentNameForPOM=null;
	//public static String runningComponentName = null;
	public static List<String> extentParentNodelist = new ArrayList<String>();
	protected static ThreadLocal<AtomicInteger> count = new ThreadLocal<>();
    protected static ThreadLocal<RemoteWebDriver> remotewebdriver = new ThreadLocal<>();
    protected static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static String dynamicTextToBeReplaced = "";
    public static String locatorIndexPosition = "";
    public static String runTimeValue = "";
    public static Map<String, ExtentTest> extentNodeAndPackageName = new LinkedHashMap<String, ExtentTest>();
	public String packageName = "";
	public String testpackage = getClass().getPackage().getName();
	// public FindElement findElement = new FindElement();
	/****************************
	 * Configuration Parameter (should be pass from UI, testng.xml ,
	 * config.property)
	 **************************/
	public static String failureErrorMessageCollector = "";
	public static String actualDataPresentOnUi = "";
	public static String browser;
	public static String device;
	public static String runOnMachine;
	public static String executionEnviroment;
	public static String releaseVersion ;
	public static String testtype;
	public static String projectPath;
	public static String userRole;
	public static String language;
	public static String alm;
	public static String extentReportFile = "";
	public static int passCount = 0;
	public static int failCount = 0;
	public static String sendMail;
	public static String passScreenshots;
	public static String ipaddress;
	public static int failedStepsCount;
	protected boolean proceedOnFail;
	public static String suitename;
	/****************************
	 * Configuration Parameter
	 **************************/

	public static int maxTimeOutForElement = 0;
	public WebDriverWait wait;
	public static Logger APP_LOG = null;
	public static Map<String, String> configurationsXlsMap = new HashMap<String, String>();
	public static int batchId;
	public static ExtentReports reportObj = null;
	public ExtentTest reportTestObj = null;
	public List<WebElement> listOfElements = null;
	public List<WebElement> elements = null;
	public static int testCaseIdPom = 1;
	public static WebElement element = null;
	public static int testStepIdPom = 1;
	public Method objMethod[];
	public static String reportStartTime = "";
	public static String runningSuitName = "";
	public static String almResult = "";
	public static Properties CONFIG;
	/***********************************************/
	public static String ADB_PATH = "";
	public static String APK_PATH = "";
	public static String APK_NAME = "";
	public static String APPIUM_PORT = "";
	public static String APK_PACKAGE = "";
	public static String LAUNCH_ACTIVITY = "";
	public ExtentTest extentParentNode;

	/**************** All Page Objects *******************************/

	public Object objPageClass;
	public CommonPage objCommonPage;
	public ALM objAlm;
	/***********************************************/
	protected static String capBrowserName = null;
	static Properties prop = new Properties();
	
	
	static {
		init();
		maxTimeOutForElement = Integer.valueOf(PropertyManager.getInstance().getValueForKey("MaxWaitForElement"));
	}

	@BeforeSuite(alwaysRun = true)
	public void getSuitName(ITestContext ctx) {
		runningSuitName = ctx.getSuite().getName();
		for (ITestNGMethod method : ctx.getAllTestMethods()) {
			method.setRetryAnalyzer(new ReTryFailedTests());
		}
	}

	/**
	 * @description getDriver methods returns WebDriver for parallel Executions
	 * @return--> Instance of WebDriver
	 */
	public static WebDriver getDriver() {
		try {
			return driver.get();
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * @description setWebDriver methods sets WebDriver for parallel Executions
	 */

	public  void setDriver(WebDriver d) {
		try {
			driver.set(d);
			webDriver = d;
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		initializeReportObject();
		webDriver = getWebDriver();
		setDriver(webDriver);
		getDriver();
	}

	// Mandatory
	public synchronized void startReport(String almId, String testDesc) {
		//addParentReport();
		reportTestObj = reportObj.startTest("TC_Id: " + almId + " --> " + testDesc);
		if (testDesc != null) {
			// Split the package name to get the page Name,e.g.,splitting
			// "com.test.productApplication_Login" will give you "Login"
			//testpackage = (testpackage.split("_"))[1];
		}
	}

	
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		endReport();
		reportObj.flush();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

		try {
			File srcFolder = new File(
					"src/test/resources/essentials/web/report/Browser/chrome/" + reportStartTime + "/");
			File destFolder = new File("src/test/resources/jenkinsLatestReport");
			String dest = "src/test/resources/jenkinsLatestReport/";
			FileUtil.deleteFile(dest);
			FileUtil.copyFolder(srcFolder, destFolder);
			FileUtil.renameFile();
			
			System.out.println("Report copy to Jenkins Folder");
			if (sendMail.equalsIgnoreCase("true")) {
				MailSender.sendMail("src/test/resources/jenkinsLatestReport/ExtentReport.html");
				System.out.println("Mail Sent");
			}
			reportObj.flush();
		} catch (Exception e) {
			APP_LOG.debug("endReport : " + e);
			reportObj.flush();
		
		}

	}

	/**
	 * @description addParentReport method adds the instance of ExtentParent
	 *              Node and current package name in extentNodeAndPackageName
	 *              map.
	 */

	public synchronized void addParentReport() {
		try {
			System.out.println(extentParentNodelist);
			packageName = getClass().getPackage().getName();
			packageName = packageName.split("\\.")[2].toUpperCase();
			packageName = packageName.replaceAll("_", ": ");

			if (extentParentNodelist.contains(packageName)) {

				extentNodeAndPackageName.get(packageName);

			} else {
				extentParentNodelist.add(packageName);
				extentParentNode = reportObj.startTest(packageName).assignCategory("Regression");
				extentNodeAndPackageName.put(packageName, extentParentNode);
			}

		} catch (Throwable e) {
			System.out.println("Error while adding Parent node into report beacuse of " + e.getMessage());
		}
	}

	public synchronized void endReport() {
		try {
//			extentReports.endTest(test);
//			packageName = getClass().getPackage().getName();
//			packageName = packageName.split("\\.")[2].toUpperCase();
//			packageName = packageName.replaceAll("_", ": ");
//			Thread.sleep(1500);
//			extentNodeAndPackageName.get(packageName).appendChild(reportTestObj);
			reportObj.endTest(extentNodeAndPackageName.get(packageName));
			reportObj.flush();
		} catch (Throwable e) {
			reportObj.endTest(extentNodeAndPackageName.get(packageName));
			reportObj.flush();
			System.out.println("endReport " + e.getMessage());
		}
	}

	/**
	 * device = Web, Mobile browser = android, iOS, Firefox, Chrome
	 * 
	 */
	protected static void init() {
		try {
			if (projectPath == null || projectPath.equals("")) { // Only for Run
																	// As java
				projectPath = PropertyManager.getInstance().getValueForKey("ProjectPath").trim();
				browser = PropertyManager.getInstance().valueFromConfig("Browser").trim();
				device = PropertyManager.getInstance().getValueForKey("Device").trim();
				releaseVersion = PropertyManager.getInstance().getValueForKey("releaseversion").trim();
				executionEnviroment = PropertyManager.getInstance().getValueForKey("EXECUTION_ENVIRONMENT").trim();
				alm = PropertyManager.getInstance().getValueForKey("ALM");
				runOnMachine = PropertyManager.getInstance().getValueForKey("runOnMachine").trim();
				sendMail = PropertyManager.getInstance().getValueForKey("sendMail");
				testtype = PropertyManager.getInstance().getValueForKey("testtype");
				ipaddress= PropertyManager.getInstance().getValueForKey("ipaddress");
				suitename= PropertyManager.getInstance().getValueForKey("suitename");
				
			}

			if (null == APP_LOG) {
				APP_LOG = InitClass.initializeLogger(projectPath, device);
			}

			if (configurationsXlsMap.isEmpty()) {
				//readEnvironmentConfigurationFile();
			}

			InitClass.initializeExternalConfigFile(projectPath);
		} catch (Exception e) {
			APP_LOG.debug("Error during reading of config.property file, this is expected if user is "
					+ "running it from testng.xml and passing params from xml it self" + e);
		}
	}

	/**
	 * device = Web, android, iOS, browser = android, iOS, Firefox, Chrome
	 * 
	 * @return
	 * 
	 */
	public static ExtentReports initializeReportObject() {

		if (reportObj == null) {
			reportStartTime = InitClass.now("dd.MMMMM.yyyy hh.mm.ss");
			extentReportFile = projectPath + "//" + device + "//report//Browser//" + browser + "//" + reportStartTime
					+ "//ExecutionReport_" + reportStartTime + ".html";
			reportObj = new ExtentReports(extentReportFile, true);
			File f = new File(projectPath + "//extent-config.xml");
			try {
				reportObj.loadConfig(f);
			} catch (Exception e) {
				APP_LOG.debug("Extent report configuration " + e);
			}

			reportObj.addSystemInfo("Run On", device.toUpperCase());
			reportObj.addSystemInfo("Browser", browser.toUpperCase());
			reportObj.addSystemInfo("Environment", executionEnviroment.toUpperCase());

		}
        //batchId = ReportSqls.selectBatchId(executionEnviroment);

		return reportObj;
	}

	public WebDriver getWebDriver() {
		String deviceToRun = device;
		if (webDriver == null) {
			if (configurationsXlsMap.isEmpty()) {
				readEnvironmentConfigurationFile();
			}
			if (deviceToRun.equalsIgnoreCase("WEB")) {

				webDriver = InitializeWebDriver.getWebDriver(browser, webDriver, projectPath, APP_LOG);
				webDriver.get(configurationsXlsMap.get("DomainUrl"));
                setDriver(webDriver); // This initialization for UI


			} else {
				APP_LOG.debug("No device define in config.property");
				return null;
			}
		}
		wait = new WebDriverWait(webDriver, 1000);

		return webDriver;
	}

	/**
	 * @description Extract the test case Id from the class name.
	 */
	public String getTestCaseId() {
		Pattern regex = Pattern.compile("(\\d{1,7})");
		String testCaseId = "";
		// create matcher for pattern 'regex' and given string 'className'
		try {
			Matcher matcherString = regex.matcher(getClass().getSimpleName());
			while (matcherString.find()) {
				testCaseId = matcherString.group();
			}
			return testCaseId;
		} catch (Throwable ex) {
			ex.printStackTrace();
			return "Not Available ";
		}
	}

	public void loadPage() {
		int maxTimeOut = Integer.valueOf(PropertyManager.getInstance().getValueForKey("INIT_ELE_MAX_TIMEOUT"));
		PageFactory.initElements(new AjaxElementLocatorFactory(webDriver, maxTimeOut), this);
	}
	 public static void loadPage(Class className) {
		 if(device.equalsIgnoreCase("web")){
	        //PageFactory.initElements(new AjaxElementLocatorFactory(webDriver, maxTimeOutForElement), className);
		 }else{
			PageFactory.initElements(new AjaxElementLocatorFactory(appiumDriver, maxTimeOutForElement), className);
		 }
	 }
	 

	public void waitForVisibilityOfElementLocated(By webElementBy) {
		int maxTimeOut = Integer.valueOf(PropertyManager.getInstance().getValueForKey("maxTimeOutForElement"));

		WebDriverWait wait = new WebDriverWait(webDriver, maxTimeOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(webElementBy));
	}

	/**
	 * Read Environment Configuration File
	 */

	public static void readEnvironmentConfigurationFile() {
		APP_LOG.debug("Reading Environment configuration file");
		ReadConfigXlsFiles objConfigXlsFiles = new ReadConfigXlsFiles();
		configurationsXlsMap = objConfigXlsFiles.readConfigurationsXls(projectPath, executionEnviroment.trim(),
				APP_LOG);
	}

	public static String addScreenshot() {
		WebDriver webDriver = getDriver();
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}

	/**
	 * Log result in report
	 */
	public static void logResultInReport(String testResult, String stepDescription, ExtentTest reportTestObj) {
		if (testResult.contains(Constants.PASS)) {
			reportTestObj.log(LogStatus.PASS, stepDescription, testResult);
			//almResult = "Passed";
			reportObj.endTest(reportTestObj);
			reportObj.flush();
		} else {
			
			String filePath = addScreenshot();
			reportTestObj.log(LogStatus.FAIL, stepDescription, testResult);
			reportTestObj.log(LogStatus.FAIL, "Failure Screenshot",
					"Snapshot below: " + reportTestObj.addScreenCapture(filePath));
			reportObj.endTest(reportTestObj);
			reportObj.flush();
			//almResult = "Failed";
		}
	}

	public static void collectFailureMessage(String failureMessage) {
		failureErrorMessageCollector = failureMessage;
	}

	public void collectActualUiData(String actualdData) {
		actualDataPresentOnUi = actualdData;
	}

	/**
	 * @return the runningComponentName
	 */
	public static String getRunningComponentName() {
		return runningComponentName.get();
	}

	/**
	 * @param runningComponentName
	 *            the runningComponentName to set
	 */
	public static void setRunningComponentName(String value) {
		runningComponentName.set(value);
	}
	

	/**
	 * valueFromConfig return value from CONFIG properties if value from Jenkins
	 * environment is null
	 * 
	 * @return--> The value from config file or from jenkins varibale
	 */
	public static String valueFromConfig(String key) {

		// Checking if value from Jenkins variable is null, if it is null then
		// value from config.properties will return
		try {
			if (CONFIG == null) {
				PropertyManager.getInstance();

			}

			if (System.getenv(key) == null) {
				APP_LOG.debug("Value of Jenkins parameter " + key + " is null, loaded value: "
						+ CONFIG.getProperty(key) + " from Config properties");
				System.out.println("Value of Jenkins parameter " + key + " is null, loaded value: "
						+ CONFIG.getProperty(key) + " from Config properties");
				return CONFIG.getProperty(key);

			}

		} catch (Throwable e) {
			APP_LOG.debug("Value of Jenkins parameter is not null");
			System.out.println("Value of Jenkins parameter is not null");
		}
		APP_LOG.debug("Value of Jenkins parameter is not null, loaded value from Jenkins parameter");
		System.out.println("Value of Jenkins parameter is not null, loaded value from Jenkins parameter");
		return System.getenv(key);

	}

  public WebDriver returnDriver() {
        try {
            if ("android".equalsIgnoreCase(browser)
                    || "ios".equalsIgnoreCase(browser)) {
                // WebDriver webDriver = getMobileDriver();
                return webDriver;
            } else {
                if ("local".equalsIgnoreCase(runOnMachine) || "127.0.0.1".equalsIgnoreCase(runOnMachine)) {
                    WebDriver webDriver = getDriver();
                    return webDriver;
                } else if ("Remote".equalsIgnoreCase(runOnMachine)
                        || "SauceLab".equalsIgnoreCase(runOnMachine)
                        || "JenkinsSauceLab".equalsIgnoreCase(runOnMachine)) {
                    WebDriver webDriver = getRemoteDriver();
                    return webDriver;
                }

            }
        } catch (Exception e) {
            APP_LOG.error("Exception occured in return driver method", e);
        }
        return webDriver;
    }

    public WebDriver getRemoteDriver() {
        try {
            return remotewebdriver.get();
        }

        catch (Exception e) {
            APP_LOG.error("Exception occured in getRemoteDriver method", e);
            return null;

        }
    } 

    

	public AppiumDriver getMobileDriver(String deviceId) {
		
		if(appiumDriver == null){
			Caps deviceCaps = null;
			if(browser.equalsIgnoreCase("Android")){
				deviceCaps = new AndroidCaps();
	        }else if(browser.equalsIgnoreCase("iOS")){
				deviceCaps = new IosCaps();
	        } 
			
			try{	
				runOnMachine = InitClass.USER_CONFIG.getProperty("RunOnMachine");
				ADB_PATH = InitClass.USER_CONFIG.getProperty("ADB_PATH");
				APK_PATH = InitClass.USER_CONFIG.getProperty("APK_PATH");
				APK_NAME = InitClass.USER_CONFIG.getProperty("APK_NAME");
				APPIUM_PORT = InitClass.USER_CONFIG.getProperty("APPIUM_PORT");
				APK_PACKAGE = InitClass.USER_CONFIG.getProperty("APK_PACKAGE");
				LAUNCH_ACTIVITY = InitClass.USER_CONFIG.getProperty("LAUNCH_ACTIVITY");
				
			//appiumDriver = new AppiumDriver(new URL("http://"+ip+":"+PropertyManager.getInstance().getValueForKey("APPIUM_PORT")+"/wd/hub"), deviceCaps.getCapabilities(), null) {
				 if(!browser.equalsIgnoreCase("Android"))
					 appiumDriver = new IOSDriver(new URL("http://"+runOnMachine+":"+APPIUM_PORT+"/wd/hub"), deviceCaps.getCapabilities(APP_LOG,deviceId));
				 else
					 appiumDriver = new AndroidDriver(new URL("http://"+runOnMachine+":"+APPIUM_PORT+"/wd/hub"), deviceCaps.getCapabilities(APP_LOG,deviceId));
			 
			}catch(Exception e){
				
			}
			
			
		}
		 appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		 
         return appiumDriver;
	}
	public synchronized boolean terminateOnFailure() {

        try {
            return executionStatus.get();
        } catch (RuntimeException e) {
            APP_LOG.error("" + e);
            return false;
        }
    }

	public static String generateRandomStringWithPrefix(
            int size, String prefix) {
        return prefix + generateRandomString(size);
    }
	public static String generateRandomString(int size) {
        StringBuffer num = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            num.append((char)(65 + (random.nextInt(100) %26)));
        }
        return num.toString();
    }
	

	public String generateLocalRandomrange()
	{
		 Random rand = new Random();
		
		 int randomInt = rand.nextInt(900) + 100;
		String routeNumber ="TST"+randomInt;
		
		return routeNumber;
	}
	

	public static String generateRandomNumber(int digits) {
        StringBuffer num = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < digits; i++) {
            num.append(random.nextInt(9));
        }
        
        return num.toString();
    }

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////Chirag Narang/////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	protected String imagePath = folderpath+"\\Images\\";
//	public static ExtentReports extentReports;
//	public static ExtentTest test;
//	private static String folderpath; 
//	public static String folder;
//	DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_SSaa");
//	Date date = new Date();
//	protected int failStepsCount=0;
//	
//	public void initReporting() 
//	{
//		createFolder();
//		extentReports = new ExtentReports(folderpath + "\\ExtentReport" + dateFormat.format(date) + ".html", false);
//		extentReports.loadConfig(new File("extent-config.xml"));
//	}
//	
//	
//	static String folderName;
//	static String ImagesFolder;
//    
//	public String createFolder()
//	{
//		String basePath = new File("").getAbsolutePath();
//		String Uniqueid = generateCurrentDateNumber();
//		folderName = "";
//		try
//		{
//			File file = new File(basePath+"\\test-output");
//	        if (!file.exists()) 
//	        {
//	            if (file.mkdir()) 
//	            {
//	                System.out.println("Directory is created!");
//	            } 
//	        }
//	        folderName = basePath+"\\test-output\\"+Uniqueid;
//	        file = new File(folderName);
//	        if (file.mkdir()) 
//	        {
//	            System.out.println("Run result directory '"+folderName+"' is created!");
//	            file = new File(folderName+"\\Images");
//	            if (file.mkdir()) 
//	            {
//	                System.out.println(file+" 'Images' directory is created!");
//	                ImagesFolder=folderName+"\\Images";
//	            }
//	        }
//	        else 
//	        {
//	            System.out.println("Failed to create sub-directory!");
//	        }
//		}
//		catch(Exception e)
//		{
//			folderName = "";
//			System.out.println("Oops...there is something wrong while creating reporting folder(s).");
//			
//		}
//		
//		setFolderpath(folderName);
//		setfolder(Uniqueid);
//	     return folderName;   
//		
//	}
//
//	private String generateCurrentDateNumber() 
//	{
//		Date dNow = new Date();
//		SimpleDateFormat sFrmt = new SimpleDateFormat("yyyyMMddhhmmss");
//		return sFrmt.format(dNow);
//	}
//	
//	public String getscreenshot(String fileName, WebElement element) 
//	{
//		// Thread.sleep(1000);
//		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_SSaa");
//		Date date = new Date();
//		String screenshotfile = dateFormat.format(date) + ".png";
//		try 
//		{
//			if (element != null) 
//			{
//				JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
//				javascript.executeScript("arguments[0].style.border='3px solid red'", element);
//				// }Change - Moved Screenshot Command out of bracket
//				FileUtils.copyFile(((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE),
//						new File(fileName+screenshotfile));
//				javascript.executeScript("arguments[0].style.border='3px solid black'", element);
//			} 
//			else 
//			{
//				JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
//				FileUtils.copyFile(((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE),
//						new File(fileName+screenshotfile));
//
//			}
//		} 
//		catch (Exception e) 
//		{
//
//			try
//			{
//				JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
//				FileUtils.copyFile(((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE),
//						new File(fileName+screenshotfile));
//			}
//			catch(Exception e1)
//			{
//				System.out.println(e1.getMessage());
//			}
//			e.printStackTrace();
//		}
//
//		return "./../"+folder+"/Images/" + screenshotfile;
//	}
//
//	public void extentReportlogSteps_Update(LogStatus status, String stepname, String Details, String filename,
//			WebElement element) 
//	{
//		String path = System.getProperty("user.dir");
//		
//		if(passScreenshots.equalsIgnoreCase("yes") || status.equals(LogStatus.FAIL))
//		test.log(status, stepname, "<div align='left' style='float:left; width: 60%;'>"+Details+"</div>"+"<div align='right' style='float:right; text-align:center;'><a href="+ getscreenshot(filename, element) + ">Screenshot</a></div>");
//		else
//		test.log(status, stepname, "<div align='left' style='float:left; width: 60%;'>"+Details+"</div>"+"<div align='right' style='float:right; text-align:center;'></div>");
//		extentReports.flush();
//		extentReports.endTest(test);	
////		if(status.equals(LogStatus.FAIL))
////			failStepsCount++;
////		if(failedStepsCount==failStepsCount){
////				failStepsCount=0;
////			    throw new AssertionError("A clear description of the failure");
////			}
//	}
//
//	public String getImageFileLocation() 
//	{
//		String imagePath = folderpath+"\\Images\\";
//		return imagePath.replace("test-output/", "");
//	}
//	
//	public void setFolderpath(String folderpath)
//	{
//		this.folderpath = folderpath;
//	}
//	
//	public String getfolderpath()
//	{
//		return this.folderpath;
//	}
//	
//	public void setfolder(String foldername)
//	{
//		this.folder = foldername;
//	}
//	public String getfoldername()
//	{
//		return this.folder;
//	}
//	
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	public void getOR(){
//		InputStream input = null;
//		try {
//			String ORfile=new File("").getAbsolutePath();
//			File file = new File(ORfile+"\\SprintTestRepository\\pomOR.properties");
//			input = new FileInputStream(file);
//			prop.load(input);
//		} catch (IOException ex) {
//			System.out.println("Error while fetching data from properties file");
//			ex.printStackTrace();
//		}
//	}
//	public WebElement GetElement(String value, int timeoutValue) 
//	{
//		By getElement=null;
//		//converting string to By type
//		try {
//		getElement=Byvalue(prop.getProperty(value));
//		}
//		catch (Exception e) 
//		{
//			System.out.println("Element not found" +e);
//		}
//		WebDriverWait wait = new WebDriverWait(webDriver, 5);
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(getElement));
//			element=null;
//			element=webDriver.findElement(getElement);
//			
//		} catch (Exception e) 
//		{
//			System.out.println("Element not found" +e);
//		}
//		wait = null;
//		return element; 
//	}
//	
//	public WebElement ConvertByToWebElement(By ele)
//	{
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
//			element=null;
//			element=webDriver.findElement(ele);
//			
//		} catch (Exception e) 
//		{
//			System.out.println("Element not found" +e);
//		}
//		wait = null;
//		return element; 
//	}
//	 public static By Byvalue(String value)
//	 {
//		 By data=null;
//		 try{
//			if(value.contains("id~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.id(value1[1]);
//				
//			}
//			else if(value.contains("xpath~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.xpath(value1[1]);
//			}
//			else if(value.contains("css~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.cssSelector(value1[1]);
//			}
//			else if(value.contains("classname~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.className(value1[1]);
//			}
//			else if(value.contains("linktext~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.linkText(value1[1]);
//			}
//			else if(value.contains("partiallinktext~"))
//			{
//				String value1[]=(value.split("~"));
//				data=By.partialLinkText(value1[1]);
//			}
//			
//	     }
//			 catch(Exception e){
//				 System.out.println("Unable to find element" +e);
//			 }
//			return data;
//	 }
	
}