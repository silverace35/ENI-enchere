package fr.eni.enchere.controller;

public enum ErrorCodes {
	IDORPASSWORD("Identifiant ou mot de passe incorrect"),
	PSEUDO("Caractères alphanumériques requis.", "^[\\w-]{2,30}$"),
	NOM("Caractères alphanumériques requis.", "^[\\p{L}- \\']{2,30}$"),
	PRENOM("Caractères alphanumériques requis.", "^[a-z A-Z\\'\\-]{2,30}$"),
	EMAIL("Type de format possible xxxx-xxxx@xx-xx.xxx.", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
	TEL("+33123123112 ou 0110203040.", "(^\\+{1}+[3]{2}+[0-9]{9}$)|(^0{1}+[0-9]{9}$)"),
	RUE("Caractères alphanumériques requis.", "^(?=.*[\\p{L}- 0-9.]).{2,30}$"),
	CODEPOSTAL("Code postal incorrect.", "^[0-9]{5}$"),
	VILLE("Caractères alphanumériques requis.", "^[\\p{L}\\'\\-]{2,50}$"),
	PWDUSER("8 caractères min. (au moins 1 majuscule, 1 chiffre, 1 caractère spécial)", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+$).{8,30}$"),
	CONFPWDUSER("8 caractères min. (au moins 1 majuscule, 1 chiffre, 1 caractère spécial)", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+$).{8,30}$"),
	PASSWORDMISSMATCH("Les mots de passe ne correspondent pas."),
	PSEUDO_OR_EMAIL_ALREADY_EXIST("Le pseudo ou l'email existe déjà."),
	PSEUDO_ALREADY_EXIST("Le pseudo existe déjà."),
	EMAIL_ALREADY_EXIST("l'email existe déjà."),
	SQL_ERROR("Connexion à la base impossible."),
	DESCRIPTION("Caractères non autorisés.", "^(?=.*[\\p{L}- 0-9.:!%]).{2,300}$"),
	PRIX_INITIAL("Saisie invalide."),
	DATES_IMP("La date de fin doit être postérieure à celle de début."),
	DATETIMEFORMAT("Format de date invalide aaaa-mm-jj hh:mm","")
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