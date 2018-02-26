package com.myself.deployrequester.dao;

import com.myself.deployrequester.bo.DeployDbscript;
import com.myself.deployrequester.po.DeployDbscriptPO;
import com.myself.deployrequester.po.QueryDbscriptPO;

import java.util.List;

public interface DeployDbscriptDAO {
    int deleteByPrimaryKey(String deploydbscriptid);

    int insert(DeployDbscriptPO deployDbscriptPO);

    int insertSelective(DeployDbscriptPO deployDbscriptPO);

    DeployDbscriptPO selectByPrimaryKey(String deploydbscriptid);

    int updateByPrimaryKeySelective(DeployDbscriptPO deployDbscriptPO);

    int updateByPrimaryKey(DeployDbscriptPO deployDbscriptPO);

    List<DeployDbscriptPO> selectByQueryDbscriptPO(QueryDbscriptPO queryDbscriptPO);

    /**
     * 仅仅查询出需要发布的脚本
     * @param queryDbscriptPO
     * @return
     */
    List<DeployDbscriptPO> selectOnlyNeedDeployByQueryDbscriptPO(QueryDbscriptPO queryDbscriptPO);
}