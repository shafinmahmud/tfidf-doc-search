package shafin.ml.tfidf.model;

import java.io.Serializable;

public class DocSimilarity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String docName;
	private Double cosineVal;

	public DocSimilarity() {
		super();
	}

	public DocSimilarity(String docName, Double cosineVal) {
		this.docName = docName;
		this.cosineVal = cosineVal;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Double getCosineVal() {
		return cosineVal;
	}

	public void setCosineVal(Double cosineVal) {
		this.cosineVal = cosineVal;
	}

	@Override
	public String toString() {
		return "DocSimilarity [docName=" + docName + ", cosineVal=" + cosineVal + "]";
	}

}
