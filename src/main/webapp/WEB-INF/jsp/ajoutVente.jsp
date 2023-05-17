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

<<<<<<< HEAD
<button type="submit">Enregistrer</button>
</form>

<div class="line-container">
	<div class="line"></div>
	<p>ou</p>
	<div class="line"></div>
</div>
=======
>>>>>>> d1ecc24cac608e6e26884ac1134c4f8f0a908141

<a class="secondary-link" href="/ENI-enchere">Annuler</a>
</main>

</body>

</html>