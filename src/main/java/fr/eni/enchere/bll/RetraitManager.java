package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.RetraitDAO;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class RetraitManager {
	private RetraitDAO retraitDAO;

	public RetraitManager() {
		this.retraitDAO = FactoryDAO.getRetraitDAO();
	}
	
	public Retrait insert(Integer noRetrait, String rue, String codePostal, String ville) throws Exception {
		Retrait r = null;
		try {
			r = this.retraitDAO.insert(new Retrait(noRetrait, rue, codePostal, ville));
		} catch (Exception e) {
			throw new BusinessException("Echec BLL : insertion retrait.");
			
		}
		return r;
	}
	
	public Retrait getRetraitByNoRetrait(int noRetrait) {
		Retrait r = null;
		try {
			r = this.retraitDAO.selectByNoRetrait (noRetrait);
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
	
	public void deleteRetrait(int noRetrait) {
		try {
			this.retraitDAO.delete(noRetrait) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
