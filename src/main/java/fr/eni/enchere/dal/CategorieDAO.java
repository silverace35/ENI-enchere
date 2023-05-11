package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.exceptions.BusinessException;

public interface CategorieDAO {
	
	Categorie insert(Categorie c) throws BusinessException;
	Categorie selectByNoCategorie(int noCategorie) throws BusinessException;
	List<Categorie> selectAllCategories() throws BusinessException;
	void update(Categorie c) throws BusinessException;
	void delete(int noCategorie) throws BusinessException;
	
	boolean checkLibelle(String libelle) throws BusinessException;
}
