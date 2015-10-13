<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
	<div class="container">
		<%if (session.getAttribute("message") != null ) {	%>
		
		
		
		<center><i><%= session.getAttribute("message")%></i></center>
		<% session.setAttribute("message", null); } else{
			response.sendRedirect("index.jsp");		
		}%>
		
		
		<center><a href="index.jsp">Go Back</a></center>
	</div>
<%@include file="footer.jsp" %>