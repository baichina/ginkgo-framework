package io.ginkgo.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.ginkgo.auth.constants.RedisKeys;
import io.ginkgo.auth.entity.Device;
import io.ginkgo.auth.entity.User;
import io.ginkgo.auth.service.DeviceService;
import io.ginkgo.cache.redis.RedisService;
import io.ginkgo.util.UUIDUtil;

/**
 * 设备信息redis实现
 * 
 * @since 1.0.0
 * @author Barry
 */
@Service
public class RedisDeviceServiceImpl implements DeviceService {

	private static final Logger log = Logger.getLogger(RedisDeviceServiceImpl.class);

	@Autowired
	@Lazy
	private RedisService<Device> redisService;

	// @Value("${system.name}")
	// private String systemName;

	@Value("${ginkgo.auth.session.timeout}")
	private long sessionTimeout;

	@Override
	public String create(String clientVersion, String securityCode, String type, String udid, String os,
			String osVersion) {
		String deviceId = UUIDUtil.getUUID();
		Map<Object, Object> map = new HashMap<>();
		map.put(Device.CLIENT_VERSION, clientVersion);
		map.put(Device.SECURITY_CODE, securityCode);
		redisService.putAll(String.format(RedisKeys.DEVICE, deviceId), map);
		return deviceId;
	}

	@Override
	public Device getDevice(String deviceId) {
		Map<Object, Object> map = redisService.entries(String.format(RedisKeys.DEVICE, deviceId));
		if (map == null || map.size() == 0) {
			return null;
		}
		Device device = new Device();
		device.setDeviceId(deviceId);
		device.setClientVersion((String) map.get(Device.CLIENT_VERSION));
		device.setSecurityCode((String) map.get(Device.SECURITY_CODE));
		device.setToken((String) map.get(Device.TOKEN));
		device.setUserId((String) map.get(Device.USER_ID));
		return device;
	}

	@Override
	public User getUser(String userId) {
		Map<Object, Object> map = redisService.entries(String.format(RedisKeys.USER, userId));
		if (map == null || map.size() == 0) {
			return null;
		}
		User user = new User();
		user.setUserId(userId);
		user.setDeviceId((String) map.get(Device.DEVICE_ID));
		return user;
	}

	@Override
	public String login(String deviceId, String userId) {
		if (getDevice(deviceId) == null || StringUtils.isEmpty(userId)) {
			return null;
		}

		// 保存设备信息
		String token = UUIDUtil.getUUID();
		redisService.put(String.format(RedisKeys.DEVICE, deviceId), Device.TOKEN, token, sessionTimeout);
		redisService.put(String.format(RedisKeys.DEVICE, deviceId), Device.USER_ID, userId, sessionTimeout);

		// 保存用户信息
		redisService.put(String.format(RedisKeys.USER, userId), Device.DEVICE_ID, deviceId);

		return token;
	}

	// @Override
	// public String getUserId(String deviceId, String token) {
	// Device device = get(deviceId);
	// if (device != null && device.getToken() != null &&
	// device.getToken().equals(token)) {
	// return device.getUserId();
	// } else {
	// log.info("getUserId not login, deviceId=" + deviceId + ", token=" +
	// token);
	// // throw new RsauthException(RsauthErrorCode.NO_LOGIN);
	// return null;
	// }
	// }

	@Override
	public Boolean check(Device device) {
		if (device == null || device.getDeviceId() == null || device.getClientVersion() == null
				|| device.getSecurityCode() == null || device.getToken() == null || device.getUserId() == null) {
			return false;
		}

		// 校验设备信息
		Device redisDevice = getDevice(device.getDeviceId());
		if (redisDevice == null) {
			return false;
		}
		if (!device.getClientVersion().equals(redisDevice.getClientVersion())) {
			// TODO 客户端升级了，需要更新客户端版本
		}
		if (!device.getSecurityCode().equals(redisDevice.getSecurityCode())) {
			// TODO 根据约定算法校验
		}
		if (!device.getToken().equals(redisDevice.getToken())) {
			return false;
		}
		if (!device.getUserId().equals(redisDevice.getUserId())) {
			return false;
		}

		// 校验用户信息
		User redisUser = getUser(device.getUserId());
		if (redisUser == null) {
			return false;
		}
		if (!device.getDeviceId().equals(redisUser.getDeviceId())) {
			log.info("login in other client, userId=" + redisUser.getUserId());
			/*
			 * 仅允许一个客户端登录
			 * 
			 * 之前登录的客户端，将被退出
			 */
			return false;
		}

		return true;
	}
}