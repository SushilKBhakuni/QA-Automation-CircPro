package com.autofusion.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autofusion.constants.Constants;
import com.autofusion.util.Xlsx_Reader;

/**
 * Servlet implementation class UpdateServlet2
 */
@WebServlet("/FetchTestReports")
public class FetchTestReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchTestReports() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String testSheetName;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String device = request.getParameter("device");
		String testBasePath = (String)request.getSession().getAttribute("testCaseBasePath");
		Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testBasePath+"/web/"+Constants.SUIT_FILE_NAME);
		
 	    if(device.equals("0")) {
 	    	testSheetName = Constants.TEST_SUITE_SHEET;
 	    } else if (device.equals("1")) {
			testSheetName = Constants.TEST_MBL_SUITE_SHEET;
		} else if(device.equals("2"))	{
			testSheetName = Constants.TEST_WIN_SUITE_SHEET;
		} else {
			testSheetName = Constants.TEST_SUITE_SHEET;
		}
 	    
		ArrayList<String> suiteList = new ArrayList<String>();
		for(int i = 2; i <= webTestSuitXls.getRowCount(testSheetName);  i++)
		 {
			 String suitName = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_TSID, i) ;
			 suiteList.add(suitName);
		 }
	     String combo = "<option value='' selected>Select Test Suite</option>\n";
	 	 for(int i=0; i < suiteList.size(); i++)
	 		  {
	 			  combo+= "<option value='"+suiteList.get(i)+"'>"+suiteList.get(i)+"</option>\n";  
	 		  }
		 out.println(combo);
		 out.close();
	}

}
