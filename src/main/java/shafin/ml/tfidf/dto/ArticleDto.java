package shafin.ml.tfidf.dto;

public class ArticleDto {

	private String fileName;
	private String url;
	private String title;
	private String photoURL;
	private String article;
	private Double cosineValue;
	
	
	public ArticleDto() {
		
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public Double getCosineValue() {
		return cosineValue;
	}

	public void setCosineValue(Double cosineValue) {
		this.cosineValue = cosineValue;
	}
	
	
}
