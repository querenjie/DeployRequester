package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.DBExecuteStatusEnum;
import com.myself.deployrequester.biz.config.sharedata.DBWillIgnoreEnum;
import com.myself.deployrequester.bo.DeployDbscript;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.dto.DeployDbscriptDTO;
import com.myself.deployrequester.dto.DeployDbserversDTO;
import com.myself.deployrequester.dto.QueryCriteriaDTO;
import com.myself.deployrequester.dto.QueryDbscriptDTO;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.DeployDbscriptDetailsqlDO;
import com.myself.deployrequester.model.PrjmodDblinkRelDO;
import com.myself.deployrequester.model.QueryDbscriptDO;
import com.myself.deployrequester.service.DeployDBScriptService;
import com.myself.deployrequester.service.DeployDbscriptDetailsqlService;
import com.myself.deployrequester.service.PrjmodDblinkRelService;
import com.myself.deployrequester.util.DBScriptUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("depdbscript")
public class DeployDbscriptController extends CommonMethodWrapper {
    private static final Logger logger = LogManager.getLogger(DeployDbscriptController.class);

    @Autowired
    private DeployDBScriptService deployDBScriptService;
    @Autowired
    private PrjmodDblinkRelService prjmodDblinkRelService;
    @Autowired
    private DeployDbscriptDetailsqlService deployDbscriptDetailsqlService;

    @RequestMapping("/deploy_dbscript_insert")
    public String gotoDbscriptInsertForm() {
        return "deploy_dbscript_insert";
    }

    @RequestMapping("/deploy_dbscript_querylist")
    public String gotoDbscriptQuerylistForm() {
        return "deploy_dbscript_querylist";
    }

    @ResponseBody
    @RequestMapping(value = "/saveDBScript", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult saveDBScript(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result = null;

        String clientIpAddr = getIpAddr(request);
        deployDbscriptDTO.setApplierip(clientIpAddr);
        deployDbscriptDTO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);

        if (StringUtils.isBlank(deployDbscriptDO.getDeploydbscriptid())) {
            //记录主键没有，那就新增记录。
            DBScriptUtil dbScriptUtil = new DBScriptUtil();
            List<String> seperatedStatementList = dbScriptUtil.analyzeSqlStatement(deployDbscriptDO.getDbscript());
            if (hasDangerousStatement(seperatedStatementList)) {
                result = JsonResult.createFailed("dangerous statement in it");
                result.addData("其中有drop或delete语句，很危险，请去掉这种语句。");
                return result;
            }

            PrjmodDblinkRelDO prjmodDblinkRelDO = new PrjmodDblinkRelDO();
            prjmodDblinkRelDO.setProjectid(deployDbscriptDO.getProjectid());
            prjmodDblinkRelDO.setModuleid(deployDbscriptDO.getModuleid());

            try {
                List<PrjmodDblinkRelDO> prjmodDblinkRelDOList = prjmodDblinkRelService.selectByPrjmodDblinkRelDO(prjmodDblinkRelDO);
                if (prjmodDblinkRelDOList != null && prjmodDblinkRelDOList.size() == 1) {
                    PrjmodDblinkRelDO prjmodDblinkRelDO1 = prjmodDblinkRelDOList.get(0);
                    deployDbscriptDO.setDeploydbserversid(prjmodDblinkRelDO1.getDeploydbserversid());
                }

                int saveSuccessRecCount = deployDBScriptService.insert(deployDbscriptDO);
                if (saveSuccessRecCount == 1) {
                    if (seperatedStatementList != null) {
                        List<DeployDbscriptDetailsqlDO> deployDbscriptDetailsqlDOList = new ArrayList<DeployDbscriptDetailsqlDO>();
                        for (String sql : seperatedStatementList) {
                            DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO = new DeployDbscriptDetailsqlDO();
                            deployDbscriptDetailsqlDO.setDeploydbscriptid(deployDbscriptDO.getDeploydbscriptid());
                            deployDbscriptDetailsqlDO.setSubsql(sql);
                            deployDbscriptDetailsqlDO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));
                            deployDbscriptDetailsqlDO.setWillignore(Short.valueOf(String.valueOf(DBWillIgnoreEnum.NOT_IGNORE.getCode())));
                            deployDbscriptDetailsqlDO.setCreater(deployDbscriptDO.getApplier());
                            deployDbscriptDetailsqlDO.setCreaterip(deployDbscriptDO.getApplierip());
                            deployDbscriptDetailsqlDO.setCreatetime(new Date());

                            deployDbscriptDetailsqlDOList.add(deployDbscriptDetailsqlDO);
                        }
                        List<String> deployDbscriptDetailsqlidList = deployDbscriptDetailsqlService.batchInsert(deployDbscriptDetailsqlDOList);
                        if (deployDbscriptDetailsqlidList.size() != deployDbscriptDetailsqlDOList.size()) {
                            //这时说明保存sql失败了。索性把t_deploy_dbscript表中的记录也直接删除掉。
                            deployDBScriptService.deleteById(deployDbscriptDO.getDeploydbscriptid());
                            result = JsonResult.createFailed("save data abnormally");
                            result.addData("保存sql语句出现问题，保存失败。");
                        } else {
                            result = JsonResult.createSuccess("save data successfully");
                            result.addData(1);
                        }
                    }
                } else {
                    result = JsonResult.createFailed("save data abnormally");
                    result.addData("保存记录出现问题，保存失败。");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log4jUtil.error(logger, "保存脚本信息的过程出现问题", e);
                result = JsonResult.createFailed("save data failed");
                result.addData("保存脚本信息的过程出现问题" + e);
            }
        } else {
            //否则就修改记录的内容
            //首先根据记录的id获取脚本记录对象
            try {
                DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptDO.getDeploydbscriptid());
                if (deployDbscript != null) {
                    PrjmodDblinkRelDO prjmodDblinkRelDO = new PrjmodDblinkRelDO();
                    prjmodDblinkRelDO.setProjectid(deployDbscript.getProjectid());
                    prjmodDblinkRelDO.setModuleid(deployDbscript.getModuleid());
                    prjmodDblinkRelDO.setDeploydbserversid(deployDbscriptDO.getDeploydbserversid());
                    int upateSuccessRecCount1 = prjmodDblinkRelService.savePrjmodDblinkRel(prjmodDblinkRelDO);

                    BeanUtils.copyProperties(deployDbscript, deployDbscriptDO, true);
                    String crewName = ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr);
                    deployDbscriptDO.setExecutorip(clientIpAddr);
                    deployDbscriptDO.setExecutor(crewName);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult query(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        QueryDbscriptDO queryDbscriptDO = new QueryDbscriptDO();
        BeanUtils.copyProperties(queryDbscriptDTO, queryDbscriptDO, true);

        try {
            List<DeployDbscript> deployDbscriptList = deployDBScriptService.selectByQueryDbscriptDO(queryDbscriptDO);
            if (deployDbscriptList != null) {
                for (DeployDbscript deployDbscript : deployDbscriptList) {
                    deployDbscript.setVisitorIp(clientIpAddr);
                }
            }
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployDbscriptList);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryDetail", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult queryDetail(@RequestBody QueryDbscriptDTO queryDbscriptDTO) {
        JsonResult result;
        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            result = JsonResult.createSuccess("query data successfully");
            result.addData(deployDbscript);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deleteById(@RequestBody QueryDbscriptDTO queryDbscriptDTO) {
        JsonResult result;
        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        try {
            int delRecCount = deployDBScriptService.deleteById(deployDbscriptId);
            result = JsonResult.createSuccess("delete data successfully");
            result.addData(delRecCount);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "删除数据出现问题", e);
            result = JsonResult.createFailed("delete data failed");
        }
        return result;
    }

    /**
     * 判断是否有drop或delete语句在里面。如果有则返回true，否则返回false。
     * @param seperatedStatementList
     * @return
     */
    private boolean hasDangerousStatement(List<String> seperatedStatementList) {
        if (seperatedStatementList == null) {
            return false;
        }
        for (String sql : seperatedStatementList) {
            sql = sql.toLowerCase();
            if (sql.startsWith("drop ") || sql.startsWith("delete ")) {
                return true;
            }
        }
        return false;
    }

}
