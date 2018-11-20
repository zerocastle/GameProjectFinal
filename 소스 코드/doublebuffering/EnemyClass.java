/*
 * 
 * 적을 쓰레드 처리해서 구현 하려고 했지만 , 쓰레드 처리는 문제 없었으나 , 업데이트 되어야 할 x 축을 getX() 메소드로 mainScreen 클래스에서 가지고와 드로우
 * 해줄라고 했지만 , draw 부분에서 객체를 계속해서 생성을 해주는 바람에 계속 초기값 50을 가져서 실패함
 */

//package doublebuffering;
//
//import java.awt.Graphics;
//import java.awt.Image;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//public class EnemyClass extends Thread {
//
//	public static boolean THREAD_SIGNAL = true;
//	Image enemy;
//	List<Image> enemyList = null;
//	int enemyMax = 10;
//	int x, y, xDirection, yDirection; // 적에 위치
//	JFrame frame = null;
//	int enemyXSize = 36;
//	int enemyYSize = 36;
//	
//	Image dbImage;
//	Graphics dbg;
//
//	public EnemyClass() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public EnemyClass(JFrame frame) {
//		// TODO Auto-generated method stub
//		enemyList = new ArrayList<Image>();
//		// 이미지 불러오기
//
//		ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/image/enemy.png"));
//		enemy = enemyIcon.getImage();
//		for (int i = 0; i < enemyMax; i++) {
//			// System.out.println("test");
//			enemyList.add(enemy);
//		}
//		this.frame = frame;
//
//		// 처음 위치
//		x = 50;
//		y = 50;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setxDirection(int xDirection) {
//		this.xDirection = xDirection;
//	}
//
//	public void setyDirection(int yDirection) {
//		this.yDirection = yDirection;
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		if (THREAD_SIGNAL) {
//			move();
//		}
//	}
//
//	private void move() {
//		// TODO Auto-generated method stub
//		THREAD_SIGNAL = false;
//		Random r = new Random();
//		boolean signal = true;
//		int enemyXDirection = r.nextInt(DoubleBuffering.WIDTH);
//		int ememyYDirection = r.nextInt(DoubleBuffering.HEIGHT - this.enemyYSize);
//		int direction = r.nextInt(4);
//		System.out.println(enemyXDirection);
//		for (int i = x; i < enemyXDirection; i++) {
//			try {
//				Thread.sleep(5);
//				System.out.println("도냐?" + i);
//				this.x = this.x + i;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		THREAD_SIGNAL = true;
//	}
//	
//	
//	
//
//	private boolean pause() {
//		// TODO Auto-generated method stub
//
//		return false;
//
//	}
//}
