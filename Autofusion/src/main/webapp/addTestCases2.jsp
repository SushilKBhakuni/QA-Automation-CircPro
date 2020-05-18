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
<jsp:include page="header.jsp">
	<jsp:param value="active" name="Header"/>
</jsp:include>

<div class="body-container">
  <div class="layout">
     <h2 class="bdrbtm">Manage Test Cases</h2>
      <p  class="text-warning" id="result"><%= msg %></p>
     <div class="whitebg blockelement padding15" id="home">
     	<ul class="progressbar">
		     		<li ><span class="icon"></span><span class="titletxt"><a href="addTestCases.jsp">Create suite</a></span>
		     		<span class="progressline1"></span>
		     		</li>
		     	<li><span class="icon"></span><span class="titletxt"><a href="addTestCases1.jsp">Create test case</a></span>
		     	<span class="progressline2"></span>
		     	</li>
		     	<li class="active"><span class="icon"></span><span class="titletxt"><a href="addTestCases2.jsp">Add Test steps</a></span></li>
		     	
		     	</ul>
		</div>     	
		<div class="whitebg blockelement padding15" id="home">     	
	    <div class="fullwidth"  >
		 		<form method="post" action="AddTestCase" class="innerformsection" name="addTestStepForm" id="addTestStepForm">
		 		<div class="fullwidth innerbtmrow">
			 		<div class="padding0 fl width100">
		 				<div class="frmrow marginbtm20">
		 				<input type="hidden" value=<%=(String) session.getAttribute("testCaseBasePath") %> name="testCaseBasePath" id="testCaseBasePath">
		 				<div class="col3">
					        <label class="labeltxt width80">Application</label>
					        	<select name="device" id="device"  class="width130 form-control" onchange="getSuiteList('addTestStepForm','selTestSuite')"
				        					 class="span6 input-medium">
							  		  			<option	value="" selected>Select Application</option>
							  		  			<option	value="web">Web</option>
												<!-- option value="windows">Windows</option-->
												<option value="mobile">Mobile</option>
											</select>
							</div>
							<div class="col3">
				  		    <label class="labeltxt width80">Select Suite</label>
				  		    <select name='selTestSuite' class="width130" id='selTestSuite'  class="span6 input-medium" onchange="getTestCase('addTestStepForm')"><option>Select test Suite</option>
				  		    </select>
				  		    </div>
				  		    <div class="col3">
				  		    <label class="labeltxt width80">Test case name</label> 
			  		    	<select name="testCaseName" class="width130" id="testCaseName"  class="span6 input-medium" onchange="getTestStep('addTestStepForm')"><option>Select test Step</option>
			  		    	</select>
			  		    	</div>
					  		</div>	   
					  				<div class="scrolldiv whitebg">
					  		
					  		  
				        <table width="100%" align="center" class="tableinner" id="tblTestSteps" style="" border="0" cellpadding="0" cellspacing="0">
				   		     <tr>
						  		   <th>Step Id</th>
						  		   <th>Description</th>
						  		   <th>Attribute</th>
						  		   <th>Element Id</th> 
						  		   <th>Unique Identifier</th>
						  		   <th>Keyword</th>
						  		   <th>Data</th>
						  		   <th>GotoifFail</th>
						  		   <th>Action</th>
					  		  </tr>
					  		  <!-- table id="appendTestSteps" class="table"></table>
					  		  
					  		  tr id="testStepRow_1"> 
					  	 		  <td><input type="text" name="stepId_1" id="stepId_1" class="input-block-level " value='TS-1' readonly></td>
					  	 		  <td><input type="text" name="description_1" id="description_1" class="input-block-level" ></td>
					  	 		   <td><select 
									name="attribute_1" id="attribute_1" class="input-block-level" ><option
										value="elementId" selected>ID</option>
									<option value="xpath">XPath</option>
									<option value="css">CSS</option>
									</select></td>
								<td><input class="input-block-level" type="text" name="elementId_1" id="elementId_1"  ></td>	
								 <td><select name="keyword_1" id="keyword_1" class="input-block-level" >
						  		  	  	<option value="click" selected>Click</option>
										<option value="windows">ClickAndWait</option>
										<option value="mobile">Type</option>
									   </select>
								  </td> 
						  		 
					  		  <td><input type="text" name="data_1" id="data_1" class="input-block-level" ></td>
					  		  <td><input type="text" name="goToIfFail_1" id="goToIfFail_1" class="input-block-level" ></td>
					  	   </tr-->
					      </table>
				</div>	   
		       		
		       		 <table width="100%" id="tblAppTestSteps" style="display:none">
		       		  <tr>
		       		     <td><p class="frmrow">
				       		<select name="selAppRow" id="selAppRow" class="form-control" onchange="showTxtAppRow(this)">
					       		<option value="appInLast">Append in Last</option>
					       		<option value="appAfter">Append after</option>
				       		</select>
				       		<input type="text" name="txtAppRow" id="txtAppRow" style="display:none" placeholder="TS-" class="input-block-level">
				       		</td>
				       		<td>
				       		<input type="hidden" name="totNewAppRow" id="totNewAppRow" value="5">
				       		<input type="button" name="btnAddTestSteps" class="button marginleft10"  id="btnAddTestSteps" value="Update Test Steps" 
				       				onclick="funcAddTestCases('addTestStepForm','AddTestSteps','Test Steps')">
				       		<input type="button" class="button"  name="btnAddNewRow" id="btnAddNewRow" value="Add New Row" onclick="addNewRow()">
				       		
				       		</td>
				       </tr>
				       </table>	
				       </div>
		       		<div>	
				    </div>
		       			 <!--  input type="hidden" name="totalStepRow" id="totalStepRow" class="input-block-level" -->
		       			
		       			<input type="hidden" name="selectedTab" id="selectedTab" value="AddTestStep">
		    	</form>
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