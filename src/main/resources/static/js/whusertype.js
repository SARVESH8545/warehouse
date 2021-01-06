 /*$(document).ready(function () {
	 //---extra validations work --//
     //--Auto Fill for User For---
     $('input[type="radio"][name="userType"]').change(function () {
         autoFillUserFor();
     });

     function autoFillUserFor() {
         var val = $('input[type="radio"][name="userType"]:checked').val();
         if (val == 'Vendor') {
             $("#userFor").val('Purchase');
         } else if (val == 'Customer') {
             $("#userFor").val('Sale');
         }
     }
     //ifOther Text Input
     $("#userIdType").change(function(){
         var val = $("#userIdType").val();
         if(val == 'OTHER'){
             $("#ifother").removeAttr("readOnly");
            // $("#ifotherView").show();
         }else {
             $("#ifother").attr("readOnly","true");
             $("#ifother").val("");
            // $("#ifOtherView").hide();
         }
     });
     
     
     $("#userCodeError").hide();
     $("#userMailError").hide();
     
     var userCodeError = false;
     var userMailError = false;
     
     $("#userCode").keyup(function () {
         $("#userCode").val($("#userCode").val().toUpperCase());
         validate_userCode();
     });
     $("#userMail").change(function (){
    	 
     });
     //-------//
     function validate_userCode() {
         var val = $("#userCode").val();
         var exp = /^[A-Z]{4,25}$/;
         if (val == '') {
             $("#userCodeError").show();
             $("#userCodeError").html("Enter <b>user Code</b>");
             $("#userCodeError").css("color", "red");
             userCodeError = false;
         } else if (!exp.test(val)) {
             $("#userCodeError").show();
             $("#userCodeError").html("Enter <b>4-25 Chars Only</b>");
             $("#userCodeError").css("color", "red");
             userCodeError = false;
         } else {
             $("#userCodeError").hide();
             userCodeError = true;
         }
         return userCodeError;
     }
  });*/
 $(document).ready(function () {
        	$("#userMailError").hide();
            $("#userCodeError").hide();

        	
        	var userMailError = false;
        	var userCodeError = false;
        	
        	$("#userMail").keyup(function(){
        		validate_userMail();
        	});
        	//userCode
            $("#userCode").keyup(function () {
                $("#userCode").val($("#userCode").val().toUpperCase());
                validate_userCode();
            });

        	function validate_userMail(){
        		var val = $("#userMail").val();
        		var exp = /^[A-Za-z0-9\.\-]+\@[a-z]+\.[a-z\.]{2,10}$/;
        		if(val==''){
        			$("#userMailError").html("Enter <b>User Mail</b>");
        			$("#userMailError").css("color","red");
        			$("#userMailError").show();
        			userMailError = false;
        		} else if(!exp.test(val)){
        			$("#userMailError").html("Enter <b>Valid Mail</b>");
        			$("#userMailError").css("color","red");
        			$("#userMailError").show();
        			userMailError = false;
        		} else {
        			$.ajax({
        				url: 'mailcheck',
        				data: {'mail':val},
        				success:function(resTxt) {
        					if(resTxt!=''){
        						$("#userMailError").html(resTxt);
        	        			$("#userMailError").css("color","red");
        	        			$("#userMailError").show();
        	        			userMailError = false;
        					}else{
        						$("#userMailError").hide();
        						userMailError = true;
        					}
        				}
        			});
        		}
        		return userMailError;
        	}
        	//userCode function
        	function validate_userCode() {
                var val = $("#userCode").val();
                var exp = /^[A-Z]{4,25}$/;
                if (val == '') {
                    $("#userCodeError").show();
                    $("#userCodeError").html("Enter <b>user Code</b>");
                    $("#userCodeError").css("color", "red");
                    userCodeError = false;
                } else if (!exp.test(val)) {
                    $("#userCodeError").show();
                    $("#userCodeError").html("Enter <b>4-25 Chars Only</b>");
                    $("#userCodeError").css("color", "red");
                    userCodeError = false;
                } else {
                    $("#userCodeError").hide();
                    userCodeError = true;
                }
                return userCodeError;
            }        	
            //---extra validations work --//
            //--Auto Fill for User For---
            $('input[type="radio"][name="userType"]').change(function () {
                autoFillUserFor();
            });

            function autoFillUserFor() {
                var val = $('input[type="radio"][name="userType"]:checked').val();
                if (val == 'Vendor') {
                    $("#userFor").val('Purchase');
                } else if (val == 'Customer') {
                    $("#userFor").val('Sale');
                }
            }
            //ifOther Text Input
            $("#userIdType").change(function(){
                var val = $("#userIdType").val();
                if(val == 'OTHER'){
                    $("#ifother").removeAttr("readOnly");
                }else {
                    $("#ifother").attr("readOnly","true");
                    $("#ifother").val("");
                }
            });
            
            //---on submit---
            $("#whUserTypeForm").submit(function(){
            	validate_userMail();
            	
            	if(userMailError) return true;
            	else return false;
            });

        });

 