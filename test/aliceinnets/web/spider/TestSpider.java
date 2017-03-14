package aliceinnets.web.spider;

import java.io.IOException;
import java.util.Arrays;
import org.jsoup.Jsoup;
import aliceinnets.util.OneLiners;
import aliceinnets.web.WebUtils;
import aliceinnets.web.spider.Spider;
import junit.framework.TestCase;

public class TestSpider extends TestCase {
	
	public void testSpider() {
		String url = "https://github.com/aliceinnets";
		int numPagesToSearch = 20;
		String[] words = new String[] { "alice", "github" };
		
		Spider spider = new Spider(url);
		for(int i=0;i<5;++i) {
			spider.crawl();
		}
		
		spider.crawl(numPagesToSearch);
		
		System.out.println(spider.getPagesToVisit());
		System.out.println(spider.getPagesVisited());
		
		for(String page : spider.getPagesCollected()) {
			try {
				System.out.println(page);
				System.out.println(Arrays.toString(WebUtils.getLinksOnPage(page)));
				System.out.println(Arrays.toString(words));
				System.out.println(Arrays.toString(OneLiners.countWords(Jsoup.connect(page).get().text(), words)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
