package fr.eni.enchere.bll;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.EnchereDAO;
import fr.eni.enchere.dal.FactoryDAO;

public class EnchereManager implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		this.enchereDAO=FactoryDAO.getEnchereDAO();
	}

	public Enchere insert(String dateEnchere, Integer montantEnchere, int noArticle, int noUtilisateur) {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	
		Enchere en = new Enchere(LocalDateTime.parse(dateEnchere, dt), montantEnchere);
			try {
				en=this.enchereDAO.insert(en, noArticle, noUtilisateur);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return en;
	}
	
	public Enchere selectByNoEnchere(int noEnchere) {
		Enchere en = null;
		try {
			en=this.enchereDAO.selectByNoEnchere(noEnchere);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return en;
	}
	
	public List<Enchere> selectAllEncheres() {
		List<Enchere> lstEncheres = new ArrayList<>();
		try {
			lstEncheres=this.enchereDAO.selectAllEncheres();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstEncheres;
	}
	
//	List<Enchere> selectAllEncheresByNoArticle(int noArticle) throws BusinessException;
	
	public List<Enchere> selectAllEncheresByNoUtilisateur(int noUtilisateur) {
		List<Enchere> lstEncheres = new ArrayList<>();
		try {
			lstEncheres=this.enchereDAO.selectAllEncheresByNoUtilisateur(noUtilisateur);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstEncheres;
	}
	
	//	void update(Enchere e) throws BusinessException;
	public void delete(int noEnchere) {
		try {
			this.enchereDAO.delete(noEnchere);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
