package shafin.ml.tfidf.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;

import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.service.BanglapediaService;
import shafin.ml.tfidf.util.FileHandler;
import shafin.ml.tfidf.util.JsonProcessor;
import shafin.ml.tfidf.util.JsoupParser;

public class BanglapediaCrawler {

	@Autowired
	BanglapediaService service;

	private final List<String> LINKS_TO_CRAWL;
	private final String URL_FILE_LOCATION = "url_bn.txt";
	private final String FILE_STORE_LOCATION = "D:\\DOCUMENT\\BP\\";

	private JsoupParser jsoupParser;

	public BanglapediaCrawler() {
		this.LINKS_TO_CRAWL = FileHandler.readFile(URL_FILE_LOCATION);
		this.jsoupParser = new JsoupParser();
	}

	public void crawl() throws JsonGenerationException, JsonMappingException, IOException {

		int index = 0;
		for (String uri : LINKS_TO_CRAWL) {
			
			BanglapediaDoc doc = parseDoc(uri);
			doc.setDocID(index++);
			doc.setKeywords(new ArrayList<String>());
			
			System.out.println("writing > "+doc.getDocID() + " : "+doc.getTitle());
			JsonProcessor jsonProcessor = new JsonProcessor();
			//System.out.println(jsonProcessor.convertToJson(doc));
			FileHandler.writeFile(FILE_STORE_LOCATION+doc.getTitle()+".json", jsonProcessor.convertToJson(doc));
		}

	}

	public BanglapediaDoc parseDoc(String uri) {

		BanglapediaDoc doc = new BanglapediaDoc();

		try {
			String html = jsoupParser.getHtmlFromGetRequest(uri);
			BanglapediaParser parser = new BanglapediaParserImpl(uri, html);

			doc.setSource(parser.parseSource());
			doc.setUrl(uri);
			doc.setLang("BANGLA");
			doc.setOtherLangURL(parser.parseOtherLangURL());
			doc.setTitle(parser.parseTitle());
			doc.setArticle(parser.parseArticle());
			doc.setPhotoURL(parser.parsePhotoURL());
			doc.setAuthor(parser.parseAuthor());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		BanglapediaCrawler crawler = new BanglapediaCrawler();
		crawler.crawl();
	}

}
