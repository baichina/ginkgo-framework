package io.ginkgo.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import io.ginkgo.jdbc.util.PagingUtil;

/**
 * 数据库基础操作
 * 
 * @author baiwei
 */
public class BaseDao {

	private static final Logger log = Logger.getLogger(BaseDao.class);

	/**
	 * 对象转为RowMapper
	 */
	private <T> RowMapper<T> objectToRowMapper(Class<T> outputClass) {
		return new RowMapper<T>() {
			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				return resultSetToObject(rs, outputClass);
			}
		};
	}

	/**
	 * 结果集转对象
	 */
	private <T> T resultSetToObject(ResultSet rs, Class<T> outputClass) {
		T bean = null;
		try {
			// 对象
			bean = outputClass.newInstance();
			// 数据
			ResultSetMetaData rsmd = rs.getMetaData();
			// 属性名
			// Field[] fields = outputClass.getFields();
			Field[] fields = getBeanFields(outputClass, null);
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i + 1);
				Object columnValue = rs.getObject(i + 1);
				for (Field field : fields) {
					if (field.getName().equalsIgnoreCase(columnName)) {
						/**
						 * 解决org.apache.commons.beanutils.ConversionException:
						 * No value specified for 'Date'
						 */
						BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
						BeanUtils.setProperty(bean, field.getName(), columnValue);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 获取属性（包含父类）
	 */
	@SuppressWarnings("rawtypes")
	private Field[] getBeanFields(Class cls, Field[] fs) {
		if (fs == null) {
			Field[] newFs = {};
			fs = newFs;
		}
		List<Field> list = new ArrayList<>(Arrays.asList(fs));
		Field[] addFs = cls.getDeclaredFields();
		if (addFs == null) {
			Field[] newAddFs = {};
			addFs = newAddFs;
		}
		list.addAll(Arrays.asList(addFs));
		Field[] allFs = new Field[list.size()];
		fs = list.toArray(allFs);

		if (cls.getSuperclass() != null) {
			fs = getBeanFields(cls.getSuperclass(), fs);
		}
		return fs;
	}

	/**
	 * 查询对象
	 */
	protected <T> T queryObject(JdbcTemplate jdbcTemplate, Class<T> outputClass, String sql, Object... args) {
		T t = null;
		try {
			t = jdbcTemplate.queryForObject(sql, objectToRowMapper(outputClass), args);
		} catch (Exception e) {
			log.error("queryObject=" + sql);
		}
		return t;
	}

	/**
	 * 查询数量
	 */
	protected Integer queryCount(JdbcTemplate jdbcTemplate, String sql, Object... args) {
		Integer count = 0;
		sql = PagingUtil.getCountSql4Mysql(sql);
		try {
			count = jdbcTemplate.queryForObject(sql, Integer.class, args);
		} catch (Exception e) {
			log.error("queryCount=" + sql);
			throw e;
		}
		return count;
	}

	/**
	 * 查询数量
	 * 
	 * @param jdbcTemplate
	 * @param sql
	 *            查询sql（并非select count(*)……）
	 * @return
	 */
	protected Integer queryCount(JdbcTemplate jdbcTemplate, String sql) {
		Integer count = 0;
		sql = PagingUtil.getCountSql4Mysql(sql);
		try {
			count = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {
			log.error("queryCount=" + sql);
			throw e;
		}
		return count;
	}

	/**
	 * 查询分页列表
	 */
	protected <T> List<T> queryList(JdbcTemplate jdbcTemplate, Class<T> outputClass, String sql, Integer pageNo,
			Integer pageSize) {
		List<T> list = null;
		sql = PagingUtil.getPagingSql4Mysql(sql, pageNo, pageSize);
		try {
			list = jdbcTemplate.query(sql, objectToRowMapper(outputClass));
		} catch (Exception e) {
			log.error("queryList=" + sql);
			throw e;
		}
		return list;
	}

	/**
	 * 查询分页列表
	 */
	protected <T> List<T> queryList(JdbcTemplate jdbcTemplate, Class<T> outputClass, String sql, Integer pageNo,
			Integer pageSize, Object... args) {
		List<T> list = null;
		sql = PagingUtil.getPagingSql4Mysql(sql, pageNo, pageSize);
		try {
			list = jdbcTemplate.query(sql, objectToRowMapper(outputClass), args);
		} catch (Exception e) {
			log.error("queryList=" + sql);
			throw e;
		}
		return list;
	}

	/**
	 * 插入对象
	 */
	// XXXX 需要解决类型转换问题
	// protected <T> Boolean insert(JdbcTemplate jdbcTemplate, T bean) {
	// int i = 0;
	// String sql = null;
	//
	// try {
	// // 属性名
	// Field[] fields = bean.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// // 访问私有变量，暴力操作
	// field.setAccessible(true);
	// try {
	// log.info("----" + field.getName() + "=====" + field.get(bean));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// i = jdbcTemplate.update(sql);
	// } catch (Exception e) {
	// log.error("insert=" + sql);
	// throw e;
	// }
	// if (i == 0) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	/**
	 * 插入
	 */
	protected <T> Boolean insert(JdbcTemplate jdbcTemplate, String sql, Object... args) {
		int i = 0;
		try {
			i = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			log.error("insert=" + sql);
			throw e;
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 插入，并返回这id，无参数
	 */
	protected Integer insertReturnId(JdbcTemplate jdbcTemplate, String sql) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 插入，并返回这id，有参数
	 */
	protected Integer insertReturnId(JdbcTemplate jdbcTemplate, PreparedStatementCreator preparedStatementCreator) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatementCreator, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 删除
	 */
	protected <T> Boolean delete(JdbcTemplate jdbcTemplate, String sql, Object... args) {
		int i = 0;
		try {
			i = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			log.error("delete=" + sql);
			throw e;
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 更新
	 */
	protected <T> Boolean update(JdbcTemplate jdbcTemplate, String sql, Object... args) {
		int i = 0;
		try {
			i = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			log.error("update=" + sql);
			throw e;
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}
}