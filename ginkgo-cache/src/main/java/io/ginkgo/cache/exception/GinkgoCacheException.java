package io.ginkgo.cache.exception;

/**
 * 缓存异常
 * 
 * @since 1.0.0
 * @author Barry
 */
public class GinkgoCacheException extends RuntimeException {

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

	@Override
	public String getMessage() {
		return errorMsg + "(" + errorCode + ")";
	}

	public GinkgoCacheException() {
		this.exception = new RuntimeException();
		this.errorCode = GinkgoCacheErrorCode.CACHE_EXCEPTION.getCode();
		this.errorMsg = GinkgoCacheErrorCode.CACHE_EXCEPTION.getMsg();
	}

	public GinkgoCacheException(String errorCode, String errorMsg) {
		this.exception = new RuntimeException();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public GinkgoCacheException(GinkgoCacheErrorCode rscacheErrorCode) {
		this.exception = new RuntimeException();
		this.errorCode = rscacheErrorCode.getCode();
		this.errorMsg = rscacheErrorCode.getMsg();
	}
}