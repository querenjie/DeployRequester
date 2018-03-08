package com.myself.deployrequester.globalvar;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployStatus {
    /**
     * 存放正在发布中的申请，元素的格式为‘项目id_模块id’。
     * 目的是在发布前可以检测到这个模块是否正在发布，并根据情况作相应的处理。
     */
    public static Set<String> deployingSet = new HashSet<String>();
}
