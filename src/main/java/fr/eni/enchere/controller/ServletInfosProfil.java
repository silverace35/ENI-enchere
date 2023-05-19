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
 * Servlet implementation class ServletInfosProfil
 */
@WebServlet("/InfosProfil/*")
public class ServletInfosProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletInfosProfil() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean erreur = false;
		
		HttpSession session = request.getSession();
		UtilisateurManager mgr = new UtilisateurManager();
		Utilisateur utilisateur = null;

		String id = request.getPathInfo();
		if (id != null) {
			id = id.replace("/", "").trim();
		}
		try {
			Integer.valueOf(id);
		} catch (Exception e) {
			erreur = true;
		}

		if (erreur) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error404.jsp");
			rd.forward(request, response);
		} else {
			utilisateur = mgr.getUtilisateurByNoUtilisateur(Integer.valueOf(id));
			if (utilisateur != null) {
				request.setAttribute("utilisateur", utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profilUtilisateur.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect("/ENI-enchere");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
