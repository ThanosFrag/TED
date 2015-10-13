<!DOCTYPE HTML>
<html>
<head>
<%@ page import = "ted.Driver,java.sql.*,javax.imageio.ImageIO,java.io.ByteArrayInputStream" %>
<title>Ebay Shop</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8 ; charset=iso-8859-15" />
<meta name="keywords" content="Nuevo Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="applijegleryion/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />

<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' />	
<link href="css/style2.css" rel='stylesheet' type='text/css' />	
<script src="js/jquery-1.11.1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script src="js/menu_jquery.js"></script>
<script src="js/simpleCart.min.js"> </script>
<script src="js/bootstrap.js"> </script>
<script src="js/ts_picker.js"> </script>
<script src="https://maps.googleapis.com/maps/api/js"></script>
<script src="main.js" type="text/javascript"></script>
<!--web-fonts-->
 <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,300italic,600,700' rel='stylesheet' type='text/css'>
 <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:300,400,700' rel='stylesheet' type='text/css'>
<!--//web-fonts-->
<script src="js/modernizr.custom.js"></script>
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
	<link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" />

<!--/script-->
<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},900);
				});
			});
</script>
<!-- the jScrollPane script -->
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
		<script type="text/javascript" id="sourcecode">
			$(function()
			{
				$('.scroll-pane').jScrollPane();
			});
		</script>
<!-- //the jScrollPane script -->
</head>
<body>
<!--start-home-->
<!-- header_top -->
<div class="top_bg" id="home">
	<div class="container">
		<div class="header_top">
			<div class="top_right">
				<ul>
					<li><a href="#">help</a></li>
					<li><a href="SendMsg">Contact</a></li>
					<li><a href="#">Delivery information</a></li>
				</ul>
			</div>
			<div class="top_left">
				<%if (session.getAttribute("name") == null ) {%>
				<ul class="megamenu skyblue">
				<li><a class="color1" href="/TED/register.jsp">Register</a></li>
				
				
				<li class="grid"><a class="color1" href="#"> Login </a>
				<div class="megapanel">
					<div class="col1">
					<div class="h_nav">
						<h4>Login</h4>
						
									<form action=login method=post>
		<%	int user;
			if (request.getAttribute("username")!=null) {
		%>
		<script>alert("Wrong UserName or Password!");</script>
		
		<%} %>
		<br>
		UserName: <input type="text" name="name"required>
		<br>
		Password: <input type="password" name="password"required>
		<br>
		<div class="address new">
		<input type="submit" value="Login"/>
		</div>
						</form>	
						
					
						
					</div>	
					</div>						
				
				</div>
				</ul>
				<%}else {%>
				<ul class="megamenu skyblue">
				<li><a class="color1" href="logout">Logout</a></li>	
				</ul>			
				<%}%>	
			</div>
				<div class="clearfix"> </div>
		</div>
	</div>
</div>
<!-- header -->
<div class="header_bg">
   <div class="container">
	<div class="header">
	  <div class="head-t">
		 <div class="logo">
			  <a href="index.jsp"><h1>Ebay<span>Shop</span></h1> </a>
		  </div>
		  <div class="header_right">
		  
		  <%if (session.getAttribute("name") != null ) {%>
		 	<ul class="megamenu skyblue">
		 		<li class="grid"><a class="color2" href="#">My Account</a>
				<div class="megapanel">
				<div class=row">
				<div class="col6">
					<div class="h_nav">
						<h4>Manage Account</h4>
						<ul>
							<li><a href="./additem.jsp">Add Item</a></li>
							<li><a href="Getitem">Active Items</a></li>
							<li><a href="GetInactiveitem">Inactive Items</a></li>
							<li><a href="Messages?type=Inbox">Inbox</a></li>
						</ul>	
					</div>							
				</div>
				</div>
					   <div class="row">
						<div class="col2"></div>
						<div class="col6"></div>
					</div>
					</div>
				</li>
		 	</ul>
		</div>
		<%} %>
		<div class="clearfix"></div>	
	    </div>
	    </div>
	    
	    <%if (session.getAttribute("login")!=null){
	    request.getRequestDispatcher("CheckNewMsg").include(request,response);
	    int messages = Integer.parseInt((String)session.getAttribute("messages"));
	    if (messages>0){%>
	      <center><i>You have <%=messages %> unread messages!</i></center>	
	    <%}
	    
	    }%>
		<!--start-header-menu-->
		<ul class="megamenu skyblue">
		
		
			<li class="active grid"><a class="color1" href="index.jsp">Home</a></li>
			<%if (session.getAttribute("admin")!=null) {%>
				<li class="grid"><a class="color2" href="adminpanel.jsp">Admin Panel</a></li>
		
									
			
			<%} %>
			<li class="active grid right"><a class="color3" href="#" onclick="document.getElementById('Search').submit();">Search</a></li>
			<li class="right">
					<form name="Search" id="Search" action="searchS" method="get">
					<%     
					Driver db1 = new Driver();
					String option1;
					db1.Connect();
					ResultSet result1 = db1.ReturnCategories();
					
					if (result1!=null)  {
					
					%>
					<select name="category">
					<option value="All"> All</option>
					<% 
					 while (result1.next()){
						 option1 = result1.getString(1);
						 %>
					 	
					 <option value=<%=option1%>> <%=option1%></option>
					
					<% } }%>
					</select>
					  <input type="search" name="name"required>
					 </form></li>
					  
					 
					 
					
			
			
		</ul>
		<% db1.Close(); %>

</div>
</div>