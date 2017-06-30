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
</head>
<body>
 <jsp:include page="menu.jsp" />  
<h1>Hi, ${user.firstName}</h1>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<a href="${contextPath}/category/add" >Add Category</a> <br/> <br />
<a href="${contextPath}/product/add" >Add Products</a> <br/> <br />
<a href="${contextPath}/product/list" >View Products</a> <br/> <br />
<a href="${contextPath}/order/viewAll" >View Orders</a> <br/> <br />

<%
  
session.setAttribute("userid",user.getPersonID() );
%>

</body>
</html>