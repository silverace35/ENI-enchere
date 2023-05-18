package fr.eni.enchere.controller;

import java.io.IOException;
import java.time.LocalDateTime;
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
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bll.ImageManager;
import fr.eni.enchere.bll.RetraitManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleStatus;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Image;
import fr.eni.enchere.bo.Retrait;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean erreur = false;
		HttpSession session = request.getSession();

		ArticleManager aMgr = new ArticleManager();
		UtilisateurManager uMgr = new UtilisateurManager();
		CategorieManager cMgr = new CategorieManager();
		EnchereManager eMgr = new EnchereManager();
		RetraitManager rMgr = new RetraitManager();
		ImageManager iMgr = new ImageManager();
		
		ArticleVendu av = null;
		Categorie c = null;
		Utilisateur u = null;
		List<Enchere> encheres = new ArrayList<Enchere>();
		Retrait r = null;
		Image i = null;
		
		if (request.getSession().getAttribute("noUtilisateur") != null) {
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
				av = aMgr.getByNoArticle(Integer.valueOf(id));
				r = rMgr.getRetraitByNoRetrait(av.getNoArticle());
//				System.out.println(r.toString());
				c = cMgr.getCategorieByNoCategorie(av.getNoCategorie());
				encheres.addAll(eMgr.selectAllEncheresByNoArticle(av.getNoArticle()));
				i = iMgr.getImageBynoArticle(av.getNoArticle());
				System.out.println(encheres.toString());
				if (!encheres.isEmpty()) {
					u = uMgr.getUtilisateurByNoUtilisateur(encheres.get(0).getNoUtilisateur());
				}
				if (av != null) {
					request.setAttribute("articleVendu", av);
					request.setAttribute("categorie", c);
					request.setAttribute("utilisateur", u);
					request.setAttribute("retrait", r);
					request.setAttribute("encheres", encheres);
					if (i !=null) {
						request.setAttribute("imageLocation", request.getContextPath()+"/uploads/"+i.getPicture());
					}
					
					//Si l'utilisateur consulte une de ses ventes
					if ((Integer)request.getSession().getAttribute("noUtilisateur") == (int)av.getNoUtilisateur()) {
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/detailMaVente.jsp");
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/detailVente.jsp");
						rd.forward(request, response);
					}
				} else {
					response.sendRedirect("/ENI-enchere");
				}
			}
		} else {
			response.sendRedirect("/ENI-enchere");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String erreurMessage = "Saisie invalide";

		ArticleManager aMgr = new ArticleManager();
		EnchereManager eMgr = new EnchereManager();
		CategorieManager cMgr = new CategorieManager();
		UtilisateurManager uMgr = new UtilisateurManager();
		RetraitManager rMgr = new RetraitManager();

		Utilisateur u = null;
		Utilisateur oldU = null;
		ArticleVendu av = null;
		boolean erreur = false;
		Retrait r = null;
		
		List<Enchere> encheres = new ArrayList<Enchere>();

		Integer noUtilisateur = (Integer)session.getAttribute("noUtilisateur");

		if (session.getAttribute("desactive") != null || session.getAttribute("noUtilisateur")== null) {
			response.sendRedirect("/ENI-enchere");
			System.out.println("utilisateur est null");
		} else {

			try {
				
				
				Integer proposition = null;

				String id = request.getPathInfo();
				if (id != null) {
					id = id.replace("/", "").trim();
				}
				try {
					if (!request.getParameter("proposition").isEmpty() && !request.getParameter("proposition").isBlank()) {
						proposition = Integer.valueOf(request.getParameter("proposition"));
					}
					Integer.valueOf(id);
				} catch (Exception e) {
					erreur = true;
				}
				u = uMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				av = aMgr.getByNoArticle(Integer.valueOf(id));
				encheres.addAll(eMgr.selectAllEncheresByNoArticle(av.getNoArticle()));
				r = rMgr.getRetraitByNoRetrait(av.getNoArticle());
				
				if (av == null || u == null || proposition == null) {
					erreur = true;
				} else if (proposition == null || proposition <= av.getPrixVente() || av.getArticleStatus() != ArticleStatus.EC || proposition < Integer.MIN_VALUE || proposition > Integer.MAX_VALUE || proposition == null) {
					erreur = true;
				} else {
					//Rend l'argent au dernier top enchere
					if (!encheres.isEmpty()) {
						oldU = uMgr.getUtilisateurByNoUtilisateur(encheres.get(0).getNoUtilisateur());
						if (oldU != null) {
							if (oldU.getNoUtilisateur() == u.getNoUtilisateur()) {
								u.setCredit(u.getCredit() + encheres.get(0).getMontantEnchere());
							} else {
								oldU.setCredit(oldU.getCredit() + encheres.get(0).getMontantEnchere());
								if (proposition > u.getCredit()) {
									erreur = true;
								} else {
									uMgr.updateCreditUtilisateur(oldU);
								}
							}
						}
					}
					
				}
				if (proposition != null) {
					if (proposition > u.getCredit()) {
						erreur = true;
					}
				} else {
					erreur = true;
				}

				if (erreur) {
					u = uMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
					av = aMgr.getByNoArticle(Integer.valueOf(id));
					Categorie c = cMgr.getCategorieByNoCategorie(av.getNoCategorie());					
					request.setAttribute("articleVendu", av);
					request.setAttribute("categorie", c);
					request.setAttribute("utilisateur", u);
					request.setAttribute("retrait", r);
					request.setAttribute("encheres", encheres);
					
					request.setAttribute("erreurMessage", erreurMessage);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/detailVente.jsp");
					rd.forward(request, response);
				} else {
					av.setPrixVente(proposition);
					u.setCredit(u.getCredit() - proposition);
					uMgr.updateCreditUtilisateur(u);
					eMgr.insert(LocalDateTime.now(), proposition, Integer.valueOf(id),
							u.getNoUtilisateur());
					aMgr.update(av);
					response.sendRedirect("/ENI-enchere/DetailVente/" + id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
