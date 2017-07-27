package aliceinnets.web.apps;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

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
//		saveAllZipFromSlos();
		saveAllFiles("http://grammars.grlmc.com/DeepLearn2017/materials/#", "");
	}
	
	public static void saveAllFiles(String url, String folder) {
//		OneLiners.mkdirs(folder);
		
		try {
			Document document = Jsoup.connect(url).get();
			System.out.println(document);
			System.out.println(document.getElementById("userName"));
			System.out.println(document.getElementById("userPassword"));
			System.out.println(document.getElementById("userName").attr("placeholder", URLEncoder.encode("sehyun.kwak@ipp.mpg.de", "UTF-8")));
			System.out.println(document.getElementById("userPassword").attr("placeholder", URLEncoder.encode("a95c08f043", "UTF-8")));
			
			System.out.println(document);
			
			
			URL url2 = new URL(url);
			URLConnection connection = url2.openConnection();
			connection.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(document.toString());
			wr.flush();
			wr.close();

			String responseCode = connection.getContentType();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + document.toString());
			System.out.println("Response Code : " + responseCode);
			Document document2 = Jsoup.parse(url2, 30000);
			System.out.println(document2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.exit(1);
		Spider spider = new Spider(url);
		spider.crawl(100);
		
		for(String page : spider.getPagesVisited()) {
			System.out.println(page);
//			if(page.equals(url)) continue;
//			
//			if(!page.contains("#")) {
//				saveAll(page, folder, "mp3", 3);
//			}
		}
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
