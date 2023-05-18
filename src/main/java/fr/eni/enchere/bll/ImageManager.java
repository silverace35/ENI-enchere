package fr.eni.enchere.bll;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Image;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.ImageDAO;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class ImageManager {
	private ImageDAO imageDAO;

	public ImageManager() {
		this.imageDAO = FactoryDAO.getImageDAO();
	}
	
	public Image insert(Integer noArticle, String picture) throws Exception {
		Image i = null;
		try {
			i = this.imageDAO.insert(new Image(noArticle, picture));
		} catch (Exception e) {
			throw new BusinessException("Echec BLL : insertion image.");
			
		}
		return i;
	}
	
	public Image getImageBynoArticle(int noArticle) {
		Image i = null;
		try {
			i = this.imageDAO.selectByNoArticle(noArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public void update(Image i) {
			
			try {
				this.imageDAO.update(i);
	
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void deleteImage(int noArticle) {
		try {
			this.imageDAO.delete(noArticle) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
