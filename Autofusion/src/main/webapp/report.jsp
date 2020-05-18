<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="com.autofusion.util.Xlsx_Reader"%>
<%@page import="java.io.*"%>
<%@page import="java.lang.Object"%>

 <%
  response.setHeader("Cache-Control","no-cache");
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragma","no-cache");
  response.setDateHeader ("Expires", 0);
  
  if(session.getAttribute("testCaseBasePath") == null){
	response.sendRedirect("index.jsp");
 }
 %>
 
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
<%
String testSheetName;
Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testBasePath+"/"+Constants.SUIT_FILE_NAME);
testSheetName = Constants.TEST_SUITE_SHEET;
ArrayList<String> suiteList = new ArrayList<String>();
for(int i = 2; i <= webTestSuitXls.getRowCount(testSheetName);  i++)
 {
 String suitName = webTestSuitXls.getCellData(testSheetName,  Constants.COL_HEAD_TSID, i) ;
 suiteList.add(suitName);
 }
 String combo = "<option value='' selected>Select Test Suite</option>\n";
	 for(int i=0; i < suiteList.size(); i++)
		  {
			  combo+= "<option value='"+suiteList.get(i)+"'>"+suiteList.get(i)+"</option>\n";  
		  }
	 
	// InputStreamReader userFileConfig = new InputStreamReader(new FileInputStream(testBasePath+"/config.properties"), "UTF-8");
  // 	Properties uconfig = new Properties();
	// uconfig.load(userFileConfig);
		// String userLogPath = uconfig.getProperty("logPath");
%>
<style>
.button
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
.accordianheader1
{
 padding:3px;
 background:#37b5e5; 
 width:1040px; 
 float:left;
 color:#fff; 
 font-size:15px;
  line-height:32px;
 }
 .accordianheader2
{
 padding:3px;
 background:#37b5e5; 
 width:518px; 
 float:left;
 color:#fff; 
 font-size:15px;
  line-height:32px;
 }
#showhide 
{
    display: none;
}
</style>

<div class="body-container">
<div class="layout">
  
  <h2 class="bdrbtm">Test Reports</h2>
  
<!--   <div class="whitebg blockelement padding15" id="home">		      -->
<!-- <ul class="progressbar"> -->
<!-- <li class="active"><span class="icon"></span><span class="titletxt"><a href="manageReport.jsp" class="link1">Manage Reports</a></span><span class="progressline1"></span></li> -->
<!-- <li><span class="icon"></span><span class="titletxt"><a href="archiveHistory.jsp">Archive History</a></span></li> -->
<!-- </ul> -->
<!-- </div> -->

  <p  class="text-warning" id="result"></p>
  
    <form method="post" class="fullwidth" id="reportForm" name="reportForm">
 <!--   <div class="frmrow"> -->
    <table width="100%" style="bordercolor : #CEE3F6;" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
				<tr>
					<td width="150" class="padding15 info">
						<h4>Device</h4>
					</td>
					<td align="center" class="padding15 info"><select
						name="SelectDevice" id="SelectDevice">
							<option value="">Select a Device</option>
							<option value="0">Web</option>
							<option value="1">Mobile</option>
							<option value="2">Window</option>
					</select></td>
					<td width="150" class="padding15 info">
						<h4>SuitName</h4>
					</td>
					<td align="center" class="padding15 info"><select
						name="SelectSuits" id="selectSuits" disabled="disabled">
							<option>Select Device First</option>
					</select></td>
					<td width="100" class="padding15 info">
						<h4>Files</h4>
					</td>
					<td align="center" class="padding15 info"><select
						name="selectFiles" id="selectFiles" disabled="disabled">
							<option>Select SuitName First</option>
					</select></td>
					<td width="150" class="padding15 info"><h4>Browsers</h4></td>
					<td align="center" class="padding15 info"><select
						name="SelectBrowser" id="selectBrowser"><option>Firefox</option>
							<option>Internet Explorer</option>
							<option>Chrome</option></select></td>
					<td align="center" class="padding15 info">
						<div class="button" id="btnRunSuites"
							data-target=".accordianouter" data-shown-text="HideReport"
							data-hidden-text="ShowReport" onclick="getData()">ShowReport</div>

					</td>
				</tr>
			</table>
  <!-- </div> -->
   <div class="accordianouter" style="display:none;">
     
      <div class="contentbox">
      
        <h5 class="accordianheader2"> TEST DETAIL SUMMARY </h5>
          <table width="50%" id="passFailTable" align="center" class="tableouter marginbtm20" cellspacing="0" cellpadding="0">
          </table>
        
       </div>
       
       
        <div class="contentbox">
         <h4 class="accordianheader1"> DETAILED REPORT </h4>
    
	 	<table width="100%" id="htmlReportTable" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
        </table>
        <!--  
        <a href="file:///C://tetstt//Batch1//log_20140714165359//Firefox(0)//reports//SmokeSuite_TC-114.July.201404.55.52.html" target="_self">Click</a>
        -->
        </div>
      
    </div>
  </form>
  </div>
  </div>
  
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

var button = document.querySelector('.button');
button.addEventListener('click', function(event){
    var target = document.querySelector(button.getAttribute('data-target'));
    if (target.style.display == "none") {
        target.style.display = "block";
        //button.innerHTML = button.getAttribute('data-shown-text');
    } else {
        target.style.display = "none";
       // button.innerHTML = button.getAttribute('data-hidden-text');
    }
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

$("select#SelectDevice").change(function(){
	var selectDevice = $(this).val();
	if(!selectDevice){
		$("select#selectSuits").attr("disabled","disabled").empty().append("<option>Select Device First</option>");
		$("select#selectFiles").attr("disabled","disabled").empty().append("<option>Select SuitName First</option>");
		return;
	}
	$.ajax({
		type: "POST",
		url: "FetchTestReports",
		dataType : "html",
		data: {device:selectDevice},
		success: function (data)
		{
			$("select#selectSuits").removeAttr("disabled").empty().append(data);	
		}
   	 });
});

$("select#selectSuits").change(function(){
	var selectSuits = $(this).val();
	var selectDevice = $("select#SelectDevice").val();
	if(!selectSuits){
		$("select#selectFiles").attr("disabled","disabled").empty().append("<option>Select SuitName First</option>");
		return;
	}
	$.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "text",
		data: {method:'getFileCombo',selectSuits:selectSuits,selectDevice:selectDevice},
		success: function (data)
		{
			$("select#selectFiles").removeAttr("disabled").empty().append(data);	
		}
  	 });
});

function getData()
{	
	
var browser= $('select#selectBrowser option:selected').text();
var suitName= $('select#selectSuits option:selected').text();
var param1= "getHtmlReportData";
var param2 = "getPassFailData";
var param3 = "getFileCombo";

   $.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "html",
		data: {browser:browser, suitName:suitName, method:param1},
		success: function(data)
		{
			$("#htmlReportTable").empty().html(data);	
		}
  	     }); 

   $.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "html",
		data: {browser:browser, method:param2},
		success: function(data)
		{
			$("#passFailTable").empty().html(data);	
		}
  	   });/* 
   $.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "text",
		data: {method:param3},
		success: function (data)
		{
			$("select#selectFiles").empty().append(data);	
		}
  	 }); */
    
}
		   
</script>

  <jsp:include page="footer.html"></jsp:include>