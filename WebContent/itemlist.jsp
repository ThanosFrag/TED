<%@include file="header.jsp" %>
<%@ page import="java.text.ParseException,java.text.SimpleDateFormat,java.util.Date,java.util.regex.Matcher,java.util.regex.Pattern"%>
<%!
public String check(String s,String e){
	 String dateformats[] = {"","yyyy-MM-dd HH:mm:ss", "yyyy-M-d HH:mm:ss","MMM-dd-y HH:mm:ss"};	
	 Date date = new Date();
     if (s != null) {
         for (String parse : dateformats) {
             SimpleDateFormat sdf = new SimpleDateFormat(parse);
             try {
                 if (sdf.parse(s).after((date))){
                	 return "Not Active";
                 }
             } catch (ParseException e1) {
				
             }
         }
     }	
     if (e != null) {
         for (String parse : dateformats) {
             SimpleDateFormat sdf = new SimpleDateFormat(parse);
             try {
                 if (sdf.parse(e).before(date)) return "Item is bought";
             } catch (ParseException e2) {
				
             }
         }
     }	     
	return null;
}


%>

<%
 
 ResultSet result = (ResultSet)request.getAttribute("Iteminfo");
 ResultSet user_res = (ResultSet)request.getAttribute("Userinfo");
 int Iid = result.getInt("idItem");
 int Uid = result.getInt("idUser");
 
 ResultSet category = (ResultSet)request.getAttribute("category");
 String categories = "";
 while (category.next()){
	 categories = categories +" " + category.getString("CategoryName");
 }
 double price = result.getDouble("Currently");
 double buy = result.getDouble("BuyPrice");
 
 String available = check(result.getString("Started"),result.getString("Ended"));
 int id;
 %>
 <!--start-content-->
<!-- products -->
	<div class="products">
		<div class="container">
			<div class="products-grids">
				<div class="col-md-12 products-single">
				<div class="col-md-5 grid-single">		
						<div class="flexslider">
							  <ul class="slides">
								<li data-thumb="<%=result.getString("Image") %>">
									<div class="thumb-image"> <img src="<%=result.getString("Image") %>" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li>
								<li data-thumb="<%=result.getString("Image") %>">
									 <div class="thumb-image"> <img src="<%=result.getString("Image") %>" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li>
								<li data-thumb="<%=result.getString("Image") %>">
								   <div class="thumb-image"> <img src="<%=result.getString("Image") %>" data-imagezoom="true" class="img-responsive" alt=""/> </div>
								</li> 
							  </ul>
						</div>
						<!-- FlexSlider -->
						<script src="js/imagezoom.js"></script>
						<script defer src="js/jquery.flexslider.js"></script>
						<script>
						// Can also be used with $(document).ready()
						$(window).load(function() {
						  $('.flexslider').flexslider({
							animation: "slide",
							controlNav: "thumbnails"
						  });
						});
						</script>

					</div>	
				<div class="col-md-7 single-text">
					<div class="details-left-info simpleCart_shelfItem">
						<h3><%=result.getString("Name") %></h3>				
						<div class="price_single">
							<% if (buy>0.0 && available==null && session.getAttribute("login")!=null) { 
								id = Integer.parseInt((String)session.getAttribute("login"));
							
							%>
							<p class="availability">Buy: <span class="actual item_price">&euro;<%=+buy %></span>
							<% String link = "Addbid?id="+Iid+"&user="+id+"&amount="+buy; %>
							<a href=<%=link%> >click to buy</a></p>	
							<%} %>						
							<span class="actual item_price">&euro;<%=price  %></span>
						</div>
						<ul class="size">
						<li><h2 class="quick">Categories: </h2><%=categories %></li>
						</ul>
						<ul class="size">
							<li><h2 class="quick">Started: </h2><%=result.getString("Started") %></li>
							<li><h2 class="quick">Ends: </h2><%=result.getString("Ended") %></li>
						</ul>
					<div class="clearfix"> </div>
				<div class="single-but item_add">
			  	<%if (session.getAttribute("login")!=null && available==null ) {
			  		id = Integer.parseInt((String)session.getAttribute("login"));
			  	%>
			  	<form action="Addbid" method="post">
			  	<input type="text" name="bid"  id="bid">
			  	<input type="hidden" name="uid" value=<%=id%>>
			  	<input type="hidden" name="iid" value=<%=Iid%>> 
			  	<input type="hidden" name="currently" value=<%=price%>> 
			  	<input type="hidden" name="buy" value=<%=buy%>> 
			  	<button type="submit" onclick="return confirmComplete(<%=price %>)">Bid</button>
			  	
			  	</form>
			  	
			  	<%} else if (available!=null){%><%=available%><%}%>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="col-md-4 products-grid-right">
				</div>
				<div class="clearfix"></div>
				<!-- collapse -->
    <div class="panel-group collpse" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          Description
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
     <%=result.getString("Description") %>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
           Location
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
      

    

	   <%if (result.getString("Longtitude")!=null && result.getString("Latitude")!=null) {%>
	 <div id="map"><iframe width="100%" height="550" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/?ie=UTF8&amp;t=m&amp;daddr=<%=result.getString("Latitude")%>,<%=result.getString("Longtitude")%>&amp;spn=0.018486,0.027466&amp;z=15&amp;output=embed"></iframe>
	</div>
	 
	 <% }%>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          User Info
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body">
    	<div class="megapanel">
					<div class="row">
						<div class="col1">
							<div class="h_nav">
								<h4>Username</h4>
								<ul>
									<li><%=user_res.getString("Username") %></li>
									</ul>
							</div>
						</div>
						<div class="col1">
							<div class="h_nav">
								<h4>User Rating</h4>
								<ul>
									<li><%=user_res.getInt("UserRating")%></li>
									</ul>
							</div>
						</div>												
					</div>
					<div class="row">
						<div class="col1"></div>
						<div class="col1"></div>
					</div>
	
	
		</div>
					
      </div>
    </div>
  </div>

</div>
</div>
</div>
</div>
<!-- collapse -->
<%@include file="footer.jsp" %>