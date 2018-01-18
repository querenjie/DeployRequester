package com.myself.deployrequester.service;

import com.myself.deployrequester.dao.DeployTestEnvDAO;
import com.myself.deployrequester.model.DeployTestEnvDO;
import com.myself.deployrequester.po.DeployTestEnvPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployTestEnvService {
    @Autowired
    private DeployTestEnvDAO deployTestEnvDAO;

    /**
     * 发布测试环境的时候向t_deploy_testenv表中插入记录
     * @param deployTestEnvDO
     * @return
     * @throws Exception
     */
    public int insertDeployTestEnv(DeployTestEnvDO deployTestEnvDO) throws Exception {
        DeployTestEnvPO deployTestEnvPO = new DeployTestEnvPO();
        BeanUtils.copyProperties(deployTestEnvDO, deployTestEnvPO, false);
        int successRecCount = deployTestEnvDAO.insert(deployTestEnvPO);
        if (successRecCount == 1) {
            deployTestEnvDO.setDeploytestenvid(deployTestEnvPO.getDeploytestenvid());
        }
        return successRecCount;
    }

    public DeployTestEnvPO selectByPrimaryKey(String deploytestenvid) throws Exception {
        return deployTestEnvDAO.selectByPrimaryKey(deploytestenvid);
    }

    public int updateByPrimaryKeySelective(DeployTestEnvDO deployTestEnvDO) throws Exception {
        DeployTestEnvPO deployTestEnvPO = new DeployTestEnvPO();
        BeanUtils.copyProperties(deployTestEnvDO, deployTestEnvPO, false);
        return deployTestEnvDAO.updateByPrimaryKeySelective(deployTestEnvPO);
    }
}
