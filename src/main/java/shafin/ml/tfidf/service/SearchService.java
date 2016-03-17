package shafin.ml.tfidf.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

	private final String CORPUS_LOCATION = "D:\\home\\corpus\\";

	public List<ArticleDto> searchCollection(String query) {
		
		List<ArticleDto> docDtos = new ArrayList<>();

		try {
			QueryEvaluator queryEvaluator;
			queryEvaluator = new QueryEvaluator(query);
	
			Map<String, Double> cosineVector = CosineSimilarity
					.getCosineSimilarities(DataFileProcessor.getTfidfHashTable(), queryEvaluator);

			for (String docID : cosineVector.keySet()) {
				
				String fileName = docID.replace(".bin", ".json");
				BanglapediaDoc banglapediaDoc = pullDoc(fileName);			
				
				if (banglapediaDoc != null) {
					Double cosineValue = cosineVector.get(docID);
					docDtos.add(convertToDto(fileName, cosineValue, banglapediaDoc));
				} else {
					System.out.println("But UNFORTUNATELY " + docID + " is not found");
				}
							
			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return docDtos;
	}

	private BanglapediaDoc pullDoc(String docName) {

		try {
			File file = new File(CORPUS_LOCATION + docName);
			JsonProcessor jsonProcessor = new JsonProcessor(file);
			return (BanglapediaDoc) jsonProcessor.convertToModel(BanglapediaDoc.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	private ArticleDto convertToDto(String docName, Double cosineValue, BanglapediaDoc doc) {

		ArticleDto dto = new ArticleDto();
		dto.setFileName(docName);
		dto.setCosineValue(cosineValue);
		dto.setUrl(doc.getUrl());
		dto.setTitle(doc.getTitle());
		dto.setPhotoURL(doc.getPhotoURL());

		String article = doc.getArticle();
		if (article.length() > 500) {
			article = article.substring(0, 500) + "...";
		}
		dto.setArticle(article);

		return dto;
	}

	public static void main(String[] args) {
		//SearchService service = new SearchService();
		long init = new Date().getTime();
		
		//List<ArticleDto> docs = service.searchCollection("বাংলাদেশের কৃষি");
		//for (ArticleDto dto : docs) {
			//System.out.println(dto.getCosineValue() + " : " + dto.getFileName());
		//}
		
		long end = new Date().getTime();
		long diff = end-init;
		System.out.println("time taken: "+ diff);
	}

}
