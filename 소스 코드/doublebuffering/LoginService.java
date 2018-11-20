package doublebuffering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {
	
//	private static boolean IN = false;

	public static boolean LoginTest(String id, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet set = null;
		boolean signal = false;

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String dbUser = "practice";
		String dbPassword = "123456";
		
		String query = "select * from game where id =? ";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			set = pstmt.executeQuery();
			if(set != null) {
				while(set.next()) {
					String dbPw = set.getString("password");
					System.out.println(dbPw);
					if(password.equals(dbPw)) {
						signal = true;
					}else
						signal = false;
				}
			}else
				signal = false;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (set != null) {
					set.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return signal;
	}

}
