package com.myself.deployrequester.biz.design.dynamiccomponent.core;

/**
 * 监测组件定义变化，并更新到组件容器
 */
public interface ComponentScanner {
	/**
	 * 
	 * 启动组件监听
	 * 
	 * @param extensions
	 *            监听的扩展名
	 * @param callback
	 *            见到到变化后的回调方法
	 */
	public void start(String[] extensions, ReloadCallback callback);
}
