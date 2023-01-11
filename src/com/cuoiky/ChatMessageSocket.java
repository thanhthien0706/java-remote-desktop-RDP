package com.cuoiky;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextPane;

public class ChatMessageSocket {
	private Socket socket;
	private JTextPane txpMessageBoard;

	private DataInputStream dis;
	private DataOutputStream dos;

	public ChatMessageSocket(Socket socket, JTextPane txpMessageBoard) throws IOException {
		this.socket = socket;
		this.txpMessageBoard = txpMessageBoard;

		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		receive();
	}

	private void receive() {
		Thread th = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						String line = dis.readUTF();
						if (line.contains("CHAT")) {
							String[] partS = line.split("CHAT");
							if (partS[0].equals("CHAT")) {
								txpMessageBoard.setText(txpMessageBoard.getText() + "\n" + partS[1]);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		th.start();
	}

	public void send(String mess) {
		try {
			String current = txpMessageBoard.getText();
			dos.writeUTF("CHAT" + mess);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			dos.close();
			dis.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
