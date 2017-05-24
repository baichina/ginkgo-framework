package io.ginkgo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List扩展工具类
 * 
 * @since 1.0.0
 * @author Barry
 */
public class ListUtil<T> {
	/**
	 * 按平均间隔(list.size() / num)，取固定个(num)元素
	 * 
	 * @param list
	 *            原列表
	 * @param num
	 *            数量
	 * @return num个元素的List
	 */
	public List<T> fetch(List<T> list, int num) {
		List<T> result = new ArrayList<T>();
		// 返回null
		if (list == null || list.isEmpty() || num <= 0) {
			return result;
		}

		int size = list.size();
		// num > size
		if (num >= size) {
			return list;
		}

		for (int i = 0; i < num; i++) {
			// 取首尾
			if (i == 0) {
				result.add(list.get(0));
			} else if (i == num - 1)
				result.add(list.get(size - 1));
			else {
				result.add(list.get(i * size / (num - 1)));
			}
		}
		return result;
	}
}