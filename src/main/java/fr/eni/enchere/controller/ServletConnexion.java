package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// identifiant peut soit etre le pseudo soit le mail
			String idUser = request.getParameter("identifiant");
	        String pwdUser = request.getParameter("motDePasse");
	        System.out.println("id : " + idUser);
	        System.out.println("mot de passe : " + pwdUser);
	        //TODO : gerer les erreurs d'entrées utilisateur
	        //TODO : lors de l'afffichage de la page profil.jsp : si ne s'est pas deja connecté
	        // => redirection vers index.jsp
	        if (idUser != null && pwdUser != null) {
	            //TODO utiliser la BLL pour vérifier la validité des identifiants
	        	//SI VALID : httpSession.setAttribute("IdUser", idUser);
	            // TODO : créer cookie pour se souvenir de moi
	            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp");
	    		rd.forward(request, response);
	        } else {
	        	throw new Exception("Identifiant ou mot de passe invalide");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
	}

}
