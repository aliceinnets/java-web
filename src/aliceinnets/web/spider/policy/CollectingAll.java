package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

public class CollectingAll implements CollectingPolicy {

	@Override
	public boolean shouldCollect(Document document) {
		return true;
	}
	
}
