package com.tuspass.realname.exception;

import com.tuspass.realname.common.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义渠道异常
 *
 * Created by panhong on 2017/12/17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException{

    /**
     * 异常码
     */
    private String result;
    /**
     * 异常消息
     */
    private String errMsg;

    private Object object;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.result = resultEnum.getResult();
        this.errMsg = resultEnum.getMsg();
    }

    public BusinessException(ResultEnum resultEnum, Object object) {
        super(resultEnum.getMsg());
        this.result = resultEnum.getResult();
        this.errMsg = resultEnum.getMsg();
        this.object = object;
    }

    public BusinessException(ResultEnum resultEnum, String errMsg) {
        super(errMsg);
        this.result = resultEnum.getResult();
        this.errMsg = errMsg;
    }

    public BusinessException(ResultEnum resultEnum, String errMsg, Object object) {
        super(errMsg);
        this.result = resultEnum.getResult();
        this.errMsg = errMsg;
        this.object = object;
    }
}
