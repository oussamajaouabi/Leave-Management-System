package services;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import models.Employee;
import models.FamilySituation;

public interface EmployeeInterface {
	// cbon
    Employee fetchEmployeeDetails(int employeeId);
    
    // cbon
    List<Employee> showEmployeesList();
    
    // cbon
    List<Employee> searchEmployee(String searchTerm);

    // email !
    void createEmployee(String nom, String prenom, Date dateNaissance, FamilySituation situationFamiliale,
                      String email, String telephone, String adresse, String cin, String poste, int soldeConge) throws AddressException, MessagingException; // pas de id + role + mot_passe

    // cbon
    void createAdmin(String nom, String prenom, Date dateNaissance, FamilySituation situationFamiliale,
            		String email, String telephone, String adresse, String cin, String poste, String mot_passe);
    
    // cbon
    boolean existsAdmin(); 
    
    // cbon
    void updateEmployee(int employeId, String nom, String prenom, Date dateNaissance,
    		FamilySituation situationFamiliale, String email, String telephone, 
                         String adresse, String cin, String mot_passe) throws Exception; // pour un employé // pas de poste + solde + role 
    
    // cbon
    void updateEmployee(int employeId, String nom, String prenom, Date dateNaissance,
    		FamilySituation situationFamiliale, String email, String telephone, 
                         String adresse, String cin, String poste, int soldeConge); // par un directeur RH ! // ajouter poste + solde + role

    // cbon
    void editPassword(int employeeId, String oldPassword, String newPassword) throws Exception;
    
    // cbon
    void deleteEmployee(int employeId);
}
