package nlp;

public class SentenceTokenizer {
	public static String[] tokenizeBasic(String str){		
		String regex = "[\\.\\?+!+]\\s+";
		return str.split(regex);				
	}
}
