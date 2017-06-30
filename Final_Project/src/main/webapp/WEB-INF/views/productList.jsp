<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>Product List</title>
</head>
<body>
<jsp:include page="menu.jsp" /> 
<div class="container">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/home/">Home</a><br/>
<form name="productOrder" action="${contextPath}/order/add" method="GET">
	<table class="table table-hover">
	
		<tr>
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Model No</b></td>
			<td><b>Posted By</b></td>
			<td><b>Categories</b></td>
			<td><b> Image </b></td>
			<td><b>Status </b></td>
			<c:if test = "${userType == 'support'}"> 
			<td><b>In Stock </b></td>
			</c:if>
			<c:if test = "${userType == 'customer'}"> 
			<td><b>Select</b></td>
			</c:if>
			
		</tr>
		
		<c:forEach var="pro" items="${products}">
		
			<tr>
				<td>
				
			<a href='${contextPath}/product/info/${pro.productId}'>	${pro.name} </a>
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
                    </c:forEach></td>
              <td> <img height="150" width="150" src="${pro.filename}" /></td>
               <td> ${pro.status} </td>     
             <c:if test = "${userType == 'support'}">
             <td> ${pro.inStock} </td>
             </c:if>       
			<c:if test = "${userType == 'customer'}">
			 <c:choose>
			 <c:when test = "${pro.status=='Not Available'}" >
			<td align ="left"><input type="Checkbox" name="checkedProduct" disabled value="${pro.name}"/>
			</c:when>
			<c:otherwise>
			<td align ="left"><input type="Checkbox" name="checkedProduct"  value="${pro.name}"/>
			</c:otherwise>
			</c:choose>
			</td>
			</c:if>
			</tr>
			
		</c:forEach>
		<c:if test = "${userType == 'customer'}">
		<tr><td align ="center"><input type="submit" value="Add to Cart"></td></tr>
		</c:if>
	</table>
	</form>
	</div>
</body>
</html>