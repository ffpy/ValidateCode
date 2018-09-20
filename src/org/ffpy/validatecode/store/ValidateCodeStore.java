package org.ffpy.validatecode.store;

import org.ffpy.validatecode.ValidateCode;

/**
 * 验证码存储器接口，对应Session、内存、数据库等存储方式。
 * <p>建议使用单例模式
 */
public interface ValidateCodeStore<T> {

	/**
	 * 存储验证码
	 *
	 * @param key   用于存储的键
	 * @param group 验证码分组名
	 * @param code  验证码
	 */
	void saveCode(T key, String group, ValidateCode code);

	/**
	 * 获取验证码
	 *
	 * @param key   用于存储的键
	 * @param group 验证码分组名
	 */
	ValidateCode getCode(T key, String group);

	/**
	 * 清除验证码
	 *
	 * @param key   用于存储的键
	 * @param group 验证码分组名
	 */
	void removeCode(T key, String group);
}
