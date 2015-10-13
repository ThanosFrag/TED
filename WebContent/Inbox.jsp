<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import = "ted.Driver,java.sql.*" %>
<%@include file="header.jsp" %>
<script>

function checkBoxes() {
  var chks = [];
  var value = [];
  var form = document.getElementById("remove");
  var inputs = form.getElementsByTagName("input");	
  var values = form.getElementsByTagName("input").values;
  for (var i = 0, len = inputs.length; i < len; i++) {
	 
	    chks.push(inputs[i]);
	    
  }  
  for (var i = 0, len = chks.length; i < len; i++) {
    if (chks[i].checked) {
    	window.location.href = 'RemoveMsg?id='+chks[i].value;
    }
  }
  return false;
  

}
</script>

<%
ResultSet result = (ResultSet)request.getAttribute("result");
String type = (String)request.getAttribute("type");
if (type == null)
	type = "Inbox";
%>

<div class="container">
    <div class="row">
        <div class="col-sm-3 col-md-2">
        </div>
        <div class="col-sm-9 col-md-10">
            <!-- Split button -->



        </div>
    </div>
    <hr />
    <div class="row">
        <div class="col-sm-3 col-md-2">
            <a href="SendMsg" class="btn btn-danger btn-sm btn-block" role="button">COMPOSE</a>
            <hr />
            <ul class="nav nav-pills nav-stacked">
                <li <%if (type.equals("Inbox")) { %> class="active" <% } %>><a href="Messages?type=Inbox"><span class="badge pull-right"></span> Inbox </a>
                </li>
                <li<%if (type.equals("Sent")) { %> class="active" <% } %>><a href="Messages?type=Sent">Sent Mail</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-md-10">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs">
                <li class="active"><a href="#home" data-toggle="tab"><span class="glyphicon glyphicon-inbox">
                </span>Primary</a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="home">
                
                    <form class="list-group" id="remove">
                    
   
                      
                    <% if (result != null) 
                    { 
                    	
                    	int id=0;%>
                    	<input type="submit" value="delete" onclick="return checkBoxes()"><br>
                    	<% 
                    	do { 
                    	
                    	%>
                    	
					<% String li = "MessageDisplay?id="+result.getString("idMessage");%>
                        
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value=<%=result.getInt(1) %>>
                                </label>
                            </div>
                             <span class="name" style="min-width: 120px;
                                display: inline-block;"><%if (result.getInt("Cond") == 0) { %><strong><%= result.getString(2)%>&emsp;</strong><% }else { %><%= result.getString(2)%> <%}%>
                                </span> <span class=""><%if (result.getInt("Cond") == 0) { %><a href=<%=li %>><strong><%= result.getString("Subject")%></strong></a><% }else { %><a href=<%=li %>><%= result.getString("Subject")%></a> <%}%></span>
                             <span class="name" style="min-width: 120px;"></span>
                             <span class="badge"><%= result.getString("Date")%></span> <span class="pull-right"></span>                           <fieldset>
                            
                            
                            </fieldset>

                      <%	} while (result.next()); }%>   
 
                      </form>            
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>