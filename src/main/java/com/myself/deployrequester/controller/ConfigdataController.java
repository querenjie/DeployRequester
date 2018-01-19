package com.myself.deployrequester.controller;

import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.dto.DeployRequesterDTO;
import com.myself.deployrequester.dto.ModuleDTO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.service.CommonDataService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("configdata")
public class ConfigdataController extends CommonMethodWrapper {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(ConfigdataController.class);

    @Autowired
    private CommonDataService commonDataService;

    @ResponseBody
    @RequestMapping(value="/getProjects", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getProjects() {
        JsonResult result = null;
        List<Project> projectList = commonDataService.getAllProjects();
//        Log4jUtil.info(logger, "size of projectList is " + projectList.size());
        if (projectList != null && projectList.size() > 0) {
            result = JsonResult.createSuccess("获取项目正常");
            result.addDataAll(projectList);
        } else {
            result = JsonResult.createFailed("未能获取到项目");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getModules", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getModules() {
        JsonResult result = null;
        List<Module> moduleList = commonDataService.getAllModules();
//        Log4jUtil.info(logger, "size of moduleList is " + moduleList.size());
        if (moduleList != null && moduleList.size() > 0) {
            result = JsonResult.createSuccess("获取模块内容正常");
            result.addDataAll(moduleList);
        } else {
            result = JsonResult.createFailed("未能获取到模块内容");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getModuleTypes", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getModuleTypes() {
        JsonResult result = null;
        List<ModuleType> moduleTypeList = commonDataService.getAllModuleTypes();
        if (moduleTypeList != null && moduleTypeList.size() > 0) {
            result = JsonResult.createSuccess("获取模块类型正常");
            result.addDataAll(moduleTypeList);
        } else {
            result = JsonResult.createFailed("未能获取到模块类型");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getModifyTypes", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getModifyTypes() {
        JsonResult result = null;
        List<ModifyType> modifyTypeList = commonDataService.getAllModifyTypes();
        if (modifyTypeList != null && modifyTypeList.size() > 0) {
            result = JsonResult.createSuccess("获取修改类型正常");
            result.addDataAll(modifyTypeList);
        } else {
            result = JsonResult.createFailed("未能获取到修改类型");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getUrlSummary", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getUrlSummary(@RequestBody ModuleDTO moduleDTO) {
        JsonResult result = null;

        String projectId = String.valueOf(moduleDTO.getProjectId());
        String moduleCode = commonDataService.getModuleCodeById(moduleDTO.getModuleId());
        String moduleTypeName = commonDataService.getModuleTypeNameById(moduleDTO.getModuleTypeId());
        UrlSummary urlSummary = commonDataService.getUrlSummary(projectId, moduleCode, moduleTypeName);
        if (urlSummary != null) {
            result = JsonResult.createSuccess("获取url信息正常");
            result.addData(urlSummary);
        } else {
            result = JsonResult.createFailed("获取url信息不正常");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/judgeUsable", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult judgeUsable(HttpServletRequest request) {
        JsonResult result;
        List<String> resultList = new ArrayList<String>();

        String ipAddr = getIpAddr(request);
        boolean canUseDeployUrl = commonDataService.canUseDeployUrl(ipAddr);
        if (canUseDeployUrl == false) {
//            Log4jUtil.info(logger, "客户端" + ipAddr + "不允许访问本系统。");
            resultList.add("Sorry, you are not allowed to access this system. We suggest that you contact administrator for authentication.");
        } else {
            resultList.add("ok");
        }

        result = JsonResult.createSuccess("ok");
        result.addDataAll(resultList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/judgeCanViewDeployUrl", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult judgeCanViewDeployUrl(HttpServletRequest request) {
        JsonResult result;
        List<String> resultList = new ArrayList<String>();

        String ipAddr = getIpAddr(request);
        boolean canViewDeployUrl = commonDataService.canViewDeployUrl(ipAddr);
        if (canViewDeployUrl == false) {
//            Log4jUtil.info(logger, "客户端" + ipAddr + "不允许查看发布url。");
            resultList.add("Sorry, you have no privilege to view deploy url .");
        } else {
            resultList.add("ok");
        }

        result = JsonResult.createSuccess("ok");
        result.addDataAll(resultList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getModulesByProjectId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getModulesByProjectId(@RequestBody ModuleDTO moduleDTO) {
        JsonResult result = null;
        List<Module> moduleList = commonDataService.getModulesByProjectId(String.valueOf(moduleDTO.getProjectId()));
        if (moduleList != null && moduleList.size() > 0) {
            result = JsonResult.createSuccess("获取模块内容正常");
            result.addDataAll(moduleList);
        } else {
            result = JsonResult.createFailed("未能获取到模块内容");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/judgeCanMarkProductDeploy", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult judgeCanMarkProductDeploy(HttpServletRequest request) {
        JsonResult result;
        List<String> resultList = new ArrayList<String>();

        String ipAddr = getIpAddr(request);
        boolean canMarkProductDeploy = commonDataService.canMarkProductDeploy(ipAddr);
        if (canMarkProductDeploy == false) {
            //Log4jUtil.info(logger, "客户端" + ipAddr + "不允许标识已经发布生产的记录。");
            resultList.add("Sorry, you have no privilege to mark records as already deployed to product environment .");
        } else {
            resultList.add("ok");
        }

        result = JsonResult.createSuccess("ok");
        result.addDataAll(resultList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/judgeCanLockDeployRequest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult judgeCanLockDeployRequest(HttpServletRequest request) {
        JsonResult result;
        List<String> resultList = new ArrayList<String>();

        String ipAddr = getIpAddr(request);
        boolean canLockDeployRequest = commonDataService.canLockProductDeploy(ipAddr);
        if (canLockDeployRequest == false) {
            resultList.add("Sorry, you have no privilege to lock deploy request.");
        } else {
            resultList.add("ok");
        }

        result = JsonResult.createSuccess("ok");
        result.addDataAll(resultList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/judgeIfDeployRequestLocked", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult judgeIfDeployRequestLocked(@RequestBody DeployRequesterDTO deployRequesterDTO) {
        JsonResult result;

        DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
        BeanUtils.copyProperties(deployRequesterDTO, deployRequesterDO, true);
        Short projectId = deployRequesterDO.getProjectcode();
        Short moduleId = deployRequesterDO.getModulecode();
        Short moduleTypeId = deployRequesterDO.getModuletypecode();
        boolean isDeployRequestLocked = commonDataService.isDeployRequestLocked(projectId, moduleId, moduleTypeId);
        if (isDeployRequestLocked) {
            result = JsonResult.createSuccess("yes");
        } else {
            result = JsonResult.createSuccess("no");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value="/obtainCrewName", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult obtainCrewName(HttpServletRequest request) {
        JsonResult result;

        String ipAddr = getIpAddr(request);
        String crewName = commonDataService.obtainCrewName(ipAddr);

        if (crewName == null) {
            crewName = "";
        }
        Log4jUtil.info(logger, "crewName = " + crewName + ", ip = " + ipAddr);
        result = JsonResult.createSuccess("ok");
        result.addData(crewName);

        return result;
    }

}
