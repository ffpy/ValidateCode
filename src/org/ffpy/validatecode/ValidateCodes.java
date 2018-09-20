package org.ffpy.validatecode;

import com.sun.istack.internal.Nullable;
import org.ffpy.validatecode.config.ValidateCodeConfig;
import org.ffpy.validatecode.error.ValidateCodeErrorCode;

import java.util.Calendar;
import java.util.Date;

/**
 * 验证码工具类
 */
public class ValidateCodes {

	/**
	 * 不可实例化
	 */
	private ValidateCodes() {
		throw new AssertionError();
	}

	/**
	 * 生成验证码
	 *
	 * @param identity 身份信息
	 * @return 验证码
	 */
	public static ValidateCode createCode(String identity) {
		String code = ValidateCodeConfig.getDefaultGenerator().createCode();
		return new ValidateCode(identity, code);
	}

	/**
	 * 生成验证码，指定过期时间
	 *
	 * @param identity   身份信息
	 * @param expireTime 过期时间
	 * @return 验证码
	 */
	public static ValidateCode createCode(String identity, Date expireTime) {
		String code = ValidateCodeConfig.getDefaultGenerator().createCode();
		return new ValidateCode(identity, code, expireTime);
	}

	/**
	 * 生成验证码，指定验证码位数
	 *
	 * @param identity 身份信息
	 * @param bits     验证码位数
	 * @return 验证码
	 */
	public static ValidateCode createCode(String identity, int bits) {
		String code = ValidateCodeConfig.getDefaultGenerator().createCode(bits);
		return new ValidateCode(identity, code);
	}

	/**
	 * 生成验证码，指定验证码位数和过期时间
	 *
	 * @param identity   身份信息
	 * @param bits       验证码位数
	 * @param expireTime 过期时间
	 * @return 验证码
	 */
	public static ValidateCode createCode(String identity, int bits, Date expireTime) {
		String code = ValidateCodeConfig.getDefaultGenerator().createCode(bits);
		return new ValidateCode(identity, code, expireTime);
	}

	/**
	 * 检测发送间隔，使用默认发送间隔
	 *
	 * @param code 验证码
	 * @return 可以发送返回true，否则返回false
	 */
	public static boolean checkSendInterval(@Nullable ValidateCode code) {
		return checkSendInterval(code, ValidateCodeConfig.getDefaultSendInterval());
	}

	/**
	 * 检测发送间隔，要距离上一次发送sendInterval秒后才能再次发送
	 *
	 * @param code         验证码
	 * @param sendInterval 发送间隔（毫秒）
	 * @return 可以发送返回true，否则返回false
	 */
	public static boolean checkSendInterval(@Nullable ValidateCode code, int sendInterval) {
		if (code == null) return true;
		Calendar allowTime = Calendar.getInstance();
		allowTime.setTime(code.getCreateTime());
		allowTime.set(Calendar.MILLISECOND, (allowTime.get(Calendar.MILLISECOND) + sendInterval));
		return allowTime.compareTo(Calendar.getInstance()) <= 0;
	}

	/**
	 * 校验验证码
	 *
	 * @param identity     身份信息
	 * @param code         用户提交的验证码
	 * @param expectedCode 要验证的验证码
	 * @return 校验失败返回错误码，校验成功返回null
	 */
	public static ValidateCodeErrorCode validate(String identity, String code, ValidateCode expectedCode) {
		// 身份信息不能是否为空
		if (identity == null || identity.isEmpty())
			return ValidateCodeErrorCode.IDENTITY_EMPTY;
		// 提交的验证码是否为空
		if (code == null || code.isEmpty())
			return ValidateCodeErrorCode.CODE_EMPTY;
		// 要验证的验证码是否存在
		if (expectedCode == null)
			return ValidateCodeErrorCode.CODE_NOT_EXISTS;
		// 要验证的验证码是否过期
		if (expectedCode.isExpired())
			return ValidateCodeErrorCode.CODE_EXPIRED;
		// 验证身份信息和验证码是否相同
		if (!identity.equals(expectedCode.getIdentity()) ||
			!code.toUpperCase().equals(expectedCode.getCode()))
			return ValidateCodeErrorCode.CODE_INCORRECT;
		// 校验成功
		return null;
	}
}
