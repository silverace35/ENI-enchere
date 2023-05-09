package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DAOUtilisateur;
import fr.eni.enchere.dal.FactoryDAO;

public class UtilisateurManager {
	private DAOUtilisateur daoUtilisateur;
		
		/**
		 * Le constructeur permet d'initialiser la variable membre repasDAO pour 
		 * permettre une communication avec la base de données. 
		 */
	public UtilisateurManager() {
		this.daoUtilisateur=FactoryDAO.getUtilisateurDAO();
	}
	
	public Utilisateur insert(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String mdp, Integer credit) throws Exception {
		Utilisateur u = null;
		try {
			u = this.daoUtilisateur.insert(new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, mdp, credit, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
		
	
	
}
