<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/settings.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/login.css"%>">ref="css/login.css">
<title>Ajout vente | ENI-enchere</title>
</head>

<jsp:include page="./coreVente.jsp">
	<jsp:param name="ServletCible" value="/ENI-enchere/AjoutArticle" />
</jsp:include>


<a class="secondary-link" href="/ENI-enchere">Annuler</a>
</main>

</body>

</html>