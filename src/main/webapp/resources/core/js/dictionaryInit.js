$(function() {
	
	dictInit();// 数据字典初始化
	apiUrlInit();// 公共数据初始化
});
window.publicData = {};
window.localData = {};


	

// 判断对象是否为空。
function isEmptyObject(obj) {
	for ( var key in obj) {
		return false;
	}
	return true;
}
// 数据字典初始化
function dictInit() {
	var keyword;
	if (isEmptyObject(localData)) {
		$.ajax({
			async : false,
			type : "GET",
			dataType : 'json',
			url : dictURL+"?sessionId="+sessionId,
			error : function(data) {
				alert("获取数据词典失败" + data);
			},
			success : function(data) {
				localData = data;
			}
		});
	}
	$("select[keyword]").each(function() {
		keyword = $(this).attr("keyword");
		selectInitBykeyword($(this), window.localData, keyword);
	});
	$("input[keyword][type='text']").not(".select_checkbox").each(function() {
		keyword = $(this).attr("keyword");
		inputShowText($(this), window.localData, keyword);
	});
	$("input[keyword][type='checkbox']").each(function() {
		keyword = $(this).attr("keyword");
		checkboxInitBykeyword($(this), window.localData, keyword);
	});
	$("td[keyword]").each(function() {
		keyword = $(this).attr("keyword");
		tdShowText($(this), window.localData, keyword);
	});
	$("input[keyword][type='text'].select_checkbox").each(function() {
		keyword = $(this).attr("keyword");
		selectCheckboxInit($(this), window.localData, keyword);
	});
	trClick4Radio();
	loadThousandsSeparator(); 
}
// 根据数据字典生成下拉复选框
function selectCheckboxInit(input, localData, keyword) {
	var name = input.prop("name");
	input.prop("name", name + "show");
	input.removeAttr("keyword");
	var inputHide = '<input type="hidden" name="' + name
			+ '" class="select_checkbox"/>';
	var prefixDiv = '<div class="Select_main"><div class="Select_check"><div class="checkbox_w">';
	var suffixDiv = '</div></div><div class="Select_but"><input type="button" class="btn-bd sure" value="确定" />'
			+ '<input type="button" class="btn-bd offs" value="关闭" /></div></div>';
	var labels = "";
	var div;
	var filterValue = input.attr("filterValue");
	var filterValues = [];
	if (filterValue != undefined)filterValues = filterValue.split(",");
	$.each(localData.data[0], function(key, objArr) {
		if (key == keyword) {
			$.each(objArr, function(id, obj) {
				if ($.inArray(obj.ddlid.toString(), filterValues) > -1)return true;
				labels += '<label><input type="checkbox" checkboxName="' + name
						+ '" value="' + obj.ddlid + '">' + '<span>'
						+ obj.ddlname + '</span></label>';
			});
		}
	});
	div = prefixDiv + labels + suffixDiv;
	input.parent().append(div);
	input.before(inputHide);
	// 下拉显示定位
	var position = input.offset();
	input.next().offset({
		// top:position.top+22,
		left : position.left,
	});
	hidee();
}
// 根据数据字典生成select
function selectInitBykeyword(select, localData, keyword) {
	var options = "";
	var value = select.attr("value");
	var filterValue = select.attr("filterValue");
	var filterValues = [];
	if (filterValue != undefined)
		filterValues = filterValue.split(",");
	$.each(localData.data[0], function(key, objArr) {
		if (key == keyword) {
			$.each(objArr, function(id, obj) {
				if ($.inArray(obj.ddlid.toString(), filterValues) == -1) {
					if(value==obj.ddlid.toString()){
						options += '<option value="' + obj.ddlid + '" selected="selected">' + obj.ddlname
						+ '</option>';
					}else{
						options += "<option value='" + obj.ddlid + "'>" + obj.ddlname
						+ "</option>";
					}
				}
			});
		}
	});
	select.append(options);
	select.removeAttr("keyword");
	if (typeof (select.attr("value")) != "undefined")
		select.removeAttr("value");
}
// 根据数据字典将对应的note在input显示
function inputShowText(input, localData, keyword) {
	var value = input.val();
	var nullString = input.attr("nullString");
	nullString = nullString == undefined ? "无" : nullString;
	$.each(localData.data[0], function(key, objArr) {
		if (key == keyword) {
			$.each(objArr, function(id, obj) {
				if (value == "") {
					input.val(nullString);
				} else if (obj.ddlid == value) {
					input.val(obj.ddlname);
				}
			});
		}
	});
	input.removeAttr("keyword");
}
// 根据数据字典将对应的note在td显示
function tdShowText(td, localData, keyword) {
	var nullString = td.attr("nullString");
	nullString = nullString == undefined ? "无" : nullString;
	var value = td.text();
	$.each(localData.data[0], function(key, objArr) {
		if (key == keyword) {
			$.each(objArr, function(id, obj) {
				if (value == "") {
					td.text(nullString);
				} else if (obj.ddlid == value) {
					td.text(obj.ddlname);
				}
			});
		}
	});
	td.removeAttr("keyword");
}
// 根据数据字典生成checkbox
function checkboxInitBykeyword(checkbox, localData, keyword) {
	var options = "";
	var value = checkbox.val();
	var name = checkbox.attr("name");
	var checkeds = value.split(",");
	var cloneLable = checkbox.parent();
	var cloneCheckbox = checkbox.clone(true).removeAttr("keyword").prop("checked",false);
	var cloneSpan = checkbox.nextAll("span");
	var i = 0;
	var filterValue = checkbox.attr("filterValue");
	var filterValues = [];
	if (filterValue != undefined)
		filterValues = filterValue.split(",");
	$.each(localData.data[0], function(key, objArr) { // 拼接checkbox
		if (key == keyword) {
			$.each(objArr, function(id, obj) {
				if ($.inArray(obj.ddlid.toString(), filterValues) > -1)return true;
				var clone = cloneCheckbox.clone(true);
				var span;
				var mylable;
				clone.val(obj.ddlid);
				if (cloneSpan.length > 0) {
					span = cloneSpan.clone(true);
					span.text(obj.ddlname);
				} else {
					span = "<span>" + obj.ddlname + "</span>";
				}
				if (cloneLable.prop("tagName") == "LABEL") {
					mylable = cloneLable.clone(true);
					mylable.text("");
					mylable.append(clone);
					mylable.append(span);
					cloneLable.after(mylable);
				} else {
					checkbox.after(clone);
					clone.after(span);
				}
			});
		}
	});
	for (var i = 0, len = checkeds.length; i < len; i++) { // 设置默认值
		$("input[type='checkbox'][name='" + name + "']").each(function() {
			if ($(this).val() == checkeds[i]){
				$(this).attr("checked", true);
				if(!$(this).prop("checked")){
					$(this).prop("checked", true);
				}
			}
		});
	}
	cloneSpan.remove();
	checkbox.remove();
	if (cloneLable.prop("tagName") == "LABEL")
		cloneLable.remove();
}
// 根据apiUrl初始化
function apiUrlInit() {
	$("select[apiUrl]").each(function() {
		selectInitByApiUrl($(this));
		$(this).removeAttr("apiUrl");
	})
	$("label[apiUrl]:has(input[type='checkbox'])").each(function() {
		checkboxInitByApiUrl($(this));
	})
	$("td[apiUrl]").each(function() {
		tdInitByApiUrl($(this));
		$(this).removeAttr("apiUrl");
	})
}
// 根据apiUrl初始化checkbox
function checkboxInitByApiUrl(obj) {
	var url = obj.attr("apiUrl");
	var where = encodeURI(encodeURI($(this).attr("limit")));
	var finalUrl = "";
	if (typeof (where) == "undefined" || where.trim() == ""
			|| where == "undefined") {
		finalUrl = baseUrl + url;
	} else {
		finalUrl = baseUrl + url + "?where=" + where;
	}
	if (publicData[finalUrl] != undefined) {
		drawCheckedbox(obj, publicData[finalUrl]);
	} else {
		$.ajax({
			async : false,
			type : "GET",
			dataType : 'json',
			url : finalUrl,
			error : function(data) {
				alert("获取公共数据失败");
			},
			success : function(data) {
				publicData[finalUrl] = data;
				drawCheckedbox(obj, publicData[finalUrl]);
			}
		})
	}
}
function drawCheckedbox(obj, data) {
	var clone = obj.clone(true).removeAttr("apiUrl").removeAttr("value")
			.removeAttr("text").removeAttr("limit");
	var value = obj.attr("value");
	var text = obj.attr("text");
	if (clone == undefined) {
		return;
	}
	var dataObj = {};
	var checkboxValue = "";
	var checkboxText = "";
	if (data.data[0] == null)
		return;
	for (var i = 0, len = data.data[0].length; i < len; i++) {
		checkboxValue = data.data[0][i][value];
		checkboxText = data.data[0][i][text];
		dataObj[checkboxValue] = checkboxText;
	}
	$.each(dataObj, function(cbvalue, cbtext) {
		var label = clone.clone(true);
		label.find("input[type='checkbox']").val(cbvalue);
		label.append("<span> " + cbtext);
		obj.after(label)
	});
	obj.remove();
}
// 根据apiUrl初始化select
function selectInitByApiUrl(select) {
	var url = select.attr("apiUrl");
	var where = encodeURI(encodeURI(select.attr("limit")));
	var finalUrl = "";
	if (typeof (where) == "undefined" || where.trim() == ""
			|| where == "undefined") {
		finalUrl = baseUrl + url;
	} else {
		finalUrl = baseUrl + url + "?where=" + where;
	}
	if (publicData[finalUrl] != undefined) {
		drawSelect(select, publicData[finalUrl]);
	} else {
		$.ajax({
			async : false,
			type : "GET",
			dataType : 'json',
			url : finalUrl,
			error : function(data) {
				alert("获取公共数据失败");
			},
			success : function(data) {
				publicData[finalUrl] = data;
				drawSelect(select, publicData[finalUrl]);
			}
		});
	}
}
function drawSelect(select, data) {
	var optionValue = select.attr("optionValue");
	var optionText = select.attr("optionText");
	var id;
	var name;
	var options = "";
	var dataObj = {};
	var value = select.attr("value");
	if (data.data[0] == null)
		return;
	for (var i = 0, len = data.data[0].length; i < len; i++) {
		id = data.data[0][i][optionValue];
		name = data.data[0][i][optionText];
		dataObj[id] = name;
	}
	$.each(dataObj, function(id, name) {
		if(value == id){
			options += "<option value='" + id + "' selected='selected'>" + name + "</option>";
		}else{
			options += "<option value='" + id + "'>" + name + "</option>";
		}
	});
	select.append(options);
	if (typeof (select.attr("value")) != "undefined")
		select.removeAttr("value");
}
function tdInitByApiUrl(td) {
	var finalUrl = baseUrl + td.attr("apiUrl");
	if (publicData[finalUrl] != undefined) {
		drawTd(td, publicData[finalUrl]);
	} else {
		$.ajax({
			async : false,
			type : "GET",
			dataType : 'json',
			url : finalUrl,
			error : function(data) {
				alert("获取公共数据失败");
			},
			success : function(data) {
				publicData[finalUrl] = data;
				drawTd(td, publicData[finalUrl]);
			}
		});
	}
}
function drawTd(td, data) {
	var id = "";
	var name = "";
	var dataObj = {};
	var targetTdName = td.attr("targetTdName");
	var valueAttr = td.attr("value");
	var textAttr = td.attr("text");
	var valueArr = td.text().trim().split(",");
	var tdText = "";
	if (data.data[0] == null)
		return;
	for (var i = 0, len = data.data[0].length; i < len; i++) {
		id = data.data[0][i][valueAttr];
		name = data.data[0][i][textAttr];
		dataObj[id] = name;
	}
	$.each(dataObj, function(id, name) {
		if ($.inArray(id, valueArr) > -1) {
			tdText += name + " ";
		}
	})
	td.siblings("[name='" + targetTdName + "']").text(tdText.trim());
}
/**
 * 清空表单里的数据
 * 
 * @param formId
 *            表单id
 */
function formClear(formId) {
	$("#" + formId).find("input").not(":button").val("").removeAttr("checked")
			.removeAttr("selected");
	$("#" + formId).find("select").each(function() {
		$(this).val("");
	});
}

// 下拉复选框js！！！！！！！！！！！！！！！！！！！！！！
$(function() {
	// 回填值
	$(".sure")
			.click(
					function() {
						var $div = $(this).parent().parent().parent();
						var checkboxs = [];
						var checkbox = {}
						var inputName = "";
						var hideInputName = "";
						var $input;
						var $hideInput;
						var values = [];
						var texts = [];
						if ($div
								.find("input[type='checkbox'][checkboxName]:checked").length == 0) {
							$div.find(".select_checkbox").val("");
						}
						$div.find(
								"input[type='checkbox'][checkboxName]:checked")
								.each(
										function(index, obj) {
											values.push(obj.value);
											texts.push($(obj).next().html());
											hideInputName = $(obj).attr(
													"checkboxname");
											inputName = hideInputName + "show";
										})
						$input = $div.find("input[type=text][name='"
								+ inputName + "']");
						$hideInput = $div.find("input[type=hidden][name='"
								+ hideInputName + "']");
						$hideInput.val(values.toString());
						$input.val(texts.toString());
						$(this).parent().parent().hide();
					});
	$(".select_checkbox").focus(function() {
		$(this).next().show();
		$(this).blur();
	});
	$(".offs").click(function() {
		$(this).parent().parent().hide();
	});
});
// 点击除下拉框外的地方隐藏下拉框
function hidee() {
	var myDiv = $(".Select_main");
	$(function() {
		$(".select_checkbox").click(function(event) {
			$(document).one("click", function() {
				myDiv.hide();
			});
			event.stopPropagation();
		});
		myDiv.click(function(event) {
			event.stopPropagation();
		});
	});
}
/**
 * 根据合计列的sum属性，找到所有列的name属性的值等于合计列sum属性的值，将其合计结果设置到合计列。 如：合计列
 * <td sum="number"></td>
 * 其他列<ta name="number"></td>
 * 
 * @param tbodyid
 *            合计计算数据来源范围，若不设置或找不到，则去页面范围内所有符合条件的列。
 */
function sumCompute(tbodyid) {
	var sumAttr = '';
	var decimal = 0;
	var tds;
	$("td[sum]").each(
			function() {
				var sum = 0;
				var result = 0;
				sumAttr = $(this).attr("sum");
				decimal = $(this).attr("decimal");
				if (tbodyid == undefined) {
					tds = $("td[name='" + sumAttr + "']");
				} else {
					tds = $("#" + tbodyid).find("td[name='" + sumAttr + "']");
				}
				if (tds.length != 0) {
					tds.each(function() {
						sum += isNaN(Number($(this).html())) ? 0 : Number($(
								this).html());// 不是数字的按0计算
					})
				}
				if (decimal == undefined) {
					$(this).html(sum);
				} else if (!isNaN(decimal)) {
					$(this).html(sum.toFixed(decimal));
				}
			})
}
/**
 * 单选框的行点击事件
 */
function trClick4Radio(){
	// 表格点击tr选中单选框
	$(".scrollContent tr").unbind("click.trClickRadio");
	$(".scrollContent tr").bind("click.trClickRadio",function() {
		if($(this).closest("tbody").hasClass("radio-ch")){
			return true;
		}
		if ($(this).find("input[type='radio']").prop("checked") == false) {
			$(this).find("input[type='radio']").prop("checked", true);
		} else {
			$(this).find("input[type='radio']").removeProp("checked");
		}
	});
	$(".scrollContent tr").bind("click.trClickRadio",function() {
		if($(this).closest("tbody").hasClass("radio-ch")){
			return true;
		}
		if($(this).find("input[type='radio']").length > 0){
			$(".scrollContent tr").each(function(){
				$(this).removeClass("bgss");
			})
			$(this).addClass("bgss");
		}
		if($(this).find("input").length == 0){
			$(".scrollContent tr").each(function(){
				$(this).removeClass("bgss");
			})
			$(this).addClass("bgss");
		}
	});
	$(".scrollContent tr input[type='text']").unbind("click.trClickRadio");
}