import java.io.File;
import java.util.ArrayList;

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

	    ArrayList<Instance>[][] results = new ArrayList[2][2];
	    for (int i = 0; i < results.length; i++)
	    	for (int j = 0; j < results[0].length; j++)
	    		results[i][j] = new ArrayList<Instance>();
	    
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
	    		results[(int)Math.round(originalClassValue)][(int)Math.round(cls)].add(instance);
	    	}catch(Exception e){
	    		throw e;
	    	}
	    }
	    
	    
	    System.out.println("==============================Classified result========================");
	    int correct = results[0][0].size() + results[1][1].size();
	    int total =  results[0][0].size() + results[1][1].size() + results[0][1].size() + results[1][0].size();	    
	    System.out.println("Accuracy: " + (float)(100 * correct)/total + "%");
	    System.out.println("Original:\t0 \t1");
	    System.out.println("Classified 0:\t" + results[0][0].size() + "\t" + results[0][1].size());
	    System.out.println("Classified 1:\t" + results[1][0].size() + "\t" + results[1][1].size());
	    System.out.println("=======================================================================");
	    System.out.println("==============================Examples========================");
	    System.out.println("Original = Negative, Classified = Negative");
	    System.out.println(results[0][0].get(0));
	    System.out.println("Original = Negative, Classified = Positive");
	    System.out.println(results[0][1].get(0));
	    System.out.println("Original = Positive, Classified = Negative");
	    System.out.println(results[1][0].get(0));
	    System.out.println("Original = Positive, Classified = Positive");
	    System.out.println(results[1][1].get(0));
	    System.out.println("=======================================================================");
	    
	    
	    
	}
}
