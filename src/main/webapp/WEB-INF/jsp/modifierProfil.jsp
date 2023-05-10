<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>

	<head>
		<%@ include file="head.jsp" %>
		<title>Modification de profil | ENI-enchere</title>
	</head>

	<body>
	
	<%
		Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
	%>
		<nav>
			<h1><a href="/ENI-enchere">ENI-Enchères</a></h1>
			<ul></ul>
		</nav>

		<main>
			<h2>Modifier mes informations</h2>

			<form action="/ENI-enchere/ServletModifierProfil" method="POST">
				<input type="hidden" name="noUtilisateur" value= "${utilisateur.getNoUtilisateur()}" />
				<input type="hidden" name="credit" value= "${utilisateur.getCredit()}" />
				<input type="hidden" name="administrateur" value= "${utilisateur.getAdministrateur()}" />
			
				<div class="fields">
					<div class="field">			
						<label for="pseudo">Pseudo : </label>
						<input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required
							 value="<%=request.getAttribute("pseudo")%>"
						/>

					</div>
	
					<div class="field">
						<label for="nom">Nom : </label>
						<input type="text" id="nom" name="nom" minlength="2" maxlength="30" required
							value="<%=request.getAttribute("nom")%>"
						/>				
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<label for="prenom">Prénom : </label>
						<input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required
						value="<%=request.getAttribute("prenom")%>"
						/>
					</div>
					
					<div class="field">
						<label for="email">Email : </label>
						<input type="text" id="email" name="email" minlength="6" maxlength="50" required
						value="<%=request.getAttribute("email")%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="tel">Téléphone : </label>
						<input type="text" id="tel" name="tel" minlength="10" maxlength="15" required
						value="<%=request.getAttribute("tel")%>"
						/>
					</div>
					
					<div class="field">
						<label for="rue">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="10" maxlength="30" required
						value="<%=request.getAttribute("rue")%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="codePostal">Code Postal : </label>
						<input type="text" id="codePostal" name="codePostal" minlength="5" maxlength="10" required
							value="<%=request.getAttribute("codePostal")%>"
						/>
					</div>
					
					<div class="field">
						<label for="ville">Ville : </label>
						<input type="text" id="ville" name="ville" minlength="2" maxlength="50" required
							value="<%=request.getAttribute("ville")%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="motDePasse">Mot de passe : </label>
						<input type="password" id="motDePasse" name="motDePasse" minlength="8" maxlength="30" required/>
					</div>
					<div class="field">
						<label for="confMotDePasse">Confirmation : </label>
						<input type="password" id="confMotDePasse" name="confMotDePasse" minlength="8" maxlength="30" required/>
					</div>
				</div>
				
				<p>Crédit : <%=request.getParameter("credit") %></p>

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