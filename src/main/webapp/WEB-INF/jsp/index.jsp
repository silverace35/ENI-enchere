<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
         <%@ include file="head.jsp" %>
        <link rel="stylesheet" href="css/settings.css">
        <link rel="stylesheet" href="css/index.css">
        <title>Accueil | ENI-enchere</title>
    </head>
    
    <%-- <% Enchere e = (Enchere)request.getAttribute("enchere"); %> --%>
	

    <body>
        <%@ include file="navigation.jsp" %>

        <main>

            <h2>Rechercher une enchère</h2>

            <form action="<%=request.getContextPath()%>/ServletIndex" method="GET">
	            <div class="search-container">            
	                <img class="icon" src="img/recherche.png">
	                <input name="barreRecherche" type="search" required/>
	            </div>
    
                <div class="select-container">
                    <label for="categorie">Catégorie : </label>
                    <select name="categorie" id="categorie">
                        <option value="1">Toutes</option>
                        <option value="2">Informatique</option>
                        <option value="3">Ameublement</option>
                        <option value="4">Vêtement</option>
                        <option value="5">Sport & Loisirs</option>
                    </select>
                </div>
                
                <div class="radio-container">
                	<div class="achats-container">
                		<input name="radio" id="radio-achat" type="radio">
                		<label for="radio-achat">Achats</label>
                		
                		<input id="enchere-ouverte" type="checkbox">
                		<label for="enchere-ouverte">Enchères ouvertes</label>
                		
                		<input id="mes-encheres"type="checkbox">
                		<label for="mes-encheres">Mes enchères</label>
                		
                		<input id="enchere-remportees"type="checkbox">
                		<label for="enchere-remportees">Mes enchères remportées</label>
                	</div>
                	<div class="ventes-container">
                		<input name="radio" id="radio-vente" type="radio">
                		<label for="radio-vente">Mes ventes</label>
                		
                		<input id="vente-cours" type="checkbox">
                		<label for="vente-cours">Mes ventes en cours</label>
                		
                		<input id="vente-debutees"type="checkbox">
                		<label for="vente-debutees">ventes non débutées</label>
                		
                		<input id="vente-terminees"type="checkbox">
                		<label for="vente-terminees">ventes terminées</label>
                	</div>
                </div>
                
    
                <button class="form-btn" type="submit">Rechercher une enchère</button>
            </form>

            <div class="list-encheres">
            	<div class="enchere">
	             	<div class="img-enchere"><img src="https://source.unsplash.com/random/300×300"></div>
	             	<div class="details-enchere">
	             		<h3><a href="#">Titre enchere</a></h3>
	             		<p>Prix enchere</p>
	             		<p>date fin enchere</p>
	             		<p><a href="#">nom vendeur</a></p>
	             	</div>
            	</div>
            </div> 
                
        </main>

    </body>

    </html>