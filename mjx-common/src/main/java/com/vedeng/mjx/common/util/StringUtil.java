package com.vedeng.mjx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 字符串工具类
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/25
 */
public class StringUtil {

    /**
     * null
     */
    static final String JS_PARMA_NULL = "null";

    /**
     * nan
     */
    static final String JS_PARMA_NAN = "nan";

    /**
     * undefined
     */
    static final String JS_PARMA_UNDEFINED = "undefined";

    /**
     *
     * <b>Description: 字符串替换</b><br>
     * @param repaceStr  替换字符串
     * @param oldStr	 替换字段
     * @param newStr    新字段
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年12月15日 上午8:53:03 </b>
     */
    public static String repaceAll(String repaceStr, String oldStr, String newStr)
    {
        // 替换字符串
        if(null == repaceStr)
        {
            return null;
        }
        // 替换字段为null则默认空字符串
        if(null == oldStr)
        {
            oldStr = "";
        }
        // 新字段为null则默认空字符串
        if(null == newStr)
        {
            newStr = "";
        }
        int index = repaceStr.indexOf(oldStr);
        // 存在 oldStr
        if(index != -1)
        {
            repaceStr = repaceStr.substring(0, index) + newStr + repaceStr.substring(index + oldStr.length(), repaceStr.length());
            return repaceAll(repaceStr, oldStr, newStr);
        }
        return repaceStr;
    }

    /**
     *
     * <b>Description: 空判断</b><br>
     * @param str
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年12月15日 上午8:53:03 </b>
     */
    public static boolean isBlank(String str) {
        if(null == str || str.trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**
     *
     * <b>Description: 非空判断</b><br>
     * @param str
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年06月25日 上午8:53:03 </b>
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     *
     * <b>Description: js空判断</b><br>
     * @param str
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年07月3日 </b>
     */
    public static boolean isJsBlank(String str) {
        if(isBlank(str)) {
            return true;
        }
        // js 页面 的 null, undefined
        if(JS_PARMA_NULL.equals(str.toLowerCase()) || JS_PARMA_UNDEFINED.equals(str.toLowerCase()) || JS_PARMA_NAN.equals(str.toLowerCase())) {
            return true;
        }

        return false;
    }

    /**
     *
     * <b>Description: js非空判断</b><br>
     * @param str
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2019年07月3日 </b>
     */
    public static boolean isNotJsBlank(String str) {
        return !isJsBlank(str);
    }
    public static  Map<Pattern, String> loadPattern(String conf) {
        if (conf == null || "".equals(conf)) {
            return Collections.emptyMap();
        }
        Map<Pattern, String> map = new HashMap<Pattern, String>();
        String[] includes = conf.split(";");
        for (int i = 0; i < includes.length; i++) {
            if (conf == null || "".equals(conf)) {
                continue;
            }
            String[] include = includes[i].split(":");
            Pattern pInclude = Pattern.compile(include[0]);
            if (include.length == 2) {
                map.put(pInclude, include[1]);
            } else {
                map.put(pInclude, "1");
            }
        }
        return map;
    }

    public static  boolean match(List<Pattern> list, String s) {
        if (s == null || list == null || list.size() == 0) {
            return false;
        } else {
            for (int i = 0; i < list.size(); i++) {
                Pattern pInclude = list.get(i);
                Matcher matcher = pInclude.matcher(s);
                if (matcher.matches()) {
                    return true;
                }
            }
            return false;
        }
    }
}
