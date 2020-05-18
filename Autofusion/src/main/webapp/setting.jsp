<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>

<% 
if(session.getAttribute("testCaseBasePath") != null ) {
%>
<jsp:include page="header1.jsp"/>
<% 
} else {
	%>
	<jsp:include page="header1.jsp"/>
<%}

  response.setHeader("Cache-Control","no-cache");
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragma","no-cache");
  response.setDateHeader ("Expires", 0);
    	 CommonUtility objCommonDriverScript = new CommonUtility();
    	 String defaultBrowser ="" ;
    	 String folderPath ="";
    	 String projectCode="";
         // e mail settings field
         String mgm ="checked";
         String rm="checked";
		 String dm="checked";
		 String mm="checked";
		 String wm="checked";
		 String ddm="checked";
		 String mdm="checked";
		 String wdm="checked";
		 String mysqlUN="";
		 String mysqlPass="";
		 String andSdkPath = "";
		 String apkPath = "";
		 String appiumPort = "";
		 String launchActivity = "";
		 String apkPackage = "";
		 String apkName = "";
		 String apim = "checked";
		 String mIosDm = "checked";
		 String reInstallApp= "checked";
		 String smtpPort="";
		 String emailTo = "";
		 String smtpHost = "";
		 String emailCC = "";
		 String emailBCC="";
		 String emailMsg=""; 
		 String subject="";
		 String emailUserName=""; 
		 String emailPass="";
		 String appendReportInMail="";
		 
		 String iosUdid=""; 
		 String iosDeviceName="";
		 String iosPlatformVer="";
					
		 String newProjectName=session.getAttribute("projectName").toString();
         if(!newProjectName.equals("newProject")){
	    	 if(session.getAttribute("testCaseBasePath") != null){
	    		 try{
	    	 			//folderPath = session.getAttribute("testCaseBasePath").toString();
	    	  	 		
		    	  		wdm = (CommonUtility.USER_CONFIG.getProperty("webDashboardMenu").equals("on"))?"checked":"";
						ddm = (CommonUtility.USER_CONFIG.getProperty("dskDashboardMenu").equals("on"))?"checked":"";
						mdm = (CommonUtility.USER_CONFIG.getProperty("mblDashboardMenu").equals("on"))?"checked":"";
						mIosDm = (CommonUtility.USER_CONFIG.getProperty("mblIosDashboardMenu").equals("on"))?"checked":"";
						
		    	  	 	
						wm = (CommonUtility.USER_CONFIG.getProperty("webMenu").equals("on"))?"checked":"";
						dm = (CommonUtility.USER_CONFIG.getProperty("desktopMenu").equals("on"))?"checked":"";
						mm = (CommonUtility.USER_CONFIG.getProperty("mblMenu").equals("on"))?"checked":"";
						rm = (CommonUtility.USER_CONFIG.getProperty("reportMenu").equals("on"))?"checked":"";
						mgm =(CommonUtility.USER_CONFIG.getProperty("manageMenu").equals("on"))?"checked":"";
						apim = (CommonUtility.USER_CONFIG.getProperty("apiMenu").equals("on"))?"checked":"";
								
						mysqlUN = CommonUtility.USER_CONFIG.getProperty("dbUserName");
						mysqlPass = CommonUtility.USER_CONFIG.getProperty("dbPassword");
						
						andSdkPath = CommonUtility.USER_CONFIG.getProperty("ADB_PATH");
						apkPath = CommonUtility.USER_CONFIG.getProperty("APK_PATH");
						appiumPort = CommonUtility.USER_CONFIG.getProperty("APPIUM_PORT");
						
						launchActivity = CommonUtility.USER_CONFIG.getProperty("LAUNCH_ACTIVITY");
						apkPackage = CommonUtility.USER_CONFIG.getProperty("APK_PACKAGE");
						apkName = CommonUtility.USER_CONFIG.getProperty("APK_NAME");
						reInstallApp = CommonUtility.USER_CONFIG.getProperty("ReInstallApp");
						
						launchActivity = CommonUtility.USER_CONFIG.getProperty("IOS_UDID");
						apkPackage = CommonUtility.USER_CONFIG.getProperty("IOS_DEVICE_NAME");
						apkName = CommonUtility.USER_CONFIG.getProperty("IOS_PLATFORM_VER");
						
						
						emailCC = CommonUtility.USER_CONFIG.getProperty("EMAIL_CC");
						emailTo = CommonUtility.USER_CONFIG.getProperty("EMAIL_TO");
						emailBCC = CommonUtility.USER_CONFIG.getProperty("EMAIL_BCC");
						smtpHost = CommonUtility.USER_CONFIG.getProperty("SMTP_HOST");
						smtpPort = CommonUtility.USER_CONFIG.getProperty("SMTP_PORT");
						
						emailMsg = CommonUtility.USER_CONFIG.getProperty("MAIL_BODY_TEXT");
						subject = CommonUtility.USER_CONFIG.getProperty("EMAIL_SUBJECT");
						emailUserName = CommonUtility.USER_CONFIG.getProperty("EMAIL_USER_NAME");
						emailPass = CommonUtility.USER_CONFIG.getProperty("EMAIL_PASSWORD");
						
						
						
	    		    }
	    		 catch(Exception e)
	    		 {
	    			    //config.property not found
	    				//response.sendRedirect("error.jsp");
	    		 }
	    	 }
         }
  %>
<p  id="result"></p>
<div class="body-container">
  <div class="layout">
  <h2 class="bdrbtm">Advance Setting</h2>	
	<p  class="text-warning" id="result"></p>
	<div class="whitebg blockelement padding15" id="home">
		<form method="post" action="SaveSetting" class="fullwidth" id="settingForm"  name="settingForm">
		   <input type="hidden" name="xlsStructurePath" id="xlsStructurePath" value="<%=session.getAttribute("xlsStructurePath") %>">
		   
	       <div class="fullwidth innerbtmrow">
		 	<div class="padding30 fl width90">
		 	
		 	<div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Project Configuration</b></label></p></th>
			</tr>
			  		<tr><td align='left' valign='middle' width='40%'><label>Project Location*</label>(<i>Please use double slashes</i> D://ABC//)</td>
					  		<td align='left' valign='middle'><input id="testCaseBasePath" class="span6 input-medium form-control"  type="text" name="testCaseBasePath" value="<%= folderPath %>" ></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>Project Name</label></td>
				      		 <td align='left' valign='middle' ><input id="projectName" class="span6 input-medium form-control"  type="text" name="projectName" readonly value="<%= newProjectName %>" ></td> </tr>
	   		</table>    
	    </div>
		 	
		       
<input id="projectCode" class="span6 input-medium"  type="hidden" name="projectCode" value="<%= projectCode %>" >
    	<div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>UI Configuration</b></label></p></th>
			</tr>
	  		<tr><td align='left' valign='middle' width='40%'><label>Web Menu</label></td>
			  		<td align='left' valign='middle'><input id="webMenu" type="checkbox" name="webMenu" <%=wm %> ></td> </tr>
		      <tr>   <td align='left' valign='middle' width='40%'><label>Mobile Menu</label></td><td align='left' valign='middle' ><input id="mblMenu" class="span6 input-medium"  type="checkbox" name="mblMenu" <%=mm %>></td> </tr>
		      <tr>   <td align='left' valign='middle' width='40%'><label>Desktop Menu</label></td><td align='left' valign='middle'><input id="desktopMenu" class="span6 input-medium"  type="checkbox" name="desktopMenu"  <%=dm %>></td> </tr>
		      <tr>   <td align='left' valign='middle' width='40%'><label>Manage</label></td><td align='left' valign='middle'><input id="manageMenu" class="span6 input-medium"  type="checkbox" name="manageMenu" <%=mgm %> ></td> </tr>
		      <tr>   <td align='left' valign='middle' width='40%'><label>Report</label></td><td align='left' valign='middle'><input id="reportMenu" class="span6 input-medium"  type="checkbox" name="reportMenu" <%=rm %> ></td> </tr>
		      <tr>   <td align='left' valign='middle' width='40%'><label>API Menu</label></td><td align='left' valign='middle'><input id="apiMenu" class="span6 input-medium"  type="checkbox" name="apiMenu" <%=apim %> ></td> </tr>
				
	   		</table>    
	    </div>

    	<div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Dashboard Configuration</b></label></p></th>
			</tr>
		  		<tr><td align='left' valign='middle' width='40%'><label>Web Report</label></td>
				  		<td align='left' valign='middle'><input id="webDashboardMenu" type="checkbox" name="webDashboardMenu" <%=wdm %> ></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Mobile Report</label></td>
			      		 <td align='left' valign='middle' ><input id="mblDashboardMenu" class="span6 input-medium form-control"  type="checkbox" name="mblDashboardMenu" <%=mdm %>></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Desktop Report</label></td><td align='left' valign='middle'><input id="dskDashboardMenu" class="span6 input-medium"  type="checkbox" name="dskDashboardMenu"  <%=ddm %>></td> </tr>
			       <tr>   <td align='left' valign='middle' width='40%'><label>iOS Report</label></td><td align='left' valign='middle'><input id="mblIosDashboardMenu" class="span6 input-medium"  type="checkbox" name="mblIosDashboardMenu"  <%=mIosDm %>></td> </tr>
	   		</table>    
	    </div>


    	<div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Database Configuration</b></label></p></th>
			</tr>
			  		<tr><td align='left' valign='middle' width='40%'><label>User Name*</label></td>
					  		<td align='left' valign='middle'><input id="dbUserName" type="text" class="span6 input-medium form-control" name="dbUserName" value="<%=mysqlUN %>" ></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>Password*</label></td>
				      		 <td align='left' valign='middle' ><input id="dbPassword" class="span6 input-medium form-control"  type="text" name="dbPassword" value="<%=mysqlPass%>"></td> </tr>
	   		</table>    
	    </div>
	    <div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Android Configuration</b></label></p></th>
			</tr>
			  		<tr><td align='left' valign='middle' width='40%'><label>Android SDK Path*</label></td>
					  		<td align='left' valign='middle'><input id="andSdkPath" type="text" class="span6 input-long form-control" name="andSdkPath" value="<%=andSdkPath %>" ></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>APK Path*</label></td>
				      		 <td align='left' valign='middle' ><input id="apkPath" class="span6 input-medium form-control"  type="text" name="apkPath" value="<%=apkPath%>"></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>APK Name*</label></td>
				      		 <td align='left' valign='middle' ><input id="apkName" class="span6 input-medium form-control"  type="text" name="apkName" value="<%=apkName%>"></td> </tr>
				      		 
				       <tr>   <td align='left' valign='middle' width='40%'><label>Appium Port*</label></td>
				      		 <td align='left' valign='middle' ><input id="appiumPort" class="span6 input-medium form-control"  type="text" name="appiumPort" value="<%=appiumPort%>"></td> </tr>
				      		 
				       <tr><td align='left' valign='middle' width='40%'><label>Launch Activity*</label></td>
					  		<td align='left' valign='middle'><input id="launchActivity" type="text" class="span6 input-medium form-control" name="launchActivity" value="<%=launchActivity %>" ></td> </tr>
					  <tr>   <td align='left' valign='middle' width='40%'><label>Package Name*</label></td>
				      		 <td align='left' valign='middle' ><input id="packageName" class="span6 input-medium form-control"  type="text" name="packageName" value="<%=apkPackage%>"></td> </tr>
				       		
				       		 
	   		</table>    
	    </div>
	    <div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>iOS Configuration</b></label></p></th>
			</tr>
			  		<tr><td align='left' valign='middle' width='40%'><label>UDID*</label></td>
					  		<td align='left' valign='middle'><input id="iosUdid" type="text" class="span6 input-long form-control" name="iosUdid" value="<%=iosUdid %>" ></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>Device Name*</label></td>
				      		 <td align='left' valign='middle' ><input id="iosDeviceName" class="span6 input-medium form-control"  type="text" name="iosDeviceName" value="<%=iosDeviceName%>"></td> </tr>
				      <tr>   <td align='left' valign='middle' width='40%'><label>Platform Version*</label><i>(7.0.4)</i></td>
				      		 <td align='left' valign='middle' ><input id="iosPlatformVer" class="span6 input-medium form-control"  type="text" name="iosPlatformVer" value="<%=iosPlatformVer%>"></td> </tr>
	   		</table>    
	    </div>
	    <div class="fullwidth">
    	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Email Configuration</b></label></p></th>
			</tr>
		
		      	       	  <tr>   <td align='left' valign='middle' width='40%'><label>Email To:</label></td>
			      		 <td align='left' valign='middle' ><input id="emailTo" class="span6 input-medium form-control"   type="text" name="emailTo" value="<%=emailTo%>"></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Email CC</label></td>
			      		 <td align='left' valign='middle' ><input id="emailCC" class="span6 input-medium form-control"  type="text" name="emailCC" value="<%=emailCC%>"></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Email BCC</label></td>
			      		 <td align='left' valign='middle' ><input id="emailBCC" class="span6 input-medium form-control"  type="text" name="emailBCC" value="<%=emailBCC%>"></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Subject</label></td>
			      		 <td align='left' valign='middle' ><input id="subject" class="span6 input-medium form-control"  type="text" name="subject" value="<%=subject%>"></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Message</label></td>
			      		 <td align='left' valign='middle' ><input id="emailMsg" class="span6 input-medium form-control"  type="text" name="emailMsg" value="<%=emailMsg%>"></td> </tr>
			      		 
			       <tr>   <td align='left' valign='middle' width='40%'><label>SMTP Host</label></td>
			      		 <td align='left' valign='middle' ><input id="smtpHost" class="span6 input-medium form-control"  type="text" name="smtpHost" value="<%=smtpHost%>"></td> </tr>
			      		 
			       <tr><td align='left' valign='middle' width='40%'><label>SMTP port</label></td>
				  		<td align='left' valign='middle'><input id="smtpPort" type="text" class="span6 input-medium form-control " name="smtpPort" value="<%=smtpPort %>" ></td> </tr>
				 
				  <tr>   <td align='left' valign='middle' width='40%'><label>UserName</label></td>
			      		 <td align='left' valign='middle' ><input id="emailUserName" class="span6 input-medium form-control"  type="text" name="emailUserName" value="<%=emailUserName%>"></td> </tr>
			      <tr>   <td align='left' valign='middle' width='40%'><label>Password</label></td>
			      		 <td align='left' valign='middle' ><input id="emailPass" class="span6 input-medium form-control"  type="text" name="emailPass" value="<%=emailPass%>"></td> </tr>
			       		
				       		 
				   		
				       		 
	   		</table>    
	    </div>
	    <table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
			<tr> 
			     <th align='left' colspan=2><p> <label class='checkbox'><b>Other Configuration</b></label></p></th>
			</tr>
		  		<tr>   <td align='left' valign='middle' width='40%'><label>Re-Installed App</label></td>
			      		 <td align='left' valign='middle' >
			      		 <input id="reInstallApp" class="span6 input-medium form-control"  type="checkbox" name="reInstallApp" value="<%=reInstallApp%>"></td> </tr>
			     <tr>   <td align='left' valign='middle' width='40%'><label>Append Report In Mail</label></td>
			      		 <td align='left' valign='middle' >
			      		 <input id="appendReportInMail" class="span6 input-medium form-control"  type="checkbox" name="appendReportInMail" value="<%=appendReportInMail%>"></td> </tr> 		 
			</table>    
	    </div>
		
		
		 	<div class="frmrow marginbtm20">
	        </div>
	</div>
</div>	     
		 <div class="control-group" align="right">
				<label class="span4">&nbsp; </label>
				<input type="button" class="button" name="btnSaveSetting" id="btnSaveSetting"	value="Save Setting" onclick="return saveSetting()">
		 </div>
		   </form> 
	 </div>   
 </div> 
   </div>
</div>

</body>
</html>

<jsp:include page="footer.html"></jsp:include>

<script language = "Javascript">
function checkEmail(emailId) 
{
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailId)){
	return true;
	}    
	return false;
	}
function validate()
{
	    var email = document.getElementById('email').value;
	    var db = document.getElementById('defaultBrowser').value;
	    var fl = document.getElementById('testCaseBasePath').value;
	    var lp = document.getElementById('logPath').value;
	    var projname = document.getElementById('projectName').value;
	    var projectCode = document.getElementById('projectCode').value;
	    
	    if (checkEmail(email)==false)
	    {
	        alert("Invalid Email Address");
	        return false;
	    }
	    else if(email == "") 
	    {
	    	alert("Email can not be blank");
	    	return false;
	    } 
	    else if(projectCode == "") 
	    {
	    	alert("Project Code can not be left blank");
	    	return false;
	    } 
	    
	    else if( /[^a-zA-Z0-9\-\/]/.test(projname))
	    {
	        alert('Project Name is not alphanumeric');
	        return false;
	    }
	    
	    else if(projname == "") 
	    {
	    	alert("project name can not be blank");
	    	return false;
	    }
	   
	    else if(db == "") 
	    {
	    	alert("default browser can not be blank");
	    	return false;
	    }
	   
	    else if(fl == "") 
	    {
	    	alert("folder location can not be blank");
	    	return false;
	    }
	   
	    else if(lp == "") 
	    {
	    	alert("log path can not be blank");
	    	return false;
	    }
	    else
	    {
	    	return true;
	    } 
	    
}

    
     function saveSetting()
     {
	    var form = $('#settingForm');
		$.ajax({
			type: "POST",
			url: "SaveSetting",
			data: form.serialize(),
			success: function (data) {
			var result=data;
		    //	alert(data);
		 	$("p").addClass("alert alert-success");
		    $("#result").html("Setting save");
		    alert("Your settings are saved.");
		    window.location.href="dashboard.jsp";
		    //var windowUrl = window.location.href;
			//if (windowUrl.indexOf('setting.jsp', 0) != -1) {
				//var newWindowUrl = windowUrl.replace('setting.jsp','dashboard.jsp');
				
			//}
			}
	  	 });
		 //settingSavedLogOut();
	}
  
	$(function () {
		$('#myTab a.active').tab('show');
	    
	    $('#myTab a').click(function (e) {
	    	  e.preventDefault();
	    	  $(this).tab('show');
	    	});
	})

</script>