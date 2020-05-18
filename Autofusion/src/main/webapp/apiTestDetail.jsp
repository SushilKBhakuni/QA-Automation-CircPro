<%@page import="java.io.IOException"%>
<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
    
<%
    String testBasePath = "";
	if(session.getAttribute("testCaseBasePath") == null){
		response.sendRedirect("index.jsp");
	}else{
		testBasePath = (String) session.getAttribute("testCaseBasePath");
	}

%>
<%  String device =request.getParameter("device"); 
%>
<jsp:useBean id="ui" class="com.autofusion.util.ReadXlsForUI" scope="application"></jsp:useBean>
<jsp:include page="header.jsp"/>
<div class="body-container">
  <div class="layout">
  
  <h2 class="bdrbtm">Test Suite</h2>
<p  class="text-warning" id="result"></p>
		<form method="post" action="ExecuteTestCases" class="fullwidth" id="testDetailForm" name="testDetailForm">
		    
	 	<table width="100%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	  <%
	   %>	
		    <tr> 
		       <th width="25">&nbsp;</th>
		       <th width="40">Sno.</th>
		       <th width="150">Test Suites</th>
		       <th><%= Constants.COL_HEAD_DESCRIPTION %></th>
		       <th width="90">Execute</th>
		       <th width="100">Env</th>   
		       <th width="100">Machine</th>
		       <th width="60">Run</th>
		</tr>
		  
		    <% String repPath = testBasePath+"//"+device+"//";
		    String machineCombo = ui.getMachineNameSuitXls(testBasePath, device);
    	 //	String machineCombo = comboString.split("##")[0];
    	 //	String envCombo = comboString.split("##")[1];
    	     String envCombo = ui.getEnvSuitXls(testBasePath, device); 
            
	      try{
	    	 	ArrayList suitXls = ui.readSuitXls(testBasePath, device);
	    	 	
	    	 	if(suitXls.size() == 0){
	            	response.sendRedirect("dashboard.jsp");
	            }
		         Iterator itr = suitXls.iterator();
		         String running = "";
		         String sName = "";
		         String testType = "";
		         int i = 1;
		         int p = 2;
		    	 while (itr.hasNext()) {
		    		ArrayList innerList =(ArrayList) itr.next();
		    	 	Iterator itr2 = innerList.iterator();
		    	 	
		    	 	out.print("<tr class='info'>");
		    	 	out.print("<td class='accordianimg'><img src='images/plusicon.png' id='plus"+i+"' width='20' height='20' onclick=expand("+i+")><img id='minus"+i+"' src='images/minus-icon.png' width='20' height='20' onclick=collapse('"+i+"') style='display:none'></td>");
		    	 	out.print("<td align='center'>"+i+"</td>");
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
		    	 			out.print("<td align='center' valign='middle'><input type='checkbox' "+rm+" name='TS"+p+"_suitName' id='"+sName+"' value='"+sName+"'></td>");
		    	 		 }
		    	 		if(device.equalsIgnoreCase("Web")){
			    	 		 if(cnt == 4 && listData.equalsIgnoreCase("Y"))
			    	 			 ie = "checked";
			    	 		 if(cnt == 5 && listData.equalsIgnoreCase("Y"))
			    	 			ff = "checked";
			    	 		 if(cnt == 6 && listData.equalsIgnoreCase("Y")) 
			    	 			chr ="checked";
		    	 		}
		    	 		
		    	 		
		    	 		if(cnt == 7){
		    	 			running = listData;
		    	 		}
		    	 		
		    	 		 if(cnt <= 2)
		    	        	out.print("<td valign='middle'>"+listData+"</td>");
		    	 		
		    	        
			    	     cnt++;
			    	}
		    	 	
		    	 	if(device.equalsIgnoreCase("api")){
		    	 		
		    	 	/* 	out.print("<td valign='middle' ><label class='checkbox headlbl'><input type='radio' name='TS"+p+"_env_option' id='"+sName+"_dev_option' value='env' onclick=showOption('enviroment','"+sName+"')>&nbsp;Multiple Env.</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio' checked name='TS"+p+"_env_option' id='"+sName+"_qa_option' value='browser' onclick=showOption('browser','"+sName+"')>&nbsp;Multiple Browser</label></td>");
			    	
		    	 		out.print("<td valign='middle' ><div id='chkEnv_"+sName+"'  style='display:none'><label class='checkbox headlbl'><input type='checkbox' name='TS"+p+"_env' id='"+sName+"_dev' value='dev'>&nbsp;DEV</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='checkbox'  name='TS"+p+"_env' id='"+sName+"_qa' value='qa'>&nbsp;QA</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='checkbox'  name='TS"+p+"_env' id='"+sName+"_ppe' value='ppe'>&nbsp;Staging</label></div>");
			    	 //	
			    	 	out.print("<div id='radEnv_"+sName+"' ><label class='checkbox headlbl'><input type='radio' checked name='TS"+p+"_env' id='"+sName+"_dev' value='dev'>&nbsp;DEV</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio'  name='TS"+p+"_env' id='"+sName+"_qa' value='qa'>&nbsp;QA</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio'  name='TS"+p+"_env' id='"+sName+"_ppe' value='ppe'>&nbsp;Staging</label></td>");
			    	 	
			    	 	out.print("<td valign='middle'  ><div id='chkBrowser_"+sName+"'><label class='checkbox headlbl'><input type='checkbox' "+ie+" name='TS"+p+"_browser' id='"+sName+"_ie' value='ie' title='Internet Explorer'>&nbsp;IE</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='checkbox' "+ff+" name='TS"+p+"_browser' id='"+sName+"_firefox' value='Firefox' title='Firefox'>&nbsp;FF</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='checkbox' "+chr+" name='TS"+p+"_browser' id='"+sName+"_crome' value='Chrome' title='Chrome'>&nbsp;Chrome</label></div>");
			    	 	
			    	 	out.print("<div id='radBrowser_"+sName+"'  style='display:none'><label class='checkbox headlbl'><input type='radio' "+ie+" name='TS"+p+"_browser' id='"+sName+"_ie' value='ie' title='Internet Explorer'>&nbsp;IE</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio' "+ff+" name='TS"+p+"_browser' id='"+sName+"_firefox' value='Firefox' title='Firefox'>&nbsp;FF</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio' "+chr+" name='TS"+p+"_browser' id='"+sName+"_crome' value='Chrome' title='Chrome'>&nbsp;Chrome</label>");
			    	 	out.print("<label class='checkbox headlbl'><input type='radio' "+chr+" name='TS"+p+"_browser' id='"+sName+"_mblcrome' value='MobileChrome' title='Chrome'>&nbsp;Mobile Chrome</label></td>");
			    	 	 */
			    	 	
			    	 	
				    	out.print("<td valign='middle' >"+
			    	 			"<select class='form-controlSelect' name='selEnvironment_"+sName+"' id='selEnvironment_"+sName+"'>"+
			    	 				"<option value=''>Select Env</option>"+envCombo+"</select>");
				    	 	
		/* 		    	out.print("<td valign='middle' >"+
				    	 			"<select class='form-controlSelect' name='selBrowser_"+sName+"Suite' id='selBrowser_"+sName+"Suite'>"+
				    	 			"<option value='Firefox'>Firefox</option><option value='Chrome'>Chrome</option>"+
				    	 			"<option value='ie'>Internet Explorer</option><option value='MobileChrome'>MobileChrome</option></select>");
		 */	    	 
				    	out.print("<td valign='middle' >"+
				    			"<select class='form-controlSelect' name='TS"+sName+"_ip' id='TS"+sName+"_ip'>"+machineCombo+"</select>");
			    	 	
			    	 	//out.print("<td><input type='submit' name='btnRunCurrentSuites' class='btn btn-primary'  id='btnRunCurrentSuites' value='Run suits' onclick='setSuitName('"+sName+"')'></td>");
			    	 	
			    	 	
			    	 	if(running.equals(Constants.EXE_RUNNING)){
			    	 		out.print("<td class='accordianimg' align='center' valign='middle'><img src='images/loading.png' width='20' height='20'  id='errorImg_"+sName+"' title='Run Now' onclick=runNow('"+sName+"')> "+ 
				    	 			   "</td>");
			    	 	}else if(running.equals(Constants.EXE_HANG)){
			    	 		out.print("<td class='accordianimg' align='center' valign='middle'><img src='images/playicon.png' width='20' height='20' id='play_"+sName+"' title='Run Now' onclick=runNow('"+sName+"')> "+ 
				    	 			   " <img width='15' height='15' src='images/loading.gif' id='errorImg_"+sName+"' title='Running..' style='display:none' > "+
				    	 			   "</td>");
			    	 	}else if(running.equals(Constants.EXE_INTERUPTED)){
			    	 		out.print("<td class='accordianimg' align='center' valign='middle'><img src='images/playicon.png' width='20' height='20'  id='play_"+sName+"' title='Run Now' onclick=runNow('"+sName+"')> "+ 
				    	 			   " <img width='15' height='15' src='images/loading.gif' id='errorImg_"+sName+"' title='Running..' style='display:none' > "+
				    	 			   "</td>");
			    	 	}else{
			    	 		out.print("<td class='accordianimg' align='center' valign='middle'><img src='images/playicon.png' width='20' height='20'  id='play_"+sName+"' title='Run Now' onclick=runNow('"+sName+"')> "+ 
			    	 			   " <img width='15' height='15' src='images/loading.gif' id='runningImg_"+sName+"' title='Click to stop script' style='display:none'  onclick=stopScript('"+sName+"')> "+
			    	 			   "</td>");
			    	 	}
			    	 	
		    	 	}else if(device.equalsIgnoreCase("mobile")){
		    	 		
		    	 		
		    	 	}else{
		    	 	
		    	 		//out.print("<td>&nbsp;</td>");
		    	 		//<input type='submit' name='btnRunCurrentSuites' class='btn btn-primary'  id='btnRunCurrentSuites' value='Run suits' onclick='setSuitName('"+sName+"')'>
		    	 		//out.print("<td>&nbsp;</td>");
		    	 		out.print("<td class='accordianimg' colspan=2 align='center' valign='middle'><img src='images/playicon.png' id='play_"+sName+"' width='20' height='20' title='Run Now' onclick=runNow('"+sName+"')> "+ 
			    	 			   " <img width='20' height='20' src='images/loading.gif' id='runningImg_"+sName+"' title='Abort Now' style='display:none' > "+
			    	 			   "<a href='file:///D:/TestingDevlopment/Autospy/POCLevel2/report/window/index.html' target='_blank'><img width='15' height='15' src='images/report.png' id='reportImg_"+sName+"' "+
			    	 			   " title='Open Report' style='display:none'></a></td>");
		    	 	}
			    	 
		    	 	
		    	 	out.print("</tr>");
		    	 	out.print("<tr>");
		    	 	
		    	 	p++;
		    	 	ArrayList testCaseXls = ui.readTestCaseXls(testBasePath+File.separator+device, sName);
			  		Iterator itr3 = testCaseXls.iterator();
			  		out.print("<td colspan=10 style='display:none' class='whitebg' id='tblTS"+i+"' >");
			  		out.print("<table width='100%' class='tableinner' cellspacing='0' cellpading='0'><tr class='warning'>");
		    	 	if(testCaseXls.size() > 0){
			   	 			out.print("<th width='30'>SNo.</th>");
							out.print("<th width='60'>"+Constants.COL_HEAD_TCID+"</th>");
							out.print("<th>"+Constants.COL_HEAD_DESCRIPTION+"</th>");
							out.print("<th width='150'><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAll' id='"+sName+"selectAll' value='' onclick=selectAll(this,'"+sName+"','_testCaseId') title='Click here to select all'> Execute All</label></th>");
							out.print("<th width='150'><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAllData' id='"+sName+"selectAllData' value='' onclick=selectAll(this,'"+sName+"','_testCaseDataId') title='Click here to select all'> "+Constants.COL_HEAD_DATA_DRIVEN+"</label></th><th width='150' align='center'>&nbsp;</th></tr>");
							String tid = "" ;
							int j=1;
							int tci = 2;
							boolean chkAllFlag = true;
							boolean chkAllFlagDd = true;
		    	 		while (itr3.hasNext()) {
		    	 			
		    	 			  	ArrayList innerTestCaseList =(ArrayList) itr3.next();
			  					Iterator itr4 = innerTestCaseList.iterator();
			  					
			  					out.print("<tr>");
			  					out.print("<td align='center'>"+j+"</td>");
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
			  							out.print("<td align='center'><input type='checkbox' "+chk+" name='"+sName+"_testCaseId"+tci+"' id='"+sName+"_"+tid+"' value='"+sName+"' ></td>");
			  						}
			  						String chk = "";
			  						if(cnt2 == 4){//Data Driven
			  							if(testData.equalsIgnoreCase("Y"))
			  								chk = "checked";
			  							else{
			  								chkAllFlagDd = false;
			  							}
			  							
			  							out.print("<td align='center'><input type='checkbox' "+chk+" name='"+sName+"_testCaseDataId"+tci+"' id='"+sName+"_DD_"+tid+"' value='"+sName+"' ></td>");
			  						} 
			  		  	    	 	
			  						if(cnt2 < 3)
			  							out.print("<td>"+testData+"</td>");
			  		  	    		
			  						if(cnt2 == 5){
			  				    	     testType =  testData;
			  				    	     out.print("<input type='hidden' name ='hPackageName_"+sName+"' id='hPackageName_"+sName+"' value='"+testType+"'/>");
			  				    	}
			  						if(cnt2 == 6){
			  				    	     testType =  testData;
			  				    		out.print("<input type='hidden' name ='hClassName_"+sName+"' id='hClassName_"+sName+"' value='"+testType+"'/>");
			  						}
			  						
			  						cnt2++;
			  		  	    		
			  						
										
				  	    		}
			  					
			  					out.print("<td align='center'><a href='testStepUI.jsp?tsuit="+sName+"&tsid="+tid+"&device="+device+"' target='_blank'>Show test steps</a></td>");
			  							
			  					j++;tci++;
			  		    	}
			  	    	 	if(chkAllFlag){
			  	    	 		out.print("<script>$('#"+sName+"selectAll').attr('checked','checked');</script>");
			  	    	 	}
			  	    	 	if(chkAllFlagDd){
			  	    	 		out.print("<script>$('#"+sName+"selectAllData').attr('checked','checked');</script>");
			  	    	 	}
			  	    	 	
			  	    	 	
			  	    	 	out.print("</tr>");
			  			}else{
			  				out.print("<td colspan=8> No test cases in this suits</td>");
			  	    	 	out.print("</tr>");
			  			}
		    	 		    	 	   
		    	 		out.print("<tr id='multiTestTable"+i+"' style='display:none'>");
		    	 	   %>  <!-- td colspan='6' class='innerbtmrow padding15' >
						     	
						     	
						     	<p> <label class='checkbox'><input type="checkbox" name="chkRunMultipleTest" id="chkRunMultipleTest" onclick="validateBrowser()"/><b>Execute tests in batch parallelly</b></label></p>
						     	<div class='frmrow'>
						         <input type="text" name="thread1" placeholder="Batch1" id="thread1"  value="TC-1,TC-2" />
						  		 <input type="text" name="thread2" id="thread2" placeholder="Batch2" value="TC-1,TC-3" />
						  		  <input type="text" name="thread3" id="thread3" placeholder="Batch3" value="TC-1,TC-4"/>
						     	  </div>
						     	  </td-->
						     	  
						     	  
						     	  </tr>
						   	  
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
	
	  <div class="fullwidth" >
	   	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
	 	
	    <tr > 
	       <th align='left'>
	       <p> <label class='checkbox'>
			     <!-- input type="checkbox" name="chkRunMultipleTest" id="chkRunMultipleTest" onclick="validateBrowser()"/-->
			       <b>Run Multiple Suite</b>
			       </label></p>
			       </th>
			       </tr>
			       <tr>
			       <td class="padding15 info">
			       <div class="frmrow">
			         <input type="text" name="thread1" id="thread1" value="" placeholder="Batch 1" readonly>
			         <% out.print("<select class='form-controlSelect' name='selBatchEnvironment' id='selBatchEnvironment'>"+
			    	 				"<option value=''>Select Env</option>"+envCombo+"</select>"); %>
			  		 <select class='form-controlSelect' name='selBatchBrowser' id='selBatchBrowser'>
				    	 	<option value='Firefox'>Firefox</option><option value='Chrome'>Chrome</option>
				    	 	<option value='ie'>Internet Explorer</option><option value='MobileChrome'>MobileChrome</option></select>
	    	 		 <select name='ipBatchExecution' id='ipBatchExecution'><option>Select Machine</option><%= machineCombo %></select>
	    	 	 </div>
			  		 </td>
			  		 </tr>
			  		 </table>
			       
     </div>      
	
	 
	 
	  <div class="fullwidth" style='display:none'>
	   	<table width="100%" align="center" class="tableouter marginbtm20" border='0' cellspacing='0' cellpading='0'>
	 	
	    <tr > 
	       <th align='left'>
	       <p> <label class='checkbox'>
			     <input type="checkbox" name="chkRunMultipleTest" id="chkRunMultipleTest" onclick="validateBrowser()"/>
			       <b>Run Multiple Suite Parallel</b>
			       </label></p>
			       </th>
			       </tr>
			       <tr>
			       <td class="padding15 info">
			       <div class="frmrow">
			      <input type="text" name="thread1" id="thread1" value="" placeholder="Batch 1">
			  		 <input type="text" name="thread2" id="thread2" value=""  placeholder="Batch 2">
			  		 <input type="text" name="thread3" id="thread3" value="" placeholder="Batch 3">
			  		 </div>
			  		 </td>
			  		 </tr>
			  		 </table>
			       
     </div>      
	  <div class="fullwidth">
  		
				  <input type="button" name="btnRunSuites" class="button"  id="btnRunSuites" value="Run selected suits" onclick="runSelectedSuites()">
				  <input type="hidden" name="hTestCaseBasePath" id="hTestCaseBasePath" value="<%= testBasePath%>">
					<input type="hidden" name="hRunApplication" id="hRunApplication" value=<%= device%>>
					<input type="hidden" name="runSuite" id="runSuite" value="">
				
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

function validateBrowser(){
	
	alert("User can select only one browser with multiple test case");
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


	function sendRequest(){
		//alert("asdf");
		async:false;
		var form = $('#testDetailForm');
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases",
			data: form.serialize(),
			success: function (data) {
				$("#result").remove();
				if(data == "success"){
					 $("p:first").addClass("alert alert-success");						
					 $("p:first").html("Record updated successfully").fadeIn("slow");
				}else if(data == "error"){
					  $("p:first").addClass("alert alert-error");
					  $("p:first").html("Error").fadeIn("slow");
				}else if(data == "duplicate"){
					$("p:first").addClass("alert alert-error");
					  $("p:first").html("Suite name already exist.").fadeIn("slow");
				}		
			}
	  	 });
     }

	function setSuitName(selSuiteName){
		//alert(selSuiteName);
		document.getElementById("runSuite").value=selSuiteName;
	}
	
	function runNow(selSuiteName){
		setSuitName(selSuiteName);
		
		if(!document.getElementById(selSuiteName).checked){
			alert("Please select the suite to execute");
			return false;
		}
		
		if(document.getElementById("selEnvironment_"+selSuiteName).value == ""){
			alert("Please Select the environment");
			return false;
		}
		
		document.getElementById("play_"+selSuiteName).style.display = "none";
		document.getElementById("runningImg_"+selSuiteName).style.display = "";
		//document.getElementById("reportImg_"+selSuiteName).style.display = 'none';
	 
		var form = $('#testDetailForm');
		//document.getElementById("runSuite"+selSuiteName).value=selSuiteName;
		//alert("ASFASFasdfasf");
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases",
			data: form.serialize(),
			success: function (data) {
			//	alert("asdfasd")
			var result=data;
			 $("p:first").addClass("alert alert-success");						
			document.getElementById("play_"+selSuiteName).style.display = "";
			document.getElementById("runningImg_"+selSuiteName).style.display = "none";
			clearInterval();
			$('#result').attr("value",result);
		
			}
	  	 });
		
		/* var intervalId = setInterval(function() {
			$.ajax({
				type: "POST",
				url: "FetchReport",
				data: form.serialize(),
				success: function (data) {
					if(data == "success"){
						//alert(data);
						//document.getElementById("reportImg_"+selSuiteName).style.display = '';
						document.getElementById("play_"+selSuiteName).style.display = '';
						document.getElementById("runningImg_"+selSuiteName).style.display = 'none';
						 $("p:first").addClass("alert alert-success");						
						 $("p:first").html("Test Completed").fadeIn("slow");
						clearInterval(intervalId);
					}
				}
	  	 	});
		}, 10000); */
		
	}

	function runSelectedSuites(){
		var form = $('#testDetailForm');
		
		  if(!confirm("Are you sure to run the All selected test suites?")){
			  return false;
		  }else{
			  disableButton();
		  }
		
		document.getElementById("runSuite").value="All";
		$.ajax({
				type: "POST",
				url: "ExecuteTestCases",
				data: form.serialize(),
				success: function (data) {
				$('#result').attr("value",result);
				$("p:first").html("Test is running...").fadeIn("slow");
			}
	  	 });
		
		
		var intervalId = setInterval(function() {
			$.ajax({
				type: "POST",
				url: "FetchReport",
				data: form.serialize(),
				success: function (data) {
					if(data == "success"){
						//alert(data);
						document.getElementById("reportImg").style.display = '';
						 $("p:first").addClass("alert alert-success");						
						clearInterval(intervalId);
					}						//alert("asdf"+data);

				}
	  	 	});
		}, 10000);

		
	}

	
	$(function () {
		  
		if(document.getElementById("hRunApplication").value=='window'){
			   $("#dashboard").removeClass("active");
		       $("#mobile").removeClass("active");
		       $("#manage").removeClass("active");
		       $("#setting").removeClass("active");
		       $("#web").removeClass("active");
		       $("#desktop").addClass("active");
		}else{
			   $("#desktop").removeClass("active");
			   $("#dashboard").removeClass("active");
		       $("#mobile").removeClass("active");
		       $("#manage").removeClass("active");
		       $("#setting").removeClass("active");
			   $("#web").addClass("active");
		}
				
		   
	   })

     function stopScript(selSuiteName){
		
		if(!confirm("Are you sure to stop the script?")){
			return false;
		}
		
		var form = $('#testDetailForm');
		
		document.getElementById("runSuite").value=selSuiteName;
		
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases?k=kill",
			data: form.serialize(),
			success: function (data) {
				//if(data == "success"){
					
					document.getElementById("play_"+selSuiteName).style.display = "";
					document.getElementById("runningImg_"+selSuiteName).style.display = "none";
					//document.getElementById("reportImg_"+selSuiteName).style.display = "";
	 
					
					/* if(hangButton){
						hangButton.style.display = "none";
					}
					if(interuptButton){
						interuptButton.style.display = "none";
					} */ 
					
					
					if(pollingInterval){
						clearInterval(pollingInterval);
						pollingInterval = undefined;
					}
					
					if(intervalId){
						clearInterval(intervalId);
						intervalId = undefined;
					}
					
				//}
			}
  	   });
	}
	
	
	function showOption(option, selSuiteName){
		
	  if(option == 'enviroment'){	
		  document.getElementById("chkEnv_"+selSuiteName).style.display = "";
		  document.getElementById("radEnv_"+selSuiteName).style.display = "none";
		  document.getElementById("chkBrowser_"+selSuiteName).style.display = "none";
		  document.getElementById("radBrowser_"+selSuiteName).style.display = "";
	  }else{
		  document.getElementById("chkEnv_"+selSuiteName).style.display = "none";
		  document.getElementById("radEnv_"+selSuiteName).style.display = "";
		  document.getElementById("chkBrowser_"+selSuiteName).style.display = "";
		  document.getElementById("radBrowser_"+selSuiteName).style.display = "none";
	  }
	}
		   
	   
</script>
<jsp:include page="footer.html"></jsp:include>