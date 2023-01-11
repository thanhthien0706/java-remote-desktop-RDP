package com.cuoiky;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class SendScreen extends Thread {
	Socket socket = null;
	Robot robot = null;
	Rectangle rectangle = null;
	boolean continueLoop = true;
	OutputStream oos = null;

	public SendScreen(Socket socket, Robot robot, Rectangle rectangle) {
		this.socket = socket;
		this.robot = robot;
		this.rectangle = rectangle;

		start();
	}

	@Override
	public void run() {
		try {
			oos = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (continueLoop) {
// tạo một hành ảnh chụp màn hình trong cái hình chữ nhật
			BufferedImage image = robot.createScreenCapture(rectangle);

			try {
//				Ghi ảnh với địn dạng jpeg vào một luông output stram để guwriqua server
				ImageIO.write(image, "jpeg", oos);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
