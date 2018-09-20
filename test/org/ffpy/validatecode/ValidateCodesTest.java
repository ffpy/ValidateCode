package org.ffpy.validatecode;

import org.ffpy.validatecode.config.ValidateCodeConfig;
import org.ffpy.validatecode.error.ValidateCodeErrorCode;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ValidateCodesTest {

	@Test
	public void testCreateCode() {
		final String identity = "user";
		ValidateCode code = ValidateCodes.createCode(identity);
		assertEquals(identity, code.getIdentity());
		assertNotNull(code.getCode());
		assertNotNull(code.getExpireTime());
		assertNotNull(code.getCreateTime());
	}

	@Test
	public void testCreateCodeWithExpireTime() {
		final String identity = "user";
		final Date expireTime = new Date();
		ValidateCode code = ValidateCodes.createCode(identity, expireTime);
		assertEquals(identity, code.getIdentity());
		assertEquals(expireTime, code.getExpireTime());
		assertNotNull(code.getCode());
		assertNotNull(code.getCreateTime());
	}

	@Test
	public void testCreateCodeWithBits() {
		final String identity = "user";
		final int bits = 8;
		ValidateCode code = ValidateCodes.createCode(identity, bits);
		assertEquals(identity, code.getIdentity());
		assertEquals(bits, code.getCode().length());
		assertNotNull(code.getExpireTime());
		assertNotNull(code.getCreateTime());
	}

	@Test
	public void testCreateCodeWithBitsAndExpireTime() {
		final String identity = "user";
		final int bits = 8;
		final Date expireTime = new Date();
		ValidateCode code = ValidateCodes.createCode(identity, bits, expireTime);
		assertEquals(identity, code.getIdentity());
		assertEquals(bits, code.getCode().length());
		assertEquals(expireTime, code.getExpireTime());
		assertNotNull(code.getCreateTime());
	}

	@Test
	public void checkDefaultSendInterval() throws InterruptedException {
		final int sendIntervalSecond = 5;
		final int sendInterval = (int) TimeUnit.SECONDS.toMillis(sendIntervalSecond);
		ValidateCodeConfig.setDefaultExpireTime(sendInterval);
		ValidateCode code = ValidateCodes.createCode("user");

		assertFalse(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(sendIntervalSecond - 1);
		assertFalse(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(1);
		assertTrue(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(1);
		assertTrue(ValidateCodes.checkSendInterval(code, sendInterval));
	}

	@Test
	public void checkSendInterval() throws InterruptedException {
		final int sendIntervalSecond = 5;
		final int sendInterval = (int) TimeUnit.SECONDS.toMillis(sendIntervalSecond);
		Calendar expireTime = Calendar.getInstance();
		expireTime.set(Calendar.MILLISECOND, expireTime.get(Calendar.MILLISECOND) + sendInterval);
		ValidateCode code = ValidateCodes.createCode("user", expireTime.getTime());

		assertFalse(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(sendIntervalSecond - 1);
		assertFalse(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(1);
		assertTrue(ValidateCodes.checkSendInterval(code, sendInterval));
		TimeUnit.SECONDS.sleep(1);
		assertTrue(ValidateCodes.checkSendInterval(code, sendInterval));
	}

	@Test
	public void validate() {
		final String identity = "user";
		ValidateCode code = ValidateCodes.createCode(identity);
		Calendar expireTime = Calendar.getInstance();
		expireTime.set(Calendar.SECOND, expireTime.get(Calendar.SECOND) - 10);

		assertEquals(ValidateCodeErrorCode.IDENTITY_EMPTY,
			ValidateCodes.validate(null, code.getCode(), code));
		assertEquals(ValidateCodeErrorCode.IDENTITY_EMPTY,
			ValidateCodes.validate("", code.getCode(), code));
		assertEquals(ValidateCodeErrorCode.CODE_EMPTY,
			ValidateCodes.validate(identity, null, code));
		assertEquals(ValidateCodeErrorCode.CODE_EMPTY,
			ValidateCodes.validate(identity, "", code));
		assertEquals(ValidateCodeErrorCode.CODE_NOT_EXISTS,
			ValidateCodes.validate(identity, code.getCode(), null));
		assertEquals(ValidateCodeErrorCode.CODE_EXPIRED,
			ValidateCodes.validate(identity, code.getCode(),
				ValidateCodes.createCode(identity, expireTime.getTime())));
		assertEquals(ValidateCodeErrorCode.CODE_INCORRECT,
			ValidateCodes.validate("aaa", code.getCode(), code));
		assertEquals(ValidateCodeErrorCode.CODE_INCORRECT,
			ValidateCodes.validate(identity, "3ds", code));
		assertNull(ValidateCodes.validate(identity, code.getCode(), code));
	}
}