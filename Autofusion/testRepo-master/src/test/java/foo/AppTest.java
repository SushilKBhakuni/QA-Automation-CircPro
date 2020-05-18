package foo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AppTest{
	
	
	public static WebDriver driver ;
	
 App app;
	@BeforeSuite
	public void setup(){
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Selenium\\chromedriver.exe");
		
		driver= new ChromeDriver();
		
		app = new App();
		
	}
	
	@Test
	public void login(){
		
		driver.get("http://google.com");
		
		
	
		System.out.println("Inside the login page");
		
		Assert.assertEquals(driver.getTitle(), "Google");
	}
	
	@Test
	public void verifyAccount(){
		
	
		System.out.println("Inside Verify ");
		
		Assert.assertEquals(app.getAccountName(), "QATester");
	}
	
	@Test
	public void verifyAddAccount(){
		
	
		System.out.println("Inside add account");
		
		Assert.assertEquals(app.addAccount(2, 3), 5);
	}
	
	@Test
	public void verifyMultiplyAccount(){
		
	
		System.out.println("Inside mutiply account");
		
		Assert.assertEquals(app.mutiplyAccount(4, 5),20);
	}
	
	@Test
	public void verifyDeleteAccount(){
		
	
		System.out.println("Inside Delete  account");
		
		Assert.assertEquals(app.deleteAccount(10, 5),5);
	}
	
	
	
	@AfterSuite
	public void tearDown(){
		
		driver.close();
	}
	
	
	
	
	
	
}
