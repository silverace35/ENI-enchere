package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bo.ArticleVendu;

/**
 * Servlet implementation class ServletAnnulationVente
 */
@WebServlet("/AnnulerVente")
public class ServletAnnulationVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAnnulationVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/ENI-enchere");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
		System.out.println("noUtilisateur : "+noUtilisateur);
		Integer noArticle=(Integer)session.getAttribute("noArticle");
		ArticleManager aMgr = new ArticleManager();
		ArticleVendu a = aMgr.getByNoArticle(noArticle);
		if(a.getNoUtilisateur().equals(noUtilisateur) && LocalDateTime.now().isBefore(a.getDateDebutEncheres()) ) {
			aMgr.delete(noArticle);
			response.sendRedirect("/ENI-enchere");
		} else {
			response.sendRedirect("/ModifierVente/"+noArticle);
		}
	}

}
