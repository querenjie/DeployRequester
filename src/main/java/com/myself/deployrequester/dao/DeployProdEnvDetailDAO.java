package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployProdEnvDetailPO;

public interface DeployProdEnvDetailDAO {
    int deleteByPrimaryKey(String deployprodenvdetailid);

    int insert(DeployProdEnvDetailPO record);

    int insertSelective(DeployProdEnvDetailPO record);

    DeployProdEnvDetailPO selectByPrimaryKey(String deployprodenvdetailid);

    int updateByPrimaryKeySelective(DeployProdEnvDetailPO record);

    int updateByPrimaryKey(DeployProdEnvDetailPO record);
}