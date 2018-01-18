package com.myself.deployrequester.controller;

/**
 * Created by QueRenJie on ${date}
 */

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.DeployRequesterStatistics1;
import com.myself.deployrequester.bo.QueryResults;
import com.myself.deployrequester.dto.QueryCriteriaDTO;
import com.myself.deployrequester.dto.QueryCriteriaStatistics1DTO;
import com.myself.deployrequester.model.QueryCriteriaDO;
import com.myself.deployrequester.model.QueryCriteriaStatistics1DO;
import com.myself.deployrequester.service.DeployRequesterService;
import com.myself.deployrequester.service.DeployRequesterStatisticsService;
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
@RequestMapping("depstatistics")
public class DeployStatisticsController extends CommonMethodWrapper {
    private static final Logger logger = LogManager.getLogger(DeployStatisticsController.class);

    @Autowired
    private DeployRequesterStatisticsService deployRequesterStatisticsService;

    @RequestMapping("/deploy_statistics1")
    public String gotoDeployStatistics1Form() {
        return "deploy_request_statistics1";
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult query(@RequestBody QueryCriteriaStatistics1DTO queryCriteriaStatistics1DTO, HttpServletRequest request) {
        String clientIpAddr = getIpAddr(request);
        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
        Log4jUtil.info(logger, crewName + "执行了统计测试情况查询，查询条件: " + queryCriteriaStatistics1DTO);
        JsonResult result;
        QueryCriteriaStatistics1DO queryCriteriaStatistics1DO = new QueryCriteriaStatistics1DO();
        BeanUtils.copyProperties(queryCriteriaStatistics1DTO, queryCriteriaStatistics1DO, false);
        try {
            List<DeployRequesterStatistics1> deployRequesterStatistics1List = deployRequesterStatisticsService.selectByQueryCriteriaStatistics1(queryCriteriaStatistics1DO);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployRequesterStatistics1List);
        } catch (Exception e) {
            Log4jUtil.error(logger, "查询出现问题", e);
            e.printStackTrace();
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }


}
