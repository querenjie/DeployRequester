package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.bo.DeployDbservers;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.dto.DeployDbserversDTO;
import com.myself.deployrequester.model.DeployDbserversDO;
import com.myself.deployrequester.service.CommonDataService;
import com.myself.deployrequester.service.DeployDbserversService;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.json.JsonResult;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("depdbservers")
public class DeployDbserversController extends CommonMethodWrapper {
    private static final Logger logger = LogManager.getLogger(DeployDbserversController.class);

    @Autowired
    private DeployDbserversService deployDbserversService;
    @Autowired
    private CommonDataService commonDataService;

    @RequestMapping("/deploy_dbservers")
    public String gotoDbserversConfigForm() {
        return "deploy_dbservers";
    }

    @ResponseBody
    @RequestMapping(value = "/saveDbservers", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult saveDbservers(@RequestBody DeployDbserversDTO deployDbserversDTO, HttpServletRequest request) {
        JsonResult result = null;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限创建数据库连接信息,请找管理员开权限。");
            return result;
        }

        DeployDbserversDO deployDbserversDO = new DeployDbserversDO();
        BeanUtils.copyProperties(deployDbserversDTO, deployDbserversDO, true);

        String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
        deployDbserversDO.setCreater(crewName);
        deployDbserversDO.setCreaterip(clientIpAddr);

        String errorMsg = checkAndGetErrMsg(deployDbserversDO);
        if (!StringUtils.isBlank(errorMsg)) {
            result = JsonResult.createFailed("参数内容不能为空");
            result.addData(errorMsg);
        } else {
            String returnMsg = deployDbserversService.checkConnection(deployDbserversDO.getIp(), deployDbserversDO.getPort(), deployDbserversDO.getUsername(), deployDbserversDO.getPassword(), deployDbserversDO.getDbname());
            if (!StringUtils.isBlank(returnMsg)) {
                result = JsonResult.createFailed("参数内容可能有问题，因为用这些参数连不上数据库");
                result.addData(returnMsg);
            } else {
                try {
                    int saveRecordCount = deployDbserversService.saveDeployDbservers(deployDbserversDO);
                    if (saveRecordCount == 1) {
                        result = JsonResult.createSuccess("save data successfully");
                    } else {
                        result = JsonResult.createSuccess("save data abnormally");
                    }
                    result.addData(saveRecordCount);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log4jUtil.error(logger, "保存数据库连接配置的过程出现问题", e);
                    result = JsonResult.createFailed("save data failed");
                    result.addData("保存数据库连接配置的过程出现问题" + e);
                }
            }
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult query(@RequestBody DeployDbserversDTO deployDbserversDTO) {
        JsonResult result;

        DeployDbserversDO deployDbserversDO = new DeployDbserversDO();
        BeanUtils.copyProperties(deployDbserversDTO, deployDbserversDO, true);

        try {
            List<DeployDbservers> deployDbserversList = deployDbserversService.selectByDeployDbserversDO(deployDbserversDO);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployDbserversList);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deleteById(@RequestBody DeployDbserversDTO deployDbserversDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限删除数据库连接信息,请找管理员开权限。");
            return result;
        }

        String deploydbserversid = deployDbserversDTO.getDeploydbserversid();
        try {
            int deleteCount = deployDbserversService.deleteByPrimaryKey(deploydbserversid);
            result = JsonResult.createSuccess("delete data successfully");
            result.addData(deleteCount);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "删除数据库连接配置出现问题", e);
            result = JsonResult.createFailed("delete data failed");
            result.addData("删除数据库连接配置出现问题:" + e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getById(@RequestBody DeployDbserversDTO deployDbserversDTO) {
        JsonResult result;
        String deploydbserversid = deployDbserversDTO.getDeploydbserversid();
        try {
            DeployDbservers deployDbservers = deployDbserversService.selectByPrimarykey(deploydbserversid);
            result = JsonResult.createSuccess("query data successfully");
            if (deployDbservers != null) {
                result.addData(deployDbservers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询数据库连接配置出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult queryAll() {
        JsonResult result;

        try {
            List<DeployDbservers> deployDbserversList = deployDbserversService.selectAll();
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployDbserversList);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }


    private String checkAndGetErrMsg(DeployDbserversDO deployDbserversDO) {
        String errorMsg = "";
        if (StringUtils.isBlank(String.valueOf(deployDbserversDO.getBelong()))) {
            errorMsg += "数据库环境不能为空！" + "\n";
        }
        if (StringUtils.isBlank(deployDbserversDO.getLinkname())) {
            errorMsg += "数据库链接名不能为空！" + "\n";
        }
        if (StringUtils.isBlank(deployDbserversDO.getIp())) {
            errorMsg += "数据库服务器的ip不能为空！" + "\n";
        }
        if (StringUtils.isBlank(String.valueOf(deployDbserversDO.getPort()))) {
            errorMsg += "端口不能为空！" + "\n";
        }
        if (StringUtils.isBlank(deployDbserversDO.getUsername())) {
            errorMsg += "数据库用户名不能为空！" + "\n";
        }
        if (StringUtils.isBlank(deployDbserversDO.getPassword())) {
            errorMsg += "密码不能为空！" + "\n";
        }
        return errorMsg;
    }
}
