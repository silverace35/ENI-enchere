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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		
		List<String> lstParam = new ArrayList<>();
		
		if (validerChamps(lstParam, pseudo, nom, prenom, email, tel, rue, codePostal, ville, pwdUser, confPwdUser)) {
		
			try {
				Utilisateur u = mgr.insert(pseudo, nom, prenom, email, tel, rue, codePostal, ville, confPwdUser, 100);
				System.out.println(u);
				request.setAttribute("utilisateur", u);
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
	
	public boolean validerChamps(List<String> lstParam, String pseudo, String nom, String prenom, 
			String email, String tel, String rue, String codePostal, String ville, String pwdUser, String confPwdUser) {
			boolean result = true;
			final String patternString = "^[\\w-]{2,30}$";
			final String patternNom = "^[a-z A-Z\\'\\-]{2,30}$";
			final String patternVille = "^[a-z A-Z\\'\\-]{2,50}$";
			final String patternRue = "^[0-9 a-z A-Z\\'\\-]{2,30}$";
			final String patternEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			final String patternTel = "(^\\+{1}+[3]{2}+[0-9]{9}$)|(^0{1}+[0-9]{9}$)";
			final String patternCP = "^[0-9]{5}$";
			final String patternMDP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,30}$";
			
				lstParam.add(valider(patternString, pseudo, "Caractères alphanumériques requis."));
				lstParam.add(valider(patternNom, nom, "Caractères alphanumériques requis."));
				lstParam.add(valider(patternNom, prenom, "Caractères alphanumériques requis."));
				lstParam.add(valider(patternEmail, email, "Type de format possible xxxx-xxxx@xxxx.xxx."));
				lstParam.add(valider(patternTel, tel, "+33123123112 ou 0110203040"));
				lstParam.add(valider(patternRue, rue, "Caractères alphanumériques requis."));
				lstParam.add(valider(patternCP, codePostal, "Code postal incorrect."));
				lstParam.add(valider(patternVille, ville, "Caractères alphanumériques requis."));
				lstParam.add(valider(patternMDP, pwdUser, "Format de mot de passe incorrect."));
				lstParam.add(valider(patternMDP, confPwdUser, "Format de mot de passe incorrect."));
				lstParam.add(confPwdUser.equals(pwdUser)? "true":"Les mots de passe ne correspondent pas.");
			for(String s:lstParam)	 {
				if (!"true".equals(s)) {
					result = false;
				}
			}
		return result;
	}
	
	public String valider(String pat, String text, String message) {
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(text);
		String result;
		if (matcher.matches() ) {
			result = "true";
		} else {
			result = message;
		}
		return result;
	}


}
