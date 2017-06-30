<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<title>Add Products</title>
</head>
<body>
<jsp:include page="menu.jsp" /> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<a href="${contextPath}/home/">Home</a><br/>
	<h2>Add a New Product</h2>

<form:form action="${contextPath}/product/add" method="post"
		commandName="product" enctype="multipart/form-data">
<table>
<tr>
<td> posted by </td>
<td> ${sessionScope.user.username }</td>
<td> <form:hidden path="postedBy"
value="${sessionScope.user.personID}"/></td>
</tr>
<tr>
<td>Category:</td>
				<td>  <form:select path="categories">
				
						 <form:options items="${categories}" required="required" />
						 
						 </form:select>
						 <font color="red"><form:errors
							path="categories" /></font>
						</td>
</tr>
			<tr>
				<td>Product Name</td>
				<td><form:input path="name" size="30" required="required" /> <font color="red"><form:errors
							path="name" /></font></td>
			</tr>
<tr>
				<td>Product Description</td>
				<td><form:input path="productDescription" size="30" required="required" /> <font color="red"><form:errors
							path="productDescription" /></font></td>
			</tr>
			<tr>
				<td>Product Model No</td>
				<td><form:input path="modelNo" size="30" required="required" /> <font color="red"><form:errors
							path="modelNo" /></font></td>
			</tr>
			<tr><td> <input type="hidden" name="filename" value="" required="required"/></td></tr>
			<tr><td>Upload Product Image</td>
			<td><input type="file" name="photo"/> </td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Create Product" /></td>
			</tr>
		</table>
</form:form>
</body>
</html>