package fr.eni.enchere.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		if(request.getSession().getAttribute("noUtilisateur") != null) {			
			response.sendRedirect("/ENI-enchere");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
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
	        if (idUser != null && pwdUser != null) {
	        	u = mgr.validerPwd(idUser, pwdUser);
	        	if (u!=null) {
	        		if("on".equals(souvenir)) {
	        			Cookie userCookie = new Cookie("cookieLogin", u.getNoUtilisateur().toString());
	        			userCookie.setMaxAge(60*60*24*365); //cookie d'une durée de 1 an
	        			response.addCookie(userCookie);
	        		}
	        		request.getSession().setAttribute("noUtilisateur", u.getNoUtilisateur()); 
	        		if(u.getAdministrateur()) {
	        			request.getSession().setAttribute("admin", true);
	        		}
	        		if(u.getDesactive()) {
	        			request.getSession().setAttribute("desactive", true);
	        		}
	        		//SI VALID : httpSession.setAttribute("IdUser", idUser);
	        		response.sendRedirect("/ENI-enchere");
	        	} else {
	        		throw new Exception("Identifiant ou mot de passe invalide");
	        	}
	        	
	        } else {
	        	// TODO : gestion erreur 
	        	throw new Exception("Identifiant ou mot de passe invalide");
	        }
		} catch (Exception e) {
			request.setAttribute(ErrorCodes.IDORPASSWORD.name(), ErrorCodes.IDORPASSWORD.getMessage());
    		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}
		
		 
	}

}
