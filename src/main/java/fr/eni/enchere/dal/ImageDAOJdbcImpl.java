package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Image;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class ImageDAOJdbcImpl implements ImageDAO {
	private static ImageDAOJdbcImpl instance;
	
	private static final String INSERT_IMAGE = "INSERT INTO images(no_article, picture) VALUES(?, ?);";
	private static final String SELECT_IMAGE_BY_ID = "SELECT * FROM images WHERE no_article=?;";
	private static final String UPDATE_IMAGE = "UPDATE images SET picture=? WHERE no_article=?;";
	private static final String DELETE_IMAGE = "DELETE FROM images WHERE no_article=?;";
	private static final String SELECT_ALL_IMAGES = "SELECT * FROM images";
	
	private ImageDAOJdbcImpl() {
	}

	//Singleton
	public static ImageDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new ImageDAOJdbcImpl();
		}
		return instance;
	}

	@Override
	public Image insert(Image i) throws BusinessException {
		if (i == null) {
			throw new BusinessException("Pas d'image sélectionnée");
		}

		try (Connection cnx = ConnectionProvider.getConnection()) {
			System.out.println("noArticle " +i.getNoArticle());
			System.out.println("picture " +i.getPicture());
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_IMAGE);
			pstmt.setInt(1, i.getNoArticle());
			pstmt.setString(2, i.getPicture());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new BusinessException();
		}
		return i;
	}

	@Override
	public Image selectByNoArticle(int noArticle) throws BusinessException {
		Image i = null;
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_IMAGE_BY_ID);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				i = new Image(rs.getInt("no_article"), rs.getString("picture"));
			}
		} catch (Exception e) {
			throw new BusinessException();
		}
		return i;
	}

	@Override
	public void update(Image i) throws BusinessException {
		if(i == null){
			throw new BusinessException("Pas d'image sélectionnée");
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_IMAGE);
			pstmt.setString(1, i.getPicture());
			pstmt.setInt(2, i.getNoArticle());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
	}

	@Override
	public void delete(int noArticle) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_IMAGE);
			pstmt.setInt(1, noArticle);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new BusinessException();
		}
	}

	@Override
	public List<Image> selectAll() throws BusinessException {
		List<Image> lstImages = new ArrayList<>();
		Image i = new Image();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_IMAGES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				i = new Image(rs.getInt("no_article"), rs.getString("picture"));
				lstImages.add(i);
			}
		} catch (Exception e) {
			throw new BusinessException("Echec DAL : insertion retrait");
		}
		return lstImages;
	}

}
