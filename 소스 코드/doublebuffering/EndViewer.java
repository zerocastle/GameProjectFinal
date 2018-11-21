package doublebuffering;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

// 

public class EndViewer extends JPanel {

	public JButton button1 = null; // 랭킹보기
	public JButton button2 = null; // 랭킹등록
	private JButton button3 = null; // 게임 다시 시작
	private DoubleBuffering buffer = null;

	Image backGround = new ImageIcon(getClass().getResource("/image/endViewBackground.jpg")).getImage();
	
	public EndViewer(DoubleBuffering buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
		this.setLayout(null); // 절대경로는 setBounds로 위치 를 직접 정해준다.
		this.button1 = new JButton("랭킹보기");
		button1.setBounds(1600 / 2, 900 / 2 - 100, 100, 50);
		
		this.button2 = new JButton("랭킹등록");
		button2.setBounds(1600 / 2, (900 / 2) - 200, 100, 50);

		this.button3 = new JButton("retry?");
		button3.setBounds(1600 / 2, (900 / 2) - 300, 100, 50);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.button1.addActionListener(new MyActionListenerEndViewer());
		this.button2.addActionListener(new MyActionListenerEndViewer());
		this.button3.addActionListener(new MyActionListenerEndViewer());
	}

	class MyActionListenerEndViewer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton button = (JButton) e.getSource();
			String command = button.getText();
			if (command.equals("랭킹보기")) {
				buffer.change("showRank");
			} else if (command.equals("랭킹등록")) {
				buffer.change("insertRank");
			} else
				buffer.change("retry");

		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(this.backGround, 0, 0, null);
	}

}
