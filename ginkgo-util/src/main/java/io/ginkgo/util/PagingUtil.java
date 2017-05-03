package io.ginkgo.util;

/**
 * 分页工具类
 * 
 * @since 1.0.0
 * @author Barry
 */
public class PagingUtil {

	/**
	 * 拼装mysql分页sql
	 * 
	 * @param sql
	 *            原sql
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	public static String getPagingSql4Mysql(String sql, Integer pageNo, Integer pageSize) {
		String pageSql = "select queryResult.* from ( ";
		pageSql += sql;
		pageSql += " ) queryResult limit " + (pageNo - 1) * pageSize + "," + pageSize;
		return pageSql;
	}

	/**
	 * 拼装mysql总数sql
	 * 
	 * @param sql
	 *            原sql
	 * @return
	 */
	public static String getCountSql4Mysql(String sql) {
		String pageSql = "select count(queryResult2.0) from ( ";
		pageSql += "select queryResult.*,0 from ( ";
		pageSql += sql;
		pageSql += ") queryResult ";
		pageSql += ") queryResult2 ";
		return pageSql;
	}
}