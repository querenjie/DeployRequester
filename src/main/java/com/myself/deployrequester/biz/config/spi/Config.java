package com.myself.deployrequester.biz.config.spi;

/**
 * 配置接口
 * Created by QueRenJie on ${date}
 */
public interface Config {
    public static int USE_DEPLOY_URL = 1;       //使用发布的url
    public static int VIEW_DEPLOY_URL = 2;      //查看发布的url
    public static int MARK_PRODUCT_DEPLOY = 3;  //标识发布过生产
    public static int LOCK_DEPLOY_REQUEST = 4;  //锁定发布申请
    public static int DEPLOY_DBSCRIPT = 5;      //发布数据库脚本

    /**
     * 构建配置
     */
    public void build();

}
