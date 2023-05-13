package fr.eni.enchere.bll;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.dal.ArticleVenduDAO;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class ArticleManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArticleVenduDAO articleVenduDAO;
	
	public ArticleManager() {
		this.articleVenduDAO = FactoryDAO.getArticleVenduDAO();
	}
	
	public ArticleVendu insert(String nomArticle, String description, LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, Integer prixInitial, Integer prixVente, Integer noUtilisateur, Integer noCategorie, Boolean retraitOkVendeur, Boolean retraitOkAcheteur, String nomPrenomAuteur) throws BusinessException {
		ArticleVendu av = null;

		try {
			av = this.articleVenduDAO.insert(new ArticleVendu(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, noUtilisateur, noCategorie, retraitOkVendeur, retraitOkAcheteur, nomPrenomAuteur));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return av;
	}
	
	public List<ArticleVendu> getArticlesEnCours() {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllArticleVenduEnCours();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getArticlesEnCoursEncherie(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllArticleVenduEnCoursEncherie(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getArticlesEnCoursPasEncherie(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllArticleVenduEnCoursPasEncherie(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}

}
