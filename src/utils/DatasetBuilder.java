package utils;

import java.io.File;
import java.io.FileFilter;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class DatasetBuilder {
	public static final int OBJECTIVE = 0;
	public static final int SUBJECTIVE = 1;
	
	protected Instances dataSet;
	public Instances getDataSet(){
		return dataSet;
	}
	
	public static FastVector buildAttributes(){
		FastVector atts = new FastVector();
		atts.addElement(new Attribute("content", (FastVector) null));
		FastVector label = new FastVector();		
		label.addElement("obj");
		label.addElement("subj");
				
		atts.addElement(new Attribute("@@class@@", label));		
		return atts;
	}
	
	//remove hidden .DS_Store file for Mac OS
	public static void removeDS_Store(String datasetPath){
		File dir = new File(datasetPath);
		removeDS_Store(dir);
	}
	
	protected static void removeDS_Store(File dir) {		
		try{			
			new File(dir.getAbsolutePath() + "/.DS_Store").delete();
			FileFilter fileFilter = new FileFilter() {		
				public boolean accept(File file) {				
					return file.isDirectory();				
				}				
			};		
			//remove recursively in subdirectories
			File[] files = dir.listFiles(fileFilter);
			for (int i = 0; i < files.length; i++){
				removeDS_Store(files[i]);
			}

		}catch(Exception e){
			System.out.println("cannot delete .DS_Store file");
		}
	}
}
