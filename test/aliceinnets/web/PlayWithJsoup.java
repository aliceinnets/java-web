package aliceinnets.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PlayWithJsoup {
	
	public static void main(String[] args) {
		try {
			String url = "http://www.hmix.net/music_gallery/music_top.htm";
			Document document = Jsoup.connect(url).get();
			System.out.println(document.baseUri());
			System.out.println(document.baseUri().equals(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
