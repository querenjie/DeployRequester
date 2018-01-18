package com.myself.deployrequester.biz.design.dynamiccomponent;

import com.myself.deployrequester.biz.config.log.Loggers;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ComponentBuilder;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ComponentScanner;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ReloadCallback;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.Switchable;
import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;
import com.myself.deployrequester.biz.design.dynamiccomponent.scanner.FolderScanner;
import com.myself.deployrequester.biz.design.dynamiccomponent.spi.ComponentContainer;
import com.myself.deployrequester.biz.design.dynamiccomponent.util.AutowireUtils;
import com.myself.deployrequester.biz.design.dynamiccomponent.util.ComponentUtils;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.LoggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组件管理器
 */
public class ComponentManager implements InitializingBean,
        ApplicationContextAware {
	/** 日志 */
	private static final Logger logger = LogManager.getLogger(ComponentManager.class);

	/** 组件类型到组件容器的映射 */
	private Map<String, ComponentContainer<?>> containers = new ConcurrentHashMap<String, ComponentContainer<?>>();
	/** spring ApplicationContext */
	private ApplicationContext applicationContext;

	/** 文件扩展名到组件实例化器的映射 */
	private Map<String, ComponentBuilder> builderMap;
	/** 组件监控器 */
	private ComponentScanner scanner;

	/**
	 * @see InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Loggers.MONITOR_LOGGER.info("启动配置文件加载...........");
		Log4jUtil.info(logger, "启动配置文件加载...........");

		// 一个scan对个多个builder加载方式，根据文件扩展名确定用那个builder
		if (builderMap != null) {
			List<String> list = new ArrayList<String>();
			for (Entry<String, ComponentBuilder> entry : builderMap.entrySet()) {
				list.add(entry.getKey());
			}
			String[] extensions = list.toArray(new String[list.size()]);
			scanner.start(extensions, new ScannerCallback());
		}
		// 设置到全局变量
		ComponentUtils.setInstance(this);
	}

	/**
	 * 搜索到文件时的回调
	 */
	private class ScannerCallback implements ReloadCallback {

		public void onReload(String type, List<ResourceFile> contents) {
			ComponentContainer<?> group = containers.get(type);
			if (group == null) {
				return;
			}
			Class<?> clazz = group.componentType();
			List newComponents = new ArrayList();
			// 只要有一个组件实例化失败了就所有组件不加载
			for (ResourceFile file : contents) {
				Object newComponent = null;
				String fileType = file.getExtension();
				ComponentBuilder builder = builderMap.get(fileType);
				if (builder == null) {
					Loggers.MONITOR_LOGGER.info("builder not found " + fileType	+ "," + type);
					Log4jUtil.warn(logger, "builder not found " + fileType	+ "," + type);
					continue;
				}
				newComponent = builder.build(clazz, file);
				if (newComponent == null) {
					continue;
				}
				// 实现了Switchable 并且设置enable为false的，不初始化
				if (newComponent instanceof Switchable
						&& !((Switchable) newComponent).enable()) {
					continue;
				}
				// 如果组件中配置了需要spring注入的属性，需要自动帮这个组件注入依赖
				AutowireUtils.autowireBean(newComponent, applicationContext);
				newComponents.add(newComponent);
			}
			group.onReload(newComponents);

			Loggers.MONITOR_LOGGER.info(group.getClass().getSimpleName() + "("
					+ newComponents.size() + ") reloaded! ");
			Log4jUtil.info(logger, group.getClass().getSimpleName() + "("
					+ newComponents.size() + ") reloaded! ");
		}
	}

	/**
	 * 根据spi获得组件组
	 *
	 * @param spiClazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ComponentContainer<?>> T getContainer(String type) {
		return (T) containers.get(type);
	}

	/**
	 * Setter method for property <tt>containers</tt>.
	 *
	 * @param containers
	 *            value to be assigned to property containers
	 */
	public void setContainers(Map<String, ComponentContainer<?>> containers) {
		this.containers = containers;
	}

	/**
	 * @see ApplicationContextAware#setApplicationContext(ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Setter method for property <tt>builderMap</tt>.
	 * 
	 * @param builderMap
	 *            value to be assigned to property builderMap
	 */
	public void setBuilderMap(Map<String, ComponentBuilder> builderMap) {
		this.builderMap = builderMap;
	}

	/**
	 * Setter method for property <tt>scanner</tt>.
	 * 
	 * @param scanner
	 *            value to be assigned to property scanner
	 */
	public void setScanner(ComponentScanner scanner) {
		this.scanner = scanner;
	}

}
