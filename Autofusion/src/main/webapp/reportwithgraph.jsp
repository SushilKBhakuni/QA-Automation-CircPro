<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.autofusion.sql.ReportSqls"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
	String testBasePath = "";
	if (session.getAttribute("testCaseBasePath") == null) {
		response.sendRedirect("index.jsp");
		return;
	} else {
		testBasePath = (String) session
				.getAttribute("testCaseBasePath");
	}
%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<link href="css/jquery.dataTables.min.css" rel="stylesheet"
	type="text/css" />
<div class="body-container">
	<div class="container">
		<div class="row">
			<h2 class="bdrbtm">Test Report</h2>
			<div class="accordianouter">
				<div class="accordianheader">
					<!-- <button class="btn btn-default dropdown-toggle" type="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Large button <span class="caret"></span>
					</button> -->
				</div>
				<div class="col-md-12">
					<div class="row">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="col-md-3">
									<div class="form-group">
										<label>Interface</label> <select class="form-control"
											id="executeOnDevice" name="executeOnDevice" onchange="getEnvCombo()">
											<option value="" selected>Select Interface</option>
											<option value="web">Web</option>
											<option value="mobile">Mobile</option>
											<option value="api">API</option>
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Environment</label> <select name="envCombo"
											id="envCombo" class="width230"
											onchange="getSuiteComboeData()">
											<option value="" selected>Select Environment</option>
											<!-- 
											
											<option value="dev">DEV</option>
											<option value="Staging">Staging</option>
											<option value="qa">QA</option> -->
										</select>
										<input type="hidden" name="testBasePath" id="testBasePath" value="<%=testBasePath %>">;
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Date of execution</label> <select class="form-control"
											id="executionDateCombo">
											<option value="" selected>Select Environment</option>

										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
									<label class="col-md-12 row">&nbsp;</label> 
										<input type="button" class="button marginnone"
											name="btnShowReport" id="btnShowReport" value="Show Result"
											onclick="getResultData()">
										<input type="hidden" class="button marginnone"
											name="btnShowReport" id="btnShowReport" value="Show Result"
											onclick="getFailSuitsData()">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="contentbox" style="display : none" id="divTestSuiteId">
					<div class="currentreport">
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr>
									<th width="50" scope="col" align="center">Suit Name</th>
									<th width="50" scope="col" nowrap align="center">Testcount</th>
									<th width="50" align="center">Firefox</th>
									<th width="50" align="center">Chrome</th>
									<th width="50" align="center">IE</th>
									<th width="50" scope="col">Android</th>
									<th width="50" scope="col">Api</th>
									<th width="50" scope="col">&nbsp;</th>
								</tr>
							<tbody id="testResultSummary"></tbody>
							</tbody>
						</table>
					</div>

					<div class="col-md-6 pull-right" style="display : none" id="divTestCase">
						<div class="currentreportFull">
							<table id="testCaseTable" class="display" cellspacing="0"
								width="100%" cellpadding="0" border="0">
								<thead>
									<tr>
										<th>Test Case</th>
										<th>Description</th>
										<th>Result</th>
										<th>Start Time</th>
										<th>End Time</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>


				</div>



				<div class="currentreportFull col-md-12"
					style="margin-top: 20px; padding-top: 20px;" id="divTestSteps">
					<table id="testStepTable">
						<thead>
							<tr>
								<th>Step id</th>
								<th>Description</th>
								<th>Action</th>
								<th>ExpectedData</th>
								<th>ActualData</th>
								<th>Failure Reason</th>
								<th>Result</th>
								<th>Screenshot</th>
								<th>Start Time</th>
								<th>End Time
							</tr>
						</thead>
						<!-- 	<tbody id="testStepRows">

						</tbody>
 -->
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.html"></jsp:include>

	<script>
		$(document).ready(function() {
			document.getElementById("divTestSteps").style.display="none";

		});

	     function showRelatedCombo(){
	    	 
	    	 var env = document.getElementById("envCombo").value;
				var device = document.getElementById("executeOnDevice").value;
	    	 
	    	 getSuiteComboeData()
	    	 
	     }
		 function getTestCaseData(suiteName, env, batchId, device) {

			$('#testCaseTable').dataTable(
					{
						 "destroy": true,
						"serverSide" : false,
						"processing" : false,
						"ajax" : {
							"type" : "POST",
							"dataSrc" : "data",
							"url" : "ManageReportServlet",
							"dataType" : "json",
							"data" : {
								suiteName : suiteName,
								env : env,
								batchId : batchId,
								device : device,
								method : "getTestCaseData"
							},
							"sEcho" : 0,
							"processing" : true,
							"columns" : [ 
							              {"data" : 0}, 
							              {"data" : 1}, 
							              {"data" : 2}, 
							              {"data" : 3}, 
							              {"data" : 4},
							            ]
						},
						"createdRow" : function(row, data, index) {

							if (data[2] == "PASS") {
								$('td', row).eq(2).addClass(
										"reportPassResultDataTable");
							} else if (data[2] == "FAIL") {
								$('td', row).eq(2).addClass(
										"reportFailResultDataTable");
							} else {
								$('td', row).eq(2).addClass(
										"reportSkipResultDataTable");
							}

						}
					});
		}

		function getTestStepData(suiteName, env, batchId, testCaseId, device) {

			$('#testStepTable').dataTable(
					{
						 "destroy": true,
						"serverSide" : false,
						"processing" : false,
						"ajax" : {
							"type" : "POST",
							"dataSrc" : "data",
							"url" : "ManageReportServlet",
							"dataType" : "json",
							"data" : {
								suiteName : suiteName,
								env : env,
								batchId : batchId,
								testCaseId : testCaseId,
								device : device,
								method : "getTestStepData"
							},
							"sEcho" : 0,
							"processing" : true,
							"columns" : [ 
											{"data" : 0}, 
											{"data" : 1}, 
											{"data" : 2}, 
											{"data" : 3}, 
											{"data" : 4},
											{"data" : 5}, 
											{"data" : 6}, 
											{"data" : 7}, 
											{"data" : 8},
											{"data" : 9}, 
											{"data" : 10}, 
										]

						},
						"createdRow" : function(row, data, index) {

							if (data[6] == "PASS") {
								$('td', row).eq(6).addClass(
										"reportPassResultDataTable");
							} else if (data[6] == "FAIL") {
								$('td', row).eq(6).addClass(
										"reportFailResultDataTable");
							} else {
								$('td', row).eq(6).addClass(
										"reportSkipResultDataTable");
							}

						}
					});
		}

		function getSuiteComboeData() {
			var env = document.getElementById("envCombo").value;
			var device = document.getElementById("executeOnDevice").value;

			if (device == "") {
				alert("Please select device interface");
				return false;
			}
			if (env == "") {
				alert("Please select environment");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "ManageReportServlet",
				dataType : "html",
				data : {
					env : env,
					device : device,
					method : "getSuitCombo"
				},
				success : function(data) {
					$("#executionDateCombo").empty().html(data);
				}
			});
		}

		function getEnvCombo() {
			
			var device = document.getElementById("executeOnDevice").value;
			var testBasePath = document.getElementById("testBasePath").value;
			
			if (device == "") {
				alert("Please select device interface");
				return false;
			}
			if (testBasePath == "") {
				alert("Please restart server");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "ManageReportServlet",
				dataType : "html",
				data : {
					path : testBasePath,
					device : device,
					method : "getEnvCombo"
				},
				success : function(data) {
					$("#envCombo").empty().html(data);
				}
			});
		}


		function getResultData() {
			var env = document.getElementById("envCombo").value;
			var executionDateCombo = document.getElementById("executionDateCombo").value;
			var device = document.getElementById("executeOnDevice").value;

			if (env == "") {
				alert("Please select environment");
				return false;
			}

			if (executionDateCombo == "") {
				alert("No report found for selected criteria");
				return false;
			}
			
			
			document.getElementById("divTestSuiteId").style.display="";
			document.getElementById("divTestCase").style.display="";
			document.getElementById("divTestSteps").style.display="";

			var executionDateValue = document
					.getElementById("executionDateCombo").value;

			if (env == "") {
				alert("Please select environment");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "ManageReportServlet",
				dataType : "html",
				data : {
					env : env,
					executionDate : executionDateValue,
					device :device,
					method : "getTestResult"
				},
				success : function(data) {
					$("#testResultSummary").empty().html(data);
				}
			});
		} 
	
		/* 		function getTestCaseData1(suiteName, env, batchId) {
		
		
		 $.ajax({
		 type : "POST",
		 url : "ManageReportServlet",
		 dataType : "html",
		 data : {
		 suiteName : suiteName,
		 env : env,
		 batchId : batchId,
		 method : "getTestCaseData"
		 },
		 success : function(data) {
		 $("#testCaseRows").empty().html(data);
		 }
		 });

		 //	$('#testCaseTable').DataTable(); 

		 }
		 */

			/* 	function getTestStepData(suiteName, env, batchId, testCaseId) {

			$.ajax({
				type : "POST",
				url : "ManageReportServlet",
				dataType : "html",
				data : {
					suiteName : suiteName,
					env : env,
					batchId : batchId,
					testCaseId : testCaseId,
					method : "getTestStepData"
				},
				success : function(data) {
					$("#testStepRows").empty().html(data);
				}
			});
		}
	 */

	 
		
		</script>