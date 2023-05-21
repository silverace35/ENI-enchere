package fr.eni.enchere.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.ImageManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.utils.Utils;

/**
 * Servlet implementation class ServletTestAjoutArticle
 */
@WebServlet("/AjoutArticle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
				maxFileSize = 1024 * 1024 * 10, // 10MB
				maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ServletAjoutArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String SAVE_DIRECTORY = "uploads";
	
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
				
		if (session.getAttribute("desactive") == null && session.getAttribute("noUtilisateur")!= null) {
			try {
				Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
				utilisateur = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				request.setAttribute("rue", utilisateur.getRue());
				request.setAttribute("codePostal", utilisateur.getCodePostal());
				request.setAttribute("ville", utilisateur.getVille());
				} catch (Exception e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
			rd.forward(request, response);
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Error403.jsp");
			rd.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ArticleManager mgr = new ArticleManager();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		List<ErrorCodes> lstParam = new ArrayList<>();
		
		String nomArticle=request.getParameter("nomArticle");
		String description=request.getParameter("description");
		LocalDateTime dateDebutEncheres=LocalDateTime.parse(request.getParameter("dateDebutEncheres"), DateTimeFormatter.ISO_DATE_TIME) ;
		LocalDateTime dateFinEncheres=LocalDateTime.parse(request.getParameter("dateFinEncheres"), DateTimeFormatter.ISO_DATE_TIME);
		Integer prixInitial=Integer.valueOf(request.getParameter("prixInitial"));
		Integer noCategorie=Integer.valueOf( request.getParameter("categorie"));
		String rue=request.getParameter("rue");
		String codePostal=request.getParameter("codePostal");
		String ville=request.getParameter("ville");
		
		// Gets absolute path to root directory of web app.
        String appPath = request.getServletContext().getRealPath("");
        // Gets image informations
        
        Part part = request.getPart("pictureFile");
       
       
		
		if(validerChamps(lstParam,nomArticle,description, dateDebutEncheres, dateFinEncheres, prixInitial, rue, codePostal, ville)) {
			try {
				Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
				ArticleVendu aV = mgr.insert(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixInitial, noUtilisateur, noCategorie, false, false, "");
				Utilisateur u = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				boolean nouvelleAdresse = (rue.equals(u.getRue()))&&(codePostal.toString().equals(u.getCodePostal()))&&(ville.equals(u.getVille()));
				RetraitManager retraitMgr = new RetraitManager();
				
				if (part.getSize()!=0) {
					// Save image File and get fileName
					String fileName = Utils.saveFile(SAVE_DIRECTORY,appPath, part);
					// Save image in database
					ImageManager iMgr = new ImageManager();
					iMgr.insert(aV.getNoArticle(),fileName);
				}
				
				if (!nouvelleAdresse) {
					retraitMgr.insert(aV.getNoArticle(), rue, codePostal, ville);
				} else {
					retraitMgr.insert(aV.getNoArticle(), u.getRue(), u.getCodePostal(), u.getVille());
				}
//				
				
				String msg = LocalDateTime.now().toString() + " | Controller : " + "ServletAjoutArticle" + 
						" | Utilisateur id : " + u.getNoUtilisateur() + " | Article id : "+aV.getNoArticle()+"\n"; 
				System.out.println(msg);
				Files.write(Paths.get(appPath,"log.txt"), msg.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
				response.sendRedirect("/ENI-enchere/DetailVente/"+aV.getNoArticle());
				//response.sendRedirect("/ENI-enchere");
				} catch (Exception e) {
					request.setAttribute("lstParam", lstParam);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutVente.jsp");
					rd.forward(request, response);
					// TODO Gestion des erreurs de saisie
					e.printStackTrace();
				}
		} else {
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

		if (prixInitial<0 || prixInitial > Integer.MAX_VALUE || prixInitial == null) {
			lstParam.add(ErrorCodes.PRIX_INITIAL);
		}
		
		if (dateDebut.isAfter(dateFin)) {
			lstParam.add(ErrorCodes.DATES_IMP);
		}
		return lstParam.size()==0;
	}
	public void valider(String text, ErrorCodes errorCode, List<ErrorCodes> lstParam) {
		Pattern pattern = Pattern.compile(errorCode.getPattern());
		Matcher matcher = pattern.matcher(text);
		if (!matcher.matches()) {
			lstParam.add(errorCode);
		}
	}
	
}

