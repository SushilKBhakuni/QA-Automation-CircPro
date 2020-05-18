<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.autofusion.util.InitClass"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<%@page import="com.autofusion.sql.ReportSqls"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>

 <%
  response.setHeader("Cache-Control","no-cache");
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragma","no-cache");
  response.setDateHeader ("Expires", 0);
  
  if(session.getAttribute("testCaseBasePath") == null){
	response.sendRedirect("index.jsp");
 }
 %>

 <%
 
 CommonUtility objCommonUtilityScript = new CommonUtility();
 ReportSqls objCommonReportSql = new ReportSqls();

 String confFile = InitClass.initializeExternalConfigFile(session.getAttribute("testCaseBasePath").toString());
 if(confFile == null){
	 response.sendRedirect("index.jsp");
 }
 String testCaseBasePath = (String) session.getAttribute("testCaseBasePath");
 %>
 
<jsp:include page="header.jsp"/>
<form method="post" action="ExecuteTestCases" class="row-fluid" name="testDetailForm" id="testDetailForm">
<div class="body-container">
  <div class="container">
  <div class="row">
    <h2 class="bdrbtm">Dashboard</h2>
   
       <%String d1 = "";String d2 = "";String d3 = "";String d4 = "";String d5 = "";
		int p1 = 0; int p2 = 0; int p3 = 0; int p4 = 0; int p5 = 0; 
		int f1 = 0; int f2 = 0; int f3 = 0; int f4 = 0; int f5 = 0; 
		int s1 = 0; int s2 = 0; int s3 = 0; int s4 = 0; int s5 = 0; 
		
		String wd1 = "";String wd2 = "";String wd3 = "";String wd4 = "";String wd5 = "";
		int wp1 = 0; int wp2 = 0; int wp3 = 0; int wp4 = 0; int wp5 = 0; 
		int wf1 = 0; int wf2 = 0; int wf3 = 0; int wf4 = 0; int wf5 = 0; 
		int ws1 = 0; int ws2 = 0; int ws3 = 0; int ws4 = 0; int ws5 = 0; 
		
		String ad1 = "";String ad2 = "";String ad3 = "";String ad4 = "";String ad5 = "";
		int ap1 = 0; int ap2 = 0; int ap3 = 0; int ap4 = 0; int ap5 = 0; 
		int af1 = 0; int af2 = 0; int af3 = 0; int af4 = 0; int af5 = 0; 
		int as1 = 0; int as2 = 0; int as3 = 0; int as4 = 0; int as5 = 0; 
       
   		 if(InitClass.USER_CONFIG.getProperty("webDashboardMenu").equals("on")){
   
   			 ResultSet rs = objCommonReportSql.readFinalReport("web");
		     if(rs != null){	
		  %>
    <div class="accordianouter">
     
		  <div class="contentbox"  >
		        <div class="currentreport">
		        <h4 class="accordianheader"><span class="accordianicon"></span><span class="fl">Web Automation Results</span></h4>
		        
		          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <!-- <th scope="col" >Category</th> -->
	              <th scope="col"  width="80">Execution Date</th>
	              <th scope="col"  >Release</th>
	              <th scope="col" >Environment</th>
	              <th scope="col" >Total Suits</th>
	              <th scope="col" >Total Test Case</th>
	              <th scope="col" >Total Steps</th>
	              <th scope="col" width="120"  >Result</th>
	              <th scope="col" >Browser</th>
	              <th scope="col" >&nbsp;</th>

 	          </tr>
	            <% List<HashMap> graphListWeb= new ArrayList<HashMap>();
		    		while(rs.next()){ 
		    			HashMap dateMapWeb = new HashMap();
		    		%>
                       <tr>
			              <%-- <td ><%=//rs.getString("suitecategory")%></td> --%>
			              <td align="center" nowrap><%=rs.getString("executiondate") + " ("+rs.getString("batchid")+")"%></td>
			              <td align="center"><%=rs.getString("releaseversion")%></td>
			              <td align="center"><%=rs.getString("environment")%></td>
			              <td align="center"><%=rs.getString("totalsuite")%></td>
			              <td align="center"><%=rs.getString("totaltestcase")%></td>
			              <td align="center"><%=rs.getString("totalsteps")%></td>
			              
			              <%
			              StringBuffer strBuffer = new StringBuffer();
			              int pass = rs.getInt("pass");
			              int fail = rs.getInt("fail");
			              int skip = rs.getInt("skip");
			              
			              int batchId = rs.getInt("batchid");
			              dateMapWeb.put("executiondate", rs.getString("executiondate")) ;
			              dateMapWeb.put("pass", pass) ;
			              dateMapWeb.put("fail", fail) ;
			              dateMapWeb.put("skip", skip) ;
			              
			              graphListWeb.add(dateMapWeb);
			              
			            int  total = pass+fail+skip;
			              
			              float passPer = (((float) pass / total) * 100);
						  float failPer = (((float) fail / total) * 100) ;
						  float skipPer = (((float) skip / total) * 100);
			              
			              
			              strBuffer.append("<div class=\"progress\">");
								strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
								strBuffer.append("</div>");
								strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
								strBuffer.append("</div>");
							strBuffer.append("</div>");
		              %>
		              <td ><%=strBuffer %></td>
		              
		              <td align="center"><%=rs.getString("browser")%></td>
		   			  <td colspan="1" align="center">&nbsp;
		              			 <!-- <a href="C:\GL\AutofusionDemoNew\web\report\Browser\Firefox\ExecutionReport_03.October.201601.08.48.html" target="_blank" onclick="openFile()"><img src='images/Bar.png' width='15' height='15' title='Open Report'/></a> --></td>
		            </tr>
	           <% 
	           }%> 
           
           		      <% 
		        		int lstSize = graphListWeb.size();	
		  
		        		if(lstSize > 0){
						   wd1 = graphListWeb.get(0).get("executiondate").toString(); 
						   wp1 = Integer.valueOf(graphListWeb.get(0).get("pass").toString()); 
						   wf1 = Integer.valueOf(graphListWeb.get(0).get("fail").toString()); 
						   ws1 = Integer.valueOf(graphListWeb.get(0).get("skip").toString());
		        		}
		        		if(lstSize > 1){
						   wd2 = graphListWeb.get(1).get("executiondate").toString(); 
						   wp2 = Integer.valueOf(graphListWeb.get(1).get("pass").toString()); 
						   wf2 = Integer.valueOf(graphListWeb.get(1).get("fail").toString()); 
						   ws2 = Integer.valueOf(graphListWeb.get(1).get("skip").toString());
		        		}
		        		if(lstSize > 2){
						   wd3 = graphListWeb.get(2).get("executiondate").toString(); 
						   wp3 = Integer.valueOf(graphListWeb.get(2).get("pass").toString()); 
						   wf3 = Integer.valueOf(graphListWeb.get(2).get("fail").toString()); 
						   ws3 = Integer.valueOf(graphListWeb.get(2).get("skip").toString());
		        		}
		        		if(lstSize > 3){
						   wd4 = graphListWeb.get(3).get("executiondate").toString(); 
						   wp4 = Integer.valueOf(graphListWeb.get(3).get("pass").toString()); 
						   wf4 = Integer.valueOf(graphListWeb.get(3).get("fail").toString()); 
						   ws4 = Integer.valueOf(graphListWeb.get(3).get("skip").toString());
		        		}
		        		if(lstSize > 4){
						   wd5 = graphListWeb.get(4).get("executiondate").toString(); 
						   wp5 = Integer.valueOf(graphListWeb.get(4).get("pass").toString()); 
						   wf5 = Integer.valueOf(graphListWeb.get(4).get("fail").toString()); 
						   ws5 = Integer.valueOf(graphListWeb.get(4).get("skip").toString());
		        		}
				    %>
           
          </table>
        </div>
          <%}%>
      
   <%}%>
       
         <div class="currentreport">
          <h4 class="accordianheader"><span class="accordianicon"></span><span class="fl">Android Mobile Automation Results</span></h4>
         <% 
          if(InitClass.USER_CONFIG.getProperty("mblDashboardMenu").equals("on")){
   	
   			 ResultSet rs = objCommonReportSql.readFinalReport("mobile");
		     if(rs != null){	
		  %>
		         <table width="100%" border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <!-- <th scope="col" >Category</th> -->
			              <th scope="col" align="center">Execution Date</th>
			              <th scope="col"  align="center">Release</th>
<!-- 			              <th scope="col" align="center">Environment</th>
 -->			              <th scope="col" align="center">Total Suits</th>
			              <th scope="col" align="center">Total Test Case</th>
			              <th scope="col" align="center" >Total Steps</th>
			              <th scope="col" width="120"  align="center">Result</th>
			              <th scope="col" align="center">Browser</th>
			              <th scope="col" >&nbsp;</th>
			              
 			          </tr>
			            <%  List<HashMap> graphList= new ArrayList<HashMap>();
			          		
			          		 
				    		while(rs.next()){ 
				    		HashMap dateMap = new HashMap();
				    		%>
		                       <tr>
					              <%-- <td ><%=//rs.getString("suitecategory")%></td> --%>
					              <td align="center"><%=rs.getString("executiondate") + " ("+rs.getString("batchid")+")"%></td>
					               <td align="center"><%=rs.getString("releaseversion")%></td>
<%-- 					              <td align="center"><%=//rs.getString("environment")%></td>
 --%>					              <td align="center"><%=rs.getString("totalsuite")%></td>
					              <td align="center"><%=rs.getString("totaltestcase")%></td>
					              <td align="center"><%=rs.getString("totalsteps")%></td>
					              
					              <%
					            
					              StringBuffer strBuffer = new StringBuffer();
					              int pass = rs.getInt("pass");
					              int fail = rs.getInt("fail");
					              int skip = rs.getInt("skip");
					              
					              int batchId = rs.getInt("batchid");
					              dateMap.put("executiondate", rs.getString("executiondate")) ;
					              dateMap.put("pass", pass) ;
					              dateMap.put("fail", fail) ;
					              dateMap.put("skip", skip) ;
					              
					              graphList.add(dateMap);
					            
					              int  total = pass+fail+skip;
					              
					              float passPer = (((float) pass / total) * 100);
								  float failPer = (((float) fail / total) * 100) ;
								  float skipPer = (((float) skip / total) * 100);
					              
					              
					              strBuffer.append("<div class=\"progress\">");
										strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
										strBuffer.append("</div>");
									strBuffer.append("</div>");
				              %>
				              <td ><%=strBuffer %></td>
				              
				              <td align="center"><%=rs.getString("browser")%></td>
				             <td colspan="1" align="center">&nbsp;
		              			 <%-- <a href="<%=rs.getString("reportpath") %>" target="_blank"><img src='images/Bar.png' width='15' height='15' title='Open Report'/></a> --%></td>
				            </tr>
			           <% 
			           }%> 
		          </table>
		          
		          <% 
		        		int lstSize = graphList.size();	
		  
		        		if(lstSize > 0){
						   d1 = graphList.get(0).get("executiondate").toString(); 
						   p1 = Integer.valueOf(graphList.get(0).get("pass").toString()); 
						   f1 = Integer.valueOf(graphList.get(0).get("fail").toString()); 
						   s1 = Integer.valueOf(graphList.get(0).get("skip").toString());
		        		}
		        		if(lstSize > 1){
						   d2 = graphList.get(1).get("executiondate").toString(); 
						   p2 = Integer.valueOf(graphList.get(1).get("pass").toString()); 
						   f2 = Integer.valueOf(graphList.get(1).get("fail").toString()); 
						   s2 = Integer.valueOf(graphList.get(1).get("skip").toString());
		        		}
		        		if(lstSize > 2){
						   d3 = graphList.get(2).get("executiondate").toString(); 
						   p3 = Integer.valueOf(graphList.get(2).get("pass").toString()); 
						   f3 = Integer.valueOf(graphList.get(2).get("fail").toString()); 
						   s3 = Integer.valueOf(graphList.get(2).get("skip").toString());
		        		}
		        		if(lstSize > 3){
						   d4 = graphList.get(3).get("executiondate").toString(); 
						   p4 = Integer.valueOf(graphList.get(3).get("pass").toString()); 
						   f4 = Integer.valueOf(graphList.get(3).get("fail").toString()); 
						   s4 = Integer.valueOf(graphList.get(3).get("skip").toString());
		        		}
		        		if(lstSize > 4){
						   d5 = graphList.get(4).get("executiondate").toString(); 
						   p5 = Integer.valueOf(graphList.get(4).get("pass").toString()); 
						   f5 = Integer.valueOf(graphList.get(4).get("fail").toString()); 
						   s5 = Integer.valueOf(graphList.get(4).get("skip").toString());
		        		}
				    %>
		          
		        <%}%>
     <%}%>
          </div>
 		  </div>
 	  <div class="contentbox"  >
 		  
 		<div class="currentreport">
          <h4 class="accordianheader"><span class="accordianicon"></span><span class="fl">API Automation Results</span></h4>
         <% 
          if(InitClass.USER_CONFIG.getProperty("mblDashboardMenu").equals("on")){
   	
   			 ResultSet rs = objCommonReportSql.readFinalReport("api");
		     if(rs != null){	
		  %>
		         <table width="100%" border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <!-- <th scope="col" >Category</th> -->
			              <th scope="col" align="center">Execution Date</th>
			              <th scope="col"  align="center">Release</th>
 			              <th scope="col" align="center">Environment</th>
			              <th scope="col" align="center">Total Suits</th>
			              <th scope="col" align="center">Total Test Case</th>
			              <th scope="col" align="center" >Total Steps</th>
			              <th scope="col" width="120"  align="center">Result</th>
			              <th scope="col" align="center">Browser</th>
			              <th scope="col" >&nbsp;</th>
			              
 			          </tr>
			            <%  List<HashMap> graphList= new ArrayList<HashMap>();
			          		
			          		 
				    		while(rs.next()){ 
				    		HashMap dateMap = new HashMap();
				    		%>
		                       <tr>
					              <%-- <td ><%=//rs.getString("suitecategory")%></td> --%>
					              <td align="center" nowrap><%=rs.getString("executiondate") + "("+rs.getString("batchid")+")"%></td>
					               <td align="center"><%=rs.getString("releaseversion")%></td>
 					              <td align="center"><%=rs.getString("environment")%></td>
					              <td align="center"><%=rs.getString("totalsuite")%></td>
					              <td align="center"><%=rs.getString("totaltestcase")%></td>
					              <td align="center"><%=rs.getString("totalsteps")%></td>
					              
					              <%
					            
					              StringBuffer strBuffer = new StringBuffer();
					              int pass = rs.getInt("pass");
					              int fail = rs.getInt("fail");
					              int skip = rs.getInt("skip");
					              
					              int batchId = rs.getInt("batchid");
					              dateMap.put("executiondate", rs.getString("executiondate")) ;
					              dateMap.put("pass", pass) ;
					              dateMap.put("fail", fail) ;
					              dateMap.put("skip", skip) ;
					              
					              graphList.add(dateMap);
					            
					              int  total = pass+fail+skip;
					              
					              float passPer = (((float) pass / total) * 100);
								  float failPer = (((float) fail / total) * 100) ;
								  float skipPer = (((float) skip / total) * 100);
					              
					              
					              strBuffer.append("<div class=\"progress\">");
										strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
										strBuffer.append("</div>");
									strBuffer.append("</div>");
				              %>
				              <td ><%=strBuffer %></td>
				              
				              <td align="center"><%=rs.getString("browser")%></td>
				             <td colspan="1" align="center">&nbsp;
		              			 <%-- <a href="<%=rs.getString("reportpath") %>" target="_blank"><img src='images/Bar.png' width='15' height='15' title='Open Report'/></a> --%></td>
				            </tr>
			           <% 
			           }%> 
		          </table>
		          
		          <% 
		        		int lstSize = graphList.size();	
		  
		        		if(lstSize > 0){
						   ad1 = graphList.get(0).get("executiondate").toString(); 
						   ap1 = Integer.valueOf(graphList.get(0).get("pass").toString()); 
						   af1 = Integer.valueOf(graphList.get(0).get("fail").toString()); 
						   as1 = Integer.valueOf(graphList.get(0).get("skip").toString());
		        		}
		        		if(lstSize > 1){
						   ad2 = graphList.get(1).get("executiondate").toString(); 
						   ap2 = Integer.valueOf(graphList.get(1).get("pass").toString()); 
						   af2 = Integer.valueOf(graphList.get(1).get("fail").toString()); 
						   as2 = Integer.valueOf(graphList.get(1).get("skip").toString());
		        		}
		        		if(lstSize > 2){
						   ad3 = graphList.get(2).get("executiondate").toString(); 
						   ap3 = Integer.valueOf(graphList.get(2).get("pass").toString()); 
						   af3 = Integer.valueOf(graphList.get(2).get("fail").toString()); 
						   as3 = Integer.valueOf(graphList.get(2).get("skip").toString());
		        		}
		        		if(lstSize > 3){
						   ad4 = graphList.get(3).get("executiondate").toString(); 
						   ap4 = Integer.valueOf(graphList.get(3).get("pass").toString()); 
						   af4 = Integer.valueOf(graphList.get(3).get("fail").toString()); 
						   as4 = Integer.valueOf(graphList.get(3).get("skip").toString());
		        		}
		        		if(lstSize > 4){
						   ad5 = graphList.get(4).get("executiondate").toString(); 
						   ap5 = Integer.valueOf(graphList.get(4).get("pass").toString()); 
						   af5 = Integer.valueOf(graphList.get(4).get("fail").toString()); 
						   as5 = Integer.valueOf(graphList.get(4).get("skip").toString());
		        		}
				    %>
		          
		        <%}%>
     <%}%>
          </div>
        
           <div class="currentreport">
          <h4 class="accordianheader"><span class="accordianicon"></span><span class="fl">iOS Mobile Automation Results</span></h4>
         <% 
          if(InitClass.USER_CONFIG.getProperty("mblDashboardMenu").equals("on")){
   	
   			 ResultSet rs = objCommonReportSql.readFinalReport("ios");
		     if(rs != null){	
		  %>
		         <table width="100%" border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <!-- <th scope="col" >Category</th> -->
			              <th scope="col" align="center">Execution Date</th>
			              <th scope="col"  align="center">Release</th>
<!-- 			              <th scope="col" align="center">Environment</th>
 -->			              <th scope="col" align="center">Total Suits</th>
			              <th scope="col" align="center">Total Test Case</th>
			              <th scope="col" align="center" >Total Steps</th>
			              <th scope="col" width="120"  align="center">Result</th>
			              <th scope="col" align="center">Browser</th>
			              <th scope="col" >&nbsp;</th>
			              
 			          </tr>
			            <%  List<HashMap> graphList= new ArrayList<HashMap>();
			          		
			          		 
				    		while(rs.next()){ 
				    		HashMap dateMap = new HashMap();
				    		%>
		                       <tr>
					              <%-- <td ><%=//rs.getString("suitecategory")%></td> --%>
					              <td align="center"><%=rs.getString("executiondate")%></td>
					               <td align="center"><%=rs.getString("releaseversion")%></td>
<%-- 					              <td align="center"><%=//rs.getString("environment")%></td>
 --%>					              <td align="center"><%=rs.getString("totalsuite")%></td>
					              <td align="center"><%=rs.getString("totaltestcase")%></td>
					              <td align="center"><%=rs.getString("totalsteps")%></td>
					              
					              <%
					            
					              StringBuffer strBuffer = new StringBuffer();
					              int pass = rs.getInt("pass");
					              int fail = rs.getInt("fail");
					              int skip = rs.getInt("skip");
					              
					              int batchId = rs.getInt("batchid");
					              dateMap.put("executiondate", rs.getString("executiondate")) ;
					              dateMap.put("pass", pass) ;
					              dateMap.put("fail", fail) ;
					              dateMap.put("skip", skip) ;
					              
					              graphList.add(dateMap);
					            
					              int  total = pass+fail+skip;
					              
					              float passPer = (((float) pass / total) * 100);
								  float failPer = (((float) fail / total) * 100) ;
								  float skipPer = (((float) skip / total) * 100);
					              
					              
					              strBuffer.append("<div class=\"progress\">");
										strBuffer.append("<div class=\"progress-bar progress-bar-success\" 	style=\"width: "+String.format("%2.00f", passPer)+"%\"	title='"+pass+" test cases passed'>"+pass);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-danger\"	style=\"width: "+String.format("%2.00f", failPer)+"%\" title='"+fail+" test cases failed'>"+fail);
										strBuffer.append("</div>");
										strBuffer.append("<div class=\"progress-bar progress-bar-warning progress-bar-striped\"	style=\"width: "+String.format("%2.00f", skipPer)+"%\" title='"+skip+" test cases skipped'>"+skip);
										strBuffer.append("</div>");
									strBuffer.append("</div>");
				              %>
				              <td ><%=strBuffer %></td>
				              
				              <td align="center"><%=rs.getString("browser")%></td>
				             <td colspan="1" align="center">&nbsp;
		              			 <%-- <a href="<%=rs.getString("reportpath") %>" target="_blank"><img src='images/Bar.png' width='15' height='15' title='Open Report'/></a> --%></td>
				            </tr>
			           <% 
			           }%> 
		          </table>
		          
		          <% 
		        		int lstSize = graphList.size();	
		  
		        		if(lstSize > 0){
						   d1 = graphList.get(0).get("executiondate").toString(); 
						   p1 = Integer.valueOf(graphList.get(0).get("pass").toString()); 
						   f1 = Integer.valueOf(graphList.get(0).get("fail").toString()); 
						   s1 = Integer.valueOf(graphList.get(0).get("skip").toString());
		        		}
		        		if(lstSize > 1){
						   d2 = graphList.get(1).get("executiondate").toString(); 
						   p2 = Integer.valueOf(graphList.get(1).get("pass").toString()); 
						   f2 = Integer.valueOf(graphList.get(1).get("fail").toString()); 
						   s2 = Integer.valueOf(graphList.get(1).get("skip").toString());
		        		}
		        		if(lstSize > 2){
						   d3 = graphList.get(2).get("executiondate").toString(); 
						   p3 = Integer.valueOf(graphList.get(2).get("pass").toString()); 
						   f3 = Integer.valueOf(graphList.get(2).get("fail").toString()); 
						   s3 = Integer.valueOf(graphList.get(2).get("skip").toString());
		        		}
		        		if(lstSize > 3){
						   d4 = graphList.get(3).get("executiondate").toString(); 
						   p4 = Integer.valueOf(graphList.get(3).get("pass").toString()); 
						   f4 = Integer.valueOf(graphList.get(3).get("fail").toString()); 
						   s4 = Integer.valueOf(graphList.get(3).get("skip").toString());
		        		}
		        		if(lstSize > 4){
						   d5 = graphList.get(4).get("executiondate").toString(); 
						   p5 = Integer.valueOf(graphList.get(4).get("pass").toString()); 
						   f5 = Integer.valueOf(graphList.get(4).get("fail").toString()); 
						   s5 = Integer.valueOf(graphList.get(4).get("skip").toString());
		        		}
				    %>
		          
		        <%}%>
     <%}%>
          </div>
          
     </div>
          
   </div></div>
       <div class="webBarGraph" id="webBarGraph"> </div>   <br>   
       <div class="webBarGraph" id="mobileBarGraph"> </div>
       <div class="webBarGraph" id="apiBarGraph"> </div>

  </div>
</div>
</div>
</form>
<jsp:include page="footer.html"></jsp:include>
</html>
<script type="text/javascript"> 
 
$(document).ready(function() {
	createBarChartMobile(<%=p1%>,<%=f1%>,<%=s1%>,
            <%=p2%>,<%=f2%>,<%=s2%>,
            <%=p3%>,<%=f3%>,<%=s3%>,
            <%=p4%>,<%=f4%>,<%=s4%>,
            <%=p5%>,<%=f5%>,<%=s5%>,
            '<%=d1%>','<%=d2%>','<%=d3%>','<%=d4%>','<%=d5%>')
 	
    createBarChartWeb(<%=wp1%>,<%=wf1%>,<%=ws1%>,
            <%=wp2%>,<%=wf2%>,<%=ws2%>,
            <%=wp3%>,<%=wf3%>,<%=ws3%>,
            <%=wp4%>,<%=wf4%>,<%=ws4%>,
            <%=wp5%>,<%=wf5%>,<%=ws5%>,
            '<%=wd1%>','<%=wd2%>','<%=wd3%>','<%=wd4%>','<%=wd5%>')
            
            
      createBarChartApi(<%=ap1%>,<%=af1%>,<%=as1%>,
                    <%=ap2%>,<%=af2%>,<%=as2%>,
                    <%=ap3%>,<%=af3%>,<%=as3%>,
                    <%=ap4%>,<%=af4%>,<%=as4%>,
                    <%=ap5%>,<%=af5%>,<%=as5%>,
                    '<%=ad1%>','<%=ad2%>','<%=ad3%>','<%=ad4%>','<%=ad5%>')
         	        
});

 
 

 function createBarChartMobile(p1,f1,s1,p2,f2,s2,p3,f3,s3,p4,f4,s4,p5,f5,s5,name1,name2,name3,name4,name5){
       //Multibar chart Function
	//	$(function () {
			$('#mobileBarGraph').highcharts({
				chart: {
					type: 'column'
				},
				title: {
					text: 'Android test result comparision'
				},
				subtitle: {
					text: ''
				},
				xAxis: {
					categories: [
						name1,
						name2,
						name3,
						name4,
						name5,
					]
				},
				
				tooltip: {
					headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
						'<td style="padding:0"><b>{point.y}</b></td></tr>',
					footerFormat: '</table>',
					shared: true,
					useHTML: true
				},
				plotOptions: {
					column: {
						pointPadding: 0.2,
						borderWidth: 0
					}
				},
				series: [{
					name: 'Pass',
					data: [p1, p2, p3, p4, p5]
		
				}, {
					name: 'Fail',
					data: [f1,f2, f3, f4, f5]
		
				}, {
					name: 'Skip',
					data: [s1,s2,s3,s4,s5]
		
				}]
			});
	//	});
   }

 function createBarChartApi(p1,f1,s1,p2,f2,s2,p3,f3,s3,p4,f4,s4,p5,f5,s5,name1,name2,name3,name4,name5){
     //Multibar chart Function
	//	$(function () {
			$('#apiBarGraph').highcharts({
				chart: {
					type: 'column'
				},
				title: {
					text: 'API test result comparision'
				},
				subtitle: {
					text: ''
				},
				xAxis: {
					categories: [
						name1,
						name2,
						name3,
						name4,
						name5,
					]
				},
				
				tooltip: {
					headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
						'<td style="padding:0"><b>{point.y}</b></td></tr>',
					footerFormat: '</table>',
					shared: true,
					useHTML: true
				},
				plotOptions: {
					column: {
						pointPadding: 0.2,
						borderWidth: 0
					}
				},
				series: [{
					name: 'Pass',
					data: [p1, p2, p3, p4, p5]
		
				}, {
					name: 'Fail',
					data: [f1,f2, f3, f4, f5]
		
				}, {
					name: 'Skip',
					data: [s1,s2,s3,s4,s5]
		
				}]
			});
	//	});
 }

 function createBarChartWeb(p1,f1,s1,p2,f2,s2,p3,f3,s3,p4,f4,s4,p5,f5,s5,name1,name2,name3,name4,name5){
     //Multibar chart Function
	//	$(function () {
			$('#webBarGraph').highcharts({
				chart: {
					type: 'column'
				},
				title: {
					text: 'Web test result comparision'
				},
				subtitle: {
					text: ''
				},
				xAxis: {
					categories: [
						name1,
						name2,
						name3,
						name4,
						name5,
					]
				},
				
				tooltip: {
					headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
						'<td style="padding:0"><b>{point.y}</b></td></tr>',
					footerFormat: '</table>',
					shared: true,
					useHTML: true
				},
				plotOptions: {
					column: {
						pointPadding: 0.2,
						borderWidth: 0
					}
				},
				series: [{
					name: 'Pass',
					data: [p1, p2, p3, p4, p5]
		
				}, {
					name: 'Fail',
					data: [f1,f2, f3, f4, f5]
		
				}, {
					name: 'Skip',
					data: [s1,s2,s3,s4,s5]
		
				}]
			});
	//	});
 }

 function createPieChart(p,f,s,name){
	// $(function () {
		
		$('#webBarGraph').highcharts({
			chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: 'PASS FAIL INFORMATION'
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						format: '<b>{point.name}</b>: {point.percentage:.1f} %'
					}
				}
			},
			series: [{
				type: 'pie',
				name: 'PASS-FAIL-INFORMATION',
				data: [
					['PASS',   p],
					['FAIL',   f],
					['SKIP',   s] 
					 
				]
			}]
		});
	//});
}
   
   

    
 </script>
