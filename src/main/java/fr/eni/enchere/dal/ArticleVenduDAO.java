package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.dal.exceptions.BusinessException;

public interface ArticleVenduDAO {

	ArticleVendu insert(ArticleVendu av) throws BusinessException;
	ArticleVendu selectByNoArticle(int noArticle) throws BusinessException;
	List<ArticleVendu> selectAllArticleVendu() throws BusinessException;
	List<ArticleVendu> selectAllArticleVenduEnCours() throws BusinessException;
	void update(ArticleVendu av) throws BusinessException;
	void delete(int noArticle) throws BusinessException;
	List<ArticleVendu> selectAllArticleVenduEnCoursEncherie(int noUtilisateur) throws BusinessException;
	List<ArticleVendu> selectAllArticleVenduEnCoursPasEncherie(int noUtilisateur) throws BusinessException;
	List<ArticleVendu> selectAllArticleVenduEnCoursTerminerGagner(int noUtilisateur) throws BusinessException;
	List<ArticleVendu> selectAllVenteEnCours(int noUtilisateur) throws BusinessException;
	List<ArticleVendu> selectAllVenteNonDebute(int noUtilisateur) throws BusinessException;
	List<ArticleVendu> selectAllVenteTerminer(int noUtilisateur) throws BusinessException;
	boolean checkArticleUtilisateur(int noArticle, int noUtilisateur) throws BusinessException;
	void deleteByUserId(int noUtilisateur) throws BusinessException;
	void updatePrixDeVente(Integer prixDeVente, Integer noArticle) throws BusinessException;
	void updateOkVendeur(boolean okVendeur, Integer noArticle) throws BusinessException;
	void updateOkAcheteur(boolean okAcheteur, Integer noArticle) throws BusinessException;
}
