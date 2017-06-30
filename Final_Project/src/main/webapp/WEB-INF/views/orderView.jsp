<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Order List</title>
</head>
<body>
<jsp:include page="menu.jsp" /> 
	<div class="container">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/home/">Home</a><br/> <br/> <br/>
	
<table class="table table-hover">
	
		<tr>
		   
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Model No</b></td>
			<td><b>Quantity</b></td>
			<td><b>Status</b></td>
			<td><b>Quantity Available for Order</b>
			<td><b>Amount payable for Order</b>
			<td><b>Quoted By</b></td>
		</tr>
		
		<c:forEach var="ord" items="${oqList}">
		
			<tr>
			
				<td>
				<a href='${contextPath}/product/info/${ord.productId}'>	${ord.name} </a>
				
				</td>
				<td>
			${ord.productDescription}
				
				</td>
				<td>
				${ord.modelNo}
				</td>
				<td>
				${ord.quantity}
				</td>
			<td>
				${ord.status}
				</td>
				
				<c:if test = "${ord.status == 'Reviewed'}">
				<td>${ord.quantityAvailable}</td>
				</c:if>
				<c:if test = "${ord.status == 'Submitted'}">
				<td>Not Available for view </td>
				</c:if>
				<c:if test = "${ord.status == 'Reviewed'}">
				<td> ${ord.amountPayable}</td>
				</c:if>
				<c:if test = "${ord.status == 'Submitted'}">
				<td>Not Available for view </td>
				</c:if>
				<td>
				${ord.username}
				</td>
				<c:if test = "${userType == 'support'}">
				<td>
				 <a href ='${contextPath}/order/updateStatus/${ord.orderId}' class="btn btn-info" role ="button">View Order </a><br/> <br/>
				   </td>
				   </c:if>
                    </tr>
                    
              </c:forEach>
	</table>
	</div>
	</body>
</html>
