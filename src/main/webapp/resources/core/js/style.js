function table(table) {

	var Sys = (function(ua) {
		var s = {};
		s.IE = ua.match(/msie ([\d.]+)/) ? true : false;
		s.Firefox = ua.match(/firefox\/([\d.]+)/) ? true : false;
		s.Chrome = ua.match(/chrome\/([\d.]+)/) ? true : false;
		s.IE6 = (s.IE && ([ /MSIE (\d)\.0/i.exec(navigator.userAgent) ][0][1] == 6)) ? true
				: false;
		s.IE7 = (s.IE && ([ /MSIE (\d)\.0/i.exec(navigator.userAgent) ][0][1] == 7)) ? true
				: false;
		s.IE8 = (s.IE && ([ /MSIE (\d)\.0/i.exec(navigator.userAgent) ][0][1] == 8)) ? true
				: false;
		return s;
	})(navigator.userAgent.toLowerCase());
	function $(Id) {
		return document.getElementById(Id);
	}
	;
	function addListener(element, e, fn) {
		element.addEventListener ? element.addEventListener(e, fn, false)
				: element.attachEvent("on" + e, fn);
	}
	;
	function removeListener(element, e, fn) {
		element.removeEventListener ? element.removeEventListener(e, fn, false)
				: element.detachEvent("on" + e, fn);
	}
	;
	var Css = function(e, o) {
		if (typeof o == "string") {
			e.style.cssText = o;
			return;
		}
		for ( var i in o)
			e.style[i] = o[i];
	};
	var Bind = function(object, fun) {
		var args = Array.prototype.slice.call(arguments).slice(2);
		return function() {
			return fun.apply(object, args);
		};
	};
	var BindAsEventListener = function(object, fun) {
		var args = Array.prototype.slice.call(arguments).slice(2);
		return function(event) {
			return fun.apply(object, [ event || window.event ].concat(args));
		};
	};
	var Extend = function(destination, source) {
		for ( var property in source) {
			destination[property] = source[property];
		}
		;
	};
	var Class = function(properties) {
		var _class = function() {
			return (arguments[0] !== null && this.initialize && typeof (this.initialize) == 'function') ? this.initialize
					.apply(this, arguments)
					: this;
		};
		_class.prototype = properties;
		return _class;
	};
	var Table = new Class(
			{
				initialize : function(tab, set) {
					this.table = tab;
					this.thead = tab.getElementsByTagName('thead')[0]; // 常用的dom元素做成索引
					this.theadtds = this.thead.getElementsByTagName('td'); // 
					this.rows = []; // 里面tbodys记录所有tr的引用
									// 这里用数组记录是因为数组有reverse方法,可以用来正序,反序
					this.clos = {}; // 里面记录所有列元素的引用
					this.edits = {}; // 编辑表格的规则和提示
					this.sortCol = null; // 记录哪列正在排序中
					this.inputtd = null; // 记录哪个input被编辑了
					this.closarg = {
						tdnum : null,
						totdnum : null,
						closmove : BindAsEventListener(this, this.closmove),
						closup : BindAsEventListener(this, this.closup)
					}; // 关于列拖拽的一些属性方法
					this.widtharg = {
						td : null,
						nexttd : null,
						x : 0,
						tdwidth : 0,
						nexttdwidth : 0,
						widthmove : BindAsEventListener(this, this.widthmove),
						widthup : BindAsEventListener(this, this.widthup)
					};
					var i = 0, j = 0, d = document, rows = tab.tBodies[0].rows, tds1 = tab.tBodies[0]
							.getElementsByTagName('td'), edit = [];
					var divs = this.thead.getElementsByTagName('div');
					this.input = d.createElement('input'); // 编辑用的input
					this.input.type = "text";
					this.input.className = 'edit';
					this.img = d.body.appendChild(d.createElement('div'));
					this.img.className = "cc";
					this.line = d.body.appendChild(d.createElement('div'));
					this.line.className = 'line';
					this.line.style.top = tab.offsetTop + "px";
					if (Sys.IE6) {
						this.checkbox = {}; // 记录那些checkbox被选中了 处理ie6不兼容的问题
						var checkboxs = tab.getElementsByTagName('input'), k = 0;
						for (var lll = checkboxs.length; k < lll; k++)
							checkboxs[k].type == "checkbox"
									&& addListener(
											checkboxs[k],
											"click",
											Bind(
													this,
													function(elm, k) {
														elm.checked == true ? (this.checkbox[k] = elm)
																: (delete this.checkbox[k]);
													}, checkboxs[k], k));
					}
					;
					for (var l = set.length; i < l; i++) {
						addListener(this.theadtds[set[i].id], 'click', Bind(
								this, this.sortTable, this.theadtds[set[i].id],
								set[i].id, set[i].type));
						set[i].edit && (this.edits[set[i].id] = {
							rule : set[i].edit.rule,
							message : set[i].edit.message
						});
					}
					;
					for (l = rows.length; j < l; j++)
						this.rows[j] = rows[j];
					for (var k = 0, l = this.theadtds.length; k < l; k++) {
						this.clos[k] = [];
						this.theadtds[k].setAttribute('clos', k), addListener(
								this.theadtds[k], 'mousedown',
								BindAsEventListener(this, this.closdrag));
					}
					for (var i = 0, l = tds1.length; i < l; i++) {
						var p = i < this.theadtds.length - 1 ? i : i
								% this.theadtds.length;
						this.clos[p][this.clos[p].length] = tds1[i];
						this.edits[p] && tds1[i].setAttribute('edit', p);
					}
					for (var i = 0, l = divs.length; i < l; i++) {
						addListener(divs[i], 'mousedown', BindAsEventListener(
								this, this.widthdrag));
					}
					/*---------------------------------------------*/
					/*---------------------------------------------*/
					addListener(this.thead, 'mouseover', BindAsEventListener(
							this, this.theadhover));
					addListener(tab.tBodies[0], 'dblclick',
							BindAsEventListener(this, this.edit));
					addListener(this.input, 'blur', Bind(this, this.save,
							this.input));
				},
				theadhover : function(e) {
					e = e || window.event;
					var obj = e.srcElement || e.target;
					if (obj.nodeName.toLowerCase() == 'td')
						this.closarg.totdnum = (obj).getAttribute('clos');
					else if (obj.nodeName.toLowerCase() == 'div')
						obj.style.cursor = "sw-resize";
				},
				widthdrag : function(e) {
					if (Sys.IE) {
						e.cancelBubble = true
					} else {
						e.stopPropagation()
					}
					this.widtharg.x = e.clientX;
					this.widtharg.td = (e.srcElement || e.target).parentNode;
					if (Sys.IE) {
						this.widtharg.nexttd = this.widtharg.td.nextSibling;
					} else {
						this.widtharg.nexttd = this.widtharg.td.nextSibling.nextSibling;
					}
					this.widtharg.tdwidth = this.widtharg.td.offsetWidth;
					this.widtharg.nexttdwidth = this.widtharg.nexttd.offsetWidth;
					this.line.style.height = this.table.offsetHeight + "px";
					addListener(document, 'mousemove', this.widtharg.widthmove);
					addListener(document, 'mouseup', this.widtharg.widthup);
				},
				widthmove : function(e) {
					window.getSelection ? window.getSelection()
							.removeAllRanges() : document.selection.empty();
					var x = e.clientX - this.widtharg.x, left = e.clientX, clientx = e.clientX;
					if (clientx < this.widtharg.x) {
						if (this.widtharg.x - clientx > this.widtharg.tdwidth - 35)
							left = this.widtharg.x - this.widtharg.tdwidth + 35;
					}
					if (clientx > this.widtharg.x) {
						if (clientx - this.widtharg.x > this.widtharg.nexttdwidth - 35)
							left = this.widtharg.x + this.widtharg.nexttdwidth
									- 35;
					}
					Css(this.line, {
						display : "block",
						left : left + "px"
					});
				},
				widthup : function(e) {
					this.line.style.display = "none";
					var x = parseInt(this.line.style.left) - this.widtharg.x;
					this.widtharg.nexttd.style.width = this.widtharg.nexttdwidth
							- x + 'px';
					this.widtharg.td.style.width = this.widtharg.tdwidth + x
							+ 'px';
					removeListener(document, 'mousemove',
							this.widtharg.widthmove);
					removeListener(document, 'mouseup', this.widtharg.widthup);
				},
				closdrag : function(e) {
					e = e || window.event;
					var obj = e.srcElement || e.target;
					if (obj.nodeName.toLowerCase() == "span")
						obj = obj.parentNode;
					this.closarg.tdnum = obj.getAttribute('clos');
					;
					addListener(document, 'mousemove', this.closarg.closmove);
					addListener(document, 'mouseup', this.closarg.closup);
				},
				closmove : function(e) {
					window.getSelection ? window.getSelection()
							.removeAllRanges() : document.selection.empty();
					Css(this.img, {
						display : "block",
						left : e.clientX + 9 + "px",
						top : e.clientY + 20 + "px"
					});
				},
				closup : function() {
					this.img.style.display = "none";
					removeListener(document, 'mousemove', this.closarg.closmove);
					removeListener(document, 'mouseup', this.closarg.closup);
					if (this.closarg.totdnum == this.closarg.tdnum)
						return;
					var rows = this.table.getElementsByTagName('tr'), tds, n, o;
					if ((parseInt(this.closarg.tdnum) + 1) == parseInt(this.closarg.totdnum)) {
						o = this.closarg.tdnum;
						n = this.closarg.totdnum;
					} else {
						n = this.closarg.tdnum;
						o = this.closarg.totdnum;
					}
					for (var i = 0, l = rows.length; i < l; i++) {
						tds = rows[i].getElementsByTagName('td');
						try {
							rows[i].insertBefore(tds[n], tds[o]);
						} catch (err) {
							return;
						}
					}
					for (var i = 0, l = this.theadtds.length; i < l; i++)
						this.theadtds[i].setAttribute('clos', i);
				},
				edit : function(e) {
					var o = e.srcElement || e.target;
					if (!o.getAttribute('edit'))
						return;
					this.inputtd = o;
					var v = o.innerHTML;
					o.innerHTML = "";
					o.appendChild(this.input);
					this.input.value = v;
					this.input.focus();
				},
				save : function(o) {
					var edit = this.edits[o.parentNode.getAttribute('edit')];
					if (edit.rule.test(this.input.value)) {
						this.inputtd.innerHTML = this.input.value;
						this.inputtd = null;
					} else {
						alert(edit.message);
					}
				},
				sortTable : function(td, n, type) {
					var frag = document.createDocumentFragment(), str = td
							.getElementsByTagName('span')[0].innerHTML, span = td
							.getElementsByTagName('span')[0];
					if (this.row != null || td == this.sortCol) {
						this.rows.reverse();
						span.innerHTML = str.replace(/.$/, str
								.charAt(str.length - 1) == "↓" ? "↑" : "↓");
					} else {
						this.rows.sort(this.compare(n, type));
						span.innerHTML = span.innerHTML + "↑";
						this.sortCol != null
								&& (this.sortCol.getElementsByTagName('span')[0].innerHTML = this.sortCol
										.getElementsByTagName('span')[0].innerHTML
										.replace(/.$/, ''));
					}
					;
					for (var i = 0, l = this.rows.length; i < l; i++)
						frag.appendChild(this.rows[i]);
					this.table.tBodies[0].appendChild(frag);
					if (Sys.IE6) {
						for ( var s in this.checkbox)
							this.checkbox[s].checked = true;
					}
					this.sortCol = td;
				},
				compare : function(n, type) {
					return function(a1, a2) {
						var convert = {
							int : function(v) {
								return parseInt(v.replace(/,/g,''))
							},
							float : function(v) {
								return parseFloat(v)
							},
							date : function(v) {
								return v.toString()
							},
							string : function(v) {
								return v.toString()
							}
						};
						!convert[type] && (convert[type] = function(v) {
							return v.toString()
						});
						a1 = convert[type](a1.cells[n].innerText);
						a2 = convert[type](a2.cells[n].innerText);
						if (a1 == a2)
							return 0;
						return a1 < a2 ? -1 : 1;
					};
				}
			});

	var set = [];
	var obj = document.getElementById(table);
	var thead = obj.getElementsByTagName('thead')[0];
	var tds = thead.getElementsByTagName('td');
	var len = tds.length;
	for (var i = 0; i < len; i++) {
		var sort = tds[i].getAttribute('sort') || '';
		var tmp = {};
		if (sort != '') {
			// 去除空格
			sort = sort.replace(/(^\s*)|(\s*$)/g, "");
			tmp.id = i;
			tmp.type = sort;
			set.push(tmp);
		}
		;
	}
	new Table($(table), set);
};

/**
 * td内容过长处理
 * 
 * @param maxLength显示的最长的字符数
 */
// class="min-25"的
// function tdTextLongHandle(maxLength){
// var text;
// $(".min-25").each(function(){
// if($(this).children().length == 0){
// text = $(this).text().trim();
// if(text.length>maxLength){
// $("this,.min-25").addClass("min-30");
// // $(this).text(text.substring(0,maxLength)+"...");
// // $(this).prop("title",text);
// }
// }
// })
// }
// // class="min-20"的
// function tdTextLongHandl(maxLength){
// var text;
// $(".min-20").each(function(){
// if($(this).children().length == 0){
// text = $(this).text().trim();
// if(text.length>maxLength){
// $("this,.min-20").addClass("min-25");
// // $(this).text(text.substring(0,maxLength)+"...");
// // $(this).prop("title",text);
// }
// }
// })
// }
// // class="min-15"的
// function tdTextLongHand(maxLength){
// var text;
// $(".min-15").each(function(){
// if($(this).children().length == 0){
// text = $(this).text().trim();
// if(text.length>maxLength){
// $("this,.min-15").addClass("min-20");
// // $(this).text(text.substring(0,maxLength)+"...");
// // $(this).prop("title",text);
// }
// }
// })
// }
// $(document).ready(function(){
// tdTextLongHandle(23);
// tdTextLongHandl(14);
// tdTextLongHand(10);
// });
// 表格隔列文字标题居右
// $(document).ready(function() {
// $(".table6 tr td:even").addClass("td_right");
// })

$(function() {
	reloadTableEffect();
	autoSetTbodyHeight();
	loadThousandsSeparator();
});
/**
 * 重新加载表格效果
 */
function reloadTableEffect() {
	trClick2Checked();
	tableInterlaced();
	tdTextLongHandle();
}
/**
 * 表格隔行变色
 */
function tableInterlaced() {
	$(".scrollContent tr").mouseover(function() {
		$(this).addClass("t3");
	}).mouseout(function() {
		$(this).removeClass("t3");
	});
	$(".scrollContent tr:even").addClass("t2");
	$(".scrollContent tr:odd").removeClass("t2");
}
/**
 * 点击行选中复选框
 */
function trClick2Checked() {
	if ($("tbody").hasClass("limit-box")) {
	} else {
		$(".scrollContent tr").unbind("click.trClickCheckBox");
		$(".scrollContent tr").bind(
				"click.trClickCheckBox",
				function() {
					if ($(this).find("input[type='checkbox']").length > 0) {
						if ($(this).find("input[type='checkbox']").prop(
								"checked") == true) {
							$(this).find("input[type='checkbox']").prop(
									"checked", false);
							$(this).removeClass("bgss");
						} else {
							$(this).find("input[type='checkbox']").prop(
									"checked", true);
							$(this).addClass("bgss");
						}
					}
				});
		$(".scrollContent tr input[type='text']").unbind("click.trClickCheckBox");
	}
}
/**
 * 自动设置tbody的高度
 */
function autoSetTbodyHeight() {
	// 根据屏幕分辨率不同给tbody加上合适的高度
	if ($(".tbody").length == 0)
		return;
	var pageHeight = $(".tbody").offset().top;
	var total = document.documentElement.clientHeight;
	var colHeight = total - pageHeight - 30;
	$(".tbody").height(colHeight);
}
// 给id="tests"的tbody加上合适的高度使其出现纵向滚动条
total = document.documentElement.clientHeight;
colHeight = total - 90;
if ($("#tests").length > 0) {
	document.getElementById("tests").style.height = colHeight + "px";
}

// 表格中文居左,数字居右
;
(function($) {
	setInterval(function() {
		$('.scrollContent td').each(function() {
			if (!isNaN($.trim($(this).text()))) {
				$(this).css("text-align", "right");
			} else {
				$(this).css("text-align", "left");
			}
			;

			if (parseInt($(this).text()) > -1) {
				$(this).css("text-align", "right");
			} else {
				$(this).css("text-align", "left");
			}
			;

			if (/[\u4e00-\u9fa5]/g.test($(this).text())) {
				$(this).css("text-align", "left");
			}
		});
	}, 10);
}(jQuery));
/**
 * td内容过长处理
 * 
 * @param maxLength显示的最长的字节数
 */
function tdTextLongHandle() {
	var text;
	var subText;
	var textLength;
	var maxLength;
	$("td[maxlength]:not([title])").each(function() {
		maxLength = Number($(this).attr("maxlength"));
		if (isNaN(maxLength) || maxLength <= 0)
			return true;// 不是数字类型或不大于0的直接返回
		if ($(this).children().length == 0) {
			text = $(this).text();
			textLength = text.replace(/[^\x00-\xff]/g, "**").length;
			if (textLength > maxLength) {
				$(this).text(autoAddEllipsis(text, maxLength));
				$(this).prop("title", text);
				$(this).attr("data", text);
			}
		}
	})
}
/**
 * 处理过长的字符串，截取并添加省略号 注：半角长度为1，全角长度为2
 * 
 * pStr:字符串 pLen:截取长度
 * 
 * return: 截取后的字符串
 */
function autoAddEllipsis(pStr, pLen) {

	var _ret = cutString(pStr, pLen);
	var _cutFlag = _ret.cutflag;
	var _cutStringn = _ret.cutstring;

	if ("1" == _cutFlag) {
		return _cutStringn + "...";
	} else {
		return _cutStringn;
	}
}

/**
 * 取得指定长度的字符串 注：半角长度为1，全角长度为2
 * 
 * pStr:字符串 pLen:截取长度
 * 
 * return: 截取后的字符串
 */
function cutString(pStr, pLen) {
	// 原字符串长度
	var _strLen = pStr.length;
	var _tmpCode;
	var _cutString;
	// 默认情况下，返回的字符串是原字符串的一部分
	var _cutFlag = "1";
	var _lenCount = 0;
	var _ret = false;
	if (_strLen <= pLen / 2) {
		_cutString = pStr;
		_ret = true;
	}
	if (!_ret) {
		for (var i = 0; i < _strLen; i++) {
			if (isFull(pStr.charAt(i))) {
				_lenCount += 2;
			} else {
				_lenCount += 1;
			}
			if (_lenCount > pLen) {
				_cutString = pStr.substring(0, i);
				_ret = true;
				break;
			} else if (_lenCount == pLen) {
				_cutString = pStr.substring(0, i + 1);
				_ret = true;
				break;
			}
		}
	}
	if (!_ret) {
		_cutString = pStr;
		_ret = true;
	}
	if (_cutString.length == _strLen) {
		_cutFlag = "0";
	}
	return {
		"cutstring" : _cutString,
		"cutflag" : _cutFlag
	};
}

/**
 * 判断是否为全角
 * 
 * pChar:长度为1的字符串 return: true:全角 false:半角
 */
function isFull(pChar) {
	if ((pChar.charCodeAt(0) > 128)) {
		return true;
	} else {
		return false;
	}
}

$(document).ready(function(){
	$("input[comma='comma_0']").blur(function() {
	     $(this).val(parseFormatNum(parseFormatNums($(this).val(), 0), 0))
	})
	$("input[comma='comma_2']").blur(function() {
	     $(this).val(parseFormatNum(parseFormatNums($(this).val(), 2), 2))
	})
	$("input[comma='comma_4']").blur(function() {
	     $(this).val(parseFormatNum(parseFormatNums($(this).val(), 4), 4))
	})
	
});

/**
 * 按照千位分隔符加载数据。
 */
function loadThousandsSeparator() {
	$(".parseFormatNum_0").each(function() {
	 if($(this).text()==''){}else{
		$(this).text(parseFormatNum(parseFormatNums($(this).text(), 0), 0))
	 }
	});
	$(".parseFormatNum_2").each(function() {
	 if($(this).text()==''){}else{	
		$(this).text(parseFormatNum(parseFormatNums($(this).text(), 2), 2))
	 }	
	});
	$(".parseFormatNum_4").each(function() {
	 if($(this).text()==''){}else{	
		$(this).text(parseFormatNum(parseFormatNums($(this).text(), 4), 4))
	 }	
	});
	/*初始化表单加逗号*/
	$("input[comma='comma_0']").each(function() {
		if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 0), 0));
		}
	});
	$("input[comma='comma_2']").each(function() {
		if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 2), 2));
		}
	});
	$("input[comma='comma_4']").each(function() {
		if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 4), 4));
		}
	});
}

/**
 * 表单加逗号千位分隔符显示效果
 */
function loadThousanq(){
	$("input[comma='comma_0']").each(function() {
	 if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 0), 0));
	  }
	});
	$("input[comma='comma_2']").each(function() {
	 if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 2), 2));
	 }
	});
	$("input[comma='comma_4']").each(function() {
	 if($(this).val()==''){}else{
		$(this).val(parseFormatNum(parseFormatNums($(this).val(), 4), 4));
	 }
	});
}


/**
 * 删除表单千位分隔符显示效果。
 */
function removeps() {
	$("input[comma='comma_0']").each(function() {
		 if($(this).val()==''){}else{
		$(this).val(parseFormatNums($(this).val(), 0))
		 }
	});
	$("input[comma='comma_2']").each(function() {
		 if($(this).val()==''){}else{
		$(this).val(parseFormatNums($(this).val(), 2))
		 }
	});
	$("input[comma='comma_4']").each(function() {
		 if($(this).val()==''){}else{
		$(this).val(parseFormatNums($(this).val(), 4))
		 }
	});
}

/**
 * 删除表格千位分隔符显示效果。
 */
function removeparse() {
	$(".parseFormatNum_0").each(function() {
		 if($(this).text()==''){}else{
		$(this).text(parseFormatNums($(this).text(), 0))
		 }
	});
	$(".parseFormatNum_2").each(function() {
		 if($(this).text()==''){}else{	
			$(this).text(parseFormatNums($(this).text(), 2))
		}
	});
	$(".parseFormatNum_4").each(function() {
		 if($(this).text()==''){}else{
		$(this).text(parseFormatNums($(this).text(), 4))
		 }
	});
}

// 金额加逗号
function parseFormatNum(num, n) {
if(isNaN(num)){//js自己的方法检验数字
    return 0;
  }else{
    return num && num.toString().replace(/(\d)(?=(\d{3})+\.)/g, function($0, $1) {
            return $1 + ",";
        });
 }
}
// function parseFormatNum(number, n) {
// 	if (n != 0) {
// 		n = (n > 0 && n <= 20) ? n : 2;
// 	}
// 	number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
// 	var sub_val = number.split(".")[0].split("").reverse();
// 	var sub_xs = number.split(".")[1];
// 	var show_html = "";
// 	for (i = 0; i < sub_val.length; i++) {
// 		show_html += sub_val[i]
// 				+ ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");
// 	}
// 	if (n == 0) {
// 		return show_html.split("").reverse().join("");
// 	} else {
// 		return show_html.split("").reverse().join("") + "." + sub_xs;
// 	}
// }



// 金额去逗号
function parseFormatNums(number, n) {
	if (n != 0) {
		n = (n > 0 && n <= 20) ? n : 2;
	}
	number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var sub_val = number.split(".")[0].split("").reverse();
	var sub_xs = number.split(".")[1];
	var show_html = "";
	for (i = 0; i < sub_val.length; i++) {
		show_html += sub_val[i]
				+ ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "" : "");
	}
	if (n == 0) {
		return show_html.split("").reverse().join("");
	} else {
		return show_html.split("").reverse().join("") + "." + sub_xs;
	}
}
/**
 * 获取搜索条件的参数。
 * @param formid
 * @returns
 */
function getSearchParams(){
	var data = $('#pageForm').serializeArray();
	for(var i=0;i<arguments.length;i++){
		if(typeof(arguments[i]) != "string")continue;
		$("#"+arguments[i])[0].reset();
		data = $.merge(data,$("#"+arguments[i]).serializeArray());
    }
	return JSON.stringify(data);
}
/**
 * 获取搜索条件的参数。
 * @param formid
 * @returns
 */
function getSearchParamsUnReset(){
	var data = $('#pageForm').serializeArray();
	for(var i=0;i<arguments.length;i++){
		if(typeof(arguments[i]) != "string")continue;
		data = $.merge(data,$("#"+arguments[i]).serializeArray());
    }
	return JSON.stringify(data);
}
/**
 * 返回列表页
 */
function goBackList(url,data,method){
	if( url ){
		var form = $("<form></form>");
		form.appendTo("body");
		var inputs="";
		var dataObj = JSON.parse(data);
        jQuery.each(dataObj, function(index,obj){ 
            inputs+='<input type="hidden" name="'+ obj.name +'" value="'+ obj.value +'" />'; 
        });
        if(method==undefined)method = "GET";
        form.html(inputs);
        form.prop("method",method);
        form.prop("action",url);
        showLoading();
        form.submit();
    };
}
/**
 * 临时表单提交,编辑或新增时的页面跳转。
 * @param url
 * @param paramString
 * @param method
 */
function tempFormSubmit(url,paramString,method,backUrl){
	if( url ){
		if(method==undefined)method = "GET";
		var tempForm = document.createElement("form");    
	   tempForm.id="tempForm1";
	   tempForm.method=method;
	   tempForm.action=url;
	   if(paramString){
	       var hideInput = document.createElement("input");    
	       hideInput.type="hidden";    
	       hideInput.name= "searchParams";  
	       hideInput.value= paramString;  
	       tempForm.appendChild(hideInput);
	   };
	   if(backUrl){
	       var hideInput = document.createElement("input");    
	       hideInput.type="hidden";    
	       hideInput.name= "backUrl";  
	       hideInput.value= backUrl;  
	       tempForm.appendChild(hideInput);
	   };
	   document.body.appendChild(tempForm);
	   showLoading();
	   $(tempForm).submit();  
	   document.body.removeChild(tempForm);  
	};
};