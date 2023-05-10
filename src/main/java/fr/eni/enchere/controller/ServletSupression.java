package fr.eni.enchere.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.dal.UtilisateurDAOJdbcImpl;

/**
 * Servlet implementation class ServletSupression
 */
@WebServlet("/profil/suppression")
public class ServletSupression extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSupression() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager mgr = new UtilisateurManager();
		Cookie[] cookies = request.getCookies();
		
		if (session.getAttribute("noUtilisateur") != null) {
			mgr.deleteUtilisateur(Integer.valueOf((String)session.getAttribute("noUtilisateur")));
			for(Cookie cookie:cookies)
			{
				if (cookie.getName().equals("cookieLogin")) {
					cookie.setMaxAge(0);
				}
			}
		}
		response.sendRedirect("/ENI-enchere");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
