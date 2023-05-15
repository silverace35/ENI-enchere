<%@page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<%@ include file="head.jsp"%>
<link rel="stylesheet" href="css/settings.css">
<link rel="stylesheet" href="css/index.css">
<title>Accueil | ENI-enchere</title>
</head>

<%-- <% Enchere e = (Enchere)request.getAttribute("enchere"); %> --%>


<body>
	<%@ include file="navigation.jsp"%>

	<main>

		<h2>Rechercher une enchère</h2>

		<form action="<%=request.getContextPath()%>" method="GET">
			<div class="search-container">
				<img class="icon" src="img/recherche.png"> <input
					name="barreRecherche" type="search" />
			</div>

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
			<%
			if (isConnected) {
			%>
				<div class="radio-container">
					<div class="achats-container">
						<input name="radio" id="radio-achat" type="radio" checked>
						<label for="radio-achat">Achats</label> 
						<input
							name="enchere-ouverte" id="enchere-ouverte" type="checkbox"
							checked> <label for="enchere-ouverte">Enchères
							ouvertes</label> <input name="mes-encheres" id="mes-encheres"
							type="checkbox"> <label for="mes-encheres">Mes
							enchères</label> <input name="enchere-remportees" id="enchere-remportees"
							type="checkbox"> <label for="enchere-remportees">Mes
							enchères remportées</label>
					</div>
					<div class="ventes-container">
						<input name="radio" id="radio-vente" type="radio"> <label
							for="radio-vente">Mes ventes</label> <input disabled name="vente-cours"
							id="vente-cours" type="checkbox"> <label for="vente-cours">Mes
							ventes en cours</label> <input disabled name="vente-debutees" id="vente-debutees"
							type="checkbox"> <label for="vente-debutees">ventes
							non débutées</label> <input disabled name="vente-terminees" id="vente-terminees"
							type="checkbox"> <label for="vente-terminees">ventes
							terminées</label>
					</div>
				</div>
			<%
			}
			//TODO: disabled l'un si l'autre est coché
			//TODO: faire le style
			%>


			<button class="form-btn" type="submit">Rechercher une enchère</button>
		</form>

		<%
		List<ArticleVendu> listArticle = (List<ArticleVendu>) request.getAttribute("listArticle");
		for (ArticleVendu av : listArticle) {
		%>
		<div class="list-encheres">
			<div class="enchere">
				<img style="height: 300px"
					src="https://source.unsplash.com/random/100×100/?code">
				<div class="details-enchere">
					<h3>
						<a href="/ENI-enchere/DetailVente/<%=av.getNoArticle()%>"><%=av.getNomArticle()%></a>
					</h3>
					<p><%=av.getPrixVente()%></p>
					<p><%=av.getDateFinEncheres()%></p>
					<p>
						<a href="/ENI-enchere/InfosProfil/<%=av.getNoUtilisateur()%>"><%=av.getNomPrenomAuteur()%></a>
					</p>
				</div>
			</div>
		</div>
		<%
		}
		%>

	</main>

	<script type="text/javascript" src="js/script.js"></script>
</body>

</html>