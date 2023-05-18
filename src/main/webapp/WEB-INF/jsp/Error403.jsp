<%@page import="fr.eni.enchere.bo.Retrait"%>
<%@page import="fr.eni.enchere.bo.ArticleStatus"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.enchere.bo.Enchere"%>
<%@page import="fr.eni.enchere.bo.Categorie"%>
<%@ page import="fr.eni.enchere.bo.ArticleVendu"%>
<%@ page import="fr.eni.enchere.bo.Utilisateur"%>
<%@ page import="java.time.format.DateTimeFormatter" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="head.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/settings.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/login.css"%>">
<title>403 | ENI-enchere</title>
</head>
<body>
<%@ include file="navigation.jsp"%>

	<main>
		<h2>FORBIDDEN 403</h2>
		<h4>Désolé cette resource n'est pas accessible !</h4>
	</main>

</body>
</html>