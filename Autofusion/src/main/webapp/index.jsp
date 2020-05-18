<%@page import="java.util.Properties"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.*"%>
<%@page import="com.autofusion.sql.*"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Test Automation Framework</title>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<%   
      CommonUtility objCommonUtility= new CommonUtility();
	  String comboS = objCommonUtility.comboValueReturn();
	 
%>


<body>
<div class="header">
  <div class="loginlayout"> <a href="#"><img src="images/logo-login.png" alt="" /></a> </div>
</div>
<div class="body-container">
  <div class="loginlayout">
    <div class="left-box">
      <h2>Start testing <span>Anytime, <strong>anywhere.</strong></span></h2>
      <div class="align-center full-width"><img src="images/test-windows-mac-linux.png" alt=""/></div>
    </div>
    <div class="right-box">
    <form method="post" action="Login" name="indexForm" class="loginfrm">
        <h3>Please sign in</h3>
        <div class="frmgroup">
          <label>Username</label>
          <input type="text" name="userName" id="userName" value="Robert" class="span6 input-medium">
        </div>
        <div class="frmgroup">
          <label>Password</label>
          <input id="password" class="span6 input-medium" type="password" name="password" value="Robert" >	
        </div>
        <div class="frmgroup">
          <label>Project</label>
          <select name="selProject" id="selProject" class="span3 input-medium" onchange="hideAddCaseBtn()">
				<option value="none">Select A Project</option>
				<option value="newProject">New</option>
				<%=comboS %> 
			</select>
        </div>
        
        <div class="frmgroup" id="errMsgDisplay">
      
         </div>
	          <input type="hidden" name="error" id="error" value="<%= request.getParameter("err")%>" class="span6 input-medium">
        <!--div-->
        
        <!-- div class="frmgroup" id ="errorMsg" style="display:none"-->
	          <!--   input type="hidden" name="errorMsg" id="errorMsg" value="" class="span6 input-medium"-->
        <!--/div-->
        
        <!-- input type="hidden" name="hXlsPath" id="hXlsPath" value="" class="span6 input-medium"-->
        <div class="frmgroup" id ="divNewPrjName" style="display:none">
          <label>New Project Name</label>
          <input type="text" name="newProjectName" id="newProjectName" value="" class="span6 input-medium">
        </div>
        
       <!--  
        <div class="frmgroup" id="xlsPathDivId" style="display:none">
	          <label>Path of XLS Master Folder</label>
	          <input type="text" name="xlsPath" id="xlsPath" value="" class="span6 input-medium">
	    </div>
       -->
       
        <div class="frmgroup">
         <input type="submit" name="submit" value="Login"
							class="btn btn-primary" onclick="return changeAction('dashboard.jsp')" >
        </div>
      </form>
    </div>
  </div>
</div>
<jsp:include page="footer.html"></jsp:include>
</body>

<script type="text/Javascript">

// errorAction();

// function errorAction()
// {
// 	if(document.getElementById("error").value == "ce")
// 	{
// 		alert("Enter the xlsPath");
// 		document.getElementById('xlsPathDivId').style.display = "";
		
// 	}
// }

function changeAction(newAction)
    {
		if (!validation()) {
			return false;
		}
        
		if(document.getElementById("hXlsPath").value == "")
			document.getElementById("hXlsPath").value= document.getElementById("xlsPath").value;
		
		if(document.getElementById("runTestFor").value == "Mobile"){
			newAction = "mblTestDetail.jsp";
		}
		indexForm.action = newAction;
		form.submit();
	}

	function validation() 
	{
		if (document.getElementById("testCaseBasePath").value == "") {
			alert("Please enter the folder path");
			return false;
		}
		return true;
	}
	
	function hideAddCaseBtn(){ 
		if(document.getElementById('selProject').value == "newProject"){
			document.getElementById('divNewPrjName').style.display = "";
			// document.getElementById('xlsPathDivId').style.display = "";
		}else{
			// document.getElementById('xlsPathDivId').style.display = "none";
			document.getElementById('divNewPrjName').style.display="none";
		}
	}
</script>
</html>