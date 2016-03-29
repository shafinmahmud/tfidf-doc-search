package shafin.ml.tfidf.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shafin.ml.tfidf.util.PropertyUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SHAFIN
 */
public class Main {

	private static String CORPUS_PATH = PropertyUtil.getPropertyValue("DATA_PATH")+"corpus/bp/";
			
	public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {

		generateDataTable();

		

		String query = "বাংলাদেশের কৃষি";
		QueryEvaluator queryEvaluator = new QueryEvaluator(query);
		System.out.println(queryEvaluator.getQueryTFVector());
		System.out.println(queryEvaluator.getQueryTFIDFVector());

		System.out.println(query);
		
		long init = new Date().getTime();
		DataTable dataTable = DataFileProcessor.getTfidfHashTable();
		long end = new Date().getTime();
		System.out.println("time taken: " + (end - init));
		
		Map<String, Double> cosineVector = CosineSimilarity.getCosineSimilarDocs(dataTable, queryEvaluator);

		for (String doc : cosineVector.keySet()) {
			// System.out.println(doc + " : " + cosineVector.get(doc));
		}
		

	}

	public static void generateDataTable() throws IOException, ClassNotFoundException {

		File[] allfiles = new File(CORPUS_PATH).listFiles();
		ExecutorService threadExecutor = Executors.newFixedThreadPool(2);

		for (File f : allfiles) {
			if (f.getName().endsWith(".json")) {

				DocProcessor fileProcessor = new DocProcessor(f);
				threadExecutor.execute(fileProcessor);
			}
		}

		threadExecutor.shutdown();

		boolean flag = true;
		while (!threadExecutor.isTerminated() || flag) {

			if (threadExecutor.isTerminated()) {
				flag = false;
				System.out.println("initializing...");

				DataTableEvaluator dataTableEvaluator = new DataTableEvaluator();
				dataTableEvaluator.evaluateTFTable();
				dataTableEvaluator.evaluateIDFTable();
				dataTableEvaluator.evaluateTFIDFTable();
			}
		}
	}
}