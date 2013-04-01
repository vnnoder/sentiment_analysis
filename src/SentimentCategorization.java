import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class SentimentCategorization {
	public static void main(String[] args) throws Exception {
	    // convert the directory into a dataset
	    TextDirectoryLoader trainingLoader = new TextDirectoryLoader();
	    TextDirectoryLoader testLoader = new TextDirectoryLoader();
	    
	    trainingLoader.setDirectory(new File("dataset/training"));
	    Instances training = trainingLoader.getDataSet();
	    //System.out.println("\n\nTraining data:\n\n" + training);
	    
	    testLoader.setDirectory(new File("dataset/testing"));
	    Instances test = testLoader.getDataSet();
	    //System.out.println("\n\nTest data:\n\n" + test);
	    
	    FilteredClassifier model = new FilteredClassifier();
	    StringToWordVector stringtowordvector = new StringToWordVector();
	    stringtowordvector.setUseStoplist(true);	    
	    model.setFilter(stringtowordvector);
	    model.setClassifier(new NaiveBayes());
	    model.buildClassifier(training);

	    
	    //StringToWordVector filter = new StringToWordVector();	    
	    //filter.setUseStoplist(true);
	    //filter.setOnlyAlphabeticTokens(true);
	    
	    //filter.setInputFormat(training);
	    //Instances filteredTraining = Filter.useFilter(training, filter);
	    //filteredTraining.setClassIndex(filteredTraining.numAttributes() - 1);
	    //System.out.println("\n\nFiltered data:\n\n" + filteredTraining);	    
	    
	    //filter.setInputFormat(test);
	    //Instances filteredTest = Filter.useFilter(test, filter);
	    //filteredTest.setClassIndex(filteredTest.numAttributes() - 1);
	    
	    //Classifier classifier = (Classifier)new NaiveBayes();
	    //classifier.buildClassifier(filteredTraining);
	    
	    //System.out.println("\n\nClassifier model:\n\n" + classifier);

	    int[][] results = new int[2][2];
	    results[0][0] = results[0][1] = results[1][0] = results[1][1] = 0;
	    System.out.println("Testing result");
	    for (int i = 1; i < test.numInstances(); i++)
	    {
	    	
	    	Instance instance = test.instance(i);
	    	double originalClassValue = instance.classValue();
	    	instance.setClassMissing();
	    	System.out.println("instance " + i + ": " + instance);
	    	try{
	    		double cls = model.classifyInstance(instance);
	    		instance.setClassValue(cls);
	    		System.out.println("Original: " + originalClassValue + "\tClass value: " + cls);	    		
	    		results[(int)Math.round(originalClassValue)][(int)Math.round(cls)] += 1;
	    	}catch(Exception e){
	    		throw e;
	    	}
	    }
	    
	    //System.out.println(test);
	    System.out.println("Original:\t0 \t1");
	    System.out.println("Classified 0:\t" + results[0][0] + "\t" + results[0][1]);
	    System.out.println("Classified 1:\t" + results[1][0] + "\t" + results[1][1]);
	    
	    
	}
}
