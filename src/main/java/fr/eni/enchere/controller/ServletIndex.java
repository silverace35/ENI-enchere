package fr.eni.enchere.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

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
		ArticleManager articleManager = new ArticleManager();
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		
	
		if (request.getSession() != null) {
			Integer noUtilisateur = (Integer)request.getSession().getAttribute("noUtilisateur");
			if (noUtilisateur != null) {
				listArticle.addAll(articleManager.getArticlesEnCoursPasEncherie(noUtilisateur));
			} else {
				listArticle.addAll(articleManager.getArticlesEnCours());
			}
		} else {
			listArticle.addAll(articleManager.getArticlesEnCours());
		}
		
		request.setAttribute("listArticle", listArticle);
		CategorieManager catMgr = new CategorieManager();
		try {
			List<Categorie> listCategories = catMgr.selectAllCategories();
			request.setAttribute("listCategories", listCategories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		ArticleManager articleManager = new ArticleManager();
		Integer id = (Integer)request.getSession().getAttribute("noUtilisateur");
		
		request.setCharacterEncoding("UTF-8");
		String radio = request.getParameter("radio");
		String barreRecherche = request.getParameter("barreRecherche");
		Integer categorie = Integer.parseInt(request.getParameter("categorie"));
		String enchereOuverte = request.getParameter("enchere-ouverte");
		String mesEncheres = request.getParameter("mes-encheres");
		String enchereRemportees = request.getParameter("enchere-remportees");
		String venteCours = request.getParameter("vente-cours");
		String venteDebutees = request.getParameter("vente-debutees");
		String venteTerminees = request.getParameter("vente-terminees");
		
		if ("achat".equals(radio)) {
			if ("on".equals(enchereOuverte)) {
				listArticle.addAll(articleManager.getArticlesEnCoursPasEncherie(id));
			}
			if ("on".equals(mesEncheres)) {
				listArticle.addAll(articleManager.getArticlesEnCoursEncherie(id));
			}
			if ("on".equals(enchereRemportees)) {
				listArticle.addAll(articleManager.getArticlesTerminerGagner(id));
			}
		} else {
			if ("on".equals(venteCours)) {
				listArticle.addAll(articleManager.getVentesEnCours(id));
			}
			if ("on".equals(venteDebutees)) {
				listArticle.addAll(articleManager.getVentesNonDebute(id));
			}
			if ("on".equals(venteTerminees)) {
				listArticle.addAll(articleManager.getVentesTerminer(id));
			}
		}
		
		request.setAttribute("listArticle", listArticle);
		CategorieManager catMgr = new CategorieManager();
		try {
			List<Categorie> listCategories = catMgr.selectAllCategories();
			request.setAttribute("listCategories", listCategories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rd.forward(request, response);
	}

}
