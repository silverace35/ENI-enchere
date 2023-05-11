package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.controller.ErrorCodes;
import fr.eni.enchere.dal.DAOUtilisateur;
import fr.eni.enchere.dal.FactoryDAO;
import fr.eni.enchere.dal.exceptions.BusinessException;

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
			if (this.daoUtilisateur.checkPseudoEmail(pseudo, email)) {
				System.out.println("j'ai cramé le doublon");
				throw new BusinessException(ErrorCodes.PSEUDO_OR_EMAIL_ALREADY_EXIST.getMessage());
			}
			
		} catch (BusinessException be) {
			throw be;
		}
		try {
			u = this.daoUtilisateur.insert(new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, mdp, credit, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
		
	public Utilisateur validerPwd(String pseudo, String pwd) {
		Utilisateur u = null;
		try {
			
			u = this.daoUtilisateur.checkPwd(pseudo, pwd);
			System.out.println(u);
			if (u!=null) {
				System.out.println("MSG BLL j'ai trouvé un utilisateur");
				
			} else {
				//TODO remonter msg erreur
				System.out.println("MSG BLL Login ou mdp incorrect");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	public void deleteUtilisateur(int noUtilisateur) {
		// TODO Auto-generated method stub
		try {
			this.daoUtilisateur.delete(noUtilisateur) ;
		} catch (Exception e) {
			
		}
			
	}

	public Utilisateur getUtilisateurByNoUtilisateur(int noUtilisateur) {
		// TODO Auto-generated method stub
		Utilisateur u = null;
		try {
			u= this.daoUtilisateur.selectByNoUtilisateur(noUtilisateur);
		}  catch (Exception e) {
			
		}
		return u;
	}

	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		try {
			Utilisateur oldUser = this.daoUtilisateur.selectByNoUtilisateur(utilisateur.getNoUtilisateur());
			if (!oldUser.getPseudo().equals(utilisateur.getPseudo())) {
				if (this.daoUtilisateur.checkPseudo(utilisateur.getPseudo())) {
					System.out.println("j'ai cramé le doublon pseudo existe");
					throw new BusinessException(ErrorCodes.PSEUDO_ALREADY_EXIST.getMessage());
				}
			}
			if (!oldUser.getEmail().equals(utilisateur.getEmail())) {
				if (this.daoUtilisateur.checkEmail(utilisateur.getEmail())) {
					System.out.println("j'ai cramé le doublon email existe");
					throw new BusinessException(ErrorCodes.EMAIL_ALREADY_EXIST.getMessage());
				}
			}
		} catch (BusinessException be) {
			throw be;
		}
		try {
			this.daoUtilisateur.update(utilisateur);
			System.out.println("ça try d'update l'utilisateur");
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
