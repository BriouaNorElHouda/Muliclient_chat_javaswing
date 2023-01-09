package MINIPRJchat;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class login extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField user;
	private int port = 8818;
	private JFormattedTextField pass;
	private JPasswordField passw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login Form");
		lblNewLabel.setForeground(new Color(54, 79, 107));
		lblNewLabel.setFont(new Font("Californian FB", Font.BOLD, 22));
		lblNewLabel.setBounds(235, 11, 125, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username :");
		lblNewLabel_1.setForeground(new Color(54, 79, 107));
		lblNewLabel_1.setBounds(212, 63, 68, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password :");
		lblNewLabel_2.setForeground(new Color(54, 79, 107));
		lblNewLabel_2.setBounds(212, 108, 68, 14);
		contentPane.add(lblNewLabel_2);
		
		user = new JTextField();
		user.setBounds(290, 60, 86, 20);
		contentPane.add(user);
		user.setColumns(10);
		
		passw = new JPasswordField();
		passw.setBounds(290, 105, 86, 20);
		contentPane.add(passw);
		
		JButton btnLOGIN = new JButton("Login");
		btnLOGIN.setForeground(new Color(252, 81, 133));
		btnLOGIN.setBackground(new Color(54, 79, 107));
		btnLOGIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/pja", "root", "");
					Socket s = new Socket("localhost", port);
					String username = user.getText();
					DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
					DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
					outStream.writeUTF(username); // send username to the output stream
					Statement stm =con.createStatement();
					String quer ="select * from login where username='"+user.getText()+"' and password ='"+passw.getText().toString()+"'";
				    ResultSet rs=stm.executeQuery(quer);
				    rs.next();
				
		        	int x = rs.getInt("idlogin");
		
				String q ="select * from register where idregister="+x;
				ResultSet rs2=stm.executeQuery(q);
				if(rs2.next())
					JOptionPane.showMessageDialog(null,"login successfully");
				
				String msgFromServer = new DataInputStream(s.getInputStream()).readUTF();
				if(msgFromServer.equals("Username already taken")) {//if server sent this message then prompt user to enter other username
					JOptionPane.showMessageDialog(frame,  "Username already taken\n"); // show message in other dialog box
				}else {
					new chat(username, s); 
					
					
				}
				
				
				}catch(Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Something wrong happened, Please check your informations");
					
									}
			}
			
		});
		btnLOGIN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		btnLOGIN.setBounds(290, 148, 89, 23);
		contentPane.add(btnLOGIN);
		
		JButton btnREGISTER = new JButton("Register");
		btnREGISTER.setBackground(new Color(54, 79, 107));
		btnREGISTER.setForeground(new Color(252, 81, 131));
		btnREGISTER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				register rg = new register();
				((register)rg).setVisible(true);
			}
		});
		btnREGISTER.setBounds(290, 227, 89, 23);
		contentPane.add(btnREGISTER);
		
		JLabel lblNewLabel_3 = new JLabel("Vous n'avez pas de compte?");
		lblNewLabel_3.setBounds(77, 214, 188, 19);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Cr\u00E9ez-en un pour commencer \u00E0 chatter");
		lblNewLabel_4.setBounds(52, 229, 228, 19);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\dell\\Downloads\\lillie NAVY flower edit (_ USEE ! \u2764 liked on Polyvore featuring flowers, fillers, blue fillers, blue, flower fillers, backgrounds, doodles, text, quotes and scribble-removebg-preview (1).jpg"));
		btnNewButton.setBounds(10, 22, 188, 165);
		contentPane.add(btnNewButton);
		
		
		
		
	}
}
