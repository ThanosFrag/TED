<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="header.jsp" %>
<% 

boolean Login = false , Conf=true;

String user = (String)session.getAttribute("name");
String conf = (String)session.getAttribute("login");

String admin = (String)session.getAttribute("admin");
if (conf==null || admin==null){
	response.sendRedirect("index.jsp");
	return;
}


		
%> 

	<div class="container">
				
		<form name="manager" method="get" action="ManageUsers">
		<div class="address new">
		<input type="submit" value="Manage Users">
		</div>
		</form>
							
		<form name="GenerateXml" method="post" action="GenerateXml">
		<div class="address new">
		<input type="submit" value="Generate Xml Files!!">
		</div>
		</form>
									
							
	<h4>
	Import Items: </h4>	
	<form name="import" action="ImportItems" method="post" enctype="multipart/form-data">
	<div class="address">
	<input type="file" multiple="multiple" name="importxml" required></div>
	<div class="address new">
	<input type="submit" value="import"></div>
	</form>
							
													
						
				
					
					
		</div>
	<div class="clearfix"></div>	
<%@include file="footer.jsp" %>
