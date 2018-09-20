package org.ffpy.validatecode.store;

import org.ffpy.validatecode.ValidateCode;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 内存验证码存储器，采用Map存储
 */
public class MemoryValidateCodeStore implements ValidateCodeStore<String> {
	/** 定时清理时间间隔（分） */
	private static final int TIMER_INTERVAL = 20;
	/** 清理的过期时间（分），即距离过期后多久才会清理 */
	private static final int REMOVE_EXPIRE_TIME = 5;
	/** 单例 */
	private static final ValidateCodeStore<String> INSTANCE = new MemoryValidateCodeStore();
	/** 存储Map，group -> (key -> code) */
	private final Map<String, Map<String, ValidateCode>> storeMap = new ConcurrentHashMap<>();

	/**
	 * 获取自身实例
	 *
	 * @return 验证码存储器实例
	 */
	public static ValidateCodeStore<String> getInstance() {
		return INSTANCE;
	}

	/**
	 * 单例
	 */
	private MemoryValidateCodeStore() {
		// 定时删除无效验证码
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				Calendar now = Calendar.getInstance();
				for (Map<String, ValidateCode> groupMap : storeMap.values()) {
					Iterator<Map.Entry<String, ValidateCode>> it = groupMap.entrySet().iterator();
					while (it.hasNext()) {
						// 移除无效的验证码
						if (isInvalid(now, it.next().getValue()))
							it.remove();
					}
				}
			}
		}, TIMER_INTERVAL, TIMER_INTERVAL, TimeUnit.MINUTES);
	}

	@Override
	public void saveCode(String key, String group, ValidateCode code) {
		Map<String, ValidateCode> groupMap = storeMap.get(group);
		if (groupMap == null) {
			groupMap = new ConcurrentHashMap<String, ValidateCode>();
			storeMap.put(group, groupMap);
		}
		groupMap.put(key, code);
	}

	@Override
	public ValidateCode getCode(String key, String group) {
		Map<String, ValidateCode> groupMap = storeMap.get(group);
		return groupMap == null ? null : groupMap.get(key);
	}

	@Override
	public void removeCode(String key, String group) {
		Map<String, ValidateCode> groupMap = storeMap.get(group);
		if (groupMap != null)
			groupMap.remove(key);
	}

	/**
	 * 判断验证码是否无效
	 *
	 * @return 是返回true，否则返回false
	 */
	private static boolean isInvalid(Calendar now, ValidateCode code) {
		Date expireTime = code.getExpireTime();
		if (expireTime == null) return false;
		Calendar c = Calendar.getInstance();
		c.setTime(expireTime);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + REMOVE_EXPIRE_TIME);
		return c.before(now);
	}
}
