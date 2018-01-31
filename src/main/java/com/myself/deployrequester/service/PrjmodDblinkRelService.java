package com.myself.deployrequester.service;

import com.myself.deployrequester.dao.PrjmodDblinkRelDAO;
import com.myself.deployrequester.model.PrjmodDblinkRelDO;
import com.myself.deployrequester.po.PrjmodDblinkRelPO;
import com.myself.deployrequester.util.reflect.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Service
public class PrjmodDblinkRelService {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(PrjmodDblinkRelService.class);

    @Autowired
    private PrjmodDblinkRelDAO prjmodDblinkRelDAO;

    public int savePrjmodDblinkRel(PrjmodDblinkRelDO prjmodDblinkRelDO) throws Exception {
        PrjmodDblinkRelPO prjmodDblinkRelPO = new PrjmodDblinkRelPO();
        BeanUtils.copyProperties(prjmodDblinkRelDO, prjmodDblinkRelPO, false);
        int updateSuccessRecordCount = prjmodDblinkRelDAO.update1(prjmodDblinkRelPO);
        if (updateSuccessRecordCount == 1) {
            return 1;
        }
        int insertSuccessRecordCount = prjmodDblinkRelDAO.insert(prjmodDblinkRelPO);
        if (insertSuccessRecordCount == 1) {
            prjmodDblinkRelDO.setPrjmoddblinkrelid(prjmodDblinkRelPO.getPrjmoddblinkrelid());
        }
        return insertSuccessRecordCount;
    }

    public List<PrjmodDblinkRelDO> selectByPrjmodDblinkRelDO(PrjmodDblinkRelDO prjmodDblinkRelDO) throws Exception {
        PrjmodDblinkRelPO prjmodDblinkRelPO = new PrjmodDblinkRelPO();
        BeanUtils.copyProperties(prjmodDblinkRelDO, prjmodDblinkRelPO, true);
        List<PrjmodDblinkRelPO> prjmodDblinkRelPOList = prjmodDblinkRelDAO.selectByPrjmodDblinkRelPO(prjmodDblinkRelPO);
        if (prjmodDblinkRelPOList == null) {
            return null;
        }

        List<PrjmodDblinkRelDO> prjmodDblinkRelDOList = new ArrayList<PrjmodDblinkRelDO>();
        for (PrjmodDblinkRelPO prjmodDblinkRelPO1 : prjmodDblinkRelPOList) {
            PrjmodDblinkRelDO prjmodDblinkRelDO1 = new PrjmodDblinkRelDO();
            BeanUtils.copyProperties(prjmodDblinkRelPO1, prjmodDblinkRelDO1, false);
            prjmodDblinkRelDOList.add(prjmodDblinkRelDO1);
        }
        return prjmodDblinkRelDOList;
    }
}
