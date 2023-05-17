<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni.enchere.controller.ErrorCodes"%>
<%@page import="java.util.List"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<body>
	
	
	<%@ include file="navigation.jsp"%>	

	<main>
		<h2>Nouvelle vente</h2>
		<form action=<%=request.getParameter("ServletCible")%> method="POST">

<%
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	String rue = request.getAttribute("rue")==null?"":(String) request.getAttribute("rue");
	String codePostal = request.getAttribute("codePostal")==null?"":(String) request.getAttribute("codePostal");
	String ville = request.getAttribute("ville")==null?"":(String) request.getAttribute("ville");
	String nomArticle = request.getAttribute("nomArticle")==null?"":(String) request.getAttribute("nomArticle");
	Integer categorie = request.getAttribute("categorie")==null?1:(Integer) request.getAttribute("categorie");
	String description = request.getAttribute("description")==null?"":(String) request.getAttribute("description");
	Integer prixInitial = request.getAttribute("prixInitial")==null?0:(Integer) request.getAttribute("prixInitial");

	List<ErrorCodes> lstPara = (List<ErrorCodes>) request.getAttribute("lstParam");
	List<Categorie> listCategories = (List<Categorie>) session.getAttribute("listCategories");
	LocalDateTime now= LocalDateTime.now();
	if (lstPara == null) {
		lstPara = new ArrayList<>();
	}
	%>
			<div class="field">
				<label for="nomArticle">Article : </label> <input type="text"
					id="nomArticle" name="nomArticle" minlength="2" maxlength="30"
					required
					value="<%=lstPara.contains(ErrorCodes.RUE) ? 
						"": request.getParameter("nomArticle") == null ? 
						nomArticle : request.getParameter("nomArticle")%>"
					placeholder="<%=lstPara.contains(ErrorCodes.RUE) ? 
							ErrorCodes.RUE.getMessage() : ""%>" />
			</div>


			<div class="field">
				<div class="select-container">
					<label for="categorie">Catégorie : </label> <select
						name="categorie" id="categorie">
						<%
						
						for (Categorie c : listCategories) {
						%>
						<option value="<%=c.getNoCategorie()%>" <%=c.getNoCategorie()==categorie?"selected":"" %>><%=c.getLibelle()%> </option>
						<%
						}
						%>
					</select>
				</div>
			</div>

			<div class="field">
				<label for="description">Description : </label> <input
					type="textarea" id="description" name="description" minlength="10"
					maxlength="300" required
					value="<%=lstPara.contains(ErrorCodes.DESCRIPTION) ? 
							"": request.getParameter("description") == null ? 
									description : request.getParameter("description")%>"
					placeholder="<%=lstPara.contains(ErrorCodes.DESCRIPTION) ? 
							ErrorCodes.DESCRIPTION.getMessage() : ""%>" />
			</div>

			<div class="fields">
				<div class="field">
					<p>Photo de l'article</p>

				</div>
			</div>
			<div class="fields">
				<div class="field">
					<Button>Uploader</Button>
				</div>
				<div class="field">
					<img style="height: 300px"
						src="https://source.unsplash.com/random/100×100/?code">
				</div>
			</div>

			<div class="fields">
				<div class="select-container">
					<label for="prixInitial">Prix initial : </label> <input
						id="prixInitial" type="number" name="prixInitial" min="0"
						value="<%=lstPara.contains(ErrorCodes.PRIX_INITIAL) ? 
								"": request.getParameter("prixInitial") == null ? 
										prixInitial : request.getParameter("prixInitial")%>"
						placeholder="<%=lstPara.contains(ErrorCodes.PRIX_INITIAL) ? 
								ErrorCodes.PRIX_INITIAL.getMessage() : ""%>" />
				</div>

				<div class="select-container">
					<label for="dateDebutEncheres">Début de l'enchère </label> <input
						type="datetime-local" id="dateDebutEncheres"
						name="dateDebutEncheres"
						value="<%=request.getParameter("dateDebutEncheres") == null ? 
								(request.getAttribute("dateDebutEncheres")==null ? LocalDateTime.now().format(formatter) 
								:request.getAttribute("dateDebutEncheres"))
								 : request.getParameter("dateDebutEncheres")%>" 
								required/>

				</div>

				<div class="select-container">
					<label for="dateFinEncheres">Fin de l'enchère </label> <input
						type="datetime-local" id="dateFinEncheres" name="dateFinEncheres"
						value="<%=lstPara.contains(ErrorCodes.DATES_IMP) ? 
								"": request.getParameter("dateFinEncheres") == null ? 
										request.getAttribute("dateFinEncheres") : request.getParameter("dateFinEncheres")%>"
								required/>

				</div>
				<p><%=lstPara.contains(ErrorCodes.DATES_IMP) ? 
						ErrorCodes.DATES_IMP.getMessage() : ""%></p>
			</div>

			<div class="field">
				<label for="rue">Rue : </label> <input type="text" id="rue"
					name="rue" minlength="2" maxlength="30" required
					value="<%=lstPara.contains(ErrorCodes.RUE) ? 
							"": request.getParameter("rue") == null ? 
									rue : request.getParameter("rue")%>"
					placeholder="<%=lstPara.contains(ErrorCodes.RUE) ? 
							ErrorCodes.RUE.getMessage() : ""%>" />
			</div>
			<div class="field">
				<label for="codePostal">Code postal : </label> <input type="text"
					id="codePostal" name="codePostal" minlength="2" maxlength="10"
					required
					value="<%=lstPara.contains(ErrorCodes.CODEPOSTAL) ? 
							"": request.getParameter("codePostal") == null ? 
									codePostal : request.getParameter("codePostal")%>"
					placeholder="<%=lstPara.contains(ErrorCodes.CODEPOSTAL) ? 
							ErrorCodes.CODEPOSTAL.getMessage() : ""%>" />
			</div>
			<div class="field">
				<label for="ville">Ville : </label> <input type="text" id="ville"
					name="ville" minlength="2" maxlength="50" required
					value="<%=lstPara.contains(ErrorCodes.VILLE) ? 
							"": request.getParameter("ville") == null ? 
									ville : request.getParameter("ville")%>"
					placeholder="<%=lstPara.contains(ErrorCodes.VILLE) ? 
							ErrorCodes.VILLE.getMessage() : ""%>" />
			</div>
			
			
<button type="submit">Enregistrer</button>
</form>