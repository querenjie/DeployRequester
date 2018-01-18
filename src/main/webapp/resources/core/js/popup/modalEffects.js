/**
 * modalEffects.js v1.0.0
 * http://www.codrops.com
 *
 * Licensed under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * Copyright 2013, Codrops
 * http://www.codrops.com
 */
 // 弹窗定位
function yyi(obj,offset){
          var objClass = obj.class;
          var top;
          var left;
          if($(obj).hasClass("md-modal")){
            top = ($(window).height() - $('#'+obj.id).height())/2;   
            left = ($(window).width() - $('#'+obj.id).width())/2; 
          }
          if($(obj).hasClass("md-tree")){
            top = ($(window).height() - $('#'+obj.id).height())/2;   
            left = ($(window).width() - $('#'+obj.id).width())/1.12; 
          }
          if($(obj).hasClass("md-treeqw")){
            var ssd = offset.top+30;
            var qqd = offset.left;
            top = ssd;
            left = qqd; 
          }
          var scrollTop = $(document).scrollTop();   
          var scrollLeft = $(document).scrollLeft();  
          $('#'+obj.id).css( { position : 'absolute', top : top + scrollTop, left : left + scrollLeft} );  
          }

var ModalEffects = (function() {

	function init() {

		//var overlay = document.querySelector( '.md-overlay' );

		[].slice.call( document.querySelectorAll( '.md-trigger' ) ).forEach( function( el, i ) {

			var modal = document.querySelector( '#' + el.getAttribute( 'data-modal' ) ),
				close = modal.querySelector( '.md-close' );
				close1 = modal.querySelector( '.abc' );
				close2 = modal.querySelector( '.asd' );
			function removeModal( hasPerspective ) {
				classie.remove( modal, 'md-show' );

				if( hasPerspective ) {
					classie.remove( document.documentElement, 'md-perspective' );
				}
			}

			function removeModalHandler() {
				removeModal( classie.has( el, 'md-setperspective' ) ); 
			}

        el.addEventListener('click', function(ev) {
        classie.add(modal, 'md-show');
        yyi(modal,$(el).offset());
        // overlay.removeEventListener( 'click', removeModalHandler );
        // overlay.addEventListener( 'click', removeModalHandler );
        // 拖拽
        var oTitle = modal.getElementsByTagName("h3")[0];
        var oDrag = new Drag(modal, {
          handle: oTitle,
          limit: false
        });
        // end---
        if (classie.has(el, 'md-setperspective')) {
          setTimeout(function() {
            classie.add(document.documentElement, 'md-perspective');
          }, 25);
        }
      });

			close.addEventListener( 'click', function( ev ) {
				ev.stopPropagation();
				removeModalHandler();
			});
			if(close1!=null){
				close1.addEventListener( 'click', function( ev ) {
					ev.stopPropagation();
					removeModalHandler();
				});
			}
			if(close2!=null){
				close2.addEventListener( 'click', function( ev ) {
					ev.stopPropagation();
					removeModalHandler();
				});
			}

		} );

	}

	init();

})();

//弹窗拖拽
function Drag() {
  //初始化
  this.initialize.apply(this, arguments)
}
Drag.prototype = {
  //初始化
  initialize: function(drag, options) {
    this.drag = this.$(drag);
    this._x = this._y = 0;
    this._moveDrag = this.bind(this, this.moveDrag);
    this._stopDrag = this.bind(this, this.stopDrag);

    this.setOptions(options);

    this.handle = this.$(this.options.handle);
    this.maxContainer = this.$(this.options.maxContainer);

    this.maxTop = Math.max(this.maxContainer.clientHeight, this.maxContainer.scrollHeight) - this.drag.offsetHeight;
    this.maxLeft = Math.max(this.maxContainer.clientWidth, this.maxContainer.scrollWidth) - this.drag.offsetWidth;

    this.limit = this.options.limit;
    this.lockX = this.options.lockX;
    this.lockY = this.options.lockY;
    this.lock = this.options.lock;

    this.onStart = this.options.onStart;
    this.onMove = this.options.onMove;
    this.onStop = this.options.onStop;

    this.handle.style.cursor = "move";

    this.changeLayout();

    this.addHandler(this.handle, "mousedown", this.bind(this, this.startDrag))
  },
  changeLayout: function() {
    this.drag.style.top = this.drag.offsetTop + "px";
    this.drag.style.left = this.drag.offsetLeft + "px";
    this.drag.style.position = "absolute";
    this.drag.style.margin = "0"
  },
  startDrag: function(event) {
    var event = event || window.event;

    this._x = event.clientX - this.drag.offsetLeft;
    this._y = event.clientY - this.drag.offsetTop;

    this.addHandler(document, "mousemove", this._moveDrag);
    this.addHandler(document, "mouseup", this._stopDrag);

    event.preventDefault && event.preventDefault();
    this.handle.setCapture && this.handle.setCapture();

    this.onStart()
  },
  moveDrag: function(event) {
    var event = event || window.event;

    var iTop = event.clientY - this._y;
    var iLeft = event.clientX - this._x;

    if (this.lock) return;

    this.limit && (iTop < 0 && (iTop = 0), iLeft < 0 && (iLeft = 0), iTop > this.maxTop && (iTop = this.maxTop), iLeft > this.maxLeft && (iLeft = this.maxLeft));

    this.lockY || (this.drag.style.top = iTop + "px");
    this.lockX || (this.drag.style.left = iLeft + "px");

    event.preventDefault && event.preventDefault();

    this.onMove()
  },
  stopDrag: function() {
    this.removeHandler(document, "mousemove", this._moveDrag);
    this.removeHandler(document, "mouseup", this._stopDrag);

    this.handle.releaseCapture && this.handle.releaseCapture();

    this.onStop()
  },
  //参数设置
  setOptions: function(options) {
    this.options = {
      handle: this.drag, //事件对象
      limit: true, //锁定范围
      lock: false, //锁定位置
      lockX: false, //锁定水平位置
      lockY: false, //锁定垂直位置
      maxContainer: document.documentElement || document.body, //指定限制容器
      onStart: function() {}, //开始时回调函数
      onMove: function() {}, //拖拽时回调函数
      onStop: function() {} //停止时回调函数
    };
    for (var p in options) this.options[p] = options[p]
  },
  //获取id
  $: function(id) {
    return typeof id === "string" ? document.getElementById(id) : id
  },
  //添加绑定事件
  addHandler: function(oElement, sEventType, fnHandler) {
    return oElement.addEventListener ? oElement.addEventListener(sEventType, fnHandler, false) : oElement.attachEvent("on" + sEventType, fnHandler)
  },
  //删除绑定事件
  removeHandler: function(oElement, sEventType, fnHandler) {
    return oElement.removeEventListener ? oElement.removeEventListener(sEventType, fnHandler, false) : oElement.detachEvent("on" + sEventType, fnHandler)
  },
  //绑定事件到对象
  bind: function(object, fnHandler) {
    return function() {
      return fnHandler.apply(object, arguments)
    }
  }
};
// 采购合同号录入
$(document).ready(function(){
    $('#addradio').click(function(){  
      $('.cghth:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 商品录入
$(document).ready(function(){
    $('#addsplrr').click(function(){  
      $('.splrr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 商品录入2
$(document).ready(function(){
    $('#addsplrrr').click(function(){  
      $('.splrrr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 品牌录入
$(document).ready(function(){
    $('#addpp').click(function(){  
      $('.pplr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 品牌录入2
$(document).ready(function(){
    $('#addppp').click(function(){  
      $('.pplr1:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 供应商录入
$(document).ready(function(){
    $('#addgys').click(function(){  
      $('.gyslr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });
// 公司录入
$(document).ready(function(){
    $('#addgs').click(function(){  
      $('.gslr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });

// 仓库录入
$(document).ready(function(){
    $('#addcklr').click(function(){  
      $('.cklr:checked').closest('tr').find('td').each(function(){
            var id = $(this).attr('data-id');
            $('#'+id).val($.trim($(this).text()));
        });
    });
  });

// input验证手机(一部手机)
function checkMobile(input) {
  var re = /^0?1[2|3|4|5|6|7|8|9][0-9]\d{8}$/;
  var value = input.value;
  if (value == '') {} else if (re.test(value)) {} else {
    SimplePop.alert("手机号码格式不正确！");
    $(input).val("");
  }
}
//两部手机
function checkMobile1(input) {
	  var re = /^0?1[2|3|4|5|6|7|8|9][0-9]\d{8}$/;
	  var value = input.value;
	  var s="";
	  if(value.length>11){
		 s = value.indexOf(",");
	  }
	  if(s==-1){
		  SimplePop.alert("多个电话号码之间用英文“,”隔开！");
		  $(input).val(""); 
		  return false;
	  }
	  if(value == ''){}else if(value.length==11){
		  if(re.test(value)){}else{
		  SimplePop.alert("手机号码格式不正确！");
		  $(input).val("");
		  }
	  }else{
		  if(re.test(value.substring(0,11)) && re.test(value.substring(12))){		  
		  }else{
			  SimplePop.alert("手机号码格式不正确！");
			  $(input).val(""); 
		  }  
	  }
}

// input验证电话号码(固定电话)
function checkPhone(input) {
  var re = /^0\d{2,3}-?\d{7,8}$/
  var value = input.value;
  if (value == '') {} else if (re.test(value)) {} else {
    SimplePop.alert("电话号码格式不正确！(区号+号码，区号以0开头，3位或4位)");
    $(input).val("");
  }
}

// input验证邮箱
function checkEmail(input) {
  var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
  var value = input.value;
  if (value == '') {} else if (re.test(value)) {} else {
    SimplePop.alert("邮箱格式错误!");
    $(input).val("");
  }
}
// 显示加载中
function showLoading(){
  // 添加遮罩层
  $('.overlay').remove();
  $('body').append('<div class="overlay"></div>');
  $(".overlay").css({'position':'fixed','top':'0','right':'0','bottom':'0','left':'0','z-index':'9998','width':'100%','height':'100%'});
  $(".overlay").css({'_padding':'0 20px 0 0','background':'#f6f4f5','display':'none'});
  $(".overlay").css({'display':'block','opacity':'0.8'});
  var h = $(document).height();
  $(".overlay").css({"height": h });

  // 添加提示层
  $('#AjaxLoading').remove();
  $('body').append('<div id="AjaxLoading" class="showbox"><div class="loadingWord"><img src="../resources/core/backgrounds/waiting.gif">加载中，请稍候...</div></div>');
  $('#AjaxLoading').css({'border':'1px solid #8CBEDA','color':'#37a','font-size':'12px','font-weight':'bold'});
  $('#AjaxLoading .loadingWord').css({'width':'180px','line-height':'50px','border':'2px solid #D6E7F2','background':'#fff'});
  $('#AjaxLoading img').css({'margin':'10px 15px','float':'left','display':'inline'});
  $('.showbox').css({'position':'fixed','top':'300px','left':'50%','z-index':'9999','opacity':'0','filter':'alpha(opacity=0)','margin-left':'-80px'});
  $(".overlay,.showbox").css({'position':'absolute','top':'expression(eval(document.documentElement.scrollTop))'});
  
  $(".showbox").stop(true).animate({'opacity':'1'},200);
}
// 隐藏加载中
function clearLoading(){
  $(".showbox").stop(true).animate({'opacity':'0'},400);
  $(".overlay").css({'display':'none','opacity':'0'});
  $("#AjaxLoading").css({'display':'none','opacity':'0'});
}
$(document).ready(function(){
  $(".action").click(function(){
    showLoading();
    setTimeout(function(){
      clearLoading();
    },800);
    
  });
  
});


// input输入框输入限制
 $(document).ready(function(){
//输入数字自动添加小数点后1位  
  $(".e_de1").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.0';
    }
  });
//输入数字自动添加小数点后2位  
  $(".e_de2").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.00';
    }
  });
//输入数字自动添加小数点后3位  
  $(".e_de3").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.000';
    }
  });
//输入数字自动添加小数点后4位  
  $(".e_de4").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.0000';
    }
  });
//输入数字自动添加小数点后5位  
  $(".e_de5").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.00000';
    }
  });
//输入数字自动添加小数点后6位  
  $(".e_de6").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.000000';
    }
  });
//输入数字自动添加小数点后7位  
  $(".e_de7").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.0000000';
    }
  });
//输入数字自动添加小数点后8位  
  $(".e_de8").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.00000000';
    }
  });      
//输入数字自动添加小数点后9位  
  $(".e_de9").change(function(){
    if(this.value.indexOf('.')==-1&&this.value!=''){
      this.value+='.000000000';
    }
  });

  $(".e_de1,.e_de2,.e_de3,.e_de4,.e_de5,.e_de6,.e_de7,.e_de8,.e_de9").keyup(function(){
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[^(\d|\.)]/g, ''));
  });
	  
  //文本框只能输入数字
  $("input[class='e_number']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[^\(\d|\.)]/g, ''));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[^\(\d|\.)]/g, ''));
    }, 100);
  //   var tmptxt = $(this).val();
  //   $(this).val(tmptxt.replace(/[^(\d|\.)]/g, ''));
   }).css("ime-mode", "disabled"); 
  
//文本框只能输入数字(可以负数)
  $("input[class='e_number_e']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[^\-?(\d|\.)]/g, ''));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[^\-?(\d|\.)]/g, ''));
    }, 100);
  //   var tmptxt = $(this).val();
  //   $(this).val(tmptxt.replace(/[^(\d|\.)]/g, ''));
   }).css("ime-mode", "disabled"); 
//文本框只能输入数字和英文（禁止输入中文）
  $("input[class='e_inqqrqw']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[\W]/g,''));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[\W]/g,''));
    }, 100);
  //   var tmptxt = $(this).val();
  //   $(this).val(tmptxt.replace(/[\W]/g,''));
   }).css("ime-mode", "disabled"); 

//inupt只能输入整数
  $("input[class='e_integer']").keyup(function() {
   if(this.value.length==1){
    this.value=this.value.replace(/[^1-9]/g,'')
  }else{
    this.value=this.value.replace(/\D/g,'')
  } 
});

  $("input[class='e_pageSize']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/^[^1-9]/, '15'));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[^\d\.]/g, ''));
    }, 100);
   }).css("ime-mode", "disabled"); 
  
  $("input[class='e_pageNo']").keyup(function() {
	    var tmptxt = $(this).val();
	    $(this).val(tmptxt.replace(/^[^1-9]/, '1'));
	  }).bind("paste", function() {
	    var el = $(this);
	    setTimeout(function() {
	    var text = $(el).val();
	    el.val(text.replace(/[^\d\.]/g, ''));
	    }, 100);
	   }).css("ime-mode", "disabled"); 
  
  //文本框只能输入大于0（不包括0）的正数
  $("input[class='e_greater']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/^[^1-9]/, ''));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[^\d\.]/g, ''));
    }, 100);
  //   var tmptxt = $(this).val();
  //   $(this).val(tmptxt.replace(/[^\d\.]/g, ''));
   }).css("ime-mode", "disabled"); 
  $("input[class='e_greater']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[^\d\.]/g, ''));
  }); 

  //文本框只能输入英文字母
  $("input[class='e_nber']").keyup(function() {
    var tmptxt = $(this).val();
    $(this).val(tmptxt.replace(/[^\a-zA-Z]/g,''));
  }).bind("paste", function() {
    var el = $(this);
    setTimeout(function() {
    var text = $(el).val();
    el.val(text.replace(/[^\a-zA-Z]/g,''));
    }, 100);
  //   var tmptxt = $(this).val();
  //   $(this).val(tmptxt.replace(/[^\a-zA-Z]/g,''));
   }).css("ime-mode", "disabled"); 

 //inupt只能输入负数
$("input[class='e_inr']").keyup(function() {
     var tmptxt = $(this).val();
     $(this).val(tmptxt.replace(/^[^1-9][\d]?$/, ''));
      if (!isNaN(this.value)) {
        if (this.value > 0) {
          this.value = 0-this.value;
        };
      } else {
        this.value = '';
      };
  })

//inupt只能输入负数和0
$("input[class='e_inrqw']").keyup(function() {
     var tmptxt = $(this).val();
      if (!isNaN(this.value)) {
        if (this.value > 0) {
          this.value = 0-this.value;
        };
      } else {
        this.value = '';
      };
  })

////清除文本框里的空格
//  $("input").keyup(function() {
//    var tmptxt = $(this).val();
//    $(this).val(tmptxt.replace(/[ ]/g,''));
//  })
});

//input只能输入1位小数点
  function e_limit1(obj){
    if(obj.value==obj.value2)
      return;
    if(obj.value.search(/^\d*(?:\.\d{0,1})?$/)==-1)
      obj.value=(obj.value2)?obj.value2:'';
    else obj.value2=obj.value;
  }
  //input只能输入2位小数点
  function e_limit2(obj){
    if(obj.value==obj.value2)
      return;
    if(obj.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)
      obj.value=(obj.value2)?obj.value2:'';
    else obj.value2=obj.value;
  }
  //input只能输入3位小数点
  function e_limit3(obj){
    if(obj.value==obj.value2)
      return;
    if(obj.value.search(/^\d*(?:\.\d{0,3})?$/)==-1)
      obj.value=(obj.value2)?obj.value2:'';
    else obj.value2=obj.value;
  }
  //input只能输入4位小数点
  function e_limit4(obj){
    if(obj.value==obj.value2)
      return;
    if(obj.value.search(/^\d*(?:\.\d{0,4})?$/)==-1)
      obj.value=(obj.value2)?obj.value2:'';
    else obj.value2=obj.value;
  }
 // 时间控件--年月日
      $(document).ready(function(){
      $('.e_ymd').focus(function(){
          WdatePicker({
              dateFmt:'yyyy-MM-dd',
          });
        });
    });
 // 时间控件--年月日时分秒
      $(document).ready(function(){
      $('.e_ymdhms').focus(function(){
          WdatePicker({
              dateFmt:'yyyy-MM-dd HH:mm:ss',
          });
        });
    }); 
      $(document).ready(function() {
    		$('.e_yyyy').focus(function() {
    			WdatePicker({
    				dateFmt : 'yyyy',
    			});
    		});
    	});
    	$(document).ready(function() {
    		$('.e_MM').focus(function() {
    			WdatePicker({
    				dateFmt : 'MM',
    			});
    		});
    	});

//input字段不能为空或者值超出最大值
 function cchecked(id){
	 if(!checkEmpty(id))return false;
	 if(!checkMaxNumberLength(id))return false;
	 return true;
}  
function checkEmpty(id){
	 var num=0;
     var str="";
     var name="";
     $("#"+id).find("input[type$='text'][msgg]").each(function(n){
          if($(this).val()=="")
          {
               num++;
               str+=$(this).attr("msgg")+"不能为空！\r\n";
          }
     });
     $("#"+id).find("select[msgg]").each(function(n){
          if($(this).val()=="")
          {
               num++;
               str+=$(this).attr("msgg")+"不能为空！\r\n";
          }
     });
     if(num>0){
          SimplePop.alert(str);
          return false;
     }
     else{
          return true;
     }
}
function checkMaxNumberLength(id){
	var str="";
	var value=0;
	var length = 0;
	var objStr = "";
	var maxnum = 0;
	$("#"+id).find("input[maxnumLength]").each(function(n){
		length = Number($(this).attr("maxnumLength"));
		value = Number(conver2number($(this).val()));
		objStr = $(this).attr("msg");
		objStr = objStr == undefined?$(this).attr("msgg"):objStr;
		objStr = objStr == undefined?$(this).parent().prev().text():objStr;
		objStr = objStr == undefined?$(this).attr("name"):objStr;
		if(isNaN(value)){
			str += objStr+"应为整数位不大于"+length+"位以内的数字！\r\n";
			return true;
		}
		if(!isNaN(length)){
			maxnum = Math.pow(10,length);
			if(value>=maxnum||value<=(0-maxnum)){
				str += objStr+"的值,整数位有效数字超出"+length+"位,无法保存！\r\n";
			}
		}
	});
	if(str.length>0){
		 SimplePop.alert(str);
         return false;
	}
	return true;
}
/**
 * 转数字
 */
function conver2number(num,replaceValue){
	num = Number(num.replace(/,/gi,''));
	if(isNaN(num)){
		if(replaceValue!=undefined){
			return replaceValue; 
		}
		return 0
	}else{
		return num;
	}
}

/* *
 * jQuery SimplePop
 * IE 7+
 * @date 2014-11-24 13:19:36
 * https://www.sucaijiayuan.com
 * */
 // 温馨提示消息弹窗js
"use strict";
var SimplePop = {
  alert: function(msg, arg) {
    var alertDefaults = {
      popType: "alert",
      title: "温馨提示",
      content: "<div class='layer_msg'><p>" + (msg === undefined ? "" : msg) + "</p><button id='simplePopBtnSure' type='button'>确定</button></div>",
      callback: function() {
        
      }
    };
    var opt = $.extend({}, this._defaults, alertDefaults, arg);
    this._creatLayer(opt)
  },
  confirm: function(msg, arg) {
    var confirmDefaults = {
      popType: "confirm",
      title: "温馨提示",
      content: "<div class='layer_msg'><p>" + (msg === undefined ? "" : msg) + "</p><button id='simplePopBtnSure' type='button'>确定</button><button id='SimplePopBtncancel' type='button'>取消</button></div>",
      cancel: function() {
        
      },
      confirm: function() {
        
      }
    };
    var opt = $.extend({}, this._defaults, confirmDefaults, arg);
    this._creatLayer(opt)
  },
  prompt: function(msg, arg) {
    var promptDefaults = {
      popType: "prompt",
      title: "温馨提示",
      content: "<div class='layer_msg'><p>" + (msg === undefined ? "" : msg) + "</p><div><input type='text' /></div><button id='simplePopBtnSure' type='button'>确定</button><button id='SimplePopBtncancel' type='button'>取消</button></div>",
      cancel: function() {
        
      },
      confirm: function(value) {
        
      }
    };
    var opt = $.extend({}, this._defaults, promptDefaults, arg);
    this._creatLayer(opt)

  },
  closeSimplePop: function() {
    this._closeLayer();
  },
  _defaults: {
    icon: "",
    title: "",
    content: "",
    width: 0,
    height: 0,
    background: "#000",
    opacity: 0.5,
    duration: "normal",
    showTitle: true,
    escClose: true,
    popMaskClose: false,
    drag: true,
    dragOpacity: 1,
    popType: "alert",
    type: "info"
  },
  _creatLayer: function(opt) {
    var self = this;
    $(".popMask").empty().remove();
    $(".popMain").empty().remove();
    $("body").append("<div class='popMask'></div>");
    var $mask = $(".popMask");
    $mask.css({
      "background-color": opt.background,
      filter: "alpha(opacity=" + opt.opacity * 100 + ")",
      "-moz-opacity": opt.opacity,
      opacity: opt.opacity
    });
    opt.popMaskClose &&
      $mask.bind("click", function() {
        self._closeLayer()
      });
    opt.escClose && $(document).bind("keyup", function(e) {
      try {
        e.keyCode == 27 && self._closeLayer()
      } catch (f) {
        self._closeLayer()
      }
    });
    $mask.fadeIn(opt.duration);
    var wrap = "<div class='popMain'>";
    wrap += "<div class='popTitle'>" + (opt.icon !== undefined && opt.icon !== "" ? "<img class='icon' src='" +
      opt.icon + "' />" : "") + "<span class='text'>" + opt.title + "</span><span class='close'>&times;</span></div>";
    wrap += "<div class='popContent'>" + opt.content + "</div>";
    wrap += "</div>";
    $("body").append(wrap);
    var $popMain = $(".popMain");
    $popMain.find('.layer_msg').addClass(opt.type + '_icon')
    var $popTitle = $(".popTitle");
    var $popContent = $(".popContent");
    opt.showTitle ? $popTitle.show() : $popTitle.hide();
    opt.width !== 0 && $popTitle.width(opt.width);
    $(".popTitle .close").bind("click", function() {
      $mask.fadeOut(opt.duration);
      $popMain.fadeOut(opt.duration);
      $popMain.attr("isClose", "1");
      opt.type == "container" && $(opt.targetId).empty().append(opt.content);
    });
    opt.width !== 0 && $popContent.width(opt.width);
    opt.height !== 0 && $popContent.height(opt.height);
    $popMain.css({
      left: $(window).width() / 2 - $popMain.width() / 2 + "px",
      top: $(window).height() / 2 - $popMain.height() / 2 + "px"
    });
    $(window).resize(function() {
      $popMain.css({
        left: $(window).width() / 2 - $popMain.width() / 2 + "px",
        top: $(window).height() / 2 - $popMain.height() / 2 + "px"
      })
    });
    opt.drag && this._drag(opt.dragOpacity)

    switch (opt.popType) {
      case "alert":
        $popMain.fadeIn(opt.duration, function() {
          $popMain.attr("style", $popMain.attr("style").replace("FILTER:", ""))
        });
        $("#simplePopBtnSure").bind("click", function() {
          opt.callback();
          self._closeLayer()
        });
        break;
      case "confirm":
        $popMain.fadeIn(opt.duration, function() {
          $popMain.attr("style", $popMain.attr("style").replace("FILTER:", ""))
        });
        $("#simplePopBtnSure").bind("click",
          function() {
            opt.confirm()
            self._closeLayer()
          });
        $("#SimplePopBtncancel").bind("click", function() {
          opt.cancel()
          self._closeLayer()
        });
        break;
      case "prompt":
        $popMain.fadeIn(opt.duration, function() {
          $popMain.attr("style", $popMain.attr("style").replace("FILTER:", ""))
        });
        $("#simplePopBtnSure").bind("click",
          function() {
            opt.confirm($(".layer_msg input").val())
            self._closeLayer()
          });
        $("#SimplePopBtncancel").bind("click", function() {
          opt.cancel()
          self._closeLayer()
        });
        break;
      default:
        break;
    }
  },
  _closeLayer: function() {
    $(".popTitle .close").triggerHandler("click")
  },
  _drag: function(d) {
    var isDown = false,
      b, g;
    $(".popTitle").bind("mousedown", function(e) {
      if ($(".popMain:visible").length > 0) {
        isDown = true;
        b = e.pageX - parseInt($(".popMain").css("left"), 10);
        g = e.pageY - parseInt($(".popMain").css("top"), 10);
        $(".popTitle").css({
          cursor: "move"
        })
      }
    });
    $(document).bind("mousemove", function(e) {
      if (isDown && $(".popMain:visible").length > 0) {
        d != 1 && $(".popMain").fadeTo(0, d);
        var f = e.pageX - b;
        e = e.pageY - g;
        if (f < 0) f = 0;
        if (f > $(window).width() - $(".popMain").width()) f = $(window).width() - $(".popMain").width() - 2;
        if (e <
          0) e = 0;
        if (e > $(window).height() - $(".popMain").height()) e = $(window).height() - $(".popMain").height() - 2;
        $(".popMain").css({
          top: e,
          left: f
        })
      }
    }).bind("mouseup", function() {
      if ($(".popMain:visible").length > 0) {
        isDown = false;
        d != 1 && $(".popMain").fadeTo(0, 1);
        $(".popTitle").css({
          cursor: "auto"
        })
      }
    })
  }
}


function SimplePopConfirm(msg,funSimp){
  SimplePop.confirm(msg,{
        type: "error",
        cancel: function(){
        },
        //确定按钮回调
        confirm: function(){
            funSimp();
        }
    });
}



function SimplePop4Confirm(msg,Func){
  var args = new Array();
  for(i=2;i<arguments.length;i++){
    args[i-2] =arguments[i];
  }
  SimplePop.confirm(msg,{
    type: "info",
    confirm: function(){
      Func(args);
    }
  })
}
