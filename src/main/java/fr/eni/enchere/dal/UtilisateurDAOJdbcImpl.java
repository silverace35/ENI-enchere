package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.controller.ErrorCodes;
import fr.eni.enchere.dal.exceptions.BusinessException;
import fr.eni.enchere.test.Utils;

public class UtilisateurDAOJdbcImpl implements DAOUtilisateur{

	private static UtilisateurDAOJdbcImpl instance;
	
	private static final String SELECTBYID="SELECT * FROM utilisateurs WHERE no_utilisateur=?;";
	private static final String SELECTALL="SELECT * FROM utilisateurs";
	private static final String UPDATE="UPDATE utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?,"
			+ " rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?;";
	private static final String UPDATE_CREDIT="UPDATE utilisateurs SET credit=? WHERE no_utilisateur=?;";
	private static final String DESACTIVE_UTILISATEUR="UPDATE utilisateurs SET desactive=1 WHERE no_utilisateur=?;";
	private static final String INSERT="INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue,"
			+ " code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String DELETEBYID="DELETE FROM utilisateurs WHERE no_utilisateur=?;";
	private static final String SELECTBYPWD="SELECT * FROM utilisateurs WHERE ((pseudo=? OR email=?) AND (mot_de_passe=?));";
	private static final String CHECK="SELECT * FROM utilisateurs WHERE pseudo=? OR email=?;";
	private static final String CHECKPSEUDO="SELECT * FROM utilisateurs WHERE pseudo=?;";
	private static final String CHECKEMAIL="SELECT * FROM utilisateurs WHERE email=?;";
	private static final String SEARCH = "SELECT no_article FROM articles_vendus WHERE nom_article like ? AND no_categorie IN (?);";
	
	private UtilisateurDAOJdbcImpl() {
	}

	//Singleton
	public static UtilisateurDAOJdbcImpl getInstance() {
		if (instance == null) {
			instance = new UtilisateurDAOJdbcImpl();
		}
		return instance;
	}

	@Override
	public Utilisateur selectByNoUtilisateur(int noUtilisateur) throws BusinessException {
		Utilisateur utilisateur = null;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECTBYID);
			
			pstmt.setInt(1, noUtilisateur);
			
			ResultSet rs = pstmt.executeQuery();
						
			if (rs.next()) {
				utilisateur = new Utilisateur(
						rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getString("rue"),
						rs.getString("code_postal"),
						rs.getString("ville"),
						rs.getString("mot_de_passe"),
						rs.getInt("credit"),
						rs.getBoolean("administrateur"),
						rs.getBoolean("desactive")
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		return utilisateur;
	}
	

	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		List<Utilisateur> listUtilisateur = new ArrayList<>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Utilisateur u = null;
				
				u = new Utilisateur(
						rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getString("rue"),
						rs.getString("code_postal"),
						rs.getString("ville"),
						rs.getString("mot_de_passe"),
						rs.getInt("credit"),
						rs.getBoolean("administrateur"),
						rs.getBoolean("desactive")
				);
				listUtilisateur.add(u);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		
		return listUtilisateur;
	}

	@Override
	public void update(Utilisateur u) throws BusinessException {
		if(u==null)
		{
			BusinessException businessException = new BusinessException("L'utilisateur est null");
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE);
			pstmt.setString(1, u.getPseudo());
			pstmt.setString(2, u.getNom());
			pstmt.setString(3, u.getPrenom());
			pstmt.setString(4, u.getEmail());
			pstmt.setString(5, u.getTelephone());
			pstmt.setString(6, u.getRue());
			pstmt.setString(7, u.getCodePostal());
			pstmt.setString(8, u.getVille());
			pstmt.setString(9, Utils.sha256(u.getMotDePasse()));
			pstmt.setInt(10, u.getCredit());
			pstmt.setBoolean(11, u.getAdministrateur());
			pstmt.setInt(12, u.getNoUtilisateur());
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
	public void updateCredit(Utilisateur u) throws BusinessException {
		if(u==null)
		{
			BusinessException businessException = new BusinessException("L'utilisateur est null");
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_CREDIT);
			pstmt.setInt(1, u.getCredit());
			pstmt.setInt(2, u.getNoUtilisateur());
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
	public void desactiveUtilisateur(Integer noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DESACTIVE_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
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
	public Utilisateur insert(Utilisateur u) throws BusinessException {
		if(u==null)
		{
			BusinessException businessException = new BusinessException("L'utilisateur est null");
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, u.getPseudo());
			pstmt.setString(2, u.getNom());
			pstmt.setString(3, u.getPrenom());
			pstmt.setString(4, u.getEmail());
			pstmt.setString(5, u.getTelephone());
			pstmt.setString(6, u.getRue());
			pstmt.setString(7, u.getCodePostal());
			pstmt.setString(8, u.getVille());
			pstmt.setString(9, Utils.sha256(u.getMotDePasse()));
			pstmt.setInt(10, u.getCredit());
			pstmt.setBoolean(11, u.getAdministrateur());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				u.setNoUtilisateur(rs.getInt(1));
			}
		}
		catch( Exception e)
		{
			//e.printStackTrace();
			BusinessException businessException = new BusinessException(e.getMessage());
			throw businessException;
		}
		return u;
	}
	
	
	public boolean checkPseudoEmail(String pseudo, String email) throws BusinessException{
		boolean res = false;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(CHECK);
			pstmt.setString(1, pseudo);
			pstmt.setString(2, email);
			ResultSet rs = pstmt.executeQuery();
			 if(rs.next()) {
				 res=true;
			 	}
		}  catch (SQLException e) {
			 throw new BusinessException(ErrorCodes.SQL_ERROR.getMessage());
		 }
		return res;
	}
	
	public boolean checkPseudo(String pseudo) throws BusinessException{
		boolean res = false;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(CHECKPSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			 if(rs.next()) {
				 res=true;
			 }
		}  catch (SQLException e) {
			 throw new BusinessException(ErrorCodes.SQL_ERROR.getMessage());
		}
		return res;
	}
	
	public boolean checkEmail(String email) throws BusinessException{
		boolean res = false;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(CHECKEMAIL);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			 if(rs.next()) {
				 res=true;
			 }
		}  catch (SQLException e) {
			 throw new BusinessException(ErrorCodes.SQL_ERROR.getMessage());
		}
		return res;
	}
	

	@Override
	public void delete(int noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETEBYID);
			pstmt.setInt(1, noUtilisateur);
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
	public Utilisateur checkPwd(String pseudoOrEmail, String pwd) throws BusinessException {
		Utilisateur u = null;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECTBYPWD);
			pstmt.setString(1, pseudoOrEmail);
			pstmt.setString(2, pseudoOrEmail);
			pstmt.setString(3, Utils.sha256(pwd));
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				u = new Utilisateur(
						rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getString("rue"),
						rs.getString("code_postal"),
						rs.getString("ville"),
						rs.getString("mot_de_passe"),
						rs.getInt("credit"),
						rs.getBoolean("administrateur"),
						rs.getBoolean("desactive"));
			} else {
				//TODO remonter msg erreur
				throw new BusinessException(ErrorCodes.IDORPASSWORD.getMessage());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new BusinessException(ErrorCodes.IDORPASSWORD.getMessage());
		}
		return u;
	}
}
