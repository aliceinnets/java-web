package aliceinnets.web.spider.policy;

public class CrawlingPolicyAnd implements CrawlingPolicy {
	
	private CrawlingPolicy condition1;
	private CrawlingPolicy condition2;
	
	
	public CrawlingPolicyAnd(CrawlingPolicy condition1, CrawlingPolicy condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	

	@Override
	public boolean shouldCrawl(String url) {
		if(condition1.shouldCrawl(url) && condition2.shouldCrawl(url)) {
			return true;
		} else {
			return false;
		}
	}


	public CrawlingPolicy getCondition1() {
		return condition1;
	}


	public void setCondition1(CrawlingPolicy condition1) {
		this.condition1 = condition1;
	}


	public CrawlingPolicy getCondition2() {
		return condition2;
	}


	public void setCondition2(CrawlingPolicy condition2) {
		this.condition2 = condition2;
	}

}
