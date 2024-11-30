package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Employee;
import models.Role;
import utilities.DatabaseConfig;

public class AuthService implements AuthInterface, DatabaseConfig {
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
	
    @Override
    public boolean login(String email, String password, Role role) {
    	String query = "SELECT employe_id FROM Employe WHERE email = ? AND mot_passe = ? AND role = ?";
        try (Connection cnx = getConnection();
             PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, role.name());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int employeeId = rs.getInt("employe_id");
                
                EmployeeInterface employeeService = new EmployeeService();
                Employee employee = employeeService.fetchEmployeeDetails(employeeId);
                if (employee != null) {
                    Employee.setConnectedUser(employee);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la tentative de connexion : " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public void logout() {
    	Employee.clearConnectedUser();
    }
}
