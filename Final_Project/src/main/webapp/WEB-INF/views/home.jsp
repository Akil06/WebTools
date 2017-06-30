<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.ordermanage.pojo.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id = "user" class = "com.ordermanage.pojo.User" scope = "session" />
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">

div{

margin-left: 20px;

}

</style>


</head>
<body>

 <jsp:include page="menu.jsp" />
 <div>  
<h2>Hi, ${user.firstName}</h1>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>



<a href="${contextPath}/product/list" class="btn btn-info" role ="button" >View Products</a> <br/> <br/> <br />

<a href="${contextPath}/product/search" class="btn btn-info" role ="button">Search Products</a> <br/> <br/> <br />

<a href="${contextPath}/order/cart" class="btn btn-info" role ="button">Products in Cart</a> <br/> <br/> <br />

<a href="${contextPath}/order/view" class="btn btn-info" role ="button" >View Orders</a> <br/> <br/> <br />
</div>
<%
  
session.setAttribute("userid",user.getPersonID() );
%>

</body>
</html>