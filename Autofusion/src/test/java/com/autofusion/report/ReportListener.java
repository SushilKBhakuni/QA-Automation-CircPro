package com.autofusion.report;
/**
 * @author nitin.singh
 */
import java.util.ArrayList;
import java.util.Collections;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.autofusion.BaseClass;
import com.autofusion.bean.CommonUtility;
import com.autofusion.constants.Constants;

@SuppressWarnings("all")
public class ReportListener extends BaseClass implements ITestListener{
    public String testStartTime = "";
    public String testEndTime = "";
    public String suiteStartTime = "";
    public String suiteEndTime = "";
    public ArrayList<String> finalSuiteTestResult = new  ArrayList<String>();
    public String suiteName = "";
    
	@Override
	public void onFinish(ITestContext arg0) {
		suiteEndTime = CommonUtility.now("yyyy-MM-dd hh:mm:ss");
		
		int totPass = Collections.frequency(finalSuiteTestResult, Constants.PASS);
		int totFail  = Collections.frequency(finalSuiteTestResult, Constants.FAIL);
		int totSkip  = Collections.frequency(finalSuiteTestResult, Constants.SKIP);
 		
		int	totalTestCase = totSkip+totFail+totPass;
		 
	
//		ReportSqls.insertSuiteDetails(suiteName, suiteName, executionEnviroment, totalTestCase, finalSuiteTestResult, 
//					CommonUtility.now("yyyy-MM-dd"), suiteStartTime, suiteEndTime, batchId, "Y", device);
//	
		//ReportSqls.insertAutomationSummary("",executionEnviroment, CommonUtility.now("yyyy-MM-dd"),batchId);
	}

	@Override
	public void onStart(ITestContext argsTestResult) {
		suiteName = argsTestResult.getSuite().getName();
		suiteStartTime = CommonUtility.now("yyyy-MM-dd hh:mm:ss");
		System.out.println("Start");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		System.out.println("onTestFailedButWithinSuccessPercentage");		
	}

	@Override
	public void onTestFailure(ITestResult argsTestResult) {
		try{
			testEndTime=CommonUtility.now("yyyy-MM-dd hh:mm:ss");
			finalSuiteTestResult.add(Constants.FAIL);
			onTestEnd(argsTestResult,Constants.FAIL);	
			System.out.println("onTestFailure"+argsTestResult.getClass().getName());		
		}catch(AssertionError e){
			System.out.println("asdf"+e);
		}
	}

	@Override
	public void onTestSkipped(ITestResult argsTestResult) {
		testEndTime=CommonUtility.now("yyyy-MM-dd hh:mm:ss");
		
		onTestEnd(argsTestResult,Constants.SKIP);		
		finalSuiteTestResult.add(Constants.SKIP);
	}

	@Override
	public void onTestStart(ITestResult result) {
		testStartTime=CommonUtility.now("yyyy-MM-dd hh:mm:ss");
	}

	@Override
	public void onTestSuccess(ITestResult argsTestResult) {
		testEndTime=CommonUtility.now("yyyy-MM-dd hh:mm:ss");
		onTestEnd(argsTestResult,Constants.PASS);
		finalSuiteTestResult.add(Constants.PASS);
		System.out.println("onTestSuccess"+argsTestResult.getClass().getName());		
		
	}
	
	
	public void onTestEnd(ITestResult argsTestResult, String result){
		//String description = argsTestResult.getAttribute("name").toString();
		
//		ReportSqls.insertTestCaseDetails(suiteName, "TC-"+testCaseIdPom, argsTestResult.getMethod().getDescription(), executionEnviroment, 
//				result, CommonUtility.now("yyyy-MM-dd"), testStartTime, testEndTime, batchId, "Y", device);
		testCaseIdPom++;
		testStepIdPom = 1;
	}

}
