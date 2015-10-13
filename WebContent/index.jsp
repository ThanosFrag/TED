<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.sql.ResultSet,java.util.*"%>
    
<%@include file="header.jsp" %>



<% 

boolean Login = false , Conf=true;

String user = (String)session.getAttribute("name");
String conf = (String)session.getAttribute("login");

String admin = (String)session.getAttribute("admin");
if (user!=null)
	Login=true;
else
	user = "user";

if (Login==true && conf==null){
	Conf = false;
}
else
	Conf = true;


		
%> 
<div class="container">
<h1>Welcome Dear <%=user %></h1>

<% if (!Conf && Login) { %>
	<h1>Your Account is under Validation Process</h1>


<%} %>
</div>


<br><br><br><br><br><br><br><br><br>
<% 
int counter = 0;
Driver db = new Driver();
db.Connect();
ResultSet result=null;
String link = null;
int flag2=0;
int i=0;
int idIt;
ArrayList<Integer> reccomended = (ArrayList<Integer>)request.getAttribute("recs");
if (reccomended != null) {
int size = reccomended.size();
if (size > 9)
	size = 9;
if (size != 0) {
%>					
<div class="recommand-section-head text-center">
	<h3 class="tittle fea">Recommended Products</h3>
</div>
<% } %>
	<div class="products">
		<div class="container">
			<div class="products-grids">
				<div class="col-md-10 products-grid-left">
				 <% for (i=0;i<size;i++) { if (counter % 3 == 0) {%>
					<div class="products-grid-lft">
					<% } %>
	 					<div class="products-grd">
							<div class="p-one simpleCart_shelfItem prd">
								<%idIt = reccomended.get(i); link ="itemlist?id="+idIt;	result = db.getItem(Integer.toString(idIt)); result.next();%>
								<a href=<%=link%>>
										<img src=<%=result.getString("Image") %> class="img-responsive" />
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
				<% counter ++;}%>
				<% if (flag2 == 0) { %>
					<div class="clearfix"> </div>
					</div>
				<% } %>
				</div>
				</div>
				</div>
<% db.Close(); }%>
<br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@include file="footer.jsp" %>