
package com.cuoiky;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextPane;

public class ServerHandlerChat {

	private ServerSocket serverSocket;
	private JTextPane txpMessage;
	private ChatMessageSocket mSocket;

	public ServerHandlerChat(ServerSocket serverSocket, JTextPane txpMessage) {
		super();
		this.serverSocket = serverSocket;
		this.txpMessage = txpMessage;

		try {

		} catch (Exception e) {
			Thread th = new Thread() {
				@Override
				public void run() {
					try {
						txpMessage.setText(txpMessage.getText() + "\nListening...");
						Socket socket = serverSocket.accept();
						txpMessage.setText(txpMessage.getText() + "\nClient connected...");
						mSocket = new ChatMessageSocket(socket, txpMessage);
					} catch (Exception e2) {
						txpMessage.setText("\nError: " + e2.getMessage());
						e2.printStackTrace();
					}
				}
			};
			th.start();
			e.printStackTrace();
		}
	}

}
