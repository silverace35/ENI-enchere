<!DOCTYPE html>
<html>

<head>
<%@ include file="head.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/settings.css"%>">
<link rel="stylesheet" href="<%=request.getContextPath()+"/css/login.css"%>">
<title>Modifier vente | ENI-enchere</title>
</head>

<jsp:include page="./coreVente.jsp">
	<jsp:param name="ServletCible" value="/ENI-enchere/ModifierVente" />
	<jsp:param name="typeVente" value="Modifier ma vente" />
</jsp:include>

<form action="<%=request.getContextPath()%>/AnnulerVente" method="POST">
<button type="submit">Annuler la vente</button>
</form>

<a class="secondary-link" href="/ENI-enchere">Annuler</a>
</main>

</body>

</html>

