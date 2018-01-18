package com.myself.deployrequester.dto;

/**
 * Created by QueRenJie on ${date}
 */
public class NoticeBoardDTO {
    private String notice;      //公告信息
    private int refreshPage;    //0:不刷新页面;1:强制刷新大家的页面

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

}
