package com.myself.deployrequester.service;

import com.myself.deployrequester.dao.DeployPerformanceDAO;
import com.myself.deployrequester.model.DeployPerformanceDO;
import com.myself.deployrequester.po.DeployPerformancePO;
import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployPerformanceService {
    @Autowired
    private DeployPerformanceDAO deployPerformanceDAO;

    public int insert(DeployPerformanceDO deployPerformanceDO) throws Exception {
        DeployPerformancePO deployPerformancePO = new DeployPerformancePO();
        BeanUtils.copyProperties(deployPerformanceDO, deployPerformancePO, false);
        int successRecCount = deployPerformanceDAO.insert(deployPerformancePO);
        if (successRecCount == 1) {
            deployPerformanceDO.setDeployperformanceid(deployPerformancePO.getDeployperformanceid());
        }
        return successRecCount;

    }

    public List<DeployPerformanceDO> selectByModulecodeAndModuletypecodeAndProjectcode(DeployPerformanceDO deployPerformanceDO) throws Exception {
        DeployPerformancePO deployPerformancePO = new DeployPerformancePO();
        BeanUtils.copyProperties(deployPerformanceDO, deployPerformancePO, false);
        List<DeployPerformanceDO> deployPerformanceDOList = null;

        List<DeployPerformancePO> deployPerformancePOList = deployPerformanceDAO.selectByModulecodeAndModuletypecodeAndProjectcode(deployPerformancePO);
        if (deployPerformancePOList != null) {
            deployPerformanceDOList = new ArrayList<DeployPerformanceDO>();
            for (DeployPerformancePO deployPerformancePO1 : deployPerformancePOList) {
                DeployPerformanceDO deployPerformanceDO1 = new DeployPerformanceDO();

                BeanUtils.copyProperties(deployPerformancePO1, deployPerformanceDO1, false);
                deployPerformanceDOList.add(deployPerformanceDO1);
            }
        }
        return deployPerformanceDOList;
    }

    public int updateByModulecodeAndModuletypecodeAndProjectcode(DeployPerformanceDO deployPerformanceDO) throws Exception {
        DeployPerformancePO deployPerformancePO = new DeployPerformancePO();
        BeanUtils.copyProperties(deployPerformanceDO, deployPerformancePO, false);
        return deployPerformanceDAO.updateByModulecodeAndModuletypecodeAndProjectcode(deployPerformancePO);
    }

    public synchronized boolean recordPerformance(DeployPerformanceDO deployPerformanceDO) throws Exception {
        int updataSuccessCount = updateByModulecodeAndModuletypecodeAndProjectcode(deployPerformanceDO);
        if (updataSuccessCount == 0) {
            int insertSuccessCount = insert(deployPerformanceDO);
            if (insertSuccessCount == 1) {
                return true;
            }
        } else if (updataSuccessCount == 1) {
            return true;
        }
        return false;
    }
}
