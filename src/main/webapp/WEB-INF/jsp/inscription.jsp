<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Creation de compte</title>
</head>


<body>
	
	<h1>ENI-Enchères</h1>
	
	<h2>Créer un compte</h2>
	
	<form class="" action="ServletInscription" method="POST">
            <div>
            	<label for="pseudo">Pseudo : </label>
	            <input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="nom">Nom : </label>
	            <input type="text" id="nom" name="nom" minlength="2" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="prenom">Prénom : </label>
	            <input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="email">Email : </label>
	            <input type="text" id="email" name="email" minlength="6" maxlength="50" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="tel">Téléphone : </label>
	            <input type="text" id="tel" name="tel" minlength="10" maxlength="15" required 
	            placeholder="Obligatoire" />
            </div>
             <div>
             	
             	<label for="rue">Rue : </label>
	            <input type="text" id="rue" name="rue" minlength="10" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="codePostal">Code Postal : </label>
	            <input type="text" id="codePostal" name="codePostal" minlength="5" maxlength="10" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="ville">Ville : </label>
	            <input type="text" id="ville" name="ville" minlength="2" maxlength="50" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="motDePasse">Mot de passe : </label>
	            <input type="password" id="motDePasse" name="motDePasse" minlength="8" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="confMotDePasse">Confirmation : </label>
	            <input type="password" id="confMotDePasse" name="confMotDePasse" minlength="8" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <Button class="" type="submit">Créer
				</button>
        </form>
            	
			<form class="" action="ServletConnexion" method="GET">
				<Button class="" type="">Annuler
				</button>
             </form>

</body>
</html>