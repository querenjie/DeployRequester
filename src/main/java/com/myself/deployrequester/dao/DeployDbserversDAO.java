package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployDbserversPO;

import java.util.List;

public interface DeployDbserversDAO {
    int deleteByPrimaryKey(String deploydbserversid);

    int insert(DeployDbserversPO deployDbserversPO);

    int insertSelective(DeployDbserversPO deployDbserversPO);

    DeployDbserversPO selectByPrimaryKey(String deploydbserversid);

    int updateByPrimaryKeySelective(DeployDbserversPO deployDbserversPO);

    int updateByPrimaryKey(DeployDbserversPO deployDbserversPO);

    /**
     * 根据belong,ip,port,username修改记录
     * @param deployDbserversPO
     * @return
     */
    int update1(DeployDbserversPO deployDbserversPO);

    List<DeployDbserversPO> selectByDeployDbserversPO(DeployDbserversPO deployDbserversPO);
}