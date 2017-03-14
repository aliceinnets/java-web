package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

public interface CollectingPolicy {
	
	public boolean shouldCollect(Document document);

}
