<%@page import="fr.eni.enchere.bo.ArticleStatus"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@page import="fr.eni.enchere.bo.ArticleStatus"%>
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

		<h2>Rechercher une <span id="yellow">enchère</span></h2>

		<form action="" method="POST">
			<div class="search-container">
				<label for="searchBar">Mots clés</label> 
				<input id="searchBar" name="barreRecherche" type="search" placeholder="Que recherchez vous ?"/>
			</div>
			<div class="bar"></div>

			<div class="select-container">
				<label for="categorie">Catégorie : </label> 
				<select name="categorie" id="categorie">
					<option value="0" selected>Toutes</option>
					<%
					boolean categorieFind = false;
					List<Categorie> listCategories = (List<Categorie>) session.getAttribute("listCategories");
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
			<div class="bar"></div>
			<%
			if (isConnected) {
				if (request.getParameter("radio") != null) {
				%>	
			<div class="radio-container">
				<label>Achats/Ventes</label>
				<div class="achats-container">
					<input name="radio" id="radio-achat" type="radio" value="achat" <%=request.getParameter("radio")==null?"":request.getParameter("radio").equals("achat")?"Checked":"" %>>
					<label for="radio-achat">Achats</label> 
					<div class="achats-display">
						<div>					
							<input name="enchere-ouverte" id="enchere-ouverte" type="checkbox" <%=request.getParameter("radio").equals("vente")?"disabled":"" %> <%=request.getParameter("enchere-ouverte") == null?"":request.getParameter("enchere-ouverte").equals("on")?"Checked":"" %>> 
							<label for="enchere-ouverte">Enchères ouvertes</label> 
						</div>	
						<div>						
							<input name="mes-encheres" id="mes-encheres" <%=request.getParameter("radio").equals("vente")?"disabled":"" %>  <%=request.getParameter("radio").equals("vente")?"disabled":"" %> type="checkbox" <%=request.getParameter("mes-encheres")==null?"":request.getParameter("mes-encheres").equals("on")?"Checked":"" %>> 
							<label for="mes-encheres">Mes enchères</label> 
						</div>	
						<div>						
							<input name="enchere-remportees" id="enchere-remportees" <%=request.getParameter("radio").equals("vente")?"disabled":"" %> type="checkbox" <%=request.getParameter("enchere-remportees")==null?"":request.getParameter("enchere-remportees").equals("on")?"Checked":"" %>> 
							<label for="enchere-remportees">Mes enchères remportées</label>
						</div>			
					</div>
				</div>
				<div class="ventes-container">
					<input name="radio" id="radio-vente" value="vente" type="radio" <%=request.getParameter("radio")==null?"":request.getParameter("radio").equals("vente")?"Checked":"" %>> 
					<label for="radio-vente">Mes ventes</label> 
					<div class="ventes-display">
						<div>					
							<input <%=request.getParameter("vente-cours")==null?"":request.getParameter("vente-cours").equals("on")?"Checked":"" %> name="vente-cours" id="vente-cours" type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> 
							<label for="vente-cours">Mes ventes en cours</label> 
						</div>	
						<div>						
							<input <%=request.getParameter("vente-debutees")==null?"":request.getParameter("vente-debutees").equals("on")?"Checked":"" %> name="vente-debutees" id="vente-debutees" type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> 
							<label for="vente-debutees">ventes non débutées</label> 
						</div>		
						<div>						
							<input <%=request.getParameter("vente-terminees")==null?"":request.getParameter("vente-terminees").equals("on")?"Checked":"" %> name="vente-terminees" id="vente-terminees" type="checkbox" <%=request.getParameter("radio").equals("achat")?"disabled":"" %>> 
							<label for="vente-terminees">ventes terminées</label>
						</div>		
					</div>
				</div>
			</div>
			<div class="bar"></div>
			<%
			} else {
			%>
			<div class="radio-container">
				<label>Achats/Ventes</label>
				<div class="achats-container">
					<input name="radio" id="radio-achat" type="radio" value="achat" Checked>
					<label for="radio-achat">Achats</label> 
					<div class="achats-display isShowed">	
						<div>					
							<input name="enchere-ouverte" id="enchere-ouverte" type="checkbox" Checked> 
							<label for="enchere-ouverte">Enchères ouvertes</label> 
						</div>	
						<div>						
							<input name="mes-encheres" id="mes-encheres" type="checkbox"> 
							<label for="mes-encheres">Mes enchères</label> 
						</div>	
						<div>
							<input name="enchere-remportees" id="enchere-remportees" type="checkbox"> 
							<label for="enchere-remportees">Mes enchères remportées</label>
						</div>		
					</div>
				</div>
				<div class="ventes-container">
					<input name="radio" id="radio-vente" value="vente" type="radio"> 
					<label for="radio-vente">Mes ventes</label> 
					<div class="ventes-display">
						<div>					
							<input disabled name="vente-cours" id="vente-cours" type="checkbox"> 
							<label for="vente-cours">Mes ventes en cours</label> 
						</div>
						<div>						
							<input disabled name="vente-debutees" id="vente-debutees" type="checkbox"> 
							<label for="vente-debutees">ventes non débutées</label> 
						</div>
						<div>						
							<input disabled name="vente-terminees" id="vente-terminees" type="checkbox"> 
							<label for="vente-terminees">ventes terminées</label>
						</div>
					</div>
				</div>
			</div>
			<div class="bar"></div>
			<%
				}
				
			%>
				
			<%
				} else {
			%>	
			<% 
				}
			%>


			<button class="form-btn" type="submit"><img src="img/arrow-right.png"></button>
		</form>

		<div class="list-encheres">
			<%
			List<ArticleVendu> listArticle = (List<ArticleVendu>) request.getAttribute("listArticle");
			for (ArticleVendu av : listArticle) {
			%>
				<div class="enchere">
					<div class="img"><img src="https://source.unsplash.com/random/?<%=av.getNomArticle()%>"></div>
					<h3><%=av.getNomArticle()%></h3>
					<p class="statut <%=av.getArticleStatus().getColor()%>"><%=av.getArticleStatus().getStatusMessage()%></p>
					<div class="detail-enchere">
						<div>
							<label class="enchere-label">Vendeur</label>
							<a href="/ENI-enchere/InfosProfil/<%=av.getNoUtilisateur()%>"><%=av.getNomPrenomAuteur()%></a>
						</div>
						<div>
							<label class="enchere-label">Termine le</label>
							<p><%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
						</div>
					</div>
						<div class="btn-container">
							<div>
								<label class="enchere-label">Meilleure offre</label>
								<div><%=av.getPrixVente()%><div class="icon"><img src="img/eni-coin.png"></div></div>
							</div>
							<%
								if (isConnected) {
									%><a href="/ENI-enchere/DetailVente/<%=av.getNoArticle()%>">Voir l'article</a><%
								}
							%>
						</div>
				</div>
			<%
			}
			%>
		</div>

	</main>

	<script type="text/javascript" src="js/script.js"></script>
</body>

</html>