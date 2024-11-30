package models;

public enum LeaveType {
	MATERNITE("Maternité"),
	MALADIE("Maladie"),
	ANNUEL("Annuel"),
	PAYE("Payé"),
	LONGUE_DUREE("Longue Durée"),
	AUTRE("Autre");

	private final String libelle;

	LeaveType(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}