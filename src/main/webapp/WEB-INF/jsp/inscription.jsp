<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/login.css">
		<title>Creation de compte | ENI-enchere</title>
	</head>

	<body>
		<nav>
			<h1><a href="/ENI-enchere">ENI-Enchères</a></h1>
			<ul></ul>
		</nav>

		<main>
			<h2>Créer un compte</h2>

			<form class="" action="ServletInscription" method="POST">
				<div class="fields">
					<div class="field">			
						<label for="pseudo">Pseudo : </label>
						<input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required/>
					</div>
	
					<div class="field">
						<label for="nom">Nom : </label>
						<input type="text" id="nom" name="nom" minlength="2" maxlength="30" required/>				
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<label for="prenom">Prénom : </label>
						<input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required/>
					</div>
					
					<div class="field">
						<label for="email">Email : </label>
						<input type="text" id="email" name="email" minlength="6" maxlength="50" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="tel">Téléphone : </label>
						<input type="text" id="tel" name="tel" minlength="10" maxlength="15" required/>
					</div>
					<div class="field">
						<label for="rue">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="10" maxlength="30" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="codePostal">Code Postal : </label>
						<input type="text" id="codePostal" name="codePostal" minlength="5" maxlength="10" required/>
					</div>
					<div class="field">
						<label for="ville">Ville : </label>
						<input type="text" id="ville" name="ville" minlength="2" maxlength="50" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="motDePasse">Mot de passe : </label>
						<input type="password" id="motDePasse" name="motDePasse" minlength="8" maxlength="30" required/>
					</div>
					<div class="field">
						<label for="confMotDePasse">Confirmation : </label>
						<input type="password" id="confMotDePasse" name="confMotDePasse" minlength="8" maxlength="30" required/>
					</div>
				</div>
				
				<Button type="submit">Créer mon compte</button>
			</form>

			<div class="line-container">
				<div class="line"></div>
				<p>ou</p>
				<div class="line"></div>
			</div>

			<a class="secondary-link" href="/ENI-enchere">Annuler</a>
		</main>

	</body>

	</html>