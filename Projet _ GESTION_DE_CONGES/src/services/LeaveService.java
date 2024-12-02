package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import models.Leave;
import models.LeaveType;
import models.StatusRequest;
import utilities.DatabaseConfig;
import utilities.Mail;

public class LeaveService implements LeaveInterface, DatabaseConfig {
	private static Connection cnx;
    
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cnx = DriverManager.getConnection(URL, USER, PASSWORD);
			return cnx;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
			return null;
		}
	}
	
	private static String capitalize(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }
	    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	@Override
    public List<Leave> showLeavesListByEmployee(int employeId) {
        String query = "SELECT * FROM Conge WHERE employe_id = ?";
        List<Leave> leaveList = new ArrayList<>();
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, employeId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	Leave leave = new Leave(
                        rs.getInt("conge_id"),
                        rs.getString("nom_conge"),
                        rs.getString("description"),
                        rs.getDate("date_demande"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getInt("nb_jours"),
                        LeaveType.valueOf(rs.getString("type_conge")),
                        StatusRequest.valueOf(rs.getString("etat_demande")),
                        rs.getInt("employe_id")
                );
            	leaveList.add(leave);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des congés : " + e.getMessage());
        }
        return leaveList;
    }
	
	@Override
	public List<Leave> showLeavesInProgressByEmployee() {
	    String query = "SELECT * FROM Conge WHERE etat_demande = ?";
	    List<Leave> leaveList = new ArrayList<>();
	    try (Connection cnx = getConnection();
	         PreparedStatement pst = cnx.prepareStatement(query)) {
	        pst.setString(1, StatusRequest.EN_COURS.name());
	        
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            Leave leave = new Leave(
	                    rs.getInt("conge_id"),
	                    rs.getString("nom_conge"),
	                    rs.getString("description"),
	                    rs.getDate("date_demande"),
	                    rs.getDate("date_debut"),
	                    rs.getDate("date_fin"),
	                    rs.getInt("nb_jours"),
	                    LeaveType.valueOf(rs.getString("type_conge")),
	                    StatusRequest.valueOf(rs.getString("etat_demande")),
	                    rs.getInt("employe_id")
	            );
	            leaveList.add(leave);
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'affichage des congés EN_COURS : " + e.getMessage());
	    }
	    return leaveList;
	}

	@Override
	public List<Leave> searchLeave(int employeeId, String searchItem) {
		List<Leave> leaveList = new ArrayList<>();
		String query = "SELECT * FROM Conge WHERE employe_id = ? AND nom_conge like ?";
	    try (Connection cnx = getConnection();
	         PreparedStatement pst = cnx.prepareStatement(query)) {
	        pst.setInt(1, employeeId);
	        pst.setString(2, "%" + searchItem + "%");
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            Leave leave = new Leave(
	            		rs.getInt("conge_id"),
                        rs.getString("nom_conge"),
                        rs.getString("description"),
                        rs.getDate("date_demande"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getInt("nb_jours"),
                        LeaveType.valueOf(rs.getString("type_conge")),
                        StatusRequest.valueOf(rs.getString("etat_demande")),
                        rs.getInt("employe_id")
	            );
	            leaveList.add(leave);
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de la recherche du congé : " + e.getMessage());
	    }
	    return leaveList;
	}

    @Override
    public void createLeave(int employeId, String nom, String description, Date dateDebut,
                           Date dateFin, LeaveType type) throws Exception {
    	String checkBalanceQuery = "SELECT solde_conge FROM Employe WHERE employe_id = ?";
    	String insertQuery = "INSERT INTO Conge (employe_id, nom_conge, description, date_demande, date_debut, date_fin, nb_jours, type_conge, etat_demande) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	String updateBalanceQuery = "UPDATE Employe SET solde_conge = solde_conge - ? WHERE employe_id = ?";

		try (Connection cnx = getConnection();
			PreparedStatement checkBalancePst = cnx.prepareStatement(checkBalanceQuery);
			PreparedStatement insertPst = cnx.prepareStatement(insertQuery);
			PreparedStatement updatePst = cnx.prepareStatement(updateBalanceQuery)) {
			
			long differenceInMillis = dateFin.getTime() - dateDebut.getTime();
			int nbJours = (int) (differenceInMillis / (1000 * 60 * 60 * 24)) + 1;
			
			checkBalancePst.setInt(1, employeId);
	        ResultSet rs = checkBalancePst.executeQuery();

	        if (rs.next()) {
	            int currentBalance = rs.getInt("solde_conge");
	            if (currentBalance - nbJours < 0) {
	                throw new Exception("Solde de congés insuffisant. Votre solde actuel est de " + currentBalance + " jours.");
	            }
	        } else {
	            throw new Exception("Employé introuvable.");
	        }
	        
			insertPst.setInt(1, employeId);
			insertPst.setString(2, nom);
			insertPst.setString(3, description);
			insertPst.setDate(4, new java.sql.Date(new Date().getTime())); // Date actuelle
			insertPst.setDate(5, new java.sql.Date(dateDebut.getTime()));
			insertPst.setDate(6, new java.sql.Date(dateFin.getTime()));
			insertPst.setInt(7, nbJours);
			insertPst.setString(8, type.name());
			insertPst.setString(9, String.valueOf(StatusRequest.EN_COURS));
			insertPst.executeUpdate();
			
			updatePst.setInt(1, nbJours);
			updatePst.setInt(2, employeId);
			updatePst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la création du congé : " + e.getMessage());
		}
    }

    @Override
    public void updateLeave(int congeId, String nomConge, String description, Date dateDebut, Date dateFin, LeaveType typeConge) throws Exception {
        String queryCheckEtat = "SELECT etat_demande, nb_jours, employe_id FROM Conge WHERE conge_id = ?";
        String queryUpdateLeave = "UPDATE Conge SET nom_conge = ?, description = ?, date_debut = ?, date_fin = ?, nb_jours = ?, type_conge = ? WHERE conge_id = ?";
        String queryUpdateSolde = "UPDATE Employe SET solde_conge = solde_conge + ? WHERE employe_id = ?";

        try (Connection cnx = getConnection()) {
            try (PreparedStatement pstCheck = cnx.prepareStatement(queryCheckEtat)) {
                pstCheck.setInt(1, congeId);
                ResultSet rs = pstCheck.executeQuery();

                if (rs.next()) {
                    String statusRequest = rs.getString("etat_demande");
                    int nbJoursAncien = rs.getInt("nb_jours");
                    int employeeId = rs.getInt("employe_id");

                    if (!statusRequest.equals(StatusRequest.EN_COURS.name())) {
                        throw new Exception("Seules les demandes de congé avec l'état \"En cours\" peuvent être modifiées.");
                    }

                    long differenceInMillis = dateFin.getTime() - dateDebut.getTime();
                    int nbJoursNouveau = (int) (differenceInMillis / (1000 * 60 * 60 * 24)) + 1;

                    if (nbJoursNouveau != nbJoursAncien) {
                        int differenceJours = nbJoursAncien - nbJoursNouveau;
                        try (PreparedStatement pstUpdateSolde = cnx.prepareStatement(queryUpdateSolde)) {
                            pstUpdateSolde.setInt(1, differenceJours);
                            pstUpdateSolde.setInt(2, employeeId);
                            pstUpdateSolde.executeUpdate();
                        }
                    }

                    try (PreparedStatement pstUpdate = cnx.prepareStatement(queryUpdateLeave)) {
                        pstUpdate.setString(1, nomConge);
                        pstUpdate.setString(2, description);
                        pstUpdate.setDate(3, new java.sql.Date(dateDebut.getTime()));
                        pstUpdate.setDate(4, new java.sql.Date(dateFin.getTime()));
                        pstUpdate.setInt(5, nbJoursNouveau);
                        pstUpdate.setString(6, typeConge.name());
                        pstUpdate.setInt(7, congeId);
                        pstUpdate.executeUpdate();
                    }
                } else {
                    throw new Exception("Aucune demande de congé trouvée avec cet ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la demande de congé : " + e.getMessage());
        }
    }

    @Override
    public void deleteLeave(int congeId) {
        String selectQuery = "SELECT nb_jours, employe_id, etat_demande FROM Conge WHERE conge_id = ?";
        String deleteQuery = "DELETE FROM Conge WHERE conge_id = ?";
        String updateBalanceQuery = "UPDATE Employe SET solde_conge = solde_conge + ? WHERE employe_id = ?";

        try (Connection cnx = getConnection();
             PreparedStatement selectPst = cnx.prepareStatement(selectQuery);
             PreparedStatement deletePst = cnx.prepareStatement(deleteQuery);
             PreparedStatement updatePst = cnx.prepareStatement(updateBalanceQuery)) {
             
            selectPst.setInt(1, congeId);
            ResultSet rs = selectPst.executeQuery();

            if (rs.next()) {
                int nbJours = rs.getInt("nb_jours");
                int employeId = rs.getInt("employe_id");
                String statusRequest = rs.getString("etat_demande");

                if ("EN_COURS".equals(statusRequest)) {
                    updatePst.setInt(1, nbJours);
                    updatePst.setInt(2, employeId);
                    updatePst.executeUpdate();
                }

                deletePst.setInt(1, congeId);
                deletePst.executeUpdate();
            } else {
                System.out.println("Aucun congé trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du congé : " + e.getMessage());
        }
    }

    @Override
    public void validateLeave(int congeId) {
        String query = "UPDATE Conge SET etat_demande = ? WHERE conge_id = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            
            pst.setString(1, StatusRequest.VALIDE.name());
            pst.setInt(2, congeId);
            pst.executeUpdate();
            
            String selectQuery = "SELECT e.nom, e.prenom, e.email, c.type_conge, c.date_debut, c.date_fin " +
                    "FROM Employe e " +
                    "JOIN Conge c ON e.employe_id = c.employe_id " +
                    "WHERE c.conge_id = ?";
            try (PreparedStatement pstSelect = cnx.prepareStatement(selectQuery)) {
                pstSelect.setInt(1, congeId);
                try (ResultSet rs = pstSelect.executeQuery()) {
                    if (rs.next()) {
                    	String nom = capitalize(rs.getString("nom"));
                        String prenom = capitalize(rs.getString("prenom"));
                        String email = rs.getString("email");
                        String typeConge = rs.getString("type_conge");
                        String debutConge = rs.getDate("date_debut").toString();
                        String finConge = rs.getDate("date_fin").toString();

                        String subject = "Validation de votre demande de congé";
                        String body = String.format(
                                "Bonjour %s %s,\n\n" +
                                "Nous avons le plaisir de vous informer que votre demande de congé de type '%s' " +
                                "pour la période allant du %s au %s a été validée.\n\n" +
                                "Cordialement,\n" +
                                "L'équipe RH de l'OACA",
                                prenom, nom, typeConge, debutConge, finConge
                            );

                        Mail mail = new Mail();
                        mail.setupServerProperties();
                        mail.draftEmail(email, subject, body);
                        mail.sendEmail();
                    }
                }
            }
        } catch (SQLException | MessagingException e) {
            System.out.println("Erreur lors de la validation du congé : " + e.getMessage());
        }
    }

    @Override
    public void refuseLeave(int congeId) {
        String updateLeaveQuery = "UPDATE Conge SET etat_demande = ? WHERE conge_id = ?";
        String updateBalanceQuery = "UPDATE Employe SET solde_conge = solde_conge + ? WHERE employe_id = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pstLeave = cnx.prepareStatement(updateLeaveQuery);
             PreparedStatement pstBalance = cnx.prepareStatement(updateBalanceQuery)) {
            
            pstLeave.setString(1, StatusRequest.REFUSE.name());
            pstLeave.setInt(2, congeId);
            pstLeave.executeUpdate();

            String selectQuery = "SELECT nb_jours, e.nom, e.prenom, e.email, c.type_conge, c.date_debut, c.date_fin, c.employe_id " +
                    "FROM Conge c " +
                    "JOIN Employe e ON e.employe_id = c.employe_id " +
                    "WHERE c.conge_id = ?";
            try (PreparedStatement pstSelect = cnx.prepareStatement(selectQuery)) {
                pstSelect.setInt(1, congeId);
                try (ResultSet rs = pstSelect.executeQuery()) {
                    if (rs.next()) {
                    	int nbJours = rs.getInt("nb_jours");
                        int employeId = rs.getInt("employe_id");
                        String nom = capitalize(rs.getString("nom"));
                        String prenom = capitalize(rs.getString("prenom"));
                        String email = rs.getString("email");
                        String typeConge = rs.getString("type_conge");
                        String debutConge = rs.getDate("date_debut").toString();
                        String finConge = rs.getDate("date_fin").toString();

                        pstBalance.setInt(1, nbJours);
                        pstBalance.setInt(2, employeId);
                        pstBalance.executeUpdate();

                        String subject = "Refus de votre demande de congé";
                        String body = String.format(
                                "Bonjour %s %s,\n\n" +
                                "Nous regrettons de vous informer que votre demande de congé de type '%s' " +
                                "pour la période allant du %s au %s a été refusée.\n\n" +
                                "Si vous avez des questions, n'hésitez pas à contacter le service RH.\n\n" +
                                "Cordialement,\n" +
                                "L'équipe RH de l'OACA",
                                prenom, nom, typeConge, debutConge, finConge
                            );
                        
                        Mail mail = new Mail();
                        mail.setupServerProperties();
                        mail.draftEmail(email, subject, body);
                        mail.sendEmail();
                    }
                }
            }
        } catch (SQLException | MessagingException e) {
            System.out.println("Erreur lors du refus du congé : " + e.getMessage());
        }
    }
}
