package shafin.ml.tfidf.nlp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdf {
	/**
	 * Calculates the tf of term termToCheck
	 * @param totalterms   : Array of all the words under processing document
	 * @param termToCheck  : term of which tf is to be calculated.
	 * @return tf(term frequency) of term termToCheck
	 */
	public static double tfCalculator(String[] totalterms, String termToCheck) {
		double count = 0; // to count the overall occurrence of the term
							// termToCheck
		for (String s : totalterms) {
			if (s.equalsIgnoreCase(termToCheck)) {
				count++;
			}
		}
		return count / totalterms.length;
	}

	/**
	 * Calculates idf of term termToCheck
	 * 
	 * @param allTerms : all the terms of all the documents
	 * @param termToCheck
	 * @return idf(inverse document frequency) score
	 */	
	public static double idfCalculator(Map<String, HashMap<String, Double>> termVector, String termToCheck) {
		double count = 0;
		
		ArrayList<String> allDocs = new ArrayList<String>(termVector.keySet());
		
		for (int i = 0; i < allDocs.size(); i++) {
			
			HashMap<String, Double> termTable = new HashMap<>(termVector.get((allDocs.get(i))));
			List<String> docTerms = new ArrayList<String>(termTable.keySet());
			
			for (int j = 0; j < docTerms.size(); j++) {
				
				String docTerm = docTerms.get(j);
				Double tfValue = termTable.get(docTerms.get(j));
				
				if (termToCheck.equalsIgnoreCase(docTerm)) {
					if( tfValue > 0){
						count++;
					}
					break;
				}
				
			}
		}
		return 1 + Math.log(allDocs.size() / count);
	}
	
	
	public static double calculateIdf(int totalDoc, int count){
		return 1 + Math.log(totalDoc / count);
	}
}
