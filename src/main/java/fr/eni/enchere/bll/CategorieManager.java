package fr.eni.enchere.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.CategorieDAO;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class CategorieManager {
	private CategorieDAO categorieDAO;

	/**
	 * Le constructeur permet d'initialiser la variable membre repasDAO pour
	 * permettre une communication avec la base de données.
	 */
	public CategorieManager() {
		this.categorieDAO = FactoryDAO.getCategorieDAO();
	}

	public Categorie insert(String libelle) throws Exception {
		Categorie c = null;
		try {
			if (this.categorieDAO.checkLibelle(libelle)) {
				throw new BusinessException("Cette catégorie existe déjà, veuillez en choisir une autre");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			c = this.categorieDAO.insert(new Categorie(libelle));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public Categorie getCategorieByNoCategorie(int noCategorie) {
		Categorie c = null;
		try {
			c = this.categorieDAO.selectByNoCategorie(noCategorie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public List<Categorie> selectAllCategories() throws BusinessException {
		List<Categorie> listeCategories = new ArrayList<>();
		try {
			listeCategories = this.categorieDAO.selectAllCategories();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeCategories;
	}

	public void update(Categorie c) {
		
		try {
			Categorie oldCategorie = this.categorieDAO.selectByNoCategorie(c.getNoCategorie());
			if (!oldCategorie.getLibelle().equals(c.getLibelle())) {
				if (this.categorieDAO.checkLibelle(c.getLibelle())) {
					throw new BusinessException("La catégorie existe déja");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.categorieDAO.update(c);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCategorie(int noCategorie) {
		try {
			this.categorieDAO.delete(noCategorie) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
