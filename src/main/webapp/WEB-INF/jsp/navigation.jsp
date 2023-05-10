<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav>
	<h1>
		<a href="/ENI-enchere">ENI-Enchères</a>
	</h1>
	<ul>
		<% 
		Cookie[] cookies = request.getCookies();
		boolean cookieLoginExist = false;
		
		if(session.getAttribute("noUtilisateur") != null ) {
			%>
			<li><a href="#">Enchères</a></li>
			<li><a href="#">Vendre un article</a></li>
			<li><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
			<li><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li> 
			<% } else if (cookies != null){
				
				for (Cookie cookie : cookies) { 
					if (cookie.getName().equals("cookieLogin")) {
						cookieLoginExist = true;
						session.setAttribute("noUtilisateur", cookie.getValue());
						%>
						<li><a href="#">Enchères</a></li>
						<li><a href="#">Vendre un article</a></li>
						<li><a href="/ENI-enchere/InfosProfil">Mon profil</a></li>
						<li><a href="/ENI-enchere/ServletDeconnexion">Déconnexion</a></li>
				<% 	}
				}
				if (cookieLoginExist == false) {
					%>
					<li><a href="ServletInscription">S'inscrire</a></li>
					<li><a href="ServletConnexion">Se connecter</a></li>
			<% }
			} else {
			%>
				<li><a href="ServletInscription">S'inscrire</a></li>
				<li><a href="ServletConnexion">Se connecter</a></li>
		<% } %> 
	</ul>
</nav>