package aliceinnets.web.spider;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import aliceinnets.web.spider.policy.CollectingAll;
import aliceinnets.web.spider.policy.CollectingPolicy;
import aliceinnets.web.spider.policy.CrawlingPolicy;
import aliceinnets.web.spider.policy.CrawlingOnce;

/**
 * This class crawls web pages link to link and collects urls  
 * under {@link CrawlingPolicy} and {@link CollectingPolicy}.
 * 
 * @author alice &lt;aliceinnets[at]gmail.com&gt;
 *
 */
public class Spider {
	
	/** agent which spider will crawl with */  
	String userAgent;
	/** maximum body size of pages in MB */
	int maxBodySize;
	/** connection time out in millisecond */
	int timeout;
	
	List<String> pagesToVisit = new LinkedList<String>();
	List<String> pagesVisited = new LinkedList<String>();
	List<String> pagesCollected = new LinkedList<String>();
	List<String> pagesFailedToVisit = new LinkedList<String>();
	
	CrawlingPolicy crawlingPolicy;
	CollectingPolicy collectingPolicy;
	
	
	public Spider() {
		this(null, null, null);
	}
	
	
	/**
	 * 
	 * 
	 * @param url
	 */
	public Spider(String url) {
		this(url, null, null);
		
	}
	
	/**
	 * 
	 * 
	 * @param url
	 * @param crawlingPolicy
	 * @param collectingPolicy
	 */
	public Spider(String url, CrawlingPolicy crawlingPolicy, CollectingPolicy collectingPolicy) {
		userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		maxBodySize = 0; // Unlimited body size
		timeout = 60000;
		
		if(url != null && !url.equals("")) {
			pagesToVisit.add(url);
		}
		
		if(crawlingPolicy != null) {
			this.crawlingPolicy = crawlingPolicy;
		} else {
			this.crawlingPolicy = new CrawlingOnce(this);
		}
		
		if(collectingPolicy != null) {
			this.collectingPolicy = collectingPolicy;
		} else {
			this.collectingPolicy = new CollectingAll();
		}
		
	}
	
	
	public void crawl(int numPagesToVisit) {
		int numPagesVisited = 0;
		while(numPagesVisited < numPagesToVisit) {
			if(pagesToVisit.isEmpty()) {
				System.out.println("No pages to visit");
				break;
			}
			
			crawl(pagesToVisit.remove(0));
			++numPagesVisited;
		}
	}
	
	
	public void crawl() {
		crawl(1);
	}
	
	
	public void crawl(String url) {
		try {
			Document document = Jsoup.connect(url)
//					.header("Accept-Encoding", "gzip, deflate")
					.userAgent(userAgent)
					.maxBodySize(maxBodySize)
					.timeout(timeout)
					.get();
			
			pagesVisited.add(url);
			System.out.println(String.format("visited page, %s", url));
			
			Elements linksOnPage = document.select("a[href]");
			List<String> links = new LinkedList<String>();
			for(Element link : linksOnPage) {
				links.add(link.absUrl("href"));
			}
			
			for(String link : links) {
				if(crawlingPolicy.shouldCrawl(link)) {
					pagesToVisit.add(link);
				}
			}
			
			if(collectingPolicy.shouldCollect(document)) {
				pagesCollected.add(url);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			pagesFailedToVisit.add(url);
			System.out.println(String.format("failed to visit page, %s", url));
		}
	}


	public String getUserAgent() {
		return userAgent;
	}


	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}


	public int getMaxBodySize() {
		return maxBodySize;
	}


	public void setMaxBodySize(int maxBodySize) {
		this.maxBodySize = maxBodySize;
	}


	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public CrawlingPolicy getCrawlingPolicy() {
		return crawlingPolicy;
	}


	public void setCrawlingPolicy(CrawlingPolicy crawlingCondition) {
		this.crawlingPolicy = crawlingCondition;
	}


	public CollectingPolicy getCollectingPolicy() {
		return collectingPolicy;
	}


	public void setCollectingPolicy(CollectingPolicy collectingPolicy) {
		this.collectingPolicy = collectingPolicy;
	}


	public List<String> getPagesToVisit() {
		return pagesToVisit;
	}


	public List<String> getPagesVisited() {
		return pagesVisited;
	}


	public List<String> getPagesCollected() {
		return pagesCollected;
	}


	public List<String> getPagesFailedToVisit() {
		return pagesFailedToVisit;
	}
	

}
