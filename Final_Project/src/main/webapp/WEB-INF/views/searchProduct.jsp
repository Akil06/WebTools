<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Searching Product</title>
    </head>
    <body>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <jsp:include page="menu.jsp" />
    <a href="${contextPath}/home/">Home</a><br/>
    
        <h1>Search Product</h1>
        <br/>
         <form action="${contextPath}/product/search" method="Post">
        <p> Keyword     : <input type="text" name='keyword' /></p>
         <input type="radio" name="searchby" value="name" checked=""/> Search By Name
        <input type="radio" name="searchby" value="productDescription" checked=""/> Search By Description
        <input type="radio" name="searchby" value="modelNo" checked=""/> Search By ModelNo
        <input type="radio" name="searchby" value="category" checked=""/> Search By Category
        <input type="submit" value="Search Products"/>
         </form>
    </body>
    
    
</html>
