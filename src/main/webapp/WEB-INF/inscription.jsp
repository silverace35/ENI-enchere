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
            	<label for="pseudo">Nom : </label>
	            <input type="text" id="nom" name="nom" minlength="2" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Prénom : </label>
	            <input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Email : </label>
	            <input type="text" id="email" name="email" minlength="6" maxlength="50" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Téléphone : </label>
	            <input type="text" id="telephone" name="telephone" minlength="10" maxlength="15" required 
	            placeholder="Obligatoire" />
            </div>
             <div>
             	
             	<label for="pseudo">Rue : </label>
	            <input type="text" id="rue" name="rue" minlength="10" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Code Postal : </label>
	            <input type="text" id="code_postal" name="code_postal" minlength="5" maxlength="10" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Ville : </label>
	            <input type="text" id="ville" name="ville" minlength="2" maxlength="50" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Mot de passe : </label>
	            <input type="password" id="mdp" name="mdp" minlength="8" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <div>
            	<label for="pseudo">Confirmation : </label>
	            <input type="password" id="mdp_confirm" name="mdp_confirm" minlength="8" maxlength="30" required 
	            placeholder="Obligatoire" />
            </div>
            <Button class="" type="submit">Créer
				</button>
        </form>
            	
				
				<Button class="" type="">Annuler
				</button>
            

</body>
</html>