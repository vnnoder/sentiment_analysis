package utils;

import weka.core.Instances;
import classifier.Classifier;

public class ClassifierTest {
	private Classifier classifier; 
	private Instances trainingSet;
	private Instances testingSet;
	public ClassifierTest(Classifier classifier, Instances trainingSet, Instances testingSet){
		this.classifier = classifier;
		this.trainingSet = trainingSet;
		this.testingSet = testingSet;
	}
	public void run() throws Exception{
		long start = System.currentTimeMillis();
		System.out.println("Start training...");				
		classifier.train(trainingSet);
		long trainingFinish = System.currentTimeMillis();
		System.out.println("Training took " + (trainingFinish - start) + " miliseconds");
		
		System.out.println("Start testing...");		
		classifier.test(testingSet);
	    long testingFinish = System.currentTimeMillis();
	    System.out.println("Testing took " + (testingFinish - trainingFinish) + " miliseconds");
	    	    	   
	    classifier.printTestResult();
	    classifier.printTestSummary(false);
	}
}
