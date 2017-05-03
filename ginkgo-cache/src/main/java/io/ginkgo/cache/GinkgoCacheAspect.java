package io.ginkgo.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import io.ginkgo.cache.exception.GinkgoCacheErrorCode;
import io.ginkgo.cache.exception.GinkgoCacheException;
import io.ginkgo.cache.redis.RedisService;

/**
 * 缓存AOP
 * <p>
 * 如果是类内调用 <br/>
 * 使用SpringContextLoaderListener.getContext().getBean(${beanId}).方法名
 * </p>
 * 
 * @since 1.0.0
 * @author Barry
 */
@Component
@Aspect
public class GinkgoCacheAspect {

	private static final Logger log = Logger.getLogger(GinkgoCacheAspect.class);

	@Autowired
	private RedisService<Object> redisService;

	/**
	 * 定义缓存逻辑
	 */
	@Around("@annotation(io.ginkgo.cache.GinkgoCache)")
	public Object cache(ProceedingJoinPoint pjp) {
		// 结果
		Object result = null;
		// 是否是业务异常
		boolean isBusinessException = false;
		try {
			// 先从redis获取数据
			Method method = getMethod(pjp);
			GinkgoCache cache = method.getAnnotation(io.ginkgo.cache.GinkgoCache.class);
			String key = cache.key();
			if (!"".equals(cache.expr())) {
				String expr = parseExpr(cache.expr(), method, pjp.getArgs());
				key = String.format(cache.key(), expr);
			}
			result = redisService.getObj(key);

			// 没有获取到数据，执行实际方法
			if (result == null) {
				// TODO 解决雪崩与穿透
				try {
					result = pjp.proceed();
				} catch (Exception e) {
					// 执行方法异常
					isBusinessException = true;
					throw e;
				}
				if (result == null) {
					// 不缓存空数据，但不报错，返回null
					log.info("racache value is null!");
					return null;
				}
				if (!(result instanceof Serializable)) {
					throw new GinkgoCacheException(GinkgoCacheErrorCode.NOT_IMPLEMENTS_SERIALIZABLE);
				}
				if (GinkgoCache.NEVER_EXPIRE == cache.expire()) {
					// 永不过期
					redisService.setObj(key, result);
				} else {
					redisService.setObj(key, result, cache.expire());
				}
			}
		} catch (Throwable e) {
			log.debug("缓存异常", e);
			if (isBusinessException) {
				try {
					throw e;
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
				throw new GinkgoCacheException(GinkgoCacheErrorCode.CACHE_EXCEPTION);
			}
		}
		return result;
	}

	// /** * 定义清除缓存逻辑 */
	// @Around(value="@annotation(org.myshop.cache.annotation.CacheEvict)")
	// public Object evict(ProceedingJoinPoint pjp ){
	// //和cache类似，使用Jedis.hdel()删除缓存即可...
	// }

	/**
	 * 获取被拦截方法对象
	 * 
	 * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上
	 * 所以应该使用反射获取当前对象的方法对象
	 */
	public Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		@SuppressWarnings("rawtypes")
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				args[i] = "";
			}
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 获取缓存的key key 定义在注解上，支持SPEL表达式
	 * 
	 * @param key
	 *            参数表达式
	 * @param method
	 *            方法
	 * @param args
	 *            参数值
	 * @return
	 */
	private String parseExpr(String expr, Method method, Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);
		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}

		return parser.parseExpression(expr).getValue(context, String.class);
	}
}