package aliceinnets.web.spider.policy;

public interface CrawlingPolicy {
	
	public boolean shouldCrawl(String url);

}
