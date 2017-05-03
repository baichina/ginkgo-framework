package io.ginkgo.cache.exception;

/**
 * 缓存异常错误码
 * 
 * @since 1.0.0
 * @author Barry
 */
public enum GinkgoCacheErrorCode {

	SUCCESS("GINKGO_CACHE_000000", "成功"),

	CACHE_EXCEPTION("GINKGO_CACHE_999999", "缓存异常"),

	REDIS_EXCEPTION("GINKGO_CACHE_000001", "redis异常"),

	NOT_IMPLEMENTS_SERIALIZABLE("GINKGO_CACHE_000002", "缓存对象没有实现可序列化接口"),

	;

	private String code = null;

	private String msg = null;

	GinkgoCacheErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static GinkgoCacheErrorCode getByCode(String code) {
		if (code == null || code.isEmpty()) {
			return null;
		}

		GinkgoCacheErrorCode[] enums = GinkgoCacheErrorCode.values();
		for (GinkgoCacheErrorCode p : enums) {
			if (p.getCode().equals(code)) {
				return p;
			}
		}

		return null;
	}
}