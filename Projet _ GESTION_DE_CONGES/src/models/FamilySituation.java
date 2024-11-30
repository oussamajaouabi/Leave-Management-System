package models;

public enum FamilySituation {
	CELIBATAIRE("Célibataire"),
	MARIE("Marié"),
	DIVORCE("Divorcé"),
	VEUF("Veuf");

	private final String libelle;

	FamilySituation(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}