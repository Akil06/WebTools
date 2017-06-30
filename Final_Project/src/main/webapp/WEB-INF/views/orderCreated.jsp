<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Created Successfully</title>
    </head>
    <body>
    <jsp:include page="menu.jsp" /> 
    	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	    <a href="${contextPath}/home/">Home</a><br/>
    
        <h2>New Order Created Successfully for : ${no} Products</h2>
        
    </body>
</html>