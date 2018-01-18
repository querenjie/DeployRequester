//品牌月返扣点新增显示数据的函数
var count = 0;
function ss(t){
  console.log(t);
}
//定义页面下方各个表格的ID
var window4lable = 'w4l';
var window3lable = 'w3l';
var window2lable = 'w2l';
//新增品牌月返扣点的"新增"点击响应
function addNewPinPaiYueFanKouDian(){
    var yfzc_id = $("#yfzc_id").val();
    var spmc = $("#spmc").val();
    var spbm4 = $("#spbm4").val();
    var ykfd = $("#ykfd").val();
    var sfzb = $("#sfzb").is(':checked')?'是':'否';
    var zb = $("#zb").val()?$("#zb").val():0;
    var ftbl= $("#ftbl").val()?$("#ftbl").val():0;
    var obj = $("input[name='jglx']:checkbox:checked");
    var jglx=[];
    for(var i=0;i<obj.length;i++){ 
      jglx[i] = obj[i].value;
    }
    var myCount = count++;
    var html =  '<tr id='+window4lable+myCount+'>'+
            '<td width=40 class=center><label class=position-relative>'+
              '<input id='+myCount+' type=checkbox class=ace />'+
              '<span class=lbl></span> </label></td>'+
            '<td id=yfzc_id_data>'+yfzc_id+'</td>'+
            '<td id=spmc_data>'+spmc+'</td>'+
            '<td id=ykfd_data>'+ykfd+'</td>'+
            '<td id=sfzb_data>'+sfzb+'</td>'+
            '<td id=zb_data>'+zb+'%</td>'+
            '<td id=ftbl_data>'+ftbl+'%</td>'+
            '<td id=pcsp_data>是</td>'+
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
  var zklx = $("#zklx").val();
  var gmjs = $("#gmjs").val();
  var qsts = $("#qsts").val();
  var jzts = $("#jzts").val();
  var gmkd = $("#gmkd").val();
  var kd = $("#kd").val()?$("#kd").val():0;
  var myCount = count++;
  var html =  '<tr id='+window3lable+myCount+'>'+
          '<td width=40 class=center><label class=position-relative>'+
            '<input id='+myCount+' type=checkbox class=ace />'+
            '<span class=lbl></span> </label>'+
          '</td>'+
          '<td id=zk_id>'+zk_id+'</td>'+
          '<td id=zklx>'+zklx+'</td>'+
          '<td id=kd>'+kd+'</td>'+
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
  }
}

function addNewYueFan(){
  var yfzc_id = $("#yfzc_id").val();
  var ykfd = $("#ykfd").val();
  var sfzb = $("#sfzb").is(':checked')?'是':'否';
  var zb = $("#zb").val()?$("#zb").val():0;
  var ftbl= $("#ftbl").val()?$("#ftbl").val():0;
  var obj = $("input[name='jglx']:checkbox:checked");
  var jglx=[];
  for(var i=0;i<obj.length;i++){ 
    jglx[i] = obj[i].value;
  }
  var myCount = count++;
  var html =  '<tr id='+window2lable+myCount+'>'+
          '<td width=40 class=center><label class=position-relative>'+
            '<input id='+myCount+' type=checkbox class=ace />'+
            '<span class=lbl></span> </label></td>'+
          '<td id=yfzc_id_data>'+yfzc_id+'</td>'+
          '<td id=jglx_data>'+jglx+'</td>'+
          '<td id=ykfd_data>'+ykfd+'</td>'+
          '<td id=sfzb_data>'+sfzb+'</td>'+
          '<td id=zb_data>'+zb+'%</td>'+
          '<td id=ftbl_data>'+ftbl+'%</td>'+
          '<td id=pcsp_data>是</td>'+
          '<td hidden id=jglx_data>'+jglx+'</td>'+
        '</tr>'
  $("#yfbodyid").append(html);
  clearData(window2lable);
} 
