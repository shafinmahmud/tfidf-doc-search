package shafin.ml.tfidf.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class QueryEvaluator {

	private Set<String> tokenizedUniqueTerms;
	private String[] tokenizedTerms;

	private HashMap<String, Double> queryTFVector;
	private HashMap<String, Double> queryTFIDFVector;

	private double queryLength;

	public QueryEvaluator() {
	}

	public QueryEvaluator(String query) throws ClassNotFoundException, IOException {
		// generate tokenizedTerms
		DocumentParser docParser = new DocumentParser(query);
		this.tokenizedTerms = docParser.getTokensExcludingStopwordsArray();

		// generate tokenizedUniqueTerms
		this.tokenizedUniqueTerms = new HashSet<String>();
		for (String term : tokenizedTerms) {
			if (!tokenizedUniqueTerms.contains(term.toLowerCase())) {
				tokenizedUniqueTerms.add(term.toLowerCase());
			}
		}

		// generate queryTFVector
		this.queryTFVector = new HashMap<>();
		Iterator<String> uniqueTermIterator = this.tokenizedUniqueTerms.iterator();

		while (uniqueTermIterator.hasNext()) {
			String term = uniqueTermIterator.next();
			double value = TfIdf.tfCalculator(this.tokenizedTerms, term);
			this.queryTFVector.put(term, value);
		}

		// generate queryTFIDFVector
		this.queryTFIDFVector = new HashMap<>();
		HashMap<String, Double> idfHashTable = DataFileProcessor.getIdfHashTable();
		List<String> terms = new ArrayList<String>(this.queryTFVector.keySet());

		for (int j = 0; j < terms.size(); j++) {
			String term = terms.get(j);

			double tf = this.queryTFVector.get(term);
			double idf = 0.0;
			try {
				idf = idfHashTable.get(term);
			} catch (NullPointerException e) {

			}

			this.queryTFIDFVector.put(term, tf * idf);

		}

		// generate queryLength
		this.queryLength = CosineSimilarity.calculateLength(this.queryTFIDFVector);
	}

	public Set<String> getTokenizedUniqueTerms() {
		return tokenizedUniqueTerms;
	}

	public void setTokenizedUniqueTerms(Set<String> tokenizedUniqueTerms) {
		this.tokenizedUniqueTerms = tokenizedUniqueTerms;
	}

	public String[] getTokenizedTerms() {
		return tokenizedTerms;
	}

	public void setTokenizedTerms(String[] tokenizedTerms) {
		this.tokenizedTerms = tokenizedTerms;
	}

	public HashMap<String, Double> getQueryTFVector() {
		return queryTFVector;
	}

	public void setQueryTFVector(HashMap<String, Double> queryTFVector) {
		this.queryTFVector = queryTFVector;
	}

	public HashMap<String, Double> getQueryTFIDFVector() {
		return queryTFIDFVector;
	}

	public void setQueryTFIDFVector(HashMap<String, Double> queryTFIDFVector) {
		this.queryTFIDFVector = queryTFIDFVector;
	}

	public double getQueryLength() {
		return queryLength;
	}

	public void setQueryLength(double queryLength) {
		this.queryLength = queryLength;
	}

}
