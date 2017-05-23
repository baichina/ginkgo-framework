package io.ginkgo.auth.constants;

/**
 * redis KEY
 * 
 * @since 1.0.0
 * @author baiwei
 */
public class RedisKeys {
	/**
	 * 设备信息，${deviceId}
	 * <p>
	 * 包含信息
	 * </p>
	 * <p>
	 * clientVersion、securityCode、token、userId
	 * </p>
	 */
	public static final String DEVICE = "rsauth:device:%s";

	/**
	 * 用户信息，${userId}
	 * <p>
	 * 包含信息
	 * </p>
	 * <p>
	 * deviceId
	 * </p>
	 */
	public static final String USER = "rsauth:user:%s";
}