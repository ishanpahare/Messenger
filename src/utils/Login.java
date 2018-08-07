package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ChatClient;

public class Login {

	public static void main(String[] args) {
		final JFrame login = new JFrame("LOGIN");
		JPanel panel = new JPanel();
		final JTextField loginName=new JTextField(20);
		JButton enter = new JButton("LOGIN!");
		
		panel.add(loginName);
		panel.add(enter);
		login.setSize(300,100);
		login.add(panel);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					try {
						ChatClient client= new ChatClient(loginName.getText());
						login.setVisible(false);
						login.dispose(); //to dispose the frame and free up resources
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

	}

}
