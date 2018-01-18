package com.myself.deployrequester.biz.config.spi;

/**
 * 配置接口
 * Created by QueRenJie on ${date}
 */
public interface Config {
    public static int USE_DEPLOY_URL = 1;       //使用发布的url
    public static int VIEW_DEPLOY_URL = 2;      //查看发布的url
    public static int MARK_PRODUCT_DEPLOY = 3;  //标识发布过生产

    /**
     * 构建配置
     */
    public void build();

}
