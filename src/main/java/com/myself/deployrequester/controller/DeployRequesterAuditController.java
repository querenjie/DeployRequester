package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.SessionData;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.bo.QueryResults;
import com.myself.deployrequester.bo.UrlSummary;
import com.myself.deployrequester.dto.DeployRequesterDTO;
import com.myself.deployrequester.dto.QueryDeployAuditDTO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.QueryDeployAuditDO;
import com.myself.deployrequester.service.CommonDataService;
import com.myself.deployrequester.service.DeployAuditService;
import com.myself.deployrequester.service.DeployRequesterService;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("depreqaudit")
public class DeployRequesterAuditController {
    private static final Logger logger = LogManager.getLogger(DeployQueryController.class);

    @Autowired
    private DeployAuditService deployAuditService;

    @RequestMapping("/deploy_request_audit_querylist")
    public String gotoRequestForm() {
        return "deploy_request_audit_querylist";
    }

    @ResponseBody
    @RequestMapping(value="/addDeployRequestAudit", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult addDeployRequestAudit(@RequestBody DeployRequesterDTO deployRequesterDTO, HttpSession session) {
        JsonResult result = null;
        if (StringUtils.isBlank(deployRequesterDTO.getDeployrequestid())) {
            result = JsonResult.createFailed("failed");
            result.addData("缺少申请记录的id");
            return result;
        }
        try {
            int addSuccessCount = deployAuditService.insert(deployRequesterDTO.getDeployrequestid());
            if (addSuccessCount == 1) {
                result = JsonResult.createSuccess("add data successfully");
                result.addData("提交申请成功，但需要测试人员来执行发布，请提醒测试人员发布。");
                return result;
            } else {
                result = JsonResult.createFailed("failed");
                result.addData("向t_deploy_audit中添加数据有异常情况。提交申请失败。" );
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "向t_deploy_audit中添加数据失败。", e);
            result = JsonResult.createFailed("failed");
            result.addData("向t_deploy_audit中添加数据失败。提交申请失败。" + e);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value="/obtainUnexecutedRequest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult obtainUnexecutedRequest(@RequestBody QueryDeployAuditDTO queryDeployAuditDTO) {
        JsonResult result = null;
        QueryDeployAuditDO queryDeployAuditDO = new QueryDeployAuditDO();
        if (queryDeployAuditDTO.getDeveloper() != null && StringUtils.isBlank(queryDeployAuditDTO.getDeveloper().trim())) {
            queryDeployAuditDTO.setDeveloper(null);
        }
        BeanUtils.copyProperties(queryDeployAuditDTO, queryDeployAuditDO, true);
        try {
            QueryResults queryResults = deployAuditService.queryUnexecutedRequest(queryDeployAuditDO);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(queryResults);
        } catch (Exception e) {
            Log4jUtil.error(logger, "查询出现问题", e);
            e.printStackTrace();
            result = JsonResult.createFailed("query data failed");
            result.addData("查询未执行的应用发布申请异常:" + e.getStackTrace().toString());
        }
        return  result;
    }


}
