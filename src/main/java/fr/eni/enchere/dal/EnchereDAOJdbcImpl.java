package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	private static EnchereDAOJdbcImpl instance;
	
	private static final String INSERT_ENCHERE = "INSERT INTO encheres (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (?,?,?,?);";
	private static final String SELECT_ENCHERE_BY_ID = "SELECT * FROM encheres WHERE no_enchere = ?;";
	private static final String SELECT_ALL_ENCHERES = "SELECT * FROM encheres;";
	private static final String SELECT_ALL_ENCHERES_BY_NO_ARTICLE = "SELECT * FROM encheres WHERE no_article=?;";
	private static final String SELECT_ALL_ENCHERES_BY_NO_UTILISATEUR = "SELECT * FROM encheres WHERE no_utilisateur=?;";
	private static final String UPDATE_ENCHERE= "UPDATE encheres SET (date_enchere=?, montant_enchere=?) WHERE no_enchere=?;";
	private static final String DELETE_ENCHERE = "DELETE FROM encheres WHERE no_enchere=?;";
	
	private EnchereDAOJdbcImpl() {
	}

	//Singleton
	public static EnchereDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new EnchereDAOJdbcImpl();
		}
		return instance;
	}
	

	@Override
	public Enchere insert(Enchere en) throws BusinessException {
		if (en == null) {
			throw new BusinessException("L'enchere est null");
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_ENCHERE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setTimestamp(1, Timestamp.valueOf(en.getDateEnchere()));
			pstmt.setInt(2, en.getMontantEnchere());
			pstmt.setInt(3, en.getNoArticle());
			pstmt.setInt(4, en.getNoUtilisateur());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				en.setNoEnchere(rs.getInt(1));
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : insertion enchere");
		}
		return en;
	}

	@Override
	public Enchere selectByNoEnchere(int noEnchere) throws BusinessException {
		Enchere en = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_ID);
			pstmt.setInt(1,noEnchere);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				en = new Enchere(
						rs.getInt("no_enchere"),
						rs.getTimestamp("date_enchere").toLocalDateTime(),
						rs.getInt("montant_enchere"),
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur"));
			}
		} catch(Exception e) {
			throw new BusinessException("Echec DAL : selectByNoEnchere");
		}
		return en;
	}

	@Override
	public List<Enchere> selectAllEncheres() throws BusinessException {
		List<Enchere> lstEncheres = new ArrayList<>();
		Enchere en = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				en = new Enchere (rs.getInt("no_enchere"), 
						rs.getTimestamp("date_enchere").toLocalDateTime(), 
						rs.getInt("montant_enchere"),
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur"));
				lstEncheres.add(en);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : selectAllEncheres");
		}
		return lstEncheres;
	}

	@Override
	public List<Enchere> selectAllEncheresByNoArticle(int noArticle) throws BusinessException {
		List<Enchere> lstEncheres = new ArrayList<>();
		Enchere en = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				en = new Enchere (rs.getInt("no_enchere"), 
						rs.getTimestamp("date_enchere").toLocalDateTime(), 
						rs.getInt("montant_enchere"),
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur"));
				lstEncheres.add(en);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : selectAllEncheresByNoArticle");
		}
		return lstEncheres;
	}

	@Override
	public List<Enchere> selectAllEncheresByNoUtilisateur(int noUtilisateur) throws BusinessException {
		List<Enchere> lstEncheres = new ArrayList<>();
		Enchere en = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_BY_NO_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				en = new Enchere (rs.getInt("no_enchere"), 
						rs.getTimestamp("date_enchere").toLocalDateTime(), 
						rs.getInt("montant_enchere"),
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur"));
				lstEncheres.add(en);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : selectAllEncheresByNoUtilisateur");
		}
		return lstEncheres;
	}

	@Override
	public void update(Enchere en) throws BusinessException {
		if(en == null){
			throw new BusinessException("L'enchere est null");
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ENCHERE);
			pstmt.setTimestamp(1, Timestamp.valueOf(en.getDateEnchere()));
			pstmt.setInt(2, en.getMontantEnchere());
			pstmt.setInt(3, en.getNoEnchere());
			pstmt.setInt(4, en.getNoArticle());
			pstmt.setInt(5, en.getNoUtilisateur());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : update enchere");
		}
	}

	@Override
	public void delete(int noEnchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ENCHERE);
			pstmt.setInt(1, noEnchere);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : delete enchere");		}
	}

}
