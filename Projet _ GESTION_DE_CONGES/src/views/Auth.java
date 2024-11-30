package views;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import models.Role;
import models.FamilySituation;
import services.AuthInterface;
import services.AuthService;
import services.EmployeeInterface;
import services.EmployeeService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.border.CompoundBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;

public class Auth {
	private JFrame frmApplicationDeGestion;
	private JTextField emailField;
	private JPasswordField passwordField;
	
	private Role role;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Auth window = new Auth();
					window.frmApplicationDeGestion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Auth() {
		initialize();
		
		Calendar calendar = Calendar.getInstance();
	    calendar.set(2000, Calendar.JANUARY, 1);
		
		EmployeeInterface employeeService = new EmployeeService();
		if (!employeeService.existsAdmin()) {
			employeeService.createAdmin(
	            "Admin", 
	            "Admin", 
	            calendar.getTime(),
	            FamilySituation.CELIBATAIRE,
	            "admin@gmail.com", 
	            "00000000", 
	            "Adresse Admin", 
	            "00000000", 
	            "Directeur RH", 
	            "admin123");
	    }
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Auth.this.role = Role.EMPLOYE;
		
		Consumer<JLabel> underline = label -> {
	        Font font = label.getFont();
	        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
	        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        label.setFont(font.deriveFont(attributes));
	    };

	    Consumer<JLabel> removeUnderline = label -> {
	        Font font = label.getFont();
	        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
	        attributes.put(TextAttribute.UNDERLINE, -1);
	        label.setFont(font.deriveFont(attributes));
	    };
		
		frmApplicationDeGestion = new JFrame();
		frmApplicationDeGestion.setIconImage(Toolkit.getDefaultToolkit().getImage(Auth.class.getResource("/images/oaca.png")));
		frmApplicationDeGestion.setTitle("Application de Gestion de Congés");
		frmApplicationDeGestion.setResizable(false);
		frmApplicationDeGestion.setBounds(100, 100, 625, 490);
		frmApplicationDeGestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmApplicationDeGestion.getContentPane().setLayout(null);
		
		Panel containerPanel = new Panel();
		containerPanel.setBounds(-71, -12, 850, 498);
		frmApplicationDeGestion.getContentPane().add(containerPanel);
		containerPanel.setLayout(null);
		
		JLabel oacaName = new JLabel("Office de l'Aviation Civile et des Aéroports");
		oacaName.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 11));
		oacaName.setHorizontalAlignment(SwingConstants.CENTER);
		oacaName.setBounds(196, 407, 280, 16);
		containerPanel.add(oacaName);
		
		Panel rolePanel = new Panel();
		rolePanel.setLayout(null);
		rolePanel.setBackground(new Color(255, 250, 250));
		rolePanel.setBounds(222, 117, 222, 35);
		containerPanel.add(rolePanel);
		
		JLabel employeeRole = new JLabel("Employé");
		employeeRole.setBounds(0, 0, 111, 35);
		rolePanel.add(employeeRole);
		employeeRole.setForeground(Color.DARK_GRAY);
		employeeRole.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		employeeRole.setHorizontalAlignment(SwingConstants.CENTER);
		employeeRole.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 13));
		underline.accept(employeeRole);
		
		JLabel hrdirectorRole = new JLabel("Directeur RH");
		hrdirectorRole.setBounds(104, 0, 118, 35);
		rolePanel.add(hrdirectorRole);
		hrdirectorRole.setForeground(Color.DARK_GRAY);
		hrdirectorRole.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hrdirectorRole.setHorizontalAlignment(SwingConstants.CENTER);
		hrdirectorRole.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 13));
		
		hrdirectorRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 Auth.this.role = Role.DIRECTEUR_RH;
		         underline.accept(hrdirectorRole);
		         removeUnderline.accept(employeeRole);
			}
		});
		
		employeeRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Auth.this.role = Role.EMPLOYE;
	            underline.accept(employeeRole);
	            removeUnderline.accept(hrdirectorRole);
			}
		});
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBounds(222, 169, 222, 194);
		loginPanel.setBackground(new Color(255, 250, 250));
		loginPanel.setBorder(new MatteBorder(0, 0, 5, 0, Color.DARK_GRAY));
		containerPanel.add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 13));
		emailLabel.setBounds(48, 23, 110, 20);
		loginPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailLabel.setLabelFor(emailField);
		emailField.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		emailField.setFont(new Font("Arial", Font.PLAIN, 11));
		emailField.setBounds(48, 43, 122, 20);
		loginPanel.add(emailField);
		emailField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Mot de passe");
		passwordLabel.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 13));
		passwordLabel.setBounds(48, 85, 110, 20);
		loginPanel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordLabel.setLabelFor(passwordField);
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		passwordField.setBounds(48, 105, 122, 20);
		loginPanel.add(passwordField);
		
		JButton loginButton = new JButton("S'authentifier");
		loginButton.setBackground(UIManager.getColor("Button.background"));
		loginButton.setBorder(new CompoundBorder());
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
		        String password = new String(passwordField.getPassword());

		        if (email.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        AuthInterface authService = new AuthService();
		        if (authService.login(email, password, Auth.this.role)) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);

		            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(loginButton);
		            currentFrame.dispose();

		            if (Auth.this.role == Role.EMPLOYE) {
		            	EmployeeView employeeView = new EmployeeView();
			            employeeView.getFrame().setVisible(true);
		            } else if (Auth.this.role == Role.DIRECTEUR_RH) {
		            	HRDirectorView hrDirectorView = new HRDirectorView();
		            	hrDirectorView.getFrame().setVisible(true);
		            }
		        } else {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Échec de la connexion. Veuillez vérifier vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		loginButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		loginButton.setBounds(58, 146, 102, 23);
		loginPanel.add(loginButton);
		
		JLabel applicationLogo = new JLabel("");
		applicationLogo.setHorizontalAlignment(SwingConstants.CENTER);
		applicationLogo.setIcon(new ImageIcon(Auth.class.getResource("/images/oaca.png")));
		applicationLogo.setBounds(71, 11, 70, 70);
		containerPanel.add(applicationLogo);
		
		JLabel applicationName_1 = new JLabel("Application de Gestion");
		applicationName_1.setForeground(Color.DARK_GRAY);
		applicationName_1.setHorizontalAlignment(SwingConstants.CENTER);
		applicationName_1.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 16));
		applicationName_1.setBounds(196, 39, 280, 26);
		containerPanel.add(applicationName_1);
		
		JLabel applicationName_2 = new JLabel("de Congés");
		applicationName_2.setHorizontalAlignment(SwingConstants.CENTER);
		applicationName_2.setForeground(Color.DARK_GRAY);
		applicationName_2.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 16));
		applicationName_2.setBounds(196, 62, 275, 26);
		containerPanel.add(applicationName_2);
		
		JLabel copyright = new JLabel("© 2024");
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 11));
		copyright.setBounds(196, 425, 280, 16);
		containerPanel.add(copyright);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setHorizontalAlignment(SwingConstants.CENTER);
		backgroundImage.setBounds(71, 11, 637, 519);
		backgroundImage.setIcon(new ImageIcon(Auth.class.getResource("/images/background.jpg")));
		containerPanel.add(backgroundImage);
	}
	
	public JFrame getFrame() {
        return frmApplicationDeGestion;
    }
}
