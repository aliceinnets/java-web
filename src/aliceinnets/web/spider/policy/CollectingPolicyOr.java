package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

public class CollectingPolicyOr implements CollectingPolicy {
	
	private CollectingPolicy condition1;
	private CollectingPolicy condition2;
	
	
	public CollectingPolicyOr(CollectingPolicy condition1, CollectingPolicy condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	

	@Override
	public boolean shouldCollect(Document document) {
		if(condition1.shouldCollect(document) || condition2.shouldCollect(document)) {
			return true;
		} else {
			return false;
		}
	}


	public CollectingPolicy getCondition1() {
		return condition1;
	}


	public void setCondition1(CollectingPolicy condition1) {
		this.condition1 = condition1;
	}


	public CollectingPolicy getCondition2() {
		return condition2;
	}


	public void setCondition2(CollectingPolicy condition2) {
		this.condition2 = condition2;
	}

}
