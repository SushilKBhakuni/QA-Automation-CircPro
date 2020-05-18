<%@page import="java.io.IOException"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <jsp:useBean id="ui" class="com.autofusion.util.ReadXlsForUI" scope="application"></jsp:useBean>
 
<jsp:include page="header.html"/>
<div class="tabcontainer">
	<div class="row-fluid">
		<ul class="nav nav-tabs" id="myTab">
		  <li class="active"><a href="#home">Test Suits</a></li>
		  <li><a href="#advanceSetting">Advance Setting</a></li>
		  <li><a href="#messages">Web</a></li>
		  <li><a href="#settings">Desktop</a></li>
		</ul>  
<div class="tab-content">
  <div class="container" id="home" >
	
	<form method="post" action="ExecuteTestCases" class="row-fluid" id="testDetailForm" name="testDetailForm">
    
	 <%  String device = "web";//request.getParameter("runTestFor"); %>   
 	<table width="100%" align="center" class="table table-bordered">
 	
    <tr> 
       <th width="25">&nbsp;</th>
       <th>Sno.</th>
       <th>Test Suites</th>
       <th><%= Constants.COL_HEAD_DESCRIPTION %></th>
       <th>Execute</th>
    <% if(device.equalsIgnoreCase("Web")){ %>   
       <th>Browser</th>
       <th>Run On</th>
       <th>Action</th>
    <%}else{%>   
       <th>&nbsp;</th>
       <th>&nbsp;</th>
    <%} %>   
    </tr>
  
    <%    
         //String testSuitPath = request.getParameter("testSuitFilePath");
    
//         String testCaseBasePath = request.getParameter("testCaseBasePath");
         String testCaseBasePath = "C:Users//nitin.singh.SYNAPSE//Desktop//QaPractice//POC";
         session.setAttribute("sTestCaseBasePath", testCaseBasePath) ;
         try{
    	 	ArrayList suitXls = ui.readSuitXls(testCaseBasePath, device);
            if(suitXls.size() == 0){
            	response.sendRedirect("index.jsp");
            	 
            }
	         Iterator itr = suitXls.iterator();
	         
	         String sName = "";
	         
	         int i = 1;
	         int p = 2;
	    	 while (itr.hasNext()) {
	    		ArrayList innerList =(ArrayList) itr.next();
	    	 	Iterator itr2 = innerList.iterator();
	    	 	
	    	 	out.print("<tr class='info'>");
	    	 	out.print("<td class='accordianimg'><img src='img/Add-icon.png' id='plus"+i+"' width='15' height='15' onclick=expand("+i+")><img id='minus"+i+"' src='img/minus-icon.png' width='15' height='15' onclick=collapse('"+i+"') style='display:none'></td>");
	    	 	out.print("<td>"+i+"</td>");
	    	 	int cnt = 1;
	    	 	String ie = "";String ff = ""; String chr = "";String rm = "";
	    	 	while (itr2.hasNext()) {
	    	 		
	    	 		String listData = itr2.next().toString();
	    	 		
	    	 		if(cnt == 1){
	    	 			 sName = listData ;
	    	 		}
	    	 		 
	    	 		 if(cnt == 3){
	    	 			if(listData.equalsIgnoreCase("Y")) 
	    	 				rm = "checked";
	    	 			out.print("<td><input type='checkbox' "+rm+" name='TS"+p+"_suitName' id='"+sName+"' value='"+sName+"'></td>");
	    	 		 }
	    	 		if(device.equalsIgnoreCase("Web")){
		    	 		 if(cnt == 4 && listData.equalsIgnoreCase("Y"))
		    	 			 ie = "checked";
		    	 		 if(cnt == 5 && listData.equalsIgnoreCase("Y"))
		    	 			ff = "checked";
		    	 		 if(cnt == 6 && listData.equalsIgnoreCase("Y")) 
		    	 			chr ="checked";
	    	 		}
	    	 		
	    	 		 if(cnt <= 2)
	    	        	out.print("<td>"+listData+"</td>");
	    	 		 
	    	        cnt++;
		    	}
	    	 	
	    	 	if(device.equalsIgnoreCase("Web")){
		    	 	out.print("<td><label class='checkbox headlbl'><input type='checkbox' "+ie+" name='TS"+p+"_browser' id='"+sName+"_ie' value='ie'>Internet Explorer</label>");
		    	 	out.print("<label class='checkbox headlbl'><input type='checkbox' "+ff+" name='TS"+p+"_browser' id='"+sName+"_firefox' value='Firefox'>Firefox</label>");
		    	 	out.print("<label class='checkbox headlbl'><input type='checkbox' "+chr+" name='TS"+p+"_browser' id='"+sName+"_crome' value='Chrome'>Chrome</label</td>");
		    	 	out.print("<td><select name='TS"+p+"_ip' id='TS"+p+"_ip'><option>localhost</option></select></td>");
		    	 	out.print("<td><input type='submit' name='btnRunCurrentSuites' class='btn btn-primary'  id='btnRunCurrentSuites' value='Run suits' onclick='setSuitName('"+sName+"')'></td>");
		    	 	//out.print("<td class='accordianimg'><img src='img/play.png' width='15' height='15' id='play' title='Run Now' onclick=runNow('"+sName+"')> "+ 
		    	 		//	   " <img width='15' height='15' src='img/error.png' id='error' title='Abort Now' style='display:none' > "+
		    	 			//   "<img width='15' height='15' src='img/report.png' id='error' title='Open Report' style='display:none' ></td>");
	    	 	}else{
	    	 		out.print("<td>&nbsp;</td>");
	    	 	}
		    	 
	    	 	
	    	 	out.print("</tr>");
	    	 	out.print("<tr>");
	    	 	
	    	 	p++;
	    	 	ArrayList testCaseXls = ui.readTestCaseXls(testCaseBasePath+"\\"+device, sName);
		  		Iterator itr3 = testCaseXls.iterator();
		  		out.print("<td colspan=8 style='display:none' id='tblTS"+i+"' >");
		  		out.print("<table width='100%' class='table table-bordered marginbtm0'><tr class='warning'>");
	    	 	if(testCaseXls.size() > 0){
		   	 			out.print("<td>SNo.</td>");
						out.print("<td>"+Constants.COL_HEAD_TCID+"</td>");
						out.print("<td>"+Constants.COL_HEAD_DESCRIPTION+"</td>");
						out.print("<td><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAll' id='"+sName+"selectAll' value='' onclick=selectAll(this,'"+sName+"','_testCaseId') title='Click here to select all'> Execute All</label></td>");
						out.print("<td><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAllData' id='"+sName+"selectAllData' value='' onclick=selectAll(this,'"+sName+"','_testCaseDataId') title='Click here to select all'> "+Constants.COL_HEAD_DATA_DRIVEN+"</label></td><td colspan=2>&nbsp;</td></tr>");
						String tid = "" ;
						int j=1;
						int tci = 2;
						boolean chkAllFlag = true;
						boolean chkAllFlagDd = true;
	    	 		while (itr3.hasNext()) {
	    	 			
	    	 			  	ArrayList innerTestCaseList =(ArrayList) itr3.next();
		  					Iterator itr4 = innerTestCaseList.iterator();
		  					
		  					out.print("<tr>");
		  					out.print("<td>"+j+"</td>");
		  					int cnt2 = 1;
		  					while (itr4.hasNext()){
		  						String testData= itr4.next().toString();
		  						if(cnt2 == 1){  //TestCase
		  							tid = testData; 
		  						}
		  						if(cnt2 == 3){  //RunMode
		  							String chk = "";
		  							if(testData.equalsIgnoreCase("Y")){
		  								chk = "checked";
		  							}else{
		  								chkAllFlag = false;
		  							}
		  							out.print("<td><input type='checkbox' "+chk+" name='"+sName+"_testCaseId"+tci+"' id='"+sName+"_"+tid+"' value='"+sName+"' ></td>");
		  						}
		  						String chk = "";
		  						if(cnt2 == 4){//Data Driven
		  							if(testData.equalsIgnoreCase("Y"))
		  								chk = "checked";
		  							else{
		  								chkAllFlagDd = false;
		  							}
		  							
		  							out.print("<td><input type='checkbox' "+chk+" name='"+sName+"_testCaseDataId"+tci+"' id='"+sName+"_DD_"+tid+"' value='"+sName+"' ></td>");
		  						} 
		  		  	    	 	
		  						if(cnt2 < 3)
		  							out.print("<td>"+testData+"</td>");
		  		  	    		cnt2++; 
			  	    		}
		  					
		  					out.print("<td><a href='testStepUI.jsp?tsuit="+sName+"&tsid="+tid+"&device="+device+"' target='_blank'>Show test steps</a></td>");
		  					
		  					j++;tci++;
		  		    	}
		  	    	 	if(chkAllFlag){
		  	    	 		out.print("<script>$('#"+sName+"selectAll').attr('checked','checked');</script>");
		  	    	 	}
		  	    	 	if(chkAllFlagDd){
		  	    	 		out.print("<script>$('#"+sName+"selectAllData').attr('checked','checked');</script>");
		  	    	 	}
		  	    	 	
		  	    	 	
		  	    	 	out.print("<tr></table>");
		  			}else{
		  				out.print("<td colspan=8> No test cases in this suits</td>");
		  	    	 	out.print("<tr></table>");
		  			}
	    	 	
	    	 	   
	    	 		out.print("<tr id='multiTestTable"+i+"' style='display:none'>");
	    	 	   %>  <td colspan="8"> 
					   <table width="100%" align="center" class="table table-bordered" >
					     	  <tr class='info'><td colspan="3"><label class='checkbox headlbl'><input type="checkbox" name="chkRunMultipleTest" value="" id="chkRunMultipleTest"><b>Run Multiple Test Parallel</b></label></td></tr>
					          <tr class=''><td><input type="text" name="thread1" placeholder="Thread1" id="thread1" value="" style='width: 250px;'></td>
					  		  <td><input type="text" name="thread2" id="thread2" placeholder="Thread2" value="" style='width: 250px;'></td>
					  		  <td><input type="text" name="thread3" id="thread3" placeholder="Thread3" value="" style='width: 250px;'></td></tr>
					   </table>
					     </td>
					 </tr>
				   <%
	    	 	
	    	 	
	    	 	i++;  //suit counter
    	    }
         }catch(IOException e){
    		 RequestDispatcher reqDis = request.getRequestDispatcher("/error.jsp"); 
 			 reqDis.forward(request, response);	
    	 }
    %>
        
	 
 </table>
 </div>
 <div  class="tab-pane" id="advanceSetting">
 <div class="tab-pane">
 		<div class="row-fluid">
	       <table width="100%" align="center" class="table table-bordered" >
	       <tr class='info'><td colspan="2"><b>Run Multiple Suite Parallel</b></td>
	       </tr>
	          <tr class=''><td width='35%'>Thread 1</td>
	  		  <td><input type="text" name="thread1" id="thread1" value="" style='width: 440px;'></td></tr>
	  		  <tr class=''><td width='35%'>Thread 2</td>
	  		  <td><input type="text" name="thread2" id="thread2" value="" style='width: 440px;'></td></tr>
	  		  <tr class=''><td width='35%'>Thread 3</td>
	  		  <td><input type="text" name="thread3" id="thread3" value="" style='width: 440px;'></td></tr>
	       </table>
       </div>
 </div>

     
 <div class="row-fluid">
	       <table width="100%" align="center" class="table table-bordered" >
	       <tr class='info'><td colspan="2"><b>Reporting</b></td>
	       </tr>
	  		  <tr class=''><td width='35%'>Email</td><td><input type="text" name="to" id="to" value="" style='width: 440px;'></td></tr>
	       </table> 
        </div>
        
       
		<div class="row-fluid">
	       <table width="100%" align="center" class="table table-bordered">
	          <tr class='info'><td colspan="2"><b>Scheduling</b></td></tr>
	  		  <tr class=''><td width='35%'>Cron Format:</td><td><input type="text" name="to" id="to" value="" style='width: 440px;'></td></tr>
	       </table> 
        </div>
       
		<div class="row-fluid">
	       <table width="100%" align="center" class="table table-bordered">
	       <tr class='info'><td colspan="2"><b>Alert</b></td></tr>
	  		  <tr class=''><td width='35%'>Email</td><td><input type="text" name="to" id="to" value="" style='width: 440px;'></td></tr>
	       </table> 
        </div>
        
		<div class="row-fluid">
	       <table width="100%" align="center" class="table table-bordered">
	       <tr class='info'><td colspan="2"><b>Error Handling</b></td></tr>
	  		  <tr class=''><td width='35%'>Retry Failed Test case</td><td>&nbsp;</td></tr>
	  		  <tr class=''><td width='35%'>If test failed</td><td>&nbsp;</td></tr>
	       </table> 
        </div>
      </div>  
        
        
     <div class="container">
  		<table width="100%">
		   <tr>
				<td align="right">
				  <input type="submit" name="btnRunSuites" class="btn btn-primary"  id="btnRunSuites" value="Run selected suits" onclick="disableButton()">
				  <input type="button" name="back" id="back" class="btn btn-primary"  value="Back" onclick="window.history.back()">
				  <input type="hidden" name="hTestCaseBasePath" id="hTestCaseBasePath" value="<%= session.getAttribute("sTestCaseBasePath").toString() %>">
					<input type="hidden" name="hRunApplication" id="hRunApplication" value=<%= device%>>
					<input type="hidden" name="runSuite" id="runSuite" value="">
				 </td>
			</tr>
			<input type=button name="a" value="bbb" onclick="sendRequest()">
		 </table>
     </div>
 </form>   
  </div>
 </div>


</body>
<script>

function expand(count){
	document.getElementById('minus'+count).style.display='';
	document.getElementById('plus'+count).style.display='none';
	document.getElementById('tblTS'+count).style.display='';
	document.getElementById('multiTestTable'+count).style.display='';
}

function collapse(count){
	document.getElementById('plus'+count).style.display='';
	document.getElementById('minus'+count).style.display='none';
	document.getElementById('tblTS'+count).style.display='none';
	document.getElementById('multiTestTable'+count).style.display='none';
	
	
}

function disableButton(){
	//document.getElementById('btnRunSuites').disabled=true; 
}

function selectAll(obj,sName, str){
	
	$('input:checkbox[name^="'+sName+str+'"]').each(function(){
		if(obj.checked)
			$(this).attr('checked','checked');
		else
			$(this).removeAttr('checked');
	});
	
}

//TODO
function checkSelectAll(obj,sName){
	 
	flag=true;
	$('input:checkbox[name^="'+sName+'_testCaseId"]').each(function(){
		if(!obj.checked){
			flag=false;
		}
	});
	
	if(flag){
		$('#'+sName+'selectAll').attr('checked','checked');
	}else{
		$("#"+sName+"selectAll").removeAttr('checked');
	}
	
}

function displaySendMailTable(){
	 
	if(document.getElementById('sendMail').checked){
	   document.getElementById('sendMailTableId').style.display='';
    }else{
    	document.getElementById('sendMailTableId').style.display='none';
    }
}


//Creating a new XMLHttpRequest object
  var xmlhttp;
	
	if (window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
	}else{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	}


	function sendRequest(){
	
		var form = $('#testDetailForm');
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases",
			data: form.serialize(),
			success: function (data) {
			var result=data;
			alert(data);
			//$('#result').attr("value",result);
		
			}
	  	 });
     }

	function setSuitName(selSuiteName){
		document.getElementById("runSuite").value=selSuiteName;
	}
	
	function runNow(selSuiteName){
		
		document.getElementById("play").style.display = "none";
		document.getElementById("error").style.display = "";
	 
		var form = $('#testDetailForm');
		document.getElementById("runSuite").value=selSuiteName;
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases",
			data: form.serialize(),
			success: function (data) {
			var result=data;
			alert(data);
			document.getElementById("play").style.display = '';
			document.getElementById("error").style.display = 'none';
			//$('#result').attr("value",result);
		
			}
	  	 });
		
	}
	 
	$(function () {
	    $('#myTab a:last').tab('show');
	    
	    
	    $('#myTab a').click(function (e) {
	    	  e.preventDefault();
	    	  $(this).tab('show');
	    	})
	   })
</script>
