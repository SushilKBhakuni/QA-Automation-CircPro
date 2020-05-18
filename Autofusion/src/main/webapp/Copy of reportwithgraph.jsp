<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.autofusion.sql.ReportSqls"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!-- <script type="text/javascript" src="js/jquery-1.12.3.js"></script>
 -->
<!-- 

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css"> -->

<%
	String testBasePath = "";
	if (session.getAttribute("testCaseBasePath") == null) {
		response.sendRedirect("setting.jsp");
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
					<button class="btn btn-default dropdown-toggle" type="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Large button <span class="caret"></span>
					</button>
				</div>
				<div class="col-md-12">
					<div class="row">
						<div class="panel panel-default">
							<div class="panel-body">

								<div class="col-md-3">
									<div class="form-group">
										<label>Environment</label> <select name="envCombo" id="envCombo"
											class="width230" onchange="getSuiteComboeData()">
											<option value="" selected>Select Environment</option>
											<option value="dev">DEV</option>
											<option value="Staging">Staging</option>
											<option value="qa">QA</option>
										</select>

									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Date of execution</label>
										 <select class="form-control" id="executionDateCombo">
											 
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Text</label> <input type="text" class="form-control" />
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">
										<input type="button" class="button marginnone" name="btnShowReport"
										 id="btnShowReport" value="Show Result" 
										 onclick="getResultData()">
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="contentbox">
					<div class="currentreport">
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr>
									<th width="50" scope="col" align="center">Suit Name</th>
									<th width="50" scope="col" nowrap align="center">Testcount</th>
									<th width="50" align="center">Firefox</th>
									<th width="50" align="center">Chrome</th>
									<th width="50" align="center">IE</th>
									<th width="30" scope="col">&nbsp;</th>
								</tr>
								<tbody id="testResultSummary"></tbody>
 							</tbody>
						</table>
					</div>

					<div class="col-md-6 pull-right">
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
								<tbody id="testCaseRows">

								</tbody>
							</table>
						</div>
					</div>


				</div>



				<div class="currentreportFull col-md-12"
					style="margin-top: 20px; padding-top: 20px;">
					<table id="testStepTable" class="display" cellspacing="0"
						width="100%" cellpadding="0" border="0">
						<thead>
							<tr>
								<th>Step id</th>
								<th>Description</th>
								<th>Result</th>
								<th>Screenshot</th>
								<th>Exception</th>
								<th>Start Time</th>
								<th>End Time</th>
							</tr>
						</thead>
						<tbody id="testStepRows">

						</tbody>
					</table>


				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.html"></jsp:include>

	<script>
		$(document).ready(function() {
			$('#testCaseTable').DataTable();

			$('#testStepTable').DataTable();

			$('#example').on('order.dt', function() {
				eventFired('Order');
			}).on('search.dt', function() {
				eventFired('Search');
			}).on('page.dt', function() {
				eventFired('Page');
			}).DataTable();
		});

		function getTestStepData(suiteName, env, batchId, testCaseId) {

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

		function getTestCaseData(suiteName, env, batchId) {
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
		}
		function getSuiteComboeData() {
			var env= document.getElementById("envCombo").value;
			
			if(env == ""){
				alert("Please select environment");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "ManageReportServlet",
				dataType : "html",
				data : {
 					env : env,
 					method : "getSuitCombo"
				},
				success : function(data) {
					$("#executionDateCombo").empty().html(data);
				}
			});
		}
		
		function getResultData() {
			var env = document.getElementById("envCombo").value;
			var executionDateValue = document.getElementById("executionDateCombo").value;
			
			if(env == ""){
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
 					method : "getTestResult"
				},
				success : function(data) {
					$("#testResultSummary").empty().html(data);
				}
			});
		}
		
		
	</script>