package fr.eni.enchere.dal;

public class FactoryDAO {
	
	public static DAOUtilisateur getUtilisateurDAO(){
		return UtilisateurDAOJdbcImpl.getInstance();
	}
	
	public static CategorieDAO getCategorieDAO(){
		return CategorieDAOJdbcImpl.getInstance();
	}
	
	public static RetraitDAO getRetraitDAO(){
		return RetraitDAOJdbcImpl.getInstance();
	}
	
	public static ArticleVenduDAO getArticleVenduDAO() {
		return ArticleVenduDAOJdbcImpl.getInstance();
	}
	
	public static EnchereDAO getEnchereDAO() {
		return EnchereDAOJdbcImpl.getInstance();
	}
	
	public static ImageDAO getImageDAO() {
		return ImageDAOJdbcImpl.getInstance();
	}
}