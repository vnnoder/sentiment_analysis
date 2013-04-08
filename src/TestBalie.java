import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import ca.uottawa.balie.Balie;
import ca.uottawa.balie.TokenList;
import ca.uottawa.balie.TokenListIterator;
import ca.uottawa.balie.Tokenizer;


public class TestBalie {
	public static void main(String[] args) throws FileNotFoundException{
		String strTest = new Scanner(new File("dataset/polarity_classifier/all/neg/cv000_29416.txt")).useDelimiter("\\A").next();
		System.out.println("==================Balie==================");
		tokenizeBalie(strTest);
		System.out.println("==================Normal==================");
		tokenizeNormal(strTest);
	}
	
	public static void tokenizeBalie(String strTest){
		Tokenizer tokenizer = new Tokenizer(Balie.LANGUAGE_ENGLISH, true);						
		tokenizer.Tokenize(strTest);
		TokenList alTokenList = tokenizer.GetTokenList();
		System.out.println("Number of sentences: " + tokenizer.SentenceCount());
		for (int i = 0; i < tokenizer.SentenceCount(); i++){
			System.out.println("Sentence " + i + ":\t" + alTokenList.SentenceText(i, false, false));
		}		
	}
	
	public static void tokenizeNormal(String strTest){
		String regex = "[\\.\\?+!+]\\s+";
		String[] sentences  = strTest.split(regex);
		for (int i = 0; i < sentences.length; i++){
			System.out.println("Sentence " + i + ":\t" + sentences[i]);
		}
	}
	
	
}
