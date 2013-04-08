import utils.ClassifierTest;
import utils.DirectoryDatasetBuilder;
import weka.core.Instances;
import classifier.BaseClassifier;

public class SentimentCategorization {
	public static void main(String[] args) throws Exception {	    
		BaseClassifier polarityClassifier = new BaseClassifier("NaiveBayes", true);
		Instances trainingSet = new DirectoryDatasetBuilder("dataset/polarity_classifier/training").getDataSet();		
		Instances testingSet = new DirectoryDatasetBuilder("dataset/polarity_classifier/testing").getDataSet();
		ClassifierTest test = new ClassifierTest(polarityClassifier, trainingSet, testingSet);
		test.run();
	}
}
