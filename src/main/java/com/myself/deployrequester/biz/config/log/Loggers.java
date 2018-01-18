package com.myself.deployrequester.biz.config.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Logger常量定义
 */
public abstract class Loggers {
	/** 发布测试环境日志 */
	public static final Logger DEPLOY_TEST_LOGGER = LogManager.getLogger("deploy_test_logger");

	/** 发布生产环境日志 */
	public static final Logger DEPLOY_PRODUCT_LOGGER = LogManager.getLogger("deploy_product_logger");

	/**
	 * 监控配置文件的进程日志
	 */
	public static final Logger MONITOR_LOGGER = LogManager.getLogger("monitor_logger");
}
