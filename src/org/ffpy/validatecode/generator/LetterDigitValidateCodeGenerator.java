package org.ffpy.validatecode.generator;

import org.ffpy.validatecode.config.ValidateCodeConfig;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 数字+字母的验证码生成器
 */
public class LetterDigitValidateCodeGenerator implements ValidateCodeGenerator {
	/** 验证码字符集，去掉易混淆字符'0'和'O' */
	private static final char[] CODES = "123456789QWERTYUIPASDFGHJKLZXCVBNM".toCharArray();
	/** 单例 */
	private static final ValidateCodeGenerator INSTANCE = new LetterDigitValidateCodeGenerator();
	/** 随机数生成器 */
	private final Random random = new SecureRandom();

	/**
	 * 获取自身实例
	 *
	 * @return 验证码生成器实例
	 */
	public static ValidateCodeGenerator getInstance() {
		return INSTANCE;
	}

	/**
	 * 单例
	 */
	private LetterDigitValidateCodeGenerator() {
	}

	@Override
	public String createCode() {
		return createCode(ValidateCodeConfig.getDefaultCodeBits());
	}

	@Override
	public String createCode(int bits) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bits; i++) {
			sb.append(CODES[random.nextInt(CODES.length)]);
		}
		return sb.toString();
	}
}
