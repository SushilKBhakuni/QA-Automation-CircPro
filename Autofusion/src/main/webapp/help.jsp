<%@page import="java.sql.ResultSet"%>
<%@page import="com.autofusion.bean.CommonUtility"%>
<jsp:include page="header.jsp"/>
<div class="body-container">
  <div class="layout">
  <h2 class="bdrbtm">Help File</h2>
	<p  class="text-warning" id="result"></p>
	 	<table width="100%" align="center" class="tableouter marginbtm20" cellspacing='0' cellpading='0'>
	    <tr> 
	       <th width="25">&nbsp;</th>
	       <th width="40">Sno.</th>
	       <th width="150">Keyword</th>
	       <th width="150">Description</th>
		</tr>		 
		
		<%
			ResultSet rs = CommonUtility.readKeywords();
	     	if(rs != null){
	    	  while(rs.next()){ 

		%> 
				<tr> 
			       <td width="25">&nbsp;</th>
			       <td width="40"><%=rs.getInt("sno") %>.</th>
			       <td width="150"><%=rs.getString("keywords") %></td>
			       <td width="150"><%=rs.getString("description") %></td>
				</tr>
		
		<% } 
		}%>
				 
	 </table>
	
	 
  
</div>
</div>
</body>

<jsp:include page="footer.html"></jsp:include>