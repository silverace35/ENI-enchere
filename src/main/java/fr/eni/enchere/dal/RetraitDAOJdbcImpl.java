package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class RetraitDAOJdbcImpl implements RetraitDAO {
	private static RetraitDAOJdbcImpl instance;
	
	private static final String INSERT_RETRAIT = "INSERT INTO Retraits(rue, code_postal, ville) VALUES(?, ?, ?);";
	private static final String SELECT_RETRAIT_BY_ID = "SELECT * FROM Retraits WHERE noArticle=?;";
	private static final String SELECT_ALL_RETRAITS = "SELECT * FROM Retraits";
	private static final String UPDATE_RETRAIT = "UPDATE Retraits SET rue=?, code_postal=?, ville=? WHERE noArticle=?;";
	private static final String DELETE_RETRAIT = "DELETE FROM Retraits WHERE noArticle=?;";
	
	private RetraitDAOJdbcImpl() {
	}

	//Singleton
	public static RetraitDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new RetraitDAOJdbcImpl();
		}
		return instance;
	}

	@Override
	public Retrait insert(Retrait r) throws BusinessException {
		if (r == null) {
			throw new BusinessException("Le retrait est null");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_RETRAIT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, r.getNoArticle());
			pstmt.setString(2, r.getRue());
			pstmt.setString(3, r.getCodePostal());
			pstmt.setString(4, r.getVille());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				r.setNoArticle(rs.getInt(1));
			}
		} catch (Exception e) {
			throw new BusinessException();
		}
		return r;
	}

	@Override
	public Retrait selectByNoArticle(int noArticle) throws BusinessException {
		Retrait r = null;
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_RETRAIT_BY_ID);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				r = new Retrait(rs.getInt("noArticle"), rs.getString("rue"), rs.getString("codePostal"), rs.getString("ville"));
			}
			pstmt.executeQuery();
		} catch (Exception e) {
			throw new BusinessException();
		}
		return r;
	}

	@Override
	public List<Retrait> selectAllRetraits() throws BusinessException {
		List<Retrait> listeRetraits = new ArrayList<>();
		Retrait r = new Retrait();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_RETRAITS);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				r = new Retrait(rs.getInt("noArticle"), rs.getString("rue"), rs.getString("codePostal"), rs.getString("ville"));
				listeRetraits.add(r);
			}
		} catch (Exception e) {
			throw new BusinessException();
		}
		return listeRetraits;
	}

	@Override
	public void update(Retrait r) throws BusinessException {
		if(r == null){
			throw new BusinessException("Le retrait est null");
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_RETRAIT);
			pstmt.setInt(1, r.getNoArticle());
			pstmt.setString(2, r.getRue());
			pstmt.setString(3, r.getCodePostal());
			pstmt.setString(4, r.getVille());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
		
	}

	@Override
	public void delete(int noArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAIT);
			pstmt.setInt(1, noArticle);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
		
	}

}
