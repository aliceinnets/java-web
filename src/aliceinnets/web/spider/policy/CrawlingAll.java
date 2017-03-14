package aliceinnets.web.spider.policy;

public class CrawlingAll implements CrawlingPolicy {

	@Override
	public boolean shouldCrawl(String url) {
		return true;
	}

}
