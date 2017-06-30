<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>Searched Products</title>
</head>
<body>
<jsp:include page="menu.jsp" />
	<div class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />

		<a href="${contextPath}/home/">Home</a><br />
		<form action="${contextPath}/order/add" method="GET">
			<c:if test="${errormissing == 'Select to add to Cart'}">
				<div>Please select to add to Cart</div>


			</c:if>

			<table class="table table-hover">

				<tr>
					<td><b>Product Name</b></td>
					<td><b>Product Description</b></td>
					<td><b>Model No</b></td>
					<td><b>Posted By</b></td>
					<td><b>Categories</b></td>

					<td><b>Select</b></td>
				</tr>
				<c:if test="${searchBy == 'category'}">
					<c:choose>
						<c:when test="${empty requestScope.searchedProductsCat}">
							<h3>No Search Results  Found !</h3>
						</c:when>
						<c:otherwise>
							<c:forEach var="procat" items="${searchedProductsCat}">



								<tr>
									<td>${procat.name}</td>
									<td>${procat.productDescription}</td>
									<td>${procat.modelNo}</td>
									<td>${procat.user.username}</td>

									<td><c:forEach var="categ" items="${procat.categories}">
                    	${categ}
                    </c:forEach></td>
									<c:if test="${errormissing == 'Select to add to Cart'}">
Please select to add to Cart
<% HttpSession s= request.getSession();
session.setAttribute("errormissing", " NA");
					  
%>
</c:if>

									<td align="left"><input type="Checkbox"
										name="checkedProduct" value="${procat.name}" /></td>
								</tr>

							</c:forEach>
					<tr>
					<td align="center"><input type="submit" value="Add to Cart"></td>
					</tr>
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:if test="${!(searchBy == 'category')}">
					<c:choose>
						<c:when test="${empty requestScope.searchedProducts}">
							<h3>No Search Results  Found !</h3>
						</c:when>
						<c:otherwise>
							<c:forEach var="pro" items="${searchedProducts}">



								<tr>
									<td>${pro.name}</td>
									<td>${pro.productDescription}</td>
									<td>${pro.modelNo}</td>
									<td>${pro.user.username}</td>

									<td><c:forEach var="categ" items="${pro.categories}">
                    	${categ}
                    </c:forEach></td>

									<td align="left"><input type="Checkbox"
										name="checkedProduct" value="${pro.name}" /></td>
								</tr>

			
</c:forEach>
				<tr>
					<td align="center"><input type="submit" value="Add to Cart"></td>
					</tr>
									
						</c:otherwise>
					</c:choose>
				</c:if>
				
			</table>
		</form>
	</div>
</body>
</html>