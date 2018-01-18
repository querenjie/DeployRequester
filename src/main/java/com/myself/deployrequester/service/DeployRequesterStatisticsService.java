package com.myself.deployrequester.service;

import com.myself.deployrequester.bo.DeployRequesterStatistics1;
import com.myself.deployrequester.bo.Module;
import com.myself.deployrequester.bo.Project;
import com.myself.deployrequester.dao.DeployRequesterStatistics1DAO;
import com.myself.deployrequester.model.DeployRequesterStatistics1DO;
import com.myself.deployrequester.model.QueryCriteriaStatistics1DO;
import com.myself.deployrequester.po.DeployRequesterStatistics1PO;
import com.myself.deployrequester.po.QueryCriteriaStatistics1PO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
public class DeployRequesterStatisticsService extends CommonDataService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployRequesterStatisticsService.class);

    @Autowired
    private DeployRequesterStatistics1DAO deployRequesterStatistics1DAO;

    /**
     * 获取统计结果
     * @param queryCriteriaStatistics1DO
     * @return
     * @throws Exception
     */
    public List<DeployRequesterStatistics1> selectByQueryCriteriaStatistics1(QueryCriteriaStatistics1DO queryCriteriaStatistics1DO) throws Exception {
        QueryCriteriaStatistics1PO queryCriteriaStatistics1PO = new QueryCriteriaStatistics1PO();
        BeanUtils.copyProperties(queryCriteriaStatistics1DO, queryCriteriaStatistics1PO, false);
        List<DeployRequesterStatistics1PO> deployRequesterStatistics1POList = null;
        if ("1".equals(queryCriteriaStatistics1DO.getGroupColumnsFlag())) {
            deployRequesterStatistics1POList = deployRequesterStatistics1DAO.select1ByQueryCriteriaStatistics1PO(queryCriteriaStatistics1PO);
        } else if ("2".equals(queryCriteriaStatistics1DO.getGroupColumnsFlag())) {
            deployRequesterStatistics1POList = deployRequesterStatistics1DAO.select2ByQueryCriteriaStatistics1PO(queryCriteriaStatistics1PO);
        } else if ("3".equals(queryCriteriaStatistics1DO.getGroupColumnsFlag())) {
            deployRequesterStatistics1POList = deployRequesterStatistics1DAO.select3ByQueryCriteriaStatistics1PO(queryCriteriaStatistics1PO);
        }
        List<DeployRequesterStatistics1DO> deployRequesterStatistics1DOList = null;
        if (deployRequesterStatistics1POList != null) {
            deployRequesterStatistics1DOList = new ArrayList<DeployRequesterStatistics1DO>();
            for (DeployRequesterStatistics1PO deployRequesterStatistics1PO : deployRequesterStatistics1POList) {
                DeployRequesterStatistics1DO deployRequesterStatistics1DO = new DeployRequesterStatistics1DO();
                BeanUtils.copyProperties(deployRequesterStatistics1PO, deployRequesterStatistics1DO, true);
                deployRequesterStatistics1DOList.add(deployRequesterStatistics1DO);
            }
        }
        List<DeployRequesterStatistics1> deployRequesterStatistics1List = new ArrayList<DeployRequesterStatistics1>();
        if (deployRequesterStatistics1DOList != null) {
            for (DeployRequesterStatistics1DO deployRequesterStatistics1DO : deployRequesterStatistics1DOList) {
                DeployRequesterStatistics1 deployRequesterStatistics1 = new DeployRequesterStatistics1();
                BeanUtils.copyProperties(deployRequesterStatistics1DO, deployRequesterStatistics1, true);
                fillDeployRequesterStatistics1(deployRequesterStatistics1);
                deployRequesterStatistics1List.add(deployRequesterStatistics1);
            }
        }

        return deployRequesterStatistics1List;
    }

    /**
     * 填充传入对象中的各个名称属性的内容
     * @param deployRequesterStatistics1
     */
    private void fillDeployRequesterStatistics1(DeployRequesterStatistics1 deployRequesterStatistics1) {
        if (deployRequesterStatistics1 == null) {
            return;
        }

        /********************补全deployRequesterStatistics1中的属性值(begin)*******************************/
        if (deployRequesterStatistics1.getProjectcode() != null) {
            Project project = getProjectById(Integer.valueOf(deployRequesterStatistics1.getProjectcode()).intValue());
            if (project != null && !StringUtils.isBlank(project.getProjectName())) {
                deployRequesterStatistics1.setProjectName(project.getProjectName());
            }
        }

        if (deployRequesterStatistics1.getModulecode() != null) {
            Module module = getModuleById(deployRequesterStatistics1.getModulecode());
            if (module != null && !StringUtils.isBlank(module.getCode())) {
                deployRequesterStatistics1.setModuleCodeName(module.getCode());
            }
        }

        if (deployRequesterStatistics1.getModuletypecode() != null) {
            deployRequesterStatistics1.setModuleTypeName(getModuleTypeNameById(deployRequesterStatistics1.getModuletypecode()));
        }
        /********************补全deployRequesterStatistics1中的属性值( end )*******************************/

    }

}
