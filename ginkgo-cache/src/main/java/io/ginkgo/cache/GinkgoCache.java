package io.ginkgo.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * 
 * @since 1.0.0
 * @author Barry
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GinkgoCache {
	/**
	 * 永不过期
	 */
	public static final int NEVER_EXPIRE = -1;

	/**
	 * 缓存名称，如as:cache:product:%s
	 */
	String key();

	/**
	 * 缓存名称SPEL表达式，取自方法的参数，如#productId，%s=100001；多个参数用逗号隔开，如#productId,#date
	 * <p>
	 * 多个参数建议合成一个字符串，如"#pageNo + '-' + #pageSize"
	 * </p>
	 */
	String expr() default "";

	/**
	 * 到期时间，单位：秒
	 * <p>
	 * 默认到期时间30分钟
	 * </p>
	 */
	int expire() default 60 * 30;
}