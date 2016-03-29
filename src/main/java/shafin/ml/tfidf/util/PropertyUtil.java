package shafin.ml.tfidf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

	private static final String PROJECT_DIRECTORY_PATH = "D:/DOCUMENT/PROJECTS/SPRING/";
	private static final String PROPERTY_FILE_QUALIFIED_NAME = "ml-tfidf/src/main/resources/path.properties";
	
	public static String getPropertyValue(String attribute) {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			prop.load(new FileInputStream(PROJECT_DIRECTORY_PATH+PROPERTY_FILE_QUALIFIED_NAME));
			return prop.getProperty(attribute);


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
	
	public static void main(String[] args) {
		getPropertyValue("INDEX_PATH");
	}
}
