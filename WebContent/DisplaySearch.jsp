<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "java.sql.ResultSet" %>
<%@include file="header.jsp" %>

<%	ResultSet result = (ResultSet)request.getAttribute("result");
	ResultSet categories = (ResultSet)request.getAttribute("categories");
	ResultSet countries = (ResultSet)request.getAttribute("countries");
	int noOfPages = (Integer)request.getAttribute("noOfPages");
	int currentPage = (Integer)request.getAttribute("currentPage");
	String name = (String)request.getAttribute("name");
	String category = (String)request.getAttribute("category");	
	String price = (String)request.getAttribute("price");
	String country = (String)request.getAttribute("country");
	if (country == null)
		country = "All";
	if (price == null)
		price = "All";
	String cou = null;
	String cat = null;
	String x = null;
	String y = null;
	String pr = null;
	int i=0,price1=0,price2=0,j=0;

	int idIt = 0;
	int flag = 0;
	int counter = 0;
	int flag2 = 0;

	String link = null;
	if (result != null) {
%>
	<div class="products">
		<div class="container">
			<div class="products-grids">
				<div class="col-md-8 products-grid-left">
				 <% do { if (counter % 3 == 0) {%>
					<div class="products-grid-lft">
					<% } %>
	 					<div class="products-grd">
							<div class="p-one simpleCart_shelfItem prd">
								<%idIt = result.getInt("idItem"); link ="itemlist?id="+idIt;%>
								<a href=<%=link%>>
										<img src=<%=result.getString("Image") %> alt="" class="img-responsive" />
								</a>
								<h4><%=result.getString("Name")%></h4>
								<p><a class="item_add" href="#"><i class="glyphicon glyphicon-shopping-cart"></i> <span class=" item_price valsa">
								<%=result.getDouble("Currently")%>  &euro;</span></a></p>
								<div class="pro-grd">
									<a href=<%=link%>>Quick View</a>
								</div>
							</div>	
						</div>
					<%  if (counter % 3 == 2) {flag2 = 1; %>
					<div class="clearfix"> </div>
					</div>
					<% } else flag2 = 0; %>
				<% counter ++;} while (result.next());%>
				<% if (flag2 == 0) { %>
					<div class="clearfix"> </div>
					</div>
				<% } %>

				</div>
				<div class="col-md-4 products-grid-right">
					<div class="w_sidebar">
						<section  class="sky-form">
							<h4>Categories</h4>
							<div class="row1 scroll-pane">
								<div class="col col-4">
									<%if (category.equals("All")) { %>
									<label class="radio"><input type="radio" name="category" checked=""><i></i>All</label>
									<%} else {	y = "searchS?name="+name+"&category=All"+"&price="+price+"&country="+country;
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="category" onclick =<%=x%>><i></i>All</label>
										<% }%>
									<% while (categories.next()) { %>
										<% cat = (String)categories.getString(1); if (category.equals(cat)) { %>
										<label class="radio"><input type="radio" name="category" checked=""><i></i><%=cat%></label>
										<%} else {  y = "searchS?name="+name+"&category="+cat+"&price="+price+"&country="+country;
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="category"  onclick =<%=x%>><i></i><%=cat%></label>
										<% } }%>
								</div>
							</div>
						</section>
						<section  class="sky-form">
							<h4>Price</h4>
							<div class="row1 scroll-pane">
								<div class="col col-4">
									<%if (price == null || price.equals("All")) { %>
									<label class="radio"><input type="radio" name="price" checked=""><i></i>All</label>
									<%} else {	y = "searchS?name="+name+"&category="+category+"&price=All"+"&country="+country;
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="price" onclick=<%=x%>><i></i>All</label>
										<% }%>
									<% i=0;while (i<6) {
										  if (i!=5)
											  price2 = price1+100;
										  else
											  price2 = price1+500;
										  pr = String.valueOf(price1)+"-"+String.valueOf(price2); %>
										<% if (price != null && price.equals(pr)) { %>
										<label class="radio"><input type="radio" name="price" checked=""><i></i><%=pr+" "%>&euro;</label>
										<%} else {  y = "searchS?name="+name+"&category="+category+"&price="+pr+"&country="+country;
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="price"  onclick=<%=x%>><i></i><%=pr+" "%>&euro;</label>
										<% } 	i++;
										price1 = price1+100;}%>																						
								</div>
							</div>
						</section>
						<section  class="sky-form">
							<h4>Countries</h4>
							<div class="row1 scroll-pane">
								<div class="col col-4">
									<%if (country == null || country.equals("All")) { %>
									<label class="radio"><input type="radio" name="country" checked=""><i></i>All</label>
									<%} else {	y = "searchS?name="+name+"&category="+category+"&price="+price+"&country=All";
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="country" onclick =<%=x%>><i></i>All</label>
										<% }%>
									<% while (countries.next()) { %>
										<% cou = (String)countries.getString(1); if (country != null && country.equals(cou)) { %>
										<label class="radio"><input type="radio" name="country" checked=""><i></i><%=cou%></label>
										<%} else {  y = "searchS?name="+name+"&category="+category+"&price="+price+"&country="+cou;
										x = "document.location.href='"+y+"'";%>
										<label class="radio"><input type="radio" name="country"  onclick =<%=x%>><i></i><%=cou%></label>
										<% } }%>
								</div>
							</div>
						</section>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
<div class="clear"></div>
	<ul class="pagination"><li><a class="current">Page <%=currentPage %> of <%=noOfPages %></a></li>
	<% if (currentPage != 1) { %>
        <li><a href="searchS?name=<%=name%>&category=<%=category%>&price=<%=price%>&country=<%=country%>&page=<%=currentPage-1 %>">&lsaquo; Previous</a></li>
	<% } %>
            <%for (i=1;i<=noOfPages;i++) { %>
            	<% if (currentPage == i) {%>
                        <li><a class="current"><%=i%></a></li>
                <% } else if (currentPage-1 == i || currentPage+1 == i || i == noOfPages || i == 1 || currentPage+2 == i || currentPage-2 == i){ %>
                		<%if (currentPage-2 == i && currentPage != 3 ) { %>
                			<li><span style="color:black">...</span></li>
                		<%} %>
                        <li><a href="searchS?name=<%=name%>&category=<%=category%>&price=<%=price%>&country=<%=country%>&page=<%=i%>"><%=i %></a></li>
                        <%if (currentPage+2 == i  && noOfPages != 3) { %>
                		<li><span style="color:black">...</span></li>
                <%} } }%>

    
    <% if (currentPage < noOfPages) { %>
        <li><a href="searchS?name=<%=name%>&category=<%=category%>&price=<%=price%>&country=<%=country%>&page=<%=currentPage+1%>">Next &rsaquo;</a></li>
	<% } %>
	</ul>
<div class="clear"></div>
	</div>
    <%} else { %>
    <div class="container">
    <center><h2><i>Item not found</i></h2></center>
    </div>
    <% } %>
<%@include file="footer.jsp" %>