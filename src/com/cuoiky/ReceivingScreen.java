package com.cuoiky;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReceivingScreen extends Thread {

	private ObjectInputStream cObjectInputStream = null;
	private JPanel cPanel = null;
	private boolean continueLoop = true;
	InputStream oin = null;
	Image image1 = null;

	private JTextField txtMessage;
	private JButton btnSend;

	public ReceivingScreen(InputStream oin, JPanel cPane) {
		this.cPanel = cPane;
		this.oin = oin;
		start();
	}

	@Override
	public void run() {
		try {

			while (continueLoop) {
				byte[] bytes = new byte[1024 * 1024];
				int count = 0;
				do {
					count += oin.read(bytes, count, bytes.length - count);
				} while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));
				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);

				Graphics graphics = cPanel.getGraphics();
				graphics.drawImage(image1, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
