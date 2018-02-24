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
    <title>数据库脚本申请页面</title>
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
        $(document).ready(function() {
            getProjects();
            getCrewName();
            setInterval("doRetrieveMsg()", 3000);
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

        /**
        * 获取员工姓名
        */
        function getCrewName() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/obtainCrewName",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#applier").val(resultData.data[0]);
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
            var projectId = $("#projectcode").val();
            var moduleId = $("#modulecode").val();
            var dbscript = $("#dbscript").val();
            var description = $("#description").val();
            var applier = $("#applier").val();
            var forcetodoit = $("#forcetodoit").val();
            var canexecute = $("#canexecute").val();

            var errorMsg = "";
            if ($.trim(belong) == "") {
                errorMsg += "必须选择目标数据库环境！" + "\n";
            }
            if ($.trim(projectId) == "") {
                errorMsg += "必须选择项目名称！" + "\n";
            }
            if ($.trim(moduleId) == "") {
                errorMsg += "必须选择模块！" + "\n";
            }
            if ($.trim(dbscript) == "") {
                errorMsg += "脚本语句不能为空！" + "\n";
            }
            if ($.trim(applier) == "") {
                errorMsg += "申请者不能为空！如果为空说明尚未被授权使用" + "\n";
            }
            if (errorMsg != "") {
                alert(errorMsg);
                return;
            }

            var DeployDbscriptDTO = {};
            DeployDbscriptDTO.belong = belong;
            DeployDbscriptDTO.projectid = projectId;
            DeployDbscriptDTO.moduleid = moduleId;
            DeployDbscriptDTO.dbscript = dbscript;
            DeployDbscriptDTO.description = description;
            DeployDbscriptDTO.applier = applier;
            DeployDbscriptDTO.forcetodoit = forcetodoit;
            DeployDbscriptDTO.canexecute = canexecute;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/saveDBScript",
                async: false,       //false:同步
                data:JSON.stringify(DeployDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            if (resultData.msg == "failed") {
                                alert(resultData.data[0]);
                                return;
                            }
                            if (resultData.data[0] == 1) {
                                alert("新增记录成功!");
                                $("#forcetodoit").val("no");
                                $("#tr_forcesubmit").hide();
                            } else {
                                alert(resultData.data[0]);
                                if (resultData.msg == "dangerous statement in it") {
                                    $("#tr_forcesubmit").show();
                                }
                            }
                        }
                    }
                },
                error:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            alert(resultData.data[0]);
                            return;
                        }
                    }
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function openQueryPage() {
            window.open("<%=basePath%>depdbscript/deploy_dbscript_querylist", "_blank");
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
<table align="center" width="60%">
    <tr><td align="center"><font size="5">postgres数据库脚本上线申请</font><a href="<%=basePath%>resources/manual/manual_1.doc">下载操作手册</a></td></tr>
</table>
<form id="formEdit" name="formEdit">
    <table id="tblEdit" align="center" width="60%" border="1">
        <tr>
            <td colspan="2" width="900">
                <font color="red">目标数据库环境：</font>
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
            <td ><font color="red">脚本语句：</font></td>
            <td colspan="3">
                <textarea id="dbscript" cols="100" rows="20"></textarea>
            </td>
        </tr>
        <tr id="tr_forcesubmit" style="display:none">
            <td><font color="red">是否强制提交危险语句：</font></td>
            <td colspan="3">
                <select id="forcetodoit">
                    <option value="yes">yes</option>
                    <option value="no" selected>no</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>附加描述：</td>
            <td colspan="3">
                <textarea id="description" cols="100" rows="10"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <font color="red">申请者：</font>
                <input type="text" id="applier" size="10" readonly>
            </td>
            <td colspan="2">
                <font color="red">何时执行：</font>
                <select id="canexecute">
                    <option value="0">暂缓执行</option>
                    <option value="1">随时都可执行</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <input type="button" value="提交申请" id="btnSave" onclick="saveData();">
                <input type="button" value="打开查询页面" id="btnOpenQueryPage" onclick="openQueryPage();">
            </td>
        </tr>
    </table>
    <table align="center" width="80%" border="0">
        <tr><td><b>解释：</b></td></tr>
        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<li>脚本语句可以由多句DDL或DML语句组成，但必须保证这组语句都是操作同一个数据库的，否则执行过程中会报错。对于操作不同数据库的语句就应该单独另建新的记录。</li></td></tr>
        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<li>附加描述可以为空，通常用来简单描述一下脚本的用处，对应的什么需求、为何要有这个脚本等内容。</li></td></tr>
    </table>
</form>
<br>

</body>
</html>
