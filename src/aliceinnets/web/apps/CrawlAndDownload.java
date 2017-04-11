package aliceinnets.web.apps;

import java.io.File;
import java.io.IOException;

import aliceinnets.util.OneLiners;
import aliceinnets.web.WebUtils;
import aliceinnets.web.spider.Spider;
import aliceinnets.web.spider.policy.CrawlingOnce;
import aliceinnets.web.spider.policy.CrawlingPolicy;
import aliceinnets.web.spider.policy.CrawlingPolicyAnd;
import aliceinnets.web.spider.policy.CrawlingUrlContainsWord;

public class CrawlAndDownload {
	
	public static void main(String[] args) {
//		saveAllMp3FromHmix();
		saveAllZipFromSlos();
	}
	
	public static void saveAllZipFromSlos() {
		String url = "http://slos.biz/";
		
		String folder = System.getProperty("user.home")+File.separator+"Downloads"+File.separator+"slos"+File.separator;
		OneLiners.mkdirs(folder);
		
//		Spider spider = new Spider(url);
//		CrawlingPolicy crawlingPolicy = new CrawlingPolicyAnd(new CrawlingOnce(spider), new CrawlingUrlContainsWord(".zip"));
//		spider.setCrawlingPolicy(crawlingPolicy);
//		spider.crawl();
//		System.out.println(spider.getPagesToVisit());
		
		saveAll(url, folder, "zip", 3);
	}
	
	public static void saveAllMp3FromHmix() {
		String url = "http://www.hmix.net/music_gallery/list/";
		
		String folder = System.getProperty("user.home")+File.separator+"Downloads"+File.separator+"hmix"+File.separator;
		OneLiners.mkdirs(folder);
		
		Spider spider = new Spider(url);
		CrawlingPolicy crawlingPolicy = new CrawlingPolicyAnd(new CrawlingOnce(spider), new CrawlingUrlContainsWord(url));
		spider.setCrawlingPolicy(crawlingPolicy);
		spider.crawl(100);
		
		for(String page : spider.getPagesVisited()) {
			if(page.equals(url)) continue;
			
			if(!page.contains("#")) {
				saveAll(page, folder, "mp3", 3);
			}
		}
	}
	
	public static void saveAll(String url, String folder, String ext, int numAttempts) {
		Spider spider = new Spider(url);
		CrawlingPolicy crawlingPolicy = new CrawlingPolicyAnd(new CrawlingOnce(spider), new CrawlingUrlContainsWord("."+ext));
		spider.setCrawlingPolicy(crawlingPolicy);
		spider.crawl();
		for(String file : spider.getPagesToVisit()) {
			if(new File(folder+file.substring(file.lastIndexOf("/")+1)).exists()) {
				System.out.println("File is already exist, at "+folder+file.substring(file.lastIndexOf("/")+1));
			} else {
				for(int i=0;i<numAttempts;++i) {
					try {
						WebUtils.saveUrl(file, folder);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
