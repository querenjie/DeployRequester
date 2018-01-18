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
    <title>发布情况查询</title>
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
            initBeatPicker();

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

            judgeUsableForMarkProductDeploy();
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
                    $("#projectcode").append("<option value=\"\">全部</option>")
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

        function doQuery() {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var moduleTypeCode = $("#moduletypecode").val();
            var applyBeginDate = $("#applyBeginDate").val();
            var applyEndDate = $("#applyEndDate").val();
            var developer = $("#developer").val();
            var isTestOk = $("#istestok").val();
            var isDeployedToProduct = $("#isdeployedtoproduct").val();

            if (applyBeginDate == "") {
                alert("查询条件中的起始日期不能为空");
                return;
            }
            var valveDateOfHalfYearBefore = getValveDateOfHalfYearBefore();
            if (applyBeginDate != "" && applyBeginDate < valveDateOfHalfYearBefore) {
                alert("查询条件中的起始日期不能小于" + valveDateOfHalfYearBefore);
                return;
            }
            if (applyEndDate != "" && applyEndDate < valveDateOfHalfYearBefore) {
                alert("查询条件中的结束日期不能小于" + valveDateOfHalfYearBefore);
                return;
            }
            if (applyEndDate != "" && applyEndDate < applyBeginDate) {
                alert("查询条件中的结束日期不能小于" + applyBeginDate);
                return;
            }


            $("#processAction").html("正在查询中......");

            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.projectcode = projectCode;
            QueryCriteriaDTO.modulecode = moduleCode;
            QueryCriteriaDTO.moduletypecode = moduleTypeCode;
            QueryCriteriaDTO.begindate = applyBeginDate;
            QueryCriteriaDTO.enddate = applyEndDate;
            QueryCriteriaDTO.istestok = isTestOk;
            QueryCriteriaDTO.isdeployedtoproduct = isDeployedToProduct;
            QueryCriteriaDTO.developer = developer;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/query",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
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
                                            tableResultContent += "</tr>";
                                            tableResultContent += "<tr><td colspan='3'><table id='tbl_" + moduleId + "' width='100%' style='display:none' border=0 cellspacing=0 cellpadding=0 style='border-left: 1 solid #000000; border-right: 1 solid #C0C0C0; border-top: 1 solid #000000; border-bottom: 1 solid #C0C0C0'>";
                                            if (deployRequestList != null && deployRequestList.length > 0) {
                                                tableResultContent += "<tr bgcolor='#ff8c00'>";
                                                tableResultContent += "<td>序号</td>";
                                                tableResultContent += "<td>项目名称</td>";
                                                tableResultContent += "<td>模块</td>";
                                                tableResultContent += "<td>模块类型</td>";
                                                tableResultContent += "<td>改动描述</td>";
                                                tableResultContent += "<td>对应菜单</td>";
                                                tableResultContent += "<td>开发人员</td>";
                                                tableResultContent += "<td>发布到测试环境的时间</td>";
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

        function doQueryDetail(deployRequestId) {
            $("#divDetailInfo").show();

            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.projectcode = null;
            QueryCriteriaDTO.modulecode = null;
            QueryCriteriaDTO.moduletypecode = null;
            QueryCriteriaDTO.begindate = null;
            QueryCriteriaDTO.enddate = null;
            QueryCriteriaDTO.istestok = null;
            QueryCriteriaDTO.isdeployedtoproduct = null;
            QueryCriteriaDTO.developer = null;
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

        function doCloseDiv_divDeployApplicationInfo() {
            $("#divDeployApplicationInfo").hide();
        }

        function openNoticeBoardPage() {
            window.open("<%=basePath%>noticeboard/notice_board", "_blank");
        }

        window.onscroll = function () {
            var divTop = $(document).scrollTop() + 50 + "px";
            $("#divDetailInfo").css("top", divTop);
        }

        function doHaventTest() {
            var deployRequestId = $("#detail_deployrequestid").html();
            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.istestok = 0;
            QueryCriteriaDTO.deployrequestid = deployRequestId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/assignIsTestOk",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            $("#detail_hastestok").html("未测试");
                            $("#tr_" + deployRequestId).attr("bgcolor", "white");
                            doQueryDetail(deployRequestId);
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doTestPass() {
            var deployRequestId = $("#detail_deployrequestid").html();
            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.istestok = 1;
            QueryCriteriaDTO.deployrequestid = deployRequestId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/assignIsTestOk",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            $("#detail_hastestok").html("测试通过");
                            $("#tr_" + deployRequestId).attr("bgcolor", "aqua");
                            doQueryDetail(deployRequestId);
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doTestNotPass() {
            var deployRequestId = $("#detail_deployrequestid").html();
            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.istestok = -1;
            QueryCriteriaDTO.deployrequestid = deployRequestId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/assignIsTestOk",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            $("#detail_hastestok").html("测试不通过");
                            $("#tr_" + deployRequestId).attr("bgcolor", "pink");
                            doQueryDetail(deployRequestId);
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doDoNotNeedTest() {
            var deployRequestId = $("#detail_deployrequestid").html();
            var QueryCriteriaDTO = {};
            QueryCriteriaDTO.istestok = -2;
            QueryCriteriaDTO.deployrequestid = deployRequestId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/assignIsTestOk",
                data:JSON.stringify(QueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            $("#detail_hastestok").html("无需测试");
                            $("#tr_" + deployRequestId).attr("bgcolor", "grey");
                            doQueryDetail(deployRequestId);
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function openAlreadyDeployedToProdDiv() {
            window.open("<%=basePath%>depquery/gotoMarkProdRecord", "_blank");
        }

        function produceApplication(deployToWhere) {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var moduleTypeCode = $("#moduletypecode").val();

            if (projectCode == "") {
                alert("必须选择项目");
                return;
            }

            //弹出div
            $("#divDeployApplicationInfo").show();
            $("#taApp").val("");


            var ProduceApplicationQueryCriteriaDTO = {};
            ProduceApplicationQueryCriteriaDTO.projectcode = projectCode;
            ProduceApplicationQueryCriteriaDTO.modulecode = moduleCode;
            ProduceApplicationQueryCriteriaDTO.moduletypecode = moduleTypeCode;
            ProduceApplicationQueryCriteriaDTO.deployToWhere = deployToWhere;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depquery/produceapplication",
                data:JSON.stringify(ProduceApplicationQueryCriteriaDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var queryResults = resultData.data[0];
                            $("#taApp").val(queryResults);
                        }
                    }
                },
                error:function(resultData){
                    $("#taApp").val("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        /**
         * 判断是否有标识生产发布记录的权限
         */
        function judgeUsableForMarkProductDeploy() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeCanMarkProductDeploy",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                if (resultData.data[0] == "ok") {
                                    $("#btnMarkProductDeploy").removeAttr("disabled");
                                } else {
                                    $("#btnMarkProductDeploy").attr("disabled", true);
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

        function getValveDateOfHalfYearBefore() {
            // 先获取当前时间
            var curDate = (new Date()).getTime();
            // 将半年的时间单位换算成毫秒
            var halfYear = 365 / 2 * 24 * 3600 * 1000;
            var pastResult = curDate - halfYear;  // 半年前的时间（毫秒单位）

            // 日期函数，定义起点为半年前
            var pastDate = new Date(pastResult),
                pastYear = pastDate.getFullYear(),
                pastMonth = pastDate.getMonth() + 1;
            if (pastMonth < 10) {
                pastMonth = "0" + pastMonth;
            }

            return pastYear + "-" + pastMonth + "-01";
        }

        function initBeatPicker() {
            $("#applyBeginDate").val(getValveDateOfHalfYearBefore());
        }

        function openStatistics1Page() {
            window.open("<%=basePath%>depstatistics/deploy_statistics1", "_blank");
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
        <tr>
            <td align="right" colspan="4">
                <input type="button" value="打开发布公告的页面" onclick="openNoticeBoardPage();">
                <input id="btnMarkProductDeploy" type="button" value="设置已经上生产的标志" onclick="openAlreadyDeployedToProdDiv();">
                <input type="button" value="打开统计测试情况的页面" onclick="openStatistics1Page();">
            </td>
        </tr>
        <tr bgcolor="#5f9ea0"><td align="left" colspan="4">条件区域</td></tr>
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
            <td>提交申请的日期范围：
                <input id="applyBeginDate" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2017,6,1],to:'<'}"/>到
                <input id="applyEndDate" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2017,6,1],to:'<'}"/>
            </td>
        </tr>
        <tr>
            <td>申请人：<input id="developer" type="text"></td>
            <td>测试环境上测试是否通过:
                <select id="istestok">
                    <option value="">全部</option>
                    <option value="0">未测</option>
                    <option value="1">通过</option>
                    <option value="-1">未通过</option>
                </select>
            </td>
            <td>是否已经上生产环境:
                <select id="isdeployedtoproduct">
                    <option value="">全部</option>
                    <option value="0">未发布</option>
                    <option value="2">已发布成功</option>
                    <option value="-1">发布失败</option>
                    <option value="3">发布预生产成功</option>
                </select>
            </td>
            <td>
                <input type="button" value="查询" onclick="doQuery();">
                <input type="button" value="生成上线申请" onclick="produceApplication('product');">
                <input type="button" value="生成预发布申请" onclick="produceApplication('preproduct');">
            </td>
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
                    <input type="button" value="设置为未测试" onclick="doHaventTest();">
                    <input type="button" value="设置为测试通过" onclick="doTestPass();">
                    <input type="button" value="设置为测试不通过" onclick="doTestNotPass();">
                    <input type="button" value="设置为无需测试" onclick="doDoNotNeedTest();">
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
