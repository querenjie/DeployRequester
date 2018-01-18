/* 
 * drag 1.0
 * 拖动滑块
 */
(function($){
    $.fn.swipeRg = function(options){
        var x, drag = this, isMove = false, defaults = {
        };
       // var options = $.extend(defaults, options);
        //添加背景，文字，滑块
        var html = '<div class="drag_bg"></div>'+
                    '<div class="drag_text" onselectstart="return false;" unselectable="on">拖动滑块验证</div>'+
                    '<div class="handler handler_bg"></div>';
        $(this).html(html);
      //屏蔽首页登录按钮
        $('#loginSubmitbtn').attr("disabled","disabled"); 
        var handler = drag.find('.handler');
        var drag_bg = drag.find('.drag_bg');
        var text = drag.find('.drag_text');
        var maxWidth = drag.width() - handler.width();  //能滑动的最大间距
        var dragOkRr=false;
      //鼠标按下时候的x轴的位置
        
        $(handler).swipe({
        	
        	swipeRight:function(e,direction, distance, duration, fingerCount) {
    			if(fingerCount==1){
    				 if(distance > 0 && distance <= maxWidth){
    	                    handler.css({'left': distance});
    	                    drag_bg.css({'width': distance});
    	                    setTimeout(function(){
    	                    	handler.css({'left': 0});
    	                        drag_bg.css({'width': 0});
                              },300); 
    	                }else if(distance > maxWidth) {
    	                	if(!dragOkRr){
    	                		dragOkRr=dragOk();
    	                	}
    	                	handler.css({'left': distance});
    	                    drag_bg.css({'width': distance});
    	                	 isMove = false;
    	                    
    	                }else{
    	                	isMove = false;
    	                	handler.css({'left': 0});
	                        drag_bg.css({'width': 0});
    	                }
    			}

    		},
    		});
        
       
       
        
       /* handler.addEventListener('touchstart', function(event) { 
        	alert(event.targetTouches.length)
        	// 如果这个元素的位置内只有一个手指的话 
        	if (event.targetTouches.length == 1) { 
        	  var touch = event.targetTouches[0]; 
        	 // 把元素放在手指所在的位置 
        	  isMove = true;
              x = touch.pageX - parseInt(handler.css('left'), 10);
        	} 
        	}, false);
        handler.addEventListener('touchmove', function(event) { 
        	alert(1)
        	// 如果这个元素的位置内只有一个手指的话 
        	if (event.targetTouches.length == 1) { 
        	   var e = event.targetTouches[0]; 
        	   var _x = e.pageX - x;
	            if(isMove){
	                if(_x > 0 && _x <= maxWidth){
	                    handler.css({'left': _x});
	                    drag_bg.css({'width': _x});
	                }else if(_x > maxWidth){  //鼠标指针移动距离达到最大时清空事件
	                	if(!dragOkRr){
	                		dragOkRr=dragOk();
	                	}
	                	isMove = false;
                       var _x = e.pageX - x;
                       if(_x < maxWidth){ //鼠标松开时，如果没有达到最大距离位置，滑块就返回初始位置
                           handler.css({'left': 0});
                           drag_bg.css({'width': 0});
                       }
	                }
	            }
        	} 
        	}, false);
        handler.addEventListener('touchend', function(event) { 
	        	isMove = false;
	            var _x = e.pageX - x;
	            if(_x < maxWidth){ //鼠标松开时，如果没有达到最大距离位置，滑块就返回初始位置
	                handler.css({'left': 0});
	                drag_bg.css({'width': 0});
	            }
        	}, false);
       
       */
        
        //清空事件
        function dragOk(){
        	var code=$('#captcha_id').val();
        	var flag=true;
        	$.ajax({
        		url : "capthca/checkCapthcaCode?code="+code,
        		type : "get",
        		//async:false,  
        		dataType : 'JSON',
        		success : function(result) {
        			if (result.returnCode == 1) {
        				handler.removeClass('handler_bg').addClass('handler_ok_bg');
        	            text.text('验证通过');
        	            drag.css({'color': '#fff'});
        	           // handler.unbind('swipeDown');
        	           // $(handler).unbind('swipeRight');
        	           // $(handler).unbind('swipeUp');
        	            //激活首页登录按钮
        	            $('#loginSubmitbtn').attr("disabled",false); 
        			} else {
        				flag=false;
        				text.text('验证失败');
        			}
        		},
        		error : function(data) {
        			flag=false;
        			alert("系统异常！");
        		}
        	})
        	
           return flag; 
        }
    };
    
   
})(jQuery);


