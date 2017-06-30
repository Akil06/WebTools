function validateform(){ 
	var error = 0;
	var quantity = document.orderCart.quantity.value;
    var quantityRegExp = /^[1-9]([0-9]{1})?$/;
    if((quantityRegExp.test(quantity) == false)||(quantity == "")||(quantity == null)) {
        //window.alert("age"+age);
        document.getElementById("errfname").style.visibility="visible";
        error = 1;
    }else{
        document.getElementById("errfname").style.visibility="hidden";
    }
    if (error==1){
    	window.alert("Incorrect Quantity");
    	return false;
    }
}