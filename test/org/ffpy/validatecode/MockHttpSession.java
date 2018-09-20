package org.ffpy.validatecode;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockHttpSession implements HttpSession {
	private Map<String, Object> attrs = new ConcurrentHashMap<>();

	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return String.valueOf(hashCode());
	}

	@Override
	public long getLastAccessedTime() {
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int i) {

	}

	@Override
	public int getMaxInactiveInterval() {
		return 0;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	@Override
	public Object getAttribute(String s) {
		return attrs.get(s);
	}

	@Override
	public Object getValue(String s) {
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}

	@Override
	public String[] getValueNames() {
		return new String[0];
	}

	@Override
	public void setAttribute(String s, Object o) {
		attrs.put(s, o);
	}

	@Override
	public void putValue(String s, Object o) {

	}

	@Override
	public void removeAttribute(String s) {
		attrs.remove(s);
	}

	@Override
	public void removeValue(String s) {

	}

	@Override
	public void invalidate() {
		attrs.clear();
	}

	@Override
	public boolean isNew() {
		return false;
	}
}
