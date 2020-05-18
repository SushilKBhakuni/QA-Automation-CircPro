package com.autofusion.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autofusion.bean.CommonUtility;
import com.autofusion.constants.Constants;
import com.autofusion.util.Xlsx_Reader;

/**
 * Servlet implementation class AddTestSuite
 */
@SuppressWarnings("all")
public class AddTestSuite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTestSuite() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		String result = "";//  = getSuiteCombo(request);
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("AddTestCase")){
		    result = addTestCase(request);	
		} else if(method.equalsIgnoreCase("AddTestSuite")){
			result = addTestSuite(request);
		} else if(method.equalsIgnoreCase("AddTestSteps")){
			result = addTestSteps(request);
		} else if(method.equalsIgnoreCase("AddTestStepsInOR")){
			result = addTestStepsInOR(request);
		} else if(method.equalsIgnoreCase("getSuiteList")){
			 result  = getSuiteCombo(request);
		} else if(method.equalsIgnoreCase("getTestCaseList")){
			 result  = getTestCaseCombo(request);
		} else if(method.equalsIgnoreCase("getTestSteps")){
			result = getTestSteps(request);
		} else if(method.equalsIgnoreCase("updateTestSteps")){
			result = updateTestSteps(request);	
		}
		response.setContentType("text/plain");
		response.getWriter().write(result);
	}

	public String getSheetName(HttpServletRequest request){
		
		Map updatedTestMap =  request.getParameterMap();
		String testSheetName = "";
		
		if(request.getParameter("device").equalsIgnoreCase("window")){
			  testSheetName = 	Constants.TEST_WIN_SUITE_SHEET;
		}else if(request.getParameter("device").equalsIgnoreCase("Web")){
			  testSheetName = 	Constants.TEST_SUITE_SHEET;
		}else if(request.getParameter("device").equalsIgnoreCase("mobile")){
			  testSheetName = 	Constants.TEST_SUITE_SHEET;
		}
		return testSheetName;
	}
	
	public String addTestSuite(HttpServletRequest request) {
		String testCaseBasePath = request.getParameter("testCaseBasePath");
		
		Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/"+Constants.SUIT_FILE_NAME);
		
		String testSheetName = getSheetName(request);
		int suiteCount = webTestSuitXls.getRowCount(testSheetName)+1;
		String newSuitName  = request.getParameter("suiteName");
		 
		/*for(int i = 2; i <= webTestSuitXls.getRowCount(testSheetName);  i++){
			String suitName = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_TSID, i) ;
			if(newSuitName.equalsIgnoreCase(suitName)){
				return "duplicate";
			}
		 }*/
		
		 	ArrayList suitList = getSuiteList(request);
		    if(suitList.contains(newSuitName)){
					return "duplicate";
			}

		    String ie =  request.getParameter("chkIE")!=null?"Y":"N";
		    String ff =  request.getParameter("chkFirefox")!=null?"Y":"N";
		    String cr =  request.getParameter("chkChrome")!=null?"Y":"N";
		    String android =  request.getParameter("chkAndroid")!=null?"Y":"N";
		    String ios =  request.getParameter("chkIOS")!=null?"Y":"N";
		    String rm =  request.getParameter("chkRunmode")!=null?"Y":"N";
		    
			webTestSuitXls.openXls();
			webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_TSID, suiteCount, newSuitName);   
			webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_DESCRIPTION, suiteCount, request.getParameter("description"));  
			webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_RUNMODE, suiteCount, rm);  //for web, windows
			 
			if(request.getParameter("device").equalsIgnoreCase("Web")){
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IE, suiteCount, ie);
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_FF, suiteCount, ff);
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_CHROME, suiteCount, cr);
			}else{
				webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_ANDROID_BWR, suiteCount, ie);
				 webTestSuitXls.setCellData(testSheetName, Constants.COL_HEAD_IOS_BWR, suiteCount, ff);
			}
			 
		   boolean res = webTestSuitXls.writeXls();
	       if(!res)
	    	  return "error";
	       else{
	    	   CommonUtility objCommonUtil = new CommonUtility();
	    	   testCaseBasePath = testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/";
	           res = objCommonUtil.createNewXls(newSuitName,testCaseBasePath);
	           if(res)
	        	   return "success";
	           else
	        	   return "error";
	       }
	  }

	public String addTestCase(HttpServletRequest request){
		  
		//String testCaseBasePath = request.getParameter("testCaseBasePath")+"/"+request.getParameter("device");
        boolean res;
		res = addTestCaseDetails(request);
		if(res)
			return "success";
		else
			return "error in writing test case";
	  
	 }

	 public boolean addTestCaseDetails(HttpServletRequest request){
		 
		 boolean res;
		 //String testCaseName = request.getParameter("testCaseName").trim();
		 String description = request.getParameter("description");
		 String dd =  request.getParameter("dataDriven")!=null?"Y":"N";
		 String rm =  request.getParameter("chkRunmode")!=null?"Y":"N";
		 
		 String testCaseBasePath = request.getParameter("testCaseBasePath");
		 Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/"+request.getParameter("selSuite")+".xlsx");
		 
		 //ArrayList suitList = suiteList(request);
		// if(suitList.contains(testCaseName)){
				//	return "duplicate";
		// }
		 int tCaseCount = webTestSuitXls.getRowCount(Constants.TEST_CASES_SHEET)+1;
		 Map updatedTestMap =  request.getParameterMap();
			
 		 webTestSuitXls.openXls();
		 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_RUNMODE, tCaseCount, rm);
		 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DATA_DRIVEN, tCaseCount, dd);
		 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_DESCRIPTION, tCaseCount, description);
		 webTestSuitXls.setCellData(Constants.TEST_CASES_SHEET, Constants.COL_HEAD_TCID, tCaseCount, "TC-"+(tCaseCount-1));
		 res = webTestSuitXls.writeXls();
		 
		 return res;
	 }
	 
	 
	 public String addTestSteps(HttpServletRequest request){
		 
		 String testCaseBasePath = request.getParameter("testCaseBasePath");
		 Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/"+request.getParameter("selTestSuite")+".xlsx");
		 boolean firstCase=false;
		 boolean findLast = false;
		 String selectedTestCase = request.getParameter("testCaseName");
		 int totalTestStepsInXls = webTestSuitXls.getRowCount(Constants.TEST_STEPS);
		 int totalNewRows =   Integer.parseInt(request.getParameter("totNewAppRow"));
		 String appendAfter =   request.getParameter("txtAppRow").toString();
		 int totalStepInUI = Integer.parseInt(request.getParameter("totalStepRows"));
		 int reqRowNoToUpdate = totalStepInUI - totalNewRows +1;
		 
		 String caseId = "";
		 String stepId = "";
		 int tmp = 0;
		 		 
		 
		 for(int testStepCount = totalTestStepsInXls+1; testStepCount < totalNewRows; testStepCount++ ){
			 tmp = testStepCount + 2;
			 caseId = webTestSuitXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, tmp);
			 stepId = webTestSuitXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, tmp);
			 appendRowInXls(webTestSuitXls, request, totalNewRows, testStepCount, reqRowNoToUpdate);
			 
			 if(request.getParameter("selAppRow").equalsIgnoreCase("appInLast")){
			/*	 if(caseId.equalsIgnoreCase(selectedTestCase)){ //loop till the last line or next step&& !findLast
					 findLast = true;
				 	 continue;
				 }else{
					 if(findLast){
						 shiftRows(webTestSuitXls, testStepCount-1, totalNewRows);
						 appendRowInXls(webTestSuitXls, request, totalNewRows, testStepCount, reqRowNoToUpdate);
						 break;
					 }else{
						 continue;
					 }
				 }*/
			 }else{  //append in mid
			
				 if(caseId.equalsIgnoreCase(selectedTestCase) && stepId.equalsIgnoreCase("TS-"+appendAfter)){ //loop till the last line or next step&& !findLast
					 //updateRestStepIds();
					 	 shiftRows(webTestSuitXls, testStepCount, totalNewRows);
						 appendRowInXls(webTestSuitXls, request, totalNewRows, testStepCount+1, reqRowNoToUpdate);
						 break;
				 }else{
						 continue;
				 }
			 }
		  }
		 return "success";
	 }
	 
	 public String addTestStepsInOR(HttpServletRequest request) {
		 String testCaseBasePath = request.getParameter("testCaseBasePath");
		 Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"//"+request.getParameter("device")+"//TestCases//"+"OR.xlsx");
		 int totalNewRows =   Integer.parseInt(request.getParameter("totalStepRows"));	 
		 appendRowInORXls(webTestSuitXls, request, totalNewRows);
		 return "success";
	 }
	 
	 public String updateTestSteps(HttpServletRequest request){ // On clicking of the button Update Test Steps
		 String testCaseBasePath = request.getParameter("testCaseBasePath");
		 Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/"+request.getParameter("selTestSuite")+".xlsx");

		 String selectedTestCase = request.getParameter("testCaseName");
		 int totalTestStepsInXls = webTestSuitXls.getRowCount(Constants.TEST_STEPS);
		 
		 int totalNewRows =   1;
		 int rowToUpdate  =   Integer.parseInt(request.getParameter("rowToUpdate"));
		 
		 String caseId = "";
		 String stepId = "";
		 
		 for(int testStepCount = 2; testStepCount < totalTestStepsInXls; testStepCount++ ){
			 caseId = webTestSuitXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, testStepCount);
			 stepId = webTestSuitXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, testStepCount);
			 
			 if(caseId.equalsIgnoreCase(selectedTestCase) && stepId.equalsIgnoreCase("TS-"+rowToUpdate)){ //loop till the last line or next step&& !findLast
					 appendRowInXls(webTestSuitXls, request, totalNewRows, testStepCount, rowToUpdate);
					 break;
			 }else{
				 continue;
			 }
		 }
		 
		 return "success";
	 }
	 
	 public void shiftRows(Xlsx_Reader webTestSuitXls, int startFrom, int noOfRows){
			webTestSuitXls.shiftRow(Constants.TEST_STEPS, startFrom, noOfRows, 1);
	 }
	 
	 //***********************************************************************
	 // Function need to be created for appendRowInXml
	 //***********************************************************************
	  
    public void appendRowInXls(Xlsx_Reader webTestSuitXls, HttpServletRequest request, int totalNewRows, int tCaseCount, int reqRowNo){
    	
    	 webTestSuitXls.openXls();	
    	 String testCase = request.getParameter("testCaseName");
    	 
		 for(int i=1; i <= totalNewRows ; i++){
			
			String testStepId = request.getParameter("stepId_"+reqRowNo);
			String description = request.getParameter("description_"+reqRowNo);
			String attribute = request.getParameter("attribute_"+reqRowNo);
			String elementId = request.getParameter("elementId_"+reqRowNo);
			String keyword = request.getParameter("keyword_"+reqRowNo);
			String data = request.getParameter("data_"+reqRowNo);
			String goToFail = request.getParameter("goToIfFail_"+reqRowNo);
			reqRowNo++;
			
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, tCaseCount, testCase);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, tCaseCount, testStepId);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DESCRIPTION, tCaseCount, description);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_KEYWORD, tCaseCount, keyword);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, tCaseCount, elementId);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DATA, tCaseCount, data);
			webTestSuitXls.setCellData(Constants.TEST_STEPS, Constants.COL_HEAD_GO_TO, tCaseCount, goToFail);
			tCaseCount++;
		 }
		 webTestSuitXls.writeXls();
	  }

    public void appendRowInORXls(Xlsx_Reader webTestSuitXls, HttpServletRequest request, int totalNewRows) {
   	 webTestSuitXls.openXls();	
   	 int totalTestStepsInXls = webTestSuitXls.getRowCount(Constants.PREFIX_DATA_OR);
   	 int rowNum = totalTestStepsInXls+1;
   	 
		 for(int i=1; i <= totalNewRows ; i++) {
			
			String VarName = request.getParameter("varName_"+i);
			String attribute = request.getParameter("attribute_"+i);
			String attributeVal = request.getParameter("attrName_"+i);
			
			webTestSuitXls.setCellData(Constants.PREFIX_DATA_OR, Constants.COL_VARIABLE_NAME, (rowNum), VarName);
			webTestSuitXls.setCellData(Constants.PREFIX_DATA_OR, attribute,(rowNum),attributeVal);
			rowNum++;
		 }
		 webTestSuitXls.writeXls(); 
	  }
    
	 public ArrayList<String> getSuiteList(HttpServletRequest request){

		  String testCaseBasePath = request.getParameter("testCaseBasePath");
		  Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/"+Constants.SUIT_FILE_NAME);
		  
		  String testSheetName = getSheetName(request);
		  
		  ArrayList<String> suiteList = new ArrayList<String>();
		  
		  for(int i = 2; i <= webTestSuitXls.getRowCount(testSheetName);  i++){
				String suitName = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_TSID, i) ;
				suiteList.add(suitName);
		  }
		  
		  return suiteList;
	  }
	
	  
	  public String getSuiteCombo(HttpServletRequest request){
		  
		  ArrayList<String> suiteList = getSuiteList(request);
		  String combo = "<option value='' selected>Select Test Suite</option>\n";
		  for(int i=0; i < suiteList.size(); i++){
			  combo+= "<option value='"+suiteList.get(i)+"'>"+suiteList.get(i)+"</option>\n";  
		  }
		  
		  return combo;
	  }
	  
	  public String getTestCaseCombo(HttpServletRequest request)
	  {  
		  LinkedHashMap<String ,String> testMap = getTestCaseMap(request);
		  String combo = "<option value='' selected>Select Test Case</option>\n";
		  Iterator itr = testMap.entrySet().iterator();
		  while(itr.hasNext())
		  {
			  Map.Entry pairs = (Map.Entry)itr.next();
			  combo+= "<option value='"+pairs.getKey().toString()+"'>"+pairs.getValue().toString()+"</option>\n";  
		  }
		  return combo;
	  }
	  
	  public LinkedHashMap<String, String> getTestCaseMap(HttpServletRequest request)
	  {
		  String testCaseBasePath = request.getParameter("testCaseBasePath");
		  String testCaseFile = request.getParameter("selTestSuite");
		  Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/"+testCaseFile+".xlsx");
		  
		  String testSheetName = Constants.TEST_CASES_SHEET;
		  LinkedHashMap<String, String> testMap = new LinkedHashMap<String, String>();
		  
		  for(int i = 2; i <= webTestSuitXls.getRowCount(testSheetName);  i++){
				String testId = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_TCID, i) ;
				String testName = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_DESCRIPTION, i) ;
				testMap.put(testId, testName);
		  }
		  return testMap;
	  }

	  public String getTestSteps(HttpServletRequest request){ // The test steps are added through this to the page of add test steps link
		  
		  String testSuit = request.getParameter("selTestSuite");
		  String testStep = request.getParameter("testCaseName");
		  
		  CommonUtility objCommonUtil = new CommonUtility();
		  String testCaseBasePath = request.getParameter("testCaseBasePath");
		  String stringBuffer = "";
		  
			  ArrayList testStepXls = objCommonUtil.readTestStepsXls(testCaseBasePath+"/"+request.getParameter("device")+"/TestCases/", testSuit, testStep, ".xlsx");
		  	 int i=1;
	    	 if(testStepXls.size() > 0){
		         Iterator itr = testStepXls.iterator();
		         
		    	 while (itr.hasNext()) {
		    		ArrayList innerList =(ArrayList) itr.next();
		    	 	//Iterator itr2 = innerList.iterator();
		    	 	
		    	 	stringBuffer+="<tr id='testStepRow_"+i+"'>";
		    	 	stringBuffer+="<td><input type=\"text\" name=\"stepId_"+i+"\" id=\"stepId_"+i+"\" class=\"input-block-level form-control\" width='2' readonly value='"+innerList.get(1)+"' readonly></td>" +
	 						"<td><input type=\"text\" name=\"description_"+i+"\" id=\"description_"+i+"\" class=\"input-block-level form-control\" readonly value='"+innerList.get(2)+"'></td>" +
	 						"<td><select " +
	 						" name=\"attribute_"+i+"\" id=\"attribute_"+i+"\" class=\"input-block-level form-control\" onchange=\"MyWindow=window.open('editOR.jsp?device="+request.getParameter("device")+"','MyWindow','toolbar=no,location=no,directories=no,status=no, menubar=no,scrollbars=no,resizable=no,width=1000,height=400')\"><option" +
	 						//" name=\"attribute_"+i+"\" id=\"attribute_"+i+"\" class=\"input-block-level\" disabled><option" +
	 						" value=\"elementId\" selected>ID</option>" +
	 						
	 						//"	<option value=\"name\"><a href=\"#\" onClick=\"MyWindow=window.open('https:\\www.google.com','MyWindow','toolbar=no,location=no,directories=no,status=no, menubar=no,scrollbars=no,resizable=no,width=600,height=400')\";> Name</a></option> " +
	 						"	<option value=\"parentElement\">parentElement</option> " +
	 						"	<option value=\"tag\">Tag</option> " +
	 						"	<option value=\"name\">Name</option> " +
	 						"	<option value=\"xpath\">XPath</option> " +
	 						"	<option value=\"link\">Link</option> " +
	 						"	<option value=\"css\">CSS</option>" +
	 						"	<option value=\"type\">Type</option>" +
	 						"	<option value=\"dynamic\">Dynamic</option>" +
	 						"	<option value=\"tagName\">TagName</option>" +
	 						"	<option value=\"className\">ClassName</option>" +
	 						"  </select></td> " +
	 						"   <td><input class=\"input-block-level form-control\" type=\"text\" name=\"elementId_"+i+"\" id=\"elementId_"+i+"\" readonly value='"+innerList.get(3)+"'></td>" +
	 						"   <td><input class=\"input-block-level form-control\" type=\"text\" name=\"variableName_"+i+"\" id=\"variableName_"+i+"\" readonly value=''></td>";
	 		  stringBuffer += getKeywordCombo(i,innerList.get(4).toString());
	 		  stringBuffer +="	<td><input type=\"text\" name=\"data_"+i+"\" id=\"data_"+i+"\" class=\"input-block-level form-control\" readonly value='"+innerList.get(5)+"'></td>" +
							"	<td><input type=\"text\" name=\"goToIfFail_"+i+"\" id=\"goToIfFail_"+i+"\" class=\"input-block-level form-control\" readonly value='"+innerList.get(6)+"'></td>" +
		    	 			// Image occuring in the form for edit
							"	<td><img src='images/edit_icon_disable.gif' onclick=enabledStepsRow("+i+") id='editImg_"+i+"'>" +
		    	 					"<img src='images/edit_icon.png' onclick=updateTestSteps("+i+") id='editDoneImg_"+i+"' style='display:none'></td>";
		    	 	stringBuffer+= "</tr>";
		    	 	
		    	 	
		    	 	i++;
		    	  }
		    	 i = i-1;
		    	}else{
		    		int k=1;
		    		for(; k <= 5; k++){
			    	 	stringBuffer+="<tr id='testStepRow_"+k+"'>";
			    	 	stringBuffer+="<td><input type=\"text\" name=\"stepId_"+k+"\" id=\"stepId_"+k+"\" class=\"input-block-level form-control\" value='TS-"+k+"' ></td>" +
		 						"<td><input type=\"text\" name=\"description_"+k+"\" id=\"description_"+k+"\" class=\"input-block-level form-control\" ></td>" +
		 						"<td><select " +
		 						" name=\"attribute_1\" id=\"attribute_"+k+"\" class=\"input-block-level form-control\" ><option" +
		 						" value=\"elementId\" selected>ID</option>" +
		 						"	<option value=\"parentElement\">parentElement</option> " +
		 						"	<option value=\"tag\">Tag</option> " +
		 						"	<option value=\"name\">Name</option> " +
		 						"	<option value=\"xpath\">XPath</option> " +
		 						"	<option value=\"link\">Link</option> " +
		 						"	<option value=\"css\">CSS</option>" +
		 						"	<option value=\"type\">Type</option>" +
		 						"	<option value=\"dynamic\">Dynamic</option>" +
		 						"	<option value=\"tagName\">TagName</option>" +
		 						"	<option value=\"className\">ClassName</option>" +
		 						"  </select></td> " +
		 						"   <td><input class=\"input-block-level form-control\" type=\"text\" name=\"elementId_"+k+"\" id=\"elementId_1\"  value='' ></td>" +
		 						"   <td><input class=\"input-block-level form-control\" type=\"text\" name=\"variableName_"+k+"\" id=\"variableName_1\"  value=''></td>";
		 			  stringBuffer += getKeywordCombo(1,"");
		 			  stringBuffer +="	<td><input type=\"text\" name=\"data_"+k+"\" id=\"data_"+k+"\" class=\"input-block-level form-control\" value='' ></td>" +
								"	<td><input type=\"text\" name=\"goToIfFail_"+k+"\" id=\"goToIfFail_"+k+"\" class=\"input-block-level form-control\" value='' ></td>" +
								"	<td><img src='img/edit_icon_disable.gif'></td>";
			    	 	stringBuffer+= "</tr>";
		    		}
		    		i = k;
		       }
	    	 	
	    	 	stringBuffer+= "<input type='hidden' value='"+i+"' name='totalStepRows' id='totalStepRows'>";
	    	 
	    	 return stringBuffer;
	  }
	  
	  public String getKeywordCombo(int i, String keyword){ // Displays the keyword list
		  
		  CommonUtility objCommonUtilityScript = new CommonUtility();
		  String str = "<td><select name=\"keyword_"+i+"\" id=\"keyword_"+i+"\" class=\"input-block-level form-control\" >" ;
		  try{
			  ResultSet rs = objCommonUtilityScript.readKeywords();
			  
			  if(rs != null){
				  while(rs.next()){
			    	 str+= " <option value=\"click\">"+rs.getString("keywords")+"</option>";
			     }
			  }
		  }catch(Exception e){
			  
		  }
			  str+=" </select></td>" ;
		  return str;
	  }
	  public ArrayList<ArrayList<String>> readTestStepsXls(String testCaseBasePath, String testSuit, String testCaseId, String fileExtention){
			
			ArrayList<ArrayList<String>> testCaseName = new ArrayList<ArrayList<String>>();
			
			try{
				File file = new File(testCaseBasePath+"/"+testSuit+fileExtention);

				if(!file.exists()){
					return testCaseName;
				}
				
				Xlsx_Reader suiteXls = new Xlsx_Reader(testCaseBasePath+"/"+testSuit+fileExtention);
				
				for(int suiteCount = 2; suiteCount <= suiteXls.getRowCount(Constants.TEST_STEPS); suiteCount++){
					
					String tCId = suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount);
					
					if(testCaseId.equalsIgnoreCase(tCId)){
						ArrayList<String> testCaseDetailList = new ArrayList<String>();
					
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TCID, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_TSID, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DESCRIPTION, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_ELEMENT_ID, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_KEYWORD, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_DATA, suiteCount));
						testCaseDetailList.add(suiteXls.getCellData(Constants.TEST_STEPS, Constants.COL_HEAD_GO_TO, suiteCount));
						
						testCaseName.add(testCaseDetailList);	
					}
					
					
				}
			}catch(Exception e){
				System.out.println("File Not found");
			}
			
			return testCaseName;
		}
}
