/**
 * 
 */
var stompClient = null;


window.onload = function() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + "123456", function (greeting) {
        	console.log(greeting.body);
        	var obj = JSON.parse(greeting.body);
        	$.ajax({
      	      type: "POST",
      	      contentType : "application/json;charset=utf-8",
      	      dataType:"json",
      	      url: "/chat_check",
      	      async: true,
      	      data:JSON.stringify({"inputname" : obj.Inname, "outputname" : obj.Ouname, "message" : obj.message }),
      	      error:function (XMLHttpRequest, textStatus, errorThrown) {
      	    	  alert(XMLHttpRequest + textStatus + "error");
      	    	},
      	      success: function(data){
      	    	  if(data.msg == "success"){
      	    		  $._messengerDefaults = {
      	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
      	    				};
      	    			 $.globalMessenger().post({
      	    		            message: "发送成功",
      	    		            hideAfter: 3,
      	    		            type: 'success',
      	    		            //showCloseButton: true
      	    		        });
      	    	  }else if(data.msg == "error"){
      	    		$._messengerDefaults = {
  	    					extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
  	    				};
  	    			 $.globalMessenger().post({
  	    		            message: "发送失败",
  	    		            hideAfter: 3,
  	    		            type: 'error',
  	    		            //showCloseButton: true
  	    		        });
      	    	  }
      	      }
      	  });
        });
    });
};


function sendName() {
    stompClient.send("/app/" + "201300", {}, JSON.stringify({'inputname': "201300", 'outputname' : "123456", 'message' : $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});

