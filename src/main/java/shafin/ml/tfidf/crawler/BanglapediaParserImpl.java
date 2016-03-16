package shafin.ml.tfidf.crawler;

import java.io.IOException;

import shafin.ml.tfidf.util.JsoupParser;

public class BanglapediaParserImpl implements BanglapediaParser {

	private String uri;
	private String pageHtml;

	public BanglapediaParserImpl(String uri, String pageHtml) {
		this.uri = uri;
		this.pageHtml = pageHtml;
	}

	@Override
	public String parseSource() throws IOException {
		return "BANGLAPEDIA";
	}

	@Override
	public String parseURL() throws IOException {
		return uri;
	}

	@Override
	public String parseOtherLangURL() throws IOException {
		JsoupParser jsoupParser = new JsoupParser();
		try {
			return jsoupParser
					.parseLinkFromHtmlSegment(jsoupParser.parseDataFromHtml(pageHtml, "#other_language_link > li > a"));
		} catch (NullPointerException e) {
			return "";
		}
	}

	@Override
	public String parseTitle() throws IOException {
		JsoupParser jsoupParser = new JsoupParser();
		return jsoupParser.stripHtmlTag(jsoupParser.parseDataFromHtml(pageHtml, "#firstHeading > span"));
	}

	@Override
	public String parseAuthor() throws IOException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String parseArticle() throws IOException {
		JsoupParser jsoupParser = new JsoupParser();
		String text = jsoupParser.stripHtmlTag(jsoupParser.parseDataFromHtml(pageHtml, "#mw-content-text"));
		return text;
	}

	@Override
	public String parsePhotoURL() throws IOException {
		JsoupParser jsoupParser = new JsoupParser();
		try {
			return  jsoupParser.parseImgSource(jsoupParser.parseDataFromHtml(pageHtml, "img.thumbimage"));
		} catch (NullPointerException e) {
			return "https://upload.wikimedia.org/wikipedia/en/thumb/3/3d/Banglapedia.svg/220px-Banglapedia.svg.png";
		}
		
	}

}
