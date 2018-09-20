package org.ffpy.validatecode.generator;

/**
 * 验证码生成器接口
 * <p>建议使用单例模式
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成默认位数的验证码
	 *
	 * @return 生成的验证码
	 */
	String createCode();

	/**
	 * 生成指定位数的验证码
	 *
	 * @param bits 验证码位数
	 * @return 生成的验证码
	 */
	String createCode(int bits);
}
