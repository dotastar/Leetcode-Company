package general.webcrawler.helloword;

public interface LinkFilter {
	public boolean accept(String url);
}