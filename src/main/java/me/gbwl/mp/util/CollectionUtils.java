package me.gbwl.mp.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class CollectionUtils {
	/**
	 * 非常蛋疼的方法！
	 * 将一个list切割成多个长度为count的小list！
	 * @param oraList 原list
	 * @param count 期望获得的小list 的长度
	 * @return
	 */
	public static <E> List<List<E>> splitList(List<E> oraList,int count){
		List<List<E>> values=new ArrayList<List<E>>();
		if(oraList==null||oraList.isEmpty()||count<1){
			values.add(oraList);
			return values;
		}
		int i=0;
		List<E> value=null;
		for(;i<oraList.size();i++){
			if(i%count==0){
				value=new ArrayList<E>();
				values.add(value);
			}
			value.add(oraList.get(i));
		}
		return values;
	}
	/**
	 * 将字符串按分隔符转成list
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static List<Integer> toIntList(String str,String separatorChars){
		List<Integer> list = new ArrayList<Integer>();
		String[] strs=StringUtils.split(str,separatorChars);
		for(String s:strs){
			list.add(Integer.valueOf(s));
		}
		return list;
	}
	/**
	 * 将字符串按分隔符转成list
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static  List<String> toStringList(String str,String separatorChars){
		List<String> list = new ArrayList<String>();
		String[] strs=StringUtils.split(str,separatorChars);
		for(String s:strs){
			list.add(s);
		}
		return list;
	}
	/**
	 * 将字符串按分隔符转成Set
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static Set<Integer> toIntSet(String str, String separatorChars) {
		Set<Integer> set=new HashSet<Integer>();
		String[] strs=StringUtils.split(str,separatorChars);
		for(String s:strs){
			set.add(Integer.valueOf(s));
		}
		return set;
	}
	/**
	 * 将字符串按分隔符转成Set
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static Set<String> toStringSet(String str, String separatorChars) {
		Set<String> set=new HashSet<String>();
		String[] strs=StringUtils.split(str,separatorChars);
		for(String s:strs){
			set.add(s);
		}
		return set;
	}
	/**
	 * 更蛋疼的方法！<br/>
	 * 集合list1减去集合list2<br/>
	 * 返回值 中位置0的list为list1中包含但list2中不包含的元素<br/>
	 * 位置1的list为list1和list2共同拥有的元素<br/>
	 * 位置2的list为list2中包含但list1中不包含的元素<br/>
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <E> List<List<E>> reduce(List<E> list1,List<E> list2){
		List<E> list1cp = new ArrayList<E>();
		List<E> list2cp = new ArrayList<E>();
		List<E> list3cp = new ArrayList<E>();
		List<List<E>> valueList=new ArrayList<List<E>>();
		list1cp.addAll(list1);
		list2cp.addAll(list2);
		list3cp.addAll(list1);
		list1cp.removeAll(list2);
		valueList.add(list1cp);
		list3cp.retainAll(list2);
		valueList.add(list3cp);
		list2cp.removeAll(list1);
		valueList.add(list2cp);
		return valueList;
	}
	/**
	 * 将enum的key转成List
	 * @param enume
	 * @return
	 */
	public static List<String> enum2List(@SuppressWarnings("rawtypes") Enumeration enume){
		List<String> list=new ArrayList<String>();
		while(enume.hasMoreElements()){
			list.add(enume.nextElement().toString());
		}
		return list;
	}
	
	public static <K> List<K> mapKeyList(Map<K,?> map){
		List<K> list = new ArrayList<K>();
		if(map==null)return list;
		for(Entry<K,?> e:map.entrySet()){
			list.add(e.getKey());
		}
		return list;
	}
}
