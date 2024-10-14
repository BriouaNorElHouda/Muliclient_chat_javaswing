 package MINIPRJchat;
   import java.sql.*;
import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class register extends JFrame  {

	private JPanel contentPane;
	private JTextField nom;
	private JTextField prenom;
	private JTextField nt;
	private JTextField addresse;
	private JPasswordField ps;
	private JTextField user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				try {
					register frame = new register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
				
				
				);
		
		
		
		 


	}
	

	/**
	 * Create the frame.
	 */
	public register() {
		setTitle("Register Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Register Form");
		lblNewLabel.setForeground(new Color(54, 79, 107));
		lblNewLabel.setFont(new Font("Californian FB", Font.BOLD, 22));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(148, 0, 147, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom :");
		lblNewLabel_1.setForeground(new Color(54, 79, 107));
		lblNewLabel_1.setBounds(22, 50, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom :");
		lblNewLabel_2.setForeground(new Color(54, 79, 107));
		lblNewLabel_2.setBounds(22, 81, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Num\u00E9ro de t\u00E9l\u00E9phone :");
		lblNewLabel_3.setForeground(new Color(54, 79, 107));
		lblNewLabel_3.setBounds(22, 175, 113, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Adresse :");
		lblNewLabel_4.setForeground(new Color(54, 79, 107));
		lblNewLabel_4.setBounds(22, 200, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		nom = new JTextField();
		nom.setBounds(78, 47, 86, 20);
		contentPane.add(nom);
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setBounds(78, 78, 86, 20);
		contentPane.add(prenom);
		prenom.setColumns(10);
		
		nt = new JTextField();
		nt.setBounds(148, 172, 86, 20);
		contentPane.add(nt);
		nt.setColumns(10);
		
		addresse = new JTextField();
		addresse.setBounds(78, 197, 156, 20);
		contentPane.add(addresse);
		addresse.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.setBackground(new Color(54, 79, 107));
		btnNewButton_1.setForeground(new Color(252, 81, 133));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pja", "root", "");
					Statement stm =con.createStatement();
					
					String quer ="INSERT into login(username,password) VALUES('"+user.getText()+"','"+ps.getText()+"')";
					stm.executeUpdate(quer);
					
					
					String quer2 ="INSERT into register (nom,prenom,username,password,Numphone,Adresse) VALUES('"+nom.getText()+"','"+prenom.getText()+"','"+user.getText()+"','"+ps.getText()+"','"+nt.getText()+"','"+addresse.getText()+"')";
					stm.executeUpdate(quer2);
					
					String quer1 ="select idlogin from login where username='"+user.getText()+"' and password ='"+ps.getText()+"'";
					ResultSet rs = stm.executeQuery(quer1);
					rs.next();
					
					
					if(!rs.next())
					JOptionPane.showMessageDialog(null,"You registred successfully");
					login lg = new login();
					((login)lg).setVisible(true);
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"Something wrong happened, Please check your informations");
									}
			}
		});
		btnNewButton_1.setBounds(324, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel password = new JLabel("Password:");
		password.setForeground(new Color(54, 79, 107));
		password.setBounds(22, 150, 55, 14);
		contentPane.add(password);
		
		ps = new JPasswordField();
		ps.setBounds(78, 144, 86, 20);
		contentPane.add(ps);
		
		JLabel username = new JLabel("Username :");
		username.setForeground(new Color(54, 79, 107));
		username.setBounds(22, 114, 55, 14);
		contentPane.add(username);
		
		user = new JTextField();
		user.setBounds(78, 111, 86, 20);
		contentPane.add(user);
		user.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\dell\\Downloads\\download-removebg-preview.png"));
		btnNewButton.setBounds(257, 43, 156, 146);
		contentPane.add(btnNewButton);
	}
}
