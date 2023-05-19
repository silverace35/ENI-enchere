package fr.eni.enchere.bo;

public enum ArticleStatus {
	CR("bientôt ouverte", "cr"), //Created
	EC("En cours", "ec"), //En cours
	ET("Enchère terminée", "et"), //Enchere terminer
	RT("Enchère terminée et close", "rt"), //Retrait effectuer
	DS("Désactivée par un administrateur", "ds");

	private String statusMessage;
	private String color;
	
	ArticleStatus(String string, String color) {
		this.statusMessage = string;
		this.color = color;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
	
	public String getColor() {
		return this.color;
	}
}
