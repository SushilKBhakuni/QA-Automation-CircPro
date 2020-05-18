<%@page import="java.io.File"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.util.Collection"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="org.apache.commons.io.filefilter.DirectoryFileFilter"%>
<%@page import="org.apache.commons.io.filefilter.RegexFileFilter"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<%
    String testBasePath ="";
	if(session.getAttribute("testCaseBasePath") == null){
		response.sendRedirect("index.jsp");
	}else{
		testBasePath = (String) session.getAttribute("testCaseBasePath");
	}
	String path = testBasePath+"//Archive//";
	File folder = new File(testBasePath+"//Archive");
	File[] listOfFiles = folder.listFiles();
	ArrayList<Object> files = new ArrayList<Object>(); 
	ArrayList<String> listOfFiles1 = new ArrayList<String>();
	ArrayList<String> listOfFiles2 = new ArrayList<String>();
	int length=listOfFiles.length;
	//*****************************
	if(length == 0) {
		response.sendRedirect("error.jsp");
	}
	//*****************************
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isDirectory()) {
    	  listOfFiles1.add(listOfFiles[i].getName());
      }
    }	
      
        Collection filess = FileUtils.listFiles(
                   new File(path), 
        		   new RegexFileFilter("(?i).*index_.*"), 
        		   DirectoryFileFilter.DIRECTORY);
        
           for (Object obj : filess) {
        	    files.add(obj);
        	    }
    %>
   
<jsp:include page="header.jsp"/>
<div class="body-container">
  <div class="layout">
  <h2 class="bdrbtm">Archive History Display:</h2>
  <% 
  for (int i = 0; i <=listOfFiles1.size(); i++) {
  	if(listOfFiles1.isEmpty())
  	{%>
  	<h4><p>Not Error!! But Archive the files first before selecting the Archive History tab...</p></h4>
  	 <% 
  	}
    }
  %>
  <p  class="text-warning" id="result"></p>
		<form method="post" class="fullwidth" id="testDetailForm" name="testDetailForm">
	 	<table width="50%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	 	<tr>
	 	<th>&nbsp;</th>
	 	<th>S No.</th>
	 	<th>File Name</th>
	 	</tr>
	    <% for (int i = 0; i < listOfFiles1.size(); i++) { %>
	    <tr class="info"> 
	    <td class='accordianimg' align="center"><img src='images/plusicon.png' id='plus<%=i%>' width='20' height='20' onclick="expand('<%=i%>');" > <img id='minus<%=i%>' src='images/minus-icon.png' width='20' height='20' onclick="collapse('<%=i%>');"  style='display:none'>
	    </td>
	     <td align="center"><%= i+1+"."%></td>
	    <td align="center">
	    <%= listOfFiles1.get(i)%>
	   </td>
	    </tr>
	    <tr>
	    <td colspan=8 style='display:none' class='whitebg' id='tblTS<%=i%>' >
	    <table width="100%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	    <tr>
	 	<th>S No.</th>
	 	<th>Index Report</th>
	 	</tr>
	    <tr class="info">
	    <td align="center">1.</td>
	    <td align="center">
	    	<% 
	    	out.print("<a target='_blank' href='"+"file:///"+String.valueOf(files.get(i))+"'>Report</a>");
	    	%>
	    	</td>
	    	</tr>
	    	</table>
	  <% // } %>  
	    </td>
	    </tr>
	   <% } %>
	    </table>
	    </form>
	    </div>
	    </div>
	    
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
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

$(function () {
	$("#desktop").removeClass("active");
	$("#dashboard").removeClass("active");
    $("#mobile").removeClass("active");
    $("#web").removeClass("active");
    $("#setting").removeClass("active");
    $("#manage").removeClass("active");
	$("#report").addClass("active");
});
</script>
<jsp:include page="footer.html"/>    