<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifier mon profil | ENI-enchere</title>
</head>
<body>

	<nav>
		<a href="profil.jsp">ENI-Enchères</a>
	</nav>

	<form action="<%=request.getContextPath()%>/ServletModifierProfil"
		method="POST">
		<label for="pseudo">Pseudo :</label> 
		<input id="pseudo" name="pseudo" type="text"> 
		
		<label for="nom">Nom :</label> 
		<input id="nom" name="nom" type="text"> 
		
		<label for="prenom">Prénom :</label> 
		<input id="prenom" name="prenom" type="text"> 
		
		<label for="email">Email :</label> 
		<input id="email" name="email" type="email"> 
		
		<label for="tel">Téléphone :</label> 
		<input id="tel" name="tel" type="number"> 
		
		<label for="rue">Rue :</label> 
		<input id="rue" name="rue" type="text"> 
		
		<label for="codePostal">Code Postal :</label> 
		<input id=""codePostal"" name=""codePostal"" type="number"> 
		
		<label for="ville">Ville :</label> 
		<input id="ville" name="ville" type="text"> 
		
		<label for="motDePasse">Mot de passe :</label> 
		<input id="motDePasse" name="motDePasse" type="password"> 
		
		<label for="confMotDePasse">Confirmation :</label> 
		<input id="confMotDePasse" name="confMotDePasse" type="password"> 
		
		<p>Crédit :</p> 
		<p>[nbr crédit]</p> 
		
		
		<button type="submit">Enregistrer</button>
		<button>Supprimer mon compte</button>
		<a href="ServletProfil">Retour</a>
	</form>

</body>
</html>