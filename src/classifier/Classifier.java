package classifier;

import weka.core.Instances;

public interface Classifier {
	void train(Instances trainingSet) throws Exception;
	void test(Instances testingSet) throws Exception;
	void printTestResult();
	void printTestSummary(boolean includeExample);
}
