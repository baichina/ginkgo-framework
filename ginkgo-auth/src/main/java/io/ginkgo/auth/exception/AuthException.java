package io.ginkgo.auth.exception;

/**
 * 异常类
 * 
 * @since 1.0.0
 * @author baiwei
 */
public class AuthException extends BusinessException {

	private static final long serialVersionUID = 6040721435393010636L;

	public AuthException() {
		this.exception = new RuntimeException();
		this.errorCode = AuthErrorCode.AUTH_EXCEPTION.getCode();
		this.errorMsg = AuthErrorCode.AUTH_EXCEPTION.getMsg();
	}

	public AuthException(String errorCode, String errorMsg) {
		this.exception = new RuntimeException();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AuthException(AuthErrorCode rsauthErrorCode) {
		this.exception = new RuntimeException();
		this.errorCode = rsauthErrorCode.getCode();
		this.errorMsg = rsauthErrorCode.getMsg();
	}
}