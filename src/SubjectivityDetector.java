import utils.ClassifierTest;
import utils.FileDatasetBuilder;
import weka.core.Instances;
import classifier.BaseClassifier;


public class SubjectivityDetector {
	public static void main(String[] args) throws Exception{
		BaseClassifier subjectivityDetector = new BaseClassifier("NaiveBayes",true);			
		Instances trainingSet = new FileDatasetBuilder("dataset/subjectivity_detector/all/").getDataSet();		
		Instances testingSet = new FileDatasetBuilder("dataset/subjectivity_detector/test/").getDataSet();
		ClassifierTest test = new ClassifierTest(subjectivityDetector, trainingSet, testingSet);
		test.run();	 
	}
}
