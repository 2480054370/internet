/**
 * myProfile
 */

$(document).ready(function() {
    $('.login_btn').click(function() {
        $('.login_Form').bootstrapValidator('validate');
        loadLogin();
	});
    
    $('#authentication').click(function() {
	    $('#form_authentication').bootstrapValidator('validate');
		  if($("#phone").val() == "" || $("#email").val() == ""){
			  console.log("phone or email is null");
		  }else{
	    	  clickattt();
		  }
	    });
	    $('#updatepassword').click(function() {
		    $('#form_updatepassword').bootstrapValidator('validate');
    		  if($("#new_password").val() != $("#confirm_password").val()){
    			  console.log("password entries are inconsistent");
    		  }else{
		    	  clickuppwd();
    		  }
		    });
	
/*	
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/profile_session",
	      async: true,
	      data:{},
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "no session"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "您还没有登录，请登录后再试",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 $(".container").empty();
	    			 $('.loginAfter').css('display','none');
		    		 $('.loginBefore').css('display','block');
	    	  }else if(data.msg == "have session"){
	    		    $('#authentication').click(function() {
	    		    $('#form_authentication').bootstrapValidator('validate');
		    		  if($("#phone").val() == "" || $("#email").val() == ""){
		    			  console.log("phone or email is null");
		    		  }else{
	    		    	  clickattt();
		    		  }
	    		    });
	    		    $('#updatepassword').click(function() {
		    		    $('#form_updatepassword').bootstrapValidator('validate');
			    		  if($("#new_password").val() != $("#confirm_password").val()){
			    			  console.log("password entries are inconsistent");
			    		  }else{
		    		    	  clickuppwd();
			    		  }
		    		    });
	    		  
	    		  $('.loginAfter').css('display','block');
	    			$('.loginBefore').css('display','none');
	    	  }
	      }
	  });
*/
	
	
	
	
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

function clickattt(){
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/profile_authentication",
	      async: true,
	      data:JSON.stringify({"phone" : $('#phone').val(), "email" : $('#email').val()}),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "have session"){
	    			  $('.loginAfter').css('display','block');
		    	      $('.loginBefore').css('display','none');
		    	  	  $("#myphone").html(data.phone);
		    	  	  $("#myemail").html(data.email);
		    	  	  $("#myheademail").html(data.email);
		    	  	  $("#phone").val("");
		    	  	  $("#email").val("");
		    	  	  $('#authentication').removeAttr("disabled");
		    	  	  //销毁bootstrapValidator
		    	  	  $("#form_authentication").data('bootstrapValidator').destroy();
		    	  	  $('#form_authentication').data('bootstrapValidator', null);
	    		      //重新加载bootstrapValidator
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
	    		      //提示成功
		    		  $._messengerDefaults = {
		    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
		    				};
		    		  $.globalMessenger().post({
	    		          message: "保存成功",
	    		          hideAfter: 2,
	    		          type: 'success',
	    		            //showCloseButton: true
	    		      });
	    		  }
	    	  else if(data.msg == "no session"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "您还没有登录，请登录后再试",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 //$(".container").empty();
	    			 $('.loginAfter').css('display','none');
		    		 $('.loginBefore').css('display','block');
	    	  }
	      }
	  });
//});
}
function clickuppwd(){
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/profile_updatepassword",
	      async: true,
	      data:JSON.stringify({"old_password" : $('#old_password').val(), "new_password" : $('#new_password').val()}),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "have session"){
	    		  if(data.data == "success"){
	    			  $('.loginAfter').css('display','block');
		    	      $('.loginBefore').css('display','none');
		    	  	  $("#confirm_password").val("");
		    	  	  $("#new_password").val("");
		    	  	  $("#old_password").val("");
		    	  	  $('#updatepassword').removeAttr("disabled");
		    	  	  //销毁bootstrapValidator
		    	  	  $("#form_updatepassword").data('bootstrapValidator').destroy();
		    	  	  $('#form_updatepassword').data('bootstrapValidator', null);
	    		      //重新加载bootstrapValidator
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
	    		      //提示成功
		    		  $._messengerDefaults = {
		    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
		    				};
		    		  $.globalMessenger().post({
	    		          message: "保存成功",
	    		          hideAfter: 2,
	    		          type: 'success',
	    		            //showCloseButton: true
	    		      });
	    		    }else if(data.data == "error"){
			    		  $._messengerDefaults = {
			    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
			    				};
			    		  $.globalMessenger().post({
		    		          message: "旧密码输入错误",
		    		          hideAfter: 2,
		    		          type: 'error',
		    		            //showCloseButton: true
		    		      });
	    		    }
	    		  }
	    	  else if(data.msg == "no session"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "您还没有登录，请登录后再试",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 //$(".container").empty();
	    			 $('.loginAfter').css('display','none');
		    		 $('.loginBefore').css('display','block');
	    	  }
	      }
	  });
}
$('#basic').click(function(){
	var val=$('input:radio[name="gender"]:checked').val();
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/profile_basic",
	      async: true,
	      data:JSON.stringify({"address" : $('#address').val(), "name" : $('#name').val(), "birth" : $('#birth').val(), "hobby" : $('#hobby').val(), "sign" : $('#sign').val(), "sex" : $('input:radio[name="gender"]:checked').val()}),
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "have session"){
	    		  $('.loginAfter').css('display','block');
	    	      $('.loginBefore').css('display','none');
	    	  	  $("#myaddress").html(data.address);
	    	  	  $("#myusername").html(data.name);
	    	  	  $("#myusernametop").html(data.name);
	    	  	  $("#myusernamehead").html(data.name);
	    	  	  $("#mybirthday").html(data.birth);
	    	  	  $("#myhobby").html(data.hobby);
	    	  	  $("#mysign").html(data.sign);
	    	  	  $("#mysex").html(data.sex);
	    	  	  $("#address").val("");
	    	  	  $("#name").val("");
	    	  	  $("#birth").val("");
	    	  	  $("#hobby").val("");
	    	  	  $("#sign").val("");
	    	  	  $("#gender1").removeAttr('checked');
	    	  	  $("#gender2").removeAttr('checked');
	    	  }else if(data.msg == "no session"){
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "您还没有登录，请登录后再试",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 //$(".container").empty();
	    			 $('.loginAfter').css('display','none');
		    		 $('.loginBefore').css('display','block');
	    	  }
	      }
	  });
});
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
	    	  if(data.msg == "login success" & $('#account').val().length >= 6 & $('#captcha').val() != ""){
	    	  	$('#captcha').val("");
	    	  	window.location.href = "/";
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

//头像上传

//实时预览
$("#uploadFile").change(function () {
    var fil = this.files;
    for (var i = 0; i < fil.length; i++) {
        reads(fil[i]);
    }
});
function reads(fil){
    var reader = new FileReader();
    reader.readAsDataURL(fil);
    reader.onload = function()
    {
    	$("#head-loader").css('display','block');
        $("#headimg").attr("src",reader.result);
    	$("#uploadhead").ajaxSubmit({
    		type: 'POST',  
    		url: "/uploadAvatar" ,  
    		success: function(data){
    			if(data.msg == "上传成功"){
    	    		  $._messengerDefaults = {
    	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
    	    				};
    	    		  $.globalMessenger().post({
      		          message: "保存成功",
      		          hideAfter: 2,
      		          type: 'success',
      		            //showCloseButton: true
      		      });
    				$("#headimg").attr("src","http://oihey4yi1.bkt.clouddn.com/" + data.file);
    				$("#headimg2").attr("src","http://oihey4yi1.bkt.clouddn.com/" + data.file);
    				$("#headimg3").attr("src","http://oihey4yi1.bkt.clouddn.com/" + data.file);
    				$("#head-loader").css('display','none');
    			}else if(data.msg == "上传失败"){
  	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "保存失败，请检查网络设置",
	    		            hideAfter: 2,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 $("#head-loader").css('display','none');
    			}
    		},  
    		error: function(XmlHttpRequest, textStatus, errorThrown){  
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "Serious error",
	    		            hideAfter: 2,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 $("#head-loader").css('display','none');
    		}  
    	});
    };
}

//背景上传
$("#uploadFile2").change(function () {
    var fil = this.files;
    for (var i = 0; i < fil.length; i++) {
        reads2(fil[i]);
    }
});
function reads2(fil){
    var reader = new FileReader();
    reader.readAsDataURL(fil);
    reader.onload = function()
    {
    	$("#banner-loader").css('display','block');
        $("#bannerimg").attr("src",reader.result);
    	$("#uploadbanner").ajaxSubmit({
    		type: 'POST',  
    		url: "/uploadbanner" ,  
    		success: function(data){  
    			if(data.msg == "上传成功"){
    	    		  $._messengerDefaults = {
    	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
    	    				};
    	    		  $.globalMessenger().post({
      		          message: "保存成功",
      		          hideAfter: 2,
      		          type: 'success',
      		            //showCloseButton: true
      		      });
    	    		$("#bannerimg").attr("src","http://oihey4yi1.bkt.clouddn.com/" + data.file);
    	    		$("#banner-loader").css('display','none');
    			}else if(data.msg == "上传失败"){
    	    		  $._messengerDefaults = {
  	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
  	    				};
  	    			 $.globalMessenger().post({
  	    		            message: "保存失败，请检查网络设置",
  	    		            hideAfter: 2,
  	    		            type: 'error',
  	    		            //showCloseButton: true
  	    		        });
  	    			$("#banner-loader").css('display','none');
    			}
    		},  
    		error: function(XmlHttpRequest, textStatus, errorThrown){  
	    		  $._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "Serious error",
	    		            hideAfter: 2,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    			 $("#banner-loader").css('display','none');
    		}  
    	}); 
    };
}


//退出
$('.exit').click(function(){
	$.ajax({
	      type: "POST",
	      contentType : "application/json;charset=utf-8",
	      dataType:"json",
	      url: "/exit",
	      async: true,
	      data:{},
	      error:function (XMLHttpRequest, textStatus, errorThrown) {
	    	  alert(XMLHttpRequest + textStatus + "error");
	    	},
	      success: function(data){
	    	  if(data.msg == "no session"){
	    			$._messengerDefaults = {
	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	    				};
	    			 $.globalMessenger().post({
	    		            message: "您还没有登录，请登录后再试",
	    		            hideAfter: 3,
	    		            type: 'error',
	    		            //showCloseButton: true
	    		        });
	    	  }else if(data.msg == "success exit"){
	    		  window.location.href = "/";
	    	  }
	      }
	  });
});
