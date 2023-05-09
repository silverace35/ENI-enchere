package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		RequestDispatcher rd = request.getRequestDispatcher("/modifierProfil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
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
	        //TODO : gerer les erreurs d'entrées utilisateur
	        if (pseudo != null && nom != null && prenom != null && email != null && email != null && rue != null && codePostal != null && ville != null && pwdUser != null && confpwdUser != null) {
	            //TODO utiliser la BLL pour vérifier la validité des identifiants
	        	//SI VALID : httpSession.setAttribute("IdUser", idUser);
	            // TODO : créer cookie pour se souvenir de moi
	            RequestDispatcher rd = request.getRequestDispatcher("/profil.jsp");
	    		rd.forward(request, response);
	        } else {
	        	throw new Exception("Certains champs sont invalides");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
