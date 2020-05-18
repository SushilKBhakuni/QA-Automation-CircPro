<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Automation Testing Tool</title>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery-1.7.1.js" /></script>

<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/exporting.js"></script>
</head>

<body>
<div class="header">
  <div class="layout" > <a href="#" class="fl"><img src="images/logo-login.png" alt="" /></a>
    <ul class="topnav">
      <li><strong><%=session.getAttribute("userName") %></strong>, <span><%=session.getAttribute("projectName") %></span></li>
      <li><a href="setting.jsp">Setting</a></li>
      <li><a href="index.jsp">Log Out</a></li>
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
  