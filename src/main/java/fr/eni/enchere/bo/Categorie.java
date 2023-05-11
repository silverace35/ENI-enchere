package fr.eni.enchere.bo;

public class Categorie {
	private Integer noCategorie;
	private String libelle;
	
	public Categorie() {
	}

	public Categorie(Integer noCategorie, String libelle) {
		super();
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}
	
	public Categorie(String libelle) {
		super();
		this.libelle = libelle;
	}

	public Integer getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [noCategorie=");
		builder.append(noCategorie);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}
}
