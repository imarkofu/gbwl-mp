package me.gbwl.mp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 
	 * 2014-11-12更新,功能：判断字符串为utf8编码还是gbk编码
	 * @param str
	 * @return
	 */
	public static boolean isUTF_8(String str){
		try {
			return str.equalsIgnoreCase(URLEncoder.encode(URLDecoder.decode(str, "utf-8"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 2014-08-07更新，获取字符串中，从startTag到endTage之间的内容。
	 * @param src
	 * @param startTag
	 * @param endTag
	 * @return
	 */
	public static String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = 0;
		try {
			if(startTag!=null){
				if(src.indexOf(startTag)>=0){
					start = src.indexOf(startTag);
				}
				start += startTag.length();
			}
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return content;
	}
	
	/**
	 * 2014-08-07更新，数组转化成String字符串。
	 * @param src
	 * @param startTag
	 * @param endTag
	 * @return
	 */
	public static String arrayToString(Object[] objects){
		StringBuilder sb=new StringBuilder();
		for(Object o:objects){
			if(isEmpty(sb.toString()))
				sb.append(o.toString());
			else
				sb.append(",").append(o.toString());
		}
		return sb.length()==0?"":sb.toString();
	}
	
	/**
	 * 2014-07-11 更新
	 * 
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> stringToList(String string, Class<T> c) {
		String[] strs = string.split("[\\s|,|\\.|;|；]+");
		List<T> list = new LinkedList<T>();
		for (String str : strs) {
			list.add((T) str);
		}
		return list;
	}

	public static List<String> toMobileList(String mobiles) {
		String[] strs = mobiles.split("[\\s|,|\\.|;|；]+");
		List<String> list = new LinkedList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;
	}

	public static String toMobileStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
			sb.append(",");
		}
		return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
	}

	public static String list2String(List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (Object o : list) {
			sb.append(o);
			sb.append(',');
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	@SuppressWarnings("rawtypes")
	public static String getList2String(List list, String split) {
		if (list == null || list.size() <= 0)
			return null;
		else {
			String str = "";
			for (int i = 0; i < list.size(); i++) {
				if (split != null && i < list.size() - 1)
					str = str + list.get(i) + split;
				else
					str = str + list.get(i);
			}
			return str;
		}
	}

	/**
	 * 锟斤拷GB2312锟斤拷锟斤拷转为ISO8859_1
	 * 
	 * @param strGB2312
	 *            GB2312锟街凤拷
	 * @return ISO8859_1锟街凤拷
	 */
	public static String GB2312ToISO8859_1(String strGB2312) {
		try {
			if (strGB2312 != null) {
				return new String(strGB2312.getBytes("gb2312"), "iso-8859-1");
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷ISO8859_1锟斤拷锟斤拷转为GB2312
	 * 
	 * @param strISO8859_1
	 *            ISO8859_1锟街凤拷
	 * @return GB2312锟街凤拷
	 */
	public static String ISO8859_1ToGB2312(String strISO8859_1) {
		try {
			if (null != strISO8859_1) {
				return new String(strISO8859_1.getBytes("iso-8859-1"), "gb2312");
			} else {
				return strISO8859_1;
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷GBK锟斤拷锟斤拷转为ISO8859_1
	 * 
	 * @param strGBK
	 *            GBK锟街凤拷
	 * @return ISO8859_1锟街凤拷
	 */
	public static String GBKToISO8859_1(String strGBK) {
		try {
			if (null != strGBK) {
				return new String(strGBK.getBytes("GBK"), "iso-8859-1");
			} else {
				return strGBK;
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷ISO8859_1锟斤拷锟斤拷转为GBK
	 * 
	 * @param strISO8859_1
	 *            ISO8859_1锟街凤拷
	 * @return GBK锟街凤拷
	 */
	public static String ISO8859_1ToGBK(String strISO8859_1) {
		try {
			if (null != strISO8859_1) {
				return new String(strISO8859_1.getBytes("iso-8859-1"), "GBK");
			} else {
				return strISO8859_1;
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Replaces all occurances of oldString in mainString with newString
	 * 
	 * @param mainString
	 *            The original string
	 * @param oldString
	 *            The string to replace
	 * @param newString
	 *            The string to insert in place of the old
	 * @return mainString with all occurances of oldString replaced by newString
	 */
	public static String replaceString(String mainString, String oldString,
			String newString) {
		if (mainString == null) {
			return null;
		}
		if (oldString == null || oldString.length() == 0) {
			return mainString;
		}
		if (newString == null) {
			newString = "";
		}

		int i = mainString.lastIndexOf(oldString);
		if (i < 0)
			return mainString;

		StringBuffer mainSb = new StringBuffer(mainString);

		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * Creates a single string from a List of strings seperated by a delimiter.
	 * 
	 * @param list
	 *            a list of strings to join
	 * @param delim
	 *            the delimiter character(s) to use. (null value will join with
	 *            no delimiter)
	 * @return a String of all values in the list seperated by the delimiter
	 */
	public static String join(List<Object> list, String delim) {
		if (list == null || list.size() < 1)
			return null;
		StringBuffer buf = new StringBuffer();
		Iterator<Object> i = list.iterator();
		while (i.hasNext()) {
			buf.append((String) i.next());
			if (i.hasNext())
				buf.append(delim);
		}
		return buf.toString();
	}

	/**
	 * Splits a String on a delimiter into a List of Strings.
	 * 
	 * @param str
	 *            the String to split
	 * @param delim
	 *            the delimiter character(s) to join on (null will split on
	 *            whitespace)
	 * @return a list of Strings
	 */
	public static List<String> split(String str, String delim) {
		List<String> splitList = null;
		StringTokenizer st = null;

		if (str == null)
			return splitList;

		if (delim != null)
			st = new StringTokenizer(str, delim);
		else
			st = new StringTokenizer(str);

		if (st != null && st.hasMoreTokens()) {
			splitList = new ArrayList<String>();

			while (st.hasMoreTokens())
				splitList.add(st.nextToken());
		}
		return splitList;
	}

	/**
	 * Encloses each of a List of Strings in quotes.
	 * 
	 * @deprecated
	 * @param list
	 *            List of String(s) to quote.
	 */
	public static List<Object> quoteStrList(List<Object> list) {
		List<Object> tmpList = list;
		list = new ArrayList<Object>();
		Iterator<Object> i = tmpList.iterator();
		while (i.hasNext()) {
			String str = (String) i.next();
			str = "'" + str + "''";
			list.add(str);
		}
		return list;
	}

	/**
	 * Creates a Map from an encoded name/value pair string
	 * 
	 * @deprecated
	 * @param str
	 *            The string to decode and format
	 * @return a Map of name/value pairs
	 */
	public static Map<String, String> strToMap(String str) {
		if (str == null)
			return null;
		Map<String, String> decodedMap = new HashMap<String, String>();
		List<String> elements = split(str, "|");
		Iterator<String> i = elements.iterator();
		while (i.hasNext()) {
			String s = (String) i.next();
			List<String> e = split(s, "=");
			if (e.size() != 2)
				continue;
			String name = (String) e.get(0);
			String value = (String) e.get(1);
			try {
				decodedMap.put(URLDecoder.decode(name, "UTF-8"),
						URLDecoder.decode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return decodedMap;
	}

	/**
	 * Creates an encoded String from a Map of name/value pairs (MUST BE
	 * STRINGS!)
	 * 
	 * @deprecated
	 * @param map
	 *            The Map of name/value pairs
	 * @return String The encoded String
	 */
	public static String mapToStr(Map<String, String> map) {
		if (map == null)
			return null;
		StringBuffer buf = new StringBuffer();
		Set<String> keySet = map.keySet();
		Iterator<String> i = keySet.iterator();
		boolean first = true;
		while (i.hasNext()) {
			Object key = i.next();
			Object value = map.get(key);
			if (!(key instanceof String) || !(value instanceof String))
				continue;
			String encodedName = URLEncoder.encode((String) key);
			String encodedValue = URLEncoder.encode((String) value);

			if (first)
				first = false;
			else
				buf.append("|");

			buf.append(encodedName);
			buf.append("=");
			buf.append(encodedValue);
		}
		return buf.toString();
	}

	public static boolean isNumeric(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException sqo) {
			return false;
		}
	}

	public static final int TRIM = 1;

	public static final int NOTRIM = 2;

	/**
	 * 锟斤拷锟斤拷锟斤拷址锟�
	 * 
	 * @param Object
	 *            str
	 * @return String
	 */
	public static String dealNull(Object str) {
		if (str == null)
			return "";
		if (str.equals(""))
			return "";
		return str.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷址锟�
	 * 
	 * @param Object
	 *            str
	 * @param String
	 *            defaultValue
	 * @return String
	 */
	public static String dealNull(Object str, String defaultValue) {
		if (str == null)
			return defaultValue;
		if (str.equals(""))
			return defaultValue;
		return str.toString();
	}

	/**
	 * 锟街凤拷锟�锟斤拷
	 * 
	 * @param String
	 *            str
	 * @return String
	 */
	public static String dealString(String str) {
		return dealNull(str);
	}

	/**
	 * 锟街凤拷锟�锟斤拷
	 * 
	 * @param String
	 *            str
	 * @param int sType
	 * @return String
	 */
	public static String dealString(String str, int sType) {
		switch (sType) {
		case TRIM: {
			return dealString(str).trim();
		}

		case NOTRIM: {
			return dealString(str);
		}

		default: {
			return dealString(str).trim();
		}
		}
	}

	/**
	 * 锟斤拷取一锟斤拷锟街凤拷某锟斤拷锟�锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�),锟斤拷锟斤拷锟斤拷锟接拷锟�
	 * 锟斤拷锟斤拷锟斤拷植锟斤拷锟矫ｏ拷锟斤拷锟斤拷取一锟斤拷锟街凤拷位
	 * 
	 * @param str
	 *            原始锟街凤拷
	 * @param specialCharsLength
	 *            锟斤拷取锟斤拷锟斤拷(锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�)
	 * @return
	 */
	public static String trim(String str, int specialCharsLength) {
		if (str == null || "".equals(str) || specialCharsLength < 1) {
			return "";
		}
		char[] chars = str.toCharArray();
		int charsLength = getCharsLength(chars, specialCharsLength);
		return new String(chars, 0, charsLength);
	}

	/**
	 * 锟斤拷取一锟斤拷锟街凤拷某锟斤拷锟�锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�),锟斤拷锟斤拷锟斤拷锟接拷锟�
	 * 锟斤拷锟斤拷锟斤拷植锟斤拷锟矫ｏ拷锟斤拷锟斤拷取一锟斤拷锟街凤拷位
	 * 
	 * @param str
	 *            原始锟街凤拷
	 * @param specialCharsLength
	 *            锟斤拷取锟斤拷锟斤拷(锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�)
	 * @return
	 */
	public static String trimString(String str, int specialCharsLength) {
		if (str == null || "".equals(str) || specialCharsLength < 1) {
			return "";
		}
		char[] chars = str.toCharArray();
		if (getCharsLength(chars) > specialCharsLength) {
			int charsLength = getCharsLength(chars, specialCharsLength);
			return new String(chars, 0, charsLength) + "..";
		} else {
			return str;
		}
	}

	public static String replaceBlank(String str) {
		if (str == null || "".equals(str.trim()))
			return "";

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * 锟斤拷取一锟斤拷锟街凤拷某锟斤拷龋锟斤拷锟斤拷氤わ拷锟斤拷泻锟斤拷锟斤拷铡锟斤拷锟斤拷锟斤拷址锟斤拷为2锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟轿
	 * �
	 * 
	 * @param chars
	 *            一锟斤拷锟街凤拷
	 * @param specialCharsLength
	 *            锟斤拷锟诫长锟饺ｏ拷锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�
	 * @return 锟斤拷锟斤拷锟斤拷龋锟斤拷锟斤拷锟斤拷址锟斤拷锟轿�
	 */
	private static int getCharsLength(char[] chars, int specialCharsLength) {
		int count = 0;
		int normalCharsLength = 0;
		for (int i = 0; i < chars.length; i++) {
			int specialCharLength = getSpecialCharLength(chars[i]);
			if (count <= specialCharsLength - specialCharLength) {
				count += specialCharLength;
				normalCharsLength++;
			} else {
				break;
			}
		}
		return normalCharsLength;
	}

	/**
	 * 锟斤拷取锟街凤拷某锟斤拷龋锟斤拷锟斤拷氤わ拷锟斤拷泻锟斤拷锟斤拷铡锟斤拷锟斤拷锟斤拷址锟斤拷为2锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷址锟斤拷锟轿
	 * �
	 * 
	 * @param chars
	 *            锟街凤拷
	 * @param specialCharsLength
	 *            锟斤拷锟诫长锟饺ｏ拷锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷锟街凤拷锟轿�
	 * @return 锟斤拷锟斤拷锟斤拷龋锟斤拷锟斤拷锟斤拷址锟斤拷锟轿�
	 */
	private static int getCharsLength(char[] chars) {
		int normalCharsLength = 0;
		for (int i = 0; i < chars.length; i++) {
			normalCharsLength = normalCharsLength
					+ getSpecialCharLength(chars[i]);
		}
		return normalCharsLength;
	}

	/**
	 * 锟斤拷取锟街凤拷龋锟斤拷锟斤拷锟斤拷铡锟斤拷锟斤拷锟斤拷址锟斤拷为2锟斤拷ASCII锟斤拷锟斤拷址锟斤拷为1
	 * 
	 * @param c
	 *            锟街凤拷
	 * @return 锟街凤拷锟�
	 */
	private static int getSpecialCharLength(char c) {
		if (isLetter(c)) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * 锟叫讹拷一锟斤拷锟街凤拷锟斤拷Ascill锟街凤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟界汉锟斤拷锟秸ｏ拷锟斤拷锟斤拷锟街凤拷
	 * 
	 * @param char c, 锟斤拷要锟叫断碉拷锟街凤拷
	 * @return boolean, 锟斤拷锟斤拷true,Ascill锟街凤拷
	 */
	private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	private static Random randGen = null;
	private final static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	private static Object initLock = new Object();

	public static final String randomString(int length) {

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen
					.nextInt(numbersAndLetters.length)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}

	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();
	private static final char[] APOS_ENCODE = "&apos;".toCharArray();

	public static String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static String escapeXMLTags(String in) {
		if (in == null) {
			return "";
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				// continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '\"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			} else if (ch == '\'') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(APOS_ENCODE);
			} else if (ch == '\u0008') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(" ");
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷gb2312 -> iso-8859-1
	 * 
	 * @param sSource
	 *            source
	 * @return result
	 */
	public static String gb2iso(String sSource) {
		try {
			return new String(sSource.getBytes("gb2312"), "iso-8859-1");
		} catch (Exception e) {
			// System.out.println("Charset gb2iso Err:" + e.getMessage());
			return sSource;
		}
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷iso-8859-1 -> gb2312
	 * 
	 * @param sSource
	 *            锟斤拷锟斤拷锟皆�
	 * @return result
	 */
	public static String iso2gb(String sSource) {
		try {
			return new String(sSource.getBytes("iso-8859-1"), "gb2312");
		} catch (Exception e) {
			// System.out.println("Charset iso2gb Err:" + e.getMessage());
			return sSource;
		}
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷byte -> gb2312
	 * 
	 * @param sSource
	 *            锟斤拷锟斤拷锟皆�
	 * @return result
	 */
	public static String byte2gb(byte[] sSource) {
		try {
			return new String(sSource, "gb2312");
		} catch (Exception e) {
			// System.out.println("Charset byte2gb Err:" + e.getMessage());
			return "";
		}
	}

	static public String native2Unicode(String s) {
		char c;
		int j = 0;
		if (s == null || s.length() == 0) {
			return null;
		}
		byte[] buffer = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 0x100) {
				c = s.charAt(i);
				byte[] buf = ("" + c).getBytes();
				buffer[j++] = buf[0];
				buffer[j++] = buf[1];
			} else {
				buffer[j++] = (byte) s.charAt(i);
			}
		}
		return new String(buffer, 0, j);
	}

	/**
	 * 锟斤拷全路锟斤拷锟街凤拷锟叫伙拷取锟侥硷拷锟斤拷
	 * 
	 * @param sPath
	 *            filename with path
	 * @return only filename
	 */
	public static String getFileName(String sPath) {
		try {
			return sPath.substring(sPath.lastIndexOf('\\') + 1, sPath.length());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷全路锟斤拷锟街凤拷锟叫伙拷取锟睫猴拷缀锟斤拷锟斤拷募锟斤拷锟�
	 * 
	 * @param sPath
	 *            file name with path and extname
	 * @return result
	 */
	public static String getFileNameNoExt(String sPath) {
		try {
			int iBegin, iEnd;
			iBegin = sPath.lastIndexOf('\\');
			iEnd = sPath.lastIndexOf('.');
			return sPath.substring(iBegin + 1, iEnd);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷路锟斤拷锟斤拷锟斤拷锟侥┪裁伙拷锟絓锟斤拷锟斤拷锟�
	 * 
	 * @param sDir
	 *            a
	 * @return a
	 */
	public static String checkDir(String sDir) {
		if (!sDir.substring(sDir.length() - 1, sDir.length()).equals("\\")) {
			sDir = sDir + "\\";
		}
		return sDir;
	}

	/**
	 * 锟斤拷锟街凤拷yyyy-MM-dd锟斤拷式锟斤拷转锟斤拷为Date锟斤拷锟斤拷
	 * 
	 * @param sDate
	 *            date
	 * @return result
	 */
	public static Date string2date(String sDate) {
		return string2date(sDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * boolean锟斤拷String锟斤拷锟斤拷转锟斤拷
	 * 
	 * @param data
	 *            data
	 * @return result
	 */
	public static boolean string2bool(String data) {
		return data != null
				&& ("true".equals(data.toLowerCase()) || "1".equals(data
						.toLowerCase()));
	}

	public static String bool2string(boolean data) {
		if (data) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * int 锟斤拷 String锟斤拷锟斤拷转锟斤拷
	 * 
	 * @param value
	 *            value
	 * @param defaultVal
	 *            default
	 * @return result
	 */
	public static int string2int(String value, int defaultVal) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultVal;
		}
	}

	/**
	 * 锟斤拷锟斤拷 锟斤拷 long锟斤拷锟斤拷转锟斤拷
	 * 
	 * @param value
	 *            source
	 * @param defaultVal
	 *            defualt
	 * @return result
	 */
	public static long date2long(java.util.Date value, long defaultVal) {
		try {
			return value.getTime();
		} catch (Exception e) {
			return defaultVal;
		}
	}

	public static long string2long(String value, long defaultVal) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return defaultVal;
		}
	}

	public static Date string2date(String sDate, String sFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(sFormat);
		try {
			return sf.parse(sDate);
		} catch (Exception ex) {
			return new Date();
		}
	}

	/**
	 * 锟斤拷一锟斤拷IP锟斤拷址锟街凤拷转锟斤拷锟斤拷一锟斤拷long锟斤拷锟斤拷
	 * 
	 * @param ipAddress
	 *            source
	 * @return result
	 */
	public static long string2IpLong(String ipAddress) {
		StringTokenizer st = new StringTokenizer(ipAddress.trim(), ".");
		long result = 0;
		while (st.hasMoreTokens()) {
			try {
				result = result * 256;
				result = result + Integer.parseInt(st.nextToken().trim());
			} catch (Exception e) {
				// 锟斤拷锟斤拷锟斤拷锟叫憋拷态锟斤拷萁锟斤拷锟斤拷幕锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟揭伙拷锟斤拷欠锟斤拷锟揭拷锟斤拷锟斤拷斐�

			}
		}
		return result;
	}

	/**
	 * 锟斤拷一锟斤拷long锟斤拷IP转锟斤拷为一锟斤拷锟街凤拷
	 * 
	 * @param ip
	 *            source
	 * @return result
	 */
	public static String longIp2String(long ip) {
		String Result = "";
		// 支锟斤拷IPv6锟斤拷哦
		for (int i = 0; i < 6; i++) {
			if (!"".equals(Result)) {
				Result = "." + Result;
			}
			Result = (ip & 0xFF) + Result;
			ip = ip >> 8;
			if (ip == 0) {
				break;
			}
		}
		return Result;
	}

	/**
	 * 锟斤拷Date锟斤拷锟斤拷转锟斤拷为String锟斤拷锟斤拷
	 * 
	 * @param date
	 *            date
	 * @return result
	 */
	public static String date2string(Date date) {
		return date2string(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String date2string(long date) {
		java.util.Date theTime = new java.util.Date(date);
		return date2string(theTime);
	}

	public static String date2string(Date date, String format) {
		if (date == null) {
			date = new java.util.Date();
		}
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	/**
	 * 锟叫讹拷锟角凤拷为锟斤拷锟斤拷,锟斤拷锟斤拷锟届常锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param syear
	 *            year
	 * @param smonth
	 *            month
	 * @param sday
	 *            day
	 * @return day
	 */
	public static String LeapYear(String syear, String smonth, String sday) {
		long year = Long.parseLong(syear);
		long month = Long.parseLong(smonth);
		long day = Long.parseLong(sday);

		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			if (day == 31)
				day = 30;
		} else if (month == 2) {
			if ((day == 31) || (day == 30) || (day == 29)) {
				if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
					day = 29;
				} else {
					day = 28;
				}
			}
		}
		if (day < 10) {
			return "0" + String.valueOf(day);
		} else {
			return String.valueOf(day);
		}
	}

	/**
	 * 锟叫讹拷锟角凤拷为锟斤拷锟斤拷,锟斤拷锟斤拷锟届常锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param year
	 *            year
	 * @param month
	 *            month
	 * @param day
	 *            day
	 * @return result
	 */
	public static long LeapYear(long year, long month, long day) {
		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			if (day == 31)
				day = 30;
		} else if (month == 2) {
			if ((day == 31) || (day == 30) || (day == 29)) {
				if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
					day = 29;
				} else {
					day = 28;
				}
			}
		}
		return day;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷址锟�锟界将#123#23456#222333#
	 * 锟街斤拷锟絣ist[0]=123,list[1]=23456,list[3]=222333
	 * 
	 * @param fullstr
	 *            source
	 * @param indexofstr
	 *            dot
	 * @return result
	 */
	@SuppressWarnings("rawtypes")
	public static List getList(String fullstr, String indexofstr) {
		List<String> list = new ArrayList<String>();
		if (fullstr == null) {
			return list;
		}
		if (indexofstr == null) {
			list.add(fullstr);
			return list;
		}
		fullstr = fullstr.trim();
		/*
		 * indexofstr = indexofstr.trim(); int length = fullstr.length(); int j
		 * = 1;
		 */
		int indexLen = indexofstr.length();
		String tempstr = fullstr;
		String tempstr2;
		int i = tempstr.indexOf(indexofstr);
		while (i >= 0) {
			tempstr2 = tempstr.substring(0, i).trim();
			if (tempstr2 != null && tempstr2.length() > 0) {
				list.add(tempstr2);
			}
			if (i + indexLen < tempstr.length()) {
				tempstr = tempstr.substring(i + indexLen);
			} else {
				break;
			}
			i = tempstr.indexOf(indexofstr);
		}
		if (tempstr.length() >= 1) {
			list.add(tempstr);
		}
		/*
		 * if (i != -1) { for (; i < length - indexLen;) { tempstr =
		 * fullstr.substring(i + indexLen, length).trim(); j =
		 * tempstr.indexOf(indexofstr); tempstr2 = tempstr.substring(0, j);
		 * list.add(tempstr2.trim()); i = i + j + indexLen; } }
		 */
		return list;
	}

	/**
	 * 锟斤拷指锟斤拷锟斤拷list转锟斤拷为String[]
	 * 
	 * @param list
	 *            source
	 * @return result
	 */
	@SuppressWarnings("rawtypes")
	public static String[] list2StringArray(List list) {
		if (list == null) {
			return null;
		}
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i).toString();
		}
		return result;
	}

	/**
	 * 锟斤拷锟矫斤拷宀ワ拷锟斤拷锟斤拷锟斤拷路锟斤拷
	 * 
	 * @param serverPath
	 *            server path rstp://serverIP:port///
	 * @param mediaPath
	 *            media path ///dir1/dir2/movie.wmv
	 * @return result of rtsp://serverIp:port/dir1/dir2/movie.wmv
	 */
	public static String composeFullUrl(String serverPath, String mediaPath) {

		if (serverPath == null || mediaPath == null)
			return "faint";
		serverPath = serverPath.replace('\\', '/');

		mediaPath = mediaPath.replace('\\', '/');

		// 路锟斤拷锟较成的斤拷壮锟斤拷
		if (!serverPath.substring(serverPath.length() - 1, serverPath.length())
				.equals("/")) {
			serverPath = serverPath + "/";
		}
		if (mediaPath.substring(0, 1).equals("/")) {
			mediaPath = mediaPath.substring(1, mediaPath.length());
		}
		return serverPath + mediaPath;
	}

	/**
	 * 锟斤拷锟斤拷URL锟斤拷锟斤拷锟斤拷锟叫的达拷锟斤拷锟斤拷锟斤拷锟斤拷斜锟杰ｏ拷锟斤拷///锟斤拷锟斤拷锟斤拷..
	 * 锟斤拷锟饺斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷确锟斤拷url
	 * 
	 * @param sURL
	 *            like rtsp://serverIp/dir1//dir2///dir3/movie.wmv
	 * @return result rtsp://serverIp/dir1/dir2/dir3/movie.wmv
	 */
	public static String checkURL(String sURL) {
		StringBuffer ResultBuffer = new StringBuffer();
		int i = sURL.length();
		int TokenCount = 0, DotCount = 0, lastTokenPos = -1, prevDirPos = -1;
		// 循锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷屑锟斤拷锟�
		for (int j = 0; j < i; j++) {
			char ch = sURL.charAt(j);
			switch (ch) {
			case '?':
				// ResultBuffer.append(ch);
				ResultBuffer.append(sURL.substring(j));
				j = i;
				break;
			case '.':
				if (0 == DotCount) {
					ResultBuffer.append(ch);
				} else if (1 == DotCount) { // get a ".."

				}
				DotCount++;
				TokenCount = 0;
				break;
			case '/':
				// 锟斤拷锟斤拷强锟酵凤拷锟�//锟侥伙拷
				if (j > 5) {
					if (2 == DotCount) {
						int l = ResultBuffer.length();
						// ..前锟斤拷锟斤拷锟斤拷锟�锟斤拷锟斤拷效
						if (lastTokenPos + 1 == l) {
							if (l >= 2 && lastTokenPos > 0) {
								ResultBuffer.delete(prevDirPos - 1, l);
							}
						}
					}
					if (0 == TokenCount) {
						ResultBuffer.append(ch);
						prevDirPos = lastTokenPos;
						lastTokenPos = ResultBuffer.length();
					}
					TokenCount++;
				} else {
					ResultBuffer.append(ch);

				}
				DotCount = 0;
				break;
			case '\\':
				DotCount = 0;
				TokenCount = 0;
				break;
			case '\n':
				DotCount = 0;
				TokenCount = 0;
				break;
			case '\r':
				DotCount = 0;
				TokenCount = 0;
				break;
			case '\t':
				DotCount = 0;
				TokenCount = 0;
				break;
			default:
				DotCount = 0;
				TokenCount = 0;
				ResultBuffer.append(ch);
				break;
			}
		}
		return ResultBuffer.toString();

	}

	/**
	 * 去锟斤拷URL锟叫碉拷://.../前锟斤拷锟斤拷址锟皆拷锟斤拷锟組MS9锟斤拷锟斤拷锟斤拷为锟角的斤拷协锟斤拷锟斤拷锟阶拷锟斤拷锟皆
	 * 拷锟斤拷锟斤拷卸瞎锟斤拷锟斤拷锟斤拷执锟斤拷锟�
	 * 
	 * @param sURL
	 *            like rtsp://serverIp:port/dir1/dir2/dir3/movie.wmv
	 * @return result of /dir1/dir2/dir3/movie.wmv
	 */
	public static String getClearURL(String sURL) {
		// 锟揭碉拷"://"锟斤拷锟斤拷锟斤拷前锟斤拷牟锟斤拷锟饺ワ拷锟�
		int iPos = sURL.indexOf("://");
		int iLen = sURL.length();
		if (iPos >= 0) {// 锟斤拷锟斤拷
			if (iPos + 4 < iLen) { // 锟角否超筹拷锟斤拷围锟斤拷
				sURL = sURL.substring(iPos + 4); // 锟斤拷锟�//锟斤拷直锟接革拷锟狡猴拷锟斤拷锟�
				iPos = sURL.indexOf("/"); // 锟揭碉拷/
				if (iPos >= 0) { // OK
					sURL = sURL.substring(iPos);
				}
			}
			return sURL;
		} else {
			// 没锟斤拷锟揭碉拷"://",锟斤拷锟斤拷原锟斤拷锟斤拷锟斤拷锟斤拷
			return sURL;
		}
	}

	/**
	 * 锟斤拷锟狡讹拷锟斤拷URL锟叫伙拷取queryString
	 * 
	 * @param URL
	 *            锟矫伙拷锟斤拷锟斤拷锟経RL
	 * @param ParameterName
	 *            锟斤拷锟饺★拷牟锟斤拷锟斤拷锟�
	 * @return sResult 锟斤拷锟饺★拷牟锟斤拷锟街�
	 */
	public static String getParameter(String URL, String ParameterName) {
		int ParameterIndex, URLLength;
		// 锟斤拷取URL锟侥筹拷锟斤拷
		URLLength = URL.length();
		StringBuffer Result;
		// 锟皆猴拷锟斤拷牡锟揭伙拷锟轿拷锟斤拷锟斤拷锟斤拷荨锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷一锟斤拷锟斤拷锟斤拷诤锟斤拷妫拷俣然锟斤拷一锟斤拷悖拷锟斤拷猓琸ey锟较讹拷锟斤拷锟斤拷锟斤拷妫�
		// 锟斤拷使锟斤拷锟斤拷锟斤拷应锟矫诧拷锟斤拷牟锟斤拷锟斤拷突锟斤拷锟斤拷锟角碉拷锟斤拷锟斤拷锟饺伙拷锟斤拷锟斤拷锟饺凤拷锟饺�
		ParameterIndex = URL.lastIndexOf(ParameterName + "=");
		// 锟斤拷锟斤拷也锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		if (ParameterIndex < 0) {
			return null;
		}
		// 锟斤拷始锟斤拷锟斤拷锟斤拷StringBuffer锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷目锟斤拷锟角ｏ拷锟劫度伙拷锟揭伙拷锟�
		Result = new StringBuffer();
		char ch;
		// 循锟斤拷锟斤拷锟窖诧拷锟斤拷锟斤拷拥锟斤拷锟斤拷锟街碉拷锟斤拷妗Ｑ拷锟斤拷锟绞贾狄拷锟揭伙拷模锟阶拷狻�
		for (int i = ParameterIndex + ParameterName.length() + 1; i < URLLength; i++) {
			// 锟斤拷取ch锟斤拷锟斤拷锟斤拷值锟斤拷一锟斤拷锟斤拷
			ch = URL.charAt(i);
			// 锟斤拷锟斤拷锟�锟斤拷锟斤拷锟斤拷为锟窖撅拷锟斤拷锟斤拷
			if (ch == '&') {
				break;
			}
			// 锟斤拷锟斤拷锟絚har锟脚碉拷锟斤拷锟斤拷尾锟斤拷
			Result.append(ch);
		}
		// 锟斤拷锟截斤拷锟�
		return Result.toString();
	}

	public static boolean checkEmail(String email) {
		// String regex =
		// "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		// String regex = "^\\w+@([^@]+\\.)[a-zA-Z]{2,3}$";
		// /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/
		if (email == null)
			return false;
		String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	// public static void main(String[] args){
	// Pattern p =
	// Pattern.compile("http://([w-]+.)+[w-]+(/[w-./?%&=]*)?",Pattern.CASE_INSENSITIVE
	// );
	// // Pattern p =
	// Pattern.compile("^((http|ftp)://)?(((([d]+.)+){3}[d]+(/[w./]+)?)|([a-z]w*((.w+)+){2,})([/][w.~]*)*)$",Pattern.CASE_INSENSITIVE
	// );
	// Matcher m =
	// p.matcher("http://www.qqgb.com/Program/Java/JavaFAQ/JavaJ2SE/Program_146959.html");
	//
	// if(m.find()){
	// System.out.println(m.group());
	// }
	//
	// m = p.matcher("wwws://baike.baidu.com/view/230199.htm?fr=ala0_1");
	//
	// if(m.find()){
	// System.out.println(m.group());
	// }
	//
	// m =
	// p.matcher("http://www.google.cn/gwt/x?u=http%3A%2F%2Fanotherbug.blog.chinajavaworld.com%2Fentry%2F4550%2F0%2F&btnGo=Go&source=wax&ie=UTF-8&oe=UTF-8");
	//
	// if(m.find()){
	// System.out.println(m.group());
	// }
	//
	// m =
	// p.matcher("http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go");
	//
	// if(m.find()){
	// System.out.println(m.group());
	// }
	//
	// }

	/**
	 * 判断字符串是否为空，为空返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty()) ? true : false;
	}
	
	/** 
     * 判断str1中包含str2的个数 
      * @param str1 
     * @param str2 
     * @return counter 
     */  
    public static int countStr(String str1, String str2, int counter) {  
    	if (str1.indexOf(str2) == -1) {
        	return counter;
        } else if (str1.indexOf(str2) != -1) {
        	return countStr(str1.substring(str1.indexOf(str2) + str2.length()), str2, ++counter);
        }
    	return counter;
    }
    
    public static boolean isIP(String ip) {
    	if (ip != null && !ip.isEmpty()) {
    		 String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                     	  + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                     	  + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                     	  + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    		 if (ip.matches(regex)) 
    			 return true;
    	}
    	return false;
    }
    private  static final char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789".toCharArray();
    public static StringBuffer getStringCode(int length){
    	Random r = new Random();
    	StringBuffer sb = new StringBuffer();
    	int index=0;
    	int len = ch.length;
    	for (int i = 0; i < length; i ++) {
    		index = r.nextInt(len);
    		sb.append(ch[index]);
    	}
    	return sb;
	}
    
    /**
     * 获取主域名
     * @param domain
     * @return
     */
    public static String getMainDomain(String domain) {
    	if (domain == null || "".equals(domain.trim()))
    		return  "";
    	domain = domain.trim();
		if (domain.startsWith("http://", 0))
			domain = domain.replace("http://", "");
		if (domain.startsWith("www.", 0))
			domain = domain.replace("www.", "");
		if (domain.indexOf("?") != -1)
			domain = domain.substring(0, domain.indexOf("?"));
		if (domain.indexOf("/") != -1)
			domain = domain.substring(0, domain.indexOf("/"));
		if (domain == null || "".equals(domain.trim())) return "";
		if (isIP(domain)) return domain;
		return getMain(domain);
    }
    private static String getMain(String domain) {
    	if (StringUtil.isEmpty(domain) || "".equals(domain.trim()))
			return "";
		int count = countDian(domain);
		if (count == 0) return "";
		else if (count == 1) return domain;
		else if(count == 2) {	//com.cn|gov.cn|net.cn|org.cn|cn.com|ac.cn|com.sb|org.me|org.uk|co.nz|co.uk|co.il|co.in|com.hk|com.tw |me.uk
			if (domain.indexOf("com.cn")!=-1 || domain.indexOf("gov.cn") != -1 || domain.indexOf("net.cn") != -1 ||
					domain.indexOf("org.cn") != -1 || domain.indexOf("cn.com") != -1 || domain.indexOf("ac.cn") != -1 ||
					domain.indexOf("com.sb") != -1 || domain.indexOf("org.me") != -1 || domain.indexOf("org.uk") != -1 ||
					domain.indexOf("co.nz") != -1 || domain.indexOf("co.uk") != -1 || domain.indexOf("co.il") != -1 ||
					domain.indexOf("co.in") != -1 || domain.indexOf("com.hk") != -1 || domain.indexOf("com.tw") != -1 || domain.indexOf("me.uk") != -1 ||
					domain.indexOf("ac.cn") != -1 || domain.indexOf("bj.cn") != -1 || domain.indexOf("sh.cn") != -1 || domain.indexOf("tj.cn") != -1 || domain.indexOf("cq.cn") != -1 || 
					domain.indexOf("he.cn") != -1 || domain.indexOf("sx.cn") != -1 || domain.indexOf("nm.cn") != -1 || domain.indexOf("ln.cn") != -1 || domain.indexOf("jl.cn") != -1 || 
					domain.indexOf("hl.cn") != -1 || domain.indexOf("js.cn") != -1 || domain.indexOf("zj.cn") != -1 || domain.indexOf("ah.cn") != -1 || domain.indexOf("fj.cn") != -1 ||
					domain.indexOf("jx.cn") != -1 || domain.indexOf("sd.cn") != -1 || domain.indexOf("ha.cn") != -1 || domain.indexOf("hb.cn") != -1 || domain.indexOf("hn.cn") != -1 ||
					domain.indexOf("gd.cn") != -1 || domain.indexOf("gx.cn") != -1 || domain.indexOf("sc.cn") != -1 || domain.indexOf("gz.cn") != -1 || domain.indexOf("yn.cn") != -1 ||
					domain.indexOf("gs.cn") != -1 || domain.indexOf("qh.cn") != -1 || domain.indexOf("nx.cn") != -1 || domain.indexOf("xj.cn") != -1 || domain.indexOf("tw.cn") != -1 ||
					domain.indexOf("hk.cn") != -1 || domain.indexOf("mo.cn") != -1 || domain.indexOf("xz.cn") != -1 || domain.indexOf("hi.cn") != -1 || domain.indexOf("sn.cn") != -1)
				return domain;
			else return getMain(domain.substring(domain.indexOf(".")+1, domain.length()));
		}
		else return getMain(domain.substring(domain.indexOf(".")+1, domain.length()));
    }
    private static int countDian(String line) {
		if (StringUtil.isEmpty(line) || "".equals(line.trim()))
			return 0;
		if (line.indexOf(".") != -1) {
			return 1+countDian(line.substring(line.indexOf(".")+1, line.length()));
		}
		return 0;
	}
}
