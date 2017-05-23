package io.ginkgo.file;

/**
 * 文件
 * 
 * @since 1.0.0
 * @author Barry
 */
public class GinkgoFile extends java.io.File {

	private static final long serialVersionUID = 6670995224463977395L;

	public GinkgoFile(String pathname) {
		super(pathname);
		// 如果父级文件夹不存在，则创建之
		if (!this.getParentFile().exists()) {
			this.getParentFile().mkdirs();
		}
	}
}