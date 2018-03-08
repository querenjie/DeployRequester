package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.DeployEnum;
import com.myself.deployrequester.biz.config.sharedata.DeploySwitchEnum;
import com.myself.deployrequester.biz.config.sharedata.SessionData;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.bo.DeployResult;
import com.myself.deployrequester.bo.UrlSummary;
import com.myself.deployrequester.dto.DeployRequesterDTO;
import com.myself.deployrequester.globalvar.DeployStatus;
import com.myself.deployrequester.model.DeployAuditDO;
import com.myself.deployrequester.model.DeployPerformanceDO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.DeployTestEnvDO;
import com.myself.deployrequester.service.*;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("deploy")
public class DeployController extends CommonMethodWrapper {
    /**
     * 日志
     */
    private static final Logger logger = LogManager.getLogger(DeployController.class);

    @Autowired
    private DeployService deployService;
    @Autowired
    private DeployTestEnvService deployTestEnvService;
    @Autowired
    private DeployRequesterService deployRequesterService;
    @Autowired
    private DeployPerformanceService deployPerformanceService;
    @Autowired
    private CommonDataService commonDataService;
    @Autowired
    private DeployAuditService deployAuditService;

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
                        if (deployTestEnvDO.getIssuccess().shortValue() == Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()).shortValue()) {
                            //向t_deploy_performance表中写数据
                            DeployPerformanceDO deployPerformanceDO = new DeployPerformanceDO();
                            deployPerformanceDO.setProjectcode(deployRequesterDO.getProjectcode());
                            deployPerformanceDO.setModulecode(deployRequesterDO.getModulecode());
                            deployPerformanceDO.setModuletypecode(deployRequesterDO.getModuletypecode());
                            deployPerformanceDO.setDeployduration(deployResult.getDuration());
                            deployPerformanceService.recordPerformance(deployPerformanceDO);
                        }
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

    @ResponseBody
    @RequestMapping(value="deployToTestEnvForAudit", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deployToTestEnvForAudit(DeployRequesterDTO deployRequesterDTO, HttpServletRequest request) {
        JsonResult result = null;
        String clientIpAddr = getIpAddr(request);
        String clientName = commonDataService.obtainCrewName(clientIpAddr);

        Short projectId = deployRequesterDTO.getProjectcode();
        Short moduleId = deployRequesterDTO.getModulecode();
        Short moduleTypeId = deployRequesterDTO.getModuletypecode();
        if (projectId == null || moduleId == null || moduleTypeId == null) {
            result = JsonResult.createFailed("failed");
            result.addData("项目id、模块id、模块类型id都必须有值。");
            return result;
        }
        //查询当前模块是否正在发布中
        String projectIdAndModuleId = String.valueOf(projectId) + "_" + String.valueOf(moduleId);

        if (DeployStatus.deployingSet.contains(projectIdAndModuleId)) {
            result = JsonResult.createFailed("failed");
            result.addData("不能重复发布此项目模块，有人正在发布此项目模块中。。。。。。");
            return result;
        }
        DeployStatus.deployingSet.add(projectIdAndModuleId);

        //获取发布的url
        String moduleCode = commonDataService.getModuleCodeById(moduleId);
        String moduleType = commonDataService.getModuleTypeNameById(moduleTypeId);
        String projectCode = String.valueOf(projectId);
        UrlSummary urlSummary = commonDataService.getUrlSummary(projectCode, moduleCode, moduleType);
        String deployUrl = "";
        if (urlSummary != null) {
            deployUrl = urlSummary.getTestDeployUrl();
        }

        //获取此模块下包含了哪些待发布申请记录id
        List<String> requestIdList = null;
        try {
            requestIdList = deployAuditService.obtainUnexecutedRequestIdListUnderProjectModule(projectId, moduleId, moduleTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "获取模块下的发布申请记录id报错", e);
            result = JsonResult.createFailed("failed");
            result.addData("获取模块下的发布申请记录id报错：" + e);
            //删除全局变量中的发布信息内容
            DeployStatus.deployingSet.remove(projectIdAndModuleId);
            return result;
        }

        if (requestIdList == null || requestIdList.size() == 0) {
            //删除全局变量中的发布信息内容
            DeployStatus.deployingSet.remove(projectIdAndModuleId);
            result = JsonResult.createFailed("failed");
            result.addData("没有需要发布的申请。");
            return result;
        }

        for (String deployRequestId : requestIdList) {
            DeployTestEnvDO deployTestEnvDO = new DeployTestEnvDO();
            deployTestEnvDO.setDeployrequestid(deployRequestId);
            deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOYING.getCode()));   //1：正在发布；2：发布成功；-1：发布失败。
            try {
                int successCount = deployTestEnvService.insertDeployTestEnv(deployTestEnvDO);
                if (successCount != 1) {
                    DeployStatus.deployingSet.remove(projectIdAndModuleId);
                    result = JsonResult.createFailed("failed");
                    result.addData("执行deployTestEnvService.insertDeployTestEnv(deployTestEnvDO)时返回的值有不为1的现象。deployTestEnvDO.getDeploytestenvid()=" + deployTestEnvDO.getDeploytestenvid());
                    return result;
                }
            } catch (Exception e) {
                //删除全局变量中的发布信息内容
                DeployStatus.deployingSet.remove(projectIdAndModuleId);
                e.printStackTrace();
                Log4jUtil.error(logger, "执行deployTestEnvService.insertDeployTestEnv(deployTestEnvDO)报错", e);
                result = JsonResult.createFailed("failed");
                result.addData("deployTestEnvService.insertDeployTestEnv(deployTestEnvDO)报错：" + e);
                return result;
            }

            //更新t_request_audit表中的内容
            DeployAuditDO deployAuditDO = null;
            try {
                deployAuditDO = deployAuditService.selectByDeployRequestId(deployRequestId);
                if (deployAuditDO == null) {
                    DeployStatus.deployingSet.remove(projectIdAndModuleId);
                    result = JsonResult.createFailed("failed");
                    result.addData("没找到t_request_audit表中对应的记录。deployRequestId=" + deployRequestId);
                    return result;
                }
            } catch (Exception e) {
                //删除全局变量中的发布信息内容
                DeployStatus.deployingSet.remove(projectIdAndModuleId);
                e.printStackTrace();
                Log4jUtil.error(logger, "执行deployAuditService.selectByDeployRequestId(deployRequestId)报错", e);
                result = JsonResult.createFailed("failed");
                result.addData("deployAuditService.selectByDeployRequestId(deployRequestId)报错：" + e);
                return result;
            }
            //更新t_request_audit表中的内容
            deployAuditDO.setAuditorip(clientIpAddr);
            deployAuditDO.setAuditor(clientName);
            deployAuditDO.setDeployswitchstatus(DeploySwitchEnum.SWITCH_ON.getCode());
            deployAuditDO.setSwitchontime(new Date());
            try {
                int updateSuccessCount = deployAuditService.updateByPrimaryKey(deployAuditDO);
                if (updateSuccessCount != 1) {
                    DeployStatus.deployingSet.remove(projectIdAndModuleId);
                    result = JsonResult.createFailed("failed");
                    result.addData("更新t_request_audit表中的内容时更新的记录数量有不为1条的现象，更新有问题。deployAuditDO.getDeployauditid()=" + deployAuditDO.getDeployauditid());
                    return result;
                }
            } catch (Exception e) {
                //删除全局变量中的发布信息内容
                DeployStatus.deployingSet.remove(projectIdAndModuleId);
                e.printStackTrace();
                Log4jUtil.error(logger, "执行deployAuditService.updateByPrimaryKey(deployAuditDO)报错", e);
                result = JsonResult.createFailed("failed");
                result.addData("deployAuditService.updateByPrimaryKey(deployAuditDO)报错：" + e);
                return result;
            }
        }

        Map<String, Object> otherParamMap = new HashMap<String, Object>();
        otherParamMap.put("clientIpAddr", clientIpAddr);
        //发布
        DeployResult deployResult = deployService.deploy(deployUrl, otherParamMap);
        if (deployResult != null) {
            if (deployResult.isSuccessDeployed()) {
                //发布成功
                for (String deployRequestId : requestIdList) {
                    try {
                        DeployTestEnvDO deployTestEnvDO = deployTestEnvService.selectByDeployRequestId(deployRequestId);
                        if (deployTestEnvDO != null) {
                            deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()));
                            deployTestEnvDO.setDeployendtime(new Date());
                            deployTestEnvDO.setDuration(deployResult.getDuration());
                            deployTestEnvDO.setFeedbackinfo(deployResult.getResultMsg());
                            int updateSuccessCountForDeployTestEnv = deployTestEnvService.updateByPrimaryKeySelective(deployTestEnvDO);
                            if (updateSuccessCountForDeployTestEnv != 1) {
                                //删除全局变量中的发布信息内容
                                DeployStatus.deployingSet.remove(projectIdAndModuleId);
                                result = JsonResult.createFailed("failed");
                                result.addData("发布虽然成功，但在执行deployTestEnvService.updateByPrimaryKeySelective(deployTestEnvDO)过程中发现返回值不为1。deployRequestId是：" + deployRequestId);
                                return result;
                            }
                        }

                        DeployRequest deployRequest = deployRequesterService.getDeployRequestById(deployRequestId);
                        DeployRequesterDO deployRequesterDO = deployRequest;
                        DeployRequesterDO deployRequesterDO1 = new DeployRequesterDO();
                        deployRequesterDO1.setProjectcode(deployRequesterDO.getProjectcode());
                        deployRequesterDO1.setDeployrequestid(deployRequesterDO.getDeployrequestid());
                        deployRequesterDO1.setDeploystatusfortestenv(deployTestEnvDO.getIssuccess());
                        int updateSuccessCountForDeployRequest = deployRequesterService.updateByPrimaryKeySelective(deployRequesterDO1);
                        if (updateSuccessCountForDeployRequest != 1) {
                            //删除全局变量中的发布信息内容
                            DeployStatus.deployingSet.remove(projectIdAndModuleId);
                            result = JsonResult.createFailed("failed");
                            result.addData("发布虽然成功，但在执行deployRequesterService.updateByPrimaryKeySelective(deployRequesterDO1)的时候有返回值不为1的现象。deployRequesterDO1.getDeployrequestid()=" + deployRequesterDO1.getDeployrequestid());
                            return result;
                        }
                        if (deployTestEnvDO.getIssuccess().shortValue() == Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()).shortValue()) {
                            //向t_deploy_performance表中写数据
                            DeployPerformanceDO deployPerformanceDO = new DeployPerformanceDO();
                            deployPerformanceDO.setProjectcode(deployRequesterDO.getProjectcode());
                            deployPerformanceDO.setModulecode(deployRequesterDO.getModulecode());
                            deployPerformanceDO.setModuletypecode(deployRequesterDO.getModuletypecode());
                            deployPerformanceDO.setDeployduration(deployResult.getDuration());
                            deployPerformanceService.recordPerformance(deployPerformanceDO);
                        }

                    } catch (Exception e) {
                        //删除全局变量中的发布信息内容
                        DeployStatus.deployingSet.remove(projectIdAndModuleId);
                        e.printStackTrace();
                        result = JsonResult.createFailed("failed");
                        result.addData("在处理发布成功的后续事项的时候操作数据库出错。" + e);
                        return result;
                    }
                }
            } else {
                //发布失败
                for (String deployRequestId : requestIdList) {
                    try {
                        DeployTestEnvDO deployTestEnvDO = deployTestEnvService.selectByDeployRequestId(deployRequestId);
                        if (deployTestEnvDO != null) {
                            deployTestEnvDO.setIssuccess(Short.valueOf(DeployEnum.DEPLOY_FAILED.getCode()));
                            deployTestEnvDO.setFeedbackinfo(deployResult.getResultMsg());
                            int updateSuccessCountForDeployTestEnv = deployTestEnvService.updateByPrimaryKeySelective(deployTestEnvDO);
                            if (updateSuccessCountForDeployTestEnv != 1) {
                                //删除全局变量中的发布信息内容
                                DeployStatus.deployingSet.remove(projectIdAndModuleId);
                                result = JsonResult.createFailed("failed");
                                result.addData("在执行deployTestEnvService.updateByPrimaryKeySelective(deployTestEnvDO)过程中发现返回值不为1。deployRequestId是：" + deployRequestId);
                                return result;
                            }
                        }

                        DeployRequest deployRequest = deployRequesterService.getDeployRequestById(deployRequestId);
                        DeployRequesterDO deployRequesterDO = deployRequest;
                        DeployRequesterDO deployRequesterDO1 = new DeployRequesterDO();
                        deployRequesterDO1.setProjectcode(deployRequesterDO.getProjectcode());
                        deployRequesterDO1.setDeployrequestid(deployRequesterDO.getDeployrequestid());
                        deployRequesterDO1.setDeploystatusfortestenv(deployTestEnvDO.getIssuccess());
                        int updateSuccessCountForDeployRequest = deployRequesterService.updateByPrimaryKeySelective(deployRequesterDO1);
                        if (updateSuccessCountForDeployRequest != 1) {
                            //删除全局变量中的发布信息内容
                            DeployStatus.deployingSet.remove(projectIdAndModuleId);
                            result = JsonResult.createFailed("failed");
                            result.addData("update t_deploy_request failed.");
                            return result;
                        }
                        if (deployTestEnvDO.getIssuccess().shortValue() == Short.valueOf(DeployEnum.DEPLOY_SUCCESS.getCode()).shortValue()) {
                            //向t_deploy_performance表中写数据
                            DeployPerformanceDO deployPerformanceDO = new DeployPerformanceDO();
                            deployPerformanceDO.setProjectcode(deployRequesterDO.getProjectcode());
                            deployPerformanceDO.setModulecode(deployRequesterDO.getModulecode());
                            deployPerformanceDO.setModuletypecode(deployRequesterDO.getModuletypecode());
                            deployPerformanceDO.setDeployduration(deployResult.getDuration());
                            deployPerformanceService.recordPerformance(deployPerformanceDO);
                        }
                    } catch (Exception e) {
                        //删除全局变量中的发布信息内容
                        DeployStatus.deployingSet.remove(projectIdAndModuleId);
                        e.printStackTrace();
                        result = JsonResult.createFailed("failed");
                        result.addData("在处理发布失败的后续事项的时候操作数据库出错。" + e);
                        return result;
                    }
                }
            }
        }

        //此处表示整个发布过程都是成功的，于是再删除全局变量中的发布信息内容
        DeployStatus.deployingSet.remove(projectIdAndModuleId);
        result = JsonResult.createSuccess("ok");
        result.addData(deployResult);
        return  result;
    }

}
