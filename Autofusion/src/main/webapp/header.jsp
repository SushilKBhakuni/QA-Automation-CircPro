<%@page import="com.autofusion.util.InitClass"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Automation Testing Tool</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<!-- <script type="text/javascript" src="js/jquery-1.7.1.js" /></script>
 --> <script type="text/javascript" src="js/jquery-1.12.3.js"></script>

<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/exporting.js"></script>
</head>

<body>
<div class="header">
  <div class="container" > <a href="#" class="fl"><img src="images/logo-login.png" alt="" /></a>
    <ul class="topnav">
      <li><strong><%=session.getAttribute("userName") %></strong>, <span><%=session.getAttribute("projectName") %></span></li>
      <li><a href="setting.jsp">Setting</a></li>
      <li><a href="help.jsp" taget='_Blank'>Help</a></li>
      <li><a href="index.jsp">Log Out</a></li>
    </ul>
    <ul class="mainmenu" >
      
      <% if(request.getParameter("Header") != null){
    		  if(request.getParameter("Header").equals("active")) %>
    			<li><a href="dashboard.jsp" id='dashboard'><span class="menu1"></span>Dashboard</a></li>
    			<% if(InitClass.USER_CONFIG.getProperty("webMenu").equals("on")){ %>
      				<li><a href="testDetail.jsp?device=web" id="web"><span class="menu2"></span>Web</a></li>
      			<% }
      			 if(InitClass.USER_CONFIG.getProperty("mblMenu").equals("on")){ %>
      			<li><a href="mblTestDetail.jsp?device=mobile" id="mobile" ><span class="menu3"></span>Mobile</a></li>
      			<% }
      			if(InitClass.USER_CONFIG.getProperty("apiMenu").equals("on")){ %>
    	 		<li><a href="apiTestDetail.jsp?device=api" id="api" ><span class="menu3"></span>API</a></li>
      			<% }%>
      			<% if(InitClass.USER_CONFIG.getProperty("desktopMenu").equals("on")){ %>
      			<li><a href="testDetail.jsp?device=window" id="desktop"><span class="menu4"></span>Desktop</a></li>
      			<% }%>
      			<% if(InitClass.USER_CONFIG.getProperty("manageMenu").equals("on")){ %>
      			<li><a href="addTestCase.jsp" class="active" id="manage"><span class="menu5"></span>Manage</a></li>
      			<% }%>
    <% } else {%>
    	 	<li><a href="dashboard.jsp" class="active" id='dashboard'><span class="menu1"></span>Dashboard</a></li>
    	 	<% if(InitClass.USER_CONFIG.getProperty("webMenu").equals("on")){ %>
    	 	<li><a href="testDetail.jsp?device=web" id="web"><span class="menu2"></span>Web</a></li>
    	 	 <%}
    	 	if(InitClass.USER_CONFIG.getProperty("mblMenu").equals("on")){ %>
    	 	<li><a href="mblTestDetail.jsp?device=mobile" id="mobile" ><span class="menu3"></span>Mobile</a></li>
      		<% }
      		if(InitClass.USER_CONFIG.getProperty("apiMenu").equals("on")){ %>
    	 	<li><a href="apiTestDetail.jsp?device=api" id="api" ><span class="menu3"></span>API</a></li>
      		<% }
      		 if(InitClass.USER_CONFIG.getProperty("desktopMenu").equals("on")){ %>
      		<li><a href="testDetail.jsp?device=window" id="desktop"><span class="menu4"></span>Desktop</a></li>
      		<% }%>
      		<% if(InitClass.USER_CONFIG.getProperty("manageMenu").equals("on")){ %>
     		<li><a href="addTestCases.jsp" id="manage"><span class="menu5"></span>Manage</a></li>
	    	 <% }
	    }%>
      
      <!-- li><a href="#"><span class="menu6"></span>Test Suits</a></li-->
      <li><a href="reportwithgraph.jsp" id="report"><span class="menu7"></span>Reports</a></li>
<!--        <li><a href="manageReport.jsp" id="managereports"><span class="menu8"></span>Manage Report</a></li> -->
<!--        <li><a href="archiveHistory.jsp" id="history"><span class="menu9"></span>History</a></li> -->
    </ul>
  </div>
</div>

<script type="text/javascript">
function disableBackButton()
{
	//window.location="index.jsp";
	//window.history.forward(0);

}
setTimeout("disableBackButton()", 0);
/*
 $('#menuId li a').on('click', function(){
	
    $('li a.active').removeClass('active');
    $(this).addClass('active');
});
 */
</script>
 