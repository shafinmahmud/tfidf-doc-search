package shafin.ml.tfidf.nlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CosineProcessor implements Runnable {

	private Map.Entry<String, HashMap<String, Double>> docEntry;
	
	
	public CosineProcessor(Entry<String, HashMap<String, Double>> docEntry) {
		super();
		this.docEntry = docEntry;
	}

	@Override
	public void run() {
	
		String docName = docEntry.getKey();
		HashMap<String, Double> docTFIDFVector = docEntry.getValue();
		
		double cosineSimVal = 0.0;
		double dotProduct = 0.0;
		double docLength = CosineSimilarity.docLengthVector.get(docName);

	
		for (Map.Entry<String, Double> termEntry : CosineSimilarity.queryTFIDFVector.entrySet()) {

			String queryTerm = termEntry.getKey();
			Double queryTermTFIDF = termEntry.getValue();

			if (docTFIDFVector.containsKey(queryTerm)) {
				dotProduct += docTFIDFVector.get(queryTerm) * queryTermTFIDF;
			}
		}

		if (docLength != 0.0 | docLength != 0.0) {			
			cosineSimVal = dotProduct / (CosineSimilarity.queryLength * docLength);
		}

		if (cosineSimVal > 0) {
			//System.out.println(cosineSimVal);
			CosineSimilarity.cosineSimilarities.put(docName, cosineSimVal);
		}
	}

}
