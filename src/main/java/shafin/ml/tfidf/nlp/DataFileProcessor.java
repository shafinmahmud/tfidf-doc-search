package shafin.ml.tfidf.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

/*
 */

/**
 *
 * @author SHAFIN
 */
public class DataFileProcessor {

	private final static String TEMP_FOLDER = "D:\\home\\search\\bin";
	private final static String TF_FILE_PATH = "D:\\home\\search\\tf.bin";
	private final static String IDF_FILE_PATH = "D:\\home\\search\\idf.bin";
	private final static String TFIDF_FILE_PATH = "D:\\home\\search\\tfidf.bin";

	private static DataTable tfHashTable;
	private static HashMap<String,Double> idfHashTable;
	private static DataTable tfidfHashTable;

	public static DataTable getTfHashTable() throws IOException, ClassNotFoundException {
		InputStream input = new FileInputStream(TF_FILE_PATH);

		try (ObjectInputStream ois = new ObjectInputStream(input)) {
			tfHashTable = (DataTable) ois.readObject();
		}
		return tfHashTable;
	}

	public static boolean isExists(){
		File dataFile = new File(TFIDF_FILE_PATH);
		return dataFile.exists();
	}
	
	public static void createTempDocTF(HashMap<String, Double> vector, String docID) throws IOException{
		File dataFile = new File(TEMP_FOLDER+"\\"+docID+".bin");
		dataFile.createNewFile();
		
		OutputStream output = new FileOutputStream(TEMP_FOLDER+"\\"+docID+".bin");

		try (ObjectOutputStream oos = new ObjectOutputStream(output);) {
			oos.writeObject(vector);
		}
	}
	
	
	public static File[] getTempDocList(){
	    return new File(TEMP_FOLDER).listFiles();
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Double> readTempDocTF(String docID) throws IOException, ClassNotFoundException{
		InputStream input = new FileInputStream(TEMP_FOLDER+"\\"+docID+".bin");
		
		HashMap<String, Double> tempDocTF;
		try (ObjectInputStream ois = new ObjectInputStream(input)) {
			tempDocTF = (HashMap<String, Double>) ois.readObject();
		}
		return tempDocTF;
	}
	
	public static void setTfHashTable(DataTable tfHashTable) throws IOException {
		File dataFile = new File(TF_FILE_PATH);
		dataFile.createNewFile();

		OutputStream output = new FileOutputStream(TF_FILE_PATH);

		try (ObjectOutputStream oos = new ObjectOutputStream(output);) {
			oos.writeObject(tfHashTable);
		}
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String,Double> getIdfHashTable() throws IOException, ClassNotFoundException {
		InputStream input = new FileInputStream(IDF_FILE_PATH);

		try (ObjectInputStream ois = new ObjectInputStream(input)) {
			idfHashTable = (HashMap<String,Double>) ois.readObject();
		}
		return idfHashTable;
	}

	public static void setIdfHashTable(HashMap<String,Double> idfHashTable) throws IOException {
		File dataFile = new File(IDF_FILE_PATH);
		dataFile.createNewFile();

		OutputStream output = new FileOutputStream(IDF_FILE_PATH);

		try (ObjectOutputStream oos = new ObjectOutputStream(output);) {
			oos.writeObject(idfHashTable);
		}
	}

	public static DataTable getTfidfHashTable() throws IOException, ClassNotFoundException {
		InputStream input = new FileInputStream(TFIDF_FILE_PATH);

		try (ObjectInputStream ois = new ObjectInputStream(input)) {
			tfidfHashTable = (DataTable) ois.readObject();
		}
		return tfidfHashTable;
	}

	public static void setTfidfHashTable(DataTable tfidfHashTable) throws IOException {
		File dataFile = new File(TFIDF_FILE_PATH);
		dataFile.createNewFile();

		OutputStream output = new FileOutputStream(TFIDF_FILE_PATH);

		try (ObjectOutputStream oos = new ObjectOutputStream(output);) {
			oos.writeObject(tfidfHashTable);
		}
	}
}
