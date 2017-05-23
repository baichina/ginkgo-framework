package io.ginkgo.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * gzip文件
 * 
 * @since 1.0.0
 * @author Barry
 */
public class GzipFile {

	/**
	 * 解压文件到当前文件夹
	 */
	public static void unGzip(String srcFile) {
		String ouputfile = "";
		try {
			// 建立gzip压缩文件输入流
			FileInputStream fin = new FileInputStream(srcFile);
			// 建立gzip解压工作流
			GZIPInputStream gzin = new GZIPInputStream(fin);
			// 建立解压文件输出流
			ouputfile = srcFile.substring(0, srcFile.lastIndexOf('.'));
//			ouputfile = ouputfile.substring(0, ouputfile.lastIndexOf('.'));
			FileOutputStream fout = new FileOutputStream(ouputfile);

			int num;
			byte[] buf = new byte[1024];
			while ((num = gzin.read(buf, 0, buf.length)) != -1) {
				fout.write(buf, 0, num);
			}
			gzin.close();
			fout.close();
			fin.close();
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return;
	}
}