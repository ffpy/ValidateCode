package org.ffpy.validatecode.config;

import org.ffpy.validatecode.generator.LetterDigitValidateCodeGenerator;
import org.ffpy.validatecode.generator.ValidateCodeGenerator;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 验证码配置
 */
public class ValidateCodeConfig {
	/** 默认验证码位数，默认为4位 */
	private static int defaultCodeBits = 4;
	/** 发送间隔（毫秒），默认为60秒 */
	private static int defaultSendInterval = (int) TimeUnit.SECONDS.toMillis(60);
	/** 默认过期时间（毫秒），默认为5分钟 */
	private static int defaultExpireTime = (int) TimeUnit.MINUTES.toMillis(5);
	/** 默认的验证码生成器，默认为数字字符验证码生成器 */
	private static ValidateCodeGenerator defaultGenerator =
		LetterDigitValidateCodeGenerator.getInstance();

	/**
	 * 不可实例化
	 */
	private ValidateCodeConfig() {
	}

	/**
	 * 获取默认验证码位数
	 *
	 * @return 默认验证码位数
	 */
	public static int getDefaultCodeBits() {
		return defaultCodeBits;
	}

	/**
	 * 设置默认验证码位数
	 *
	 * @param defaultCodeBits 默认验证码位数
	 */
	public static void setDefaultCodeBits(int defaultCodeBits) {
		if (defaultCodeBits <= 0)
			throw new IllegalArgumentException("默认验证码位数必须大于0");
		ValidateCodeConfig.defaultCodeBits = defaultCodeBits;
	}

	/**
	 * 获取默认发送间隔
	 *
	 * @return 默认发送间隔（毫秒）
	 */
	public static int getDefaultSendInterval() {
		return defaultSendInterval;
	}

	/**
	 * 设置默认发送间隔
	 *
	 * @param defaultSendInterval 默认发送间隔（毫秒）
	 */
	public static void setDefaultSendInterval(int defaultSendInterval) {
		if (defaultSendInterval <= 0)
			throw new IllegalArgumentException("默认发送间隔必须大于0");
		ValidateCodeConfig.defaultSendInterval = defaultSendInterval;
	}

	/**
	 * 获取默认过期时间
	 *
	 * @return 默认过期时间（毫秒）
	 */
	public static int getDefaultExpireTime() {
		return defaultExpireTime;
	}

	/**
	 * 设置默认过期时间
	 *
	 * @param defaultExpireTime 默认过期时间（毫秒）
	 */
	public static void setDefaultExpireTime(int defaultExpireTime) {
		if (defaultExpireTime <= 0)
			throw new IllegalArgumentException("默认过期时间必须大于0");
		ValidateCodeConfig.defaultExpireTime = defaultExpireTime;
	}

	/**
	 * 获取默认验证码生成器
	 *
	 * @return 默认验证码生成器
	 */
	public static ValidateCodeGenerator getDefaultGenerator() {
		return defaultGenerator;
	}

	/**
	 * 设置默认验证码生成器
	 *
	 * @param defaultGenerator 默认验证码生成器
	 */
	public static void setDefaultGenerator(ValidateCodeGenerator defaultGenerator) {
		ValidateCodeConfig.defaultGenerator = Objects.requireNonNull(defaultGenerator,
			"defaultGenerator不能为null");
	}
}
