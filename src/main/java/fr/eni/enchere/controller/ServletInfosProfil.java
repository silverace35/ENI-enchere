package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletInfosProfil
 */
@WebServlet("/InfosProfil")
public class ServletInfosProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInfosProfil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Utilisateur utilisateur = null;
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profilUtilisateur.jsp");
		
		//Je verifie que la session n'est pas null
		if (session != null) {
			//Je verifie que le numero utilisateur est bien renseigner en session
			if (!session.getAttribute("noUtilisateur").equals("")) {
				//utilisateur = bll.getUtilisateurByNoUtilisateur(session.getAttribute("noUtilisateur"));
			}
		}
		
		if (utilisateur != null) {
			request.setAttribute("utilisateur", utilisateur);
			rd.forward(request, response);
		} else {
			response.sendRedirect("/ENI-enchere");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
