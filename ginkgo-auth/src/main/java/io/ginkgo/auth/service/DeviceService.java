package io.ginkgo.auth.service;

import io.ginkgo.auth.entity.Device;
import io.ginkgo.auth.entity.User;

/**
 * 设备信息
 * 
 * @since 1.0.0
 * @author Barry
 */
public interface DeviceService {

	/**
	 * 生成设备信息
	 * 
	 * @param clientVersion
	 *            客户端版本
	 * @param securityCode
	 *            安全码：防止冒用客户端
	 * @param type
	 *            机型
	 * @param udid
	 *            唯一设备ID（IOS）
	 * @param os
	 *            操作系统
	 * @param osVersion
	 *            系统版本
	 * @return 设备号
	 */
	public String create(String clientVersion, String securityCode, String type, String udid, String os,
			String osVersion);

	/**
	 * 获取设备信息
	 * 
	 * @param deviceId
	 *            设备号
	 * @return 设备信息
	 */
	public Device getDevice(String deviceId);

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户信息
	 */
	public User getUser(String userId);

	/**
	 * 登录
	 * <p>
	 * 支持多设备登录，登录令牌不同
	 * </p>
	 * 
	 * @param deviceId
	 *            设备号
	 * @param userId
	 *            用户ID
	 * @return 登录令牌
	 */
	public String login(String deviceId, String userId);

	// /**
	// * 获取用户ID
	// *
	// * @param deviceId
	// * 设备号
	// * @param token
	// * 登录令牌
	// * @return 用户ID
	// */
	// public String getUserId(String deviceId, String token);

	/**
	 * 校验设备信息
	 * 
	 * @param device
	 *            设备信息
	 * @return
	 */
	public Boolean check(Device device);
}