package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.exceptions.BusinessException;

public interface RetraitDAO {

	Retrait insert(Retrait r) throws BusinessException;
	Retrait selectByNoRetrait(int noRetrait) throws BusinessException;
	List<Retrait> selectAllRetraits() throws BusinessException;
	void update(Retrait r) throws BusinessException;
	void delete(int noArticle) throws BusinessException;
	
}
