<%@ page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page import="java.time.format.DateTimeFormatter" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<link rel="stylesheet" href="../css/settings.css">
<link rel="stylesheet" href="../css/login.css">
<title>Détail vente | ENI-enchere</title>
</head>
<body>
<%ArticleVendu av = (ArticleVendu) request.getAttribute("articleVendu");%>
<%Utilisateur u = (Utilisateur) request.getAttribute("u");%>
<%@ include file="navigation.jsp"%>
	<main>
		<h1>Détail vente</h1>
		<div class="vente">
			<img style="height: 300px" src="https://source.unsplash.com/random/100×100/?code">
			<div class="detail-vente">
				<h3>Nom de l'article : <%=av.getNomArticle() %></h3>
				<h3>Description : <%=av.getDescription() %></h3>
				<h3>Catégorie :</h3>
				<h3>Meilleure offre :<%=av.getPrixVente() %> par ?????</h3>
				<h3>Mise à prix : <%=av.getPrixInitial() %></h3>
				<h3>Fin de l'enchère : <%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></h3>
				<h3>Retrait : </h3>
				<h3>Vendeur : <%=av.getNomPrenomAuteur() %></h3>
				<form action="/ENI-enchere/DetailVente/<%=av.getNoArticle() %>" method="POST">				
					<label for="proposition">Ma proposition</label>
					<input id="proposition" name="proposition" step="10" type="number" value="<%=av.getPrixVente() + 10%>" min="<%=av.getPrixVente() + 10%>">
					<button type="submit">Enchérir</button>
				</form>
			</div>
		</div>
	</main>

</body>
</html>