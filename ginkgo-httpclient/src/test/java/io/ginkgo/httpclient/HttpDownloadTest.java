package io.ginkgo.httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import io.ginkgo.file.GzipFile;

public class HttpDownloadTest {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// HttpDownload httpDownload = new HttpDownload();
		// httpDownload.downloadFile(
		// "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/d1f4_bdd2cb6510aceefa_page_20170509/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170511/cn-north-1/s3/aws4_request&X-Amz-Date=20170511T063505Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=c9d8d430473d87db003e82302363448b0b5ad93d73b611fe0da4bd5ab8044019",
		// "./down/", "1.zip");
		GzipFile.unGzip("./down/1.zip");
	}
}