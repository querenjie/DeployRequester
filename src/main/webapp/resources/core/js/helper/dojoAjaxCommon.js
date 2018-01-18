function doDojoRequest(url,form,successMethod,faildMethod,btn){

    if(btn){
		btn.disabled=true;
	}
	
    if(dojo.widget.byId("loading")){
		dojo.widget.byId("loading").show();
	}
	dojo.io.bind({
		url:url,
		formNode:form,
		encoding:"utf-8",
		method:"post",
		load: function(type, data, evt) {
			if(successMethod){
				successMethod(type, data, evt);
			}else{
				var ajaxResult=eval(data);
				if(ajaxResult[0]=="SAVE_OK"){
					alert('操作成功');
				}else if(ajaxResult[0]=="SAVE_ERROR"){
					alert('操作失败'+ajaxResult[1]);
					if(btn){
						btn.disabled=false;
					}
				}
			}
			if(dojo.widget.byId("loading")){
				dojo.widget.byId("loading").hide();
			}
		},
		error: function(type,data,evt,error){
			alert('error');
			if(faildMethod){
				faildMethod(type, data, error);
			}else{
				alert('操作出错'+data.message);
				if(btn){
					btn.disabled=false;
				}
			}
			if(dojo.widget.byId("loading")){
				dojo.widget.byId("loading").hide();
			}
		}
	});	
}


//RPC AJAX
function doDojoRpc(url,methodStr,successMethod,faildMethod,btn){
	
	//执行AJAX取客户人员数据
    var service = new dojo.rpc.JsonService(url);
	
	var loading = dojo.widget.byId("loading");
	if(loading){
        loading.show();
	}
	
    var defered = eval("service."+methodStr);
	
    defered.addCallback(
        function(custPerson){
			if(successMethod){
				successMethod(custPerson);
			}
			if(loading){
				loading.hide();
			}
        }
    );
	defered.addErrback(
	    function(data){
            alert("远程调用出错，请稍后再试!"+data.message);
			if(btn){
				btn.disabled = false;
			}
			if(loading){
				loading.hide();
			}
        }
    );
}

