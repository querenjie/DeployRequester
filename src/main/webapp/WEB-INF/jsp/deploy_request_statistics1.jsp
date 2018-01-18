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
    <title>测试情况统计</title>
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
            var groupColumnsFlag = $("#groupColumnsFlag").val();

            if (projectCode == "") {
                alert("必须选择项目");
                return;
            }
            if (applyBeginDate == "") {
                alert("查询条件中的起始日期不能为空");
                return;
            }
            if (applyEndDate != "" && applyEndDate < applyBeginDate) {
                alert("查询条件中的结束日期不能小于" + applyBeginDate);
                return;
            }

            $("#processAction").html("正在查询中......");

            var QueryCriteriaStatistics1DTO = {};
            QueryCriteriaStatistics1DTO.projectcode = projectCode;
            QueryCriteriaStatistics1DTO.modulecode = moduleCode;
            QueryCriteriaStatistics1DTO.moduletypecode = moduleTypeCode;
            QueryCriteriaStatistics1DTO.begindate = applyBeginDate;
            QueryCriteriaStatistics1DTO.enddate = applyEndDate;
            QueryCriteriaStatistics1DTO.groupColumnsFlag = groupColumnsFlag;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>depstatistics/query",
                data:JSON.stringify(QueryCriteriaStatistics1DTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    var recordCount = 0;
                    var tableResultContent = "";
                    if (resultData != null) {
                        if (resultData.data != null && resultData.data.length > 0) {
                            var deployRequesterStatistics1List = resultData.data[0];
                            if (deployRequesterStatistics1List != null && deployRequesterStatistics1List.length > 0) {
                                recordCount = deployRequesterStatistics1List.length;

                                var headContent = "<td><b>开发人员</b></td><td><b>未测试的数量</b></td><td><b>测试通过的数量</b></td><td><b>测试不通过的数量</b></td><td><b>无需测试的数量</b></td><td><b>由测试人员测试通过的数量</b></td><td><b>测试通过率</b></td><td><b>排名</b></td>";
                                if (groupColumnsFlag == 1) {
                                    headContent = "<td><b>项目名称</b></td>" + headContent;
                                } else if (groupColumnsFlag == 2) {
                                    headContent = "<td><b>项目名称</b></td><td><b>模块名称</b></td>" + headContent;
                                } else if (groupColumnsFlag == 3) {
                                    headContent = "<td><b>项目名称</b></td><td><b>模块名称</b></td><td><b>模块类型</b></td>" + headContent;
                                }
                                tableResultContent += "<tr bgcolor='#ff8c00' align='center'>" + headContent + "</tr>";
                                var lastRankNo = -1;    //存放上一条记录的排名
                                var lastProjectName = "";   //存放上一条记录的项目名称
                                var lastModuleCodeName = "";    //存放上一条记录的模块名称
                                var lastModuleTypeName = "";    //存放上一条记录的模块类型
                                var lastPassRate = -1;          //存放上一条记录的测试通过率
                                $.each(deployRequesterStatistics1List, function(index) {
                                    var deployRequesterStatistics1 = deployRequesterStatistics1List[index];
                                    var projectName = deployRequesterStatistics1.projectName;
                                    var moduleCodeName = deployRequesterStatistics1.moduleCodeName;
                                    var moduleTypeName = deployRequesterStatistics1.moduleTypeName;
                                    var rankno = deployRequesterStatistics1.rankno;
                                    var developer = deployRequesterStatistics1.developer;
                                    var nottested = deployRequesterStatistics1.nottested;
                                    var testsuccess = deployRequesterStatistics1.testsuccess;
                                    var testfailed = deployRequesterStatistics1.testfailed;
                                    var donotneedtest = deployRequesterStatistics1.donotneedtest;
                                    var testsuccessreally = deployRequesterStatistics1.testsuccessreally;
                                    var passrate = deployRequesterStatistics1.passrate;

                                    if (groupColumnsFlag == 1) {
                                        if (projectName == lastProjectName && passrate == lastPassRate) {
                                            rankno = lastRankNo;
                                        }
                                    } else if (groupColumnsFlag == 2) {
                                        if (projectName == lastProjectName && moduleCodeName == lastModuleCodeName && passrate == lastPassRate) {
                                            rankno = lastRankNo;
                                        }
                                    } else if (groupColumnsFlag == 3) {
                                        if (projectName == lastProjectName && moduleCodeName == lastModuleCodeName && moduleTypeName == lastModuleTypeName && passrate == lastPassRate) {
                                            rankno = lastRankNo;
                                        }
                                    }
                                    lastRankNo = rankno;
                                    lastPassRate = passrate;
                                    lastProjectName = projectName;
                                    lastModuleCodeName = moduleCodeName;
                                    lastModuleTypeName = moduleTypeName;

                                    var rowContent = "<td>" + developer + "</td>";
                                    rowContent += "<td align='right'>" + nottested + "</td>";
                                    rowContent += "<td align='right'>" + testsuccess + "</td>";
                                    rowContent += "<td align='right'>" + testfailed + "</td>";
                                    rowContent += "<td align='right'>" + donotneedtest + "</td>";
                                    rowContent += "<td align='right'>" + testsuccessreally + "</td>";
                                    rowContent += "<td align='right'>" + passrate + "%" + "</td>";
                                    rowContent += "<td align='right' title='根据测试通过率排名的'><b>第 " + rankno + " 名</b></td>";
                                    if (groupColumnsFlag == 1) {
                                        rowContent = "<td>" + projectName + "</td>" + rowContent;
                                    } else if (groupColumnsFlag == 2) {
                                        rowContent = "<td>" + projectName + "</td><td>" + moduleCodeName + "</td>" + rowContent;
                                    } else if (groupColumnsFlag == 3) {
                                        rowContent = "<td>" + projectName + "</td><td>" + moduleCodeName + "</td><td>" + moduleTypeName + "</td>" + rowContent;
                                    }
                                    tableResultContent += "<tr>" + rowContent + "</tr>";
                                });
                                $("#tblResult").html(tableResultContent);
                                $("#processAction").html("以下就是查询结果,共 " + recordCount + " 条记录。");
                            }  else if (deployRequesterStatistics1List != null && deployRequesterStatistics1List.length == 0) {
                                tableResultContent = "";
                                recordCount = 0;
                                $("#tblResult").html(tableResultContent);
                                $("#processAction").html("以下就是查询结果,共 " + recordCount + " 条记录。");
                            }
                        }
                    }
                },
                error:function(resultData){
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
        <tr bgcolor="#5f9ea0"><td align="left" colspan="4">统计测试情况    条件区域</td></tr>
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
            <td>提交申请的日期范围：<font color="red">(第一个日期项不能为空)</font>
                <input id="applyBeginDate" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,2],to:'<'}"/>到
                <input id="applyEndDate" type="text" data-beatpicker="true" data-beatpicker-disable="{from:[2018,1,2],to:'<'}"/>
            </td>
        </tr>
        <tr>
            <td colspan="3">分组字段:
                <select id="groupColumnsFlag">
                    <option value="1">项目名称</option>
                    <option value="2">项目名称+模块名称</option>
                    <option value="3">项目名称+模块名称+模块类型</option>
                </select>
            </td>
            <td>
                <input type="button" value="执行统计" onclick="doQuery();">
            </td>
        </tr>
    </table>
    <br>
    <table align="center" width="80%" border="0"><tr><td>关于测试通过率的解释：测试通过率 = 由测试人员测试通过的数量 / (未测试的数量 + 测试通过的数量 + 测试不通过的数量 - 开发人员自己点击测试通过的数量)</td></tr></table>
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
