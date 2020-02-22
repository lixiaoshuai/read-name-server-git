package com.tuspass.realname.common.entity;


import com.tuspass.realname.common.constant.Constants;

import java.io.Serializable;

/**
 * Created by young on 6/11/15.
 */
public class RespEntity<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7739979424806069482L;
	private String code;
	private String message;
	private T data;

	public RespEntity() {
		this.code = "";
		this.message = "";
	}

	public RespEntity(String code) {
		this.code = (code == null ? "" : code);
		this.message = "";
	}

	public RespEntity(String code, String message) {
		this.code = (code == null ? "" : code);
		this.message = (message == null ? "" : message);
	}

	public RespEntity(String code, String message, T data) {
		this.code = (code == null ? "" : code);
		this.message = (message == null ? "" : message);
		this.data = data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RespEntity successResp(String message, Object data) {
		return new RespEntity(Constants.BUSI_SUCCESS, message, data);
	}
	public static RespEntity successResp(Object data) {
		return new RespEntity(Constants.BUSI_SUCCESS, "操作成功", data);
	}
	public static RespEntity successResp(String message) {
		return new RespEntity(Constants.BUSI_SUCCESS, message, null);
	}

	public static RespEntity successResp() {
		return new RespEntity(Constants.BUSI_SUCCESS, "操作成功", null);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RespEntity errorResp(String code,String message, Object data) {
		return new RespEntity(code, message, data);
	}
	public static RespEntity errorResp(String code,String message) {
		return new RespEntity(code, message);
	}

	public static RespEntity errorResp(String message) {
		return errorResp(Constants.BUSI_ERROR, message, null);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isOk() {
		return Constants.BUSI_SUCCESS.equals(this.code);
	}
}
