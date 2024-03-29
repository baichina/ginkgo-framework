package io.ginkgo.auth.exception;

/**
 * 通用系统错误码：AUTH_开头
 * 
 * @since 1.0.0
 * @author Barry
 */
public enum AuthErrorCode {

	SUCCESS("AUTH_000000", "成功"),

	AUTH_EXCEPTION("AUTH_999999", "账号认证异常"),

	NO_LOGIN("AUTH_000001", "没有登录信息"),

	;

	private String code = null;

	private String msg = null;

	AuthErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static AuthErrorCode getByCode(String code) {
		if (code == null || code.isEmpty()) {
			return null;
		}

		AuthErrorCode[] enums = AuthErrorCode.values();
		for (AuthErrorCode p : enums) {
			if (p.getCode().equals(code)) {
				return p;
			}
		}

		return null;
	}
}