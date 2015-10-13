<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="header.jsp" %>

<%@ page import = "ted.Driver,java.sql.*" %>
<div class="container">
 <%
 Driver db = new Driver();
 db.Connect();
 
 String Name = request.getParameter("name");
 ResultSet result = db.listUser(0,0,1,Name);
 
 %>

UserName : <%=result.getString(1) %> <br>
Email :	<%=result.getString(2) %>   <br>

<% if (result.getInt(3) == 0) { %>
	Do you want to approve this user? <br>
	<form  method="post">	
	 <input type="submit" value="Yes" name="submit"
	 	<% String x = request.getParameter("submit");
			if(x!=null && x.equals("Yes")) {
				db.listUser(0,0,2, Name);
				response.sendRedirect("Umanage.jsp");
			} 
		%>	
	> 
	 <input type="submit" value="No" name="submit"
	 	<%  x = request.getParameter("submit");
			if(x!=null && x.equals("No")) {
				db.listUser(0,0,3, Name);
				response.sendRedirect("Umanage.jsp");
			} 
		%>
	> 
	</form>
<% } %>	
<%db.Close(); %>
<a href="index.jsp">Go Back</a>
</div>
<%@include file="footer.jsp" %>