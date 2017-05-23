package io.ginkgo.file;

import java.io.IOException;

/**
 * 文件
 * 
 * @since 1.0.0
 * @author Barry
 */
public class GinkgoFileTest {

	public static void main(String[] args) {
		GinkgoFile gf = new GinkgoFile("./file/2017/5/11/1.txt");
		try {
			gf.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}