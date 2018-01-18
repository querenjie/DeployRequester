package com.myself.deployrequester.biz.design.dynamiccomponent.scanner;

import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;
import com.myself.deployrequester.util.Log4jUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 方便开发环境没有shareddata目录时可以加载程序目录中的脚本
 */
public class EnvAwareScanner extends FolderScanner implements InitializingBean {
	/** 日志 */
	public static final Logger LOGGER = Logger.getLogger(EnvAwareScanner.class);
	/** 预发布脚本的后缀名 */
	private static final String PRE_ENV_EXTENSION = "pre";
	/** 当前环境如果为eclipse时使用的脚本路径 */
	private String eclipsePath = "dynamicscript";
	/** 是否在预发布环境中 */
	private boolean isInPreEnv = checkInPreEnv();
	/** 是否在eclipse环境中 */
	private boolean isInEclipseEnv = checkInEclipseEnv();

	/**
	 * @param extensions 原始配置的后缀
	 * @return
	 */
	protected String[] prepareExtensions(String[] extensions) {
		if (isInPreEnv) {
			// 添加监控预发布后缀
			List<String> ext = new ArrayList<String>();
			Collections.addAll(ext, extensions);
			ext.add(PRE_ENV_EXTENSION);
			return ext.toArray(new String[ext.size()]);
		} else {
			return extensions;
		}
	}

	/**
	 * @param list 资源文件
	 * @return
	 */
	@Override
	protected List<ResourceFile> prepareResourceFiles(List<ResourceFile> list) {
		if (isInPreEnv) {
			// 预发布脚本
			List<ResourceFile> preFiles = new ArrayList<ResourceFile>();
			// 其他脚本
			List<ResourceFile> otherFiles = new ArrayList<ResourceFile>();
			// 分类
			for (ResourceFile file : list) {
				if (PRE_ENV_EXTENSION.equals(file.getExtension())) {
					preFiles.add(file);
				} else {
					otherFiles.add(file);
				}
			}
			// 把预发布脚本合并到正式脚本
			for (ResourceFile preFile : preFiles) {
				boolean existed = false;
				for (ResourceFile file : otherFiles) {
					// 存在这个文件，就用预发布文件的内容替换到正式文件
					if (preFile.getName().equals(
							file.getName() + "." + file.getExtension())) {
						file.setContent(preFile.getContent());
						existed = true;
						continue;
					}
				}
				// 如果不存在就新加这个文件
				if (!existed) {
					String name = preFile.getName();
					preFile.setName(StringUtils.substringBeforeLast(name, "."));
					preFile.setExtension(StringUtils.substringAfterLast(name,
							"."));
					otherFiles.add(preFile);
				}
			}
			return otherFiles;
		} else {
			return list;
		}
	}

	/**
	 * @see InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		this.setFolderPath(checkFolderPath(this.getFolderPath()));
	}

	/**
	 * 返回实际的监听目录
	 * 
	 * @param folderPath
	 *            原始配置的目录
	 * @return 实际应用的目录
	 */
	protected String checkFolderPath(String folderPath) {
		Log4jUtil.info(LOGGER, "folderPath=" + folderPath);
		// 生产环境，使用传入的配置
		if (isInEclipseEnv) {
			return EnvAwareScanner.class.getClassLoader().getResource(
					eclipsePath).getFile();
		} else {
			return folderPath;
		}
	}

	/**
	 * 判断当前环境是否为eclipse启动
	 * 
	 * @return 是否为eclipse启动
	 */
	protected boolean checkInEclipseEnv() {
		return new File(".project").exists();
	}

	/**
	 * 判断当前环境是否是预发布环境
	 * 
	 * @return 是否是预发布环境
	 */
	protected boolean checkInPreEnv() {
		String hostName = System.getenv("HOSTNAME");
		if (StringUtils.isBlank(hostName)) {
			return false;
		}
		return StringUtils.endsWith(hostName, "-99-1");
	}

	/**
	 * Setter method for property <tt>eclipsePath</tt>.
	 * 
	 * @param eclipsePath
	 *            value to be assigned to property eclipsePath
	 */
	public void setEclipsePath(String eclipsePath) {
		this.eclipsePath = eclipsePath;
	}

}
