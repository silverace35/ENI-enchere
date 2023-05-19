package fr.eni.enchere.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleVendu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private Integer prixInitial;
	private Integer prixVente;
	private Integer noUtilisateur;
	private Integer noCategorie;
	private boolean retraitOkVendeur;
	private boolean retraitOkAcheteur;
	private ArticleStatus articleStatus;
	private String nomPrenomAuteur;

	public ArticleVendu() {
	}


	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, Integer prixInitial, Integer prixVente, Integer noUtilisateur, Integer noCategorie,
			boolean retraitOkVendeur, boolean retraitOkAcheteur, String nomPrenomAuteur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noUtilisateur = noUtilisateur;
		this.noCategorie = noCategorie;
		this.retraitOkVendeur = retraitOkVendeur;
		this.retraitOkAcheteur = retraitOkAcheteur;
		this.nomPrenomAuteur = nomPrenomAuteur;
		this.updateStatus();
	}
	
	public ArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, Integer prixInitial, Integer prixVente, Integer noUtilisateur, Integer noCategorie,
			boolean retraitOkVendeur, boolean retraitOkAcheteur, String nomPrenomAuteur) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noUtilisateur = noUtilisateur;
		this.noCategorie = noCategorie;
		this.retraitOkVendeur = retraitOkVendeur;
		this.retraitOkAcheteur = retraitOkAcheteur;
		this.nomPrenomAuteur = nomPrenomAuteur;
		this.updateStatus();
	}

	public void updateStatus() {
		int diffDepart = this.dateDebutEncheres.compareTo(LocalDateTime.now());
		int diffFin = this.dateFinEncheres.compareTo(LocalDateTime.now());
		
		if (diffDepart > 0) {
			//Cas ou la date de depart des encheres est apres la date actuelle.
			this.articleStatus = ArticleStatus.CR;
		} else if (diffDepart <= 0 && diffFin > 0) {
			//Cas ou la date de depart est passer mais pas celle de fin
			this.articleStatus = ArticleStatus.EC;
		} else if (this.retraitOkAcheteur && this.retraitOkVendeur) {
			//Cas ou l'acheteur et le vendeur ont valider le retrait
			this.articleStatus = ArticleStatus.RT;
		} else if (diffFin <= 0) {
			//Cas ou la date de fin est depasser
			this.articleStatus = ArticleStatus.ET;
			//?
		}
	}


	public int getNoArticle() {
		return noArticle;
	}


	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}


	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}


	public Integer getPrixInitial() {
		return prixInitial;
	}


	public void setPrixInitial(Integer prixInitial) {
		this.prixInitial = prixInitial;
	}


	public Integer getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}


	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}


	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}


	public Integer getNoCategorie() {
		return noCategorie;
	}


	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}


	public boolean isRetraitOkVendeur() {
		return retraitOkVendeur;
	}


	public void setRetraitOkVendeur(boolean retraitOkVendeur) {
		this.retraitOkVendeur = retraitOkVendeur;
	}


	public boolean isRetraitOkAcheteur() {
		return retraitOkAcheteur;
	}


	public void setRetraitOkAcheteur(boolean retraitOkAcheteur) {
		this.retraitOkAcheteur = retraitOkAcheteur;
	}


	public ArticleStatus getArticleStatus() {
		return articleStatus;
	}


	public void setArticleStatus(ArticleStatus articleStatus) {
		this.articleStatus = articleStatus;
	}


	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", prixInitial="
				+ prixInitial + ", prixVente=" + prixVente + ", noUtilisateur=" + noUtilisateur + ", noCategorie="
				+ noCategorie + ", retraitOkVendeur=" + retraitOkVendeur + ", retraitOkAcheteur=" + retraitOkAcheteur
				+ ", articleStatus=" + articleStatus.name() + "]";
	}


	public String getNomPrenomAuteur() {
		return nomPrenomAuteur;
	}


	public void setNomPrenomAuteur(String nomPrenomAuteur) {
		this.nomPrenomAuteur = nomPrenomAuteur;
	}
}
