package general.webcrawler;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		helloWorld();
	}

	public static void helloWorld() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://www.google.com/"); //"http://" is required
		try (CloseableHttpResponse response = client.execute(get)) {

			System.out.println(response.toString());

			System.out.println(response.getProtocolVersion());
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getStatusLine().getReasonPhrase());
			System.out.println(response.getStatusLine().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
