package com.myself.deployrequester.service;

import com.myself.deployrequester.dao.DeployDbscriptDetailsqlDAO;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.DeployDbscriptDetailsqlDO;
import com.myself.deployrequester.po.DeployDbscriptDetailsqlPO;
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
public class DeployDbscriptDetailsqlService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployDbscriptDetailsqlService.class);

    @Autowired
    private DeployDbscriptDetailsqlDAO deployDbscriptDetailsqlDAO;

    /**
     * 批量插入数据到t_deploy_dbscript_detailsql。
     * 如果返回的结果集中的元素数量和入参集合中的元素数量一样的话那就说明保存是成功的.
     * @param deployDbscriptDOList
     * @return
     * @throws Exception
     */
    public List<String> batchInsert(List<DeployDbscriptDetailsqlDO> deployDbscriptDetailsqlDOList) throws Exception {
        List<String> deployDbscriptDetailsqlidList = new ArrayList<String>();

        for (DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO : deployDbscriptDetailsqlDOList) {
            DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO = new DeployDbscriptDetailsqlPO();
            BeanUtils.copyProperties(deployDbscriptDetailsqlDO, deployDbscriptDetailsqlPO, false);
            int insertSuccessRecCount = deployDbscriptDetailsqlDAO.insert(deployDbscriptDetailsqlPO);
            if (insertSuccessRecCount == 1) {
                deployDbscriptDetailsqlDO.setDeploydbscriptdetailsqlid(deployDbscriptDetailsqlPO.getDeploydbscriptdetailsqlid());
                deployDbscriptDetailsqlidList.add(deployDbscriptDetailsqlDO.getDeploydbscriptdetailsqlid());
            } else {
                //只要有一个无法保存那么整个批次的sql都回退不保存。
                for (int i = 0; i < deployDbscriptDetailsqlidList.size(); i++) {
                    String deployDbscriptDetailsqlid = deployDbscriptDetailsqlidList.get(i);
                    deployDbscriptDetailsqlDAO.deleteByPrimaryKey(deployDbscriptDetailsqlid);
                    deployDbscriptDetailsqlidList.remove(deployDbscriptDetailsqlid);
                    i--;
                }
            }
        }

        return deployDbscriptDetailsqlidList;
    }

    public int deleteByDeployDbscriptId(String deployDbscriptId) throws Exception {
        return deployDbscriptDetailsqlDAO.deleteByDeployDbscriptId(deployDbscriptId);
    }

    /**
     * 获取指定的脚本申请记录中已经执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    public List<DeployDbscriptDetailsqlDO> selectExecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        List<DeployDbscriptDetailsqlPO> deployDbscriptDetailsqlPOList = deployDbscriptDetailsqlDAO.selectExecutedByDeployDbscriptId(deployDbscriptId);
        if (deployDbscriptDetailsqlPOList == null) {
            return null;
        }

        List<DeployDbscriptDetailsqlDO> deployDbscriptDetailsqlDOList = new ArrayList<DeployDbscriptDetailsqlDO>();
        for (DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO : deployDbscriptDetailsqlPOList) {
            DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO = new DeployDbscriptDetailsqlDO();
            BeanUtils.copyProperties(deployDbscriptDetailsqlPO, deployDbscriptDetailsqlDO, true);
            deployDbscriptDetailsqlDOList.add(deployDbscriptDetailsqlDO);
        }

        return deployDbscriptDetailsqlDOList;
    }

    /**
     * 获取指定的脚本申请记录中尚未执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    public List<DeployDbscriptDetailsqlDO> selectUnexecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        List<DeployDbscriptDetailsqlPO> deployDbscriptDetailsqlPOList = deployDbscriptDetailsqlDAO.selectUnexecutedByDeployDbscriptId(deployDbscriptId);
        if (deployDbscriptDetailsqlPOList == null) {
            return null;
        }

        List<DeployDbscriptDetailsqlDO> deployDbscriptDetailsqlDOList = new ArrayList<DeployDbscriptDetailsqlDO>();
        for (DeployDbscriptDetailsqlPO deployDbscriptDetailsqlPO : deployDbscriptDetailsqlPOList) {
            DeployDbscriptDetailsqlDO deployDbscriptDetailsqlDO = new DeployDbscriptDetailsqlDO();
            BeanUtils.copyProperties(deployDbscriptDetailsqlPO, deployDbscriptDetailsqlDO, true);
            deployDbscriptDetailsqlDOList.add(deployDbscriptDetailsqlDO);
        }

        return deployDbscriptDetailsqlDOList;
    }

    /**
     * 删除指定的脚本申请记录中尚未执行过的sql信息记录
     */
    public int deleteUnexecutedByDeployDbscriptId(String deployDbscriptId) throws Exception {
        return deployDbscriptDetailsqlDAO.deleteUnexecutedByDeployDbscriptId(deployDbscriptId);
    }

    /**
     * 获取指定的申请记录中的子sql记录的最大的序号
     * @param deployDbscriptId
     * @return
     */
    public Short selectMaxSeqno(String deployDbscriptId) throws Exception {
        return deployDbscriptDetailsqlDAO.selectMaxSeqno(deployDbscriptId);
    }
}
