package com.myself.deployrequester.dao;

import com.myself.deployrequester.po.PrjmodDblinkRelPO;

public interface PrjmodDblinkRelPOMapper {
    int deleteByPrimaryKey(String prjmoddblinkrelid);

    int insert(PrjmodDblinkRelPO record);

    int insertSelective(PrjmodDblinkRelPO record);

    PrjmodDblinkRelPO selectByPrimaryKey(String prjmoddblinkrelid);

    int updateByPrimaryKeySelective(PrjmodDblinkRelPO record);

    int updateByPrimaryKey(PrjmodDblinkRelPO record);
}