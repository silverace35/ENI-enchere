<%@page import="fr.eni.enchere.bo.Retrait"%>
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
<title>Détail vente | ENI-enchere</title>
</head>
<body>
<%@ include file="navigation.jsp"%>
<%
	ArticleVendu av = (ArticleVendu)request.getAttribute("articleVendu");
	Utilisateur u = (Utilisateur)request.getAttribute("utilisateur");
	Categorie c = (Categorie)request.getAttribute("categorie");
	List<Enchere> encheres = (List<Enchere>)request.getAttribute("encheres");
	Retrait r = (Retrait)request.getAttribute("retrait");
%>
	<main>
		<%if (av.getArticleStatus() == ArticleStatus.CR) { %>
		<h2 style="color: black" class="">Enchère pas encore commencée</h2>
		<%} %>
		<%if (av.getArticleStatus() == ArticleStatus.ET) { %>
		<h2 style="color: black" class="">Enchère terminée</h2>
		<%} %>
		<%if (!encheres.isEmpty() && av.getArticleStatus() == ArticleStatus.ET && (Integer)session.getAttribute("noUtilisateur") == (int)u.getNoUtilisateur()) { %>
		<h2 style="color: black">Vous avez gagné !</h2>
		<%}%>
		<div class="vente">
			<div class="img vente-img">		
				<img src="https://source.unsplash.com/random/?<%=av.getNomArticle() %>">
			</div>
			<div class="detail-vente">
			<h1><%=av.getNomArticle() %></h1>
				<h5>(<%=c!=null?c.getLibelle():"La catégorie n'existe plus" %>)</h5>
				<div class="name">				
					<span>Vendu par</span>
					<a href="/ENI-enchere/InfosProfil/<%=av.getNoUtilisateur()%>"> <%=av.getNomPrenomAuteur()%></a>
				</div>
					<p>Description : <%=av.getDescription() %></p>
				<div class="line"></div>
				
				<div class="fields-container">	
				<div class="vente-fields">				
					<label>Mise à prix : <%=av.getPrixInitial() %> <div class="icon"><img src="../img/eni-coin.png"></div></label>
					<div class="name">Meilleure offre : <%=av.getPrixVente()%> <div class="icon"><img src="../img/eni-coin.png"></div> par<a href="/ENI-enchere/InfosProfil/<%=u.getNoUtilisateur()%>"><%=" "+u.getNom() + " " + u.getPrenom()%></a></div>
					
					<% if (u!=null) {%>
					<%} else { %>
					<label>Aucun utilisateur n'a enchéri</label>
					<%} %>
				</div>	
				<div class="vente-fields">				
					<label>Fin de l'enchère : <%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></label>
					<label>Retrait : <%=r.getRue() + " " +r.getCodePostal() + " " + r.getVille() %></label>
				</div>			
				</div>
			</div>
		</div>
		<% if (av.getArticleStatus() == ArticleStatus.EC && (int)av.getNoUtilisateur() !=  (Integer)session.getAttribute("noUtilisateur") && session.getAttribute("desactive") == null) {%>
			<form action="/ENI-enchere/DetailVente/<%=av.getNoArticle() %>" method="POST">				
				<div class="vente-container">	
					<div class="detail-vente-field">						
						<label for="proposition">Ma proposition</label>
						<input id="proposition" name="proposition" type="number" value="<%=av.getPrixVente()+1%>">
					</div>					
						<p class="error"><%=request.getAttribute("erreurMessage")!=null?request.getAttribute("erreurMessage"):""%></p>
					<button type="submit">Enchérir</button>
				</div>
			</form>
		<%} %>
		
		<div class="history">
			<h3>Historique des offres :</h3>
			<ul class="history-ul">			
				<% 
				for(Enchere enchere : encheres) {
					%>
					<li><%=enchere.getDateEnchere().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")) %> : <%=enchere.getMontantEnchere() %> <div class="icon"><img src="../img/eni-coin.png"></div></li>
					<%
				}
				%>
			</ul>
		</div>
	</main>

</body>
</html>