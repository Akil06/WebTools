<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Info</title>
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
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div>
<h3> Product Info</h3>
	<a href="${contextPath}/home/">Home</a><br/>
<table>
<tr><td><b> Product Name : </b> </td> <td> ${products.name}</td></tr> 
<br/>
<tr> <td><b> Product Description : </b> </td> <td> ${products.productDescription}</td></tr>
<br/>
<tr> <td><b> Model No: </b> </td> <td> ${products.modelNo}</td></tr>
<br/>
<tr> <td><b> Status: </b> </td> <td> ${products.status}</td></tr>
<br/>
 <td> <img height="150" width="150" src="${products.filename}" /></td>

</table>
<c:if test = "${userType == 'support'}">

<tr>
<td><b> In Stock </b> </td> <td> ${products.inStock}</td></tr> <br/> <br/>

<form action="${contextPath}/product/update/${products.productId}" method="GET">
<tr> <td> <b>Enter New Stock Number to Update :</b></td><td> <input type="number" name ="inStock" value="" required/></td></tr><br/>
<tr> <td><b> Choose to update Availability :</b></td> <td> <select name ="status" ></tr> <br/>
<option value="Available"> Available</option>
<option value="Not Available">Not Available</option>
</select> </br>
<!-- <td> <input type="number" name ="amountPayable" value="${products.filename}" required/></td> -->
 <input type="submit" value="Update Product"><br/> <br/>
 </form>
</c:if>
</div>
</body>
</html>