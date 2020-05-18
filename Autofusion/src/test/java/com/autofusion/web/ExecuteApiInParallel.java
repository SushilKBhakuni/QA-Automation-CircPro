package com.autofusion.web;

import java.util.ArrayList;

@SuppressWarnings({"all"})
public class ExecuteApiInParallel implements Runnable{

	public String locationPath;
	public String appToRun;
	public String ip;
	public String suiteName;
	public String environment;
	public String browserToRun;
	public String testType;
	public ArrayList testCaseLst;
	public String numOfThread;
	
	public static void main(String args[]) {
		ExecuteApiInParallel obj = new ExecuteApiInParallel();
		
		
		
		//obj.createThread("C:\\Nitin\\AutofusionRepositorySprint","api","127.0.0.1","API_Sprint","DEVAPI","api", "keyword");
		obj.createThread(args[0],args[1],args[2],args[3],args[4],args[5],args[6], args[7]);

	}
	
	
	@Override
	public void run() {
		new DriverScript().mainMethodWebSingle(locationPath, appToRun, ip, suiteName,
				   testCaseLst, environment, browserToRun, testType);
	}

	
	public void createThread(String locationPath, String appToRun, String ip, String suiteName,
			   String environment, String browserToRun, String testType, String numOfThread) {

		
		this.locationPath = locationPath;
		this.appToRun=appToRun;
		this.ip = ip;
		this.suiteName = suiteName;
		this.environment = environment;
		this.browserToRun = browserToRun;
		this.testType=testType;
		this.testCaseLst=testCaseLst;
		this.numOfThread = numOfThread;
		/*ExecuteScript objExecuteScript = new ExecuteScript();
		objExecuteScript.setAppToRun(appToRun);
		objExecuteScript.setBrowserToRun(browserToRun);
		objExecuteScript.setEnvironment(environment);
		objExecuteScript.setIp(ip);
		
		*/
		 int threadCount = Integer.valueOf(numOfThread);
		for(int i=0; i<=threadCount; i++){
			new Thread(() -> {
				new DriverScript().mainMethodWebSingle(locationPath, appToRun, ip, suiteName,
						   testCaseLst, environment, browserToRun, testType);
			}).start();
		}
		
		/*
		Thread t1 = new Thread(objExecuteScript);
		t1.start();*/
	}


	public String getLocationPath() {
		return locationPath;
	}


	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}


	public String getAppToRun() {
		return appToRun;
	}


	public void setAppToRun(String appToRun) {
		this.appToRun = appToRun;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getSuiteName() {
		return suiteName;
	}


	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}


	public String getEnvironment() {
		return environment;
	}


	public void setEnvironment(String environment) {
		this.environment = environment;
	}


	public String getBrowserToRun() {
		return browserToRun;
	}


	public void setBrowserToRun(String browserToRun) {
		this.browserToRun = browserToRun;
	}


	public String getTestType() {
		return testType;
	}


	public void setTestType(String testType) {
		this.testType = testType;
	}


	public ArrayList getTestCaseLst() {
		return testCaseLst;
	}


	public void setTestCaseLst(ArrayList testCaseLst) {
		this.testCaseLst = testCaseLst;
	}
	
}
