<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>

<%
  
 	if(session.getAttribute("testCaseBasePath") == null){
		response.sendRedirect("setting.jsp");
 	}
    String msg = ""; 
    if(request.getAttribute("message")!=null){
    	 msg =  request.getAttribute("message").toString();
    }
%>
<jsp:include page="header.html"/>
<p  class="text-warning" id="result"><%= msg %></p>
<div class="tabcontainer">
	<div class="row-fluid">
	  <ul class="nav nav-tabs" id="myTab">
	  	<li ><a href="#home" class="active">View Logs</a></li>
	</ul>
		<div class="tab-content" >
		     <div class="tab-pane" id="home">
		       <form method="post" action="AddTestSuite" class="row-fluid" name="addTestSuiteForm" id="addTestSuiteForm">
		 		<div class="row-fluid">
		 			<div class="span6"><a href="">Open log file</a>
					</div> 
		      </div>
		       </form>
		      
       	     </div>

<script type="text/javascript">

function showTxtAppRow(obj){
 
	if(obj.value=="appAfter"){
		document.getElementById("txtAppRow").style.display="";
	}else{
		document.getElementById("txtAppRow").style.display="none";
	}
	
}

function sub(){
	
	document.getElementById("uploadForm").action="Convert";
	document.getElementById("uploadForm").submit();
}

$(function () {
	    $('#myTab a:first').tab('show');
	    $('#myTab a').click(function (e) {
	    	  e.preventDefault();
	    	  $(this).tab('show');
	    	})
	   })
	   
	   function getSuiteList(formName, fieldName){
	
			var param = "getSuiteList";
			var form = $('#'+formName);
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param,
				data: form.serialize(),
				dataType:'html',
				success: function (data) {
				//alert(data);	
				//$("select[name=]").html(data);
				$("#"+fieldName).html(data);
				
			   }
			});

	   }

		function getTestCase(formName){    //get Test cases select combo 
			
			var param = "getTestCaseList";
			var form = $('#'+formName);
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param,
				data: form.serialize(),
				dataType:'html',
				success: function (data) {
				//alert(data);	
				$("#testCaseName").html(data);
			   }
			});
    }
		
		function getTestStep(formName){   //retrieved test steps from xls
			
			var param = "getTestSteps";
			var form = $('#'+formName);
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param,
				data: form.serialize(),
				dataType:'html',
				success: function (data) {
					
					$("#tblTestSteps").find("tr:gt(0)").remove();
					$("#tblTestSteps").append(data)
				 	document.getElementById("txtAppRow").value = document.getElementById("totalStepRows").value;
					document.getElementById("tblTestSteps").style.display = "";
					document.getElementById("tblAppTestSteps").style.display = "";
			   }
			});
		}
    
	   function enabledStepsRow(i){
		   $("#description_"+i).removeAttr('readonly');
		   $("#attribute_"+i).removeAttr('disabled');
		   $("#elementId_"+i).removeAttr('readonly');
		   $("#keyword_"+i).removeAttr('disabled');
		   $("#data_"+i).removeAttr('readonly');
		   $("#goToIfFail_"+i).removeAttr('readonly');
		   document.getElementById('editImg').style.display="none";
		   document.getElementById('editDoneImg').style.display="";
	   }
	   
	   function disabledStepsRow(i){
		   $("#description_"+i).attr('readonly','readonly');
		   $("#attribute_"+i).attr("disabled","disabled");
		   $("#elementId_"+i).attr('readonly','readonly');
		   $("#keyword_"+i).attr("disabled","disabled");
		   $("#data_"+i).attr('readonly','readonly');
		   $("#goToIfFail_"+i).attr('readonly','readonly');
		   document.getElementById('editImg').style.display="";
		   document.getElementById('editDoneImg').style.display="none";
	   }

	function funcAddTestCases(formName, param)  // add test suite/steps/cases    
	{
			var form = $('#'+formName);
			
			if(document.getElementById("totNewAppRow").value==0){
				alert("Please add new row to add");
				return false;
			}
			
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param,
				data: form.serialize(),
				success: function (data) {
					alert(data);
					 $("#result").remove();
					if(data == "success"){
						 $("p").addClass("alert alert-success");						
						 $("p").html("Record updated successfully").fadeIn("slow");
						 newRow=0;
						 document.getElementById("totNewAppRow").value= newRow;
						 $("#tblTestSteps :input").each(function() {
							   $(this).attr("readonly", true);
						 });
						 
					}else if(data == "error"){
						  $("p").addClass("alert alert-error");
						  $("p").html("Error").fadeIn("slow");
					}else if(data == "duplicate"){
						$("p").addClass("alert alert-error");
						  $("p").html("Suite name already exist.").fadeIn("slow");
					}
			   }
			
			});
		}
	   
	   var newRow = 0;

	   function addNewRow(){
	         	       
	        var $tr = $('#tblTestSteps').find("tr:last").clone();
	        $tr.find("input,select,tr").attr("name", function()
	        {
	               // break the field name and it's number into two parts
	               var parts = this.id.match(/(\D+)(\d+)$/);
	               var fieldName = parts[1]+ ++parts[2];
	               return fieldName;
	               // repeat for id attributes
	          }).attr("id", function()
              { 
                   var parts = this.id.match(/(\D+)(\d+)$/);
                   var fieldId = parts[1]+ ++parts[2];
                   //lastIdCount = parts[2];
                   return fieldId;
               });
	           
	           totalRow =  document.getElementById("totalStepRows").value;
	           totalRow++;
	           //alert(totalRow);
	          
	           $('#tblTestSteps').find("tbody tr:last").after($tr);
	           var a = "stepId_"+totalRow;
	           document.getElementById(a).value= "TS-"+totalRow;
	           document.getElementById("totalStepRows").value= totalRow;
	           newRow++;
	           document.getElementById("totNewAppRow").value= newRow;
	           enabledStepsRow(totalRow);
	   }
	   
	   
	   function deleteRmaRow(rowObj){
	    
	     var rowCount = $('#tblRma tr').length
	     var trid = $(rowObj).closest('tr').attr('id');
	     var inputArr = trid.split("_");
	     var rowCounter = inputArr[1];
	         // alert("deleteRmaRow "+rowCount);
	          
	     if(rowCount > 2){
	        $(rowObj).closest('tr[id]').remove();
	     }else{
	        $('#'+trid).find("input,select").val("");
	        document.getElementById("tblRmaMain").style.display = "none"; 
	        document.getElementById("txtRmaWarranty_"+rowCounter).style.backgroundColor="";
	        lastIdCount = 0;
	     }
	   }

	   function uploadFile(){
		   
			var form = $('#uploadForm');
			$.ajax({
				type: "POST",
				url: "Convert",
				data: form.serialize(),
				contentType: "multipart/form-data; ",
				cache: false,
	            success: function (data) {
					 $("#result").remove();
					if(data == "success"){
						 $("p").addClass("alert alert-success");						
						 $("p").html("Record updated successfully").fadeIn("slow");
					}else if(data == "error"){
						  $("p").addClass("alert alert-error");
						  $("p").html("Error").fadeIn("slow");
					}else if(data == "duplicate"){
						$("p").addClass("alert alert-error");
						  $("p").html("Suite name already exist.").fadeIn("slow");
					}
			   }
			});
		   
	   }
	   
	   function updateTestSteps(row){  //update single row

		    var param = "updateTestSteps";
			var form = $('#addTestStepForm');
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param+"&rowToUpdate="+row,
				data: form.serialize(),
				dataType:'html',
				success: function (data) {
					disabledStepsRow(row);
			   }
			});
	   }
	   </script>