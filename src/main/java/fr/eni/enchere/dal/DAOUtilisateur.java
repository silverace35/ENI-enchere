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
}
