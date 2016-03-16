package shafin.ml.tfidf.nlp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.util.JsonProcessor;

public class DocProcessor implements Runnable {
    private final File file;
	private BufferedReader in;

    
	public DocProcessor(File file) {
		super();
		this.file = file;
	}


	@Override
	public void run() {
		try {
			in = new BufferedReader(new FileReader(file));

			StringBuilder sb = new StringBuilder();
			String s = null;
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}
			
			JsonProcessor jsonProcessor = new JsonProcessor(sb.toString());
			BanglapediaDoc doc = (BanglapediaDoc) jsonProcessor.convertToModel(BanglapediaDoc.class);
			
			
			DataTableEvaluator dataTableEvaluator = new DataTableEvaluator();
			DocumentParser docParser = new DocumentParser(doc.getArticle());
			String[] tokenizedTerms = docParser.getTokensExcludingStopwordsArray();

			dataTableEvaluator.evaluateDocTF(file.getName(), tokenizedTerms);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
