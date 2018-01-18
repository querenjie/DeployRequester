package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class Speech {
    private String speakContent;        //喊话内容
    private String sourceIp;            //喊话人的IP
    private String destIps;             //被喊话人的IP。多个IP之间用逗号隔开。
    private String speakTime;           //喊话的时间。年月日时分秒
    private String speaker;             //喊话人姓名
    private long speakTimeMillis;       //喊话时间的毫秒表示形式

    public String getSpeakContent() {
        return speakContent;
    }

    public void setSpeakContent(String speakContent) {
        this.speakContent = speakContent;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestIps() {
        return destIps;
    }

    public void setDestIps(String destIps) {
        this.destIps = destIps;
    }

    public String getSpeakTime() {
        return speakTime;
    }

    public void setSpeakTime(String speakTime) {
        this.speakTime = speakTime;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public long getSpeakTimeMillis() {
        return speakTimeMillis;
    }

    public void setSpeakTimeMillis(long speakTimeMillis) {
        this.speakTimeMillis = speakTimeMillis;
    }
}
