<%--
  Created by IntelliJ IDEA.
  User: suneee
  Date: 2017/10/23
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>数据库连接配置页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<%=basePath%>resources/core/css/remodal.css">
    <link rel="stylesheet" href="<%=basePath%>resources/core/css/remodal-default-theme.css">
    <style>
        .remodal-bg.with-red-theme.remodal-is-opening,
        .remodal-bg.with-red-theme.remodal-is-opened {
            filter: none;
        }

        .remodal-overlay.with-red-theme {
            background-color: #f44336;
        }

        .remodal.with-red-theme {
            background: #fff;
        }

        .remodal-overlay.without-animation.remodal-is-opening,
        .remodal-overlay.without-animation.remodal-is-closing,
        .remodal.without-animation.remodal-is-opening,
        .remodal.without-animation.remodal-is-closing,
        .remodal-bg.without-animation.remodal-is-opening,
        .remodal-bg.without-animation.remodal-is-closing {
            animation: none;
        }

        .table-td-borderline table td{border:1px solid #F00}
        /* css注释：只对table td标签设置红色边框样式 */
    </style>
    <script src="<%=basePath%>resources/core/js/jquery.js"></script>
    <script src="<%=basePath%>resources/core/js/remodal/remodal.js"></script>
    <script src="<%=basePath%>resources/core/js/remodal/remodal_test.js"></script>
    <script type="text/javascript">
        var gCanDeployDbscript = "no";      //本页面的全局变量，用于表示是否能有权限发布数据库脚本。

        $(document).ready(function() {
            $("#btnSave").click(
                function() {
                    saveData();
                }
            );

            getProjects();
            setInterval("doRetrieveMsg()", 3000);
            query();
        });


        function doRetrieveMsg() {
            retrieveNotice();
            retrieveSpeech();
        }

        function retrieveNotice() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>noticeboard/getnotice",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#serverStatus").html("");
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var notice = resultData.data[0].notice;
                            var refreshPage = resultData.data[0].refreshPage;
                            var delaytimeBeforeRefreshPage = resultData.data[0].delaytimeBeforeRefreshPage;
                            var describe = resultData.data[0].describe;
                            if (refreshPage == 0 && notice != "") {
                                $("#noticeContent").html(notice);
                                $("#promptDescription").html("");
                                $("#callModal").click();
                            } else if (refreshPage == 1 && (notice != "" || describe != "")) {
                                $("#noticeContent").html(notice);
                                $("#promptDescription").html(describe);
                                $("#callModal").click();
                                setTimeout("reloadPage()", delaytimeBeforeRefreshPage);
                            }
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function retrieveSpeech() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>noticeboard/getSpeech",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#serverStatus").html("");
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var speech = resultData.data[0];
                            var msg = "";
                            msg += speech.speaker + "在" + speech.speakTime + "给你消息:" + "\n";
                            msg += speech.speakContent;
                            alert(msg);
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getProjects() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getProjects",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#projectcode").empty();
                    $("#projectcode").append("<option value=\"\"></option>")
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#projectcode").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].projectName + "</option>");
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function initModules() {
            var projectId = $("#projectcode").val();
            if (projectId != "") {
                getModulesByProjectId(projectId);
            }
        }

        function getModulesByProjectId(projectId) {
            var moduleDTO = {};
            moduleDTO.projectId = projectId;
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getModulesByProjectId",
                async: false,
                data:JSON.stringify(moduleDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#modulecode").empty();
                    $("#modulecode").append("<option value=\"\"></option>")
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#modulecode").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].code + "</option>");
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function saveData() {
            var belong = $("#belong").val();
            var projectid = $("#projectcode").val();
            var moduleid = $("#modulecode").val();
            var linkname = $("#linkname").val();
            var linknamedesc = $("#linknamedesc").val();
            var ip = $("#ip").val();
            var port = $("#port").val();
            var username = $("#username").val();
            var password = $("#password").val();
            var needrecpwd = $('input[name="needrecpwd"]:checked').val();
            var dbname = $("#dbname").val();
            var issyncdb = $("#issyncdb").val();

            var errorMsg = "";
            if ($.trim(belong) == "") {
                errorMsg += "必须选择数据库环境！" + "\n";
            }
            if ($.trim(projectid) == "") {
                errorMsg += "必须选择项目！" + "\n";
            }
            if ($.trim(moduleid) == "") {
                errorMsg += "必须选择模块！" + "\n";
            }
            if ($.trim(linkname) == "") {
                errorMsg += "数据库链接名不能为空！" + "\n";
            }
            if ($.trim(ip) == "") {
                errorMsg += "数据库服务器的ip不能为空！" + "\n";
            } else if (!checkIp(ip)) {
                errorMsg += "数据库服务器的ip必须要符合ip4的规则！" + "\n";
            }
            if ($.trim(port) == "") {
                errorMsg += "端口不能为空！" + "\n";
            } else if (!checkPort(port)) {
                errorMsg += "端口必须遵循计算机端口的格式和范围（0到2^16）！" + "\n";
            }
            if ($.trim(username) == "") {
                errorMsg += "数据库用户名不能为空！" + "\n";
            }
            if ($.trim(password) == "") {
                errorMsg += "密码不能为空！" + "\n";
            }
            if ($.trim(dbname) == "") {
                errorMsg += "数据库名称不能为空！" + "\n";
            }
            if (errorMsg != "") {
                alert(errorMsg);
                return;
            }

            var deployDbserversDTO = {};
            deployDbserversDTO.belong = belong;
            deployDbserversDTO.projectid = projectid;
            deployDbserversDTO.moduleid = moduleid;
            deployDbserversDTO.linkname = linkname;
            deployDbserversDTO.linknamedesc = linknamedesc;
            deployDbserversDTO.ip = ip;
            deployDbserversDTO.port = port;
            deployDbserversDTO.username = username;
            deployDbserversDTO.password = password;
            deployDbserversDTO.needrecpwd = needrecpwd;
            deployDbserversDTO.dbname = dbname;
            deployDbserversDTO.issyncdb = issyncdb;

            $("#btnSave").attr("disabled", true);
            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbservers/saveDbservers",
                async: false,       //false:同步
                data:JSON.stringify(deployDbserversDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            if (resultData.msg == "failed") {
                                alert(resultData.data[0]);
                                $("#btnSave").removeAttr("disabled");
                                return;
                            }
                            if (resultData.data[0] == 1) {
                                queryAll();
//                                alert("保存成功!");
                            } else {
                                alert(resultData.data[0]);
                            }
                            $("#btnSave").removeAttr("disabled");
                        }
                    }
                },
                error:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            alert(resultData.data[0]);
                            $("#btnSave").removeAttr("disabled");
                            return;
                        }
                    }
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                    $("#btnSave").removeAttr("disabled");
                }
            });

        }

        function checkIp(ip) {
            var regexp = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
            var valid = regexp.test(ip);
            if (!valid) {//首先必须是 xxx.xxx.xxx.xxx 类型的数字，如果不是，返回false
                return false;
            }
            var arrIp = ip.split('.');
            for (var i = 0; i < arrIp.length; i++) {
                if (arrIp[i].length > 1 && arrIp[i].charAt(0) === '0') {
                    return false;
                } else if (parseInt(arrIp[i]) > 255) {
                    return false;
                }
            }
            return true;
        }

        function checkPort(port) {
            var regexp = /^\d{1,5}$/;
            var valid = regexp.test(port);
            if (!valid) {
                return false;
            }
            if (port.length > 1 && port.charAt(0) === '0') {
                return false;
            } else if (parseInt(port) > 256 * 256) {
                return false;
            }
            return true;
        }

        function query() {
            var belong = $("#belong").val();
            var projectid = $("#projectcode").val();
            var moduleid = $("#modulecode").val();
            var ip = $("#ip").val();
            var port = $("#port").val();
            var username = $("#username").val();
            var dbname = $("#dbname").val();
            var linkname = $("#linkname").val();
            var linknamedesc = $("#linknamedesc").val();

            var deployDbserversDTO = {};
            deployDbserversDTO.belong = belong;
            deployDbserversDTO.projectid = projectid;
            deployDbserversDTO.moduleid = moduleid;
            deployDbserversDTO.ip = ip;
            deployDbserversDTO.port = port;
            deployDbserversDTO.username = username;
            deployDbserversDTO.dbname = dbname;
            deployDbserversDTO.linkname = linkname;
            deployDbserversDTO.linknamedesc = linknamedesc;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbservers/query",
                async: false,       //false:同步
                data:JSON.stringify(deployDbserversDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployDbserversList = resultData.data[0];
                            var tableHtml = "";
                            $("#processAction").html("查询结果如下(黄色底纹的记录表示为同步库)");
                            if (deployDbserversList != null && deployDbserversList.length > 0) {
                                tableHtml += "<tr bgcolor='#ff8c00'>";
                                tableHtml += "<td align='center'>环境</td>";
                                tableHtml += "<td align='center'>项目</td>";
                                tableHtml += "<td align='center'>模块</td>";
                                tableHtml += "<td align='center'>数据库链接名</td>";
                                tableHtml += "<td align='center'>ip</td>";
                                tableHtml += "<td align='center'>端口</td>";
                                tableHtml += "<td align='center'>用户名</td>";
                                tableHtml += "<td align='center'>数据库名称</td>";
                                tableHtml += "<td align='center'>操作</td>";
                                tableHtml += "</tr>";
                                $.each(deployDbserversList, function(index) {
                                    var deployDbservers = deployDbserversList[index];
                                    var bgColor = "";
                                    if (deployDbservers.issyncdb == 1) {
                                        bgColor = "yellow";
                                    } else {
                                        bgColor = "";
                                    }
                                    tableHtml += "<tr bgcolor='" + bgColor + "'>";
                                    tableHtml += "<td>" + deployDbservers.belongName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.projectName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.moduleName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.linkname + "</td>";
                                    tableHtml += "<td>" + deployDbservers.ip + "</td>";
                                    tableHtml += "<td>" + deployDbservers.port + "</td>";
                                    tableHtml += "<td>" + deployDbservers.username + "</td>";
                                    tableHtml += "<td>" + deployDbservers.dbname + "</td>";
                                    tableHtml += "<td>";
                                    tableHtml += "<input type='button' value='删除' onclick=\"deleteById('" + deployDbservers.deploydbserversid + "');\">";
                                    tableHtml += "<input type='button' value='编辑' onclick=\"getById('" + deployDbservers.deploydbserversid + "');\">";
                                    tableHtml += "</td>";
                                    tableHtml += "</tr>";
                                });
                            } else {
                                tableHtml = "";
                            }
                            $("#tblResult").html(tableHtml);
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function deleteById(id) {
            var deployDbserversDTO = {};
            deployDbserversDTO.deploydbserversid = id;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbservers/deleteById",
                async: false,       //false:同步
                data:JSON.stringify(deployDbserversDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            if (resultData.msg == "failed") {
                                alert(resultData.data[0]);
                                return;
                            }
                            query();
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getById(id) {
            var deployDbserversDTO = {};
            deployDbserversDTO.deploydbserversid = id;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbservers/getById",
                async: false,       //false:同步
                data:JSON.stringify(deployDbserversDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployDbserver = resultData.data[0];
                            if (deployDbserver != null) {
                                $("#belong").val(deployDbserver.belong);
                                $("#projectcode").val(deployDbserver.projectid);
                                $("#modulecode").val(deployDbserver.moduleid);
                                $("#linkname").val(deployDbserver.linkname);
                                $("#linknamedesc").val(deployDbserver.linknamedesc);
                                $("#ip").val(deployDbserver.ip);
                                $("#port").val(deployDbserver.port);
                                $("#username").val(deployDbserver.username);
                                $("#password").val(deployDbserver.password);
                                $("#dbname").val(deployDbserver.dbname);
                                $("#issyncdb").val(deployDbserver.issyncdb);
                            }
                        } else {
                            alert("找不到记录!");
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function resetQueryConditions() {
            $("#formEdit")[0].reset();
            $("#linknamedesc").val("");
        }

        function judgeCanDeployDbscript() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeCanDeployDbscript",
                async: false,       //false:同步
                data:JSON.stringify(deployDbserversDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var resultStr = resultData.data[0];
                            if (resultStr == "ok") {
                                gCanDeployDbscript = "yes";
                            } else {
                                gCanDeployDbscript = "no";
                            }
                        }
                    }
                    if (gCanDeployDbscript == "yes") {
                        $("#btnSave").removeAttr("disabled");
                        $("[value='删除']").each(function() {
                            $(this).removeAttr("disabled");
                        });
                        $("[value='编辑']").each(function() {
                            $(this).removeAttr("disabled");
                        });
                    } else {
                        $("#btnSave").attr("disable", true);
                        $("[value='删除']").each(function() {
                            $(this).attr("disabled", true);
                        });
                        $("[value='编辑']").each(function() {
                            $(this).attr("disabled", true);
                        });
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function queryAll() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbservers/queryAll",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployDbserversList = resultData.data[0];
                            var tableHtml = "";
                            if (deployDbserversList != null && deployDbserversList.length > 0) {
                                $("#processAction").html("该记录已经保存到系统中(黄色底纹的记录表示为同步库)");
                                tableHtml += "<tr bgcolor='#ff8c00'>";
                                tableHtml += "<td align='center'>环境</td>";
                                tableHtml += "<td align='center'>项目</td>";
                                tableHtml += "<td align='center'>模块</td>";
                                tableHtml += "<td align='center'>数据库链接名</td>";
                                tableHtml += "<td align='center'>ip</td>";
                                tableHtml += "<td align='center'>端口</td>";
                                tableHtml += "<td align='center'>用户名</td>";
                                tableHtml += "<td align='center'>数据库名称</td>";
                                tableHtml += "<td align='center'>操作</td>";
                                tableHtml += "</tr>";
                                $.each(deployDbserversList, function(index) {
                                    var deployDbservers = deployDbserversList[index];
                                    var bgColor = "";
                                    if (deployDbservers.issyncdb == 1) {
                                        bgColor = "yellow";
                                    } else {
                                        bgColor = "";
                                    }
                                    tableHtml += "<tr bgcolor='" + bgColor + "'>";
                                    tableHtml += "<td>" + deployDbservers.belongName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.projectName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.moduleName + "</td>";
                                    tableHtml += "<td>" + deployDbservers.linkname + "</td>";
                                    tableHtml += "<td>" + deployDbservers.ip + "</td>";
                                    tableHtml += "<td>" + deployDbservers.port + "</td>";
                                    tableHtml += "<td>" + deployDbservers.username + "</td>";
                                    tableHtml += "<td>" + deployDbservers.dbname + "</td>";
                                    tableHtml += "<td>";
                                    tableHtml += "<input type='button' value='删除' onclick=\"deleteById('" + deployDbservers.deploydbserversid + "');\">";
                                    tableHtml += "<input type='button' value='编辑' onclick=\"getById('" + deployDbservers.deploydbserversid + "');\">";
                                    tableHtml += "</td>";
                                    tableHtml += "</tr>";
                                });
                            } else {
                                tableHtml = "";
                            }
                            $("#tblResult").html(tableHtml);
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

    </script>
</head>
<body>
<font color="blue">建议使用chrome浏览器，在其他浏览器上运行有可能不正常。(chrome is a strong recommendation, others may cause malfunction.)</font>
<div class="remodal-bg">
    <a id="callModal" href="#" data-remodal-target="modal5" style="visibility: hidden;">Call</a>
</div>


<div class="remodal" data-remodal-id="modal5">
    <div><font id="noticeContent" size="5" color="blue"></font></div>
    <div><font id="promptDescription" size="8" color="red"></font></div>
    <a id="closemodal" data-remodal-action="close" class="remodal-close"></a>
</div>

<div id="target"></div>

<div><font color="red" id="serverStatus" size="5"></font> </div>
<table align="center" width="50%">
    <tr><td align="center"><font size="5">数据库连接配置</font></td></tr>
</table>
<form id="formEdit" name="formEdit">
    <table id="tblEdit" align="center" width="50%" border="1">
        <tr>
            <td width="25%"><font color="red">数据库环境：</font></td>
            <td>
                <select id="belong">
                    <option value=""></option>
                    <option value="1">预发环境</option>
                    <option value="2">生产环境</option>
                </select>
            </td>
            <td>
                <font color="red">项目名称：</font>
                <select id="projectcode" onchange="initModules();">
                </select>
            </td>
            <td>
                <font color="red">模块：</font>
                <select id="modulecode">
                </select>
            </td>
        </tr>
        <tr>
            <td><font color="red">数据库链接名：</font></td>
            <td colspan="3"><input type="text" id="linkname" size="50"></td>
        </tr>
        <tr>
            <td>数据库链接描述：</td>
            <td colspan="3"><textarea id="linknamedesc" cols="80" rows="10"></textarea></td>
        </tr>
        <tr>
            <td><font color="red">数据库服务器的ip：</font></td>
            <td colspan="3"><input type="text" id="ip" maxlength="15"></td>
        </tr>
        <tr>
            <td><font color="red">端口：</font> </td>
            <td colspan="3"><input type="text" id="port" maxlength="5"></td>
        </tr>
        <tr>
            <td><font color="red">数据库用户名：</font> </td>
            <td colspan="3"><input type="text" id="username" maxlength="100"></td>
        </tr>
        <tr>
            <td><font color="red">密码：</font></td>
            <td colspan="3"><input type="password" id="password" maxlength="100"></td>
        </tr>
        <tr>
            <td><font color="red">是否需要记住密码：</font> </td>
            <td colspan="3">
                <label>
                    <!--input type="radio" name="needrecpwd" value="0">不记密码-->
                    <input type="radio" name="needrecpwd" value="1" checked>让系统记住密码
                </label>
            </td>
        </tr>
        <tr>
            <td><font color="red">数据库名称：</font></td>
            <td colspan="3"><input type="dbname" id="dbname" maxlength="100" size="40">(这是大小写敏感的)</td>
        </tr>
        <tr>
            <td><font color="red">是否为同步库：</font></td>
            <td colspan="3">
                <select id="issyncdb">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
                <br>一般情况下只有报表库才是同步库。针对各个业务库中的表结构变更要同步到同步库。
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <input type="button" value="保存(新增/修改)" id="btnSave">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="清空查询条件" onclick="resetQueryConditions();">
                <input type="button" value="查询" id="btnQuery" onclick="query();">
            </td>
        </tr>
    </table>
</form>
<br>
<table align="center" width="80%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
    <tr><td align="center" bgcolor="#9acd32"><font id="processAction">提示：请选择条件进行查询</font></td></tr>
    <tr>
        <td>
            <table id="tblResult" width="100%" border="1" style="border-collapse:collapse;">

            </table>
        </td>
    </tr>
</table>

</body>
</html>
