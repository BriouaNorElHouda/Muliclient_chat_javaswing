package MINIPRJchat;


import java.awt.EventQueue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class server extends JFrame {

private static final long serialVersionUID = 1L;
private static Map<String, Socket> Users = new ConcurrentHashMap<>();
private static Set<String> aUsers = new HashSet<>(); 
private static int port = 7777;  
private JFrame frame;
private ServerSocket serverSocket; 
private JTextArea serverM; 
private JList usernameList;  
private JList activeCList; 
private DefaultListModel<String> activeDlm = new DefaultListModel<String>();
private DefaultListModel<String> allDlm = new DefaultListModel<String>(); 
private JButton btnNewButton;

	
	public static void main(String[] args) {  
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server window = new server();  
					window.frame.setVisible(true); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public server()  {
		initialize();  
		try {
			serverSocket = new ServerSocket(port);  
			serverM.append("Serveur démarré sur le port: " + port + "\n"); 
			serverM.append("En attendant les clients...\n");
			new Clientth().start(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Clientth  extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Socket cs = serverSocket.accept(); 
					String uName = new DataInputStream(cs.getInputStream()).readUTF(); 
					DataOutputStream cOutStream = new DataOutputStream(cs.getOutputStream()); 
					if (aUsers!= null && aUsers.contains(uName)) { 
						cOutStream.writeUTF("Username already taken");
					} else {
						Users.put(uName, cs); 
						aUsers.add(uName);
						cOutStream.writeUTF(""); 
						activeDlm.addElement(uName); 
						if (!allDlm.contains(uName)) 
							allDlm.addElement(uName);
						activeCList.setModel(activeDlm); 
						usernameList.setModel(allDlm);
						serverM.append("Client " + uName + " Connected...\n"); 
						new lirem(cs, uName).start(); 
						new CLientList().start(); 
					}
				} catch (IOException ioex) {  
					ioex.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class lirem extends Thread { 
		Socket s;
		String Id;
		private lirem(Socket s, String uname) { 
			this.s = s;
			this.Id = uname;
		}

		@Override
		public void run() {
			while (usernameList != null && !Users.isEmpty()) {  
				try {
					String message = new DataInputStream(s.getInputStream()).readUTF(); 
					System.out.println("message read ==> " + message); 
					String[] msgList = message.split(":"); 
						
						Iterator<String> itr1 = Users.keySet().iterator(); 
						while (itr1.hasNext()) {
							String usrName = (String) itr1.next(); 
							if (!usrName.equalsIgnoreCase(Id)) { 
								try {
									if (aUsers.contains(usrName)) { 
										new DataOutputStream(((Socket) Users.get(usrName)).getOutputStream())
												.writeUTF( Id + " :" + msgList[1]);
									} else {
										
										new DataOutputStream(s.getOutputStream())
												.writeUTF("Message couldn't be delivered to user " + usrName + " because it is disconnected.\n");
									}
								} catch (Exception e) {
									e.printStackTrace(); 
								}
							}
						}
					if (msgList[0].equalsIgnoreCase("exit")) { 
						aUsers.remove(Id); 
						serverM.append(Id + " disconnected....\n"); 

						new CLientList().start();

						Iterator<String> itr = aUsers.iterator(); 
						while (itr.hasNext()) {
							String usrName2 = (String) itr.next();
							if (!usrName2.equalsIgnoreCase(Id)) { 
								try {
									new DataOutputStream(((Socket) Users.get(usrName2)).getOutputStream())
											.writeUTF(Id + " disconnected..."); 
								} catch (Exception e) { 
									e.printStackTrace();
								}
								new CLientList().start(); 
							}
						}
						activeDlm.removeElement(Id); 
						activeCList.setModel(activeDlm); 
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class CLientList extends Thread { 
		@Override
		public void run() {
			try {
				String ids = "";
				Iterator itr = aUsers.iterator(); 
				while (itr.hasNext()) { 
					String key = (String) itr.next();
					ids += key + ",";
				}
				if (ids.length() != 0) { 
					ids = ids.substring(0, ids.length() - 1);
				}
				itr = aUsers.iterator(); 
				while (itr.hasNext()) { 
					String key = (String) itr.next();
					try {
						new DataOutputStream(((Socket) Users.get(key)).getOutputStream())
								.writeUTF(":;.,/=" + ids); 
						} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	private void initialize() { 
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(54, 79, 107));
		frame.setBounds(100, 100, 796, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Server ");

		serverM = new JTextArea();
		serverM.setBackground(new Color(245, 245, 245));
		serverM.setEditable(false);
		serverM.setBounds(171, 11, 578, 469);
		frame.getContentPane().add(serverM);
		serverM.setText("Démarrage du serveur...\n");

		usernameList = new JList();
		usernameList.setBackground(new Color(245, 245, 245));
		usernameList.setBounds(10, 359, 132, 121);
		frame.getContentPane().add(usernameList);

		activeCList = new JList();
		activeCList.setBackground(new Color(245, 245, 245));
		activeCList.setBounds(10, 195, 132, 121);
		frame.getContentPane().add(activeCList);

		JLabel lblNewLabel = new JLabel("Les noms d'utilisateur:");
		lblNewLabel.setForeground(new Color(252, 81, 133));
		lblNewLabel.setFont(new Font("Californian FB", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 327, 141, 27);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("En ligne:");
		lblNewLabel_1.setForeground(new Color(252, 81, 133));
		lblNewLabel_1.setFont(new Font("Californian FB", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 165, 98, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\\\Users\\\\dell\\\\Downloads\\\\download-removebg-preview.png"));
		btnNewButton.setBounds(10, 11, 132, 143);
		frame.getContentPane().add(btnNewButton);

	}
}