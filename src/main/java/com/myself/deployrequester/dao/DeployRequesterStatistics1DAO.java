package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployRequesterStatistics1PO;
import com.myself.deployrequester.po.QueryCriteriaStatistics1PO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeployRequesterStatistics1DAO {
    List<DeployRequesterStatistics1PO> select1ByQueryCriteriaStatistics1PO(QueryCriteriaStatistics1PO queryCriteriaStatistics1PO);

    List<DeployRequesterStatistics1PO> select2ByQueryCriteriaStatistics1PO(QueryCriteriaStatistics1PO queryCriteriaStatistics1PO);

    List<DeployRequesterStatistics1PO> select3ByQueryCriteriaStatistics1PO(QueryCriteriaStatistics1PO queryCriteriaStatistics1PO);
}
