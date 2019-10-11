
package com.vedeng.mjx.common.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Description: 正则表达工具类</b><br> 
 * <b>Author: Franlin.wu</b> 
 * 
 * <br><b>Date: 2018年11月26日 上午11:08:18 </b> 
 *
 */
public class RegularExpressionUtil {
	/**
	 * 是否是数字
	 * 带小数点
	 */
	public static final String REG_IS_NUMBER_AND_POINT = "^-?\\d+(\\.\\d+)?$";
	
	/**
	 * 是否是数字 
	 * 纯数字
	 */
	public static final String REG_IS_NUMBER = "^\\d+$";
	
	/**
	 * 手机号码的正则验证规则
	 */
	public static final String MOBILE_REGULAR = "^1[\\d]{10}";
	
	/**
	 * 
	 * <b>Description: 判断是否是数字[默认是纯数字]</b><br> 
	 * @param str
	 * @param reg  [^\\d+$]
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年11月26日 上午11:12:05 </b>
	 */
	public static boolean isNumeric(String str, String reg)
	{
		if(null == str)
		{
			return false;
		}
		// 正则表达式为空
		if(null == reg || reg.trim().length() == 0)
		{
			// 默认纯数字
			reg = REG_IS_NUMBER;
		}
		return isRegular(str, reg);
	}

	/**
	 * 
	 * <b>Description: 正则表达判断</b><br> 
	 * @param str  [校验值]
	 * @param reg  [正则表达式]
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年11月26日 下午1:42:34 </b>
	 */
	public static boolean isRegular(String str, String reg)
	{
		// 都为null
		if(null == str || null == reg)
		{
			return false;
		}
		// 正则表达式
		Pattern pattern = Pattern.compile(reg);
		// 校验的值
		Matcher isReg = pattern.matcher(str);
		// 判断
		return isReg.matches();
	}
	
	/**
	 * 校验手机号码
	 * <b>Description:</b>
	 * @param mobiles
	 * @return boolean
	 * @Note
	 * <b>Author：</b> bill.bo
	 * <b>Date:</b> 2018年12月17日 下午3:38:01
	 */
	public static boolean isMobileNO(String mobiles){
		// 判断是否为空
		if(StringUtil.isBlank(mobiles)){
			return false;
		}
		// 正则表达式
		Pattern pattern = Pattern.compile(MOBILE_REGULAR);
		// 校验值
		Matcher matcher = pattern.matcher(mobiles);
		return matcher.matches();
	}
}

