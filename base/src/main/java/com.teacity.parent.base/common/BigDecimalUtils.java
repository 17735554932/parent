package com.teacity.parent.base.common;

import java.math.BigDecimal;

/**
 * 提供精确的加减乘除运算
 *
 * @Author:chenssy
 * @date:2014年9月15日
 */
public class BigDecimalUtils {

	/**
	 * 默认保留位：2
	 */
	private static int DEFAULT_SCALE = 2;

	/**
	 * 默认四舍五入规则为：向上舍入
	 */
	private static int DEFAULT_ROUND = BigDecimal.ROUND_DOWN;
	//private static int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;

	/**
	 * 
	 * 加法运算
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 *            加数
	 * @param v2
	 *            被加数
	 * @return
	 */
	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}
	/**
	 *
	 * 加法运算
	 *
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 *            加数
	 * @param v2
	 *            被加数
	 * @return
	 */
	public static BigDecimal add2(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1-v2
	 * 
	 * @return 两个参数的差
	 */
	public static double sub(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2).doubleValue();
	}
	
	public static BigDecimal sub2(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法运算<br>
	 * 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 *            除数
	 * @param v2
	 *            被除数
	 * @param scale
	 *            精确精度
	 * @return
	 */
	public static String div(String v1, String v2, int scale, int round) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		if (ValidateHelper.isEmpty(scale)) {
			scale = DEFAULT_SCALE;
		}

		if (ValidateHelper.isEmpty(round)) {
			round = DEFAULT_ROUND;
		}

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).toString();
		//return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 
	 * @author: 米世廷
	 **
	 * @param b1
	 *            除数
	 * @param b2
	 *            被除数
	 */
	public static BigDecimal div(BigDecimal b1, Integer b2) {
		BigDecimal bigDecimal = new BigDecimal(b2 + "");
		return b1.divide(bigDecimal, 2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 比较两个数<br>
	 * v1 > v2 return 1<br>
	 * v1 = v2 return 0<br>
	 * v1 < v2 return -1
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 *            比较数
	 * @param v2
	 *            被比较数
	 * @return
	 */
	public static int compareTo(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 * 比较两个数<br>
	 * v1 > v2 return 1<br>
	 * v1 = v2 return 0<br>
	 * v1 < v2 return -1
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param b1
	 *            比较数
	 * @param b2
	 *            被比较数
	 * @return
	 */
	public static int compareTo(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2);
	}

	/**
	 * 返回较小数
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMin(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.min(b2).toString();
	}

	/**
	 * 返回较大数
	 * 
	 * @autor:chenssy
	 * @date:2014年9月15日
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String returnMax(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.max(b2).toString();
	}

	/**
	 * 处理BigDecimal数据，保留scale位小数
	 * 
	 * @author:chenssy
	 * @date:2014年10月21日
	 *
	 * @param value
	 * @param scale
	 * @return
	 */
	public static BigDecimal getValue(BigDecimal value, int scale) {
		if (!ValidateHelper.isEmpty(value)) {
			return value.setScale(scale, BigDecimal.ROUND_DOWN);
			//return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return value;
	}

	/**
	 * 将object转换为Bigdecimal
	 * 
	 * @author:chenssy
	 * @date:2014年10月17日
	 *
	 * @param value
	 *            待转换的数值
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal resultValue = new BigDecimal(0);
		if (value instanceof String) {
			resultValue = new BigDecimal((String) value);
		} else if (value instanceof Integer) {
			resultValue = new BigDecimal((Integer) value);
		} else if (value instanceof Long) {
			resultValue = new BigDecimal((Long) value);
		} else if (value instanceof Double) {
			resultValue = new BigDecimal((Double) value);
		} else {
			resultValue = (BigDecimal) value;
		}

		return resultValue;
	}

	/**
	 * 将object转换为Bigdecimal,若object为空，则返回resultValue
	 * 
	 * @autor:chenssy
	 * @date:2014年9月20日
	 *
	 * @param value
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object value, BigDecimal resultValue) {
		if (ValidateHelper.isEmpty(value)) {
			return resultValue;
		}

		resultValue = getBigDecimal(resultValue);

		return resultValue;
	}

	/**
	 * 将BigDecimal 转换成Long
	 * 
	 * @autor:chenssy
	 * @date:2014年9月20日
	 *
	 * @param value
	 * @return
	 */
	public static Long bigDecimalToLong(BigDecimal value) {
		if (value != null) {
			return new Long(value.longValue());
		}
		return null;
	}

	/**
	 * 将BigDecimal 转换成integer
	 * 
	 * @autor:huangc
	 * @date:2014年9月20日
	 *
	 * @param value
	 * @return
	 */
	public static Integer bigDecimalToInteger(BigDecimal value) {
		if (value != null) {
			return new Integer(value.intValue());
		}
		return null;
	}

	public static BigDecimal installmentTotalinterest(BigDecimal installmentmoney, BigDecimal appDayRate,
			Integer Installmentsum) {
		// 总利息= 总额*月利率*期数)
		// 总额*日利率
		// *总的天数 == 天数*期数.
		BigDecimal bigDecimal = installmentmoney.multiply(appDayRate).multiply(new BigDecimal(Installmentsum + ""));
		return bigDecimal;
	}

	/**
	 * //总利息= 总额*月利率*期数) 写死了。
	 * 
	 * @author: 米世廷
	 * @createTime: 2017年7月16日 下午5:58:54
	 * @history:
	 * @param installmentmoney
	 * @param Installmentsum
	 * @return BigDecimal
	 */
	public static BigDecimal installmentTotalinterest(BigDecimal installmentmoney, Integer Installmentsum) {
		// 总利息= 总额*月利率*期数)
		// 总额*日利率
		// *总的天数 == 天数*期数.
		BigDecimal bigDecimal = installmentmoney.multiply(new BigDecimal("0.015"))
				.multiply(new BigDecimal(Installmentsum + ""));
		return bigDecimal;
	}

	/**
	 * 处理BigDecimal数据，保留2位小数 截取不四舍五入 如果null 则返回 "" 金额专用
	 * 
	 * @author: 隗功旭
	 * @createTime: 2017年7月23日 下午9:55:07
	 * @history:
	 * @param value
	 * @return String
	 */
	public static String getMoneyValue(BigDecimal value) {
		if (!ValidateHelper.isEmpty(value)) {
			return value.setScale(2, BigDecimal.ROUND_DOWN).toString();
		}
		return "0.00";
	}
	public static String getMoneyValue2(BigDecimal value) {
		if (!ValidateHelper.isEmpty(value)) {
			return value.setScale(4, BigDecimal.ROUND_DOWN).toString();
		}
		return "0.00";
	}

	/**
	 * 处理BigDecimal数据，不保留小数 截取不四舍五入 如果null 则返回 "" 金额专用
	 * 
	 * @author: 隗功旭
	 * @createTime: 2017年7月23日 下午9:55:07
	 * @history:
	 * @param value
	 * @return String
	 */
	public static String getMoneyValue_0(BigDecimal value) {
		if (!ValidateHelper.isEmpty(value)) {
			return value.setScale(0, BigDecimal.ROUND_DOWN).toString();
		}
		return "0";
	}

	/**
	 * 提供精确的除法运算方法div
	 * @param value1 被除数                                   value1/value2 
	 * @param value2 除数
	 * @param scale 精确范围
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal value1, BigDecimal value2, int scale){
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
			scale=2;
		}
		return value1.divide(value2, scale,BigDecimal.ROUND_DOWN);
		//return value1.divide(value2, scale,BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal div_DOWN(BigDecimal value1, BigDecimal value2, int scale){
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
			scale=2;
		}
		return value1.divide(value2, scale,BigDecimal.ROUND_DOWN);
	}
	
	
	public static boolean isPoss(BigDecimal money){
		if (compareTo(money, new BigDecimal("50000")) ==1) {
			return true;
		}
		if (compareTo(money, new BigDecimal("1"))==-1) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(isPoss(new BigDecimal("50001")));
	}
	/**
	 * 计算随机金额
	 * @param min
	 * @param max
	 * @return
	 */
	public static BigDecimal  getRandomMoney(double min,double max) {
		double v = (Math.random() * 10) + Math.random();
		if(v >= max){
			v = max;
		}
		if(v <= min){
			v=min;
		}
		return BigDecimalUtils.getValue(new BigDecimal(v),2);
	}
	/**
	 * money : 金额   fee : 费率
	 * @param money
	 * @param rate
	 * @return
	 */
	public static BigDecimal  getFeeOtherMoney(BigDecimal money,BigDecimal rate) {
		BigDecimal fee = money.multiply(rate);
		BigDecimal feeOther = money.subtract(fee);
		return BigDecimalUtils.getValue(feeOther,2);
	}
	
	
}
