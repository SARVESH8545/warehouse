$(document).ready(function () {
	//1.hide error span            
	$("#orderCodeError").hide();
	$("#refNumberError").hide();
	$("#qualityCheckError").hide();
	$("#statusError").hide();
	$("#descriptionError").hide();

	//2.define flag variable           
	var orderCodeError = false;
	var refNumberError = false;
	var qualityCheckError = false;
	var statusError = false;
	var descriptionError = false;

	//link to event           
	$("#orderCode").keyup(function () {
		//5.call the function
		$("#orderCode").val($("#orderCode").val().toUpperCase());
		validate_orderCode();
	});

	$("#refNumber").keyup(function () {
		$("#refNumber").val($("#refNumber").val().toUpperCase());
		validate_refNumber();
	});

	$("#status").keyup(function () {
		validate_status();
	});

	$('input[type="radio"][name="qualityCheckError"]').change(function () {
		validate_qualityCheckError();
	});

	$("#description").keyup(function () {
		validate_description();
	});
	//4.define the function        
	//-----Code--------//
	function validate_orderCode() {
		var val = $("#orderCode").val();
		var exp = /^[A-Z.]{5,25}$/;
		if (val == '') {
			$("#orderCodeError").html("<b>Order Code Required</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();
			orderCodeError = false;
		} else if (!exp.test(val)) {
			$("#orderCodeError").html("<b>Order Code [5-25]chars</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();
			orderCodeError = false;
		} else {
			//AJAX START
			$.ajax({
				url : 'validatecode',
				data: {"code":val},
				success:function(resTxt) {
					if(resTxt!=''){
						$("#orderCodeError").show();
						$("#orderCodeError").html(resTxt);
						$("#orderCodeError").css("color", "red");
						orderCodeError = false;
					}else{
						$("#orderCodeError").hide();
						orderCodeError = true;
					}
				}
			}); //AJAX END
		}//else end
		return orderCodeError;
	}

	//-----Ref Number-------//
	function validate_refNumber() {
		var val = $("#refNumber").val();
		if (val == '') {
			$("#refNumberError").html("<b>Reference Number Required</b>");
			$("#refNumberError").css("color", "red");
			$("#refNumberError").show();
			refNumberError = false;
		} else {
			$("#refNumberError").hide();
			refNumberError = true;
		}
		return refNumberError;
	}
	//-----QTY--------//
	function validate_qualityCheck() {
		var len = $('input[type="radio"][name="qualityCheck"]:checked').length;
		if (len == 0) {
			$("#qualityCheckError").html("<b>Quality Check Required</b>");
			$("#qualityCheckError").css("color", "red");
			$("#qualityCheckError").show();
			qualityCheckError = false;
		} else {
			$("#qualityCheckError").hide();
			qualityCheckError = true;
		}
		return qualityCheckError;
	}

	//-----Status--------//
	function validate_status() {
		var val = $("#status").val();
		if (val == '') {
			$("#statusError").html("<b>Status Required</b>");
			$("#statusError").css("color", "red");
			$("#statusError").show();
			statusError = false;
		} else {
			$("#statusError").hide();
			statusError = true;
		}
		return statusError;
	}

	//-----description--------//
	function validate_description() {
		var val = $("#description").val();

		if (val.length < 5 || val.length > 150) {
			$("#descriptionError").html("<b>description Required</b>");
			$("#descriptionError").css("color", "red");
			$("#descriptionError").show();
			descriptionError = false;
		} else {
			$("#descriptionError").hide();
			descriptionError = true;
		}
		return descriptionError;
	}

	//-----------form submission-------------/
	$("#purchaseOrderForm").submit(function () {
		validate_orderCode();
		validate_refNumber();
		validate_status();
		validate_qualityCheck();
		validate_description();
		if (orderCodeError && qualityCheckError 
				&& refNumberError && statusError 
				&& descriptionError) {
			return true;
		} else {
			return false;
		}
	});
});