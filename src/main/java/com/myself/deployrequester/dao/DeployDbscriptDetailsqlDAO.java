package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployDbscriptDetailsqlPO;

import java.util.List;

public interface DeployDbscriptDetailsqlDAO {
    int deleteByPrimaryKey(String deploydbscriptdetailsqlid);

    int insert(DeployDbscriptDetailsqlPO record);

    int insertSelective(DeployDbscriptDetailsqlPO record);

    DeployDbscriptDetailsqlPO selectByPrimaryKey(String deploydbscriptdetailsqlid);

    int updateByPrimaryKeySelective(DeployDbscriptDetailsqlPO record);

    int updateByPrimaryKey(DeployDbscriptDetailsqlPO record);

    int deleteByDeployDbscriptId(String deployDbscriptId);

    /**
     * 获取指定的脚本申请记录中已经执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    List<DeployDbscriptDetailsqlPO> selectExecutedByDeployDbscriptId(String deployDbscriptId);

    /**
     * 获取指定的脚本申请记录中尚未执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    List<DeployDbscriptDetailsqlPO> selectUnexecutedByDeployDbscriptId(String deployDbscriptId);

    /**
     * 删除指定的脚本申请记录中尚未执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    int deleteUnexecutedByDeployDbscriptId(String deployDbscriptId);

    /**
     * 获取指定的申请记录中的子sql记录的最大的序号
     * @param deployDbscriptId
     * @return
     */
    Short selectMaxSeqno(String deployDbscriptId);
}