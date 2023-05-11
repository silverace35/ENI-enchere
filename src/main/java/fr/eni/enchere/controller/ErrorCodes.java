package fr.eni.enchere.controller;

public enum ErrorCodes {
	IDORPASSWORD("Identifiant ou mot de passe incorrect"),
	PSEUDO("Caractères alphanumériques requis.", "^[\\w-]{2,30}$"),
	NOM("Caractères alphanumériques requis.", "^[a-z A-Z\\'\\-]{2,30}$"),
	PRENOM("Caractères alphanumériques requis.", "^[a-z A-Z\\'\\-]{2,30}$"),
	EMAIL("Type de format possible xxxx-xxxx@xxxx.xxx.", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
	TEL("+33123123112 ou 0110203040", "(^\\+{1}+[3]{2}+[0-9]{9}$)|(^0{1}+[0-9]{9}$)"),
	RUE("Caractères alphanumériques requis.", "^[0-9 a-z A-Z\\'\\-]{2,30}$"),
	CODEPOSTAL("Code postal incorrect.", "^[0-9]{5}$"),
	VILLE("Caractères alphanumériques requis.", "^[a-z A-Z\\'\\-]{2,50}$"),
	PWDUSER("Format de mot de passe incorrect.", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+$).{8,30}$"),
	CONFPWDUSER("Format de mot de passe incorrect.", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,30}$"),
	PASSWORDMISSMATCH("Les mots de passe ne correspondent pas."),
	PSEUDO_OR_EMAIL_ALREADY_EXIST("Le pseudo ou l'email existe déjà."),
	SQL_ERROR("Connexion à la base impossible.")
	;

	private String message;
	private String pattern;
	
	ErrorCodes(String string) {
		this.message = string;
		this.pattern = null;
	}
	
	ErrorCodes(String string, String pattern) {
		this.message = string;
		this.pattern = pattern;
	}
	
	public String getMessage() {
		return this.message;
	}

	public String getPattern() {
		return pattern;
	}
}