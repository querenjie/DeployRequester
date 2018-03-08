package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.DeployStatusForProdEnvEnum;
import com.myself.deployrequester.biz.config.sharedata.TestStatusEnum;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.dao.DeployAuditDAO;
import com.myself.deployrequester.dao.DeployRequesterDAO;
import com.myself.deployrequester.model.DeployAuditDO;
import com.myself.deployrequester.model.DeployDbscriptDO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.QueryDeployAuditDO;
import com.myself.deployrequester.po.DeployAuditPO;
import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.po.QueryDeployAuditPO;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployAuditService extends CommonDataService {
    /**
     * 日志
     */
    private static final Logger logger = LogManager.getLogger(DeployAuditService.class);

    @Autowired
    private DeployAuditDAO deployAuditDAO;
    @Autowired
    private DeployRequesterDAO deployRequesterDAO;

    private final short REST = 1;
    private final short PROVIDER = 2;

    /**
     * 向t_deploy_audit表中插入一条记录
     * @param deployRequestId
     * @return
     * @throws Exception
     */
    public int insert(String deployRequestId) throws Exception {
        DeployAuditPO deployAuditPO = new DeployAuditPO();
        deployAuditPO.setDeployrequestid(deployRequestId);
        return deployAuditDAO.insert(deployAuditPO);
    }

    /**
     * 根据QueryDeployAuditDO获取应用发布申请记录
     * @param queryDeployAuditDO
     * @return
     * @throws Exception
     */
    public QueryResults queryUnexecutedRequest(QueryDeployAuditDO queryDeployAuditDO) throws Exception {
        QueryDeployAuditPO queryDeployAuditPO = new QueryDeployAuditPO();
        BeanUtils.copyProperties(queryDeployAuditDO, queryDeployAuditPO, true);

        List<DeployRequesterPO> deployRequesterPOList = deployRequesterDAO.selectUnexecutedRequestByQueryDeployAuditPO(queryDeployAuditPO);
        List<DeployRequesterDO> deployRequesterDOList = new ArrayList<DeployRequesterDO>();
        if (deployRequesterPOList != null) {
            for (DeployRequesterPO deployRequestPO : deployRequesterPOList) {
                DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
                BeanUtils.copyProperties(deployRequestPO, deployRequesterDO, true);
                deployRequesterDOList.add(deployRequesterDO);
            }
        }

        QueryResults queryResults = new QueryResults();
        queryResults.setAllModuleList(ConfigData.MODULE_CONFIG);

        if (deployRequesterDOList != null) {
            Log4jUtil.info(logger, "deployRequesterDOList.size()="+deployRequesterDOList.size());
            //从结果集中获取moduleId
            List<Short> moduleList = new ArrayList<Short>();
            for (DeployRequesterDO deployRequesterDO : deployRequesterDOList) {
                Short moduleId = deployRequesterDO.getModulecode();
                if (!moduleList.contains(moduleId)) {
                    moduleList.add(moduleId);
                }
            }
            //此时moduleList中已经装好了moduleId，而且是不重复的。然后轮询这个moduleList对每个module的内容进行封装。
            for (Short moduleId : moduleList) {
                int thisModuleCount = 0;
                int thisModuleRestCount = 0;
                int thisModuleProviderCount = 0;
                QueryModuleStatistics queryModuleStatistics = new QueryModuleStatistics();
                for (DeployRequesterDO deployRequesterDO : deployRequesterDOList) {
                    if (moduleId.shortValue() == deployRequesterDO.getModulecode().shortValue()) {
                        //Log4jUtil.info(logger, "moduleId.shortValue()=" + moduleId.shortValue() + ",thisModuleCount=" + thisModuleCount + ",thisModuleRestCount="+thisModuleRestCount + ",thisModuleProviderCount="+thisModuleProviderCount);
                        //统计计数
                        queryModuleStatistics.setModuleCount(++thisModuleCount);
                        if (deployRequesterDO.getModuletypecode().shortValue() == REST) {
                            queryModuleStatistics.setModuleRestCount(++thisModuleRestCount);
                        }
                        if (deployRequesterDO.getModuletypecode().shortValue() == PROVIDER) {
                            queryModuleStatistics.setModuleProviderCount(++thisModuleProviderCount);
                        }
                        //设置module的code和name
                        for (Module module : queryResults.getAllModuleList()) {
                            if (!StringUtils.isBlank(queryModuleStatistics.getModuleCode()) && !StringUtils.isBlank(queryModuleStatistics.getModuleName())) {
                                //已经设置过值的话就不用再设置值了
                                break;
                            }
                            if (module.getId().shortValue() == moduleId.shortValue()) {
                                queryModuleStatistics.setModuleId(module.getId());
                                queryModuleStatistics.setModuleCode(module.getCode());
                                queryModuleStatistics.setModuleName(module.getName());
                            }
                        }
                        //记录加入到List中
                        DeployRequest deployRequest = new DeployRequest();
                        BeanUtils.copyProperties(deployRequesterDO, deployRequest, false);
                        fillDeployRequest(deployRequest);
                        queryModuleStatistics.getDeployRequestList().add(deployRequest);
                    }
                }
                queryResults.getQueryModuleStatisticsMap().put(moduleId, queryModuleStatistics);
            }
        }
        return queryResults;
    }

    /**
     * 根据项目id、模块id、模块类型id获取发布申请的记录id，同时满足这些都是要经过测试人员审核发布的。
     * @param projectId
     * @param moduleId
     * @param moduleTypeId
     * @return
     */
    public List<String> obtainUnexecutedRequestIdListUnderProjectModule(Short projectId, Short moduleId, Short moduleTypeId) throws Exception {
        QueryDeployAuditPO queryDeployAuditPO = new QueryDeployAuditPO();
        queryDeployAuditPO.setProjectCode(projectId);
        queryDeployAuditPO.setModuleCode(moduleId);
        queryDeployAuditPO.setModuleTypeCode(moduleTypeId);
        List<DeployRequesterPO> deployRequesterPOList = deployRequesterDAO.selectUnexecutedRequestByQueryDeployAuditPO(queryDeployAuditPO);

        List<String> requestIdList = null;
        if (deployRequesterPOList != null && deployRequesterPOList.size() > 0) {
            requestIdList = new ArrayList<String>();
            for (DeployRequesterPO deployRequesterPO : deployRequesterPOList) {
                requestIdList.add(deployRequesterPO.getDeployrequestid());
            }
        }

        return requestIdList;
    }

    /**
     *
     * @param deployrequestid
     * @return
     * @throws Exception
     */
    public DeployAuditDO selectByDeployRequestId(String deployrequestid) throws Exception {
        List<DeployAuditPO> deployAuditPOList = deployAuditDAO.selectByDeployRequestId(deployrequestid);
        if (deployAuditPOList == null || deployAuditPOList.size() == 0) {
            return null;
        }

        DeployAuditPO deployAuditPO = deployAuditPOList.get(0);
        DeployAuditDO deployAuditDO = new DeployAuditDO();
        BeanUtils.copyProperties(deployAuditPO, deployAuditDO, true);
        return deployAuditDO;
    }

    /**
     * 根据主键修改记录
     * @param deployAuditDO
     * @return
     * @throws Exception
     */
    public int updateByPrimaryKey(DeployAuditDO deployAuditDO) throws Exception {
        DeployAuditPO deployAuditPO = new DeployAuditPO();
        BeanUtils.copyProperties(deployAuditDO, deployAuditPO, true);
        int updateSuccessCount = deployAuditDAO.updateByPrimaryKey(deployAuditPO);
        return updateSuccessCount;
    }

    /**
     * 填充传入对象中的各个名称属性的内容
     * @param deployRequest
     */
    private void fillDeployRequest(DeployRequest deployRequest) {
        if (deployRequest == null) {
            return;
        }

        /********************补全deployRequest中的属性值(begin)*******************************/
        Project project = getProjectById(Integer.valueOf(deployRequest.getProjectcode()).intValue());
        deployRequest.setProjectName(project.getProjectName());

        if (deployRequest.getModulecode() != null) {
            Module module = getModuleById(deployRequest.getModulecode());
            deployRequest.setModuleCodeName(module.getCode());
            deployRequest.setModuleDesc(module.getName());
        }
        if (deployRequest.getModuletypecode() != null) {
            deployRequest.setModuleTypeName(getModuleTypeNameById(deployRequest.getModuletypecode()));
        }
        if (deployRequest.getModifytype() != null) {
            deployRequest.setModifyTypeName(getModifyTypeNameById(Integer.parseInt(String.valueOf(deployRequest.getModifytype()))));
        }
        if (deployRequest.getIstestok() != null) {
            deployRequest.setIsTestOkDesc(TestStatusEnum.getDescByCode(deployRequest.getIstestok()));
        }
        if (deployRequest.getDeploystatusforprodenv() != null) {
            deployRequest.setDeployStatusForProdEnvDesc(DeployStatusForProdEnvEnum.getDescByCode(deployRequest.getDeploystatusforprodenv().intValue()));
        }

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        if (deployRequest.getCreatetime() != null) {
            deployRequest.setFormatedCreateDate(formatter.format(deployRequest.getCreatetime()));
        }
        if (deployRequest.getTestflagmodifytime() != null) {
            deployRequest.setFormatedTestflagmodifytime(formatter.format(deployRequest.getTestflagmodifytime()));
        }
        /********************补全deployRequest中的属性值( end )*******************************/

    }

}