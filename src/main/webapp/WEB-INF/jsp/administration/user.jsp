<%@page import="java.util.List"%>
<%@page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administration | Utilisateurs</title>
<%@ include file="/WEB-INF/jsp/head.jsp"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="/ENI-enchere">ENI-enchere</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/ENI-enchere/administration">Utilisateurs</a></li>
					<li class="nav-item"><a class="nav-link" href="/ENI-enchere/administration/categorie">Catégorie</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>




	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">id</th>
				<th scope="col">pseudo</th>
				<th scope="col">nom</th>
				<th scope="col">prenom</th>
				<th scope="col-2">email</th>
				<th scope="col">telephone</th>
				<th scope="col">Credit</th>
				<th scope="col">Administrateur</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Utilisateur> listUsers = (List<Utilisateur>)request.getAttribute("listUtilisateur");
			if(listUsers != null) {
				for(Utilisateur u : listUsers) {
			%>
			<tr>
				<td><%=u.getNoUtilisateur() %></td>
				<td><%=u.getPseudo() %></td>
				<td><%=u.getNom() %></td>
				<td><%=u.getPrenom() %></td>
				<td><%=u.getEmail() %></td>
				<td><%=u.getTelephone() %></td>
				<td><%=u.getCredit() %></td>
				<td><%=u.getAdministrateur()?"oui":"non" %></td>
				<td><a href="suppressionUtilisateur/<%=u.getNoUtilisateur() %>" title="Supression"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
				  <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
				</svg></a>
				
				<%
				if(!u.getDesactive()) {
				%>
				<a href="desactivationUtilisateur/<%=u.getNoUtilisateur() %>" title="Desactivation"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill-lock" viewBox="0 0 16 16">
				  <path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm-9 8c0 1 1 1 1 1h5v-1a1.9 1.9 0 0 1 .01-.2 4.49 4.49 0 0 1 1.534-3.693C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Zm7 0a1 1 0 0 1 1-1v-1a2 2 0 1 1 4 0v1a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1h-4a1 1 0 0 1-1-1v-2Zm3-3a1 1 0 0 0-1 1v1h2v-1a1 1 0 0 0-1-1Z"/>
				</svg></a>
				<%} %>
				</td>
			</tr>
			<%
				}
			}
			%>
		</tbody>
	</table>
</body>
</html>