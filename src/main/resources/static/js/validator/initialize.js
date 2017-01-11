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
              return value == sum;
            }
          }
        }
      }
    }
  });
  $('.login_btn').click(function() {
    $('.login_validate').bootstrapValidator('validate');
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
  $('.change_btn').click(function() {
    $('.change_password_validate').bootstrapValidator('validate');
  });

  // 我的账户
  $('.form-body').bootstrapValidator({
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
      }
    }
  });
});