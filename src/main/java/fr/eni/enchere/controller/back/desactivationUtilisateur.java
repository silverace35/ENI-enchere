package fr.eni.enchere.controller.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class suppressionUtilisateur
 */
@WebServlet("/desactivationUtilisateur/*")
public class desactivationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public desactivationUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			Integer idAdmin = (Integer)request.getSession().getAttribute("noUtilisateur");
			Utilisateur u = null;
			UtilisateurManager uMgr = new UtilisateurManager();
			ArticleManager aMgr = new ArticleManager();
			EnchereManager eMgr = new EnchereManager();
			List<ArticleVendu> listArticle = new ArrayList<>();
			
			u = uMgr.getUtilisateurByNoUtilisateur(idAdmin);
			
			if (u != null) {
				if (u.getAdministrateur()) {
					boolean erreur = false;
					
					String id = request.getPathInfo();
					if (id != null) {
						id = id.replace("/", "").trim();
					}
					try {
						Integer.valueOf(id);
					} catch (Exception e) {
						erreur = true;
						e.printStackTrace();
					}
					
					if (!erreur) {
						aMgr.deleteByUserId(Integer.valueOf(id));
						listArticle = aMgr.getArticlesEnCoursEncherie(Integer.valueOf(id));
						
						eMgr.deleteByUserId(Integer.valueOf(id));
						
						if (!listArticle.isEmpty()) {
							Integer maxEnchere = null;
							for (ArticleVendu articleVendu : listArticle) {
								maxEnchere = eMgr.getMaxEnchereForArticleId(articleVendu.getNoArticle());
								aMgr.updatePrixDeVente(maxEnchere, articleVendu.getNoArticle());
							}
						}
						
						uMgr.desactiveUtilisateur(Integer.valueOf(id));
						response.sendRedirect(request.getContextPath()+"/administration");
					} else {
						response.sendRedirect(request.getContextPath());
					}
				}
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
