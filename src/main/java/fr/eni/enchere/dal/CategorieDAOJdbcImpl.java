package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class CategorieDAOJdbcImpl implements CategorieDAO {
	private static CategorieDAOJdbcImpl instance;
	
	private static final String INSERT_CATEGORIE = "INSERT INTO Categories(libelle) VALUES(?);";
	private static final String SELECT_CATEGORIE_BY_ID = "SELECT * FROM Categories WHERE no_Categorie=?;";
	private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Categories;";
	private static final String UPDATE_CATEGORIE= "UPDATE Categories SET libelle=? WHERE no_Categorie=?;";
	private static final String DELETE_CATEGORIE = "DELETE FROM Categories WHERE no_Categorie=?;";
	private static final String CHECK_LIBELLE="SELECT * FROM Categories WHERE libelle=?;";
	
	private CategorieDAOJdbcImpl() {
	}

	//Singleton
	public static CategorieDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new CategorieDAOJdbcImpl();
		}
		return instance;
	}

	@Override
	public Categorie insert(Categorie c) throws BusinessException {
		if (c == null) {
			BusinessException businessException = new BusinessException("La catégorie est null");
			throw businessException;
		}
		
		if(!checkLibelle(c.getLibelle())) {
			throw new BusinessException("Cette catégorie existe déjà, veuillez en choisir une autre");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_CATEGORIE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, c.getNoCategorie());
			pstmt.setString(2, c.getLibelle());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				c.setNoCategorie(rs.getInt(1));
			}
		} catch (Exception e) {
			throw new BusinessException();
		}
		return c;
	}

	@Override
	public Categorie selectByNoCategorie(int noCategorie) throws BusinessException {
		Categorie c = null;
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE_BY_ID);
			pstmt.setInt(1, noCategorie);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				c = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
			}
			pstmt.executeQuery();
		} catch (Exception e) {
			throw new BusinessException();
		}
		return c;
	}

	@Override
	public List<Categorie> selectAllCategories() throws BusinessException {
		List<Categorie> listeCategories = new ArrayList<>();
		Categorie c = null;
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_CATEGORIES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				c = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				listeCategories.add(c);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : selectAllCategories");
		}
		return listeCategories;
	}

	@Override
	public void update(Categorie c) throws BusinessException {
		if(c == null){
			BusinessException businessException = new BusinessException("La catégorie est null");
			throw businessException;
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CATEGORIE);
			pstmt.setInt(1, c.getNoCategorie());
			pstmt.setString(2, c.getLibelle());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
	}

	@Override
	public void delete(int noCategorie) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_CATEGORIE);
			pstmt.setInt(1, noCategorie);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
	}
	
	public boolean checkLibelle(String libelle) throws BusinessException{
		boolean isUnique = true;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(CHECK_LIBELLE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			 if(rs.next()) {
				 isUnique = false;
			 }
		}  catch (SQLException e) {
			 throw new BusinessException();
		}
		return isUnique;
	}

}
