package fr.eni.enchere.controller.back;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
@WebServlet("/administration/ajoutCategorie")
public class ajoutCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ajoutCategorie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			System.out.println("adminCategorieAjout");
			Integer idAdmin = (Integer) request.getSession().getAttribute("noUtilisateur");
			Utilisateur u = null;
			UtilisateurManager uMgr = new UtilisateurManager();
			CategorieManager cMgr = new CategorieManager();
			
			String libelle = request.getParameter("libelle");

			u = uMgr.getUtilisateurByNoUtilisateur(idAdmin);

			if (u != null) {
				if (u.getAdministrateur()) {
						try {
							if (!"".equals(libelle) && libelle!=null) {
								cMgr.insert(libelle);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						response.sendRedirect(request.getContextPath() + "/administration/categorie");
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
					rd.forward(request, response);
				}
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
				rd.forward(request, response);
			}
		}
	}

}
