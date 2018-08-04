package persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.google.gson.Gson;

public class Persistence {
	private static String fileSeparator = System.getProperty("file.separator");
	
	private static String generationFilePath = "generation";
	private static String baseFilePath = "genomes";
	
	private static String genomeFileName = "genome";
	private static String generationFileName = "generation";
	private static String fileExtension = ".json";
	private static Gson gson = new Gson();
	
	public static void saveObjects(List<? extends Storable> objects, int generationNumber) {
		for(Storable object : objects) {
			saveObjectToFile(object, generationNumber);
		}
	}
	
	public static void eraseAllExperiments() {
		throw new UnsupportedOperationException();
	}
	
	public static void saveObjectToFile(Storable object, int generationNumber) {
		String json = gson.toJson(object);
		try {
			FileUtils.write(new File(getStorageFileName(generationNumber, object)), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getLatestGenerationNumber() {
		IOFileFilter fileFilter = new IOFileFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg0.getName().contains(generationFileName);
			}

			@Override
			public boolean accept(File arg0) {
				return arg0.getName().contains(generationFileName);
			}
		};
		
		Collection<File> generationDirs = FileUtils.listFilesAndDirs(new File(baseFilePath), FalseFileFilter.FALSE, fileFilter);
		
		int latestGenerationNumber = 0;
		for(File f: generationDirs) {
			int generationNumber = getNumberFromFileName(f.getName());
			if(generationNumber > latestGenerationNumber)
				latestGenerationNumber = generationNumber;
		}
		return latestGenerationNumber;
	}
	
	private static int getNumberFromFileName(String fileName) {
		Pattern p = Pattern.compile("([0-9]+)");
	    Matcher m = p.matcher(new StringBuilder(fileName).reverse());
	    if(m.find()) {
	      return Integer.parseInt(new StringBuilder(m.group(1)).reverse().toString());
	    }
	    return 0;
	}
	
	private static String getStorageFileName(int generationNumber, Storable object) {
		return getGenerationFilePath(generationNumber) + fileSeparator + object.getName() + object.hashCode() + fileExtension;
	}
	
	private static String getGenerationFileName(int generationNumber) {
		return getGenerationFilePath(generationNumber) + fileSeparator + generationFileName + fileExtension;
	}
	
	private static String getGenerationFilePath(int generationNumber) {
		return baseFilePath + fileSeparator + generationFilePath + generationNumber;
	}
}
