package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;


public interface DAOUtilisateur {

	// Select one
	Utilisateur selectByNoUtilisateur(int noUtilisateur) throws BusinessException;

	// Select All
	List<Utilisateur> selectAll() throws BusinessException;

	// Update
	void update(Utilisateur u) throws BusinessException;

	// Create
	Utilisateur insert(Utilisateur u) throws BusinessException;

	// Delete with noUtilisateur
	void delete(int noUtilisateur) throws BusinessException;
	
	// Check login/email and pwd match
	Utilisateur checkPwd(String pseudo, String email, String pwd) throws BusinessException;
	
	// Check whether the pseudo or the email already exists
	public boolean checkPseudoEmail(String pseudo, String email) throws BusinessException;
	// Check if the pseudo already exists
	public boolean checkEmail(String email) throws BusinessException;
	// Check if the email already exists
	public boolean checkPseudo(String pseudo) throws BusinessException;
}