<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome To Test Automation Framework</title>
	<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-responsive.css"
		type="text/css">
</head>
<div class="navbar row-fluid headersection">
<h2 class="text-center">Autofusion Test Automation Application</h2>
</div>
<%
    /* Nitin
    CommonDriverScript objCommonDriverScript = new CommonDriverScript();
	 String path = objCommonDriverScript.readCookie(request, "cTestCaseBasePath");
	 if(path != null){
		 session.setAttribute("testCaseBasePath", path);
	 }
   */
%>
<body>
	<div class="container">
		<div class="row-fluid">
			<form method="post" action="Login" name="indexForm"
				class="form-horizontal">
				<div class="control-group">
				 <!-- label class="alert alert-error"><%//= msg %></label -->
				<div class="form-inner">
					<div class="control-group">
						<label class="span4">User Name
						</label> <input id="userName" class="span6 input-medium"
							type="text" name="userName" value="admin" >
					</div>
					<div class="control-group">
						 
						<label class="span4">Password
						</label> <input id="password" class="span6 input-medium"
							type="text" name="password" value="admin" >	
					</div>
					<div class="control-group">
						<label class="span4">Project</label> <select
							name="project" id="project" class="span3 input-medium" onchange="hideAddCaseBtn()"><option
								value="POCLevel2" selected>POCLevel2</option>
							<option value="cpos">CEB-SSR</option>
							<option value="other">CEB-MMK</option>
						</select>
					</div>
					<div class="control-group" style="display:none" id="divNewPrjName">
						<label class="span4">Project Name</label> <input type="text" name="newProjectName" id="newProjectName" value="" class="span6 input-medium">
					</div>
					<div class="control-group" id="divLocalPath">
						<label class="span4">Path of xls</label> 
						<input type="text" name="xlsPath" id="xlsPath" value="" class="span6 input-medium">
					</div>
					<div class="control-group">
						<label class="span4">&nbsp; </label>
						 <!-- input type="submit" class="btn btn-primary" name="addTestCase" id="addTestCase"
							value="Add Test Cases" onclick="return changeAction('convert.jsp')" -->
						 <input type="submit" name="submit" value="Login"
							class="btn btn-primary" onclick="return changeAction('dashboard.jsp')" >

					</div>
				</div>
				</div>
				<!-- a href="FrameworkStructure.zip"
					class="btn btn-success pull-left">Download Folder Structure</a-->
			</form>
		</div>
	</div>
</body>
<script>
	function changeAction(newAction) {

		if (!validation()) {
			return false;
		}

		if(document.getElementById("runTestFor").value == "Mobile"){
			newAction = "mblTestDetail.jsp";
		}
		
		indexForm.action = newAction;
		form.submit();

	}

	function validation() {

		if (document.getElementById("testCaseBasePath").value == "") {
			alert("Please enter the folder path");
			return false;
		}

		return true;
	}
	
	function hideAddCaseBtn(){
	 
		if(document.getElementById('project').value == "other"){
			document.getElementById('divNewPrjName').style.display = "";
		}else{
			document.getElementById('divNewPrjName').style.display="none";
		}
	}
</script>
</html>