package shafin.ml.tfidf.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import shafin.ml.tfidf.model.ArticleDto;
import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.util.FileHandler;
import shafin.ml.tfidf.util.JsonProcessor;
import shafin.ml.tfidf.util.ListUtil;
import shafin.ml.tfidf.util.PropertyUtil;

@Service("homeService")
public class HomeService {

	private final String CORPUS_LOCATION = PropertyUtil.getPropertyValue("DATA_PATH")+"corpus/bp/";

	public List<ArticleDto> getRandomArticle(int num) {
		List<ArticleDto> randomArticles = new ArrayList<>();
		List<String> fileNames = FileHandler.getFileList(CORPUS_LOCATION);

		if (fileNames.size() > num) {
			List<String> randoms = ListUtil.pickNRandom(fileNames, num);
			for (String randomFileName : randoms) {
				BanglapediaDoc doc = pullDoc(randomFileName);
				randomArticles.add(convertToDto(randomFileName, doc));
			}
		} else {
			for (String fileName : fileNames) {
				BanglapediaDoc doc = pullDoc(fileName);
				randomArticles.add(convertToDto(fileName, doc));
			}
		}

		return randomArticles;
	}

	private ArticleDto convertToDto(String docName, BanglapediaDoc doc) {

		ArticleDto dto = new ArticleDto();
		dto.setFileName(docName);
		dto.setUrl(doc.getUrl());
		dto.setTitle(doc.getTitle());
		dto.setPhotoURL(doc.getPhotoURL());

		String article = doc.getArticle();
		if (article.length() > 300) {
			article = article.substring(0, 300) + "...";
		}
		dto.setArticle(article);

		return dto;
	}
	

	public BanglapediaDoc pullDoc(String docName) {

		try {
			File file = new File(CORPUS_LOCATION + docName);
			JsonProcessor jsonProcessor = new JsonProcessor(file);
			return (BanglapediaDoc) jsonProcessor.convertToModel(BanglapediaDoc.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
