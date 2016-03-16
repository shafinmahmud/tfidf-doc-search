package shafin.ml.tfidf.nlp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shafin.ml.tfidf.util.MapUtil;

public class CosineSimilarity {

	/**
	 * Method to calculate cosine similarity between two documents.
	 * 
	 * @param docVector1 : document vector 1 (a)
	 * @param docVector2 : document vector 2 (b)
	 * @return
	 */

	public static Map<String,Double> getCosineSimilarities(DataTable tfidfDataTable, QueryEvaluator queryData){
			
		HashMap<String, Double> cosineSimilarities = new HashMap<>();
		
		Map<String, HashMap<String, Double>> docTermVectors = tfidfDataTable.getDocTermVector();
		HashMap<String, Double> queryTFIDFVector = queryData.getQueryTFIDFVector();
		
		HashMap<String, Double> docLengthVector = tfidfDataTable.getDocLengthVector();
		Double queryLength = queryData.getQueryLength();
		
		for(String docID : docTermVectors.keySet()){
			
			double cosineSimilarity = 0.0;
			double dotProduct = 0.0;
			double docLength = docLengthVector.get(docID);
			
			HashMap<String, Double> docTFIDFVector = docTermVectors.get(docID);
			
			for(String term : queryTFIDFVector.keySet()){
				if(docTFIDFVector.containsKey(term)){
					dotProduct += docTFIDFVector.get(term) * queryTFIDFVector.get(term);
				}
			}
			
			if (docLength != 0.0 |  docLength != 0.0) {
				cosineSimilarity = dotProduct / (queryLength *  docLength);
			} 
			
			if(cosineSimilarity > 0){
				cosineSimilarities.put(docID, cosineSimilarity);
			}
		}
		
		return MapUtil.sortByValueDecending(cosineSimilarities);
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
