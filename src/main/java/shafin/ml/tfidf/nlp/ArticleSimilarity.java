package shafin.ml.tfidf.nlp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.util.FileHandler;
import shafin.ml.tfidf.util.JsonProcessor;
import shafin.ml.tfidf.util.PropertyUtil;

public class ArticleSimilarity implements Runnable {

	private static String CORPUS_PATH = PropertyUtil.getPropertyValue("DATA_PATH") + "corpus/bp/";
	private static String DATA_PATH = PropertyUtil.getPropertyValue("DATA_PATH") + "similarity/";

	private DataTable dataTable;

	private String filePath;

	public static void main(String[] args) throws ClassNotFoundException, IOException, CloneNotSupportedException {

		System.out.println(new Date() + ": Loading TFIDF table...");
		DataTable dtable = DataFileProcessor.getTfidfHashTable();

		ExecutorService executorService = Executors.newFixedThreadPool(1);

		List<String> existingFiles = FileHandler.getFileList(DATA_PATH);
		List<String> files = FileHandler.getRecursiveFileList(CORPUS_PATH);

		for (String path : files) {

			System.out.println("processing: " + path);

			if (!existingFiles.contains(new File(path).getName())) {
				ArticleSimilarity articleSimilarity = new ArticleSimilarity(path, dtable);
				executorService.execute(articleSimilarity);
			}

		}

		executorService.shutdown();
	}

	public ArticleSimilarity(String filePath, DataTable dataTable) {
		this.filePath = filePath;
		this.dataTable = dataTable;
	}

	@Override
	public void run() {

		String json = FileHandler.readFileAsSingleString(this.filePath);
		JsonProcessor jsonProcessor = new JsonProcessor(json);
		BanglapediaDoc doc1;
		try {

			doc1 = (BanglapediaDoc) jsonProcessor.convertToModel(BanglapediaDoc.class);

			String article = doc1.getArticle();

			QueryEvaluator queryEvaluator = new QueryEvaluator(article);
			Map<String, Double> cosineVector = CosineSimilarity.getCosineSimilarDocs(dataTable, queryEvaluator);

			JsonProcessor jsonProcessor2 = new JsonProcessor();
			String jsonString = jsonProcessor2.convertToJson(cosineVector);
			String filePath = DATA_PATH + new File(this.filePath).getName();
			FileHandler.writeFile(filePath, jsonString);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
