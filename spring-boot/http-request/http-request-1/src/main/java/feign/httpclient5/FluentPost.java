package feign.httpclient5;

import java.io.IOException;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.message.BasicNameValuePair;

public class FluentPost {

	public static void main(String[] args) {
		String result = post("http://httpbin.org/post");
		System.out.println(result);
	}

	public static String post(String url) {
		String result = null;
		Request request = Request.post(url);
		// POST Body
		request.bodyForm(//
				new BasicNameValuePair("username", "wdbyte.com"), //
				new BasicNameValuePair("password", "secret") //
		);
		try {
			result = request.execute().returnContent().asString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
