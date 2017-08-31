// JavaScript Document

$(function(){
	
	//ajaxCSRF
	jQuery(document).ajaxSend(function(event, xhr, settings) {
	    function getCookie(name) {
	        var cookieValue = null;
	        if (document.cookie && document.cookie != '') {
	            var cookies = document.cookie.split(';');
	            for (var i = 0; i < cookies.length; i++) {
	                var cookie = jQuery.trim(cookies[i]);
	                // Does this cookie string begin with the name we want?
	                if (cookie.substring(0, name.length + 1) == (name + '=')) {
	                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
	                    break;
	                }
	            }
	        }
	        return cookieValue;
	    }
	    function sameOrigin(url) {
	        // url could be relative or scheme relative or absolute
	        var host = document.location.host; // host + port
	        var protocol = document.location.protocol;
	        var sr_origin = '//' + host;
	        var origin = protocol + sr_origin;
	        // Allow absolute or scheme relative URLs to same origin
	        return (url == origin || url.slice(0, origin.length + 1) == origin + '/') ||
	            (url == sr_origin || url.slice(0, sr_origin.length + 1) == sr_origin + '/') ||
	            // or any other URL that isn't scheme relative or absolute i.e relative.
	            !(/^(\/\/|http:|https:).*/.test(url));
	    }
	    function safeMethod(method) {
	        return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
	    }
	 
	    if (!safeMethod(settings.type) && sameOrigin(settings.url)) {
	        xhr.setRequestHeader("X-CSRFToken", getCookie('csrftoken'));
	    }
	});
	
	//关闭查询和发布信息界面
	$('#gray-canvas').click(function(){
		$('#dialog').fadeOut(50,"linear",function(){
			$('#gray-canvas').fadeOut(50,"linear",function(){
				$('#class-push').hide();
				$('#class-push li').remove();
			});
		});
	});
	
	//ajax推送信息查询
	$.get("/index/ajaxPush",{code : "xxx"},function(data){
		if(data.haspush=='true'){	
			$('#gray-canvas').fadeIn(100,"linear",function(){
				$('#dialog').fadeIn(100,"linear",function(){
					$('#class-push h1').text("消息推送：");
					$('#class-push li').remove();
					$.each(data,function(key,value){
						if(key!="haspush")
							$('#class-push ul').append($('<li>'+value+'</li>'));
					});
				});
			});
		}
	});
});
