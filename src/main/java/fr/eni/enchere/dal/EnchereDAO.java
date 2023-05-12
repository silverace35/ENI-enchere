package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.exceptions.BusinessException;

public interface EnchereDAO {

	Enchere insert(Enchere en, int noArticle, int noUtilisateur) throws BusinessException;
	Enchere selectByNoEnchere(int noEnchere) throws BusinessException;
	List<Enchere> selectAllEncheres() throws BusinessException;
	List<Enchere> selectAllEncheresByNoArticle(int noArticle) throws BusinessException;
	List<Enchere> selectAllEncheresByNoUtilisateur(int noUtilisateur) throws BusinessException;
	void update(Enchere e) throws BusinessException;
	void delete(int noEnchere) throws BusinessException;
	
}
