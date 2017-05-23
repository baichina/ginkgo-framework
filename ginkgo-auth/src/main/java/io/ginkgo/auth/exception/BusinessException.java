package io.ginkgo.auth.exception;

/**
 * 业务异常基类
 * 
 * @since 1.0.0
 * @author baiwei
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6040721435393010636L;

	protected String errorCode;

	protected String errorMsg;

	protected Exception exception;

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BusinessException() {
		this.exception = new RuntimeException();
	}

	public BusinessException(String errorCode, String errorMsg) {
		this.exception = new RuntimeException();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String getMessage() {
		return errorMsg + "(" + errorCode + ")";
	}
}