<%@page import="fr.eni.enchere.bo.ArticleStatus"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.enchere.bo.Retrait"%>
<%@page import="fr.eni.enchere.bo.Enchere"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
<%@ page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
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
	Retrait r = (Retrait)request.getAttribute("retrait");
	String imgLoc = request.getAttribute("imageLocation") == null? 
			("https://source.unsplash.com/random?"+av.getNomArticle()):(String)request.getAttribute("imageLocation");
	
%>

	<main>
		<%if (av.getArticleStatus() == ArticleStatus.CR) { %>
		<h2 style="color: black" class="">Enchère bientôt ouverte</h2>
		<%} %>
		<%if (av.getArticleStatus() == ArticleStatus.EC) { %>
		<h2 style="color: black" class="">Enchère en cours</h2>
		<%} %>
		<%if (av.getArticleStatus() == ArticleStatus.ET) { %>
		<h2 style="color: black" class="">Enchère terminée <%=u!=null?" gagné par "+ u.getNom()+ " " +u.getPrenom():"" %></h2>
		<%} %>
		<div class="vente">
			<div class="img vente-img">		
				<img src="<%=imgLoc%>">
			</div>
			<div class="detail-vente">
			<h1><%=av.getNomArticle() %></h1>
				<h5>(<%=c!=null?c.getLibelle():"La catégorie n'existe plus" %>)</h5>
					<p>Description : <%=av.getDescription() %></p>
				<div class="line"></div>
				<div class="fields-container">
					<div class="vente-fields">
						<label>Mise à prix : <%=av.getPrixInitial()%>
							<div class="icon">
								<img src="../img/eni-coin.png">
							</div></label>

						<%
						if (u != null) {
						%>
						<div class="name">
							Meilleure offre :
							<%=av.getPrixVente()%>
							<div class="icon">
								<img src="../img/eni-coin.png">
							</div>
							par<a href="/ENI-enchere/InfosProfil/<%=u.getNoUtilisateur()%>"><%=u.getNom()%>
								<%=u.getPrenom()%></a>
						</div>

						<%
						} else {
						%>
						<label>Aucun utilisateur n'a enchéri</label>
						<%
						}
						%>
					</div>
					<div class="vente-fields">
						<label>Fin de l'enchère : <%=av.getDateFinEncheres().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></label>
						<label>Retrait : <%=r.getRue() + " " + r.getCodePostal() + " " + r.getVille()%></label>
					</div>
				</div>
			</div>
		</div>
		<%
		if ((av.getArticleStatus() == ArticleStatus.CR
				&& (Integer) session.getAttribute("noUtilisateur") == (int) av.getNoUtilisateur())) {
		%>
		<a class="secondary-link"
			href="/ENI-enchere/ModifierVente/<%=av.getNoArticle()%>">Modifier
			la vente</a>
		<%
		}
		%>
		<% if (av.getArticleStatus() == ArticleStatus.ET && (int)av.getNoUtilisateur() ==  (Integer)session.getAttribute("noUtilisateur") && session.getAttribute("desactive") == null && !av.isRetraitOkVendeur() && !encheres.isEmpty()) {%>
			</br>
			<a class="main-link" href="/ENI-enchere/okVendeur/<%=av.getNoArticle()%>">Valider le retrait</a>
		<%} %>
		
		<% if (av.isRetraitOkAcheteur() && av.isRetraitOkVendeur() && !encheres.isEmpty()){%>
			<p>L'acheteur et vous avez validé le retrait !</p>
		<%} else if (av.isRetraitOkAcheteur() && !av.isRetraitOkVendeur() && !encheres.isEmpty()) {%>
			<p>L'acheteur à validé le retrait !</p>
		<%} else if (!av.isRetraitOkAcheteur() && av.isRetraitOkVendeur() && !encheres.isEmpty()) {%>
			<p>Vous avez validé le retrait !</p>
		<%}%>
		<div class="history">
			<%
			if (u != null) {
			%>
			<h3>Historique des offres :</h3>
			<ul class="history-ul">
				<%
				for (Enchere enchere : encheres) {
				%>
				<li><%=enchere.getDateEnchere().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm"))%>
					: <%=enchere.getMontantEnchere()%>
					<div class="icon">
						<img src="../img/eni-coin.png">
					</div></li>
				<%
				}
				%>
			</ul>
			<%
			} else {
			%>
			<div></div>
			<%
			}
			%>
		</div>
	</main>

</body>
</html>
