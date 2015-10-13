<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
	
 <%

 ResultSet result = (ResultSet)request.getAttribute("result");
 %>


  <div class="contact">
	  <div class="container">
			<h3 class="tittle con">Message Details</h3>
			     <div class="lcontact span_1_of_contact">
				      <div class="contact-form">
						        <p class="comment-form-author"><label>From:</label>
						     	   <%=result.getString(1) %>
						        </p>
						   		<p class="comment-form-author"><label>To:</label>
						     	   <%=result.getString(2) %>
						        </p>
						        <p class="comment-form-author"><label>Subject:</label>
						     	   <%=result.getString(3) %>
						        </p>
						        <p class="comment-form-author"><label>Message:</label>
						    	  <%=result.getString(4) %>
						        </p>
				       </div>
				       <a href="Messages?type=Inbox">Back</a>
			     </div>
		</div>
	</div>



<%@include file="footer.jsp" %>