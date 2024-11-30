package views;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;

import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Cursor;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import com.toedter.calendar.JDateChooser;

import models.Employee;
import models.FamilySituation;
import models.Leave;
import services.AuthInterface;
import services.AuthService;
import services.EmployeeService;
import services.LeaveInterface;
import services.LeaveService;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;

public class HRDirectorView {
	private JFrame frmApplicationDeGestion;
	private JTextField addEmployeeFirstNameField;
	private JTextField addEmployeeLastNameField;
	private JTextField addEmployeeCinField;
	private JTextField addEmployeeJobField;
	private JTextField addEmployeePhoneNumberField;
	private JTextField addEmployeeAddressField;
	private JTextField addEmployeeEmailField;
	private JTextField employeeEmailField;
	private JTextField employeePhoneNumberField;
	private JTextField employeeAddressField;
	private JTextField employeeCinField;
	private JTextField employeeIdField;
	private JTextField employeeLastNameField;
	private JTextField employeeFirstNameField;
	private JTextField employeeJobField;
	private JTextField employeeLeaveBalanceField;
	private JTextField searchEmployeeField;
	private JTextField addEmployeeLeaveBalanceField;
	private JTable table3;
	
	public void switchPanels(JLayeredPane layeredPane, Panel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	private static void loadEmployeeDataToTable(DefaultTableModel model) {
        EmployeeService employeeService = new EmployeeService();
        List<Employee> employees = employeeService.showEmployeesList();

        for (Employee employee : employees) {
            model.addRow(new Object[] {
                employee.getEmployeId(),
                employee.getNom(),
                employee.getPrenom(),
                employee.getDateNaissance(),
                employee.getSituationFamiliale(),
                employee.getEmail(),
                employee.getTelephone(),
                employee.getAdresse(),
                employee.getCin(),
                employee.getPoste(),
                employee.getSoldeConge()
            });
        }
    }
	
	private static void loadLeaveDataToTable(DefaultTableModel model) {
	    LeaveInterface leaveService = new LeaveService();
	    List<Leave> leaveList = leaveService.showLeavesInProgressByEmployee();

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
	            leave.getEtatDemande(),
	            leave.getEmployeId()
	        });
	    }
	}
	
	/**
	 * Create the application.
	 */
	public HRDirectorView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Consumer<JLabel> underline = label -> {
	        Font font = label.getFont();
	        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
	        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        label.setFont(font.deriveFont(attributes));
	    };
	    
		frmApplicationDeGestion = new JFrame();
		frmApplicationDeGestion.setIconImage(Toolkit.getDefaultToolkit().getImage(HRDirectorView.class.getResource("/images/oaca.png")));
		frmApplicationDeGestion.setResizable(false);
		frmApplicationDeGestion.getContentPane().setBackground(new Color(70, 130, 180));
		frmApplicationDeGestion.setTitle("Application de Gestion de Congés - Interface du directeur RH");
		frmApplicationDeGestion.setBounds(100, 100, 950, 605);
		frmApplicationDeGestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmApplicationDeGestion.getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(156, 0, 778, 596);
		frmApplicationDeGestion.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		Panel employeesBody = new Panel();
		layeredPane.add(employeesBody, "name_98953778732400");
		employeesBody.setLayout(null);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 778, 596);
		employeesBody.add(layeredPane_1);
		employeesBody.setBackground(SystemColor.inactiveCaptionBorder);
		layeredPane_1.setLayout(null);
		
		Panel manageEmployeesPanel = new Panel();
		manageEmployeesPanel.setBounds(0, 0, 778, 567);
		manageEmployeesPanel.setBackground(SystemColor.inactiveCaptionBorder);
		layeredPane_1.add(manageEmployeesPanel);
		manageEmployeesPanel.setLayout(null);
		
		JLabel employeeFamilySituationLabel = new JLabel("Situation Familiale");
		employeeFamilySituationLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeFamilySituationLabel.setBounds(240, 120, 173, 14);
		manageEmployeesPanel.add(employeeFamilySituationLabel);
		
		JLabel employeeEmailLabel = new JLabel("Email");
		employeeEmailLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeEmailLabel.setBounds(240, 145, 126, 14);
		manageEmployeesPanel.add(employeeEmailLabel);
		
		JLabel employeePhoneNumberLabel = new JLabel("Numéro de Téléphone");
		employeePhoneNumberLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeePhoneNumberLabel.setBounds(240, 170, 138, 14);
		manageEmployeesPanel.add(employeePhoneNumberLabel);
		
		JLabel employeeAddressLabel = new JLabel("Adresse");
		employeeAddressLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeAddressLabel.setBounds(240, 195, 119, 14);
		manageEmployeesPanel.add(employeeAddressLabel);
		
		JLabel employeeCinLabel = new JLabel("CIN");
		employeeCinLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeCinLabel.setBounds(240, 220, 126, 14);
		manageEmployeesPanel.add(employeeCinLabel);
		
		JLabel employeeJobLabel = new JLabel("Poste");
		employeeJobLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeJobLabel.setBounds(240, 245, 126, 14);
		manageEmployeesPanel.add(employeeJobLabel);
		
		JDateChooser employeeBirthDateField = new JDateChooser();
		employeeBirthDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeBirthDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeBirthDateField.setBounds(383, 95, 110, 20);
		manageEmployeesPanel.add(employeeBirthDateField);
		
		employeeEmailField = new JTextField();
		employeeEmailLabel.setLabelFor(employeeEmailField);
		employeeEmailField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeEmailField.setColumns(10);
		employeeEmailField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeEmailField.setBounds(383, 143, 110, 20);
		manageEmployeesPanel.add(employeeEmailField);
		
		employeePhoneNumberField = new JTextField();
		employeePhoneNumberLabel.setLabelFor(employeePhoneNumberField);
		employeePhoneNumberField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeePhoneNumberField.setColumns(10);
		employeePhoneNumberField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeePhoneNumberField.setBounds(383, 168, 110, 20);
		manageEmployeesPanel.add(employeePhoneNumberField);
		
		employeeAddressField = new JTextField();
		employeeAddressLabel.setLabelFor(employeeAddressField);
		employeeAddressField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeAddressField.setColumns(10);
		employeeAddressField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeAddressField.setBounds(383, 193, 110, 20);
		manageEmployeesPanel.add(employeeAddressField);
		
		employeeCinField = new JTextField();
		employeeCinLabel.setLabelFor(employeeCinField);
		employeeCinField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeCinField.setColumns(10);
		employeeCinField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeCinField.setBounds(383, 218, 110, 20);
		manageEmployeesPanel.add(employeeCinField);
		
		JComboBox<FamilySituation> employeeFamilySituationField = new JComboBox<>();
		employeeFamilySituationLabel.setLabelFor(employeeFamilySituationField);
		employeeFamilySituationField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeFamilySituationField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeFamilySituationField.setModel(new DefaultComboBoxModel<>(FamilySituation.values()));
		employeeFamilySituationField.setRenderer(new DefaultListCellRenderer() {
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
		employeeFamilySituationField.setBounds(383, 120, 110, 20);
		manageEmployeesPanel.add(employeeFamilySituationField);
		
		JButton updateEmployeeButton = new JButton("Modifier un employé");
		updateEmployeeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateEmployeeButton.setBorder(new CompoundBorder());
		updateEmployeeButton.setBackground(UIManager.getColor("Button.background"));
		
		updateEmployeeButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		updateEmployeeButton.setBounds(175, 302, 179, 23);
		manageEmployeesPanel.add(updateEmployeeButton);
		
		JButton deleteEmployeeButton = new JButton("Supprimer un employé");
		deleteEmployeeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteEmployeeButton.setBorder(new CompoundBorder());
		
		deleteEmployeeButton.setBackground(UIManager.getColor("Button.shadow"));
		deleteEmployeeButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		deleteEmployeeButton.setBounds(383, 302, 179, 23);
		manageEmployeesPanel.add(deleteEmployeeButton);
		
		JLabel employeeIdLabel = new JLabel("ID de l'Employé");
		employeeIdLabel.setVerticalAlignment(SwingConstants.TOP);
		employeeIdLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeIdLabel.setBounds(240, 17, 100, 20);
		manageEmployeesPanel.add(employeeIdLabel);
		
		employeeIdField = new JTextField();
		employeeIdLabel.setLabelFor(employeeIdField);
		employeeIdField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeIdField.setColumns(10);
		employeeIdField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeIdField.setBounds(383, 17, 110, 20);
		manageEmployeesPanel.add(employeeIdField);
		
		JLabel employeeLastNameLabel = new JLabel("Nom");
		employeeLastNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeLastNameLabel.setBounds(240, 42, 98, 14);
		manageEmployeesPanel.add(employeeLastNameLabel);
		
		employeeLastNameField = new JTextField();
		employeeLastNameLabel.setLabelFor(employeeLastNameField);
		employeeLastNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeLastNameField.setColumns(10);
		employeeLastNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeLastNameField.setBounds(383, 42, 110, 20);
		manageEmployeesPanel.add(employeeLastNameField);
		
		employeeFirstNameField = new JTextField();
		employeeFirstNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeFirstNameField.setColumns(10);
		employeeFirstNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeFirstNameField.setBounds(383, 67, 110, 20);
		manageEmployeesPanel.add(employeeFirstNameField);
		
		JLabel employeeFirstNameLabel = new JLabel("Prénom");
		employeeFirstNameLabel.setLabelFor(employeeFirstNameField);
		employeeFirstNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeFirstNameLabel.setBounds(240, 67, 119, 14);
		manageEmployeesPanel.add(employeeFirstNameLabel);
		
		JLabel employeeBirthDateLabel = new JLabel("Date de Naissance");
		employeeBirthDateLabel.setLabelFor(employeeBirthDateField);
		employeeBirthDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeBirthDateLabel.setBounds(240, 95, 162, 14);
		manageEmployeesPanel.add(employeeBirthDateLabel);
		
		DefaultTableModel model = new DefaultTableModel(
	            new String[] {
	                "ID de l'employé", "Nom", "Prénom", "Date de naissance", "Situation familiale", "Email", 
	                "Numéro de Téléphone", "Adresse", "CIN", "Poste", "Solde congé"
	            },
	            0
	        );
		
		JTable table1 = new JTable(model);
		table1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table1.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollPane1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
				table1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
				            int selectedRow = table1.getSelectedRow();
				            
				            int id = (int) table1.getValueAt(selectedRow, 0);
				            String nom = table1.getValueAt(selectedRow, 1).toString();
				            String prenom = table1.getValueAt(selectedRow, 2).toString();
				            Date dateNaissance = (Date) table1.getValueAt(selectedRow, 3);
				            FamilySituation situation = FamilySituation.valueOf(table1.getValueAt(selectedRow, 4).toString());
				            String email = table1.getValueAt(selectedRow, 5).toString();
				            String telephone = table1.getValueAt(selectedRow, 6).toString();
				            String adresse = table1.getValueAt(selectedRow, 7).toString();
				            String cin = table1.getValueAt(selectedRow, 8).toString();
				            String poste = table1.getValueAt(selectedRow, 9).toString();
				            int solde = (int) table1.getValueAt(selectedRow, 10);
				            
				            employeeIdField.setText(String.valueOf(id));
				            employeeLastNameField.setText(nom);
				            employeeFirstNameField.setText(prenom);
				            employeeEmailField.setText(email);
				            employeeFamilySituationField.setSelectedItem(situation);
				            employeePhoneNumberField.setText(telephone);
				            employeeAddressField.setText(adresse);
				            employeeCinField.setText(cin);
				            employeeJobField.setText(poste);
				            employeeBirthDateField.setDate(dateNaissance);
				            employeeLeaveBalanceField.setText(String.valueOf(solde));
					}
				});
				
				scrollPane1.setViewportView(table1);
				
						scrollPane1.setBounds(10, 343, 756, 166);
						manageEmployeesPanel.add(scrollPane1);
						
		JLabel employeeLeaveBalanceLabel = new JLabel("Solde Congé");
		employeeLeaveBalanceLabel.setVerticalAlignment(SwingConstants.TOP);
		employeeLeaveBalanceLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		employeeLeaveBalanceLabel.setBounds(240, 268, 126, 22);
		manageEmployeesPanel.add(employeeLeaveBalanceLabel);
		
		employeeJobField = new JTextField();
		employeeJobLabel.setLabelFor(employeeJobField);
		employeeJobField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeJobField.setColumns(10);
		employeeJobField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeJobField.setBounds(383, 243, 110, 20);
		manageEmployeesPanel.add(employeeJobField);
		
		employeeLeaveBalanceField = new JTextField();
		employeeLeaveBalanceLabel.setLabelFor(employeeLeaveBalanceField);
		employeeLeaveBalanceField.setFont(new Font("Arial", Font.PLAIN, 11));
		employeeLeaveBalanceField.setColumns(10);
		employeeLeaveBalanceField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		employeeLeaveBalanceField.setBounds(383, 268, 110, 20);
		manageEmployeesPanel.add(employeeLeaveBalanceField);
		
		searchEmployeeField = new JTextField();
		searchEmployeeField.setColumns(10);
		searchEmployeeField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		searchEmployeeField.setBounds(240, 524, 110, 20);
		manageEmployeesPanel.add(searchEmployeeField);
		
		JButton searchEmployeeButton = new JButton("Rechercher par nom / prénom");
		searchEmployeeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchEmployeeButton.setBorder(new CompoundBorder());
		searchEmployeeButton.setBackground(UIManager.getColor("Button.background"));
		searchEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            String searchItem = searchEmployeeField.getText().trim();

		            if (searchItem.isEmpty()) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, 
		                    "Veuillez saisir un critère de recherche.", 
		                    "Avertissement", 
		                    JOptionPane.WARNING_MESSAGE);
		                model.setRowCount(0);
		                loadEmployeeDataToTable(model);
		                return;
		            }

		            EmployeeService employeService = new EmployeeService();
		            List<Employee> results = employeService.searchEmployee(searchItem);

		            if (results.isEmpty()) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, 
		                    "Aucun employé trouvé pour le critère de recherche.", 
		                    "Information", 
		                    JOptionPane.INFORMATION_MESSAGE);
		            }

		            model.setRowCount(0);
		            for (Employee employee : results) {
		                model.addRow(new Object[]{
		                    employee.getEmployeId(),
		                    employee.getNom(),
		                    employee.getPrenom(),
		                    employee.getDateNaissance(),
		                    employee.getSituationFamiliale(),
		                    employee.getEmail(),
		                    employee.getTelephone(),
		                    employee.getAdresse(),
		                    employee.getCin(),
		                    employee.getPoste(),
		                    employee.getSoldeConge()
		                });
		            }

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, 
		                "Erreur : " + ex.getMessage(), 
		                "Erreur", 
		                JOptionPane.ERROR_MESSAGE);
		        }

			}
		});
		searchEmployeeButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		searchEmployeeButton.setBounds(383, 524, 179, 23);
		manageEmployeesPanel.add(searchEmployeeButton);
		
		deleteEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					 String id = employeeIdField.getText().trim();
					 
					 if (id.isEmpty()) {
						 JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez sélectionner un employé à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
			             return;
			         }

			         int confirmation = JOptionPane.showConfirmDialog(
			        		 frmApplicationDeGestion,
			                 "Êtes-vous sûr de vouloir supprimer cet employé ?",
			                 "Confirmation de suppression",
			                 JOptionPane.YES_NO_OPTION,
			                 JOptionPane.WARNING_MESSAGE
			         );

			         if (confirmation == JOptionPane.YES_OPTION) {
			        	 EmployeeService employeeService = new EmployeeService();
			        	 employeeService.deleteEmployee(Integer.valueOf(id));
			        	 
			        	 model.setRowCount(0);
			             loadEmployeeDataToTable(model);

			             employeeIdField.setText("");
			             employeeLastNameField.setText("");
			             employeeFirstNameField.setText("");
			             employeeBirthDateField.setDate(null);
			             employeeFamilySituationField.setSelectedIndex(0);
			             employeeEmailField.setText("");
			             employeePhoneNumberField.setText("");
			             employeeAddressField.setText("");
			             employeeCinField.setText("");
			             employeeJobField.setText("");
			             employeeLeaveBalanceField.setText("");

			             JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'employé a été supprimé avec succès !");
			         }        
				 } catch (Exception ex) {
			         JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		      }
			}
		});
		
		updateEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int employeeId = Integer.valueOf(employeeIdField.getText());
                    String lastName = employeeLastNameField.getText();
                    String firstName = employeeFirstNameField.getText();
                    Date birthDate = employeeBirthDateField.getDate();
                    FamilySituation familySituation = (FamilySituation) employeeFamilySituationField.getSelectedItem();
                    String email = employeeEmailField.getText();
                    String phoneNumber = employeePhoneNumberField.getText();
                    String address = employeeAddressField.getText();
                    String cin = employeeCinField.getText();
                    String job = employeeJobField.getText();
                    String leaveBalanceText = employeeLeaveBalanceField.getText().trim();

                    if (lastName.isEmpty() || firstName.isEmpty() || birthDate == null || familySituation == null || 
                    		email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || 
                    		cin.isEmpty() || job.isEmpty() || leaveBalanceText.isEmpty()) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
                    
                    int leaveBalance;
                    try {
                        leaveBalance = Integer.parseInt(leaveBalanceText);
                        if (leaveBalance < 0) {
                            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le solde de congé doit être un nombre positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez saisir un nombre valide pour le solde de congé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'email n'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!phoneNumber.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le numéro de téléphone doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!cin.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le CIN doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    } 
                    
                    EmployeeService employeService = new EmployeeService();
                    employeService.updateEmployee(Integer.valueOf(employeeId), lastName, firstName, new java.sql.Date(birthDate.getTime()), familySituation, email, phoneNumber, address, cin, job, leaveBalance);

                    model.setRowCount(0);
                    loadEmployeeDataToTable(model);

                    employeeIdField.setText("");
                    employeeLastNameField.setText("");
                    employeeFirstNameField.setText("");
                    employeeBirthDateField.setDate(null);
                    employeeFamilySituationField.setSelectedIndex(0);
                    employeeEmailField.setText("");
                    employeePhoneNumberField.setText("");
                    employeeAddressField.setText("");
                    employeeCinField.setText("");
                    employeeJobField.setText("");
                    employeeLeaveBalanceField.setText("");

                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'employé a été modifié avec succès !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		
		Panel addEmployeePanel = new Panel();
		addEmployeePanel.setBounds(0, 0, 778, 567);
		addEmployeePanel.setBackground(SystemColor.inactiveCaptionBorder);
		layeredPane_1.add(addEmployeePanel);
		addEmployeePanel.setLayout(null);
		
		addEmployeeFirstNameField = new JTextField();
		addEmployeeFirstNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeFirstNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeFirstNameField.setBounds(426, 37, 110, 20);
		addEmployeePanel.add(addEmployeeFirstNameField);
		addEmployeeFirstNameField.setColumns(10);
		
		JLabel addEmployeeLastNameLabel = new JLabel("Nom");
		addEmployeeLastNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeLastNameLabel.setBounds(250, 13, 68, 14);
		addEmployeePanel.add(addEmployeeLastNameLabel);
		
		addEmployeeLastNameField = new JTextField();
		addEmployeeLastNameLabel.setLabelFor(addEmployeeLastNameField);
		addEmployeeLastNameField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeLastNameField.setColumns(10);
		addEmployeeLastNameField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeLastNameField.setBounds(426, 11, 110, 20);
		addEmployeePanel.add(addEmployeeLastNameField);
		
		JLabel addEmployeeFirstNameLabel = new JLabel("Prénom");
		addEmployeeFirstNameLabel.setLabelFor(addEmployeeFirstNameField);
		addEmployeeFirstNameLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeFirstNameLabel.setBounds(250, 38, 89, 14);
		addEmployeePanel.add(addEmployeeFirstNameLabel);
		
		JLabel addEmployeeBirthDateLabel = new JLabel("Date de Naissance");
		addEmployeeBirthDateLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeBirthDateLabel.setBounds(250, 63, 132, 14);
		addEmployeePanel.add(addEmployeeBirthDateLabel);
		
		JLabel addEmployeeFamilySituationLabel = new JLabel("Situation Familiale");
		addEmployeeFamilySituationLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeFamilySituationLabel.setBounds(250, 88, 148, 14);
		addEmployeePanel.add(addEmployeeFamilySituationLabel);
		
		JLabel addEmployeeEmailLabel = new JLabel("Email");
		addEmployeeEmailLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeEmailLabel.setBounds(250, 114, 148, 14);
		addEmployeePanel.add(addEmployeeEmailLabel);
		
		JLabel addEmployeePhoneNumberLabel = new JLabel("Numéro de Téléphone");
		addEmployeePhoneNumberLabel.setVerticalAlignment(SwingConstants.TOP);
		addEmployeePhoneNumberLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeePhoneNumberLabel.setBounds(250, 139, 148, 20);
		addEmployeePanel.add(addEmployeePhoneNumberLabel);
		
		JLabel addEmployeeAddressLabel = new JLabel("Adresse");
		addEmployeeAddressLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeAddressLabel.setBounds(250, 164, 148, 14);
		addEmployeePanel.add(addEmployeeAddressLabel);
		
		JLabel addEmployeeCinLabel = new JLabel("CIN");
		addEmployeeCinLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeCinLabel.setBounds(250, 189, 148, 14);
		addEmployeePanel.add(addEmployeeCinLabel);
		
		JLabel addEmployeeJobLabel = new JLabel("Poste");
		addEmployeeJobLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeJobLabel.setBounds(250, 214, 148, 14);
		addEmployeePanel.add(addEmployeeJobLabel);
		
		JDateChooser addEmployeeBirthDateField = new JDateChooser();
		addEmployeeBirthDateField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeBirthDateLabel.setLabelFor(addEmployeeBirthDateField);
		addEmployeeBirthDateField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeBirthDateField.setBounds(426, 63, 110, 20);
		addEmployeePanel.add(addEmployeeBirthDateField);
		
		addEmployeeCinField = new JTextField();
		addEmployeeCinField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeCinLabel.setLabelFor(addEmployeeCinField);
		addEmployeeCinField.setColumns(10);
		addEmployeeCinField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeCinField.setBounds(426, 189, 110, 20);
		addEmployeePanel.add(addEmployeeCinField);
		
		addEmployeeJobField = new JTextField();
		addEmployeeJobField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeJobLabel.setLabelFor(addEmployeeJobField);
		addEmployeeJobField.setColumns(10);
		addEmployeeJobField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeJobField.setBounds(426, 214, 110, 20);
		addEmployeePanel.add(addEmployeeJobField);
		
		addEmployeePhoneNumberField = new JTextField();
		addEmployeePhoneNumberField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeePhoneNumberLabel.setLabelFor(addEmployeePhoneNumberField);
		addEmployeePhoneNumberField.setColumns(10);
		addEmployeePhoneNumberField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeePhoneNumberField.setBounds(426, 139, 110, 20);
		addEmployeePanel.add(addEmployeePhoneNumberField);
		
		addEmployeeAddressField = new JTextField();
		addEmployeeAddressField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeAddressLabel.setLabelFor(addEmployeeAddressField);
		addEmployeeAddressField.setColumns(10);
		addEmployeeAddressField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeAddressField.setBounds(426, 164, 110, 20);
		addEmployeePanel.add(addEmployeeAddressField);
		
		addEmployeeEmailField = new JTextField();
		addEmployeeEmailField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeEmailLabel.setLabelFor(addEmployeeEmailField);
		addEmployeeEmailField.setColumns(10);
		addEmployeeEmailField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeEmailField.setBounds(426, 112, 110, 20);
		addEmployeePanel.add(addEmployeeEmailField);
		
		JComboBox<FamilySituation> addEmployeeFamilySituationField = new JComboBox<>();
		addEmployeeFamilySituationField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeFamilySituationLabel.setLabelFor(addEmployeeFamilySituationField);
		addEmployeeFamilySituationField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeFamilySituationField.setModel(new DefaultComboBoxModel<>(FamilySituation.values()));
		addEmployeeFamilySituationField.setRenderer(new DefaultListCellRenderer() {
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
		addEmployeeFamilySituationField.setBounds(426, 88, 110, 19);
		addEmployeePanel.add(addEmployeeFamilySituationField);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollPane2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane2.setBounds(10, 311, 758, 233);
		addEmployeePanel.add(scrollPane2);
		
		JTable table2 = new JTable(model);
		table2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table2.setFont(new Font("Arial", Font.PLAIN, 11));
		
		scrollPane2.setViewportView(table2);
		
		JButton addEmployeeButton = new JButton("Ajouter un employé");
		addEmployeeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addEmployeeButton.setBackground(UIManager.getColor("Button.background"));
		addEmployeeButton.setBorder(new CompoundBorder());
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    String lastName = addEmployeeLastNameField.getText().trim();
                    String firstName = addEmployeeFirstNameField.getText().trim();
                    Date birthDate = addEmployeeBirthDateField.getDate();
                    FamilySituation familySituation = (FamilySituation) addEmployeeFamilySituationField.getSelectedItem();
                    String email = addEmployeeEmailField.getText().trim();
                    String phoneNumber = addEmployeePhoneNumberField.getText().trim();
                    String address = addEmployeeAddressField.getText().trim();
                    String cin = addEmployeeCinField.getText().trim();
                    String job = addEmployeeJobField.getText().trim();
                    String leaveBalanceText = addEmployeeLeaveBalanceField.getText().trim();

                    addEmployeeButton.setEnabled(false);
		            frmApplicationDeGestion.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
                    
                    if (lastName.isEmpty() || firstName.isEmpty() || birthDate == null || familySituation == null || 
                    		email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || 
                    		cin.isEmpty() || job.isEmpty() || leaveBalanceText.isEmpty()) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
                    
                    int leaveBalance;
                    try {
                        leaveBalance = Integer.parseInt(leaveBalanceText);
                        if (leaveBalance < 0) {
                            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le solde de congé doit être un nombre positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez saisir un nombre valide pour le solde de congé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "L'email n'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!phoneNumber.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le numéro de téléphone doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!cin.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le CIN doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    } 
                    
                    EmployeeService employeService = new EmployeeService();
                    employeService.createEmployee(lastName, firstName, new java.sql.Date(birthDate.getTime()),
                            familySituation, email, phoneNumber, address, cin, job, leaveBalance);

                    model.setRowCount(0);
                    loadEmployeeDataToTable(model);

                    addEmployeeLastNameField.setText("");
                    addEmployeeFirstNameField.setText("");
                    addEmployeeBirthDateField.setDate(null);
                    addEmployeeFamilySituationField.setSelectedIndex(0);
                    addEmployeeEmailField.setText("");
                    addEmployeePhoneNumberField.setText("");
                    addEmployeeAddressField.setText("");
                    addEmployeeCinField.setText("");
                    addEmployeeJobField.setText("");
                    addEmployeeLeaveBalanceField.setText("");

                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "Employé ajouté avec succès !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } finally {
		        	addEmployeeButton.setEnabled(true);
	                frmApplicationDeGestion.setCursor(Cursor.getDefaultCursor());
	            }
			}
		});
				
		addEmployeeButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		addEmployeeButton.setBounds(301, 272, 179, 23);
		addEmployeePanel.add(addEmployeeButton);
				
		JLabel addEmployeeLeaveBalanceLabel = new JLabel("Solde Congé");
		addEmployeeLeaveBalanceLabel.setVerticalAlignment(SwingConstants.TOP);
		addEmployeeLeaveBalanceLabel.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		addEmployeeLeaveBalanceLabel.setBounds(250, 239, 148, 20);
		addEmployeePanel.add(addEmployeeLeaveBalanceLabel);
				
		addEmployeeLeaveBalanceField = new JTextField();
		addEmployeeLeaveBalanceLabel.setLabelFor(addEmployeeLeaveBalanceField);
		addEmployeeLeaveBalanceField.setFont(new Font("Arial", Font.PLAIN, 11));
		addEmployeeLeaveBalanceField.setColumns(10);
		addEmployeeLeaveBalanceField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addEmployeeLeaveBalanceField.setBounds(426, 239, 110, 20);
		addEmployeePanel.add(addEmployeeLeaveBalanceField);
		loadEmployeeDataToTable(model);
		
		Panel leavesBody = new Panel();
		layeredPane.add(leavesBody, "name_98957748537000");
		leavesBody.setBackground(SystemColor.inactiveCaptionBorder);
		leavesBody.setLayout(null);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane3.setFont(new Font("Arial", Font.PLAIN, 11));
		scrollPane3.setBounds(10, 63, 758, 435);
		leavesBody.add(scrollPane3);
		
		table3 = new JTable();
		DefaultTableModel model3 = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID du congé", "Nom du congé", "Description", "Date de la demande", "Date début", "Date fin", "Nombre de jours", "Type de congé", "État de la demande", "ID de l'employé"
				}
	        );
		table3.setModel(model3);
		scrollPane3.setViewportView(table3);
		table3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table3.setFont(new Font("Arial", Font.PLAIN, 11));
		loadLeaveDataToTable(model3);
		
		JButton validateLeaveButton = new JButton("Valider congé");
		validateLeaveButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        try {
			            int selectedRow = table3.getSelectedRow();

			            if (selectedRow == -1) {
			                JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez sélectionner une ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
			                return;
			            }

			            int congeId = (int) table3.getValueAt(selectedRow, 0);

			            int confirmation = JOptionPane.showConfirmDialog(
			                frmApplicationDeGestion,
			                "Êtes-vous sûr de vouloir valider ce congé ?",
			                "Confirmation",
			                JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE
			            );

			            if (confirmation == JOptionPane.YES_OPTION) {
			            	validateLeaveButton.setEnabled(false);
			                frmApplicationDeGestion.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			                
			                LeaveService leaveService = new LeaveService();
			                leaveService.validateLeave(congeId);

			                model.setRowCount(0);
				            loadEmployeeDataToTable(model);
				            model3.setRowCount(0);
				            loadLeaveDataToTable(model3);

			                JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le congé a été validé avec succès !");
			            }
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur lors de la validation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			        } finally {
			        	validateLeaveButton.setEnabled(true);
		                frmApplicationDeGestion.setCursor(Cursor.getDefaultCursor());
		            }
			    }
		});
		validateLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		validateLeaveButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		validateLeaveButton.setBorder(new CompoundBorder());
		validateLeaveButton.setBackground(UIManager.getColor("Button.background"));
		validateLeaveButton.setBounds(177, 515, 179, 23);
		leavesBody.add(validateLeaveButton);
		
		JButton refuseLeaveButton = new JButton("Refuser Congé");
		refuseLeaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            int selectedRow = table3.getSelectedRow();

		            if (selectedRow == -1) {
		                JOptionPane.showMessageDialog(frmApplicationDeGestion, "Veuillez sélectionner une ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            int congeId = (int) table3.getValueAt(selectedRow, 0);

		            int confirmation = JOptionPane.showConfirmDialog(
			                frmApplicationDeGestion,
			                "Êtes-vous sûr de vouloir valider ce congé ?",
			                "Confirmation",
			                JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE
			            );
		            
		            if (confirmation == JOptionPane.YES_OPTION) {
		            	refuseLeaveButton.setEnabled(false);
		                frmApplicationDeGestion.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		                
			            LeaveInterface leaveService = new LeaveService();
			            leaveService.refuseLeave(congeId);
	
			            model.setRowCount(0);
			            loadEmployeeDataToTable(model);
			            model3.setRowCount(0);
			            loadLeaveDataToTable(model3);
	
			            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Le congé a été refusé avec succès !");
		            }
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(frmApplicationDeGestion, "Erreur lors de la validation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		        } finally {
	                refuseLeaveButton.setEnabled(true);
	                frmApplicationDeGestion.setCursor(Cursor.getDefaultCursor());
	            }
			}
		});
		refuseLeaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refuseLeaveButton.setFont(new Font("Open Sans", Font.ITALIC, 11));
		refuseLeaveButton.setBorder(new CompoundBorder());
		refuseLeaveButton.setBackground(UIManager.getColor("Button.shadow"));
		refuseLeaveButton.setBounds(385, 515, 179, 23);
		leavesBody.add(refuseLeaveButton);
		
		JLabel messageInfo = new JLabel("Veuillez sélectionner une demande de congé..");
		messageInfo.setHorizontalAlignment(SwingConstants.CENTER);
		messageInfo.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 13));
		messageInfo.setBounds(10, 11, 758, 39);
		leavesBody.add(messageInfo);
		
		Panel employeesMenu = new Panel();
		employeesMenu.setLayout(null);
		employeesMenu.setBounds(0, 139, 156, 138);
		frmApplicationDeGestion.getContentPane().add(employeesMenu);
		
		Button manageEmployeesMenuButton = new Button("Gérer Employés");
		manageEmployeesMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, manageEmployeesPanel);
			}
		});
		manageEmployeesMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		manageEmployeesMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		manageEmployeesMenuButton.setFocusable(false);
		manageEmployeesMenuButton.setFocusTraversalKeysEnabled(false);
		manageEmployeesMenuButton.setBackground(SystemColor.menu);
		manageEmployeesMenuButton.setBounds(10, 33, 136, 22);
		employeesMenu.add(manageEmployeesMenuButton);
		
		Button addEmployeeMenuButton = new Button("Créer un Employé");
		addEmployeeMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, addEmployeePanel);
			}
		});
		addEmployeeMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addEmployeeMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		addEmployeeMenuButton.setFocusable(false);
		addEmployeeMenuButton.setFocusTraversalKeysEnabled(false);
		addEmployeeMenuButton.setBackground(SystemColor.menu);
		addEmployeeMenuButton.setBounds(10, 60, 136, 22);
		employeesMenu.add(addEmployeeMenuButton);
		
		JLabel employeesMenuHead = new JLabel("Employés");
		employeesMenuHead.setHorizontalAlignment(SwingConstants.CENTER);
		employeesMenuHead.setFont(new Font("Open Sans ExtraBold", Font.ITALIC, 15));
		employeesMenuHead.setBounds(0, 0, 156, 34);
		underline.accept(employeesMenuHead);
		employeesMenu.add(employeesMenuHead);
		
		Panel leavesMenu = new Panel();
		leavesMenu.setLayout(null);
		leavesMenu.setBounds(0, 283, 156, 92);
		frmApplicationDeGestion.getContentPane().add(leavesMenu);
		
		Button leavesMenuButton = new Button("Gérer Congés");
		leavesMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(layeredPane, leavesBody);
			}
		});
		leavesMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leavesMenuButton.setFont(new Font("Open Sans", Font.BOLD, 12));
		leavesMenuButton.setFocusable(false);
		leavesMenuButton.setFocusTraversalKeysEnabled(false);
		leavesMenuButton.setBackground(SystemColor.menu);
		leavesMenuButton.setBounds(10, 33, 136, 22);
		leavesMenu.add(leavesMenuButton);
		
		JLabel leavesMenuHead = new JLabel("Congés");
		leavesMenuHead.setHorizontalAlignment(SwingConstants.CENTER);
		leavesMenuHead.setFont(new Font("Open Sans ExtraBold", Font.BOLD | Font.ITALIC, 15));
		leavesMenuHead.setBounds(10, 0, 136, 33);
		underline.accept(leavesMenuHead);
		leavesMenu.add(leavesMenuHead);
		
		Panel logoutMenu = new Panel();
		logoutMenu.setLayout(null);
		logoutMenu.setBounds(0, 399, 156, 70);
		frmApplicationDeGestion.getContentPane().add(logoutMenu);
		
		JLabel logoutMenuButton = new JLabel("");
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
		logoutMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutMenuButton.setIcon(new ImageIcon(HRDirectorView.class.getResource("/images/logout.png")));
		logoutMenuButton.setVerticalAlignment(SwingConstants.TOP);
		logoutMenuButton.setHorizontalAlignment(SwingConstants.CENTER);
		logoutMenuButton.setBounds(0, 0, 156, 59);
		logoutMenu.add(logoutMenuButton);
		
		JLabel applicationLogo = new JLabel("");
		applicationLogo.setIcon(new ImageIcon(HRDirectorView.class.getResource("/images/oaca.png")));
		applicationLogo.setHorizontalAlignment(SwingConstants.CENTER);
		applicationLogo.setBounds(0, 0, 70, 70);
		frmApplicationDeGestion.getContentPane().add(applicationLogo);
	}
	
	public JFrame getFrame() {
        return frmApplicationDeGestion;
    }
}
