package general.webcrawler.helloword;

import general.datastructure.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Intro to the methods of URL class and URLConnection class
 * 
 * @author yazhoucao
 * 
 */
public class URLIntro {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "http://en.wikipedia.org/wiki/Threaded_binary_tree#cite_note-1";
		introURL(path);
		StdOut.println("\n");
		introURLConnection(path);

		StdOut.println();
	}

	/**
	 * Class URL represents a Uniform Resource Locator, a pointer to a
	 * "resource" on the World Wide Web. A resource can be something as simple
	 * as a file or a directory, or it can be a reference to a more complicated
	 * object, such as a query to a database or to a search engine.
	 * 
	 * @param path
	 */
	public static void introURL(String path) {
		boolean printHTML = false;
		try {
			StdOut.println("/************************** URL class introduction **************************/");
			URL url = new URL(path);
			StdOut.println("getDefaultPort:\t" + url.getDefaultPort());
			StdOut.println("getPort:\t" + url.getPort()
					+ "\t\t(the port number, or -1 if the port is not set)");
			StdOut.println("getHost:\t" + url.getHost());
			StdOut.println("getAuthority:\t" + url.getAuthority());
			StdOut.println("getProtocol:\t" + url.getProtocol());
			StdOut.println("getQuery:\t"
					+ url.getQuery()
					+ "\t\t(the query part of this URL, or null if one does not exist)");
			StdOut.println("getRef:\t\t"
					+ url.getRef()
					+ "\t(Gets the anchor, also known as the 'reference' of this URL.)");
			StdOut.println("getUserInfo:\t" + url.getUserInfo());
			StdOut.println("toExternalForm:\t" + url.toExternalForm());
			StdOut.println("getContent:\t" + url.getContent().toString());
			StdOut.println("\tGets the contents of this URL. \n\tThis method is a shorthand for:openConnection().getContent()");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			StdOut.println("openStream:\tHTML content: " + br.readLine()
					+ ".......");
			StdOut.println("\tOpens a connection to this URL and returns an InputStream for reading from that connection. \n\tThis method is a shorthand for:openConnection().getInputStream().");

			String line;
			while (printHTML && (line = br.readLine()) != null)
				StdOut.println(line);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The abstract class URLConnection is the superclass of all classes that
	 * represent a communications link between the application and a URL.
	 * Instances of this class can be used both to read from and to write to the
	 * resource referenced by the URL. In general, creating a connection to a
	 * URL is a multistep process:
	 * 1.openConnection()
	 * 2.connect()
	 * 
	 * @param path
	 */
	public static void introURLConnection(String path) {
		boolean printHTML = false;
		try {
			StdOut.println("/************************** URLConnection class introduction **************************/");
			StdOut.println("The abstract class URLConnection is the superclass of all classes that represent a communications link between the application and a URL.");
			StdOut.println("Direct Known Subclasses: HttpURLConnection, JarURLConnection");
			StdOut.println("In general, creating a connection to a URL is a multistep process:");
			StdOut.println("\t1.openConnection()");
			StdOut.println("\t2.connect()");
			URL url = new URL(path);
			URLConnection conn = url.openConnection();
			// URLConnection conn = new URLConnection(url); //Wrong, this is an
			// abstract class
			conn.connect();

			StdOut.println("\nMethods: ");
			Map<String, List<String>> headerFields = conn.getHeaderFields();
			StdOut.println("getHeaderFields:\t");
			for (Map.Entry<String, List<String>> entry : headerFields
					.entrySet())
				StdOut.println("\t" + entry.getKey() + ":\t"
						+ entry.getValue().toString());
			StdOut.println("getContentLength:\t"
					+ conn.getContentLength()
					+ "\n\tReturns the value of the content-length header field. \n\t-1 if the content length is not known, or if the content length is greater than Integer.MAX_VALUE.");
			StdOut.println("getContentLengthLong:\t"
					+ conn.getContentLengthLong()
					+ "\n\tReturns the value of the content-length header field as a long.\n\t-1 if the content length is not known.");
			StdOut.println("getConnectTimeout:\t"
					+ conn.getConnectTimeout()
					+ "\n\t0 return implies that the option is disabled (i.e., timeout of infinity).");
			StdOut.println("getLastModified:\t" + conn.getLastModified()
					+ "\tconverted:" + new Date(conn.getLastModified()));
			StdOut.println("getExpiration:\t\t"
					+ conn.getExpiration()
					+ "\tconverted:"
					+ new Date(conn.getExpiration())
					+ "\n\tthe expiration date of the resource that this URL references, or 0 if not known.");
			StdOut.println("getContentEncoding:\t" + conn.getContentEncoding());
			StdOut.println("getContentType:\t\t" + conn.getContentType());

			StdOut.println("getInputStream:\tHTML content:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while (printHTML && (line = br.readLine()) != null)
				StdOut.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
