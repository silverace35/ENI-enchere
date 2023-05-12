package fr.eni.enchere.bo;

import java.time.LocalDateTime;

public class Enchere {
	private Integer noEnchere;
	private LocalDateTime dateEnchere;
	private Integer montantEnchere;
	
	public Enchere() {
	}

	public Enchere(LocalDateTime dateEnchere, Integer montantEnchere) {
		super();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public Enchere(Integer noEnchere, LocalDateTime dateEnchere, Integer montantEnchere) {
		super();
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public Integer getNoEnchere() {
		return noEnchere;
	}

	public void setNoEnchere(Integer noEnchere) {
		this.noEnchere = noEnchere;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public Integer getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(Integer montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [noEnchere=");
		builder.append(noEnchere);
		builder.append(", dateEnchere=");
		builder.append(dateEnchere);
		builder.append(", montantEnchere=");
		builder.append(montantEnchere);
		builder.append("]");
		return builder.toString();
	}
}
