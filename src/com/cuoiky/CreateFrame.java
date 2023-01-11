package com.cuoiky;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class CreateFrame extends Thread {
	String width = "", heigh = "";
	private JFrame frame = new JFrame();
	private JDesktopPane desktop = new JDesktopPane();
	private Socket cSocket = null;
	private JInternalFrame interFrame = new JInternalFrame("Server Screen", true, true, true);
	private JPanel cPanel = new JPanel();

	public CreateFrame(Socket cSocket, String width, String heigh) {
		this.width = width;
		this.heigh = heigh;
		this.cSocket = cSocket;
		start();
	}

	public void drawGUI() throws PropertyVetoException {
		frame.add(desktop, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		interFrame.setLayout(new BorderLayout());
		interFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
		interFrame.setSize(100, 100);
		desktop.add(interFrame);

		interFrame.setMaximum(true);

		cPanel.setFocusable(true);
		interFrame.setVisible(true);

	}

	@Override
	public void run() {
		InputStream in = null;
		try {
			drawGUI();
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		try {
			in = cSocket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		new ReceivingScreen(in, cPanel);
		new SendEvents(cSocket, cPanel, width, heigh);

	}

}
