package shafin.ml.tfidf.crawler;

import java.io.IOException;

public interface BanglapediaParser {

	public  String parseSource() throws IOException;

	public  String parseURL() throws IOException;

	public  String parseOtherLangURL() throws IOException;
	
	public  String parseTitle() throws IOException;
	
	public  String parseAuthor() throws IOException;
	
	public  String parsePhotoURL() throws IOException;
	
	public  String parseArticle() throws IOException;

}
