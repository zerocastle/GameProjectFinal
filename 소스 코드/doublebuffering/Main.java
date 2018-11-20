package doublebuffering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener {
	BufferedImage img = null;
	JTextField loginTextField;
	JPasswordField passwordField;
	JButton button;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		// TODO Auto-generated constructor stub
		this.setTitle("로그인 테스트");
		this.setSize(1600, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		setLocationRelativeTo(null);

		try {
			img = ImageIO.read(new File("login/login.png"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		// 레이아웃 설정
		JLayeredPane layeredpane = new JLayeredPane(); // 셀로판지
		layeredpane.setBounds(0, 0, 1600, 900);

		// 패널1
		MyPanel panel = new MyPanel();
		panel.setBounds(0, 0, 1600, 900);

		// 로그인
		this.loginTextField = new JTextField(15);
		this.loginTextField.setBounds(731, 399, 280, 30);
		this.loginTextField.setOpaque(false); // 투명처리
		this.loginTextField.setForeground(Color.green); // 전경색
		this.loginTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		// 패스워드
		this.passwordField = new JPasswordField();
		passwordField.setBounds(731, 529, 280, 30);
		this.passwordField.setOpaque(false);
		this.passwordField.setForeground(Color.green);
		this.passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		layeredpane.add(passwordField);
		layeredpane.add(loginTextField);

		// 로그인 버튼추가
		this.button = new JButton(new ImageIcon("login/btLogin_hud.png"));
		button.setBounds(755, 689, 104, 48);
		// 버튼 투명처리
		this.button.setBorderPainted(false);
		this.button.setFocusPainted(false);
		this.button.setContentAreaFilled(false);
		button.addActionListener(this);
		layeredpane.add(button);

		// 마지막 추가들
		layeredpane.add(panel);

		this.add(layeredpane);
		this.setVisible(true);

	}

	class MyPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			g.drawImage(img, 0, 0, null);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String id = loginTextField.getText();
		char[] pass = passwordField.getPassword();
		String password = new String(pass);

		if (id.equals("") || password.equals("")) {
			// 메세지를 날린다.
			JOptionPane.showMessageDialog(null, "빈칸이있음");

		} else {
			boolean existLogin = LoginService.LoginTest(id, password);
			if (existLogin) {
				JOptionPane.showMessageDialog(null, "success");
				this.dispose(); // 창 끄게함
				// 쓰레드 실행 DoubleBuffering 클래스를 호출한다 .

				Thread t1 = new Thread(new DoubleBuffering());
				t1.start();
			} else {
				JOptionPane.showMessageDialog(null, "fail"); // 메세지 띄우기
			}
		}
	}

}
