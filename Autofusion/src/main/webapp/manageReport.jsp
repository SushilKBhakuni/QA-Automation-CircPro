<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.lang.Object"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="com.autofusion.util.Xls_Reader"%>
<%@page import="java.util.ArrayList"%>
<%
    String testBasePath = "";
 	if(session.getAttribute("testCaseBasePath") == null)
 	{
		response.sendRedirect("setting.jsp");
 	}
 	else
 	{
		testBasePath =(String)session.getAttribute("testCaseBasePath");
	}
%>

<jsp:include page="header.jsp"/>

<style>
 button {
  background-image: url(images/delete1.jpg);
  background-repeat: no-repeat;
  background-position: 50% 50%;
  /* put the height and width of your image here */
  height: 28px;
  width: 30px;
  border: none;
}

button span {
  display: none;
}
td .mytd
{
text-align: center;
}
#c2
{
float:right;
}
.manageButton
 {
    border-radius:2px; 
    cursor:pointer; 
    color:#fff;
    box-shadow:1px 1px 1px #0E6696;
    float:right;
    display: block;
    height: 10px;
    width: 65px;
    background:#37b5e5;
    text-align: center;
    padding:10px 20px;
    font-size:14px;
    border:0px;
    text-decoration: none;
    list-style:none;
    margin-top:15px;
}
#showhide 
{
    display: none;
}
</style>

<div class="body-container">
<div class="layout">
<h2 class="bdrbtm">Manage Reports Section</h2>
<p  class="text-warning" id="result"></p>
<form method="post" class="fullwidth" id="manageReportForm" name="manageReportForm">
<table width="100%" style="bordercolor : #CEE3F6;" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
				<tr>
					<td width="150" class="padding15 info">
						<h4>Device</h4>
					</td>
					<td align="center" class="padding15 info"><select name="SelectDeviceName" id="SelectDeviceName">
							<option value="">Select a Device</option>
							<option value="0">Web</option>
							<option value="1">Mobile</option>
							<option value="2">Window</option>
					</select></td>
					
					<td width="150" class="padding15 info">
						<h4>SuitName</h4>
					</td>
					<td align="center" class="padding15 info"><select name="SelectSuits" id="selectSuitsName" disabled="disabled">
							<option>Select Device First</option>
					</select></td>
					
					<td width="100" class="padding15 info">
						<h4>Files</h4>
					</td>
					<td align="center" class="padding15 info"><select name="selectFilesName" id="selectFilesName" disabled="disabled">
							<option>Select SuitName First</option>
					</select></td>
					
					<td width="150" class="padding15 info"><h4>Browsers</h4></td>
					<td align="center" class="padding15 info"><select name="SelectBrowserName" id="selectBrowserName">
					        <option>Firefox</option>
							<option>Internet Explorer</option>
							<option>Chrome</option></select></td>
							
					<td align="center" class="padding15 info">
			<div class="manageButton" id="btnRunSuites" data-target=".accordianouter" onclick="getDisplayData1()">ShowLogs</div>
					</td>
				</tr>
</table>
  
    <div class="accordianouter" style="display:none;">
       <div class="contentbox">
        <h1 class="accordianheader1"><b><strong>List of the Reports</strong></b> </h1><br>
          <div id="test3">
          </div>
          </div> 
          </div> 
       </form> 	
</div>
</div>
     
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
var button1 = document.querySelector('.manageButton');
button1.addEventListener('click', function(event) {
    var target = document.querySelector(button1.getAttribute('data-target'));
    if (target.style.display == "none") {
        target.style.display = "block";
       // button.innerHTML = button.getAttribute('data-shown-text');
    } else {
        target.style.display = "none";
      //  button.innerHTML = button.getAttribute('data-hidden-text');
    }
});

$("select#SelectDeviceName").change(function(){
	var selectDevice = $(this).val();
	if(!selectDevice){
		$("select#selectSuitsName").attr("disabled","disabled").empty().append("<option>Select Device First</option>");
		$("select#selectFilesName").attr("disabled","disabled").empty().append("<option>Select SuitName First</option>");
		return;
	}
	$.ajax({
		type: "POST",
		url: "FetchTestReports",
		dataType : "html",
		data: {device:selectDevice},
		success: function (data)
		{
			$("select#selectSuitsName").removeAttr("disabled").empty().append(data);	
		}
   	 });
});

$("select#selectSuitsName").change(function(){
	var selectSuits = $(this).val();
	var selectDevice = $("select#SelectDeviceName").val();
	if(!selectSuits){
		$("select#selectFilesName").attr("disabled","disabled").empty().append("<option>Select SuitName First</option>");
		return;
	}
	$.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "text",
		data: {method:'getFileCombo',selectSuits:selectSuits,selectDevice:selectDevice},
		success: function (data)
		{
			$("select#selectFilesName").removeAttr("disabled").empty().append(data);	
		}
  	 });
});

$(function () {
	$("#desktop").removeClass("active");
	$("#dashboard").removeClass("active");
    $("#mobile").removeClass("active");
    $("#web").removeClass("active");
    $("#setting").removeClass("active");
    $("#manage").removeClass("active");
	$("#report").addClass("active");
});

function gettingCheckedBoxes() //Not in use but maybe useful in future
{
	var checkBoxArr = [];
	$("[id*=check]").click(function(){
		var checkId = $(this).attr('id');
		var checkStatus = $(this).is(':checked');
		if(checkStatus){
			if(checkBoxArr.indexOf(checkId) == -1){

				checkBoxArr.push(checkId);
			}
		} else {
			var pos = checkBoxArr.indexOf(checkId);
			checkBoxArr.splice(pos,1);
		}
		console.log(checkBoxArr);
	});	
}

function deleteMultipleSelectedChkboxes()
	{
	    //  var counter = count;
		var form = $('#manageReportForm');
		$.ajax({
				type: "POST",
				url: "LogFileMultipleDelete",
				data: form.serialize(),
				success: function (data) {
				$("p:first").html("Files are getting deleted...").fadeIn("slow");
			}
	  	 });
	}

function Delete(key, path, logFileName) { 
	var LogFile= logFileName;
	var Path= path;
	var buttonId = '#delete_'+key;
		 $.ajax({
				type: "POST",
				url: "LogFileDelete",
				dataType : "text",
				data: {LogFile:LogFile, Path:Path},
				success: function (data)
				{
					$("p").addClass("alert alert-success");
				    $("#result").html("Setting save");
				    $("#result").append(data);
				}
		   });
		$(buttonId).parent().parent().remove();
}

function Archive(key, path, logFileName) { 
	var LogFile= logFileName;
	var Path= path; 
	var buttonId = '#archive_'+key;
	            $.ajax({
				type: "POST",
				url: "Archive",
				dataType : "text",
				data: {LogFile:LogFile, Path:Path},
				success: function (data)
				{
				 	$("p").addClass("alert alert-success");
				    $("#result").html("Setting save");
				    $("#result").append(data);
				}
		   });
}

function expand(count){
	document.getElementById('minus'+count).style.display='';
	document.getElementById('plus'+count).style.display='none';
	document.getElementById('tblTS'+count).style.display='';
}

function collapse(count){
	document.getElementById('plus'+count).style.display='';
	document.getElementById('minus'+count).style.display='none';
	document.getElementById('tblTS'+count).style.display='none';
}

function getDisplayData1() { // called on the click of button
	var selectDevice = $("select#SelectDeviceName").val();
	var selectSuits = $("select#selectSuitsName").val();
	var selectFiles = $("select#selectFilesName").val();

	if(selectDevice && selectSuits && selectFiles) {
var browser= $('select#selectBrowserName option:selected').text();
var suitName= $('select#selectSuitsName option:selected').text();
var param1= "getDisplayData";

$.ajax({
	type: "POST",
	url: "ManageReportServlet",
	dataType : "html",
	data: {browser:browser, suitName:suitName, method:param1},
	success: function(data)
	{
		$("#test3").empty().html(data);	
	}
	     }); }
	else {
		$("#test3").empty().append("No Error!! But Select the dropdowns first, then click the button to get the list of reports!!");	
	}
}

</script>
<jsp:include page="footer.html"></jsp:include>