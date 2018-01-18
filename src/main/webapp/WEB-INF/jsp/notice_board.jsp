
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>发布瞬时公告</title>
    <script src="<%=basePath%>resources/core/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript">
        var checkedIpArray = new Array();   //存放选中的ip

        function broadcast(whichButton) {
            var notice = $("#taNotice").val();
            var noticeBoardDTO = {};
            if (whichButton.id == "broadcastNoticeButton") {
                if (notice == "") {
                    alert("发布公告内容不能为空！");
                    return;
                }
                $("#broadcastNoticeButton").attr("disabled", true);
                noticeBoardDTO.notice = notice;
                noticeBoardDTO.refreshPage = 0;
            }

            if (whichButton.id == "broadcastReloadPageButton") {
                $("#broadcastReloadPageButton").attr("disabled", true);
                noticeBoardDTO.notice = notice;
                noticeBoardDTO.refreshPage = 1;
            }

            $.ajax({
                type: "POST",
                url: "<%=basePath%>noticeboard/assignnotice",
                data:JSON.stringify(noticeBoardDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (whichButton.id == "broadcastNoticeButton") {
                            $("#broadcastNoticeButton").removeAttr("disabled");
                        }
                        if (whichButton.id == "broadcastReloadPageButton") {
                            $("#broadcastReloadPageButton").removeAttr("disabled");
                        }
                    }
                },
                error:function(resultData){
                    alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function showAliveUser() {
            $.ajax({
                type: "POST",
                url: "<%=basePath%>noticeboard/showAliveUser",
                data:"",//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "ok") {
                            if (resultData.data != null && resultData.data.length == 1) {
                                var strHtml = "";
                                var ipAndCrewNameArray = resultData.data[0];
                                if (ipAndCrewNameArray.length > 0) {
                                    var columnNum = 3;      //一行显示多少列
                                    var currentPointToColumn = 0;   //指示当前指向哪一列
                                    strHtml = "<table width='100%' border=1 bordercolor='#a0c6e5' style='border-collapse:collapse;'><tr><td colspan=" + columnNum + " align='center' bgcolor='yellow'>以下是正在线上的人员</td></tr>";
                                    for (var i = 0; i < ipAndCrewNameArray.length; i++) {
                                        var arrIpAndCrewName = ipAndCrewNameArray[i].split("_");
                                        var ip = arrIpAndCrewName[0];
                                        var crewName = arrIpAndCrewName[1];
                                        if (currentPointToColumn == 0) {
                                            strHtml += "<tr>";
                                        }
                                        strHtml += "<td width='" + (100/columnNum) + "%'>";
                                        strHtml += "<input name='ck' type='checkbox' value='" + ip + "'>";
                                        strHtml += ipAndCrewNameArray[i];
                                        strHtml += "</td>";
                                        if (++currentPointToColumn == columnNum) {
                                            strHtml += "</tr>";
                                            currentPointToColumn = 0;
                                        }
                                    }
                                    if (currentPointToColumn > 0 && currentPointToColumn < (columnNum -1)) {
                                        for (var i = 0; i < columnNum - currentPointToColumn; i++) {
                                            strHtml += "<td width='" + (100/columnNum) + "%'>&nbsp;</td>";
                                        }
                                        strHtml += "</tr>";
                                    }
                                    strHtml += "</table>";
                                }
                                $("#alived_ips").html(strHtml);
                            }
                        }
                    }
                },
                error:function(resultData){
                    alert("发布系统停止运行，请耐心等待。。。");
                }
            });
        }

        function callThem() {
            var speakContent = $("#taNotice2").val();
            if (speakContent == "") {
                alert("喊话内容不能为空。");
                return;
            }

            checkedIpArray.splice(0, checkedIpArray.length);
            $("input[name='ck']:checked").each(
                function() {
                    checkedIpArray.push($(this).val());
                }
            );
            var destIps = checkedIpArray.join(",");
            if (destIps == "") {
                alert("请选择至少一个在线人员。");
                return;
            }

            var speachDTO = {};
            speachDTO.speakContent = speakContent;
            speachDTO.destIps = destIps;

            $.ajax({
                type: "POST",
                url: "<%=basePath%>noticeboard/addSpeach",
                data:JSON.stringify(speachDTO),//json序列化
                datatype:"json", //此处不能省略
                contentType: "application/json; charset=utf-8",//此处不能省略
                success:function(resultData){
                    if (resultData != null) {
                        if (resultData.msg == "ok") {
                            alert("喊话内容已经发送了，对方很快就会收到。");
                        } else {
                            alert("喊话过程有异常。");
                        }
                    }
                },
                error:function(resultData){
                    alert("发布系统停止运行，请耐心等待。。。");
                }
            });

        }
    </script>
</head>
<body>
    <font color="blue">建议使用chrome浏览器，在其他浏览器上运行有可能不正常。(chrome is a strong recommendation, others may cause malfunction.)</font>

    <table align="center" width="40%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
        <tr bgcolor="#5f9ea0"><td align="left" colspan="10">瞬时公告内容</td></tr>
        <tr>
            <td><textarea id="taNotice" cols="88" rows="16"></textarea></td>
        </tr>
        <tr>
            <td>
                <input id="broadcastNoticeButton" type="button" value="发布公告" onclick="broadcast(this);">
                <input id="broadcastReloadPageButton" type="button" value="强制刷新大家的页面" onclick="broadcast(this);">
            </td>
        </tr>
    </table>
    <hr>
    <table align="center" width="40%"  border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;">
        <tr bgcolor="#5f9ea0"><td align="left" colspan="2">向指定人员喊话的内容</td></tr>
        <tr>
            <td colspan="2"><textarea id="taNotice2" cols="88" rows="10" colspan="2"></textarea></td>
        </tr>
        <tr>
            <td>
                <input id="showAliveUserButton" type="button" value="点击我查看当前在线的人员" onclick="showAliveUser();">
            </td>
            <td align="right">
                勾选以下在线人员，然后点击<input id="callThemButton" type="button" value="喊话" onclick="callThem();">
            </td>
        </tr>
        <tr>
            <td id="alived_ips" colspan="2"></td>
        </tr>
    </table>

</body>
</html>
