<%@page import="java.time.format.DateTimeFormatter"%>
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

		<form action="" method="POST">
			<div class="search-container">
				<img class="icon" src="img/recherche.png"> <input
					name="barreRecherche" type="search" />
			</div>

			<div class="select-container">
				<label for="categorie">Catégorie : </label> 
				<select name="categorie" id="categorie">
					<option value="0" selected>Toutes</option>
					<%
					boolean categorieFind = false;
					List<Categorie> listCategories = (List<Categorie>) request.getAttribute("listCategories");
					for (Categorie c : listCategories) {
						categorieFind = false;
						if(request.getParameter("categorie") != null) {
							if (request.getParameter("categorie").equals(c.getNoCategorie().toString())) {
								categorieFind = true;
							}
						}
						%>
						<option value="<%=c.getNoCategorie() %>" <%=categorieFind?"selected":"" %>><%=c.getLibelle() %></option>
						<%
					} 
					%>
				</select>
			</div>
			<%
			if (isConnected) {
				if (request.getParameter("radio") != null) {
				%>	
				<div class="radio-container">
					<div class="achats-container">
						<input name="radio" id="radio-achat" type="radio" value="achat" <%=request.getParameter("radio")==null?"":request.getParameter("radio").equals("achat")?"Checked":"" %>>
						<label for="radio-achat">Achats</label> 
						<input name="enchere-ouverte" id="enchere-ouverte" type="checkbox" 
							<%=request.getParameter("radio").equals("vente")?"disabled":"" %> <%=request.getParameter("enchere-ouverte") == null?"":request.getParameter("enchere-ouverte").equals("on")?"Checked":"" %>> <label for="enchere-ouverte">Enchères
							ouvertes</label> <input name="mes-encheres" id="mes-encheres" <%=request.getParameter("radio").equals("vente")?"disabled":"" %> 
							<%=request.getParameter("radio").equals("vente")?"disabled":"" %> type="checkbox" <%=request.getParameter("mes-encheres")==null?"":request.getParameter("mes-encheres").equals("on")?"Checked":"" %>> <label for="mes-encheres">Mes
							enchères</label> <input name="enchere-remportees" id="enchere-remportees"
							 <%=request.getParameter("radio").equals("vente")?"disabled":"" %> type="checkbox" <%=request.getParameter("enchere-remportees")==null?"":request.getParameter("enchere-remportees").equals("on")?"Checked":"" %>> <label for="enchere-remportees">Mes
							enchères remportées</label>
					</div>
					<div class="ventes-container">
						<input name="radio" id="radio-vente" value="vente" type="radio" <%=request.getParameter("radio")==null?"":request.getParameter("radio").equals("vente")?"Checked":"" %>> <label
							for="radio-vente">Mes ventes</label> <input <%=request.getParameter("vente-cours")==null?"":request.getParameter("vente-cours").equals("on")?"Checked":"" %> name="vente-cours"
							id="vente-cours" type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> <label for="vente-cours">Mes
							ventes en cours</label> <input <%=request.getParameter("vente-debutees")==null?"":request.getParameter("vente-debutees").equals("on")?"Checked":"" %> name="vente-debutees" id="vente-debutees"
							type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> <label for="vente-debutees">ventes
							non débutées</label> <input <%=request.getParameter("vente-terminees")==null?"":request.getParameter("vente-terminees").equals("on")?"Checked":"" %> name="vente-terminees" id="vente-terminees"
							type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> <label for="vente-terminees">ventes
							terminées</label>
					</div>
				</div>
				<%
				} else {
				%>
					<div class="radio-container">
					<div class="achats-container">
						<input name="radio" id="radio-achat" type="radio" value="achat" Checked>
						<label for="radio-achat">Achats</label> 
						<input
							name="enchere-ouverte" id="enchere-ouverte" type="checkbox"
							Checked> <label for="enchere-ouverte">Enchères
							ouvertes</label> <input name="mes-encheres" id="mes-encheres"
							type="checkbox"> <label for="mes-encheres">Mes
							enchères</label> <input name="enchere-remportees" id="enchere-remportees"
							type="checkbox"> <label for="enchere-remportees">Mes
							enchères remportées</label>
					</div>
					<div class="ventes-container">
						<input name="radio" id="radio-vente" value="vente" type="radio"> <label
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
				
			%>
				
			<%
				} else {
			%>	
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
					<p><%=av.getArticleStatus().getStatusMessage()%></p>
					<p><%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
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