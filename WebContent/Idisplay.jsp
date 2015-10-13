<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="header.jsp" %>

	
 <%
 int i=1;
 String link=null;
 ResultSet result = (ResultSet)request.getAttribute("result");
int noOfPages = (Integer)request.getAttribute("noOfPages");
int currentPage = (Integer)request.getAttribute("currentPage");
 
 if	(result==null || session.getAttribute("login")==null){
		%>
		<script>alert("You need to login in order to view your Items!");
		window.location = "/TED/index.jsp";</script>
		<% 
	}
 int counter=1;
 int items=0;
 while (result.next()) items++;
 result.beforeFirst();
 %>

 <!--start-content-->
<!-- checkout -->
<div class="cart-items">
	<div class="container">
			 <h3 class="tittle">My Active items(<%=items %>)</h3>
			 
			 
			 <% String cart=null;String edit=null;
			 	while (result.next()){
				 link ="itemlist?id="+result.getString("idItem"); 
				 String close = "close"+counter;
				 edit = "edit"+counter;
				 if (counter>1)
				  cart = "cart-header"+counter;
				 else
				  cart = "cart-header";
				 counter++;
	%> 
			 
			   
			 <div class=<%=cart%>>

				 <div class="cart-sec simpleCart_shelfItem">
						<div class="cart-item cyc">
						<a href=<%=link%>>
							 <img src=<%=result.getString("Image")%> class="img-responsive" alt="">
						</a>
						</div>
						<div class="cart-grd">
							<a href=<%=link%>>Quick View</a>
						</div>
					   <div class="cart-item-info">
						<h3><a href=<%=link%>> <%=result.getString("Name") %></a><span>Item id: <%=result.getInt("idItem")%></span></h3>
							 <div class="delivery">
							 <p>Currently : <%="$"+result.getDouble("Currently") %></p>
							 <span>Ends <%=result.getString("Ended") %></span>
							 <div class="clearfix"></div>
				        </div>	
					   </div>
					   <div class="clearfix"></div>
											
				  </div>
			 </div>
			<%} %>		
		 </div>
		 		 <div class="clear"></div>
	<ul class="pagination"><li><a class="current">Page <%=currentPage %> of <%=noOfPages %></a></li>
	<% if (currentPage != 1) { %>
        <li><a href="Getitem?page=<%=currentPage-1 %>">&lsaquo; Previous</a></li>
	<% } %>
            <%for (i=1;i<=noOfPages;i++) { %>
            	<% if (currentPage == i) {%>
                        <li><a class="current"><%=i%></a></li>
                <% } else if (currentPage-1 == i || currentPage+1 == i || i == noOfPages || i == 1 || currentPage+2 == i || currentPage-2 == i){ %>
                		<%if (currentPage-2 == i && currentPage != 3 ) { %>
                			<li><span style="color:black">...</span></li>
                		<%} %>
                        <li><a href="Getitem?page=<%=i%>"><%=i %></a></li>
                        <%if (currentPage+2 == i  && noOfPages != 3) { %>
                		<li><span style="color:black">...</span></li>
                <%} } }%>

    
    <% if (currentPage < noOfPages) { %>
        <li><a href="Getitem?page=<%=currentPage+1%>">Next &rsaquo;</a></li>
	<% } %>
	</ul>
<div class="clear"></div>
		 </div>
      <!--//checkout-->
<%@include file="footer.jsp" %>