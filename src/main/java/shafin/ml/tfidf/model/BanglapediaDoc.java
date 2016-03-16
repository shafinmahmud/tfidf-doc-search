package shafin.ml.tfidf.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "banglapediaDoc")
public class BanglapediaDoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field
	@JsonProperty("docID")
	private int docID;

	@Field
	@JsonProperty("source")
	private String source;

	@Field
	@JsonProperty("lang")
	private String lang;

	@Field
	@JsonProperty("url")
	private String url;

	@Field
	@JsonProperty("otherLangURL")
	private String otherLangURL;

	@Field
	@JsonProperty("title")
	private String title;

	@Field
	@JsonProperty("keywords")
	private List<String> keywords;

	@Field
	@JsonProperty("author")
	private String author;

	@Field
	@JsonProperty("photoURL")
	private String photoURL;
	
	@Field
	@JsonProperty("article")
	private String article;

	public BanglapediaDoc() {
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOtherLangURL() {
		return otherLangURL;
	}

	public void setOtherLangURL(String otherLangURL) {
		this.otherLangURL = otherLangURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	

}
