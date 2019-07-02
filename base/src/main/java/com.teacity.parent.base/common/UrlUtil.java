package com.teacity.parent.base.common;


import java.io.Serializable;

public class UrlUtil implements Serializable {

	
	public static String headOld = "http://www.xinyongpay.net";
	public static String head = "http://test.jinhuayurun.com";
	/**
	 * 单url查询时使用此方法
	 * @param path
	 * @return
	 */
	public static String getUrl(String path) {
		if(null==path) {
			return "";
		}
		if(!path.contains("/")) {
			return path;
		}
		if(path.contains(headOld)) {
			path = path.replace(headOld, "");
		}
		if(path.contains(head)) {
			return path;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(head).append(path);
		return sb.toString();
	}
	/**
	 * 当path为多个url拼接  返回前端时
	 * @param path
	 * @return
	 */
	public static String getMoreUrl(String path) {
		if(null==path) {
			return "";
		}
		if(!path.contains("/")) {
			return path;
		}
		if(path.contains(headOld)) {
			path = path.replace(headOld, "");
		}
		String[] urls = path.split(",");
		StringBuffer sb = new StringBuffer();
		for (String string : urls) {
			if(string.contains(head)) {
				sb.append(string).append(",");
			}else {
				sb.append(head).append(string).append(",");
			}
		}
		return sb.toString();
	}
	/**
	 * 操作数据库URL时使用此方法
	 * @param path
	 * @return
	 */
	public static String getUpdateUrl(String path) {
		if(null==path) {
			return null;
		}
		if(!path.contains("/")) {
			return path;
		}
		if(path.contains(head)) {
			return path.replace(head, "");
		}
		if(path.contains(headOld)) {
			return path.replace(headOld, "");
		}
		return path;
	}
	/**
	 * 当操作数据库的URL为多个拼起来保存在单字段内时
	 * @param path
	 * @return
	 */
	public static String getUpdateMoreUrl(String path) {
		if(null==path) {
			return null;
		}
		if(!path.contains("/")) {
			return path;
		}
		String[] urls = path.split(",");
		StringBuffer sb = new StringBuffer();
		for (String string : urls) {
			if(string.contains(head)) {
				sb.append(string.replace(head, "")).append(",");
			}else if(string.contains(headOld)) {
				sb.append(string.replace(headOld, "")).append(",");
			}else {
				sb.append(string).append(",");
			}
		}
		return sb.toString();
	}

	public static String urlSql(String urlColunm) {
		String urlSql=" CONCAT(" +
				"if("+urlColunm+" is null or "+urlColunm+" = '','','http://test.jinhuayurun.com/')," +
				"SUBSTRING_INDEX("+urlColunm+", '/' ,- 5)" +
				") ";
		return urlSql;
	}
}
