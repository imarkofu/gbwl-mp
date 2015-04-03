package me.gbwl.mp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import me.gbwl.mp.base.Article;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

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
	
	private File articlesFile;
	private long articlesFileModify;
	private List<Article> articles;
	
	private WieParameter(){
		
	}
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
	public List<Article> getArticlesCache() {
		if (articlesFile == null || articlesFile.lastModified() != articlesFileModify) {
			FileReader fr = null;
			try {
				String articleFilePath = WieParameter.class.getClassLoader().getResource("articles").getPath();
				articlesFile = new File(articleFilePath);
				articlesFileModify = articlesFile.lastModified();
				fr = new FileReader(articlesFile);
				char[] t = new char[(int) articlesFile.length()];
				fr.read(t);	//内容理论上应该不会太大，直接一次行读取全部内容，大文件不建议这样读取
				String content = new String(t);
				if (StringUtil.isEmpty(content)) {
					articles = new ArrayList<Article>();
				} else {
					try {
						articles = JSON.parseArray(content, Article.class);
					} catch (Exception e) {
						logger.error("JSON转对象异常：" + e.getMessage(), e.getCause());
						articles = new ArrayList<Article>();
					}
				}
			} catch (FileNotFoundException e) {
				logger.error("存储文章内容的文件不存在：" + e.getMessage(), e.getCause());
				articles = new ArrayList<Article>();
			} catch (IOException e) {
				logger.error("读取存储文件的文件异常：" + e.getMessage(), e.getCause());
				articles = new ArrayList<Article>();
			} catch (Exception e) {
				logger.error("未知异常:" + e.getMessage(), e.getCause());
				articles = new ArrayList<Article>();
			} finally {
				if (fr != null) try { fr.close(); } catch (Exception e) { }
			}
		}
		return articles;
	}
}
