package utils;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;

public class DirectoryDatasetBuilder extends DatasetBuilder {
	public DirectoryDatasetBuilder(String datasetPath) throws IOException{
		//remove MacOS hidden files
		removeDS_Store(datasetPath);
			
		TextDirectoryLoader testLoader = new TextDirectoryLoader();
	    testLoader.setDirectory(new File(datasetPath));
	    dataSet = testLoader.getDataSet();
	}
}
