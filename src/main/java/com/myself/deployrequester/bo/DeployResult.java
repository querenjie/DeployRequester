package com.myself.deployrequester.bo;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployResult {
    public boolean isSuccessDeployed;  //是否发布成功
    public String resultMsg;           //发布返回的信息
    public String suggestion;
    public long duration;              //发布过程用的多少毫秒


    public boolean isSuccessDeployed() {
        return isSuccessDeployed;
    }

    public void setSuccessDeployed(boolean successDeployed) {
        isSuccessDeployed = successDeployed;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
