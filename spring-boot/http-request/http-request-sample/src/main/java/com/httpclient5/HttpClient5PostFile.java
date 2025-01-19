package com.httpclient5;

import java.io.File;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.FileEntity;

public class HttpClient5PostFile {

	public static void main(final String[] args) throws Exception {
		String params = "/Users/huyennv/params.json";
		try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
			final HttpPost httppost = new HttpPost("http://httpbin.org/post");
			// You can also use the FileEntity
			// final InputStreamEntity reqEntity = new InputStreamEntity(new
			// FileInputStream(params), -1, ContentType.APPLICATION_JSON);
			FileEntity reqEntity = new FileEntity(new File(params), ContentType.APPLICATION_JSON);

			httppost.setEntity(reqEntity);

			System.out.println("Execute request " + httppost.getMethod() + " " + httppost.getUri());
			try (final CloseableHttpResponse response = httpclient.execute(httppost)) {
				System.out.println("----------------------------------------");
				System.out.println(response.getCode() + " " + response.getReasonPhrase());
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
		}
	}

}
