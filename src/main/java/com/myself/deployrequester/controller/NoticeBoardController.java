package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.NoticeBoardConfig;
import com.myself.deployrequester.bo.NoticeBoard;
import com.myself.deployrequester.bo.Speech;
import com.myself.deployrequester.dto.NoticeBoardDTO;
import com.myself.deployrequester.dto.SpeachDTO;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("noticeboard")
public class NoticeBoardController extends CommonMethodWrapper {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(NoticeBoardController.class);

    @RequestMapping("/notice_board")
    public String gotoNoticeBoardForm() {
        return "notice_board";
    }

    @ResponseBody
    @RequestMapping(value="/assignnotice", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult assignNotice(@RequestBody NoticeBoardDTO noticeBoardDTO) {
        JsonResult result;
        NoticeBoardConfig.NOTICE = noticeBoardDTO.getNotice();
        NoticeBoardConfig.REFRESH_PAGE = noticeBoardDTO.getRefreshPage();
        Log4jUtil.info(logger, "NoticeBoardConfig.NOTICE="+NoticeBoardConfig.NOTICE);
        Log4jUtil.info(logger, "NoticeBoardConfig.REFRESH_PAGE="+NoticeBoardConfig.REFRESH_PAGE);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NoticeBoardConfig.NOTICE = "";
        NoticeBoardConfig.REFRESH_PAGE = 0;
        result = JsonResult.createSuccess("ok");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getnotice", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getNotice(HttpServletRequest request) {
        /********此操作用于表示这个ip还活着 (begin) *********************/
        String clientIpAddr = getIpAddr(request);
        ConfigData.IP_ALIVED_MAP.put(clientIpAddr, Long.valueOf(System.currentTimeMillis()));
        /********此操作用于表示这个ip还活着 ( end ) *********************/

        JsonResult result;
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setNotice(NoticeBoardConfig.NOTICE);
        noticeBoard.setRefreshPage(NoticeBoardConfig.REFRESH_PAGE);
        noticeBoard.setDelaytimeBeforeRefreshPage(NoticeBoardConfig.DELAYTIME_BEFORE_REFRESH_PAGE);
        if (noticeBoard.getRefreshPage() == 1) {
            noticeBoard.setDescribe("页面将在" + (noticeBoard.getDelaytimeBeforeRefreshPage() / 1000) + "秒后强制刷新!");
        }
        result = JsonResult.createSuccess("ok");
        result.addData(noticeBoard);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/showAliveUser", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult showAliveUser() {
        JsonResult result;
        List<String> ipAndCrewNameList = new ArrayList<String>();

        if (ConfigData.IP_ALIVED_MAP != null) {
            final long VALVE_FOR_DELETE = 10000;    //超过10秒不活跃的都要去除
            long currentTimeMillis = System.currentTimeMillis();
            Set<String> deleteSet = new HashSet<String>();  //存放将要删除的ip
            Iterator<Map.Entry<String, Long>> iterator = ConfigData.IP_ALIVED_MAP.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Long> entry = iterator.next();
                long aliveTimeMillis = entry.getValue().longValue();
                if (currentTimeMillis - aliveTimeMillis > VALVE_FOR_DELETE) {
                    deleteSet.add(entry.getKey());
                }
            }
            for (String ip : deleteSet) {
                ConfigData.IP_ALIVED_MAP.remove(ip);
            }
        }

        Iterator<Map.Entry<String, Long>> iterator = ConfigData.IP_ALIVED_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String ip = entry.getKey();
            String crewName = ConfigData.IP_CREWNAME_MAPPING.get(ip);
            ipAndCrewNameList.add(ip + "_" + crewName);
        }

        result = JsonResult.createSuccess("ok");
        result.addData(ipAndCrewNameList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/addSpeach", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult addSpeach(@RequestBody SpeachDTO speachDTO, HttpServletRequest request) {
        JsonResult result;

        final long VALVE_FOR_DELETE = 24 * 60 * 60 * 1000;    //一天之前没消费的喊话都删除
        String clientIpAddr = getIpAddr(request);
        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        Speech speech = new Speech();
        BeanUtils.copyProperties(speachDTO, speech, false);
        speech.setSourceIp(clientIpAddr);
        speech.setSpeaker(crewName);
        speech.setSpeakTime(dateFormat.format(now));
        speech.setSpeakTimeMillis(System.currentTimeMillis());

        //将过期的消息删除掉
        for (Speech speech1 : NoticeBoardConfig.SPEECH_LIST) {
            if (speech.getSpeakTimeMillis() - speech1.getSpeakTimeMillis() > VALVE_FOR_DELETE) {
                NoticeBoardConfig.SPEECH_LIST.remove(speech1);
            }
        }

        NoticeBoardConfig.SPEECH_LIST.add(speech);

        result = JsonResult.createSuccess("ok");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getSpeech", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getSpeech(HttpServletRequest request) {
        /********此操作用于表示这个ip还活着 (begin) *********************/
        String clientIpAddr = getIpAddr(request);
        ConfigData.IP_ALIVED_MAP.put(clientIpAddr, Long.valueOf(System.currentTimeMillis()));
        /********此操作用于表示这个ip还活着 ( end ) *********************/

        JsonResult result;
        result = JsonResult.createSuccess("ok");

        for (Speech speech : NoticeBoardConfig.SPEECH_LIST) {
            if (StringUtils.isBlank(speech.getDestIps())) {
                removeSpeech(speech);
                continue;
            }
            if (speech.getDestIps().indexOf(clientIpAddr) > -1) {
                String restIpsStr = getRestIpsStr(clientIpAddr, speech.getDestIps());
                speech.setDestIps(restIpsStr);

                result.addData(speech);
                break;
            }
        }

        return result;
    }

    /**
     * 用同步的操作删除list中的speech对象
     * @param speech
     */
    synchronized private void removeSpeech(Speech speech) {
        NoticeBoardConfig.SPEECH_LIST.remove(speech);
    }

    /**
     * 获取剩下的ip字符串
     * @param ip
     * @param ips
     * @return
     */
    private String getRestIpsStr(String ip, String ips) {
        if (StringUtils.isBlank(ips)) {
            return "";
        }

        String restIpsStr = "";
        List<String> tempList = new ArrayList<String>();
        String[] ipsArray = ips.split(",");
        if (ipsArray != null) {
            for (String oneIp : ipsArray) {
                if (!ip.equals(oneIp.trim())) {
                    tempList.add(oneIp.trim());
                }
            }
        }
        for (int i = 0; i < tempList.size(); i++) {
            if (i < tempList.size() - 1) {
                restIpsStr += tempList.get(i) + ",";
            } else {
                restIpsStr += tempList.get(i);
            }
        }

        return restIpsStr;
    }

}
