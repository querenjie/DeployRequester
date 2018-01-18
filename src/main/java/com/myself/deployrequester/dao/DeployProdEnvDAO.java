package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployProdEnvPO;

public interface DeployProdEnvDAO {
    int deleteByPrimaryKey(String deployprodenvid);

    int insert(DeployProdEnvPO record);

    int insertSelective(DeployProdEnvPO record);

    DeployProdEnvPO selectByPrimaryKey(String deployprodenvid);

    int updateByPrimaryKeySelective(DeployProdEnvPO record);

    int updateByPrimaryKey(DeployProdEnvPO record);
}