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
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
		rd.forward(request, response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		UtilisateurManager mgr = new UtilisateurManager();
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String pwdUser = request.getParameter("motDePasse");
		String confpwdUser = request.getParameter("confMotDePasse");
		
		
		if (validerChamps() && pwdUser.equals(confpwdUser)) {
		
			try {
				Utilisateur u = mgr.insert(pseudo, nom, prenom, email, tel, rue, codePostal, ville, confpwdUser, 100);
				System.out.println(u);
				request.setAttribute("utilisateur", u);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
				rd.forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} else {
			//TODO highlight erreurs champs & remettre les champs bons
			
		}
			
	}
	
	//TODO valider tous les champs NOT NULL & REGEX
	public boolean validerChamps() {
		return true;
	}
	

}
