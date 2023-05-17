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
import fr.eni.enchere.dal.exceptions.BusinessException;

/**
 * Servlet implementation class ServletModifierProfil
 */
@WebServlet("/ServletModifierProfil")
public class ServletModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierProfil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("noUtilisateur") == null) {
			response.sendRedirect("/ENI-enchere");
		} else {
			HttpSession session = request.getSession();
			UtilisateurManager mgr = new UtilisateurManager();
			Utilisateur utilisateur = null;
			try {
				int noUtilisateur = Integer.valueOf(String.valueOf(session.getAttribute("noUtilisateur")));
				utilisateur = mgr.getUtilisateurByNoUtilisateur(noUtilisateur);
				System.out.println("utilisateur : "+utilisateur.toString());
				System.out.println(utilisateur.getPseudo());
				request.setAttribute("pseudo", utilisateur.getPseudo());
				request.setAttribute("nom", utilisateur.getNom());
				request.setAttribute("prenom", utilisateur.getPrenom());
				request.setAttribute("email", utilisateur.getEmail());
				request.setAttribute("tel", utilisateur.getTelephone());
				request.setAttribute("rue", utilisateur.getRue());
				request.setAttribute("codePostal", utilisateur.getCodePostal());
				request.setAttribute("ville", utilisateur.getVille());
				
				request.setAttribute("utilisateur", utilisateur);
			
			} catch (Exception e) {
				
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtilisateurManager mgr = new UtilisateurManager();
		Utilisateur utilisateur = null;
		try {
			int noUtilisateur = Integer.valueOf(String.valueOf(session.getAttribute("noUtilisateur")));
			utilisateur = mgr.getUtilisateurByNoUtilisateur(noUtilisateur);
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
			
			int credit = utilisateur.getCredit();
			boolean administrateur = utilisateur.getAdministrateur();
			
			request.setAttribute("utilisateur", utilisateur);
			
			List<ErrorCodes> lstParam = new ArrayList<>();
			
			if (validerChamps(lstParam, pseudo, nom, prenom, email, tel, rue, codePostal, ville, pwdUser, confPwdUser)) {
				utilisateur = new Utilisateur(noUtilisateur, pseudo, nom, prenom, email, tel, rue, codePostal, ville, confPwdUser, credit, administrateur);
				System.out.println(utilisateur.toString());
				try {
					mgr.updateUtilisateur(utilisateur);
				} catch (BusinessException be) {
					if (be.getMessage().contains(ErrorCodes.PSEUDO_ALREADY_EXIST.getMessage())) {
						lstParam.add(ErrorCodes.PSEUDO_ALREADY_EXIST);
					}
					if (be.getMessage().contains(ErrorCodes.EMAIL_ALREADY_EXIST.getMessage())) {
						lstParam.add(ErrorCodes.EMAIL_ALREADY_EXIST);
					}
					
					request.setAttribute("lstParam", lstParam);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
					rd.forward(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				response.sendRedirect("/ENI-enchere");
			} else {
				request.setAttribute("lstParam", lstParam);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		if (!matcher.matches()) {
			lstParam.add(errorCode);
		}
	}

}
