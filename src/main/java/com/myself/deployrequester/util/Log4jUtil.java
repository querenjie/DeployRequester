package com.myself.deployrequester.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 日志工具
 */
public class Log4jUtil {

	/** 线程编号修饰符 */
	public static final char THREAD_RIGHT_TAG = ']';

	/** 线程编号修饰符 */
	public static final char THREAD_LEFT_TAG = '[';

	/** 换行符 */
	public static final char ENTERSTR = '\n';

	/** 逗号 */
	public static final char COMMA = ',';

	/**
	 * 初始化参数长度 8个
	 */
	private static final int init_len = 8;

	/**
	 * 禁用构造函数
	 */
	private Log4jUtil() {
		// 禁用构造函数
	}

	/**
	 * 生成调试级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 */
	public static void debug(Logger logger, Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug(logPrefix() + obj);
		}
	}

	/**
	 * 生成通知级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 */
	public static void info(Logger logger, Object obj) {
		if (logger.isInfoEnabled()) {
			logger.info(logPrefix() + obj);
		}
	}

	/**
	 * 生成通知级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 * @param e
	 *            异常对象
	 */
	public static void info(Logger logger, Object obj, Throwable e) {
		if (logger.isInfoEnabled()) {
			logger.info(logPrefix() + obj, e);
		}
	}

	/**
	 * 生成无前缀打印通知级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 */
	public static void infoNoPrefix(Logger logger, Object obj) {
		if (logger.isInfoEnabled()) {
			logger.info(obj);
		}
	}

	/**
	 * 生成警告级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 */
	public static void warn(Logger logger, Object obj) {

		logger.warn(logPrefix() + obj);

	}

	/**
	 * 生成警告级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 * @param t
	 *            异常
	 */
	public static void warn(Logger logger, Object obj, Throwable t) {

		if (t == null) {
			logger.warn(logPrefix() + obj);
		} else {
			logger.warn(logPrefix() + obj, t);
		}

	}

	/**
	 * 生成错误级别日志<br>
	 * 
	 * @param logger
	 *            日志文件
	 * @param obj
	 *            对象
	 * @param t
	 *            可为空
	 */
	public static void error(Logger logger, Object obj, Throwable t) {

		if (t == null) {
			logger.error(logPrefix() + obj);
		} else {
			logger.error(logPrefix() + obj, t);
		}

	}

	// /**
	// * 获取sofa统一上下文
	// *
	// * @return String 统一上下文
	// */
	// public static final String getContextUniqueId() {
	// try {
	// UniformContextHeaders info = ContextLifeManager.checkAPIQuery();
	// return info.getInvokeId();//上下文ID
	// } catch (Exception e) {
	// return "-";
	// }
	// }

	/**
	 * 获取调用Log类的调用类,调用方法,和行数
	 * 
	 * <p>
	 * 如[LoggerUtil-main-165]
	 * 
	 * @return 返回结果
	 */
	public static String getCaller() {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StackTraceElement s = stack[3];

		// 类名
		String className = StringUtils.left(s.getFileName(), StringUtils
				.lastIndexOf(s.getFileName(), "."));

		// 方法名
		String methodName = s.getMethodName();

		// 行数
		int lineNumber = s.getLineNumber();

		return "[" + className + "." + methodName + "(" + lineNumber + ")]";
	}

	/**
	 * 日志打印时前缀添加
	 * 
	 * @return 返回添加前缀后的结果
	 */
	private static String logPrefix() {
		return getCaller() + " --> ";
		// return getContextUniqueId() + "-->" + getCaller() + " --> ";
	}

	/**
	 * 日志打印时前缀添加
	 * 
	 * @return 返回添加前缀后的结果
	 */
	private static String digestPrefix() {
		String digestPrefix = getCaller();
		if (StringUtils.isNotBlank(digestPrefix)) {
			digestPrefix = digestPrefix.substring(0, digestPrefix.length() - 1);
		}
		return digestPrefix;
	}

	/**
	 * 打印摘要日志
	 * 
	 * @param logger
	 *            日志文件
	 * @param infos
	 *            日志内容 基本顺序： 1:userId;2:操作业务内容;3:操作的步骤;4：最终结果（Y/N）
	 * 
	 */
	public static void digesterInfo(Logger logger, String... infos) {
		try {
			// 打印摘要日志
			if (null != infos && infos.length > 0) {
				if (logger.isInfoEnabled()) {
					StringBuffer logSb = new StringBuffer(digestPrefix());
					for (int i = 0, infos_len = infos.length; i < init_len; i++) {
						if (i >= infos_len) {
							// 如果缺少这个参数则默认打印“-”
							logSb.append(",-");
						} else {
							logSb.append("," + infos[i]);
						}
					}
					logSb.append("]");
					logger.info(logSb.toString());
				}
			}
		} catch (Exception e) {
			return;
		}

	}

}
