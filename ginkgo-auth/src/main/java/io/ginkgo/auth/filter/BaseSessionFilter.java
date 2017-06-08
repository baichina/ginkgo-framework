package io.ginkgo.auth.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import io.ginkgo.auth.entity.Device;

/**
 * 过滤器基类
 * <p>
 * 定义必须要实现的方法
 * </p>
 * 
 * @since 1.0.0
 * @author Barry
 */
public abstract class BaseSessionFilter implements Filter {

	private static final Logger log = Logger.getLogger(BaseSessionFilter.class);

	/**
	 * 用户本次请求的设备信息
	 */
	private static ThreadLocal<Device> device = new ThreadLocal<Device>();

	public static Device getDevice() {
		return device.get();
	}

	public static void setDevice(HttpServletRequest req) {
		Device newDevice = new Device();
		newDevice.setDeviceId(req.getHeader(Device.DEVICE_ID));
		newDevice.setClientVersion(req.getHeader(Device.CLIENT_VERSION));
		newDevice.setSecurityCode(req.getHeader(Device.SECURITY_CODE));
		newDevice.setToken(req.getHeader(Device.TOKEN));
		newDevice.setUserId(req.getHeader(Device.USER_ID));
		// 本次访问的头信息
		log.info("request=" + req.getServletPath() + " deviceId=" + newDevice.getDeviceId() + ", clientVersion="
				+ newDevice.getClientVersion() + ", securityCode=" + newDevice.getSecurityCode() + ", token="
				+ newDevice.getToken() + ", userId=" + newDevice.getUserId());
		device.set(newDevice);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("doFilter");
		HttpServletRequest req = (HttpServletRequest) request;

		// 将头信息设置到线程对象中
		setDevice(req);

		if (isNotFilterPath(req.getServletPath())) {
			// 不需要过滤的URL
			chain.doFilter(request, response);
			return;
		}

		if (validate()) {
			// 校验通过
			chain.doFilter(request, response);
		} else {
			// 校验不通过
			PrintWriter out = response.getWriter();
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("code", "AUTH_999999");
			responseBody.put("msg", "logout");
			out.print(JSON.toJSON(responseBody).toString());
			return;
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.debug("sessionFilter init");
	}

	@Override
	public void destroy() {
		log.debug("sessionFilter destroy");
	}

	/**
	 * 不需要过滤的URL，默认需要过滤
	 * <p>
	 * 重载此方法，不要过滤的URL可以配置在文件中
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	protected Boolean isNotFilterPath(String path) {
		return false;
	}

	/**
	 * 校验http header登录会话信息，默认通过
	 * <p>
	 * 重载此方法
	 * </p>
	 * 
	 * @param httpHeader
	 * @return
	 */
	protected Boolean validate() {
		return true;
	}
}