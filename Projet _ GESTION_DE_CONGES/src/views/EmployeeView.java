package views;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.Button;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

import models.Leave;
import models.Employee;
import models.FamilySituation;
import models.LeaveType;
import services.AuthInterface;
import services.AuthService;
import services.EmployeeInterface;
import services.EmployeeService;
import services.LeaveInterface;
import services.LeaveService;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Cursor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;

public class EmployeeView {
	private JFrame frmApplicationDeGestion;
	private JTextField leaveNameField;
	private JTextField leaveIdField;
	private JTable table1;
	private JTextField searchLeaveField;
	private JTextField addLeaveNameField;
	private JTable table2;
	private JTextField editLastNameField;
	private JTextField editFirstNameField;
	private JTextField editEmailField;
	private JTextField editPhoneNumberField;
	private JTextField editAddressField;
	private JTextField editCinField;
	private JPasswordField passwordField;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField confirmPasswordField;
	
	public void switchPanels(JLayeredPane layeredPane, Panel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void loadLeaveData(int employeId, DefaultTableModel model) {
	    LeaveInterface leaveService = new LeaveService();
	    List<Leave> leaveList = leaveService.showLeavesListByEmployee(employeId);

	    model.setRowCount(0);

	    for (Leave leave : leaveList) {
	        model.addRow(new Object[]{
	        	leave.getCongeId(),
	        	leave.getNomConge(),
	        	leave.getDescription(),
	        	leave.getDateDemande(),
	        	leave.getDateDebut(),
	        	leave.getDateFin(),
	            leave.getNbJours(),
	            leave.getTypeConge(),
	            leave.getEtatDemande()
	        });
	    }
	}
	
	/**
	 * Create the application.
	 */
	public EmployeeView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Employee employee = Employee.getConnectedUser();

		Consumer<JLabel> underline = label -> {
	        Font font = label.getFont();
	        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
	        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        label.setFont(font.deriveFont(attributes));
	    };
	    
		frmApplicationDeGestion = new JFrame();
		frmApplicationDeGestion.setIconImage(Toolkit.getDefaultToolkit().getImage(EmployeeView.class.getResource("/images/oaca.png")));
		frmApplicationDeGestion.setResizable(false);
		frmApplicationDeGestion.getContentPane().setBackground(new Color(70, 130, 180));
		frmApplicationDeGestion.setTitle("Application de Gestion de Congés - Interface employé");
		frmApplicationDeGestion.setBounds(100, 100, 950, 605);
		frmApplicationDeGestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmApplicationDeGestion.getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(154, 0, 780, 566);
		frmApplicationDeGestion.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		Panel employeeBody = new Panel();
		employeeBody.setBackground(SystemColor.inactiveCaptionBorder);
		layeredPane.add(employeeBody, "name_174901589373700");
		employeeBody.setLayout(null);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 934, 566);
		employeeBody.add(layeredPane_1);
		
		Panel profilePanel = new Panel();
		profilePanel.setBackground(SystemColor.inactiveCaptionBorder);
		profilePanel.setBounds(0, 0, 780, 566);
		layeredPane_1.add(profilePanel);
		profilePanel.setLayout(null);
		
		JLabel lastNameProfileLabel = new JLabel("Nom");
		lastNameProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		lastNameProfileLabel.setBounds(243, 139, 104, 14);
		profilePanel.add(lastNameProfileLabel);
		
		JLabel firstNameProfileLabel = new JLabel("Prénom");
		firstNameProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		firstNameProfileLabel.setBounds(243, 164, 106, 14);
		profilePanel.add(firstNameProfileLabel);
		
		JLabel birthDateProfileLabel = new JLabel("Date de Naissance");
		birthDateProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		birthDateProfileLabel.setBounds(243, 189, 140, 14);
		profilePanel.add(birthDateProfileLabel);
		
		JLabel familySituationProfileLabel = new JLabel("Situation Familiale");
		familySituationProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		familySituationProfileLabel.setBounds(243, 214, 126, 14);
		profilePanel.add(familySituationProfileLabel);
		
		JLabel emailProfileLabel = new JLabel("Email");
		emailProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		emailProfileLabel.setBounds(243, 241, 104, 14);
		profilePanel.add(emailProfileLabel);
		
		JLabel phoneNumberProfileLabel = new JLabel("Numéro de Téléphone");
		phoneNumberProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		phoneNumberProfileLabel.setBounds(243, 266, 140, 14);
		profilePanel.add(phoneNumberProfileLabel);
		
		JLabel addressProfileLabel = new JLabel("Adresse");
		addressProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addressProfileLabel.setBounds(243, 291, 126, 14);
		profilePanel.add(addressProfileLabel);
		
		JLabel cinProfileLabel = new JLabel("CIN");
		cinProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		cinProfileLabel.setBounds(243, 316, 140, 14);
		profilePanel.add(cinProfileLabel);
		
		JLabel lastNameProfile = new JLabel("");
		lastNameProfile.setVerticalAlignment(SwingConstants.TOP);
		lastNameProfile.setLabelFor(lastNameProfileLabel);
		lastNameProfile.setForeground(Color.BLUE);
		lastNameProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		lastNameProfile.setBounds(421, 139, 104, 27);
		profilePanel.add(lastNameProfile);
		
		JLabel firstNameProfile = new JLabel("");
		firstNameProfile.setVerticalAlignment(SwingConstants.TOP);
		firstNameProfile.setLabelFor(firstNameProfileLabel);
		firstNameProfile.setForeground(Color.BLUE);
		firstNameProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		firstNameProfile.setBounds(421, 164, 104, 27);
		profilePanel.add(firstNameProfile);
		
		JLabel birthDateProfile = new JLabel("");
		birthDateProfile.setVerticalAlignment(SwingConstants.TOP);
		birthDateProfile.setLabelFor(birthDateProfileLabel);
		birthDateProfile.setForeground(Color.BLUE);
		birthDateProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		birthDateProfile.setBounds(421, 189, 162, 27);
		profilePanel.add(birthDateProfile);
		
		JLabel familySituationProfile = new JLabel("");
		familySituationProfile.setVerticalAlignment(SwingConstants.TOP);
		familySituationProfile.setLabelFor(familySituationProfileLabel);
		familySituationProfile.setForeground(Color.BLUE);
		familySituationProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		familySituationProfile.setBounds(421, 214, 104, 27);
		profilePanel.add(familySituationProfile);
		
		JLabel emailProfile = new JLabel("");
		emailProfile.setVerticalAlignment(SwingConstants.TOP);
		emailProfile.setLabelFor(emailProfileLabel);
		emailProfile.setForeground(Color.BLUE);
		emailProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		emailProfile.setBounds(421, 241, 140, 27);
		profilePanel.add(emailProfile);
		
		JLabel phoneNumberProfile = new JLabel("");
		phoneNumberProfile.setVerticalAlignment(SwingConstants.TOP);
		phoneNumberProfile.setLabelFor(phoneNumberProfileLabel);
		phoneNumberProfile.setForeground(Color.BLUE);
		phoneNumberProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		phoneNumberProfile.setBounds(421, 266, 104, 27);
		profilePanel.add(phoneNumberProfile);
		
		JLabel addressProfile = new JLabel("");
		addressProfile.setVerticalAlignment(SwingConstants.TOP);
		addressProfile.setLabelFor(addressProfileLabel);
		addressProfile.setForeground(Color.BLUE);
		addressProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		addressProfile.setBounds(421, 291, 104, 27);
		profilePanel.add(addressProfile);
		
		JLabel cinProfile = new JLabel("");
		cinProfile.setVerticalAlignment(SwingConstants.TOP);
		cinProfile.setLabelFor(cinProfileLabel);
		cinProfile.setForeground(Color.BLUE);
		cinProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		cinProfile.setBounds(421, 316, 104, 18);
		profilePanel.add(cinProfile);
		
		JLabel jobProfileLabel = new JLabel("Poste");
		jobProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		jobProfileLabel.setBounds(243, 341, 140, 14);
		profilePanel.add(jobProfileLabel);
		
		JLabel jobProfile = new JLabel((String) null);
		jobProfile.setVerticalAlignment(SwingConstants.TOP);
		jobProfile.setLabelFor(jobProfileLabel);
		jobProfile.setForeground(Color.BLUE);
		jobProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		jobProfile.setBounds(421, 341, 104, 18);
		profilePanel.add(jobProfile);
		
		JLabel leaveBalanceProfileLabel = new JLabel("Solde Congé");
		leaveBalanceProfileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfileLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveBalanceProfileLabel.setBounds(589, 488, 81, 18);
		profilePanel.add(leaveBalanceProfileLabel);
		
		JLabel leaveBalanceProfile = new JLabel((String) null);
		leaveBalanceProfile.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfile.setLabelFor(leaveBalanceProfileLabel);
		leaveBalanceProfile.setForeground(Color.BLUE);
		leaveBalanceProfile.setFont(new Font("Open Sans", Font.BOLD, 13));
		leaveBalanceProfile.setBounds(599, 428, 57, 38);
		profilePanel.add(leaveBalanceProfile);
		
		lastNameProfile.setText(employee.getNom());
		firstNameProfile.setText(employee.getPrenom());
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
		birthDateProfile.setText(dateFormatter.format(employee.getDateNaissance()));
		
		familySituationProfile.setText(employee.getSituationFamiliale().getLibelle());
		emailProfile.setText(employee.getEmail());
		phoneNumberProfile.setText(employee.getTelephone());
		addressProfile.setText(employee.getAdresse());
		cinProfile.setText(employee.getCin());
		jobProfile.setText(employee.getPoste());
		leaveBalanceProfile.setText(employee.getSoldeConge() + " jours");
		
		JLabel profileTitle = new JLabel("Mon Profil");
		profileTitle.setHorizontalAlignment(SwingConstants.CENTER);
		profileTitle.setFont(new Font("Open Sans ExtraBold", Font.BOLD | Font.ITALIC, 20));
		profileTitle.setBounds(0, 0, 780, 140);
		profilePanel.add(profileTitle);
		
		JLabel leaveBalanceImage = new JLabel("");
		leaveBalanceImage.setIcon(new ImageIcon(EmployeeView.class.getResource("/images/calendar.png")));
		leaveBalanceImage.setBounds(579, 397, 126, 86);
		profilePanel.add(leaveBalanceImage);
		
		Panel editProfilePanel = new Panel();
		editProfilePanel.setBackground(SystemColor.inactiveCaptionBorder);
		editProfilePanel.setBounds(0, 0, 780, 566);
		layeredPane_1.add(editProfilePanel);
		editProfilePanel.setLayout(null);
		
		JLabel editLastNameLabel = new JLabel("Nom");
		editLastNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editLastNameLabel.setBounds(231, 145, 104, 14);
		editProfilePanel.add(editLastNameLabel);
		
		JLabel editFirstNameLabel = new JLabel("Prénom");
		editFirstNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editFirstNameLabel.setBounds(231, 170, 106, 14);
		editProfilePanel.add(editFirstNameLabel);
		
		JLabel editBirthDateLabel = new JLabel("Date de Naissance");
		editBirthDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editBirthDateLabel.setBounds(231, 195, 140, 14);
		editProfilePanel.add(editBirthDateLabel);
		
		JLabel editFamilySituationLabel = new JLabel("Situation Familiale");
		editFamilySituationLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editFamilySituationLabel.setBounds(231, 220, 126, 14);
		editProfilePanel.add(editFamilySituationLabel);
		
		editLastNameField = new JTextField();
		editLastNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		editLastNameField.setColumns(10);
		editLastNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editLastNameField.setBounds(383, 143, 159, 20);
		editProfilePanel.add(editLastNameField);
		
		JLabel editEmailLabel = new JLabel("Email");
		editEmailLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editEmailLabel.setBounds(231, 247, 104, 14);
		editProfilePanel.add(editEmailLabel);
		
		JLabel editPhoneNumberLabel = new JLabel("Numéro de Téléphone");
		editPhoneNumberLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editPhoneNumberLabel.setBounds(231, 274, 140, 14);
		editProfilePanel.add(editPhoneNumberLabel);
		
		JLabel editAddressLabel = new JLabel("Adresse");
		editAddressLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editAddressLabel.setBounds(231, 299, 126, 14);
		editProfilePanel.add(editAddressLabel);
		
		JLabel editCinLabel = new JLabel("CIN");
		editCinLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		editCinLabel.setBounds(231, 324, 140, 14);
		editProfilePanel.add(editCinLabel);
		
		JLabel editProfileTitle = new JLabel("Modifier Mon Profil");
		editProfileTitle.setHorizontalAlignment(SwingConstants.CENTER);
		editProfileTitle.setFont(new Font("Open Sans ExtraBold", Font.BOLD | Font.ITALIC, 20));
		editProfileTitle.setBounds(0, 0, 780, 140);
		editProfilePanel.add(editProfileTitle);
		
		editFirstNameField = new JTextField();
		editFirstNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		editFirstNameField.setColumns(10);
		editFirstNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editFirstNameField.setBounds(383, 168, 159, 20);
		editProfilePanel.add(editFirstNameField);
		
		JDateChooser editBirthDateField = new JDateChooser();
		editBirthDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editBirthDateField.setBounds(381, 195, 161, 20);
		editProfilePanel.add(editBirthDateField);
		
		JComboBox<FamilySituation> editFamilySituationField = new JComboBox<>();
		editFamilySituationField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editFamilySituationField.setFont(new Font("Arial", Font.PLAIN, 11));
		editFamilySituationField.setModel(new DefaultComboBoxModel<>(FamilySituation.values()));
		editFamilySituationField.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getListCellRendererComponent(
		        JList<?> list,
		        Object value,
		        int index,
		        boolean isSelected,
		        boolean cellHasFocus
		    ) {
		        if (value instanceof FamilySituation) {
		            value = ((FamilySituation) value).getLibelle();
		        }
		        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    }
		});
		editFamilySituationField.setBounds(383, 220, 159, 19);
		editProfilePanel.add(editFamilySituationField);
		
		editEmailField = new JTextField();
		editEmailField.setFont(new Font("Arial", Font.PLAIN, 11));
		editEmailField.setColumns(10);
		editEmailField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editEmailField.setBounds(383, 245, 159, 20);
		editProfilePanel.add(editEmailField);
		
		editPhoneNumberField = new JTextField();
		editPhoneNumberField.setFont(new Font("Arial", Font.PLAIN, 11));
		editPhoneNumberField.setColumns(10);
		editPhoneNumberField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editPhoneNumberField.setBounds(383, 272, 159, 20);
		editProfilePanel.add(editPhoneNumberField);
		
		editAddressField = new JTextField();
		editAddressField.setFont(new Font("Arial", Font.PLAIN, 11));
		editAddressField.setColumns(10);
		editAddressField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editAddressField.setBounds(383, 297, 159, 20);
		editProfilePanel.add(editAddressField);
		
		editCinField = new JTextField();
		editCinField.setFont(new Font("Arial", Font.PLAIN, 11));
		editCinField.setColumns(10);
		editCinField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editCinField.setBounds(383, 322, 159, 20);
		editProfilePanel.add(editCinField);
		
        editLastNameField.setText(employee.getNom());
        editFirstNameField.setText(employee.getPrenom());
        editBirthDateField.setDate(employee.getDateNaissance());
        editFamilySituationField.setSelectedItem(employee.getSituationFamiliale());
        editEmailField.setText(employee.getEmail());
        editPhoneNumberField.setText(employee.getTelephone());
        editAddressField.setText(employee.getAdresse());
        editCinField.setText(employee.getCin());
        
        JLabel passwordLabel = new JLabel("Mot de Passe");
        passwordLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
        passwordLabel.setBounds(231, 349, 140, 14);
        editProfilePanel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordField.setBounds(383, 347, 159, 20);
        editProfilePanel.add(passwordField);
        
        JButton updateEmployeeButton = new JButton("Modifier les informations");
        updateEmployeeButton.setBorder(new CompoundBorder());
        updateEmployeeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateEmployeeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
                    String nom = editLastNameField.getText();
                    String prenom = editFirstNameField.getText();
                    Date dateNaissance = editBirthDateField.getDate();
                    FamilySituation situationFamiliale = (FamilySituation) editFamilySituationField.getSelectedItem();  
                    String email = editEmailField.getText();
                    String telephone = editPhoneNumberField.getText();
                    String adresse = editAddressField.getText();
                    String cin = editCinField.getText();
                    @SuppressWarnings("deprecation")
        			String mot_passe = passwordField.getText();

                    if (nom.isEmpty() || prenom.isEmpty() || dateNaissance == null || situationFamiliale == null || email.isEmpty() || telephone.isEmpty() || adresse.isEmpty() || cin.isEmpty() || mot_passe.isEmpty()) {
        	            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
        	            return;
        	        }        

                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'email n'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!telephone.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le numéro de téléphone doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!cin.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le CIN doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    EmployeeService employeService = new EmployeeService();
                    employeService.updateEmployee(employee.getEmployeId(), nom, prenom, dateNaissance, situationFamiliale, email, telephone, adresse, cin, mot_passe);

                    employee.setNom(nom);
                    employee.setPrenom(prenom);
                    employee.setDateNaissance(dateNaissance);
                    employee.setSituationFamiliale(situationFamiliale);
                    employee.setEmail(email);
                    employee.setTelephone(telephone);
                    employee.setAdresse(adresse);
                    employee.setCin(cin);
                    
                    lastNameProfile.setText(employee.getNom());
            		firstNameProfile.setText(employee.getPrenom());
            		birthDateProfile.setText(dateFormatter.format(employee.getDateNaissance()));
            		familySituationProfile.setText(employee.getSituationFamiliale().getLibelle());
            		emailProfile.setText(employee.getEmail());
            		phoneNumberProfile.setText(employee.getTelephone());
            		addressProfile.setText(employee.getAdresse());
            		cinProfile.setText(employee.getCin());
            		
                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "Employé modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur lors de la modification : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
        	}
        });
        updateEmployeeButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
        updateEmployeeButton.setBounds(275, 393, 200, 23);
        editProfilePanel.add(updateEmployeeButton);
		
		Panel editPasswordPanel = new Panel();
		editPasswordPanel.setBackground(SystemColor.inactiveCaptionBorder);
		editPasswordPanel.setBounds(0, 0, 780, 566);
		layeredPane_1.add(editPasswordPanel);
		editPasswordPanel.setLayout(null);
		
		JLabel oldPasswordLabel = new JLabel("Mot de Passe");
		oldPasswordLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		oldPasswordLabel.setBounds(206, 153, 196, 14);
		editPasswordPanel.add(oldPasswordLabel);
		
		JLabel editPasswordTitle = new JLabel("Modifier Mon Mot de Passe");
		editPasswordTitle.setHorizontalAlignment(SwingConstants.CENTER);
		editPasswordTitle.setFont(new Font("Open Sans ExtraBold", Font.BOLD | Font.ITALIC, 20));
		editPasswordTitle.setBounds(0, 0, 780, 140);
		editPasswordPanel.add(editPasswordTitle);
		
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		oldPasswordField.setFont(new Font("Arial", Font.PLAIN, 11));
		oldPasswordField.setBounds(414, 151, 159, 20);
		editPasswordPanel.add(oldPasswordField);
		
		JButton editPasswordButton = new JButton("Mettre à Jour");
		editPasswordButton.setBorder(new CompoundBorder());
		editPasswordButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String oldPassword = oldPasswordField.getText();
		        String newPassword = newPasswordField.getText();
		        String confirmPassword = confirmPasswordField.getText();
		        
		        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        if (!newPassword.equals(confirmPassword)) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le nouveau mot de passe et sa confirmation ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int employeId = employee.getEmployeId();

		        try {
		        	EmployeeInterface employeeService = new EmployeeService();
		        	employeeService.editPassword(employeId, oldPassword, newPassword);
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Mot de passe changé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
		            
		            oldPasswordField.setText("");
		            newPasswordField.setText("");
		            confirmPasswordField.setText("");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		editPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editPasswordButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		editPasswordButton.setBounds(306, 267, 152, 23);
		editPasswordPanel.add(editPasswordButton);
		
		JLabel newPasswordLabel = new JLabel("Nouveau Mot de Passe");
		newPasswordLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		newPasswordLabel.setBounds(206, 185, 196, 14);
		editPasswordPanel.add(newPasswordLabel);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		newPasswordField.setBounds(414, 183, 159, 20);
		editPasswordPanel.add(newPasswordField);
		
		JLabel confirmPasswordLabel = new JLabel("Confirmation du Mot de Passe");
		confirmPasswordLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		confirmPasswordLabel.setBounds(206, 216, 196, 14);
		editPasswordPanel.add(confirmPasswordLabel);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		confirmPasswordField.setBounds(414, 214, 159, 20);
		editPasswordPanel.add(confirmPasswordField);
        
		Panel leave_body = new Panel();
		layeredPane.add(leave_body, "name_174917729986100");
		leave_body.setLayout(null);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBounds(0, 0, 934, 566);
		leave_body.add(layeredPane_2);
		layeredPane_2.setLayout(null);
		
		Panel myLeavesPanel = new Panel();
		myLeavesPanel.setBackground(SystemColor.inactiveCaptionBorder);
		myLeavesPanel.setBounds(0, 0, 780, 566);
		layeredPane_2.add(myLeavesPanel);
		myLeavesPanel.setLayout(null);
		
		JLabel leaveEndDateLabel = new JLabel("Date Fin");
		leaveEndDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveEndDateLabel.setBounds(111, 125, 126, 14);
		myLeavesPanel.add(leaveEndDateLabel);
		
		JLabel leaveDescriptionLabel = new JLabel("Description (Optionnel)");
		leaveDescriptionLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveDescriptionLabel.setBounds(111, 75, 147, 18);
		myLeavesPanel.add(leaveDescriptionLabel);
		
		JLabel leaveTypeLabel = new JLabel("Type de Congé");
		leaveTypeLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveTypeLabel.setBounds(111, 150, 126, 18);
		myLeavesPanel.add(leaveTypeLabel);
		
		JDateChooser leaveStartDateField = new JDateChooser();
		leaveStartDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveStartDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveStartDateField.setBounds(284, 102, 112, 20);
		myLeavesPanel.add(leaveStartDateField);
		
		
		JComboBox<LeaveType> leaveTypeField = new JComboBox<>();
		leaveTypeLabel.setLabelFor(leaveTypeField);
		leaveTypeField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveTypeField.setModel(new DefaultComboBoxModel<>(LeaveType.values()));
		leaveTypeField.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getListCellRendererComponent(
		        JList<?> list,
		        Object value,
		        int index,
		        boolean isSelected,
		        boolean cellHasFocus
		    ) {
		        if (value instanceof LeaveType) {
		            value = ((LeaveType) value).getLibelle();
		        }
		        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    }
		});
		leaveTypeField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveTypeField.setBounds(286, 151, 110, 18);
		myLeavesPanel.add(leaveTypeField);
		
		JButton updateLeaveButton = new JButton("Modifier un Congé");
		updateLeaveButton.setBorder(new CompoundBorder());
		updateLeaveButton.setBackground(UIManager.getColor("Button.background"));
		updateLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateLeaveButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		updateLeaveButton.setBounds(111, 202, 179, 23);
		myLeavesPanel.add(updateLeaveButton);
		
		JButton deleteLeaveButton = new JButton("Supprimer un Congé");
		deleteLeaveButton.setBorder(new CompoundBorder());
		deleteLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteLeaveButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		deleteLeaveButton.setBackground(UIManager.getColor("Button.darkShadow"));
		deleteLeaveButton.setBounds(322, 202, 179, 23);
		myLeavesPanel.add(deleteLeaveButton);
		
		JLabel leaveIdLabel = new JLabel("ID du Congé");
		leaveIdLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveIdLabel.setBounds(111, 25, 104, 18);
		myLeavesPanel.add(leaveIdLabel);
		
		JLabel leaveStartDateLabel = new JLabel("Date Début");
		leaveStartDateLabel.setLabelFor(leaveStartDateField);
		leaveStartDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveStartDateLabel.setBounds(111, 100, 140, 14);
		myLeavesPanel.add(leaveStartDateLabel);
		
		leaveNameField = new JTextField();
		leaveNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveNameField.setColumns(10);
		leaveNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveNameField.setBounds(286, 50, 110, 20);
		myLeavesPanel.add(leaveNameField);
		
		leaveIdField = new JTextField();
		leaveIdLabel.setLabelFor(leaveIdField);
		leaveIdField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveIdField.setColumns(10);
		leaveIdField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveIdField.setBounds(286, 25, 110, 20);
		myLeavesPanel.add(leaveIdField);
		
		JButton searchLeaveButton = new JButton("Rechercher par Nom");
		searchLeaveButton.setBackground(UIManager.getColor("Button.background"));
		searchLeaveButton.setBorder(new CompoundBorder());
		searchLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchLeaveButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		searchLeaveButton.setBounds(390, 527, 179, 23);
		myLeavesPanel.add(searchLeaveButton);
		
		JLabel leaveNameLabel = new JLabel("Nom du Congé");
		leaveNameLabel.setLabelFor(leaveNameField);
		leaveNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveNameLabel.setBounds(111, 50, 106, 18);
		myLeavesPanel.add(leaveNameLabel);
		
		JTextArea leaveDescriptionField = new JTextArea();
		leaveDescriptionField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveDescriptionLabel.setLabelFor(leaveDescriptionField);
		leaveDescriptionField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveDescriptionField.setBounds(286, 73, 110, 22);
		myLeavesPanel.add(leaveDescriptionField);
		
		JDateChooser leaveEndDateField = new JDateChooser();
		leaveEndDateLabel.setLabelFor(leaveEndDateField);
		leaveEndDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		leaveEndDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaveEndDateField.setBounds(284, 127, 112, 20);
		myLeavesPanel.add(leaveEndDateField);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollPane1.setBounds(10, 248, 760, 261);
		myLeavesPanel.add(scrollPane1);
		
		table1 = new JTable();
		table1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table1.setFont(new Font("Arial", Font.PLAIN, 11));
		
		searchLeaveField = new JTextField();
		searchLeaveField.setFont(new Font("Arial", Font.PLAIN, 11));
		searchLeaveField.setColumns(10);
		searchLeaveField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		searchLeaveField.setBounds(261, 527, 110, 20);
		myLeavesPanel.add(searchLeaveField);
		
		table1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table1.getSelectedRow();
	            
				try {
		            int leaveId = (int) table1.getValueAt(selectedRow, 0);
		            String leaveName = table1.getValueAt(selectedRow, 1).toString();
		            String leaveDescription = table1.getValueAt(selectedRow, 2).toString();
		            Date leaveStartDate = (Date) table1.getValueAt(selectedRow, 4);
		            Date leaveEndDate = (Date) table1.getValueAt(selectedRow, 5);
		            LeaveType leaveType = (LeaveType) table1.getValueAt(selectedRow, 7);

		            leaveIdField.setText(String.valueOf(leaveId));
		            leaveNameField.setText(leaveName);
		            leaveDescriptionField.setText(leaveDescription);
		            leaveStartDateField.setDate(leaveStartDate);
		            leaveEndDateField.setDate(leaveEndDate);
		            leaveTypeField.setSelectedItem(leaveType);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		table1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID du cong\u00E9", "Nom du cong\u00E9", "Description", "Date de la demande", "Date d\u00E9but", "Date fin", "Nombre de jours", "Type de cong\u00E9", "État de la Demande"
			}
		));
		
		JLabel leaveBalanceProfileLabel1 = new JLabel("Solde Congé");
		leaveBalanceProfileLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfileLabel1.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveBalanceProfileLabel1.setBounds(589, 129, 81, 18);
		myLeavesPanel.add(leaveBalanceProfileLabel1);
		
		JLabel leaveBalanceProfile1 = new JLabel("0 jours");
		leaveBalanceProfile1.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfile1.setForeground(Color.BLUE);
		leaveBalanceProfile1.setFont(new Font("Open Sans", Font.BOLD, 13));
		leaveBalanceProfile1.setBounds(599, 69, 57, 38);
		myLeavesPanel.add(leaveBalanceProfile1);
		leaveBalanceProfile1.setText(employee.getSoldeConge() + " jours");
		
		JLabel leaveBalanceImage1 = new JLabel("");
		leaveBalanceImage1.setIcon(new ImageIcon(EmployeeView.class.getResource("/images/calendar.png")));
		leaveBalanceImage1.setBounds(579, 38, 126, 86);
		myLeavesPanel.add(leaveBalanceImage1);
		
		DefaultTableModel model1 = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "ID du congé", "Nom du congé", "Description", "Date de la demande",
			        "Date début", "Date fin", "Nombre de jours", "Type de congé", "État de la demande"
			    }
			);
		loadLeaveData(employee.getEmployeId(), model1);

		table1.setModel(model1);
		scrollPane1.setViewportView(table1);
		
		DefaultTableModel model2 = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID du congé", "Nom du congé", "Description", "Date de la demande",
					"Date début", "Date fin", "Nombre de jours", "Type de congé", "État de la demande"
				}
			);	
		loadLeaveData(employee.getEmployeId(), model2);
		
		searchLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			        String searchItem = searchLeaveField.getText().trim();

			        if (searchItem.isEmpty()) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, 
		                    "Veuillez saisir un critère de recherche.", 
		                    "Avertissement", 
		                    JOptionPane.WARNING_MESSAGE);
		                model1.setRowCount(0);
		                loadLeaveData(employee.getEmployeId(), model1);
		                return;
		            }
			        
			        LeaveInterface leaveService = new LeaveService();
			        List<Leave> results = leaveService.searchLeave(employee.getEmployeId(), searchItem);

			        if (results.isEmpty()) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Aucun congé trouvé avec les critères donnés.", "Information", JOptionPane.INFORMATION_MESSAGE);
		            }
			        
			        model1.setRowCount(0);
		            for (Leave leave : results) {
		                model1.addRow(new Object[]{
		                	leave.getCongeId(),
		                    leave.getNomConge(),
		                    leave.getDescription(),
		                    leave.getDateDemande(),
		                    leave.getDateDebut(),
		                    leave.getDateFin(),
		                    leave.getNbJours(),
		                    leave.getTypeConge(),
		                    leave.getEtatDemande(),
		                    leave.getEmployeId()
		                });
		            }
			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});

		Panel addLeavePanel = new Panel();
		addLeavePanel.setBackground(SystemColor.inactiveCaptionBorder);
		addLeavePanel.setBounds(0, 0, 780, 566);
		layeredPane_2.add(addLeavePanel);
		addLeavePanel.setLayout(null);
		
		JLabel addLeaveNameLabel = new JLabel("Nom du Congé");
		addLeaveNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addLeaveNameLabel.setBounds(101, 23, 104, 20);
		addLeavePanel.add(addLeaveNameLabel);
		
		JLabel addLeaveDescriptionLabel = new JLabel("Description (Optionnel)");
		addLeaveDescriptionLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addLeaveDescriptionLabel.setBounds(101, 48, 153, 20);
		addLeavePanel.add(addLeaveDescriptionLabel);
		
		JLabel addLeaveStartDateLabel = new JLabel("Date Début");
		addLeaveStartDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addLeaveStartDateLabel.setBounds(101, 75, 140, 20);
		addLeavePanel.add(addLeaveStartDateLabel);
		
		JLabel addLeaveEndDateLabel = new JLabel("Date Fin");
		addLeaveEndDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addLeaveEndDateLabel.setBounds(101, 100, 126, 20);
		addLeavePanel.add(addLeaveEndDateLabel);
		
		JLabel addLeaveTypeLabel = new JLabel("Type de Congé");
		addLeaveTypeLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addLeaveTypeLabel.setBounds(101, 125, 126, 18);
		addLeavePanel.add(addLeaveTypeLabel);
		
		JComboBox<LeaveType> addLeaveTypeField = new JComboBox<>();
		addLeaveTypeLabel.setLabelFor(addLeaveTypeField);
		addLeaveTypeField.setFont(new Font("Arial", Font.PLAIN, 11));
		addLeaveTypeField.setModel(new DefaultComboBoxModel<>(LeaveType.values()));
		addLeaveTypeField.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getListCellRendererComponent(
		        JList<?> list,
		        Object value,
		        int index,
		        boolean isSelected,
		        boolean cellHasFocus
		    ) {
		        if (value instanceof LeaveType) {
		            value = ((LeaveType) value).getLibelle();
		        }
		        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    }
		});
		
		addLeaveTypeField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addLeaveTypeField.setBounds(267, 124, 110, 19);
		addLeavePanel.add(addLeaveTypeField);
		
		JDateChooser addLeaveEndDateField = new JDateChooser();
		addLeaveEndDateLabel.setLabelFor(addLeaveEndDateField);
		addLeaveEndDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		addLeaveEndDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addLeaveEndDateField.setBounds(265, 100, 112, 20);
		addLeavePanel.add(addLeaveEndDateField);
		
		JDateChooser addLeaveStartDateField = new JDateChooser();
		addLeaveStartDateLabel.setLabelFor(addLeaveStartDateField);
		addLeaveStartDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		addLeaveStartDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addLeaveStartDateField.setBounds(265, 75, 112, 20);
		addLeavePanel.add(addLeaveStartDateField);
		
		JTextArea addLeaveDescriptionField = new JTextArea();
		addLeaveDescriptionLabel.setLabelFor(addLeaveDescriptionField);
		addLeaveDescriptionField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addLeaveDescriptionField.setFont(new Font("Arial", Font.PLAIN, 11));
		addLeaveDescriptionField.setBounds(267, 46, 110, 22);
		addLeavePanel.add(addLeaveDescriptionField);
		
		addLeaveNameField = new JTextField();
		addLeaveNameLabel.setLabelFor(addLeaveNameField);
		addLeaveNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		addLeaveNameField.setColumns(10);
		addLeaveNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addLeaveNameField.setBounds(267, 23, 110, 20);
		addLeavePanel.add(addLeaveNameField);
		
		JButton addLeaveButton = new JButton("Créer une Demande de Congé");
		addLeaveButton.setBackground(UIManager.getColor("Button.background"));
		addLeaveButton.setBorder(new CompoundBorder());
		
		addLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addLeaveButton.setFont(new Font("Open Sans SemiBold", Font.ITALIC, 11));
		addLeaveButton.setBounds(281, 167, 224, 23);
		addLeavePanel.add(addLeaveButton);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollPane2.setBounds(10, 214, 760, 341);
		addLeavePanel.add(scrollPane2);
		
		table2 = new JTable();
		table2.setFont(new Font("Arial", Font.PLAIN, 11));
		table2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table2.setModel(model2);
		scrollPane2.setViewportView(table2);
		
		JLabel leaveBalanceProfileLabel2 = new JLabel("Solde Congé");
		leaveBalanceProfileLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfileLabel2.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		leaveBalanceProfileLabel2.setBounds(568, 125, 81, 18);
		addLeavePanel.add(leaveBalanceProfileLabel2);
		
		JLabel leaveBalanceProfile2 = new JLabel("0 jours");
		leaveBalanceProfile2.setHorizontalAlignment(SwingConstants.CENTER);
		leaveBalanceProfile2.setForeground(Color.BLUE);
		leaveBalanceProfile2.setFont(new Font("Open Sans", Font.BOLD, 13));
		leaveBalanceProfile2.setBounds(578, 65, 57, 38);
		addLeavePanel.add(leaveBalanceProfile2);
		leaveBalanceProfile2.setText(employee.getSoldeConge() + " jours");
		
		JLabel leaveBalanceImage2 = new JLabel("");
		leaveBalanceImage2.setIcon(new ImageIcon(EmployeeView.class.getResource("/images/calendar.png")));
		leaveBalanceImage2.setBounds(558, 34, 126, 86);
		addLeavePanel.add(leaveBalanceImage2);
		
		deleteLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			        int congeId = Integer.parseInt(leaveIdField.getText());

			        int confirm = JOptionPane.showConfirmDialog(
			            null,
			            "Êtes-vous sûr de vouloir supprimer ce congé ?",
			            "Confirmation",
			            JOptionPane.YES_NO_OPTION
			        );

			        if (confirm == JOptionPane.YES_OPTION) {
			            LeaveInterface leaveService = new LeaveService();
			            leaveService.deleteLeave(congeId);

			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Congé supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

			            EmployeeInterface employeeService = new EmployeeService();
			            int employeeId = employee.getEmployeId();
				        Employee employee = employeeService.fetchEmployeeDetails(employeeId);
				        Employee.setConnectedUser(employee);
				        
				        leaveBalanceProfile.setText(employee.getSoldeConge() + " jours");
				        leaveBalanceProfile1.setText(employee.getSoldeConge() + " jours");
				        leaveBalanceProfile2.setText(employee.getSoldeConge() + " jours");
			            		
			            model1.setRowCount(0);
			            loadLeaveData(employee.getEmployeId(), model1);
			            model2.setRowCount(0);
			            loadLeaveData(employee.getEmployeId(), model2);
			        }
			    } catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez entrer un ID valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		
		updateLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int leaveId = Integer.parseInt(leaveIdField.getText());
		            int selectedRow = table1.getSelectedRow();

		            if (selectedRow == -1) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, "Aucun congé sélectionné dans le tableau.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            int selectedLeaveId = (int) table1.getValueAt(selectedRow, 0);

		            if (leaveId != selectedLeaveId) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'ID du congé saisi ne correspond pas à l'ID du congé sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            
			        String leaveName = leaveNameField.getText();
			        String leaveDescription = leaveDescriptionField.getText();
			        Date leaveStartDate = leaveStartDateField.getDate();
			        Date leaveEndDate = leaveEndDateField.getDate();
			        LeaveType leaveType = (LeaveType) leaveTypeField.getSelectedItem();
			        
			        if (leaveName.isEmpty() || leaveStartDate == null || leaveEndDate == null) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Vérifiez les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			        
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(leaveStartDate);
			        int year = calendar.get(Calendar.YEAR);
		        
			        if (year != 2024) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Seuls les congés avec une date de début en 2024 sont acceptés.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			        
			        Date currentDate = new Date();
		            if (leaveStartDate.before(currentDate)) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, "La date de début doit être postérieure à la date courante.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            
			        if (leaveEndDate.before(leaveStartDate)) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "La date de fin doit être postérieure à la date de début.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
	
			        LeaveInterface leaveService = new LeaveService();
			        leaveService.updateLeave(leaveId, leaveName, leaveDescription, leaveStartDate, leaveEndDate, leaveType);

			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "La demande de congé a été modifiée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

			        model1.setRowCount(0);
			        loadLeaveData(employee.getEmployeId(), model1);
			        model2.setRowCount(0);
			        loadLeaveData(employee.getEmployeId(), model2);
			        
			        leaveIdField.setText("");
			        leaveNameField.setText("");
			        leaveDescriptionField.setText("");
			        leaveStartDateField.setDate(null);
			        leaveEndDateField.setDate(null);
			        leaveTypeField.setSelectedIndex(0);
			        
			        EmployeeInterface employeeService = new EmployeeService();
			        int employeeId = employee.getEmployeId();
			        Employee employee = employeeService.fetchEmployeeDetails(employeeId);
			        Employee.setConnectedUser(employee);
			        
			        leaveBalanceProfile.setText(employee.getSoldeConge() + " jours");
			        leaveBalanceProfile1.setText(employee.getSoldeConge() + " jours");
			        leaveBalanceProfile2.setText(employee.getSoldeConge() + " jours");

			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		
		addLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int employeeId = employee.getEmployeId();
			        String leaveName = addLeaveNameField.getText();
			        String leaveDescription = addLeaveDescriptionField.getText();
			        Date leaveStartDate = addLeaveStartDateField.getDate();
			        Date leaveEndDate = addLeaveEndDateField.getDate();
			        LeaveType leaveType = LeaveType.valueOf(addLeaveTypeField.getSelectedItem().toString());

			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(leaveStartDate);
			        int year = calendar.get(Calendar.YEAR);
			        
			        if (leaveName.isEmpty() || leaveStartDate == null || leaveEndDate == null) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Vérifiez les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			        
			        if (year != 2024) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Seuls les congés avec une date de début en 2024 sont acceptés.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }

			        Date currentDate = new Date();
		            if (leaveStartDate.before(currentDate)) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, "La date de début doit être postérieure à la date courante.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		            
			        if (leaveEndDate.before(leaveStartDate)) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "La date de fin doit être postérieure à la date de début.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }

			        LeaveInterface leaveService = new LeaveService();
			        leaveService.createLeave(employeeId, leaveName, leaveDescription, leaveStartDate, leaveEndDate, leaveType);

			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "La demande de congé a été enregistrée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
			        
			        model1.setRowCount(0);
			        loadLeaveData(employee.getEmployeId(), model1);
			        model2.setRowCount(0);
			        loadLeaveData(employee.getEmployeId(), model2);
			        
			        addLeaveNameField.setText("");
			        addLeaveDescriptionField.setText("");
			        addLeaveStartDateField.setDate(null);
			        addLeaveEndDateField.setDate(null);
			        addLeaveTypeField.setSelectedIndex(0);
			        
			        EmployeeInterface employeeService = new EmployeeService();
			        Employee employee = employeeService.fetchEmployeeDetails(employeeId);
			        Employee.setConnectedUser(employee);
			        
			        leaveBalanceProfile.setText(employee.getSoldeConge() + " jours");
			        leaveBalanceProfile1.setText(employee.getSoldeConge() + " jours");
			        leaveBalanceProfile2.setText(employee.getSoldeConge() + " jours");
			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur lors de l'enregistrement de la demande de congé : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		
		Panel employeeMenu = new Panel();
		employeeMenu.setLayout(null);
		employeeMenu.setBounds(0, 139, 156, 138);
		frmApplicationDeGestion.getContentPane().add(employeeMenu);
		
		Button profileMenuButton = new Button("Profil");
		profileMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, profilePanel);
			}
		});
		profileMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profileMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		profileMenuButton.setFocusable(false);
		profileMenuButton.setFocusTraversalKeysEnabled(false);
		profileMenuButton.setBackground(UIManager.getColor("Button.background"));
		profileMenuButton.setBounds(10, 33, 136, 22);
		employeeMenu.add(profileMenuButton);
		
		Button editProfileMenuButton = new Button("Modifier Profil");
		editProfileMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editProfileMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, editProfilePanel);
			}
		});
		editProfileMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		editProfileMenuButton.setFocusable(false);
		editProfileMenuButton.setFocusTraversalKeysEnabled(false);
		editProfileMenuButton.setBackground(UIManager.getColor("Button.background"));
		editProfileMenuButton.setBounds(10, 60, 136, 22);
		employeeMenu.add(editProfileMenuButton);
		
		Button editPasswordMenuButton = new Button("Modifier Mot de Passe");
		editPasswordMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, editPasswordPanel);
			}
		});
		editPasswordMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editPasswordMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		editPasswordMenuButton.setFocusable(false);
		editPasswordMenuButton.setFocusTraversalKeysEnabled(false);
		editPasswordMenuButton.setBackground(UIManager.getColor("Button.background"));
		editPasswordMenuButton.setBounds(10, 88, 136, 22);
		employeeMenu.add(editPasswordMenuButton);
		
		JLabel employeeMenuHead = new JLabel("Employé");
		employeeMenuHead.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 15));
		employeeMenuHead.setHorizontalAlignment(SwingConstants.CENTER);
		employeeMenuHead.setBounds(0, 0, 156, 34);
		underline.accept(employeeMenuHead);
		employeeMenu.add(employeeMenuHead);
		
		Panel leaveMenu = new Panel();
		leaveMenu.setLayout(null);
		leaveMenu.setBounds(0, 283, 156, 92);
		frmApplicationDeGestion.getContentPane().add(leaveMenu);
		
		Button myLeavesMenuButton = new Button("Mes Congés");
		myLeavesMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		myLeavesMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, myLeavesPanel);
			}
		});
		myLeavesMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		myLeavesMenuButton.setFocusable(false);
		myLeavesMenuButton.setFocusTraversalKeysEnabled(false);
		myLeavesMenuButton.setBackground(UIManager.getColor("Button.background"));
		myLeavesMenuButton.setBounds(10, 33, 136, 22);
		leaveMenu.add(myLeavesMenuButton);
		
		Button addLeaveMenuButton = new Button("Créer un Congé");
		addLeaveMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addLeaveMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, addLeavePanel);
			}
		});
		addLeaveMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		addLeaveMenuButton.setFocusable(false);
		addLeaveMenuButton.setFocusTraversalKeysEnabled(false);
		addLeaveMenuButton.setBackground(UIManager.getColor("Button.background"));
		addLeaveMenuButton.setBounds(10, 61, 136, 22);
		leaveMenu.add(addLeaveMenuButton);
		
		JLabel leaveMenuHead = new JLabel("Congés");
		leaveMenuHead.setHorizontalAlignment(SwingConstants.CENTER);
		leaveMenuHead.setFont(new Font("Open Sans ExtraBold", Font.BOLD | Font.ITALIC, 15));
		leaveMenuHead.setBounds(10, 0, 136, 33);
		underline.accept(leaveMenuHead);
		leaveMenu.add(leaveMenuHead);
		
		Panel logoutMenu = new Panel();
		logoutMenu.setLayout(null);
		logoutMenu.setBounds(0, 399, 156, 70);
		frmApplicationDeGestion.getContentPane().add(logoutMenu);
		
		JLabel logoutMenuButton = new JLabel("");
		logoutMenuButton.setHorizontalAlignment(SwingConstants.CENTER);
		logoutMenuButton.setVerticalAlignment(SwingConstants.TOP);
		logoutMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int confirm = JOptionPane.showConfirmDialog(
			            frmApplicationDeGestion,
			            "Êtes-vous sûr de vouloir vous déconnecter ?",
			            "Confirmation de déconnexion",
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE
			        );

			        if (confirm == JOptionPane.YES_OPTION) {
			            AuthInterface authService = new AuthService();
			            authService.logout();

			            Auth authView = new Auth();
			            authView.getFrame().setVisible(true);

			            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutMenuButton);
			            currentFrame.dispose();
			        }
			}
		});
		logoutMenuButton.setIcon(new ImageIcon(EmployeeView.class.getResource("/images/logout.png")));
		logoutMenuButton.setBounds(0, 0, 156, 59);
		logoutMenu.add(logoutMenuButton);
		
		JLabel applicationLogo = new JLabel("");
		applicationLogo.setIcon(new ImageIcon(EmployeeView.class.getResource("/images/oaca.png")));
		applicationLogo.setHorizontalAlignment(SwingConstants.CENTER);
		applicationLogo.setBounds(0, 0, 70, 70);
		frmApplicationDeGestion.getContentPane().add(applicationLogo);
	}

	public JFrame getFrame() {
        return frmApplicationDeGestion;
    }
}
