package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Image;
import fr.eni.enchere.dal.exceptions.BusinessException;

public interface ImageDAO {

	Image insert(Image i) throws BusinessException;
	Image selectByNoArticle(int noArticle) throws BusinessException;
	void update(Image i) throws BusinessException;
	void delete(int noArticle) throws BusinessException;
	
}
