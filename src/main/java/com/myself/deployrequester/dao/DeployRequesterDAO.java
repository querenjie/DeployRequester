package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployRequesterPO;
import com.myself.deployrequester.po.ProduceApplicationQueryCriteriaPO;
import com.myself.deployrequester.po.QueryCriteriaPO;
import com.myself.deployrequester.po.QueryDeployAuditPO;

import java.util.List;


public interface DeployRequesterDAO {
    int deleteByPrimaryKey(String deployrequestid);

    int insert(DeployRequesterPO record);

    int insertSelective(DeployRequesterPO record);

    DeployRequesterPO selectByPrimaryKey(String deployrequestid);

    int updateByPrimaryKeySelective(DeployRequesterPO deployRequesterPO);

    int updateByPrimaryKey(DeployRequesterPO record);

    List<DeployRequesterPO> selectByQueryCriteriaPO(QueryCriteriaPO queryCriteriaPO);

    int updateIsTestOk(DeployRequesterPO deployRequesterPO);

    /**
     * 标志申请记录已上生产
     *
     * @param queryCriteriaPO
     * @return
     */
    int markDeployedToProd(QueryCriteriaPO queryCriteriaPO);

    List<DeployRequesterPO> selectByProduceApplicationQueryCriteriaPO(ProduceApplicationQueryCriteriaPO produceApplicationQueryCriteriaPO);

    /**
     * 获取尚未开启执行的应用发布申请
     * @param queryDeployAuditPO
     * @return
     */
    List<DeployRequesterPO> selectUnexecutedRequestByQueryDeployAuditPO(QueryDeployAuditPO queryDeployAuditPO);
}