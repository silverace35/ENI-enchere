<!DOCTYPE html>
<html>

<head>
<%@ include file="head.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/settings.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/login.css"%>">
<title>Ajout vente | ENI-enchere</title>
</head>

<jsp:include page="./coreVente.jsp">
	<jsp:param name="ServletCible" value="/ENI-enchere/AjoutArticle" />
	<jsp:param name="typeVente" value="Nouvelle vente" />
</jsp:include>


<div class="line-container">
	<div class="line"></div>
	<p>ou</p>
	<div class="line"></div>
</div>

<a class="secondary-link" href="/ENI-enchere">Annuler</a>
</main>

</body>

</html>