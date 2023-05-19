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
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletOkVendeur
 */
@WebServlet("/okAcheteur/*")
public class ServletOkAcheteur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletOkAcheteur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager aMgr = new ArticleManager();
		ArticleVendu av = null;
		UtilisateurManager uMgr = new UtilisateurManager();
		Utilisateur u = null;
		EnchereManager eMgr = new EnchereManager();
		List<Enchere> listEnchere = new ArrayList<>();
		
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			boolean erreur = false;
			
			String id = request.getPathInfo();
			System.out.println(request.getPathInfo() + "| path info ----------------------------------");
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
				try {
					av = aMgr.getByNoArticle(Integer.valueOf(id));
					u = uMgr.getUtilisateurByNoUtilisateur((Integer)request.getSession().getAttribute("noUtilisateur"));
					listEnchere = eMgr.selectAllEncheresByNoArticle(Integer.valueOf(id));
					if (av != null && u!=null && !av.isRetraitOkAcheteur() && !listEnchere.isEmpty()) {
						if (u.getNoUtilisateur() == (int)listEnchere.get(0).getNoUtilisateur()) {
							av.setRetraitOkAcheteur(true);
							aMgr.updateOkAcheteur(true, av.getNoArticle());
							response.sendRedirect("/ENI-enchere/DetailVente/"+av.getNoArticle());
						} else {
							RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Erreur403.jsp");
							rd.forward(request, response);
						}
					} else {
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Erreur403.jsp");
						rd.forward(request, response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Erreur403.jsp");
				rd.forward(request, response);
			}
			
			
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Erreur403.jsp");
			rd.forward(request, response);
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
