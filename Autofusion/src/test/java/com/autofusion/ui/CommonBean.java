package com.autofusion.ui;

import java.util.ArrayList;

public class CommonBean {

	
	public static String testCaseBasePath;
	public String browserToRun;
	public int currentTestStepRow = 2;
	private int currentTestCaseCount;    
	private ArrayList<String> finalTestResult;
	public String currentDataRow;
	private String isDataDriven;
	public static String cmdBrowserName="Firefox";
	public static String cmdSuitName = "";
	public static String testReport = "TestReport";
	public String reportFileName;
	public String reportStartTime;
	public String requestedSuiteToRun;
	public String currentThreadDDFlag;
	
	
	public static String getTestCaseBasePath() {
		return testCaseBasePath;
	}
	public static void setTestCaseBasePath(String testCaseBasePath) {
		CommonBean.testCaseBasePath = testCaseBasePath;
	}
	public String getBrowserToRun() {
		return browserToRun;
	}
	public void setBrowserToRun(String browserToRun) {
		this.browserToRun = browserToRun;
	}
	public int getCurrentTestStepRow() {
		return currentTestStepRow;
	}
	public void setCurrentTestStepRow(int currentTestStepRow) {
		this.currentTestStepRow = currentTestStepRow;
	}
	public int getCurrentTestCaseCount() {
		return currentTestCaseCount;
	}
	public void setCurrentTestCaseCount(int currentTestCaseCount) {
		this.currentTestCaseCount = currentTestCaseCount;
	}
	public ArrayList<String> getFinalTestResult() {
		return finalTestResult;
	}
	public void setFinalTestResult(ArrayList<String> finalTestResult) {
		this.finalTestResult = finalTestResult;
	}
	public String getCurrentDataRow() {
		return currentDataRow;
	}
	public void setCurrentDataRow(String currentDataRow) {
		this.currentDataRow = currentDataRow;
	}
	public String getIsDataDriven() {
		return isDataDriven;
	}
	public void setIsDataDriven(String isDataDriven) {
		this.isDataDriven = isDataDriven;
	}
	public static String getCmdBrowserName() {
		return cmdBrowserName;
	}
	public static void setCmdBrowserName(String cmdBrowserName) {
		CommonBean.cmdBrowserName = cmdBrowserName;
	}
	public static String getCmdSuitName() {
		return cmdSuitName;
	}
	public static void setCmdSuitName(String cmdSuitName) {
		CommonBean.cmdSuitName = cmdSuitName;
	}
	public static String getTestReport() {
		return testReport;
	}
	public static void setTestReport(String testReport) {
		CommonBean.testReport = testReport;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public String getReportStartTime() {
		return reportStartTime;
	}
	public void setReportStartTime(String reportStartTime) {
		this.reportStartTime = reportStartTime;
	}
	public String getRequestedSuiteToRun() {
		return requestedSuiteToRun;
	}
	public void setRequestedSuiteToRun(String requestedSuiteToRun) {
		this.requestedSuiteToRun = requestedSuiteToRun;
	}
	public String getCurrentThreadDDFlag() {
		return currentThreadDDFlag;
	}
	public void setCurrentThreadDDFlag(String currentThreadDDFlag) {
		this.currentThreadDDFlag = currentThreadDDFlag;
	}
	
	
	
	
}
