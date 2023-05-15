package fr.eni.enchere.bo;

public enum ArticleStatus {
	CR("Pas encore demarrer"), //Created
	EC("En cours"), //En cours
	ET("Enchere terminer"), //Enchere terminer
	RT("Enchere terminer et clot"), //Retrait effectuer
	DS("Desactiver par un administrateur"),;

	private String statusMessage;
	
	ArticleStatus(String string) {
		this.statusMessage = string;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
}
