package com.vedeng.mjx.web.controller.message.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * json文件工具
 * 
 * @author liuliang
 * 
 */
public class JsonUtil {
	
	private final static ObjectMapper instance =  new ObjectMapper();
	public static ObjectMapper getInstance() {
		return instance;
	}
	
	public static String getJsonDefault(Object obj,String strDefault){
		String result = "";
		if (obj != null){
			if (obj instanceof JSONNull) {
				result = strDefault;
			} else {
				if (obj instanceof Integer) {
					result = StringUtils.trimToEmpty(String.valueOf((Integer)obj));
				} else if (obj instanceof String) {
					result = StringUtils.trimToEmpty((String)obj);
				} else if (obj instanceof Double) {
					result = StringUtils.trimToEmpty(String.valueOf((Double)obj));
				} else if (obj instanceof Float) {
					result = StringUtils.trimToEmpty(String.valueOf((Float)obj));
				}else {
					result = JsonUtil.getJSONString(obj);
				}
			}
		} else {
			result = strDefault;
		}
		return result;
	}
	
	
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (null == v)
				continue;
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	public static String getJSONString(Object object) {
		String jsonString = null;
		// 日期值处理器
		JsonConfig jsonConfig = new JsonConfig();
		
		//jsonConfig.registerJsonValueProcessor(java.util.Date.class,
		//	new JsonDateValueProcessor());
		//jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,	new JsonDateValueProcessor());
		//		System.out.println(String.class);
		
		//jsonConfig.registerJsonPropertyNameProcessor(object.getClass(), new JsonLowCaseKeyProcessor());
		
		if (object != null) {
			//			QJson.getDTOArray(jsonString, clazz)
			if (object instanceof Collection || object instanceof Object[]) {
				jsonString = JSONArray.fromObject(object,jsonConfig).toString();
			}else {
				jsonString = JSONObject.fromObject(object,jsonConfig).toString();
			}
		}
		return jsonString == null ? "{}" : jsonString;
	}

	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getMapByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转换成JSON字符串
	 * 
	 * @param obj
	 *            需要转换的对象
	 * @return 对象的string字符
	 */
	public static String toJson(Object obj) {
		JSONObject jSONObject = JSONObject.fromObject(obj);
		return jSONObject.toString();
	}

	/**
	 * JSON字符串转换成对象
	 * 
	 * @param jsonString
	 *            需要转换的字符串
	 * @param type
	 *            需要转换的对象类型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, Class<T> type) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return (T) JSONObject.toBean(jsonObject, type);
	}

	/**
	 * 将JSONArray对象转换成list集合
	 * 
	 * @param jsonArr
	 * @return
	 */
	public static List<Object> jsonToList(JSONArray jsonArr) {
		List<Object> list = new ArrayList<Object>();
		for (Object obj : jsonArr) {
			if (obj instanceof JSONArray) {
				list.add(jsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				list.add(jsonToMap((JSONObject) obj));
			} else {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * 将json字符串转换成map对象
	 *
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		JSONObject obj = JSONObject.fromObject(json);
		return jsonToMap(obj);
	}

	/**
	 * 将JSONObject转换成map对象
	 *
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JSONObject obj) {
		Set<?> set = obj.keySet();
		Map<String, Object> map = new HashMap<String, Object>(set.size());
		for (Object key : obj.keySet()) {
			Object value = obj.get(key);
			if (value instanceof JSONArray) {
				map.put(key.toString(), jsonToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				map.put(key.toString(), jsonToMap((JSONObject) value));
			} else {
				map.put(key.toString(), obj.get(key));
			}

		}
		return map;
	}

}
