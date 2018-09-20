package org.ffpy.validatecode.store;

import org.ffpy.validatecode.MockHttpSession;
import org.ffpy.validatecode.ValidateCode;
import org.ffpy.validatecode.ValidateCodes;
import org.junit.Test;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class SessionValidateCodeStoreTest {

	@Test
	public void testSaveCodeAndGetCodeAndRemoveCode() {
		ValidateCodeStore<HttpSession> store = SessionValidateCodeStore.getInstance();
		HttpSession session1 = new MockHttpSession();
		HttpSession session2 = new MockHttpSession();

		ValidateCode code1 = ValidateCodes.createCode("user1");
		ValidateCode code2 = ValidateCodes.createCode("user2");
		ValidateCode code3 = ValidateCodes.createCode("user1");
		ValidateCode code4 = ValidateCodes.createCode("user2");

		store.saveCode(session1, "group1", code1);
		store.saveCode(session2, "group1", code2);
		store.saveCode(session1, "group2", code3);
		store.saveCode(session2, "group2", code4);

		assertEquals(code1, store.getCode(session1, "group1"));
		assertEquals(code2, store.getCode(session2, "group1"));
		assertEquals(code3, store.getCode(session1, "group2"));
		assertEquals(code4, store.getCode(session2, "group2"));

		store.removeCode(session1, "group1");
		store.removeCode(session2, "group1");
		store.removeCode(session1, "group2");
		store.removeCode(session2, "group2");

		assertNull(store.getCode(session1, "group1"));
		assertNull(store.getCode(session2, "group1"));
		assertNull(store.getCode(session1, "group2"));
		assertNull(store.getCode(session2, "group2"));
	}
}