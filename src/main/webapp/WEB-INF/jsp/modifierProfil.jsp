<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/login.css">
		<title>Modification de profil | ENI-enchere</title>
	</head>

	<body>
		<nav>
			<h1><a href="ServletProfil">ENI-Enchères</a></h1>
			<ul></ul>
		</nav>

		<main>
			<h2>Modifier mes informations</h2>

			<form action="ServletModifierProfil" method="POST">
				<div class="fields">
					<div class="field">			
						<label for="pseudo">Pseudo : </label>
						<input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required/>
					</div>
	
					<div class="field">
						<label for="pseudo">Nom : </label>
						<input type="text" id="nom" name="nom" minlength="2" maxlength="30" required/>				
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<label for="pseudo">Prénom : </label>
						<input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required/>
					</div>
					
					<div class="field">
						<label for="pseudo">Email : </label>
						<input type="text" id="email" name="email" minlength="6" maxlength="50" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="pseudo">Téléphone : </label>
						<input type="text" id="telephone" name="telephone" minlength="10" maxlength="15" required/>
					</div>
					<div class="field">
						<label for="pseudo">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="10" maxlength="30" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="pseudo">Code Postal : </label>
						<input type="text" id="code_postal" name="code_postal" minlength="5" maxlength="10" required/>
					</div>
					<div class="field">
						<label for="pseudo">Ville : </label>
						<input type="text" id="ville" name="ville" minlength="2" maxlength="50" required/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="pseudo">Mot de passe : </label>
						<input type="password" id="mdp" name="mdp" minlength="8" maxlength="30" required/>
					</div>
					<div class="field">
						<label for="pseudo">Confirmation : </label>
						<input type="password" id="mdp_confirm" name="mdp_confirm" minlength="8" maxlength="30" required/>
					</div>
				</div>
				
				<p>Crédit : 640</p>

				<button type="submit">Enregistrer</button>
			</form>

			<div class="line-container">
				<div class="line"></div>
				<p>ou</p>
				<div class="line"></div>
			</div>

			<a class="secondary-link" href="ServletIndex">Supprimer mon compte</a>
		</main>

	</body>

	</html>