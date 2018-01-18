package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class NoticeBoard {
    private String notice;      //公告信息
    private int refreshPage;    //0:不刷新页面;1:强制刷新大家的页面
    private int delaytimeBeforeRefreshPage;     //强制刷新页面前的延后毫秒数。仅在REFRESH_PAGE=1时起作用
    private String describe;    //页面刷新前的通知


    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getRefreshPage() {
        return refreshPage;
    }

    public void setRefreshPage(int refreshPage) {
        this.refreshPage = refreshPage;
    }

    public int getDelaytimeBeforeRefreshPage() {
        return delaytimeBeforeRefreshPage;
    }

    public void setDelaytimeBeforeRefreshPage(int delaytimeBeforeRefreshPage) {
        this.delaytimeBeforeRefreshPage = delaytimeBeforeRefreshPage;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
