<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>

<%	int user;
	if (request.getAttribute("username")==null)
		user =0;
	else
		user =1;
%>

<!--account-->
   <div class="account">
	  <div class="container">
	       <div class="account-bottom">
	       	<div class="col-md-6 account-left">
	       <form action=register method=post>
	       			<div class="account-top heading">
						<h3>NEW Users</h3>
					</div>
					<div class="address">
						<span>First Name</span>
						<input type="text" name="name"required>
					</div>
					<div class="address">
						<span>Last Name</span>
						<input type="text" name="surname"required>
					</div>
					<div class="address">
						<span>Username</span>
						<input type="text" name="Name" oninput="checkUser(<%=user %>)" required> 
						<% if (user==1){ %>
							<font color=red>Username already Taken</font>
						<%} %>
					</div>
					<div class="address">
						<span>Email Address</span>
						<input type="text" name="Email" required>
					</div>
					<div class="address">
						<span>Country</span>
						<input type="text" name="Country"required>
					</div>
					<div class="address">
						<span>Location</span>
						<input type="text" name="Location"required>
					</div>
					<div class="address">
						<span>Password</span>
						<input type="password" name="password"  id= "password" required>
					</div>
					<div class="address">
						<span>Re-enter Password</span>
						 <input type='password' oninput="check(this)" required>
					</div>
					<div class="address new">
						<input type="submit" value="Register">
					</div>
	       			
	       </form>
	       </div>
	       </div>
       </div>
   </div>

<%@include file="footer.jsp" %>