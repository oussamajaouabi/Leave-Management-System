package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import models.Employee;
import models.Role;
import utilities.DatabaseConfig;
import utilities.Mail;
import models.FamilySituation;

public class EmployeeService implements EmployeeInterface, DatabaseConfig {
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

	private static String generateTemporaryPassword() {
	    int length = 8;
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
	    StringBuilder motDePasse = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	        motDePasse.append(chars.charAt(random.nextInt(chars.length())));
	    }
	    return motDePasse.toString();
	}
	
	private static String capitalize(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }
	    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	@Override
	public Employee fetchEmployeeDetails(int employeeId) {
        String query = "SELECT employe_id, nom, prenom, date_naissance, situation_familiale, email, telephone, adresse, cin, poste, solde_conge, mot_passe, role FROM Employe WHERE employe_id = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, employeeId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
            	return new Employee(
                        rs.getInt("employe_id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        FamilySituation.valueOf(rs.getString("situation_familiale")),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("adresse"),
                        rs.getString("cin"),
                        rs.getString("poste"),
                        rs.getInt("solde_conge"),
                        rs.getString("mot_passe"),
                        Role.valueOf(rs.getString("role"))
                    );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des détails de l'employé : " + e.getMessage());
        } 
        return null;
    }

	@Override
	public List<Employee> showEmployeesList() {
	    List<Employee> employes = new ArrayList<>();
	    String query = "SELECT * FROM Employe WHERE role = ?";
	    try (Connection cnx = getConnection();
	         PreparedStatement pst = cnx.prepareStatement(query)) {
	        pst.setString(1, Role.EMPLOYE.name());
	        
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            Employee employe = new Employee(
	                    rs.getInt("employe_id"),
	                    rs.getString("nom"),
	                    rs.getString("prenom"),
	                    rs.getDate("date_naissance"),
	                    FamilySituation.valueOf(rs.getString("situation_familiale")),
	                    rs.getString("email"),
	                    rs.getString("telephone"),
	                    rs.getString("adresse"),
	                    rs.getString("cin"),
	                    rs.getString("poste"),
	                    rs.getInt("solde_conge"),
	                    rs.getString("mot_passe"),
	                    Role.valueOf(rs.getString("role"))
	            );
	            employes.add(employe);
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'affichage des employés : " + e.getMessage());
	    }
	    return employes;
	}

	@Override
	public List<Employee> searchEmployee(String searchTerm) {
		List<Employee> employees = new ArrayList<>();
	    String query = "SELECT * FROM Employe WHERE role = ? AND (nom LIKE ? OR prenom LIKE ?)";
	    try (Connection cnx = getConnection();
	         PreparedStatement pst = cnx.prepareStatement(query)) {
	        String searchPattern = "%" + searchTerm + "%";
	        pst.setString(1, Role.EMPLOYE.name());
	        pst.setString(2, searchPattern);
	        pst.setString(3, searchPattern);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            Employee employee = new Employee(
	                    rs.getInt("employe_id"),
	                    rs.getString("nom"),
	                    rs.getString("prenom"),
	                    rs.getDate("date_naissance"),
	                    FamilySituation.valueOf(rs.getString("situation_familiale")),
	                    rs.getString("email"),
	                    rs.getString("telephone"),
	                    rs.getString("adresse"),
	                    rs.getString("cin"),
	                    rs.getString("poste"),
	                    rs.getInt("solde_conge"),
	                    rs.getString("mot_passe"),
	                    Role.valueOf(rs.getString("role"))
	            );
	            employees.add(employee);
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de la recherche de l'employé : " + e.getMessage());
	    }
	    return employees;
	}
	
	@Override
    public void createEmployee(String nom, String prenom, Date dateNaissance, FamilySituation situationFamiliale,
                             String email, String telephone, String adresse, String cin, String poste, int soldeConge) throws AddressException, MessagingException {
        String query = "INSERT INTO Employe (nom, prenom, date_naissance, situation_familiale, email, telephone, adresse, cin, poste, solde_conge, mot_passe) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setDate(3, new java.sql.Date(dateNaissance.getTime()));
            pst.setString(4, situationFamiliale.name());
            pst.setString(5, email);
            pst.setString(6, telephone);
            pst.setString(7, adresse);
            pst.setString(8, cin);
            pst.setString(9, poste);
            pst.setInt(10, soldeConge);
            
            String temporaryPassword = generateTemporaryPassword();
            pst.setString(11, temporaryPassword);
            
            String subject = "Création de compte - Plateforme de gestion des congés";
            String body = String.format(
            	    "Bonjour %s %s,\n\n" +
            	    "Nous sommes ravis de vous informer qu'un compte a été créé pour vous sur notre plateforme de gestion des congés pour l'Office de l'Aviation Civile et des Aéroports (OACA).\n\n" +
            	    "Voici vos informations de connexion :\n" +
            	    "- Identifiant : %s\n" +
            	    "- Mot de passe temporaire : %s\n\n" +
            	    "Nous vous invitons à changer votre mot de passe dès votre première connexion afin de sécuriser votre compte.\n\n" +
            	    "Cordialement,\n" +
            	    "L'équipe RH de l'OACA",
            	    capitalize(nom), capitalize(prenom), email, temporaryPassword
            	);
            Mail mail = new Mail();
            mail.setupServerProperties();
            mail.draftEmail(email, subject, body);
            mail.sendEmail();
            
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de l'employé : " + e.getMessage());
        }
    }
	
	@Override
    public void createAdmin(String nom, String prenom, Date dateNaissance, FamilySituation situationFamiliale,
                             String email, String telephone, String adresse, String cin, String poste, String mot_passe) {
        String query = "INSERT INTO Employe (nom, prenom, date_naissance, situation_familiale, email, telephone, adresse, cin, poste, solde_conge, role, mot_passe) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, null, ?, ?)";
        
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setDate(3, new java.sql.Date(dateNaissance.getTime()));
            pst.setString(4, situationFamiliale.name());
            pst.setString(5, email);
            pst.setString(6, telephone);
            pst.setString(7, adresse);
            pst.setString(8, cin);
            pst.setString(9, poste);
            
            pst.setString(10, Role.DIRECTEUR_RH.name());
            pst.setString(11, mot_passe);           
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du directeur RH : " + e.getMessage());
        }
    }

	@Override
	public boolean existsAdmin() {
	    String query = "SELECT COUNT(*) AS count FROM Employe WHERE role = ?";
	    try (Connection cnx = getConnection();
	         PreparedStatement pst = cnx.prepareStatement(query)) {
	        pst.setString(1, Role.DIRECTEUR_RH.name());
	        ResultSet rs = pst.executeQuery();
	        if (rs.next() && rs.getInt("count") > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de la vérification de l'existence de l'admin : " + e.getMessage());
	    }
	    return false;
	}
	
	@Override
	public void updateEmployee(int employeId, String nom, String prenom, Date dateNaissance,
			FamilySituation situationFamiliale, String email, String telephone, String adresse, String cin, String mot_passe) throws Exception {
		String queryCheck = "SELECT mot_passe FROM Employe WHERE employe_id = ?";
	    String queryUpdate = "UPDATE Employe SET nom = ?, prenom = ?, date_naissance = ?, situation_familiale = ?, " +
	            "email = ?, telephone = ?, adresse = ?, cin = ? WHERE employe_id = ?";

	    try (Connection cnx = getConnection();
	         PreparedStatement pstCheck = cnx.prepareStatement(queryCheck);
	         PreparedStatement pstUpdate = cnx.prepareStatement(queryUpdate)) {

	        pstCheck.setInt(1, employeId);
	        ResultSet rs = pstCheck.executeQuery();
	        if (rs.next()) {
	            String currentPassword = rs.getString("mot_passe");
	            if (!currentPassword.equals(mot_passe)) {
	                throw new Exception("Le mot de passe fourni est incorrect.");
	            }
	        } else {
	            throw new Exception("Aucun employé trouvé avec cet ID.");
	        }

	        pstUpdate.setString(1, nom);
	        pstUpdate.setString(2, prenom);
	        pstUpdate.setDate(3, new java.sql.Date(dateNaissance.getTime()));
	        pstUpdate.setString(4, situationFamiliale.name());
	        pstUpdate.setString(5, email);
	        pstUpdate.setString(6, telephone);
	        pstUpdate.setString(7, adresse);
	        pstUpdate.setString(8, cin);
	        pstUpdate.setInt(9, employeId);

	        int rowsUpdated = pstUpdate.executeUpdate();
	        if (rowsUpdated == 0) {
	            throw new Exception("La mise à jour a échoué : aucun employé correspondant.");
	        }
	    }
	}
	
	@Override
	public void updateEmployee(int employeId, String nom, String prenom, Date dateNaissance,
			FamilySituation situationFamiliale, String email, String telephone, String adresse, String cin, String poste, int soldeConge) {
		String query = "UPDATE Employe SET nom = ?, prenom = ?, date_naissance = ?, situation_familiale = ?, " +
                "email = ?, telephone = ?, adresse = ?, cin = ?, poste = ?, solde_conge = ? WHERE employe_id = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setDate(3, new java.sql.Date(dateNaissance.getTime()));
            pst.setString(4, situationFamiliale.name());
            pst.setString(5, email);
            pst.setString(6, telephone);
            pst.setString(7, adresse);
            pst.setString(8, cin);
            pst.setString(9, poste);
            pst.setInt(10, soldeConge);
            pst.setInt(11, employeId);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated <= 0) {
                System.out.println("Aucun employé trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'employé : " + e.getMessage());
        }
	}
	
	@Override
    public void editPassword(int employeeId, String oldPassword, String newPassword) throws Exception {
        String queryCheck = "SELECT mot_passe FROM Employe WHERE employe_id = ?";
        String queryUpdate = "UPDATE Employe SET mot_passe = ? WHERE employe_id = ?";

        try (Connection cnx = getConnection();
             PreparedStatement pstCheck = cnx.prepareStatement(queryCheck);
             PreparedStatement pstUpdate = cnx.prepareStatement(queryUpdate)) {

            pstCheck.setInt(1, employeeId);
            ResultSet rs = pstCheck.executeQuery();
            if (rs.next()) {
                String currentPassword = rs.getString("mot_passe");
                if (!currentPassword.equals(oldPassword)) {
                    throw new Exception("Mot de passe incorrect.");
                }

                pstUpdate.setString(1, newPassword);
                pstUpdate.setInt(2, employeeId);
                pstUpdate.executeUpdate();
            } else {
                throw new Exception("Employé non trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du changement de mot de passe : " + e.getMessage());
        }
    }

	@Override
	public void deleteEmployee(int employeId) {
		String query = "DELETE FROM Employe WHERE employe_id = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, employeId);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted <= 0) {
                System.out.println("Aucun employé trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'employé : " + e.getMessage());
        }
	}
}
