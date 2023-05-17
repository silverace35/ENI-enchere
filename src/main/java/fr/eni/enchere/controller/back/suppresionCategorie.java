package fr.eni.enchere.controller.back;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class suppresionCategorie
 */
@WebServlet("/administration/suppressionCategorie/*")
public class suppresionCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public suppresionCategorie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			Integer idAdmin = (Integer)request.getSession().getAttribute("noUtilisateur");
			Utilisateur u = null;
			UtilisateurManager uMgr = new UtilisateurManager();
			CategorieManager cMgr = new CategorieManager();
			
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
						cMgr.deleteCategorie(Integer.valueOf(id));
						response.sendRedirect(request.getContextPath()+"/administration/categorie");
					} else {
						response.sendRedirect(request.getContextPath());
					}
				}
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
