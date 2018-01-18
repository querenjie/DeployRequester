package com.myself.deployrequester.biz.design.dynamiccomponent.util;

import com.myself.deployrequester.biz.design.dynamiccomponent.ComponentManager;
import com.myself.deployrequester.biz.design.dynamiccomponent.spi.ComponentContainer;

/**
 * 用于方便获取组件容器的工具类
 */
public class ComponentUtils {
	/** 组件管理器实例， */
	private static ComponentManager instance = null;

	/**
	 * 根据类型获得组件容器（直接根据输入class转好类型）
	 * 
	 * @param type
	 *            类型
	 * @param containerType
	 *            组件容器class类型
	 * @return 组件容器
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ComponentContainer<?>> T getContainer(String type,
																   Class<T> containerType) {
		if (instance == null) {
			return null;
		}
		return (T) instance.getContainer(type);
	}

	/**
	 * 根据类型获得组件容器
	 * 
	 * @param type
	 *            类型
	 * @return 组件容器
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ComponentContainer<?>> T getContainer(String type) {
		if (instance == null) {
			return null;
		}
		return (T) instance.getContainer(type);
	}

	/**
	 * Setter method for property <tt>instance</tt>.
	 * 
	 * @param instance
	 *            value to be assigned to property instance
	 */
	public static void setInstance(ComponentManager instance) {
		ComponentUtils.instance = instance;
	}

}
