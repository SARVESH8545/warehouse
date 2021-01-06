$(document).ready(function(){
	$("#shippingCodeError").hide();
	$("#shippingRefNumError").hide();
	$("#courierRefNumError").hide();
	//$("#contactDtlsError").hide();
	$("#billToAddressError").hide();
	$("#shipToAddressError").hide();
	$("#descriptionError").hide();

	var shippingCodeError = false;
	var shippingRefNumError = false;
	var courierRefNumError = false;
	//var contactDtlsError = false;
	var billToAddressError = false;
	var shipToAddressError = false;
	var descriptionError = false;

	$("#shippingCode").keyup(function (){
		$(this).val($(this).val().toUpperCase());
		validate_shippingCode();
	});

	$("#shippingRefNum").keyup(function(){
		$(this).val($(this).val().toUpperCase());
		validate_shippingRefNum();
	});
	$("#courierRefNum").keyup(function(){
		$(this).val($(this).val().toUpperCase());
		validate_shippingRefNum();
	});

	/*$("#contactDtls").change(function(){
		validate_contactDtls();
	});*/

	$("#billToAddress").change(function(){
		validate_billToAddress();
	});

	$("#shipToAddress").change(function(){
		validate_billToAddress();
	});

	$("#description").keyup(function() {
		validate_description();
	});
	//shippingCode
	function validate_shippingCode(){
		var val = $("#shippingCode").val();
		var exp = /^[A-Z]{4,25]$/;
		if(val == ''){
			$("#shippingCodeError").html("Enter <b> shipping Code</b>");
			$("#shippingCodeError").css("color", "red");
			$("#shippingCodeError").show();
			shippingCodeError = false;
		}else if(!exp.test(val)){
			$("#shippingCodeError").html("Must be <b> 4-25 Chars only</b>");
			$("#shippingCodeError").css("color", "red");
			$("#shippingCodeError").show();	
			shippingCodeError = false;
		}else{
			//AJAX START
			$.ajax({
				url : 'validatecode',
				data : {"code":val},
				success: function(resTxt){
					if(resTxt!=""){
						$("#shippingCodeError").html("Enter <b> shipping Code</b>");
						$("#shippingCodeError").css("color", "red");
						$("#shippingCodeError").show();
						shippingCodeError = false;	
					}else{
						$("#shippingCodeError").hide();
						shippingCodeError = true;
					}
				}
			});
		}
		return shippingCodeError;
	}
	//shippingRefNum
	function validate_shippingRefNum(){
		var val = $("#shippingRefNum").val();
		var exp = /^[1-9A-Z][0-9]*$/;
		if(val ==''){
			$("#shippingRefNumError").html("<b>Number is Required</b>");
			$("#shippingRefNumError").css("color","red");
			$("#shippingRefNumError").show();
			shippingRefNumError = false;
		}else if(!exp.test(val)){
			$("#shippingRefNumError").html("<b>Only AlphaNumeric Value</b>");
			$("#shippingRefNumError").css("color","red");
			$("#shippingRefNumError").show();
			shippingRefNumError = false;
		}else{
			$("shippingRefNumError").hide();
			shippingRefNumError = true;
		}
		return shippingRefNumError;
	}

	//courierRefNum
	function validate_courierRefNum(){
		var val = $("#courierRefNum").val();
		var exp = /^[1-9A-Z][0-9]*$/;
		if(val ==''){
			$("#courierRefNumError").html("<b>Number is Required</b>");
			$("#courierRefNumError").css("color","red");
			$("#courierRefNumError").show();
			courierRefNumError = false;
		}else if(!exp.test(val)){
			$("#courierRefNumError").html("<b>Only AlphaNumeric Value</b>");
			$("#courierRefNumError").css("color","red");
			$("#courierRefNumError").show();
			courierRefNumError = false;
		}else{
			$("courierRefNumError").hide();
			courierRefNumError = true;
		}
		return courierRefNumError;
	}

	function validate_billToAddress() {
		var val = $("#billToAddress").val();
		if (val.length < 5 || val.length > 150) {
			$("#billToAddressError").show();
			$("#billToAddressError").html("Must be <b>5-150 Chars only</b>");
			$("#billToAddressError").css("color", "red");
			descriptionError = false;
		} else {
			$("#billToAddressError").hide();
			billToAddressError = true;
		}
		return billToAddressError;
	}

	function validate_shipToAddress() {
		var val = $("#shipToAddress").val();
		if (val.length < 5 || val.length > 150) {
			$("#shipToAddressError").show();
			$("#shipToAddressError").html("Must be <b>5-150 Chars only</b>");
			$("#shipToAddressError").css("color", "red");
			shipToAddressError = false;
		} else {
			$("#shipToAddressError").hide();
			shipToAddressError = true;
		}
		return shipToAddressError;
	}

	function validate_description() {
		var val = $("#description").val();
		if (val.length < 5 || val.length > 150) {
			$("#descriptionError").show();
			$("#descriptionError").html("Must be <b>5-150 Chars only</b>");
			$("#descriptionError").css("color", "red");
			descriptionError = false;
		} else {
			$("#descriptionError").hide();
			descriptionError = true;
		}
		return descriptionError;
	}
	//--on submit------------//
	$("#shippingForm").submit(function (){
		validate_shippingCode();
		validate_shippingRefNum();
		validate_courierRefNum();
		validate_billToAddress();
		validate_shipToAddress();
		validate_description();

		if (shippingCodeError && shippingRefNumError 
				&& courierRefNumError  
				&& billToAddressError && shipToAddressError 
				&& descriptionError)
			return true;
		else
			return false;
	});
});