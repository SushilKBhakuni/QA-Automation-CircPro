package com.autofusion.keywords;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReTryFailedTests implements IRetryAnalyzer {

	private int retryCount = 0;
	private int maxRetryCount =0;

	/**
	 * @author Mohit Gupta
	 * @date 15 Feb,17
	 */
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {

			retryCount++;

			return true;
		}
		return false;
	}

}
