package com.myself.deployrequester.biz.design.dynamiccomponent.core;

import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;

import java.util.List;

/**
 * 组件配置变化时的回调接口
 */
public interface ReloadCallback {

	/**
	 * 当组件定义脚本变化时的回调方法
	 * 
	 * @param type
	 *            组件类型
	 * @param contents
	 *            组件定义文件
	 */
	public void onReload(String type, List<ResourceFile> contents);

}
