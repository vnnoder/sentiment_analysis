package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class FileDatasetBuilder extends DatasetBuilder {
			
	public FileDatasetBuilder(String datasetPath) throws IOException{
		File dir = new File(datasetPath);
		removeDS_Store(datasetPath);
		
		FileFilter fileFilter = new FileFilter() {		
			public boolean accept(File file) {				
				return file.isFile();				
			}				
		};	
		buildInstances(dir.listFiles(fileFilter));
	}

		
	
	public void buildInstances(File[] files) throws FileNotFoundException, IOException{
	    FastVector attrs = buildAttributes();
		dataSet = new Instances("SubjectivityRelation", attrs, 0);
		for (int i = 0; i < files.length; i++){
			dataSet = buildInstances(files[i], files[i].getName(), dataSet, attrs);
		}		
		dataSet.setClassIndex(files.length - 1);		 
	}
	
	
	
	private Instances buildInstances(File file, String label, Instances dataSet, FastVector attrs) throws FileNotFoundException, IOException{				
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null){			
			int index = dataSet.attribute(0).addStringValue(line);			
			double[] vals = new double[dataSet.numAttributes()];
			vals[0] = index;
			vals[1] = dataSet.attribute(1).indexOfValue(label);
			dataSet.add(new Instance(1.0, vals));
		}
		reader.close();
		
		return dataSet;
	}
	
	
}
