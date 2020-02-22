package com.tuspass.realname.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Value("${miniprogram.requestKey}")
    private String requestKey;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkToken(request, response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("TOKEN");

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String url = request.getRequestURI();
        if(url.contains("/error")){
            return false;
        }

    /*    if (StringUtils.isEmpty(token)) {
            LOGGER.info("未获取TOKEN，不允许操作");
            response.setStatus(450);
            return false;
        }*/

   /*     String body = getRequestPostStr(request);
        Map<String, Object> parameterMap = JSON.parseObject(body, Map.class);
        LOGGER.info("请求url:{}, 请求参数:{}", url, JSONObject.toJSONString(parameterMap));

        if (!SignatureUtil.validateSign(Util.convertMap(parameterMap), requestKey)) {
            LOGGER.error("请求签名校验失败");
            throw new BusinessException(ResultEnum.PARAMETER_ERROR, "参数错误");
        }*/


        return true;
    }
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

}
