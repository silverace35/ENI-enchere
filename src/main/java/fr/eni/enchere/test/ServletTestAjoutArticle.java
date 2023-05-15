package fr.eni.enchere.test;

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
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/AjoutArticle")
public class ServletTestAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTestAjoutArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
		CategorieManager catMgr = new CategorieManager();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		Utilisateur utilisateur = null;
		try {
			int noUtilisateur = (int)session.getAttribute("noUtilisateur");
			utilisateur = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
			request.setAttribute("utilisateur", utilisateur);
			List<Categorie> listCategories = catMgr.selectAllCategories();
			request.setAttribute("listCategories", listCategories);
			System.out.println(listCategories);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		String nomArticle=request.getParameter("nomArticle");
		String description=request.getParameter("description");
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), DateTimeFormatter.ISO_DATE_TIME) ;
		LocalDateTime dateFinEncheres=LocalDateTime.parse(request.getParameter("dateFinEncheres"), DateTimeFormatter.ISO_DATE_TIME);
		//request.getParameter("dateDebutEncheres")
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		int noUtilisateur = (int)session.getAttribute("noUtilisateur");
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		System.out.println("noCategorie :" + noCategorie);
//		CategorieManager catMgr = new CategorieManager();
//		try {
//			List<Categorie> listCategories = catMgr.selectAllCategories();
//			request.setAttribute("listCategories", listCategories);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			mgr.insert(nomArticle, description,dateDebutEncheres, dateFinEncheres, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
			
//			RequestDispatcher rd = request.getRequestDispatcher("/ENI-enchere");
//			rd.forward(request, response);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
