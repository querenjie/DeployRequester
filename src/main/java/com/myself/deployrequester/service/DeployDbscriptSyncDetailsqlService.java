package com.myself.deployrequester.service;

import com.myself.deployrequester.dao.DeployDbscriptSyncDetailsqlDAO;
import com.myself.deployrequester.model.DeployDbscriptDetailsqlDO;
import com.myself.deployrequester.model.DeployDbscriptSyncDetailsqlDO;
import com.myself.deployrequester.po.DeployDbscriptDetailsqlPO;
import com.myself.deployrequester.po.DeployDbscriptSyncDetailsqlPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployDbscriptSyncDetailsqlService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployDbscriptSyncDetailsqlService.class);

    @Autowired
    private DeployDbscriptSyncDetailsqlDAO deployDbscriptSyncDetailsqlDAO;

    /**
     * 批量插入数据到t_deploy_dbscript_sync_detailsql。
     * 如果返回的结果集中的元素数量和入参集合中的元素数量一样的话那就说明保存是成功的.
     * @param deployDbscriptSyncDetailsqlDOList
     * @return
     * @throws Exception
     */
    public List<String> batchInsert(List<DeployDbscriptSyncDetailsqlDO> deployDbscriptSyncDetailsqlDOList) throws Exception {
        List<String> deployDbscriptSyncDetailsqlidList = new ArrayList<String>();

        for (DeployDbscriptSyncDetailsqlDO deployDbscriptSyncDetailsqlDO : deployDbscriptSyncDetailsqlDOList) {
            DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO = new DeployDbscriptSyncDetailsqlPO();
            BeanUtils.copyProperties(deployDbscriptSyncDetailsqlDO, deployDbscriptSyncDetailsqlPO, false);
            int insertSuccessRecCount = deployDbscriptSyncDetailsqlDAO.insert(deployDbscriptSyncDetailsqlPO);
            if (insertSuccessRecCount == 1) {
                deployDbscriptSyncDetailsqlDO.setDeploydbscriptsyncdetailsqlid(deployDbscriptSyncDetailsqlPO.getDeploydbscriptsyncdetailsqlid());
                deployDbscriptSyncDetailsqlidList.add(deployDbscriptSyncDetailsqlDO.getDeploydbscriptsyncdetailsqlid());
            } else {
                //只要有一个无法保存那么整个批次的sql都回退不保存。
                for (int i = 0; i < deployDbscriptSyncDetailsqlidList.size(); i++) {
                    String deployDbscriptSyncDetailsqlid = deployDbscriptSyncDetailsqlidList.get(i);
                    deployDbscriptSyncDetailsqlDAO.deleteByPrimaryKey(deployDbscriptSyncDetailsqlid);
                    deployDbscriptSyncDetailsqlidList.remove(deployDbscriptSyncDetailsqlid);
                    i--;
                }
            }
        }

        return deployDbscriptSyncDetailsqlidList;
    }

    public int deleteByDeployDbscriptId(String deployDbscriptId) throws Exception {
        return deployDbscriptSyncDetailsqlDAO.deleteByDeployDbscriptId(deployDbscriptId);
    }

    /**
     * 获取指定的脚本申请记录中已经执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    public List<DeployDbscriptSyncDetailsqlDO> selectExecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        List<DeployDbscriptSyncDetailsqlPO> deployDbscriptSyncDetailsqlPOList = deployDbscriptSyncDetailsqlDAO.selectExecutedByDeployDbscriptId(deployDbscriptId);
        if (deployDbscriptSyncDetailsqlPOList == null) {
            return null;
        }

        List<DeployDbscriptSyncDetailsqlDO> deployDbscriptSyncDetailsqlDOList = new ArrayList<DeployDbscriptSyncDetailsqlDO>();
        for (DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO : deployDbscriptSyncDetailsqlPOList) {
            DeployDbscriptSyncDetailsqlDO deployDbscriptSyncDetailsqlDO = new DeployDbscriptSyncDetailsqlDO();
            BeanUtils.copyProperties(deployDbscriptSyncDetailsqlPO, deployDbscriptSyncDetailsqlDO, true);
            deployDbscriptSyncDetailsqlDOList.add(deployDbscriptSyncDetailsqlDO);
        }

        return deployDbscriptSyncDetailsqlDOList;
    }

    /**
     * 获取指定的脚本申请记录中尚未执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    public List<DeployDbscriptSyncDetailsqlDO> selectUnexecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        List<DeployDbscriptSyncDetailsqlPO> deployDbscriptSyncDetailsqlPOList = deployDbscriptSyncDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscriptId);
        if (deployDbscriptSyncDetailsqlPOList == null) {
            return null;
        }

        List<DeployDbscriptSyncDetailsqlDO> deployDbscriptSyncDetailsqlDOList = new ArrayList<DeployDbscriptSyncDetailsqlDO>();
        for (DeployDbscriptSyncDetailsqlPO deployDbscriptSyncDetailsqlPO : deployDbscriptSyncDetailsqlPOList) {
            DeployDbscriptSyncDetailsqlDO deployDbscriptSyncDetailsqlDO = new DeployDbscriptSyncDetailsqlDO();
            BeanUtils.copyProperties(deployDbscriptSyncDetailsqlPO, deployDbscriptSyncDetailsqlDO, true);
            deployDbscriptSyncDetailsqlDOList.add(deployDbscriptSyncDetailsqlDO);
        }

        return deployDbscriptSyncDetailsqlDOList;
    }

    /**
     * 删除指定的脚本申请记录中尚未执行过的sql信息记录
     */
    public int deleteUnexecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        return deployDbscriptSyncDetailsqlDAO.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);
    }

    /**
     * 获取指定的申请记录中的子sql记录的最大的序号
     * @param deployDbscriptId
     * @return
     */
    public Short selectMaxSeqno(String deployDbscriptId) throws Exception {
        return deployDbscriptSyncDetailsqlDAO.selectMaxSeqno(deployDbscriptId);
    }
}
