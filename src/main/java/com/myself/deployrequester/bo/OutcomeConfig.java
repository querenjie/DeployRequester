package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class OutcomeConfig {
    private String keywordsInOutcomeMsg;   //返回消息中的某些能判断结果是成功还是失败的关键字，关键字之间用空格隔开
    private boolean isDeploySuccess;
    private String suggestion;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getKeywordsInOutcomeMsg() {
        return keywordsInOutcomeMsg;
    }

    public void setKeywordsInOutcomeMsg(String keywordsInOutcomeMsg) {
        this.keywordsInOutcomeMsg = keywordsInOutcomeMsg;
    }

    public boolean isDeploySuccess() {
        return isDeploySuccess;
    }

    public void setDeploySuccess(boolean deploySuccess) {
        isDeploySuccess = deploySuccess;
    }
}
