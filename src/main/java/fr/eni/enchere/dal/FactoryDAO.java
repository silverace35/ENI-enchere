package fr.eni.enchere.dal;

public class FactoryDAO {
	
	public static DAOUtilisateur getUtilisateurDAO(){
		return UtilisateurDAOJdbcImpl.getInstance();
	}
}