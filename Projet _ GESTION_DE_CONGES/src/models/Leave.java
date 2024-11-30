package models;

import java.util.Date;

public class Leave {
	private int congeId;
	private String nomConge;
	private String description;
	private Date dateDemande;
	private Date dateDebut;
	private Date dateFin;
	private int nbJours;
	private LeaveType typeConge;
	private StatusRequest etatDemande;
	private int employeId;
	
	public Leave(int congeId, String nomConge, String description, Date dateDemande, Date dateDebut, Date dateFin,
			int nbJours, LeaveType typeConge, StatusRequest etatDemande, int employeId) {
		this.congeId = congeId;
		this.nomConge = nomConge;
		this.description = description;
		this.dateDemande = dateDemande;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nbJours = nbJours;
		this.typeConge = typeConge;
		this.etatDemande = etatDemande;
		this.employeId = employeId;
	}

	public int getCongeId() {
		return congeId;
	}

	public String getNomConge() {
		return nomConge;
	}

	public String getDescription() {
		return description;
	}

	public Date getDateDemande() {
		return dateDemande;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public int getNbJours() {
		return nbJours;
	}

	public LeaveType getTypeConge() {
		return typeConge;
	}

	public StatusRequest getEtatDemande() {
		return etatDemande;
	}

	public int getEmployeId() {
		return employeId;
	}

	public void setCongeId(int congeId) {
		this.congeId = congeId;
	}

	public void setNom(String nomConge) {
		this.nomConge = nomConge;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public void setNbJours(int nbJours) {
		this.nbJours = nbJours;
	}

	public void setTypeConge(LeaveType typeConge) {
		this.typeConge = typeConge;
	}

	public void setEtatDemande(StatusRequest etatDemande) {
		this.etatDemande = etatDemande;
	}

	public void setEmployeId(int employeId) {
		this.employeId = employeId;
	}
}
