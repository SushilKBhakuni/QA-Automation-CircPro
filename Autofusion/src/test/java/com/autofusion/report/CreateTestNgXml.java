package com.autofusion.report;
/**
 * @author nitin.singh
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xls_Reader;

@SuppressWarnings({"rawtypes","unused","all"})
public class CreateTestNgXml {

	public static void createXmlSuite(String locationPath, String appToRun, String ip, String suiteName,
			ArrayList testCaseLst, String environment, String browser){


		Xls_Reader suiteXls = new Xls_Reader(locationPath+"\\"+appToRun.toLowerCase()+"\\TestCases\\"+suiteName+".xlsx");

		XmlSuite objXmlSuite= new XmlSuite();
		objXmlSuite.setName(suiteName);

		HashMap<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("Browser", browser);
		paramMap.put("Environment", environment);
		paramMap.put("AppToRun", appToRun);
		paramMap.put("runOnMachine", ip);
		paramMap.put("projectPath", locationPath);


		objXmlSuite.setParameters(paramMap);
		XmlClass objXmlClass;
		List<XmlClass> lstClasses = new ArrayList<XmlClass>();
		List<XmlSuite> lstSuite = new ArrayList<XmlSuite>();

		for(int testCaseRowCount = 2; testCaseRowCount <= suiteXls.getRowCount(Constants.TEST_CASES_SHEET); testCaseRowCount++){

			lstClasses.clear();
			lstSuite.clear();
			String tCaseID = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, testCaseRowCount);
			String className = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "ClassName", testCaseRowCount);
			String runMode = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, testCaseRowCount);
			String tCaseDesc = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DESCRIPTION, testCaseRowCount);
			String packageName = suiteXls.getCellData(Constants.TEST_CASES_SHEET, "Package", testCaseRowCount);
			String flag = suiteXls.getCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_FLAG, testCaseRowCount);


			if(runMode.endsWith("Y"))
			{
				try {
					XmlSuite suite= new XmlSuite();
					suite.setName(suiteName);
					XmlTest test = new XmlTest(suite);
					test.setName(tCaseID);
					List<XmlClass> classes = new ArrayList<XmlClass>();
					classes.add(new XmlClass(Class.forName(className)));
					test.setXmlClasses(classes) ;
					List<XmlSuite> suites = new ArrayList<XmlSuite>();
					suites.add(suite);
					TestNG tng = new TestNG();
					tng.setXmlSuites(suites);
					tng.run();
				} catch (ClassNotFoundException e) {
					System.out.println("TestNG run Failed-------->"+e);
				}
			}

		}

	}

	public static ArrayList createarryLst(String locationPath, String appToRun, ArrayList arylst, String suiteName)
	{
		Xls_Reader suiteXls = new Xls_Reader(locationPath+"\\"+appToRun.toLowerCase()+"\\TestCases\\"+suiteName+".xlsx");

		for(int testCaseRowCount = 2; testCaseRowCount <= suiteXls.getRowCount(Constants.TEST_SUITE_SHEET); testCaseRowCount++){
			String tCaseID = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_TSID, testCaseRowCount);
			String runMode = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_RUNMODE, testCaseRowCount);
			String tCaseDesc = suiteXls.getCellData(Constants.TEST_SUITE_SHEET, Constants.COL_HEAD_DESCRIPTION, testCaseRowCount);
			if(runMode.endsWith("Y"))
			{
				arylst.add(tCaseID);
			}
		}
		return arylst;

	}
}
