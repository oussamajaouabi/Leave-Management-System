package models;

public enum StatusRequest {
	EN_COURS("En Cours"),
	VALIDE("Validé"),
	REFUSE("Refusé");

	private final String libelle;

	StatusRequest(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}