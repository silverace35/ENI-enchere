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

import org.apache.tomcat.jni.Local;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/AjoutArticle")
public class ServletAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjoutArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		Utilisateur utilisateur = null;
		CategorieManager catMgr = new CategorieManager();
				
		try {
			Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
			utilisateur = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
			request.setAttribute("rue", utilisateur.getRue());
			request.setAttribute("codePostal", utilisateur.getCodePostal());
			request.setAttribute("ville", utilisateur.getVille());
			//List<Categorie> listCategories = catMgr.selectAllCategories();
			
			//request.setAttribute("listCategories", listCategories);
			} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
		rd.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArticleManager mgr = new ArticleManager();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		List<ErrorCodes> lstParam = new ArrayList<>();
		
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
				int noUtilisateur = (int)session.getAttribute("noUtilisateur");
				ArticleVendu aV = mgr.insert(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
				Utilisateur u = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				boolean nouvelleAdresse = (rue.equals(u.getRue()))&&(codePostal.toString().equals(u.getCodePostal()))&&(ville.equals(u.getVille()));
				if (!nouvelleAdresse) {
					RetraitManager retraitMgr = new RetraitManager();
					retraitMgr.insert(aV.getNoArticle(), rue, codePostal, ville);
					response.sendRedirect("/ENI-enchere");
				}
				} catch (Exception e) {
					// TODO Gestion des erreurs de saisie
					e.printStackTrace();
				}
		} else {
		
//			CategorieManager catMgr = new CategorieManager();
//			List<Categorie> listCategories;
//			try {
//				listCategories = catMgr.selectAllCategories();
//				request.setAttribute("listCategories", listCategories);
//			} catch (BusinessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			request.setAttribute("lstParam", lstParam);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
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

