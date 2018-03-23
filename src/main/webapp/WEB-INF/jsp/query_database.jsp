<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: suneee
  Date: 2018/3/16
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>查询数据表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<%=basePath%>resources/core/css/remodal.css">
    <link rel="stylesheet" href="<%=basePath%>resources/core/css/remodal-default-theme.css">
    <link rel="stylesheet" href="<%=basePath%>resources/core/css/BeatPicker.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>resources/core/js/zTree35/zTreeStyle/zTreeStyle.css" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>resources/core/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>resources/core/js/zTree35/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="<%=basePath%>resources/core/js/zTree35/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .tableborder {
            border-top-width: 1px;
            border-right-width: 1px;
            border-bottom-width: 1px;
            border-left-width: 1px;
            /*以上分别设置的是表格边框中上右下左的边框宽度*/
            border-top-style: solid;
            border-right-style: solid;
            border-bottom-style: solid;
            border-left-style: solid;
            /*设置边框的表现样式，solid为实线*/
            border-top-color: #000000;
            border-right-color: #000000;
            border-bottom-color: #000000;
            border-left-color: #000000;
            /*设置边框的颜色*/
        }
    </style>
    <script type="text/javascript">
        var DBServerTypeValue="";
        var oldNodes = [];
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false,
                txtSelectedEnable: true,
                showIcon: true,
                fontCss: function (treeId, treeNode) {
                    return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
                        color: "#000000", "font-weight": "normal"
                    };
                }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                onClick: onClick,
                beforeAsync: beforeAsync,
                onCollapse: onCollapse,
                onExpand: onExpand,
                onAsyncSuccess: treeAsyncSuccess
            },
            check: {// 设置 zTree 的节点上是否显示 checkbox / radio ,默认为false
                enable: false
            }
        };

        function treeAsyncSuccess(event, treeId, treeNode, msg) {
        }


        function onCollapse(event, treeId, treeNode) { //树节点折叠事件
            $("#columnTable").empty();
        }

        function onExpand(event, treeId, treeNode) { //树节点展开事件
        }

        function onClick(event, treeId, treeNode, clickFlag) { //树节点点击事件
            if(treeNode.port !=null){
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>queryDatabase/queryColumn",
                    data:{
                        id: treeNode.id,    //表名id
                        dataBaseName:treeNode.dataBaseName,   //数据库名
                        tableName:treeNode.name, //表名
                        ip:treeNode.ip,//服务器ip
                        port:treeNode.port,//服务器端口号
                        userName:treeNode.userName,//服务器用户名
                        password:treeNode.password,//服务器密码
                    },
                    datatype:"json", //此处不能省略
                    success:function(resultData){
                        if(resultData.data !=null){
                            $("#columnTable").empty();
                            var resultColumnData =resultData.data;
                            var htmlText="<table  height='300' width='100%' border='1'  cellpadding='1' cellspacing='1'><tr  align='center' border='1' ><td colspan ='5' style='font-weight:bold'>"+
                                "表名："+treeNode.name+"</td>"+  "</tr><tr>"+
                                "<td id='serial_number' width='5%' align='center' style='font-weight:bold'>序号</td>" +
                                "<td id='field_name' width='30%' align='center' style='font-weight:bold'>字段名</td>" +
                                "<td id='type_size' width='30%' align='center' style='font-weight:bold'>类型/大小</td>" +
                                "<td id='remarks' width='25%' align='center' style='font-weight:bold'>备注</td>" +
                                "<td id='primary' width='10%' align='center' style='font-weight:bold'>是否主键</td>"+
                                "</tr>";

                            for (var i = 0;i < resultColumnData.length ; i ++){
                                var columnBean = resultColumnData[i];
                                var columnName="";
                                var columnType="";
                                var comment="";
                                var primaryKey="";
                                for(var key in columnBean){
                                    if(key == "columnName"){
                                        columnName = columnBean[key];
                                        if(columnName ==null){
                                            columnName ="";
                                        }
                                    }
                                    if(key == "columnType"){
                                        columnType = columnBean[key];
                                        if(columnType ==null){
                                            columnType ="";
                                        }
                                    }
                                    if(key == "comment"){
                                        comment = columnBean[key];
                                        if(comment ==null){
                                            comment ="";
                                        }
                                    }
                                    if(key == "primaryKey"){
                                        primaryKey = columnBean[key];
                                        if(primaryKey ==columnName){
                                            primaryKey ="是";
                                        }else {
                                            primaryKey ="否";
                                        }
                                    }
                                }
                                htmlText += "<tr align='center'>" +
                                    "<td align='center'>"+(i+1)+"</td>"+
                                    "<td align='center'>"+columnName+"</td>"+
                                    "<td align='center'>"+columnType+"</td>"+
                                    "<td align='center'>"+comment+"</td>"+
                                    "<td align='center'>"+primaryKey+"</td>"+
                                    "</tr>"
                            }
                            htmlText += "</table>";
                            $("#columnTable").append(htmlText);
                        }
                    },
                    error:function(resultData){
                        $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                    }
                });
            }

        }

        function beforeAsync(treeId, treeNode) {
            return treeNode ? treeNode.level < 5 : true;
        }
        /**
         * 加载树
         */
        function initTree() {
            $.ajax({
                url: "<%=basePath%>queryDatabase/initTreeNode",
                type: "POST",
                data:{
                    typeCode:DBServerTypeValue
                },
                dataType: "json",
                success: function (data) {

                    var zNodes = data.data;
                    if (data.data == "[]") {
                        zNodes = null;
                        return;
                    }
                    var resultId = "";
                    for(var i = 0;i < zNodes.length ; i ++){
                        if(zNodes[i].pId == 0){
                            resultId =  zNodes[i].id;
                            break;
                        }
                    }
                    var t = $("#tree");
                    $.fn.zTree.init(t, setting, zNodes);
                    var zTree = $.fn.zTree.getZTreeObj("tree");
                    var node = zTree.getNodeByParam('id', resultId);//获取id为1的点
                    zTree.setting.callback.onClick(null, zTree.setting.treeId, node);//调用
                    $("#fontTableCode").hide();
                    $("#searchTabletypeCode").show();
                    $("#SearchTable").show();
                    $("#treeDiv").show();
                    $("#columnTableDiv").show();
                    $("#searchTableName").show();
                    $("#tree").show();
                    $("#columnTable").show();
                    $("#columnTableTR").show();
                    //("#DBServerTypeCode").show();
                    //$("#DBServerTypeCodeTR").show();

                }
            });
        }

        $(document).ready(function(){
            setTimeout(initLoadTree(),2000);
            $("#fontTableCode").hide();
        });
        function initLoadTree(){
            debugger;
            codeid = "1";
            codename = "";
            codeendflag = "";
            codelevel = "0";
            //initTree();
            $("#searchTabletypeCode").hide();
            $("#SearchTable").hide();
            $("#treeDiv").hide();
            $("#columnTableDiv").hide();
            $("#searchTableName").hide();
            $("#tree").hide();
            $("#columnTable").hide();
            $("#columnTableTR").hide();
            //$("#DBServerTypeCode").hide();
            //$("#DBServerTypeCodeTR").hide();
        }
        function searchTable() {
            $("#columnTable").empty();
            var searchValue = $("#searchTabletypeCode").val();
                searchNode (searchValue);
        }
        function searchNode (text) {
            if (text == "") {
                return;
            }
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            updateNodes(false);
            oldNodes = treeObj.getNodesByParamFuzzy("name", text, null);
            updateNodes(true);
        }
        function updateNodes (flag) {
            //遍历搜索高亮显示
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            for (var i = 0, l = oldNodes.length; i < l; i++) {
                oldNodes[i].highlight = flag;
                treeObj.updateNode(oldNodes[i]);
                treeObj.expandNode(oldNodes[i].getParentNode(), flag, null, null, false);
            }
        }

        function queryDatabaseType(){
           DBServerTypeValue= $("#DBServerTypeCode").val();
            if(DBServerTypeValue !=""){
                //$("#fontTableCode").hide();
                initTree();
                $("#fontTableCode").show();
                $("#searchTabletypeCode").val("");
                $("#columnTable").empty();
            }
            $("#columnTableTR").hide();
            $("#searchTabletypeCode").hide();
            $("#SearchTable").hide();
            $("#searchTableName").hide();
        }
    </script>
</head>
<body>
<div style='text-align:center'><font color="blue" >建议使用chrome浏览器，在其他浏览器上运行有可能不正常。(chrome is a strong recommendation, others may cause malfunction.)</font></div>

<table align="center" width="80%"  border="1" bordercolor="#a0c6e5">

    <tr>
        <td align="center"  bgcolor="#5f9ea0" colspan="2">条件区域</td>
    </tr>
    <tr>
        <td colspan="1" id="DBServerTypeCodeTR">请选择服务器类型:
            <select id="DBServerTypeCode" onchange="queryDatabaseType()">
                <option  value="">--请选择--</option>
                <option  value="1">预发布服务器</option>
                <option  value="2">生产服务器</option>
            </select>
        </td>
        <td  colspan="1"><span id="searchTableName">请输入表名：</span>
            <input id="searchTabletypeCode"></input>
            <input type="button" id="SearchTable" value="查询" onclick =searchTable()></input>
            <div id="fontTableCode" align="center"><font color="#ed2b4c" size="10px">正在加载中,大约30s,请稍候...</font></div>
        </td>
    </tr>
    <tr id="columnTableTR">
        <div id="treeDiv">
            <td width="30%" valign='top'>
                <ul id="tree" class="ztree"></ul>
            </td>
        </div>
        <div id="columnTableDiv">
            <td width="70%" id="columnTable"  valign='top'></td>

        </div>
    <tr>

</table>
</body>
</html>
