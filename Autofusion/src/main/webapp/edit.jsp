<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<form action="edit" method="post">
<select name="item">
    <c:forEach items="${uniqueTestCases}" var="item">
        <option value="${item}">${item}</option>
    </c:forEach>
</select>
<input type="submit" value="Edit" name="edit">
</form>

<form action="save" method="post" name = "saved">
    <c:forEach items="${testCasetoEdit}" var="i" varStatus="count"> 
        <br>
		TCID:<input type="text" name="TCID${count.index}" value="${i.tcid}"><br>
		TSID:<input type="text" name="TSID${count.index}" value="${i.tsid}"><br>
		SkipStep:<input type="text" name="SkipStep${count.index}" value="${i.skipStep}"><br>
		Description:<input type="text" name="Description${count.index}" value="${i.description}"><br>
		Keyword:<input type="text" name="Keyword${count.index}" value="${i.keyword}"><br>
		ElementId:<input type="text" name="ElementId${count.index}" value="${i.elementId}"><br>
		Data:<input type="text" name="Data${count.index}" value="${i.data}"><br>
		Wait:<input type="text" name="Wait${count.index}" value="${i.wait}"><br>
		GlobalVariable:<input type="text" name="GlobalVariable${count.index}" value="${i.globalVariable}"><br>
		Data1:<input type="text" name="Data1${count.index}" value="${i.data1}"><br>
		Data2:<input type="text" name="Data2${count.index}" value="${i.data2}"><br>
		GotoIfFail:<input type="text" name="GotoIfFail${count.index}" value="${i.gotoIfFail}"><br>
		Firefox0:<input type="text" name="Firefox0${count.index}" value="${i.firefox0}"><br>
		<br>ElimentID<br>
		XpathChrome: <input type="text" name="XpathChrome${count.index}" value="${i.xpathChrome}"><br>
		ID:<input type="text" name="ID${count.index}" value="${i.id}"><br>
		Name:<input type="text" name="Name${count.index}" value="${i.name}"><br>
		CSS:<input type="text" name="CSS${count.index}" value="${i.css}"><br>
		Xpath:<input type="text" name="Xpath${count.index}" value="${i.xpath}"><br>
		Link:<input type="text" name="Link${count.index}" value="${i.link}"><br>
		Type:<input type="text" name="Type${count.index}" value="${i.type}"><br>
		Dynamic:<input type="text" name="Dynamic${count.index}" value="${i.dynamic}"><br>
		TagName:<input type="text" name="TagName${count.index}" value="${i.tagName}"><br>
		ClassName:<input type="text" name="ClassName${count.index}" value="${i.className}"><br>
		Owner:<input type="text" name="Owner${count.index}" value="${i.owner}"><br>
		ComponentName:<input type="text" name="ComponentName${count.index}" value="${i.componentName}"><br>
		Abbreviation:<input type="text" name="Abbreviation${count.index}" value="${i.abbreviation}"><br> 
		Comments:<input type="text" name="Comments${count.index}" value="${i.comments}"><br>
    </c:forEach>
<input type="submit" value="Save" name="save">
</form>
</body>
</html>