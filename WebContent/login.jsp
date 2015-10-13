<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
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
<input type="submit" value="Login"/>
<a href="/TED/index.jsp">back</a>

</form>
</body>
</html>