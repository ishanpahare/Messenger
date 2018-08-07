package client;
import server.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements Runnable {
	
	Socket socket;
	JTextArea ta;
	JButton send,logout;
	JTextField tf;
	
	Thread thread;
	DataInputStream din;
	DataOutputStream dout;
	
	String loginName;
	
	public ChatClient(String login) throws UnknownHostException, IOException {
		super(login);
		loginName=login;
		
		ta=new JTextArea(18,40);
		tf=new JTextField(40);
		
		send=new JButton("Send");
		logout=new JButton("logout");
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(loginName+" "+"DATA "+tf.getText().toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(loginName+" "+"LOGOUT");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		socket = new Socket("localhost",1234); 
		din=new DataInputStream(socket.getInputStream());
		dout=new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(loginName);
		dout.writeUTF(loginName+" "+ "Login");
		
		thread=new Thread(this);
		thread.start();
		setup();
		
		}
	private void setup()
	{
		setSize(500,400);
		
		JPanel panel=new JPanel();
		panel.add(new JScrollPane(ta));
		panel.add(tf);
		panel.add(send);
		panel.add(logout);
		add(panel);
		
		
		setVisible(true); 
	}
	@Override
	public void run() {
		while(true)
		{
			try {
				ta.append("\n"+din.readUTF());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
