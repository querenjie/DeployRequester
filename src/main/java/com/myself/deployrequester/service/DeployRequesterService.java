package com.myself.deployrequester.service;

import com.myself.deployrequester.biz.config.sharedata.ConfigData;
import com.myself.deployrequester.biz.config.sharedata.DeployStatusForProdEnvEnum;
import com.myself.deployrequester.biz.config.sharedata.LockElement;
import com.myself.deployrequester.biz.config.sharedata.TestStatusEnum;
import com.myself.deployrequester.bo.*;
import com.myself.deployrequester.controller.NoticeBoardController;
import com.myself.deployrequester.dao.DeployRequesterDAO;
import com.myself.deployrequester.model.DeployRequesterDO;
import com.myself.deployrequester.model.ProduceApplicationQueryCriteriaDO;
import com.myself.deployrequester.model.QueryCriteriaDO;
import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.po.ProduceApplicationQueryCriteriaPO;
import com.myself.deployrequester.po.QueryCriteriaPO;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.MD5Util;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class DeployRequesterService extends CommonDataService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DeployRequesterService.class);

    @Autowired
    private DeployRequesterDAO deployRequesterDAO;

    private final short REST = 1;
    private final short PROVIDER = 2;

    /**
     * 锁定发布申请，使其不能发布.
     * @param projectId
     * @param moduleId
     * @param moduleTypeId
     */
    public void lockDeployRequest(Short projectId, Short moduleId, Short moduleTypeId) {
        if (projectId != null && moduleId != null && moduleTypeId != null) {
            if (ConfigData.LOCK_ELEMENT_LIST != null) {
                for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
                    if (lockElement.getProjectId().shortValue() == projectId.shortValue()
                            && lockElement.getModuleId().shortValue() == moduleId.shortValue()
                            && lockElement.getModuleTypeId().shortValue() == moduleTypeId.shortValue()) {
                        lockElement.setLocked(true);
                    }
                }
            }
        } else if (projectId != null && moduleId != null && moduleTypeId == null) {
            if (ConfigData.LOCK_ELEMENT_LIST != null) {
                for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
                    if (lockElement.getProjectId().shortValue() == projectId.shortValue()
                            && lockElement.getModuleId().shortValue() == moduleId.shortValue()) {
                        lockElement.setLocked(true);
                    }
                }
            }
        } else if (projectId != null && moduleId == null && moduleTypeId == null) {
            if (ConfigData.LOCK_ELEMENT_LIST != null) {
                for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
                    if (lockElement.getProjectId().shortValue() == projectId.shortValue()) {
                        lockElement.setLocked(true);
                    }
                }
            }
        } else if (projectId != null && moduleId == null && moduleTypeId != null) {
            if (ConfigData.LOCK_ELEMENT_LIST != null) {
                for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
                    if (lockElement.getProjectId().shortValue() == projectId.shortValue()
                            && lockElement.getModuleTypeId().shortValue() == moduleTypeId.shortValue()) {
                        lockElement.setLocked(true);
                    }
                }
            }
        }

    }

    /**
     * 解锁发布申请，使其可以继续发布.
     * @param projectId
     * @param moduleId
     * @param moduleTypeId
     */
    public void unlockDeployRequest(Short projectId, Short moduleId, Short moduleTypeId) {
        if (projectId != null && moduleId != null && moduleTypeId != null) {
            if (ConfigData.LOCK_ELEMENT_LIST != null) {
                for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
                    if (lockElement.getProjectId().shortValue() == projectId.shortValue()
                            && lockElement.getModuleId().shortValue() == moduleId.shortValue()
                            && lockElement.getModuleTypeId().shortValue() == moduleTypeId.shortValue()) {
                        lockElement.setLocked(false);
                    }
                }
            }
        }
    }

    /**
     * 获取所有被锁定的模块
     * @return
     * @throws Exception
     */
    public List<DeployRequest> getLockedDeployRequest() throws Exception {
        if (ConfigData.LOCK_ELEMENT_LIST == null) {
            return null;
        }
        List<DeployRequest> lockedDeployRequestList = new ArrayList<DeployRequest>();
        for (LockElement lockElement : ConfigData.LOCK_ELEMENT_LIST) {
            if (lockElement.isLocked()) {
                DeployRequest deployRequest = new DeployRequest();
                deployRequest.setProjectcode(lockElement.getProjectId());
                deployRequest.setModulecode(lockElement.getModuleId());
                deployRequest.setModuletypecode(lockElement.getModuleTypeId());
                fillDeployRequest(deployRequest);
                lockedDeployRequestList.add(deployRequest);
            }
        }
        return lockedDeployRequestList;
    }

    /**
     * insert deployment request information into table t_deploy_request
     *
     * @param deployRequesterDO
     * @return 1 indicates success, other values indicates failure
     */
    public int insertDeployRequester(DeployRequesterDO deployRequesterDO) throws Exception {
        DeployRequesterPO deployRequesterPO = new DeployRequesterPO();
        BeanUtils.copyProperties(deployRequesterDO, deployRequesterPO, false);
        int successRecCount = deployRequesterDAO.insert(deployRequesterPO);
        if (successRecCount == 1) {
            deployRequesterDO.setDeployrequestid(deployRequesterPO.getDeployrequestid());
        }
        return successRecCount;
    }

    public int updateByPrimaryKeySelective(DeployRequesterDO deployRequesterDO) throws Exception {
        DeployRequesterPO deployRequesterPO = new DeployRequesterPO();
        BeanUtils.copyProperties(deployRequesterDO, deployRequesterPO, true);
        return deployRequesterDAO.updateByPrimaryKeySelective(deployRequesterPO);
    }

    public int updateIsTestOk(DeployRequesterDO deployRequesterDO) throws Exception {
        DeployRequesterPO deployRequesterPO = new DeployRequesterPO();
        BeanUtils.copyProperties(deployRequesterDO, deployRequesterPO, false);
        return deployRequesterDAO.updateIsTestOk(deployRequesterPO);
    }

    public QueryResults selectByQueryCriteria(QueryCriteriaDO queryCriteriaDO) throws Exception {
        QueryCriteriaPO queryCriteriaPO = new QueryCriteriaPO();
        BeanUtils.copyProperties(queryCriteriaDO, queryCriteriaPO, true);
        Log4jUtil.info(logger, queryCriteriaPO);
        List<DeployRequesterPO> deployRequesterPOList = deployRequesterDAO.selectByQueryCriteriaPO(queryCriteriaPO);
        List<DeployRequesterDO> deployRequesterDOList = null;
        if (deployRequesterPOList != null) {
            deployRequesterDOList = new ArrayList<DeployRequesterDO>();
            for (DeployRequesterPO deployRequestPO : deployRequesterPOList) {
                DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
                BeanUtils.copyProperties(deployRequestPO, deployRequesterDO, true);
                deployRequesterDOList.add(deployRequesterDO);
            }
        }

        QueryResults queryResults = new QueryResults();
        Short projectCode = queryCriteriaDO.getProjectcode();
        if (projectCode != null) {
            String projectId = String.valueOf(projectCode);
            List<Module> moduleList = this.getModulesByProjectId(projectId);
            queryResults.setAllModuleList(moduleList);
        } else {
            queryResults.setAllModuleList(ConfigData.MODULE_CONFIG);
        }

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
     * 根据发布测试申请的主键获取发布申请的对象
     * @param deployRequestId
     * @return
     * @throws Exception
     */
    public DeployRequest getDeployRequestById(String deployRequestId) throws Exception {
        DeployRequesterPO deployRequestPO = deployRequesterDAO.selectByPrimaryKey(deployRequestId);
        if (deployRequestPO == null) {
            return null;
        }
        DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
        BeanUtils.copyProperties(deployRequestPO, deployRequesterDO, true);
        DeployRequest deployRequest = new DeployRequest();
        BeanUtils.copyProperties(deployRequesterDO, deployRequest, false);
        fillDeployRequest(deployRequest);
        return deployRequest;
    }

    /**
     * 标识申请记录已经发布到生产环境
     * @param queryCriteriaDO
     * @return
     * @throws Exception
     */
    public int markDeployedToProd(QueryCriteriaDO queryCriteriaDO) throws Exception {
        QueryCriteriaPO queryCriteriaPO = new QueryCriteriaPO();
        BeanUtils.copyProperties(queryCriteriaDO, queryCriteriaPO, true);
        int updatedRecCount = deployRequesterDAO.markDeployedToProd(queryCriteriaPO);
        return updatedRecCount;
    }

    /**
     * 生成生产发布申请
     * @param queryCriteriaDO
     * @return
     * @throws Exception
     */
    public String produceApplicationStr(ProduceApplicationQueryCriteriaDO produceApplicationQueryCriteriaDO) throws Exception {
        ProduceApplicationQueryCriteriaPO produceApplicationQueryCriteriaPO = new ProduceApplicationQueryCriteriaPO();
        BeanUtils.copyProperties(produceApplicationQueryCriteriaDO, produceApplicationQueryCriteriaPO, true);
        List<DeployRequesterPO> deployRequesterPOList = deployRequesterDAO.selectByProduceApplicationQueryCriteriaPO(produceApplicationQueryCriteriaPO);
        List<DeployRequesterDO> deployRequesterDOList = null;
        if (deployRequesterPOList != null) {
            deployRequesterDOList = new ArrayList<DeployRequesterDO>();
            for (DeployRequesterPO deployRequestPO : deployRequesterPOList) {
                DeployRequesterDO deployRequesterDO = new DeployRequesterDO();
                BeanUtils.copyProperties(deployRequestPO, deployRequesterDO, true);
                deployRequesterDOList.add(deployRequesterDO);
            }

            return produceAppStrFromList(deployRequesterDOList, produceApplicationQueryCriteriaDO.getDeployToWhere());
        }
        return null;
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
        if (deployRequest.getDeploytimefortestenv() != null) {
            deployRequest.setFormatedDeploytimefortestenv(formatter.format(deployRequest.getDeploytimefortestenv()));
        }
        /********************补全deployRequest中的属性值( end )*******************************/

    }

    /**
     * 从给定的List集合中生成生产发布申请内容
     * @param deployRequesterDOList
     * @param deployToWhere
     * @return
     */
    private String produceAppStrFromList(List<DeployRequesterDO> deployRequesterDOList, String deployToWhere) {
        if (deployRequesterDOList == null) {
            return "";
        }

        StringBuffer sbApplicationStr = new StringBuffer();
        StringBuffer sbApplicationDetailStr = new StringBuffer();
        Set<String> signedDescriptionSet = new HashSet<String>();
        /**
         * 开设一个最多能存放30个模块标记的记录的数组，每个模块记录有4个标记
         * arrProjModules[i][0]：存放项目id。事实上整个数组中只存放一种项目id
         * arrProjModules[i][1]：存放模块id
         * arrProjModules[i][2]：存放rest的标识（0：无rest;大于0：有rest）
         * arrProjModules[i][3]：存放provider的标识（0：无provider;大于0：有provider）
         */
        int[][] arrProjModules = new int[30][4];

        int idx = 0;
        for (DeployRequesterDO deployRequesterDO : deployRequesterDOList) {
            int projectId = deployRequesterDO.getProjectcode().intValue();
            int moduleId = deployRequesterDO.getModulecode().intValue();
            int moduleTypeId = deployRequesterDO.getModuletypecode().intValue();
            changeArray(arrProjModules, projectId, moduleId, moduleTypeId);

            String moduleCodeAndName = "";
            Module module = getModuleById(Short.valueOf(String.valueOf(moduleId)));
            if (module != null) {
                moduleCodeAndName = module.getCode() + "(" + module.getName() + ")";
            }
            String menuName = deployRequesterDO.getMenuname();
            String description = deployRequesterDO.getDescription();

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(description);
            String strNoBlank = m.replaceAll("");
            strNoBlank = strNoBlank.toLowerCase();
            String signedDescription = MD5Util.sign(strNoBlank, "utf-8");

            if (!signedDescriptionSet.contains(signedDescription)) {
                signedDescriptionSet.add(signedDescription);
                sbApplicationDetailStr.append(++idx).append("、");
                sbApplicationDetailStr.append("模块：" + moduleCodeAndName).append("\t");
                sbApplicationDetailStr.append("菜单：" + menuName).append("\t");
                sbApplicationDetailStr.append("改动内容：" + description).append("\n");
            }
        }

        boolean writeTitle = true;
        for (int i = 0; i < arrProjModules.length; i++) {
            int projectId = arrProjModules[i][0];
            int moduleId = arrProjModules[i][1];
            if (projectId == 0) {
                break;
            }
            Project project = getProjectById(projectId);
            String projectName = project == null ? "" : project.getProjectName();
            Module module = getModuleById(Short.valueOf(String.valueOf(moduleId)));
            String moduleCode = module == null ? "" : module.getCode();
            boolean hasRest = arrProjModules[i][2] > 0 ? true : false;
            boolean hasProvider = arrProjModules[i][3] > 0 ? true : false;

            DeployURL deployURL = ConfigData.MODULE_DEPLOY_URL_CONFIG.get(projectId + "_" + moduleCode + "_" + "rest");
            String moduleName = deployURL != null ? deployURL.getModuleName() : "";

            if (writeTitle) {
                if ("product".equals(deployToWhere)) {
                    sbApplicationStr.append(projectName + "的生产发布申请:\n");
                } else if ("preproduct".equals(deployToWhere)) {
                    sbApplicationStr.append(projectName + "的预发布申请:\n");
                }
                writeTitle = false;
            }

            sbApplicationStr.append(moduleName);
            if (hasRest && hasProvider) {
                sbApplicationStr.append("(provider && rest)");
            }
            if (hasRest && !hasProvider) {
                sbApplicationStr.append("(rest)");
            }
            if (!hasRest && hasProvider) {
                sbApplicationStr.append("(provider)");
            }
            sbApplicationStr.append("\n");
        }
        return sbApplicationStr.toString() + "\n" + sbApplicationDetailStr.toString();
    }

    private void changeArray(int[][] arrProjectModules, int projectId, int moduleId, int moduleTypeId) {
        int i = 0;
        boolean found = false;
        for (; i < arrProjectModules.length; i++) {
            if (arrProjectModules[i][0] == 0) {
                break;
            }
            if (arrProjectModules[i][0] == projectId && arrProjectModules[i][1] == moduleId) {
                if (moduleTypeId == 1) {
                    arrProjectModules[i][2]++;
                }
                if (moduleTypeId == 2) {
                    arrProjectModules[i][3]++;
                }
                found = true;
                break;
            }
        }

        if (!found) {
            arrProjectModules[i][0] = projectId;
            arrProjectModules[i][1] = moduleId;
            if (moduleTypeId == 1) {
                arrProjectModules[i][2]++;
            }
            if (moduleTypeId == 2) {
                arrProjectModules[i][3]++;
            }
        }
    }

    private static void change(int[] p) {
        for (int i = 0; i < p.length; i++) {
            p[i] = i;
        }
    }
}
