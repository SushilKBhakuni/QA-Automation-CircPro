<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ui" class="com.autofusion.util.ReadXlsForUI" scope="application"></jsp:useBean>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="css/bootstrap-responsive.css"	type="text/css">
<title>Autospy Automation Application</title>
</head>
<body>

<form method="post" action="Convert" class="row-fluid">
<div class="container">
		<h2 class="text-center">Run IDE Recorded Test Cases</h2>
		<div class="row-fluid">

 <table width="100%" align="center"  class="table table-bordered">
 
 	 <tr>
	       <th>Sno.</th>
	       <th><%= Constants.COL_HEAD_IDE_FILE_NAME %></th>
	       <th><%= Constants.COL_HEAD_CREATE_NEW_SUIT %></th>
	       <th><%= Constants.COL_HEAD_CREATE_NEW_TEST_CASE %></th>
	       <th><%= Constants.COL_HEAD_APPEND_IN_TEST_CASE %></th>
	       <th><%= Constants.COL_HEAD_DESCRIPTION %></th>
	  </tr>
 
  <%    
         String testCaseBasePath = request.getParameter("testCaseBasePath");
         session.setAttribute("sTestCaseBasePath", testCaseBasePath) ;
         
    	 ArrayList suitXls = ui.readConfigIdeData(testCaseBasePath);
         Iterator itr = suitXls.iterator();
         
         String sName = "";
         
         int i = 1;
         int p = 2;
    	 while (itr.hasNext()) {
    		ArrayList innerList =(ArrayList) itr.next();
    	 	Iterator itr2 = innerList.iterator();
    	 	
    	 	out.print("<tr>");
    	 	out.print("<td>"+i+"</td>");
    	 	
    	 	while (itr2.hasNext()) {
    	 			String listData = itr2.next().toString();
    	 	    	out.print("<td>"+listData+"</td>");
    	 	}
       	 	out.print("</tr>");
    	 }
    %>
 </table>

<table width="100%">
  <tr><td align="right"><input class="btn btn-primary" type="submit" name="subIde" id="subIde">&nbsp;<input class="btn btn-primary" type="button" name="back" id="back" value="Back" onclick="window.history.back()">
   <input type="hidden" name="testCaseBasePath" id="testCaseBasePath" value="<%= testCaseBasePath%>">
   <input type="hidden" name="runApplication" id="runApplication" value="<%= request.getParameter("runTestFor")%>">
  </td></tr>

 </table>
</div>
</div>

 </form>

</body>
</html>