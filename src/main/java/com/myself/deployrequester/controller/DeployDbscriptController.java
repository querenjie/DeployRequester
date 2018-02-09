package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.DBExecuteStatusEnum;
import com.myself.deployrequester.biz.config.sharedata.DBIsAbandonedEnum;
import com.myself.deployrequester.biz.config.sharedata.DBWillIgnoreEnum;
import com.myself.deployrequester.bo.DeployDbscript;
import com.myself.deployrequester.bo.DeployRequest;
import com.myself.deployrequester.dto.*;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.DeployDbscriptDetailsqlDO;
import com.myself.deployrequester.model.PrjmodDblinkRelDO;
import com.myself.deployrequester.model.QueryDbscriptDO;
import com.myself.deployrequester.po.DeployDbscriptPO;
import com.myself.deployrequester.service.CommonDataService;
import com.myself.deployrequester.service.DeployDBScriptService;
import com.myself.deployrequester.service.DeployDbscriptDetailsqlService;
import com.myself.deployrequester.service.PrjmodDblinkRelService;
import com.myself.deployrequester.util.DBScriptUtil;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.MD5Util;
import com.myself.deployrequester.util.json.JsonResult;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.groovy.util.StringUtil;
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
    @Autowired
    private CommonDataService commonDataService;

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
        if (!commonDataService.canApplyDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限提交脚本申请,请找管理员开权限。");
            return result;
        }

        deployDbscriptDTO.setApplierip(clientIpAddr);
        deployDbscriptDTO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);

        if (StringUtils.isBlank(deployDbscriptDO.getDeploydbscriptid())) {
            //记录主键没有，那就新增记录。
            DBScriptUtil dbScriptUtil = new DBScriptUtil();
            List<String> seperatedStatementList = dbScriptUtil.analyzeSqlStatement(deployDbscriptDO.getDbscript());
            if (hasDangerousStatement(seperatedStatementList)) {
                if (!"yes".equals(deployDbscriptDTO.getForcetodoit())) {
                    result = JsonResult.createFailed("dangerous statement in it");
                    result.addData("其中有drop或delete语句，很危险，请去掉这种语句。");
                    return result;
                }
            }

            String signedString = MD5Util.signSqls(seperatedStatementList, "utf-8");
            deployDbscriptDO.setSqlmd5(signedString);

            QueryDbscriptDO queryDbscriptDO = new QueryDbscriptDO();
            queryDbscriptDO.setProjectid(deployDbscriptDO.getProjectid());
            queryDbscriptDO.setModuleid(deployDbscriptDO.getModuleid());
            queryDbscriptDO.setBelong(deployDbscriptDO.getBelong());
            queryDbscriptDO.setSqlmd5(signedString);
            try {
                List<DeployDbscript> deployDbscriptList = deployDBScriptService.selectByQueryDbscriptDO(queryDbscriptDO);
                if (deployDbscriptList != null && deployDbscriptList.size() > 0) {
                    result = JsonResult.createFailed("failed");
                    result.addData("此脚本貌似之前有申请过，不能重复提交。");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log4jUtil.error(logger, "排重查询过程出现问题", e);
                result = JsonResult.createFailed("failed");
                result.addData("排重查询过程出现问题：" + e);
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
                        short i = 1;
                        for (String sql : seperatedStatementList) {
                            DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO = new DeployDbscriptDetailsqlDO();
                            deployDbscriptDetailsqlDO.setDeploydbscriptid(deployDbscriptDO.getDeploydbscriptid());
                            deployDbscriptDetailsqlDO.setSubsqlseqno(i++);
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
    public JsonResult queryDetail(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;
        String clientIpAddr = getIpAddr(request);
        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            if (deployDbscript != null) {
                deployDbscript.setVisitorIp(clientIpAddr);
                result = JsonResult.createSuccess("query data successfully");
                result.addData(deployDbscript);
            } else {
                result = JsonResult.createFailed("query data failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "查询出现问题", e);
            result = JsonResult.createFailed("query data failed");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deleteById(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canApplyDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限删除脚本申请,请找管理员开权限。");
            return result;
        }

        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            if (deployDbscript != null) {
                if (deployDbscript.getExecutestatus() != 0) {
                    //对于已经执行的脚本是不允许删除的，哪怕是没有执行成功或正在执行。
                    result = JsonResult.createFailed("failed");
                    result.addData("由于该记录不是未执行状态，所以不能删除。");
                    return result;
                }
            }
            int delRecCount = deployDBScriptService.deleteById(deployDbscriptId);
            result = JsonResult.createSuccess("delete data successfully");
            result.addData(delRecCount);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "删除数据出现问题", e);
            result = JsonResult.createFailed("failed");
            result.addData("删除数据出现问题:" + e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/abandonDeployDbscript", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult abandonDeployDbscript(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canApplyDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限申请放弃脚本的执行,请找管理员开权限。");
            return result;
        }

        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        deployDbscriptDO.setDeploydbscriptid(deployDbscriptId);
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            if (deployDbscript != null) {
                if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("已经是执行成功状态，放弃执行申请无效。");
                    return result;
                }
                if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("sql正在执行中，放弃执行申请无效。");
                    return result;
                }
                //更新主记录的isabandoned字段的内容
                deployDbscriptDO.setIsabandoned(Short.valueOf(String.valueOf(DBIsAbandonedEnum.ABANDONED.getCode())));
                int updateSuccessCount = deployDBScriptService.modifiyIsabandoned(deployDbscriptDO);
                result = JsonResult.createSuccess("update data successfully");
                result.addData("放弃执行申请生效。");
            } else {
                result = JsonResult.createFailed("query data failed");
                result.addData("未找到记录！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "出现问题", e);
            result = JsonResult.createFailed("failed");
            result.addData("出现问题:" + e);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/applyRedeployDbscript", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult applyRedeployDbscript(@RequestBody ApplyRedeployDbscriptDTO applyRedeployDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canApplyDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限重新申请提交脚本,请找管理员开权限。");
            return result;
        }

        String deployDbscriptId = applyRedeployDbscriptDTO.getDeploydbscriptid();
        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        deployDbscriptDO.setDeploydbscriptid(deployDbscriptId);
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            if (deployDbscript != null) {
                if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("已经是执行成功状态，申请重新执行剩余脚本无效。");
                    return result;
                }
                if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("sql正在执行中，申请重新执行剩余脚本无效。");
                    return result;
                }
                //批量插入新的未执行的sql
                DBScriptUtil dbScriptUtil = new DBScriptUtil();
                List<String> seperatedStatementList = dbScriptUtil.analyzeSqlStatement(applyRedeployDbscriptDTO.getUnexecutedSql());
                if (hasDangerousStatement(seperatedStatementList)) {
                    if (!"yes".equals(applyRedeployDbscriptDTO.getForcetodoit())) {
                        result = JsonResult.createFailed("dangerous statement in it");
                        result.addData("其中有drop或delete语句，很危险，请去掉这种语句。");
                        return result;
                    }
                }

                //删除未执行的sql
                int deleteUnexecutedSuccessCount = deployDbscriptDetailsqlService.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);

                if (seperatedStatementList != null) {
                    //在拼装sql记录对象之前必须确定序号从那个数值开始
                    Short maxSeqno = deployDbscriptDetailsqlService.selectMaxSeqno(deployDbscriptId);

                    List<DeployDbscriptDetailsqlDO> deployDbscriptDetailsqlDOList = new ArrayList<DeployDbscriptDetailsqlDO>();
                    for (String sql : seperatedStatementList) {
                        DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO = new DeployDbscriptDetailsqlDO();
                        deployDbscriptDetailsqlDO.setDeploydbscriptid(deployDbscriptDO.getDeploydbscriptid());
                        deployDbscriptDetailsqlDO.setSubsqlseqno(++maxSeqno);
                        deployDbscriptDetailsqlDO.setSubsql(sql);
                        deployDbscriptDetailsqlDO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));
                        deployDbscriptDetailsqlDO.setWillignore(Short.valueOf(String.valueOf(DBWillIgnoreEnum.NOT_IGNORE.getCode())));
                        deployDbscriptDetailsqlDO.setCreater(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
                        deployDbscriptDetailsqlDO.setCreaterip(clientIpAddr);
                        deployDbscriptDetailsqlDO.setCreatetime(new Date());

                        deployDbscriptDetailsqlDOList.add(deployDbscriptDetailsqlDO);
                    }
                    List<String> deployDbscriptDetailsqlidList = deployDbscriptDetailsqlService.batchInsert(deployDbscriptDetailsqlDOList);
                    if (deployDbscriptDetailsqlidList.size() != deployDbscriptDetailsqlDOList.size()) {
                        result = JsonResult.createFailed("save data abnormally");
                        result.addData("保存sql语句出现问题，保存失败。");
                        deployDbscriptDetailsqlService.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);
                        return result;
                    }
                }
                //更新主记录的isabandoned字段的内容
                deployDbscriptDO.setIsabandoned(Short.valueOf(String.valueOf(DBIsAbandonedEnum.NOT_ABANDONED.getCode())));
                int updateSuccessCount = deployDBScriptService.modifiyIsabandoned(deployDbscriptDO);
                result = JsonResult.createSuccess("update data successfully");
                result.addData("重新申请发布剩余脚本生效。");
            } else {
                result = JsonResult.createFailed("query data failed");
                result.addData("未找到记录！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "出现问题", e);
            result = JsonResult.createFailed("failed");
            result.addData("出现问题:" + e);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deployDbscript", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deployDbscript(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限执行脚本,请找管理员开权限。");
            return result;
        }

        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbscriptid())) {
            result = JsonResult.createFailed("failed");
            result.addData("入参中获取不到申请记录的id。");
            return result;
        }
        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbserversid())) {
            result = JsonResult.createFailed("failed");
            result.addData("入参中获取不到数据库连接的id。");
            return result;
        }

        DeployDbscript deployDbscript = null;

        try {
            deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptDTO.getDeploydbscriptid());
            if (deployDbscript == null) {
                result = JsonResult.createFailed("failed");
                result.addData("根据申请记录的id查询申请记录对象时发现找不到这个对象了。");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "根据申请记录的id查询申请记录对象时发现问题", e);
            result = JsonResult.createFailed("failed");
            result.addData("根据申请记录的id查询申请记录对象时发现问题:" + e);
            return result;
        }

        if (deployDbscript.getIsabandoned() == 1) {
            //当申请记录是已经放弃执行剩余的sql脚本的时候就不能执行发布了
            result = JsonResult.createFailed("failed");
            result.addData("当前申请已经放弃执行剩余的sql脚本了，所以发布过程不执行。");
            return result;
        }

        deployDbscript.setDeploydbserversid(deployDbscriptDTO.getDeploydbserversid());

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();

        BeanUtils.copyProperties(deployDbscript, deployDbscriptDO, true);

        deployDbscriptDO.setExecutor(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDO.setExecutorip(clientIpAddr);
        deployDbscriptDO.setExecutetime(new Date());

        if (deployDbscriptDO.getProjectid() == null || deployDbscriptDO.getModuleid() == null) {
            result = JsonResult.createFailed("failed");
            result.addData("没有绑定项目和模块！");
            return result;
        }
        if (StringUtils.isBlank(deployDbscriptDO.getDeploydbserversid())) {
            result = JsonResult.createFailed("failed");
            result.addData("没有绑定数据库连接！");
            return result;
        }

        PrjmodDblinkRelDO prjmodDblinkRelDO = new PrjmodDblinkRelDO();
        prjmodDblinkRelDO.setDeploydbserversid(deployDbscriptDO.getDeploydbserversid());
        prjmodDblinkRelDO.setProjectid(deployDbscriptDO.getProjectid());
        prjmodDblinkRelDO.setModuleid(deployDbscriptDO.getModuleid());
        prjmodDblinkRelDO.setBelong(deployDbscriptDO.getBelong());
        try {
            prjmodDblinkRelService.savePrjmodDblinkRel(prjmodDblinkRelDO);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "保存t_prjmod_dblink_rel表失败", e);
            result = JsonResult.createFailed("failed");
            result.addData("保存t_prjmod_dblink_rel表失败:" + e);
            return result;
        }

        String errorMsg = deployDBScriptService.deployDbscript(deployDbscriptDO);
        if (StringUtils.isBlank(errorMsg)) {
            result = JsonResult.createSuccess("success");
            result.addData("");
        } else {
            result = JsonResult.createSuccess("failed");
            result.addData(errorMsg);
        }
        return  result;

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
