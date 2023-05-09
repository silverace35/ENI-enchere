<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Se connecter | ENI-enchere</title>
</head>
<body>
	<nav>	
		<a href="profil.jsp">ENI-Enchères</a>
	</nav>
	
	<form action="<%=request.getContextPath()%>/ServletConnexion" method="POST">
		<label for="identifiant">Identifiant</label>
		<input id="identifiant" name="identifiant" type="text">
		
		<label for="motDePasse">Mot de passe</label>
		<input id="motDePasse" name="motDePasse" type="password">
		
		<div class="conn-details">		
			<button type="submit">Connexion</button>
			<label for="souvenir">Se souvenir de moi</label>
			<input id="souvenir" name="souvenir" type="checkbox">
			<a href="#">Mot de passe oublié</a>
			<!-- TODO : ajouter un message de mail envoyé lors du click du lien -->
		</div>
	</form>
	
	<a href="ServletInscription">Créer un compte</a>

</body>
</html>