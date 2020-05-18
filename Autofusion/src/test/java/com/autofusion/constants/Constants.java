package com.autofusion.constants;

/**
 * @author nitin.singh
 */
public abstract class Constants {

	public static long WAIT_ELEMENT_PRESENT = 20;
	public static String TEST_SUITE_SHEET = "Test Suite";
	public static String EXECUTION_ON_DEVICE = "Device";
	public static String COMPONENT_NAME = "ComponentName";
	public static String TEST_WIN_SUITE_SHEET = "WindowTestSuite";
	public static String TEST_MBL_SUITE_SHEET = "MobileTestSuite";

	public static String BROWSER_DRIVER_LOCATION = "";
	public static String SELENIUM_PROFILE_LOCATION = "";
	public static String TEST_CASES_SHEET = "Test Cases";
	public static String TEST_STEPS = "Test Steps";
	public static String COL_HEAD_RUNMODE = "Runmode";
	public static String COL_HEAD_DESCRIPTION = "TestCaseDescription";
	public static String COL_HEAD_FLAG = "TestCaseFlag";
	public static String COL_HEAD_STEP_DESCRIPTION = "TestStepDescription";
	public static String COL_HEAD_KEYWORD = "Keyword";
	public static String COL_HEAD_KEYWORD_IOS = "KeywordIOS";
	public static String COL_HEAD_ELEMENT_ID = "ElementId";
	public static String COL_HEAD_DATA = "Data";
	public static String COL_HEAD_INDEX_POS = "IndexPosition";

	public static String COL_HEAD_DATA_DRIVEN = "DataDriven";
	public static String COL_DYNAMIC_XPATH = "DynamicXpathValue";
	public static String COL_INDEX_POSITION = "IndexPosition";
	public static String COL_HEAD_GO_TO = "JumpTo";
	public static String COL_HEAD_EXECUTION_STATUS = "ExecutionStatus";
	public static String EXE_RUNNING = "Running";
	public static String EXE_HANG = "Hang";
	public static String EXE_COMPLETE = "Complete";
	public static String EXE_INTERUPTED = "Inturupted";
	public static String TEST_COMMON_SHEET = "CommonSteps";
	public static String DEFAULT_BROWSER = "Firefox";

	public static String COL_HEAD_WAIT = "Wait";
	public static String COL_SKIP_STEP = "SkipTestStep";// skipping test step
	public static String SCREENSHOT_FOLDER = "screenshots";
	public static String SUIT_FILE_NAME = "suits.xlsx";

	public static String SUIT_FILE_NAME_HTMLBROWSERREPORT = "htmlXlsReport";
	public static String SUIT_FILE_SHEET = "Sheet1";
	public static String SUIT_FILE_SHEET_TWO = "Sheet2";
	public static String COL_HEAD_PASS = "Pass";
	public static String COL_HEAD_FAIL = "Fail";
	public static String COL_HEAD_SKIP = "Skipped";
	public static String COL_HEAD_TOTAL_EXECUTED_TC = "Total Executed Test Cases";

	public static String COL_HEAD_STATUS = "Status";
	public static String COL_HEAD_EndTime = "End Time";
	public static String COL_HEAD_Fail_TotalCount = "Fail/TotalCount";
	public static String COL_HEAD_StartTime = "Start Time";
	public static String COL_HEAD_TCID = "TCID";
	public static String COL_HEAD_TSID = "TSID";
	public static String COL_HEAD_RESULT = "Result";
	public static String COL_HEAD_TestScript = "TestScript#";
	public static String COL_HEAD_CURRENT_SUITE_NAME = "Current_SuiteName";

	public static String COL_HEAD_IDE_FILE_NAME = "IdeFileName";
	public static String COL_HEAD_CREATE_NEW_SUIT = "CreateNewSuit";
	public static String COL_HEAD_CREATE_NEW_TEST_CASE = "CreateNewTestCase";
	public static String COL_HEAD_APPEND_IN_TEST_CASE = "AppendInTestCase";

	public static String IDE_FILE_NAME = "configIde.xlsx";

	public static String COL_VARIABLE_NAME = "Variable Name";
	public static String COL_ID = "id";
	public static String COL_NAME = "name";
	public static String COL_CSS = "css";
	public static String COL_XPATH = "xpath";

	public static String PASS = "PASS";
	public static String FAIL = "FAIL";
	public static String SKIP = "SKIP";

	public static String PREFIX_DATA_SHEET = "DS";
	public static String PREFIX_DATA_CONFIG = "CON";
	public static String PREFIX_DATA_OR = "OR";

	public static String PREFIX_FIELD_XPATH = "xpath";
	public static String PREFIX_FIELD_NAME = "name";
	public static String PREFIX_FIELD_ID = "id";
	public static String PREFIX_FIELD_CSS = "css";
	public static String PREFIX_FIELD_LINKTEXT = "link"; // linkText
	public static String PREFIX_FIELD_PARTIALLINKTEXT = "partialLinkText";
	public static String PREFIX_FIELD_TAGNAME = "tagName";
	public static String PREFIX_FIELD_CLASSNAME = "className";

	public static String EXPECTED_RESULT = "ER";
	public static String SPLIT_PARAMETER = "|";
	public static String elementPrefixList[] = { "css", "id", "//", "name", "link", "partiallinktext", "tagName",
			"class" }; // "//" for xpath

	public static String COL_DATA_DRIVEN = "DataDriven";
	public static String DATA_SHEET = "Data Sheet";
	public static String COL_HEAD_TCDI = "TCDI";

	public static String PREFIX_TEST_STEP = "TS-";
	public static String PREFIX_TEST_CASE = "TC-";

	public static String COL_HEAD_CHROME = "Chrome";
	public static String COL_HEAD_IE = "InternetExplorer";
	public static String COL_HEAD_FF = "Firefox";
	public static String COL_HEAD_SAFARI = "Safari";
	public static String COL_HEAD_ANDROID_BWR = "Android";
	public static String COL_HEAD_IOS_BWR = "IOS";
	public static String COL_HEAD_CLASS_NAME = "ClassName";
	public static String COL_HEAD_PACKAGE = "Package";
	public static String PREFIX_FIELD_XPATHCHROME = "xpathChrome";

	public static String COL_HEAD_CHROME_LOG_PATH = "cr_logPath";
	public static String COL_HEAD_IE_LOG_PATH = "ie_logPath";
	public static String COL_HEAD_FF_LOG_PATH = "ff_logPath";
	public static Long HANG_TIMEOUT = 1800L;

	public static String COMMON_STEPS_VAR_PREFIX = "COMMON_";
	public static String COMMON_STEPS_FILE_NAME = "CommonSteps";

	/***** Config.xlsx Column Heading *****************/

	public static final String CONFIG_COL_DOMAIN_NAME = "DomainUrl";
	public static final String OR = "or";



}
