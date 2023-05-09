package fr.eni.enchere.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletCreerCompte
 */
@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
		rd.forward(request, response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO FAIRE TOUS LES TESTS FORMAT MAIL ETC.
		// TODO Remplir les champs si l'insertion n'est pas ok

		request.setCharacterEncoding("UTF-8");
		UtilisateurManager mgr = new UtilisateurManager();
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("code_postal");
		String ville = request.getParameter("ville");
		String mdp = request.getParameter("mdp");
		String mdpConfirm = request.getParameter("mdp_confirm");
		
		if (mdp.equals(mdpConfirm)) {
		
			try {
				Utilisateur u = mgr.insert(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, mdp, 100);
				System.out.println(u);
				request.setAttribute("utilisateur", u);
				RequestDispatcher rd = request.getRequestDispatcher("ServletProfil");
				// Affichage effectif par délégation
				rd.forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} else {
			
		}
			
	}

}
