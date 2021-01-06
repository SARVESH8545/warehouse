$(document).ready(function (){
	//1. hide span tag

	$("#grnCodeError").hide();
	$("#grnTypeError").hide();
	$("#descriptionError").hide();

	//2.define flag
	var grnCodeError = false;
	var grnTypeError = false;
	var descriptionError = false;

	//function
	$("#grnCode").keyup(function(){
		$(this).val($(this).val().toUpperCase());
		validate_grnCode();
	});

	$("grnType").kryup(function(){
		validate_grnType();
	});

	$("#description").keyup(function () {
		validate_description();
	});

	//grnCode
	function validate_grnCode(){
		var val = $("#grnCode").val();
		var exp = /^[a-zA-Z0-9]{4,25]$/;
		if(val == ''){
			$("#grnCodeError").html("Enter <b> grn Code</b>");
			$("#grnCodeError").css("color", "red");
			$("#grnCodeError").show();
			grnCodeError = false;
		}else if(!exp.test(val)){
			$("#grnCodeError").html("Must be <b> 4-25 Chars only</b>");
			$("#grnCodeError").css("color", "red");
			$("#grnCodeError").show();	
			grnCodeError = false;
		}else{
			//AJAX START
			$.ajax({
				url : 'validatecode',
				data : {"code":val},
				success: function(resTxt){
					if(resTxt!=""){
						$("#grnCodeError").html("Enter <b> grn Code</b>");
						$("#grnCodeError").css("color", "red");
						$("#grnCodeError").show();
						grnCodeError = false;	
					}else{
						$("#grnCodeError").hide();
						grnCodeError = true;
					}
				}
			});
		}
		return grnCodeError;
	}

	function validate_grnType(){
		var val = $("#grnType").val();
		if(val == ''){
			$("#grnTypeError").html("<b>Enter Type</b>");
			$("#grnTypeError").css("color","red");
			$("#grnTypeError").show();
			grnTypeError = false;
		}else{
			$("#grnTypeError").hide();
			grnTypeError = true;
		}
		return grnTypeError;
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
	
	$("#grnForm").submit(function(){
		validate_grnCode();
		validate_grnType();
		validate_description();
		
		if(grnCodeError && 
				grnTypeError &&
				descriptionError )
			return true;
		else false;
	});
});