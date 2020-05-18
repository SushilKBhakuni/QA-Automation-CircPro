<%@page import="java.io.File"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <jsp:useBean id="ui" class="com.autofusion.util.ReadXlsForUI"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="css/bootstrap-responsive.css"	type="text/css"> 
<script type="text/javascript" src="js/exporting.js"></script>
<title>Autospy Automation Application</title></head>
<body>
<div class="body-container">
  <div class="layout">
<div class="navbar row-fluid headersection">
<h2>Test Step Details</h2>
</div>

<form class="row-fluid">
<div class="container">
		<div class="row-fluid">

 
 <table width="60%" align="center" border=1  class="tableinner" cellspacing='0' cellpading='0'>
    <tr class="warning"> 
       <th width="50"> <b> <u> <%= Constants.COL_HEAD_TCID %> </u></b> </th>
       <th width="50"> <b><u> <%= Constants.COL_HEAD_TSID %> </u></b></th>
        <th width="70"> <b><u> <%= Constants.COL_HEAD_DESCRIPTION %></u></b> </th>
        <th width="60"> <b><u> <%= Constants.COL_HEAD_KEYWORD %></u></b> </th>
       <th width="60"> <b><u> Locator </u></b> </th>
       <th width="50"> <b><u> <%= Constants.COL_HEAD_DATA %></u></b> </th>
      
    </tr>
    <%   String testCaseBasePath = "";
		 String device =request.getParameter("device");       
    	 if(session.getAttribute("testCaseBasePath") == null){
    		 response.sendRedirect("error.jsp");
    	 }else{
    		 testCaseBasePath = session.getAttribute("testCaseBasePath").toString()+File.separator+device;
    	 }

         String testStep = request.getParameter("tsid").toString();
    	 String testSuit = request.getParameter("tsuit").toString();
    	 //String testSuit =  "TS-1";
    	 //String testStep = "TS-1";
    	 ArrayList testStepXls = ui.readTestStepsXls(testCaseBasePath, testSuit, testStep);
    	 if(testStepXls.size() > 0){
	         Iterator itr = testStepXls.iterator();
	
	    	 while (itr.hasNext()) {
	    		ArrayList innerList =(ArrayList) itr.next();
	    	 	Iterator itr2 = innerList.iterator();
	    	 	
	    	 	out.print("<tr >");
	    	 
	    	 	while (itr2.hasNext()) {
	    	        out.print("<td>"+itr2.next()+"</td>");
	    	 
		    	}
	    	 	//out.print("<td><img src='img/edit_icon.png'></td>");
	    	 	out.print("</tr>");
	    	}
    	 }else{
    		 out.print("<tr><td colspan=5>No test steps defined</td></tr>");
    		 
    	 }
    
    %>
 </tbody>
</table>
   <input type="button" name="btnclose" class="accordianreport2"  id="btnRunSuites" value="Close Window" onclick=" window.close()" align="right">
  
 </div>
 </div>
</form>

</div>
</div>
</body>
</html>
