var mykeyword;
(function($){
	var bigAutocomplete = new function(){
		this.currentInputText = null;//目前获得光标的输入框（解决一个页面多个输入框绑定自动补全功能）
		this.functionalKeyArray = [9,20,13,16,17,18,91,92,93,45,36,33,34,35,37,39,112,113,114,115,116,117,118,119,120,121,122,123,144,19,145,40,38,27];//键盘上功能键键值数组
		this.holdText = null;//输入框中原始输入的内容
		this.datas = [];
		this.titles = [];
		this.id = '';
		
		//初始化插入自动补全div，并在document注册mousedown，点击非div区域隐藏div
		this.init = function(id){
			$("body").append("<div id='bigAutocompleteContent' class='bigautocomplete-layout new-bigAutocompleteContent'></div>");
			$(document).bind('mousedown',function(event){
				var $target = $(event.target);
				if((!($target.parents().andSelf().is('#bigAutocompleteContent'))) && (!$target.is(bigAutocomplete.currentInputText))){
					bigAutocomplete.hideAutocomplete();
				}
			})
			
			//鼠标悬停时选中当前行
			$("#bigAutocompleteContent").delegate("tr", "mouseover", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
				$(this).addClass("ct");
			}).delegate("tr", "mouseout", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
			});

			//单击选中行后，选中行内容设置到输入框中，并执行callback函数
			$("#bigAutocompleteContent").delegate("tbody tr", "click", function() {
				// bigAutocomplete.currentInputText.val( $(this).find("div:last").html());
				var callback_ = bigAutocomplete.currentInputText.data("config").callback;
				if($("#bigAutocompleteContent").css("display") != "none" && callback_ && $.isFunction(callback_)){
					callback_($(this).data("jsonData"));
				}else{
					var id = bigAutocomplete.currentInputText.data("config").id || [];
					var sel_data = $("#bigAutocompleteContent tbody .ct").data("jsonData") || '';
					// console.log(sel_data);
					for(var k=0;k<sel_data.length;k++){
						if ($('#'+id[k]).length > 0) {
							$('#'+id[k]).val(sel_data[k]);
						};
					}
				}

				if(isExitsFunction("bigAutocompleteAfterClick")){
					bigAutocompleteAfterClick();
				}
				bigAutocomplete.hideAutocomplete();
			});	
			
			$(".new-bigAutocompleteContent").delegate("tbody tr", "click", function() {
		       $.ajax({
		             type: "GET",
		             url: baseUrl+"barcode/getBarcodeLevel",
		             data: {goodsid:$("#goodsid").val()},
		             dataType: "json",
		             success: function(data){
		            	 document.getElementById("level").value=data.level;
		             }
		       });
				
				
			});

			// 回车选中
	        $("body").keydown(function(event) {
	            if (event.keyCode == "13") {//keyCode=13是回车键
	                $('#bigAutocompleteContent tbody tr').click();
	            }
	        });

			
		}
		
		this.autocomplete = function(param){
			
			this.id = $(this).attr('id');
			if($("body").length > 0 && $("#bigAutocompleteContent").length <= 0){
				bigAutocomplete.init(this.id);//初始化信息
			}			
			
			var $this = $(this);//为绑定自动补全功能的输入框jquery对象
			$this.prop("autocomplete","off");//关闭js根据缓存自动补全
			$this.unbind("keydown.auto focus.auto input.auto propertychange.auto");
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			
			this.config = {
			               //width:下拉框的宽度，默认使用输入框宽度
			               width:$this.outerWidth() - 2,
			               /*ajax：用来ajax后台获取数据,与jquery的$.ajax({})一致，格式:
			               {ajax:{
	               					url:'后台获取数据地址',
	               					type: 'get或者post',
	               					dataType: '返回的数据类型，json或jsonp或html',
	               					data: {},
	               					beforeSend: callback,//这里需要传的是一个回调方法
	               					success: callback,//这里需要传的是一个回调方法
	               					error:callback//这里需要传的是一个回调方法
	               				}
			               	}
			               */
			               ajax:null,
			               /*data：格式{data:[['数据1','数据2','数据3'],[],[]]}
			               ajax和data参数只有一个生效，data优先*/
			               data:null,
			               /*title: 表格头部标题，格式['姓名','年龄','性别']*/
			               title:null,
			               highlight: false, // 是否高亮显示匹配的字
			               //callback：选中行后按回车或单击时回调的函数
			               callback:null};
			$.extend(this.config,param);
			
			$this.data("config",this.config);
			$this.datas = $this.data("config").data;
			$this.titles = $this.data("config").title || [];
			
			//输入框keydown事件
			var timeout = '';
			$this.bind("keydown.auto",function(event) {
				switch (event.keyCode) {
				case 40://向下键
					
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $nextSiblingTr = $bigAutocompleteContent.find(".ct");
					if($nextSiblingTr.length <= 0){//没有选中行时，选中第一行
						$nextSiblingTr = $bigAutocompleteContent.find("tbody tr:first");
					}else{
						$nextSiblingTr = $nextSiblingTr.next();
					}
					$bigAutocompleteContent.find("tbody tr").removeClass("ct");
					
					if($nextSiblingTr.length > 0){//有下一行时（不是最后一行）
						$nextSiblingTr.addClass("ct");//选中的行加背景
						$this.val($nextSiblingTr.find("div:first").html());//选中行内容设置到输入框中
						
						//div滚动到选中的行,jquery-1.6.1 $nextSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($nextSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $nextSiblingTr.height() );
						
					}else{
						$this.val(bigAutocomplete.holdText);//输入框显示用户原始输入的值
					}
					
					
					break;
				case 38://向上键
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $previousSiblingTr = $bigAutocompleteContent.find(".ct");
					if($previousSiblingTr.length <= 0){//没有选中行时，选中最后一行行
						$previousSiblingTr = $bigAutocompleteContent.find("tbody tr:last");
					}else{
						$previousSiblingTr = $previousSiblingTr.prev();
					}
					$bigAutocompleteContent.find("tbody tr").removeClass("ct");
					
					if($previousSiblingTr.length > 0){//有上一行时（不是第一行）
						$previousSiblingTr.addClass("ct");//选中的行加背景
						$this.val($previousSiblingTr.find("div:first").html());//选中行内容设置到输入框中
						
						//div滚动到选中的行,jquery-1.6.1 $$previousSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($previousSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $previousSiblingTr.height());
					}else{
						$this.val(bigAutocomplete.holdText);//输入框显示用户原始输入的值
					}
					
					break;
				case 27://ESC键隐藏下拉框
					
					bigAutocomplete.hideAutocomplete();
					break;
				}
				clearTimeout(timeout);
			});		
			
			//输入框值变化事件
			$this.bind("input.auto propertychange.auto",function(event) {
				var k = event.keyCode;
				if(k!=13&&k!=37&&k!=38&&k!=39&&k!=40){
					LoadingShow();
				}
				var ctrl = event.ctrlKey;
				var isFunctionalKey = false;//按下的键是否是功能键
				for(var i=0;i<bigAutocomplete.functionalKeyArray.length;i++){
					if(k == bigAutocomplete.functionalKeyArray[i]){
						isFunctionalKey = true;
						break;
					}
				}
				//k键值不是功能键或是ctrl+c、ctrl+x时才触发自动补全功能
				if(!isFunctionalKey && (!ctrl || (ctrl && k == 67) || (ctrl && k == 88)) ){
					var config = $this.data("config");
					//补全前先清空补全内容的框的值（不包括当前焦点的框）
					var ids = $this.data("config").id;
					for(var i=0,len=ids.length;i<len;i++){
						if(ids[i]==$this.prop("id"))continue;
						$("#"+ids[i]).val("");
					}
					
					var offset = $this.offset();
					$bigAutocompleteContent.width(config.width);
					var h = $this.outerHeight() - 1;
					$bigAutocompleteContent.css({"top":offset.top + h + 1,"left":offset.left});
					
					var data = $this.datas;
					var ajax = config.ajax;
					var keyword_ = $.trim($this.val());
					var mykeyword = keyword_;
			
					if(keyword_ == null || keyword_ == ""){
						bigAutocomplete.hideAutocomplete();
						return;
					}					
					if(data != null && $.isArray(data) && data.length > 0 ){
						var data_ = new Array();
						for(var i=0;i<data.length;i++){
							var son = data[i];
							var len = son.length || 0;
							//for (var j=0;j<len;j++){
								if(son[0].indexOf(keyword_) > -1){
									data_.push(son);
								}
							//}
						}
						
						//data_ = arrUnique(data_);
						//console.log(data_);
						makeContAndShow(data_);
					}else if(ajax != null && ajax != ""){//ajax请求数据
						var url = ajax.url || '';
						var type = ajax.type || 'get';
						var data = ajax.data || {};
						var dataType = ajax.dataType || 'json';
						var beforeSend = beforeSend || '';
						var success = ajax.success || '';
						var error = ajax.error || '';

						if (url == '') return false;
						// url = url.replace("keyword",keyword_); 
						var reg = new RegExp("keyword","g");
						keyword_ = keyword_.toUpperCase();
						keyword_ = encodeURI(encodeURI(keyword_));
					    
					    if (isNaN(keyword_)) {
					    	url = url.replace("keyword1","0");
					    };
					    url = url.replace("keyword1","keyword");
					    url = url.replace(reg,keyword_); 
						url = type.toLowerCase() == 'get'? (url.indexOf('?') > -1? url+'&keyword='+keyword_: url+'?keyword='+keyword_): url;
						if (type.toLowerCase() == 'post') {
							data.keyword = keyword_;
						};
						timeout = setTimeout(function(){
					    var que = $(document).queue(function () {//队列
								$.ajax({
									url: url,
									type: type,
									dataType: dataType,
									data: data,
									beforeSend: function(){
										if(beforeSend && $.isFunction(beforeSend)){
											beforeSend();
										}
									},
									success: function(result){
										if(success && $.isFunction(success)){
											success(result);
										}
										$this.setData('');
										que.dequeue();
									},
									error: function(){
										if(error && $.isFunction(error)){
											error();
										}
										$this.setData('');
										que.dequeue();
									}
								});
								
							});
						},300);
					}

					bigAutocomplete.holdText = $this.val();
				}
				//回车键
				if(k == 13){
					var callback_ = $this.data("config").callback;
					if($bigAutocompleteContent.css("display") != "none"){
						if(callback_ && $.isFunction(callback_)){
							callback_($bigAutocompleteContent.find(".ct").data("jsonData"));
						}
						$bigAutocompleteContent.hide();						
					}
				}
				
			});	
			
		    function makw(){
			var may = "<span style='padding:2px 30px;'>没有查询到相应数据，请重新输入....</span>";
			$bigAutocompleteContent.html(may);
			$bigAutocompleteContent.show();
			}
			function LoadingShow(){
			var may = "<span style='padding:2px 30px;'>努力加载中,请稍候....</span>";
			$bigAutocompleteContent.html(may);
			$bigAutocompleteContent.show();
			}		
			//组装下拉框html内容并显示
			function makeContAndShow(data_){
				if(data_ == null || data_.length <=0 ){
					makw();
					return;
				}
				var cont = "<table>";
				var highlight = $this.data("config").highlight || false;
				var keyword = $.trim($this.val());
				keyword = keyword.toUpperCase();
				if ($this.titles.length > 0) {
					cont += "<thead><tr>";
					for (var i = 0; i < $this.titles.length; i++) {
						var title = $this.titles[i].split('|') || [];
						var style = title.length >0 && title[1] == 'hide'? 'style="display:none;"': ''; 
						cont += "<th "+style+">"+title[0]+"</th>";
					};
					cont += "</tr></thead>";
				};
				cont += "<tbody>";
				for(var i=0;i<data_.length;i++){
					cont += "<tr>"
					var son = data_[i];
					var len = son.length || 0;
					for (var j=0;j<len;j++){
						// console.log(j);
						var reg = new RegExp(keyword,"g");
						var str = '<span style="color:red;">'+keyword+'</span>';
						var is_string = (typeof son[j]=='string') && son[j].constructor==String? true: false; 
						var text = highlight && is_string? son[j].replace(reg,str): son[j];
						var title = $this.titles.length > 0? $this.titles[j].split('|'): [];
						var style = title.length >0 && title[1] == 'hide'? 'style="display:none;"': ''; 
						cont += "<td "+style+"><div>" + text + "</div></td>"
					}
					cont += "</tr>";
				}
				cont += "</tbody>";
				cont += "</table>";
				$bigAutocompleteContent.html(cont);
				$bigAutocompleteContent.show();
				
				//每行tr绑定数据，返回给回调函数
				$bigAutocompleteContent.find("tbody tr").each(function(index){
					$(this).data("jsonData",data_[index]);
				})
			}			
			
			// 删除数组中重复的元素
			function arrUnique(arr) {
			    var result = [], isRepeated;
			    arr = arr || [];
			    for (var i = 0, len = arr.length; i < len; i++) {
			        isRepeated = false;
			        for (var j = 0, len = result.length; j < len; j++) {
			            if (arr[i] == result[j]) {   
			                isRepeated = true;
			                break;
			            }
			        }
			        if (!isRepeated) {
			            result.push(arr[i]);
			        }
			    }
			    return result;
			}		
			
			//输入框focus事件
			$this.bind("focus.auto",function(){
				bigAutocomplete.currentInputText = $this;
			});
			
			// 设置显示的数据
			$this.setData = function(data,update){
				data = data || '';
				update = update || false;
				$this.datas = data;
				if (update) {
					makeContAndShow(data);
				};
			}

			// 设置标题
			$this.setTitle = function(titles){
				titles = titles || [];
				if (titles == '') return false;
				$this.titles = titles;

				makeContAndShow($this.datas);
			}

			$this.getId = function(){
				return $this.atr('id');
			};
			return $this;
		}
		//隐藏下拉框
		this.hideAutocomplete = function(){
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			if($bigAutocompleteContent.css("display") != "none"){
				$bigAutocompleteContent.find("tr").removeClass("ct");
				$bigAutocompleteContent.hide();
			}			
		}
		
	};
	$.fn.bigAutocomplete = bigAutocomplete.autocomplete;
	
})(jQuery)
function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}
//部门信息查询录入
	$(function(){
		if($("input").hasClass("sector_entry")){

		var test = $(".sector_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['companyid','companyopcode','companyname'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'CompanyAPI/QueryCompany?param='+encodeURI(encodeURI("keyword")),				
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						if(Str!=null&&Str!=""){
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(Str[i].companyid);
						datas[i].push(Str[i].companyopcode);
						datas[i].push(Str[i].companyname);
						}
					}
					test.setData(datas,true); // 设置显示的内容，并更新
					test.setTitle(['编号','拼音码','部门名称']); // 设置标题
				}
			}
	 	});
	}
	 });



// 供应商信息查询录入
	$(function(){
		if($("input").hasClass("supplier_entry")){

		var test1 = $(".supplier_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['supplierid','suppliername','supplierstname','linkman','email','telephone','bank','bankno','tax_no','inputmanid','bookindata','credit','creditflag','grade','sppliertype','onlineflag'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'SupplierAPI/getSupplierObj?param='+encodeURI(encodeURI("keyword")),				
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						if(Str!=null&&Str!=""){
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(Str[i].supplierid);
						datas[i].push(Str[i].suppliername);
						datas[i].push(Str[i].supplierstname);
						datas[i].push(Str[i].linkman);
						datas[i].push(Str[i].email);
						datas[i].push(Str[i].telephone);
						datas[i].push(Str[i].bank);
						datas[i].push(Str[i].bankno);
						datas[i].push(Str[i].tax_no);
						datas[i].push(Str[i].inputmanid);
						datas[i].push(Str[i].bookindata);
						datas[i].push(Str[i].credit);
						datas[i].push(Str[i].creditflag);
						datas[i].push(Str[i].grade);
						datas[i].push(Str[i].sppliertype);
						datas[i].push(Str[i].onlineflag);
						}
					}
					test1.setData(datas,true); // 设置显示的内容，并更新
					test1.setTitle(['供应商编码','供应商名称','供应商简称|hide','联系人','E-MAIL|hide','联系电话','开户行|hide','账号|hide','税号|hide','登记人ID|hide','登记日期|hide','信用额度|hide','信用控制|hide','级别|hide','供应商类型|hide','是否网上供应商|hide']); // 设置标题
				}
			}
	 	});
	}
	 });

// 客户信息查询录入（ok）
	$(function(){
		if($("input").hasClass("customer_entry")){

			var test2 = $(".customer_entry").bigAutocomplete({
			 	width:'auto',
			 	id:['clientid','clientname','clientshortname','artificialperson','regaddress','regcapital','linkman','linkmanduty','telephone','bank','bankno','taxno','inputmanid','bookindate','credit','creditflag','receiveacct','region','clientkind'],
			 	highlight: true,
				ajax:{
					url: baseUrl+'ClientAPI/queryClientList?param='+encodeURI(encodeURI("keyword")),				
					type : "GET",
					data : null,
					success: function(data){
						    var result = eval(data);
							var Str = result.data[0];
							var datas = [];
							if(Str!=null&&Str!=""){
							for (var i = 0; i < Str.length; i++) {
							datas[i] = [];
							datas[i].push(Str[i].clientid);
							datas[i].push(Str[i].clientname);
							datas[i].push(Str[i].clientshortname);
							datas[i].push(Str[i].artificialperson);
							datas[i].push(Str[i].regaddress);
							datas[i].push(Str[i].regcapital);
							datas[i].push(Str[i].linkman);
							datas[i].push(Str[i].linkmanduty);
							datas[i].push(Str[i].telephone);
							datas[i].push(Str[i].bank);
							datas[i].push(Str[i].bankno);
							datas[i].push(Str[i].taxno);
							datas[i].push(Str[i].inputmanid);
							datas[i].push(Str[i].bookindate);
							datas[i].push(Str[i].credit);
							datas[i].push(Str[i].creditflag);
							datas[i].push(Str[i].receiveacct);
							datas[i].push(Str[i].region);
							datas[i].push(Str[i].clientkind);
							}
						}
						test2.setData(datas,true); // 设置显示的内容，并更新
						test2.setTitle(['客户编码','客户名称','客户简称|hide','法人代表','注册地址|hide','注册资金|hide','联系人','联系人职务|hide','联系电话','开户行','账号','税号|hide','登记人ID|hide','登记日期|hide','信用额度|hide','信用控制|hide','应收款余额|hide','客户区域|hide','客户性质|hide']); // 设置标题
					}
				}
		 	});
		}
	 });


// 品牌信息查询录入(ok)
	$(function(){
		if($("input").hasClass("brand_entry")){

		var test3 = $(".brand_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['brandid','opcode','brandname'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'BrandAPI/getBrandQuery?param='+encodeURI(encodeURI("keyword")),				
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						if(Str!=null&&Str!=""){
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(Str[i].brandid);
						datas[i].push(Str[i].opcode);
						datas[i].push(Str[i].brandname);
						}
					}
					$("#brandid").val("");
					$("#opcode").val("");
					test3.setData(datas,true); // 设置显示的内容，并更新
					test3.setTitle(['品牌ID','拼音码','品牌名称']); // 设置标题
				}
			}
	 	});
	}
	 });


// 商品信息查询录入(ok)
	$(function(){
		if($("input").hasClass("goods_entry")){
		var test4 = $(".goods_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['goodsid','goodsname','goodsunit','goodstype','goodsclassid','departmentid','brandid','prodarea','inputmanid','bookindate','priceflag','stupperlimit','stlowerlimit','barcode','classcodeflag','smallscaleflag'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'GoodsAPI/getGoodsListParam?param='+encodeURI(encodeURI("keyword")),				
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						if(Str!=null&&Str!=""){
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(Str[i].goodsid);
						datas[i].push(Str[i].goodsname);
						datas[i].push(Str[i].goodsunit);
						datas[i].push(Str[i].goodstype);
						datas[i].push(Str[i].goodsclassid);
						datas[i].push(Str[i].departmentid);
						datas[i].push(Str[i].brandid);
						datas[i].push(Str[i].prodarea);
						datas[i].push(Str[i].inputmanid);
						datas[i].push(Str[i].bookindate);
						//datas[i].push(Str[i].status);
						datas[i].push(Str[i].priceflag);
						datas[i].push(Str[i].stupperlimit);
						datas[i].push(Str[i].stlowerlimit);
						datas[i].push(Str[i].barcode);
						datas[i].push(Str[i].classcodeflag);
						datas[i].push(Str[i].smallscaleflag);
						}
					}
					test4.setData(datas,true); // 设置显示的内容，并更新
					test4.setTitle(['商品编码','商品名称','单位','商品规格','商品分类ID|hide','主营部门ID|hide','品牌ID|hide','产地|hide','登记人ID|hide','登记日期|hide','限价标志|hide','库存上限|hide','库存下限|hide','条码|hide','大类码标志|hide','小规模标志|hide']); // 设置标题
				}
			}
	 	});
	}
});




// 商品相关联的商品属性、仓库信息、价格文件等查询录入
	$(function(){
		if($("input").hasClass("good_entry")){
		var test5 = $(".good_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['goodsid','goodsname','pricetypeid','pricetypename','depotid','depotname','goodspropertyid','goodspropertyname','minprice','supplyprice','cxbz','macpriceid','limitnum','stocknum','agenttype','agenttypename','brandid','brandname'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'GoodsAPI/getGoodsStockList?param='+encodeURI(encodeURI("keyword")),				
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						if(Str!=null&&Str!=""){
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(Str[i].goodsid);
						datas[i].push(Str[i].goodsname);
						datas[i].push(Str[i].pricetypeid);
						datas[i].push(Str[i].pricetypename);
						datas[i].push(Str[i].depotid);
						datas[i].push(Str[i].depotname);
						datas[i].push(Str[i].goodspropertyid);
						datas[i].push(Str[i].goodspropertyname);
						datas[i].push(Str[i].minprice);
						datas[i].push(Str[i].supplyprice);
						datas[i].push(Str[i].cxbz=='Y'?'是':'否');
						datas[i].push(Str[i].macpriceid);
						datas[i].push(Str[i].maxqty);
						datas[i].push(Str[i].stockqty);				
						datas[i].push(Str[i].agenttypeid);
						datas[i].push(Str[i].agenttypename);
						datas[i].push(Str[i].brandid);
						datas[i].push(Str[i].brandname);
						}
						}
					test5.setData(datas,true); // 设置显示的内容，并更新
					test5.setTitle(['商品编码','商品名称','价格类型ID|hide','价格类型','仓库ID|hide','仓库','商品属性ID|hide','商品属性','限卖价','供货价|hide','是否促销价','促销价格文件编号|hide','限卖数|hide','库存数|hide','经营方式ID|hide','经营方式|hide','品牌ID|hide','品牌名称|hide',]); // 设置标题
				}
			}
	 	});
	}
});	

//有效价格文件的商品
function drawGoodsInfoByPrice(){
		var supplierid = $("#supplierid").val()
		var suconid = $("#suconid").val()
		var departmentid = $("#departmentid").val();
		var urlSuffix = "&suconid="+suconid+"&purchaseDeptid="+departmentid;
		if($("#bigAutocompleteContent").length>0)$("#bigAutocompleteContent").remove();
		if($("#agenttypeid").val() == 3){//联营管库
			urlSuffix += "&supplierid="+supplierid;
		}
		if($("input").hasClass("good_price_entry")){
		var test6 = $(".good_price_entry").bigAutocomplete({
		 	width:'auto',
		 	id:['goodsid','goodsname','goodsunit','minprice','netprice','wsprice','saleprice','supplyprice'],
		 	highlight: true,
			ajax:{
				url: baseUrl+'GoodsAPI/getGoodsPriceList?where='+encodeURI(encodeURI("keyword"))+urlSuffix,
				type : "GET",
				data : null,
				success: function(data){
					    var result = eval(data);
						var Str = result.data[0];
						var datas = [];
						for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(nullShow(Str[i].goodsid));
						datas[i].push(nullShow(Str[i].goodsname));
						datas[i].push(nullShow(Str[i].goodsunit));
						datas[i].push(nullShow(Str[i].minprice));
						datas[i].push(nullShow(Str[i].netprice));
						datas[i].push(nullShow(Str[i].wsprice));
						datas[i].push(nullShow(Str[i].saleprice));
						datas[i].push(nullShow(Str[i].supplyprice));
						
						}
					test6.setData(datas,true); // 设置显示的内容，并更新
					test6.setTitle(['商品编码','商品名称','单位','零售限价','网上价','批发价','零售单价','供货价']); // 设置标题
				}
			}
	 	});
	}
}

//商品供应商信息查询录入
$(function(){
	if($("input").hasClass("supplier_goods_entry")){

	var test1 = $(".supplier_goods_entry").bigAutocomplete({
	 	width:'auto',
	 	id:['supplierid','suppliername','supplierstname','linkman','email','telephone','bank','bankno','tax_no','inputmanid','bookindata','credit','creditflag','grade','sppliertype','onlineflag'],
	 	highlight: true,
		ajax:{
			url: baseUrl+'SupplierAPI/getSupplierListForContract?suppliertype=0&param='+encodeURI(encodeURI("keyword")),				
			type : "GET",
			data : null,
			success: function(data){
				    var result = eval(data);
					var Str = result.data[0];
					var datas = [];
					if(Str!=null&&Str!=""){
					for (var i = 0; i < Str.length; i++) {
					datas[i] = [];
					datas[i].push(Str[i].supplierid);
					datas[i].push(Str[i].suppliername);
					datas[i].push(Str[i].supplierstname);
					datas[i].push(Str[i].linkman);
					datas[i].push(Str[i].email);
					datas[i].push(Str[i].telephone);
					datas[i].push(Str[i].bank);
					datas[i].push(Str[i].bankno);
					datas[i].push(Str[i].tax_no);
					datas[i].push(Str[i].inputmanid);
					datas[i].push(Str[i].bookindata);
					datas[i].push(Str[i].credit);
					datas[i].push(Str[i].creditflag);
					datas[i].push(Str[i].grade);
					datas[i].push(Str[i].sppliertype);
					datas[i].push(Str[i].onlineflag);
					}
				}
				test1.setData(datas,true); // 设置显示的内容，并更新
				test1.setTitle(['供应商编码','供应商名称','供应商简称|hide','联系人','E-MAIL|hide','联系电话','开户行|hide','账号|hide','税号|hide','登记人ID|hide','登记日期|hide','信用额度|hide','信用控制|hide','级别|hide','供应商类型|hide','是否网上供应商|hide']); // 设置标题
			}
		}
 	});
}
 });

function nullShow(obj){
	return  obj == null?"":obj;
}
$(function(){
	initEmployeeEntry();
})
function initEmployeeEntry(){
	if($("input").hasClass("inputman_entry")){
		var inputman_entry = $(".inputman_entry").bigAutocomplete({
			width:'auto',
			id:['inputmanid','inputmanname','inputmanopcode','inputmandeptname','inputmanpositionname'],
			highlight: true,
			ajax:{
				url: baseUrl+'EmployeeAPI/getEmployeeList?param='+encodeURI(encodeURI("keyword")),
				type : "GET",
				data : null,
				success: function(data){
					var result = eval(data);
					var Str = result.data[0];
					var datas = [];
					for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(nullShow(Str[i].employeeid));
						datas[i].push(nullShow(Str[i].employeename));
						datas[i].push(nullShow(Str[i].opcode));
						datas[i].push(nullShow(Str[i].deptname));
						datas[i].push(nullShow(Str[i].positionname));
					}
					inputman_entry.setData(datas,true); // 设置显示的内容，并更新
					inputman_entry.setTitle(['员工编码','员工姓名','拼音码','部门','岗位']); // 设置标题
				}
			}
		});
	}
	if($("input").hasClass("auditor_entry")){
		var auditor_entry = $(".auditor_entry").bigAutocomplete({
			width:'auto',
			id:['auditorid','auditorname','auditoropcode','auditordeptname','auditorpositionname'],
			highlight: true,
			ajax:{
				url: baseUrl+'EmployeeAPI/getEmployeeList?param='+encodeURI(encodeURI("keyword")),
				type : "GET",
				data : null,
				success: function(data){
					var result = eval(data);
					var Str = result.data[0];
					var datas = [];
					for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(nullShow(Str[i].employeeid));
						datas[i].push(nullShow(Str[i].employeename));
						datas[i].push(nullShow(Str[i].opcode));
						datas[i].push(nullShow(Str[i].deptname));
						datas[i].push(nullShow(Str[i].positionname));
					}
					auditor_entry.setData(datas,true); // 设置显示的内容，并更新
					auditor_entry.setTitle(['员工编码','员工姓名','拼音码','部门','岗位']); // 设置标题
				}
			}
		});
	}
	if($("input").hasClass("invalidman_entry")){
		var invalidman_entry = $(".invalidman_entry").bigAutocomplete({
			width:'auto',
			id:['invalidmanid','invalidmanname','invalidmanopcode','invalidmandeptname','invalidmanpositionname'],
			highlight: true,
			ajax:{
				url: baseUrl+'EmployeeAPI/getEmployeeList?param='+encodeURI(encodeURI("keyword")),
				type : "GET",
				data : null,
				success: function(data){
					var result = eval(data);
					var Str = result.data[0];
					var datas = [];
					for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(nullShow(Str[i].employeeid));
						datas[i].push(nullShow(Str[i].employeename));
						datas[i].push(nullShow(Str[i].opcode));
						datas[i].push(nullShow(Str[i].deptname));
						datas[i].push(nullShow(Str[i].positionname));
					}
					invalidman_entry.setData(datas,true); // 设置显示的内容，并更新
					invalidman_entry.setTitle(['员工编码','员工姓名','拼音码','部门','岗位']); // 设置标题
				}
			}
		});
	}
	if($("input").hasClass("validateman_entry")){
		var validateman_entry = $(".validateman_entry").bigAutocomplete({
			width:'auto',
			id:['validatemanid','validatemanname','validatemanopcode','validatemandeptname','validatemanpositionname'],
			highlight: true,
			ajax:{
				url: baseUrl+'EmployeeAPI/getEmployeeList?param='+encodeURI(encodeURI("keyword")),
				type : "GET",
				data : null,
				success: function(data){
					var result = eval(data);
					var Str = result.data[0];
					var datas = [];
					for (var i = 0; i < Str.length; i++) {
						datas[i] = [];
						datas[i].push(nullShow(Str[i].employeeid));
						datas[i].push(nullShow(Str[i].employeename));
						datas[i].push(nullShow(Str[i].opcode));
						datas[i].push(nullShow(Str[i].deptname));
						datas[i].push(nullShow(Str[i].positionname));
					}
					validateman_entry.setData(datas,true); // 设置显示的内容，并更新
					validateman_entry.setTitle(['员工编码','员工姓名','拼音码','部门','岗位']); // 设置标题
				}
			}
		});
	}
}