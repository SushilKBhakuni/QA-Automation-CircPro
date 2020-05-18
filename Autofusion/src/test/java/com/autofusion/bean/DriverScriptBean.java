package com.autofusion.bean;
/**
 * @author nitin.singh
 */
public class DriverScriptBean {

	String projectBasePath;
	String selectedDeviceId;
	String browserToOpen;
	String isDataDriven;
	int lastRowCounter = 2;
	String lastTestCaseId ;
	String currentTestCaseId ;
	public String getSelectedDeviceId() {
		return selectedDeviceId;
	}

	public void setSelectedDeviceId(String selectedDeviceId) {
		this.selectedDeviceId = selectedDeviceId;
	}

	String currentSuitName;
	String executionStartTime;
	String reportBasePath;
	public String executionDevice = "";
	public String executionDeviceCategory = "";
	public String exutionEnvironment = "";
   
	public String testSuitDesc;
    public String batchId;
    
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getTestSuitDesc() {
		return testSuitDesc;
	}

	public void setTestSuitDesc(String testSuitDesc) {
		this.testSuitDesc = testSuitDesc;
	}
    	
	
	public String getExecutionDevice() {
		return executionDevice;
	}

	public void setExecutionDevice(String executionDevice) {
		this.executionDevice = executionDevice;
	}

	public String getReportBasePath() {
		return reportBasePath;
	}

	public void setReportBasePath(String reportBasePath) {
		this.reportBasePath = reportBasePath;
	}

	public String getExecutionStartTime() {
		return executionStartTime;
	}

	public void setExecutionStartTime(String executionStartTime) {
		this.executionStartTime = executionStartTime;
	}

	public String getCurrentSuitName() {
		return currentSuitName;
	}

	public void setCurrentSuitName(String currentSuitName) {
		this.currentSuitName = currentSuitName;
	}

	public String getLastTestCaseId() {
		return lastTestCaseId;
	}

	public void setLastTestCaseId(String lastTestCaseId) {
		this.lastTestCaseId = lastTestCaseId;
	}

	public String getCurrentTestCaseId() {
		return currentTestCaseId;
	}

	public void setCurrentTestCaseId(String currentTestCaseId) {
		this.currentTestCaseId = currentTestCaseId;
	}

	
	 

	public int getLastRowCounter() {
		return lastRowCounter;
	}

	public void setLastRowCounter(int lastRowCounter) {
		this.lastRowCounter = lastRowCounter;
	}

	public String getIsDataDriven() {
		return isDataDriven;
	}

	public void setIsDataDriven(String isDataDriven) {
		this.isDataDriven = isDataDriven;
	}

	public String getProjectBasePath() {
		return projectBasePath;
	}

	public void setProjectBasePath(String projectBasePath) {
		this.projectBasePath = projectBasePath;
	}
	
	public String getBrowserToOpen() {
		return browserToOpen;
	}

	public void setBrowserToOpen(String browserToOpen) {
		this.browserToOpen = browserToOpen;
	}
	 public String getExecutionDeviceCategory() {
			return executionDeviceCategory;
		}

		public void setExecutionDeviceCategory(String executionDeviceCategory) {
			this.executionDeviceCategory = executionDeviceCategory;
		}

		public String getExutionEnvironment() {
			return exutionEnvironment;
		}

		public void setExutionEnvironment(String exutionEnvironment) {
			this.exutionEnvironment = exutionEnvironment;
		}

	

}
