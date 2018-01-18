package com.myself.deployrequester.biz.config.group;

import com.myself.deployrequester.biz.config.spi.Config;
import com.myself.deployrequester.biz.design.dynamiccomponent.spi.ComponentContainer;

import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
public class ConfigGroup implements ComponentContainer<Config> {
    /**
     * 重新加载组件
     *
     * @param components
     */
    public void onReload(List<Config> components) {
        for (Config config : components) {
            config.build();
        }
    }

    /**
     * 组件的接口类型
     *
     * @return
     */
    public Class<Config> componentType() {
        return Config.class;
    }
}
