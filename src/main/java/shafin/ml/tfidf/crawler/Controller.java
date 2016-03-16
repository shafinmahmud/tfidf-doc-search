package shafin.ml.tfidf.crawler;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public class Controller {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		BanglapediaCrawler crawler = new BanglapediaCrawler();
		crawler.crawl();
		
	}
}
