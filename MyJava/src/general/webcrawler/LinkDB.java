package general.webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 用来保存已经访问过 Url 和待访问的 Url 的类
 */
public class LinkDB {

	// 已访问的 url 集合
	private static Set<String> visitedUrl = new HashSet<String>();
	// 待访问的 url 集合
	private static Queue<String> unVisitedUrl = new LinkedList<String>();

	public static Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	public static String unVisitedUrlDeQueue() {
		return unVisitedUrl.poll();
	}

	// 保证每个 url 只被访问一次
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url)&& !unVisitedUrl.contains(url))
			unVisitedUrl.add(url);
	}

	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}

	public static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isEmpty();
	}
}