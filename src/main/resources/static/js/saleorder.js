$(document).ready(function(){
	//1. hide span tag
	$("#orderCodeError").hide();
	$("#refNumberError").hide();
	$("#stockModeError").hide();
	$("#stockSourceError").hide();
	$("#defStatusError").hide();
	$("#descriptionError").hide();
	
	//2. define flag
	var orderCodeError = false;
	var refNumberError = false;
	var stockModeError = false;
	var stockSourceError = false;
	//var defStatusError = false;
	var descriptionError = false
	
	$("#orderCode").keyup(function (){
		$(this).val($(this).val().toUpperCase());
		validate_orderCode();
	});
	
	$("#refNumber").keyup(function(){
		$(this).val($(this).val().toUpperCase());
		validate_refNumber();
	});
	
	$('input[type="radio"][name="stockMode"]').change(function () {
        validate_stockMode();
    });

	$("#stockSource").change(function () {
        validate_stockSource();
    })
	
	$("#description").keyup(function () {
		validate_description();
	});
	//orderCode
	function validate_orderCode(){
		var val = $("#orderCode").val();
		var exp = /^[A-Z]{4,25]$/;
		if(val == ''){
			$("#orderCodeError").html("Enter <b> Order Code</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();
			orderCodeError = false;
		}else if(!exp.test(val)){
			$("#orderCodeError").html("Must be <b> 4-25 Chars only</b>");
			$("#orderCodeError").css("color", "red");
			$("#orderCodeError").show();	
			orderCodeError = false;
		}else{
			//AJAX START
			$.ajax({
				url : 'validatecode',
				data : {"code":val},
				success: function(resTxt){
					if(resTxt!=""){
						$("#orderCodeError").html("Enter <b> Order Code</b>");
						$("#orderCodeError").css("color", "red");
						$("#orderCodeError").show();
						orderCodeError = false;	
					}else{
						$("#orderCodeError").hide();
						orderCodeError = true;
					}
				}
			});
		}
		return orderCodeError;
	}
	
	//refNumber
	function validate_refNumber(){
		var val = $("#refNumber").val();
		var exp = /^[1-9A-Z][0-9]*$/;
		if(val ==''){
			$("#refNumberError").html("<b>Number is Required</b>");
			$("#refNumberError").css("color","red");
			$("#refNumberError").show();
			refNumberError = false;
		}else if(!exp.test(val)){
			$("#refNumberError").html("<b>Only AlphaNumeric Value</b>");
			$("#refNumberError").css("color","red");
			$("#refNumberError").show();
			refNumberError = false;
		}else{
			$("refNumberError").hide();
			refNumberError = true;
		}
		return refNumberError;
	}
	//stockMode
	 function validate_stockMode() {
	        var len = $('input[type="radio"][name="stockMode"]:checked').length;
	        if (len == 0) {
	            $("#stockModeError").show();
	            $("#stockModeError").html("Choose One<b>Stock Mode</b>");
	            $("#stockModeError").css("color", "red");
	            stockModeError = false;
	        } else {
	            $("#stockModeError").hide();
	            stockModeError = true;
	        }
	        return stockModeError;
	    }
	 //-------//
	 function validate_stockSource() {
         var val = $("#stockSource").val();
         if (val == '') {
             $("#stockSourceError").show();
             $("#stockSourceError").html("Choose <b>Stock Source</b>");
             $("#stockSourceError").css("color", "red");
             stockSourceError = false;
         } else {
             $("#stockSourceError").hide();
             stockSourceError = true;
         }
         return stockSourceError;
     }
	//description
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
	//----on submit-----//
	$("#saleOrderForm").submit(function (){
		validate_orderCode();
		validate_refNumber();
		validate_stockMode();
		validate_stockSource();
		validate_description();
		if(orderCodeError && descriptionError
				&& refNumberError && stockModeError
				&& stockSourceError)
			return true;
		else
			false;
	});
});