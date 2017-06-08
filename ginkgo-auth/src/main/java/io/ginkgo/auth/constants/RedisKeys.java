package io.ginkgo.auth.constants;

/**
 * redis KEY
 * 
 * @since 1.0.0
 * @author Barry
 */
public class RedisKeys {

	public static final String SYSTEM = "auth";

	/**
	 * 设备信息，${deviceId}
	 * <p>
	 * 包含信息
	 * </p>
	 * <p>
	 * clientVersion、securityCode、token、userId
	 * </p>
	 */
	public static final String DEVICE = SYSTEM + ":device:%s";

	/**
	 * 用户信息，${userId}
	 * <p>
	 * 包含信息
	 * </p>
	 * <p>
	 * deviceId
	 * </p>
	 */
	public static final String USER = SYSTEM + ":user:%s";
}