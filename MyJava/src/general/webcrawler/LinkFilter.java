package general.webcrawler;

public interface LinkFilter {
	public boolean accept(String url);
}