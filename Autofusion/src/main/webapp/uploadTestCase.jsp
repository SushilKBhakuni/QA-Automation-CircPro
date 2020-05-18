<%@page import="com.autofusion.bean.CommonUtility"%>
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
<jsp:include page="header.jsp"/>


<div class="body-container">
  <div class="layout">
  
  <h2 class="bdrbtm">Add Test Cases</h2>


<p  class="text-warning" id="result"><%= msg %></p>


	        <div class="tab-pane" id="upload">
	 		<form method="post" action="Convert" class="row-fluid" name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
	 		<table width="100%" align="center" class="table table-bordered" >
			       <tr class='info'><th>Upload IDE Recorded Test Cases</th></tr>
			    </table>
	 		<input type="hidden" value=<%=(String) session.getAttribute("testCaseBasePath") %> name="testCaseBasePath" id="testCaseBasePath">
			<div class="tabcontainer" style="float:left; width:98%">
				<div class="control-group">
				<label class="span4">File Name</label>
			    <input type="file" name="fileName" id="fileName" value="file">
			   </div>
				
			  <div class="control-group">
				<label class="radio">
					  <input type="radio" name="optionsRadios" id="newTestSuite" value="suite" checked>
					  Create New Suite
				</label>
				<label class="radio">
					  <input type="radio" name="optionsRadios"  id="newTestCase" value="testCase">
					  Create new test case
				</label>
			   </div>
			   <div class="control-group">
			    <input type="text" name="suiteName" id="suiteName" value="" class="span3 input-medium">
			   </div>
			   <div class="control-group">	
				<label class="radio">
					  <input type="radio" name="optionsRadios" id="appTestCase" value="appTestCase">
					  Append in test case
				</label>
					<input type="text" name="appendTestCase" class="span3 input-medium"  id="appendTestCase" value="">
			   </div>
			   <div class="control-group">
			   <label class="">
			   		<input type="text" class="span3 input-medium"  name="description" id="description" value=""  placeholder="Description">
			    </label>
			   </div>
			   <div class="control-group">
			   	<input type="button" name="btnUploadFile" id="btnUploadFile" value="Upload File" class="btn btn-primary"   onclick="uploadFile(this)">
			   	</div>
				<input type="hidden" name="selectedTab" id="selectedTab" value="UploadFile">
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

function uploadFile(){
	
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
		   $("#variableName_"+i).removeAttr('readonly');
		   $("#goToIfFail_"+i).removeAttr('readonly');
		   document.getElementById('editImg_'+i).style.display="none";
		   document.getElementById('editDoneImg_'+i).style.display="";
	   }
	   
	   function disabledStepsRow(i){
		   $("#description_"+i).attr('readonly','readonly');
		   $("#attribute_"+i).attr("disabled","disabled");
		   $("#elementId_"+i).attr('readonly','readonly');
		   $("#keyword_"+i).attr("disabled","disabled");
		   $("#data_"+i).attr('readonly','readonly');
		   $("#variableName_"+i).attr('readonly','readonly');
		   $("#goToIfFail_"+i).attr('readonly','readonly');
		   document.getElementById('editImg_'+i).style.display="";
		   document.getElementById('editDoneImg_'+i).style.display="none";
	   }

	function funcAddTestCases(formName, param, ms)  // add test suite/steps/cases    
	{
			var form = $('#'+formName);
			
			//if(document.getElementById("totNewAppRow").value ==0 && document.getElementById("selectedTab").value == "AddTestStep"){
				//alert("Please add new row to add");
				//return false;
		//	}
			
			$.ajax({
				type: "POST",
				url: "AddTestSuite?method="+param,
				data: form.serialize(),
				success: function (data) {
				//	alert(data);
					 $("#p:first").remove();
					if(data == "success"){
						 $("p:first").addClass("alert alert-success");
						 $("p:first").html(ms+" added successfully").fadeIn("slow");
						 newRow=0;
						 document.getElementById("totNewAppRow").value= newRow;
						 $("#tblTestSteps :input").each(function() {
							   $(this).attr("readonly", true);
						 });
						 
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
	   </div>
	   </div>
	   
	   <jsp:include page="footer.html"></jsp:include>