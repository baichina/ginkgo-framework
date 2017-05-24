package io.ginkgo.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.ginkgo.App;
import io.ginkgo.BaseTest;
import io.ginkgo.auth.entity.Device;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class DeviceServiceTest extends BaseTest {

	@Autowired
	private DeviceService deviceService;

	@Test
	public void create() {
		String clientVersion = "v1.0.0";
		String securityCode = "******";
		String type = "";
		String udid = "";
		String os = "";
		String osVersion = "";
		testPrint(deviceService.create(clientVersion, securityCode, type, udid, os, osVersion));
	}

	@Test
	public void get() {
		testPrint(deviceService.getDevice("BAMuc99LFIGZqQ8qyBOIPz"));
		testPrint(deviceService.getDevice("Db-1kopJlNQaev9t1tr6-v"));
	}

	@Test
	public void login() {
		testPrint(deviceService.login("BAMuc99LFIGZqQ8qyBOIPz", "100001"));
		testPrint(deviceService.login("Db-1kopJlNQaev9t1tr6-v", "100002"));
	}

	// @Test
	// public void getUserId() {
	// testPrint(deviceService.getUserId("BAMuc99LFIGZqQ8qyBOIPz",
	// "CIk4nDVPtEfb8RLtq26UsV"));
	// testPrint(deviceService.getUserId("Db-1kopJlNQaev9t1tr6-v",
	// "CIk4nDVPtEfb8RLtq26UsV"));
	// }

	@Test
	public void check() {
		Device device = new Device();
		device.setDeviceId("Db-1kopJlNQaev9t1tr6-v");
		device.setClientVersion("v1.0.0");
		device.setSecurityCode("******");
		testPrint(deviceService.check(device));
		device.setToken("CIk4nDVPtEfb8RLtq26UsV------");
		testPrint(deviceService.check(device));
		device.setToken("CIk4nDVPtEfb8RLtq26UsV");
		testPrint(deviceService.check(device));
		device.setDeviceId("Au_foMJplC1LhnLD-6Zgux");
		testPrint(deviceService.check(device));
	}
}