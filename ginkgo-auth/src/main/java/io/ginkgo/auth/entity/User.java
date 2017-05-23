package io.ginkgo.auth.entity;

import java.io.Serializable;

/**
 * 用户信息
 * 
 * @since 1.0.0
 * @author Barry
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2851326506001055600L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 设备号
	 */
	private String deviceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}