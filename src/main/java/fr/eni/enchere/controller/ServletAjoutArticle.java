package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/AjoutArticle")
public class ServletAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjoutArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		Utilisateur utilisateur = null;
		CategorieManager catMgr = new CategorieManager();

		try {
			int noUtilisateur = (int)session.getAttribute("noUtilisateur");
			System.out.println(noUtilisateur);
			utilisateur = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
			request.setAttribute("utilisateur", utilisateur);
			List<Categorie> listCategories = catMgr.selectAllCategories();
			request.setAttribute("listCategories", listCategories);
			System.out.println(listCategories);
			} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
		rd.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArticleManager mgr = new ArticleManager();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		
		String nomArticle=request.getParameter("nomArticle");
		String description=request.getParameter("description");
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), DateTimeFormatter.ISO_DATE_TIME) ;
		LocalDateTime dateFinEncheres=LocalDateTime.parse(request.getParameter("dateFinEncheres"), DateTimeFormatter.ISO_DATE_TIME);
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		String rue=request.getParameter("rue");
		String codePostal=request.getParameter("codePostal");
		String ville=request.getParameter("ville");
		
		try {
			int noUtilisateur = (int)session.getAttribute("noUtilisateur");
			ArticleVendu aV = mgr.insert(nomArticle, description,dateDebutEncheres, dateFinEncheres, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
			Utilisateur u = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
			boolean nouvelleAdresse = (rue.equals(u.getRue()))&&(codePostal.toString().equals(u.getCodePostal()))&&(ville.equals(u.getVille()));
			if (!nouvelleAdresse) {
				RetraitManager retraitMgr = new RetraitManager();
				retraitMgr.insert(aV.getNoArticle(), rue, codePostal, ville);
			}
			response.sendRedirect("/ENI-enchere");
			
		} catch (Exception e) {
			// TODO Gestion des erreurs de saisie
			e.printStackTrace();
		}
		

	}

}
