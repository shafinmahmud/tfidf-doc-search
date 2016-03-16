package shafin.ml.tfidf.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import shafin.ml.tfidf.dto.ArticleDto;
import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.nlp.CosineSimilarity;
import shafin.ml.tfidf.nlp.DataFileProcessor;
import shafin.ml.tfidf.nlp.QueryEvaluator;
import shafin.ml.tfidf.util.JsonProcessor;

@Service("searchService")
public class SearchService {

	private final String CORPUS_LOCATION = "D:\\DOCUMENT\\BP\\";

	public List<ArticleDto> searchCollection(String query) {

		List<BanglapediaDoc> resultDoc = new ArrayList<>();
		System.out.println(query);

		try {
			QueryEvaluator queryEvaluator;
			queryEvaluator = new QueryEvaluator(query);
			System.out.println(queryEvaluator.getQueryTFVector());
			System.out.println(queryEvaluator.getQueryTFIDFVector());

			Map<String, Double> cosineVector;
			cosineVector = CosineSimilarity.getCosineSimilarities(DataFileProcessor.getTfidfHashTable(),
					queryEvaluator);

			for (String fileName : cosineVector.keySet()) {
				BanglapediaDoc banglapediaDoc = pullDoc(fileName);

				if (banglapediaDoc != null) {
					resultDoc.add(banglapediaDoc);
					System.out.println(fileName + " : " + cosineVector.get(fileName));
				} else {
					System.out.println("But UNFORTUNATELY " + fileName + ".json is found");
				}

			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return convertToDtoList(resultDoc);
	}

	private BanglapediaDoc pullDoc(String docName) {

		try {
			File file = new File(CORPUS_LOCATION + docName + ".json");
			JsonProcessor jsonProcessor = new JsonProcessor(file);
			return (BanglapediaDoc) jsonProcessor.convertToModel(BanglapediaDoc.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<ArticleDto> convertToDtoList(List<BanglapediaDoc> banglapediaDocs){
		return null;
	}
	
	private ArticleDto convertToDto(String docName, BanglapediaDoc doc) {
		
		ArticleDto dto = new ArticleDto();
		dto.setFileName(docName);
		dto.setUrl(doc.getUrl());
		dto.setTitle(doc.getTitle());
		dto.setPhotoURL(doc.getPhotoURL());
		
		String article = doc.getArticle();	
		if(article.length() > 100){
			article = article.substring(0, 100)+"...";
		}
		dto.setArticle(article);

		return dto;
	}

}
