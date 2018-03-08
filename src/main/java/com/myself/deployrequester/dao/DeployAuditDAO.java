package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployAuditPO;

import java.util.List;

public interface DeployAuditDAO {
    int deleteByPrimaryKey(String deployauditid);

    int insert(DeployAuditPO record);

    int insertSelective(DeployAuditPO record);

    DeployAuditPO selectByPrimaryKey(String deployauditid);

    int updateByPrimaryKeySelective(DeployAuditPO record);

    int updateByPrimaryKey(DeployAuditPO record);

    List<DeployAuditPO> selectByDeployRequestId(String deployrequestid);
}