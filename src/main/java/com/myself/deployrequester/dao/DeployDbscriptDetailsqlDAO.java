package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployDbscriptDetailsqlPO;

public interface DeployDbscriptDetailsqlDAO {
    int deleteByPrimaryKey(String deploydbscriptdetailsqlid);

    int insert(DeployDbscriptDetailsqlPO record);

    int insertSelective(DeployDbscriptDetailsqlPO record);

    DeployDbscriptDetailsqlPO selectByPrimaryKey(String deploydbscriptdetailsqlid);

    int updateByPrimaryKeySelective(DeployDbscriptDetailsqlPO record);

    int updateByPrimaryKey(DeployDbscriptDetailsqlPO record);

    int deleteByDeployDbscriptId(String deployDbscriptId);
}