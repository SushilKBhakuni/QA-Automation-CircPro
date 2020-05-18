package com.autofusion.keywords;

/**
 * @author nitin.singh
 */
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.autofusion.bean.CommonUtility;
import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;

public class PerformAction extends FindElement {

	public static String filePath = "";
	public static String st = "";
	public static String et = "";
	public static String stepDescription = "";
	public static String userInputValue = "";
	private static KeywordsFactory KeywordsFactory = new KeywordsFactory();

	/**
	 * This function take args in Map
	 * 
	 * dataMap.put(KeywordConstant.ACTION_TO_PERFORM, ACTION_CLICK);
	 * dataMap.put(KeywordConstant.ELEMENT_LOCATOR, "Xpath");
	 * dataMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");
	 * dataMap.put(KeywordConstant.STEP_DESCRIPTION, "Click on ..");
	 * 
	 * 
	 * @param argsMap
	 * @return
	 */
	public static String execute(Map<String, String> argsMap) {

		String currentKeyword = "";
		String result = "";
		try {
			if (argsMap.containsKey(KeywordConstant.ACTION_TO_PERFORM)) {
				currentKeyword = argsMap.get(KeywordConstant.ACTION_TO_PERFORM);
			}
			result = execute(currentKeyword, argsMap);
			assertResult(currentKeyword, result);
		} catch (Exception e) {
			APP_LOG.debug("execute " + e);
		}
		return result;
	}

	/**************************
	 * These functions are depricate now
	 **********************************************************/
	/***
	 * 
	 * @param currentKeyword
	 * @param argsMap
	 * @return
	 */
	public static String execute(String currentKeyword, Map<String, String> argsMap) {
		st = CommonUtility.now("yyyy-MM-dd hh:mm:ss");
		String keywordResult = Constants.FAIL;

		try {
			userInputValue = argsMap.get(KeywordConstant.ELEMENT_INPUT_VALUE);
			Object objKeyword = KeywordsFactory.getInstance(currentKeyword,
					APP_LOG);
			Method method = objKeyword.getClass()
					.getDeclaredMethod(currentKeyword, new Class[]{Map.class});
			keywordResult = (String) method.invoke(objKeyword,
					new Object[]{argsMap});
		} catch (Exception e) {
			System.out.println("" + e.getCause());
		}
		try {
			if (keywordResult.contains(Constants.FAIL)) {

				/*
				 * filePath = takeScreenShot( projectPath + device +
				 * "//report//Browser//" + browser + "//" + reportStartTime +
				 * "//", InitClass.now("dd.MMMMM.yyyyhh.mm.ss"));
				 */

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		et = CommonUtility.now("yyyy-MM-dd hh:mm:ss");

		return keywordResult;
	}

	/**
	 * 
	 * @param currentKeyword
	 * @param elementLocator
	 * @param inputValue
	 * @return
	 */

	public static String execute(String currentKeyword, String elementLocator,
			String inputValue) {
		String result;
		Map<String, String> argsMap = new HashMap<String, String>();
		argsMap.put(KeywordConstant.ELEMENT_LOCATOR, elementLocator);
		argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, inputValue);

		result = execute(currentKeyword, argsMap);
		assertResult(currentKeyword, result);

		return result;
	}

	/**
	 * 
	 * @param currentKeyword
	 * @param elementLocator
	 * @return
	 */

	public static String execute(String currentKeyword, String elementLocator) {
		Map<String, String> argsMap = new HashMap<String, String>();
		argsMap.put(KeywordConstant.ELEMENT_LOCATOR, elementLocator);
		argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");
		String result = execute(currentKeyword, argsMap);
		assertResult(currentKeyword, result);
		return result;
	}

	/**
	 * 
	 * @param currentKeyword
	 * @return
	 */

	public static String execute(String currentKeyword) {

		Map<String, String> argsMap = new HashMap<String, String>();
		argsMap.put(KeywordConstant.ELEMENT_LOCATOR, "");
		argsMap.put(KeywordConstant.ELEMENT_INPUT_VALUE, "");

		String result = execute(currentKeyword, argsMap);

		assertResult(currentKeyword, result);

		return result;

	}

	/************************************************************************************/

	public static void assertResult(String currentKeyword, String result) {
		if (result.contains(Constants.PASS)) {
			Assert.assertTrue(true);
			insertStepsDetails(currentKeyword, result);
		} else {

			insertStepsDetails(currentKeyword, result);
			filePath = "";
			actualDataPresentOnUi = ""; // Reseting the UI data
			failureErrorMessageCollector = "";
			try {
				Assert.assertTrue(false);
			} catch (AssertionError e) {
				APP_LOG.debug("Assert " + e);
			}
		}
	}
	
	public static void insertStepsDetails(String currentKeyword, String result){
//		ReportSqls.insertTestStepDetails(runningSuitName, "TC-"+testCaseIdPom, "TS-"+testStepIdPom, currentKeyword, executionEnviroment, result, filePath, "", 
//				failureErrorMessageCollector, CommonUtility.now("yyyy-MM-dd"), st , et, batchId, "Y",device, stepDescription, userInputValue );
		testStepIdPom++;
	}

}