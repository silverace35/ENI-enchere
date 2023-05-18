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

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class Administration
 */
@WebServlet("/administration")
public class Administration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Administration() {
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
			
			u = uMgr.getUtilisateurByNoUtilisateur(id);
			
			if (u != null) {
				if (u.getAdministrateur()) {
					List<Utilisateur> listUtilisateur = new ArrayList<Utilisateur>();
					
					try {
						listUtilisateur = uMgr.getAllUtilisateur();
						request.setAttribute("listUtilisateur", listUtilisateur);
						
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/administration/user.jsp");
						rd.forward(request, response);
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
					rd.forward(request, response);
				}
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
				rd.forward(request, response);
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
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
