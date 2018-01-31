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
    <title>标记发布生产的页面</title>
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

        function doMarkDeployedToProdRecords() {
            var projectCode = $("#projectcode").val();
            var moduleCode = $("#modulecode").val();
            var moduleTypeCode = $("#moduletypecode").val();
            var deploystatusforprodenv = $("#deploystatusforprodenv").val();
            var deployToProdEnvTime = $("#deployDate").val() + " " + $("#deployHour").val() + ":" + $("#deployMinutes").val() + ":00";
            if (projectCode == "" || deployToProdEnvTime == "") {
                $("#resultMsg").html("项目和发布时间都不能为空");
                return;
            }
            if (deploystatusforprodenv == "") {
                $("#resultMsg").html("必须选择发布环境");
                return;
            }
            if (deployToProdEnvTime.length != 19) {
                $("#resultMsg").html("发布时间格式不对。至少不能为空");
                return;
            }

            if (confirm("确定条件输入没问题吗，谨慎操作！")) {
                $("#resultMsg").html("正在更新记录中......");

                var QueryCriteriaDTO = {};
                QueryCriteriaDTO.projectcode = projectCode;
                QueryCriteriaDTO.modulecode = moduleCode;
                QueryCriteriaDTO.moduletypecode = moduleTypeCode;
                QueryCriteriaDTO.deploystatusforprodenv = deploystatusforprodenv;
                QueryCriteriaDTO.deployToProdEnvTime = deployToProdEnvTime;

                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>depquery/markDeployedProdEnvRecords",
                    data:JSON.stringify(QueryCriteriaDTO),//json序列化
                    datatype:"json", //此处不能省略
                    contentType: "application/json; charset=utf-8",//此处不能省略
                    success:function(resultData){
                        if (resultData != null) {
                            if (resultData.msg == "update data successfully") {
                                if (resultData.data != null && resultData.data.length > 0) {
                                    var updatedRecordCount = resultData.data[0];
                                    $("#resultMsg").html("成功修改了" + updatedRecordCount + "条记录");
                                }
                            } else {
                                $("#resultMsg").html(resultData.msg);
                            }
                        }
                    },
                    error:function(resultData){
                        $("#resultMsg").html("发布系统停止运行，请耐心等待。。。");
                    }
                });
            }
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
        <tr bgcolor="#5f9ea0"><td align="left" colspan="4">条件区域</td></tr>
        <tr>
            <td width="20%"><font color="red">项目名称：</font></td>
            <td>
                <select id="projectcode" onchange="initModules();">
                </select>
            </td>
        </tr>
        <tr>
            <td>模块：</td>
            <td>
                <select id="modulecode">
                </select>
            </td>
        </tr>
        <tr>
            <td>模块类型：</td>
            <td>
                <select id="moduletypecode">
                </select>
            </td>
        </tr>
        <tr>
            <td><font color="red">发布环境：</font> </td>
            <td>
                <select id="deploystatusforprodenv">
                    <option value=""></option>
                    <option value="3">预发布</option>
                    <option value="2">生产</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><font color="red">发布的时间：</font></td>
            <td>
                <input id="deployDate" type="text" data-beatpicker="true"/>
                <select id="deployHour">
                    <option value="00">00</option>
                    <option value="01">01</option>
                    <option value="02">02</option>
                    <option value="03">03</option>
                    <option value="04">04</option>
                    <option value="05">05</option>
                    <option value="06">06</option>
                    <option value="07">07</option>
                    <option value="08">08</option>
                    <option value="09">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                </select>
                时
                <select id="deployMinutes">
                    <option value="00">00</option>
                    <option value="01">01</option>
                    <option value="02">02</option>
                    <option value="03">03</option>
                    <option value="04">04</option>
                    <option value="05">05</option>
                    <option value="06">06</option>
                    <option value="07">07</option>
                    <option value="08">08</option>
                    <option value="09">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                    <option value="24">24</option>
                    <option value="25">25</option>
                    <option value="26">26</option>
                    <option value="27">27</option>
                    <option value="28">28</option>
                    <option value="29">29</option>
                    <option value="30">30</option>
                    <option value="31">31</option>
                    <option value="32">32</option>
                    <option value="33">33</option>
                    <option value="34">34</option>
                    <option value="35">35</option>
                    <option value="36">36</option>
                    <option value="37">37</option>
                    <option value="38">38</option>
                    <option value="39">39</option>
                    <option value="40">40</option>
                    <option value="41">41</option>
                    <option value="42">42</option>
                    <option value="43">43</option>
                    <option value="44">44</option>
                    <option value="45">45</option>
                    <option value="46">46</option>
                    <option value="47">47</option>
                    <option value="48">48</option>
                    <option value="49">49</option>
                    <option value="50">50</option>
                    <option value="51">51</option>
                    <option value="52">52</option>
                    <option value="53">53</option>
                    <option value="54">54</option>
                    <option value="55">55</option>
                    <option value="56">56</option>
                    <option value="57">57</option>
                    <option value="58">58</option>
                    <option value="59">59</option>
                </select>
                分
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <font id="resultMsg"></font>
                <input type="button" value="设置为已上生产" onclick="doMarkDeployedToProdRecords();">
            </td>
        </tr>
    </table>
</body>
</html>
