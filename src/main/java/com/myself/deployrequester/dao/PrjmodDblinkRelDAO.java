package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.DeployDbserversPO;
import com.myself.deployrequester.po.PrjmodDblinkRelPO;

import java.util.List;

public interface PrjmodDblinkRelDAO {
    int deleteByPrimaryKey(String prjmoddblinkrelid);

    int insert(PrjmodDblinkRelPO record);

    int insertSelective(PrjmodDblinkRelPO record);

    PrjmodDblinkRelPO selectByPrimaryKey(String prjmoddblinkrelid);

    int updateByPrimaryKeySelective(PrjmodDblinkRelPO record);

    int updateByPrimaryKey(PrjmodDblinkRelPO record);

    List<PrjmodDblinkRelPO> selectByPrjmodDblinkRelPO(PrjmodDblinkRelPO prjmodDblinkRelPO);

    /**
     * 根据projectid,moduleid修改记录
     * @param prjmodDblinkRelPO
     * @return
     */
    int update1(PrjmodDblinkRelPO prjmodDblinkRelPO);
}