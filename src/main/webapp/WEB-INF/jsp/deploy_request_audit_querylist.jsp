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
    <title>测试环境尚未执行的应用发布申请</title>
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
        //正在部署的项目和模块的id，用‘,’分隔。
        var gDeployingArray = new Array();

        $(document).ready(function() {
            getProjects();
            getModuleTypes();
            getCrewName();
            doQuery();

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

            judgeCanAuditDeployRequest();
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
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                //目前只有大宗可用
                                if (resultData.data[index].id == 3) {
                                    $("#projectcode").append("<option value=\"" + resultData.data[index].id + "\">" + resultData.data[index].id + "-" + resultData.data[index].projectName + "</option>");
                                    initModules();
                                }
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

        function doQuery() {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var moduleTypeCode = $("#moduletypecode").val();
            var developer = $("#developer").val();

            $("#processAction").html("正在查询中......");

            var queryDeployAuditDTO = {};
            queryDeployAuditDTO.projectCode = projectCode;
            queryDeployAuditDTO.moduleCode = moduleCode;
            queryDeployAuditDTO.moduleTypeCode = moduleTypeCode;
            queryDeployAuditDTO.developer = developer;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depreqaudit/obtainUnexecutedRequest",
                data:JSON.stringify(queryDeployAuditDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    var moduleRecCount = 0;        //模块记录数量
                    var detailRecCount = 0;        //明细记录数量
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var queryResults = resultData.data[0];
                            var queryModuleStatisticsMap = queryResults.queryModuleStatisticsMap;
                            if (queryResults != null && queryResults.allModuleList != null && queryResults.allModuleList.length > 0) {
                                var tableResultContent = "";
                                $.each(queryResults.allModuleList, function(index) {
                                    var module = queryResults.allModuleList[index];
                                    var moduleId = module.id;
                                    var moduleCode = module.code;
                                    var moduleName = module.name;

                                    for (var key in queryModuleStatisticsMap) {
                                        if (key == moduleId) {
                                            var queryModuleStatistics = queryModuleStatisticsMap[key];
                                            var moduleCount = queryModuleStatistics.moduleCount;
                                            var moduleRestCount = queryModuleStatistics.moduleRestCount;
                                            var moduleProviderCount = queryModuleStatistics.moduleProviderCount;
                                            var deployRequestList = queryModuleStatistics.deployRequestList;

                                            moduleRecCount ++;
                                            detailRecCount += moduleCount;

                                            tableResultContent += "<tr bgcolor='#ffe4c4' style='border-top:2px solid black;'>";
                                            tableResultContent += "<td>" + "模块名称:" + moduleId + "-" + moduleCode + "(" + moduleName + ") &nbsp;&nbsp;&nbsp;&nbsp;" + "</td>";
                                            tableResultContent += "<td>" + "发布申请数量:" + moduleCount + ", 其中rest:" + moduleRestCount + ", provider:" + moduleProviderCount + "</td>";
                                            if (moduleCount > 0) {
                                                tableResultContent += "<td><input id='btn_" + moduleId + "' type='button' value='expand' onclick='showOrHide(this);'></td>";
                                            } else {
                                                tableResultContent += "<td><input type='button' value='expand' disabled></td>";
                                            }
                                            if (moduleRestCount > 0) {
                                                tableResultContent += "<td><input id='btn_deploy_rest_" + moduleId + "' type='button' value='发布rest' onclick='doDeploy(this);'></td>";
                                            } else {
                                                tableResultContent += "<td>&nbsp;</td>";
                                            }
                                            if (moduleProviderCount > 0) {
                                                tableResultContent += "<td><input id='btn_deploy_provider_" + moduleId + "' type='button' value='发布provider' onclick='doDeploy(this);'></td>";
                                            } else {
                                                tableResultContent += "<td>&nbsp;</td>";
                                            }
                                            tableResultContent += "</tr>";
                                            tableResultContent += "<tr id='tr_" + moduleId + "' bgcolor='#add8e6' style='border-top:2px solid black; display:none'>";
                                            tableResultContent += "<td colspan='5'><font id='deploy_status_msg_" + moduleId + "'></font></td>";
                                            tableResultContent += "<tr>";
                                            tableResultContent += "<tr><td colspan='5'><table id='tbl_" + moduleId + "' width='100%' style='display:none' border=0 cellspacing=0 cellpadding=0 style='border-left: 1 solid #000000; border-right: 1 solid #C0C0C0; border-top: 1 solid #000000; border-bottom: 1 solid #C0C0C0'>";
                                            if (deployRequestList != null && deployRequestList.length > 0) {
                                                tableResultContent += "<tr bgcolor='#ff8c00'>";
                                                tableResultContent += "<td>序号</td>";
                                                tableResultContent += "<td>项目名称</td>";
                                                tableResultContent += "<td>模块</td>";
                                                tableResultContent += "<td>模块类型</td>";
                                                tableResultContent += "<td>改动描述</td>";
                                                tableResultContent += "<td>对应菜单</td>";
                                                tableResultContent += "<td>开发人员</td>";
                                                tableResultContent += "<td>记录生成的时间</td>";
                                                tableResultContent += "</tr>";

                                                for (var i = 0; i < deployRequestList.length; i++) {
                                                    var deployRequest = deployRequestList[i];
                                                    var bgcolorString = "";
                                                    if (deployRequest.istestok == '0') {
                                                        bgcolorString = "white";
                                                    } else if (deployRequest.istestok == '1') {
                                                        bgcolorString = "aqua";
                                                    } else if (deployRequest.istestok == '-1') {
                                                        bgcolorString = "pink";
                                                    } else if (deployRequest.istestok == '-2') {
                                                        bgcolorString = "grey";
                                                    }
                                                    tableResultContent += "<tr id='tr_" + deployRequest.deployrequestid + "' onclick=\"doQueryDetail('" + deployRequest.deployrequestid + "');\" bgcolor='" + bgcolorString + "' alt='单击显示详细资料' style='cursor:hand'>"
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + (i + 1) + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.projectcode + "-" + deployRequest.projectName + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.modulecode + "-" + deployRequest.moduleCodeName + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.moduletypecode + "-" + deployRequest.moduleTypeName + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.description + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.menuname + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.developer + "</td>";
                                                    tableResultContent += "<td style='border-bottom:1px solid green;'>" + deployRequest.formatedCreateDate + "</td>";
                                                    tableResultContent += "</tr>";
                                                }
                                            }
                                            tableResultContent += "</table><td></tr>";
                                        }
                                    }
                                });
                                $("#tblResult").html(tableResultContent);
                                $("#processAction").html("以下就是查询结果(其中模块数量："  + moduleRecCount + ", 明细记录数量：" + detailRecCount + ")");
                            }
                        }
                    }
                    $("#processAction").html("以下就是查询结果(其中模块数量："  + moduleRecCount + ", 明细记录数量：" + detailRecCount + ")");
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doQueryDetail(deployRequestId) {
            $("#divDetailInfo").show();

            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.deployrequestid = deployRequestId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/queryDetail",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployRequest = resultData.data[0];
                            $("#detail_deployrequestid").html(deployRequest.deployrequestid);
                            $("#detail_projectname").html(deployRequest.projectcode + "-" + deployRequest.projectName);
                            $("#detail_modulename").html(deployRequest.modulecode + "-" + deployRequest.moduleCodeName);
                            $("#detail_moduletype").html(deployRequest.moduletypecode + "-" + deployRequest.moduleTypeName);
                            $("#detail_modifytype").html(deployRequest.modifytype + "-" + deployRequest.modifyTypeName);
                            $("#detail_description").html(deployRequest.description);
                            $("#detail_menu").html(deployRequest.menuname);
                            $("#detail_developer").html(deployRequest.developer);
                            $("#detail_hastestok").html(deployRequest.isTestOkDesc);
                            $("#detail_testflagmodifier").html(deployRequest.testflagmodifier);
                            $("#detail_formatedTestflagmodifytime").html(deployRequest.formatedTestflagmodifytime);
                            $("#detail_hasdeployedtoprodenv").html(deployRequest.deployStatusForProdEnvDesc);
                            $("#detail_createtime").html(deployRequest.formatedCreateDate);
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doCloseDiv() {
            $("#divDetailInfo").hide();
        }

        window.onscroll = function () {
            var divTop = $(document).scrollTop() + 50 + "px";
            $("#divDetailInfo").css("top", divTop);
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

        function showOrHide(me) {
            var moduleId = me.id.substring(4);
            if ($("#tbl_" + moduleId).css("display")=="none") {
                $("#tbl_" + moduleId).show();
                me.value = "collapse";
            } else {
                $("#tbl_" + moduleId).hide();
                me.value = "expand";
            }
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
                                $("#welcome").html("欢迎：" + resultData.data[0]);
                            });
                        }
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function judgeCanAuditDeployRequest() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeCanAuditDeployRequest",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                if (resultData.data[0] == "ok") {
                                    $('input').each(function(){
                                        var id = $(this).attr('id');
                                        if (id != undefined) {
                                            if (id.indexOf("btn_deploy_rest_") > -1 || id.indexOf("btn_deploy_provider_") > -1) {
                                                $(this).removeAttr("disabled");
                                            }
                                        }
                                    });
                                } else {
                                    $('input').each(function(){
                                        var id = $(this).attr('id');
                                        if (id != undefined) {
                                            if (id.indexOf("btn_deploy_rest_") > -1 || id.indexOf("btn_deploy_provider_") > -1) {
                                                $(this).attr("disabled", true);
                                            }
                                        }
                                    });
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

        function doDeploy(eleObj) {
            var projectId = $("#projectcode").val();
            var arr1 = eleObj.id.split("_");
            if (arr1.length != 4) {
                alert("获取不到moduleId,元素命名有问题。");
                return;
            }
            var moduleId = arr1[3];
            if (moduleId == undefined || moduleId == null || moduleId == '') {
                alert("获取不到moduleId");
                return;
            }
            if (projectId == null || projectId == '') {
                alert("获取不到项目");
                return;
            }
            var moduleTypeId = arr1[2] == "rest" ? 1 : arr1[2] == "provider" ? 2 : 0;

            $("#tr_" + moduleId).show();

            var deployStatusMsg = "";
            var performanceMsg = getPerformance(projectId, moduleId, moduleTypeId);
            deployStatusMsg += performanceMsg + "<br>" + getCurrentTime() + "  现在正在发布......";
            $("#" + eleObj.id).attr("disabled", true);
            $("#deploy_status_msg_" + moduleId).html(deployStatusMsg);

            var deployRequesterDTO = {};
            deployRequesterDTO.projectcode = projectId;
            deployRequesterDTO.modulecode = moduleId;
            deployRequesterDTO.moduletypecode = moduleTypeId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>deploy/deployToTestEnvForAudit",
                data:JSON.stringify(deployRequesterDTO),//json序列化
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
                                var result = $("#deploy_status_msg_" + moduleId).html() + "<br>";
                                if (isSuccessDeployed) {
                                    result += "发布测试环境成功";
                                    result += "<br>" + $.trim(resultMsg);
                                    result += "<br>" + "发布耗时：" + duration + " 毫秒";
                                } else {
                                    result += "发布测试环境失败";
                                    result += "<br>" + $.trim(resultMsg);
                                    result += "<br>" + suggestion;
                                }
                                $("#deploy_status_msg_" + moduleId).html(result);
                            }
                        } else if (resultData.msg == "failed") {
                            if (resultData.data != null && resultData.data.length > 0) {
                                alert(resultData.data[0]);
                            }
                        }
                    }
                    $("#" + eleObj.id).hide();
                },
                error:function(data){
                    $("#" + eleObj.id).hide();
                }
            });





        }

        function getPerformance(projectId, moduleId, moduleTypeId) {
            var moduleDTO = {};
            moduleDTO.projectId = projectId;
            moduleDTO.moduleId = moduleId;
            moduleDTO.moduleTypeId = moduleTypeId;

            var returnMsg = "";

            $.ajax({
                type: "POST",
                url: "<%=basePath%>performance/getPerformance",
                async: false,
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
//                                $("#deploy_status_msg").html("(预测发布时间长度为：" + duration + "毫秒)");
                                returnMsg = "(预测发布时间长度为：" + duration + "毫秒)";
                            } else {
//                                $("#deploy_status_msg").html("");
                                returnMsg = "";
                            }
                        } else {
//                            $("#deploy_status_msg").html("");
                            returnMsg = "";
                        }
                    } else {
//                        $("#deploy_status_msg").html("");
                        returnMsg = "";
                    }
                },
                error:function(resultData){
                    //alert("发布系统停止运行，请耐心等待。。。");
                }
            });
            return returnMsg;
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
        <tr bgcolor="#5f9ea0">
            <td align="left" colspan="4">查询需要发布的记录--条件区域</td>
            <td align="right"><b><font id="welcome" color="white"></font></b></td>
        </tr>
        <tr>
            <td>项目名称：
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
            <td>申请人：<input id="developer" type="text"></td>
            <td><input type="button" value="查询" onclick="doQuery();"></td>
        </tr>
    </table>
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

    <div id="divDetailInfo" style="position: absolute; width: 800px; height: 500px; border: 1px solid #08575B;top: 50px;left: 10px; background:#FFF; color:#000; z-index: 100; display:none">
        <table align="center" width="100%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
            <tr><td colspan="2" align="center" bgcolor="#0000ff" style="color:#FFF">发布申请详细内容</td></tr>
            <tr>
                <td width="20%">申请记录ID:</td>
                <td id="detail_deployrequestid">&nbsp;</td>
            </tr>
            <tr>
                <td>项目名称:</td>
                <td id="detail_projectname">&nbsp;</td>
            </tr>
            <tr>
                <td>模块名称:</td>
                <td id="detail_modulename">&nbsp;</td>
            </tr>
            <tr>
                <td>模块类型:</td>
                <td id="detail_moduletype">&nbsp;</td>
            </tr>
            <tr>
                <td>改动类型:</td>
                <td id="detail_modifytype">&nbsp;</td>
            </tr>
            <tr>
                <td>改动内容:</td>
                <td id="detail_description">&nbsp;</td>
            </tr>
            <tr>
                <td>对应菜单:</td>
                <td id="detail_menu">&nbsp;</td>
            </tr>
            <tr>
                <td>开发人员:</td>
                <td id="detail_developer">&nbsp;</td>
            </tr>
            <tr>
                <td>是否已经测试通过:</td>
                <td id="detail_hastestok">&nbsp;</td>
            </tr>
            <tr>
                <td>设置标记的操作人:</td>
                <td id="detail_testflagmodifier">&nbsp;</td>
            </tr>
            <tr>
                <td>设置标记的时间:</td>
                <td id="detail_formatedTestflagmodifytime">&nbsp;</td>
            </tr>
            <tr>
                <td>是否已经发布到生产:</td>
                <td id="detail_hasdeployedtoprodenv">&nbsp;</td>
            </tr>
            <tr>
                <td>本记录生成时间:</td>
                <td id="detail_createtime">&nbsp;</td>
            </tr>
        </table>
        <table width="100%">
            <tr>
                <td align="right">
                    <input type="button" value="关闭" onclick="doCloseDiv();">
                </td>
            </tr>
        </table>
    </div>

    <div id="divDeployApplicationInfo" style="position: absolute; width: 800px; height: 550px; border: 1px solid #08575B;top: 150px;left: 200px; background:#FFF; color:#000; z-index: 100; display:none">
        <table align="center" width="100%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
            <tr><td align="center" bgcolor="#0000ff" style="color:#FFF">发布生产申请内容(复制出来让淡然帮忙发布)</td></tr>
            <tr>
                <td><textarea id="taApp" cols="100" rows="30"></textarea></td>
            </tr>
            <tr>
                <td align="right"><input type="button" value="关闭" onclick="doCloseDiv_divDeployApplicationInfo();"></td>
            </tr>
        </table>
    </div>
</body>
</html>
