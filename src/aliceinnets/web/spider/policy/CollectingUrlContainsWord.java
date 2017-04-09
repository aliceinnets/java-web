package aliceinnets.web.spider.policy;

import org.jsoup.nodes.Document;

import aliceinnets.util.OneLiners;

public class CollectingUrlContainsWord implements CollectingPolicy {
	
	private String word;
	private int minRepetitions;
	
	private boolean caseSensitive;
	private boolean wholeWord;
	private boolean excludeNonWordChars;
	
	public CollectingUrlContainsWord(String word) {
		this(word, 1);
	}
	
	
	public CollectingUrlContainsWord(String word, int minRepetitions) {
		this.word = word;
		this.minRepetitions = minRepetitions;
		
		this.caseSensitive = false;
		this.wholeWord = false;
		this.excludeNonWordChars = false;
	}
	

	@Override
	public boolean shouldCollect(Document document) {
		String url = document.baseUri();
		if(OneLiners.countWord(url, word, caseSensitive, wholeWord, excludeNonWordChars) >= minRepetitions) {
			return true;
		} else {
			return false;
		}
 	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public int getMinRepetitions() {
		return minRepetitions;
	}


	public void setMinRepetitions(int minRepetitions) {
		this.minRepetitions = minRepetitions;
	}


	public boolean isCaseSensitive() {
		return caseSensitive;
	}


	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}


	public boolean isWholeWord() {
		return wholeWord;
	}


	public void setWholeWord(boolean wholeWord) {
		this.wholeWord = wholeWord;
	}


	public boolean isExcludeNonWordChars() {
		return excludeNonWordChars;
	}


	public void setExcludeNonWordChars(boolean excludeNonWordChars) {
		this.excludeNonWordChars = excludeNonWordChars;
	}

}
