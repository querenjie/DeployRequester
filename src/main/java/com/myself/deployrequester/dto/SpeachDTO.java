package com.myself.deployrequester.dto;

/**
 * Created by QueRenJie on ${date}
 */
public class SpeachDTO {
    private String speakContent;       //喊话内容
    private String destIps;             //被喊话人的IP。多个IP之间用逗号隔开。

    public String getSpeakContent() {
        return speakContent;
    }

    public void setSpeakContent(String speakContent) {
        this.speakContent = speakContent;
    }

    public String getDestIps() {
        return destIps;
    }

    public void setDestIps(String destIps) {
        this.destIps = destIps;
    }

}
