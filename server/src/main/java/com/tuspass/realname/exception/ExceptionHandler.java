package com.tuspass.realname.exception;

import com.tuspass.realname.common.entity.RespEntity;
import com.tuspass.realname.common.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Author lxs
 * @Description
 * @Date 2020/2/21 2:19
 **/
@ControllerAdvice
public class ExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public RespEntity exceptionHandler(Exception e){
        LOGGER.error("系统异常", e);
        return RespEntity.errorResp(ResultEnum.SYSTEM_ERROR.getMsg());
    }
}
