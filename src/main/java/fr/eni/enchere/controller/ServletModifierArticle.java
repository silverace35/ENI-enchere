package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/ModifierVente/*")
public class ServletModifierArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager uMgr = new UtilisateurManager();
		Utilisateur u = null;
		ArticleManager aMgr = new ArticleManager();
		ArticleVendu aV = null;
		RetraitManager rMgr = new RetraitManager();
		Retrait r = null;
		
		
		boolean erreur = false;
		
		String id = request.getPathInfo();
		if (id != null) {
			id = id.replace("/", "").trim();
			session.setAttribute("noArticle",id);
		}
		try {
			System.out.println(id);
			Integer.valueOf(id);
		} catch (Exception e) {
			erreur = true;
			e.printStackTrace();
		}
				
		try {
			aV = aMgr.getByNoArticle(Integer.valueOf(id));
			request.setAttribute("nomArticle", aV.getNomArticle());
			request.setAttribute("categorie", aV.getNoCategorie());
			request.setAttribute("description", aV.getDescription());
			request.setAttribute("prixInitial", aV.getPrixInitial());
			request.setAttribute("dateDebutEncheres", aV.getDateDebutEncheres());
			request.setAttribute("dateFinEncheres", aV.getDateFinEncheres());
			System.out.println(aV.getDateDebutEncheres());
			r = rMgr.getRetraitByNoRetrait(Integer.valueOf(id));
			if(r!=null) {
				request.setAttribute("rue", r.getRue());
				request.setAttribute("codePostal", r.getCodePostal());
				request.setAttribute("ville", r.getVille());
			} else {
				u = uMgr.getUtilisateurByNoUtilisateur((Integer)session.getAttribute("noUtilisateur"));
				request.setAttribute("rue", u.getRue());
				request.setAttribute("codePostal", u.getCodePostal());
				request.setAttribute("ville", u.getVille());
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierVente.jsp");
		
		rd.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArticleManager aMgr = new ArticleManager();
		UtilisateurManager uMgr = new UtilisateurManager();
		List<ErrorCodes> lstParam = new ArrayList<>();
		RetraitManager rMgr = new RetraitManager();
		Retrait r = null;
		
		String nomArticle=request.getParameter("nomArticle");
		String description=request.getParameter("description");
		System.out.println(request.getParameter("dateDebutEncheres"));
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), DateTimeFormatter.ISO_DATE_TIME) ;
		LocalDateTime dateFinEncheres=LocalDateTime.parse(request.getParameter("dateFinEncheres"), DateTimeFormatter.ISO_DATE_TIME);
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		String rue=request.getParameter("rue");
		String codePostal=request.getParameter("codePostal");
		String ville=request.getParameter("ville");
		
		if(validerChamps(lstParam,nomArticle,description, dateDebutEncheres, dateFinEncheres, prixInitial, rue, codePostal, ville)) {
			try {
				Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
				Integer noArticle=/*Integer.valueOf(String.valueOf(session.getAttribute("noArticle")))*/23;
				
				ArticleVendu aV = new ArticleVendu(noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
				aMgr.update(aV);
				
				r = rMgr.getRetraitByNoRetrait(noArticle);
				if(r!=null) {
					boolean nouvelleAdresse = (rue.equals(r.getRue()))&&(codePostal.toString().equals(r.getCodePostal()))&&(ville.equals(r.getVille()));
					if (!nouvelleAdresse) {
						r.setRue(rue);
						r.setCodePostal(codePostal);
						r.setVille(ville);
						rMgr.update(r);
					}
				
				} else {
					Utilisateur u = uMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
					boolean nouvelleAdresse = (rue.equals(u.getRue()))&&(codePostal.toString().equals(u.getCodePostal()))&&(ville.equals(u.getVille()));
					if (!nouvelleAdresse) {
						RetraitManager retraitMgr = new RetraitManager();
						retraitMgr.insert(noArticle, rue, codePostal, ville);
					}
				}
				
				response.sendRedirect("/ENI-enchere");
				
				} catch (Exception e) {
					// TODO Gestion des erreurs de saisie
					e.printStackTrace();
					request.setAttribute("lstParam", lstParam);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierVente.jsp");
					rd.forward(request, response);
				}
		} else {
			request.setAttribute("lstParam", lstParam);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierVente.jsp");
			rd.forward(request, response);
		}
	}
	
	public boolean validerChamps(List<ErrorCodes> lstParam, String nomArticle, String description, LocalDateTime dateDebut, LocalDateTime dateFin,
			Integer prixInitial, String rue, String codePostal, String ville) {
		boolean result = true;
		valider(nomArticle, ErrorCodes.RUE, lstParam);
		valider(description, ErrorCodes.DESCRIPTION, lstParam);
		valider(rue, ErrorCodes.RUE, lstParam);
		valider(codePostal, ErrorCodes.CODEPOSTAL, lstParam);
		valider(ville, ErrorCodes.VILLE, lstParam);

		if (prixInitial<0) {
			lstParam.add(ErrorCodes.PRIX_INITIAL);
		}
		
		if (dateDebut.isAfter(dateFin)) {
			lstParam.add(ErrorCodes.DATES_IMP);
		}
		return lstParam.size()==0;
	}
	public void valider(String text, ErrorCodes errorCode, List<ErrorCodes> lstParam) {
		if (!text.matches(errorCode.getPattern())) {
			lstParam.add(errorCode);
		}
	}

}
