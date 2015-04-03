package me.gbwl.mp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @Title: BlackIP.java<br>
 * @package: me.gbwl.mp.util<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年4月3日 下午1:38:38<br>
 */
public class BlackIP {

	private static final Logger logger = Logger.getLogger(BlackIP.class);
	private File file;
	private long modify;
	
	private List<String> blackIps;
	private BlackIP(){ }
	private static class Tools {
		private static BlackIP blackIP = new BlackIP();
	}
	public static BlackIP getInstance() {
		if (Tools.blackIP.file == null || Tools.blackIP.file.lastModified() != Tools.blackIP.modify) {
			Tools.blackIP.init();
		}
		return Tools.blackIP;
	}
	private void init() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String path = WieParameter.class.getClassLoader().getResource("black.properties").getPath();
			file = new File(path);
			modify = file.lastModified();
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = "";
			blackIps = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (!StringUtil.isEmpty(line)) {
					line = line.trim();
					if (StringUtil.isIP(line)) {
						blackIps.add(line);
					}
				}
 			}
		} catch (Exception e) {
			logger.error("加载黑名单IP参数错误:" + e.getMessage(), e.getCause());
			throw new ExceptionInInitializerError("加载黑名单IP参数错误！！！");
		} finally {
			if (br != null) try { br.close(); } catch (Exception e) { }
			if (fr != null) try { fr.close(); } catch (Exception e) { }
		}
	}
	public boolean isBlackIP(String ip) {
		if (!StringUtil.isEmpty(ip)) {
			ip = ip.trim();
			if (StringUtil.isIP(ip)) {
				for (String str : blackIps) {
					if (str.equals(ip)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public void addBlackIP(String ip) {
		if (!StringUtil.isEmpty(ip)) {
			ip = ip.trim();
			if (StringUtil.isIP(ip)) {
				if (!isBlackIP(ip)) {	//目前的黑名单中存在
					FileWriter fw = null;
					try {
						fw = new FileWriter(file, true);
						fw.append("\r\n");
						fw.append(ip);
						fw.flush();
						blackIps.add(ip);
					} catch (Exception e) {
						logger.error("add black ip exception:" + e.getMessage(), e.getCause());
					} finally {
						if (fw != null) try { fw.close(); } catch (Exception e) { }
						modify = file.lastModified();
					}
				}
			}
		}
	}
}
