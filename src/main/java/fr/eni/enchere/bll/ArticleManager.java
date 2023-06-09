package fr.eni.enchere.bll;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
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
	
	public List<ArticleVendu> getArticlesTerminerGagner(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllArticleVenduEnCoursTerminerGagner(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getVentesNonDebute(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllVenteNonDebute(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getVentesEnCours(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllVenteEnCours(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getVentesTerminer(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			listArticle = this.articleVenduDAO.selectAllVenteTerminer(noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public List<ArticleVendu> getArticlesEnCoursPasEncherie(int noUtilisateur) {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
		try {
			List<ArticleVendu> toRemove = new ArrayList<ArticleVendu>();
			listArticle = this.articleVenduDAO.selectAllArticleVenduEnCoursPasEncherie(noUtilisateur);
			for (ArticleVendu articleVendu : listArticle) {
				if (articleVendu.getNoUtilisateur() == noUtilisateur) {
					toRemove.add(articleVendu);
				}
			}
			listArticle.removeAll(toRemove);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return listArticle;
	}
	
	public ArticleVendu getByNoArticle(int noArticle) {
		ArticleVendu av = null;
		
		try {
			av = this.articleVenduDAO.selectByNoArticle(noArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return av;
	}
	
	public void update(ArticleVendu av) {
			
		try {
			this.articleVenduDAO.update(av);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ArticleVendu> getAllArticles() {
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		try {
			listArticle = this.articleVenduDAO.selectAllArticleVendu();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listArticle;
	}
	
	public boolean checkArticleUtilisateur(int noArticle, int noUtilisateur) {
		boolean res = false;
		try {
			 res=this.articleVenduDAO.checkArticleUtilisateur(noArticle, noUtilisateur);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return res;
	}


	public void delete(int noArticle) {
		try {
			this.articleVenduDAO.delete(noArticle);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteByUserId(int noUtilisateur) {
		try {
			this.articleVenduDAO.deleteByUserId(noUtilisateur);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePrixDeVente(Integer prixDeVente, Integer noArticle) {
		try {
			if (prixDeVente != null) {
				this.articleVenduDAO.updatePrixDeVente(prixDeVente, noArticle);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOkVendeur(Boolean okVendeur, Integer noArticle) {
		try {
			if (okVendeur != null) {
				this.articleVenduDAO.updateOkVendeur(okVendeur, noArticle);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOkAcheteur(Boolean okAcheteur, Integer noArticle) {
		try {
			if (okAcheteur != null) {
				this.articleVenduDAO.updateOkAcheteur(okAcheteur, noArticle);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
