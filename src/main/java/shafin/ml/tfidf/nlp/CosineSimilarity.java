package shafin.ml.tfidf.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shafin.ml.tfidf.util.MapUtil;

public class CosineSimilarity {

	public static HashMap<String, Double> cosineSimilarities;
	public static HashMap<String, Double> docLengthVector;
	public static HashMap<String, Double> queryTFIDFVector;
	public static Double queryLength;

	public static Map<String, Double> getCosineSimilarities(DataTable tfidfDataTable, QueryEvaluator queryData) {

		/* initializing the static fields */
		cosineSimilarities = new HashMap<>();
		docLengthVector = new HashMap<>();
		queryTFIDFVector = new HashMap<>();
		
		Map<String, HashMap<String, Double>> docTermVectors = tfidfDataTable.getDocTermVector();
		queryTFIDFVector = queryData.getQueryTFIDFVector();

		docLengthVector = tfidfDataTable.getDocLengthVector();
		queryLength = queryData.getQueryLength();

		ExecutorService threadExecutor = Executors.newFixedThreadPool(5);

		for (Map.Entry<String, HashMap<String, Double>> docEntry : docTermVectors.entrySet()) {

			CosineProcessor cosineProcessor = new CosineProcessor(docEntry);
			threadExecutor.execute(cosineProcessor);

		}

		threadExecutor.shutdown();
		boolean flag = true;

		while (!threadExecutor.isTerminated() || flag) {
			if (threadExecutor.isTerminated()) {
				flag = false;
				return MapUtil.sortByValueDecending(cosineSimilarities);
			}
		}
		return null;
	}

	public static double calculateLength(HashMap<String, Double> tfidfVector) {

		double magnitude = 0.0;
		List<String> terms = new ArrayList<String>(tfidfVector.keySet());

		for (int j = 0; j < terms.size(); j++) {
			double tfidf = tfidfVector.get(terms.get(j));
			magnitude += Math.pow(tfidf, 2); // (a^2)
		}

		return Math.sqrt(magnitude);// sqrt(a^2)
	}

}
