package fr.eni.enchere.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("noUtilisateur") != null) {
			response.sendRedirect("/ENI-enchere");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		String confPwdUser = request.getParameter("confMotDePasse");

		List<ErrorCodes> lstParam = new ArrayList<>();

		if (validerChamps(lstParam, pseudo, nom, prenom, email, tel, rue, codePostal, ville, pwdUser, confPwdUser)) {

			try {

				Utilisateur u = mgr.insert(pseudo, nom, prenom, email, tel, rue, codePostal, ville, confPwdUser, 100);
				System.out.println(u); 
				request.setAttribute("utilisateur", u);
				// HttpSession session = request.getSession();
				request.getSession().setAttribute("noUtilisateur", u.getNoUtilisateur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
				rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			request.setAttribute("lstParam", lstParam);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
		}

	}

	public boolean validerChamps(List<ErrorCodes> lstParam, String pseudo, String nom, String prenom, String email,
			String tel, String rue, String codePostal, String ville, String pwdUser, String confPwdUser) {
		boolean result = true;
		valider(pseudo, ErrorCodes.PSEUDO, lstParam);
		valider(nom, ErrorCodes.NOM, lstParam);
		valider(prenom, ErrorCodes.PRENOM, lstParam);
		valider(email, ErrorCodes.EMAIL, lstParam);
		valider(tel, ErrorCodes.TEL, lstParam);
		valider(rue, ErrorCodes.RUE, lstParam);
		valider(codePostal, ErrorCodes.CODEPOSTAL, lstParam);
		valider(ville, ErrorCodes.VILLE, lstParam);
		valider(pwdUser, ErrorCodes.PWDUSER, lstParam);
		valider(confPwdUser, ErrorCodes.CONFPWDUSER, lstParam);
		
		if (!confPwdUser.equals(pwdUser)) {
			lstParam.add(ErrorCodes.PASSWORDMISSMATCH);
		}
		
		for (ErrorCodes e : lstParam) {
			result = false;
		}
		return result;
	}

	public void valider(String text, ErrorCodes errorCode, List<ErrorCodes> lstParam) {
		Pattern pattern = Pattern.compile(errorCode.getPattern());
		Matcher matcher = pattern.matcher(text);
		String result;
		if (!matcher.matches()) {
			lstParam.add(errorCode);
		}
	}

}
