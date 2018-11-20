package ys.db;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import doublebuffering.DoubleBuffering;

public class ShowRank extends JPanel {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet set = null;

	String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	String dbUser = "practice";
	String dbPassword = "123456";

	String query = "select * from gameMember order by score desc";
	private DoubleBuffering buffer = null;
	private JButton back = new JButton("뒤로가기");
	
	public ShowRank(DoubleBuffering buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
		this.showRankList();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
	}

	public void showRankList() {
		// TODO Auto-generated method stub
		int counter = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			pstmt = con.prepareStatement(query);
			set = pstmt.executeQuery();

			while (set.next()) {
				++counter;
				this.add(new JLabel(counter + "순위 : " + set.getString("name") + set.getString("score")));
				System.out.println(set.getString("name"));

			}
			this.setVisible(true);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (con != null)
					con.close();
				if (pstmt != null)
					pstmt.close();
				if (set != null)
					set.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		this.back.addActionListener(new MyActionListenerEndViewer());
		this.add(this.back);
		

	}
	
	class MyActionListenerEndViewer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buffer.change("backButton");
		}
		
	}

}
