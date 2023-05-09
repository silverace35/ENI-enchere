<%@page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profil | ENI-enchere</title>
</head>
<body>
	<%
		Utilisateur u = (Utilisateur)request.getAttribute("utilisateur");
	%>
	
	<p><%=u.getPseudo() %></p>
	<p><%=u.getNom() %></p>
	<p><%=u.getPrenom() %></p>
	<p><%=u.getEmail() %></p>
	<p><%=u.getTelephone() %></p>
	<p><%=u.getRue() %></p>
	<p><%=u.getCodePostal() %></p>
	<p><%=u.getVille() %></p>
	<p><%=u.getMotDePasse() %></p>
	<p><%=u.getCredit() %></p>
	<p><%=u.getAdministrateur() %></p>
	
	<a href="/UpdateProfil">Modifier</a>
	
</body>
</html>