package org.ffpy.validatecode.store;

import org.ffpy.validatecode.ValidateCode;
import org.ffpy.validatecode.ValidateCodes;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryValidateCodeStoreTest {

	@Test
	public void testSaveCodeAndGetCodeAndRemoveCode() {
		ValidateCodeStore<String> store = MemoryValidateCodeStore.getInstance();

		ValidateCode code1 = ValidateCodes.createCode("user1");
		ValidateCode code2 = ValidateCodes.createCode("user2");
		ValidateCode code3 = ValidateCodes.createCode("user1");
		ValidateCode code4 = ValidateCodes.createCode("user2");

		store.saveCode("user1", "group1", code1);
		store.saveCode("user2", "group1", code2);
		store.saveCode("user1", "group2", code3);
		store.saveCode("user2", "group2", code4);

		assertEquals(code1, store.getCode("user1", "group1"));
		assertEquals(code2, store.getCode("user2", "group1"));
		assertEquals(code3, store.getCode("user1", "group2"));
		assertEquals(code4, store.getCode("user2", "group2"));

		store.removeCode("user1", "group1");
		store.removeCode("user2", "group1");
		store.removeCode("user1", "group2");
		store.removeCode("user2", "group2");

		assertNull(store.getCode("user1", "group1"));
		assertNull(store.getCode("user2", "group1"));
		assertNull(store.getCode("user1", "group2"));
		assertNull(store.getCode("user2", "group2"));
	}
}