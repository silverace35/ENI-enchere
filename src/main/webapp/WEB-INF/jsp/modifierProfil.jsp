<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni.enchere.controller.ErrorCodes"%>
<%@page import="java.util.List"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/login.css">
		<title>Modification de profil | ENI-enchere</title>
	</head>

	<body>
	
	<%
		Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
	
		List<ErrorCodes> lstPara =(List<ErrorCodes>)request.getAttribute("lstParam");
		
		if (lstPara == null) {
			lstPara = new ArrayList<>();
		}
	
	%>
		<nav>
			<h1><a href="/ENI-enchere">ENI-Enchères</a></h1>
			<ul></ul>
		</nav>

		<main>
			<h2>Modifier mes informations</h2>

			<form action="/ENI-enchere/ServletModifierProfil" method="POST">
			
				<div class="fields">
					<div class="field">			
						<label for="pseudo">Pseudo : </label>
						<input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required
							 value= "<%=lstPara.contains(ErrorCodes.PSEUDO)?"":request.getAttribute("pseudo")==null?request.getParameter("pseudo"):request.getAttribute("pseudo")%>"
							placeholder= "<%=lstPara.contains(ErrorCodes.PSEUDO)?ErrorCodes.PSEUDO.getMessage():""%>"
						/>

					</div>
	
					<div class="field">
						<label for="nom">Nom : </label>
						<input type="text" id="nom" name="nom" minlength="2" maxlength="30" required
							value= "<%=lstPara.contains(ErrorCodes.NOM)?"":request.getAttribute("nom")==null?request.getParameter("nom"):request.getAttribute("nom")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.NOM)?ErrorCodes.NOM.getMessage():""%>"
						/>				
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<label for="prenom">Prénom : </label>
						<input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required
						value= "<%=lstPara.contains(ErrorCodes.PRENOM)?"":request.getAttribute("prenom")==null?request.getParameter("prenom"):request.getAttribute("prenom")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.PRENOM)?ErrorCodes.PRENOM.getMessage():""%>"
						/>
					</div>
					
					<div class="field">
						<label for="email">Email : </label>
						<input type="text" id="email" name="email" minlength="6" maxlength="50" required
						value= "<%=lstPara.contains(ErrorCodes.EMAIL)?"":request.getAttribute("email")==null?request.getParameter("email"):request.getAttribute("email")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.EMAIL)?ErrorCodes.EMAIL.getMessage():""%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="tel">Téléphone : </label>
						<input type="text" id="tel" name="tel" minlength="10" maxlength="15" required
						value= "<%=lstPara.contains(ErrorCodes.TEL)?"":request.getAttribute("tel")==null?request.getParameter("tel"):request.getAttribute("tel")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.TEL)?ErrorCodes.TEL.getMessage():""%>"
						/>
					</div>
					
					<div class="field">
						<label for="rue">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="10" maxlength="30" required
						value= "<%=lstPara.contains(ErrorCodes.RUE)?"":request.getAttribute("rue")==null?request.getParameter("rue"):request.getAttribute("rue")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.RUE)?ErrorCodes.RUE.getMessage():""%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="codePostal">Code Postal : </label>
						<input type="text" id="codePostal" name="codePostal" minlength="5" maxlength="10" required
							value= "<%=lstPara.contains(ErrorCodes.CODEPOSTAL)?"":request.getAttribute("codePostal")==null?request.getParameter("codePostal"):request.getAttribute("codePostal")%>"
						placeholder= "<%=lstPara.contains(ErrorCodes.CODEPOSTAL)?ErrorCodes.CODEPOSTAL.getMessage():""%>"
						/>
					</div>
					
					<div class="field">
						<label for="ville">Ville : </label>
						<input type="text" id="ville" name="ville" minlength="2" maxlength="50" required
							value= "<%=lstPara.contains(ErrorCodes.VILLE)?"":request.getAttribute("ville")==null?request.getParameter("ville"):request.getAttribute("ville")%>"
							placeholder= "<%=lstPara.contains(ErrorCodes.VILLE)?ErrorCodes.VILLE.getMessage():""%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="motDePasse">Mot de passe : </label>
						<input type="password" id="motDePasse" name="motDePasse" minlength="8" maxlength="30" required
						placeholder= "<%=lstPara.contains(ErrorCodes.PWDUSER)?ErrorCodes.PWDUSER.getMessage():""%>"
						/>
					</div>
					<div class="field">
						<label for="confMotDePasse">Confirmation : </label>
						<input type="password" id="confMotDePasse" name="confMotDePasse" minlength="8" maxlength="30" required
						placeholder= "<%=lstPara.contains(ErrorCodes.CONFPWDUSER)?ErrorCodes.PWDUSER.getMessage():""%>"
						/>
					</div>
				</div>
						<p><%=lstPara.contains(ErrorCodes.PASSWORDMISSMATCH)?ErrorCodes.PASSWORDMISSMATCH.getMessage():""%></p>
				
				<p>Crédit : ${utilisateur.getCredit()}</p>

				<button type="submit">Enregistrer</button>
			</form>

			<div class="line-container">
				<div class="line"></div>
				<p>ou</p>
				<div class="line"></div>
			</div>

			<a class="warning-link" href="/ENI-enchere/profil/suppression">Supprimer mon compte</a>
			
			<div class="line-container">
				<div class="line"></div>
				<p>ou</p>
				<div class="line"></div>
			</div>
			
			<a class="secondary-link" href="/ENI-enchere/InfosProfil">Retour</a>
		</main>

	</body>

	</html>