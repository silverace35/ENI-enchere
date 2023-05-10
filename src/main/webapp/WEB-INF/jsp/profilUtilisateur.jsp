<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp" %>
<title>Profil | ENI-enchere</title>
</head>
<body>
<%Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");%>
<%@ include file="navigation.jsp" %>

	<main>
		<table class="rwd-table">
		  <tr>
		    <td data-th="Pseudo "><%=u.getPseudo() %></td>
		    <td data-th="Nom "><%=u.getNom() %></td>
		    <td data-th="Prénom "><%=u.getPrenom() %></td>
		    <td data-th="E-Mail "><%=u.getEmail() %></td>
		     <td data-th="Téléphone "><%=u.getTelephone() %></td>
		    <td data-th="Rue "><%=u.getRue() %></td>
		    <td data-th="Code postal "><%=u.getCodePostal() %></td>
		    <td data-th="Ville "><%=u.getVille() %></td>
		  </tr>
		</table>
	
		<a class="secondary-link" href="/ENI-enchere/ServletModifierProfil">Modifier</a>
	</main>

</body>
</html>