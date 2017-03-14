package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

public class CollectingNone implements CollectingPolicy {

	@Override
	public boolean shouldCollect(Document document) {
		return false;
	}

}
