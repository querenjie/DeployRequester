package com.myself.deployrequester.biz.config.sharedata;

import com.myself.deployrequester.bo.Speech;

import java.util.ArrayList;
import java.util.List;

/**
 *  公告栏
 * Created by QueRenJie on ${date}
 */
public class NoticeBoardConfig {
    //公告信息
    public static String NOTICE = "";

    //0:不刷新页面;1:强制刷新大家的页面
    public static int REFRESH_PAGE = 0;

    //强制刷新页面前的延后毫秒数。仅在REFRESH_PAGE=1时起作用
    public static int DELAYTIME_BEFORE_REFRESH_PAGE = 5000;

    //存放给对方的喊话内容。
    public static List<Speech> SPEECH_LIST = new ArrayList<Speech>();
}
