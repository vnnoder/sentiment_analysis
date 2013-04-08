package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import weka.core.Instance;
import weka.core.Instances;
import classifier.BaseClassifier;
import exception.NoClassifierException;

import nlp.SentenceTokenizer;

/*
 * Filter sentences without subjectivity
 * Input is a directory of documents
 * Output is a directory of filtered documents 
 */
public class SubjectivityFilter {
	private String datasetPath;	
	private BaseClassifier subjectivityDetector;
	public SubjectivityFilter(String datasetPath){
		this.datasetPath = datasetPath;
		this.subjectivityDetector = new BaseClassifier("NaiveBayes",true);					
	}
	
	public String newPath(String datasetPath, String path){
		int pos = path.indexOf(datasetPath);
		if (pos <= 0){
			return path + "_subjectivity";
		}else{
			return datasetPath.substring(0,datasetPath.length()-1) + "_subjectivity" + File.separator + path.substring(pos + datasetPath.length()); 
		}		 
	}
	
	public void execute() throws IOException, NoClassifierException{
		Instances trainingSet = new FileDatasetBuilder("dataset/subjectivity_detector/all/").getDataSet();
		this.subjectivityDetector.train(trainingSet);		
		File dir = new File(this.datasetPath);
		DatasetBuilder.removeDS_Store(dir);						
		process(dir);
	}
	
	private void process(File file) throws IOException{
		System.out.println("process " + file.getCanonicalPath());
		if (file.isDirectory()){
			File newDir = new File(newPath(this.datasetPath, file.getCanonicalPath()));
			if (!newDir.exists()){
				newDir.mkdir();
			}
			File[] files = file.listFiles();
			for (File f:files){
				process(f);
			}
		}
		else if (file.isFile()){
			processFile(file);
		}
	}
	
	private void processFile(File file) throws IOException{
		File newFile = new File(newPath(this.datasetPath,file.getCanonicalPath()));
		FileWriter writer = new FileWriter(newFile);
		String fileContent = new Scanner(file).useDelimiter("\\A").next();
		String[] sentences = SentenceTokenizer.tokenizeBasic(fileContent);
		SentencesDatasetBuilder builder = new SentencesDatasetBuilder(sentences);
		Instances dataSet = builder.getDataSet();
		for (int i = 0; i < dataSet.numInstances(); i++){
			Instance ins = dataSet.instance(i);
			ins.setClassMissing();
			try{
				ins = this.subjectivityDetector.classify(ins);				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			if (Math.round(ins.classValue()) == DatasetBuilder.SUBJECTIVE){
				writer.append(ins.stringValue(ins.attribute(0))).append(". ");
			}
		}
		
		
		writer.flush();
		writer.close();
	}
}
