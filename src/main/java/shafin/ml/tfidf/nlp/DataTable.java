package shafin.ml.tfidf.nlp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTable implements Serializable{

	private static final long serialVersionUID = 1L;

	private Map<String, Integer> allTerms;
	private Map<String, HashMap<String, Double>> docTermVector;
	private HashMap<String, Double> docLengthVector;

	public DataTable() {
		this.allTerms = new HashMap<>();
		this.docTermVector = new HashMap<String, HashMap<String, Double>>();
		this.docLengthVector = new HashMap<>();
	}

	public Map<String, Integer> getAllTerms() {
		return allTerms;
	}

	public void setAllTerms(Map<String, Integer> allTerms) {
		this.allTerms = allTerms;
	}

	public Map<String, HashMap<String, Double>> getDocTermVector() {
		return docTermVector;
	}

	public void setDocTermVector(Map<String, HashMap<String, Double>> docTFTable) {
		this.docTermVector = docTFTable;
	}

	public HashMap<String, Double> getDocLengthVector() {
		return docLengthVector;
	}

	public void setDocLengthVector(HashMap<String, Double> docLengthVector) {
		this.docLengthVector = docLengthVector;
	}

	@Override
	public String toString() {

		List<String> docIDs = new ArrayList<String>(docTermVector.keySet());

		for (int i = 0; i < docIDs.size(); i++) {
			System.out.print(docIDs.get(i) + "\t");

			HashMap<String, Double> tfTable = new HashMap<>(docTermVector.get((docIDs.get(i))));
			List<String> terms = new ArrayList<String>(tfTable.keySet());

			for (int j = 0; j < terms.size(); j++) {
				System.out.print(terms.get(j) + "\t");
			}
			System.out.print("\n\t");

			for (int j = 0; j < terms.size(); j++) {
				System.out.print(tfTable.get(terms.get(j)) + "\t");
			}
			System.out.print("\n");
		}

		return "";
	}
	


}
