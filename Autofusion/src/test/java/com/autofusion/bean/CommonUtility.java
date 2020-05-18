
package com.autofusion.bean;
/**
 * @author nitin.singh
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import com.autofusion.constants.Constants;
import com.autofusion.sql.SQLManager;
import com.autofusion.util.Xls_Reader;
import com.autofusion.util.Xlsx_Reader;




/**
 * @author nitin.singh
 */
import io.appium.java_client.AppiumDriver;

@SuppressWarnings({"rawtypes","resource","all"})
public class CommonUtility {

	public AppiumDriver driver = null;
	public Properties prop = new Properties();
	public static ArrayList<String> strState = new ArrayList<String>();
	public int i = 0, j = 0;
	public static List<WebElement> tab2;
	public static int apilevel;
	public static String deviceversion;
	public static String devicemodel;
	public static String deviceserial;
	public static String devicetype;
	public static Properties CONFIG;
	public static Properties USER_CONFIG;
	// public static Logger APP_LOG = null;
	public static String fileSeprator = System.getProperty("file.separator");

	public CommonUtility(String path) {
	}

	public CommonUtility() {
	}

	public String[] listFile(String folder) {

		File dir = new File(folder);

		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : ");
			return null;
		}

		String[] list = dir.list();

		return list;
	}

	public StringBuffer makeReport() throws IOException {

		FileInputStream fstream = new FileInputStream(
				"D://Projects//CEB-Automation//datamigration//DataMigrationDumpFiles//report//21//IRR_2_21.April.201410.57.37.html");

		DataInputStream in = new DataInputStream(fstream);

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		StringBuffer buf = new StringBuffer();
		while ((strLine = br.readLine()) != null) {
			buf.append(strLine);
		}

		return buf;
	}

	public ArrayList readWindowFinalReport(String testCaseBasePath) {

		ArrayList arrList = new ArrayList();
		File f1 = new File(testCaseBasePath + "/window/TestReport.xlsx");
		if (!f1.exists()) {
			return arrList;
		}
		Xls_Reader currentSuiteXls = new Xls_Reader(testCaseBasePath + "/window/TestReport.xlsx");

		// for(int testCaseStepsCount = 2; testCaseStepsCount <=
		// currentSuiteXls.getRowCount(Constants.TEST_COMMON_SHEET);
		// testCaseStepsCount++){

		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "SuiteName", 2));
		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "DateTime", 2));
		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "passed", 2));
		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "failed", 2));
		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "skip", 2));
		arrList.add(currentSuiteXls.getCellData(Constants.TEST_CASES_SHEET, "TotalTestCase", 2));

		return arrList;
	}

	public boolean createNewXls(String testSuiteFileName, String basePath) {

		try {
			File f = new File(basePath + "/" + testSuiteFileName + ".xlsx");
			if (f.exists()) {
				return false;
			}

			FileOutputStream fileOut = new FileOutputStream(basePath + "/" + testSuiteFileName + ".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet tCasesWorksheet = workbook.createSheet(Constants.TEST_CASES_SHEET);

			XSSFRow row1 = tCasesWorksheet.createRow((short) 0);
			row1.createCell(0);
			row1.createCell(1);
			row1.createCell(2);
			row1.createCell(3);

			row1.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
			row1.getCell(1).setCellValue(Constants.COL_HEAD_DESCRIPTION);
			row1.getCell(2).setCellValue(Constants.COL_HEAD_RUNMODE);
			row1.getCell(3).setCellValue(Constants.COL_DATA_DRIVEN);

			XSSFSheet tStepWorksheet = workbook.createSheet(Constants.TEST_STEPS);
			XSSFRow tStepWorksheetRow = tStepWorksheet.createRow((short) 0);

			tStepWorksheetRow.createCell(0);
			tStepWorksheetRow.createCell(1);
			tStepWorksheetRow.createCell(2);
			tStepWorksheetRow.createCell(3);
			tStepWorksheetRow.createCell(4);
			tStepWorksheetRow.createCell(5);
			tStepWorksheetRow.createCell(6);

			tStepWorksheetRow.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
			tStepWorksheetRow.getCell(1).setCellValue(Constants.COL_HEAD_TSID);
			tStepWorksheetRow.getCell(2).setCellValue(Constants.COL_HEAD_DESCRIPTION);
			tStepWorksheetRow.getCell(3).setCellValue(Constants.COL_HEAD_KEYWORD);
			tStepWorksheetRow.getCell(4).setCellValue(Constants.COL_HEAD_ELEMENT_ID);
			tStepWorksheetRow.getCell(5).setCellValue(Constants.COL_HEAD_DATA);
			tStepWorksheetRow.getCell(6).setCellValue(Constants.COL_HEAD_GO_TO);

			XSSFSheet tDataWorksheet = workbook.createSheet(Constants.DATA_SHEET);
			XSSFRow tDataWorksheetRow = tDataWorksheet.createRow((short) 0);

			tDataWorksheetRow.createCell(0);
			tDataWorksheetRow.createCell(1);
			tDataWorksheetRow.createCell(2);

			tDataWorksheetRow.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
			tDataWorksheetRow.getCell(1).setCellValue("TCDI");
			tDataWorksheetRow.getCell(2).setCellValue(Constants.COL_HEAD_RUNMODE);

			XSSFSheet tCommonWorksheet = workbook.createSheet(Constants.TEST_COMMON_SHEET);
			XSSFRow tCommonWorksheetRow = tCommonWorksheet.createRow((short) 0);

			tCommonWorksheetRow.createCell(0);
			tCommonWorksheetRow.createCell(1);
			tCommonWorksheetRow.createCell(2);
			tCommonWorksheetRow.createCell(3);
			tCommonWorksheetRow.createCell(4);
			tCommonWorksheetRow.createCell(5);

			tCommonWorksheetRow.getCell(0).setCellValue(Constants.COL_HEAD_TCID);
			tCommonWorksheetRow.getCell(1).setCellValue(Constants.COL_HEAD_TSID);
			tCommonWorksheetRow.getCell(2).setCellValue(Constants.COL_HEAD_DESCRIPTION);
			tCommonWorksheetRow.getCell(3).setCellValue(Constants.COL_HEAD_KEYWORD);
			tCommonWorksheetRow.getCell(4).setCellValue(Constants.COL_HEAD_ELEMENT_ID);
			tCommonWorksheetRow.getCell(5).setCellValue(Constants.COL_HEAD_DATA);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (IOException e) {
			System.out.println("Xls not created" + e);
			return false;
		}
	}

	public ArrayList<ArrayList<String>> readTestStepsXls(String testCaseBasePath, String testSuit, String testCaseId,
			String fileExtention) {

		ArrayList<ArrayList<String>> testCaseName = new ArrayList<ArrayList<String>>();

		try {
			File file = new File(testCaseBasePath + "/" + testSuit + fileExtention);

			if (!file.exists()) {
				return testCaseName;
			}

			Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath + "/" + testSuit + fileExtention);

			for (int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_STEPS); suiteCount++) {

				String tCId = suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount);

				if (testCaseId.equalsIgnoreCase(tCId)) {
					ArrayList<String> testCaseDetailList = new ArrayList<String>();

					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount));
					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, suiteCount));
					testCaseDetailList.add(
							suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DESCRIPTION, suiteCount));
					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount));
					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_KEYWORD, suiteCount));
					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DATA, suiteCount));
					testCaseDetailList
							.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_GO_TO, suiteCount));

					testCaseName.add(testCaseDetailList);
				}

			}
		} catch (Exception e) {
			System.out.println("File Not found");
		}

		return testCaseName;
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	

	public static String[] splitMailAddresses(String address) {

		String[] addressSplited = {};

		if (!address.equals(""))
			addressSplited = address.split(";");

		return addressSplited;
	}

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	 public String comboValueReturn() throws SQLException {
    	 String select ="";
         String projName="", projCode="";
         Connection con = SQLManager.getConnection();
    	 Statement stmt = con.createStatement();
    	 ResultSet rs = stmt.executeQuery("select * from tbl_project");
    	 while(rs.next())
    	 {
    		 projCode= rs.getString(2).trim().replace(" ", "");
    		 projName= rs.getString(3);
    		 select+= "<option value="+projCode+">"+projName+"</option>";
    	 }
    	return select;
    }
	 

	  
	  public static ResultSet readKeywords(){
			
			String query ="SELECT * FROM  keywords";
			ResultSet rs = null;
			try {
				rs = SQLManager.selectData(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 	rs;
	  }
	  public static void insertReportSummaryData(String suiteName, String suiteDescription , int totTCase, int totPass, int totFail, int  totSkip,
			  String browserToOpen, String stTime, String endTime , String batchId, String device, Logger APP_LOG) throws SQLException{
					
			String query = "insert into reportsummary(suitename,suitdescription,totaltestcase,totalpass,totalfail,totalskip," +
					" browser, starttime, endtime, batch_no, device) values " +
					"('"+suiteName+"','"+suiteDescription+"',"+totTCase+","+totPass+","+totFail+","+totSkip+"," +
							"'"+browserToOpen+"','"+stTime+"','"+endTime+"','"+batchId+"','"+device+"')";
			APP_LOG.debug("Inser Data in report summary insertReportSummaryData : "+query);
			SQLManager.insertData(query);

		}
	  public static String getRandomTimeString(){
			Calendar cal = Calendar.getInstance();
			String time = cal.get(Calendar.HOUR) + "_" + cal.get(Calendar.MINUTE) + "_" + cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND);

			return time;
		}
	  
	  public static String GenerateStringFromResource(String path) throws IOException {

		    return new String(Files.readAllBytes(Paths.get(path)));

		}
}
