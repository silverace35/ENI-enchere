package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletDetailVente
 */
@WebServlet("/DetailVente/*")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetailVente() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean erreur = false;
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/detailVente.jsp");
		HttpSession session = request.getSession();
		ArticleManager mgr = new ArticleManager();
		ArticleVendu av = null;
		
		String id = request.getPathInfo();
		if (id != null) {
			id = id.replace("/", "").trim();
		}
		try {
			System.out.println(id);
			Integer.valueOf(id);
		} catch (Exception e) {
			erreur = true;
			e.printStackTrace();
		}

		if (erreur) {
			response.sendRedirect("/ENI-enchere");
		} else {
			av = mgr.getByNoArticle(Integer.valueOf(id));
			if (av != null) {
				request.setAttribute("articleVendu", av);
				rd.forward(request, response);
			} else {
				response.sendRedirect("/ENI-enchere");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArticleManager aMgr = new ArticleManager();
		EnchereManager eMgr = new EnchereManager();
		UtilisateurManager uMgr = new UtilisateurManager();
		Utilisateur u = null;
		ArticleVendu av = null;
		boolean erreur = false;
		Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
		if (request.getSession().getAttribute("noUtilisateur") == null) {
			System.out.println("raté");
			response.sendRedirect("/ENI-enchere");
		} else {
			
		try {
			Integer proposition = Integer.valueOf(request.getParameter("proposition"));
			request.setAttribute("proposition", proposition);
			
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
			u = uMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
			request.setAttribute("u", u);
			av = aMgr.getByNoArticle(Integer.valueOf(id));
			if (av == null || u == null) {
				System.out.println("a");
				erreur = true;
			} else if(proposition < av.getPrixVente()) {
				System.out.println("b");
				erreur = true;
			} else if (proposition > u.getCredit()){
				System.out.println(u.getCredit());
				erreur = true;
			} else {
				System.out.println("d");
				eMgr.insert(LocalDateTime.now(), proposition, Integer.valueOf(id).intValue(), Integer.valueOf(id).intValue());
				av.setPrixVente(proposition);
				u.setCredit(u.getCredit() - proposition);
				aMgr.update(av);
				uMgr.updateUtilisateur(u);
				System.out.println("coucou");
				response.sendRedirect("/ENI-enchere/DetailVente/"+id);
			} 
			
			if (erreur) {
				response.sendRedirect("/ENI-enchere");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}

}
