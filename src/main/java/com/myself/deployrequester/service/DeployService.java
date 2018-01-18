package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.DeployResult;
import com.myself.deployrequester.bo.OutcomeConfig;
import com.myself.deployrequester.dto.QueryCriteriaDTO;
import com.myself.deployrequester.util.httpclient.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployService {
    public DeployResult deploy(String deployUrl, Map<String, Object> otherParamMap) {
        /**********配置发布的结果 (begin) **************************************************
        addOutcomeConfig("startup", true, "web 模块已启动");
        addOutcomeConfig("other", false, "有人在发布这个模块");
        addOutcomeConfig("mvn package_failure", false, "打包发生错误");
        addOutcomeConfig("is error", false, "未知错误");
        addOutcomeConfig("rest started success", true, "rest服务启动正常");
        addOutcomeConfig("docker image", false, "发布过程不稳定，可以再重新试一下");
        addOutcomeConfig("provider started success", true, "provider服务启动正常");
        addOutcomeConfig("ERROR does not started", true, "发布失败了");
        **********配置发布的结果 (end) **************************************************/
        DeployResult deployResult = new DeployResult();
        Exception exception = new Exception();

        long begin = System.currentTimeMillis();
        String resultMsg = HttpClientUtil.doGet(deployUrl, otherParamMap, exception);
        long duration = System.currentTimeMillis() - begin;
        deployResult.setDuration(duration);

        if (resultMsg == null) {
            deployResult.setSuccessDeployed(false);
            deployResult.setResultMsg("发布服务器无法返回数据，可能存在问题。异常：" + exception);
            deployResult.setSuggestion("建议联系南宁那边把服务器弄好。");
        } else {
            boolean isMatchKeyWords = false;     //结果是否能匹配关键词
            if (ConfigData.OUTCOME_CONFIG != null) {
                for (OutcomeConfig outcomeConfig : ConfigData.OUTCOME_CONFIG) {
                    String[] keywords = outcomeConfig.getKeywordsInOutcomeMsg().split(" ");
                    if (keywords != null) {
                        int index = -1;
                        for (String word : keywords) {
                            if (!" ".equals(word)) {
                                int wordlocation = resultMsg.indexOf(word);
                                if (wordlocation > index) {
                                    index = wordlocation;
                                    isMatchKeyWords = true;
                                } else {
                                    isMatchKeyWords = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (isMatchKeyWords) {
                        deployResult.setSuccessDeployed(outcomeConfig.isDeploySuccess());
                        deployResult.setResultMsg(resultMsg);
                        deployResult.setSuggestion(outcomeConfig.getSuggestion());
                        return deployResult;
                    }

                }
            }
            deployResult.setSuccessDeployed(false);
            deployResult.setResultMsg(resultMsg);
        }
        return deployResult;
    }



}
