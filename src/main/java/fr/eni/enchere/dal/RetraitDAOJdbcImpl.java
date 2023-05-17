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
	
	private static final String INSERT_RETRAIT = "INSERT INTO Retraits(no_article,rue, code_postal, ville) VALUES(?, ?, ?, ?);";
	private static final String SELECT_RETRAIT_BY_ID = "SELECT * FROM Retraits WHERE no_article=?;";
	private static final String SELECT_ALL_RETRAITS = "SELECT * FROM Retraits";
	private static final String UPDATE_RETRAIT = "UPDATE Retraits SET rue=?, code_postal=?, ville=? WHERE no_article=?;";
	private static final String DELETE_RETRAIT = "DELETE FROM Retraits WHERE no_article=?;";
	
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
			//TODO : A TESTER AMBIGUITE NoARTICLE ET NoRETRAIT
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_RETRAIT);
			pstmt.setInt(1, r.getNoRetrait());
			pstmt.setString(2, r.getRue());
			pstmt.setString(3, r.getCodePostal());
			pstmt.setString(4, r.getVille());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new BusinessException();
		}
		return r;
	}

	@Override
	public Retrait selectByNoRetrait(int noRetrait) throws BusinessException {
		Retrait r = null;
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_RETRAIT_BY_ID);
			pstmt.setInt(1, noRetrait);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("J'ai execute la query");
			if (rs.next()) {
				r = new Retrait(rs.getInt("no_article"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
				System.out.println("DAL : "+r.toString());
			}
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
				r = new Retrait(rs.getInt("no_retrait"), rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
				listeRetraits.add(r);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : insertion retrait");
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
			pstmt.setString(1, r.getRue());
			pstmt.setString(2, r.getCodePostal());
			pstmt.setString(3, r.getVille());
			pstmt.setInt(4, r.getNoRetrait());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
		
	}

	@Override
	public void delete(int noRetrait) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAIT);
			pstmt.setInt(1, noRetrait);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
		
	}

}
