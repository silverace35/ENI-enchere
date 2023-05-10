package fr.eni.enchere.controller;

public enum ErrorCodes {
	IDORPASSWORD("Identifiant ou mot de passe incorrect");

	private String message;
	
	ErrorCodes(String string) {
		this.message = string;
	}
	
	public String getMessage() {
		return this.message;
	}
}
