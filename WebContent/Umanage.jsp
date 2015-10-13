<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="header.jsp" %>

<%
ResultSet result = (ResultSet)request.getAttribute("result");
int noOfPages = (Integer)request.getAttribute("noOfPages");
int currentPage = (Integer)request.getAttribute("currentPage");
int i;

%>



<div class="cart-items">
	<div class="container">
			 <h3 class="tittle">Managing Acounts</h3>
			

		<% if (result != null) { do { %>
		<div class="cart-header">
		<div class="cart-sec simpleCart_shelfItem">
		<% String li = "Udisplay.jsp?name="+result.getString(1);
		   
		%>
		
			
			<form action="Udisplay.jsp" method="get" target="_blank"><a href=<%=li%> > <%= result.getString(1)%> </a> </form>
		</div>
		</div>
		<%	} while (result.next()); } %>
	</div>
			 		 <div class="clear"></div>
	<ul class="pagination"><li><a class="current">Page <%=currentPage %> of <%=noOfPages %></a></li>
	<% if (currentPage != 1) { %>
        <li><a href="ManageUsers?page=<%=currentPage-1 %>">&lsaquo; Previous</a></li>
	<% } %>
            <%for (i=1;i<=noOfPages;i++) { %>
            	<% if (currentPage == i) {%>
                        <li><a class="current"><%=i%></a></li>
                <% } else if (currentPage-1 == i || currentPage+1 == i || i == noOfPages || i == 1 || currentPage+2 == i || currentPage-2 == i){ %>
                		<%if (currentPage-2 == i && currentPage != 3 ) { %>
                			<li><span style="color:black">...</span></li>
                		<%} %>
                        <li><a href="ManageUsers?page=<%=i%>"><%=i %></a></li>
                        <%if (currentPage+2 == i  && noOfPages != 3) { %>
                		<li><span style="color:black">...</span></li>
                <%} } }%>

    
    <% if (currentPage < noOfPages) { %>
        <li><a href="ManageUsers?page=<%=currentPage+1%>">Next &rsaquo;</a></li>
	<% } %>
	</ul>
<div class="clear"></div>
	</div>


<%@include file="footer.jsp" %>