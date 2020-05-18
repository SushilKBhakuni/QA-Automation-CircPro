<%@page import="com.autofusion.constants.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <jsp:useBean id="mui" class="com.autofusion.util.ReadXlsForUI" scope="application"></jsp:useBean>
<jsp:include page="header.jsp"/>
<body>
 <%  
    String testBasePath = "";
	
 	if(session.getAttribute("testCaseBasePath") == null){
		response.sendRedirect("index.jsp");
	}else{
		testBasePath = (String) session.getAttribute("testCaseBasePath");
	}
 	 String device =request.getParameter("device"); 
  %>
 <div class="body-container">
  <div class="layout">
  
  <h2 class="bdrbtm">Test Suite</h2>
		  
		<form method="post" action="ExecuteTestCases" class="fullwidth" name="mblForm" id="mblForm">
		 <table width="100%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	    <tr> 
	       <th width="25">&nbsp;</th>
	       <th>Sno.</th>
	       <th>Test Suites</th>
	       <th><%= Constants.COL_HEAD_DESCRIPTION %></th>
<!-- 	       <th>Select Device</th> -->
<!-- 	       <th>Run On</th> -->
	       <th>Execute</th>
	       <th>Application Type</th>
	       <!-- h>Select Device</th-->
	        <th>Device attached</th>
	       <th>&nbsp;</th>
	    </tr>
	  
    <%    
    
		String testCaseBasePath = session.getAttribute("testCaseBasePath").toString();
		if(testCaseBasePath.equals("") || testCaseBasePath == null){
			response.sendRedirect("setting.jsp?error=1");
		}
          
         
    	 	ArrayList suitXls = mui.readSuitXls(testCaseBasePath, device);
    	 	String machineCombo = mui.getMachineNameSuitXls(testBasePath, device);
    	 	//String machineCombo = comboString.split("##")[0];
    	 	
    	 	//String envCombo = comboString.split("##")[1];
    	 	 String envCombo = mui.getEnvSuitXls(testBasePath, device); 

    	 try{
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
	    	 	
	    	 	out.print("<tr class='info '>");
	    	 	out.print("<td class='accordianimg' ><img src='images/plusicon.png' id='plus"+i+"' width='15' height='15' onclick=expand("+i+")><img id='minus"+i+"' src='images/minus-icon.png' width='15' height='15' onclick=collapse('"+i+"') style='display:none'></td>");
	    	 	out.print("<td align='center' >"+i+"</td>");
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
	    	 			out.print("<td align='center' ><input class='frmrow' type='checkbox' "+rm+" name='TS"+p+"_suitName' id='"+sName+"' value='"+sName+"'></td>");
	    	 		 }
	    	 		 if(cnt <= 2)
	    	        	out.print("<td>"+listData+"</td>");
	    	 		 
	    	        cnt++;
		    	}//<input type='checkbox' checked  name='TS"+p+"_mblWeb' id='"+sName+"_mblWeb' value='mblWeb'>Mobile Web</label>
	    	 	out.print("<td valign='middle' align='center' >"+
		    	"<select class='form-controlSelect' name='"+sName+"_platform' id='"+sName+"_platform'><option value='android'>Android</option><option value='ios'>iOS</option></td>");
	    	 	out.print("<td align='center' valign='middle'>"+
	    	 	"<select class='form-controlSelect' name='TS"+sName+"_ip' id='TS"+sName+"_ip'><option>Select Machine</option>"+machineCombo+"</select></td>");
	    	 	
	    	 	//out.print("<td valign='middle' align='center'><label class='checkbox headlbl'><label class='checkbox headlbl'><select name='selMblVersion'><option value=''>Select Version</option><option value='4.0'>4.0</option><option value='4.1'>4.1</option></select></td>");
	    	 	out.print("<td class='accordianimg' colspan=2 align='center'><img valign='middle' src='images/playicon.png' width='20' height='20' id='play_"+sName+"' title='Run Now' onclick=runNow('"+sName+"')> "+ 
	    	 			   " <img width='20' height='20' src='images/loading.gif' id='runningImg"+sName+"' title='Abort Now' style='display:none' onclick=stopScript('"+sName+"')> "+
	    	 			   "<a href='file:///D:/TestingDevlopment/Autospy/POCLevel2/report/window/index.html' target='_blank'><img width='20' height='20' src='images/report.png' id='reportImg_"+sName+"' "+
	    	 			   " title='Open Report' style='display:none'></a></td>");
	    	 	
	    	 	 
	    	 	
	    	 	out.print("</tr>");
	    	 	out.print("<tr>");
	    	 	
	    	 	p++;
	    	 	ArrayList testCaseXls = mui.readTestCaseXls(testCaseBasePath+File.separator+device, sName);
		  		Iterator itr3 = testCaseXls.iterator();String testType = "";
		  		out.print("<td colspan=8 style='display:none' id='tblTS"+i+"' class='whitebg'>");
		  		out.print("<table width='100%' class='tableinner' cellspacing='0' cellpading='0'><tr class='warning'>");
	    	 	if(testCaseXls.size() > 0){
		   	 			out.print("<th bgcolor='#fcf8e3'>SNo.</th>");
						out.print("<th bgcolor='#fcf8e3'>"+Constants.COL_HEAD_TCID+"</th>");
						out.print("<th bgcolor='#fcf8e3'>"+Constants.COL_HEAD_DESCRIPTION+"</th>");
						out.print("<th bgcolor='#fcf8e3' align='center'><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAll' id='"+sName+"selectAll' value='' onclick=selectAll(this,'"+sName+"','_testCaseId') title='Click here to select all'> Execute All</label></th>");
						out.print("<th bgcolor='#fcf8e3' align='center'><label class='checkbox headlbl'><input type='checkbox' name='"+sName+"selectAllData' id='"+sName+"selectAllData' value='' onclick=selectAll(this,'"+sName+"','_testCaseDataId') title='Click here to select all'> "+Constants.COL_HEAD_DATA_DRIVEN+"</label></th><th bgcolor='#fcf8e3' colspan=2>&nbsp;</th></tr>");
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
		  						if(cnt2 == 5){
		  				    	     testType =  testData;
		  				    	     out.print("<input type='hidden' name ='hPackageName_"+sName+"' id='hPackageName_"+sName+"' value='"+testType+"'/>");
		  				    	}
		  						if(cnt2 == 6){
		  				    	     testType =  testData;
		  				    		out.print("<input type='hidden' name ='hClassName_"+sName+"' id='hClassName_"+sName+"' value='"+testType+"'/>");
		  						}
		  						if(cnt2 < 3)
		  							out.print("<td>"+testData+"</td>");
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
		  	    	 	
		  	    	 	
		  	    	 	out.print("<tr></table>");
		  			}else{
		  				out.print("<td colspan=7> No test cases in this suits</td>");
		  	    	 	out.print("<tr></table>");
		  			}
	    	 	i++;  //suit counter
    	    }
         }catch(Exception e){
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
			         <input type="text" name="thread1" id="thread1" value="" placeholder="Batch 1">
			  		 <select name='envBatchExecution' id='envBatchExecution'><option value='android'>Android</option><option value='ios'>iOS</option></select> 
	    	 		 <select name='ipBatchExecution' id='ipBatchExecution'><option>Select Machine</option><%= machineCombo %></select>
	    	 	 </div>
			  		 </td>
			  		 </tr>
			  		 </table>
			       
     </div>      
	
 <div class="fullwidth">
   <input type="button" name="btnRunSuites" class="button"  id="btnRunSuites" value="Run selected suits" onclick="runSelectedSuites()">
   <!-- input type="button" name="back" id="back" class="btn btn-primary"  value="Back" onclick="window.history.back()"-->
   <input type="hidden" name="hTestCaseBasePath" id="hTestCaseBasePath" value="<%= session.getAttribute("testCaseBasePath").toString() %>">
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
	
}

function collapse(count){
	document.getElementById('plus'+count).style.display='';
	document.getElementById('minus'+count).style.display='none';
	document.getElementById('tblTS'+count).style.display='none';
	
}

function disableButton(){
	document.getElementById('btnRunSuites').disabled=true; 
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

$(function () {
	/* $('#myTab a.active').tab('show');
   	$('#myTab a').click(function (e) {
    	  e.preventDefault();
    	  $(this).tab('show');
    	}); */
    	$("#desktop").removeClass("active");    	
	   $("#dashboard").removeClass("active");
       $("#web").removeClass("active");
       $("#manage").removeClass("active");
       $("#setting").removeClass("active");
	   $("#mobile").addClass("active");
   })
   
   function runSelectedSuites(){
	
	  if(!confirm("Are you sure to run the All selected test suites?")){
		  return false;
	  }else{
		  disableButton();
	  }
	
		var form = $('#mblForm');
		document.getElementById("runSuite").value="All";

		
		
		$.ajax({
				type: "POST",
				url: "ExecuteTestCases",
				data: form.serialize(),
				success: function (data) {
				//$('#result').attr("value",result);
				$("p:first").html("").fadeIn("slow");
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

   function runNow(selSuiteName){
		
		document.getElementById("play_"+selSuiteName).style.display = "none";
		document.getElementById("runningImg"+selSuiteName).style.display = "";
		document.getElementById("reportImg_"+selSuiteName).style.display = 'none';
	 
		var form = $('#mblForm');
		document.getElementById("runSuite").value=selSuiteName;
		$.ajax({
			type: "POST",
			url: "ExecuteTestCases",
			data: form.serialize(),
			success: function (data) {
			var result=data;
			// $("p:first").addClass("alert alert-success");						
			 //$("p:first").html("Execution complete").fadeIn("slow");
			document.getElementById("play_"+selSuiteName).style.display = '';
			document.getElementById("runningImg"+selSuiteName).style.display = 'none';
			$('#result').attr("value",result);
		
			},
			error :function (XMLHttpRequest,errorThrown){
				//alert("some error occured");
			}
	  	 });
		
		/* 
		var intervalId = setInterval(function() {
			$.ajax({
				type: "POST",
				url: "FetchReport",
				data: form.serialize(),
				success: function (data) {
					if(data == "success"){
						alert(data);
						document.getElementById("reportImg").style.display = '';
						document.getElementById("play").style.display = '';
						document.getElementById("errorImg").style.display = 'none';
						 $("p:first").addClass("alert alert-success");						
						 $("p:first").html("Test Completed").fadeIn("slow");
						clearInterval(intervalId);
					}
				}
	  	 	});
		}, 10000); */
		
	}
	
function stopScript(selSuiteName){
	
	if(!confirm("Are you sure to stop the script?")){
		return false;
	}
	
	var form = $('#mblForm');
	
	document.getElementById("runSuite").value=selSuiteName;
	
	$.ajax({
		type: "POST",
		url: "ExecuteTestCases?k=kill",
		data: form.serialize(),
		success: function (data) {
			//if(data == "success"){
				
				document.getElementById("play_"+selSuiteName).style.display = "";
				document.getElementById("runningImg"+selSuiteName).style.display = "none";
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

</script>
<jsp:include page="footer.html"></jsp:include>