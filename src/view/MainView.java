package view;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.naming.NamingException;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

import metier.beans.SmartPhone;
import metier.beans.User;
import metier.smartphoneejb.SmartPhoneRemote;
import metier.userejb.UserRemote;
import test.Test;
import javax.swing.JComboBox;

public class MainView {

	private JFrame frame;
	private JTextField userNameInput;
	private JTextField userPrenomInput;
	private JTextField userTelInput;
	private JTextField userEmailInput;
	private JTable userTable;
	private User selectedUser = null;
	private SmartPhone selectedPhone = null;
	UserRemote userRemote = null;
	SmartPhoneRemote smartPhoneRemote = null;
	private JTextField phoneRefInput;
	private JTextField phoneLabelInput;
	private JTextField phoneMarqueInput;
	private JTable smartPhoneTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws NamingException
	 */
	public MainView() throws NamingException {
		initliazeRemote();
		initialize();
	}

	public void initliazeRemote() throws NamingException {
		this.userRemote = Test.lookUpUserRemoteStateless("ejb:/EjbDemo/UserSession!metier.userejb.UserRemote");
		this.smartPhoneRemote = Test
				.lookUpUserRemoteStateless("ejb:/EjbDemo/SmartPhoneSession!metier.smartphoneejb.SmartPhoneRemote");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 924, 608);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 890, 551);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Gestion Utilisateur", null, panel, null);
		panel.setLayout(null);

		JLabel userNameLabel = new JLabel("Name :");
		userNameLabel.setBounds(50, 39, 79, 23);
		panel.add(userNameLabel);

		JLabel userPrenomLabel = new JLabel("Prenom :");
		userPrenomLabel.setBounds(50, 80, 79, 23);
		panel.add(userPrenomLabel);

		JLabel userTelLabel = new JLabel("Telephone :");
		userTelLabel.setBounds(50, 121, 79, 23);
		panel.add(userTelLabel);

		JLabel userEmailLabel = new JLabel("Email :");
		userEmailLabel.setBounds(50, 159, 79, 23);
		panel.add(userEmailLabel);

		JLabel userDateNaissanceLabel = new JLabel("Date naissance :");
		userDateNaissanceLabel.setBounds(50, 210, 79, 23);
		panel.add(userDateNaissanceLabel);

		userNameInput = new JTextField();
		userNameInput.setBounds(169, 41, 206, 19);
		panel.add(userNameInput);
		userNameInput.setColumns(10);

		userPrenomInput = new JTextField();
		userPrenomInput.setColumns(10);
		userPrenomInput.setBounds(169, 82, 206, 19);
		panel.add(userPrenomInput);

		userTelInput = new JTextField();
		userTelInput.setColumns(10);
		userTelInput.setBounds(169, 123, 206, 19);
		panel.add(userTelInput);

		userEmailInput = new JTextField();
		userEmailInput.setColumns(10);
		userEmailInput.setBounds(169, 161, 206, 19);
		panel.add(userEmailInput);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(385, 39, 490, 368);
		panel.add(scrollPane);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(169, 214, 206, 19);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		panel.add(dateChooser);

		JButton deleteUserButton = new JButton("Delete");
		deleteUserButton.setBounds(183, 283, 85, 21);
		deleteUserButton.addActionListener(e -> {
			if (selectedUser == null) {
				JOptionPane.showMessageDialog(frame, "Please Select A User from the Table Below");
			} else {
				int result = JOptionPane.showConfirmDialog(frame, "Sure you want to delete this User ? ", "Delete User",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					for (SmartPhone sp : smartPhoneRemote.findAll()) {
						if (sp.getUser() != null) {
							if (sp.getUser().getId().equals(selectedUser.getId())) {
								sp.setUser(null);
								smartPhoneRemote.update(sp);
							}
						}
					}
					int transactionResult = userRemote.deleteById(selectedUser.getId());
					if (transactionResult > 0) {
						JOptionPane.showMessageDialog(frame, "User Deleted successfully !");
						updateUserTableData(userTable);
						updatePhoneTableData(smartPhoneTable);
						selectedUser = null;
					}
				}
			}
		});
		panel.add(deleteUserButton);

		userTable = new JTable();
		scrollPane.setViewportView(userTable);
		userTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nom", "Prenom", "Telephone", "Email", "Date Naissance" }));
		updateUserTableData(userTable);
		userTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedEmail = userTable.getModel().getValueAt(userTable.getSelectedRow(), 3).toString();
				System.out.println(selectedEmail);
				selectedUser = userRemote.findByEmail(selectedEmail);
				if (selectedUser != null) {
					userNameInput.setText(selectedUser.getNom());
					userPrenomInput.setText(selectedUser.getPrenom());
					userEmailInput.setText(selectedUser.getEmail());
					userTelInput.setText(selectedUser.getTelephone());
					dateChooser.setDate(selectedUser.getDateNaissance());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Gestion Smart Phone et affectation", null, panel_1, null);
		panel_1.setLayout(null);

		JComboBox<String> phoneUserComboBox = new JComboBox<String>();
		phoneUserComboBox.setEditable(true);
		phoneUserComboBox.setBounds(166, 160, 203, 21);
		phoneUserComboBox.addItem(null);
		for (User u : userRemote.findAll()) {
			phoneUserComboBox.addItem(u.getEmail());
		}
		panel_1.add(phoneUserComboBox);

		JButton saveUserButton = new JButton("Save");
		saveUserButton.addActionListener((e) -> {
			if (selectedUser != null) {
				selectedUser.setNom(userNameInput.getText());
				selectedUser.setPrenom(userPrenomInput.getText());
				selectedUser.setEmail(userEmailInput.getText());
				selectedUser.setTelephone(userTelInput.getText());
				selectedUser.setDateNaissance(dateChooser.getDate());
				int result = userRemote.update(selectedUser);
				if (result > 0) {
					JOptionPane.showMessageDialog(frame, "User Updated successfully !");
					updateUserTableData(userTable);
					updateUsersComboBox(phoneUserComboBox);
					selectedUser = null;
				}
			} else {
				int result = userRemote.save(new User(userNameInput.getText(), userPrenomInput.getText(),
						userTelInput.getText(), userEmailInput.getText(), dateChooser.getDate()));
				if (result > 0) {
					JOptionPane.showMessageDialog(frame, "User Saved successfully !");
					updateUserTableData(userTable);
					updateUsersComboBox(phoneUserComboBox);
				}
			}
		});
		saveUserButton.setBounds(65, 283, 85, 21);
		panel.add(saveUserButton);

		JButton cancelUserButton = new JButton("Cancel");
		cancelUserButton.addActionListener(e -> {
			selectedUser = null;
			userNameInput.setText(null);
			userPrenomInput.setText(null);
			userEmailInput.setText(null);
			userTelInput.setText(null);
			dateChooser.setDate(new Date());
		});
		cancelUserButton.setBounds(126, 338, 85, 21);
		panel.add(cancelUserButton);

		updateUserTableData(userTable);

		JLabel phoneRefLabel = new JLabel("Reference :");
		phoneRefLabel.setBounds(59, 25, 79, 23);
		panel_1.add(phoneRefLabel);

		JLabel phoneNameLabel = new JLabel("Label :");
		phoneNameLabel.setBounds(59, 70, 79, 23);
		panel_1.add(phoneNameLabel);

		JLabel phoneMarqueLabel = new JLabel("Marque :");
		phoneMarqueLabel.setBounds(59, 113, 79, 23);
		panel_1.add(phoneMarqueLabel);

		JLabel phoneUserLabel = new JLabel("User :");
		phoneUserLabel.setBounds(59, 159, 79, 23);
		panel_1.add(phoneUserLabel);

		phoneRefInput = new JTextField();
		phoneRefInput.setBounds(166, 27, 203, 19);
		panel_1.add(phoneRefInput);
		phoneRefInput.setColumns(10);

		phoneLabelInput = new JTextField();
		phoneLabelInput.setColumns(10);
		phoneLabelInput.setBounds(166, 72, 203, 19);
		panel_1.add(phoneLabelInput);

		phoneMarqueInput = new JTextField();
		phoneMarqueInput.setColumns(10);
		phoneMarqueInput.setBounds(166, 115, 203, 19);
		panel_1.add(phoneMarqueInput);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(64, 233, 775, 251);
		panel_1.add(scrollPane_1);

		smartPhoneTable = new JTable();
		smartPhoneTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Reference (imei)", "Label", "Marque", "User Email" }));
		smartPhoneTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedRef = smartPhoneTable.getModel().getValueAt(smartPhoneTable.getSelectedRow(), 0)
						.toString();
				SmartPhone sp = smartPhoneRemote.findByRef(selectedRef);
				if (sp != null) {
					phoneRefInput.setText(sp.getRef());
					phoneLabelInput.setText(sp.getName());
					phoneMarqueInput.setText(sp.getMarque());
					if (sp.getUser() == null) {
						phoneUserComboBox.getModel().setSelectedItem(phoneUserComboBox.getItemAt(0));
					} else {
						for (int i = 0; i < phoneUserComboBox.getItemCount(); i++) {
							if (phoneUserComboBox.getItemAt(i) != null) {
								if (phoneUserComboBox.getItemAt(i).toString().equals(sp.getUser().getEmail())) {
									phoneUserComboBox.getModel().setSelectedItem(phoneUserComboBox.getItemAt(i));
								}
							}
						}
					}
					selectedPhone = sp;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		updatePhoneTableData(smartPhoneTable);
		smartPhoneTable.getColumnModel().getColumn(0).setPreferredWidth(97);
		scrollPane_1.setViewportView(smartPhoneTable);

		JButton savePhoneButton = new JButton("Save");
		savePhoneButton.setBounds(463, 43, 85, 21);
		savePhoneButton.addActionListener(e -> {
			if (selectedPhone != null) {
				if (phoneUserComboBox.getSelectedItem() == null) {
					int result = smartPhoneRemote.update(new SmartPhone(null, phoneRefInput.getText(),
							phoneLabelInput.getText(), phoneMarqueInput.getText(), null, null));
					if (result > 0) {
						JOptionPane.showMessageDialog(frame, "Smart Phone Updated successfully !");
						updatePhoneTableData(smartPhoneTable);
						selectedPhone = null;
					}
				} else {
					int result = smartPhoneRemote.update(new SmartPhone(null, phoneRefInput.getText(),
							phoneLabelInput.getText(), phoneMarqueInput.getText(),
							userRemote.findByEmail((String) phoneUserComboBox.getSelectedItem()), null));
					if (result > 0) {
						JOptionPane.showMessageDialog(frame, "Smart Phone Updated successfully !");
						updatePhoneTableData(smartPhoneTable);
						selectedPhone = null;
					}
				}
			} else {
				if (phoneUserComboBox.getSelectedItem() == null) {
					int result = smartPhoneRemote.save(new SmartPhone(null, phoneRefInput.getText(),
							phoneLabelInput.getText(), phoneMarqueInput.getText(), null, null));
					if (result > 0) {
						JOptionPane.showMessageDialog(frame, "Smart Phone Saved successfully !");
						updatePhoneTableData(smartPhoneTable);
						selectedPhone = null;
					}
				} else {
					int result = smartPhoneRemote.save(new SmartPhone(null, phoneRefInput.getText(),
							phoneLabelInput.getText(), phoneMarqueInput.getText(),
							userRemote.findByEmail((String) phoneUserComboBox.getSelectedItem()), null));
					if (result > 0) {
						JOptionPane.showMessageDialog(frame, "Smart Phone Saved successfully !");
						updatePhoneTableData(smartPhoneTable);
						selectedPhone = null;
					}
				}
			}
		});
		panel_1.add(savePhoneButton);

		JButton deletePhoneButton = new JButton("Delete");
		deletePhoneButton.setBounds(607, 43, 85, 21);
		deletePhoneButton.addActionListener(e -> {
			if (selectedPhone == null) {
				JOptionPane.showMessageDialog(frame, "Please Selected A Phone from the Table Below");
			} else {
				int result = JOptionPane.showConfirmDialog(frame, "Sure you want to delete this Phone ? ",
						"Delete Phone", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					int transactionResult = smartPhoneRemote.deleteById(selectedPhone.getId());
					if (transactionResult > 0) {
						JOptionPane.showMessageDialog(frame, "Smart Phone Deleted successfully !");
						updatePhoneTableData(smartPhoneTable);
						selectedPhone = null;
					}
				}
			}
		});
		panel_1.add(deletePhoneButton);

		JButton cancelPhone = new JButton("Cancel");
		cancelPhone.setBounds(535, 88, 85, 21);
		cancelPhone.addActionListener(e -> {
			selectedPhone = null;
			phoneRefInput.setText(null);
			phoneLabelInput.setText(null);
			phoneMarqueInput.setText(null);
		});
		panel_1.add(cancelPhone);

	}

	private void updateUserTableData(JTable table) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		List<User> allUsers = userRemote.findAll();
		for (User u : allUsers) {
			dtm.addRow(
					new Object[] { u.getNom(), u.getPrenom(), u.getTelephone(), u.getEmail(), u.getDateNaissance() });
		}
	}

	private void updatePhoneTableData(JTable table) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		for (SmartPhone p : smartPhoneRemote.findAll()) {
			dtm.addRow(new Object[] { p.getRef(), p.getName(), p.getMarque(),
					p.getUser() == null ? null : p.getUser().getEmail() });
		}
	}

	private void updateUsersComboBox(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem(null);
		for (User u : userRemote.findAll()) {
			comboBox.addItem(u.getEmail());
		}
	}

}
