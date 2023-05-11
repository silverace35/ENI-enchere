package fr.eni.enchere.bo;

public class Retrait {
	private Integer noArticle;
	private String rue;
	private String codePostal;
	private String ville;
	private Boolean isRetire = false;
	
	public Retrait() {
	}

	public Retrait(Integer noArticle, String rue, String codePostal, String ville, Boolean isRetire) {
		super();
		this.noArticle = noArticle;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.isRetire = isRetire;
	}
	
	public Retrait(String rue, String codePostal, String ville, Boolean isRetire) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.isRetire = isRetire;
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Boolean getIsRetire() {
		return isRetire;
	}

	public void setIsRetire(Boolean isRetire) {
		this.isRetire = isRetire;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Retrait [noArticle=");
		builder.append(noArticle);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append(", isRetire=");
		builder.append(isRetire);
		builder.append("]");
		return builder.toString();
	}
}
