package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

public class UtilisateurDAOJdbcImpl implements DAOUtilisateur{

	private static UtilisateurDAOJdbcImpl instance;
	
	private static final String SELECTBYID="SELECT * FROM utilisateurs WHERE no_utilisateur=?;";
	private static final String SELECTALL="SELECT * FROM utilisateurs";
	private static final String UPDATE="UPDATE utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?,"
			+ " rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?;";
	private static final String INSERT="INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue,"
			+ " code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String DELETEBYID="DELETE FROM utilisateurs WHERE no_utilisateur=?;";
	
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
						rs.getBoolean("administrateur")
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
						rs.getBoolean("administrateur")
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
			pstmt.setString(9, u.getMotDePasse());
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
			pstmt.setString(9, u.getMotDePasse());
			pstmt.setInt(10, u.getCredit());
			pstmt.setBoolean(11, u.getAdministrateur());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				u.setNoUtilisateur(rs.getInt(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			throw businessException;
		}
		return u;
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
}
