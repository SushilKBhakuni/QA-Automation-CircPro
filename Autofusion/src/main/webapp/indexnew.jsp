<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Case</title>
</head>
<body>
<!--form action="edit" method="post">
<input type="submit" value="Edit Test Case">
</form-->

<a href="edit.jsp">Edit Test Case</a>

<form action="post" method="post">
<h2>Add New Test Case</h2>
Enter Test Case Data:<br>
<br>
TCID:<input type="text" name="TCID" value="1"><br>
TSID:<input type="text" name="TSID" value="2"><br>
SkipStep:<input type="text" name="SkipStep" value="3"><br>
Description:<input type="text" name="Description" value="4"><br>
Keyword:<input type="text" name="Keyword" value="5"><br>
ElementId:<input type="text" name="ElementId" value="6"><br>
Data:<input type="text" name="Data" value="7"><br>
Wait:<input type="text" name="Wait" value="8"><br>
GlobalVariable:<input type="text" name="GlobalVariable" value="9"><br>
Data1:<input type="text" name="Data1" value="10"><br>
Data2:<input type="text" name="Data2" value="11"><br>
GotoIfFail:<input type="text" name="GotoIfFail" value="12"><br>
Firefox0:<input type="text" name="Firefox0" value="13"><br>
<br>
Add Fields for ElimentID:<br>
<br>
XpathChrome: <input type="text" name="XpathChrome" value="Bike"><br>
ID:<input type="text" name="ID" value="Car"><br>
Name:<input type="text" name="Name" value="Bike"><br>
CSS:<input type="text" name="CSS" value="Car"><br>
Xpath:<input type="text" name="Xpath" value="Bike"><br>
Type:<input type="text" name="Type" value="Car"><br>
Dynamic:<input type="text" name="Dynamic" value="Bike"><br>
TagName:<input type="text" name="TagName" value="Car"><br>
ClassName:<input type="text" name="ClassName" value="Bike"><br>
Owner:<input type="text" name="Owner" value="Car"><br>
ComponentName:<input type="text" name="ComponentName" value="Bike"><br>
Abbreviation:<input type="text" name="Abbreviation" value="Car"><br> 
Comments:<input type="text" name="Comments" value="Bike"><br>
<br>
<input type="submit" value="Submit">
</form> 
</body>
</html>