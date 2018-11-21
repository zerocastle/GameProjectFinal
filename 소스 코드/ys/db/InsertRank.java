package ys.db;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import doublebuffering.DoubleBuffering;

public class InsertRank extends JPanel {
	Image backGround = new ImageIcon(getClass().getResource("/image/b.jpg")).getImage();

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet set = null;

	String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	String dbUser = "practice";
	String dbPassword = "123456";
	String query = "insert into gamemember (name,score) values(?,?)";

	JTextField name;
	JTextField score;
	JButton button;

	private DoubleBuffering buffer = null;
	private JButton back = new JButton("뒤로가기");
	
	public InsertRank(DoubleBuffering buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
		name = new JTextField(15);
		score = new JTextField(15);
		button = new JButton("등록");
		score.setText(Integer.toString(DoubleBuffering.SCORE));
		score.setEnabled(false);
		back.addActionListener(new MyActionListenerInsertRank());
		button.addActionListener(new MyActionListenerInsertRank());
		this.add(name);
		this.add(score);
		this.add(button);
		this.add(back);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(this.backGround, 0, 0, null);
	}

	public void addMember() {
		// TODO Auto-generated method stub
		int signal = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, this.name.getText());
			pstmt.setInt(2, DoubleBuffering.SCORE);
			signal = pstmt.executeUpdate();
			if(signal > 0) {
				JOptionPane.showMessageDialog(null,this.name.getText() + " 님 등록 되었습니다.");
			}else
				JOptionPane.showMessageDialog(null, "등고에 실패 했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null) {
					con.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(set != null) {
					set.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	

	}
	class MyActionListenerInsertRank implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton button = (JButton)e.getSource();
			String command = button.getText();
			
			if(command.equals("뒤로가기")) {
				buffer.change("backButton");
			}else if(command.equals("등록")) {
				addMember();
				buffer.change("backButton");
			}
		}
		
	}

}
