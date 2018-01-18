package com.myself.deployrequester.biz.design.dynamiccomponent.core;

import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;

/**
 * 实例化并初始化组件
 */
public interface ComponentBuilder {
	/**
	 * 根据spi的接口名和源码创建目标实例
	 * 
	 * @param clazz
	 *            目标类型，可以是某个接口
	 * @param source
	 *            目标源码
	 * @return 组件实例
	 */
	public <T> T build(Class<T> clazz, ResourceFile source);
}
