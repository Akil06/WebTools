<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Order in Cart</title>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
	<div class="container">
	<jsp:include page="menu.jsp" />
		<a href="${contextPath}/home/">Home</a><br />
		<form action="${contextPath}/order/place" method="GET">
			<table class="table">
				<tr>
					<td>Product Name</td>
					<td>Product Description</td>
					<td>Model No</td>
					<td>Quantity</td>
				</tr>
				<c:forEach var="cart" items="${oqListCart}">
				<tr>
					<td>${cart.name}</td>
					<td>${cart.productDescription}</td>
					<td>${cart.modelNo}</td>
					<td><input type="number" name="quantity" value="${ord.quantity}" required/></td>
				</tr>
				</c:forEach>
			</table>
			<input type="submit" value="Place Order"/>
		</form>
	</div>
</body>
</html>
