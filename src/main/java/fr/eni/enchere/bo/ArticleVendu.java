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
	private String descritpion;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private int prixInitial;
	private int prixVente;
	private int noUtilisateur;
	private int noCategorie;
	private boolean retraitOkVendeur;
	private boolean retraitOkAcheteur;
	private ArticleStatus articleStatus;

	public ArticleVendu() {
	}


	public ArticleVendu(int noArticle, String nomArticle, String descritpion, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int prixInitial, int prixVente, int noUtilisateur, int noCategorie,
			boolean retraitOkVendeur, boolean retraitOkAcheteur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.descritpion = descritpion;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noUtilisateur = noUtilisateur;
		this.noCategorie = noCategorie;
		this.retraitOkVendeur = retraitOkVendeur;
		this.retraitOkAcheteur = retraitOkAcheteur;
		this.updateStatus();
	}
	
	public ArticleVendu(String nomArticle, String descritpion, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int prixInitial, int prixVente, int noUtilisateur, int noCategorie,
			boolean retraitOkVendeur, boolean retraitOkAcheteur) {
		super();
		this.nomArticle = nomArticle;
		this.descritpion = descritpion;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.noUtilisateur = noUtilisateur;
		this.noCategorie = noCategorie;
		this.retraitOkVendeur = retraitOkVendeur;
		this.retraitOkAcheteur = retraitOkAcheteur;
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
		} else if (diffFin <= 0) {
			//Cas ou la date de fin est depasser
			this.articleStatus = ArticleStatus.ET;
		} else if (this.retraitOkAcheteur && this.retraitOkVendeur) {
			//Cas ou l'acheteur et le vendeur ont valider le retrait
			this.articleStatus = ArticleStatus.RT;
		} else {
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


	public String getDescritpion() {
		return descritpion;
	}


	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
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


	public int getPrixInitial() {
		return prixInitial;
	}


	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public int getNoUtilisateur() {
		return noUtilisateur;
	}


	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}


	public int getNoCategorie() {
		return noCategorie;
	}


	public void setNoCategorie(int noCategorie) {
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
}
