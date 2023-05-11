<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav role="navigation">
	<h1>
		<a href="/ENI-enchere">ENI-Enchères</a>
	</h1>
	<ul id="desktop-menu">
		<%
			Cookie[] cookies = request.getCookies();
			boolean cookieLoginExist = false;

			if (session.getAttribute("noUtilisateur") != null) {
			%>
			<li class="menu-links" id="hidden"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="#">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			} else if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cookieLogin")) {
					cookieLoginExist = true;
					session.setAttribute("noUtilisateur", cookie.getValue());
			%>
			<li class="menu-links" id="hidden_link"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="#">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			}
			}
			if (cookieLoginExist == false) {
			%>
			<li class="menu-links"><a href="ServletInscription">S'inscrire</a></li>
			<li class="menu-links"><a href="ServletConnexion">Se connecter</a></li>
			<%
			}
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

			if (session.getAttribute("noUtilisateur") != null) {
			%>
			<li id="hidden"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="#">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			} else if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cookieLogin")) {
					cookieLoginExist = true;
					session.setAttribute("noUtilisateur", cookie.getValue());
			%>
			<li class="menu-links" id="hidden_link"><a href="#">Enchères</a></li>
			<li class="menu-links"><a href="#">Vendre un article</a></li>
			<li class="menu-links"><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
			<li class="menu-links"><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
			<%
			}
			}
			if (cookieLoginExist == false) {
			%>
			<li class="menu-links"><a href="ServletInscription">S'inscrire</a></li>
			<li class="menu-links"><a href="ServletConnexion">Se connecter</a></li>
			<%
			}
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