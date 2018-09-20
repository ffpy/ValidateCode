package org.ffpy.validatecode.store;

import org.ffpy.validatecode.ValidateCode;

import javax.servlet.http.HttpSession;

/**
 * Session验证码存储器
 */
public class SessionValidateCodeStore implements ValidateCodeStore<HttpSession> {
	/** 单例 */
	private static final ValidateCodeStore<HttpSession> INSTANCE = new SessionValidateCodeStore();

	/**
	 * 获取自身实例
	 *
	 * @return 验证码存储器实例
	 */
	public static ValidateCodeStore<HttpSession> getInstance() {
		return INSTANCE;
	}

	/**
	 * 单例
	 */
	private SessionValidateCodeStore() {
	}

	@Override
	public void saveCode(HttpSession session, String group, ValidateCode code) {
		session.setAttribute(group, code);
	}

	@Override
	public ValidateCode getCode(HttpSession session, String group) {
		return (ValidateCode) session.getAttribute(group);
	}

	@Override
	public void removeCode(HttpSession session, String group) {
		if (session != null)
			session.removeAttribute(group);
	}
}
