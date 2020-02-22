package com.tuspass.realname.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by panhong on 2019/5/8 13:50
 */
public class SignatureUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureUtil.class);

    /**
     * 生成sign MD5 签名
     * @param map map
     * @param paternerKey paternerKey
     * @return sign
     */
    public static String generateSign(Map<String, String> map, String paternerKey){
        if(map.containsKey("sign")){
            map.remove("sign");
        }
        String str = mapJoin(map);

        String key = str+"&key="+paternerKey;
        LOGGER.info("signature input:{}", key);
        return DigestUtils.md5Hex(key).toUpperCase();
    }
    public static String mapJoin(Map map){
        Map map1 = new LinkedHashMap();
        map1.putAll(map);
        StringBuffer sb = new StringBuffer();
        for (Object key : map1.keySet()){
            sb.append(key).append("=").append(map1.get(key)).append("&");
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    /**
     * 校验签名
     * @param map 参与签名的参数
     * @param key mch key
     * @return boolean
     */
    public static boolean validateSign(Map<String,String> map, String key){
        String sign = map.get("sign");
        String md5 = generateSign(map, key);
        LOGGER.info("signature input:{}", md5);
        return sign.equals(md5);
    }

    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("phone","23435234");
        map.put("name","23435234");
        map.put("sex","23435234你");
        map.put("address","23435234");

        Map map1 = new LinkedHashMap();
        map1.putAll(map);
        StringBuffer sb = new StringBuffer();
        for (Object key : map1.keySet()){
            sb.append(key).append("=").append(map1.get(key)).append("&");
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println(sb.toString());
    }
}
