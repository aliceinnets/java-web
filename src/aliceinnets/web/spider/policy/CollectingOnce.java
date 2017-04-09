package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

import aliceinnets.web.spider.Spider;

public class CollectingOnce implements CollectingPolicy {
	
	Spider spider;
	
	public CollectingOnce(Spider spider) {
		this.spider = spider;
	}
	

	@Override
	public boolean shouldCollect(Document document) {
		if(!spider.getPagesCollected().contains(document.baseUri())) {
			return true;
		} else {
			return false;
		}
	}

}
