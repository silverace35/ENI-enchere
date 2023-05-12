package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.RetraitDAO;

public class RetraitManager {
	private RetraitDAO retraitDAO;

	public RetraitManager() {
		this.retraitDAO = FactoryDAO.getRetraitDAO();
	}
	
	public Retrait insert(String rue, String codePostal, String ville) throws Exception {
		Retrait r = null;
		try {
			r = this.retraitDAO.insert(new Retrait(rue, codePostal, ville));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public Retrait getRetraitByNoArticle(int noArticle) {
		Retrait r = null;
		try {
			r = this.retraitDAO.selectByNoArticle(noArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public void update(Retrait r) {
			
			try {
				this.retraitDAO.update(r);
	
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void deleteRetrait(int noArticle) {
		try {
			this.retraitDAO.delete(noArticle) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
