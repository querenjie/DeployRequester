package com.myself.deployrequester.biz.design.dynamiccomponent.util;

import com.myself.deployrequester.util.LoggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * 使用spring自动装载机制，帮助bean注入依赖，如果实现了InitializingBean接口，会调用afterPropertiesSet()方法
 */
public class AutowireUtils {
	public static final Log LOGGER = LogFactory.getLog(AutowireUtils.class);

	/**
	 * 注入依赖
	 * 
	 * @param bean
	 *            组件
	 * @param context
	 *            spring上下文
	 */
	public static void autowireBean(Object bean, ApplicationContext context) {
		if (context == null) {
			return;
		}
		AutowireCapableBeanFactory acbf = findAutoWiringBeanFactory(context);
		if (acbf != null) {
			acbf.autowireBeanProperties(bean,
					AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
		}
		if (bean instanceof ApplicationContextAware) {
			((ApplicationContextAware) bean).setApplicationContext(context);
		}
		if (bean instanceof InitializingBean) {
			InitializingBean lifecycle = (InitializingBean) bean;
			try {
				lifecycle.afterPropertiesSet();
			} catch (Exception e) {
				LoggerUtil.error(LOGGER, bean.getClass() + " init fail!", e);
			}

		}
	}

	/**
	 * 获得AutowireCapableBeanFactory
	 * 
	 * @param context
	 *            spring上下文
	 * @return AutowireCapableBeanFactory
	 */
	private static AutowireCapableBeanFactory findAutoWiringBeanFactory(
			ApplicationContext context) {
		if (context instanceof AutowireCapableBeanFactory) {
			return (AutowireCapableBeanFactory) context;
		} else if (context instanceof ConfigurableApplicationContext) {
			return ((ConfigurableApplicationContext) context).getBeanFactory();
		} else if (context.getParent() != null) {
			return findAutoWiringBeanFactory(context.getParent());
		}
		return null;
	}
}
