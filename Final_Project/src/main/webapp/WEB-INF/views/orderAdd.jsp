<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="<c:url value="/resources/javascript/validate.js" />"></script>
<link href="<c:url value="/resources/css/validate.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order List</title>
</head>
<body>
	<jsp:include page="menu.jsp" /> 
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/home/">Home</a><br/>
	<form name="orderCart" onsubmit="return validateform()" action="${contextPath}/order/place" method="GET">
<table border="1" cellpadding="5" cellspacing="5">
	
		<tr>
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Model No</b></td>
			<td><b>Added By</b></td>
			<td><b>Categories</b></td>
			<td><b>Quantity</b></td>
		</tr>
		
		<c:forEach var="pro" items="${products}">
		
			<tr>
				<td>
				${pro.name}
				</td>
				<td>
			${pro.productDescription}
				
				</td>
				<td>
				${pro.modelNo}
				</td>
				<td>
				${pro.user.username}
				
				</td>
				<td><c:forEach var="categ" items="${pro.categories}">
                    	${categ}
                    </c:forEach>
                    </td>
                    </tr>
                    
              <td> <input type="number" name ="quantity" value="" required/></td>
               <span id="errfname" class="error">wrong format</span>
              </c:forEach>
	</table>
	<tr><td align ="center"><input type="submit" value="Place Order"></td></tr>
	</form>
</body>
</html>
