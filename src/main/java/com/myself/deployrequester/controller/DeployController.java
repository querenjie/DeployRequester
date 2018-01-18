package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.DeployEnum;
import com.myself.deployrequester.biz.config.sharedata.SessionData;
import com.myself.deployrequester.bo.DeployResult;
import com.myself.deployrequester.model.DeployPerformanceDO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.DeployTestEnvDO;
import com.myself.deployrequester.service.DeployPerformanceService;
import com.myself.deployrequester.service.DeployRequesterService;
import com.myself.deployrequester.service.DeployService;
import com.myself.deployrequester.service.DeployTestEnvService;
import com.myself.deployrequester.util.json.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("deploy")
public class DeployController extends CommonMethodWrapper {
    @Autowired
    private DeployService deployService;
    @Autowired
    private DeployTestEnvService deployTestEnvService;
    @Autowired
    private DeployRequesterService deployRequesterService;
    @Autowired
    private DeployPerformanceService deployPerformanceService;

    @ResponseBody
    @RequestMapping(value="deployToTestEnv", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deployToTestEnv(HttpSession session, HttpServletRequest request) {
        JsonResult result = null;
        DeployResult deployResult = null;
        String deployRequestId = (String) session.getAttribute(SessionData.KEY_DEPLOY_REQUEST_ID);
        DeployTestEnvDO deployTestEnvDO = new DeployTestEnvDO();
        deployTestEnvDO.setDeployrequestid(deployRequestId);
        deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOYING.getCode()));   //1：正在发布；2：发布成功；-1：发布失败。

        try {
            //向t_deploy_testenv表中写数据
            int successCount = deployTestEnvService.insertDeployTestEnv(deployTestEnvDO);
            if (successCount == 1) {
                String testDeployUrl = (String) session.getAttribute(SessionData.KEY_TEST_DEPLOY_URL);
                if (StringUtils.isBlank(testDeployUrl)) {
                    result = JsonResult.createFailed("从session中获取不到发布测试环境的url");
                    return result;
                }
                DeployRequesterDO deployRequesterDO = (DeployRequesterDO) session.getAttribute(SessionData.KEY_DEPLOY_REQUEST);

                String clientIpAddr = getIpAddr(request);
                Map<String, Object> otherParamMap = new HashMap<String, Object>();
                otherParamMap.put("clientIpAddr", clientIpAddr);
                //发布
                deployResult = deployService.deploy(testDeployUrl, otherParamMap);

                if (deployResult != null) {
                    if (deployResult.isSuccessDeployed()) {
                        deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()));
                        deployTestEnvDO.setDeployendtime(new Date());
                        deployTestEnvDO.setDuration(deployResult.getDuration());

                        deployRequesterDO.setDeploystatusfortestenv(Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()));
                    } else {
                        deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOY_FAILED.getCode()));
                        deployRequesterDO.setDeploystatusfortestenv(Short.valueOf(DeployEnum.DEPLOY_FAILED.getCode()));
                    }
                    deployTestEnvDO.setFeedbackinfo(deployResult.getResultMsg());

                    //向t_deploy_testenv表中更新记录
                    int updateSuccessCountForDeployTestEnv = deployTestEnvService.updateByPrimaryKeySelective(deployTestEnvDO);
                    if (updateSuccessCountForDeployTestEnv == 1) {
                        //记录更新成功后，向t_deploy_request表中更新记录
                        DeployRequesterDO deployRequesterDO1 = new DeployRequesterDO();
                        deployRequesterDO1.setProjectcode(deployRequesterDO.getProjectcode());
                        deployRequesterDO1.setDeployrequestid(deployRequesterDO.getDeployrequestid());
                        deployRequesterDO1.setDeploystatusfortestenv(deployTestEnvDO.getIssuccess());
                        int updateSuccessCountForDeployRequest = deployRequesterService.updateByPrimaryKeySelective(deployRequesterDO1);
                        if (updateSuccessCountForDeployRequest != 1) {
                            result = JsonResult.createFailed("update t_deploy_request failed.");
                            return result;
                        }
                        //向t_deploy_performance表中写数据
                        DeployPerformanceDO deployPerformanceDO = new DeployPerformanceDO();
                        deployPerformanceDO.setProjectcode(deployRequesterDO.getProjectcode());
                        deployPerformanceDO.setModulecode(deployRequesterDO.getModulecode());
                        deployPerformanceDO.setModuletypecode(deployRequesterDO.getModuletypecode());
                        deployPerformanceDO.setDeployduration(deployResult.getDuration());
                        deployPerformanceService.recordPerformance(deployPerformanceDO);
                    } else {
                        result = JsonResult.createFailed("update t_deploy_testenv failed.");
                        return result;
                    }
                }
            } else {
                result = JsonResult.createFailed("insert t_deploy_testenv failed");
                return result;
            }
        } catch (Exception e) {
            result = JsonResult.createFailed("写发布过程数据异常:" + e);
            return result;
        }
        result = JsonResult.createSuccess("ok");
        result.addData(deployResult);
        return  result;
    }

}
