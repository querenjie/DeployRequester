package com.myself.deployrequester.biz.design.dynamiccomponent.scanner;

import com.myself.deployrequester.biz.config.log.Loggers;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ComponentScanner;
import com.myself.deployrequester.biz.design.dynamiccomponent.core.ReloadCallback;
import com.myself.deployrequester.biz.design.dynamiccomponent.model.ResourceFile;
import com.myself.deployrequester.util.Log4jUtil;
import com.myself.deployrequester.util.LoggerUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * 定时扫描指定文件目录
 * <p>
 * 注意：为节省扫描性能，文件删除是没法触发重新加载的，删除后需要随便修改下剩余的某个文件
 * </p>
 */
public class FolderScanner implements ComponentScanner {
	/** 日志 */
	private static final Logger logger = LogManager.getLogger(FolderScanner.class);
	/** 10秒钟扫一次 */
	private long scanInterval = 10 * 1000;
	/** 用于备份文件夹内容标识，如果内容标识变了就表示文件有更新 */
	private Map<String, Long> folderCheckNumMap = new HashMap<String, Long>();
	/** 监听的目录 */
	private File folder;
	/** 监听的目录地址 */
	private String folderPath;
	/** 监听到文件变化后的回调 */
	private ReloadCallback callback;
	/** 监听的文件扩展名 */
	private String[] extensions;

	/**
	 * 通过最后修改日期来判断某个文件夹是否变化
	 * 
	 * @param f
	 *            文件夹
	 * @return 是否发生变化
	 */
	@SuppressWarnings("unchecked")
	private boolean checkChangedAndSaveState(File f) {
		// checknum表示文件夹是否更新
		Long oldFolderCheckNum = folderCheckNumMap.get(f.getAbsolutePath());
		Long newFolderCheckNum = 0L;
		Iterator<File> fileIterator = FileUtils.iterateFiles(f, extensions,
				true);
		while (fileIterator.hasNext()) {
			File ff = fileIterator.next();
			newFolderCheckNum += ff.getAbsolutePath().hashCode();
			newFolderCheckNum += ff.lastModified();
		}
		// 
		if (oldFolderCheckNum == null
				|| !newFolderCheckNum.equals(oldFolderCheckNum)) {
			// 有变化
			folderCheckNumMap.put(f.getAbsolutePath(), newFolderCheckNum);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 用于子类扩展检测的后缀名
	 * 
	 * @param extensions
	 *            原始配置的后缀
	 * @return 实际使用的后缀
	 */
	protected String[] prepareExtensions(String[] extensions) {
		return extensions;
	}

	/**
	 * 用于子类扩展处理读取到的资源文件
	 * 
	 * @param list
	 *            资源文件
	 * @return 处理后的资源文件
	 */
	protected List<ResourceFile> prepareResourceFiles(List<ResourceFile> list) {
		return list;
	}

	public void start(String[] extensions, ReloadCallback callback) {
		this.callback = callback;
		this.folder = new File(folderPath);
		this.extensions = prepareExtensions(extensions);

		Loggers.MONITOR_LOGGER.info("monitor folder : " + folder.getAbsolutePath());
		Log4jUtil.info(logger, "monitor folder : " + folder.getAbsolutePath());

		// 启动的时候先同步触发一次
		fireScan();
		// 然后启动一个线程定时触发
		new Timer("components-folder-scanner", true).schedule(new ScanTask(),
				scanInterval, scanInterval);
	}

	/**
	 * 检测所有监听目录，如果某个目录有变化就调用回调函数
	 */
	public void fireScan() {
		// 如果发生异常不中断容器加载
		try {
			for (File f : folder.listFiles()) {
				// 第一层目录只判断文件夹
//				Log4jUtil.info(logger, "f.getName() : " + f.getName());
//				Log4jUtil.info(logger, "f.isFile() : " + f.isFile());
				if (f.isFile()) {
					continue;
				}
				// 文件夹没变化就不更新了
				boolean hasFileChanged = checkChangedAndSaveState(f);
//				Log4jUtil.info(logger, "hasFileChanged : " + hasFileChanged);
				if (!hasFileChanged) {
					continue;
				}
				// 文件夹名字作为组件类型
				String folderName = f.getName();
				Loggers.MONITOR_LOGGER.info("检测到目录下的内容有变化，重新加载目录下的文件。目录：" + folderName);
				Log4jUtil.info(logger, "检测到目录下的内容有变化，重新加载目录下的文件。目录：" + folderName);
				List<ResourceFile> list = new ArrayList<ResourceFile>();
				File[] files = FileUtils
						.convertFileCollectionToFileArray(FileUtils.listFiles(
								f, extensions, true));
				for (File ff : files) {
					ResourceFile file = new ResourceFile();
					file.setName(StringUtils.substringBeforeLast(ff.getName(),
							"."));
					file.setContent(FileUtils.readFileToByteArray(ff));
					file.setExtension(StringUtils.substringAfterLast(ff
							.getName(), "."));
					list.add(file);
				}
				// 处理读取到的资源文件
				list = prepareResourceFiles(list);
				// 按照文件名排序“_”开头的会排在最前面（可利用这个特性控制加载顺序）
				Collections.sort(list, new Comparator<ResourceFile>() {
					public int compare(ResourceFile o1, ResourceFile o2) {
						return o1.getName().compareToIgnoreCase(o2.getName());
					}
				});
				try {
					// 通知回调
					callback.onReload(folderName, list);
				}
				// 这里暂时把error也捕捉掉，因为脚本编写不当可能会报NoClassDefFoundError，造成整个系统启动不了
				catch (Throwable e) {
					Log4jUtil.error(logger, "onReload error", e);
				}
			}
		} catch (Exception e) {
			Log4jUtil.error(logger, "file scan error", e);
		}
	}

	/**
	 * 定时检测文件变化的线程
	 */
	private class ScanTask extends TimerTask {

		@Override
		public void run() {
			fireScan();
		}

	}

	/**
	 * Setter method for property <tt>scanInterval</tt>.
	 * 
	 * @param scanInterval
	 *            value to be assigned to property scanInterval
	 */
	public void setScanInterval(long scanInterval) {
		this.scanInterval = scanInterval;
	}

	/**
	 * Getter method for property <tt>folderPath</tt>.
	 * 
	 * @return property value of folderPath
	 */
	public String getFolderPath() {
		return folderPath;
	}

	/**
	 * Setter method for property <tt>folderPath</tt>.
	 * 
	 * @param folderPath
	 *            value to be assigned to property folderPath
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

}
