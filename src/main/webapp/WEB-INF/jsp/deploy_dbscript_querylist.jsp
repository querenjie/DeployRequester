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
    <title>脚本发布情况查询</title>
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
        var gCanDeployDbscript = "no";      //本页面的全局变量，用于表示是否能有权限发布数据库脚本。
        var gDeployDbserversId = "";        //用于暂存一下数据库连接的id
        var gDeployDbscriptId = "";         //用于暂存当前编辑的脚本记录的id

        $(document).ready(function() {
            getProjects();
            initBeatPicker();

            setInterval("doRetrieveMsg()", 3000);
        });

        function doRetrieveMsg() {
            retrieveNotice();
            retrieveSpeech();
            judgeCanDeployDbscript();
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

        function deleteById(deploydbscriptid) {
            var QueryDbscriptDTO = {};
            QueryDbscriptDTO.deploydbscriptid = deploydbscriptid;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/deleteById",
                data:JSON.stringify(QueryDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            if (resultData.msg == "failed") {
                                alert(resultData.data[0]);
                            } else {
                                doQuery();
                            }
                        } else {
                            alert("没找到记录，或许记录已被删除。");
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function doQueryDetail(deploydbscriptid) {
            $("#divDetailInfo").show();

            var QueryDbscriptDTO = {};
            QueryDbscriptDTO.deploydbscriptid = deploydbscriptid;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/queryDetail",
                data:JSON.stringify(QueryDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployDbscript = resultData.data[0];
                            $("#detail_deploydbscriptid").html(deployDbscript.deploydbscriptid);
                            $("#detail_belongDesc").html("<b>" + deployDbscript.belongDesc + "</b>");
                            $("#detail_projectName").html(deployDbscript.projectid + "-" + deployDbscript.projectName);
                            $("#detail_moduleName").html(deployDbscript.moduleid + "-" + deployDbscript.moduleName);
                            $("#detail_dbscript").val(deployDbscript.dbscript);
                            $("#detail_description").val(deployDbscript.description);
                            $("#detail_applier").html(deployDbscript.applier);
                            $("#detail_formatedCreateTime").html(deployDbscript.formatedCreateTime);
                            $("#detail_executor").html(deployDbscript.executor);
                            $("#detail_formatedExecuteTime").html(deployDbscript.formatedExecuteTime);
                            $("#detail_executeStatusDesc").html(deployDbscript.executeStatusDesc);
                            $("#detail_failuremsg").val(deployDbscript.failuremsg);
                            if (gCanDeployDbscript == "yes") {
                                var s = (deployDbscript.dblinkDesc == null ? "" : deployDbscript.dblinkDesc) + "<input id='btnOpenEditStyle' type='button' value='启用编辑模式' onclick=\"openEditStyle('" + deployDbscript.deploydbserversid + "', '" + deployDbscript.belong + "');\">";
                                $("#detail_dblinkDesc").html(s);
                            } else {
                                $("#detail_dblinkDesc").html(deployDbscript.dblinkDesc);
                            }
                            $("#detail_dblinkDesc_desc").html("");
                            $("#detail_executedSql").val("");
                            var executedSqlList = deployDbscript.executedSqlList;
                            if (executedSqlList != null && executedSqlList.length > 0) {
                                var executedSqlStrings = "";
                                for (var i = 0; i < executedSqlList.length; i++) {
                                    executedSqlStrings += executedSqlList[i] + ";\n";
                                }
                                $("#detail_executedSql").val(executedSqlStrings);
                            }
                            $("#detail_unexecutedSql").val("");
                            var unexecutedSqlList = deployDbscript.unexecutedSqlList;
                            if (unexecutedSqlList != null && unexecutedSqlList.length > 0) {
                                var unexecutedSqlStrings = "";
                                for (var i = 0; i < unexecutedSqlList.length; i++) {
                                    unexecutedSqlStrings += unexecutedSqlList[i] + ";\n";
                                }
                                $("#detail_unexecutedSql").val(unexecutedSqlStrings);
                            }
                            $("#detail_isabandoned").html(deployDbscript.isabandonedDesc);

                            highlightOrDisableDeployButton(deployDbscript);
                            if (deployDbscript.visitorIp == deployDbscript.applierip) {
                                $("#detail_unexecutedSql").removeAttr("readonly");
                            }
                            if (deployDbscript.visitorIp == deployDbscript.applierip && (deployDbscript.executestatus != 1 && deployDbscript.executestatus != 2)) {
                                //如果是自己创建的申请记录并且此记录的执行状态不是成功或者正在执行状态，点亮‘申请重新发布脚本’按钮。
                                $("#btnApplyRedeployDbScript").removeAttr("disabled");
                            } else {
                                $("#btnApplyRedeployDbScript").attr("disabled", true);
                            }
                            if (deployDbscript.visitorIp == deployDbscript.applierip && (deployDbscript.executestatus != 1 && deployDbscript.executestatus != 2) && deployDbscript.isabandoned == 0) {
                                //如果是自己创建的申请记录并且此记录的执行状态不是成功或者正在执行状态并且没有放弃剩余sql的可执行性，点亮‘放弃发布脚本’按钮。
                                $("#btnAbandonDeployDbScript").removeAttr("disabled");
                            } else {
                                $("#btnAbandonDeployDbScript").attr("disabled", true);
                            }

                            if (deployDbscript.executestatus == 1) {
                                //如果是执行成功状态，则隐藏以下tr
                                $("#tr_detail_unexecutedSql").hide();
                                $("#tr_detail_isabandoned").hide();
                            } else {
                                $("#tr_detail_unexecutedSql").show();
                                $("#tr_detail_isabandoned").show();
                            }

                            gDeployDbserversId = deployDbscript.deploydbserversid;
                            gDeployDbscriptId = deploydbscriptid;
                            $("#fntDblinkDesc").css("color", "#000000");

                        } else {
                            doCloseDiv();
                            alert("没找到记录，或许记录已被删除。");
                        }
                    }
                },
                error:function(resultData){
                    //$("#processAction").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doQuery() {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var applier = $("#applier").val();
            var formatedCreatetimeBegin = $("#formatedCreatetimeBegin").val();
            var formatedCreatetimeEnd = $("#formatedCreatetimeEnd").val();
            var dbscript = $("#dbscript").val();
            var description = $("#description").val();
            var formatedExecutetimeBegin = $("#formatedExecutetimeBegin").val();
            var formatedExecutetimeEnd = $("#formatedExecutetimeEnd").val();
            var executestatus = $("#executestatus").val();
            var failuremsg = $("#failuremsg").val();
            var showExcuteOption = $("#showExcuteOption").val();


            if (projectCode == "") {
                alert("必须选择项目");
                return;
            }
            if (formatedCreatetimeBegin == "") {
                alert("查询条件中的申请起始日期不能为空");
                return;
            }
            var valveDateOfHalfYearBefore = getValveDateOfHalfYearBefore();
            if (formatedCreatetimeBegin != "" && formatedCreatetimeBegin < valveDateOfHalfYearBefore) {
                alert("查询条件中的申请起始日期不能小于" + valveDateOfHalfYearBefore);
                return;
            }
            if (formatedCreatetimeEnd != "" && formatedCreatetimeEnd < valveDateOfHalfYearBefore) {
                alert("查询条件中的申请结束日期不能小于" + valveDateOfHalfYearBefore);
                return;
            }
            if (formatedCreatetimeEnd != "" && formatedCreatetimeEnd < formatedCreatetimeBegin) {
                alert("查询条件中的申请结束日期不能小于" + formatedCreatetimeBegin);
                return;
            }


            $("#processAction").html("正在查询中......");

            var QueryDbscriptDTO = {};
            if (projectCode != "") QueryDbscriptDTO.projectid = projectCode;
            if (moduleCode != "") QueryDbscriptDTO.moduleid = moduleCode;
            if (dbscript != "") QueryDbscriptDTO.dbscript = dbscript;
            if (description != "") QueryDbscriptDTO.description = description;
            if (applier != "") QueryDbscriptDTO.applier = applier;
            if (formatedCreatetimeBegin != "") QueryDbscriptDTO.formatedCreatetimeBegin = formatedCreatetimeBegin;
            if (formatedCreatetimeEnd != "") QueryDbscriptDTO.formatedCreatetimeEnd = formatedCreatetimeEnd;
            if (formatedExecutetimeBegin != "") QueryDbscriptDTO.formatedExecutetimeBegin = formatedExecutetimeBegin;
            if (formatedExecutetimeEnd != "") QueryDbscriptDTO.formatedExecutetimeEnd = formatedExecutetimeEnd;
            if (executestatus != "") QueryDbscriptDTO.executestatus = executestatus;
            if (failuremsg != "") QueryDbscriptDTO.failuremsg = failuremsg;
            if (showExcuteOption != "") QueryDbscriptDTO.showExcuteOption = showExcuteOption;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/query",
                data:JSON.stringify(QueryDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployDbscriptList = resultData.data[0];
                            var tableHtml = "";
                            if (deployDbscriptList != null && deployDbscriptList.length > 0) {
                                $("#processAction").html("查询结果如下");
                                tableHtml += "<tr bgcolor='#ffe4c4'>";
                                tableHtml += "<td align='center'><b>数据库环境</b></td>";
                                tableHtml += "<td align='center'><b>项目名称</b></td>";
                                tableHtml += "<td align='center'><b>模块名称</b></td>";
                                tableHtml += "<td align='center'><b>脚本语句部分内容</b></td>";
                                tableHtml += "<td align='center'><b>附加描述部分内容</b></td>";
                                tableHtml += "<td align='center'><b>申请者</b></td>";
                                tableHtml += "<td align='center'><b>申请时间</b></td>";
                                tableHtml += "<td align='center'><b>脚本执行状态</b></td>";
                                tableHtml += "<td align='center'><b>脚本执行时间</b></td>";
                                tableHtml += "<td align='center'><b>操作</b></td>";
                            }
                            $.each(deployDbscriptList, function(index) {
                                var deployDbscript = deployDbscriptList[index];
                                var bgColor = "white";
                                if (deployDbscript.executestatus == 0) {
                                    //如果尚未执行，底色为白色
                                    bgColor = "white";
                                }
                                if (deployDbscript.executestatus == 1) {
                                    //如果已经执行成功，底色为绿色
                                    bgColor = "green";
                                }
                                if (deployDbscript.isabandoned == 1) {
                                    //如果已经放弃执行，底色为灰色
                                    bgColor = "grey";
                                }
                                if (deployDbscript.executestatus == -1 && deployDbscript.isabandoned == 0) {
                                    //如果是执行失败状态并且可以继续执行的，底色为金色
                                    bgColor = "gold";
                                }
                                tableHtml += "<tr bgcolor='" + bgColor + "'>";
                                tableHtml += "<td>" + deployDbscript.belongDesc + "</td>";
                                tableHtml += "<td>" + deployDbscript.projectName + "</td>";
                                tableHtml += "<td>" + deployDbscript.moduleName + "</td>";
                                if (deployDbscript.dbscript.length > 20) {
                                    tableHtml += "<td>" + deployDbscript.dbscript.substring(0, 20) + "......" + "</td>";
                                } else {
                                    tableHtml += "<td>" + deployDbscript.dbscript + "</td>";
                                }
                                if (deployDbscript.description.length > 20) {
                                    tableHtml += "<td>" + deployDbscript.description.substring(0, 20) + "......" + "</td>";
                                } else {
                                    tableHtml += "<td>" + deployDbscript.description + "</td>";
                                }
                                tableHtml += "<td>" + deployDbscript.applier + "</td>";
                                tableHtml += "<td>" + deployDbscript.formatedCreateTime + "</td>";
                                tableHtml += "<td>" + deployDbscript.executeStatusDesc + "</td>";
                                tableHtml += "<td>" + (deployDbscript.formatedExecuteTime == null ? "" : deployDbscript.formatedExecuteTime) + "</td>";
                                tableHtml += "<td>";
                                if (deployDbscript.visitorIp == deployDbscript.applierip && deployDbscript.executestatus == 0) {
                                    tableHtml += "<input type='button' value='删除' onclick=\"deleteById('" + deployDbscript.deploydbscriptid + "');\">";
                                } else {
                                    tableHtml += "<input type='button' value='删除' disabled>";
                                }
                                tableHtml += "<input type='button' value='查看详情' onclick=\"doQueryDetail('" + deployDbscript.deploydbscriptid + "');\">";
                                tableHtml += "</td>";
                                tableHtml += "</tr>";
                            });
                            $("#tblResult").html(tableHtml);
                        }
                    }

                    var whiteChars = "<font color='white'><b>白底</b></font>";
                    var goldChars = "<font color='gold'><b>黄底</b></font>";
                    var greyChars = "<font color='grey'><b>灰底</b></font>";
                    var greenChars = "<font color='green'><b>绿底</b></font>";
                    $("#processAction").html("以下就是查询结果(提示：" + whiteChars + "和" + goldChars + "的表示可以继续发布执行的；" + greenChars + "的表示已经发布成功的；" + greyChars + "的表示已经放弃发布了。)");
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

        /**
         * 判断是否有发布数据库脚本到生产以及相关的管理权限。
         * 这当中也包括了操作数据库连接配置的权限
         */
        function judgeCanDeployDbscript() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>configdata/judgeCanDeployDbscript",
                async: false,       //false:同步
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            $.each(resultData.data, function(index) {
                                if (resultData.data[0] == "ok") {
                                    $("#btnDbserversConfig").removeAttr("disabled");
                                    gCanDeployDbscript = "yes";
                                    $("[value='启用编辑模式']").each(function() {
                                        $(this).removeAttr("disabled");
                                    });
                                } else {
                                    $("#btnDbserversConfig").attr("disabled", true);
                                    gCanDeployDbscript = "no";
                                    $("[value='启用编辑模式']").each(function() {
                                        $(this).attr("disabled", true);
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
            $("#formatedCreatetimeBegin").val(getValveDateOfHalfYearBefore());
        }
        
        function openDbLinkConfigPage() {
            window.open("<%=basePath%>depdbservers/deploy_dbservers", "_blank");
        }

        function openEditStyle(deploydbserversid, belong) {
            var deployDbserversDTO = {};
            deployDbserversDTO.belong = belong;

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
                            var dblinkSelectHtml = "<select id='selDblink' onchange='assignTempValue(this);'>";
                            dblinkSelectHtml += "<option value=''></option>";
                            if (deployDbserversList != null && deployDbserversList.length > 0) {
                                $.each(deployDbserversList, function(index) {
                                    var deployDbservers = deployDbserversList[index];
                                    if (deploydbserversid == deployDbservers.deploydbserversid) {
                                        dblinkSelectHtml += "<option value='" + deployDbservers.deploydbserversid + "' selected>";
                                    } else {
                                        dblinkSelectHtml += "<option value='" + deployDbservers.deploydbserversid + "'>";
                                    }
                                    dblinkSelectHtml += "数据库环境：" + deployDbservers.belongName + " | ";
                                    dblinkSelectHtml += "数据库链接名：" + deployDbservers.linkname + " | ";
                                    dblinkSelectHtml += "ip地址：" + deployDbservers.ip + " | ";
                                    dblinkSelectHtml += "端口：" + deployDbservers.port + " | ";
                                    dblinkSelectHtml += "数据库名称：" + deployDbservers.dbname;
                                    dblinkSelectHtml += "</option>";
                                });
                            }
                            dblinkSelectHtml += "</select>";
                            $("#detail_dblinkDesc").html(dblinkSelectHtml);
                            $("#fntDblinkDesc").css("color", "#ff0000");
                        }
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function assignTempValue(selDblink) {
            gDeployDbserversId = selDblink.value;
            if (gDeployDbserversId == "" || gDeployDbserversId == null) {
                $("#detail_dblinkDesc_desc").html("");
            } else {
                var deployDbserversDTO = {};
                deployDbserversDTO.deploydbserversid = gDeployDbserversId;

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
                                var deployDbservers = resultData.data[0];
                                var dbLinkDesc = deployDbservers.linkname + "(" + deployDbservers.linknamedesc + ")";
                                dbLinkDesc += "[" + deployDbservers.belongName + "--" + deployDbservers.ip;
                                dbLinkDesc += ":" + deployDbservers.port + "/" + deployDbservers.dbname + "]";
                                $("#detail_dblinkDesc_desc").html(dbLinkDesc);
                            }
                        }
                    },
                    error:function(resultData){
                        $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                    }
                });

            }
        }


        /**
         * 发布脚本
         */
        function doDeployDbscript() {
            if (gCanDeployDbscript == "no") {
                alert("您没有发布脚本的权限!");
                return;
            }
            if (gDeployDbscriptId == "") {
                alert("记录id不能为空！");
                return;
            }
            if (gDeployDbserversId == "" || gDeployDbserversId == null) {
                alert("必须选择数据库连接的描述！");
                return;
            }
            var deployDbscriptDTO = {};
            deployDbscriptDTO.deploydbscriptid = gDeployDbscriptId;
            deployDbscriptDTO.deploydbserversid = gDeployDbserversId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/deployDbscript",
                async: false,       //false:同步
                data:JSON.stringify(deployDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "success") {
                            alert("发布脚本成功");
                        } else {
                            alert(resultData.data[0]);
                        }
                        doQueryDetail(gDeployDbscriptId);
                        doQuery();
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }
        
        function highlightOrDisableDeployButton(deployDbscript) {
            //如果脚本不是成功或者正在执行状态并且当前用户有权操作脚本发布并且剩余未执行的脚本没被放弃执行，点亮发布按钮。
            if ((deployDbscript.executestatus != 1 && deployDbscript.executestatus != 2) && gCanDeployDbscript == "yes" && deployDbscript.isabandoned == 0) {
                $("#btnDeployDbScript").removeAttr("disabled");
                return;
            }
            $("#btnDeployDbScript").attr("disabled", true);
        }

        /**
         * 放弃发布脚本
         */
        function doAbandonDeployDbscript() {
            var queryDbscriptDTO = {};
            queryDbscriptDTO.deploydbscriptid = gDeployDbscriptId;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/abandonDeployDbscript",
                async: false,       //false:同步
                data:JSON.stringify(queryDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            alert(resultData.data[0]);
                            doQueryDetail(gDeployDbscriptId);
                        } else {
                            alert(resultData.data[0]);
                        }
                        doQuery();
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        /**
         * 申请重新发布脚本
         */
        function doApplyRedeployDbscript() {
            var unexecutedSql = $("#detail_unexecutedSql").val();
            if (unexecutedSql.replace(/(^s*)|(s*$)/g, "").length == 0) {
                alert("未经执行的sql为空语句，不能提交申请。");
                return;
            }
            if (gDeployDbscriptId == "") {
                alert("申请的脚本记录的id丢失了，不能提交申请。");
                return;
            }
            var forcetodoit = $("#forcetodoit").val();

            var applyRedeployDbscriptDTO = {};
            applyRedeployDbscriptDTO.deploydbscriptid = gDeployDbscriptId;
            applyRedeployDbscriptDTO.unexecutedSql = unexecutedSql;
            applyRedeployDbscriptDTO.forcetodoit = forcetodoit;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depdbscript/applyRedeployDbscript",
                async: false,       //false:同步
                data:JSON.stringify(applyRedeployDbscriptDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "update data successfully") {
                            alert(resultData.data[0]);
                            doQueryDetail(gDeployDbscriptId);
                            $("#tr_forcesubmit").hide();
                        } else {
                            alert(resultData.data[0]);
                            if (resultData.msg == "dangerous statement in it") {
                                $("#tr_forcesubmit").show();
                            }
                        }
                        doQuery();
                    }
                },
                error:function(resultData){
                    $("#serverStatus").html("发布系统停止运行，请耐心等待。。。");
                }
            });

        }

        function doQueryDetailAgain() {
            if (gDeployDbscriptId == null || gDeployDbscriptId == "") {
                return;
            }
            doQueryDetail(gDeployDbscriptId);
            doQuery();
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
                <input id="btnDbserversConfig" type="button" value="打开数据库连接配置管理的页面" onclick="openDbLinkConfigPage();">
            </td>
        </tr>
        <tr bgcolor="#5f9ea0"><td align="left" colspan="4">脚本发布情况查询--条件区域</td></tr>
        <tr>
            <td><font color="red">项目名称：</font>
                <select id="projectcode" onchange="initModules();">
                </select>
            </td>
            <td>模块：
                <select id="modulecode">
                </select>
            </td>
            <td>申请者姓名：
                <input id="applier" type="text">
            </td>
            <td>提交申请的日期范围：
                <input id="formatedCreatetimeBegin" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,1],to:'<'}"/>到
                <input id="formatedCreatetimeEnd" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,1],to:'<'}"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">脚本语句(模糊查询)：<br><textarea id="dbscript" cols="80" rows="3"></textarea></td>
            <td colspan="2">附加描述(模糊查询)：<br><textarea id="description" cols="80" rows="3"></textarea></td>
        </tr>
        <tr>
            <td>发布生产的日期范围：
                <input id="formatedExecutetimeBegin" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,1],to:'<'}"/>到
                <input id="formatedExecutetimeEnd" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,1],to:'<'}"/>
            </td>
            <td>脚本执行状态：
                <select id="executestatus">
                    <option value="">全部</option>
                    <option value="0">未执行</option>
                    <option value="1">执行成功</option>
                    <option value="-1">执行失败</option>
                </select>
                <br>
                是否需要执行的选项：
                <select id="showExcuteOption">
                    <option value="">全部</option>
                    <option value="yes">只显示需要执行的脚本</option>
                </select>
            </td>
            <td>执行报错原因(模糊查询)：<br><textarea id="failuremsg" cols="50" rows="4"></textarea></td>
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

    <div id="divDetailInfo" style="position: absolute; width: 1000px; height: 600px; border: 1px solid #08575B;top: 50px;left: 10px; background:#FFF; color:#000; z-index: 100; overflow:auto; display:none">
        <table align="center" width="100%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
            <tr><td colspan="2" align="center" bgcolor="#0000ff" style="color:#FFF">数据库脚本发布申请详细内容</td></tr>
            <tr>
                <td bgcolor='#ffe4c4'>申请记录ID:</td>
                <td id="detail_deploydbscriptid">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>数据库环境:</td>
                <td id="detail_belongDesc">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'><font id="fntDblinkDesc">数据库连接的描述:</font></td>
                <td>
                    <div id="detail_dblinkDesc">&nbsp;</div>
                    <div id="detail_dblinkDesc_desc">&nbsp;</div>
                </td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>项目名称:</td>
                <td id="detail_projectName">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>模块名称:</td>
                <td id="detail_moduleName">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>脚本语句:</td>
                <td><textarea id="detail_dbscript" cols="110" rows="10" readonly></textarea></td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>附加描述:</td>
                <td><textarea id="detail_description" cols="110" rows="4" readonly></textarea></td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>申请人:</td>
                <td id="detail_applier">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>申请时间:</td>
                <td id="detail_formatedCreateTime">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>执行人:</td>
                <td id="detail_executor">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>执行时间:</td>
                <td id="detail_formatedExecuteTime">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>执行状态:</td>
                <td id="detail_executeStatusDesc">&nbsp;</td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>执行报错信息:</td>
                <td><textarea id="detail_failuremsg" cols="110" rows="3" readonly></textarea></td>
            </tr>
            <tr>
                <td bgcolor='#ffe4c4'>已经执行的sql:</td>
                <td><textarea id="detail_executedSql" cols="110" rows="5" readonly></textarea></td>
            </tr>
            <tr id="tr_detail_unexecutedSql">
                <td bgcolor='#ffe4c4'>未经执行的sql:</td>
                <td><textarea id="detail_unexecutedSql" cols="110" rows="5" readonly></textarea></td>
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
            <tr id="tr_detail_isabandoned">
                <td bgcolor="#ffe4c4">是否已经放弃未执行的sql:</td>
                <td id="detail_isabandoned">&nbsp;</td>
            </tr>
        </table>
        <table width="100%">
            <tr>
                <td align="right">
                    <input id="btnRefreshContent" type="button" value="刷新页面" onclick="doQueryDetailAgain();">
                    <input id="btnAbandonDeployDbScript" type="button" value="放弃发布脚本" onclick="doAbandonDeployDbscript();">
                    <input id="btnApplyRedeployDbScript" type="button" value="申请重新发布脚本" onclick="doApplyRedeployDbscript();">
                    <input id="btnDeployDbScript" type="button" value="发布脚本" onclick="doDeployDbscript();">
                    <input type="button" value="关闭" onclick="doCloseDiv();">
                </td>
            </tr>
        </table>
    </div>

</body>
</html>
