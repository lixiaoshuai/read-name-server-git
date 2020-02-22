package com.tuspass.realname.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by panhong on 2019/5/8 13:50
 */
public class MapUtil {
    /**
     * Map key 排序
     * @param map map
     * @return map
     */
    public static Map<String,String> order(Map<String, String> map){
        HashMap<String, String> tempMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(	map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }

    public static Map<String, Object> orderObject(Map<String, Object> map){
        HashMap<String, Object> tempMap = new LinkedHashMap();
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(map.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1,Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, Object> item = infoIds.get(i);
            Object value = item.getValue();
            if (value instanceof JSONObject) {
                value = orderObject(((JSONObject) value).getInnerMap());
            }
            tempMap.put(item.getKey(), value);
        }
        return tempMap;
    }


    /**
     * url 参数串连
     * @param map map
     * @param keyLower keyLower
     * @param valueUrlencode valueUrlencode
     * @return string
     */
    public static String mapJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode){
        StringBuilder stringBuilder = new StringBuilder();
        for(String key :map.keySet()){
            if(map.get(key)!=null&&!"".equals(map.get(key))){
                try {
                    String temp = (key.endsWith("_")&&key.length()>1)?key.substring(0,key.length()-1):key;
                    stringBuilder.append(keyLower?temp.toLowerCase():temp)
                            .append("=")
                            .append(valueUrlencode? URLEncoder.encode(map.get(key),"utf-8").replace("+", "%20"):map.get(key))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if(stringBuilder.length()>0){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        return stringBuilder.toString();
    }
}
