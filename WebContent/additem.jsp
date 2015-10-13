<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>

<%
	double latitude = 0.0;
    double longitude = 0.0 ;
    Driver db = new Driver();
    db.Connect();
	String x = null;
	String y = null;
	String option;
	boolean t= true;
%>
<% 
if	(session.getAttribute("login")==null){
	%>
	<script>alert("You need to login in order to add Item!");
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



<%-- 
<%!
	public void Find(String x,String y){
	%>
	<%!
	 
	
	
	
	%>
		<iframe 
	
		src="https://www.google.com/maps/embed?pb=!1m17!1m8!1m3!1d
		3178877.868245887!2d
		22.781871051269!3d
		38.91545309195277!3m2!1i1024!2i768!4f13.1!4m6!3e6!4m0!4m3!3m2!1d
		<%=Double.parseDouble(x)%>!2d
		<%=Double.parseDouble(y)%>!5e0!3m2!1sel!2sgr!4v1440257243539" 
		width="600" height="450" frameborder="0" style="border:0">
		</iframe>	
	<%!
	
}
%>
--%>

<script>
document.getElementById('imageinput').addEventListener('change', function(){
	    var file = this.files[0];
	    // This code is only for demo ...
	    if (!file.type.match(imageType)) {
	    	        throw "File Type must be an image";
	    	    }

	}, false);

</script>
<script type="text/javascript">



function checkBoxes() {
  var chks = [];
  var form = document.getElementById("add");
  var inputs = form.getElementsByTagName("input");	
  for (var i = 0, len = inputs.length; i < len; i++) {
	    if (inputs[i].type == "checkbox") chks.push(inputs[i]);
		//inputs[i].onclick = checkBoxes;
  }  
  for (var i = 0, len = chks.length; i < len; i++) {
    if (chks[i].checked) {chks.valueOf()
    	return true;
    }
  }
  alert('you must choose at least one category')
  return false;

}
</script>
   <div class="account">
   <div class="col-md-12 products-single">	
	  <div class="container">
	
	       <div class="account-bottom">
	       	<div class="col-md-5 account-left">
	       <form name="add" id="add" action=additem method=post enctype="multipart/form-data">
	       			<div class="account-top heading">
						<h3>Add a Item</h3>
					</div>
					<div class="address">
						<span>Upload Image</span>
						<input type="file" id="imageinput" name="imageinput"  accept="image/*" required/>
					</div>
					<div class="address">
						<span>Name</span>
						<input type="text" name="Name"required>
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
						          <div class="checkbox required">      
						<% 
						 while (result.next()){
							 option = result.getString(1);
							 %>
						          <input type = "checkbox"
						                 name = "<%=option%>"
						                 id = "<%=option%>"
						                 value = "<%=option%>" />
						          <label for = "<%=option%>"><%=option%></label>
						          <br>
						        </p>    
						  <%} }%>   
						  
						  </div>
						 </fieldset> 
					</div>
					<div class="address">
						<span>Buy Price</span>
						<input type='text' name= "Buy">
					</div>
					<div class="address">
						<span>Description</span>
						<textarea name= "Description"  form="add" required>Enter Description</textarea>
					</div>					
					
					<div class="address">
						<span>Started</span>
						<input type='text' name="started" required>
<a href="  javascript:show_calendar('document.add.started', document.add.started.value)"><img src="cal.gif" width="16" height="16" border="0" alt="click"></a>
					</div>
					<div class="address">
						<span>Ends</span>
					<input type='text' name="ended" required>
<a href="  javascript:show_calendar('document.add.ended', document.add.ended.value)"><img src="cal.gif" width="16" height="16" border="0" alt="click"></a>
					</div>
					
					<div class="address">
						<span>Country</span>
						<input type="text" name="Country"required>
					</div>
					<fieldset>
					<legend>Location</legend>
					</fieldset>
					<div class="address">
						<span>Latitude</span>
						<input type='text' name="lat" >						
					</div>		
					<div class="address">
						<span>Longitude</span>
						<input type='text' name="long" >
						
					</div>										
					<div class="address new">
						<input type="submit" value="Add Item" onclick="return checkBoxes()">
					</div>
					
	       			
	       </form>	       	
	       	</div>
	       	</div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>