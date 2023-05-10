<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>

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
						<%List<String> lstPara =(List<String>)request.getAttribute("lstParam");%>		
				<div class="fields">
					<div class="field">	
						<label for="pseudo">Pseudo : </label>
						<input type="text" id="pseudo" name="pseudo" minlength="2" maxlength="30" required
						value= "<%=lstPara==null?"":lstPara.get(0)=="true"?request.getParameter("pseudo"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(0)=="true"?"":lstPara.get(0)%>"
						
						/>
					
					</div>
	<!-- (("true").equals(lstParam.get(0))?request.getParameter("pseudo"):lstParam.get(0)) -->
					<div class="field">
						<label for="nom">Nom : </label>
						<input type="text" id="nom" name="nom" minlength="2" maxlength="30" required
						value= "<%=lstPara==null?"":lstPara.get(1)=="true"?request.getParameter("nom"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(1)=="true"?"":lstPara.get(1)%>"
						/>		
					</div>
				</div>
				<div class="fields">
					<div class="field">
						<label for="prenom">Prénom : </label>
						<input type="text" id="prenom" name="prenom" minlength="2" maxlength="30" required
						value= "<%=lstPara==null?"":lstPara.get(2)=="true"?request.getParameter("prenom"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(2)=="true"?"":lstPara.get(2)%>"
						/>
					</div>
					
					<div class="field">
						<label for="email">Email : </label>
						<input type="text" id="email" name="email" minlength="6" maxlength="50" required
						value= "<%=lstPara==null?"":lstPara.get(3)=="true"?request.getParameter("email"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(3)=="true"?"":lstPara.get(3)%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="tel">Téléphone : </label>
						<input type="text" id="tel" name="tel" minlength="10" maxlength="15" required
						value= "<%=lstPara==null?"":lstPara.get(4)=="true"?request.getParameter("tel"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(4)=="true"?"":lstPara.get(4)%>"
						/>
					</div>
					<div class="field">
						<label for="rue">Rue : </label>
						<input type="text" id="rue" name="rue" minlength="10" maxlength="30" required
						value= "<%=lstPara==null?"":lstPara.get(5)=="true"?request.getParameter("rue"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(5)=="true"?"":lstPara.get(5)%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="codePostal">Code Postal : </label>
						<input type="text" id="codePostal" name="codePostal" minlength="5" maxlength="10" required
						value= "<%=lstPara==null?"":lstPara.get(6)=="true"?request.getParameter("codePostal"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(6)=="true"?"":lstPara.get(6)%>"
						/>
					</div>
					<div class="field">
						<label for="ville">Ville : </label>
						<input type="text" id="ville" name="ville" minlength="2" maxlength="50" required
						value= "<%=lstPara==null?"":lstPara.get(7)=="true"?request.getParameter("ville"):""%>"
						placeholder= "<%=lstPara==null?"":lstPara.get(7)=="true"?"":lstPara.get(7)%>"
						/>
					</div>
				</div>
				
				<div class="fields">
					<div class="field">
						<label for="motDePasse">Mot de passe : </label>
						<input type="password" id="motDePasse" name="motDePasse" minlength="8" maxlength="30" required
						placeholder= "<%=lstPara==null?"":(lstPara.get(8)=="true"?(lstPara.get(10)=="true"?"":lstPara.get(10)):lstPara.get(8))%>"
						
						/>
					</div>
					<div class="field">
						<label for="confMotDePasse">Confirmation : </label>
						<input type="password" id="confMotDePasse" name="confMotDePasse" minlength="8" maxlength="30" required
						placeholder= "<%=lstPara==null?"":(lstPara.get(9)=="true"?(lstPara.get(10)=="true"?"":lstPara.get(10)):lstPara.get(9))%>"
						/>
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