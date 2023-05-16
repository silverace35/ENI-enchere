<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="connexionTest.jsp"%>
<div class="bandeau"></div>
<nav role="navigation">
	<h1>
		<a href="/ENI-enchere">ENI-Enchères</a>
	</h1>
	<ul id="desktop-menu">
		<%
			if (isConnected) {
			%>
			<li class="menu-links" id="hidden"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="/ENI-enchere/AjoutArticle">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil/<%=session.getAttribute("noUtilisateur") %>">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			} else {
			%>
			<li class="menu-links"><a href="ServletInscription">S'inscrire</a></li>
			<li class="menu-links"><a href="ServletConnexion">Se connecter</a></li>
			<%
			}
			%>
	</ul>
	<div id="menuToggle">
		<input type="checkbox" /> 
		<span></span> 
		<span></span> 
		<span></span>
		<ul id="menu">
			<%

			if (isConnected) {
			%>
			<li id="hidden"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="/ENI-enchere/AjoutArticle">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil/<%=session.getAttribute("noUtilisateur") %>">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			} else {
			%>
			<li class="menu-links"><a href="ServletInscription">S'inscrire</a></li>
			<li class="menu-links"><a href="ServletConnexion">Se connecter</a></li>
			<%
			}
			%>
		</ul>
	</div>
</nav>