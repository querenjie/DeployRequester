function doRequest(url, form, successMethod, faildMethod, btn, isRefresh) {

	if (!isRefresh) {
		isRefresh = false;
	}
	if (btn) {
		btn.disabled = true;
	}
	var msg = Ext.Msg.wait("操作进行中,请等候...", "", {
		interval :200,
		increment :20
	});
	Ext.Ajax.timeout = 900000;
	Ext.Ajax.request( {
		url :url,
		form :form,
		methods :'post',
		success : function(result, request) {
			if (successMethod) {
				successMethod(result, request);
			} else {
				var ajaxResult = eval("(" + result.responseText + ")");
				if (ajaxResult.success) {
					alert('操作成功');
				} else {
					alert('操作失败' + ajaxResult.message);
				}
			}
			if (btn) {
				btn.disabled = false;
			}
			msg.hide();
			if (isRefresh) {
				window.location.href = window.location;
			}
		},
		failure : function(result, request) {
			if (faildMethod) {
				faildMethod(result, request);
			} else {
				alert('操作出错');
			}
			if (btn) {
				btn.disabled = false;
			}
			msg.hide();
		}
	});

}
