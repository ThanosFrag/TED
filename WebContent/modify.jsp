<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%
	double latitude = 0.0;
    double longitude = 0.0 ;
    Driver db = new Driver();
    db.Connect();
	String x = null;
	String y = null;
	String option;
	boolean t= true;
	ResultSet item = (ResultSet)request.getAttribute("item");
	item.first();
	ResultSet cat = (ResultSet)request.getAttribute("cat");
	ArrayList<String> categories = new ArrayList<String>();
	while (cat.next()){
		categories.add(cat.getString("CategoryName"));
	}
	
%>
<% 
if	(session.getAttribute("login")==null){
	%>
	<script>alert("You need to login in order to modify Item!");
	window.location = "/TED/index.jsp";</script>
	<% 
}
%>

<script> 
$( "#unbind" ).click(function() {
    $(this).attr("disabled", "disabled");
    $("#bind").removeAttr("disabled");
});
    function show() { 
        if(document.getElementById('benefits').style.display=='none') { 
            document.getElementById('benefits').style.display='block'; 
        } 
        else if(document.getElementById('benefits').style.display=='block') { 
            document.getElementById('benefits').style.display='none'; 
        } 
        return false;
    } 

</script> 




<script>
document.getElementById('imageinput').addEventListener('change', function(){
	    var file = this.files[0];
	    // This code is only for demo ...
	    if (!file.type.match(imageType)) {
	    	        throw "File Type must be an image";
	    	    }

	}, false);

</script>

   <div class="account">
   <div class="col-md-12 products-single">	
	  <div class="container">
	
	       <div class="account-bottom">
	       	<div class="col-md-5 account-left">
	       <form name="add" action=ChangeItem method=post enctype="multipart/form-data">
	       <input type="hidden" name="IMAGE" value=<%=item.getString("Image")%>>
	       <input type="hidden" name="ID" value=<%=item.getString("idItem")%>>
	       			<div class="account-top heading">
						<h3>Modify Item - <%=item.getInt("idItem")%></h3>
					</div>
					<div class="address">
						<span>Upload Image</span>
						<img src=<%=item.getString("Image")%>>
						<input type="file" id="imageinput" name="imageinput"  accept="image/*"  value=<%=item.getString("Image")%> />
					</div>
					<div class="address">
						<span>Name</span>
						<input type="text" name="Name" value=<%=item.getString("Name")%> required>
					</div>
					<div class="address">
						<% ResultSet result = db.ReturnCategories();
						
						if (result!=null)  {
						
						%>
						
						<fieldset>
						       <legend>Selecting Category</legend>
						       <p>
						          <label>Category</label> 
						          <br>           
						<% 
						 while (result.next()){
							 option = result.getString(1);
							 %>
						          <input type = "checkbox"
						                 name = "<%=option%>"
						                 id = "<%=option%>"
						                 value = "<%=option%>" 
						                 <%if (categories.contains(option))%> checked
						                 
						                 />
						          <label for = "<%=option%>"><%=option%></label>
						          <br>
						        </p>    
						  <%} }%>   
						 </fieldset> 
					</div>
					<div class="address">
						<span>Buy Price</span>
						<%if (item.getDouble("BuyPrice")>0.0){ %>
						<input type='text' name= "Buy" value=<%=item.getDouble("BuyPrice")%>>
						<%}else {%>
						<input type='text' name= "Buy">
						<%} %>
					</div>
					<div class="address">
						<span>Description</span>
						<input type='text' name= "Description" value=<%=item.getString("Description")%> required>
					</div>					
					
					<div class="address">
						<span>Started</span>
						<input type='text' name="started" value=<%=item.getString("Started")%> required>
<a href="  javascript:show_calendar('document.add.started', document.add.started.value)"><img src="cal.gif" width="16" height="16" border="0" alt="click"></a>
					</div>
					<div class="address">
						<span>Ends</span>
					<input type='text' name="ended" value=<%=item.getString("Ended")%> required>
<a href="  javascript:show_calendar('document.add.ended', document.add.ended.value)"><img src="cal.gif" width="16" height="16" border="0" alt="click"></a>
					</div>
					
					<div class="address">
						<span>Country</span>
						<input type="text" name="Country" value=<%=item.getString("Country")%> required>
					</div>
					<fieldset>
					<legend>Location</legend>
					</fieldset>
					<div class="address">
						<span>Latitude</span>
						<%if (item.getObject("Latitude")==null){%>
						<input type='text' name="lat" >
						<%}else{%>		
						<input type='text' name="lat" value=<%=item.getDouble("Latitude")%>>		
						<%} %>		
					</div>		
					<div class="address">
						<span>Longitude</span>
						<%if (item.getObject("Longtitude")==null){%>
						<input type='text' name="long" >
						<%}else{%>		
						<input type='text' name="long" value=<%=item.getDouble("Longtitude")%>>	
						<%} %>	
					</div>										
					<div class="address new">
						<input type="submit" value="Modify Item"  onclick="return confirmComplete(0)">
					</div>
					
	       			
	       </form>	       	
	       	</div>
	       	</div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>