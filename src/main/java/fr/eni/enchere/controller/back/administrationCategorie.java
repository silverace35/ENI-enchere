package fr.eni.enchere.controller.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class administrationCategorie
 */
@WebServlet("/administration/categorie")
public class administrationCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public administrationCategorie() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			Integer id = (Integer)request.getSession().getAttribute("noUtilisateur");
			Utilisateur u = null;
			UtilisateurManager uMgr = new UtilisateurManager();
			CategorieManager cMgr = new CategorieManager();
			
			u = uMgr.getUtilisateurByNoUtilisateur(id);
			
			if (u != null) {
				if (u.getAdministrateur()) {
					List<Categorie> listCategorie = new ArrayList<Categorie>();
					
					try {
						listCategorie = cMgr.selectAllCategories();
						request.setAttribute("listCategorie", listCategorie);
						
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/administration/categorie.jsp");
						rd.forward(request, response);
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
				} else {
					response.sendRedirect(request.getContextPath());
				}
			} else {
				response.sendRedirect(request.getContextPath());
			}
		} else {
			response.sendRedirect(request.getContextPath());
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
