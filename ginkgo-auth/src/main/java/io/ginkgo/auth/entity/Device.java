package io.ginkgo.auth.entity;

import java.io.Serializable;

/**
 * 设备信息
 * 
 * @since 1.0.0
 * @author Barry
 */
public class Device implements Serializable {

	private static final long serialVersionUID = 1696092660952551526L;

	// 请求头信息KEY
	public static final String DEVICE_ID = "deviceId";
	public static final String CLIENT_VERSION = "clientVersion";
	public static final String SECURITY_CODE = "securityCode";
	public static final String TOKEN = "token";
	public static final String USER_ID = "userId";

	/**
	 * 设备号
	 */
	private String deviceId;
	/**
	 * 客户端版本
	 */
	private String clientVersion;
	/**
	 * 安全码：防止冒用客户端
	 */
	private String securityCode;
	/**
	 * 登录令牌
	 */
	private String token;
	/**
	 * 用户ID
	 */
	private String userId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public Integer getIntUserId() {
		Integer intUserId = null;
		try {
			intUserId = Integer.parseInt(userId);
		} catch (Exception e) {
		}
		return intUserId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}