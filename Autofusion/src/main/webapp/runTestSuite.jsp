<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp"/>
<div class="tabcontainer">
	<div class="row-fluid">
	  <ul class="nav nav-tabs" id="myTab">
	  	<li ><a href="#home" class="active">Run Suite</a></li>
	</ul>
		<div class="tab-content" >
		     <div class="tab-pane" id="home">
		 		<form method="post" action="ExecuteTestCases" class="row-fluid" name="testDetailForm" id="testDetailForm">
				<div class="row-fluid">
			       <table width="100%" align="center" class="table table-bordered" >
			       <tr class='info'>
			       	<th><b>Suite Name</b></th>
			       	<th><b>Web</b></th>
			       	<th><b>Windows</b></th>
			       	<th><b>Mobile</b></th>
			       </tr>
			  	    <tr class=''><td>Smoke Test</td>
			  		  <td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value="" ></label></td>
			  		  <td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value=""></label></td>
			  		  <td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value=""></label></td>
			  		</tr>
			  		<tr class=''><td>Regression Test 2</td>
			  		  <td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value="" ></label></td>
			  		  <td><label class='checkbox headlbl'><input  disabled="true" type="checkbox" name="to" id="to" value="" ></label></td>
			  		  <td><label class='checkbox headlbl'><input   disabled="true" type="checkbox" name="to" id="to" value="" ></label></td>
			  		</tr>
			  		<tr class=''><td>Sanity Test</td>
			  			<td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value="" ></label></td>
			  			<td><label class='checkbox headlbl'><input type="checkbox" checked name="to" id="to" value="" ></label></td>
			  			<td><label class='checkbox headlbl'><input disabled="true" type="checkbox" name="to" id="to" value=""></label></td>
			  		</tr>
			       </table> 
		       </div>
		        <div class="row-fluid">
		  		<table width="100%">
				   <tr>
						<td align="right">
						  <input type="submit" name="btnRunSuites" class="btn btn-primary"  id="btnRunSuites" value="Run selected suits" onclick="disableButton()">
						 </td>
					</tr>
				</table>
			   </div>			  
		      </form>   
		     </div>
		</div>
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


function displaySendMailTable(){
	 
	if(document.getElementById('sendMail').checked){
	   document.getElementById('sendMailTableId').style.display='';
    }else{
    	document.getElementById('sendMailTableId').style.display='none';
    }
}



//Creating a new XMLHttpRequest object
var xmlhttp;
if (window.XMLHttpRequest)
{
	xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
}
else
{
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
}


function sendRequest(){
	
	/*xmlhttp.open("POST", "ExecuteTestCases", false);
	xmlhttp.send(null);
	
	//Execution blocked till server send the response
	if (xmlhttp.readyState == 4) { 
		if (xmlhttp.status == 200) 
		{
			alert("asdf");
			//document.getElementById("message").innerHTML = xmlhttp.responseText;
			alert(xmlhttp.responseText);
		} 
		else
		{
			alert('Something is wrong !!');
		}
	}*/
	
	
	alert("asdf");
	var form = $('#testDetailForm');
	 alert(form);
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
 
 
 
$(function () {
    $('#myTab a:last').tab('show');
    
    
    $('#myTab a').click(function (e) {
    	  e.preventDefault();
    	  $(this).tab('show');
    	})
   })

</script>


<jsp:include page="footer.html"></jsp:include>