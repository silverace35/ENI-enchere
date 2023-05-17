<jsp:include page="./coreVente.jsp">
	<jsp:param name="ServletCible" value="/ENI-enchere/ModifierVente" />
</jsp:include>


<button type="submit">Enregistrer</button>
</form>

<form action="/ENI-enchere/AnnulerArticle" method="POST">
<button type="submit">Annuler la vente</button>
</form>

<a class="secondary-link" href="/ENI-enchere">Annuler</a>
</main>

</body>

</html>

