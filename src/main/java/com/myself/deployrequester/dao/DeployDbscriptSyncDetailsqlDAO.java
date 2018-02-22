package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployDbscriptSyncDetailsqlPO;

import java.util.List;

public interface DeployDbscriptSyncDetailsqlDAO {
    int deleteByPrimaryKey(String deploydbscriptsyncdetailsqlid);

    int insert(DeployDbscriptSyncDetailsqlPO record);

    int insertSelective(DeployDbscriptSyncDetailsqlPO record);

    DeployDbscriptSyncDetailsqlPO selectByPrimaryKey(String deploydbscriptsyncdetailsqlid);

    int updateByPrimaryKeySelective(DeployDbscriptSyncDetailsqlPO record);

    int updateByPrimaryKey(DeployDbscriptSyncDetailsqlPO record);

    int deleteByDeployDbscriptId(String deployDbscriptId);

    /**
     * 获取指定的脚本申请记录中已经执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    List<DeployDbscriptSyncDetailsqlPO> selectExecutedByDeployDbscriptId(String deployDbscriptId);

    /**
     * 获取指定的脚本申请记录中尚未执行过的sql信息记录
     * @param deployDbscriptId
     * @return
     */
    List<DeployDbscriptSyncDetailsqlPO> selectUnexecutedByDeployDbscriptId(String deployDbscriptId);

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

    /**
     * 获取某个指定的脚本申请记录的同步子脚本数量，以此判断是否这个脚本申请具有同步脚本。
     * @param deployDbscriptId
     * @return
     */
    int selectCountByDeployDbscriptId(String deployDbscriptId);
}