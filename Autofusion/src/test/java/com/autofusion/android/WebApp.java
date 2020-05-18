package com.autofusion.android;
/**
 * @author nitin.singh
 */


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
public class WebApp 
{
	public static AppiumDriver driver = null;
	public static Properties prop=new Properties();
	public static ArrayList<String> strState=new ArrayList<String>();
/*	public static int apilevel=Integer.parseInt(Android_Common.getDeviceAPILevel().trim());
	public static String deviceversion=Android_Common.getDeviceVersion();
	public static String devicemodel=Android_Common.getDeviceModel();
	public static String deviceserial=Android_Common.getDeviceSerial();
	public static String devicetype=Android_Common.getDeviceType();
*/	
	
	public void initializeAndroidDevice(){
		
		
	}
	
	@BeforeClass
	public static void setUp() throws Exception 
	{
        DesiredCapabilities capabilities = new DesiredCapabilities();
     /////   if(apilevel<17)
     //   {
     ///   	capabilities.setCapability("automationName", "Selendroid");
    //    	System.out.println("In Selendroid Mode....");
    //    }
    //    else
    //    {
        	capabilities.setCapability("automationName", "Appium");
        	System.out.println("In default Appium Mode....");
   //     }

        	capabilities.setCapability("platformName", "Android");
    	//	capabilities.setCapability("browserName", "Chrome");
    		//capabilities.setCapability("chromedriver-port", "9550");
    		
//    		capabilities.setCapability("udid", "015d4b3404440c0e");
  //      	capabilities.setCapability("platformVersion", "5.0.2");
    //    	capabilities.setCapability("deviceName", "Nexus 7");
        		
        	
//       capabilities.setCapability("udid", "015d4b3404440c0e");
//    	capabilities.setCapability("platformVersion", "5.0.2");
//    	capabilities.setCapability("deviceName", "Nexus 7");
    	
//    	capabilities.setCapability("udid", "emulator-5554");
//    	capabilities.setCapability("platformVersion", "4.4.2");
//    	capabilities.setCapability("deviceName", "sdk");
    	
    	
		capabilities.setCapability("platformVersion", "4.4.4");
    	capabilities.setCapability("udid", "ZX1D62GFX9");
    	capabilities.setCapability("deviceName", "OPTIONAL");

    	
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		
	}
	@AfterClass
	public static void tearDown() throws Exception 
	{
		driver.quit();
	}
	@Test
	public void test1_AcceptAgreement()
	{
		System.out.println("Test Case 1 - Accept the License Agreement");
		System.out.println("------------------------------------------------");
		driver.get("http://m.snapdeal.com");
		driver.findElement(By.xpath(".//*[@id='searchField']")).sendKeys("Mobile");
		driver.findElement(By.xpath(".//*[@id='btnSearch']")).click();
		driver.findElement(By.xpath(".//*[@id='categorylistWrapper']/div[1]/a/div/div[3]")).click();
		//String t = driver.findElement(By.xpath("html/body/div[8]/div/div[2]/b")).getText();
		
		
		
		
		try {
			driver.wait(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Android_Common.wait(1000);
		
		System.out.println("------------------------------------------------");
	}
	
}
