package com.tuspass.realname.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by panhong on 2019/5/8 13:58
 */
public class Util {

    private static final String numberChar = "0123456789";

    /**
     * 转换签名map
     * @param params 签名map
     * @return 转换结果
     */
    public static Map<String, String> convertMap(Map<String, Object> params) {
        Map<String, String> tmpMap = new HashMap<>();
        for(String key : params.keySet()){
            Object value = params.get(key);
            if(value instanceof JSONObject){
                tmpMap.put(key, JSON.toJSONString(MapUtil.orderObject(((JSONObject) value).getInnerMap())));
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray)value;
                for (int i = 0x00; i<array.size(); i++) {
                    Object object = array.get(i);
                    if (object instanceof JSONObject) {
                        Map<String, Object> map = ((JSONObject)object).getInnerMap();
                        array.set(i, MapUtil.orderObject(map));
                    }
                }
                tmpMap.put(key, JSON.toJSONString(value));
            } else {
                tmpMap.put(key, String.valueOf(value));
            }
        }

        return tmpMap;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getSingleIp(HttpServletRequest request) {
        String defaultIp = "0.0.0.0";
        String ip = getIp(request);
        if(StringUtils.indexOf(ip, ",") > 0) {
            ip = StringUtils.substring(ip, 0, StringUtils.indexOf(ip, ","));
        }
        if (StringUtils.isBlank(ip)) {
            return defaultIp;
        }
        return ip;
    }


    /**
     * transmit a Hex-byte to Hex-character in upper case
     * example: (0x0a - 'A')
     * @param   b			Hex-byte(0x00-0x0F)
     * @return  char		Hex-character: '0' - '9', 'A' - 'F'
     */
    public static char byteToHexChar(byte b) {
        if(b > 0x0F) {
            throw new Error("Not a Hex Byte: " + b);
        }

        if((b>=0) && (b<=9)) {
            return (char)(b+'0');
        }
        else {
            return (char)((b-0x0A)+'A');
        }
    }

    /**
     * transmit a hex-bytes array to a hex-charater string
     * example:({0x1A,0x02} - "1A02"
     * @param bts
     * @param off
     * @param length
     * @return
     */
    public static String bytesToString(byte[] bts, int off, int length){
        StringBuffer str = new StringBuffer(length*2);
        for(int i=0;i<length;i++) {
            str.append(byteToHexChar((byte)((bts[i+off]>>4)&0x0F)));
            str.append(byteToHexChar((byte)(bts[i+off]&0x0F)));
        }
        return str.toString();
    }

    /**
     * transmit a hex-bytes array to a hex-charater string
     * example:({0x1A,0x02} - "1A02"
     * @param	bts			Hex-byte array
     * @return  String		Hex-string build by Hex-characters
     * @throws Exception 	when input is not build by hex-bytes
     */
    public static String bytesToString(byte[] bts) {
        return bytesToString(bts, 0, bts.length);
    }

    public static String generateNumString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }
}
