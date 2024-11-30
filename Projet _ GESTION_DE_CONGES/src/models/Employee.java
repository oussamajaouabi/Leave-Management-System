package models;

import java.util.Date;

public class Employee {
	private int employeId;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private FamilySituation situationFamiliale;
	private String email;
	private String telephone;
	private String adresse;
	private String cin;
	private String poste;
	private int soldeConge;
	private String motPasse;
	private Role role;
	
	private static Employee connectedUser;
	
	public Employee(int employeId, String nom, String prenom, Date dateNaissance, FamilySituation situationFamiliale,
			String email, String telephone, String adresse, String cin, String poste, int soldeConge, String motPasse, Role role) {
		this.employeId = employeId;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.situationFamiliale = situationFamiliale;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.cin = cin;
		this.poste = poste;
		this.soldeConge = soldeConge;
		this.motPasse = motPasse;
		this.role = role;
	}

	public int getEmployeId() {
		return employeId;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public FamilySituation getSituationFamiliale() {
		return situationFamiliale;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getCin() {
		return cin;
	}

	public String getPoste() {
		return poste;
	}

	public int getSoldeConge() {
		return soldeConge;
	}
	
	public String getMotPasse() {
		return motPasse;
	}
	
	public Role getRole() {
		return role;
	}
	
	public static Employee getConnectedUser() {
        return connectedUser;
    }

	public void setEmployeId(int employeId) {
		this.employeId = employeId;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setSituationFamiliale(FamilySituation situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public void setSoldeConge(int soldeConge) {
		this.soldeConge = soldeConge;
	}
	
	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

    public static void setConnectedUser(Employee employee) {
    	connectedUser = employee;
    }

    public static void clearConnectedUser() {
    	connectedUser = null;
    }
}
