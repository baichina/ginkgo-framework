package io.ginkgo.httpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.ginkgo.file.GinkgoFile;

/**
 * HTTP下载
 * 
 * @since 1.0.0
 * @author Barry
 */
public class HttpDownload {

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            下载地址
	 * @param localPath
	 *            保存路径
	 * @param localPath
	 *            文件名
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void downloadFile(String url, String localPath, String fileName)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		GinkgoFile ginkgoFile = new GinkgoFile(localPath + fileName);
		try {
			FileOutputStream fos = new FileOutputStream(ginkgoFile);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fos.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真
			}
			fos.flush();
			fos.close();
		} finally {
			in.close();
			response.close();
			httpclient.close();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            下载地址
	 * @param localPath
	 *            保存路径
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void downloadFile(String url, String localPath) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		GinkgoFile ginkgoFile = new GinkgoFile(localPath + getFileName(response));
		try {
			FileOutputStream fos = new FileOutputStream(ginkgoFile);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fos.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真
			}
			fos.flush();
			fos.close();
		} finally {
			in.close();
			response.close();
			httpclient.close();
		}
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * 
	 * @param response
	 * @return
	 */
	public static String getFileName(CloseableHttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						// filename = new
						// String(param.getValue().toString().getBytes(),
						// "utf-8");
						// filename=URLDecoder.decode(param.getValue(),"utf-8");
						filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filename;
	}
}
