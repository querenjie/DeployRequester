package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployTestEnvPO;

public interface DeployTestEnvDAO {
    int deleteByPrimaryKey(String deploytestenvid);

    int insert(DeployTestEnvPO record);

    int insertSelective(DeployTestEnvPO record);

    DeployTestEnvPO selectByPrimaryKey(String deploytestenvid);

    int updateByPrimaryKeySelective(DeployTestEnvPO record);

    int updateByPrimaryKey(DeployTestEnvPO record);
}