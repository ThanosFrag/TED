<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="header.jsp" %>

<% if (session.getAttribute("login")==null){response.sendRedirect("index.jsp");return;}

	ResultSet result =(ResultSet)request.getAttribute("buddies");

%>


  <div class="contact">
	  <div class="container">
			<h3 class="tittle con">Contact</h3>
			     <div class="lcontact span_1_of_contact">
				      <div class="contact-form">
				  	        <form method="post" action="Messages">
					    	    <p class="comment-form-author"><label>Send Message to:</label>
					    	    <select name="to" required>
					    	    <% while (result!=null && result.next()) {%><option value=<%=result.getString("Username") %>><%=result.getString("Username") %></option>
					    	   <%  } %>
					    	    </select>
						    	</p>
						    	<input type="hidden" name="from" value=<%=session.getAttribute("login")%>>
						        <p class="comment-form-author"><label>Subject:</label>
						     	   <input type="text" name="subject" class="textbox" value="Enter your Subject here..." onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email';}"  required>
						        </p>
						        <p class="comment-form-author"><label>Message:</label>
						    	  <textarea name="message" required onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Message';}">Enter your message here...</textarea>
						        </p>
						        <input name="submit" type="submit" id="submit" value="Send Message">
					        </form>
				       </div>
			     </div>
			     <div class="contact_grid span_2_of_contact_right">
					<h3>Criteria:</h3>
				    <div class="address">
						<i class="pin_icon"></i>
					    <div class="contact_address">
						In order to contact with somebody, you need to have already completed a transition with him/her
					    </div>
					    <div class="clearfix"></div>
					</div>
		        </div>
		        <div class="clearfix"></div>
	  </div>
  </div>




<%@include file="footer.jsp" %>