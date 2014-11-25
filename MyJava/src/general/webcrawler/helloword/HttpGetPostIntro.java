package general.webcrawler.helloword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpGetPostIntro {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		httpGetIntro();
	}

	public static void httpGetIntro() {
		CloseableHttpClient client = HttpClients.createDefault();
		// protocol "http://" is required
		HttpGet get = new HttpGet("http://www.google.com/"); 
		HttpResponse response;
		try {
			response = client.execute(get);
			System.out.println(response.toString());
			System.out.println(response.getProtocolVersion());
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getStatusLine().getReasonPhrase());
			System.out.println(response.getStatusLine().toString());
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();	
			// If the response does not enclose an entity, there is no need
			// to bother about connection release
			if (entity != null
					&& entity.getContentType().getValue()
							.startsWith("text/html")) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(entity.getContent()))) {
					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
				} catch (RuntimeException rte) {
					// In case of an unexpected exception you may want to abort
					// the HTTP request in order to shut down the underlying
					// connection and release it back to the connection manager.
					get.abort();
					throw rte;
				}
			} else {
				// Reponse is not HTML, read it from the InputStream -
				// entity.getContent() -
				// And do something with it like save it.
				System.out.println("Non-HTML content, ignored.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
