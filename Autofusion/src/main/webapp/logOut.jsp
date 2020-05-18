<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LogOut</title>
</head>
<body>
<%-- 
<%= "User Name:" %>
<%= session.getAttribute("userName") %>
<%= "Project Name:" %>
<%= session.getAttribute("projectName") %>
<%= "TestcaseBase Path:" %>
<%= session.getAttribute("testCaseBasePath") %>
<%= "prjBasePath:" %>
<%= session.getAttribute("prjBasePath") %>
 --%>

<% 
session.removeAttribute("userName"); 
session.removeAttribute("projectName"); 
session.removeAttribute("testCaseBasePath");
session.removeAttribute("prjBasePath"); 
session.invalidate(); 
%>

<%
  response.setHeader("Cache-Control","no-cache");
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragma","no-cache");
//  response.setDateHeader ("Expires", 0);

/* 
 if(session.getAttribute("testCaseBasePath") == null){
	response.sendRedirect("index.jsp");
 }
 */ 
 //window.location.href = "C:/QA-Automation/trunk/AutomationTool/WebContent/dashboard.jsp";
 %>

<%
response.sendRedirect("index.jsp");
%>
<!-- <p>You have been successfully logout!!</p>
<p><a href="index.jsp">Login Again</a></p>
 -->
 </body>
</html>