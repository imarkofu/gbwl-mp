package me.gbwl.mp.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 工具类
 * @author gds
 *
 */
public class RequestUtils {
	/**
	 * 该方法用于获取request中的参数值，如该参数不存在，则返回""
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getStringP(HttpServletRequest request,String name){
		String p=request.getParameter(name);
		return p==null?"":p.trim();
	}
	/**
	 * 该方法用于获取request中的参数值，如该参数不存在，则返回-1，如存在，将该值转换成int型
	 * @param request
	 * @param name
	 * @return
	 */
	public static int getIntP(HttpServletRequest request,String name){
		String p=RequestUtils.getStringP(request, name);
		return p.length()==0?-1:Integer.parseInt(p);
	}
	/**
	 * 该方法用于获取request中的参数值，如该参数不存在，则返回-1,如存在，将该值转换成long型
	 * @param request
	 * @param name
	 * @return
	 */
	public static long getLongP(HttpServletRequest request,String name){
		String p=RequestUtils.getStringP(request, name);
		return p.length()==0?-1:Long.parseLong(p);
	}
	/**
	 * 该方法用于获取request中的参数值，如该参数不存在，则返回-1,如存在，将该值转换成float型
	 * @param request
	 * @param name
	 * @return
	 */
	public static float getFloatP(HttpServletRequest request,String name){
		String p=RequestUtils.getStringP(request, name);
		return p.length()==0?-1:Float.parseFloat(p);
	}
	/**
	 * 该方法用于获取request中的参数值，如该参数不存在，则返回-1,如存在，将该值转换成double型
	 * @param request
	 * @param name
	 * @return
	 */
	public static double getDoubleP(HttpServletRequest request,String name){
		String p=RequestUtils.getStringP(request, name);
		return p.length()==0?-1:Double.parseDouble(p);
	}
	/**
	 * 该方法用于获取request中的参数值.并将用","分割的参数值转化成String型数组
	 * @param request
	 * @param name
	 * @return
	 */
	public static List<String> getStringListP(HttpServletRequest request,String name){
		List<String> list=new ArrayList<String>();
		String value=RequestUtils.getStringP(request, name);
		String[] values=value.split(",");
		for(String str:values){
			if(str.length()!=0)list.add(str);
		}
		return list;
	}
	/**
	 * 该方法用于获取request中的参数值.并将用","分割的参数值转化成int型数组
	 * @param request
	 * @param name
	 * @return
	 */
	public static List<Integer> getIntListP(HttpServletRequest request,String name){
		List<Integer> list=new ArrayList<Integer>();
		String value=RequestUtils.getStringP(request, name);
		String[] values=value.split(",");
		for(String str:values){
			if(str.length()!=0)list.add(Integer.parseInt(str));
		}
		return list;
	}
	
	/**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
