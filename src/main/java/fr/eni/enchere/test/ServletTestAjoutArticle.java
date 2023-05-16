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
@WebServlet("/TestAjoutArticle")
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
		LocalDateTime d = LocalDateTime.now();
		try {
			int noUtilisateur = Integer.valueOf((String)session.getAttribute("noUtilisateur"));
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
		System.out.println((List<Categorie>)request.getAttribute("listCategories"));
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
		String nomArticle=request.getParameter("nomArticle");
		String description=request.getParameter("description");
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), dt) ;
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		int noUtilisateur = Integer.valueOf((String)session.getAttribute("noUtilisateur"));
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		
		try {
			mgr.insert(nomArticle, description,null, null, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
