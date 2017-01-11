/**
 * login 
 */
var numvalue;
var numsum;

$(document).ready(function() {
	// Generate a simple captcha
  function randomNumber(min, max) {
      return Math.floor(Math.random() * (max - min + 1) + min);
  };
  // 算数验证码
	$('#captchaOperation, #captchaOperation2').html([randomNumber(1, 100), '+', randomNumber(1, 200), '='].join(' '));
  // 验证登录
  $('.login_validate').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
      name: {
        message: 'The username is not valid',
        validators: {
          notEmpty: {
            message: '帐号不能为空'
          },
          stringLength: {
            min: 6,
            max: 30,
            message: '帐号最少6个字符'
          }
        }
      },
      password: {
        validators: {
          notEmpty: {
            message: '密码不能为空'
          }
        }
      },
      captcha: {
        validators: {
          callback: {
            message: '你好菜，数学体育老师教的？',
            callback: function(value, validator) {
              var items = $('#captchaOperation').html().split(' '), sum = parseInt(items[0]) + parseInt(items[2]);
              numvalue = value;
              numsum = sum;
              return value == sum;
            }
          }
        }
      }
    }
  });
  $('.login_btn').click(function() {
    $('.login_validate').bootstrapValidator('validate');
	loadLogin();
  });

  // 验证注册
  $('.register_validate').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	
    	email: {
	        validators: {
	          notEmpty: {
	            message: '邮件地址不能为空'
	          },
	          emailAddress: {
	            message: '请输入正确的邮件地址如：123@qq.com'
	          }
	        }
	      },
      name: {
        message: 'The username is not valid',
        validators: {
          notEmpty: {
            message: '帐号不能为空'
          },
          stringLength: {
            min: 6,
            max: 30,
            message: '帐号最少6个字符'
          }
        }
      },
      new_password: {
        validators: {
          notEmpty: {
            message: '密码不能为空'
          },
          identical: {//相同
            field: 'new_password',
            message: '两次密码不一致'
          }
        }
      },
      confirm_password: {
        validators: {
          notEmpty: {
            message: '请再次确认'
          },
          identical: {//相同
            field: 'new_password',
            message: '两次密码不一致'
          }
        }
      },
      captcha2: {
        validators: {
          callback: {
            message: '你好菜，数学体育老师教的？',
            callback: function(value, validator) {
              var items = $('#captchaOperation2').html().split(' '), sum = parseInt(items[0]) + parseInt(items[2]);
              return value == sum;
            }
          }
        }
      }
    }
  });
  $('.register_btn').click(function() {
    $('.register_validate').bootstrapValidator('validate');
    loadregister();
  });

  // 验证忘记密码发送短信
  $('.forget_validate').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
      phone_number: {
        message: 'The username is not valid',
        validators: {
          notEmpty: {
            message: '这是必要的'
          },
          stringLength: {
            min: 11,
            max: 11,
            message: '这好像不是一个手机号码'
          },
          regexp: {
            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
            message: '请输入正确的手机号码'
          }
        }
      },
      code: {
        message: 'The username is not valid',
        validators: {
          notEmpty: {
            message: '这也是必要的'
          }
        }
      }
    }
  });
  $('.forget_btn').click(function() {
	  loadCodeCheck();
    $('.forget_validate').bootstrapValidator('validate');
  });

  // 验证修改密码
  $('.change_password_validate').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
      new_password: {
        validators: {
          notEmpty: {
            message: '密码不能为空'
          },
          identical: {//相同
            field: 'new_password',
            message: '两次密码不一致'
          }
        }
      },
      confirm_password: {
        validators: {
          notEmpty: {
            message: '请再次确认'
          },
          identical: {//相同
            field: 'new_password',
            message: '两次密码不一致'
          }
        }
      }
    }
  });
//  $('.change_btn').click(function() {
//	    $('.change_password_validate').bootstrapValidator('validate');
//	  });


  // 我的账户
  $('#form_authentication').bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
      phone: {
        message: 'The username is not valid',
        validators: {
          notEmpty: {
            message: '手机号码不能为空'
          },
          stringLength: {
            min: 11,
            max: 11,
            message: '这好像不是一个手机号码'
          },
          integer:{
            message: '　'
          },
          regexp: {
            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
            message: '请输入正确的手机号码'
          }
        }
      },
      password: {
        validators: {
          notEmpty: {
            message: '密码不能为空'
          }
        }
      },
      email: {
        validators: {
          notEmpty: {
            message: '邮件地址不能为空'
          },
          emailAddress: {
            message: '请输入正确的邮件地址如：123@qq.com'
          }
        }
      }
    }
  });
  
  
  $('#form_updatepassword').bootstrapValidator({
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	  new_password: {
	    	      validators: {
	    	        notEmpty: {
	    	          message: '密码不能为空'
	    	        },
	    	        identical: {//相同
	    	          field: 'new_password',
	    	          message: '两次密码不一致'
	    	        }
	    	      }
	    	    },
	    	    confirm_password: {
	    	      validators: {
	    	        notEmpty: {
	    	          message: '请再次确认'
	    	        },
	    	        identical: {//相同
	    	          field: 'new_password',
	    	          message: '两次密码不一致'
	    	        }
	    	      }
	    	    }
	    }
  });

});


$(document).on("click", "[name='register']", function(){
	$(".card-signup").removeClass("form_toggle");
	$("[name='register_Form']").addClass("form_toggle");
})
$(document).on("click", "[name='login']", function(){
	$(".card-signup").removeClass("form_toggle");
	$("[name='login_Form']").addClass("form_toggle");
})
$(document).on("click", "[name='forget']", function(){
	$(".card-signup").removeClass("form_toggle");
	$("[name='forget_Form']").addClass("form_toggle");
})
$(document).on("click", "[name='change_password']", function(){
	if($("[name='success']").hasClass("has-success")){
		$(".card-signup").removeClass("form_toggle");
		$("[name='change_password_Form']").addClass("form_toggle");
	}
	else{
		event.stopPropagation();
	}
})

		function loadLogin(){
	//var account = document.getElementById("account").value;
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/login_check",
	      async: true,
	      data:JSON.stringify({"account" : $("#account").val(), "pwd" : $("#pwd").val()}),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "login success"){
	    	  	if($('#captcha').val() == ""){
	    			$._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "验证码不能为空，请重新输入",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  	}else if(numvalue != numsum){
	    			$._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "验证码输入不正确，请重新输入",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  	}else{
	    	  		 $('#captcha').val("");
			    	 window.location.href = "/";
	    	  	}
	    	  }else if(data.msg == "pwd error"){
	    			$._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "账号或密码错误，请重新输入",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  }else if(data.msg == "account error" & $('#account').val().length >= 6){
	    			$._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "账号或密码错误，请重新输入",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  }
	      }
	  });
}

function loadregister(){
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/register_check",
	      async: true,
	      data:JSON.stringify({"reaccount" : $("#reaccount").val(), "repwd" : $("#repwd").val(), "rerepwd" : $("#rerepwd").val(), "remail" : $("#remail").val()}),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "register success" & $('#reaccount').val().length >= 6 & $('#recaptcha').val() != "" & $('#repwd').val() == $('#rerepwd').val()){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    		  $.globalMessenger().post({
    		          message: "注册成功",
    		          hideAfter: 2,
    		          type: 'success',
    		            //showCloseButton: true
    		      });
	    		  //清空内容
	    		$('#recaptcha').val("");
	    		$('#remail').val("");
	    		$('#reaccount').val("");
	    		$('#repwd').val("");
	    		$('#rerepwd').val("");
	    		//销毁bootstrapValidator
	    	  	  $(".register_validate").data('bootstrapValidator').destroy();
	    	  	  $('.register_validate').data('bootstrapValidator', null);
    		      //重新加载bootstrapValidator
	    	  	$('.register_validate').bootstrapValidator({
					message: 'This value is not valid',
					feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
						email: {
				        validators: {
				          notEmpty: {
				            message: '邮件地址不能为空'
				          },
				          emailAddress: {
				            message: '请输入正确的邮件地址如：123@qq.com'
				          }
				        }
				      },
						name: {
					message: 'The username is not valid',
					validators: {
  						notEmpty: {
    						message: '帐号不能为空'
  						},
  						stringLength: {
    						min: 6,
    						max: 30,
    						message: '帐号最少6个字符'
  						}
					}
					},
					new_password: {
					validators: {
  						notEmpty: {
    						message: '密码不能为空'
  						},
  						identical: {//相同
    						field: 'new_password',
    						message: '两次密码不一致'
  						}
					}
					},
					confirm_password: {
					validators: {
  						notEmpty: {
    						message: '请再次确认'
  						},
  						identical: {//相同
    						field: 'new_password',
    						message: '两次密码不一致'
  						}
					}
					},
					captcha2: {
					validators: {
  						callback: {
    						message: '你好菜，数学体育老师教的？',
    						callback: function(value, validator) {
      							var items = $('#captchaOperation2').html().split(' '), sum = parseInt(items[0]) + parseInt(items[2]);
      							return value == sum;
    						}
  						}
					}
					}
			}
			});
    		      //跳转回登陆
	    	  	$(".card-signup").removeClass("form_toggle");
	    	  	$("[name='login_Form']").addClass("form_toggle");
	    	  }else if(data.msg == "reaccount error" & $('#reaccount').val().length >= 6){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "账号已存在，请重新输入",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  }
	      }
	  });
}

function loadCodeCheck(){
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/check_vercode",
	      async: true,
	      data:JSON.stringify({"findemail" : $("#find_email").val(), "vercode" : $("#vercode").val() }),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "code success"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "验证通过",
	    		            hideAfter: 2,
	    		            type: 'success',
	    		            //showCloseButton: true
	    		        });
	    			 //跳转修改密码
	    			 $(".card-signup").removeClass("form_toggle");
	 				$("[name='change_password_Form']").addClass("form_toggle");
	 				updatepassword(data.account);
	 				
	 				//清空内容
	 				$("#find_email").val("");
	 				$("#vercode").val("");
	 				//销毁bootstrapValidator
		    	  	  $(".forget_validate").data('bootstrapValidator').destroy();
		    	  	  $('.forget_validate').data('bootstrapValidator', null);
	 				//重新加载bootstrapValidator
	 				  $('.forget_validate').bootstrapValidator({
	 					    message: 'This value is not valid',
	 					    feedbackIcons: {
	 					        valid: 'glyphicon glyphicon-ok',
	 					        invalid: 'glyphicon glyphicon-remove',
	 					        validating: 'glyphicon glyphicon-refresh'
	 					    },
	 					    fields: {
	 					      phone_number: {
	 					        message: 'The username is not valid',
	 					        validators: {
	 					          notEmpty: {
	 					            message: '这是必要的'
	 					          },
	 					          stringLength: {
	 					            min: 11,
	 					            max: 11,
	 					            message: '这好像不是一个手机号码'
	 					          },
	 					          regexp: {
	 					            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
	 					            message: '请输入正确的手机号码'
	 					          }
	 					        }
	 					      },
	 					      code: {
	 					        message: 'The username is not valid',
	 					        validators: {
	 					          notEmpty: {
	 					            message: '这也是必要的'
	 					          }
	 					        }
	 					      }
	 					    }
	 					  });
	    	  }else if(data.msg == "code error"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "验证码不正确，请重新输入",
	    		            hideAfter: 2,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 //不跳转
	    			 $(".card-signup").removeClass("form_toggle");
	    				$("[name='forget_Form']").addClass("form_toggle");
	    	  }
	      }
	  });
}
	

function updatepassword(account){
	var upaccount = account;
	$('.change_btn').click(function() {
		$.ajax({
		      type: "POST",
		      contentType : "application/json;charset=utf-8",
		      dataType:"json",
		      url: "/check_updatepwd",
		      async: true,
		      data:JSON.stringify({"cuppwd" : $("#CUpPwd").val(), "cuprepwd" : $("#CUpRePwd").val(), "upaccount" : upaccount}),
		      error:function (XMLHttpRequest, textStatus, errorThrown) {
		    	  alert(XMLHttpRequest + textStatus + "error");
		    	},
		      success: function(data){
		    	  if(data.msg == "update success"){
		    		  $._messengerDefaults = {
		    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
		    				};
		    			 $.globalMessenger().post({
		    		            message: "修改成功",
		    		            hideAfter: 2,
		    		            type: 'success',
		    		            //showCloseButton: true
		    		        });
		    			 //跳转登陆
		    				$(".card-signup").removeClass("form_toggle");
		    				$("[name='login_Form']").addClass("form_toggle");
		    				
		    				//清空内容
		    				$("#CUpPwd").val("");
		    				$("#CUpRePwd").val("");
			 				//销毁bootstrapValidator
				    	  	  $(".change_password_validate").data('bootstrapValidator').destroy();
				    	  	  $('.change_password_validate').data('bootstrapValidator', null);
			 				//重新加载bootstrapValidator
		    				$('.change_password_validate').bootstrapValidator({
		    				    message: 'This value is not valid',
		    				    feedbackIcons: {
		    				        valid: 'glyphicon glyphicon-ok',
		    				        invalid: 'glyphicon glyphicon-remove',
		    				        validating: 'glyphicon glyphicon-refresh'
		    				    },
		    				    fields: {
		    				      new_password: {
		    				        validators: {
		    				          notEmpty: {
		    				            message: '密码不能为空'
		    				          },
		    				          identical: {//相同
		    				            field: 'new_password',
		    				            message: '两次密码不一致'
		    				          }
		    				        }
		    				      },
		    				      confirm_password: {
		    				        validators: {
		    				          notEmpty: {
		    				            message: '请再次确认'
		    				          },
		    				          identical: {//相同
		    				            field: 'new_password',
		    				            message: '两次密码不一致'
		    				          }
		    				        }
		    				      }
		    				    }
		    				  });
		    	  }else if(data.msg == "update error"){
		    		  $._messengerDefaults = {
		    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
		    				};
		    			 $.globalMessenger().post({
		    		            message: "两次密码输入不一致，请重新输入",
		    		            hideAfter: 2,
		    		            type: 'error',
		    		            //showCloseButton: true
		    		        });
		    			 //不跳转
		 				$(".card-signup").removeClass("form_toggle");
						$("[name='change_password_Form']").addClass("form_toggle");
		    	  }
		      }
		  });
	});
}

$('#sendCode').click(function() {
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/send_email",
	      async: true,
	      data:JSON.stringify({"findemail" : $("#find_email").val() }),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "send success"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "发送成功，请到邮箱查看验证码",
	    		            hideAfter: 3,
	    		            type: 'success',
	    		            //showCloseButton: true
	    		        });
	    	  }else if(data.msg == "send error"){
	    			
	    	  }else if(data.msg == "account not exist"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "用户不存在，请检查输入是否正确",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  }
	      }
	  });
});