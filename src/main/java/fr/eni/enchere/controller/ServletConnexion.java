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
import fr.eni.enchere.utils.Logger;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String LOCATION = "ServletConnexion";

       
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
		
		// Si l'utilisateur est déjà connecté, renvoi vers l'index. Sinon déléguation vers le formulaire de connexion
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
		HttpSession session = request.getSession();
		UtilisateurManager mgr = new UtilisateurManager();
		Utilisateur u = null;
		String appPath = request.getServletContext().getRealPath("");
		try {
			// identifiant peut soit etre le pseudo soit le mail
			String idUser = request.getParameter("identifiant");
	        String pwdUser = request.getParameter("motDePasse");
	        String souvenir = request.getParameter("souvenir");
	        //TODO : gerer les erreurs d'entrées utilisateur
	        if (idUser != null && pwdUser != null) {
	        	u = mgr.validerPwd(idUser, pwdUser);
	        	if (u!=null) {
	        		
	        		// Set cookie utilisateur
	        		if("on".equals(souvenir)) {
	        			Cookie userCookie = new Cookie("cookieLogin", u.getNoUtilisateur().toString());
	        			userCookie.setMaxAge(60*60*24*365); //cookie d'une durée de 1 an
	        			response.addCookie(userCookie);
	        		}
	        		session.setAttribute("noUtilisateur", u.getNoUtilisateur()); 
	        		if(u.getAdministrateur()) {
	        			session.setAttribute("admin", true);
	        		}
	        		if(u.getDesactive()) {
	        			session.setAttribute("desactive", true);
	        		}
	        		//SI VALID : httpSession.setAttribute("IdUser", idUser);
	        		Logger.log(appPath, LOCATION, u.getNoUtilisateur(), "connected" , null, "Admin : "
	        				+(Boolean)session.getAttribute("admin"), "Desactivé : "+(Boolean)session.getAttribute("desactive"));
	        		response.sendRedirect("/ENI-enchere");
	        	} else {
	        		Logger.log(appPath, LOCATION, null, "Identifiant ou mot de passe invalide" , null);
	        		throw new Exception("Identifiant ou mot de passe invalide");
	        	}
	        	
	        } else {
	        	// TODO : gestion erreur 
	        	Logger.log(appPath, LOCATION, null, "Identifiant ou mot de passe non renseigné" , null);
	        	throw new Exception("Identifiant ou mot de passe non renseigné");
	        }
		} catch (Exception e) {
			request.setAttribute(ErrorCodes.IDORPASSWORD.name(), ErrorCodes.IDORPASSWORD.getMessage());
    		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
    		
    		// Enregistrement du passage dans la servlet
    		Logger.log(appPath, LOCATION, (Integer)session.getAttribute("noUtilisateur"), "Failed to connect" , null);
    		
			rd.forward(request, response);
			e.printStackTrace();
		}
		
		 
	}

}
