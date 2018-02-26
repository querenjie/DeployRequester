package com.myself.deployrequester.controller;

import com.myself.deployrequester.biz.config.sharedata.*;
import com.myself.deployrequester.bo.DeployDbscript;
import com.myself.deployrequester.bo.DeployDbservers;
import com.myself.deployrequester.dto.*;
import com.myself.deployrequester.model.*;
import com.myself.deployrequester.service.*;
import com.myself.deployrequester.util.DBScriptUtil;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.MD5Util;
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
import java.sql.Connection;
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
    @Autowired
    private DeployDbserversService deployDbserversService;
    @Autowired
    private DeployDbscriptSyncDetailsqlService deployDbscriptSyncDetailsqlService;

    @RequestMapping("/deploy_dbscript_insert")
    public String gotoDbscriptInsertForm() {
        return "deploy_dbscript_insert";
    }

    @RequestMapping("/deploy_dbscript_querylist")
    public String gotoDbscriptQuerylistForm() {
        return "deploy_dbscript_querylist";
    }

    @RequestMapping("/deploy_dbscript_querylist2")
    public String gotoDbscriptQuerylist2Form() {
        return "deploy_dbscript_querylist2";
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

            DeployDbserversDO deployDbserversDO = new DeployDbserversDO();
            deployDbserversDO.setBelong(deployDbscriptDO.getBelong());
            deployDbserversDO.setProjectid(deployDbscriptDO.getProjectid());
            deployDbserversDO.setModuleid(deployDbscriptDO.getModuleid());
            try {
                List<DeployDbservers> deployDbserversList = deployDbserversService.selectByDeployDbserversDO(deployDbserversDO);
                if (deployDbserversList != null && deployDbserversList.size() == 1) {
                    String deploydbserversid = deployDbserversList.get(0).getDeploydbserversid();
                    deployDbscriptDO.setDeploydbserversid(deploydbserversid);
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
                            //保存子sql到t_deploy_dbscript_detailsql表成功后
                            //获取同步表的数据库连接
                            DeployDbserversDO deployDbserversDO1 = new DeployDbserversDO();
                            deployDbserversDO1.setBelong(deployDbscriptDO.getBelong());
                            deployDbserversDO1.setProjectid(deployDbscriptDO.getProjectid());
                            deployDbserversDO1.setIssyncdb(Short.valueOf("1"));
                            List<DeployDbservers> deployDbserversList1 = deployDbserversService.selectByDeployDbserversDO(deployDbserversDO1);
                            if (deployDbserversList1 != null && deployDbserversList1.size() == 1) {
                                //这是同步库的链接对象
                                DeployDbservers deployDbserversForSync = deployDbserversList1.get(0);

                                //看看自己是不是同步库，如果不是的话才能考虑是否保存同步的sql。
                                boolean isSelfTheSameWithSyncDb = false;
                                if (StringUtils.isNotBlank(deployDbscriptDO.getDeploydbserversid()) && deployDbscriptDO.getDeploydbserversid().equals(deployDbserversForSync.getDeploydbserversid())) {
                                    isSelfTheSameWithSyncDb = true;
                                }

                                if (!isSelfTheSameWithSyncDb) {
                                    try {
                                        Connection conn = deployDBScriptService.getConn(deployDbserversForSync);
                                        if (conn != null) {
                                            //说明能连上同步库，然后就计算出需要同步的sql
                                            List<String> syncSqlList = deployDBScriptService.getSyncSqlList(conn, seperatedStatementList);
                                            if (conn != null) {
                                                conn.close();
                                            }
                                            if (syncSqlList != null && syncSqlList.size() > 0) {
                                                short j = 1;
                                                List<DeployDbscriptSyncDetailsqlDO> deployDbscriptSyncDetailsqlDOList = new ArrayList<DeployDbscriptSyncDetailsqlDO>();
                                                for (String sql : syncSqlList) {
                                                    DeployDbscriptSyncDetailsqlDO deployDbscriptSyncDetailsqlDO = new DeployDbscriptSyncDetailsqlDO();
                                                    deployDbscriptSyncDetailsqlDO.setDeploydbscriptid(deployDbscriptDO.getDeploydbscriptid());
                                                    deployDbscriptSyncDetailsqlDO.setSubsqlseqno(j++);
                                                    deployDbscriptSyncDetailsqlDO.setSubsql(sql);
                                                    deployDbscriptSyncDetailsqlDO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));
                                                    deployDbscriptSyncDetailsqlDO.setWillignore(Short.valueOf(String.valueOf(DBWillIgnoreEnum.NOT_IGNORE.getCode())));
                                                    deployDbscriptSyncDetailsqlDO.setCreater(deployDbscriptDO.getApplier());
                                                    deployDbscriptSyncDetailsqlDO.setCreaterip(deployDbscriptDO.getApplierip());
                                                    deployDbscriptSyncDetailsqlDO.setCreatetime(new Date());

                                                    deployDbscriptSyncDetailsqlDOList.add(deployDbscriptSyncDetailsqlDO);
                                                }
                                                List<String> deployDbscriptSyncDetailsqlidList = deployDbscriptSyncDetailsqlService.batchInsert(deployDbscriptSyncDetailsqlDOList);
                                                if (deployDbscriptSyncDetailsqlidList.size() == deployDbscriptSyncDetailsqlDOList.size()) {
                                                    deployDbscriptDO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));
                                                    deployDbscriptDO.setIsabandonedforsync(Short.valueOf(String.valueOf(DBIsAbandonedEnum.NOT_ABANDONED.getCode())));
                                                    int updateSuccess = deployDBScriptService.updateSelective(deployDbscriptDO);
                                                    if (updateSuccess == 1) {
                                                        result = JsonResult.createSuccess("save data successfully");
                                                        result.addData("整个保存申请记录的过程包括同步sql的保存成功。");
                                                        return result;
                                                    } else {
                                                        result = JsonResult.createFailed("save data abnormally");
                                                        result.addData("保存脚本申请成功，然而在保存完同步sql之后更新主记录的同步状态时找不到主记录。");
                                                        return result;
                                                    }
                                                } else {
                                                    result = JsonResult.createFailed("save data abnormally");
                                                    result.addData("保存同步的sql语句出现问题，保存失败。");
                                                    return result;
                                                }
                                            } else {
                                                //此处表示没有需要同步的sql,此时保存申请脚本的记录过程已经结束了。
                                                result = JsonResult.createSuccess("save data successfully");
                                                result.addData("保存申请记录的过程成功。");
                                                return result;
                                            }
                                        } else {
                                            //未能正常产生同步数据库的链接对象
                                            result = JsonResult.createFailed("failed");
                                            result.addData("未能正常产生同步数据库的链接对象");
                                            return result;
                                        }
                                    } catch (Exception e) {
                                        result = JsonResult.createFailed("failed");
                                        result.addData("连接同步数据库出现问题：" + e);
                                        return result;
                                    }
                                } else {
                                    //此处表示没有需要同步的sql,此时保存申请脚本的记录过程已经结束了。
                                    result = JsonResult.createSuccess("save data successfully");
                                    result.addData("保存申请记录的过程成功。");
                                    return result;
                                }
                            } else {
                                //如果没有找到同步库，返回成功。
                                result = JsonResult.createSuccess("save data successfully");
                                result.addData("保存申请记录的过程成功。");
                                return result;
                            }
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
                int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
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
    @RequestMapping(value = "/abandonDeployDbscriptForSync", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult abandonDeployDbscriptForSync(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
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
                if (deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("已经是执行成功状态，放弃同步脚本执行申请无效。");
                    return result;
                }
                if (deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("sql正在执行中，放弃同步脚本执行申请无效。");
                    return result;
                }
                //更新主记录的isabandoned字段的内容
                deployDbscriptDO.setIsabandonedforsync(Short.valueOf(String.valueOf(DBIsAbandonedEnum.ABANDONED.getCode())));
                int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
                result = JsonResult.createSuccess("update data successfully");
                result.addData("放弃同步脚本执行申请生效。");
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
                int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
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
    @RequestMapping(value = "/applyRedeployDbscriptForSync", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult applyRedeployDbscriptForSync(@RequestBody ApplyRedeployDbscriptDTO applyRedeployDbscriptDTO, HttpServletRequest request) {
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
                if (deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("已经是执行成功状态，申请重新执行剩余同步脚本无效。");
                    return result;
                }
                if (deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("sql正在执行中，申请重新执行剩余同步脚本无效。");
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
                int deleteUnexecutedSuccessCount = deployDbscriptSyncDetailsqlService.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);

                if (seperatedStatementList != null) {
                    //在拼装sql记录对象之前必须确定序号从那个数值开始
                    Short maxSeqno = deployDbscriptSyncDetailsqlService.selectMaxSeqno(deployDbscriptId);

                    List<DeployDbscriptSyncDetailsqlDO> deployDbscriptSyncDetailsqlDOList = new ArrayList<DeployDbscriptSyncDetailsqlDO>();
                    for (String sql : seperatedStatementList) {
                        DeployDbscriptSyncDetailsqlDO deployDbscriptSyncDetailsqlDO = new DeployDbscriptSyncDetailsqlDO();
                        deployDbscriptSyncDetailsqlDO.setDeploydbscriptid(deployDbscriptDO.getDeploydbscriptid());
                        deployDbscriptSyncDetailsqlDO.setSubsqlseqno(++maxSeqno);
                        deployDbscriptSyncDetailsqlDO.setSubsql(sql);
                        deployDbscriptSyncDetailsqlDO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));
                        deployDbscriptSyncDetailsqlDO.setWillignore(Short.valueOf(String.valueOf(DBWillIgnoreEnum.NOT_IGNORE.getCode())));
                        deployDbscriptSyncDetailsqlDO.setCreater(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
                        deployDbscriptSyncDetailsqlDO.setCreaterip(clientIpAddr);
                        deployDbscriptSyncDetailsqlDO.setCreatetime(new Date());

                        deployDbscriptSyncDetailsqlDOList.add(deployDbscriptSyncDetailsqlDO);
                    }
                    List<String> deployDbscriptSyncDetailsqlidList = deployDbscriptSyncDetailsqlService.batchInsert(deployDbscriptSyncDetailsqlDOList);
                    if (deployDbscriptSyncDetailsqlidList.size() != deployDbscriptSyncDetailsqlDOList.size()) {
                        result = JsonResult.createFailed("save data abnormally");
                        result.addData("保存sql语句出现问题，保存失败。");
                        deployDbscriptSyncDetailsqlService.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);
                        return result;
                    }
                }
                //更新主记录的isabandoned字段的内容
                deployDbscriptDO.setIsabandonedforsync(Short.valueOf(String.valueOf(DBIsAbandonedEnum.NOT_ABANDONED.getCode())));
                int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
                result = JsonResult.createSuccess("update data successfully");
                result.addData("重新申请发布剩余同步脚本生效。");
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

        if (deployDbscript.getIsabandoned() == Short.valueOf(String.valueOf(DBIsAbandonedEnum.ABANDONED.getCode()))) {
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

    @ResponseBody
    @RequestMapping(value = "/deployDbscriptForSync", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult deployDbscriptForSync(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
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

        if (deployDbscript.getIsabandonedforsync() == Short.valueOf(String.valueOf(DBIsAbandonedEnum.ABANDONED.getCode()))) {
            //当申请记录是已经放弃执行剩余的sql脚本的时候就不能执行发布了
            result = JsonResult.createFailed("failed");
            result.addData("当前申请已经放弃执行剩余的同步sql脚本了，所以发布过程不执行。");
            return result;
        }

        deployDbscript.setDeploydbserversid(deployDbscriptDTO.getDeploydbserversid());

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();

        BeanUtils.copyProperties(deployDbscript, deployDbscriptDO, true);

        deployDbscriptDO.setExecutorforsync(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDO.setExecutoripforsync(clientIpAddr);
        deployDbscriptDO.setExecutetimeforsync(new Date());

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

        String errorMsg = deployDBScriptService.deployDbscriptForSync(deployDbscriptDO);
        if (StringUtils.isBlank(errorMsg)) {
            result = JsonResult.createSuccess("success");
            result.addData("");
        } else {
            result = JsonResult.createSuccess("failed");
            result.addData(errorMsg);
        }
        return  result;

    }

    @ResponseBody
    @RequestMapping(value = "/assignCanexecute", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult assignCanexecute(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);

        String deployDbscriptId = queryDbscriptDTO.getDeploydbscriptid();
        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        deployDbscriptDO.setDeploydbscriptid(deployDbscriptId);
        deployDbscriptDO.setCanexecute(queryDbscriptDTO.getCanexecute());
        try {
            DeployDbscript deployDbscript = deployDBScriptService.getDeployDbscriptById(deployDbscriptId);
            if (deployDbscript != null) {
                if (!commonDataService.canChangeCanExecDbscript(clientIpAddr) && !clientIpAddr.equals(deployDbscript.getApplierip())) {
                    result = JsonResult.createFailed("failed");
                    result.addData("您没有权限修改何时执行脚本的状态,请找管理员开权限。");
                    return result;
                }
                if ("yes".equals(deployDbscript.getHasSyncSql())) {
                    if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()
                            && (deployDbscript.getExecutestatusforsync() != null && deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())) {
                        result = JsonResult.createFailed("failed");
                        result.addData("已经是执行成功状态，不能修改何时执行脚本的状态。");
                        return result;
                    }
                    if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()
                            && deployDbscript.getExecutestatusforsync() != null && deployDbscript.getExecutestatusforsync().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                        result = JsonResult.createFailed("failed");
                        result.addData("sql正在执行中，不能修改何时执行脚本的状态。");
                        return result;
                    }
                } else {
                    if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode()) {
                        result = JsonResult.createFailed("failed");
                        result.addData("已经是执行成功状态，不能修改何时执行脚本的状态。");
                        return result;
                    }
                    if (deployDbscript.getExecutestatus().intValue() == DBExecuteStatusEnum.EXECUTING.getCode()) {
                        result = JsonResult.createFailed("failed");
                        result.addData("sql正在执行中，不能修改何时执行脚本的状态。");
                        return result;
                    }
                }
                if (deployDbscript.getIsabandoned().intValue() == DBIsAbandonedEnum.ABANDONED.getCode()
                        && deployDbscript.getIsabandoned().intValue() == DBIsAbandonedEnum.ABANDONED.getCode()) {
                    result = JsonResult.createFailed("failed");
                    result.addData("脚本已经放弃执行了，不能修改何时执行脚本的状态。");
                    return result;
                }
                //更新主记录的canexecute字段的内容
                //deployDbscriptDO.canexecute的内容已经在页面中被赋值并提交到后台来了。
                int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
                result = JsonResult.createSuccess("update data successfully");
                result.addData("修改何时执行脚本的状态生效。");
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
    @RequestMapping(value = "/assignDeployDbscriptSuccess", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult assignDeployDbscriptSuccess(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result = null;

        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbscriptid())) {
            result = JsonResult.createFailed("save data failed");
            result.addData("缺少主键，操作中止。");
            return result;
        }

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限设置脚本的执行状态,请找管理员开权限。");
            return result;
        }
        deployDbscriptDTO.setExecutor(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDTO.setExecutorip(clientIpAddr);
        deployDbscriptDTO.setExecutetime(new Date());
        deployDbscriptDTO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);
        try {
            int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
            if (updateSuccessCount == 1) {
                result = JsonResult.createSuccess("save data successfully");
                result.addData("设置成功。");
                return result;
            } else if (updateSuccessCount == 0) {
                result = JsonResult.createFailed("save data failed");
                result.addData("貌似没找到需要更新的记录。");
                return result;
            } else {
                result = JsonResult.createFailed("save data failed");
                result.addData("更新的记录数量多余一条(更新了 " + updateSuccessCount + " 条记录)，这不正常。");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "更新记录出问题", e);
            result = JsonResult.createFailed("save data failed");
            result.addData("更新记录出问题。");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancelDeployDbscriptSuccess", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult cancelDeployDbscriptSuccess(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result = null;

        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbscriptid())) {
            result = JsonResult.createFailed("save data failed");
            result.addData("缺少主键，操作中止。");
            return result;
        }

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限设置脚本的执行状态,请找管理员开权限。");
            return result;
        }
        deployDbscriptDTO.setExecutor(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDTO.setExecutorip(clientIpAddr);
        deployDbscriptDTO.setExecutetime(new Date());
        deployDbscriptDTO.setExecutestatus(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);
        try {
            int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
            if (updateSuccessCount == 1) {
                result = JsonResult.createSuccess("save data successfully");
                result.addData("设置成功。");
                return result;
            } else if (updateSuccessCount == 0) {
                result = JsonResult.createFailed("save data failed");
                result.addData("貌似没找到需要更新的记录。");
                return result;
            } else {
                result = JsonResult.createFailed("save data failed");
                result.addData("更新的记录数量多余一条(更新了 " + updateSuccessCount + " 条记录)，这不正常。");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "更新记录出问题", e);
            result = JsonResult.createFailed("save data failed");
            result.addData("更新记录出问题。");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/assignDeployDbscriptForSyncSuccess", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult assignDeployDbscriptForSyncSuccess(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result = null;

        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbscriptid())) {
            result = JsonResult.createFailed("save data failed");
            result.addData("缺少主键，操作中止。");
            return result;
        }

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限设置脚本的执行状态,请找管理员开权限。");
            return result;
        }
        deployDbscriptDTO.setExecutorforsync(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDTO.setExecutoripforsync(clientIpAddr);
        deployDbscriptDTO.setExecutetimeforsync(new Date());
        deployDbscriptDTO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.EXECUTE_SUCCESSFULLY.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);
        try {
            int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
            if (updateSuccessCount == 1) {
                result = JsonResult.createSuccess("save data successfully");
                result.addData("设置成功。");
                return result;
            } else if (updateSuccessCount == 0) {
                result = JsonResult.createFailed("save data failed");
                result.addData("貌似没找到需要更新的记录。");
                return result;
            } else {
                result = JsonResult.createFailed("save data failed");
                result.addData("更新的记录数量多余一条(更新了 " + updateSuccessCount + " 条记录)，这不正常。");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "更新记录出问题", e);
            result = JsonResult.createFailed("save data failed");
            result.addData("更新记录出问题。");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancelDeployDbscriptForSyncSuccess", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult cancelDeployDbscriptForSyncSuccess(@RequestBody DeployDbscriptDTO deployDbscriptDTO, HttpServletRequest request) {
        JsonResult result = null;

        if (StringUtils.isBlank(deployDbscriptDTO.getDeploydbscriptid())) {
            result = JsonResult.createFailed("save data failed");
            result.addData("缺少主键，操作中止。");
            return result;
        }

        String clientIpAddr = getIpAddr(request);
        if (!commonDataService.canDeployDbscript(clientIpAddr)) {
            result = JsonResult.createFailed("failed");
            result.addData("您没有权限设置脚本的执行状态,请找管理员开权限。");
            return result;
        }
        deployDbscriptDTO.setExecutorforsync(ConfigData.IP_CREWNAME_MAPPING.get(clientIpAddr));
        deployDbscriptDTO.setExecutoripforsync(clientIpAddr);
        deployDbscriptDTO.setExecutetimeforsync(new Date());
        deployDbscriptDTO.setExecutestatusforsync(Short.valueOf(String.valueOf(DBExecuteStatusEnum.NOT_EXECUTE_YET.getCode())));

        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptDTO, deployDbscriptDO, true);
        try {
            int updateSuccessCount = deployDBScriptService.modifiy(deployDbscriptDO);
            if (updateSuccessCount == 1) {
                result = JsonResult.createSuccess("save data successfully");
                result.addData("设置成功。");
                return result;
            } else if (updateSuccessCount == 0) {
                result = JsonResult.createFailed("save data failed");
                result.addData("貌似没找到需要更新的记录。");
                return result;
            } else {
                result = JsonResult.createFailed("save data failed");
                result.addData("更新的记录数量多余一条(更新了 " + updateSuccessCount + " 条记录)，这不正常。");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.error(logger, "更新记录出问题", e);
            result = JsonResult.createFailed("save data failed");
            result.addData("更新记录出问题。");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryOnlyNeedDeploy", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult queryOnlyNeedDeploy(@RequestBody QueryDbscriptDTO queryDbscriptDTO, HttpServletRequest request) {
        JsonResult result;

        String clientIpAddr = getIpAddr(request);
        QueryDbscriptDO queryDbscriptDO = new QueryDbscriptDO();
        BeanUtils.copyProperties(queryDbscriptDTO, queryDbscriptDO, true);

        try {
            List<DeployDbscript> deployDbscriptList = deployDBScriptService.selectOnlyNeedDeployByQueryDbscriptDO(queryDbscriptDO);
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
