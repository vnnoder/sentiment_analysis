import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import nlp.SentenceTokenizer;

import classifier.BaseClassifier;
import exception.NoClassifierException;
import weka.core.Instances;
import utils.ClassifierTest;
import utils.DirectoryDatasetBuilder;
import utils.FileDatasetBuilder;


public class SentimentWithSubjectivityDetector {
	public static void main(String[] args) throws Exception{
		BaseClassifier polarityClassifier = new BaseClassifier("NaiveBayes", true);
		Instances trainingSet = new DirectoryDatasetBuilder("dataset/polarity_classifier/training_subjectivity").getDataSet();		
		Instances testingSet = new DirectoryDatasetBuilder("dataset/polarity_classifier/testing_subjectivity").getDataSet();
		ClassifierTest test = new ClassifierTest(polarityClassifier, trainingSet, testingSet);
		test.run();				
	}
}
