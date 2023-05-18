package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.ImageManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Image;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.test.Utils;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/ModifierVente/*")
@MultipartConfig
public class ServletModifierArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SAVE_DIRECTORY = "uploads";   
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
		//request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UtilisateurManager uMgr = new UtilisateurManager();
		Utilisateur u = null;
		ArticleManager aMgr = new ArticleManager();
		ArticleVendu aV = null;
		RetraitManager rMgr = new RetraitManager();
		Retrait r = null;
		ImageManager iMgr = new ImageManager();
		Image i = null;
		boolean erreur = false;
		
		String id = request.getPathInfo();
		if (id != null) {
			id = id.replace("/", "").trim();
		}
		try {
			Integer.valueOf(id);
			session.setAttribute("noArticle",Integer.valueOf(id));
			
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
			r = rMgr.getRetraitByNoRetrait(Integer.valueOf(id));
				request.setAttribute("rue", r.getRue());
				request.setAttribute("codePostal", r.getCodePostal());
				request.setAttribute("ville", r.getVille());
			i=iMgr.getImageBynoArticle(Integer.valueOf(id));
			//request.setAttribute("image", i);
			//${pageContext.request.contextPath}/uploads/${image.picture}
			request.setAttribute("imageLocation", request.getContextPath()+"/uploads/"+i.getPicture());
			System.out.println(request.getContextPath()+"/uploads/"+i.getPicture());
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
		//request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArticleManager aMgr = new ArticleManager();
		UtilisateurManager uMgr = new UtilisateurManager();
		List<ErrorCodes> lstParam = new ArrayList<>();
		RetraitManager rMgr = new RetraitManager();
		
		String nomArticle=(String)request.getParameter("nomArticle");
		String description=(String)request.getParameter("description");
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), DateTimeFormatter.ISO_DATE_TIME) ;
		LocalDateTime dateFinEncheres=LocalDateTime.parse(request.getParameter("dateFinEncheres"), DateTimeFormatter.ISO_DATE_TIME);
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		String rue=(String)request.getParameter("rue");
		String codePostal=(String)request.getParameter("codePostal");
		String ville=(String)request.getParameter("ville");
		
		// Gets absolute path to root directory of web app.
        String appPath = request.getServletContext().getRealPath("");
        // Gets image informations
        Part part = request.getPart("pictureFile");
        // Save image File and get fileName
        String fileName = Utils.saveFile(SAVE_DIRECTORY, appPath, part);
		
		if(validerChamps(lstParam,nomArticle,description, dateDebutEncheres, dateFinEncheres, prixInitial, rue, codePostal, ville)) {
			try {
				Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
				Integer noArticle=(Integer)session.getAttribute("noArticle");
				ArticleVendu aV = new ArticleVendu(noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, null, noUtilisateur, noCategorie, false, false, "");
				aMgr.update(aV);
				rMgr.update(new Retrait(noArticle, rue, codePostal, ville));
				
				response.sendRedirect("/ENI-enchere/DetailVente/"+noArticle);
				
				} catch (Exception e) {
					System.out.println("Echec d'update");
					e.printStackTrace();
					request.setAttribute("lstParam", lstParam);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierVente.jsp");
					rd.forward(request, response);
				}
		} else {
			System.out.println("Les champs ne sont pas validés");
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
//		if (!text.matches(errorCode.getPattern())) {
//			lstParam.add(errorCode);
//		}
		Pattern pattern = Pattern.compile(errorCode.getPattern());
		Matcher matcher = pattern.matcher(text);
		if (!matcher.matches()) {
			lstParam.add(errorCode);
		}
		
	}

}

