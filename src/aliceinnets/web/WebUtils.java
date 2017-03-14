package aliceinnets.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebUtils {
	
	/** agent which spider will crawl with */
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	/** maximum body size of pages in MB */
	public static int maxBodySize = 0; // Unlimited body size
	/** connection time out in millisecond */
	public static int timeout = 60000;
	/** size of buffer in byte */
	public static int bufferSize = 2048;
	
	
	
	public final static String saveUrl(String url, String savePath) throws IOException {
		return saveUrl(url, savePath, bufferSize, timeout, userAgent);
	}
	
	
	public final static String saveUrl(String url, String savePath, int bufferSize, int timeout, String userAgent) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(timeout);
		connection.addRequestProperty("User-Agent", userAgent);
		int responseCode = connection.getResponseCode();
		
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String fileName;
			String disposition = connection.getHeaderField("Content-Disposition");
			String contentType = connection.getContentType();
			int contentLength = connection.getContentLength();
			
			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				} else {
					connection.disconnect();
					System.out.println("Failed to extract file name, content disposition was "+disposition);
					return null;
				}
			} else {
				// extracts file name from URL
				fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
			}

			System.out.println("Content type = " + contentType);
			System.out.println("Content disposition = " + disposition);
			System.out.println("Content length = " + contentLength);
			System.out.println("File name = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = connection.getInputStream();
			String saveFilePath = savePath + File.separator + fileName;
			
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			
			int bytesRead = -1;
			byte[] buffer = new byte[bufferSize];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			
			connection.disconnect();
			System.out.println("File downloaded");
			return saveFilePath;
		} else {
			connection.disconnect();
			System.out.println("No file to download. Server replied HTTP code: "+responseCode);
			return null;
		}
	}
	

	public final static List<String> getLinksListOnPage(String url) throws IOException {
		Document document = Jsoup.connect(url)
//				.header("Accept-Encoding", "gzip, deflate")
					.userAgent(userAgent)
					.maxBodySize(maxBodySize)
					.timeout(timeout)
					.get();
		
		Elements linksOnPage = document.select("a[href]");
		
		List<String> links = new LinkedList<String>();
		for(Element link : linksOnPage) {
			links.add(link.absUrl("href"));
		}	
		return links;
	}
	
	
	public final static String[] getLinksOnPage(String url) throws IOException {
		List<String> list = getLinksListOnPage(url);
		return list.toArray(new String[list.size()]);
	}
	

}
