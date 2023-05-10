package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

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
		UtilisateurManager mgr = new UtilisateurManager();
		Utilisateur u = null;
		try {
			// identifiant peut soit etre le pseudo soit le mail
			String idUser = request.getParameter("identifiant");
	        String pwdUser = request.getParameter("motDePasse");
	        String souvenir = request.getParameter("souvenir");
	        //TODO : gerer les erreurs d'entrées utilisateur
	        //TODO : lors de l'afffichage de la page profil.jsp : si ne s'est pas deja connecté
	        // => redirection vers index.jsp
	        if (idUser != null && pwdUser != null) {
	        	u = mgr.validerPwd(idUser, pwdUser);
	        	if (u!=null) {
	        		if("on".equals(souvenir)) {
	        			Cookie userCookie = new Cookie("cookieLogin", u.getNoUtilisateur().toString());
	        			userCookie.setMaxAge(60*60*24*365); //cookie d'une durée de 1 an
	        			response.addCookie(userCookie);
	        		}
	        		HttpSession session = request.getSession(); 
	        		request.getSession().setAttribute("noUtilisateur", u.getNoUtilisateur()); 
	        		//SI VALID : httpSession.setAttribute("IdUser", idUser);
		            // TODO : créer cookie pour se souvenir de moi
		            //TODO: ajouter dans profil.jsp getsession + getAttribute(noUtilisateur)
		            // TODO : ajouter dans profil.jsp userCookie
// 					Cookie[] cookies = request.getCookies();
//					if (cookies != null) {
//					 for (Cookie cookie : cookies) {
//					   if (userCookie.getName().equals("cookieName")) {
//					     
//					    }
//					  }
//					}
	        		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		    		rd.forward(request, response);
	        	} else {
	        		//TODO message d'erreur gestion erreur 
	        		response.sendRedirect("ServletConnexion");
	        	}
	        	
	        } else {
	        	// TODO : gestion erreur 
	        	throw new Exception("Identifiant ou mot de passe invalide");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
	}

}
