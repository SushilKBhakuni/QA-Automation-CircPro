<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<%
 	if(session.getAttribute("testCaseBasePath") == null){
		response.sendRedirect("setting.jsp");
 	}
//  	else {
//  		String testBasePath = request.getParameter("testCaseBasePath");
//  		System.out.println(testBasePath);
//     }
    
%>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="css/bootstrap-responsive.css"	type="text/css"> 
<%-- <jsp:include page="header1.jsp"/>  --%>
<div class="body-container">
  <div class="layout">
  <div class="navbar row-fluid headersection">
     <h2 class="bdrbtm">Enter the details for the OR sheet</h2>
     </div>
      <div class="fullwidth"  >
		 		<form method="post" action="AddTestCase" class="innerformsection" name="addTestStepForm" id="addTestStepForm">
                
                <div class="scrolldiv whitebg">
                <input type="hidden" value=<%=(String) session.getAttribute("testCaseBasePath") %> name="testCaseBasePath" id="testCaseBasePath">
                <input type="hidden" value=<%=request.getParameter("device")%> name="device" id="device">
					  <% int i=1; %>		
					  		  
				        <table width="100%" align="center" class="tableinner" id="tblTestSteps" style="" border="0" cellpadding="0" cellspacing="0">
				   		     <tr>
						  		   <th>Variable Name (Should be unique*)</th>
						  		   <th>Attribute</th>
						  		   <th>Attribute Value</th> 
<!-- 						  		   <th>Application</th> -->
					  		 </tr>
					  		 <tr>
					  		 <td align="center"><input type="text" name="varName_<%=i%>" id="varName_<%=i%>" class="input-block-level" width='2'></td>
					  		 <td align="center">
					  		 <select name="attribute_<%=i%>" id="attribute_<%=i%>" class="input-block-level" >
<!-- 					  		 <option name="attribute" id="attribute" class="input-block-level"> -->
					  		    <option value="elementId" selected>Select Attribute</option>
					  		    <option value="id">ID</option>
	 							<option value="name">Name</option> 
	 							<option value="parentElement">parentElement</option>
	 							<option value="tag">Tag</option> 
	 							<option value="name">Name</option>
	 							<option value="xpath">XPath</option> 
	 							<option value="link">Link</option>
	 							<option value="css">CSS</option>
	 							<option value="type">Type</option>
	 							<option value="dynamic">Dynamic</option>
	 							<option value="tagName">TagName</option>
	 							<option value="className">ClassName</option>
	 						  </select></td>
	 						  <td align="center"><input type="text" name="attrName_<%=i%>" id="attrName_<%=i%>" class="input-block-level" width='2'></td>
<!-- 					  	     <td> -->
<!-- 					  	     <select name="device" id="device"  class="input-block-level" > -->
<!-- 							  		 <option value="" selected>Select Application</option> -->
<!-- 							  		 <option value="web">Web</option> -->
<!-- 									 <option value="windows">Windows</option> -->
<!-- 									 <option value="mobile">Mobile</option> -->
<!-- 							</select> -->
<!-- 					  	     </td> -->
					  	     </tr>
					  	     </table>
				 </div>
				 <div align="right">
				 <br>
				 <table>
					  	     <tr>
					  	     <td>
					  	     <input type='hidden' value="<%=i%>" name='totalStepRows' id='totalStepRows'>
					  	     <input type="hidden" value="<%=i%>" name="totNewAppRow" id="totNewAppRow">
					  	     <input type="button" name="btnAddTestSteps" class="button marginleft10"  id="btnAddTestSteps" value="Update Test Steps" onclick="funcAddTestCases('addTestStepForm','AddTestStepsInOR')">
					  	     <input type="button" class="button marginleft10"  name="btnAddNewRow" id="btnAddNewRow" value="Add New Row" onclick="addNewRow()">
					  	     <input type="button" name="btnclose" class="button marginleft10"  id="btnRunSuites" value="Close Window" onclick=" window.close()">
					  	</td></tr>
					  	</table>
					  	</div>
                </form>
      </div>
  
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
       var newRow = 0;
	   function addNewRow()
	   {     
	        var $tr = $('#tblTestSteps').find("tr:last").clone();
	        
	        $tr.find("input,select,tr")
	        .attr("name", function()
	        {
	               // break the field name and it's number into two parts
	               var parts= this.id.split("_");
	               var fieldName = parts[0]+ "_"+ ++parts[1];
	               return fieldName;
	         })
	         
	         .attr("id", function()
             { 
                   var parts= this.id.split("_");
                   var fieldId = parts[0]+ "_"+ ++parts[1];
                   return fieldId;
              });
	           
	           totalRow =  document.getElementById("totalStepRows").value;
	           totalRow++;
	          
	           $('#tblTestSteps').find("tbody tr:last").after($tr);
	           document.getElementById("totalStepRows").value= totalRow;
	           newRow++;
	           document.getElementById("totNewAppRow").value= newRow;
	     }
	   
	   function funcAddTestCases(formName, param)  // add test suite/steps/cases    
		{
				var form = $('#'+formName);
			
				$.ajax({
					type: "POST",
					url: "AddTestSuite?method="+param,
					data: form.serialize(),
					success: function (data)
					{
					//alert(data);
					alert("Row added successfully!!");	
				   }
				});
			}
	   </script>
	   </div>
     </div>
<%-- <jsp:include page="footer.html"/> --%>

