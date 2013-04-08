package classifier;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.unsupervised.attribute.StringToWordVector;
import exception.ClassifierNotTrainedException;
import exception.NoClassifierException;
import exception.NullDistributionException;

public class BaseClassifier implements Classifier{
	protected FilteredClassifier model;
	protected ArrayList<Instance>[][] testResult;
	protected boolean trained;
	
	public BaseClassifier(){
		this("NaiveBayes",true);
	}
	
	public BaseClassifier(String classifierMethod, boolean useStopList){		
	    model = new FilteredClassifier();
	    StringToWordVector stringtowordvector = new StringToWordVector();
	    //stringtowordvector.setStemmer(new LovinsStemmer());
	    if (useStopList) 
	    	stringtowordvector.setUseStoplist(true);	    
	    model.setFilter(stringtowordvector);
	    if (classifierMethod == "NaiveBayes")
	    	model.setClassifier(new NaiveBayes());
	    
	}
	
	
	
	public void train(Instances training) throws IOException, NoClassifierException{					    
	    //System.out.println("\n\nTraining data:\n\n" + training);
	    try {
	    	this.model.buildClassifier(training);
	    	this.trained = true;
	    } catch (Exception e){
	    	throw new NoClassifierException("No classifier is defined");
	    }	    
	}
	
	public Instance classify(Instance instance) throws Exception{
		if (!trained){
			throw new ClassifierNotTrainedException("The classifier is not trained yet.");
		}
		instance.setClassMissing();
		double cls = model.classifyInstance(instance);
		instance.setClassValue(cls);
		return instance;
	}
		
	public void test(Instances test) throws IOException, NullDistributionException, ClassifierNotTrainedException{		
		if (!trained){
			throw new ClassifierNotTrainedException("The classifier is not trained yet.");
		}
		
		
	    ArrayList<Instance>[][] results = new ArrayList[2][2];
	    for (int i = 0; i < results.length; i++)
	    	for (int j = 0; j < results[0].length; j++)
	    		results[i][j] = new ArrayList<Instance>();
	    
	    //System.out.println("Testing result");
	    for (int i = 0; i < test.numInstances(); i++)
	    {
	    	
	    	Instance instance = test.instance(i);
	    	double originalClassValue = instance.classValue();
	    	instance.setClassMissing();
	    	//System.out.println("instance " + i + ": " + instance);
	    	try{
	    		double cls = model.classifyInstance(instance);
	    		instance.setClassValue(cls);	    			    		
	    		results[(int)Math.round(originalClassValue)][(int)Math.round(cls)].add(instance);
	    		System.out.println("instance " + i + ": " + "original class: " + (int)Math.round(originalClassValue) + "\t classfied class: " + (int)Math.round(cls));
	    	}catch(Exception e){
	    		throw new NullDistributionException();
	    	}
	    }
	    
	    this.testResult = results;	    
	}
	
	public void printTestResult(){
		System.out.println("==============================Classified as Negative========================");
		System.out.println("Original = Negative, Correctly Classified");
	    printInstancesResult(testResult[0][0], false);
	    
	    System.out.println("Original = Positive, Wrongly Classififed");	    	    
	    printInstancesResult(testResult[1][0], false);
	    
	    System.out.println("==============================Classified as Positive========================");
	    System.out.println("Original = Positive, Correctly Classified");
	    printInstancesResult(testResult[1][1], true);
	    	    
	    System.out.println("Original = Negative, Wrongly Classififed");
	    printInstancesResult(testResult[0][1], true);
	    
	}
	
	private void printInstancesResult(ArrayList<Instance> result, boolean showPositive){
		for (int i = 0; i < result.size(); i++){
	    	try{
	    		double[] probs  = model.distributionForInstance(result.get(i));
	    		if (showPositive)
	    			System.out.println("Probabilities of class positive: " + probs[1]);
	    		else
	    			System.out.println("Probabilities of class negative: " + probs[0]);	    			
	    	}catch (Exception e){
	    		System.out.println("Unable to get probability for instance " + i);
	    	}    		
	    }
	}
	
	
	public void printTestSummary(boolean includeExample){
		if (includeExample) printExamples();
		printClassifiedSummary();					   
	}
	
	private void printClassifiedSummary(){
		System.out.println("==============================Classified result========================");
	    int correct = testResult[0][0].size() + testResult[1][1].size();
	    int total =  testResult[0][0].size() + testResult[1][1].size() + testResult[0][1].size() + testResult[1][0].size();	    
	    System.out.println("Accuracy: " + (float)(100 * correct)/total + "%");
	    System.out.println("Original:\t0 \t1");
	    System.out.println("Classified 0:\t" + testResult[0][0].size() + "\t" + testResult[0][1].size());
	    System.out.println("Classified 1:\t" + testResult[1][0].size() + "\t" + testResult[1][1].size());
	    System.out.println("=======================================================================");
	}
	
	private void printExamples(){
		System.out.println("==============================Examples========================");
	    System.out.println("Original = Negative, Classified = Negative");
	    System.out.println(testResult[0][0].get(0).stringValue(0));
	    System.out.println("Original = Negative, Classified = Positive");
	    System.out.println(testResult[0][1].get(0).stringValue(0));
	    System.out.println("Original = Positive, Classified = Negative");
	    System.out.println(testResult[1][0].get(0).stringValue(0));
	    System.out.println("Original = Positive, Classified = Positive");
	    System.out.println(testResult[1][1].get(0).stringValue(0));
	    System.out.println("=======================================================================");
	}
}
