package aliceinnets.web.spider;

import java.io.File;
import java.io.IOException;

import aliceinnets.util.OneLiners;
import aliceinnets.web.WebUtils;
import aliceinnets.web.spider.policy.CrawlingOnce;
import aliceinnets.web.spider.policy.CrawlingPolicy;
import aliceinnets.web.spider.policy.CrawlingPolicyAnd;
import aliceinnets.web.spider.policy.CrawlingUrlContainsWord;

public class CrawlAndDownload {
	
	public static void main(String[] args) {
		String url = "http://www.hmix.net/music_gallery/list/";
		
		String folder = System.getProperty("user.home")+File.separator+"Downloads"+File.separator+"hmix"+File.separator;
		OneLiners.mkdirs(folder);
		
		saveAllMp3(url, folder);
	}
	
	public static void saveAllMp3(String url, String folder) {
		Spider spider = new Spider(url);
		CrawlingPolicy crawlingPolicy = new CrawlingPolicyAnd(new CrawlingOnce(spider), new CrawlingUrlContainsWord(url));
		spider.setCrawlingPolicy(crawlingPolicy);
		spider.crawl(100);
		
		for(String page : spider.getPagesVisited()) {
			if(page.equals(url)) continue;
			
			if(!page.contains("#")) {
				spider = new Spider(page);
				crawlingPolicy = new CrawlingPolicyAnd(new CrawlingOnce(spider), new CrawlingUrlContainsWord(".mp3"));
				spider.setCrawlingPolicy(crawlingPolicy);
				spider.crawl();
				for(String mp3 : spider.getPagesToVisit()) {
					try {
						WebUtils.saveUrl(mp3, folder);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
