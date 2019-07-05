/**
 * Copyright (c) 2015-2016, wangff Yang 王飞飞 (397605584@qq.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xyf.platform.base.util;

import com.jfinal.core.JFinal;
import com.jfinal.log.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Log log = Log.getLog(StringUtils.class);
    /**
     * 字符串开始位置
     */
    public static final Integer START_POINT = 0;

    public static String Cny2fen(String cnyNum) {
        String returncny = "0";
        if ((cnyNum != null) && (cnyNum.length() > 0))
        {
            returncny = ""+(BigDecimal.valueOf(Double.parseDouble(cnyNum)).multiply(new BigDecimal(100))).intValue();

        }

        System.out.println("cnyNum===" + returncny);
        return returncny;
    }

    public static void main(String[] args){
        System.out.println(hideFootStr("斯蒂芬",1));
//        System.out.println(Cny2fen("1.01"));
    }

    public static Double Fen2Cny(String cnyNum) {
        double returncny = 0;
        if ((cnyNum != null) && (cnyNum.length() > 0))
        {
            returncny = BigDecimal.valueOf(Double.parseDouble(cnyNum)).divide(new BigDecimal(100)).doubleValue();
        }
        return returncny;
    }
    public static String urlDecode(String string) {
        try {
            return URLDecoder.decode(string, JFinal.me().getConstants().getEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("urlDecode is error", e);
        }
        return string;
    }

    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, JFinal.me().getConstants().getEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("urlEncode is error", e);
        }
        return string;
    }

    public static String urlRedirect(String redirect) {
        try {
            redirect = new String(redirect.getBytes(JFinal.me().getConstants().getEncoding()), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            log.error("urlRedirect is error", e);
        }
        return redirect;
    }

    public static boolean areNotEmpty(String... strings) {
        if (strings == null || strings.length == 0)
            return false;

        for (String string : strings) {
            if (string == null || "".equals(string)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.equals("");
    }

    public static boolean areNotBlank(String... strings) {
        if (strings == null || strings.length == 0)
            return false;

        for (String string : strings) {
            if (string == null || "".equals(string.trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String string) {
        return string != null && !string.trim().equals("");
    }

    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    public static long toLong(String value, Long defaultValue) {
        try {
            if (value == null || "".equals(value.trim()))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Long.parseLong(value.substring(1));
            return Long.parseLong(value);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static int toInt(String value, int defaultValue) {
        try {
            if (value == null || "".equals(value.trim()))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Integer.parseInt(value.substring(1));
            return Integer.parseInt(value);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static BigInteger toBigInteger(String value, BigInteger defaultValue) {
        try {
            if (value == null || "".equals(value.trim()))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return new BigInteger(value).negate();
            return new BigInteger(value);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static boolean match(String string, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static Integer toInteger(String str){
        int returni=0;
        int idx = str.lastIndexOf(".");
        String strNum = str.substring(0,idx);
        try {
            returni=Integer.parseInt(strNum);
        }catch (Exception e){
            e.printStackTrace();
        }
        return returni;
    }

    public static boolean isEmail(String email) {
        return Pattern.matches("\\w+@(\\w+.)+[a-z]{2,3}", email);
    }

    public static boolean isMobileNumber(String phoneNumber) {
        return Pattern.matches("^(1[3,5,7,8])\\d{9}$", phoneNumber);
    }

    public static String escapeHtml(String text) {
        if (isBlank(text))
            return text;

        return text.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;").replace("/", "&#x2F;");
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成流水号
     *
     * @param uuid 谋订单的主键ID
     * @return
     */
    public static String generateSerialNumber(String uuid) {
        return sdf.format(new Date()) + uuid.hashCode();
    }

    public static String clearSpecialCharacter(String string) {
        if (isBlank(string)) {
            return string;
        }

        /**
         P：标点字符；
         L：字母；
         M：标记符号（一般不会单独出现）；
         Z：分隔符（比如空格、换行等）；
         S：符号（比如数学符号、货币符号等）；
         N：数字（比如阿拉伯数字、罗马数字等）；
         C：其他字符
         */
//        return string.replaceAll("[\\pP\\pZ\\pM\\pC]", "");
        return string.replaceAll("[\\\\\'\"\\/\f\n\r\t]", "");
    }


    /**
     * 生成验证码
     */
    public static String getValidateCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999 - 1000 + 1) + 1000);//为变量赋随机值100000-999999
    }

    /**
     * 清除html标签
     *
     * @param htmlStr
     * @return
     */
    public static String clearHTMLTag(String htmlStr) {
        if (isBlank(htmlStr)) {
            return htmlStr;
        }
//        //定义HTML标签的正则表达式
//        String regEx_html="<[^>]+>";
//        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
//        Matcher m_html=p_html.matcher(htmlStr);
//        htmlStr=m_html.replaceAll(""); //过滤html标签
//        return htmlStr.trim();

        return JsoupUtils.getText(htmlStr);
    }
    /**
     * 隐藏掉开头 N 个字符 参数:data数据源, num 将隐藏的部分
     *
     * @return String 示例:"***abcdhijk"
     */
    public static String hideHeadStr(String data, int num) {
        StringBuffer hide = new StringBuffer();
        for (int i = 0; i < num; i++) {
            hide.append("*");
        }
        return hide.toString() + data.substring(num);
    }


    /**
     * 隐藏掉结尾 N 个字符 参数:data数据源, num 将隐藏的部分
     *
     * @return String 示例:"abcdhijk***"
     */
    public static String hideFootStr(String data, int num) {
        StringBuffer hide = new StringBuffer();
        for (int i = 0; i < num; i++) {
            hide.append("*");
        }
        return data.substring(0, data.length() - num) + hide.toString();
    }

    /**
     * 隐藏掉中间 N 个字符 参数:data数据源, num 将隐藏的部分, begin 开始隐藏的位置
     *
     * @return String 示例:"abcd***hijk"
     */
    public static String hideMiddleStr(String data, int num, int begin) {
        if (null == data || "".equals(data.trim())) {
            throw new RuntimeException("---------参数异常:" + data + "," + num
                    + "," + begin + "---------");
        }
        if ((begin - 1 > data.length() && begin - 1 < 0)
                || (num < 0 && num > data.length())
                || (num + begin - 1) > data.length()) {
            throw new RuntimeException("---------参数异常:data:"+data+",num:"+num+",begin:"+begin+"---------");
        }
        StringBuffer hide = new StringBuffer();
        for (int i = 0; i < num; i++) {
            hide.append("*");
        }
        return data.substring(0, begin - 1) + hide.toString()
                + data.substring((num + begin - 1), data.length());
    }
    /**
     * 截取字符串小数两位: PS:小数点位数大于两位,截取成两位
     *
     * @param oldStr
     * @return String 2018年5月16日
     */
    public static String strPointAfterTwo(final String oldStr) {
        if (StringUtils.isNotBlank(oldStr)) {
            return pointAfter3(oldStr) ? splitAfter(oldStr) : oldStr;
        }
        return !StringUtils.isNotBlank(oldStr) ? "0" : oldStr.trim();
    }
    /**
     * 获取小数点后两位的字符串
     *
     * @param oldStr
     * @return String 2018年5月17日
     */
    private static String splitAfter(final String oldStr) {
        final int indexOf = oldStr.indexOf('.');

        // 确定截取位置
        final int end = indexOf + 3;
        return oldStr.substring(START_POINT, end);

    }
    /**
     * 是否含小数点并小数点位数大于2位
     */
    private static boolean pointAfter3(final String oldStr) {
        return oldStr.contains(".") && oldStr.length() - oldStr.indexOf('.') - 1 > 3;
    }
}
