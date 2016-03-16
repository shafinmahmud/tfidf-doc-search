package shafin.ml.tfidf.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shafin.ml.tfidf.util.FileHandler;

/**
 *
 * @author SHAFIN
 */
public class DataTableEvaluator {

	private final String TOKEN_FILE = "D:\\home\\search\\tokens.txt";
	
	public DataTableEvaluator() throws IOException {
		DataTable dataHashTable = new DataTable();
		HashMap<String, Double> idfMap = new HashMap<>();

		if (!DataFileProcessor.isExists()) {
			DataFileProcessor.setTfHashTable(dataHashTable);
			DataFileProcessor.setIdfHashTable(idfMap);
			DataFileProcessor.setTfidfHashTable(dataHashTable);
		}

	}

	public void evaluateDocTF(String docID, String[] tokenizedTerms)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		// create a docTFTable vector for this DOC
		HashMap<String, Double> docTF = new HashMap<String, Double>();
		Set<String> tokenizedUniqueTerms = new HashSet<>();

		// insert new terms in allTerms
		for (String term : tokenizedTerms) {

			if (!tokenizedUniqueTerms.contains(term.toLowerCase())) {
				tokenizedUniqueTerms.add(term.toLowerCase());
			}
		}

		// iterate allTerms
		Iterator<String> uniqueTermIterator = tokenizedUniqueTerms.iterator();
		while (uniqueTermIterator.hasNext()) {

			String term = uniqueTermIterator.next();

			// calculate TF value for the term
			double value = TfIdf.tfCalculator(tokenizedTerms, term);
			docTF.put(term, new Double(value));

		}
		
		DataFileProcessor.createTempDocTF(docTF, docID.replace(".json", ""));
		System.out.println("parsing file : " + docID);
	}

	
	public void evaluateTFTable() throws ClassNotFoundException, IOException {

		DataTable tfHashTable = DataFileProcessor.getTfHashTable();

		Set<String> allTerms = tfHashTable.getAllTerms();
		Map<String, HashMap<String, Double>> docTFTable = tfHashTable.getDocTermVector();

		File[] docTFtemps = DataFileProcessor.getTempDocList();
		for (File f : docTFtemps) {

			System.out.println("consolidating : " + f.getName());
			
			HashMap<String, Double> docTF = DataFileProcessor.readTempDocTF(f.getName().replace(".bin", ""));
			for (String term : docTF.keySet()) {
				if (!allTerms.contains(term.toLowerCase())) {
					
					FileHandler.appendFile(this.TOKEN_FILE, term+"\n");
					allTerms.add(term.toLowerCase());
				}
			}
			
			docTFTable.put(f.getName(), docTF);
		}		
		 DataFileProcessor.setTfHashTable(tfHashTable);
	}
	

	public void evaluateIDFTable() throws ClassNotFoundException, IOException {

		DataTable tfHashTable = DataFileProcessor.getTfHashTable();
		HashMap<String, Double> idfHashTable = DataFileProcessor.getIdfHashTable();

		Set<String> allTerms = tfHashTable.getAllTerms();
		Map<String, HashMap<String, Double>> docTFTable = tfHashTable.getDocTermVector();

		System.out.println("IDF cal : "+allTerms.size());
		
		int i = 0;
		for (String termToCheck : allTerms) {
			System.out.println(i++ +" : "+termToCheck);
			
			if(!idfHashTable.containsKey(termToCheck)){
				double val = TfIdf.idfCalculator(docTFTable, termToCheck);
				idfHashTable.put(termToCheck, new Double(val));
			}
			
			if(i%100 == 0){
				DataFileProcessor.setIdfHashTable(idfHashTable);
			}
		}
		DataFileProcessor.setIdfHashTable(idfHashTable);
	}

	public void evaluateTFIDFTable() throws ClassNotFoundException, IOException {

		DataTable tfidfHashTable = DataFileProcessor.getTfidfHashTable();
		Map<String, HashMap<String, Double>> docTFIDFVector = tfidfHashTable.getDocTermVector();
		Map<String, Double> docLengthVector = tfidfHashTable.getDocLengthVector();

		DataTable tfHashTable = DataFileProcessor.getTfHashTable();
		Map<String, HashMap<String, Double>> docTFVector = tfHashTable.getDocTermVector();

		HashMap<String, Double> idfHashTable = DataFileProcessor.getIdfHashTable();

		List<String> docIDs = new ArrayList<String>(docTFVector.keySet());

		for (int i = 0; i < docIDs.size(); i++) {
			

			String docName = docIDs.get(i);
			HashMap<String, Double> tfidfVector = new HashMap<>();

			HashMap<String, Double> tfTable = new HashMap<>(docTFVector.get((docIDs.get(i))));
			List<String> terms = new ArrayList<String>(tfTable.keySet());

			for (int j = 0; j < terms.size(); j++) {
				String term = terms.get(j);
				double tf = tfTable.get(term);
				double idf = idfHashTable.get(term);
				double tfidf = tf * idf;

				tfidfVector.put(term, tfidf);
			}
			docTFIDFVector.put(docName, tfidfVector);

			// setting the docLengthVector
			double length = CosineSimilarity.calculateLength(tfidfVector);
			
			docLengthVector.put(docName, new Double(length));
		}

		tfidfHashTable.setAllTerms(tfHashTable.getAllTerms());
		DataFileProcessor.setTfidfHashTable(tfidfHashTable);

	}

	public void printIDFvector() throws ClassNotFoundException, IOException {
		HashMap<String, Double> idfMap = DataFileProcessor.getIdfHashTable();
		List<String> idfTerms = new ArrayList<String>(idfMap.keySet());
		for (int i = 0; i < idfTerms.size(); i++) {
			String term = idfTerms.get(i);
			System.out.println(term + " : idf > " + idfMap.get(term));
		}
	}

	public void printTFvector() throws ClassNotFoundException, IOException {
		DataTable tfTable = DataFileProcessor.getTfHashTable();
		tfTable.toString();
	}

	public void printTFIDFvector() throws ClassNotFoundException, IOException {
		DataTable tfidfTable = DataFileProcessor.getTfidfHashTable();
		tfidfTable.toString();
	}

}
