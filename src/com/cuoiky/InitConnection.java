package com.cuoiky;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextPane;

public class InitConnection {
	ServerSocket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	String width = "";
	String height = "";

	private JTextPane txpMessage;

	public InitConnection(ServerSocket serverSocket, String value1, JTextPane txpMessage) {
		this.txpMessage = txpMessage;
		this.socket = serverSocket;

		Robot robot = null;
		Rectangle rectangle = null;

		try {
			this.txpMessage.setText(this.txpMessage.getText() + "\nWaiting for client to get connected");

//			lấy môt trường đồ họa trên máy local
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();

//			Tạo đối tượng GD để lấy thông thông tin màn hình mặc định
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

//			Lấy kích thương màn hình hiện tại và lưu vào dimO
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			String width = "" + dim.getWidth();
			String height = "" + dim.getHeight();

//			Tao hinh chữ nhật với kích thước dim
			rectangle = new Rectangle(dim);

//			Tạo đối tượng robot để khiểm soats chuột bàn phím,
			robot = new Robot(gDev);

			eventHandler();

			while (true) {
				Socket sc = socket.accept();
				dis = new DataInputStream(sc.getInputStream());
				dos = new DataOutputStream(sc.getOutputStream());

				String password1 = dis.readUTF();
				if (password1.equals(value1)) {
					dos.writeUTF("valid");
					dos.writeUTF(width);
					dos.writeUTF(height);

					new SendScreen(sc, robot, rectangle);
					new ReceiveEvents(sc, robot);
				} else {
					dos.writeUTF("invalid");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eventHandler() {
	}

}
