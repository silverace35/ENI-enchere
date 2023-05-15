package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO{
	
	private static ArticleVenduDAOJdbcImpl instance;
	
	private static final String SELECT_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie, retrait_ok_vendeur, retrait_ok_acheteur, u.nom, u.prenom FROM articles_vendus a INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur";
	private static final String SELECT_ALL_EN_COURS = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie, retrait_ok_vendeur, retrait_ok_acheteur, u.nom, u.prenom FROM articles_vendus a INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur WHERE date_debut_encheres < now() AND date_fin_encheres > now()";
	private static final String SELECT_ALL_EN_COURS_ENCHERIE = "SELECT"
			+ " a.no_article, nom_article, description, date_debut_encheres,"
			+ " date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur,"
			+ " no_categorie, retrait_ok_vendeur, retrait_ok_acheteur, u.nom, u.prenom FROM articles_vendus a"
			+ " INNER JOIN encheres e"
			+ " ON e.no_article = a.no_article"
			+ " INNER JOIN utilisateurs u"
			+ " ON u.no_utilisateur = a.no_utilisateur"
			+ " WHERE e.no_utilisateur = ?"
			+ " AND date_debut_encheres < now() AND date_fin_encheres > now()"
			+ " GROUP BY a.no_article;";
	private static final String SELECT_ALL_EN_COURS_PAS_ENCHERIE = "SELECT"
			+ " a.no_article, nom_article, description, date_debut_encheres,"
			+ " date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur,"
			+ " no_categorie, retrait_ok_vendeur, retrait_ok_acheteur, u.nom, u.prenom FROM articles_vendus a"
			+ " INNER JOIN utilisateurs u"
			+ " ON u.no_utilisateur = a.no_utilisateur"
			+ " WHERE no_article NOT IN"
			+ " (SELECT no_article FROM encheres where no_utilisateur = ?)"
			+ " AND date_debut_encheres < now() AND date_fin_encheres > now();";

	private static final String SELECT_ALL_TERMINER_GAGNER = "SELECT"
			+ " a.no_article, a.nom_article, a.description, a.date_debut_encheres,"
			+ " a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur,"
			+ " a.no_categorie, a.retrait_ok_vendeur, a.retrait_ok_acheteur"
			+ " FROM encheres e INNER JOIN articles_vendus a"
			+ " ON a.no_article = e.no_article"
			+ " WHERE (e.montant_enchere, e.no_article) IN"
			+ " (SELECT MAX(montant_enchere), no_article FROM encheres GROUP BY no_article)"
			+ " AND date_enchere <= now() AND e.no_utilisateur = ?;";
	private static final String SELECT_ALL_VENTE_EN_COURS = "SELECT"
			+ " * FROM articles_vendus"
			+ " WHERE no_utilisateur = ?"
			+ " AND date_debut_encheres <= now()"
			+ " AND date_fin_encheres > now()";
	private static final String SELECT_ALL_VENTE_NON_DEBUTE = "SELECT"
			+ " * FROM articles_vendus"
			+ " WHERE no_utilisateur = ?"
			+ " AND date_debut_encheres > now();";
	private static final String SELECT_ALL_VENTE_TERMINER = "SELECT"
			+ " * FROM articles_vendus"
			+ " WHERE no_utilisateur = ?"
			+ " AND date_fin_encheres <= now();";
	private static final String SELECT_ARTICLEVENDU_BY_ID = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie, retrait_ok_vendeur, retrait_ok_acheteur, u.nom, u.prenom FROM articles_vendus a INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur WHERE no_article=?";
	private static final String INSERT = "INSERT INTO articles_vendus (nom_article, description,"
			+ " date_debut_encheres, date_fin_encheres, prix_initial, prix_vente,"
			+ " no_utilisateur, no_categorie, retrait_ok_vendeur, retrait_ok_acheteur)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article=?;";
	private static final String UPDATE_ARTICLE= "UPDATE articles_vendus SET nom_article=?,"
			+ " description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?,"
			+ " prix_vente=?, no_utilisateur=?, no_categorie=?, retrait_ok_vendeur=?,"
			+ " retrait_ok_acheteur=? WHERE no_article=?;";
	
	public static ArticleVenduDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new ArticleVenduDAOJdbcImpl();
		}
		return instance;
	}
	
	private ArticleVenduDAOJdbcImpl() {
	}
	
	@Override
	public List<ArticleVendu> selectAllArticleVendu() throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticleVenduEnCoursEncherie(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_EN_COURS_ENCHERIE);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllVenteEnCours(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EN_COURS);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllVenteNonDebute(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_NON_DEBUTE);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllVenteTerminer(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_TERMINER);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticleVenduEnCoursTerminerGagner(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_TERMINER_GAGNER);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticleVenduEnCoursPasEncherie(int noUtilisateur) throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_EN_COURS_PAS_ENCHERIE);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticleVenduEnCours() throws BusinessException {
		List<ArticleVendu> listArticleVendu = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_EN_COURS);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArticleVendu av = null;
				
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
				listArticleVendu.add(av);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listArticleVendu;
	}
	
	
	@Override
	public ArticleVendu selectByNoArticle(int noArticle) throws BusinessException {
		ArticleVendu av = null;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLEVENDU_BY_ID);
			
			pstmt.setInt(1, noArticle);
			
			ResultSet rs = pstmt.executeQuery();
						
			if (rs.next()) {
				av = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						rs.getTimestamp("date_debut_encheres").toLocalDateTime(),
						rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						rs.getInt("no_utilisateur"),
						rs.getInt("no_categorie"),
						rs.getBoolean("retrait_ok_vendeur"),
						rs.getBoolean("retrait_ok_acheteur"),
						rs.getString("nom") + " " + rs.getString("prenom")
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		return av;
	}

	@Override
	public ArticleVendu insert(ArticleVendu av) throws BusinessException {
		if(av==null)
		{
			BusinessException businessException = new BusinessException("L'article vendu est null");
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, av.getNomArticle());
			pstmt.setString(2, av.getDescription());
			pstmt.setTimestamp(3, Timestamp.valueOf(av.getDateDebutEncheres()));
			pstmt.setTimestamp(4, Timestamp.valueOf(av.getDateFinEncheres()));
			if (av.getPrixInitial() == null) {
				pstmt.setNull(5, 0);
			} else {
				pstmt.setInt(5, av.getPrixInitial());
			}
			if (av.getPrixVente() == null) {
				if (av.getPrixInitial() == null) {
					pstmt.setNull(6, 0);
				} else {
					pstmt.setInt(6, av.getPrixInitial());
				}
			} else {
				pstmt.setInt(6, av.getPrixVente());
			}
			if (av.getNoUtilisateur() == null) {
				pstmt.setNull(7, 0);
			} else {
				pstmt.setInt(7, av.getNoUtilisateur());
			}
			if (av.getNoCategorie() == null) {
				pstmt.setNull(8, 0);
			} else {
				pstmt.setInt(8, av.getNoCategorie());
			}
			pstmt.setBoolean(9, av.isRetraitOkVendeur());
			pstmt.setBoolean(10, av.isRetraitOkAcheteur());
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				av.setNoArticle(rs.getInt(1));
			}
		}
		catch( SQLException e)
		{
			//e.printStackTrace();
			BusinessException businessException = new BusinessException(e.getMessage());
			throw businessException;
		}
		return av;
	}

	@Override
	public void update(ArticleVendu av) throws BusinessException {
		if(av==null)
		{
			BusinessException businessException = new BusinessException("L'article vendu est null");
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1, av.getNomArticle());
			pstmt.setString(2, av.getDescription());
			pstmt.setTimestamp(3, Timestamp.valueOf(av.getDateDebutEncheres()));
			pstmt.setTimestamp(4, Timestamp.valueOf(av.getDateFinEncheres()));
			if (av.getPrixInitial() == null) {
				pstmt.setNull(5, 0);
			} else {
				pstmt.setInt(5, av.getPrixInitial());
			}
			if (av.getPrixVente() == null) {
				pstmt.setNull(6, 0);
			} else {
				pstmt.setInt(6, av.getPrixVente());
			}
			if (av.getNoUtilisateur() == null) {
				pstmt.setNull(7, 0);
			} else {
				pstmt.setInt(7, av.getNoUtilisateur());
			}
			if (av.getNoCategorie() == null) {
				pstmt.setNull(8, 0);
			} else {
				pstmt.setInt(8, av.getNoCategorie());
			}
			pstmt.setBoolean(9, av.isRetraitOkVendeur());
			pstmt.setBoolean(10, av.isRetraitOkAcheteur());
			pstmt.setInt(11, av.getNoArticle());
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
	}

	@Override
	public void delete(int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, noArticle);
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
	}
}
