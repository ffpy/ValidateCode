package org.ffpy.validatecode.generator;

import org.ffpy.validatecode.config.ValidateCodeConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class LetterDigitValidateCodeGeneratorTest {

	@Test
	public void testCreateCode() {
		ValidateCodeGenerator generator = LetterDigitValidateCodeGenerator.getInstance();
		String code = generator.createCode();
		assertEquals(ValidateCodeConfig.getDefaultCodeBits(), code.length());
		for (int i = 0; i < code.length(); i++) {
			assertTrue(Character.isLetter(code.charAt(i)) ||
				Character.isDigit(code.charAt(i)));
		}
	}

	@Test
	public void testCreateCodeWithBits() {
		final int bits = 8;
		ValidateCodeGenerator generator = LetterDigitValidateCodeGenerator.getInstance();
		String code = generator.createCode(bits);
		assertEquals(bits, code.length());
		for (int i = 0; i < code.length(); i++) {
			assertTrue(Character.isLetter(code.charAt(i)) ||
				Character.isDigit(code.charAt(i)));
		}
	}
}