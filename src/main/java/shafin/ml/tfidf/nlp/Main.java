package shafin.ml.tfidf.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {

		//generateDataTable();

		String query = "বাংলাদেশের কৃষি"; 
		QueryEvaluator queryEvaluator = new QueryEvaluator(query);
		System.out.println(queryEvaluator.getQueryTFVector());
		System.out.println(queryEvaluator.getQueryTFIDFVector());

		System.out.println(query);
		Map<String, Double> cosineVector = CosineSimilarity.getCosineSimilarities(DataFileProcessor.getTfidfHashTable(),
				queryEvaluator);

		for (String doc : cosineVector.keySet()) {
			System.out.println(doc + " : " + cosineVector.get(doc));
		}

	}

	public static void generateDataTable() throws IOException, ClassNotFoundException {

		File[] allfiles = new File("D:\\home\\corpus\\").listFiles();
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