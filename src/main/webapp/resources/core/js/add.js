



// 存储新增显示数据的函数  页面：storage.html
$(document).ready(function(){
		    // 新增
		    $('#addStorage').click(function(){
		     var storage_rkxdh = $('input[id="storage_rkxdh"]').val();
		     var storage_cgxdh = $('input[id="storage_cgxdh"]').val();
		     var storage_jglx = $('select[id="storage_jglx"]').val();
		     var storage_dw = $('input[id="storage_dw"]').val();
		     var storage_dds = $('input[id="storage_dds"]').val();
		     var storage_dhsl = $('input[id="storage_dhsl"]').val();
		     var storage_rksl = $('input[id="storage_rksl"]').val();
		     var storage_bhsdj = $('input[id="storage_bhsdj"]').val();
		     var storage_spsx = $('input[id="storage_spsx"]').val();
		     var storage_hsdj = $('input[id="storage_hsdj"]').val();

		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td></td><td>'+storage_rkxdh+'</td><td>'+storage_cgxdh+'</td><td></td><td></td><td>'+storage_jglx+'</td>';     
		      html += '<td>'+storage_dw+'</td><td>'+storage_dds+'</td><td>'+storage_dhsl+'</td><td>'+storage_rksl+'</td><td>'+storage_bhsdj+'</td>'; 
		      html += '<td>'+storage_hsdj+'</td><td></td><td></td><td></td><td></td><td>'+storage_spsx+'</td>';  
		      html += '<td></td><td></td></tr>';  
		      $('#Storage').append(html);
		      clearData('modal-4');
		    });

		    // 删除
		    $('#del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 


// 订单新增显示数据的函数  页面：order.html
$(document).ready(function(){
		    // 新增
		    $('#addOrder').click(function(){
		      var order_spbm = $('input[id="order_spbm"]').val();
		      var order_spmc = $('input[id="order_spmc"]').val();
		      var order_jldw = $('input[id="order_jldw"]').val();
		      var order_spsx = $('input[id="order_spsx"]').val();
		      var order_jglx = $('select[id="order_jglx"]').val();
		      var order_dhsl = $('input[id="order_dhsl"]').val();
		      var order_wsje = $('input[id="order_wsje"]').val();
		      var order_hsje = $('input[id="order_hsje"]').val();
		      var order_dqkc = $('input[id="order_dqkc"]').val();
		      var order_zdaqkc = $('input[id="order_zdaqkc"]').val();
		      var order_ypjxl = $('input[id="order_ypjxl"]').val();
		      var order_kxts = $('input[id="order_kxts"]').val();
		      var order_sl = $('input[id="order_sl"]').val();
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+order_spbm+'</td><td>'+order_spmc+'</td><td>'+order_jldw+'</td><td>'+order_spsx+'</td><td>'+order_jglx+'</td>';     
		      html += '<td>'+order_dhsl+'</td><td></td><td>'+order_sl+'</td><td>'+order_wsje+'</td><td>'+order_hsje+'</td>'; 
		      html += '<td>'+order_dqkc+'</td><td>'+order_zdaqkc+'</td><td>'+order_ypjxl+'</td><td>'+order_kxts+'</td><td></td></tr>';  
		      $('#order_one').append(html);
		      clearData('modal-1');
		    });

		    // 删除
		    $('#del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 


// 促销价格文件新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addCxjg').click(function(){
		      var cxjg_id = $('input[id="cxjg_id"]').val();
		      var cxjg_spbm = $('input[id="cxjg_spbm"]').val();
		      var cxjg_spmc = $('input[id="cxjg_spmc"]').val();
		      var cxjg_xmsl = $('input[id="cxjg_xmsl"]').val();
		      var cxjg_yssl = $('input[id="cxjg_yssl"]').val();
		      var cxjg_fmlftbl = $('input[id="cxjg_fmlftbl"]').val();
		      var cxjg_bc = $('input[id="cxjg_bc"]').val();
		      var cxjg_ft = $('input[id="cxjg_ft"]').val();
		      var cxjg_bz = $('input[id="cxjg_bz"]').val();
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+cxjg_id+'</td><td>'+cxjg_spbm+'</td><td>'+cxjg_spmc+'</td><td>'+cxjg_xmsl+'</td><td>'+cxjg_yssl+'</td><td>'+cxjg_fmlftbl+'</td>';     
		      html += '<td>'+cxjg_bc+'</td><td>'+cxjg_ft+'</td><td>'+cxjg_bz+'</td></tr>'; 
		      $('#cxjg').append(html);
		      clearData('modal-5');
		    });

		    // 删除
		    $('#del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 

// 通用政策1新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addTyzc1').click(function(){
		      var tyzc_id = $('input[id="tyzc_id"]').val();
		      var tyzc_spbm = $('input[id="tyzc_spbm"]').val();
		      var tyzc_ppbm = $('input[id="tyzc_ppbm"]').val();
		      var tyzc_type = $('input[id="tyzc_type"]:checked').val()||'';
		      var tyzc_kd = $('input[id="tyzc_kd"]').val();
		      var tyzc_tf = $('input[id="tyzc_tf"]').val();
		      var tyzc_sl = $('input[id="tyzc_sl"]').val();
		      var tyzc_yssl = $('input[id="tyzc_yssl"]').val();
		      var tyzc_sfyzhtdj = $('input[id="tyzc_sfyzhtdj"]').is(':checked')? '是': '否';
		      var tyzc_sfpcdp = $('input[id="tyzc_sfpcdp"]').is(':checked')? '是': '否';
		   
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+tyzc_id+'</td><td>'+tyzc_spbm+'</td><td>'+tyzc_ppbm+'</td><td>'+tyzc_type+'</td><td>'+tyzc_kd+'</td>';     
		      html += '<td>'+tyzc_tf+'</td><td>'+tyzc_sl+'</td><td>'+tyzc_yssl+'</td><td>'+tyzc_sfyzhtdj+'</td><td>'+tyzc_sfpcdp+'</td></tr>'; 
		      $('#Tyzc1').append(html);
		      clearData('modal-6');
		    });

		    // 删除
		    $('#Tyzc_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 
// 通用政策2新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addTyzc2').click(function(){
		      var Tyzc2_spbm = $('input[id="Tyzc2_spbm"]').val();
		      var Tyzc2_spmc = $('input[id="Tyzc2_spmc"]').val();
		      var Tyzc2_pp = $('input[id="Tyzc2_pp"]').val();
		      var Tyzc2_jglx = $('select[id="Tyzc2_jglx"]').val();
		   
		      var html = '';
		      html += '<tr><td class="min-3" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td class="min-12">'+Tyzc2_spbm+'</td><td class="min-25">'+Tyzc2_spmc+'</td><td class="min-8">'+Tyzc2_jglx+'</td><td class="min-10">'+Tyzc2_pp+'</td></tr>';     
		      $('#Tyzc2').append(html);
		      clearData('modal-7');
		    });

		    // 删除
		    $('#Tyzc2_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 


// 价区扣点1新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addJqkd').click(function(){
		      var jqkd_id = $('input[id="jqkd_id"]').val();
		      var jqkd_qsjg = $('input[id="jqkd_qsjg"]').val();
		      var jqkd_zdjg = $('input[id="jqkd_zdjg"]').val();
		      var jqkd_kd = $('input[id="jqkd_kd"]').val();
		      var jqkd_sfyzhtdj = $('input[id="jqkd_sfyzhtdj"]').is(':checked')? '是': '否';
		      var jqkd_sfpcdp = $('input[id="jqkd_sfpcdp"]').is(':checked')? '是': '否';
    
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+jqkd_id+'</td><td>'+jqkd_qsjg+'</td><td>'+jqkd_zdjg+'</td><td>'+jqkd_kd+'</td><td>'+jqkd_sfyzhtdj+'</td>';     
		      html += '<td>'+jqkd_sfpcdp+'</td></tr>';  
		      $('#Jqkd').append(html);
		      clearData('modal-8');
		    });

		    // 删除
		    $('#Jqkd_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 
// 价区扣点2新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addJqkd2').click(function(){
		      var Jqkd2_spbm = $('input[id="Jqkd2_spbm"]').val();
		      var Jqkd2_spmc = $('input[id="Jqkd2_spmc"]').val();
		      var Jqkd2_pp = $('input[id="Jqkd2_pp"]').val();
		      var Jqkd2_jglx = $('select[id="Jqkd2_jglx"]').val();
		   
		      var html = '';
		      html += '<tr><td class="min-3" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td class="min-12">'+Jqkd2_spbm+'</td><td class="min-25">'+Jqkd2_spmc+'</td><td class="min-8">'+Jqkd2_jglx+'</td><td class="min-12">'+Jqkd2_pp+'</td></tr>';     
		      $('#Jqkd2').append(html);
		      clearData('modal-7');
		    });

		    // 删除
		    $('#Jqkd2_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 
// 销售规模1新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addxsgm1').click(function(){
		      var xsgm_id = $('input[id="xsgm_id"]').val();
		      var xsgm_gmlx = $('select[id="xsgm_gmlx"]').val();
		      var xsgm_qsgm = $('input[id="xsgm_qsgm"]').val();
		      var xsgm_zdgm = $('input[id="xsgm_zdgm"]').val();
		      var xsgm_kd = $('input[id="xsgm_kd"]').val();
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+xsgm_id+'</td><td>'+xsgm_gmlx+'</td><td>'+xsgm_qsgm+'</td><td>'+xsgm_zdgm+'</td><td>'+xsgm_kd+'</td></tr>';     
		      $('#xsgm1').append(html);
		      clearData('modal-10');
		    });

		    // 删除
		    $('#xsgm_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 
// 销售规模2新增显示数据的函数  页面：promotion_agreement.html
$(document).ready(function(){
		    // 新增
		    $('#addxsgm2').click(function(){
		      var xsgm2_spbm = $('input[id="xsgm2_spbm"]').val();
		      var xsgm2_spmc = $('input[id="xsgm2_spmc"]').val();
		      var xsgm2_pp = $('input[id="xsgm2_pp"]').val();
		      var xsgm2_jglx = $('select[id="xsgm2_jglx"]').val();
		   
		      var html = '';
		      html += '<tr><td class="min-3" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td class="min-10">'+xsgm2_spbm+'</td><td class="min-25">'+xsgm2_spmc+'</td><td class="min-12">'+xsgm2_pp+'</td><td class="min-8">'+xsgm2_jglx+'</td></tr>';     
		      $('#xsgm2').append(html);
		      clearData('modal-11');
		    });

		    // 删除
		    $('#xsgm2_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 

// 采购价格文件新增显示数据的函数  页面：purchase_price_list.html
$(document).ready(function(){
		    // 新增
		    $('#addcgjgwj').click(function(){
		      var cgjgwj_sp = $('input[id="cgjgwj_sp"]').val();
		      var cgjgwj_xgj = $('input[id="cgjgwj_xgj"]').val();
		      var cgjgwj_pfj = $('input[id="cgjgwj_pfj"]').val();
		      var cgjgwj_jglx = $('select[id="cgjgwj_jglx"]').val();
		      var cgjgwj_wsj = $('input[id="cgjgwj_wsj"]').val();
		      var cgjgwj_lsdj = $('input[id="cgjgwj_lsdj"]').val();
		      var cgjgwj_lsxj = $('input[id="cgjgwj_lsxj"]').val();
		      var cgjgwj_bz = $('textarea[id="cgjgwj_bz"]').val();
		   
		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+cgjgwj_sp+'</td><td>'+cgjgwj_jglx+'</td><td>'+cgjgwj_xgj+'</td><td>'+cgjgwj_lsdj+'</td>'; 
		      html += '<td>'+cgjgwj_lsxj+'</td><td>'+cgjgwj_pfj+'</td><td>'+cgjgwj_wsj+'</td><td>'+cgjgwj_bz+'</td></tr>';     
		      $('#cgjgwj').append(html);
		      clearData('modal-1');
		    });

		    // 删除
		    $('#cgjgwj_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 


// 补充协议（打款、提货政策）新增商品显示数据的函数  页面：promotion_policy.html
$(document).ready(function(){
		    // 新增
		    $('#adddkth').click(function(){
		      var dkth_spbm = $('input[id="dkth_spbm"]').val();
		      var dkth_spmc = $('input[id="dkth_spmc"]').val();
		      var dkth_pp = $('input[id="dkth_pp"]').val();
		      var dkth_jglx = $('select[id="dkth_jglx"]').val();

		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+dkth_spbm+'</td><td>'+dkth_spmc+'</td><td>'+dkth_pp+'</td><td>'+dkth_jglx+'</td></tr>';    
		      $('#dkth').append(html);
		      clearData('modal-4');
		    });

		    // 删除
		    $('#dkth_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 		


// 补充协议（通补）新增商品显示数据的函数  页面：promotion_policy.html
$(document).ready(function(){
		    // 新增
		    $('#addtb').click(function(){
		      var tb_spbm = $('input[id="tb_spbm"]').val();
		      var tb_spmc = $('input[id="tb_spmc"]').val();
		      var tb_pp = $('input[id="tb_pp"]').val();
		      var tb_jglx = $('select[id="tb_jglx"]').val();

		      var html = '';
		      html += '<tr><td width="30" class="center"><label class="position-relative">';
		      html += '<input type="checkbox" class="ace" /><span class="lbl"></span> </label></td>';
		      html += '<td>'+tb_spbm+'</td><td>'+tb_spmc+'</td><td>'+tb_pp+'</td><td>'+tb_jglx+'</td></tr>';    
		      $('#tb').append(html);
		      clearData('modal-5');
		    });

		    // 删除
		    $('#dkth_del').click(function(){
		      $('.ace').each(function(){
		        if ($(this).prop('checked') == true) {
		          $(this).closest('tr').remove();
		        };
		      });
		    });
		  });

		 //清空弹出框的内容
		function clearData(win){
		   $("#"+win+" input[type='text']").each(function(){
		       $(this).val('');
		   });
		   $("#"+win+" input[type='checkbox']").each(function(){
		       $(this).attr("checked",false);
		   });
		} 	


//品牌月返扣点新增显示数据的函数
var count = 0;//用于计数ID ,保证唯一
//用于缓存界面中表格中的数据
var w4lcache={};
var w3lcache={};
var w2lcache={};
function ss(t){
	console.log(t);
}
//定义页面下方各个表格的ID
var window4lable = 'w4l';
var window3lable = 'w3l';
var window2lable = 'w2l';
//新增品牌月返扣点的"新增"点击响应
function addNewPinPaiYueFanKouDian(){
		var yfzc_id = $("#yfzc_id4").val();
		var spmc = $("#spmc").val();
		var spbm4 = $("#spbm4").val();
		var ykfd = $("#ykfd").val();//月返扣点
		var sfzb1 = $("#sfzb1").is(':checked')?'是':'否';
		var sfpcsp1 = $("#sfpcsp1").is(':checked')?'是':'否';
		var sfzbdata = $("#sfzb").is(':checked')?'1':'0';
		var zb = $("#zb").val()?$("#zb").val():0;
		var ftbl= $("#ftbl").val()?$("#ftbl").val():0;
		var obj = $("input[name='jglx']:checkbox:checked");
		var jglx=[];//价格类型
		for(var i=0;i<obj.length;i++){ 
			jglx[i] = obj[i].value;
		}
		var jglxdata = jglx.join();
		var myCount = count++;
		var ppyfdata = {
				'braprofitid':yfzc_id,
				'brandname':spmc,
				'brandid':spbm4,
				'monthreturn':ykfd,
				'ispercent':sfzbdata,
				'stockpercent':zb,
				'grossprofit':ftbl,
				'pricetype':jglxdata
		}
		w4lcache[myCount] = ppyfdata;
		var html =	'<tr id='+window4lable+myCount+'>'+
						'<td width=40 class=center><label class=position-relative>'+
							'<input id='+myCount+' type=checkbox class=ace />'+
							'<span class=lbl></span> </label></td>'+
						'<td id=yfzc_id_data>'+yfzc_id+'</td>'+
						'<td id=spmc_data>'+spmc+'</td>'+
						'<td id=ykfd_data>'+ykfd+'</td>'+
						'<td id=sfzb_data>'+sfzb1+'</td>'+
						'<td id=zb_data>'+zb+'%</td>'+
						'<td id=ftbl_data>'+ftbl+'%</td>'+
						'<td id=sfpcsp1_data>'+sfpcsp1+'</td>'+
						'<td hidden id=jglx_data>'+jglx+'</td>'+
					'</tr>'
		$("#ppyfbodyid").append(html);
		clearData(window4lable);
}	
//清空弹出框的内容
function clearData(win){
	 $("#"+win+" input[type='text']").each(function(){
		   $(this).val('');
	 });
	 $("#"+win+" input[type='checkbox']").each(function(){
		   $(this).attr("checked",false);
	 });
}
//季返,年返扣点的"新增"点击响应
function addNewJiFan(){
	var zk_id = $("#zk_id").val();
	var zklx = $("#zklx").val()?'年返':'季返';
	var zklxdata = $("#zklx").val();
	var gmjs = $("#gmjs").val()?'按台数算':'按金额算';
	var gmjsdata = $("#gmjs").val();
	var qsts = $("#qsts").val();
	var jzts = $("#jzts").val();
	var gmkd = $("#gmkd").val();
	var myCount = count++;
	var jfdata = {
			'scalepftid':zk_id,
			'saletype':zklx,
			'computetype':gmjsdata,
			'scalebase':qsts,
			'jzts':jzts,
			'gmkd':gmkd
	}
	w3lcache[myCount] = jfdata;
	var html =	'<tr id='+window3lable+myCount+'>'+
					'<td width=40 class=center><label class=position-relative>'+
						'<input id='+myCount+' type=checkbox class=ace />'+
						'<span class=lbl></span> </label>'+
					'</td>'+
					'<td id=zk_id>'+zk_id+'</td>'+
					'<td id=zklx>'+zklx+'</td>'+
					'<td id=gmjs>'+gmjs+'</td>'+
					'<td id=qsts>'+qsts+'</td>'+
					'<td id=jzts>'+jzts+'</td>'+
					'<td id=gmkd>'+gmkd+'</td>'+
				'</tr>';
	$("#jfnfbodyid").append(html);
	clearData(window3lable);


}	
//删除单条,多条数据的函数
function deleteData(win){
	
	var obj = $("input:checkbox:checked");
	for(var i=0;i<obj.length;i++){ 
		var id = obj[i].id;
		$("#"+win+id).remove();
		//同时删除缓存
		switch(win)
		{
			case 'w4l':
				w4lcache[id] = null;
			  	break;
			case 'w3l':
				w3lcache[id] = null;
			  	break;
			case 'w2l':
				w2lcache[id] = null;
				break;
			default:
			  break;
		}
	}
}
//月返扣点的"新增"按钮
function addNewYueFan(){
	var yfzc_id = $("#yfzc_id").val();
	var ykfd = $("#ykfd").val();
	var sfzb = $("#sfzb").is(':checked')?'是':'否';
	var sfpcsp = $("#sfpcsp").is(':checked')?'是':'否';
	var sfzbdata = $("#sfzb").is(':checked')?'1':'0';
	var zb = $("#zb").val()?$("#zb").val():0;
	var ftbl= $("#ftbl").val()?$("#ftbl").val():0;
	var obj = $("input[name='jglx']:checkbox:checked");
	var jglx=[];
	for(var i=0;i<obj.length;i++){ 
		jglx[i] = obj[i].value;
	}
	var jglxdata = jglx.join();
	var myCount = count++;
	var yfdata={
		'profitid':yfzc_id,
		'pricetype':jglxdata,
		'stockpercent':sfzbdata,
		'grossprofit':ftbl,
		'deductpoint':zb,
		'monthreturn':ykfd
	}
	w2lcache[myCount] = yfdata;
	var html =	'<tr id='+window2lable+myCount+'>'+
					'<td width=40 class=center><label class=position-relative>'+
						'<input id='+myCount+' type=checkbox class=ace />'+
						'<span class=lbl></span> </label></td>'+
					'<td id=yfzc_id_data>'+yfzc_id+'</td>'+
					'<td id=jglx_data>'+jglx+'</td>'+
					'<td id=ykfd_data>'+ykfd+'</td>'+
					'<td id=sfzb_data>'+sfzb+'</td>'+
					'<td id=zb_data>'+zb+'%</td>'+
					'<td id=ftbl_data>'+ftbl+'%</td>'+
					'<td id=sfpcsp_data>'+sfpcsp+'</td>'+
					'<td hidden id=jglx_data>'+jglx+'</td>'+
				'</tr>'
	$("#yfbodyid").append(html);
	clearData(window2lable);
}	

function save(){
	var jsondata={};

	spSuConDtlProfits = myJsonToArray(w2lcache);
	spSuConDtlScalepfts = myJsonToArray(w3lcache);
	spSuConDtlBraprofits = myJsonToArray(w4lcache);
	
	jsondata = {
		//spSuContract:spSuContract,					//SpSuContract
		spSuConDtlProfits:spSuConDtlProfits,		//List<SpSuConDtlProfit> 	//新增月返信息
		spSuConDtlScalepfts:spSuConDtlScalepfts,		//List<SpSuConDtlScalepft>	//新增季（年）度规模折扣信息界面
		spSuConDtlBraprofits:spSuConDtlBraprofits	//List<SpSuConDtlBraprofit> //新增品牌月返信息
	}
	var upData = JSON.stringify(jsondata);
	ss(upData );
	return;
	//test
	var saveDataAry=[];  
    var data1={"userName":"test","address":"gz"};  
    var data2={"userName":"ququ","address":"gr"};  
    saveDataAry.push(data1);  
    saveDataAry.push(data2);
	//Endtest
	
	$.ajax({ 
        type:"POST", 
        url:"mySave", 
        dataType:"json",      
        contentType:"application/json",               
        data:upData,
         
        success:function(data){ 
                                   
        } 
     }); 

}
//json转数组 ,用于上传
function myJsonToArray(jsonData){
	var returnData = new Array();
	for(var o in jsonData){  
		
		if(jsonData[o]!=null){
			returnData.push(jsonData[o]);
		}
	}  
	return returnData;
}