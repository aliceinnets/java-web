package aliceinnets.web.spider.policy;

import aliceinnets.web.spider.Spider;

public class CrawlingOnce implements CrawlingPolicy {
	
	Spider spider;
	
	public CrawlingOnce(Spider spider) {
		this.spider = spider;
	}
	

	@Override
	public boolean shouldCrawl(String url) {
		if(!spider.getPagesVisited().contains(url) && !spider.getPagesToVisit().contains(url)) {
			return true;
		} else {
			return false;
		}
	}

}
