package me.gbwl.mp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Title: WieParameter.java<br>
 * @package: me.gbwl.mp.util<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年3月27日 下午2:22:19<br>
 */
public class WieParameter {

	private static final Logger logger = Logger.getLogger(WieParameter.class);
	private File wieFile;
	private long modify;
	
	private String	domain;
	private List<String> types;
	
	private WieParameter(){ }
	private static class Tools {
		private static WieParameter wieParameter = new WieParameter();
	}
	public static WieParameter getInstance() {
		if (Tools.wieParameter.wieFile == null || 
				Tools.wieParameter.wieFile.lastModified() != Tools.wieParameter.modify)
			Tools.wieParameter.load();
		return Tools.wieParameter;
	}
	private void load() {
		InputStream is = null;
		Properties prop = new Properties();
		try {
			String path = WieParameter.class.getClassLoader().getResource("wie.properties").getPath();
			wieFile = new File(path);
			modify = wieFile.lastModified();
			is = new FileInputStream(wieFile);
			prop.load(is);
			domain = prop.getProperty("domain");
			String type = prop.getProperty("type");
			types = Arrays.asList(type.split("\\|"));
		} catch (Exception e) {
			logger.error("加载实时参数错误:" + e.getMessage(), e.getCause());
			throw new ExceptionInInitializerError("加载实时参数错误！！！");
		}
	}
	public String getDomain() {
		return domain;
	}
	public String getType(int index) {
		if (index < types.size())
			return types.get(index-1);
		return "";
	}
}
