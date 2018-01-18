package com.myself.deployrequester.biz.design.dynamiccomponent.builder.js;

import com.myself.deployrequester.util.LoggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.script.Invocable;
import javax.script.ScriptException;

/**
 * 默认js加载实现
 */
public class SimpleJsInvoker implements JsInvoker {

	/** 日志 */
	public static final Log LOGGER = LogFactory.getLog(SimpleJsInvoker.class);
	/** 文件名 */
	private String name;
	/** 脚本Invocable */
	private Invocable invoker;

	/**
	 * 调用js中的某个方法
	 * 
	 * @param func
	 *            方法名
	 * @param args
	 *            参数
	 * @return
	 */
	public String invokeFunction(String func, Object... args) {
		try {
			Object result = invoker.invokeFunction(func, args);
			return result.toString();
		} catch (ScriptException e) {
			LoggerUtil.error(LOGGER, "脚本加载异常," + func, e);
		} catch (NoSuchMethodException e) {
			LoggerUtil.error(LOGGER, "未找到方法," + func, e);
		}
		return null;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name
	 *            value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>invoker</tt>.
	 * 
	 * @param invoker
	 *            value to be assigned to property invoker
	 */
	public void setInvoker(Invocable invoker) {
		this.invoker = invoker;
	}

}
