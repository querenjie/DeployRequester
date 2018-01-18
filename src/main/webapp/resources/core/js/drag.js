/* 
 * drag 1.0
 * 拖动滑块
 */
(function($){
    $.fn.drag = function(options){
        var x, drag = this, isMove = false, defaults = {
        };
        var options = $.extend(defaults, options);
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
        handler.mousedown(function(e){
            isMove = true;
            x = e.pageX - parseInt(handler.css('left'), 10);
        });
        
        //鼠标指针在上下文移动时，移动距离大于0小于最大间距，滑块x轴位置等于鼠标移动距离
        $(document).mousemove(function(e){
            var _x = e.pageX - x;
            if(isMove){
                if(_x > 0 && _x <= maxWidth){
                    handler.css({'left': _x});
                    drag_bg.css({'width': _x});
                }else if(_x > maxWidth){  //鼠标指针移动距离达到最大时清空事件
                	if(!dragOkRr){
                		dragOkRr=dragOk();
                	}
                   
                }
            }
        }).mouseup(function(e){
            isMove = false;
            var _x = e.pageX - x;
            if(_x < maxWidth){ //鼠标松开时，如果没有达到最大距离位置，滑块就返回初始位置
                handler.css({'left': 0});
                drag_bg.css({'width': 0});
            }
        });
        
        //清空事件
        function dragOk(){
        	var code=$('#captcha_id').val();
        	var flag=true;
        	$.ajax({
        		url : "capthca/checkCapthcaCode?code="+code,
        		type : "get",
        		async:false,  
        		dataType : 'JSON',
        		success : function(result) {
        			if (result.returnCode == 1) {
        				handler.removeClass('handler_bg').addClass('handler_ok_bg');
        	            text.text('验证通过');
        	            drag.css({'color': '#fff'});
        	            handler.unbind('mousedown');
        	            $(document).unbind('mousemove');
        	            $(document).unbind('mouseup');
        	            //激活首页登录按钮
        	            $('#loginSubmitbtn').attr("disabled",false); 
        	            dragOkRr
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


