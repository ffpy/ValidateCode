package org.ffpy.validatecode.error;

/**
 * 验证码校验错误码
 */
public enum ValidateCodeErrorCode {
	IDENTITY_EMPTY("身份信息不能为空"),
	CODE_EMPTY("验证码不能为空"),
	CODE_NOT_EXISTS("验证码不存在"),
	CODE_EXPIRED("验证码已过期"),
	CODE_INCORRECT("验证码不正确"),
	;

	/** 错误信息 */
	private final String message;

	ValidateCodeErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
