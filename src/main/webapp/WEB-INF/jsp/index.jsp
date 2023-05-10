<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8" />
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
                
                <ul>
                	<li></li>
                </ul>
    
                <button class="form-btn" type="submit">Rechercher une enchère</button>
            </form>

        </main>

    </body>

    </html>