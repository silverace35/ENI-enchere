<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8" />
		<link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/login.css">
		<title>Se connecter | ENI-enchere</title>
	</head>

	<body>
		<nav>
			<h1><a href="/ENI-enchere">ENI-Enchères</a></h1>
			<ul></ul>
		</nav>

		<main>
			<h2>Se connecter</h2>
			<form action="<%=request.getContextPath()%>/ServletConnexion" method="POST">
				<div class="fields">			
					<div class="field">			
						<label for="identifiant">Identifiant</label>
						<input id="identifiant" name="identifiant" type="text" />
					</div>
					<div class="field">
						<label for="motDePasse">Mot de passe</label>
						<input id="motDePasse" name="motDePasse" type="password" />
					</div>
				</div>

				<div class="details-container">
					<div class="details">
						<label for="souvenir">Se souvenir de moi</label>
						<input id="souvenir" name="souvenir" type="checkbox" />
					</div>
					<a href="#">Mot de passe oublié</a>
					<!-- TODO : ajouter un message de mail envoyé lors du click du lien -->
				</div>
				<button type="submit">Connexion</button>
			</form>

			<div class="line-container">
				<div class="line"></div>
				<p>ou</p>
				<div class="line"></div>
			</div>

			<a class="secondary-link" href="ServletInscription">Créer un compte</a>
		</main>

	</body>

	</html>