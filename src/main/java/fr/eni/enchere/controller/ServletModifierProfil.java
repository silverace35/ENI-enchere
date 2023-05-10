package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletModifierProfil
 */
@WebServlet("/ServletModifierProfil")
public class ServletModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierProfil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") == null) {
			response.sendRedirect("/ENI-enchere");
		} else {
			HttpSession session = request.getSession();
			UtilisateurManager mgr = new UtilisateurManager();
			Utilisateur utilisateur = null;
			try {
				//int noUtilisateur = Integer.valueOf((String)session.getAttribute("noUtilisateur"));
				int noUtilisateur = 5;
				utilisateur = mgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				System.out.println("utilisateur : "+utilisateur.toString());
				System.out.println(utilisateur.getPseudo());
				request.setAttribute("pseudo", utilisateur.getPseudo());
				request.setAttribute("nom", utilisateur.getNom());
				request.setAttribute("prenom", utilisateur.getPrenom());
				request.setAttribute("email", utilisateur.getEmail());
				request.setAttribute("tel", utilisateur.getTelephone());
				request.setAttribute("rue", utilisateur.getRue());
				request.setAttribute("codePostal", utilisateur.getCodePostal());
				request.setAttribute("ville", utilisateur.getVille());
				
				request.setAttribute("utilisateur", utilisateur);
			
			} catch (Exception e) {
				
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager mgr = new UtilisateurManager();
		Utilisateur utilisateur = null;
		try {
			//int noUtilisateur = Integer.valueOf((String)session.getAttribute("noUtilisateur"));
			int noUtilisateur = Integer.valueOf(request.getParameter("noUtilisateur")) ;
			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			String pwdUser = request.getParameter("motDePasse");
			String confpwdUser = request.getParameter("confMotDePasse");
			int credit = Integer.valueOf(request.getParameter("credit")) ;
			boolean  administrateur = Boolean.getBoolean ((String)request.getParameter("administrateur"));
			System.out.println(administrateur);
	        //TODO : gerer les erreurs d'entrées utilisateur
	        if (pseudo != null && nom != null && prenom != null && email != null && tel != null && rue != null && codePostal != null && ville != null && pwdUser != null && confpwdUser != null) {
	            //TODO utiliser la BLL pour vérifier la validité des identifiants
	        	utilisateur = new Utilisateur(noUtilisateur, pseudo, nom, prenom, email, tel, rue, codePostal, ville, confpwdUser, credit, administrateur);
	        	System.out.println(utilisateur.toString());
	        	mgr.updateUtilisateur(utilisateur);
	        	
	        	response.sendRedirect("/ENI-enchere");
	        } else {
	        	throw new Exception("Certains champs sont invalides");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
