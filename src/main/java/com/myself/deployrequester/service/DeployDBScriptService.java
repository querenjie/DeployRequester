package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.DBExecuteStatusEnum;
import com.myself.deployrequester.biz.config.sharedata.DeployStatusForProdEnvEnum;
import com.myself.deployrequester.biz.config.sharedata.EnvOfDBEnum;
import com.myself.deployrequester.biz.config.sharedata.TestStatusEnum;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.dao.DeployDbscriptDAO;
import com.myself.deployrequester.dao.DeployDbscriptDetailsqlDAO;
import com.myself.deployrequester.dao.DeployDbserversDAO;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.QueryDbscriptDO;
import com.myself.deployrequester.po.DeployDbscriptPO;
import com.myself.deployrequester.po.DeployDbserversPO;
import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.po.QueryDbscriptPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployDBScriptService extends CommonDataService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployDBScriptService.class);

    @Autowired
    private DeployDbscriptDAO deployDbscriptDAO;
    @Autowired
    private DeployDbserversDAO deployDbserversDAO;
    @Autowired
    private DeployDbscriptDetailsqlDAO deployDbscriptDetailsqlDAO;

    public int insert(DeployDbscriptDO deployDbscriptDO) throws Exception {
        DeployDbscriptPO deployDbscriptPO = new DeployDbscriptPO();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscriptPO, false);
        int insertSuccessRecordCount = deployDbscriptDAO.insert(deployDbscriptPO);
        if (insertSuccessRecordCount == 1) {
            //如果插入数据成功的话就直接把id赋值给DO对象
            deployDbscriptDO.setDeploydbscriptid(deployDbscriptPO.getDeploydbscriptid());
        }
        return insertSuccessRecordCount;
    }

    public List<DeployDbscript> selectByQueryDbscriptDO(QueryDbscriptDO queryDbscriptDO) throws Exception {
        QueryDbscriptPO queryDbscriptPO = new QueryDbscriptPO();
        BeanUtils.copyProperties(queryDbscriptDO, queryDbscriptPO, true);
        List<DeployDbscriptPO> deployDbscriptPOList = deployDbscriptDAO.selectByQueryDbscriptPO(queryDbscriptPO);
        if (deployDbscriptPOList == null) {
            return null;
        }
        List<DeployDbscript> deployDbscriptList = new ArrayList<DeployDbscript>();
        for (DeployDbscriptPO deployDbscriptPO : deployDbscriptPOList) {
            DeployDbscript deployDbscript = new DeployDbscript();
            BeanUtils.copyProperties(deployDbscriptPO, deployDbscript, true);
            fillDeployDbscript(deployDbscript);
            deployDbscriptList.add(deployDbscript);
        }
        return deployDbscriptList;
    }

    /**
     * 根据脚本申请的主键获取脚本申请的对象
     * @param deployDbscriptId
     * @return
     * @throws Exception
     */
    public DeployDbscript getDeployDbscriptById(String deployDbscriptId) throws Exception {
        DeployDbscriptPO deployDbscriptPO = deployDbscriptDAO.selectByPrimaryKey(deployDbscriptId);
        if (deployDbscriptPO == null) {
            return null;
        }
        DeployDbscriptDO deployDbscriptDO = new DeployDbscriptDO();
        BeanUtils.copyProperties(deployDbscriptPO, deployDbscriptDO, true);
        DeployDbscript deployDbscript = new DeployDbscript();
        BeanUtils.copyProperties(deployDbscriptDO, deployDbscript, false);
        fillDeployDbscript(deployDbscript);
        return deployDbscript;
    }

    /**
     * 根据脚本申请的主键删除该条记录
     * @param deployDbscriptId
     * @return
     * @throws Exception
     */
    public int deleteById(String deployDbscriptId) throws Exception {
        deployDbscriptDetailsqlDAO.deleteByDeployDbscriptId(deployDbscriptId);
        int delRecCount = deployDbscriptDAO.deleteByPrimaryKey(deployDbscriptId);
        return delRecCount;
    }

    /**
     * 执行脚本并记录状态
     * @param deployDbscriptDO
     * @return
     * @throws Exception
     */
    public boolean deployDbscript(DeployDbscriptDO deployDbscriptDO) throws Exception {
        if (StringUtils.isBlank(deployDbscriptDO.getDbscript())) {
            //如果脚本内容为空则表示执行失败
            return false;
        }
        deployDbscriptDO.setExecutetime(new Date());






        return false;
    }

    private void fillDeployDbscript(DeployDbscript deployDbscript) {
        if (deployDbscript == null) {
            return;
        }

        /********************补全deployDbscript中的属性值(begin)*******************************/
        Project project = getProjectById(deployDbscript.getProjectid().intValue());
        deployDbscript.setProjectName(project.getProjectName());

        Module module = getModuleById(deployDbscript.getModuleid());
        deployDbscript.setModuleName(module.getCode() + "-" + module.getName());

        if (deployDbscript.getDeploydbserversid() != null) {
            DeployDbserversPO deployDbserversPO = deployDbserversDAO.selectByPrimaryKey(deployDbscript.getDeploydbserversid());
            if (deployDbserversPO != null) {
                String dbLinkDesc = deployDbserversPO.getLinkname() + "(" + deployDbserversPO.getLinknamedesc() + ")" + "[" + EnvOfDBEnum.getDescByCode(deployDbserversPO.getBelong().intValue()) + "--" + deployDbserversPO.getIp() + ":" + deployDbserversPO.getPort() + "/" + deployDbserversPO.getDbname() + "]";
                deployDbscript.setDblinkDesc(dbLinkDesc);
            }
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        deployDbscript.setFormatedCreateTime(formatter.format(deployDbscript.getCreatetime()));
        if (deployDbscript.getExecutetime() != null) {
            deployDbscript.setFormatedExecutetime(formatter.format(deployDbscript.getExecutetime()));
        }

        deployDbscript.setExecuteStatusDesc(DBExecuteStatusEnum.getDescByCode(deployDbscript.getExecutestatus().intValue()));
        deployDbscript.setBelongDesc(EnvOfDBEnum.getDescByCode(deployDbscript.getBelong().intValue()));
        /********************补全deployDbscript中的属性值( end )*******************************/

    }


}
