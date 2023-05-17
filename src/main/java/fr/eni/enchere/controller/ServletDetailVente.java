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
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.ArticleStatus;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
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
		
		ArticleVendu av = null;
		Categorie c = null;
		Utilisateur u = null;
		List<Enchere> encheres = new ArrayList<Enchere>();
		
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
				c = cMgr.getCategorieByNoCategorie(av.getNoCategorie());
				encheres.addAll(eMgr.selectAllEncheresByNoArticle(av.getNoArticle()));
				System.out.println(encheres.toString());
				if (!encheres.isEmpty()) {
					u = uMgr.getUtilisateurByNoUtilisateur(encheres.get(0).getNoUtilisateur());
				}
				if (av != null) {
					request.setAttribute("articleVendu", av);
					request.setAttribute("categorie", c);
					request.setAttribute("utilisateur", u);
					request.setAttribute("encheres", encheres);
					System.out.println(av.toString());
					//Si l'utilisateur consulte une de ses ventes
					System.out.println(request.getSession().getAttribute("noUtilisateur"));
					if ((Integer)request.getSession().getAttribute("noUtilisateur") == av.getNoUtilisateur()) {
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
		UtilisateurManager uMgr = new UtilisateurManager();

		Utilisateur u = null;
		Utilisateur oldU = null;
		ArticleVendu av = null;
		boolean erreur = false;
		List<Enchere> encheres = new ArrayList<Enchere>();

		Integer noUtilisateur = Integer.valueOf(String.valueOf(session.getAttribute("noUtilisateur")));

		if (request.getSession().getAttribute("noUtilisateur") == null) {
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
					proposition = Integer.valueOf(request.getParameter("proposition"));
					Integer.valueOf(id);
				} catch (Exception e) {
					erreur = true;
				}
				u = uMgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				av = aMgr.getByNoArticle(Integer.valueOf(id));
				encheres.addAll(eMgr.selectAllEncheresByNoArticle(av.getNoArticle()));
				if (av == null || u == null) {
					erreur = true;
					System.out.println("Article ou user null");
				} else if (proposition == null || proposition <= av.getPrixVente() || av.getArticleStatus() != ArticleStatus.EC) {
					System.out.println("proposition null ou proposition <= prix de vente");
					System.out.println(proposition);
					System.out.println(av.getPrixVente());
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
				if (proposition > u.getCredit()) {
					erreur = true;
				}
				

				if (erreur) {
					request.setAttribute("erreurMessage", erreurMessage);
					response.sendRedirect("/ENI-enchere");
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
