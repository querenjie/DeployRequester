package com.myself.deployrequester.biz.design.dynamiccomponent.core;

/**
 * 实现这个接口的组件，在实例化后会判断enable()，如果false这个组件就不加入组件容器，enable会在组件初始化之前执行
 */
public interface Switchable {
	/**
	 * 开关是否开启
	 * 
	 * @return 该组件是否有效
	 */
	public boolean enable();
}
