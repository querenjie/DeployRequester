package com.myself.deployrequester.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的查询结果
 * Created by QueRenJie on ${date}
 */
public class QueryResults {
    /**
     *  所有的模块列表
     */
    private List<Module> allModuleList;
    /**
     * 每个模块对应的统计信息。其中的key是moduleId
     */
    private Map<Short, QueryModuleStatistics> queryModuleStatisticsMap = new HashMap<Short, QueryModuleStatistics>();

    public List<Module> getAllModuleList() {
        return allModuleList;
    }

    public void setAllModuleList(List<Module> allModuleList) {
        this.allModuleList = allModuleList;
    }

    public Map<Short, QueryModuleStatistics> getQueryModuleStatisticsMap() {
        return queryModuleStatisticsMap;
    }

}
