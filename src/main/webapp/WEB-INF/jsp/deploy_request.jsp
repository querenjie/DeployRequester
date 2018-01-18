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
    <title>发布测试环境申请</title>
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
            getModuleTypes();
            getModifyTypes();
            getCrewName();

            setInterval("doRetrieveMsg()", 3000);

        });

        function doRetrieveMsg() {
            retrieveNotice();
            retrieveSpeech();
        }

        function validateDatas() {
            var msg = "";
            if ($("#projectcode").val() == "") {
                msg += "必须选择项目" + "\n";
            }
            if ($.trim($("#developer").val()) == "") {
                msg += "必须输入开发人员姓名" + "\n";
            }
            if ($("#modulecode").val() == "") {
                msg += "必须选择模块" + "\n";
            }
            if ($("#moduletypecode").val() == "") {
                msg += "必须选择模块类型" + "\n";
            }
            if ($("#modifytype").val() == "") {
                msg += "必须选择修复类型" + "\n";
            }
            if ($("#isautodeploytotestenv").val() == "") {
                msg += "必须选择是否自动发布" + "\n";
            }
            if ($("#description").val() == "") {
                msg += "必须填写代码更新功能描述" + "\n";
            }
            if ($("#menuname").val() == "") {
                msg += "必须填写对应的菜单"
            }

            if (msg != "") {
                alert(msg);
                return false;
            }
            return true;
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

        function getModuleTypes() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getModuleTypes",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#moduletypecode").empty();
                    $("#moduletypecode").append("<option value=\"\"></option>")
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#moduletypecode").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].typeName + "</option>");
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getModifyTypes() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getModifyTypes",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    $("#modifytype").empty();
                    $("#modifytype").append("<option value=\"\"></option>")
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#modifytype").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].modifyType + "</option>");
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getUrlSummary(projectId, moduleId, moduleTypeId) {
            var moduleDTO = {};
            moduleDTO.projectId = projectId;
            moduleDTO.moduleId = moduleId;
            moduleDTO.moduleTypeId = moduleTypeId;
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/getUrlSummary",
                async: false,
                data:JSON.stringify(moduleDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                $("#testdeployurl").html(resultData.data[index].testDeployUrl);
                                $("#productdeployurl").html(resultData.data[index].productDeployUrl);
                                $("#tlogs").html(resultData.data[index].tlogs);
                                $("#tslogs").html(resultData.data[index].tslogs);
                                $("#tstatus").html(resultData.data[index].tstatus);
                                $("#plogs").html(resultData.data[index].plogs);
                                $("#pslogs").html(resultData.data[index].pslogs);
                                $("#pstatus").html(resultData.data[index].pstatus);
                                $("#prelogs").html(resultData.data[index].prelogs);
                                $("#preslogs").html(resultData.data[index].preslogs);
                                $("#prestatus").html(resultData.data[index].prestatus);
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function getPerformance(projectId, moduleId, moduleTypeId) {
            var moduleDTO = {};
            moduleDTO.projectId = projectId;
            moduleDTO.moduleId = moduleId;
            moduleDTO.moduleTypeId = moduleTypeId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>performance/getPerformance",
                data:JSON.stringify(moduleDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var duration = 0;
                            $.each(resultData.data, function(index) {
                                duration = resultData.data[index];
                            });
                            if (duration > 0) {
                                $("#duration").html("(预测发布时间长度为：" + duration + "毫秒)");
                            } else {
                                $("#duration").html("");
                            }
                        } else {
                            $("#duration").html("");
                        }
                    } else {
                        $("#duration").html("");
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function submitRequest() {
            judgeUsable();
            if (!validateDatas()) {
                $("#submitButton").removeAttr("disabled");
                return;
            }
            if (confirm("确认要发布到测试环境吗？")) {
                if ($("#submitButton").attr("disabled")) {
                    alert("You have no permission to deploy anything to testing environment.");
                    return;
                }
            } else {
                return;
            }
            $("#submitButton").attr("disabled", true);


            var deployRequesterDTO = {};
            deployRequesterDTO.projectcode = $("#projectcode").val();
            deployRequesterDTO.developer = $("#developer").val();
            deployRequesterDTO.modulecode = $("#modulecode").val();
            deployRequesterDTO.moduletypecode = $("#moduletypecode").val();
            deployRequesterDTO.modifytype = $("#modifytype").val();
            deployRequesterDTO.isautodeploytotestenv = $("#isautodeploytotestenv").val();
            deployRequesterDTO.description = $("#description").val();
            deployRequesterDTO.menuname = $("#menuname").val();

            $("#taStatus").val("正在提交申请到数据库中......");
            $.ajax({
                type: "POST",
                url: "<%=basePath%>depreq/addDeployRequest",
                data:JSON.stringify(deployRequesterDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(data){
                    if (data != null) {
                        if (data.msg == "insert data successfully") {
                            //如果写数据库成功
                            if ($("#isautodeploytotestenv").val() == 1) {
                                //如果是自动发布
                                deployToTestEnv();
                            }
                        } else {
                            $("#taStatus").val(data.msg);
                        }
                    }
                },
                error:function(data){
                    $("#taStatus").val("发布系统停止运行，请耐心等待。。。");
                    $("#submitButton").removeAttr("disabled");
                }
            });
        }

        function initModules() {
            var projectId = $("#projectcode").val();
            if (projectId != "") {
                getModulesByProjectId(projectId);
            }
            showurls();
        }
        
        function showurls() {
            var projectId = $("#projectcode").val();
            var moduleId = $("#modulecode").val();
            var moduleTypeId = $("#moduletypecode").val();
            if (projectId == "" || moduleId == "" || moduleTypeId == "") {
                clearurl();
            } else {
                getUrlSummary(projectId, moduleId, moduleTypeId);
                judgeCanViewDeployUrl();
                $("#duration").html("");
                getPerformance(projectId ,moduleId, moduleTypeId);
            }
        }

        function clearurl() {
            $("#testdeployurl").html("");
            $("#productdeployurl").html("");
            $("#tlogs").html("");
            $("#tslogs").html("");
            $("#tstatus").html("");
            $("#plogs").html("");
            $("#pslogs").html("");
            $("#pstatus").html("");
        }

        function deployToTestEnv() {
            var projectId = $("#projectcode").val();
            var moduleId = $("#modulecode").val();
            var moduleTypeId = $("#moduletypecode").val();
            getPerformance(projectId, moduleId, moduleTypeId);

            $("#taStatus").val(getCurrentTime() + "  发布申请已经正常写入到数据库中，现在正在发布......");
            $.ajax({
                type: "POST",
                url: "<%=basePath%>deploy/deployToTestEnv",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "ok") {
                            //如果发布成功
                            if (resultData.data != null && resultData.data.length > 0) {
                                var duration = 0;
                                var isSuccessDeployed = false;
                                var resultMsg = "";
                                var suggestion = "";
                                $.each(resultData.data, function(index) {
                                    duration = resultData.data[index].duration;
                                    isSuccessDeployed = resultData.data[index].isSuccessDeployed;
                                    resultMsg = resultData.data[index].resultMsg;
                                    suggestion = resultData.data[index].suggestion;
                                });
                                var result = "";
                                if (isSuccessDeployed) {
                                    result = "发布测试环境成功";
                                    result += "\n" + $.trim(resultMsg);
                                    result += "\n" + "发布耗时：" + duration + " 毫秒";
                                } else {
                                    result = "发布测试环境失败";
                                    result += "\n" + $.trim(resultMsg);
                                    result += "\n" + suggestion;
                                }
                                $("#taStatus").val(result);
                            }
                        } else {
                            $("#taStatus").val(resultData.msg);
                        }
                    }
                    $("#submitButton").removeAttr("disabled");
                },
                error:function(data){
                    $("#taStatus").val(data);
                    $("#submitButton").removeAttr("disabled");
                }
            });
        }

        function getCurrentTime() {
            var currentTime = new Date();
            var year = currentTime.getFullYear();
            var month = currentTime.getMonth() + 1;
            var date = currentTime.getDate();
            var hours = currentTime.getHours();
            var minutes = currentTime.getMinutes();
            var seconds = currentTime.getSeconds();
            return (year + "-" + padl(month, "0") + "-" + padl(date, "0") + " " + padl(hours, "0") + ":" + padl(minutes, "0") + ":" + padl(seconds, "0"));
        }

        function padl(str, replacement) {
            str = str + "";
            replacement = replacement + "";
            if (str.length == 1) {
                return replacement + str;
            }
            return str;
        }

        function judgeUsable() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeUsable",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                if (resultData.data[0] == "ok") {
                                    $("#promptMsg").html("");
                                    $("#submitButton").removeAttr("disabled");
                                } else {
                                    $("#promptMsg").html(resultData.data[0]);
                                    $("#submitButton").attr("disabled", true);
                                }
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert(resultData)
                }
            });
        }

        function judgeCanViewDeployUrl() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeCanViewDeployUrl",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                if (resultData.data[0] == "ok") {

                                } else {
                                    $("#testdeployurl").html("<font color='red'>" + resultData.data[0] + "</font>");
                                    $("#productdeployurl").html("<font color='red'>" + resultData.data[0] + "</font>");
                                }
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert(resultData)
                }
            });
        }

        function openQueryPage() {
            window.open("<%=basePath%>depquery/deploy_query", "_blank");
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

            judgeUsable();
        }

        function reloadPage() {
            $("#closemodal").click();
            top.location.reload();
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
                                $("#developer").val(resultData.data[0]);
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
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
        <tr><td align="center"><font size="5">发布测试环境申请</font></td></tr>
    </table>
    <table align="center" width="50%" border="1">
        <tr>
            <td><font color="red">项目名称：</font></td>
            <td>
                <select id="projectcode" onchange="initModules();">
                </select>
            </td>
            <td><font color="red">申请者（即开发人员姓名或笔名）：</font></td>
            <td><input type="text" id="developer" readonly></td>
        </tr>
        <tr>
            <td><font color="red">模块：</font> </td>
            <td>
                <select id="modulecode" onchange="showurls();">
                </select>
            </td>
            <td><font color="red">模块类型：</font> </td>
            <td>
                <select id="moduletypecode" onchange="showurls();">
                </select>
            </td>
        </tr>
        <tr>
            <td><font color="red">修复类型：</font> </td>
            <td>
                <select id="modifytype">
                </select>
            </td>
            <td><font color="red">是否自动发布：</font> </td>
            <td>
                <select id="isautodeploytotestenv">
                    <option value="1">是</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><font color="red">代码更新功能描述：</font> </td>
            <td colspan="3"><textarea id="description" cols="88" rows="16" title="尽量写详细点，能让测试看明白"></textarea> </td>
        </tr>
        <tr>
            <td><font color="red">对应的菜单：</font> </td>
            <td colspan="3"><input type="text" id="menuname" size="80" maxlength="100" title="如果没有就填无"></td>
        </tr>
    </table>
    <table border="0" align="center" width="50%">
        <tr><td align="center">发布进度状态<div id="duration"></div></td></tr>
        <tr><td align="center"><textarea id="taStatus" cols="110" rows="10" disabled></textarea></td></tr>
    </table>
    <table align="center" width="50%">
        <tr>
            <td align="right">
                <font color="red" id="promptMsg"></font>
                <input id="submitButton" type="button" value="提交申请" onclick="submitRequest();">
                <input id="queryButton" type="button" value="查询" onclick="openQueryPage();">
            </td>
        </tr>
    </table>

    <div >
    <table align="center" width="70%" >
        <tr>
            <td width="60" style="border-bottom:1px solid red;">测试环境发布路径：</td>
            <td style="border-bottom:1px solid red;"><div id="testdeployurl"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">生产环境发布路径：</td>
            <td style="border-bottom:1px solid red;"><div id="productdeployurl"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">测试环境日志：</td>
            <td style="border-bottom:1px solid red;"><div id="tlogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">测试环境启动日志：</td>
            <td style="border-bottom:1px solid red;"><div id="tslogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">测试环境启动状态日志：</td>
            <td style="border-bottom:1px solid red;"><div id="tstatus"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">生产环境日志：</td>
            <td style="border-bottom:1px solid red;"><div id="plogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">生产环境启动日志：</td>
            <td style="border-bottom:1px solid red;"><div id="pslogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">生产环境启动状态日志：</td>
            <td style="border-bottom:1px solid red;"><div id="pstatus"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">预生产环境日志：</td>
            <td style="border-bottom:1px solid red;"><div id="prelogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">预生产环境启动日志：</td>
            <td style="border-bottom:1px solid red;"><div id="preslogs"></div></td>
        </tr>
        <tr>
            <td style="border-bottom:1px solid red;">预生产环境启动状态日志：</td>
            <td style="border-bottom:1px solid red;"><div id="prestatus"></div></td>
        </tr>
    </table>
    </div>
</body>
</html>
