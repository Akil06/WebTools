<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>Order List</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script  type="text/javascript">
   xmlhttp = new XMLHttpRequest();
   //var req = new JSONHttpRequest();
   
   function getuserinfo(){
	   var id = document.getElementById('personid').value;
	   var url = "/orderManage/user/info?userid="+id;
	   xmlhttp.onreadystatechange=function(){
		   
		   if (xmlhttp.readyState==4){
			   var jsonResponse = xmlhttp.responseText;
			   document.getElementById('userinfo').innerHTML=jsonResponse;
		   }
		   else {
			   document.getElementById('userinfo').innerHTML="<strong>Waiting for Server Response.. </strong>";
		   }
	   }
	   xmlhttp.open("GET",url,true);
	  //xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	   xmlhttp.send();
   }
</script>




</head>
<body>
<jsp:include page="menu.jsp" /> 
	<div class="container">
	

	<a href="${contextPath}/home/">Home</a><br/> <br/> <br/>
	<form  action="${contextPath}/order/updateOrder" method="POST">
<table class="table table-hover">
	
		<tr>
		   <td><b></b></td>
		   <td><b></b></td>
			<td><b>Product Name</b></td>
			<td><b>Product Description</b></td>
			<td><b>Model No</b></td>
			<td><b>Quantity</b></td>
			<td><b>Status</b></td>
			<td><b>Quoted By</b></td>
			<td><b>Quantity Available</b></td>
			<td><b> Amount Payable</b>
		</tr>
		
		<c:forEach var="ord" items="${updateOrderList}">
		
			<tr>
			<td> <input type="hidden" name ="orderid" value="${ord.orderId}" required/></td>
			<td> <input type="hidden" name ="productid" value="${ord.productId}" required/></td>
				<td>
				${ord.name}
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
				<td>
				<input id ="personid" type="hidden" value="${ord.personID}"/>
	<!--  <input type="submit" value=""class="proceedButton" onClick="return getuserinfo(${ord.personID});" />-->
	<a class="proceedButton" onclick="return getuserinfo();" >
				${ord.username} </a>
				</td>
				
			   <td> <input type="number" name ="quantityAvailable" value="" required/></td>
			<td> <input type="number" name ="amountPayable" value="" required/></td>
			<!--   <td> <a href ='${contextPath}/order/updateOrder/${ord.orderId}/${ord.productId}'>View Order </a><br/> <br/></td>
			--> 
                    </tr>
                    
              </c:forEach>
	</table>
	<tr><td align ="center"><input type="submit" value="Update Order"></td></tr>
	</form>
	</div>
	<div id='userinfo'>
	</div>
	${ord.personID}
	</body>
</html>
