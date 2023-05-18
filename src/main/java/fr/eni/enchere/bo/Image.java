package fr.eni.enchere.bo;

public class Image {
	private int noArticle;
	private String picture;
	
	public Image() {
		// TODO Auto-generated constructor stub
	}

	public Image(Integer noArticle, String picture) {
		super();
		this.noArticle = noArticle;
		this.picture = picture;
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
}
