package MINIPRJchat;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;




public class chat extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField cTyping;
	private JList activeCList;
	private JTextArea clientM;
	private JButton clientdic;
	

	DataInputStream inputStream;
	DataOutputStream outStream;
	DefaultListModel<String> dm;
	String username, clientIds = "";
	private JButton btnNewButton;


	public chat() {
		initialize();
	}

	public chat(String username, Socket s) {
		initialize(); 
		this.username =username;
		try {
			frame.setTitle("Client View - " + username); 
			dm = new DefaultListModel<String>(); 
			activeCList.setModel(dm);
			inputStream = new DataInputStream(s.getInputStream()); 
			outStream = new DataOutputStream(s.getOutputStream());
			new Read().start(); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	class Read extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					String m = inputStream.readUTF();  
					System.out.println("inside read thread : " + m); 
					if (m.contains(":;.,/=")) { 
						m = m.substring(6); 
						dm.clear(); 
						StringTokenizer st = new StringTokenizer(m, ","); 
						while (st.hasMoreTokens()) {
							String u = st.nextToken();
							if (!username.equals(u))
								dm.addElement(u); 
						}
					} else {
						clientM.append("" + m + "\n"); 
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

	
	private void initialize() { 
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(54, 79, 107));
		frame.setBounds(100, 100, 926, 705);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Client View");

		clientM = new JTextArea();
		clientM.setBackground(new Color(245, 245, 245));
		clientM.setEditable(false);
		clientM.setBounds(231, 21, 656, 556);
		frame.getContentPane().add(clientM);

		cTyping = new JTextField();
		cTyping.setHorizontalAlignment(SwingConstants.LEFT);
		cTyping.setBounds(231, 595, 566, 49);
		frame.getContentPane().add(cTyping);
		cTyping.setColumns(10);

		JButton clientSendMsgBtn = new JButton("Send");
		clientSendMsgBtn.setForeground(new Color(252, 81, 133));
		clientSendMsgBtn.setBackground(new Color(54, 79, 107));
		clientSendMsgBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String textAreaMessage = cTyping.getText(); 
				if (textAreaMessage != null && !textAreaMessage.isEmpty()) { 
					try {
						String messageToBeSentToServer = "";
						String cast = "broadcast"; 
						int flag = 0; 
						
							messageToBeSentToServer = cast + ":" + textAreaMessage; 
						
						
							outStream.writeUTF(messageToBeSentToServer);
							cTyping.setText("");
							clientM.append( textAreaMessage + "\n");
						
						clientIds = ""; 
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame, "User does not exist anymore."); 
					}
				}
			}
		});
		clientSendMsgBtn.setBounds(807, 595, 80, 49);
		frame.getContentPane().add(clientSendMsgBtn);

		activeCList = new JList();
		activeCList.setToolTipText("Active Users");
		activeCList.setBounds(12, 196, 193, 383);
		frame.getContentPane().add(activeCList);

		clientdic = new JButton("Disconnect");
		clientdic.setForeground(new Color(252, 81, 133));
		clientdic.setBackground(new Color(54, 79, 107));
		clientdic.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				try {
					outStream.writeUTF("exit"); 
					clientM.append("You are disconnected now.\n");
					frame.dispose(); 
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		clientdic.setBounds(12, 595, 193, 49);
		frame.getContentPane().add(clientdic);

		JLabel lblNewLabel = new JLabel("En ligne:");
		lblNewLabel.setFont(new Font("Californian FB", Font.BOLD, 14));
		lblNewLabel.setForeground(new Color(252, 81, 133));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(12, 169, 95, 16);
		frame.getContentPane().add(lblNewLabel);
		
		btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(54, 79, 107));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\dell\\Downloads\\lillie NAVY flower edit (_ USEE ! \u2764 liked on Polyvore featuring flowers, fillers, blue fillers, blue, flower fillers, backgrounds, doodles, text, quotes and scribble-removebg-preview (2).jpg"));
		btnNewButton.setBounds(10, 21, 195, 133);
		frame.getContentPane().add(btnNewButton);

		

		frame.setVisible(true);
	}
}