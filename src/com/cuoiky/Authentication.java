package com.cuoiky;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Authentication extends JFrame {

	private JTextPane txpMessage;
	private Socket cSocket = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	String verify = "";
	String width = "", height = "";

	public Authentication(Socket cSocket, JTextPane txpMessage, JTextField txtPass) throws HeadlessException {
		this.txpMessage = txpMessage;
		this.cSocket = cSocket;
		verifyAuthen(txtPass);
	}

//	Xu lý xác thực
	private void verifyAuthen(JTextField txtPass) {
		try {
			dos = new DataOutputStream(cSocket.getOutputStream());
			dis = new DataInputStream(cSocket.getInputStream());

			dos.writeUTF(txtPass.getText());
			verify = dis.readUTF();

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		if (verify.equals("valid")) {
			try {
				width = dis.readUTF();
				height = dis.readUTF();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			CreateFrame abc = new CreateFrame(cSocket, width, height);

		} else {
			System.out.println("Please enter valid password");
			JOptionPane.showMessageDialog(this, "Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

}
