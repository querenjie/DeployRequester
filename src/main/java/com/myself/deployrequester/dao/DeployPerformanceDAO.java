package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployPerformancePO;

import java.util.List;
import java.util.Map;

public interface DeployPerformanceDAO {
    int deleteByPrimaryKey(String deployperformanceid);

    int insert(DeployPerformancePO deployPerformancePO);

    int insertSelective(DeployPerformancePO deployPerformancePO);

    DeployPerformancePO selectByPrimaryKey(String deployperformanceid);

    int updateByPrimaryKeySelective(DeployPerformancePO deployPerformancePO);

    int updateByPrimaryKey(DeployPerformancePO deployPerformancePO);

    List<DeployPerformancePO> selectByModulecodeAndModuletypecodeAndProjectcode(DeployPerformancePO deployPerformancePO);

    int updateByModulecodeAndModuletypecodeAndProjectcode(DeployPerformancePO deployPerformancePO);
}