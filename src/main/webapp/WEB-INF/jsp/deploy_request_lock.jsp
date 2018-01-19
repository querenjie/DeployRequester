<%--
  Created by IntelliJ IDEA.
  User: suneee
  Date: 2017/10/31
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>发布锁定</title>
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

    </style>

    <link rel="stylesheet" href="<%=basePath%>resources/core/css/BeatPicker.min.css"/>
    <script src="<%=basePath%>resources/core/js/jquery-1.11.0.min.js"></script>
    <script src="<%=basePath%>resources/core/js/BeatPicker.min.js"></script>

    <script src="<%=basePath%>resources/core/js/remodal/remodal.js"></script>
    <script src="<%=basePath%>resources/core/js/remodal/remodal_test.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            getProjects();
            getModuleTypes();
            doQueryLockedDeployRequest();
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
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
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

        function reloadPage() {
            $("#closemodal").click();
            top.location.reload();
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
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
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
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getModuleTypes() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getModuleTypes",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#moduletypecode").empty();
                    $("#moduletypecode").append("<option value=\"\">全部</option>")
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#moduletypecode").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].typeName + "</option>");
                            });
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function doLock() {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var moduleTypeCode = $("#moduletypecode").val();

            if (projectCode == "") {
                alert("必须选择项目");
                return;
            }

            var DeployRequesterDTO = {};
            DeployRequesterDTO.projectcode = projectCode;
            DeployRequesterDTO.modulecode = moduleCode;
            DeployRequesterDTO.moduletypecode = moduleTypeCode;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depreq/lockDeployRequest",
                data:JSON.stringify(DeployRequesterDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "ok") {
                            //刷新结果页面
                            doQueryLockedDeployRequest();
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        /**
         * 获取所有锁定的发布申请
         */
        function doQueryLockedDeployRequest() {
            var tableResultContent = "";
            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/getLockedDeployRequest",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData) {
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            //刷新结果页面
                            var lockedDeployRequestList = resultData.data[0];
                            if (lockedDeployRequestList != null && lockedDeployRequestList.length > 0) {
                                tableResultContent += "<tr bgcolor='#ff8c00'>";
                                tableResultContent += "<td align='center'>项目名称</td>";
                                tableResultContent += "<td align='center'>模块名称</td>";
                                tableResultContent += "<td align='center'>模块类型名称</td>";
                                tableResultContent += "<td align='center'>操作动作</td>";
                                tableResultContent += "</tr>";
                                for (var i = 0; i < lockedDeployRequestList.length; i++) {
                                    var onerow = lockedDeployRequestList[i];
                                    var projectName = onerow.projectcode + "-" + onerow.projectName;
                                    var moduleName = onerow.modulecode + "-" + onerow.moduleCodeName + "-" + onerow.moduleDesc;
                                    var moduleTypeName = onerow.moduletypecode + "-" + onerow.moduleTypeName;
                                    tableResultContent += "<tr>";
                                    tableResultContent += "<td>" + projectName + "</td>";
                                    tableResultContent += "<td>" + moduleName + "</td>";
                                    tableResultContent += "<td>" + moduleTypeName + "</td>";
                                    tableResultContent += "<td><input type='button' value='解锁' onclick=\"doUnLock('" + onerow.projectcode + "', '" + onerow.modulecode + "', '" + onerow.moduletypecode + "');\"></td>";
                                    tableResultContent += "</tr>";
                                }
                            }
                        }
                    }
                    $("#tblResult").html(tableResultContent);
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        /**
         * 解锁发布申请
         * @param projectId
         * @param moduleId
         * @param moduleTypeId
         */
        function doUnLock(projectId, moduleId, moduleTypeId) {
            var DeployRequesterDTO = {};
            DeployRequesterDTO.projectcode = projectId;
            DeployRequesterDTO.modulecode = moduleId;
            DeployRequesterDTO.moduletypecode = moduleTypeId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depreq/unlockDeployRequest",
                data:JSON.stringify(DeployRequesterDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "ok") {
                            //刷新结果页面
                            doQueryLockedDeployRequest();
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
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

    <table align="center" width="80%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
        <tr bgcolor="#5f9ea0"><td align="left" colspan="4">如果模块被锁定就意味着无法发布测试环境</td></tr>
        <tr>
            <td><font color="red">项目名称：</font>
                <select id="projectcode" onchange="initModules();">
                </select>
            </td>
            <td>模块：
                <select id="modulecode">
                </select>
            </td>
            <td>模块类型：
                <select id="moduletypecode">
                </select>
            </td>
            <td align="center">
                <input type="button" value="锁定" onclick="doLock();">
                <input type="button" value="刷新结果" onclick="doQueryLockedDeployRequest();">
            </td>
        </tr>
    </table>
    <br>
    <table align="center" width="80%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
        <tr><td align="center" bgcolor="#9acd32"><font id="processAction">以下是所有被锁定的模块</font></td></tr>
        <tr>
            <td>
                <table id="tblResult" width="100%" border="1" style="border-collapse:collapse;">

                </table>
            </td>
        </tr>
    </table>

</body>
</html>
