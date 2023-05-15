<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni.enchere.controller.ErrorCodes"%>
<%@page import="java.util.List"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/login.css">
		<title>Ajout vente | ENI-enchere</title>
	</head>

	<body>
	<%@ include file="connexionTest.jsp"%>
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
			<h2>Nouvelle vente</h2>

			<form action="/ENI-enchere/AjoutArticle" method="POST">
			
				<div class="fields">
					<div class="field">
						<label for="nomArticle">Article : </label>
						<input type="text" id="nomArticle" name="nomArticle" minlength="2" maxlength="30" required
							 value= ""
							placeholder= ""
						/>
					</div>
				</div>
				
				
				<div class="fields">
					<div class="select-container">
						<label for="categorie">Catégorie : </label> 
						<select name="categorie" id="categorie">
							<%
							List<Categorie> listCategories = (List<Categorie>) request.getAttribute("listCategories");
							for (Categorie c : listCategories) {
							%>
							<option value="<%=c.getNoCategorie() %>"><%=c.getLibelle() %></option>
							<%} %>
						</select>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="description">Description : </label>
						<input type="textarea" id="description" name="description" minlength="10" maxlength="300" required
						value= ""
						placeholder= ""
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<p>Photo de l'article</p>
						
					</div>
					<div class="field">
						<Button>Uploader</Button>
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<img style="height: 300px"
							src="https://source.unsplash.com/random/100×100/?code">
					</div>
				</div>
				
				<div class="fields">
					<div class="select-container">
						<label for="prixInitial">Prix initial : </label> 
							<input id="prixInitial" type="number" name="prixInitial" min="0" /> 
					</div>
				</div>
					
				<div class="fields">
					<div class="select-container">
						<label for="dateDebutEncheres">Début de l'enchère </label>
						<input type="datetime-local" id="dateDebutEncheres"
						       name="dateDebutEncheres" value="">

					</div>
				</div>
				<div class="fields">
					<div class="select-container">
						<label for="dateFinEncheres">Fin de l'enchère </label>
						<input type="datetime-local" id="dateFinEncheres"
						       name="dateFinEncheres" value="">

					</div>
				</div>
				
				<div class="fields">
					<label for="rue">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="2" maxlength="30" required
							 value= "${utilisateur.getRue() }"
							placeholder= ""
						/>
				</div>
				<div class="fields">
					<label for="codePostal">Code postal : </label>
						<input type="text" id="codePostal" name="codePostal" minlength="2" maxlength="10" required
							 value= "${utilisateur.getCodePostal() }"
							placeholder= ""
						/>
				</div>
				<div class="fields">
					<label for="ville">Ville : </label>
						<input type="text" id=""ville"" name=""ville"" minlength="2" maxlength="50" required
							 value= "${utilisateur.getVille() }"
							placeholder= ""
						/>
				</div>
					

					

				<button type="submit">Enregistrer</button>
			</form>

 
			
			<a class="secondary-link" href="/ENI-enchere">Annuler</a>
		</main>

	</body>

	</html>