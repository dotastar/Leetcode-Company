package general.webcrawler.helloword;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PageDownloader {

	/**
	 * 根据 url 和网页类型生成需要保存的网页的文件名
	 * 去除掉 url 中非文件名字符
	 */
	public String getFileNameByUrl(String url, String contentType) {
		url = url.substring(7);// remove http://
		if (contentType.indexOf("html") != -1) { // text/html
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		} else { // 如application/pdf
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "."
					+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	/* 下载 url 指向的网页 */
	public String downloadAsFile(String url) {
		String filePath = null;
		/* 1.生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = HttpClients.createDefault();

		/* 2.生成 GetMethod 对象并设置参数 */
		HttpGet getMethod = new HttpGet(url);

		/* 3.执行 HTTP GET 请求 */
		try {
			HttpResponse res = httpClient.execute(getMethod);
			int statusCode = res.getStatusLine().getStatusCode();
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + res.getStatusLine());
				filePath = null;
			}
			/* 4.处理 HTTP 响应内容 */
			HttpEntity entity = res.getEntity();
			if (entity != null && entity.getContentType().getValue().startsWith("text/html")) {
				String content = EntityUtils.toString(entity);	// String content of page
				System.out.println(content.indexOf("software engineer"));
				System.out.println(content);
				filePath = "/Users/yazhoucao/crawler/" + getFileNameByUrl(url, entity.getContentType().getValue());
				//entity.writeTo(new FileOutputStream(new File(filePath)));
			}
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return filePath;
	}

	// 测试的 main 方法
	public static void main(String[] args) {
		PageDownloader downLoader = new PageDownloader();
		System.out.println(downLoader.downloadAsFile("https://www.google.com/#newwindow=1&q=software+engineer"));
	}
}