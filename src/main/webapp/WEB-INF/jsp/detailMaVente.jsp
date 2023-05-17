<%@page import="fr.eni.enchere.bo.ArticleStatus"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.enchere.bo.Enchere"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
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
<title>Détail ma vente | ENI-enchere</title>
</head>
<body>
<%@ include file="navigation.jsp"%>
<%
	ArticleVendu av = (ArticleVendu)request.getAttribute("articleVendu");
	Utilisateur u = (Utilisateur)request.getAttribute("utilisateur");
	Categorie c = (Categorie)request.getAttribute("categorie");
	List<Enchere> encheres = (List<Enchere>)request.getAttribute("encheres");
%>
	<main>
		<h1>Détail ma vente</h1>
		<%if (av.getArticleStatus() == ArticleStatus.CR) { %>
		<h2 style="color: black" class="">Enchère pas encore commencée</h2>
		<%} %>
		<%if (av.getArticleStatus() == ArticleStatus.EC) { %>
		<h2 style="color: black" class="">Enchère en cours</h2>
		<%} %>
		<%if (av.getArticleStatus() == ArticleStatus.ET) { %>
		<h2 style="color: black" class="">Enchère terminée <%=u!=null?u.getNom()+ " " +u.getPrenom():"" %></h2>
		<%} %>
		<div class="vente">
			<img style="height: 300px" src="https://source.unsplash.com/random/100×100/?code">
			<div class="detail-vente">
				<h3>Nom de l'article : <%=av.getNomArticle() %></h3>
				<h3>Description : <%=av.getDescription() %></h3>
				<h3>Catégorie : <%=c!=null?c.getLibelle():"La catégorie n'existe plus" %></h3>
				<% if (u!=null) {%>
				<h3>Meilleure offre : </h3><span> <%=av.getPrixVente()%> par <a href="/ENI-enchere/InfosProfil/<%=u.getNoUtilisateur()%>"><%=u.getNom() + " " + u.getPrenom()%></a></span>
				<%} else { %>
				<h3>Aucun utilisateur n'a enchéri</h3>
				<%} %>
				<h3>Mise à prix : <%=av.getPrixInitial() %></h3>
				<h3>Fin de l'enchère : <%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></h3>
				<h3>Retrait : </h3>
				<span>Vendeur : </span><a href="/ENI-enchere/InfosProfil/<%=av.getNoUtilisateur()%>"><%=av.getNomPrenomAuteur()%></a>
			</div>
		</div>
		
		<%if ((av.getArticleStatus() == ArticleStatus.EC && Integer.valueOf(String.valueOf(session.getAttribute("noUtilisateur"))) == av.getNoUtilisateur() && encheres.isEmpty()) || av.getArticleStatus() == ArticleStatus.CR) { %>
		<a class="secondary-link" href="/ENI-enchere/ModifierVente/<%=av.getNoArticle() %>">Modifier la vente</a>
		<%} %>
		
		<div>
			<% 
			for(Enchere enchere : encheres) {
			%>
			<div>
				<span><%=enchere.getDateEnchere().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")) %> : <%=enchere.getMontantEnchere() %> crédits</span>
			</div>
			<%
			}
			%>
		</div>
	</main>

</body>
</html>