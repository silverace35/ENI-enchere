package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;

/**
 * Servlet implementation class ServletIndex
 */
@WebServlet("")
public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletIndex() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String barreRecherche = request.getParameter("barreRecherche");
		String categorie = request.getParameter("categorie");
		
		try {
			if (barreRecherche != null) {
				
				request.setAttribute("barreRecherche", barreRecherche);
				request.setAttribute("categorie", categorie);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
				rd.forward(request, response);	            
	        } else {
	        	
	        	throw new Exception("Veuillez entrer un mot clé dans la barre de recherche");
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
