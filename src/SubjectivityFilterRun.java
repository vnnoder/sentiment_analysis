import java.io.IOException;

import exception.NoClassifierException;

import utils.SubjectivityFilter;


public class SubjectivityFilterRun {
	public static void main(String[] args) throws IOException, NoClassifierException{
		SubjectivityFilter filter = new SubjectivityFilter("dataset/polarity_classifier/testing/");
		filter.execute();
	}
}	
