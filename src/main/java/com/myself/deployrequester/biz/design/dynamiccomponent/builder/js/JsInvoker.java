package com.myself.deployrequester.biz.design.dynamiccomponent.builder.js;

import javax.script.Invocable;

/**
 * js组件接口
 */
public interface JsInvoker {

	/**
	 * 设置组件名称
	 * 
	 * @param name
	 *            组件名称
	 */
	public void setName(String name);

	/**
	 * 设置调用器
	 * 
	 * @param invoker
	 *            调用器
	 */
	public void setInvoker(Invocable invoker);

}
