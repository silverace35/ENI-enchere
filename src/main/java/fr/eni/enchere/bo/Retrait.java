package fr.eni.enchere.bo;

import java.io.Serializable;

public class Retrait implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer noRetrait;
	private String rue;
	private String codePostal;
	private String ville;
	
	public Retrait() {
	}

	public Retrait(Integer noRetrait, String rue, String codePostal, String ville) {
		super();
		this.noRetrait = noRetrait;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	public Retrait(String rue, String codePostal, String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Integer getNoRetrait() {
		return noRetrait;
	}

	public void setNoRetrait(Integer noRetrait) {
		this.noRetrait = noRetrait;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Retrait [noRetrait=");
		builder.append(noRetrait);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}
}
