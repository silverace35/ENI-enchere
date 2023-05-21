package fr.eni.enchere.controller;

import java.io.IOException;
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
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.ImageManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Image;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.utils.Logger;

/**
 * Servlet implementation class ServletIndex
 */
@WebServlet("")
public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String LOCATION = "ServletIndex";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletIndex() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArticleManager articleManager = new ArticleManager();
		UtilisateurManager utilisateurMgr = new UtilisateurManager();
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		ImageManager iMgr = new ImageManager();
		List<Image> lstImages = new ArrayList<>();
		
		// Récupération des images à afficher sur la page index
		lstImages = iMgr.selectAll();
		request.setAttribute("lstImages",lstImages);
        
		// Vérification s'il y a une session
		Integer noUtilisateur = null;
		if (request.getSession() != null) {
			 noUtilisateur = (Integer)session.getAttribute("noUtilisateur");
			 
			 //Vérification s'il y a un utilisateur enregistré dans la session
			if (noUtilisateur != null) {
				
				//Récupération des articles sur lesquels l'utilisateur n'a pas encore enchéri
				listArticle.addAll(articleManager.getArticlesEnCoursPasEncherie(noUtilisateur));
				try {
					
					//Récupération de l'utilisateur et envoie de ses coordonnées en session pour utilisation future
					Utilisateur	u = utilisateurMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
					session.setAttribute("rue", u.getRue());
					session.setAttribute("codePostal", u.getCodePostal());
					session.setAttribute("ville", u.getVille());
					} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				
				//Récupération des encheres ouvertes 
				listArticle.addAll(articleManager.getArticlesEnCours());
			}
		} else {
			listArticle.addAll(articleManager.getArticlesEnCours());
		}
		
		// Enregistrement du passage dans la servlet
		String appPath = request.getServletContext().getRealPath("");
		Logger.log(appPath, LOCATION,noUtilisateur, "Accessed the index" , null);
		
		// Envoi de la liste d'articles en attribut pour la jsp
		session.setAttribute("appPath", appPath);
		request.setAttribute("listArticle", listArticle);
		
		// Récupération de la liste des catégories en BDD et envoi en attribut
		CategorieManager catMgr = new CategorieManager();
		try {
			List<Categorie> listCategories = catMgr.selectAllCategories();
			session.setAttribute("listCategories", listCategories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Délégation vers la jsp
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		List<ArticleVendu> listArticle = new ArrayList<ArticleVendu>();
		ArticleManager articleManager = new ArticleManager();
		Integer id = (Integer)request.getSession().getAttribute("noUtilisateur");
		String radio = request.getParameter("radio");
		String barreRecherche = request.getParameter("barreRecherche");
		Integer categorie = Integer.parseInt(request.getParameter("categorie"));
		String enchereOuverte = request.getParameter("enchere-ouverte");
		String mesEncheres = request.getParameter("mes-encheres");
		String enchereRemportees = request.getParameter("enchere-remportees");
		String venteCours = request.getParameter("vente-cours");
		String venteDebutees = request.getParameter("vente-debutees");
		String venteTerminees = request.getParameter("vente-terminees");
		List<ArticleVendu> toRemove = new ArrayList<ArticleVendu>();
		ImageManager iMgr = new ImageManager();
		List<Image> lstImages = new ArrayList<>();
		
		// Enregistrement du passage dans la servlet
		String appPath = request.getServletContext().getRealPath("");
		Logger.log(appPath, LOCATION, id, "Accessed the index" , null);
		
		// Récupération des images à afficher sur la page index
		lstImages = iMgr.selectAll();
		request.setAttribute("lstImages",lstImages);
		
		// Récupération de la liste des articles selon que ce sont des ventes ou des enchères et selon les checkboxes sélectionnées.
		if ("achat".equals(radio)) {
			if ("on".equals(enchereOuverte)) {
				listArticle.addAll(articleManager.getArticlesEnCoursPasEncherie(id));
			}
			if ("on".equals(mesEncheres)) {
				listArticle.addAll(articleManager.getArticlesEnCoursEncherie(id));
			}
			if ("on".equals(enchereRemportees)) {
				listArticle.addAll(articleManager.getArticlesTerminerGagner(id));
			}
		} else if("vente".equals(radio)){
			if ("on".equals(venteCours)) {
				listArticle.addAll(articleManager.getVentesEnCours(id));
			}
			if ("on".equals(venteDebutees)) {
				listArticle.addAll(articleManager.getVentesNonDebute(id));
			}
			if ("on".equals(venteTerminees)) {
				listArticle.addAll(articleManager.getVentesTerminer(id));
			}
		} else {
			listArticle.addAll(articleManager.getArticlesEnCours());
		}
		
		// Retrait de la liste des articles affichés les articles qui ne font pas partie du tri 
		if (categorie != 0) {
			toRemove = new ArrayList<ArticleVendu>();
			for (ArticleVendu articleVendu : listArticle) {
				if (articleVendu.getNoCategorie() != categorie) {
					toRemove.add(articleVendu);
				}
			}
			listArticle.removeAll(toRemove);
		}
		
		// Retrait de la liste des articles affichés les articles qui ne correspondent pas au mot clé
		if (!barreRecherche.equals("") || !barreRecherche.isEmpty() || !barreRecherche.isBlank()) {
			toRemove = new ArrayList<ArticleVendu>();
			for (ArticleVendu articleVendu : listArticle) {
				if (!articleVendu.getNomArticle().toLowerCase().contains(barreRecherche.toLowerCase())) {
					toRemove.add(articleVendu);
				}
			}
			listArticle.removeAll(toRemove);
		}
		
		// Délégation vers la jsp
		request.setAttribute("listArticle", listArticle);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

}
