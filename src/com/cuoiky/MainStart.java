package com.cuoiky;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class MainStart extends JFrame {

	private JPanel contentPane;
	private JTextField txtIPMe;
	private JTextField txtPassMe;
	private JTextField txtIPPartner;
	private JTextField txtPassPartner;
	private JButton btnControl;
	private JButton btnListen;
	private JTextPane txpMessage;

	private InetAddress IP;
	private String port = "4907";
	private ServerSocket serverSocket;

	public MainStart() {
		addControls();
		addEvents();
		showWindow();
		initMain();
	}

	private void addEvents() {
		btnControl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread(new Runnable() {
					public void run() {
						try {
							if (!txtIPPartner.getText().equals("") && !txtPassPartner.getText().equals("")) {

								Socket sc = new Socket(txtIPPartner.getText(), Integer.parseInt(port));
								txpMessage.setText(txpMessage.getText() + "\nConnecting to " + IP);

								new Authentication(sc, txpMessage, txtPassPartner);
							} else {
								txpMessage.setText(txpMessage.getText() + "\nEnter Ip and Password of partner");
							}

						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}).start();

			}

		});

		btnListen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							serverSocket = new ServerSocket(Integer.parseInt(port));
							new InitConnection(serverSocket, txtPassMe.getText(), txpMessage);
						} catch (Exception e3) {
							e3.printStackTrace();
						}
					}

				}).start();

			}
		});
	}

	private void initMain() {
		try {
			IP = InetAddress.getLocalHost();
			String x = IP.getHostAddress();

			txtIPMe.setText(x);
			txtPassMe.setText(randomNumber(4));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String randomNumber(int len) {
		String AB = "0123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	private void addControls() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cho phép điều khiển");
		lblNewLabel.setBackground(Color.CYAN);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 171, 33);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("IP của bạn");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 72, 68, 13);
		contentPane.add(lblNewLabel_1);

		txtIPMe = new JTextField();
		txtIPMe.setBounds(101, 57, 198, 42);
		contentPane.add(txtIPMe);
		txtIPMe.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Mật khẩu");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 136, 68, 13);
		contentPane.add(lblNewLabel_1_1);

		txtPassMe = new JTextField();
		txtPassMe.setColumns(10);
		txtPassMe.setBounds(101, 121, 198, 42);
		contentPane.add(txtPassMe);

		JLabel lbliuKhinMy = new JLabel("Điều khiển máy tính khác");
		lbliuKhinMy.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbliuKhinMy.setBackground(Color.CYAN);
		lbliuKhinMy.setBounds(372, 10, 229, 33);
		contentPane.add(lbliuKhinMy);

		JLabel lblNewLabel_1_2 = new JLabel("IP đối tác");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(372, 72, 68, 13);
		contentPane.add(lblNewLabel_1_2);

		txtIPPartner = new JTextField();
		txtIPPartner.setColumns(10);
		txtIPPartner.setBounds(463, 57, 198, 42);
		contentPane.add(txtIPPartner);

		JLabel lblNewLabel_1_1_1 = new JLabel("Mật khẩu");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(372, 136, 68, 13);
		contentPane.add(lblNewLabel_1_1_1);

		txtPassPartner = new JTextField();
		txtPassPartner.setColumns(10);
		txtPassPartner.setBounds(463, 121, 198, 42);
		contentPane.add(txtPassPartner);

		btnControl = new JButton("Điều khiển");
		btnControl.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnControl.setBounds(509, 195, 111, 33);
		contentPane.add(btnControl);

		btnListen = new JButton("Bat dau");
		btnListen.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnListen.setBounds(68, 195, 173, 33);
		contentPane.add(btnListen);

		txpMessage = new JTextPane();
		txpMessage.setBounds(14, 238, 651, 195);
		contentPane.add(txpMessage);

	}

	public void showWindow() {
		this.setSize(689, 481);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		MainStart frame = new MainStart();
		frame.setVisible(true);
	}
}
