package utils;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class SentencesDatasetBuilder extends DatasetBuilder {			
	public SentencesDatasetBuilder(String[] sentences){
			
		buildInstances(sentences);
	}		
	
	public void buildInstances(String[] sentences){
	    FastVector attrs = buildAttributes();
		dataSet = new Instances("SubjectivityRelation", attrs, 0);
		for (int i = 0; i < sentences.length; i++){
			dataSet.add(buildInstance(sentences[i], dataSet, attrs));
		}		
		dataSet.setClassIndex(1);		 
	}		
	
	private Instance buildInstance(String sentence, Instances dataSet, FastVector attrs){											
		int index = dataSet.attribute(0).addStringValue(sentence);			
		double[] vals = new double[dataSet.numAttributes()];
		vals[0] = index;
		vals[1] = 0;
		return new Instance(1.0, vals);		
	}			

}
