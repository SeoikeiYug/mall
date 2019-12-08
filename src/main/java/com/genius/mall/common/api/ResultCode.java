package com.genius.mall.common.api;

/**
 * @类名 ResultCode
 * @描述 枚举了一些常用API操作码
 * @作者 shuaiqi
 * @创建日期 2019/10/28 9:27
 * @版本号 1.0
 * @参考地址
 **/
public enum ResultCode implements IErrorCode {
	SUCCESS(200, "操作成功"),
	FAILED(500, "操作失败"),
	VALIDATE_FAILED(400, "参数校验失败"),
	UNAUTHORIZED(401, "暂未登陆或token已过期"),
	FORBIDDEN(403, "没有相关权限");

	private long code;
	private String message;

	private ResultCode(long code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public long getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
