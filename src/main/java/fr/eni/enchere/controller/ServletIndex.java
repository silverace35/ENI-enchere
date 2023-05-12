package fr.eni.enchere.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bo.ArticleVendu;

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
		List<ArticleVendu> listArticle = articleManager.getArticlesEnCours();
		request.setAttribute("listArticle", listArticle);
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO : 
		request.setCharacterEncoding("UTF-8");
		
		String barreRecherche = request.getParameter("barreRecherche");
		String categorie = request.getParameter("categorie");
		Boolean enchereOuverte = Boolean.parseBoolean(request.getParameter("enchere-ouverte"));
		Boolean mesEncheres = Boolean.parseBoolean(request.getParameter("mes-encheres"));
		Boolean enchereRemportees = Boolean.parseBoolean(request.getParameter("enchere-remportees"));
		Boolean venteCours = Boolean.parseBoolean(request.getParameter("vente-cours"));
		Boolean venteDebutees = Boolean.parseBoolean(request.getParameter("vente-debutees"));
		Boolean venteTerminees = Boolean.parseBoolean(request.getParameter("vente-terminees"));
		
		try {
			if (enchereOuverte || mesEncheres || enchereRemportees || venteCours || venteDebutees || venteTerminees) {
				request.setAttribute("barreRecherche", barreRecherche);
				request.setAttribute("categorie", categorie);
				request.setAttribute("enchere-ouverte", enchereOuverte);
				request.setAttribute("mes-encheres", mesEncheres);
				request.setAttribute("enchere-remportees", enchereRemportees);
				request.setAttribute("vente-cours", venteCours);
				request.setAttribute("vente-debutees", venteDebutees);
				request.setAttribute("vente-terminees", venteTerminees);
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
