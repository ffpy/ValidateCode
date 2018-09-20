package org.ffpy.validatecode;

import org.ffpy.validatecode.config.ValidateCodeConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 验证码
 */
public class ValidateCode {
	/** 身份信息 */
	private final String identity;
	/** 验证码 */
	private final String code;
	/** 过期时间 */
	private final Date expireTime;
	/** 创建时间 */
	private final Date createTime;

	/**
	 * @param identity 身份信息
	 * @param code     验证码
	 */
	public ValidateCode(String identity, String code) {
		this.identity = identity;
		this.code = code;
		this.createTime = new Date();

		// 设置默认过期时间
		Calendar expire = Calendar.getInstance();
		expire.set(Calendar.MILLISECOND, expire.get(Calendar.MILLISECOND) +
					ValidateCodeConfig.getDefaultExpireTime());
		this.expireTime = expire.getTime();
	}

	/**
	 * @param identity   身份信息
	 * @param code       验证码
	 * @param expireTime 过期时间
	 */
	public ValidateCode(String identity, String code, Date expireTime) {
		this.identity = identity;
		this.code = code;
		this.expireTime = expireTime;
		this.createTime = new Date();
	}

	/**
	 * @param identity   身份信息
	 * @param code       验证码
	 * @param expireTime 过期时间
	 * @param createTime 创建时间
	 */
	public ValidateCode(String identity, String code, Date expireTime, Date createTime) {
		this.identity = identity;
		this.code = code;
		this.expireTime = expireTime;
		this.createTime = createTime;
	}

	/**
	 * 判断验证码是否已过期
	 *
	 * @return 已过期返回true，否则返回false
	 */
	public boolean isExpired() {
		if (expireTime == null) return false;
		return expireTime.before(new Date());
	}

	/**
	 * 获取身份信息
	 *
	 * @return 身份信息
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * 获取验证码
	 *
	 * @return 验证码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 获取过期时间
	 *
	 * @return 过期时间
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * 获取创建时间
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public String toString() {
		return "ValidateCode{" +
			"identity='" + identity + '\'' +
			", code='" + code + '\'' +
			", expireTime=" + expireTime +
			", createTime=" + createTime +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ValidateCode that = (ValidateCode) o;
		return Objects.equals(identity, that.identity) &&
			Objects.equals(code, that.code) &&
			Objects.equals(expireTime, that.expireTime) &&
			Objects.equals(createTime, that.createTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity, code, expireTime, createTime);
	}
}
