<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="com.autofusion.util.Xlsx_Reader"%>
<%@page import="java.io.*"%>
<%@page import="java.lang.Object"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.util.Collection"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="org.apache.commons.io.filefilter.DirectoryFileFilter"%>
<%@page import="org.apache.commons.io.filefilter.RegexFileFilter"%>
<%@page import="com.autofusion.sql.SQLManager"%>
<%@page import="java.sql.*"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

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
Xlsx_Reader webTestSuitXls = new Xlsx_Reader(testBasePath+"/mobile/"+Constants.SUIT_FILE_NAME);
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
// 		String path = testBasePath+"//Archive//";
// 	File folder = new File(testBasePath+"//Archive");
// 	File[] listOfFiles = folder.listFiles();
// 	ArrayList<Object> files = new ArrayList<Object>(); 
// 	ArrayList<Object> files1 = new ArrayList<Object>();
// 	ArrayList<Object> files2 = new ArrayList<Object>();
// 	ArrayList<String> listOfFiles1 = new ArrayList<String>();
// 	ArrayList<String> listOfFiles2 = new ArrayList<String>();
// //	int length=listOfFiles.length;
// // 	if(length == 0) {
// // 		response.sendRedirect("error.jsp");
// // 	}
// //  if(length!=0) {
//     for (int i = 0; i <=(listOfFiles.length-1); i++) {
//       if (listOfFiles[i].isDirectory()) {
//     	  listOfFiles1.add(listOfFiles[i].getName());
//       }
//     }	
      
//         Collection filess = FileUtils.listFiles(
//                    new File(path), 
//         		   new RegexFileFilter("(?i).*index_.*"), 
//         		   DirectoryFileFilter.DIRECTORY);
        
//            for (Object obj : filess) {
//         	    files.add(obj);
//         	    }
           
//            Collection filess1 = FileUtils.listFiles(
//                    new File(path), 
//         		   new RegexFileFilter("(?i).*htmlXlsReport_.*"), 
//         		   DirectoryFileFilter.DIRECTORY);
        
//            for (Object obj : filess1) {
//         	    files1.add(obj);
//         	    }
           
//            Collection filess2 = FileUtils.listFiles(
//                    new File(path), 
//         		   new RegexFileFilter("(?i).*_TC-.*"), 
//         		   DirectoryFileFilter.DIRECTORY);
        
//            for (Object obj : filess2) {
//         	    files2.add(obj);
//         	    }
%>
<%
String path = testBasePath+"//Archive//";
File folder = new File(testBasePath+"//Archive");
File[] listOfFiles = folder.listFiles();
ArrayList<Object> files = new ArrayList<Object>(); 
ArrayList<Object> files1 = new ArrayList<Object>();
ArrayList<Object> files2 = new ArrayList<Object>();
ArrayList<String> listOfFiles1 = new ArrayList<String>();
ArrayList<String> listOfFiles2 = new ArrayList<String>();
//int length=listOfFiles.length;
//	if(length == 0) {
//		response.sendRedirect("error.jsp");
//	}
//if(length!=0) {
for (int i = 0; i <=(listOfFiles.length-1); i++) {
  if (listOfFiles[i].isDirectory()) {
	  listOfFiles1.add(listOfFiles[i].getName());
  }
}	
  
    Collection filess = FileUtils.listFiles(
               new File(path), 
    		   new RegexFileFilter("(?i).*index_.*"), 
    		   DirectoryFileFilter.DIRECTORY);
    
       for (Object obj : filess) {
    	    files.add(obj);
    	    }
       
       Collection filess1 = FileUtils.listFiles(
               new File(path), 
    		   new RegexFileFilter("(?i).*htmlXlsReport_.*"), 
    		   DirectoryFileFilter.DIRECTORY);
    
       for (Object obj : filess1) {
    	    files1.add(obj);
    	    }
       
       Collection filess2 = FileUtils.listFiles(
               new File(path), 
    		   new RegexFileFilter("(?i).*_TC-.*"), 
    		   DirectoryFileFilter.DIRECTORY);
    
       for (Object obj : filess2) {
    	    files2.add(obj);
    	    }
       
       //********************Retrieving the values from the DataBase*****************************************
      
//        String current_suitename="", TCID="", description="", status="", fail_totalcnt="", run_strttime="", run_endtime="";
//        Connection con = ConnectionManager.getConnection() ;
//  	   Statement stmt = con.createStatement();
//  	   ResultSet rs = stmt.executeQuery("Select * from index_reportdata");

//  	    while(rs.next())
// 	    {
//  		current_suitename= rs.getString(2);
//  		TCID= rs.getString(3);
//  		description= rs.getString(4);
//  		status= rs.getString(5);
//  		fail_totalcnt= rs.getString(6);
//  		run_strttime= rs.getString(7);
//  		run_endtime= rs.getString(8);
 		
// //  		System.out.println(current_suitename);
// //  		System.out.println(TCID);
// //  		System.out.println(description);
// //  		System.out.println(status);
// //  		System.out.println(fail_totalcnt);
// //  		System.out.println(run_strttime);
// //  		System.out.println(run_endtime);
// 	    }
	   //*************************************************************
%>

<style>
.nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #fff;
    border-color: #ddd #ddd transparent;
    border-image: none;
    border-style: solid;
    border-width: 1px;
    color: #f5f6f7;
    cursor: default;
}
.innerbtmrow{ background: #d4dadc;}
.frmrow select {
    border: 1px solid #ccc;
    display: inline-block;
    height: 32px;
    margin-right: 10px;
    padding: 5px 15px;
    width: 150px;
}
ul.links {
    float: left;
    width: 100%;
    padding:10px 20px;
    margin: 0;
    list-style-type: none;
}

a.link1, a.link2, a.link3 {
    background-color: #37b5e5;
    border-right: 1px solid white;
    color: white;
    float: left;
    font-size: 15px;
    height: 2.5em;
    padding: 0.2em 0.6em;
    text-decoration: none;
    width: 9.6em;
}
.nav-tabs .status1 {
    background: yellow;
}
 .nav-tabs > li.active > a:hover 
 { 
/*  background-color: #0095ff;  */
 background-color: #37b5e5;
 } 
 .nav-tabs > li.active > a 
 { 
 background-color: #37b5e5; 
 } 
 .nav-tabs > li.active > a:focus
 { 
/*  background-color: #0095ff;  */
background-color: #37b5e5; 
 } 

li.link1, li.link2, li.link3 {
    display: inline;
}

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
    height: 35px;
    width: 110px;
    background:#37b5e5;
    text-align: center;
    padding:10px 20px;
    font-size:14px;
    border:0px;
    text-decoration: none;
    list-style:none;
    margin-top:15px;
}
.button
 {
    border-radius:2px; 
    cursor:pointer; 
    color:#fff;
    box-shadow:1px 1px 1px #0E6696;
    float:right;
    display: block;
    height: 35px;
    width: 110px;
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
 width:1015px; 
 float:left;
 color:#fff; 
 font-size:15px;
  line-height:32px;
 }
 .accordianheader2
{
 padding:3px;
 background:#37b5e5; 
 width:510px; 
 float:left;
 color:#fff; 
 font-size:15px;
  line-height:32px;
 }
 .accordianheader3
{
 padding:3px;
 background:#37b5e5; 
 width:1075px; 
 float:left;
 color:#fff; 
 font-size:25px;
  line-height:32px;
 }

.left-box1, .right-box1{border-radius:5px;background:#f3f3f3;box-shadow:0 0px 8px 5px rgba(0, 0, 0, 0.2);padding:10px;min-height:335px;margin-top:20px;}
.left-box1{ width:530px; float:left;}
.right-box1{ width:470px; float:right;}
</style>
<div class="body-container">
<div class="layout"> 

<ul class="nav nav-tabs">
<!-- <li class="active" id="link1"><a href="#" class="link1" target="_parent">Show Reports</a></li>  -->
<li id="link1"><a href="#" class="link1" target="_parent">Show Reports</a></li> 
<!-- <li class="link1"><a href="#" class="link1" target="_parent">Show Reports</a></li>  -->
<li class="link2"><a href="#" class="link2" target="_parent">Manage Reports</a></li> 
<li class="link3"><a href="#" class="link3" target="_parent">Archive History</a></li>
</ul>

<div id="div1">
  <h2 class="bdrbtm">Test Reports</h2>
  <p  class="text-warning" id="result"></p>
<!--    <div class="accordianouter">  -->
<!--      <div class="contentbox">  -->
   <div class="fullwidth"  >
    <form method="post" class="fullwidth" id="reportForm" name="reportForm">
 <!--   <div class="frmrow"> -->
 <div class="fullwidth innerbtmrow" >
<!-- 		 	<div class="padding30 fl width90" > -->
		 	<div class="fl width90" >
		 	<div class="frmrow marginbtm20">
    <table width="100%" style="bordercolor : #CEE3F6;" align="center" class="" cellspacing='0' cellpading='0'>
				<tr>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="50" height="2" class="">
					
						<label class="labeltxt width80">Device</label>
					</td>
					<td align="left" height="2" class=""><select
						name="SelectDevice" id="SelectDevice">
							<option value="">Select a Device</option>
							<option value="0">Web</option>
							<option value="1">Mobile</option>
							<option value="2">Window</option>
					</select></td>
					<td width="150" height="2" class="">
<!-- 						<h4>SuitName</h4> -->
                    <label class="labeltxt width80">SuitName</label>
						
					</td>
					<td align="left" height="2" class=""><select
						name="SelectSuits" id="selectSuits" disabled="disabled">
							<option>Select Device First</option>
					</select></td>
					<td width="100"  height="2" class="">
					<label class="labeltxt width80">Files</label>
						
					</td>
					<td align="left" height="2" class=""><select
						name="selectFiles" id="selectFiles" disabled="disabled">
							<option>Select SuitName First</option>
					</select></td>
					<td width="150" height="2" class="">
					<label class="labeltxt width80">Browsers</label>
					</td>
					<td align="left" height="2" class=""><select
						name="SelectBrowser" id="selectBrowser"><option>Firefox</option>
							<option>Internet Explorer</option>
							<option>Chrome</option></select></td>
					<td align="center" height="2" class="">
	                <div class="button" id="btnRunSuites" data-target=".accordianouter" onclick="getData()">ShowReport</div>
					</td>
				</tr>
			</table>
			 </div> </div> </div>
  <!-- </div> -->
   <div class="accordianouter" style="display:none;">
     
      <div class="contentbox">
      <div class="left-box1">
        <h5 class="accordianheader2"> TEST DETAIL SUMMARY </h5>
          <table width="80%" id="passFailTable" align="center" class="tableouter marginbtm20" cellspacing="0" cellpadding="0">
          </table>
        
       </div>
       
       <div class="right-box1">
       <div id="containerPop" style="min-width: 470px; height: 400px; margin: 0 auto"></div>
       </div>
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
<!--    </div> -->
<!--   </div> -->
   </div>
  </div>
  

<div id="div2" style="display:none;">
<!--     <div class="body-container"> -->
<!-- <div class="layout"> -->
<h2 class="bdrbtm">Manage Reports Section</h2>
<p  class="text-warning" id="result"></p>
 <div class="fullwidth"  >
 <form method="post" class="fullwidth" id="manageReportForm" name="manageReportForm">
 <div class="fullwidth innerbtmrow" >
 <div class="fl width90" >
		 	<div class="frmrow marginbtm20">
<table width="100%" style="bordercolor : #CEE3F6;" align="center" cellspacing='0' cellpading='0'>
				<tr>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="50" height="2" class="">
					
						<label class="labeltxt width80">Device</label>
					</td>
					<td align="left" height="2" class=""><select name="SelectDeviceName" id="SelectDeviceName">
							<option value="">Select a Device</option>
							<option value="0">Web</option>
							<option value="1">Mobile</option>
							<option value="2">Window</option>
					</select></td>
					
					<td width="150" height="2" class="">
<!-- 						<h4>SuitName</h4> -->
                    <label class="labeltxt width80">SuitName</label>
						
					</td>
					<td align="left" height="2" class=""><select name="SelectSuits" id="selectSuitsName" disabled="disabled">
							<option>Select Device First</option>
					</select></td>
					
					<td width="100"  height="2" class="">
					<label class="labeltxt width80">Files</label>
						
					</td>
					<td align="left" height="2" class=""><select name="selectFilesName" id="selectFilesName" disabled="disabled">
							<option>Select SuitName First</option>
					</select></td>
					
					<td width="150" height="2" class="">
					<label class="labeltxt width80">Browsers</label>
					</td>
					<td align="left" height="2" class=""><select name="SelectBrowserName" id="selectBrowserName">
					        <option>Firefox</option>
							<option>Internet Explorer</option>
							<option>Chrome</option></select></td>
						
						<td align="center" height="2" class="">
<!-- 					</td>	 -->
<!-- 					<td align="center" class="padding15 info"> -->
			<div class="manageButton" id="btnRunSuites" data-target=".accordianouter" onclick="getDisplayData1()">ShowLogs</div>
					</td>
				</tr>
</table>
  </div></div></div>
<!--     <div class="accordianouter" style="display:none;"> -->
<!-- <div class="accordianouter"> -->
<!--        <div class="contentbox"> -->
<!--         <h1 class="accordianheader3"><b><strong>List of the Reports</strong></b> </h1><br> -->
          <div id="test3">
          
          </div>
          
<!--           </div>  -->
<!--           </div>  -->
       </form> 	
<!-- </div> -->
<!-- </div>  -->
      </div>
</div>

<div id="div3" style="display:none;">
<!-- <div class="body-container"> -->
<!--   <div class="layout"> -->
  <h2 class="bdrbtm">Archive History Display:</h2>
  <% 
  for (int i = 0; i <=listOfFiles1.size(); i++) {
  	if(listOfFiles1.isEmpty())
  	{%>
  	<h4><p>Not Error!! But Archive the files first before selecting the Archive History tab...</p></h4>
  	 <% 
  	}
    }
  %>
  <p  class="text-warning" id="result"></p>
		<form method="post" class="fullwidth" id="testDetailForm" name="testDetailForm">
	 	<table width="100%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	 	<tr>
	 	<th>&nbsp;</th>
	 	<th>S No.</th>
	 	<th>File Name</th>
	 	</tr>
	    <% for (int i = 0; i < listOfFiles1.size(); i++) {
	    	File f  = new File(String.valueOf(files.get(i)));
		    File f1 = new File(String.valueOf(files1.get(i)));
		    File f2 = new File(String.valueOf(files2.get(i)));
	    	%>
	    <tr class="info"> 
	    <td class='accordianimg' align="center"><img src='images/plusicon.png' id='plus1<%=i%>' width='20' height='20' onclick="expand1('<%=i%>');" > <img id='minus1<%=i%>' src='images/minus-icon.png' width='20' height='20' onclick="collapse1('<%=i%>');"  style='display:none'>
	    </td>
	     <td align="center"><%= i+1+"."%></td>
	    <td align="center">
	    <%= listOfFiles1.get(i)%>
	   </td>
	    </tr>
	    <tr>
	    <td colspan=8 style='display:none' class='whitebg' id='tblTS1<%=i%>' >
	    <table width="100%" align="center" class="tableinner" cellspacing='0' cellpading='0'>
	     <tr class="warning">
			 <th width="100">SNo</th>
		     <th width="200">Reports Name</th>
		</tr>
	    <tr class="warning">
	    <td align="center">1.</td>
	    <td align="center">
	    	<% 
	    	out.print("<a target='_blank' href='"+"file:///"+String.valueOf(files.get(i))+"'>"+f.getName()+"</a>");
	    	%>
	    	</td>
	    	</tr>
	    	
	    	<tr class="warning">
	    <td align="center">2.</td>
	    <td align="center">
	    	<% 
	    	out.print(f1.getName());
	    	%>
	    	</td>
	    	</tr>
	    	
	    	<tr class="warning">
	    <td align="center">3.</td>
	    <td align="center">
	    	<% 
	    	out.print("<a target='_blank' href='"+"file:///"+String.valueOf(files2.get(i))+"'>"+f2.getName()+"</a>");
	    	%>
	    	</td>
	    	</tr>
	    	</table>
	  <% // } %>  
	    </td>
	    </tr>
	   <% } %>
	    </table>
	    </form>
<!-- 	    </div> -->
<!-- 	    </div> -->
</div>
</div>
</div>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script> -->
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->
<script type="text/javascript">
// $( document ).ready(function() {
//     $('#containerPop').highcharts({
//         chart: {
//             plotBackgroundColor: null,
//             plotBorderWidth: 1,//null,
//             plotShadow: false
//         },
//         title: {
//             text: 'TEST DETAIL SUMMARY'
//         },
//         tooltip: {
//             pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
//         },
//         plotOptions: {
//             pie: {
//                 allowPointSelect: true,
//                 cursor: 'pointer',
//                 dataLabels: {
//                     enabled: true,
//                     format: '<b>{point.name}</b>: {point.percentage:.1f} %',
//                     style: {
//                         color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
//                     }
//                 }
//             }
//         },
//         series: [{
//             type: 'pie',
//             name: 'Browser share',
//             data: [
//                 ['Pass',   48],
//                 ['Fail',       0],
//                 {
//                     name: 'Skipped',
//                     y: 0,
//                     sliced: true,
//                     selected: true
//                 },
//                 ['Total Executed Test Cases',   48]
//             ]
//         }]
//     });
//  });
 
 createPieChart(0, 0, 0);
 function createPieChart(pass, fail, skipped){
	 $('#containerPop').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: 1,//null,
	            plotShadow: false
	        },
	        title: {
	            text: 'TEST DETAIL SUMMARY'
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            data: [
	                ['Pass',   pass],
	                ['Fail',       fail],
	                {
	                    name: 'Skipped',
	                    y: skipped,
	                    sliced: true,
	                    selected: true
	                }
	               // ['Total Executed Test Cases',   (pass+fail+skipped)]
	            ]
	        }]
	    });
	 
 }
 
 function createNoPieChart(pass, fail, skipped) {
	 $('#containerPop').empty().append("No data for the pie chart!! Hence, Cannot be created... Sorry!! Get the data first.");
 }
 
$('#link1').click(function(){
	document.getElementById("div1").style.display = "block";
	document.getElementById("div2").style.display = "none";
	document.getElementById("div3").style.display = "none";
	// $("#link1").css("color","red");
	//$(".nav-tabs > li.active > a.link1").addClass('status1');
	$("#link1").css("color","black");
	$(".link2").css("color","white");
	$(".link3").css("color","white");
});

$('.link2').click(function(){
	document.getElementById("div1").style.display = "none";
	document.getElementById("div2").style.display = "block";
	document.getElementById("div3").style.display = "none";
	$("#link1").css("color","white");
	$(".link2").css("color","black");
	$(".link3").css("color","white");
	//$(".nav-tabs > li.active > a.link2").addClass('status1');
	
});

$('.link3').click(function(){
	document.getElementById("div1").style.display = "none";
	document.getElementById("div2").style.display = "none";
	document.getElementById("div3").style.display = "block";
	$("#link1").css("color","white");
	$(".link3").css("color","black");
	$(".link2").css("color","white");
});

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

function expand1(count){
	document.getElementById('minus1'+count).style.display='';
	document.getElementById('plus1'+count).style.display='none';
	document.getElementById('tblTS1'+count).style.display='';
}

function collapse1(count){
	document.getElementById('plus1'+count).style.display='';
	document.getElementById('minus1'+count).style.display='none';
	document.getElementById('tblTS1'+count).style.display='none';
}

$(function () {
	$("#desktop").removeClass("active");
	$("#dashboard").removeClass("active");
    $("#mobile").removeClass("active");
    $("#web").removeClass("active");
    $("#setting").removeClass("active");
    $("#manage").removeClass("active");
	$("#report").addClass("active");
});


//$("[id*=tc_]").each(function() {
	//$(this).click(function() {
		//$("#tc_1").click(function() {
	/*	function getReport1() 
        {	
		var param1 = "getHtmlReportDataFromDataBase";
		$.ajax({
			type: "POST",
			url: "GetLinkClickReportData",
			dataType : "html",
			data: {method:param1},
			success: function(data)
			{
				if(data) {
				$("#htmlReportTable").empty().html(data);	}
				else {
				$("#result").append("No Reports Data to display");	}
			}
	  	   }); 
	}*/
	// });


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

function getReport(crrntsuitname) // Not in use
{
	var browser= $('select#selectBrowser option:selected').text();
	// var suitName= $('select#selectSuits option:selected').text();
	// var param1= "getHtmlReportData";	
	var param1 = "getHtmlReportDataFromDataBase";
	$.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "html",
		data: {browser:browser, suitName:crrntsuitname, method:param1},
		success: function(data)
		{
			if(data) {
			$("#htmlReportTable").empty().html(data);	}
			else {
			$("#result").append("No Reports Data to display");	}
		}
  	     }); 
}

function getData()
{	
createPieChart(0, 0, 0);
var browser= $('select#selectBrowser option:selected').text();
var suitName= $('select#selectSuits option:selected').text();
var fileName= $('select#selectFiles option:selected').text();
var param1= "getHtmlReportData";
// var param1 = "getHtmlReportDataFromDataBase";
var param2 = "getPassFailData";
var param3 = "getFileCombo";
 // var param2 = "getPassFailDataFromDatabase";

    $.ajax({
	type: "POST",
	url: "GettingReportData",
	dataType : "html",
	data: {browser:browser, suitName:suitName, method:param2, fileName:fileName},
	success: function(data)
	{
		if(data) {
		$("#passFailTable").empty().html(data);	}
		else {
		$("#passFailTable").empty().append("No Reports Data to display");
		}
	}
	});
    
   $.ajax({
		type: "POST",
		url: "GettingReportData",
		dataType : "html",
		data: {browser:browser, suitName:suitName, method:param1, fileName:fileName},
		success: function(data)
		{
			if(data) {
			$("#htmlReportTable").empty().html(data);	}
			else {
			$("#result").append("No Reports Data to display");	}
		}
  	     }); 

   /* 
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
	alert("Do you want to delete all these selected log files!!!");
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

function removeTable(id) // Not in use but can be used in future
{
    var tbl = $(id);
    if(tbl) tbl.parentNode.removeChild(tbl);
}

function Delete(key, path, logFileName) { 
	alert("Do you really want to delete this log file!!!");
	var LogFile= logFileName;
	var Path= path;
	var buttonId = '#delete_'+key;
	//var minusImgId = '#minus'+key;
	var tableId = '#tblTS'+key;
		 $.ajax({
				type: "POST",
				url: "LogFileDelete",
				dataType : "text",
				data: {LogFile:LogFile, Path:Path},
				success: function (data)
				{
// 					$("p").addClass("alert alert-success");
// 				    $("#result").html("Setting save");
// 				    $("#result").append(data);
				}
		   });
		$(tableId).parent().remove(); // for deleting the expanded content as well
		$(buttonId).parent().parent().remove(); // for deleting the row
}

function Archive(key, path, logFileName) { 
	alert("Do you want to archive this log file!!!");
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
// 				 	$("p").addClass("alert alert-success");
// 				    $("#result").html("Setting save");
// 				    $("#result").append(data);
				}
		   });
}

// function getDisplayData1() { // called on the click of button
// 	var selectDevice = $("select#SelectDeviceName").val();
// 	var selectSuits = $("select#selectSuitsName").val();
// 	var selectFiles = $("select#selectFilesName").val();

// 	// if(selectDevice && selectSuits && selectFiles) {
// var browser= $('select#selectBrowserName option:selected').text();
// var suitName= $('select#selectSuitsName option:selected').text();
// var param1= "getDisplayData";

// $.ajax({
// 	type: "POST",
// 	url: "ManageReportServlet",
// 	dataType : "html",
// 	data: {browser:browser, suitName:suitName, method:param1},
// 	success: function(data)
// 	{
// 		$("#test3").empty().html(data);	
// 	}
// 	     }); //}
// // 	else {
// // 		$("#test3").empty().append("No Error!! But Select the dropdowns first, then click the button to get the list of reports!!");	
// // 	}
// }

//  function getData1() { // called on the click of button
// 	$('.link2').click(function(){
// 	var selectDevice = $("select#SelectDevice").val();
// 	var selectSuits = $("select#selectSuits").val();
// 	var selectFiles = $("select#selectFiles").val();

// 	//if(selectDevice && selectSuits && selectFiles) {
// var browser= $('select#selectBrowser option:selected').text();
// var suitName= $('select#selectSuits option:selected').text();
// var param1= "getDisplayData";

// $.ajax({
// 	type: "POST",
// 	url: "ManageReportServlet",
// 	dataType : "html",
// 	data: {browser:browser, suitName:suitName, method:param1},
// 	success: function(data)
// 	{
// 		$("#test2").empty().html(data);	
// 	}
// 	     }); //}
// // 	else {
// // 		$("#test2").empty().append("No Error!! But Select the dropdowns first, then click the button to get the list of reports!!");	
// // 	}
// });

	
</script>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script> -->
<script>
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