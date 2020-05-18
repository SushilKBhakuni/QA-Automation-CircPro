package com.autofusion.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autofusion.constants.Constants;
import com.autofusion.sql.*;
import com.autofusion.util.Xlsx_Reader;

@SuppressWarnings("unused")
@WebServlet("/GettingReportData")
public class GettingReportData extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static int cnt;
	public static int countt, tsuitid;
	// public int t_pass=0, t_fail=0, t_skip=0, t_total=0;
	//public static int passPer;
	public static String currSuit = "";

	public GettingReportData()
	{
		super();
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
		final PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String result = "";
		final String method = request.getParameter("method");
		if (method.equalsIgnoreCase("getHtmlReportData"))
		{
			result = getHtmlReportData(request, response);
		}
		else if (method.equalsIgnoreCase("getPassFailData"))
		{
			result = getPassFailData(request, response);
		}

		else if (method.equalsIgnoreCase("getPassFailDataFromDatabase"))
		{
			try
			{
				result = getPassFailDataFromDatabase(request, response);
			}
			catch (final SQLException e)
			{ // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (method.equalsIgnoreCase("getHtmlReportDataFromDataBase"))
		{
			try
			{
				result = getHtmlReportDataFromDataBase(request, response);
			}
			catch (final SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (method.equalsIgnoreCase("getFileCombo"))
		{
			result = getFileCombo(request, response);
		}

		else if (method.equalsIgnoreCase("getFileComboFromDatabase"))
		{
			try
			{
				result = getFileComboFromDatabase(request, response);
			}
			catch (final SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println(result);
		out.close();
	}

	public String getHtmlReportData(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
		int index, index1;
		String fName = "";
		String fName1 = "";
		String status = "";
		final String fileName = request.getParameter("fileName");
		final String browser = request.getParameter("browser");
		final String currentSuiteName = request.getParameter("suitName");
		final String testBasePath = (String) request.getSession().getAttribute("testCaseBasePath");
		final String testSheetName = Constants.TEST_SUITE_SHEET; //"WebTestSuite"
		final String testSheetName1 = Constants.SUIT_FILE_SHEET; //Sheet1
		final Xlsx_Reader suiteXls1 = new Xlsx_Reader(testBasePath + "\\" + Constants.SUIT_FILE_NAME);//suits.xlsx
		for (int suiteCount = 2; suiteCount <= suiteXls1.getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++)
		{
			if (suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_TSID, suiteCount).matches(currentSuiteName))
			{
				cnt = suiteCount;
				break;
			}
		}

		final InputStreamReader userFileConfig = new InputStreamReader(new FileInputStream(testBasePath + "/config.properties"),
				"UTF-8");
		final Properties uconfig = new Properties();
		uconfig.load(userFileConfig);
		final String userLogPath = uconfig.getProperty("logPath");

		final StringBuilder sb = new StringBuilder("");
		final String logPathh = suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_FF_LOG_PATH, (cnt));

		index = logPathh.indexOf("Batch1");
		index1 = index + 7;
		final String finallogPathh = logPathh.substring(0, index1);

		final String finalreportpath = finallogPathh + fileName + "//" + browser + "//report";
		// File folder1 = new File(logPathh+"/reports");

		final File folder1 = new File(finalreportpath);
		if (folder1.length() == 0)
		{
			sb.append("<tr>");
			sb.append("<td>No Result to display in the reports!!</td>");
			sb.append("</tr>");
			return sb.toString();
		}
		else
		{
			final File[] listOfFiles1 = folder1.listFiles();
			for (final File file : listOfFiles1)
			{
				final String currentFName = file.getAbsolutePath();
				if (currentFName.contains(Constants.SUIT_FILE_NAME_HTMLBROWSERREPORT + "_"))
				{
					fName1 = currentFName;
					break;
				}
			}
			final Xlsx_Reader suiteXls = new Xlsx_Reader(fName1);

			sb.append("<tr>");
			sb.append("<th scope=\"col\" width=\"50\">TCID</th>");
			sb.append("<th scope=\"col\" width=\"100\">Description</th>");
			sb.append("<th scope=\"col\" width=\"60\">Status</th>");
			sb.append("<th scope=\"col\" width=\"200\">Fail/TotalCount</th>");
			sb.append("<th scope=\"col\" width=\"250\">Run Start Time</th>");
			sb.append("<th scope=\"col\" width=\"250\">Run End Time</th>");
			sb.append("</tr>");

			for (int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.SUIT_FILE_SHEET); suiteCount++)
			{
				status = suiteXls.getCellData(testSheetName1, Constants.COL_HEAD_STATUS, suiteCount);
				sb.append("<tr>");
				if (status.equalsIgnoreCase(Constants.SKIP))
				{
					sb.append("<td>&nbsp;&nbsp;"
							+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_TCID, suiteCount) + "</td>");
				}
				else
				{
					final File folder = new File(logPathh + "/reports");
					final File[] listOfFiles = folder.listFiles();
					final String tcId = suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_TCID, suiteCount);

					for (final File file : listOfFiles)
					{
						final String currentFName = file.getAbsolutePath();
						if (currentFName.contains(currentSuiteName + "_" + tcId))
						{
							fName = currentFName;
							break;
						}
					}
					sb.append("<td><b><a target='_blank' href=file:///" + fName + ">"
							+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_TCID, suiteCount) + "</a></b></td>");
				}

				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_DESCRIPTION, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_STATUS, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_Fail_TotalCount, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_StartTime, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET, Constants.COL_HEAD_EndTime, suiteCount) + "</td>");
				sb.append("</tr>");
				status = "";
			}
			return sb.toString();
		}
	}

	public String getHtmlReportDataFromDataBase(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		String current_suitename = "", TCID = "", description = "", status = "";
		final String fail_totalcnt = "";
		String run_strttime = "", run_endtime = "";
		final Connection con = SQLManager.getConnection();
		final Statement stmt = con.createStatement();

		final StringBuilder sb = new StringBuilder("");
		final String currentSuiteName = request.getParameter("suitName"); // selected from the dropdown list
		final String selectedDateTime = request.getParameter("fileName");

		final String query1 = "select * from tbl_tsuit ts left join tbl_tcase tc on ts.tsuit_id=tc.tsuit_id where ts.date_time='"
				+ selectedDateTime + "'";

		// String query1 = "select tsuit_id from tbl_tsuit where date_time='"+selectedDateTime+"'";
		//				System.out.println("Query1: "+query1);
		//Connection con1 = ConnectionManager.getConnection() ;
		//Statement stmt1 = con1.createStatement();
		//ResultSet rs1 = stmt1.executeQuery(query1);
		/*
		 * while(rs1.next()) { tsuitid = rs1.getInt("tsuit_id");
		 * 
		 * }
		 */
		//*****************************
		//System.out.println("Suite ID: "+ tsuitid);
		//String query2 = "select * from tbl_tcase where tsuit_id='"+tsuitid+"'";
		//ResultSet rs = stmt.executeQuery("Select * from tbl_tcase");
		final ResultSet rs = stmt.executeQuery(query1);
		sb.append("<tr>");
		sb.append("<th scope=\"col\" width=\"200\">Current Suite Name</th>");
		sb.append("<th scope=\"col\" width=\"50\">TCID</th>");
		sb.append("<th scope=\"col\" width=\"100\">Description</th>");
		sb.append("<th scope=\"col\" width=\"60\">Status</th>");
		sb.append("<th scope=\"col\" width=\"200\">Fail/TotalCount</th>");
		sb.append("<th scope=\"col\" width=\"250\">Run Start Time</th>");
		sb.append("<th scope=\"col\" width=\"250\">Run End Time</th>");
		sb.append("</tr>");

		while (rs.next())
		{
			current_suitename = rs.getString(2); // selected from the DB the suitname
			TCID = rs.getString(7);
			description = rs.getString(8);
			status = rs.getString(9);
			// fail_totalcnt= rs.getString(6);
			run_strttime = rs.getString(10);
			run_endtime = rs.getString(11);

			if (current_suitename.equals(currentSuiteName))
			{
				sb.append("<tr>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + current_suitename + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + TCID + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + description + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + status + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + fail_totalcnt + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + run_strttime + "</td>");
				sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + run_endtime + "</td>");
				sb.append("</tr>");
			}
			else
			{
			}
		}
		return sb.toString();
	}


	public String getPassFailDataFromDatabase(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		final String current_suitename = "", pass = "", fail = "", skip = "", total = "";
		int t_pass = 0, t_fail = 0, t_skip = 0;
		final int t_total = 0;
		// int i=1;
		final Connection con = SQLManager.getConnection();
		final Statement stmt = con.createStatement();
		final StringBuilder sb = new StringBuilder("");

		final String currentSuiteName = request.getParameter("suitName"); // selected from the dropdown list
		final String selectedDateTime = request.getParameter("fileName");
		final ResultSet rs = stmt
				.executeQuery("select status from tbl_tsuit ts left join tbl_tcase tc on ts.tsuit_id=tc.tsuit_id where ts.date_time='"
						+ selectedDateTime + "'");

		sb.append("<tr>");
		//sb.append("<th scope=\"col\" width=\"40\">S No.</th>");
		sb.append("<th scope=\"col\" width=\"40\">Suite_Name</th>");
		sb.append("<th scope=\"col\" width=\"40\">Total_Pass</th>");
		sb.append("<th scope=\"col\" width=\"40\">Total_Fail</th>");
		sb.append("<th scope=\"col\" width=\"60\">Total_Skipped</th>");
		// sb.append("<th scope=\"col\" width=\"100\">Total_Test_Cases</th>");
		sb.append("<th scope=\"col\" width=\"50\">Action</th>");
		sb.append("</tr>");

		while (rs.next())
		{
			if (rs.getString(1).equals("PASS"))
			{
				t_pass++;
			}
			if (rs.getString(1).equals("SKIP"))
			{
				t_skip++;
			}
			if (rs.getString(1).equals("FAIL"))
			{
				t_fail++;
			}
		}

		sb.append("<tr>");
		//	sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;<a href=\"#\" onclick=\"getReport1()\">"+current_suitename+"</a></td>");
		// sb.append("<td width=\"20\" height=\"1\" id=\"tc_"+i+"\">&nbsp;&nbsp;"+current_suitename+"</td>");
		sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + currentSuiteName + "</td>");
		sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + t_pass + "</td>");
		sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + t_fail + "</td>");
		sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;" + t_skip + "</td>");
		// sb.append("<td width=\"20\" height=\"1\">&nbsp;&nbsp;"+t_total+"</td>");
		if (t_pass == 0 && t_fail == 0 && t_skip == 0)
		{
			sb.append("<td align=\"center\"><a onclick=\"createNoPieChart()\" href=\"#\"><img width=\"15\" height=\"15\" title=\"Bar Chart\" src=\"images/Bar.png\"></a></td>");
		}
		else
		{
			sb.append("<td align=\"center\"><a onclick=\"createPieChart(" + t_pass + "," + t_fail + "," + t_skip
					+ ")\" href=\"#\"><img width=\"15\" height=\"15\" title=\"Bar Chart\" src=\"images/Bar.png\"></a></td>");
		}
		sb.append("</tr>");

		// i++;
		//    			t_pass=0;
		//    			t_fail=0;
		//    			t_skip=0;
		//    			t_total=0;
		// }
		// }
		// rs1.close();
		//rs.close();
		return sb.toString();
	}

	public String getPassFailData(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
		int index, index1;
		String fName1 = "";
		String finalreportpath = "";
		final String currentSuiteName = request.getParameter("suitName");
		final String testBasePath = (String) request.getSession().getAttribute("testCaseBasePath");
		final String testSheetName = Constants.TEST_SUITE_SHEET;
		final String fileName = request.getParameter("fileName");
		final String browser = request.getParameter("browser");
		final Xlsx_Reader suiteXls1 = new Xlsx_Reader(testBasePath + "\\" + Constants.SUIT_FILE_NAME);
		for (int suiteCount = 2; suiteCount <= suiteXls1.getRowCount(Constants.TEST_SUITE_SHEET); suiteCount++)
		{
			if (suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_TSID, suiteCount).matches(currentSuiteName))
			{
				countt = suiteCount;
				break;
			}
		}
		final String logPathh = suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_FF_LOG_PATH, countt);
		final StringBuilder sb = new StringBuilder("");

		index = logPathh.indexOf("Batch1");
		index1 = index + 7;
		final String finallogPathh = logPathh.substring(0, index1);

		// If the suite is run on all the three browsers ... 
		if (browser.equals("Firefox"))
		{
			finalreportpath = finallogPathh + fileName + "//" + browser + "(0)//reports";
		}
		/*
		 * else if(browser.equals("Chrome")) { finalreportpath = finallogPathh + fileName + "//" +browser+"(2)//reports";
		 * } else if(browser.equals("InternetExplorer")) { finalreportpath = finallogPathh + fileName + "//"
		 * +browser+"(0)//reports"; }
		 */

		// But this thread number still variates, suppose, if firefox is NOT run, then for chrome the thread number will be 0 and 
		// that is fixed here for firefox only...
		// System.out.println(finalreportpath);
		final File folder1 = new File(finalreportpath);
		// File folder1 = new File(logPathh+"/reports");
		if (folder1.length() == 0)
		{
			sb.append("<tr>");
			sb.append("<td>No Data for display for the PASS FAIL Table!!</td>");
			sb.append("</tr>");
			return sb.toString();
		}
		else
		{
			final File[] listOfFiles1 = folder1.listFiles();
			for (final File file : listOfFiles1)
			{
				final String currentFName = file.getAbsolutePath();
				if (currentFName.contains(Constants.SUIT_FILE_NAME_HTMLBROWSERREPORT + "_"))
				{
					fName1 = currentFName;
					break;
				}
			}
			final Xlsx_Reader suiteXls = new Xlsx_Reader(fName1);

			sb.append("<tr>");
			sb.append("<th scope=\"col\" width=\"40\">S No.</th>");
			sb.append("<th scope=\"col\" width=\"40\">Current Suite Name</th>");
			sb.append("<th scope=\"col\" width=\"40\">TCID</th>");
			sb.append("<th scope=\"col\" width=\"40\">Pass</th>");
			sb.append("<th scope=\"col\" width=\"40\">Fail</th>");
			sb.append("<th scope=\"col\" width=\"60\">Skipped</th>");
			sb.append("<th scope=\"col\" width=\"100\">Total Test Cases</th>");
			// sb.append("<th scope=\"col\" width=\"50\">Pass%</th>");
			sb.append("<th scope=\"col\" width=\"50\">Action</th>");
			sb.append("</tr>");

			for (int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.SUIT_FILE_SHEET_TWO); suiteCount++)
			{
				double passPer = 0.0;
				final int pass = Integer.parseInt(suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_PASS,
						suiteCount));
				final int fail = Integer.parseInt(suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_FAIL,
						suiteCount));
				final int skip = Integer.parseInt(suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_SKIP,
						suiteCount));
				final int total = (pass + fail + skip);
				//				if(pass!=0 || fail!=0)
				//				{

				try
				{
					if (total != 0)
					{
						passPer = ((pass / total) * 100);
					}
				}
				catch (final ArithmeticException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//}
				//int passPer= (pass/(pass+fail+skip))*100;

				sb.append("<tr>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">" + (suiteCount - 1) + "." + "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_CURRENT_SUITE_NAME, suiteCount)
						+ "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_TCID, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_PASS, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_FAIL, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_SKIP, suiteCount) + "</td>");
				sb.append("<td width=\"20\" height=\"2\" align=\"center\">&nbsp;&nbsp;"
						+ suiteXls.getCellData(Constants.SUIT_FILE_SHEET_TWO, Constants.COL_HEAD_TOTAL_EXECUTED_TC, suiteCount)
						+ "</td>");
				//				if(pass==0 || fail==0 || skip==0) {
				//					sb.append("<td align=\"center\">0%</td>");
				//				}
				//				else {
				//sb.append("<td align=\"center\">"+ (passPer)+"%"+"</td>");// }
				//else {sb.append("<td align=\"center\">0%</td>");}
				//if(pass==0 && fail==0 && skip==0) {
				//sb.append("<td align=\"center\"> 0% </td>");
				//}
				//				else {
				//					sb.append("<td align=\"center\">"+ (passPer)+"%"+"</td>");
				//				}
				if (pass == 0 && fail == 0 && skip == 0)
				{
					sb.append("<td align=\"center\"><a onclick=\"createNoPieChart()\" href=\"#\"><img width=\"15\" height=\"15\" title=\"Bar Chart\" src=\"images/Bar.png\"></a></td>");
				}
				else
				{
					sb.append("<td align=\"center\"><a onclick=\"createPieChart(" + pass + "," + fail + "," + skip
							+ ")\" href=\"#\"><img width=\"15\" height=\"15\" title=\"Bar Chart\" src=\"images/Bar.png\"></a></td>");
				}
				sb.append("</tr>");
			}
			return sb.toString();
		}
	}

	public ArrayList<String> getFileData(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException
	{
		int index, index1;
		String path = "";
		final String testBasePath = (String) request.getSession().getAttribute("testCaseBasePath");
		String testSheetName = "";
		final String testSuitName = request.getParameter("selectSuits");
		final String testDeviceName = request.getParameter("selectDevice");
		final Xlsx_Reader suiteXls1 = new Xlsx_Reader(testBasePath + "\\" + Constants.SUIT_FILE_NAME);

		if (testDeviceName.equals("0"))
		{
			testSheetName = Constants.TEST_SUITE_SHEET;
		}
		else if (testDeviceName.equals("1"))
		{
			testSheetName = Constants.TEST_MBL_SUITE_SHEET;
		}
		else
		{
			testSheetName = Constants.TEST_WIN_SUITE_SHEET;
		}
		String logPath = "";


		for (int p = 2; p <= suiteXls1.getRowCount(testSheetName); p++)
		{
			final String cName = suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_TSID, p);

			if (cName.equalsIgnoreCase(testSuitName))
			{
				logPath = suiteXls1.getCellData(testSheetName, Constants.COL_HEAD_FF_LOG_PATH, p);
				break;
			}
		}

		if (logPath.matches("(?i).*Batch1.*"))
		{
			index = logPath.indexOf("Batch1");
			index1 = index + 7;
			path = logPath.substring(0, index1);
		}
		final ArrayList<String> fileList = new ArrayList<String>();
		final File folder = new File(path);
		if (folder.exists())
		{
			final File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++)
			{
				if (listOfFiles[i].isDirectory())
				{
					fileList.add(listOfFiles[i].getName());
				}
			}
		}
		return fileList;
	}

	public String getFileCombo(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
		int index, index1;
		final ArrayList<String> fileList = getFileData(request, response);
		for (int i = 0; i < fileList.size(); i++)
		{
			// System.out.println(fileList.get(i));
			index = fileList.get(i).indexOf("_");
			index1 = fileList.get(i).length();
			fileList.set(i, fileList.get(i).substring(index + 1, index1));
		}

		String combo = "<option value='' selected>Select Log File</option>\n";
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		for (int i = 0; i < fileList.size(); i++)
		{
			try
			{
				final java.util.Date date = sdf.parse(fileList.get(i));
				// combo+= "<option value='"+fileList.get(i)+"'>"+DateFormat.getDateTimeInstance(
				//   DateFormat.MEDIUM, DateFormat.SHORT).format(date)+"</option>\n";  
				combo += "<option value='" + fileList.get(i) + "'>" + "log_" + fileList.get(i) + "</option>\n";
			}
			catch (final ParseException e)
			{
				e.printStackTrace();
			}
		}
		return combo;
	}

	public String getFileComboFromDatabase(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		String combo = "<option value='' selected>Select Desired Date and Time</option>\n";
		final Connection con = SQLManager.getConnection();
		final Statement stmt = con.createStatement();
		final ResultSet rs = stmt.executeQuery("Select * from tbl_tsuit");

		while (rs.next())
		{
			combo += "<option value='" + rs.getString(3) + "'>" + rs.getString(3) + "</option>\n";
		}
		System.out.println("Combo List Values Includes : " + combo);
		return combo;
	}
}
