package com.autofusion.android;

import io.appium.java_client.AppiumDriver;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;

/**
 * 
 * @author nitin.singh
 *
 *This class is for mobileWeb
 *
 */
@SuppressWarnings("rawtypes")
public class KeywordAndroid {

	public static AppiumDriver driver = null;
	public static Logger APP_LOGS;

	public KeywordAndroid(){
		//AndroidCommon objAndroidCommon = new AndroidCommon(this.APP_LOGS);
		//driver = objAndroidCommon.initializeAndroidDevice("Web","");
	}
	
	public static void scrollNavigation(AppiumDriver wd, String widID){
		
		JavascriptExecutor js = (JavascriptExecutor) wd;
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("text", "Mashups");
		swipeObject.put("direction", "right");
		swipeObject.put("element", widID);
		js.executeScript("mobile: scrollTo", swipeObject);
		wait(200);
	}
	/*	
		public static void clickKSTab(AppiumDriver wd, String tabName){
			
			int googleapi=Integer.parseInt(AndroidCommon.getDeviceAPILevel().trim());
			if(googleapi==10 && !getDeviceModel().equalsIgnoreCase("google_sdk"))
			{
				wait(200);
				List<WebElement> view1=wd.findElements(By.className("android.widget.HorizontalScrollView"));
				WebElement gelem=view1.get(0);
				gelem.click();
				wait(200);
				List<WebElement> TextView1 = wd.findElements(By.className("android.widget.TextView"));
				wait(300);
				for(int k=0;k<TextView1.size();k++)
				{
					if(!wd.findElements( By.className("android.widget.TextView") ).isEmpty())
					{
						wait(300);
						String temp=wd.findElements(By.className("android.widget.TextView")).get(k).getText();
						System.out.println("TabName: "+temp);
						if(temp.equalsIgnoreCase(tabName))
						{
							wd.findElements(By.className("android.widget.TextView")).get(k).click();
							wait(100);break;
						}
					}
					//if(i==4){break;}
				}
			}
			//-------------------------------
			if (getDeviceModel().equalsIgnoreCase("google_sdk") || getDeviceType().equalsIgnoreCase("emulator"))
			{
				wait(500);
				WebDriverWait wait = new WebDriverWait(wd, 50);
	            WebElement element =  wait.until(ExpectedConditions.elementToBeClickable(By.className("android.widget.HorizontalScrollView")));
				List<WebElement> view1=wd.findElements(By.className("android.widget.HorizontalScrollView"));
				WebElement view2=view1.get(0);
				wait(200);
				String widID = ((RemoteWebElement)(view2)).getId();
				List<WebElement> TextView = view2.findElements(By.className("android.widget.TextView"));
				wait(200);
				for(WebElement elem : TextView)
				{
					strState.add(i, elem.getText());
					i++;
					if(elem.getText().equalsIgnoreCase(tabName))
					{
						elem.click();
						wait(50);
						i=5;
						break;
					}
				}
				if(i<5)
				{
					scrollNavigation(wd, widID);
					List<WebElement> hview=wd.findElements(By.className("android.widget.HorizontalScrollView"));
					WebElement hview2=hview.get(0);
					wait(200);
					List<WebElement> hTextView = hview2.findElements(By.className("android.widget.TextView"));
					wait(200);
					for(WebElement elem : hTextView)
					{
						if (!strState.contains(elem.getText()))
						{
							strState.add(j, elem.getText());
							j++;
							if(elem.getText().equalsIgnoreCase(tabName))
							{
								elem.click();
								wait(50);
								break;
							}
						}
					}
				}
				for (String s : strState) {
				    System.out.println("Tab Name: "+ s);
				}
			}
			if(getDeviceModel().equalsIgnoreCase("Nexus 7"))
			{
				wd.findElement(By.className("android.widget.Spinner")).click();
				wait(50);
				List<WebElement>tab2=wd.findElements(By.className("android.app.ActionBar$Tab"));
				int tablength=tab2.size();
				int i=0;
				for (WebElement elem : tab2)
				{
					WebElement elem1 = elem.findElement(By.className("android.widget.TextView"));
					System.out.println("TabName: "+elem1.getText());
					String temp=elem1.getText();
					if(temp.equalsIgnoreCase(tabName))
					{
						elem1.click();
						wait(100);
						break;
					}
					i++;
					wait(50);
					if(i<tablength){wd.findElement(By.className("android.widget.Spinner")).click();wait(50);}

				}
				
			}
			
		}
	//############################
		
		
		public static void NavigateListOptions(AppiumDriver wd)
		{
			int k=0;
			System.out.println("In Test2....");
			List<WebElement> FrameView = wd.findElements(By.className("android.widget.FrameLayout"));
			for (WebElement welem : FrameView)
			{
				wait(500);
				List<WebElement> ListView = welem.findElements(By.className("android.widget.ListView"));
				System.out.println(ListView.size());
				if(ListView.size()>1)
				{
					k=1;
				}
				if(ListView.size()==1)
				{
					k=0;
				}
				WebElement listelem=ListView.get(k);
				List<WebElement> TextView1 = listelem.findElements(By.className("android.widget.TextView"));
				for (WebElement elem4 : TextView1)
				{
					System.out.println(elem4.getText());
					elem4.click();
					wait(300);
					try {
						boolean kb=verifyKeyboard();
						if(kb==true){wd.navigate().back();wait(100);}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wd.navigate().back();
					wait(500);

				}
				break;
			}
			
		}
		
		public static void NavigateListItem(AppiumDriver wd,String itemName){
			
			int k=0;
			System.out.println("Clicking on the List item: " + itemName);
			List<WebElement> FrameView = wd.findElements(By.className("android.widget.FrameLayout"));
			for (WebElement welem : FrameView)
			{
				wait(500);
				List<WebElement> ListView = welem.findElements(By.className("android.widget.ListView"));
				if(ListView.size()>1)
				{
					k=1;
				}
				if(ListView.size()==1)
				{
					k=0;
				}
				WebElement listelem=ListView.get(k);
				List<WebElement> TextView1 = listelem.findElements(By.className("android.widget.TextView"));
				for (WebElement elem4 : TextView1)
				{
					//System.out.println(elem4.getText());
					if(elem4.getText().equalsIgnoreCase(itemName))
					{
						elem4.click();
						wait(300);
						break;
					}
					wait(300);

				}
				break;
			}
			
		}


*/		public static void wait(int n)
		{
			long t0,t1;
			t0=System.currentTimeMillis();
			do{
				t1=System.currentTimeMillis();
			}
			while(t1-t0<n);
		}
			
	
}
