package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.bo.QueryResults;
import com.myself.deployrequester.dto.ProduceApplicationQueryCriteriaDTO;
import com.myself.deployrequester.dto.QueryCriteriaDTO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.ProduceApplicationQueryCriteriaDO;
import com.myself.deployrequester.model.QueryCriteriaDO;
import com.myself.deployrequester.service.DeployRequesterService;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("depquery")
public class DeployQueryController extends CommonMethodWrapper {
    private static final Logger logger = LogManager.getLogger(DeployQueryController.class);

    @Autowired
    private DeployRequesterService deployRequesterService;

    @RequestMapping("/deploy_query")
    public String gotoDeployQueryForm() {
        return "deploy_request_querylist";
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult query(@RequestBody QueryCriteriaDTO queryCriteriaDTO, HttpServletRequest request) {
        String clientIpAddr = getIpAddr(request);
        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
        Log4jUtil.info(logger, crewName + "执行了普通查询，查询条件: " + queryCriteriaDTO);
        JsonResult result;
        QueryCriteriaDO queryCriteriaDO = new QueryCriteriaDO();
        BeanUtils.copyProperties(queryCriteriaDTO, queryCriteriaDO, false);
        try {
            QueryResults queryResults = deployRequesterService.selectByQueryCriteria(queryCriteriaDO);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(queryResults);
        } catch (Exception e) {
            Log4jUtil.error(logger, "查询出现问题", e);
            e.printStackTrace();
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryDetail", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult queryDetail(@RequestBody QueryCriteriaDTO queryCriteriaDTO) {
        JsonResult result;
        String deployRequestId = queryCriteriaDTO.getDeployrequestid();
        try {
            DeployRequest deployRequest = deployRequesterService.getDeployRequestById(deployRequestId);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/assignIsTestOk", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult assignIsTestOk(@RequestBody QueryCriteriaDTO queryCriteriaDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);

        String deployRequestId = queryCriteriaDTO.getDeployrequestid();
        Short isTestOk = queryCriteriaDTO.getIstestok();
        if (deployRequestId == null || isTestOk == null) {
            result = JsonResult.createFailed("uncompleted request data");
        } else {
            DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
            deployRequesterDO.setDeployrequestid(deployRequestId);
            deployRequesterDO.setIstestok(isTestOk);
            deployRequesterDO.setTestflagmodifier(crewName);
            deployRequesterDO.setTestflagmodifierip(clientIpAddr);
            try {
                int updatedRecordCount = deployRequesterService.updateIsTestOk(deployRequesterDO);
                if (updatedRecordCount == 1) {
                    result = JsonResult.createSuccess("update data successfully");
                } else {
                    result = JsonResult.createSuccess("update data abnormally");
                }
                result.addData(updatedRecordCount);
            } catch (Exception e) {
                e.printStackTrace();
                Log4jUtil.error(logger, "修改测试通过情况出现问题", e);
                result = JsonResult.createFailed("修改测试通过情况出现问题");
            }
        }
        return result;
    }

    @RequestMapping("/gotoMarkProdRecord")
    public String gotoMarkProdRecord() {
        return "mark_prod_record";
    }

    @ResponseBody
    @RequestMapping(value = "/markDeployedProdEnvRecords", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult markDeployedProdEnvRecords(@RequestBody QueryCriteriaDTO queryCriteriaDTO) {
        JsonResult result;
        Short projectCode = queryCriteriaDTO.getProjectcode();
        String deployToProdEnvTime = queryCriteriaDTO.getDeployToProdEnvTime();
        if (projectCode == null || deployToProdEnvTime == null) {
            result = JsonResult.createFailed("项目和发布时间都不能为空");
        }
        if (deployToProdEnvTime.length() != 19) {
            result = JsonResult.createFailed("发布时间格式不对");
        }
        QueryCriteriaDO queryCriteriaDO = new QueryCriteriaDO();
        BeanUtils.copyProperties(queryCriteriaDTO, queryCriteriaDO, true);

        try {
            int updatedRecordCount = deployRequesterService.markDeployedToProd(queryCriteriaDO);
            if (updatedRecordCount > 0) {
                result = JsonResult.createSuccess("update data successfully");
            } else {
                result = JsonResult.createSuccess("update data abnormally");
            }
            result.addData(updatedRecordCount);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "标识已经上生产的过程出现问题", e);
            result = JsonResult.createFailed("标识已经上生产的过程出现问题");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/produceapplication", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult produceApplication(@RequestBody ProduceApplicationQueryCriteriaDTO produceApplicationQueryCriteriaDTO, HttpServletRequest request) {
        String clientIpAddr = getIpAddr(request);
        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
        String temp = "";
        if ("product".equals(produceApplicationQueryCriteriaDTO.getDeployToWhere())) {
            temp = "生产发布申请";
        } else if ("preproduct".equals(produceApplicationQueryCriteriaDTO.getDeployToWhere())) {
            temp = "预发布申请";
        }
        Log4jUtil.info(logger, crewName + "查看了" + temp + "，查询条件: " + produceApplicationQueryCriteriaDTO);

        JsonResult result;
        ProduceApplicationQueryCriteriaDO produceApplicationQueryCriteriaDO = new ProduceApplicationQueryCriteriaDO();
        BeanUtils.copyProperties(produceApplicationQueryCriteriaDTO, produceApplicationQueryCriteriaDO, false);
        try {
            String applicationStr = deployRequesterService.produceApplicationStr(produceApplicationQueryCriteriaDO);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(applicationStr);
        } catch (Exception e) {
            Log4jUtil.error(logger, "查询出现问题", e);
            e.printStackTrace();
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getLockedDeployRequest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getLockedDeployRequest() {
        JsonResult result;
        try {
            List<DeployRequest> deployRequestList = deployRequesterService.getLockedDeployRequest();
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployRequestList);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }


}