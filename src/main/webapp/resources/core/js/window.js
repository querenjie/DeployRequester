//hgj 增加EXT弹出信息
function showMessage(content){
 Ext.MessageBox.show({
           title: '客服系统提示您：',
           msg: content,
           buttons: Ext.MessageBox.OK,
           buttonText : '确定',
           //fn: showResult, //完成后的动作
           icon: Ext.MessageBox.WARNING,
           defaultTextHeight:16
       });
}

//增加ext窗口显示
var win = null;
function showWindow(tit,pageUrl,wid,hei){
	var time = new Date();
	if(pageUrl !="" && pageUrl.indexOf('?')>0){
		pageUrl +="&time="+time;
	}else{
		pageUrl +="?time="+time;
	} 
	if(hei>150) hei=150;
 win= new  Ext.Window({title:tit,
 			width:wid,
 			y:hei,
 			autoHeight:true,
 			maximizable:true,
 			modal:true,
			autoLoad:{url:pageUrl, scripts:true}}); 	
 win.show();
}

var win2 = null;
function showWindow2(tit,pageUrl,wid,hei){
	var time = new Date();
	if(pageUrl !="" && pageUrl.indexOf('?')>0){
		pageUrl +="&time="+time;
	}else{
		pageUrl +="?time="+time;
	} 
	if(hei>150) hei=150;
	win2= new  Ext.Window({title:tit,
 			width:wid,
 			y:hei,
 			autoHeight:true,
 			maximizable:true,
 			modal:true,
			autoLoad:{url:pageUrl, scripts:true}}); 	
	win2.show();
}

var win3 = null;
function showWindow3(tit,pageUrl,wid,hei){
 win3= new  Ext.Window({title:tit,
 			width:wid,
			height:hei,
			maximizable:false,
 			modal:true, 		
			autoLoad:{url:pageUrl, scripts:true}}); 	
 win3.show();
}

var win4 = null;
function showWindow4(tit,pageUrl,wid,hei){
 win4= new  Ext.Window({title:tit,
 			width:wid,
			height:hei,
 			maximizable:true,
 			modal:true,
			autoLoad:{url:pageUrl, scripts:true}}); 	
 win4.show();
}

var win5 = null;
function showWindow5(tit,pageUrl,wid,hei){
win5= new Ext.Window({title:tit,
 			width:wid,
 			height:hei,
 			y:180,
 			autoHeight:true,
 			maximizable:true,
 			modal:false,
			autoLoad:{url:pageUrl, scripts:true}}); 
 win5.show();
}

//通过Iframe方式加载页面
function showWindowIframe(tit,pageUrl,wid,hei,top){
	 var iFrameObjId = "Ext2_iFrame_in_Tab_"+new Date().getTime();	
	 top = (top==null)?100:top;
	 var htmlString  = '<div id="loadingDiv" style="display:block;position:absolute;left:50%;top:50%;margin:-100px 0 0 -80px;height'+hei+'px;z-index:1; text-align:center; line-height:20px; font-size:14px;">'
	 	+'<img src="../../main/images/loading.gif"><br>数据正在加载中，请稍等...</div>'
		+'<iframe id="'+iFrameObjId+'" name="'+iFrameObjId+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+pageUrl+'" onload="document.getElementById(\'loadingDiv\').style.display=\'none\'"></iframe>'
	 win= new Ext.Window({title:tit,
	 			width:wid,
	 			height:hei, 
	 			maximizable:true,
	 			y:top,
	 			modal:true,
	 			iFrameObjId:iFrameObjId,
				html:htmlString});
	 win.show();
	 return iFrameObjId;
	}
	
	

//通过Iframe方式加载页面
function showWindowIframeDiv(tit,pageUrl,wid,hei,top){
	 var iFrameObjId = "Ext2_iFrame_in_Tab_"+new Date().getTime();	
	 top = (top==null)?100:top;
	 var htmlString  = '<div id="loadingDiv" style="display:block;position:absolute;left:50%;top:50%;margin:-100px 0 0 -80px;height'+hei+'px;z-index:1; text-align:center; line-height:20px; font-size:14px;">'
	 	+'<img src="../../main/images/loading.gif"><br>数据正在加载中，请稍等...</div>'
		+'<iframe id="'+iFrameObjId+'" name="'+iFrameObjId+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+pageUrl+'" onload="document.getElementById(\'loadingDiv\').style.display=\'none\'"></iframe>'
	 win= new Ext.Window({title:tit,
	 			width:wid,
	 			height:hei, 
	 			maximizable:true,
	 			y:top,
	 			iFrameObjId:iFrameObjId,
				html:htmlString});
	 win.show();
	 return iFrameObjId;
	}

function showWindowFix(tit,pageUrl,wid, hei){
	var time = new Date().getTime();
	if(pageUrl !="" && pageUrl.indexOf('?')>0){
		pageUrl +="&time="+time;
	}else{
		pageUrl +="?time="+time;
	} 
  win= new Ext.Window({title:tit,
 			width:wid,
 			height:hei,
 			y:180,
 			autoHeight:true,
 			maximizable:true,
 			modal:true,
			autoLoad:{url:pageUrl, scripts:true}}); 
 win.show();
}

function showWindowIframeFix(tit,pageUrl,wid, hei){
	var time = new Date().getTime();
	if(pageUrl !="" && pageUrl.indexOf('?')>0){
		pageUrl +="&time="+time;
	}else{
		pageUrl +="?time="+time;
	} 
	/**
	var iFrameObjId = "Ext2_iFrame_in_Tab_"+new Date().getTime();	
  win= new Ext.Window({title:tit,
 			width:wid,
 			height:hei,
 			autoHeight:true,
 			maximizable:true, 
 			modal:true,
 			iFrameObjId:iFrameObjId,
			html:'<iframe  id="'+iFrameObjId+'" scrolling="auto" frameborder="0" width="'+(wid-15)+'" height="'+hei+'" src="'+pageUrl+'"></iframe>'});
 win.show();
 **/
	openNewWindow(tit,pageUrl,wid, hei);
}

//居中打开新窗
function openNewWindow(tit,pageUrl,wid, hei){	
	var time = new Date().getTime();
	if(pageUrl !="" && pageUrl.indexOf('?')>0){
		pageUrl +="&time="+time;
	}else{
		pageUrl +="?time="+time;
	} 	
	var iTop = (window.screen.availHeight-30-hei)/2;       //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-wid)/2;           //获得窗口的水平位置;
	window.open(pageUrl,'','height='+hei+',innerHeight='+hei+',width='+wid+',innerWidth='+wid+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes, resizable=yes, location=no, status=no');
	//window.open (pageUrl, tit, "width="+wid+", height="+hei+",toolbar=no,menubar=no,scrollbars=yes, resizable=yes, location=no, status=no"); //写成一行
}

function closeWin(){
	if(opener){
		try{
			opener.location.reload();
		}catch(e){}
		window.close();
	}else if(opener){
		window.close();
	}else if(parent.win){
		parent.win.close();
	}else{
		top.closeActiveTab();
	}
}


//表单重置
function resetForm(form){
  var v=document.forms[form].elements;
  for(var i=0;i<v.length;i++){
    if(v[i].type=="text" || v[i].type=="select-one"){
      v[i].value="";
    }
	if( v[i].type == "checkbox"  ){
		v[i].checked = false;
	}
  } 
}