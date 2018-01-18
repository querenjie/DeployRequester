package com.myself.deployrequester.biz.design.dynamiccomponent.builder;

import com.myself.deployrequester.biz.design.dynamiccomponent.builder.js.JsInvoker;
import com.myself.deployrequester.biz.design.dynamiccomponent.builder.js.SimpleJsInvoker;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ComponentBuilder;
import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;
import com.myself.deployrequester.util.LoggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 实例化使用js编写的组件
 */
public class JsComponentBuilder implements ComponentBuilder {
	/** 加载脚本文件时使用的编码 */
	private String encoding = "GBK";
	/** 日志 */
	public static final Log LOGGER = LogFactory.getLog(SimpleJsInvoker.class);

	public <T> T build(Class<T> clazz, ResourceFile source) {
		try {
			T t = clazz.newInstance();
			if (!(t instanceof JsInvoker)) {
				LoggerUtil.warn(LOGGER, "非js组件," + clazz.getName());
			}
			JsInvoker jsInvoker = (JsInvoker) t;
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine engine = engineManager.getEngineByName("JavaScript");
			engine.eval(new String(source.getContent(), encoding));
			jsInvoker.setInvoker((Invocable) engine);
			jsInvoker.setName(source.getName());
			return (T) jsInvoker;
		} catch (Exception e) {
			LoggerUtil.error(LOGGER, "脚本加载异常," + source.getContent(), e);
			throw new IllegalStateException("js load fail " + clazz.getName(),
					e);
		}
	}

	/**
	 * Setter method for property <tt>encoding</tt>.
	 * 
	 * @param encoding
	 *            value to be assigned to property encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
